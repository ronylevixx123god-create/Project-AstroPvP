/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 */
package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.ListIterator;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RegionPrintoutBuilder {
    private final ProtectedRegion region;
    private final StringBuilder builder = new StringBuilder();

    public RegionPrintoutBuilder(ProtectedRegion region) {
        this.region = region;
    }

    private void newLine() {
        this.builder.append("\n");
    }

    public void appendBasics() {
        this.builder.append((Object)ChatColor.BLUE);
        this.builder.append("Region: ");
        this.builder.append((Object)ChatColor.YELLOW);
        this.builder.append(this.region.getId());
        this.builder.append((Object)ChatColor.GRAY);
        this.builder.append(" (type=");
        this.builder.append(this.region.getTypeName());
        this.builder.append((Object)ChatColor.GRAY);
        this.builder.append(", priority=");
        this.builder.append(this.region.getPriority());
        this.builder.append(")");
        this.newLine();
    }

    public void appendFlags() {
        this.builder.append((Object)ChatColor.BLUE);
        this.builder.append("Flags: ");
        this.appendFlagsList(true);
        this.newLine();
    }

    public void appendFlagsList(boolean useColors) {
        boolean hasFlags = false;
        for (Flag<?> flag : DefaultFlag.getFlags()) {
            Object val = this.region.getFlag(flag);
            Object group = null;
            if (val == null) continue;
            if (hasFlags) {
                this.builder.append(", ");
            } else if (useColors) {
                this.builder.append((Object)ChatColor.YELLOW);
            }
            RegionGroupFlag groupFlag = flag.getRegionGroupFlag();
            if (groupFlag != null) {
                group = this.region.getFlag(groupFlag);
            }
            if (group == null) {
                this.builder.append(flag.getName()).append(": ").append(String.valueOf(val));
            } else {
                this.builder.append(flag.getName()).append(" -g ").append(String.valueOf(group)).append(": ").append(String.valueOf(val));
            }
            hasFlags = true;
        }
        if (!hasFlags) {
            if (useColors) {
                this.builder.append((Object)ChatColor.RED);
            }
            this.builder.append("(none)");
        }
    }

    public void appendParents() {
        this.appendParentTree(true);
    }

    public void appendParentTree(boolean useColors) {
        if (this.region.getParent() == null) {
            return;
        }
        ArrayList<ProtectedRegion> inheritance = new ArrayList<ProtectedRegion>();
        ProtectedRegion r = this.region;
        inheritance.add(r);
        while (r.getParent() != null) {
            r = r.getParent();
            inheritance.add(r);
        }
        ListIterator it = inheritance.listIterator(inheritance.size());
        int indent = 0;
        while (it.hasPrevious()) {
            ProtectedRegion cur = (ProtectedRegion)it.previous();
            if (useColors) {
                this.builder.append((Object)ChatColor.GREEN);
            }
            if (indent != 0) {
                for (int i = 0; i < indent; ++i) {
                    this.builder.append("  ");
                }
                this.builder.append("\u2517");
            }
            this.builder.append(cur.getId());
            if (!cur.equals(this.region)) {
                if (useColors) {
                    this.builder.append((Object)ChatColor.GRAY);
                }
                this.builder.append(" (parent, priority=" + cur.getPriority() + ")");
            }
            ++indent;
            this.newLine();
        }
    }

    public void appendDomain() {
        this.builder.append((Object)ChatColor.BLUE);
        this.builder.append("Owners: ");
        DefaultDomain owners = this.region.getOwners();
        if (owners.size() != 0) {
            this.builder.append((Object)ChatColor.YELLOW);
            this.builder.append(owners.toUserFriendlyString());
        } else {
            this.builder.append((Object)ChatColor.RED);
            this.builder.append("(no owners)");
        }
        this.newLine();
        this.builder.append((Object)ChatColor.BLUE);
        this.builder.append("Members: ");
        DefaultDomain members = this.region.getMembers();
        if (members.size() != 0) {
            this.builder.append((Object)ChatColor.YELLOW);
            this.builder.append(members.toUserFriendlyString());
        } else {
            this.builder.append((Object)ChatColor.RED);
            this.builder.append("(no members)");
        }
        this.newLine();
    }

    public void appendBounds() {
        BlockVector min = this.region.getMinimumPoint();
        BlockVector max = this.region.getMaximumPoint();
        this.builder.append((Object)ChatColor.BLUE);
        this.builder.append("Bounds:");
        this.builder.append((Object)ChatColor.YELLOW);
        this.builder.append(" (" + min.getBlockX() + "," + min.getBlockY() + "," + min.getBlockZ() + ")");
        this.builder.append(" -> (" + max.getBlockX() + "," + max.getBlockY() + "," + max.getBlockZ() + ")");
        this.newLine();
    }

    public void appendRegionInfo() {
        this.builder.append((Object)ChatColor.GRAY);
        this.builder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.builder.append(" Region Info ");
        this.builder.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.newLine();
        this.appendBasics();
        this.appendFlags();
        this.appendParents();
        this.appendDomain();
        this.appendBounds();
    }

    public void send(CommandSender sender) {
        sender.sendMessage(this.toString());
    }

    public StringBuilder append(boolean b) {
        return this.builder.append(b);
    }

    public StringBuilder append(char c) {
        return this.builder.append(c);
    }

    public StringBuilder append(char[] str, int offset, int len) {
        return this.builder.append(str, offset, len);
    }

    public StringBuilder append(char[] str) {
        return this.builder.append(str);
    }

    public StringBuilder append(CharSequence s, int start, int end) {
        return this.builder.append(s, start, end);
    }

    public StringBuilder append(CharSequence s) {
        return this.builder.append(s);
    }

    public StringBuilder append(double d) {
        return this.builder.append(d);
    }

    public StringBuilder append(float f) {
        return this.builder.append(f);
    }

    public StringBuilder append(int i) {
        return this.builder.append(i);
    }

    public StringBuilder append(long lng) {
        return this.builder.append(lng);
    }

    public StringBuilder append(Object obj) {
        return this.builder.append(obj);
    }

    public StringBuilder append(String str) {
        return this.builder.append(str);
    }

    public StringBuilder append(StringBuffer sb) {
        return this.builder.append(sb);
    }

    public StringBuilder appendCodePoint(int codePoint) {
        return this.builder.appendCodePoint(codePoint);
    }

    public String toString() {
        return this.builder.toString().trim();
    }
}

