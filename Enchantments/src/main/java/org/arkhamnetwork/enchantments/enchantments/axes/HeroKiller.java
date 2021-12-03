package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class HeroKiller extends CustomEnchantment {
    public HeroKiller() {
        super("Hero Killer", GeneralUtil.axe, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (!(playerHit instanceof Player)) {
            return;
        }

        if (!(player instanceof Player)) return;
        final Player p = (Player) player;
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

        if (((Player) playerHit).getPlayer().getInventory().getChestplate() != null && AstroItemSkinsAPI.hasSkinEquipped(((Player) playerHit).getPlayer(), SkinType.VALENTINES_2021_BACKPACK)) {
            return;
        }
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) > 0) {
            if (!isWearingHeroic((Player) playerHit))
                return;

            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - p.getMetadata("lastSoulRemoveEvent").get(0).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems(p, 4);
            }


            double increase = 0.10 * level;


            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {

                if (player == null || !(player instanceof Player) || playerHit == null || !(playerHit instanceof Player))
                    return;

                if (((Player) player).getItemInHand() == null || ((Player) playerHit).getItemInHand() == null) return;

                if (!(WeaponBonusUtils.hasWeaponBonus(((Player) playerHit).getItemInHand()))) {
                    if (!(WeaponBonusUtils.getWeaponBonusByItem(((Player) playerHit).getItemInHand()) == WeaponBonusType.REFORGED && Math.random() < 0.4)) {
                        increase *= 1.2;
                    }
                }
                if (!(e.getDamager() instanceof Player)) return;
                if (((Player) e.getDamager()).getItemInHand() == null) return;
                if (EnchantmentAPI.itemHasEnchantment(((Player) e.getDamager()).getItemInHand(), "Soul Master"))
                    increase *= (((1.2 * 5) / 100) + 1);
            }
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1 + increase));
        }
    }


    private boolean isWearingHeroic(final Player player) {
        for (final ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null) {
                final Material type = armor.getType();
                if ((type == Material.LEATHER_BOOTS || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET) && new NBTWrapper(armor).getString("cosmicType").equals("HEROIC_ARMOR")) {
                    return true;
                }
            }
        }
        return false;
    }
}
