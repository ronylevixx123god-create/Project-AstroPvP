package org.arkhamnetwork.enchantments.enchantments.swords;

import com.bryside.astroattributes.DragonTitanAttributesAPI;
import com.bryside.astroattributes.enums.RuneType;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astroparticles.utils.ParticleUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), Enchantments.getInstance());
    }

    private static int getPVPHitCombo(Player p) {
        return p.hasMetadata("pvp_rageHitCombo") ? (p.getMetadata("pvp_rageHitCombo").get(0)).asInt() : 0;
    }

    public static void subtractPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), Math.max(0, getPVPHitCombo(p) - 1)));
    }

    private static int getPVEHitCombo(Player p) {
        return p.hasMetadata("pve_rageHitCombo") ? (p.getMetadata("pve_rageHitCombo").get(0)).asInt() : 0;
    }

    public static void subtractPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), Math.max(0, getPVEHitCombo(p) - 1)));
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target instanceof ArmorStand) {
            return;
        }
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {
            Player pUser = (Player) user;
            if (!pUser.getItemInHand().getType().name().toLowerCase().contains("_sword") || !(event.getDamager() instanceof LivingEntity))
                return;

            double mod = 0.118D * enchantLevel;
            double hitCombo = ((target instanceof Player) ? getPVPHitCombo((Player) user) : getPVEHitCombo((Player) user)) * mod + 1.0D;
            if (hitCombo > 8.0D)
                hitCombo = 8.0D;
            if (hitCombo > 1.0D && !GeneralUtil.isEffectedByDevour(target)) {
                if (user instanceof Player) {
                    if (ParticleUtils.isPlayerIndicating((Player) user))
                        playRageEffect(target.getEyeLocation(), enchantLevel);
                }
                double damage = event.getDamage(EntityDamageEvent.DamageModifier.BASE);

                    event.setDamage((damage * hitCombo * mod));
                target.setMetadata("effectedByRage", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis()));
            }
            if (target instanceof Player) {
                addPVPHitCombo((Player) user);
            } else {
                if (target instanceof Player) {
                    subtractPVEHitCombo((Player) target);
                }

                addPVEHitCombo((Player) user);
            }
        }
    }

    private void addPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), getPVPHitCombo(p) + 1));
    }

    private void addPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), getPVEHitCombo(p) + 1));
    }

    private void playRageEffect(Location loc, int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}
