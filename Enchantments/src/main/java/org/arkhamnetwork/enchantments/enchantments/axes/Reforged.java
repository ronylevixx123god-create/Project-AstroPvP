package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Reforged extends CustomEnchantment {
    public Reforged() {
        super("Reforged", GeneralUtil.weapons_and_tools, 7);
        this.max = 10;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(user instanceof Player)) {
            return;
        }
        final Player playerAttacker = (Player) user;
        final double random = Math.random();
        final double chance = 0.08 * enchantLevel;
        if (random < chance) {
            final ItemStack weapon = playerAttacker.getItemInHand();
            if (weapon != null && weapon.getDurability() != 0 && weapon.getMaxStackSize() == 1) {
                playerAttacker.setItemInHand(ItemUtils.addDurability(weapon, 1));
                playerAttacker.updateInventory();
            }
        }
    }

    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            final Player playerAttacker = player;
            final double random = Math.random();
            final double chance = 0.08 * enchantLevel;
            if (random < chance) {
                final ItemStack weapon = playerAttacker.getItemInHand();
                if (weapon != null && weapon.getDurability() != 0 && weapon.getMaxStackSize() == 1) {
                    playerAttacker.setItemInHand(ItemUtils.addDurability(weapon, 1));
                    playerAttacker.updateInventory();
                }
            }
        }
    }
}
