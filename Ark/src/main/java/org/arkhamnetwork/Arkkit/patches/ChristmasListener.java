/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.scheduler.CraftTask
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ArkPluginListener;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.Christmas;
import org.arkhamnetwork.Arkkit.patches.ChristmasRainbowTask;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class ChristmasListener
extends ArkPluginListener<Christmas> {
    public ChristmasListener(Christmas patch) {
        super(patch);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        ChristmasListener.setSugarLevel(e.getPlayer(), 0);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (((Christmas)this.patch).isChristmasElf(e.getEntity())) {
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.GHAST_MOAN, 1.0f, 1.1f);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_IDLE, 1.0f, 1.3f);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (((Christmas)this.patch).isChristmasElf((Entity)e.getEntity())) {
            ItemStack mask;
            e.getDrops().clear();
            e.getDrops().add(NewYears.getExplosiveCake(5 + ThreadLocalRandom.current().nextInt(5)));
            e.getDrops().add(NewYears.getCosmicCracker(5 + ThreadLocalRandom.current().nextInt(5)));
            e.getDrops().add(NewYears.getExplosiveCake(3 + ThreadLocalRandom.current().nextInt(5)));
            e.getDrops().add(Drinks.getChristmasDrink());
            if (Math.random() <= 0.01 && Bukkit.getPluginManager().isPluginEnabled("CosmicMasks") && (mask = new MaskBuilder(Math.random() <= 0.5 ? "SANTA" : "REINDEER").build()) != null) {
                e.getDrops().add(mask);
                Bukkit.getLogger().info("[Christmas] Dropping Christmas mask at " + (Object)e.getEntity().getLocation() + "!");
            }
        }
    }

    @EventHandler
    public void onPlayerUseChristmasCandy(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && Christmas.isChristmasCandy(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            if (e.getPlayer().getWorld().getName().startsWith("cosmic-station")) {
                e.getPlayer().sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot eat Christmas Candy at Outposts.");
                return;
            }
            if (e.getPlayer().getWorld().getName().startsWith("dungeon_")) {
                e.getPlayer().sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot eat Christmas Candy inside of a Dungeon.");
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
            int christmasSugarLevel = this.getSugarLevel(p) + 1;
            int duration = christmasSugarLevel * 20;
            ChristmasListener.setSugarLevel(p, christmasSugarLevel);
            this.updateSugarTask(p, christmasSugarLevel);
            if (p.hasMetadata("spectator")) {
                ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0, 1.0, 0.0), 0.4f, christmasSugarLevel * 5);
            }
            p.sendMessage((Object)ChatColor.YELLOW + "You eat a piece of christmas candy... YUM! [" + christmasSugarLevel + "x]");
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, Math.min(1, christmasSugarLevel / 2)));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, duration, Math.min(3, christmasSugarLevel / 2)));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration / 3, christmasSugarLevel > 3 ? 1 : 0));
            if (christmasSugarLevel > 2) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration / 3, Math.min(3, christmasSugarLevel / 2)));
            }
            if (christmasSugarLevel > 6) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, 0));
            }
            if (christmasSugarLevel > 8) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration / 2, Math.min(3, christmasSugarLevel / 2)));
            }
            if (christmasSugarLevel > 6) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, Math.min(3, christmasSugarLevel / 2)));
            }
        }
    }

    private int getSugarLevel(Player p) {
        return p.hasMetadata("christmasSugarLevel") ? ((MetadataValue)p.getMetadata("christmasSugarLevel").get(0)).asInt() : 0;
    }

    protected static void setSugarLevel(Player p, int x) {
        p.setMetadata("christmasSugarLevel", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)x));
    }

    private void updateSugarTask(Player p, int level) {
        if (p.hasMetadata("christmasSugarTask")) {
            CraftTask task = (CraftTask) p.getMetadata("christmasSugarTask").get(0).value();
            task.cancel();
        }
        p.setMetadata("christmasSugarTask", new FixedMetadataValue(Arkkit.get(), new ChristmasRainbowTask(p, level).runTaskTimer(Arkkit.get(), Math.max(2, 30 - level), Math.max(2, 30 - level))));
    }
}

