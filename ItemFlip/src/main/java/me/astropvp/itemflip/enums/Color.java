
package me.astropvp.itemflip.enums;

import org.bukkit.ChatColor;

public enum Color {
    BLUE("Blue", ChatColor.BLUE, 3),
    YELLOW("Yellow", ChatColor.YELLOW, 4),
    PURPLE("Purple", ChatColor.LIGHT_PURPLE, 10),
    RED("Red", ChatColor.RED, 14),
    GREEN("Green", ChatColor.GREEN, 13),
    BLACK("Black", ChatColor.BLACK, 15),
    ORANGE("Orange", ChatColor.GOLD, 1),
    WHITE("White", ChatColor.WHITE, 0),
    GRAY("Gray", ChatColor.GRAY, 7);

    private String name;
    private ChatColor prefix;
    private short id;

    private Color(String name, ChatColor prefix, int id) {
        this.name = name;
        this.prefix = prefix;
        this.id = (short)id;
    }

    public String getName() {
        return this.name;
    }

    public ChatColor getPrefix() {
        return this.prefix;
    }

    public short getId() {
        return this.id;
    }

    public String getPrefixedName() {
        return this.getPrefix() + this.getName();
    }
}
