package org.arkhamnetwork.enchantments.tasks;

import org.bukkit.*;
import org.arkhamnetwork.enchantments.listeners.*;
import org.arkhamnetwork.enchantments.libs.*;
import com.compilex.CosmicOutposts.*;
import com.compilex.CosmicOutposts.outpost.enums.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.bukkit.metadata.*;
import java.util.*;
import org.bukkit.entity.*;

public class PlayerPotionEffectPersistTask implements Runnable
{
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
                if (p.hasMetadata("armorRescanRunning")) {
                    continue;
                }
                if (p.hasMetadata("lastDuelEnd") && MinecraftServerUtils.getCurrentServerTick() < p.getMetadata("lastDuelEnd").get(0).asLong() + 80L) {
                    continue;
                }
                // TODO: ADD STAff FREEZE HERE
                /*if (this.statusPlusEnabled && StaffPlus.get().freeze.isActive(p.getName())) {
                    continue;
                }*/
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
