/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Boss
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Bosses
 *  com.cosmicpvp.CosmicBossEggs.util.MiscUtil
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.cosmicbosses.utils.BossEggUtils
 *  me.ifamasssxd.cosmicfactionquests.CosmicFactionQuests
 *  org.arkhamnetwork.Arkkit.patches.ItemNametag
 *  org.arkhamnetwork.Arkkit.patches.McmmoCredits
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.arkhamnetwork.CosmicMoneyNotes.ArkhamMoneyNotesAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.EnchantmentStorageMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.cosmicpvp.CosmicSpaceChests.utils;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.cosmicpvp.CosmicBossEggs.boss.type.Boss;
import com.cosmicpvp.CosmicBossEggs.boss.type.Bosses;
import com.cosmicpvp.CosmicBossEggs.util.MiscUtil;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.libs.RankItems;
import com.cosmicpvp.CosmicSpaceChests.libs.TitleItems;
import com.cosmicpvp.CosmicSpaceChests.listeners.EnvoySummonItemListener;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.Arrays;
import java.util.Random;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossEggUtils;
import me.ifamasssxd.cosmicfactionquests.CosmicFactionQuests;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.arkhamnetwork.Arkkit.patches.McmmoCredits;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.arkhamnetwork.CosmicMoneyNotes.ArkhamMoneyNotesAPI;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static ItemStack buildItem(Material m, short metaData, String name, String ... description) {
        ItemStack is = new ItemStack(m, 1, metaData);
        ItemMeta im = is.getItemMeta();
        if (name != null) {
            im.setDisplayName(name);
        }
        if (description != null && description.length > 0) {
            im.setLore(Arrays.asList(description));
        }
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack getFactionQuestReroll(boolean apply) {
        try {
            return CosmicFactionQuests.get().getFactionQuestManager().createQuestRerolltem(apply);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static ItemStack getXpBottle(int xpAmount, String enchanter) {
        return ItemUtils.buildItem(Material.EXP_BOTTLE, (short)0, ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Experience Bottle" + (Object)ChatColor.GRAY + " (Throw)", (Object)ChatColor.LIGHT_PURPLE + "Value " + (Object)ChatColor.WHITE + xpAmount + " XP", (Object)ChatColor.LIGHT_PURPLE + "Enchanter " + (Object)ChatColor.WHITE + enchanter);
    }

    public static ItemStack getMoneyNote(String signer, int amount) {
        return ArkhamMoneyNotesAPI.getMoneyNote((String)signer, (long)amount);
    }

    public static ItemStack getMoneyNoteNoIID(String string, int i) {
        return ArkhamMoneyNotesAPI.getMoneyNoteNoIID((String)string, (long)i);
    }

    public static ItemStack getBossEgg(Bosses b) {
        return MiscUtil.getBossEgg((Boss)b.b);
    }

    public static ItemStack getEnchantmentBook(CustomEnchantment ce) {
        return EnchantmentBook.getEnchantmentBook((CustomEnchantment)ce, (int)(new Random().nextInt(ce.getMaxLevel()) + 1));
    }

    public static ItemStack getEnchantmentBook(CustomEnchantment ce, int level) {
        return EnchantmentBook.getEnchantmentBook((CustomEnchantment)ce, (int)level);
    }

    public static ItemStack getRandomEnchantmentBook() {
        CustomEnchantment ce = EnchantmentAPI.getEnchantments().toArray(new CustomEnchantment[EnchantmentAPI.getEnchantments().size()])[new Random().nextInt(EnchantmentAPI.getEnchantments().size())];
        return EnchantmentBook.getEnchantmentBook((CustomEnchantment)ce, (int)(new Random().nextInt(ce.getMaxLevel()) + 1));
    }

    public static ItemStack getMysterySoulEnchantmentBook() {
        return TieredEnchantments.getMysteryEnchantmentBook((int)6);
    }

    public static ItemStack getEnchantmentBook(ItemRarity ir) {
        return TieredEnchantments.getMysteryEnchantmentBook((int)CosmicSpaceChestsAPI.getItemRarity(ir));
    }

    public static ItemStack getMysteryDust(ItemRarity ir, int successIncrease) {
        return EnchantmentBook.getMagicDust((ItemRarity)ir, (int)successIncrease);
    }

    public static ItemStack getEnchantmentBook(CustomEnchantment ce, int level, int success, int failure) {
        return EnchantmentBook.getEnchantmentBook((CustomEnchantment)ce, (int)level, (int)success, (int)failure);
    }

    public static ItemStack getEnvoySummoner() {
        return EnvoySummonItemListener.buildEnvoySpawnItem();
    }

    public static ItemStack getWhitescroll() {
        return EnchantmentBook.getWhiteScroll();
    }

    public static ItemStack getBlackscroll() {
        return EnchantmentBook.getBlackScroll();
    }

    public static ItemStack getBlackscroll(int successRate) {
        return EnchantmentBook.getBlackScroll((int)successRate);
    }

    public static ItemStack getTitleVoucher(String title) {
        return TitleItems.getTitleItem(title);
    }

    public static ItemStack getMCMMOVoucher(int amount) {
        return McmmoCredits.buildMcmmoCreditItem((int)amount);
    }

    public static ItemStack getSkillVoucher(SkillType type, int amount) {
        return McmmoCredits.buildSkillCreditItem((SkillType)type, (int)amount);
    }

    public static ItemStack getMobSpawner(EntityType entityType) {
        return ItemUtils.buildItem(Material.MOB_SPAWNER, entityType.getTypeId(), ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ItemUtils.getEntityName(entityType) + ChatColor.GOLD.toString() + " Spawner", new String[0]);
    }

    public static ItemStack getRankItem(ItemRarity ir) {
        return RankItems.getRankItem(RankItems.getRank(ItemUtils.getItemRarityID(ir)));
    }

    public static ItemStack getDepthStriderBook(int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta ebm = (EnchantmentStorageMeta)book.getItemMeta();
        ebm.addStoredEnchant(Enchantment.DEPTH_STRIDER, level, true);
        book.setItemMeta((ItemMeta)ebm);
        return book;
    }

    public static ItemStack getCustomTNT(TNTTypes tntType) {
        return TNTTypes.getTNTItem((TNTTypes)tntType);
    }

    public static ItemStack getCustomCreeperEgg(CreeperTypes creeperType) {
        return CreeperTypes.getCCItem((CreeperTypes)creeperType);
    }

    public static ItemStack setAmount(ItemStack i, int amount) {
        i.setAmount(amount);
        return i;
    }

    public static ItemStack getLootCrate(ItemRarity ir) {
        return CosmicSpaceChestsAPI.getDropPackageChest(ir);
    }

    public static ItemStack getTransmogScroll() {
        return EnchantmentBook.getTransmogScroll();
    }

    public static ItemStack getItemNametag() {
        return ItemNametag.getNametagItem();
    }

    public static ItemStack getNewBossEgg(BossType bossType) {
        return BossEggUtils.getBossEgg((BossType)bossType);
    }

    private static String getEntityName(EntityType et) {
        if (et == EntityType.IRON_GOLEM) {
            return "Iron Golem";
        }
        if (et == EntityType.MAGMA_CUBE) {
            return "Magma Cube";
        }
        return et.getName();
    }

    private static int getItemRarityID(ItemRarity ir) {
        if (ir == ItemRarity.SIMPLE) {
            return 1;
        }
        if (ir == ItemRarity.UNIQUE) {
            return 2;
        }
        if (ir == ItemRarity.ELITE) {
            return 3;
        }
        if (ir == ItemRarity.ULTIMATE) {
            return 4;
        }
        if (ir == ItemRarity.LEGENDARY) {
            return 5;
        }
        return 1;
    }

    public static void giveItemToPlayer(Player p, ItemStack i) {
        if (p.getInventory().firstEmpty() != -1) {
            p.getInventory().addItem(new ItemStack[]{i});
        } else {
            Bukkit.getLogger().info("[CosmicDropPackages] " + p.getName() + " did not have inventory space for " + (Object)i + ", dropping on ground!");
            p.getWorld().dropItem(p.getLocation(), i);
        }
    }
}

