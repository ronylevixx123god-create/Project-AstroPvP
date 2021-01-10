/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Bosses
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.libs.KitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.RankItems
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters
 *  me.ifamasssxd.cosmicfactionboosters.enums.BoosterType
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  me.ifamasssxd.killtracker.listeners.Tracker
 *  org.apache.commons.lang.WordUtils
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicCrate;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.CosmicBossEggs.boss.type.Bosses;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.RankItems;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.ifamasssxd.killtracker.listeners.Tracker;
import org.apache.commons.lang.WordUtils;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CosmicCrateLootSelector {
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

    public CosmicCrateLootSelector(Player p, Version v) {
        double x;
        this.player = p;
        this.crateVersion = v;
        this.animationTask = new CopyOnWriteArrayList();
        this.selectedSlots = new ArrayList<Integer>();
        this.phase = CosmicCratePhase.GENERATING;
        this.rewards = CCAPI.getCosmicCrateLoot(p, v);
        this.displayRewards = new ArrayList<ItemStack>(this.rewards);
        this.crateInventoryName = "Cosmic Crate: " + WordUtils.capitalize((String)v.name());
        if (this.crateVersion == Version.DECEMBER) {
            this.metaItem = LocalItemUtils.setAmount(CCAPI.getExplosiveSnowball(), 10);
            if (Math.random() < 0.05 || p.getName().equals("Vaquxine")) {
                this.metaItem = KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            }
        }
        if (this.crateVersion == Version.JANURARY) {
            if (Math.random() < 0.025) {
                this.metaItem = KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            } else {
                x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = KillTrackerAPI.buildTracker((Tracker)Tracker.Legendary);
                }
                if (x < 0.8) {
                    this.metaItem = KillTrackerAPI.buildTracker((Tracker)Tracker.Ultimate);
                }
                if (x < 0.5) {
                    this.metaItem = KillTrackerAPI.buildTracker((Tracker)Tracker.Elite);
                }
                if (Math.random() < 0.05 || p.getName().equals("Vaquxine")) {
                    this.metaItem = TieredEnchantments.getMysteryEnchantmentBook((int)6);
                }
            }
        }
        if (this.crateVersion == Version.FEBRUARY) {
            if (Math.random() < 0.025) {
                this.metaItem = KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            } else if (Math.random() < 0.05) {
                x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MUSHROOM_COW);
                }
                if (x < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MAGMA_CUBE);
                }
                if (x < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.GHAST);
                }
            } else {
                this.metaItem = LocalItemUtils.setAmount(CCAPI.getExplosiveSnowball(), 10);
            }
        }
        if (this.crateVersion == Version.MARCH) {
            if (Math.random() < 0.025) {
                this.metaItem = KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]);
            } else if (Math.random() < 0.05) {
                x = Math.random();
                if (x <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MUSHROOM_COW);
                }
                if (x < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MAGMA_CUBE);
                }
                if (x < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.GHAST);
                }
            } else {
                this.metaItem = LocalItemUtils.setAmount(ItemUtils.getCustomCreeperEgg((CreeperTypes)CreeperTypes.Lucky), new Random().nextInt(4) + 4);
            }
        }
        if (this.crateVersion == Version.APRIL) {
            if (Math.random() < 0.025) {
                this.metaItem = ItemUtils.getEnvoySummoner();
            } else if (Math.random() < 0.05) {
                int donorTier = new Random().nextInt(5) + 1;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                if (donorTier >= 4) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = RankItems.getRankItem((String)RankItems.getRank((int)donorTier));
            } else {
                x = Math.random();
                this.metaItem = x < 0.1 ? ItemUtils.getBossEgg((Bosses)Bosses.HEADLESS_KING) : (x < 0.4 ? ItemUtils.getBossEgg((Bosses)Bosses.WITHER) : ItemUtils.getBossEgg((Bosses)Bosses.PIG_ZOMBIE));
            }
        }
        if (this.crateVersion == Version.JULY) {
            if (Math.random() < 0.025) {
                this.metaItem = ItemUtils.getEnvoySummoner();
            } else if (Math.random() < 0.05) {
                int donorTier = new Random().nextInt(5) + 1;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                if (donorTier >= 4) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            } else {
                double x2 = Math.random();
                this.metaItem = x2 <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x2 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x2 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x2 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))));
            }
        }
        if (this.crateVersion == Version.AUGUST) {
            double x3;
            this.metaItem = Math.random() < 0.025 ? ItemUtils.getMysterySoulEnchantmentBook() : (Math.random() < 0.1 ? CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD) : ((x3 = Math.random()) <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x3 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x3 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x3 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))))));
        }
        if (this.crateVersion == Version.SCHOOL) {
            if (Math.random() < 0.05) {
                this.metaItem = ItemUtils.getMysterySoulEnchantmentBook();
            } else if (Math.random() < 0.7) {
                double x4 = Math.random();
                if (x4 <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MUSHROOM_COW);
                }
                if (x4 < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MAGMA_CUBE);
                }
                if (x4 < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.GHAST);
                }
            } else {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD);
            }
        }
        if (this.crateVersion == Version.OCTOBER) {
            if (Math.random() < 0.05) {
                this.metaItem = CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 1800, 4.0);
            } else if (Math.random() < 0.75) {
                double x5 = Math.random();
                if (x5 <= 1.0) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MUSHROOM_COW);
                }
                if (x5 < 0.8) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.MAGMA_CUBE);
                }
                if (x5 < 0.5) {
                    this.metaItem = ItemUtils.getMobSpawner((EntityType)EntityType.GHAST);
                }
            } else {
                double x6;
                this.metaItem = Math.random() < 0.25 ? CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD) : ((x6 = Math.random()) <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x6 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x6 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x6 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER)))));
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
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            } else {
                double x7;
                this.metaItem = Math.random() < 0.75 ? ItemUtils.setAmount((ItemStack)ItemUtils.getMysterySoulEnchantmentBook(), (int)2) : (Math.random() < 0.25 ? CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 3960, 6.6) : ((x7 = Math.random()) <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x7 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x7 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x7 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))))));
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
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            } else {
                double x8;
                this.metaItem = Math.random() < 0.65 ? CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 1800, 8.0) : (Math.random() < 0.25 ? CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD) : ((x8 = Math.random()) <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x8 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x8 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x8 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))))));
            }
        }
        if (this.crateVersion == Version.BLACK_FRIDAY) {
            double x9;
            this.metaItem = Math.random() < 0.15 ? ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)100, (int)10, (OrbType)OrbType.ARMOR), (int)3) : (Math.random() < 0.1 ? CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD) : ((x9 = Math.random()) <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x9 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x9 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x9 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))))));
        }
        if (this.crateVersion == Version.DECEMBER_2016) {
            if (Math.random() < 0.15) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD);
            } else if (Math.random() < 0.1) {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            } else {
                this.metaItem = ItemUtils.setAmount((ItemStack)ItemUtils.getMysterySoulEnchantmentBook(), (int)2);
            }
        }
        if (this.crateVersion == Version.HOLIDAY) {
            if (Math.random() < 0.75) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD);
            } else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            } else {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(2);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            }
        }
        if (this.crateVersion == Version.JANUARY_2017) {
            if (Math.random() < 0.7) {
                double x10 = Math.random();
                this.metaItem = x10 <= 0.01 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x10 <= 0.04 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x10 < 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x10 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))));
                this.metaItem = ItemUtils.setAmount((ItemStack)this.metaItem, (int)2);
            } else {
                this.metaItem = Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : EvolutionKitItems.getKitItem((EvolutionKits)EvolutionKits.PHOENIX);
            }
        }
        if (this.crateVersion == Version.VALENTINE) {
            if (Math.random() < 0.7) {
                this.metaItem = CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD);
            } else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            } else {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(3);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            }
        }
        if (this.crateVersion == Version.MARCH_2017) {
            if (Math.random() < 0.7) {
                double x11 = Math.random();
                this.metaItem = x11 <= 0.02 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x11 <= 0.08 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x11 < 0.2 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x11 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))));
                this.metaItem = ItemUtils.setAmount((ItemStack)this.metaItem, (int)4);
            } else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            } else {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(3);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            }
        }
        if (this.crateVersion == Version.APRIL_2017) {
            this.metaItem = Math.random() < 0.7 ? CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD) : (Math.random() < 0.1 ? CCAPI.getCosmicCrate(Version.APRIL, "April 2017 Cosmic Crate") : EvolutionKitItems.getKitItem((EvolutionKits)EvolutionKits.ALCHEMIST));
        }
        if (this.crateVersion == Version.MAY_2017) {
            this.metaItem = Math.random() < 0.7 ? CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD) : (Math.random() < 0.1 ? CosmicConquestAPI.createFactionToken((int)(50 + new Random().nextInt(51))) : KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
        }
        if (this.crateVersion == Version.JUNE_2017) {
            if (Math.random() < 0.75) {
                double x12 = Math.random();
                this.metaItem = x12 <= 0.02 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x12 <= 0.08 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x12 < 0.2 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x12 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))));
                this.metaItem = ItemUtils.setAmount((ItemStack)this.metaItem, (int)(1 + new Random().nextInt(4)));
            } else {
                this.metaItem = Math.random() < 0.2 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CosmicConquestAPI.createFactionToken((int)(50 + new Random().nextInt(51)));
            }
        }
        if (this.crateVersion == Version.JULY_2017) {
            this.metaItem = Math.random() < 0.75 ? CCAPI.getCosmicCrate(Version.JULY, "July 2017 Cosmic Crate") : (Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : EvolutionKitItems.getKitItem((EvolutionKits)EvolutionKits.OGRE));
        }
        if (this.crateVersion == Version.AUGUST_2017) {
            this.metaItem = Math.random() < 0.65 ? CCAPI.getCosmicCrate(Version.AUGUST, "August 2017 Cosmic Crate") : (Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CCAPI.getMysteryCrate());
        }
        if (this.crateVersion == Version.SCHOOL_2017) {
            this.metaItem = Math.random() < 0.65 ? ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD), (int)(2 + new Random().nextInt(2)))) : (Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CCAPI.getCosmicCrate(Version.SCHOOL, "Back To School 2017 Cosmic Crate"));
        }
        if (this.crateVersion == Version.SEPTEMBER_2017) {
            if (Math.random() < 0.65) {
                this.metaItem = CCAPI.getMysteryCrate();
            } else if (Math.random() < 0.1) {
                this.metaItem = HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            } else {
                double x13 = Math.random();
                this.metaItem = x13 <= 0.02 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x13 <= 0.08 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x13 < 0.2 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (x13 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))));
                this.metaItem = ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)this.metaItem, (int)(1 + new Random().nextInt(2))));
            }
        }
        if (this.crateVersion == Version.OCTOBER_2017) {
            this.metaItem = Math.random() < 0.6 ? ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)ItemUtils.getNewBossEgg((BossType)BossType.SOUL_REAPER), (int)(1 + new Random().nextInt(2)))) : (Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CCAPI.getCosmicCrate(Version.OCTOBER, "October 2017 Cosmic Crate"));
        }
        if (this.crateVersion == Version.HALLOWEEN_2017) {
            if (Math.random() < 0.6) {
                double x14 = Math.random();
                this.metaItem = x14 <= 0.02 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (x14 <= 0.08 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (x14 < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER)));
                this.metaItem = ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)this.metaItem, (int)(1 + new Random().nextInt(2))));
            } else {
                this.metaItem = Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CCAPI.getCosmicCrate(Version.HALLOWEEN, "Halloween 2017 Cosmic Crate");
            }
        }
        if (this.crateVersion == Version.NOVEMBER_2017) {
            this.metaItem = Math.random() < 0.6 ? ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD), (int)(2 + new Random().nextInt(2)))) : (Math.random() < 0.1 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CCAPI.getCosmicCrate(Version.NOVEMBER, "November 2017 Cosmic Crate"));
        }
        if (this.crateVersion == Version.THANKSGIVING_2017) {
            if (Math.random() < 0.65) {
                int donorTier = new Random().nextInt(3) + 3;
                if (donorTier >= 5) {
                    donorTier -= new Random().nextInt(3);
                }
                this.metaItem = CosmicRankQuestsAPI.getRankQuestItem((int)donorTier);
            } else {
                this.metaItem = Math.random() < 0.05 ? EnchantmentBook.getEnchantmentBook((CustomEnchantment)EnchantmentAPI.getEnchantment((String)"Godly Overload"), (int)(1 + (Math.random() < 0.25 ? 1 : 0)), (int)(10 + new Random().nextInt(40)), (int)(50 + new Random().nextInt(50))) : HeroicRankCrystalAPI.getHeroicRankCrystalItem();
            }
        }
        if (this.crateVersion == Version.DECEMBER_2017) {
            this.metaItem = Math.random() < 0.05 ? ItemID.removeItemID((ItemStack)DungeonsAPI.get().getDungeon(0).getDungeonPortal()) : (Math.random() < 0.05 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : CCAPI.getCosmicCrate(Version.DECEMBER_2016, "December 2017 Cosmic Crate"));
        }
        if (this.crateVersion == Version.HOLIDAY_2017) {
            this.metaItem = Math.random() < 0.15 ? DungeonsRewardsAPI.getDungeonLootBag((Player)p, (Dungeon)DungeonsAPI.get().getDungeon(0)) : (Math.random() < 0.05 ? ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.MOTHER_OF_YIJKI)[new Random().nextInt(4)]) : CCAPI.getCosmicCrate(Version.HOLIDAY, "Holiday 2017 Cosmic Crate"));
        }
        if (this.crateVersion == Version.JANUARY_2018) {
            this.metaItem = Math.random() < 0.2 ? CommandFallenHero.getHeroicFallenHeroBundle() : (Math.random() < 0.05 ? CCAPI.getCosmicCrate(Version.HOLIDAY_2017, "Holiday 2017 Cosmic Crate") : DungeonsRewardsAPI.getDungeonLootBag((Player)p, (Dungeon)DungeonsAPI.get().getDungeon(0)));
        }
        if (this.crateVersion == Version.FEBUARY_2018) {
            this.metaItem = Math.random() < 0.2 ? CosmicRankQuestsAPI.getRankQuestItem((int)5) : (Math.random() < 0.05 ? HeroicRankCrystalAPI.getHeroicRankCrystalItem() : DungeonsRewardsAPI.getDungeonLootBag((Player)p, (Dungeon)DungeonsAPI.get().getDungeon(0)));
        }
        if (this.crateVersion.getCrateData() != null) {
            this.metaItem = this.crateVersion.getCrateData().getRandomMetaItem(p);
        }
    }

    public void open() {
        this.lootInventory = Bukkit.createInventory(null, (int)54, (String)this.crateInventoryName);
        ItemStack hiddenItem = LocalItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "???", (Object)ChatColor.GRAY + "Click to redeem an item from", (Object)ChatColor.GRAY + "your " + (Object)ChatColor.UNDERLINE + this.crateVersion.name() + (Object)ChatColor.GRAY + " Cosmic Crate!");
        ItemStack lockedItem = LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)14, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "???", (Object)ChatColor.GRAY + "Redeem all your other rewards to", (Object)ChatColor.GRAY + "unlock this special \"META\" item.");
        ItemStack placeholderItem = LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "...", new String[0]);
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
            if (this.lootInventory.getItem(i) != null) continue;
            this.lootInventory.setItem(i, placeholderItem);
        }
        this.phase = CosmicCratePhase.REDEEMING_REWARDS;
        this.player.openInventory(this.lootInventory);
        this.player.playSound(this.player.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
    }

    public boolean hasSlotAnimation(int slot) {
        for (SlotAnimationTask sat : this.animationTask) {
            if (sat.slot != slot) continue;
            return true;
        }
        return false;
    }

    public void runSlotAnimation(int slot) {
        SlotAnimationTask sat = new SlotAnimationTask(this, slot);
        this.animationTask.add(sat);
        sat.runTaskTimer((Plugin)CosmicCrate.get(), 1L, 1L);
    }

    public void endSlotAnimation(int slot) {
        for (SlotAnimationTask sat : this.animationTask) {
            if (sat.slot != slot) continue;
            Bukkit.getScheduler().cancelTask(sat.getTaskId());
            this.animationTask.remove((Object)sat);
        }
        if (this.displayRewards.size() == 0) {
            this.phase = CosmicCratePhase.META_ANIMATION;
            this.runMetaUnlockAnimation();
        }
    }

    public void endAllAnimations() {
        for (SlotAnimationTask sat : this.animationTask) {
            Bukkit.getScheduler().cancelTask(sat.getTaskId());
        }
        this.animationTask.clear();
        this.endMetaAnimation();
    }

    public void runMetaUnlockAnimation() {
        this.metaAnimation = new MetaAnimationTask(this, this.crateVersion);
        this.metaAnimation.runTaskTimer((Plugin)CosmicCrate.get(), 4L, 4L);
    }

    public void endMetaAnimation() {
        if (this.metaAnimation != null) {
            this.metaAnimation.cancel();
        }
    }

    public void conclude() {
        Location l;
        this.rewards.add(this.metaItem);
        if (this.rewards.size() > 0) {
            for (ItemStack i : this.rewards) {
                if (i == null) {
                    Bukkit.getLogger().info("[CosmicCrate] NULL item in rewards list for player: " + this.player.getName() + " opening: " + this.crateVersion.toString());
                    continue;
                }
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    if (ItemID.hasItemID((ItemStack)i)) {
                        i = ItemID.removeItemID((ItemStack)i);
                    }
                    if (ItemID.needsItemID((ItemStack)i) && i.getAmount() < 16) {
                        if (i.getAmount() > 1) {
                            boolean dropped = false;
                            for (int am = 0; am < i.getAmount(); ++am) {
                                ItemStack clone = i.clone();
                                clone.setAmount(1);
                                clone = ItemID.assignItemID(null, (ItemStack)clone);
                                if (PlayerUtil.giveOrDropProtectedItem((Player)this.player, (ItemStack)clone, (int)-1)) continue;
                                dropped = true;
                            }
                            if (dropped) {
                                this.player.sendRawMessage(CC.Red + "Your inventory was full! Leftover loot has been " + CC.RedU + "dropped" + CC.Red + " as a protected item at your feet.");
                            }
                            Bukkit.getLogger().info("Giving " + this.player.getName() + " " + i.getAmount() + "x " + (Object)i + " due to requiring IIDs! Dropped on ground: " + dropped);
                            continue;
                        }
                        i = ItemID.assignItemID(null, (ItemStack)i);
                    }
                }
                PlayerUtil.giveOrDropProtectedItem((Player)this.player, (ItemStack)i, (int)-1);
            }
            this.rewards.clear();
        }
        this.endAllAnimations();
        this.phase = CosmicCratePhase.DONE;
        this.player.removeMetadata("ccrate_loot_selector", (Plugin)CosmicCrate.get());
        if (this.crateVersion == Version.DECEMBER) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.GREEN);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JANURARY) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.YELLOW);
                        CCAPI.launchFirework(l, Color.MAROON);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.FEBRUARY) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.MARCH) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.LIME);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.APRIL) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.YELLOW);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JULY) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.WHITE);
                        CCAPI.launchFirework(l, Color.BLUE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.AUGUST) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.AQUA);
                        CCAPI.launchFirework(l, Color.TEAL);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.SCHOOL) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.WHITE);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.OCTOBER) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HALLOWEEN) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.RED);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.NOVEMBER) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.FUCHSIA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.BLACK_FRIDAY) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.DECEMBER_2016) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SNOWBALL_POOF, (Location)l, (float)0.44f, (int)60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HOLIDAY) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.YELLOW);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SNOWBALL_POOF, (Location)l, (float)0.44f, (int)60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JANUARY_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.AQUA);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.VALENTINE) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.HEART, (Location)l, (float)0.44f, (int)60);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.MARCH_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.APRIL_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.ORANGE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FLAME, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.MAY_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.GREEN);
                        CCAPI.launchFirework(l, Color.LIME);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FLAME, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.JUNE_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.FUCHSIA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FLAME, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.JULY_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.WHITE);
                        CCAPI.launchFirework(l, Color.BLUE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FLAME, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.7f);
        }
        if (this.crateVersion == Version.AUGUST_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.SCHOOL_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.YELLOW);
                        CCAPI.launchFirework(l, Color.GRAY);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.SEPTEMBER_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.OCTOBER_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLACK);
                        CCAPI.launchFirework(l, Color.YELLOW);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HALLOWEEN_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.MAROON);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.4f);
        }
        if (this.crateVersion == Version.NOVEMBER_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.WHITE);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)l, (float)0.44f, (int)100);
            this.player.playSound(this.player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.4f);
        }
        if (this.crateVersion == Version.THANKSGIVING_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.MAROON);
                        CCAPI.launchFirework(l, Color.BLACK);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.EXPLODE, (Location)l, (float)0.44f, (int)75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.DECEMBER_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.BLUE);
                        CCAPI.launchFirework(l, Color.AQUA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.HOLIDAY_2017) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.RED);
                        CCAPI.launchFirework(l, Color.GREEN);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)50);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.JANUARY_2018) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.AQUA);
                        CCAPI.launchFirework(l, Color.NAVY);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)50);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion == Version.FEBUARY_2018) {
            l = this.player.getEyeLocation();
            for (int i = 0; i < 8; ++i) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                    @Override
                    public void run() {
                        CCAPI.launchFirework(l, Color.PURPLE);
                        CCAPI.launchFirework(l, Color.FUCHSIA);
                    }
                }, (long)(20 * i));
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.44f, (int)50);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)75);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        }
        if (this.crateVersion.getCrateData() != null) {
            this.crateVersion.getCrateData().playConclusion(this.player);
        }
    }

    public class SlotAnimationTask
    extends BukkitRunnable {
        CosmicCrateLootSelector cls;
        int slot = 0;
        int iterations = 0;

        SlotAnimationTask(CosmicCrateLootSelector cls, int slot) {
            this.cls = cls;
            this.slot = slot;
        }

        public void run() {
            ++this.iterations;
            if (this.iterations >= 120) {
                for (int x : this.getRowSlots(this.slot)) {
                    if (this.cls.lootInventory.getItem(x).getType() != Material.STAINED_GLASS_PANE || x == 49) continue;
                    this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
                }
                for (int x : this.getColumnSlots(this.slot)) {
                    if (this.cls.lootInventory.getItem(x).getType() != Material.STAINED_GLASS_PANE || x == 49) continue;
                    this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
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
            for (int x : this.getRowSlots(this.slot)) {
                if (this.cls.lootInventory.getItem(x).getType() != Material.STAINED_GLASS_PANE || x == 49) continue;
                this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)new Random().nextInt(7), ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
            }
            for (int x : this.getColumnSlots(this.slot)) {
                if (this.cls.lootInventory.getItem(x).getType() != Material.STAINED_GLASS_PANE || x == 49) continue;
                this.cls.lootInventory.setItem(x, LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)new Random().nextInt(7), ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "...", new String[0]));
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
                Bukkit.getLogger().info("[CosmicCrate] EMPTY displayRewards list! DEBUG: crateVersion:" + (Object)((Object)this.cls.crateVersion) + ", player:" + this.cls.player.getName() + ", inventory:" + (Object)this.cls.lootInventory);
                return new ItemStack(Material.AIR);
            }
            ItemStack i = this.cls.displayRewards.get(new Random().nextInt(this.cls.displayRewards.size()));
            this.cls.displayRewards.remove((Object)i);
            return i;
        }

        private List<Integer> getRowSlots(int slot) {
            ArrayList<Integer> slots = new ArrayList<Integer>();
            int x = slot;
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
            ArrayList<Integer> slots = new ArrayList<Integer>();
            int x = slot;
            while (slot - 9 > 0) {
                slots.add(slot -= 9);
            }
            slot = x;
            while (slot + 9 < 54) {
                slots.add(slot += 9);
            }
            return slots;
        }
    }

    public class MetaAnimationTask
    extends BukkitRunnable {
        CosmicCrateLootSelector cls;
        Version crateVersion;
        ItemStack metaItem;
        int iterations = 0;
        int intervals = 0;

        MetaAnimationTask(CosmicCrateLootSelector cls, Version v) {
            this.cls = cls;
            this.crateVersion = v;
            this.metaItem = LocalItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "???", (Object)ChatColor.GRAY + "Click to redeem an item from", (Object)ChatColor.GRAY + "your " + (Object)ChatColor.UNDERLINE + this.crateVersion.name() + (Object)ChatColor.GRAY + " Cosmic Crate!");
        }

        public void run() {
            if (this.intervals == 6) {
                this.cls.lootInventory.setItem(49, this.metaItem);
                boolean color = false;
                for (int i = 0; i < this.cls.lootInventory.getSize(); ++i) {
                    ItemStack is = this.cls.lootInventory.getItem(i);
                    if (is == null || is.getType() != Material.STAINED_GLASS_PANE) continue;
                    if (this.crateVersion == Version.DECEMBER) {
                        if (color) {
                            is.setDurability((short)5);
                            color = false;
                        } else {
                            is.setDurability((short)14);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.JANURARY) {
                        if (color) {
                            is.setDurability((short)4);
                            color = false;
                        } else {
                            is.setDurability((short)1);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.FEBRUARY) {
                        if (color) {
                            is.setDurability((short)6);
                            color = false;
                        } else {
                            is.setDurability((short)0);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.MARCH) {
                        if (color) {
                            is.setDurability((short)5);
                            color = false;
                        } else {
                            is.setDurability((short)13);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.APRIL) {
                        if (color) {
                            is.setDurability((short)2);
                            color = false;
                        } else {
                            is.setDurability((short)4);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.JULY) {
                        if (color) {
                            is.setDurability((short)11);
                            color = false;
                        } else {
                            is.setDurability((short)14);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.AUGUST) {
                        if (color) {
                            is.setDurability((short)3);
                            color = false;
                        } else {
                            is.setDurability((short)9);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.SCHOOL) {
                        if (color) {
                            is.setDurability((short)8);
                            color = false;
                        } else {
                            is.setDurability((short)1);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.OCTOBER) {
                        if (color) {
                            is.setDurability((short)15);
                            color = false;
                        } else {
                            is.setDurability((short)14);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.HALLOWEEN) {
                        if (color) {
                            is.setDurability((short)1);
                            color = false;
                        } else {
                            is.setDurability((short)14);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.NOVEMBER) {
                        if (color) {
                            is.setDurability((short)2);
                            color = false;
                        } else {
                            is.setDurability((short)10);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.BLACK_FRIDAY) {
                        if (color) {
                            is.setDurability((short)15);
                            color = false;
                        } else {
                            is.setDurability((short)8);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.DECEMBER_2016) {
                        if (color) {
                            is.setDurability((short)11);
                            color = false;
                        } else {
                            is.setDurability((short)3);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.HOLIDAY) {
                        if (color) {
                            is.setDurability((short)5);
                            color = false;
                        } else {
                            is.setDurability((short)14);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.JANUARY_2017) {
                        if (color) {
                            is.setDurability((short)9);
                            color = false;
                        } else {
                            is.setDurability((short)0);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.VALENTINE) {
                        if (color) {
                            is.setDurability((short)14);
                            color = false;
                        } else {
                            is.setDurability((short)15);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.MARCH_2017) {
                        if (color) {
                            is.setDurability((short)3);
                            color = false;
                        } else {
                            is.setDurability((short)5);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.APRIL_2017) {
                        if (color) {
                            is.setDurability((short)10);
                            color = false;
                        } else {
                            is.setDurability((short)1);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.MAY_2017) {
                        if (color) {
                            is.setDurability((short)5);
                            color = false;
                        } else {
                            is.setDurability((short)13);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.JUNE_2017) {
                        if (color) {
                            is.setDurability((short)14);
                            color = false;
                        } else {
                            is.setDurability((short)2);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.JULY_2017) {
                        if (color) {
                            is.setDurability((short)14);
                            color = false;
                        } else {
                            is.setDurability((short)11);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.AUGUST_2017) {
                        if (color) {
                            is.setDurability((short)9);
                            color = false;
                        } else {
                            is.setDurability((short)11);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.SCHOOL_2017) {
                        if (color) {
                            is.setDurability((short)1);
                            color = false;
                        } else {
                            is.setDurability((short)8);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.SEPTEMBER_2017) {
                        if (color) {
                            is.setDurability((short)11);
                            color = false;
                        } else {
                            is.setDurability((short)9);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.OCTOBER_2017) {
                        if (color) {
                            is.setDurability((short)1);
                            color = false;
                        } else {
                            is.setDurability((short)7);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.HALLOWEEN_2017) {
                        if (color) {
                            is.setDurability((short)14);
                            color = false;
                        } else {
                            is.setDurability((short)1);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.NOVEMBER_2017) {
                        if (color) {
                            is.setDurability((short)0);
                            color = false;
                        } else {
                            is.setDurability((short)10);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.THANKSGIVING_2017) {
                        if (color) {
                            is.setDurability((short)1);
                            color = false;
                        } else {
                            is.setDurability((short)4);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.DECEMBER_2017) {
                        if (color) {
                            is.setDurability((short)3);
                            color = false;
                        } else {
                            is.setDurability((short)11);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.HOLIDAY_2017) {
                        if (color) {
                            is.setDurability((short)5);
                            color = false;
                        } else {
                            is.setDurability((short)14);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.JANUARY_2018) {
                        if (color) {
                            is.setDurability((short)3);
                            color = false;
                        } else {
                            is.setDurability((short)9);
                            color = true;
                        }
                    } else if (this.crateVersion == Version.FEBUARY_2018) {
                        if (color) {
                            is.setDurability((short)10);
                            color = false;
                        } else {
                            is.setDurability((short)2);
                            color = true;
                        }
                    } else if (this.crateVersion.getCrateData() != null) {
                        if (color) {
                            is.setDurability(this.crateVersion.getCrateData().getColorPane());
                            color = false;
                        } else {
                            is.setDurability(this.crateVersion.getCrateData().getNonColorPane());
                            color = true;
                        }
                    }
                    this.cls.lootInventory.setItem(i, is);
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
                ItemStack panel = LocalItemUtils.buildItem(Material.STAINED_GLASS_PANE, this.getAnimationGlassColor(this.intervals), ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "UNLOCKING...", new String[0]);
                for (int x = 0; x < 6; ++x) {
                    ItemStack right;
                    int leftSlot = this.iterations - 1 + x * 9;
                    int rightSlot = 9 - this.iterations + x * 9;
                    ItemStack left = this.cls.lootInventory.getItem(leftSlot);
                    if (left != null && left.getType() == Material.STAINED_GLASS_PANE && leftSlot != 49) {
                        this.cls.lootInventory.setItem(leftSlot, panel);
                    }
                    if ((right = this.cls.lootInventory.getItem(rightSlot)) == null || right.getType() != Material.STAINED_GLASS_PANE || rightSlot == 49) continue;
                    this.cls.lootInventory.setItem(rightSlot, panel);
                }
                this.cls.player.playSound(this.cls.player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.3f);
            } else if (this.intervals >= 5) {
                if (this.iterations++ == 5) {
                    ++this.intervals;
                    this.iterations = 1;
                }
                return;
            }
        }

        private short getAnimationGlassColor(int interval) {
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

    public static enum CosmicCratePhase {
        GENERATING,
        REDEEMING_REWARDS,
        META_ANIMATION,
        UNLOCKING_META,
        LOOTING,
        DONE;

    }
}

