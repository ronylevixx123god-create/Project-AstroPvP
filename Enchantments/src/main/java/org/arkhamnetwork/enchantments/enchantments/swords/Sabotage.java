package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Sabotage extends CustomEnchantment {
    public Sabotage() {
        super("Sabotage", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        final Player p = (Player) player;
        if (!GeneralUtil.isSword(p.getItemInHand())) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.CORRUPT_BOOTS)) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - p.getMetadata("lastItemHeldChange").get(0).asLong() <= 5L) {
            return;
        }
        if (p.hasMetadata("soulTrap") && p.getMetadata("soulTrap").get(0).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) > 0) {
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - p.getMetadata("lastSoulRemoveEvent").get(0).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems(p, 8);
            }
            playerHit.setMetadata("sabotage", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
            playerHit.setMetadata("sabotageLevel", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
        }
    }
}
