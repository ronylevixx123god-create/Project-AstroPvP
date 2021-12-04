package org.arkhamnetwork.enchantments.tasks;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.listeners.PlayerPotionEffectListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerPotionEffectPersistTask implements Runnable {
    boolean cosmicOutpostsEnabled;
    boolean statusPlusEnabled;

    public PlayerPotionEffectPersistTask() {
        this.cosmicOutpostsEnabled = false;
        this.statusPlusEnabled = false;
        this.cosmicOutpostsEnabled = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
    }

    @Override
    public void run() {
        for (final Map.Entry<UUID, PlayerPotionEffects> trackedEffects : PlayerPotionEffectListener.getPlayerPotionEffectTracker().entrySet()) {
            final Player p = trackedEffects.getValue().getBukkitPlayer();
            if (p != null && !p.isDead() && p.isValid()) {
                if (this.cosmicOutpostsEnabled && CosmicOutpostsAPI.getOutpostTierOfWorld(p) == OutpostTier.VANILLA) {
                    continue;
                }
                if (p.getWorld().getName().equals("events_world")) {
                    continue;
                }
                if (p.hasMetadata("armorRescanRunning")) {
                    continue;
                }
                if (p.hasMetadata("lastDuelEnd") && MinecraftServerUtils.getCurrentServerTick() < p.getMetadata("lastDuelEnd").get(0).asLong() + 80L) {
                    continue;
                }
                final boolean worldChange = p.hasMetadata("lastWorldChange") && System.currentTimeMillis() - p.getMetadata("lastWorldChange").get(0).asLong() < 2500L;
                final boolean recentDeath = p.hasMetadata("lastPlayerDeath") && System.currentTimeMillis() - p.getMetadata("lastPlayerDeath").get(0).asLong() < 1000L;
                if (worldChange || recentDeath) {
                    continue;
                }
                trackedEffects.getValue().applyPotionEffects(true);
            }
        }
    }
}
