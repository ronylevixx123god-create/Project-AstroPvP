package org.arkhamnetwork.enchantments.listeners;

import org.arkhamnetwork.enchantments.libs.*;
import com.compilex.CosmicOutposts.*;
import com.compilex.CosmicOutposts.outpost.enums.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.arkhamnetwork.Arkkit.patches.combatlog.*;
import org.bukkit.event.player.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.event.entity.*;
import com.google.common.collect.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import com.rit.sucy.enchanting.*;
import com.rit.sucy.*;
import org.bukkit.inventory.*;
import java.util.*;

public class PlayerPotionEffectListener implements Listener
{
    private static HashMap<UUID, PlayerPotionEffects> playerPotionEffectTracker;
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        PlayerPotionEffectListener.playerPotionEffectTracker.put(p.getUniqueId(), new PlayerPotionEffects(p));
        if (CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)p) == OutpostTier.NONE) {
            rescanPlayerArmor(e.getPlayer());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(final PlayerQuitEvent e) {
        final PlayerPotionEffects effects = PlayerPotionEffectListener.playerPotionEffectTracker.remove(e.getPlayer().getUniqueId());
        if (effects != null) {
            effects.setBukkitPlayer(null);
        }
    }
    
    @EventHandler
    public void onCombatTag(final CombatTagEvent e) {
        if (!e.wasAlreadyInCombat() && e.getPlayer() != null && e.getPlayer().isOnline()) {
            PlayerPotionEffects.applyPotionEffects(e.getPlayer(), true);
        }
    }
    
    @EventHandler
    public void onPlayerChangeWorld(final PlayerChangedWorldEvent e) {
        e.getPlayer().setMetadata("lastWorldChange", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)true));
        if (CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)e.getPlayer()) == OutpostTier.NONE && !e.getFrom().getName().contains("duel")) {
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    if (!e.getPlayer().isDead() && e.getPlayer().isValid()) {
                        PlayerPotionEffectListener.rescanPlayerArmor(e.getPlayer());
                    }
                }
            }, 11L);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(final PlayerDeathEvent e) {
        e.getEntity().setMetadata("lastPlayerDeath", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
    }
    
    public static void rescanPlayerArmor(final Player p) {
        if (p.hasMetadata("armorRescanRunning")) {
            return;
        }
        p.setMetadata("armorRescanRunning", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)true));
        final ArrayList<ItemStack> armor = new ArrayList<>();
        for (final ItemStack item : p.getInventory().getArmorContents()) {
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    armor.add(item);
                }
            }
        }
        EEquip.unequipPlayer(p);
        for (final PotionEffect pe : new ArrayList<PotionEffect>(p.getActivePotionEffects())) {
            if (PlayerPotionEffects.getTrackedPotionEffects(p).hasPotionEffect(pe.getType(), pe.getAmplifier())) {
                p.removePotionEffect(pe.getType());
            }
        }
        PlayerPotionEffects.resetPotionTracker(p);
        EEquip.loadPlayer(p);
        for (final Map.Entry<CustomEnchantment, Integer> data : EListener.getValidEnchantments(p, armor).entries()) {
            data.getKey().applyEquipEffect(p, (int)data.getValue());
        }
        PlayerPotionEffects.applyPotionEffects(p, true);
        p.removeMetadata("armorRescanRunning", (Plugin)Enchantments.getInstance());
    }
    
    public static HashMap<UUID, PlayerPotionEffects> getPlayerPotionEffectTracker() {
        return PlayerPotionEffectListener.playerPotionEffectTracker;
    }
    
    static {
        PlayerPotionEffectListener.playerPotionEffectTracker = new HashMap<UUID, PlayerPotionEffects>();
    }
}
