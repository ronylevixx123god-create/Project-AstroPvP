/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  org.arkhamnetwork.enchantments.util.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.scheduler.CraftTask
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.util.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftTask;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class HalloweenListener
        implements Listener {
    HalloweenListener() {
    }

    protected static void setSugarLevel(Player p, int x) {
        p.setMetadata("sugarLevel", new FixedMetadataValue(Arkkit.get(), x));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.GHAST_MOAN, 0.4f, 0.33f);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        HalloweenListener.setSugarLevel(e.getPlayer(), 0);
    }

    @EventHandler
    public void onPlayerUseHalloweenCandy(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && Halloween.isHalloweenCandy(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            if (e.getPlayer().getWorld().getName().startsWith("cosmic-station")) {
                e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot eat Halloween Candy at Outposts.");
                return;
            }
            if (e.getPlayer().getWorld().getName().startsWith("dungeon_")) {
                e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot eat Halloween Candy inside of a Dungeon.");
                return;
            }
            if (ArkPlugins.PARKOUR_REGION.getArkPlugin().isRunning() && e.getPlayer().hasMetadata("worldguardRegion") && e.getPlayer().getMetadata("worldguardRegion").get(0).asString().contains("parkour")) {
                e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot eat Halloween Candy inside of a parkour region.");
                return;
            }
            ItemStack candy = e.getItem().clone();
            if (candy.getAmount() > 1) {
                candy.setAmount(candy.getAmount() - 1);
                e.getPlayer().setItemInHand(candy);
            } else {
                e.getPlayer().setItemInHand(null);
            }
            Player p = e.getPlayer();
            p.getWorld().playSound(e.getPlayer().getLocation(), Sound.EAT, 1.0f, 1.25f);
            int sugarLevel = this.getSugarLevel(p) + 1;
            int duration = sugarLevel * 20;
            HalloweenListener.setSugarLevel(p, sugarLevel);
            this.updateSugarTask(p, sugarLevel);
            if (!p.hasMetadata("spectator")) {
                ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0, 1.0, 0.0), 0.4f, sugarLevel * 5);
            }
            p.sendMessage(ChatColor.YELLOW + "You eat a piece of space candy... YUM! [" + sugarLevel + "x]");
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, Math.min(1, sugarLevel / 2)));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, duration, Math.min(3, sugarLevel / 2)));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration / 3, sugarLevel > 3 ? 1 : 0));
            if (sugarLevel > 2) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration / 3, Math.min(3, sugarLevel / 2)));
            }
            if (sugarLevel > 6) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, 0));
            }
            if (sugarLevel > 8) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration / 2, Math.min(3, sugarLevel / 2)));
            }
            if (sugarLevel > 6) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, Math.min(3, sugarLevel / 2)));
            }
            Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(p, "ITEM_USE", new Object[]{"CANDY", candy}));
        }
    }

    private int getSugarLevel(Player p) {
        return p.hasMetadata("sugarLevel") ? p.getMetadata("sugarLevel").get(0).asInt() : 0;
    }

    private void updateSugarTask(Player p, int level) {
        if (p.hasMetadata("sugarTask")) {
            CraftTask task = (CraftTask) p.getMetadata("sugarTask").get(0).value();
            task.cancel();
        }
        p.setMetadata("sugarTask", new FixedMetadataValue(Arkkit.get(), new HalloweenRainbowTask(p, level).runTaskTimer(Arkkit.get(), Math.max(2, 30 - level), Math.max(2, 30 - level))));
    }
}

