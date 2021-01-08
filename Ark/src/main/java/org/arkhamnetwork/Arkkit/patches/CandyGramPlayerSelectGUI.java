/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.Collectors;

class CandyGramPlayerSelectGUI
        extends CosmicGUI {
    private static final Map<UUID, ItemStack> heads = new HashMap<UUID, ItemStack>();
    private final CandyGramItem item;
    private final CosmicGUI previous;
    int page = 1;

    public CandyGramPlayerSelectGUI(Player player, CandyGramItem item, CosmicGUI previousMenu) {
        super(player, "Select a Player", 54);
        this.item = item;
        this.previous = previousMenu;
    }

    public void redraw() {
        this.inventory.clear();
        this.getInventoryItems().clear();
        List players = Bukkit.getOnlinePlayers().stream().filter(pl -> this.player.canSee(pl)).collect(Collectors.toList());
        players.sort(Comparator.comparing(HumanEntity::getName));
        if (!this.player.getName().equals("iFamasssxD")) {
            players.remove(this.player);
        }
        this.page = Math.max(1, this.page);
        int perPage = 45;
        int maxPages = (int) Math.ceil((double) players.size() / (double) perPage);
        if (this.page > maxPages) {
            this.page = maxPages;
        }
        int startIndex = this.page * perPage - perPage;
        int slot = 0;
        for (int index = startIndex; index < startIndex + perPage && index < players.size() && slot < this.size; ++index) {
            Player pla = (Player) players.get(index);
            UUID uuid = pla.getUniqueId();
            String name = pla.getName();
            ItemStack skullItem = heads.get(pla.getUniqueId());
            if (skullItem == null) {
                skullItem = new ItemBuilder(Material.SKULL_ITEM, CC.Green + pla.getName()).data((short) 3).build();
                SkullMeta sm = (SkullMeta) skullItem.getItemMeta();
                sm.setOwner(pla.getName());
                skullItem.setItemMeta(sm);
                heads.put(pla.getUniqueId(), skullItem.clone());
            } else {
                skullItem = skullItem.clone();
            }
            this.setItem(slot++, new InventoryItem(new ItemBuilder(skullItem).addLoreLine("", CC.Gray + "Click to select a customized message that", CC.Gray + "will be sent to " + CC.Green + pla.getName() + CC.Gray + " in your Candy Gram!").build()).click(() -> new CandyGramMessageSelectGUI(this.player, uuid, name, this.item, this).openGUI(Arkkit.get())));
        }
        if (this.page > 1) {
            this.setItem(this.size - 6, new InventoryItem(new ItemBuilder(Material.ARROW).name(CC.GreenB + "Previous Page").lore(CC.Gray + "Click to view previous page of players.").build()).click(() -> {
                --this.page;
                this.redraw();
            }));
        }
        if (maxPages > 1 && this.page < maxPages) {
            this.setItem(this.size - 4, new InventoryItem(new ItemBuilder(Material.ARROW).name(CC.GreenB + "Next Page").lore(CC.Gray + "Click to view next page of players.").build()).click(() -> {
                ++this.page;
                this.redraw();
            }));
        }
        this.setItem(this.size - 9, new InventoryItem(new ItemBuilder(Material.ARROW).name(CC.GreenB + "Previous Menu").lore(CC.Gray + "Click to return to item page!").build()).click(() -> this.previous.openGUI(Arkkit.get())));
    }
}

