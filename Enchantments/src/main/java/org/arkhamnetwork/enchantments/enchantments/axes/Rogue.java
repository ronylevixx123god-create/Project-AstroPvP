package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.BoostedChainsaw;
import me.astropvp.astropvpskins.objects.skins.swords.Carver;
import net.astropvp.core.killtracker.KillTrackerAPI;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
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
        if (!(player instanceof Player)) // rooThink
            return;
        if (!(playerHit instanceof Player)) return;

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

            if (p.hasMetadata("noSoulProc"))
                return;

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.SUMMER_2021_AMULET) && Math.random() < 0.20) {
                playerHit.sendMessage(CC.translate("&c&l* LUAU LEI &7Blocked Rogue &c&l*"));
                return;
            }

            if (((Player) playerHit).getPlayer().getInventory().getChestplate() != null && (AstroItemSkinsAPI.hasSkinEquipped(((Player) playerHit).getPlayer(), SkinType.THANKSGIVING_2021_BACKPACK) || AstroItemSkinsAPI.hasSkinEquipped(((Player) playerHit).getPlayer(), SkinType.THANKSGIVING_2021_MUTATED_BACKPACK)) && Math.random() < 0.075) {
                return;
            }

            if (((Carver)SkinType.THANKSGIVING_2021_SWORD.getItemSkin()).getRogueSilenced().contains(p.getName())) {
                return;
            }

            if (((BoostedChainsaw)SkinType.BACK_TO_SCHOOL_2021_AXE.getItemSkin()).getChainSawedPlayers().contains(p.getName())) {
                return;
            }
            double chance = 0;

            double damage = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            if (!(e.getDamager() instanceof Player)) return;
            if (((Player) e.getDamager()).getItemInHand() == null) return;
            if (EnchantmentAPI.itemHasEnchantment(((Player) e.getDamager()).getItemInHand(), "Soul Master")) {
                damage *= 1.1;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.JULY_2021_BACKPACK) && player.getHealth() <= player.getMaxHealth()/2) {
                multi +=0.15;
            }

            if (player.hasMetadata("carver_damage")) {
                multi +=0.10;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(multi*10, e, "Rogue");

                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&e&lSoul Harvester&c&l)"));

            } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.SUMMER_2021_SWORD)) {
                AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(multi*10, e, "Rogue");
                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&c&lSoul Maui's Hook&c&l)"));
            } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.DOOMSDAY_SWORD)) {
                AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(multi*10, e, "Rogue");
                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] &c&l-" + soulCost + " SOULS ** (&2&lDoomsday Machete&c&l)"));
            } else {
                ((Player) playerHit).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l** " + ((Player) player).getName() + "'s ROGUE &7[" + new DecimalFormat("##.##").format(multi) + "x] **"));
                AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(multi*10, e, "Rogue");
            }


            //de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0, 1, 0, 0.4f, 12);

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
