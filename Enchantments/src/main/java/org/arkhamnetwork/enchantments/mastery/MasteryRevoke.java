package org.arkhamnetwork.enchantments.mastery;

import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class MasteryRevoke extends MasteryCustomEnchantment {
    public MasteryRevoke() {
        super("Revoke", GeneralUtil.swords, 10);
        this.max = 3;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = 0.1D * enchantLevel;
        long time = (long) (enchantLevel * 1.5D);
        if (target.hasMetadata("revoked"))
            return;
        if (random < chance) {
            if (user.hasMetadata("RevokeCooldown") && System.currentTimeMillis() - ((MetadataValue) user.getMetadata("RevokeCooldown").get(0)).asLong() <= 60000L)
                return;
            user.setMetadata("RevokeCooldown", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis())));
            target.setMetadata("revoked", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), ((Player) user).getName()));
            ((Player) user).sendMessage("");
            ((Player) user).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** REVOKED " + ((Player) target).getName() + "'s ANTI M-KITS **");
            ((Player) user).sendMessage("");
            Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), () -> target.removeMetadata("revoked", (Plugin) Enchantments.getInstance()), 20L * time);
        }
    }
}

