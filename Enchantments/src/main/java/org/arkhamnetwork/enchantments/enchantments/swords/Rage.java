package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class Rage extends CustomEnchantment
{
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0;
        this.interval = 8.0;
        Bukkit.getPluginManager().registerEvents(new RageListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getDamage() > 0.0 && user instanceof Player) {
            final Player pUser = (Player)user;
            if (!(event.getDamager() instanceof LivingEntity)) {
                return;
            }
            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - target.getMetadata("doubleStriked").get(0).asLong() < 500L) {
                return;
            }

            final double mod = 0.06 * enchantLevel;
            double hitCombo = ((target instanceof Player) ? getPVPHitCombo((Player)user) : getPVEHitCombo((Player)user)) * mod + 1.0;
            if (hitCombo > 12.0) {
                hitCombo = 12.0;
            }

            this.playRageEffect(target.getEyeLocation(), enchantLevel);
                if (AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CHRISTMAS_2021_SWORD) || AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CHRISTMAS_2021_MUTATED_SWORD)) {
                    AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(25 * hitCombo, event, "&6&lXmas Rage");

                } else {
                    AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(15 * hitCombo, event, "&6&lRage");
                }
                target.setMetadata("effectedByRage", new FixedMetadataValue((Plugin) Enchantments.getInstance(), System.currentTimeMillis()));


            if (target instanceof Player) {
                this.addPVPHitCombo((Player)user);
            }
            else {
                this.addPVEHitCombo((Player)user);
            }
        }
    }

    public static int getPVPHitCombo(final Player p) {
        return p.hasMetadata("pvp_rageHitCombo") ? p.getMetadata("pvp_rageHitCombo").get(0).asInt() : 0;
    }

    public void addPVPHitCombo(final Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue((Plugin)Enchantments.getInstance(), getPVPHitCombo(p) + 1));
    }

    public static void subtractPVPHitCombo(final Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue((Plugin)Enchantments.getInstance(), Math.max(0, getPVPHitCombo(p) - 1)));
    }

    private static int getPVEHitCombo(final Player p) {
        return p.hasMetadata("pve_rageHitCombo") ? p.getMetadata("pve_rageHitCombo").get(0).asInt() : 0;
    }

    private void addPVEHitCombo(final Player p) {
        p.setMetadata("pve_rageHitCombo", new FixedMetadataValue((Plugin)Enchantments.getInstance(), getPVEHitCombo(p) + 1));
    }

    public static void subtractPVEHitCombo(final Player p) {
        p.setMetadata("pve_rageHitCombo", new FixedMetadataValue((Plugin)Enchantments.getInstance(), Math.max(0, getPVEHitCombo(p) - 1)));
    }

    private void playRageEffect(final Location loc, final int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}