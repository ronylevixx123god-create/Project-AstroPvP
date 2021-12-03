package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.astroparticles.utils.ParticleUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.ParticleEffect;
import org.bukkit.Bukkit;
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
        super("Rogue", GeneralUtil.weapons, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (!(player instanceof Player)) // rooThink
            return;
        if (!(playerHit instanceof Player)) return;
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.SUMMER_2021_AMULET) && Math.random() < 0.20) {
            playerHit.sendMessage(CC.translate("&c&l* LUAU LEI &7Blocked Rogue &c&l*"));
            return;
        }
        if (!KillTrackerAPI.hasGodMode((Player) player))
            return;
        if (player.hasMetadata("noSoulProcs"))
            return;
        final Player p = (Player) player;
        final ItemStack used = p.getItemInHand();
        if (used == null || !EnchantmentAPI.itemHasEnchantment(used, this.enchantName)) {
            return;
        }
        if (this.isPlayerBehind(p, playerHit)) {
            final double multi = 1.0 + 0.166666666666666 * level + Math.random() / 4;
            final int soulCost = 150;
            if (soulCost > KillTrackerAPI.getAllSouls(p)) {
                return;
            }
            double damage = e.getDamage();
            if (!(e.getDamager() instanceof Player)) return;
            if (((Player) e.getDamager()).getItemInHand() == null) return;
            if (EnchantmentAPI.itemHasEnchantment(((Player)e.getDamager()).getItemInHand(),"Soul Master")) {
                damage*=(((2 * 5)/100) + 1);
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                if (WeaponBonusUtils.hasWeaponBonus(((Player) player).getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(((Player)player).getItemInHand()) == WeaponBonusType.REFORGED && Math.random() < 0.5) {
                    e.setDamage(damage + (multi * 0.3));
                    ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&b&lReforged Bonus&c&l)"));

                } else {
                    e.setDamage(damage + (multi * 0.31));
                    ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&e&lSoul Harvester&c&l)"));

                }
            } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.SUMMER_2021_SWORD)) {
                e.setDamage(damage + (multi * 0.3));
                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&c&lSoul Maui's Hook&c&l)"));
            } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.DOOMSDAY_SWORD)) {
                    e.setDamage(damage + (multi * 0.30));
                    ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&2&lDoomsday Machete&c&l)"));
            } else {
                ((Player) playerHit).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** " + ((Player) player).getName() + "'s ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] **"));
                e.setDamage(damage + (multi * 0.2));
            }


            if (player instanceof Player) {
                try {
                    if (ParticleUtils.isPlayerIndicating((Player) player)) {
                        try {
                            de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0, 1, 0, 0.4f, 12);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } catch (Exception exception) {

                }
            }
            KillTrackerAPI.removeSoulsFromGems(p, soulCost);
            if (playerHit instanceof Player) {
                DamageIndicatorUtils.sendDamageIndicator((Player) playerHit, CC.DarkAquaB + "* " + "ROGUE" + CC.Gray + "[" + new DecimalFormat("##.##").format(multi) + "x]" + CC.DarkAquaB +  "*", "ROGUE");
            }
        }
    }

    public boolean isPlayerBehind(final Player player, final Entity theEntity) {
        Location playerLoc = player.getLocation();
        Location targetLoc = theEntity.getLocation();
        double pvecy = -Math.sin(Math.toRadians(playerLoc.getPitch()));
        double pvecx = -Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.sin(Math.toRadians(playerLoc.getYaw()));
        double pvecz = Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.cos(Math.toRadians(playerLoc.getYaw()));
        double tvecy = -Math.sin(Math.toRadians(targetLoc.getPitch()));
        double tvecx = -Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.sin(Math.toRadians(targetLoc.getYaw()));
        double tvecz = Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.cos(Math.toRadians(targetLoc.getYaw()));
        double dot = tvecx * pvecx + tvecy * pvecy + tvecz * pvecz;
        return dot > 0.6;
    }
}
