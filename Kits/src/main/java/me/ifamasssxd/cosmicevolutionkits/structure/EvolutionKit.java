/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.UpgradeUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicevolutionkits.utils.InventoryUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;


public abstract class EvolutionKit
{
    protected final Random random;

    public EvolutionKit() {
        this.random = new Random();
    }

    public abstract List<ItemStack> getItems(final Player p0, final int p1);

    public abstract String getItemName();

    public abstract List<String> getLore();

    public abstract Material getDisplayMaterial();

    public short getDisplayData() {
        return 0;
    }

    public abstract int getInventorySlot();

    public String getKitGemItemName() {
        return ChatColor.GOLD + ChatColor.BOLD.toString() + "EVOLUTION KIT " + ChatColor.YELLOW + this.getItemName();
    }

    public List<String> getKitGemLore() {
        return Lists.newArrayList(ChatColor.GRAY + "Right-click this gem to unlock", ChatColor.GRAY + "the power of the " + this.getItemName() + ChatColor.GRAY + " /vkit.");
    }

    public String getKitLevelUpItemName() {
        return CC.DarkGreenB + "Evolution Gem " + CC.DarkGreen + "(" + ChatColor.YELLOW + this.getItemName() + CC.DarkGreen + ")";
    }

    public List<String> getKitLevelUpLore() {
        return Lists.newArrayList(ChatColor.GRAY + "Right-click this evolution gem to", ChatColor.GRAY + "increase the power of the", this.getItemName() + CC.Gray + " /vkit by 1 level.");
    }

    public int getMaxLevel(final String name) {
        return EnchantmentAPI.getEnchantment(name).getMaxLevel();
    }

    public void onClaim(final Player player, final EvolutionKits parent, final KitPlayer kitPlayer) {
        boolean dropped = false;
        int level = kitPlayer.getLevel(parent);
        final List<ItemStack> items = this.getItems(player, level);
        if (items.size() > this.getEmptySlots(player) && parent != EvolutionKits.MIMIC) {
            player.sendMessage("");
            player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your me.astropvp.cratekeys.inventory was too full to accept all of your kit me.astropvp.cratekeys.items, please clear some more slots and try again.");
            return;
        }
        for (final ItemStack item : items) {
            if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && ItemID.needsItemID(item) && item.getAmount() == 1) {
                ItemID.setItemID(item, ItemID.generateNewItemID());
            }
            if (!InventoryUtils.giveItem(player, item)) {
                dropped = true;
            }
        }
        if (dropped) {
            player.sendMessage("");
            player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your me.astropvp.cratekeys.inventory was too full to accept all of your kit me.astropvp.cratekeys.items, they have been dropped on the ground and are claimable by you for 30 seconds.");
        }
        kitPlayer.setAmountClaimed(kitPlayer.getAmountClaimed() + 1);
        final int levelClaimed = level;
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "(!) " + ChatColor.GOLD + "You have claimed your " + ChatColor.GOLD + ChatColor.UNDERLINE + "Level " + ChatColor.GOLD + ChatColor.BOLD + ChatColor.UNDERLINE + levelClaimed + ChatColor.GOLD + " " + parent.getKit().getItemName() + ChatColor.GOLD + " Evolution Kit!");
        double levelUpChance = (10 - level) * 5;
        if (player.isOp() && !player.getName().equals("Woofless") && !player.getName().equals("TBNRfrags")) {
            levelUpChance = 100.0;
        }
        final int upgradeLevel = UpgradeUtils.getUpgradeLevel(player, "HEROIC_VKIT_LEVELING");
        final boolean secondUpgradeSuccess = upgradeLevel > 0 && ((upgradeLevel == 1) ? 0.1 : ((upgradeLevel == 2) ? 0.15 : ((upgradeLevel == 3) ? 0.2 : ((upgradeLevel == 4) ? 0.3 : ((upgradeLevel == 5) ? 0.45 : 0.0))))) >= Math.random();
        final boolean originalSuccess = this.random.nextInt(100) <= levelUpChance;
        if (level < 10 && (originalSuccess || secondUpgradeSuccess)) {
            player.sendMessage("");
            ++level;
            if (!originalSuccess || (player.isOp() && secondUpgradeSuccess)) {
                player.sendMessage(CC.YellowB + "(!) " + CC.Yellow + "You leveled up thanks to your " + CC.Gold + "Kit Evolution" + CC.Yellow + " /f upgrade!");
            }
            kitPlayer.levelUp(player, level, parent, null);
        }
        kitPlayer.setCooldown(parent);
        player.sendMessage("");
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 0.9f);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.3f);
    }

    private int getEmptySlots(final Player p) {
        int x = 0;
        for (final ItemStack i : p.getInventory()) {
            if (i == null || i.getType() == Material.AIR) {
                ++x;
            }
        }
        return x;
    }
}

