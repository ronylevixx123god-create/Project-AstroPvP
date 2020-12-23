/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.FireworkEffect
 *  org.bukkit.Sound
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.inventory.meta.FireworkMeta
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.api.ExperienceAPI;
import org.arkhamnetwork.Arkkit.utils.RandomFireworks;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.HashSet;

final class RewardNPCListener
        implements Listener {
    private final HashSet<String> mcmmo_uuids = new HashSet();

    RewardNPCListener() {
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        HumanEntity he;
        String npcName;
        if (e.getRightClicked() instanceof HumanEntity && e.getRightClicked().hasMetadata("NPC") && (npcName = (he = (HumanEntity) e.getRightClicked()).getName()).equalsIgnoreCase("Skill Trainer")) {
            String uuid = e.getPlayer().getUniqueId().toString();
            if (this.mcmmo_uuids.contains(uuid)) {
                e.getPlayer().sendMessage(ChatColor.RED + "You have already claimed your McMMO prize today!");
                e.setCancelled(true);
                return;
            }
            this.mcmmo_uuids.add(uuid);
            int sword_xp = 7500;
            ExperienceAPI.addRawXP(e.getPlayer(), "fishing", (float) sword_xp, "UNKNOWN");
            e.getPlayer().sendMessage(ChatColor.WHITE + "Skill Trainer: " + ChatColor.GRAY + "The true sign of intelligence is not knowledge but imagination.");
            e.getPlayer().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "+" + sword_xp + " Fishing XP");
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
            Firework fw = (Firework) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation().clone().add(0.0, 1.0, 0.0), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(RandomFireworks.getManager().getRandomColor()).withFade(RandomFireworks.getManager().getRandomColor()).with(RandomFireworks.getManager().getRandomType()).trail(true).build();
            fwm.addEffect(effect);
            fwm.setPower(0);
            fw.setFireworkMeta(fwm);
        }
    }
}

