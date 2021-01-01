package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class Rogue extends CustomEnchantment {

    public Rogue() {
        super("Rogue", GeneralUtil.axe, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (!(player instanceof Player)) // rooThink
            return;
        if (!KillTrackerAPI.hasGodMode((Player) player))
            return;
        if (player.hasMetadata("noSoulProcs"))
            return;
        final Player p = (Player)player;
        final ItemStack used = p.getItemInHand();
        if (used == null || !EnchantmentAPI.itemHasEnchantment(used, this.enchantName)) {
            return;
        }
        if (this.isPlayerBehind(p, playerHit)) {
            final double multi = 1.0 + 0.166666666666666 * level + Math.random() / 2.0;
            final int soulCost = 150;
            if (soulCost > KillTrackerAPI.getAllSouls(p)) {
                return;
            }
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (multi * 0.85));
            ParticleEffect.playEffect(ParticleEffect.CRIT, player.getLocation().add(0.0, 1.0, 0.0), 0.4f, 12);
            ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS **"));
            KillTrackerAPI.removeSoulsFromGems(p, soulCost);
            if (playerHit instanceof Player) {
                ((Player) playerHit).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** " + ((Player) player).getName() + "'s ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] **"));
            }
        }
    }
    public boolean isPlayerBehind(final Player player, final Entity theEntity) {
        final Location playerLoc = player.getLocation();
        final Location targetLoc = theEntity.getLocation();
        final double pvecy = -Math.sin(Math.toRadians(playerLoc.getPitch()));
        final double pvecx = -Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.sin(Math.toRadians(playerLoc.getYaw()));
        final double pvecz = Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.cos(Math.toRadians(playerLoc.getYaw()));
        final double tvecy = -Math.sin(Math.toRadians(targetLoc.getPitch()));
        final double tvecx = -Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.sin(Math.toRadians(targetLoc.getYaw()));
        final double tvecz = Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.cos(Math.toRadians(targetLoc.getYaw()));
        final double dot = tvecx * pvecx + tvecy * pvecy + tvecz * pvecz;
        return dot > 0.6;
    }
}
