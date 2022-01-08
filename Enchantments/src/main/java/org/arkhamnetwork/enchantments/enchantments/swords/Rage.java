package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.files.CustomConfig;
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
import org.bukkit.plugin.Plugin;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof Player) {
            Player pUser = (Player)user;
            if (!(event.getDamager() instanceof LivingEntity))
                return;
            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - ((MetadataValue)target.getMetadata("doubleStriked").get(0)).asLong() < 500L)
                return;
            if (user.hasMetadata("noRageUntil") && ((MetadataValue)user.getMetadata("noRageUntil").get(0)).asLong() > System.currentTimeMillis())
                return;
            double mod = 0.08D * enchantLevel;
            double hitCombo = ((target instanceof Player) ? getPVPHitCombo((Player)user) : getPVEHitCombo((Player)user)) * mod + 1.0D;
            double rageCap = CustomConfig.get().getDouble("rageComboCap");
            double rageMulti = CustomConfig.get().getDouble("rageMultiplier");

            if (hitCombo > rageCap)
                hitCombo = rageCap;

            if (hitCombo > 1.0D) {
                event.setDamage(event.getDamage() * hitCombo * rageMulti);
                target.setMetadata("effectedByRage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
                //playRageEffect(target.getEyeLocation(), enchantLevel);
            }
            if (target instanceof Player) {
                addPVPHitCombo((Player)user);
            } else {
                addPVEHitCombo((Player)user);
            }
        }
    }

    public static int getPVPHitCombo(Player p) {
        return p.hasMetadata("pvp_rageHitCombo") ? ((MetadataValue)p.getMetadata("pvp_rageHitCombo").get(0)).asInt() : 0;
    }

    public void addPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), getPVPHitCombo(p) + 1));
    }

    public static void subtractPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Math.max(0, getPVPHitCombo(p) - 1)));
    }

    private static int getPVEHitCombo(Player p) {
        return p.hasMetadata("pve_rageHitCombo") ? ((MetadataValue)p.getMetadata("pve_rageHitCombo").get(0)).asInt() : 0;
    }

    private void addPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(getPVEHitCombo(p) + 1)));
    }

    public static void subtractPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(Math.max(0, getPVEHitCombo(p) - 1))));
    }

    private void playRageEffect(Location loc, int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}
