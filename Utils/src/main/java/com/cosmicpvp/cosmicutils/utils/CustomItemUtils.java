/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicCrate.api.CCAPI
 *  com.compilex.CosmicCrate.libs.Version
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Boss
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Bosses
 *  com.cosmicpvp.CosmicBossEggs.util.MiscUtil
 *  com.cosmicpvp.CosmicRankQuests.utils.PlayerRankUtils
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.listeners.EnvoySummonItemListener
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.cosmicpvp.CosmicGlobalKits.libs.FallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.cosmicbosses.utils.BossEggUtils
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.patches.ItemNametag
 *  org.arkhamnetwork.Arkkit.patches.McmmoCredits
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.arkhamnetwork.CosmicMoneyNotes.ArkhamMoneyNotesAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 */
package com.cosmicpvp.cosmicutils.utils;

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.cosmicpvp.CosmicBossEggs.boss.type.Boss;
import com.cosmicpvp.CosmicBossEggs.boss.type.Bosses;
import com.cosmicpvp.CosmicBossEggs.util.MiscUtil;
import com.cosmicpvp.CosmicRankQuests.utils.PlayerRankUtils;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.listeners.EnvoySummonItemListener;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.struct.ItemTier;
import com.cosmicpvp.cosmicutils.utils.TitleItems;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossEggUtils;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.killtracker.KillTrackerAPI;
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

public class CustomItemUtils {
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

    public static ItemStack createSoulGem(int amount) {
        return KillTrackerAPI.createSoulShard((int)amount);
    }

    public static ItemStack createGKitFallenHero(String name) {
        if (!CosmicUtils.get().isGlobalKitsEnabled()) {
            return null;
        }
        return FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.valueOf((String)name));
    }

    public static ItemStack createVKitFallenHero(String name) {
        if (!CosmicUtils.get().isEvolutionKitEnabled()) {
            return null;
        }
        return FallenHero.getFallenHeroItem((EvolutionKit)EvolutionKits.valueOf((String)name).getKit());
    }

    public static ItemStack createRandomGKitFallenHero() {
        if (!CosmicUtils.get().isGlobalKitsEnabled()) {
            return null;
        }
        return FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[ThreadLocalRandom.current().nextInt(GlobalKits.values().length)]);
    }

    public static ItemStack createRandomVKitFallenHero() {
        if (!CosmicUtils.get().isEvolutionKitEnabled()) {
            return null;
        }
        return FallenHero.getFallenHeroItem((EvolutionKit)EvolutionKits.values()[ThreadLocalRandom.current().nextInt(EvolutionKits.values().length)].getKit());
    }

    public static ItemStack createRandomEvolutionKitGem() {
        if (!CosmicUtils.get().isEvolutionKitEnabled()) {
            return null;
        }
        return CustomItemUtils.createEvolutionKitGem(EvolutionKits.values()[ThreadLocalRandom.current().nextInt(EvolutionKits.values().length)]);
    }

    public static ItemStack createEvolutionKitGem(EvolutionKits kit) {
        if (!CosmicUtils.get().isEvolutionKitEnabled()) {
            return null;
        }
        return kit.getLevelUpItem(true);
    }

    public static ItemStack createFactionCrystals(int amount, boolean heroic) {
        if (!CosmicUtils.get().isConquestsEnabled()) {
            return null;
        }
        return heroic ? CosmicConquestAPI.createHeroicToken((int)amount) : CosmicConquestAPI.createFactionToken((int)amount);
    }

    public static ItemStack createMysteryMobSpawner() {
        return new CustomItemBuilder(CustomItemBuilder.CustomItemType.MYSTERY_MOBSPAWNER).build();
    }

    public static ItemStack createCosmicCrate(String crateName, String username) {
        if (!CosmicUtils.get().isCosmicCrateEnabled()) {
            return null;
        }
        if (crateName.equalsIgnoreCase("superior")) {
            return CCAPI.getGodlyCosmicCrate();
        }
        if (crateName.equalsIgnoreCase("mystery")) {
            return CCAPI.getMysteryCrate();
        }
        if (crateName.equalsIgnoreCase("heroic")) {
            return CCAPI.getHeroicMysteryCrate();
        }
        return CCAPI.getCosmicCrate((Version)Version.valueOf((String)crateName), (String)username);
    }

    public static ItemStack getXpBottle(int xpAmount, String enchanter) {
        return CustomItemUtils.buildItem(Material.EXP_BOTTLE, (short)0, ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Experience Bottle" + (Object)((Object)ChatColor.GRAY) + " (Throw)", (Object)((Object)ChatColor.LIGHT_PURPLE) + "Value " + (Object)((Object)ChatColor.WHITE) + xpAmount + " XP", (Object)((Object)ChatColor.LIGHT_PURPLE) + "Enchanter " + (Object)((Object)ChatColor.WHITE) + enchanter);
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
        CustomEnchantment ce = EnchantmentAPI.getEnchantments().toArray(new CustomEnchantment[0])[new Random().nextInt(EnchantmentAPI.getEnchantments().size())];
        return EnchantmentBook.getEnchantmentBook(ce, (int)(new Random().nextInt(ce.getMaxLevel()) + 1));
    }

    public static ItemStack getMysterySoulEnchantmentBook() {
        return TieredEnchantments.getMysteryEnchantmentBook((int)6);
    }

    public static ItemStack getMysteryEnchantmentBook(int tier) {
        if (!CosmicUtils.get().isEnchantmentsEnabled()) {
            return null;
        }
        return TieredEnchantments.getMysteryEnchantmentBook((int)tier);
    }

    public static ItemStack getEnchantmentBook(ItemRarity ir) {
        return TieredEnchantments.getMysteryEnchantmentBook((int)CosmicSpaceChestsAPI.getItemRarity((ItemRarity)ir));
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
        return CustomItemUtils.buildItem(Material.MOB_SPAWNER, entityType.getTypeId(), ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + CustomItemUtils.getEntityName(entityType) + ChatColor.GOLD.toString() + " Spawner", (Object)((Object)ChatColor.GRAY) + "Be careful where you place this!");
    }

    public static ItemStack getRankItem(ItemTier ir) {
        return PlayerRankUtils.getRankItem((String)PlayerRankUtils.getRank((int)ir.getRarityId()));
    }

    public static ItemStack getRankItem(ItemRarity ir) {
        return PlayerRankUtils.getRankItem((String)PlayerRankUtils.getRank((int)CustomItemUtils.getItemRarityID(ir)));
    }

    public static ItemStack getDepthStriderBook(int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta ebm = (EnchantmentStorageMeta)book.getItemMeta();
        ebm.addStoredEnchant(Enchantment.DEPTH_STRIDER, level, true);
        book.setItemMeta(ebm);
        return book;
    }

    public static ItemStack getCustomTNT(TNTTypes tntType) {
        return TNTTypes.getTNTItem((TNTTypes)tntType);
    }

    public static ItemStack getCustomCreeperEgg(CreeperTypes creeperType) {
        return CreeperTypes.getCCItem((CreeperTypes)creeperType);
    }

    public static ItemStack getCustomCreeperEgg(String creeperType) {
        return CreeperTypes.getCCItem((CreeperTypes)CreeperTypes.valueOf((String)creeperType));
    }

    public static ItemStack getCustomTNT(String tntEnumName) {
        return CustomItemUtils.getCustomTNT(TNTTypes.valueOf((String)tntEnumName));
    }

    public static ItemStack setAmount(ItemStack i, int amount) {
        i.setAmount(amount);
        return i;
    }

    public static ItemStack getLootCrate(ItemRarity ir) {
        return CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ir);
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
            p.getInventory().addItem(i);
        } else {
            Bukkit.getLogger().info("[CosmicDropPackages] " + p.getName() + " did not have inventory space for " + i + ", dropping on ground!");
            p.getWorld().dropItem(p.getLocation(), i);
        }
    }
}

