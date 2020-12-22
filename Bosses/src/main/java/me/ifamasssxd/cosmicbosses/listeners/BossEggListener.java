/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 */
package me.ifamasssxd.cosmicbosses.listeners;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;
import me.ifamasssxd.cosmicbosses.boss.type.Boss;
import me.ifamasssxd.cosmicbosses.boss.type.Bosses;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtil;
import com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BossEggListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack egg;
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (egg = e.getItem()).getType() == Material.getMaterial(383) && egg.hasItemMeta() && egg.getItemMeta().hasDisplayName()) {
            String egg_name = egg.getItemMeta().getDisplayName();
            if (!egg.getItemMeta().hasLore() || !egg.getItemMeta().getLore().get(0).contains("Be careful")) {
                return;
            }
            String o_egg_name = egg_name;
            egg_name = egg_name.split(" ")[egg_name.split(" ").length - 1];
            Boss b = null;
            if (egg_name.contains("Goblin") && o_egg_name.startsWith(ChatColor.AQUA.toString())) {
                b = Bosses.PIG_ZOMBIE.b;
            }
            if (egg_name.contains("King") && o_egg_name.startsWith(ChatColor.GOLD.toString())) {
                b = Bosses.HEADLESS_KING.b;
            }
            if (egg_name.equals("Elf") && o_egg_name.startsWith(ChatColor.YELLOW.toString())) {
                b = Bosses.ELF.b;
            }
            if (b == null) {
                b = Bosses.valueOf((String)egg_name.toUpperCase()).b;
            }
            if (b != null) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                Location spawn_targ = e.getPlayer().getTargetBlock((HashSet<Byte>)null, 6).getLocation().add(0.0, 2.0, 0.0);
                if (WorldGuardUtil.isPvPDisabled(spawn_targ) || MiscUtil.getRegionName((Location)spawn_targ).startsWith("spawn") || MiscUtil.getRegionName((Location)spawn_targ).equals("spawn") || !Board.getFactionAt((FLocation)new FLocation(spawn_targ)).isWarZone() || spawn_targ.getWorld().getName().equals("world_duels") || spawn_targ.getWorld().getName().equals("world_duels2")) {
                    e.getPlayer().sendMessage((Object)((Object)ChatColor.RED) + "You " + (Object)((Object)ChatColor.UNDERLINE) + "cannot" + (Object)((Object)ChatColor.RED) + " spawn a boss here!");
                    e.getPlayer().sendMessage((Object)((Object)ChatColor.GRAY) + "You may only spawn bosses at the planet's " + (Object)((Object)ChatColor.UNDERLINE) + "warzone!");
                    return;
                }
                b.spawnBoss(spawn_targ);
                if (this.isBossNearby(e.getPlayer(), 9)) {
                    if (egg.getAmount() > 1) {
                        egg.setAmount(egg.getAmount() - 1);
                    } else {
                        e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                    }
                    e.getPlayer().updateInventory();
                } else {
                    e.getPlayer().sendMessage((Object)((Object)ChatColor.RED) + "Your boss could not be spawned here.");
                }
            }
        }
    }

    @EventHandler
    public void onAnvilInventoryClick(InventoryClickEvent e) {
        Material monsterEgg = Material.getMaterial(383);
        if (e.getInventory().getType() == InventoryType.ANVIL && (e.getCurrentItem() != null && e.getCurrentItem().getType() == monsterEgg || e.getCursor() != null && e.getCursor().getType() == monsterEgg)) {
            final Player pl = (Player)e.getWhoClicked();
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            pl.updateInventory();
            Bukkit.getServer().getScheduler().runTaskLater((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    pl.closeInventory();
                    pl.sendMessage((Object)((Object)ChatColor.RED) + "You " + (Object)((Object)ChatColor.UNDERLINE) + "cannot" + (Object)((Object)ChatColor.RED) + " rename this item.");
                }
            }, 2L);
        }
    }

    private boolean isBossNearby(Player p, int radius) {
        for (Entity ent : p.getNearbyEntities(radius, radius, radius)) {
            if (!ent.hasMetadata("cbe_boss")) continue;
            return true;
        }
        return false;
    }
}

