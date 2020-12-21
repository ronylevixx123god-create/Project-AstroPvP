/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.util.yaml.YAMLFormat
 *  com.sk89q.util.yaml.YAMLNode
 *  com.sk89q.util.yaml.YAMLProcessor
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.BlockVector2D
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.protection.databases;

import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLNode;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.AbstractProtectionDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class YAMLDatabase
extends AbstractProtectionDatabase {
    private YAMLProcessor config;
    private Map<String, ProtectedRegion> regions;
    private final Logger logger;

    public YAMLDatabase(File file, Logger logger) throws ProtectionDatabaseException, FileNotFoundException {
        this.logger = logger;
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                throw new FileNotFoundException(file.getAbsolutePath());
            }
        }
        this.config = new YAMLProcessor(file, false, YAMLFormat.COMPACT);
    }

    @Override
    public void load() throws ProtectionDatabaseException {
        try {
            this.config.load();
        }
        catch (IOException e) {
            throw new ProtectionDatabaseException(e);
        }
        Map regionData = this.config.getNodes("regions");
        if (regionData == null) {
            this.regions = new HashMap<String, ProtectedRegion>();
            return;
        }
        HashMap<String, ProtectedRegion> regions = new HashMap<String, ProtectedRegion>();
        LinkedHashMap<ProtectedRegion, String> parentSets = new LinkedHashMap<ProtectedRegion, String>();
        for (Map.Entry entry : regionData.entrySet()) {
            String id = ((String)entry.getKey()).toLowerCase().replace(".", "");
            YAMLNode node = (YAMLNode)entry.getValue();
            String type = node.getString("type");
            try {
                ProtectedRegion region;
                if (type == null) {
                    this.logger.warning("Undefined region type for region '" + id + '\"');
                    continue;
                }
                if (type.equals("cuboid")) {
                    Vector pt1 = this.checkNonNull((Object)node.getVector("min"));
                    Vector pt2 = this.checkNonNull((Object)node.getVector("max"));
                    BlockVector min = Vector.getMinimum((Vector)pt1, (Vector)pt2).toBlockVector();
                    BlockVector max = Vector.getMaximum((Vector)pt1, (Vector)pt2).toBlockVector();
                    region = new ProtectedCuboidRegion(id, min, max);
                } else if (type.equals("poly2d")) {
                    Integer minY = this.checkNonNull(node.getInt("min-y"));
                    Integer maxY = this.checkNonNull(node.getInt("max-y"));
                    List points = node.getBlockVector2dList("points", null);
                    region = new ProtectedPolygonalRegion(id, points, minY, maxY);
                } else if (type.equals("global")) {
                    region = new GlobalProtectedRegion(id);
                } else {
                    this.logger.warning("Unknown region type for region '" + id + '\"');
                    continue;
                }
                Integer priority = this.checkNonNull(node.getInt("priority"));
                region.setPriority(priority);
                this.setFlags(region, node.getNode("flags"));
                region.setOwners(this.parseDomain(node.getNode("owners")));
                region.setMembers(this.parseDomain(node.getNode("members")));
                regions.put(id, region);
                String parentId = node.getString("parent");
                if (parentId == null) continue;
                parentSets.put(region, parentId);
            }
            catch (NullPointerException e) {
                this.logger.warning("Missing data for region '" + id + '\"');
            }
        }
        for (Map.Entry entry : parentSets.entrySet()) {
            ProtectedRegion parent = (ProtectedRegion)regions.get(entry.getValue());
            if (parent != null) {
                try {
                    ((ProtectedRegion)entry.getKey()).setParent(parent);
                }
                catch (ProtectedRegion.CircularInheritanceException e) {
                    this.logger.warning("Circular inheritance detect with '" + (String)entry.getValue() + "' detected as a parent");
                }
                continue;
            }
            this.logger.warning("Unknown region parent: " + (String)entry.getValue());
        }
        this.regions = regions;
    }

    private <V> V checkNonNull(V val) throws NullPointerException {
        if (val == null) {
            throw new NullPointerException();
        }
        return val;
    }

    private void setFlags(ProtectedRegion region, YAMLNode flagsData) {
        if (flagsData == null) {
            return;
        }
        for (Flag<?> flag : DefaultFlag.getFlags()) {
            Object o2;
            Object o = flagsData.getProperty(flag.getName());
            if (o != null) {
                this.setFlag(region, flag, o);
            }
            if (flag.getRegionGroupFlag() == null || (o2 = flagsData.getProperty(flag.getRegionGroupFlag().getName())) == null) continue;
            this.setFlag(region, flag.getRegionGroupFlag(), o2);
        }
    }

    private <T> void setFlag(ProtectedRegion region, Flag<T> flag, Object rawValue) {
        T val = flag.unmarshal(rawValue);
        if (val == null) {
            this.logger.warning("Failed to parse flag '" + flag.getName() + "' with value '" + rawValue.toString() + "'");
            return;
        }
        region.setFlag(flag, val);
    }

    private DefaultDomain parseDomain(YAMLNode node) {
        if (node == null) {
            return new DefaultDomain();
        }
        DefaultDomain domain = new DefaultDomain();
        for (String name : node.getStringList("players", null)) {
            domain.addPlayer(name);
        }
        for (String name : node.getStringList("groups", null)) {
            domain.addGroup(name);
        }
        return domain;
    }

    @Override
    public void save() throws ProtectionDatabaseException {
        this.config.clear();
        for (Map.Entry<String, ProtectedRegion> entry : this.regions.entrySet()) {
            ProtectedRegion region = entry.getValue();
            YAMLNode node = this.config.addNode("regions." + entry.getKey());
            if (region instanceof ProtectedCuboidRegion) {
                ProtectedCuboidRegion cuboid = (ProtectedCuboidRegion)region;
                node.setProperty("type", (Object)"cuboid");
                node.setProperty("min", (Object)cuboid.getMinimumPoint());
                node.setProperty("max", (Object)cuboid.getMaximumPoint());
            } else if (region instanceof ProtectedPolygonalRegion) {
                ProtectedPolygonalRegion poly = (ProtectedPolygonalRegion)region;
                node.setProperty("type", (Object)"poly2d");
                node.setProperty("min-y", (Object)poly.getMinimumPoint().getBlockY());
                node.setProperty("max-y", (Object)poly.getMaximumPoint().getBlockY());
                ArrayList points = new ArrayList();
                for (BlockVector2D point : poly.getPoints()) {
                    HashMap<String, Integer> data = new HashMap<String, Integer>();
                    data.put("x", point.getBlockX());
                    data.put("z", point.getBlockZ());
                    points.add(data);
                }
                node.setProperty("points", points);
            } else if (region instanceof GlobalProtectedRegion) {
                node.setProperty("type", (Object)"global");
            } else {
                node.setProperty("type", (Object)region.getClass().getCanonicalName());
            }
            node.setProperty("priority", (Object)region.getPriority());
            node.setProperty("flags", this.getFlagData(region));
            node.setProperty("owners", this.getDomainData(region.getOwners()));
            node.setProperty("members", this.getDomainData(region.getMembers()));
            ProtectedRegion parent = region.getParent();
            if (parent == null) continue;
            node.setProperty("parent", (Object)parent.getId());
        }
        this.config.setHeader("#\r\n# WorldGuard regions file\r\n#\r\n# WARNING: THIS FILE IS AUTOMATICALLY GENERATED. If you modify this file by\r\n# hand, be aware that A SINGLE MISTYPED CHARACTER CAN CORRUPT THE FILE. If\r\n# WorldGuard is unable to parse the file, your regions will FAIL TO LOAD and\r\n# the contents of this file will reset. Please use a YAML validator such as\r\n# http://yaml-online-parser.appspot.com (for smaller files).\r\n#\r\n# REMEMBER TO KEEP PERIODICAL BACKUPS.\r\n#");
        this.config.save();
    }

    private Map<String, Object> getFlagData(ProtectedRegion region) {
        HashMap<String, Object> flagData = new HashMap<String, Object>();
        for (Map.Entry<Flag<?>, Object> entry : region.getFlags().entrySet()) {
            Flag<?> flag = entry.getKey();
            this.addMarshalledFlag(flagData, flag, entry.getValue());
        }
        return flagData;
    }

    private <V> void addMarshalledFlag(Map<String, Object> flagData, Flag<V> flag, Object val) {
        if (val == null) {
            return;
        }
        flagData.put(flag.getName(), flag.marshal(val));
    }

    private Map<String, Object> getDomainData(DefaultDomain domain) {
        HashMap<String, Object> domainData = new HashMap<String, Object>();
        this.setDomainData(domainData, "players", domain.getPlayers());
        this.setDomainData(domainData, "groups", domain.getGroups());
        return domainData;
    }

    private void setDomainData(Map<String, Object> domainData, String key, Set<String> domain) {
        if (domain.size() == 0) {
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String str : domain) {
            list.add(str);
        }
        domainData.put(key, list);
    }

    @Override
    public Map<String, ProtectedRegion> getRegions() {
        return this.regions;
    }

    @Override
    public void setRegions(Map<String, ProtectedRegion> regions) {
        this.regions = regions;
    }
}

