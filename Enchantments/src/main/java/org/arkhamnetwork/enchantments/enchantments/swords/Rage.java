package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.RageUtils;
import org.arkhamnetwork.enchantments.util.config.ConfigManager;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0;
        this.interval = 8.0;
        Bukkit.getPluginManager().registerEvents(new RageListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof Player) {
            Player pUser = (Player) user;
            if (!(event.getDamager() instanceof LivingEntity)) {
                return;
            }

            if (!(target instanceof Player)) return;

            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - ((MetadataValue) target.getMetadata("doubleStriked").get(0)).asLong() < 500L) {
                return;
            }
            if (user.hasMetadata("noRageUntil") && user.getMetadata("noRageUntil").get(0).asLong() > System.currentTimeMillis()) {
                return;
            }

            double mod = Enchantments.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getDouble("rageMultiplier");
            double hitCombo = getPVPHitCombo((Player) user);
            if (hitCombo > 10.0D) {
                hitCombo = 10.0D;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.VALENTINES_2021_AMULET)) {
                hitCombo *= 0.75;
            }

            this.playRageEffect(target.getEyeLocation(), enchantLevel);

            event.setDamage(event.getDamage() * (1 + (hitCombo * mod)));

            target.setMetadata("effectedByRage", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis()));


            this.addPVPHitCombo((Player) user);
        }


    }

/*    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(target instanceof Player)) {
            return;
        }

        if (event.isCancelled()) return;

        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof Player) {

            Player pUser = (Player) user;

            double rageModifier = Enchantments.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getDouble("rageMultiplier");
            Bukkit.broadcastMessage("[RageDebug] " + pUser + " : " + RageUtils.getComboOfPlayer(pUser, true));
            double mod = enchantLevel * rageModifier * RageUtils.getComboOfPlayer(pUser, true);


            playRageEffect(target.getEyeLocation(), enchantLevel);


            if (mod > 10) {
                mod = 10;
            }

            double damage = event.getDamage();



            if (AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CHRISTMAS_2021_SWORD) || AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CHRISTMAS_2021_MUTATED_SWORD)) {
                if (!(Math.random() < 0.35 && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.BACK_TO_SCHOOL_2021_BOOTS))) {
                    mod *=1.33;
                }
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_AMULET) || AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.VALENTINES_2022_MUTATED_AMULET)) {
                mod*=0.7;
            }

            event.setDamage(damage + mod);
        }

    }*/

    public static int getPVPHitCombo(final Player p) {
        return p.hasMetadata("pvp_rageHitCombo") ? p.getMetadata("pvp_rageHitCombo").get(0).asInt() : 0;
    }

    public void addPVPHitCombo(final Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue((Plugin) Enchantments.getInstance(), getPVPHitCombo(p) + 1));
    }

    public static void subtractPVPHitCombo(final Player p) {
        p.setMetadata("pvp_rageHitCombo", new FixedMetadataValue((Plugin) Enchantments.getInstance(), Math.max(0, getPVPHitCombo(p) - 1)));
    }


    private void playRageEffect(final Location loc, final int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}
