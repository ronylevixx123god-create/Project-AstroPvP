package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class HeroicTrap extends HeroicCustomEnchantment {
    private DecimalFormat df;

    public HeroicTrap() {
        super("Titan Trap", "Trap");
        this.df = new DecimalFormat("#.##");
        this.max = 3;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player))
            return;

        if (((Player) player).getWalkSpeed() < 0.2F)
            return;
        double random = Math.random();
        double chance = 0.01D * level;
        double durationTicks = (35 + level * 10);
        Player ptarget = (Player) target;

        if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.CHRISTMAS_SWORD_ORNAMENTAL)) {
            chance = 0.01D * 5;
            durationTicks = (35 + 5 * 10);
        }

        if (random < chance) {
            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.FLAMING_HALO) && Math.random() < 0.50) {
                ptarget.sendMessage(CC.translate("&6&l* FLAMING HALO BLOCKED &7[Titan Trap] &6&l*"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.THANKSGIVING_HELMET_WITCH)) {
                ptarget.sendMessage(CC.translate("&e&l* WITCH HAT &7Blocked &dTitan Trap &e&l*"));
                return;
            }


            if (target.hasMetadata("metaphysicalEnchant")) {
                int metaphysicalLevel = target.getMetadata("metaphysicalEnchant").get(0).asInt();
                chance -= metaphysicalLevel * 0.0125D;
                chance = Math.max(chance, 0.01D);
                if (random > chance) {
                    ptarget.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "** METAPHYSICAL (" + ChatColor.DARK_GRAY + "Trap blocked!" + ChatColor.BOLD + ") **");
                    return;
                }
            }
            ptarget.setWalkSpeed(0.001F);
            ptarget.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int) durationTicks, 128), true);
            Location location = ptarget.getEyeLocation();
            Material typeItWas = location.getBlock().getType();
            List<Player> pl = ptarget.getNearbyEntities(32.0D, 32.0D, 32.0D).stream().filter(ent -> ent instanceof Player).map(ent -> (Player) ent).collect(Collectors.toList());
            pl.add(ptarget);
            for (Player near : pl)
                near.sendBlockChange(location, Material.STATIONARY_WATER, (byte) 0);
            ptarget.playSound(ptarget.getLocation(), Sound.WATER, 10.0F, 1.1F);
            ptarget.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** TITAN TRAP [" + ChatColor.GRAY + this.df.format(durationTicks / 20.0D) + "s" + ChatColor.RED + ChatColor.BOLD + "] **");
            Enchantments.getInstance().getServer().getScheduler().runTaskLater(Enchantments.getInstance(), () -> {
                ptarget.setWalkSpeed(0.2F);
                ptarget.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "** FREE [" + ChatColor.GRAY + "Titan Trap" + ChatColor.GREEN + ChatColor.BOLD + "] **");
                for (Player tar : pl)
                    tar.sendBlockChange(location, typeItWas, (byte) 0);
            }, (int) durationTicks);
            Location l = ptarget.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 80, 2001);

        }
    }
}
