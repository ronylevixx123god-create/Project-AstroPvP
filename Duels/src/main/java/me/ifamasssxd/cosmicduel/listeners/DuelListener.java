/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Chest
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.weather.WeatherChangeEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.listeners;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.tasks.DuelArenaTask;
import me.ifamasssxd.cosmicduel.utils.DuelUtils;
import me.ifamasssxd.cosmicduel.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DuelListener
        implements Listener {
    List<String> fixAliases = Lists.newArrayList("/fix", "/efix", "/erepair", "/repair");

    @EventHandler(priority=EventPriority.MONITOR)
    public void onChestFall(EntityChangeBlockEvent e) {
        String regionName = WorldGuardUtils.getRegionName(e.getBlock().getLocation());
        if (e.getTo() == Material.CHEST && DuelArenaTask.arena_regions.contains(regionName) && e.getBlock().getWorld().getName().equals(Arena.getDuelWorldName())) {
            NewDuel duel = DuelAPI.getCurrentlyActiveDuel(Arena.getArenaFromRegion(regionName));
            if (duel != null) {
                System.out.println("Storing duel envoy block!");
                duel.getEnvoys().add(e.getBlock().getLocation());
            } else {
                System.out.println("No duel found!");
                e.setCancelled(true);
                if (e.getBlock().getType() == Material.CHEST) {
                    e.getBlock().setType(Material.AIR);
                }
            }
            DuelUtils.removeDisabledItems(e.getBlock());
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onDuelMasterClicked(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player && ((Player)event.getRightClicked()).getName().equalsIgnoreCase("Duel Master") && event.getRightClicked().hasMetadata("NPC")) {
            event.getPlayer().chat("/duel");
        }
    }

    public static void removeChest(Location l) {
        Chest chest = (Chest)l.getBlock().getState();
        chest.getInventory().clear();
        chest.update();
        l.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!e.getEntity().getLocation().getWorld().getName().contains("_duel")) return;
        new BukkitRunnable(){
            public void run(){
                e.getDrops().clear();
            }
        }.runTaskLater(CosmicDuel.get(), 40L);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (p.getGameMode() == GameMode.SPECTATOR) {
                e.setCancelled(true);
            }
        }
    }

    public NewDuel getDuel(Arena arena) {
        for (NewDuel duel : DuelAPI.duels) {
            if (duel.getArena() == null || !duel.getArena().equals(arena)) continue;
            return duel;
        }
        return null;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState() && Arena.getDuelWorldName().equalsIgnoreCase(e.getWorld().getName())) {
            e.setCancelled(true);
            e.getWorld().setStorm(false);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onItemSpawn(ItemSpawnEvent e) {
        if (e.getEntity() != null && e.getEntity().getWorld() != null && e.getEntity().getWorld().getName().equalsIgnoreCase(Arena.getDuelWorldName())) {
            e.getEntity().setMetadata("mcMMO: Tracked Item", (MetadataValue)new FixedMetadataValue((Plugin) CosmicDuel.get(), (Object)""));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerPreProcess(PlayerCommandPreprocessEvent e) {
        if (Arena.getDuelWorldName().equalsIgnoreCase(e.getPlayer().getWorld().getName()) && e.getMessage().startsWith("/f ") && !e.getPlayer().isOp() && !e.getPlayer().getGameMode().equals((Object)GameMode.CREATIVE)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot use net.astropvp.armorsets.commands in a duel.");
            e.getPlayer().sendMessage((Object)ChatColor.GRAY + "You can leave the duel with /duel leave.");
            e.setMessage("/null");
            return;
        }
        if (DuelAPI.hasDuel(e.getPlayer())) {
            if (e.getPlayer().isOp()) {
                return;
            }
            NewDuel d = DuelAPI.getDuel(e.getPlayer());
            if (DuelAPI.isDuelValid(d)) {
                if (e.getMessage().endsWith("duel leave")) {
                    return;
                }
                for (String cmd : this.fixAliases) {
                    if (!e.getMessage().startsWith(cmd)) continue;
                    if (d.getSettings().hasSetting(Setting.FIX)) {
                        return;
                    }
                    e.setCancelled(true);
                    e.setMessage("/null");
                    e.getPlayer().sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Your duel has /fix " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "disabled.");
                    return;
                }
                if (e.getMessage().endsWith("/fly") || e.getMessage().endsWith("/efly")) {
                    if (d.getSettings().hasSetting(Setting.FLY)) {
                        return;
                    }
                    e.setCancelled(true);
                    e.setMessage("/null");
                    e.getPlayer().sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Your duel has /fly " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "disabled.");
                    return;
                }
                e.setMessage("/null");
                e.setCancelled(true);
                e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot use net.astropvp.armorsets.commands in a duel.");
                e.getPlayer().sendMessage((Object)ChatColor.GRAY + "You can leave the duel with /duel leave.");
            }
        }
        if (e.getMessage().equalsIgnoreCase("/elo top")) {
            e.setMessage("/duel top");
        }
    }
}
