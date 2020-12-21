/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.protection.databases;

import au.com.bytecode.opencsv.CSVReader;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.AbstractProtectionDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.util.ArrayReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVDatabase
extends AbstractProtectionDatabase {
    private static final Map<String, StateFlag> legacyFlagCodes = new HashMap<String, StateFlag>();
    private final Logger logger;
    private final File file;
    private Map<String, ProtectedRegion> regions;

    public CSVDatabase(File file, Logger logger) {
        this.file = file;
        this.logger = logger;
    }

    @Override
    public void save() throws ProtectionDatabaseException {
        throw new UnsupportedOperationException("CSV format is no longer implemented");
    }

    @Override
    public void load() throws ProtectionDatabaseException {
        HashMap<String, ProtectedRegion> regions = new HashMap<String, ProtectedRegion>();
        LinkedHashMap<ProtectedCuboidRegion, String> parentSets = new LinkedHashMap<ProtectedCuboidRegion, String>();
        CSVReader reader = null;
        try {
            String[] line;
            reader = new CSVReader(new FileReader(this.file));
            while ((line = reader.readNext()) != null) {
                int priority;
                BlockVector max;
                BlockVector min;
                Vector pt2;
                Vector pt1;
                if (line.length < 2) {
                    this.logger.warning("Invalid region definition: " + line);
                    continue;
                }
                String id = line[0].toLowerCase().replace(".", "");
                String type = line[1];
                ArrayReader<String> entries = new ArrayReader<String>(line);
                if (type.equalsIgnoreCase("cuboid")) {
                    if (line.length < 8) {
                        this.logger.warning("Invalid region definition: " + line);
                        continue;
                    }
                    pt1 = new Vector(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]));
                    pt2 = new Vector(Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]));
                    min = Vector.getMinimum((Vector)pt1, (Vector)pt2).toBlockVector();
                    max = Vector.getMaximum((Vector)pt1, (Vector)pt2).toBlockVector();
                    priority = entries.get(8) == null ? 0 : Integer.parseInt(entries.get(8));
                    String ownersData = entries.get(9);
                    String flagsData = entries.get(10);
                    ProtectedCuboidRegion region = new ProtectedCuboidRegion(id, min, max);
                    region.setPriority(priority);
                    this.parseFlags(region, flagsData);
                    region.setOwners(this.parseDomains(ownersData));
                    regions.put(id, region);
                    continue;
                }
                if (!type.equalsIgnoreCase("cuboid.2")) continue;
                pt1 = new Vector(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]));
                pt2 = new Vector(Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]));
                min = Vector.getMinimum((Vector)pt1, (Vector)pt2).toBlockVector();
                max = Vector.getMaximum((Vector)pt1, (Vector)pt2).toBlockVector();
                priority = entries.get(8) == null ? 0 : Integer.parseInt(entries.get(8));
                String parentId = entries.get(9);
                String ownersData = entries.get(10);
                String membersData = entries.get(11);
                String flagsData = entries.get(12);
                ProtectedCuboidRegion region = new ProtectedCuboidRegion(id, min, max);
                region.setPriority(priority);
                this.parseFlags(region, flagsData);
                region.setOwners(this.parseDomains(ownersData));
                region.setMembers(this.parseDomains(membersData));
                regions.put(id, region);
                if (parentId.length() <= 0) continue;
                parentSets.put(region, parentId);
            }
        }
        catch (IOException e) {
            throw new ProtectionDatabaseException(e);
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException iOException) {}
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

    private DefaultDomain parseDomains(String data) {
        String[] parts;
        if (data == null) {
            return new DefaultDomain();
        }
        DefaultDomain domain = new DefaultDomain();
        Pattern pattern = Pattern.compile("^([A-Za-z]):(.*)$");
        for (String part : parts = data.split(",")) {
            if (part.trim().length() == 0) continue;
            Matcher matcher = pattern.matcher(part);
            if (!matcher.matches()) {
                this.logger.warning("Invalid owner specification: " + part);
                continue;
            }
            String type = matcher.group(1);
            String id = matcher.group(2);
            if (type.equals("u")) {
                domain.addPlayer(id);
                continue;
            }
            if (type.equals("g")) {
                domain.addGroup(id);
                continue;
            }
            this.logger.warning("Unknown owner specification: " + type);
        }
        return domain;
    }

    private void parseFlags(ProtectedRegion region, String data) {
        if (data == null) {
            return;
        }
        StateFlag.State curState = StateFlag.State.ALLOW;
        for (int i = 0; i < data.length(); ++i) {
            String flagStr;
            char k = data.charAt(i);
            if (k == '+') {
                curState = StateFlag.State.ALLOW;
                continue;
            }
            if (k == '-') {
                curState = StateFlag.State.DENY;
                continue;
            }
            if (k == '_') {
                if (i == data.length() - 1) {
                    this.logger.warning("_ read ahead fail");
                    break;
                }
                flagStr = "_" + data.charAt(i + 1);
                ++i;
                this.logger.warning("_? custom flags are no longer supported");
                continue;
            }
            flagStr = String.valueOf(k);
            StateFlag flag = legacyFlagCodes.get(flagStr);
            if (flag != null) {
                region.setFlag(flag, curState);
                continue;
            }
            this.logger.warning("Legacy flag '" + flagStr + "' is unsupported");
        }
    }

    protected String nullEmptyString(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return null;
        }
        return str;
    }

    @Override
    public Map<String, ProtectedRegion> getRegions() {
        return this.regions;
    }

    @Override
    public void setRegions(Map<String, ProtectedRegion> regions) {
        this.regions = regions;
    }

    static {
        legacyFlagCodes.put("z", DefaultFlag.PASSTHROUGH);
        legacyFlagCodes.put("b", DefaultFlag.BUILD);
        legacyFlagCodes.put("p", DefaultFlag.PVP);
        legacyFlagCodes.put("m", DefaultFlag.MOB_DAMAGE);
        legacyFlagCodes.put("c", DefaultFlag.CREEPER_EXPLOSION);
        legacyFlagCodes.put("t", DefaultFlag.TNT);
        legacyFlagCodes.put("l", DefaultFlag.LIGHTER);
        legacyFlagCodes.put("f", DefaultFlag.FIRE_SPREAD);
        legacyFlagCodes.put("F", DefaultFlag.LAVA_FIRE);
        legacyFlagCodes.put("C", DefaultFlag.CHEST_ACCESS);
    }
}

