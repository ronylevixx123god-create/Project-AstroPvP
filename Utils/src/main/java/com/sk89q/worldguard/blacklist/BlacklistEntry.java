/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.worldguard.blacklist;

import com.sk89q.worldedit.blocks.ItemType;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.Blacklist;
import com.sk89q.worldguard.blacklist.BlacklistTrackedEvent;
import com.sk89q.worldguard.blacklist.events.BlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockBreakBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockInteractBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockPlaceBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.DestroyWithBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemAcquireBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemDropBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemUseBlacklistEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.ChatColor;

public class BlacklistEntry {
    private Blacklist blacklist;
    private Set<String> ignoreGroups;
    private Set<String> ignorePermissions;
    private String[] breakActions;
    private String[] destroyWithActions;
    private String[] placeActions;
    private String[] interactActions;
    private String[] useActions;
    private String[] dropActions;
    private String[] acquireActions;
    private String message;
    private String comment;

    public BlacklistEntry(Blacklist blacklist) {
        this.blacklist = blacklist;
    }

    public String[] getIgnoreGroups() {
        return this.ignoreGroups.toArray(new String[this.ignoreGroups.size()]);
    }

    public String[] getIgnorePermissions() {
        return this.ignorePermissions.toArray(new String[this.ignorePermissions.size()]);
    }

    public void setIgnoreGroups(String[] ignoreGroups) {
        HashSet<String> ignoreGroupsSet = new HashSet<String>();
        for (String group : ignoreGroups) {
            ignoreGroupsSet.add(group.toLowerCase());
        }
        this.ignoreGroups = ignoreGroupsSet;
    }

    public void setIgnorePermissions(String[] ignorePermissions) {
        HashSet<String> ignorePermissionsSet = new HashSet<String>();
        Collections.addAll(ignorePermissionsSet, ignorePermissions);
        this.ignorePermissions = ignorePermissionsSet;
    }

    public String[] getBreakActions() {
        return this.breakActions;
    }

    public void setBreakActions(String[] actions) {
        this.breakActions = actions;
    }

    public String[] getDestroyWithActions() {
        return this.destroyWithActions;
    }

    public void setDestroyWithActions(String[] actions) {
        this.destroyWithActions = actions;
    }

    public String[] getPlaceActions() {
        return this.placeActions;
    }

    public void setPlaceActions(String[] actions) {
        this.placeActions = actions;
    }

    public String[] getInteractActions() {
        return this.interactActions;
    }

    public void setInteractActions(String[] actions) {
        this.interactActions = actions;
    }

    public String[] getUseActions() {
        return this.useActions;
    }

    public void setUseActions(String[] actions) {
        this.useActions = actions;
    }

    public String[] getDropActions() {
        return this.dropActions;
    }

    public void setDropActions(String[] actions) {
        this.dropActions = actions;
    }

    public String[] getAcquireActions() {
        return this.acquireActions;
    }

    public void setAcquireActions(String[] actions) {
        this.acquireActions = actions;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean shouldIgnore(LocalPlayer player) {
        if (this.ignoreGroups != null) {
            for (String group : player.getGroups()) {
                if (!this.ignoreGroups.contains(group.toLowerCase())) continue;
                return true;
            }
        }
        if (this.ignorePermissions != null) {
            for (String perm : this.ignorePermissions) {
                if (!player.hasPermission(perm)) continue;
                return true;
            }
        }
        return false;
    }

    private String[] getActions(BlacklistEvent event) {
        if (event instanceof BlockBreakBlacklistEvent) {
            return this.breakActions;
        }
        if (event instanceof BlockPlaceBlacklistEvent) {
            return this.placeActions;
        }
        if (event instanceof BlockInteractBlacklistEvent) {
            return this.interactActions;
        }
        if (event instanceof DestroyWithBlacklistEvent) {
            return this.destroyWithActions;
        }
        if (event instanceof ItemAcquireBlacklistEvent) {
            return this.acquireActions;
        }
        if (event instanceof ItemDropBlacklistEvent) {
            return this.dropActions;
        }
        if (event instanceof ItemUseBlacklistEvent) {
            return this.useActions;
        }
        return null;
    }

    public boolean check(boolean useAsWhitelist, BlacklistEvent event, boolean forceRepeat, boolean silent) {
        boolean ret;
        LocalPlayer player = event.getPlayer();
        if (this.shouldIgnore(player)) {
            return true;
        }
        String name = player.getName();
        long now = System.currentTimeMillis();
        boolean repeating = false;
        BlacklistTrackedEvent tracked = this.blacklist.lastAffected.get(name);
        if (tracked != null) {
            if (tracked.matches(event, now)) {
                repeating = true;
            }
        } else {
            this.blacklist.lastAffected.put(name, new BlacklistTrackedEvent(event, now));
        }
        String[] actions = this.getActions(event);
        boolean bl = ret = !useAsWhitelist;
        if (actions == null) {
            return ret;
        }
        for (String action : actions) {
            if (action.equalsIgnoreCase("deny")) {
                if (silent) {
                    return false;
                }
                ret = false;
                continue;
            }
            if (action.equalsIgnoreCase("allow")) {
                if (silent) {
                    return true;
                }
                ret = true;
                continue;
            }
            if (action.equalsIgnoreCase("kick")) {
                if (silent) continue;
                if (this.message != null) {
                    player.kick(String.format(this.message, BlacklistEntry.getFriendlyItemName(event.getType())));
                    continue;
                }
                player.kick("You can't " + event.getDescription() + " " + BlacklistEntry.getFriendlyItemName(event.getType()));
                continue;
            }
            if (action.equalsIgnoreCase("ban")) {
                if (silent) continue;
                if (this.message != null) {
                    player.ban("Banned: " + String.format(this.message, BlacklistEntry.getFriendlyItemName(event.getType())));
                    continue;
                }
                player.ban("Banned: You can't " + event.getDescription() + " " + BlacklistEntry.getFriendlyItemName(event.getType()));
                continue;
            }
            if (silent || repeating && !forceRepeat) continue;
            if (action.equalsIgnoreCase("notify")) {
                this.blacklist.notify(event, this.comment);
                continue;
            }
            if (action.equalsIgnoreCase("log")) {
                this.blacklist.getLogger().logEvent(event, this.comment);
                continue;
            }
            if (!action.equalsIgnoreCase("tell")) continue;
            if (this.message != null) {
                player.printRaw((Object)((Object)ChatColor.YELLOW) + String.format(this.message, BlacklistEntry.getFriendlyItemName(event.getType())) + ".");
                continue;
            }
            player.printRaw((Object)((Object)ChatColor.YELLOW) + "You're not allowed to " + event.getDescription() + " " + BlacklistEntry.getFriendlyItemName(event.getType()) + ".");
        }
        return ret;
    }

    private static String getFriendlyItemName(int id) {
        ItemType type = ItemType.fromID((int)id);
        if (type != null) {
            return type.getName() + " (#" + id + ")";
        }
        return "#" + id + "";
    }
}

