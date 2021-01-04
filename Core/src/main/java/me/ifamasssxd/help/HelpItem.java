/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.help;

public class HelpItem {
    String name;
    String hoverCommand;

    public HelpItem(String name, String hoverCommand) {
        this.name = name;
        this.hoverCommand = hoverCommand;
    }

    public String getName() {
        return this.name;
    }

    public String getHoverCommand() {
        return this.hoverCommand;
    }
}

