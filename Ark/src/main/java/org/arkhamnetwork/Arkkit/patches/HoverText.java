/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public final class HoverText {
    String text;
    List<ChatColor> lastColors = new ArrayList<ChatColor>();

    public List<ChatColor> getLastColors() {
        return this.lastColors;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

