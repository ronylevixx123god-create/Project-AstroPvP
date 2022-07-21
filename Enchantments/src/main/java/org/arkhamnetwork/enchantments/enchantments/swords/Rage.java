package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.google.common.collect.Maps;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.objects.RageObject;
import org.arkhamnetwork.enchantments.util.CacheUtils;
import org.arkhamnetwork.enchantments.util.RageStacksCache;
import org.arkhamnetwork.enchantments.util.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;


public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {

            if (!(target instanceof Player)) {
                return;
            }
            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - ((MetadataValue) target.getMetadata("doubleStriked").get(0)).asLong() < 500L) {
                return;
            }


            RageStacksCache rageStacksCache = CacheUtils.getRageStackCache((Player) user);

            if (rageStacksCache.getLastRageStackTime() + 15000 < System.currentTimeMillis() || !rageStacksCache.isSameVictim((Player) target)) {
                rageStacksCache.updateLastRageStackTime();
                rageStacksCache.resetRageStack();
                rageStacksCache.updateVictim((Player) target);
                return;
            }


            if (rageStacksCache.getRageStack() >= 1) {
                event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageStacksCache.getRageStack() * 0.45);
                this.playRageEffect(target.getLocation());

            }

            rageStacksCache.incrementRageStack();
            rageStacksCache.updateLastRageStackTime();
            rageStacksCache.updateVictim((Player) target);

            target.setMetadata("effectedByRage", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis()));


        }

    }

    private void playRageEffect(Location loc) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}

