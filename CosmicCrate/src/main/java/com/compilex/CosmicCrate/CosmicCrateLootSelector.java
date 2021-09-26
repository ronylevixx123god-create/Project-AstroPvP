// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate;

import org.bukkit.scheduler.BukkitRunnable;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.DungeonsAPI;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicBossEggs.boss.type.Bosses;
import com.cosmicpvp.CosmicSpaceChests.libs.RankItems;
import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.entity.EntityType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.ifamasssxd.killtracker.listeners.Tracker;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import java.util.Random;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import org.apache.commons.lang.WordUtils;
import java.util.Collection;
import com.compilex.CosmicCrate.api.CCAPI;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.inventory.Inventory;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.entity.Player;

public class CosmicCrateLootSelector
{
    public Player player;
    public Version crateVersion;
    public Inventory lootInventory;
    public CosmicCratePhase phase;
    public List<Integer> selectedSlots;
    public List<ItemStack> rewards;
    public volatile List<ItemStack> displayRewards;
    public CopyOnWriteArrayList<SlotAnimationTask> animationTask;
    public String crateInventoryName;
    public MetaAnimationTask metaAnimation;
    public ItemStack metaItem;
    
    public CosmicCrateLootSelector(final Player p, final Version v) {
        this.player = p;
        this.crateVersion = v;
        this.animationTask = new CopyOnWriteArrayList<SlotAnimationTask>();
        this.selectedSlots = new ArrayList<Integer>();
        this.phase = CosmicCratePhase.GENERATING;
        this.rewards = CCAPI.getCosmicCrateLoot(p, v);
        this.displayRewards = new ArrayList<ItemStack>(this.rewards);
        this.crateInventoryName = "Cosmic Crate: " + WordUtils.capitalize(v.name());
        if (this.crateVersion == Version.DECEMBER) {
            this.metaItem = LocalItemUtils.setAmount(CCAPI.getExplosiveSnowball(), 10);
            if (Math.random() < 0.05 || p.getName().equals("Vaquxine")) {
                this.metaItem = KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            }
        }
        if (this.crateVersion == Version.JANURARY) {
            if (Math.random() < 0.025) {
                this.metaItem = KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            }
            else {
                final double x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = KillTrackerAPI.buildTracker(Tracker.Legendary);
                }
                if (x < 0.8) {
                    this.metaItem = KillTrackerAPI.buildTracker(Tracker.Ultimate);
                }
                if (x < 0.5) {
                    this.metaItem = KillTrackerAPI.buildTracker(Tracker.Elite);
                }
                if (Math.random() < 0.05 || p.getName().equals("Vaquxine")) {
                    this.metaItem = TieredEnchantments.getMysteryEnchantmentBook(6);
                }
            }
        }
        if (this.crateVersion == Version.FEBRUARY) {
            if (Math.random() < 0.025) {
                this.metaItem = KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            }
            else if (Math.random() < 0.05) {
                final double x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MUSHROOM_COW);
                }
                if (x < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MAGMA_CUBE);
                }
                if (x < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.GHAST);
                }
            }
            else {
                this.metaItem = LocalItemUtils.setAmount(CCAPI.getExplosiveSnowball(), 10);
            }
        }
        if (this.crateVersion == Version.MARCH) {
            if (Math.random() < 0.025) {
                this.metaItem = KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            }
            else if (Math.random() < 0.05) {
                final double x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MUSHROOM_COW);
                }
                if (x < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MAGMA_CUBE);
                }
                if (x < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.GHAST);
                }
            }
            else {
                this.metaItem = LocalItemUtils.setAmount(ItemUtils.getCustomCreeperEgg(CreeperTypes.Lucky), new Random().nextInt(4) + 4);
            }
        }
        if (this.crateVersion == Version.APRIL) {
            if (Math.random() < 0.025) {
                this.metaItem = ItemUtils.getEnvoySummoner();
            }
            else if (Math.random() < 0.05) {
                int donorTier = new Random().nextInt(5) + 1;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                if (donorTier >= 4) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = RankItems.getRankItem(RankItems.getRank(donorTier));
            }
            else {
                final double x = Math.random();
                if (x < 0.1) {
                    this.metaItem = ItemUtils.getBossEgg(Bosses.HEADLESS_KING);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getBossEgg(Bosses.WITHER);
                }
                else {
                    this.metaItem = ItemUtils.getBossEgg(Bosses.PIG_ZOMBIE);
                }
            }
        }
        if (this.crateVersion == Version.JULY) {
            if (Math.random() < 0.025) {
                this.metaItem = ItemUtils.getEnvoySummoner();
            }
            else if (Math.random() < 0.05) {
                int donorTier = new Random().nextInt(5) + 1;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                if (donorTier >= 4) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
            else {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
            }
        }
        if (this.crateVersion == Version.AUGUST) {
            if (Math.random() < 0.025) {
                this.metaItem = ItemUtils.getMysterySoulEnchantmentBook();
            }
            else if (Math.random() < 0.1) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
            }
        }
        if (this.crateVersion == Version.SCHOOL) {
            if (Math.random() < 0.05) {
                this.metaItem = ItemUtils.getMysterySoulEnchantmentBook();
            }
            else if (Math.random() < 0.7) {
                final double x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MUSHROOM_COW);
                }
                if (x < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MAGMA_CUBE);
                }
                if (x < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.GHAST);
                }
            }
            else {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
        }
        if (this.crateVersion == Version.OCTOBER) {
            if (Math.random() < 0.05) {
                this.metaItem = CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 1800, 4.0);
            }
            else if (Math.random() < 0.75) {
                final double x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MUSHROOM_COW);
                }
                if (x < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.MAGMA_CUBE);
                }
                if (x < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner(EntityType.GHAST);
                }
            }
            else if (Math.random() < 0.25) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
            }
        }
        if (this.crateVersion == Version.HALLOWEEN) {
            if (Math.random() < 0.05) {
                int donorTier = new Random().nextInt(5) + 1;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                if (donorTier >= 4) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
            else if (Math.random() < 0.75) {
                this.metaItem = ItemUtils.setAmount(ItemUtils.getMysterySoulEnchantmentBook(), 2);
            }
            else if (Math.random() < 0.25) {
                this.metaItem = CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 3960, 6.6);
            }
            else {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
            }
        }
        if (this.crateVersion == Version.NOVEMBER) {
            if (Math.random() < 0.05) {
                int donorTier = new Random().nextInt(5) + 1;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                if (donorTier >= 4) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
            else if (Math.random() < 0.65) {
                this.metaItem = CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 1800, 8.0);
            }
            else if (Math.random() < 0.25) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
            }
        }
        if (this.crateVersion == Version.BLACK_FRIDAY) {
            if (Math.random() < 0.15) {
                this.metaItem = ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(100, 10, OrbType.ARMOR), 3);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
            }
        }
        if (this.crateVersion == Version.DECEMBER_2016) {
            if (Math.random() < 0.15) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else if (Math.random() < 0.1) {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
            else {
                this.metaItem = ItemUtils.setAmount(ItemUtils.getMysterySoulEnchantmentBook(), 2);
            }
        }
        if (this.crateVersion == Version.HOLIDAY) {
            if (Math.random() < 0.75) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
        }
        if (this.crateVersion == Version.JANUARY_2017) {
            if (Math.random() < 0.7) {
                final double x = Math.random();
                if (x <= 0.01) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.04) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.1) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
                this.metaItem = ItemUtils.setAmount(this.metaItem, 2);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = EvolutionKitItems.getKitItem(EvolutionKits.PHOENIX);
            }
        }
        if (this.crateVersion == Version.VALENTINE) {
            if (Math.random() < 0.7) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(3);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
        }
        if (this.crateVersion == Version.MARCH_2017) {
            if (Math.random() < 0.7) {
                final double x = Math.random();
                if (x <= 0.02) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.08) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.2) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
                this.metaItem = ItemUtils.setAmount(this.metaItem, 4);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(3);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
        }
        if (this.crateVersion == Version.APRIL_2017) {
            if (Math.random() < 0.7) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = CCAPI.getCosmicCrate(Version.APRIL, "April 2017 Cosmic Crate");
            }
            else {
                this.metaItem = EvolutionKitItems.getKitItem(EvolutionKits.ALCHEMIST);
            }
        }
        if (this.crateVersion == Version.MAY_2017) {
            if (Math.random() < 0.7) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD);
            }
            else if (Math.random() < 0.1) {
                this.metaItem = CosmicConquestAPI.createFactionToken(50 + new Random().nextInt(51));
            }
            else {
                this.metaItem = KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            }
        }
        if (this.crateVersion == Version.JUNE_2017) {
            if (Math.random() < 0.75) {
                final double x = Math.random();
                if (x <= 0.02) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.08) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.2) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
                this.metaItem = ItemUtils.setAmount(this.metaItem, 1 + new Random().nextInt(4));
            }
            else if (Math.random() < 0.2) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CosmicConquestAPI.createFactionToken(50 + new Random().nextInt(51));
            }
        }
        if (this.crateVersion == Version.JULY_2017) {
            if (Math.random() < 0.75) {
                this.metaItem = CCAPI.getCosmicCrate(Version.JULY, "July 2017 Cosmic Crate");
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = EvolutionKitItems.getKitItem(EvolutionKits.OGRE);
            }
        }
        if (this.crateVersion == Version.AUGUST_2017) {
            if (Math.random() < 0.65) {
                this.metaItem = CCAPI.getCosmicCrate(Version.AUGUST, "August 2017 Cosmic Crate");
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CCAPI.getMysteryCrate();
            }
        }
        if (this.crateVersion == Version.SCHOOL_2017) {
            if (Math.random() < 0.65) {
                this.metaItem = ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.GOD), 2 + new Random().nextInt(2)));
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CCAPI.getCosmicCrate(Version.SCHOOL, "Back To School 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.SEPTEMBER_2017) {
            if (Math.random() < 0.65) {
                this.metaItem = CCAPI.getMysteryCrate();
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                final double x = Math.random();
                if (x <= 0.02) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.08) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.2) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
                this.metaItem = ItemID.removeItemID(ItemUtils.setAmount(this.metaItem, 1 + new Random().nextInt(2)));
            }
        }
        if (this.crateVersion == Version.OCTOBER_2017) {
            if (Math.random() < 0.6) {
                this.metaItem = ItemID.removeItemID(ItemUtils.setAmount(ItemUtils.getNewBossEgg(BossType.SOUL_REAPER), 1 + new Random().nextInt(2)));
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CCAPI.getCosmicCrate(Version.OCTOBER, "October 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.HALLOWEEN_2017) {
            if (Math.random() < 0.6) {
                final double x = Math.random();
                if (x <= 0.02) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
                }
                else if (x <= 0.08) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
                }
                else if (x < 0.4) {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
                }
                else {
                    this.metaItem = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
                }
                this.metaItem = ItemID.removeItemID(ItemUtils.setAmount(this.metaItem, 1 + new Random().nextInt(2)));
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CCAPI.getCosmicCrate(Version.HALLOWEEN, "Halloween 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.NOVEMBER_2017) {
            if (Math.random() < 0.6) {
                this.metaItem = ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.GOD), 2 + new Random().nextInt(2)));
            }
            else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CCAPI.getCosmicCrate(Version.NOVEMBER, "November 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.THANKSGIVING_2017) {
            if (Math.random() < 0.65) {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(3);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(donorTier);
            }
            else if (Math.random() < 0.05) {
                this.metaItem = EnchantmentBook.getEnchantmentBook(EnchantmentAPI.getEnchantment("Godly Overload"), 1 + ((Math.random() < 0.25) ? 1 : 0), 10 + new Random().nextInt(40), 50 + new Random().nextInt(50));
            }
            else {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
        }
        if (this.crateVersion == Version.DECEMBER_2017) {
            if (Math.random() < 0.05) {
                this.metaItem = ItemID.removeItemID(DungeonsAPI.get().getDungeon(0).getDungeonPortal());
            }
            else if (Math.random() < 0.05) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = CCAPI.getCosmicCrate(Version.DECEMBER_2016, "December 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.HOLIDAY_2017) {
            if (Math.random() < 0.15) {
                this.metaItem = DungeonsRewardsAPI.getDungeonLootBag(p, DungeonsAPI.get().getDungeon(0));
            }
            else if (Math.random() < 0.05) {
                this.metaItem = ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet(ArmorSet.MOTHER_OF_YIJKI)[new Random().nextInt(4)]);
            }
            else {
                this.metaItem = CCAPI.getCosmicCrate(Version.HOLIDAY, "Holiday 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.JANUARY_2018) {
            if (Math.random() < 0.2) {
                this.metaItem = CommandFallenHero.getHeroicFallenHeroBundle();
            }
            else if (Math.random() < 0.05) {
                this.metaItem = CCAPI.getCosmicCrate(Version.HOLIDAY_2017, "Holiday 2017 Cosmic Crate");
            }
            else {
                this.metaItem = DungeonsRewardsAPI.getDungeonLootBag(p, DungeonsAPI.get().getDungeon(0));
            }
        }
        if (this.crateVersion == Version.FEBUARY_2018) {
            if (Math.random() < 0.2) {
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem(5);
            }
            else if (Math.random() < 0.05) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
            else {
                this.metaItem = DungeonsRewardsAPI.getDungeonLootBag(p, DungeonsAPI.get().getDungeon(0));
            }
        }
        if (this.crateVersion.getCrateData() != null) {
            this.metaItem = this.crateVersion.getCrateData().getRandomMetaItem(p);
        }
    }
    
    public void open() {
        this.lootInventory = Bukkit.createInventory((InventoryHolder)null, 54, this.crateInventoryName);
        final ItemStack hiddenItem = LocalItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "???", ChatColor.GRAY + "Click to redeem an item from", ChatColor.GRAY + "your " + ChatColor.UNDERLINE + this.crateVersion.name() + ChatColor.GRAY + " Cosmic Crate!");
        final ItemStack lockedItem = LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)14, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "???", ChatColor.GRAY + "Redeem all your other rewards to", ChatColor.GRAY + "unlock this special \"META\" item.");
        final ItemStack placeholderItem = LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "...", new String[0]);
        this.lootInventory.setItem(12, hiddenItem.clone());
        this.lootInventory.setItem(13, hiddenItem.clone());
        this.lootInventory.setItem(14, hiddenItem.clone());
        this.lootInventory.setItem(21, hiddenItem.clone());
        this.lootInventory.setItem(22, hiddenItem.clone());
        this.lootInventory.setItem(23, hiddenItem.clone());
        this.lootInventory.setItem(30, hiddenItem.clone());
        this.lootInventory.setItem(31, hiddenItem.clone());
        this.lootInventory.setItem(32, hiddenItem.clone());
        this.lootInventory.setItem(49, lockedItem.clone());
        for (int i = 0; i < this.lootInventory.getSize(); ++i) {
            if (this.lootInventory.getItem(i) == null) {
                this.lootInventory.setItem(i, placeholderItem);
            }
        }
        this.phase = CosmicCratePhase.REDEEMING_REWARDS;
        this.player.openInventory(this.lootInventory);
        this.player.playSound(this.player.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
    }
    
    public boolean hasSlotAnimation(final int slot) {
        for (final SlotAnimationTask sat : this.animationTask) {
            if (sat.slot == slot) {
                return true;
            }
        }
        return false;
    }
    
    public void runSlotAnimation(final int slot) {
        final SlotAnimationTask sat = new SlotAnimationTask(this, slot);
        this.animationTask.add(sat);
        sat.runTaskTimer((Plugin)CosmicCrate.get(), 1L, 1L);
    }
    
    public void endSlotAnimation(final int slot) {
        for (final SlotAnimationTask sat : this.animationTask) {
            if (sat.slot == slot) {
                Bukkit.getScheduler().cancelTask(sat.getTaskId());
                this.animationTask.remove(sat);
            }
        }
        if (this.displayRewards.size() == 0) {
            this.phase = CosmicCratePhase.META_ANIMATION;
            this.runMetaUnlockAnimation();
        }
    }
    
    public void endAllAnimations() {
        for (final SlotAnimationTask sat : this.animationTask) {
            Bukkit.getScheduler().cancelTask(sat.getTaskId());
        }
        this.animationTask.clear();
        this.endMetaAnimation();
    }
    
    public void runMetaUnlockAnimation() {
        (this.metaAnimation = new MetaAnimationTask(this, this.crateVersion)).runTaskTimer((Plugin)CosmicCrate.get(), 4L, 4L);
    }
    
    public void endMetaAnimation() {
        if (this.metaAnimation != null) {
            this.metaAnimation.cancel();
        }
    }
    
    public void conclude() {
        this.rewards.add(this.metaItem);
        if (this.rewards.size() > 0) {
            for (ItemStack i : this.rewards) {
                if (i == null) {
                    Bukkit.getLogger().info("[CosmicCrate] NULL item in rewards list for player: " + this.player.getName() + " opening: " + this.crateVersion.toString());
                }
                else {
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        if (ItemID.hasItemID(i)) {
                            i = ItemID.removeItemID(i);
                        }
                        if (ItemID.needsItemID(i) && i.getAmount() < 16) {
                            if (i.getAmount() > 1) {
                                boolean dropped = false;
                                for (int am = 0; am < i.getAmount(); ++am) {
                                    ItemStack clone = i.clone();
                                    clone.setAmount(1);
                                    clone = ItemID.assignItemID((InventoryClickEvent)null, clone);
                                    if (!PlayerUtil.giveOrDropProtectedItem(this.player, clone, -1)) {
                                        dropped = true;
                                    }
                                }
                                if (dropped) {
                                    this.player.sendRawMessage(CC.Red + "Your inventory was full! Leftover loot has been " + CC.RedU + "dropped" + CC.Red + " as a protected item at your feet.");
                                }
                                Bukkit.getLogger().info("Giving " + this.player.getName() + " " + i.getAmount() + "x " + i + " due to requiring IIDs! Dropped on ground: " + dropped);
                                continue;
                            }
                            i = ItemID.assignItemID((InventoryClickEvent)null, i);
                        }
                    }
                    PlayerUtil.giveOrDropProtectedItem(this.player, i, -1);
                }
            }
            this.rewards.clear();
        }
        this.endAllAnimations();
        this.phase = CosmicCratePhase.DONE;
        this.player.removeMetadata("ccrate_loot_selector", (Plugin)CosmicCrate.get());
        if (this.crateVersion == Version.DECEMBER) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.GREEN);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JANURARY) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.YELLOW);
                        CCAPI.launchFirework(l, Color.MAROON);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.FEBRUARY) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.MARCH) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.LIME);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.APRIL) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.YELLOW);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JULY) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.WHITE);
                        CCAPI.launchFirework(l, Color.BLUE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.AUGUST) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.AQUA);
                        CCAPI.launchFirework(l, Color.TEAL);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.SCHOOL) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.WHITE);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.OCTOBER) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HALLOWEEN) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.RED);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.NOVEMBER) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.FUCHSIA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.BLACK_FRIDAY) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.DECEMBER_2016) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            ParticleEffect.playEffect(ParticleEffect.SNOWBALL_POOF, l, 0.44f, 60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HOLIDAY) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.YELLOW);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            ParticleEffect.playEffect(ParticleEffect.SNOWBALL_POOF, l, 0.44f, 60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JANUARY_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.AQUA);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, l, 0.44f, 60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.VALENTINE) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            ParticleEffect.playEffect(ParticleEffect.HEART, l, 0.44f, 60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.MARCH_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.APRIL_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.FLAME, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.MAY_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.LIME);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.FLAME, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.JUNE_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.FUCHSIA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.FLAME, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.JULY_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.WHITE);
                        CCAPI.launchFirework(l, Color.BLUE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.FLAME, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.AUGUST_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.SCHOOL_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.YELLOW);
                        CCAPI.launchFirework(l, Color.GRAY);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.SEPTEMBER_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.OCTOBER_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.YELLOW);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HALLOWEEN_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.MAROON);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.4f);
        }
        if (this.crateVersion == Version.NOVEMBER_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l, 0.44f, 100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.4f);
        }
        if (this.crateVersion == Version.THANKSGIVING_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.MAROON);
                        CCAPI.launchFirework(l, Color.BLACK);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.EXPLODE, l, 0.44f, 75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.DECEMBER_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, l, 0.44f, 75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HOLIDAY_2017) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.GREEN);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 50);
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, l, 0.44f, 75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JANUARY_2018) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.AQUA);
                        CCAPI.launchFirework(l, Color.NAVY);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 50);
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, l, 0.44f, 75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.FEBUARY_2018) {
            final Location l = this.player.getEyeLocation();
            for (int j = 0; j < 8; ++j) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.FUCHSIA);
                    }
                }, (long)(20 * j));
            }
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.44f, 50);
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, l, 0.44f, 75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion.getCrateData() != null) {
            this.crateVersion.getCrateData().playConclusion(this.player);
        }
    }
    
    public enum CosmicCratePhase
    {
        GENERATING, 
        REDEEMING_REWARDS, 
        META_ANIMATION, 
        UNLOCKING_META, 
        LOOTING, 
        DONE;
    }
    
    public class MetaAnimationTask extends BukkitRunnable
    {
        CosmicCrateLootSelector cls;
        Version crateVersion;
        ItemStack metaItem;
        int iterations;
        int intervals;
        
        MetaAnimationTask(final CosmicCrateLootSelector cls, final Version v) {
            this.iterations = 0;
            this.intervals = 0;
            this.cls = cls;
            this.crateVersion = v;
            this.metaItem = LocalItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "???", ChatColor.GRAY + "Click to redeem an item from", ChatColor.GRAY + "your " + ChatColor.UNDERLINE + this.crateVersion.name() + ChatColor.GRAY + " Cosmic Crate!");
        }
        
        public void run() {
            if (this.intervals == 6) {
                this.cls.lootInventory.setItem(49, this.metaItem);
                boolean color = false;
                for (int i = 0; i < this.cls.lootInventory.getSize(); ++i) {
                    final ItemStack is = this.cls.lootInventory.getItem(i);
                    if (is != null && is.getType() == Material.STAINED_GLASS_PANE) {
                        if (this.crateVersion == Version.DECEMBER) {
                            if (color) {
                                is.setDurability((short)5);
                                color = false;
                            }
                            else {
                                is.setDurability((short)14);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.JANURARY) {
                            if (color) {
                                is.setDurability((short)4);
                                color = false;
                            }
                            else {
                                is.setDurability((short)1);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.FEBRUARY) {
                            if (color) {
                                is.setDurability((short)6);
                                color = false;
                            }
                            else {
                                is.setDurability((short)0);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.MARCH) {
                            if (color) {
                                is.setDurability((short)5);
                                color = false;
                            }
                            else {
                                is.setDurability((short)13);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.APRIL) {
                            if (color) {
                                is.setDurability((short)2);
                                color = false;
                            }
                            else {
                                is.setDurability((short)4);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.JULY) {
                            if (color) {
                                is.setDurability((short)11);
                                color = false;
                            }
                            else {
                                is.setDurability((short)14);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.AUGUST) {
                            if (color) {
                                is.setDurability((short)3);
                                color = false;
                            }
                            else {
                                is.setDurability((short)9);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.SCHOOL) {
                            if (color) {
                                is.setDurability((short)8);
                                color = false;
                            }
                            else {
                                is.setDurability((short)1);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.OCTOBER) {
                            if (color) {
                                is.setDurability((short)15);
                                color = false;
                            }
                            else {
                                is.setDurability((short)14);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.HALLOWEEN) {
                            if (color) {
                                is.setDurability((short)1);
                                color = false;
                            }
                            else {
                                is.setDurability((short)14);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.NOVEMBER) {
                            if (color) {
                                is.setDurability((short)2);
                                color = false;
                            }
                            else {
                                is.setDurability((short)10);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.BLACK_FRIDAY) {
                            if (color) {
                                is.setDurability((short)15);
                                color = false;
                            }
                            else {
                                is.setDurability((short)8);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.DECEMBER_2016) {
                            if (color) {
                                is.setDurability((short)11);
                                color = false;
                            }
                            else {
                                is.setDurability((short)3);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.HOLIDAY) {
                            if (color) {
                                is.setDurability((short)5);
                                color = false;
                            }
                            else {
                                is.setDurability((short)14);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.JANUARY_2017) {
                            if (color) {
                                is.setDurability((short)9);
                                color = false;
                            }
                            else {
                                is.setDurability((short)0);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.VALENTINE) {
                            if (color) {
                                is.setDurability((short)14);
                                color = false;
                            }
                            else {
                                is.setDurability((short)15);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.MARCH_2017) {
                            if (color) {
                                is.setDurability((short)3);
                                color = false;
                            }
                            else {
                                is.setDurability((short)5);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.APRIL_2017) {
                            if (color) {
                                is.setDurability((short)10);
                                color = false;
                            }
                            else {
                                is.setDurability((short)1);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.MAY_2017) {
                            if (color) {
                                is.setDurability((short)5);
                                color = false;
                            }
                            else {
                                is.setDurability((short)13);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.JUNE_2017) {
                            if (color) {
                                is.setDurability((short)14);
                                color = false;
                            }
                            else {
                                is.setDurability((short)2);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.JULY_2017) {
                            if (color) {
                                is.setDurability((short)14);
                                color = false;
                            }
                            else {
                                is.setDurability((short)11);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.AUGUST_2017) {
                            if (color) {
                                is.setDurability((short)9);
                                color = false;
                            }
                            else {
                                is.setDurability((short)11);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.SCHOOL_2017) {
                            if (color) {
                                is.setDurability((short)1);
                                color = false;
                            }
                            else {
                                is.setDurability((short)8);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.SEPTEMBER_2017) {
                            if (color) {
                                is.setDurability((short)11);
                                color = false;
                            }
                            else {
                                is.setDurability((short)9);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.OCTOBER_2017) {
                            if (color) {
                                is.setDurability((short)1);
                                color = false;
                            }
                            else {
                                is.setDurability((short)7);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.HALLOWEEN_2017) {
                            if (color) {
                                is.setDurability((short)14);
                                color = false;
                            }
                            else {
                                is.setDurability((short)1);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.NOVEMBER_2017) {
                            if (color) {
                                is.setDurability((short)0);
                                color = false;
                            }
                            else {
                                is.setDurability((short)10);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.THANKSGIVING_2017) {
                            if (color) {
                                is.setDurability((short)1);
                                color = false;
                            }
                            else {
                                is.setDurability((short)4);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.DECEMBER_2017) {
                            if (color) {
                                is.setDurability((short)3);
                                color = false;
                            }
                            else {
                                is.setDurability((short)11);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.HOLIDAY_2017) {
                            if (color) {
                                is.setDurability((short)5);
                                color = false;
                            }
                            else {
                                is.setDurability((short)14);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.JANUARY_2018) {
                            if (color) {
                                is.setDurability((short)3);
                                color = false;
                            }
                            else {
                                is.setDurability((short)9);
                                color = true;
                            }
                        }
                        else if (this.crateVersion == Version.FEBUARY_2018) {
                            if (color) {
                                is.setDurability((short)10);
                                color = false;
                            }
                            else {
                                is.setDurability((short)2);
                                color = true;
                            }
                        }
                        else if (this.crateVersion.getCrateData() != null) {
                            if (color) {
                                is.setDurability(this.crateVersion.getCrateData().getColorPane());
                                color = false;
                            }
                            else {
                                is.setDurability(this.crateVersion.getCrateData().getNonColorPane());
                                color = true;
                            }
                        }
                        this.cls.lootInventory.setItem(i, is);
                    }
                }
                this.cls.phase = CosmicCratePhase.UNLOCKING_META;
                this.cls.endMetaAnimation();
                return;
            }
            if (this.intervals < 5) {
                if (this.iterations++ == 5) {
                    ++this.intervals;
                    this.iterations = 1;
                }
                final ItemStack panel = LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, this.getAnimationGlassColor(this.intervals), ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "UNLOCKING...", new String[0]);
                for (int x = 0; x < 6; ++x) {
                    final int leftSlot = this.iterations - 1 + x * 9;
                    final int rightSlot = 9 - this.iterations + x * 9;
                    final ItemStack left = this.cls.lootInventory.getItem(leftSlot);
                    if (left != null && left.getType() == Material.STAINED_GLASS_PANE && leftSlot != 49) {
                        this.cls.lootInventory.setItem(leftSlot, panel);
                    }
                    final ItemStack right = this.cls.lootInventory.getItem(rightSlot);
                    if (right != null && right.getType() == Material.STAINED_GLASS_PANE && rightSlot != 49) {
                        this.cls.lootInventory.setItem(rightSlot, panel);
                    }
                }
                this.cls.player.playSound(this.cls.player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.3f);
            }
            else if (this.intervals >= 5) {
                if (this.iterations++ == 5) {
                    ++this.intervals;
                    this.iterations = 1;
                }
            }
        }
        
        private short getAnimationGlassColor(final int interval) {
            if (interval == 0) {
                return 0;
            }
            if (interval == 1) {
                return 5;
            }
            if (interval == 2) {
                return 3;
            }
            if (interval == 3) {
                return 4;
            }
            if (interval >= 4) {
                return 1;
            }
            return 15;
        }
    }
    
    public class SlotAnimationTask extends BukkitRunnable
    {
        CosmicCrateLootSelector cls;
        int slot;
        int iterations;
        
        SlotAnimationTask(final CosmicCrateLootSelector cls, final int slot) {
            this.slot = 0;
            this.iterations = 0;
            this.cls = cls;
            this.slot = slot;
        }
        
        public void run() {
            ++this.iterations;
            if (this.iterations >= 120) {
                for (final int x : this.getRowSlots(this.slot)) {
                    if (this.cls.lootInventory.getItem(x).getType() == Material.STAINED_GLASS_PANE && x != 49) {
                        this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
                    }
                }
                for (final int x : this.getColumnSlots(this.slot)) {
                    if (this.cls.lootInventory.getItem(x).getType() == Material.STAINED_GLASS_PANE && x != 49) {
                        this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
                    }
                }
                this.cls.lootInventory.setItem(this.slot, this.getRandomLegitItem());
                this.cls.player.playSound(this.cls.player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.2f);
                this.cls.endSlotAnimation(this.slot);
                return;
            }
            if (this.iterations >= 20 && this.iterations % 2 != 0) {
                return;
            }
            if (this.iterations >= 40 && this.iterations % 3 != 0) {
                return;
            }
            for (final int x : this.getRowSlots(this.slot)) {
                if (this.cls.lootInventory.getItem(x).getType() == Material.STAINED_GLASS_PANE && x != 49) {
                    this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)new Random().nextInt(7), ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
                }
            }
            for (final int x : this.getColumnSlots(this.slot)) {
                if (this.cls.lootInventory.getItem(x).getType() == Material.STAINED_GLASS_PANE && x != 49) {
                    this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)new Random().nextInt(7), ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
                }
            }
            this.cls.lootInventory.setItem(this.slot, this.getRandomFluffItem());
            this.cls.player.playSound(this.cls.player.getLocation(), Sound.CLICK, 0.4f, 0.2f);
            if (this.iterations <= 40) {
                this.cls.player.playSound(this.cls.player.getLocation(), Sound.ORB_PICKUP, 0.3f, 0.3f);
            }
        }
        
        private ItemStack getRandomFluffItem() {
            return this.cls.rewards.get(new Random().nextInt(this.cls.rewards.size()));
        }
        
        private ItemStack getRandomLegitItem() {
            if (this.cls.displayRewards.size() <= 0) {
                Bukkit.getLogger().info("[CosmicCrate] EMPTY displayRewards list! DEBUG: crateVersion:" + this.cls.crateVersion + ", player:" + this.cls.player.getName() + ", inventory:" + this.cls.lootInventory);
                return new ItemStack(Material.AIR);
            }
            final ItemStack i = this.cls.displayRewards.get(new Random().nextInt(this.cls.displayRewards.size()));
            this.cls.displayRewards.remove(i);
            return i;
        }
        
        private List<Integer> getRowSlots(int slot) {
            final List<Integer> slots = new ArrayList<Integer>();
            final int x = slot;
            while (slot % 9 != 0 && slot > 0) {
                slots.add(slot--);
            }
            slots.add(slot);
            slot = x;
            while (slot % 9 != 0 && slot < 54) {
                slots.add(slot++);
            }
            return slots;
        }
        
        private List<Integer> getColumnSlots(int slot) {
            final List<Integer> slots = new ArrayList<Integer>();
            final int x = slot;
            while (slot - 9 > 0) {
                final List<Integer> list = slots;
                slot -= 9;
                list.add(slot);
            }
            slot = x;
            while (slot + 9 < 54) {
                final List<Integer> list2 = slots;
                slot += 9;
                list2.add(slot);
            }
            return slots;
        }
    }
}
