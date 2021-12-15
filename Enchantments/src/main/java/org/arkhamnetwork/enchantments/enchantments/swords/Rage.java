package org.arkhamnetwork.enchantments.enchantments.swords;

import com.bryside.astroattributes.DragonTitanAttributesAPI;
import com.bryside.astroattributes.enums.RuneType;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astroparticles.utils.ParticleUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.RageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), Enchantments.getInstance());
    }

/*    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target instanceof ArmorStand) {
            return;
        }
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {
            Player pUser = (Player) user;
            double mod = 0.1D * (double)enchantLevel;
            double hitCombo = RageUtils.getComboOfPlayer(pUser, target instanceof Player);
            if (hitCombo > 3.5D) {
                hitCombo = 3.5D;
            }

            if (hitCombo > 1.0D && !GeneralUtil.isEffectedByDevour(target)) {
                event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * hitCombo);
                playRageEffect(target.getEyeLocation(), enchantLevel);
            }
        }

    }*/

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {
            Player pUser = (Player)user;
            if (!pUser.getItemInHand().getType().name().toLowerCase().contains("_sword") || !(event.getDamager() instanceof LivingEntity)) {
                return;
            }

            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - ((MetadataValue)target.getMetadata("doubleStriked").get(0)).asLong() < 500L) {
                return;
            }

            double mod = 0.1D * (double)enchantLevel;
            double hitCombo = (double)(target instanceof Player ? getPVPHitCombo((Player)user) : getPVEHitCombo((Player)user)) * mod + 1.0D;
            if (hitCombo > 4.5D) {
                hitCombo = 4.5D;
            }

            if (hitCombo > 1.0D && !GeneralUtil.isEffectedByDevour(target)) {
                this.playRageEffect(target.getEyeLocation(), enchantLevel);
                event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * hitCombo);
                target.setMetadata("effectedByRage", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis()));
            }

            if (target instanceof Player) {
                this.addPVPHitCombo((Player)user);
            } else {
                this.addPVEHitCombo((Player)user);
            }
        }

    }

    private void playRageEffect(Location loc, int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }

    private static int getPVPHitCombo(Player p) {
        return p.hasMetadata("pvp_rageHitCombo") ? ((MetadataValue)p.getMetadata("pvp_rageHitCombo").get(0)).asInt() : 0;
    }

    private void addPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), getPVPHitCombo(p) + 1));
    }

    public static void subtractPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), Math.max(0, getPVPHitCombo(p) - 1)));
    }

    private static int getPVEHitCombo(Player p) {
        return p.hasMetadata("pve_rageHitCombo") ? ((MetadataValue)p.getMetadata("pve_rageHitCombo").get(0)).asInt() : 0;
    }

    private void addPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), getPVEHitCombo(p) + 1));
    }

    public static void subtractPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), Math.max(0, getPVEHitCombo(p) - 1)));
    }
}
