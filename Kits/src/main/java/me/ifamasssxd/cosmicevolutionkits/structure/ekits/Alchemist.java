/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.utils.ItemUtils;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Alchemist
extends EvolutionKit {
    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList items = Lists.newArrayList();
        int bookCount = kitLevel;
        while (bookCount-- >= 0) {
            int bookRarityId;
            for (bookRarityId = 3 + new Random().nextInt(2); (double)kitLevel * 0.08 > Math.random() && bookRarityId < 6; ++bookRarityId) {
            }
            if (bookRarityId == 6 && Math.random() > 0.2) {
                --bookRarityId;
            }
            if (bookRarityId == 6) {
                items.add(ItemUtils.getMysterySoulEnchantmentBook());
                continue;
            }
            items.add(TieredEnchantments.getMysteryEnchantmentBook((int)bookRarityId));
        }
        int dustCount = kitLevel * 2;
        while (dustCount-- >= 0) {
            int dustRarityId;
            for (dustRarityId = 3 + new Random().nextInt(2); (double)kitLevel * 0.08 > Math.random() && dustRarityId < 6; ++dustRarityId) {
            }

        }
        if (kitLevel >= 2) {
            int soulCount = Math.max((int)((double)(kitLevel * 500) * (Math.random() * 2.0)), kitLevel * 200) / 2;
            items.add(KillTrackerAPI.createSoulShard((int)soulCount));
        }
        if (kitLevel >= 3) {
            int blackScrollCount = kitLevel / 3;
            while (blackScrollCount-- >= 0) {
                items.add(ItemUtils.getBlackscroll((int)(kitLevel * 5 + new Random().nextInt(51))));
            }
        }
        if (kitLevel >= 5) {
            int whiteScrollCount = kitLevel / 5;
            while (whiteScrollCount-- > 0) {
                items.add(ItemUtils.getWhitescroll());
            }
        }
        if (kitLevel >= 6) {
            int randomScrollCount = kitLevel / 3;
            while (randomScrollCount-- > 0) {
                for (int scrollRarityId = 3; (double)(kitLevel / 10) > Math.random() && scrollRarityId < 6; ++scrollRarityId) {
                }
            }
        }
        if (kitLevel >= 7 && Math.random() < (double)kitLevel * 0.075) {
            items.add(ItemUtils.getTransmogScroll());
        }
        if (kitLevel >= 8 && Math.random() < (double)kitLevel * 0.05) {
            items.add(ItemUtils.getItemNametag());
        }
        if (kitLevel >= 9) {
            if (Math.random() < 0.25) {
                items.add(EnchantmentOrb.getEnchantmentOrb((int)(50 + new Random().nextInt(50)), (int)(kitLevel == 10 ? 11 : 10), (OrbType)OrbType.WEAPON));
            } else {
                items.add(EnchantmentOrb.getEnchantmentOrb((int)(50 + new Random().nextInt(50)), (int)(kitLevel == 10 ? 11 : 10), (OrbType)OrbType.ARMOR));
            }
        }
        if (kitLevel == 10) {
            if (Math.random() < 0.05) {
                items.add(ItemUtils.getTitleVoucher((String)"Alchemist"));
            }
            if (Math.random() <= 0.01) {
                items.add(ItemUtils.getTitleVoucher((String)"DupeExploit"));
            }
        }
        return this.combineSimilarItems(items);
    }

    private List<ItemStack> combineSimilarItems(List<ItemStack> items) {
        ArrayList<ItemStack> newList = new ArrayList<ItemStack>();
        ItemStack lastItem = null;
        for (ItemStack i : items) {
            if (lastItem == null) {
                lastItem = i;
                continue;
            }
            if (i.isSimilar(lastItem)) {
                lastItem.setAmount(lastItem.getAmount() + i.getAmount());
                continue;
            }
            newList.add(lastItem);
            lastItem = i;
        }
        return newList;
    }

    @Override
    public String getItemName() {
        return (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "Alchemist";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "A randomized bundle of...", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Enchantment Books", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Enchantment Dust", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Player Souls", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Black Scrolls", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Mystery Mob Spawners", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "White Scrolls", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Randomization Scrolls", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Transmog Scrolls", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Item Nametags", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Enchantment Orbs", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.YELLOW + "Player Titles", "", (Object)ChatColor.GRAY + "All the ingredients to forge", (Object)ChatColor.GRAY + "your own equipment!", (Object)ChatColor.GOLD + "* Spring Break 2017");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.POTION;
    }

    @Override
    public short getDisplayData() {
        return 0;
    }

    @Override
    public int getInventorySlot() {
        return 2;
    }
}

