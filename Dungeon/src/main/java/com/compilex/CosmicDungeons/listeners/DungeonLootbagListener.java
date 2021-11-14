/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.enums.HeroicArmorType
 *  com.compilex.CosmicArmorSets.enums.HeroicWeaponType
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.AbilityArmorSetItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.KitGemBuilder
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.cosmicpvp.cosmicutils.utils.UpgradeUtils
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.ItemLinking
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.RandomizationScroll
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.AbilityArmorSetItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.KitGemBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DungeonLootbagListener
implements Listener {
    /*
     * Enabled aggressive block sorting
     */
    @EventHandler
    public void onPlayerOpenDungeonLootbag(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && DungeonsRewardsAPI.isDungeonLootBag(e.getItem())) {
            ItemStack pointNote;
            DungeonType foundType;
            NBTTagCompoundBuilder data;
            String type;
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            ItemStack is = e.getItem();
            Player p = e.getPlayer();
            DungeonType bagType = DungeonType.ABANDONED_SHIP;
            NBTWrapper wrapper = new NBTWrapper(is);
            if (wrapper.getString("cosmicType").equals("lootbag") && (type = (data = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"))).getString("bagType")) != null && (foundType = DungeonType.getFromName(type)) != null) {
                bagType = foundType;
            }
            if (this.getEmptyInventorySlots(p) < 4 && !CosmicDungeonsPlugin.get().isDevplanet()) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You do not have enough empty inventory slots to open a Dungeon Lootbag.");
                p.sendMessage((Object)ChatColor.RED + "REQ: " + (Object)ChatColor.GRAY + (bagType.equals((Object)DungeonType.ABANDONED_SHIP) ? 4 : 6) + " slots");
                return;
            }
            if (p.getItemInHand().getAmount() > 1) {
                if (!CosmicDungeonsPlugin.get().isDevplanet()) {
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot open stacked Dungeon Lootbags.");
                    return;
                }
                p.sendMessage((Object)ChatColor.RED + "You cannot open stacked bags, but since this is devplanet its allowed..");
                is.setAmount(is.getAmount() - 1);
            } else {
                long itemID;
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID((ItemStack)e.getItem())) != -1L) {
                    ItemID.blacklistIID((long)itemID, (String)"dungeonLootbagOpen");
                }
                p.setItemInHand(null);
            }
            List<ItemStack> loot = this.getCoolDungeonLoot(bagType);
            ItemStack heroicBonusItem = null;
            ItemStack heroicUpgradeBonusItem = null;
            int level = UpgradeUtils.getUpgradeLevel((Player)p, (String)"HEROIC_DUNGEON_LOOTBAG");
            if (level > 0) {
                double chance = level == 1 ? 0.05 : (level == 2 ? 0.08 : (level == 3 ? 0.15 : (chance = level == 4 ? 0.25 : 0.0)));
                if (Math.random() <= chance) {
                    List<ItemStack> potentialLoot = this.getCoolDungeonLoot(bagType);
                    potentialLoot.addAll(this.getOtherDungeonLoot(bagType));
                    heroicUpgradeBonusItem = potentialLoot.get(ThreadLocalRandom.current().nextInt(potentialLoot.size()));
                    loot.add(heroicUpgradeBonusItem);
                }
            }
            if (p.hasPermission("cosmicpvp.heroic")) {
                List<ItemStack> potentialLoot = this.getCoolDungeonLoot(bagType);
                potentialLoot.addAll(this.getOtherDungeonLoot(bagType));
                heroicBonusItem = potentialLoot.get(ThreadLocalRandom.current().nextInt(potentialLoot.size()));
                loot.add(heroicBonusItem);
            }
            if (loot.size() > 0) {
                for (int ind = 0; ind < loot.size(); ++ind) {
                    ItemStack i = loot.get(ind);
                    String itemName = i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : i.getType().name().replace("_", " ");
                    String colorlessName = ChatColor.stripColor((String)itemName).toLowerCase();
                    if (colorlessName.contains("faction point") || colorlessName.contains("faction crystal") || colorlessName.contains("voucher") || colorlessName.contains("title") || colorlessName.contains("tag") || colorlessName.contains("transmog")) continue;
                    if (i.getType() == Material.ENCHANTED_BOOK) {
                        itemName = "Depth Strider Enchantment Book";
                    }
                    JSONMessage message = new JSONMessage("");
                    message.addText(null, (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "(*) " + (Object)ChatColor.GOLD + p.getDisplayName() + (Object)ChatColor.GOLD + " received a(n) ", ChatColor.GOLD);
                    message.addItem(i, itemName != null ? ItemLinking.formatColors((String)itemName).getText() : i.getType().name().replace("_", " ").toLowerCase(), ChatColor.GOLD);
                    message.addText(null, (Object)ChatColor.GOLD + " from a(n) " + bagType.getDungeonName() + " /dungeon Lootbag", ChatColor.GOLD);
                    if (heroicUpgradeBonusItem != null && heroicUpgradeBonusItem.equals((Object)i)) {
                        message.addText(null, " " + CC.Yellow + "(" + CC.YellowB + "SRC" + CC.Yellow + ": Lootbag Scavanger /f upgrade)", ChatColor.YELLOW);
                        heroicUpgradeBonusItem = null;
                    } else if (heroicBonusItem != null && heroicBonusItem.equals((Object)i)) {
                        message.addText(null, " " + CC.Yellow + "(" + CC.YellowB + "SRC" + CC.Yellow + ":", ChatColor.YELLOW);
                        message.addHoverData(CC.Yellow + " Heroic Rank)", CC.YellowB + "Heroic Rank " + CC.Yellow + "(" + CC.YellowB + "Top Rank+" + CC.Yellow + ") " + CC.Gray + "can be\n" + CC.Gray + "obtained from various in game mechanics\n" + CC.Gray + "as well as unlocked at " + CC.GrayU + "buy.cosmicpvp.com" + CC.Gray + "!", "https://buy.cosmicpvp.com", ChatColor.YELLOW);
                        heroicBonusItem = null;
                    }
                    message.addText(null, "!", ChatColor.GOLD);
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        message.sendToPlayer(pl);
                    }
                }
            }
            loot.addAll(this.getOtherDungeonLoot(bagType));
            boolean dropped = false;
            for (ItemStack i : loot) {
                if (PlayerUtil.giveOrDropProtectedItem((Player)p, (ItemStack)i, (int)-1)) continue;
                dropped = true;
            }
            if (dropped) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "Your inventory was full and some Lootbag items have been dropped on the ground!");
            }
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.CLOUD, (Location)p.getLocation().add(0.0, 1.0, 0.0), (float)0.6f, (int)50);
            p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 0.3f);
            Bukkit.getLogger().info("[CosmicDungeons] Giving " + p.getName() + loot.size() + " items: " + Arrays.toString(loot.toArray((Object[])new ItemStack[loot.size()])) + " from lootbag: " + bagType.name());
            p.updateInventory();
        }
    }

    public List<ItemStack> getAllDungeonLoot(DungeonType dungeonType) {
        List<ItemStack> item = this.getCoolDungeonLoot(dungeonType);
        item.addAll(this.getOtherDungeonLoot(dungeonType));
        return item;
    }

    public List<ItemStack> getCoolDungeonLoot(DungeonType dungeonType) {
        boolean isDestroyed = dungeonType.equals((Object)DungeonType.DESTROYED_OUTPOST);
        ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (Math.random() < 0.04) {
        }
        if (isDestroyed) {
            CustomEnchantment enchantment;
            if (Math.random() <= 0.01) {
                loot.add(CosmicArmorSetsAPI.createHeroicPeice((HeroicArmorType)HeroicArmorType.values()[((Random)random).nextInt(HeroicArmorType.values().length)]));
            }
            if (Math.random() <= 0.025) {
                loot.add(ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true));
            } else if (Math.random() <= 0.075) {
                loot.add(ArmorSet.PHANTOM.getRandomPeice(true));
            } else if (Math.random() <= 0.04) {
                loot.add(ArmorSet.MOTHER_OF_YIJKI.getRandomPeice(true));
            }
            if (Math.random() <= 0.01) {
                loot.add(DungeonType.DESTROYED_OUTPOST.getDungeonInstance().getDungeonPortal());
            }
            if (Math.random() <= 0.05 && ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin().isRunning()) {
            } else if (Math.random() <= 0.01 && ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin().isRunning()) {
            }
            if (Math.random() <= 0.1) {
                loot.add(EnchantmentBook.getHeroicBlackscroll((boolean)true));
            }
            if (Math.random() <= 0.33) {
                loot.add(KillTrackerAPI.createSoulShard((int)(((Random)random).nextInt(5000) + 5001)));
            }
            if (Math.random() <= 0.25) {
                loot.add(CosmicConquestAPI.createHeroicToken((int)(2 + ((Random)random).nextInt(3))));
            }
            if (Math.random() <= 0.1) {
                loot.add(EnchantmentOrb.getEnchantmentOrb((int)(50 + ((Random)random).nextInt(51)), (int)13, (OrbType)(((Random)random).nextInt(10) <= 2 ? OrbType.ARMOR : OrbType.WEAPON)));
            }
            if (Math.random() <= 0.05 && (enchantment = EnchantmentAPI.getEnchantment((String)"Infinite Luck")) != null) {
                loot.add(EnchantmentBook.getEnchantmentBook((CustomEnchantment)enchantment, (int)(1 + ((Random)random).nextInt(enchantment.getMaxLevel())), (int)(20 + ((Random)random).nextInt(40)), (int)(40 + ((Random)random).nextInt(40))));
            }
            if (Math.random() <= 0.05) {
                loot.add(CosmicArmorSetsAPI.createHeroicWeapon((HeroicWeaponType)(((Random)random).nextBoolean() ? HeroicWeaponType.SWORD : HeroicWeaponType.AXE)));
            }
            if (Math.random() <= 0.05) {
                loot.add(CosmicDisguiseAPI.createRedeemableDisguise((String)"CHICKEN"));
            }
            if (ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin().isRunning() && Math.random() <= 0.25) {
            }
            if (Math.random() <= 0.1) {
                loot.add(CosmicArmorSetsAPI.createArmorSetUpgrade((ArmorSet)ArmorSet.values()[((Random)random).nextInt(ArmorSet.values().length)], (double)(50 + ((Random)random).nextInt(51))));
            }
            if (Math.random() <= 0.01) {
                this.addLoot((ItemBuilder)new KitGemBuilder(false, true, false, true), loot);
            }
            if (Math.random() <= 0.2) {
                this.addLoot((ItemBuilder)new AbilityArmorSetItemBuilder(true, true), loot);
            }
            if (Math.random() <= 0.025) {
                this.addLoot((ItemBuilder)new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK), loot);
            }
        }
        if (Math.random() < 0.02 && !isDestroyed) {
            int x = new Random().nextInt(4);
            loot.add(CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.MOTHER_OF_YIJKI)[x]);
        } else if (Math.random() < 0.1) {
            int x = new Random().nextInt(4);
            loot.add(CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.PHANTOM)[x]);
        } else if (Math.random() < 0.02) {
            int x = new Random().nextInt(4);
            loot.add(CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.valueOf((String)"SUPREME"))[x]);
        }
        if (Math.random() < 0.01) {
            loot.add(DungeonsAPI.get().getDungeon(0).getDungeonPortal());
        }
        if (Math.random() < 0.03) {
            if (Math.random() < 0.3 && !isDestroyed) {
                loot.add(DungeonsRewardsAPI.getSoulAnvil());
            } else {
            }
        }
        if (Math.random() < 0.15) {
            double x = new Random().nextInt(5);
            if (isDestroyed) {
                if (x == 0.0) {
                    loot.add(ItemUtils.getTitleVoucher((String)"GodKiller"));
                }
                if (x == 1.0) {
                    loot.add(ItemUtils.getTitleVoucher((String)"IAmReality"));
                }
            } else {
                if (x == 0.0) {
                    loot.add(ItemUtils.getTitleVoucher((String)"#BetterThanYijki"));
                }
                if (x == 1.0) {
                    loot.add(ItemUtils.getTitleVoucher((String)"ChampionOfTheCosmoverse"));
                }
            }
            if (x == 2.0) {
                loot.add(ItemUtils.getTitleVoucher((String)"oof"));
            }
            if (x == 3.0) {
                loot.add(ItemUtils.getTitleVoucher((String)"Yikes!"));
            }
            if (x == 4.0) {
                loot.add(ItemUtils.getTitleVoucher((String)"#WhatClient"));
            }
        }
        if (dungeonType.equals((Object)DungeonType.ABANDONED_SHIP) && Math.random() <= 0.05) {
            loot.add(DungeonsAPI.get().getTeleportationCrystal().clone());
        }
        if (Math.random() <= 0.05) {
            loot.add(CosmicConquestAPI.createHeroicToken((int)(ThreadLocalRandom.current().nextInt(4) + 1)));
        }
        if (Math.random() < 0.1) {
            loot.add(DungeonsRewardsAPI.getRandomHeroicEnchantment());
        }
        if (Math.random() < 0.15) {
            loot.add(ItemUtils.getMysteryDust((ItemRarity)ItemRarity.SOUL, (int)(5 + ThreadLocalRandom.current().nextInt(6))));
        }
        if (Math.random() < 0.15) {
            loot.add(ItemUtils.getDepthStriderBook((int)(1 + ThreadLocalRandom.current().nextInt(3))));
        }
        return loot;
    }

    public List<ItemStack> addLoot(ItemBuilder builder, List<ItemStack> items) {
        ItemStack item = builder.build();
        if (item != null) {
            items.add(item);
        }
        return items;
    }

    public List<ItemStack> getOtherDungeonLoot(DungeonType dungeonType) {
        int mcmmoCredits;
        int moneyToGive;
        boolean isDestroyedOutpost = dungeonType.equals((Object)DungeonType.DESTROYED_OUTPOST);
        ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
        if (Math.random() < 0.25) {
            loot.add(ItemUtils.getItemNametag());
        }
        if (Math.random() < 0.4) {
            loot.add(ItemUtils.getTransmogScroll());
        }
        if (Math.random() < 0.25) {
            loot.add(ItemUtils.getWhitescroll());
        }
        if (isDestroyedOutpost) {
            if (Math.random() < 0.4) {
                loot.add(LoreTag.getLoreTagItem());
            }
            if (Math.random() < 0.4) {
                loot.add(RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD));
            }
        }
        return loot;
    }

    private int getEmptyInventorySlots(Player p) {
        int emptySlots = 0;
        for (ItemStack i : p.getInventory()) {
            if (i != null && i.getType() != Material.AIR) continue;
            ++emptySlots;
        }
        return emptySlots;
    }
}

