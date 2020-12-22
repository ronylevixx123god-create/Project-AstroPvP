/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

class CandyGram {
    private CandyGramItem item;
    private CandyGramMessages message;
    private String sender;

    public double getTotalCost() {
        return this.message.getCost() + this.item.getCost() + (this.sender.equalsIgnoreCase(CandyGramSender.ANONYMOUS.getName()) ? 10000 : (this.sender.equals(CandyGramSender.SECRET_ADMIRERER.getName()) ? 100000 : 0));
    }

    public ItemStack getItemStack() {
        ArrayList lore = Lists.newArrayList((Object[])new String[]{"", CC.LightPurpleB + "Message"});
        this.getMessage().getMessages().forEach(msg -> {
            String line = ChatColor.translateAlternateColorCodes((char)'&', (String)msg);
            lore.add((!line.startsWith("\u00a7") ? CC.Yellow : "") + " " + line);
        });
        lore.add("");
        lore.add(CC.GreenB + "Candy Gram From: " + CC.Green + this.getSender());
        return new ItemBuilder(this.getItem().getMaterial().getItemType()).data((short)this.getItem().getMaterial().getData()).name(this.getItem().getName()).lore((List)lore).build();
    }

    public CandyGramItem getItem() {
        return this.item;
    }

    public CandyGramMessages getMessage() {
        return this.message;
    }

    public String getSender() {
        return this.sender;
    }

    public CandyGram(CandyGramItem item, CandyGramMessages message, String sender) {
        this.item = item;
        this.message = message;
        this.sender = sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

