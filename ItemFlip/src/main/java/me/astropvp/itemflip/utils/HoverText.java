package me.astropvp.itemflip.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;


public class HoverText {
    String text;
    List<ChatColor> lastColors = new ArrayList<>();


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

