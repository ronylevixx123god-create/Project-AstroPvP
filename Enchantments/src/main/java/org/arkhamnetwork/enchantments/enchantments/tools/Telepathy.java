package org.arkhamnetwork.enchantments.enchantments.tools;

import org.bukkit.*;
import org.arkhamnetwork.enchantments.util.*;
import java.util.*;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.libs.*;
import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import org.arkhamnetwork.enchantments.mastery.*;
import org.arkhamnetwork.CosmicCustomTNT.listeners.*;
import org.arkhamnetwork.CosmicCustomTNT.enums.*;
import org.bukkit.inventory.ItemStack;

public class Telepathy extends CustomEnchantment
{
    private List<Material> fortuneBlocks;
    
    public Telepathy() {
        super("Telepathy", GeneralUtil.tools, 6);
        this.fortuneBlocks = new ArrayList<Material>(Arrays.asList(Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.QUARTZ_ORE, Material.LAPIS_ORE));
        this.max = 4;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            final BlockBreakEvent bbe = (BlockBreakEvent)event;
            final Material m = bbe.getBlock().getType();
            if (m == Material.SPONGE) {
                return;
            }
            final double chance = enchantLevel * 0.25;
            if (Math.random() < chance) {
                if (bbe.getBlock().getDrops().size() < 0 || ((bbe.getBlock().getType() == Material.IRON_ORE || bbe.getBlock().getType() == Material.GOLD_ORE) && EnchantmentAPI.itemHasEnchantment(player.getItemInHand(), "Auto Smelt"))) {
                    return;
                }
                if (bbe.getBlock().getDrops(player.getItemInHand()).size() < 0) {
                    return;
                }
                final Collection<ItemStack> drops = (Collection<ItemStack>)bbe.getBlock().getDrops(player.getItemInHand());
                if (drops.size() <= 0) {
                    return;
                }
                final EnchantBreakBlockEvent enchantBreakEvent = new EnchantBreakBlockEvent(player, bbe.getBlock(), m);
                if (!enchantBreakEvent.callEvent()) {
                    return;
                }
                ItemStack drop = (ItemStack)drops.toArray()[0];
                final boolean hasSilkTouch = player.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH);
                final boolean hasAutoSell = EnchantmentAPI.itemHasEnchantment(player.getItemInHand(), "Auto Sell");
                if (hasAutoSell && MasteryAutoSell.getSellPrice(drop.getData()) > 0.0) {
                    return;
                }
                boolean silkTouchedBlock = false;
                if (hasSilkTouch) {
                    final int amount = drop.getAmount();
                    final Item item = Item.getItemOf(net.minecraft.server.v1_8_R3.Block.getById(bbe.getBlock().getTypeId()));
                    short durability = bbe.getBlock().getData();
                    final Material mm = Material.getMaterial(Item.getId(item));
                    if (mm == Material.ANVIL) {
                        durability = 0;
                    }
                    if (mm != drop.getType()) {
                        silkTouchedBlock = true;
                    }
                    drop = new ItemStack(mm, amount, durability);
                }
                if (bbe.getBlock().getType() == Material.TNT && TNTListener.tnt_tracker.containsKey(bbe.getBlock().getLocation())) {
                    final TNTTypes tntType = TNTListener.tnt_tracker.get(bbe.getBlock().getLocation());
                    drop = TNTTypes.getTNTItem(tntType);
                }
                if (bbe.getBlock().getType() == Material.DOUBLE_STEP) {
                    drop = new ItemStack(Material.STEP, 2);
                }
                if (!silkTouchedBlock) {
                    final int fortuneDropAmount = this.getBlockDropsFromEnchantedTool(block, player.getItemInHand());
                    if (drop.getAmount() < fortuneDropAmount) {
                        drop.setAmount(fortuneDropAmount);
                    }
                }
                bbe.getBlock().setTypeIdAndData(0, (byte)0, true);
                player.giveExp(bbe.getExpToDrop());
                if (bbe.getBlock().getType() == Material.RAILS) {
                    return;
                }
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(drop);
                }
                else {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
            }
        }
    }
    
    private int getBlockDropsFromEnchantedTool(final Block b, final ItemStack tool) {
        final Material m = b.getType();
        if (tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 0) {
            final int fortune = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if (this.fortuneBlocks.contains(b.getType())) {
                if (fortune == 1) {
                    return 1 + ((Math.random() < 0.25) ? 1 : 0);
                }
                if (fortune == 2) {
                    return 1 + ((Math.random() < 0.25) ? 1 : 0) + ((Math.random() < 0.25) ? 1 : 0) + ((Math.random() < 0.25) ? 1 : 0);
                }
                if (fortune >= 3) {
                    return 1 + ((Math.random() < 0.2) ? 1 : 0) + ((Math.random() < 0.2) ? 1 : 0) + ((Math.random() < 0.2) ? 1 : 0) + ((Math.random() < 0.2) ? 1 : 0);
                }
            }
        }
        return 1;
    }
}
