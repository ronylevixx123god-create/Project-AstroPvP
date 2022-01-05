/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.Inventory
 *  org.bukkit.me.astropvp.cratekeys.inventory.InventoryHolder
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.cosmicduel.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SpectateInventory
implements Listener {
    private static Inventory spectateInventory;

    public SpectateInventory() {
        spectateInventory = Bukkit.createInventory((InventoryHolder)null, 18, (String)"Select an Arena");
        SpectateInventory.updateInventory();
    }

    public static void updateInventory() {
        spectateInventory.clear();
        int index = 0;
        for (Arena arena : Arena.arenas) {
            NewDuel d = DuelAPI.getCurrentlyActiveDuel(arena);
            ArrayList<String> lore = new ArrayList<String>();
            if (d != null) {
                lore.add((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + d.getName() + " Duel:");
                lore.add(SpectateInventory.getDuelString(d));
            } else {
                lore.add(CC.translate("&c&lNO DUEL"));
            }
            ItemStack is = ItemBuilder.buildItem(arena.getIcon(), 1, (short)0, arena.getName() + " Arena " + (Object)ChatColor.GRAY + "(Click to Spectate)", lore);
            spectateInventory.setItem(index++, is);
        }
    }

    public static void closeInventory() {
        Lists.newArrayList(spectateInventory.getViewers()).forEach(HumanEntity::closeInventory);
    }

    private static String getQueueString(NewDuel d) {
        String p_names = "";
        int index = 0;
        for (Player p : d.getPlayers()) {
            DuelPlayer dp;
            p_names = p_names + (Object)ChatColor.YELLOW + p.getName();

            if (index != d.getPlayers().size() - 1) {
                p_names = p_names + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + " vs ";
            }
            ++index;
        }
        if (p_names.endsWith(" vs ")) {
            p_names = p_names.substring(0, p_names.length() - 4);
        }
        return ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + p_names;
    }

    private static String getDuelString(NewDuel d) {
        String p_names = "";
        int index = 0;
        for (Player p : d.getPlayers()) {
            DuelPlayer dp;
            p_names = p_names + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + p.getName();

            if (index != d.getPlayers().size() - 1) {
                p_names = p_names + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + " vs ";
            }
            ++index;
        }
        if (p_names.endsWith(" vs ")) {
            p_names = p_names.substring(0, p_names.length() - 4);
        }
        return ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + p_names;
    }

    @EventHandler
    public void onInventoryclick(InventoryClickEvent e) {
        if (e.getInventory().equals((Object)spectateInventory)) {
            Player p = (Player)e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                Arena arena = Arena.getArena(e.getCurrentItem().getItemMeta().getDisplayName());
                if (arena == null) {
                    System.out.println("Null arena for " + e.getCurrentItem().getItemMeta().getDisplayName());
                    return;
                }
                if (DuelAPI.getCurrentlyActiveDuel(arena) != null) {
                    NewDuel d = DuelAPI.getCurrentlyActiveDuel(arena);
                    if (d.getCreateState() != DuelCreateState.QUEUE_POP) {
                        p.closeInventory();
                        d.prepareSpectator(p);
                    } else {
                        p.sendMessage((Object)ChatColor.RED + "This duel is not ready yet.");
                    }
                } else {
                    p.sendMessage((Object)ChatColor.RED + "This arena does not have an active duel.");
                }
            }
        }
    }

    public static void openInventory(Player p) {
        SpectateInventory.updateInventory();
        p.openInventory(spectateInventory);
    }
}

