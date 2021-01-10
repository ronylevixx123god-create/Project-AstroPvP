/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.UpgradeUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import java.util.List;
import java.util.Random;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.cosmicevolutionkits.structure.KitPlayer;
import me.ifamasssxd.cosmicevolutionkits.utils.InventoryUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class EvolutionKit {
    protected final Random random = new Random();

    public abstract List<ItemStack> getItems(Player var1, int var2);

    public abstract String getItemName();

    public abstract List<String> getLore();

    public abstract Material getDisplayMaterial();

    public short getDisplayData() {
        return 0;
    }

    public abstract int getInventorySlot();

    public String getKitGemItemName() {
        return (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "EVOLUTION KIT " + (Object)ChatColor.YELLOW + this.getItemName();
    }

    public List<String> getKitGemLore() {
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.GRAY + "Right-click this gem to unlock", (Object)ChatColor.GRAY + "the power of the " + this.getItemName() + (Object)ChatColor.GRAY + " /vkit."});
    }

    public String getKitLevelUpItemName() {
        return CC.DarkGreenB + "Evolution Gem " + CC.DarkGreen + "(" + (Object)ChatColor.YELLOW + this.getItemName() + CC.DarkGreen + ")";
    }

    public List<String> getKitLevelUpLore() {
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.GRAY + "Right-click this evolution gem to", (Object)ChatColor.GRAY + "increase the power of the", this.getItemName() + CC.Gray + " /vkit by 1 level."});
    }

    public int getMaxLevel(String name) {
        return EnchantmentAPI.getEnchantment((String)name).getMaxLevel();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void onClaim(Player player, EvolutionKits parent, KitPlayer kitPlayer) {
        dropped = false;
        level = kitPlayer.getLevel(parent);
        items = this.getItems(player, level);
        if (items.size() > this.getEmptySlots(player) && parent != EvolutionKits.MIMIC) {
            player.sendMessage("");
            player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Your inventory was too full to accept all of your kit items, please clear some more slots and try again.");
            return;
        }
        for (ItemStack item : items) {
            if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && ItemID.needsItemID((ItemStack)item) && item.getAmount() == 1) {
                ItemID.setItemID((ItemStack)item, (long)ItemID.generateNewItemID());
            }
            if (InventoryUtils.giveItem(player, item)) continue;
            dropped = true;
        }
        if (dropped) {
            player.sendMessage("");
            player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Your inventory was too full to accept all of your kit items, they have been dropped on the ground and are claimable by you for 30 seconds.");
        }
        kitPlayer.setAmountClaimed(kitPlayer.getAmountClaimed() + 1);
        levelClaimed = level;
        player.sendMessage("");
        player.sendMessage((Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GOLD + "You have claimed your " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + "Level " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + (Object)ChatColor.UNDERLINE + levelClaimed + (Object)ChatColor.GOLD + " " + parent.getKit().getItemName() + (Object)ChatColor.GOLD + " Evolution Kit!");
        levelUpChance = (10 - level) * 5;
        if (player.isOp() && !player.getName().equals("Woofless") && !player.getName().equals("TBNRfrags")) {
            levelUpChance = 100.0;
        }
        if ((upgradeLevel = UpgradeUtils.getUpgradeLevel((Player)player, (String)"HEROIC_VKIT_LEVELING")) <= 0) ** GOTO lbl-1000
        v0 = upgradeLevel == 1 ? 0.1 : (upgradeLevel == 2 ? 0.15 : (upgradeLevel == 3 ? 0.2 : (upgradeLevel == 4 ? 0.3 : (upgradeLevel == 5 ? 0.45 : 0.0))));
        if (v0 >= Math.random()) {
            v1 = true;
        } else lbl-1000:
        // 2 sources

        {
            v1 = false;
        }
        secondUpgradeSuccess = v1;
        v2 = originalSuccess = (double)this.random.nextInt(100) <= levelUpChance;
        if (level < 10 && (originalSuccess || secondUpgradeSuccess)) {
            player.sendMessage("");
            ++level;
            if (!originalSuccess || player.isOp() && secondUpgradeSuccess) {
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

    private int getEmptySlots(Player p) {
        int x = 0;
        for (ItemStack i : p.getInventory()) {
            if (i != null && i.getType() != Material.AIR) continue;
            ++x;
        }
        return x;
    }
}

