package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SoulTrap extends CustomEnchantment {
    public SoulTrap() {
        super("Soul Trap", GeneralUtil.axe, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (!(playerHit instanceof Player)) {
            return;
        }
        final Player p = (Player) player;
        final Player pHit = (Player) playerHit;
        final double radius = 1.0 * level;
        final double chance = 0.07 * level;
        final long duration = level;
        final ItemStack used = p.getItemInHand();
        if (used == null || !EnchantmentAPI.itemHasEnchantment(used, this.enchantName)) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - p.getMetadata("lastItemHeldChange").get(0).asLong() <= 5L) {
            return;
        }
        if (p.hasMetadata("soulTrap") && p.getMetadata("soulTrap").get(0).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (p.hasMetadata("noSoulProcs"))
            return;
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) > 0 && Math.random() < chance) {
            if (pHit.hasMetadata("soulTrap") && System.currentTimeMillis() < pHit.getMetadata("soulTrap").get(0).asLong() + 5000L) {
                return;
            }
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - p.getMetadata("lastSoulRemoveEvent").get(0).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems(p, 5);
            }
            final int souls = KillTrackerAPI.getAllSouls(pHit);
            int toTake = 10 * level;
            if (toTake > souls) {
                toTake = souls;
            }
            if (toTake > 0) {
                KillTrackerAPI.removeSoulsFromGems(pHit, toTake);
                Bukkit.getLogger().info("removing " + toTake + " souls from " + pHit.getName() + " form " + p.getName() + "'s Soul Trap!");
            } else {
                pHit.damage((double) (1 + level));
            }
            pHit.setMetadata("soulTrap", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + duration * 1000L)));
            pHit.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "** SOUL TRAP " + ChatColor.GRAY + "[" + duration + "s]" + ChatColor.BLUE + ChatColor.BOLD + "**");
            if (KillTrackerAPI.hasGodMode(pHit)) {
                KillTrackerAPI.toggleGodMode(pHit);
            }
            pHit.playSound(pHit.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.3f);
            de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), pHit.getLocation(), 0f,0.5f,0f,0.4f,25);
            de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), pHit.getLocation(), 0f,0.5f,0f,0.4f,25);

        }
    }
}
