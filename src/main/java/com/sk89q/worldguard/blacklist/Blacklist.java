/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.worldguard.blacklist;

import com.sk89q.worldedit.blocks.ItemType;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.BlacklistEntry;
import com.sk89q.worldguard.blacklist.BlacklistLogger;
import com.sk89q.worldguard.blacklist.BlacklistTrackedEvent;
import com.sk89q.worldguard.blacklist.events.BlacklistEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;

public abstract class Blacklist {
    private Map<Integer, List<BlacklistEntry>> blacklist = new HashMap<Integer, List<BlacklistEntry>>();
    private BlacklistLogger blacklistLogger = new BlacklistLogger();
    private BlacklistEvent lastEvent;
    Map<String, BlacklistTrackedEvent> lastAffected = new HashMap<String, BlacklistTrackedEvent>();
    private boolean useAsWhitelist;
    private final Logger logger;

    public Blacklist(Boolean useAsWhitelist, Logger logger) {
        this.useAsWhitelist = useAsWhitelist;
        this.logger = logger;
    }

    public boolean isEmpty() {
        return this.blacklist.isEmpty();
    }

    public List<BlacklistEntry> getEntries(int id) {
        return this.blacklist.get(id);
    }

    public int getItemCount() {
        return this.blacklist.size();
    }

    public boolean isWhitelist() {
        return this.useAsWhitelist;
    }

    public BlacklistLogger getLogger() {
        return this.blacklistLogger;
    }

    public boolean check(BlacklistEvent event, boolean forceRepeat, boolean silent) {
        List<BlacklistEntry> entries = this.getEntries(event.getType());
        if (entries == null) {
            return true;
        }
        boolean ret = true;
        for (BlacklistEntry entry : entries) {
            if (entry.check(this.useAsWhitelist, event, forceRepeat, silent)) continue;
            ret = false;
        }
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load(File file) throws IOException {
        FileReader input = null;
        HashMap<Integer, List<BlacklistEntry>> blacklist = new HashMap<Integer, List<BlacklistEntry>>();
        try {
            String line;
            input = new FileReader(file);
            BufferedReader buff = new BufferedReader(input);
            ArrayList<BlacklistEntry> currentEntries = null;
            block9: while ((line = buff.readLine()) != null) {
                if ((line = line.trim()).length() == 0 || line.charAt(0) == ';' || line.charAt(0) == '#') continue;
                if (line.matches("^\\[.*\\]$")) {
                    String[] items = line.substring(1, line.length() - 1).split(",");
                    currentEntries = new ArrayList<BlacklistEntry>();
                    for (String item : items) {
                        int id;
                        block29: {
                            try {
                                id = Integer.parseInt(item.trim());
                            }
                            catch (NumberFormatException e) {
                                id = Blacklist.getItemID(item.trim());
                                if (id != 0) break block29;
                                this.logger.log(Level.WARNING, "Unknown block name: " + item);
                                continue block9;
                            }
                        }
                        BlacklistEntry entry = new BlacklistEntry(this);
                        if (blacklist.containsKey(id)) {
                            ((List)blacklist.get(id)).add(entry);
                        } else {
                            ArrayList<BlacklistEntry> entries = new ArrayList<BlacklistEntry>();
                            entries.add(entry);
                            blacklist.put(id, entries);
                        }
                        currentEntries.add(entry);
                    }
                    continue;
                }
                if (currentEntries != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 1) {
                        this.logger.log(Level.WARNING, "Found option with no value " + file.getName() + " for '" + line + "'");
                        continue;
                    }
                    boolean unknownOption = false;
                    for (BlacklistEntry entry : currentEntries) {
                        if (parts[0].equalsIgnoreCase("ignore-groups")) {
                            entry.setIgnoreGroups(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("ignore-perms")) {
                            entry.setIgnorePermissions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-break")) {
                            entry.setBreakActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-destroy-with")) {
                            entry.setDestroyWithActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-place")) {
                            entry.setPlaceActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-interact")) {
                            entry.setInteractActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-use")) {
                            entry.setUseActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-drop")) {
                            entry.setDropActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("on-acquire")) {
                            entry.setAcquireActions(parts[1].split(","));
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("message")) {
                            entry.setMessage(parts[1].trim());
                            continue;
                        }
                        if (parts[0].equalsIgnoreCase("comment")) {
                            entry.setComment(parts[1].trim());
                            continue;
                        }
                        unknownOption = true;
                    }
                    if (!unknownOption) continue;
                    this.logger.log(Level.WARNING, "Unknown option '" + parts[0] + "' in " + file.getName() + " for '" + line + "'");
                    continue;
                }
                this.logger.log(Level.WARNING, "Found option with no heading " + file.getName() + " for '" + line + "'");
            }
            this.blacklist = blacklist;
        }
        finally {
            try {
                if (input != null) {
                    input.close();
                }
            }
            catch (IOException iOException) {}
        }
    }

    public BlacklistEvent getLastEvent() {
        return this.lastEvent;
    }

    public void notify(BlacklistEvent event, String comment) {
        this.lastEvent = event;
        this.broadcastNotification((Object)((Object)ChatColor.GRAY) + "WG: " + (Object)((Object)ChatColor.LIGHT_PURPLE) + event.getPlayer().getName() + (Object)((Object)ChatColor.GOLD) + " (" + event.getDescription() + ") " + (Object)((Object)ChatColor.WHITE) + Blacklist.getFriendlyItemName(event.getType()) + (comment != null ? " (" + comment + ")" : "") + ".");
    }

    public abstract void broadcastNotification(String var1);

    public void forgetPlayer(LocalPlayer player) {
        this.lastAffected.remove(player.getName());
    }

    public void forgetAllPlayers() {
        this.lastAffected.clear();
    }

    private static int getItemID(String name) {
        ItemType type = ItemType.lookup((String)name);
        if (type != null) {
            return type.getID();
        }
        return -1;
    }

    private static String getFriendlyItemName(int id) {
        ItemType type = ItemType.fromID((int)id);
        if (type != null) {
            return type.getName() + " (#" + id + ")";
        }
        return "#" + id + "";
    }
}

