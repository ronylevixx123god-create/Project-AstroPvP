/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PotionSplashEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

final class DrinkListener
        implements Listener {
    private final Random r = new Random();

    public static void applySpaceDrink(Player p, boolean notify) {
        int drunkLevel;
        int action = new Random().nextInt(20);
        p.setMetadata("lastDrink", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis()));
        if (notify) {
            p.sendMessage(CC.LightPurpleB + "*" + CC.Gray + " splashed by " + CC.LightPurple + "Space Drink " + CC.LightPurpleB + "*");
        }
        if (action == 0) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 140, 2));
            p.sendMessage(ChatColor.YELLOW + "You gulp down the Space Drink, and immediately start to feel dizzy and light headed.");
        }
        if (action == 1) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 1));
            p.sendMessage(ChatColor.YELLOW + "You swallow the Space Drink, and suddenly the whole world goes dark... #BLACKOUT!");
        }
        if (action == 2) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 140, 1));
            p.sendMessage(ChatColor.YELLOW + "You finish the drink, smash the glass on the ground, and feel an unstoppable sense of rage fill your body.");
        }
        if (action == 3) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 3));
            p.sendMessage(ChatColor.YELLOW + "You slowly empty the Space Drink into your mouth, and suddenly feel a huge burst of energy!");
        }
        if ((drunkLevel = Drinks.moreDrunk(p)) == Drinks.tipsyLevel) {
            p.sendMessage(Drinks.tipsyStr);
        } else if (drunkLevel == Drinks.smashedLevel) {
            p.sendMessage(Drinks.smashedStr);
        } else if (drunkLevel >= Drinks.poisoningLevel) {
            double dmg = drunkLevel - (Drinks.poisoningLevel - 1);
            if (p.getHealth() - dmg >= 1.0) {
                p.damage(dmg);
            }
            p.sendMessage(Drinks.poisoningStr);
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, drunkLevel * 50, 0), true);
    }

    @EventHandler
    public void onPlayerDrink(PlayerItemConsumeEvent e) {
        if (Drinks.isDrink(e.getItem())) {
            DrinkListener.applySpaceDrink(e.getPlayer(), false);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent e) {
        if (Drinks.isSplashDrink(e.getPotion().getItem())) {
            e.setCancelled(true);
            ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, e.getEntity().getLocation().add(0.0, 1.0, 0.0), 0.8f, 6);
            for (LivingEntity ent : e.getAffectedEntities()) {
                if (!(ent instanceof Player)) continue;
                Player p = (Player) ent;
                DrinkListener.applySpaceDrink(p, true);
                ParticleEffect.playEffect(ParticleEffect.MAGIC_CRIT, p.getLocation().add(0.0, 1.0, 0.0), 1.15f, 16);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        int drunkLevel;
        if (!event.isCancelled() && (drunkLevel = Drinks.getDrunkLevel(event.getPlayer())) > 0) {
            String msg = event.getMessage();
            boolean changed = false;
            if (this.r.nextInt(100) < Drinks.chanceToSlurS + drunkLevel * Drinks.chanceToSlurSPerLevel) {
                msg = msg.replaceAll("s([^h])", "sh$1");
                changed = true;
            }
            if (this.r.nextInt(100) < Drinks.chanceToHic + drunkLevel * Drinks.chanceToHicPerLevel) {
                msg = msg + ".. hic!";
                changed = true;
            }
            if (changed) {
                event.setMessage(msg);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Drinks.getDrunks().remove(event.getPlayer().getName());
    }
}

