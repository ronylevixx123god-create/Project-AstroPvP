package org.arkhamnetwork.enchantments.enchantments.axes;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.LunaSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class Rogue extends CustomEnchantment {

    public Rogue() {
        super("Rogue", GeneralUtil.swords, 2);
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
        if (player.hasMetadata("witcher_ability"))
            return;
        final Player p = (Player) player;
        final ItemStack used = p.getItemInHand();
        if (used == null || !EnchantmentAPI.itemHasEnchantment(used, this.enchantName)) {
            return;
        }
        if (this.isPlayerBehind(p, playerHit)) {
            double multi = 1.0D + 0.166666666666666D * (double) level + Math.random() / 2.0D;
            final int soulCost = 150;
            if (soulCost > KillTrackerAPI.getAllSouls(p)) {
                return;
            }
            double damage = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            if (!(e.getDamager() instanceof Player)) return;
            if (((Player) e.getDamager()).getItemInHand() == null) return;
            if (LunaSet.isLunarStriked((Player) playerHit)) return;
            if (EnchantmentAPI.itemHasEnchantment(((Player) e.getDamager()).getItemInHand(), "Soul Master")) {
                damage *= 1.1;
            }

            if (CosmicArmorSetsAPI.hasCrystalApplied((Player) e.getDamager(), ArmorSet.LUNA)) {
                damage *= 1.1;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                if (WeaponBonusUtils.hasWeaponBonus(((Player) player).getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(((Player) player).getItemInHand()) == WeaponBonusType.REFORGED && Math.random() < 0.5) {
                    e.setDamage(damage + (multi * 0.15));
                    ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&b&lReforged Bonus&c&l)"));

                } else {
                    e.setDamage(damage + (multi * 0.15));
                    ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&e&lSoul Harvester&c&l)"));

                }
            } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.SUMMER_2021_SWORD)) {
                e.setDamage(damage + (multi * 0.15));
                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&c&lSoul Maui's Hook&c&l)"));
            } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.DOOMSDAY_SWORD)) {
                e.setDamage(damage + (multi * 0.15));
                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&2&lDoomsday Machete&c&l)"));
            } else {
                ((Player) playerHit).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** " + ((Player) player).getName() + "'s ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] **"));
                e.setDamage(damage + (multi * 0.1));
            }


            de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0, 1, 0, 0.4f, 15);

            KillTrackerAPI.removeSoulsFromGems(p, soulCost);
            DamageIndicatorUtils.sendDamageIndicator((Player) playerHit, CC.DarkAquaB + "* " + "ROGUE" + CC.Gray + "[" + new DecimalFormat("##.##").format(multi) + "x]" + CC.DarkAquaB + "*", "ROGUE");
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
