/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.block.Block
 *  org.bukkit.block.Chest
 *  org.bukkit.block.DoubleChest
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

final class CommandCondense
extends AbstractCommand {
    private static HashSet<Byte> transparentBlocks = new HashSet<Byte>(Arrays.asList((byte)Material.AIR.getId(), (byte)Material.WATER.getId(), (byte)Material.STATIONARY_WATER.getId(), (byte)Material.LAVA.getId(), (byte)Material.STATIONARY_LAVA.getId()));

    public CommandCondense(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission("enchantments.max.12") && !player.hasPermission("enchantments.max.20")) {
                player.sendMessage((Object)ChatColor.RED + "You do not have permission to use /" + (Object)cmd + ". Tier 4+ rank is required.");
                return true;
            }
            Inventory inventory = CommandCondense.getTargetInventory(player, 5);
            if (inventory == null) {
                inventory = player.getInventory();
            }
            ArrayList<ItemStack> contents = new ArrayList<ItemStack>(inventory.getSize());
            while (this.condenseInventory(player, inventory, true) > 0) {
                for (ItemStack item : inventory.getContents()) {
                    contents.add(item);
                }
                inventory.clear();
                CommandCondense.sortItems(contents);
                for (ItemStack item : contents) {
                    if (item == null) continue;
                    inventory.addItem(new ItemStack[]{item});
                }
                contents.clear();
            }
            player.sendMessage(ChatColor.YELLOW.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + "Successfully combined and organized inventory.");
            return true;
        }
        return true;
    }

    private double getConversionRate(Material type) {
        switch (type) {
            case IRON_INGOT: 
            case GOLD_INGOT: 
            case DIAMOND: 
            case EMERALD: 
            case REDSTONE: 
            case COAL: {
                return 0.1111111111111111;
            }
            case GLOWSTONE_DUST: 
            case CLAY_BALL: 
            case SNOW_BALL: {
                return 0.25;
            }
            case GOLD_NUGGET: {
                return 0.1111111111111111;
            }
        }
        return 1.0;
    }

    private Material getConversionType(Material type) {
        switch (type) {
            case IRON_INGOT: {
                return Material.IRON_BLOCK;
            }
            case GOLD_INGOT: {
                return Material.GOLD_BLOCK;
            }
            case DIAMOND: {
                return Material.DIAMOND_BLOCK;
            }
            case EMERALD: {
                return Material.EMERALD_BLOCK;
            }
            case REDSTONE: {
                return Material.REDSTONE_BLOCK;
            }
            case COAL: {
                return Material.COAL_BLOCK;
            }
            case GLOWSTONE_DUST: {
                return Material.GLOWSTONE;
            }
            case CLAY_BALL: {
                return Material.CLAY;
            }
            case SNOW_BALL: {
                return Material.SNOW_BLOCK;
            }
            case GOLD_NUGGET: {
                return Material.GOLD_INGOT;
            }
        }
        return type;
    }

    private boolean hasRoomForItem(Inventory inventory, ItemStack item) {
        int space_available = 0;
        for (ItemStack compare : inventory.getContents()) {
            if (item.isSimilar(compare)) {
                space_available += compare.getMaxStackSize() - compare.getAmount();
            }
            if (compare != null && compare.getType() != Material.AIR) continue;
            space_available += item.getMaxStackSize();
        }
        return space_available >= item.getAmount();
    }

    private int condenseInventory(Player p, Inventory inventory, boolean firstRun) {
        int x = 0;
        ArrayList overflow = new ArrayList();
        ArrayList<ItemStack> to_remove = new ArrayList<ItemStack>(inventory.getSize());
        for (ItemStack itemStack : inventory.getContents()) {
            int amount;
            int new_item_amount;
            double conversion;
            if (itemStack == null || itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore() || (conversion = this.getConversionRate(itemStack.getType())) == 1.0 || (new_item_amount = (int)((double)(amount = itemStack.getAmount()) * conversion)) == 0) continue;
            int old_item_amount = (int)Math.floor((double)amount % (1.0 / conversion));
            ItemStack new_item = new ItemStack(this.getConversionType(itemStack.getType()), new_item_amount, itemStack.getDurability());
            new_item.setItemMeta(itemStack.getItemMeta().clone());
            overflow.addAll(inventory.addItem(new ItemStack[]{new_item}).values());
            if (old_item_amount <= 0) {
                to_remove.add(itemStack);
                ++x;
                continue;
            }
            int dif = itemStack.getAmount() - old_item_amount;
            itemStack.setAmount(old_item_amount);
            ++x;
        }
        for (ItemStack item : to_remove) {
            inventory.removeItem(new ItemStack[]{item});
        }
        HashMap<Material, Integer> items = new HashMap<Material, Integer>();
        ArrayList<ItemStack> uniqueItems = new ArrayList<ItemStack>();
        for (ItemStack itemStack : inventory) {
            if (itemStack == null || itemStack.getDurability() != 0 || this.getConversionType(itemStack.getType()).equals((Object)itemStack.getType())) continue;
            if (!itemStack.hasItemMeta()) {
                items.put(itemStack.getType(), items.containsKey((Object)itemStack.getType()) ? (Integer)items.get((Object)itemStack.getType()) + itemStack.getAmount() : itemStack.getAmount());
                continue;
            }
            uniqueItems.add(itemStack);
        }
        for (Material material : items.keySet()) {
            inventory.remove(material);
        }
        for (ItemStack itemStack : uniqueItems) {
            inventory.remove(itemStack.getType());
        }
        for (Map.Entry entry : items.entrySet()) {
            inventory.addItem(new ItemStack[]{new ItemStack((Material)entry.getKey(), ((Integer)entry.getValue()).intValue())});
        }
        for (ItemStack itemStack : uniqueItems) {
            inventory.addItem(new ItemStack[]{itemStack});
        }
        Iterator overflow_iter = overflow.iterator();
        while (overflow_iter.hasNext()) {
            ItemStack itemStack = (ItemStack)overflow_iter.next();
            if (!this.hasRoomForItem(inventory, itemStack)) {
                p.getWorld().dropItem(p.getLocation(), itemStack);
                continue;
            }
            inventory.addItem(new ItemStack[]{itemStack});
            overflow_iter.remove();
        }
        return x;
    }

    protected static Inventory getTargetInventory(Player player, int max_distance) {
        try {
            Block block = player.getTargetBlock(transparentBlocks, max_distance);
            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
                Faction f = Board.getFactionAt((FLocation)new FLocation(block.getLocation()));
                if (!f.isNone() && !f.getRelationTo((RelationParticipator)FPlayers.i.get((OfflinePlayer)player)).isAtLeast(Relation.ALLY)) {
                    player.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + " Could not organize target inventory, you must have an ALLY+ relationship to the faction that owns this chunk - defaulting to player inventory.");
                    return null;
                }
                if (block.getState() instanceof DoubleChest) {
                    return ((DoubleChest)block.getState()).getInventory();
                }
                if (block.getState() instanceof Chest) {
                    return ((Chest)block.getState()).getInventory();
                }
            } else if (block.getType() == Material.ENDER_CHEST) {
                return player.getEnderChest();
            }
        }
        catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
        return null;
    }

    protected static void sortItems(List<ItemStack> items) {
        Collections.sort(items, ItemStackComparator.INSTANCE);
    }

    private static class ItemStackComparator
    implements Comparator<ItemStack> {
        protected static final ItemStackComparator INSTANCE = new ItemStackComparator();

        private ItemStackComparator() {
        }

        @Override
        public int compare(ItemStack i1, ItemStack i2) {
            if (i1 == null && i2 != null) {
                return 1;
            }
            if (i1 != null && i2 == null) {
                return -1;
            }
            if (i1 == null && i2 == null) {
                return 0;
            }
            if (i1.getType().getId() < i2.getType().getId()) {
                return -1;
            }
            if (i1.getType().getId() > i2.getType().getId()) {
                return 1;
            }
            if (i1.getDurability() < i2.getDurability()) {
                return -1;
            }
            if (i1.getDurability() > i2.getDurability()) {
                return 1;
            }
            return 0;
        }
    }
}

