/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.RandomFireworks;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class JulyFreedom
        extends ArkPlugin {
    private final int MOB_ITEM_DROP_CHANCE = 1;
    private final Random random = new Random();
    List<Color> available = Lists.newArrayList(Color.RED, Color.WHITE, Color.BLUE);
    private boolean usingFactions = true;

    public JulyFreedom(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("Factions")) {
            this.usingFactions = false;
            Bukkit.getLogger().info("Unable to find Factions plugin, disabling support.");
        }
        this.registerListener(new FireworkListener());
        this.registerTask(new SpaceFireworkTask().runTaskTimer(Arkkit.get(), 24000L, 72000L), SpaceFireworkTask.class);
    }

    @Override
    public void disable() {
        this.unregisterListeners();
    }

    private void launchFirework(final Location loc) {
        new BukkitRunnable() {

            public void run() {
                JulyFreedom.this.shootFirework(loc);
            }
        }.runTaskLater(this.getBukkitPlugin(), 5L);
    }

    private void shootFirework(Location location) {
        Firework fw = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();
        fm.setPower(1);
        fm.addEffects(FireworkEffect.builder().with(RandomFireworks.getManager().getRandomType()).withColor(this.getRandomColor()).build());
        fw.setFireworkMeta(fm);
    }

    private Color getRandomColor() {
        return this.available.get(this.random.nextInt(this.available.size()));
    }

    public class SpaceFireworkTask
            extends BukkitRunnable {
        public void run() {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Happy " + ChatColor.WHITE + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "4th of July" + ChatColor.BLUE + ChatColor.BOLD.toString() + " CosmicPvP!");
                pl.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "+1 Space Firework");
                pl.sendMessage(ChatColor.GRAY + "We'll be handing out free space fireworks all day in celebration!");
                if (pl.getInventory().firstEmpty() == -1) continue;
                pl.getInventory().addItem(NewYears.getCosmicCracker());
            }
        }
    }

    public class FireworkListener
            implements Listener {
        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onPlayerJoin(PlayerJoinEvent event) {
            Location playerLoc = event.getPlayer().getLocation();
            if (JulyFreedom.this.usingFactions) {
                if (WorldGuardUtils.isPvPDisabled(playerLoc)) {
                    JulyFreedom.this.launchFirework(playerLoc);
                }
            } else {
                JulyFreedom.this.launchFirework(playerLoc);
            }
        }

        @EventHandler
        public void onEntityDeath(EntityDeathEvent event) {
            if (event.getEntity() instanceof Player) {
                return;
            }
            if (JulyFreedom.this.random.nextInt(150) == 0) {
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), NewYears.getCosmicCracker());
            }
        }
    }
}

