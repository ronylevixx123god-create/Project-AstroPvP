package org.arkhamnetwork.enchantments.enchantments.heroic;

import java.text.*;

import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import me.ifamasssxd.cosmicmasks.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import java.util.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class HeroicBleed extends HeroicCustomEnchantment
{
    private DecimalFormat df;

    public HeroicBleed() {
        super("Deep Bleed", "Bleed", GeneralUtil.axe, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0 && user instanceof Player) {

            final double bleedChance = 0.025 * enchantLevel;
            if (Math.random() < bleedChance && getBleedStack(target) < this.getMaxBleedStack(enchantLevel)) {
                if (Math.random() < 0.25 && user.hasMetadata("bleedStack") && EnchantmentAPI.itemHasEnchantment(((Player)user).getItemInHand(), "Combat Medic")) {
                    clearBleedEffect(user);
                }
                this.addBleedStack(user, target);
            }
        }
    }

    private int getMaxBleedStack(final int level) {
        return 20;
    }

    public static int getBleedStack(final LivingEntity le) {
        return le.hasMetadata("bleedStack") ? le.getMetadata("bleedStack").get(0).asInt() : 0;
    }

    public static void clearBleedEffect(final LivingEntity le) {
        le.removeMetadata("bleedStack", (Plugin)Enchantments.getInstance());
        if (le instanceof Player) {
            ((Player)le).setWalkSpeed(0.2f);
        }
        else {
            le.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    private void addBleedStack(final LivingEntity leDamager, final LivingEntity le) {
        le.setMetadata("bleedStack", new FixedMetadataValue((Plugin)Enchantments.getInstance(), getBleedStack(le) + 1));
        final int bleedStack = Math.max(1, getBleedStack(le) / 2);
        if (le instanceof Player) {
            final Player p = (Player)le;
            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.VIRUS_HELMET_HAZMAT)) {
                p.sendMessage(CC.translate("&2&l* PRISON BREAK &7(Hazmat Helmet) &2&l*"));
                return;
            }
            if (p.hasMetadata("immune_freeze")) {
                p.sendMessage(CC.DarkGrayB + "* DRAGON SLAYER [" + ChatColor.GRAY + "Deep Bleed slowness blocked!" + CC.DarkGrayB + "] *");
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) p, SkinType.THANKSGIVING_2022_BOOTS)) {
                p.sendMessage(CC.translate("&6&l* MAINFRAME BOOTS &7[Deep Bleed slowness blocked] &6&l*"));
                return;
            }

            if (MaskType.HOLY.hasEquipped(p)) {
                p.sendMessage(CC.GoldB + "* HOLY MASK [" + ChatColor.GRAY + "Deep Bleed slowness blocked!" + CC.GoldB + "] *");
                return;
            }

            if (((Player)p).getInventory().getChestplate() != null && AstroItemSkinsAPI.hasSkinEquipped((Player) p, SkinType.BACK_TO_SCHOOL_2021_AMULET))
                return;

            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.BACK_TO_SCHOOL_2021_BELT)) {
                p.sendMessage(CC.translate("&8&l* BANDOLIER BELT [&7Blocked Deep Bleed slowness&8&l] *"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) p, SkinType.JULY_2021_BOOTS)) {
                p.sendMessage(CC.translate("&c&l** BARRY's BOLTS [&7Blocked Deep Bleed&c&l] **"));
                return;
            }
            p.setWalkSpeed((float)(0.20000000298023224 - 0.0075 * bleedStack));
            if (bleedStack >= 5 && bleedStack % 5 == 0) {
                p.sendMessage(ChatColor.RED + "** DEEP BLEED [" + ChatColor.DARK_RED + "-" + this.df.format(bleedStack * 0.0075 / 0.20000000298023224 * 100.0) + ChatColor.RED + "% Speed] **");
            }
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.setWalkSpeed(0.2f);
                }
            }, 100L);
            for (final Entity ent : p.getNearbyEntities(7.0, 7.0, 7.0)) {
                if (ent.getEntityId() == leDamager.getEntityId()) {
                    continue;
                }
                if (!(ent instanceof Player)) {
                    continue;
                }
                if (!ent.hasMetadata("bloodLust")) {
                    continue;
                }
                final Player pEnt = (Player)ent;
                if (!GeneralUtil.isAlly(p, pEnt)) {
                    continue;
                }
                final int bloodLustLevel = pEnt.getMetadata("bloodLust").get(0).asInt();
                if (Math.random() >= 0.2 + bloodLustLevel * 0.05) {
                    continue;
                }
                final double amountToHeal = Math.max(4.0, bleedStack * 0.1 * bloodLustLevel);
                //AstroDamageHandler.getInstance().getDamageHandler().healPlayer((LivingEntity)pEnt, amountToHeal, "&d&lDeep Bleed");
                pEnt.playSound(pEnt.getLocation(), Sound.EAT, 0.4f, 0.6f);
            }
        }
        else {
            //le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bleedStack * 20, Math.max(1, bleedStack / 3)));
        }
        this.playBleedEffect(le.getLocation().add(0.0, 1.0, 0.0), bleedStack);
    }

    private void playBleedEffect(final Location loc, final int bleedStack) {
        for (int i = Math.max(1, bleedStack / 2); i > 0; --i) {
            WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
            WorldUtil.sendWorldEffect(loc.getBlock(), Material.BEDROCK.getId(), 2001);
        }
    }
}
