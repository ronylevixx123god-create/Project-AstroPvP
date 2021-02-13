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
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonLootbagListener
implements Listener {
    @EventHandler
    public void onPlayerOpenDungeonLootbag(PlayerInteractEvent e) throws Exception {
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
            if (wrapper.getString("cosmicType").equals("lootbag") && (type = (data = new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData"))).getString("bagType")) != null && (foundType = DungeonType.getFromName(type)) != null) {
                bagType = foundType;
            }
            if (this.getEmptyInventorySlots(p) < 4 && !CosmicDungeonsPlugin.get().isDevplanet()) {
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You do not have enough empty inventory slots to open a Dungeon Lootbag.");
                p.sendMessage(ChatColor.RED + "REQ: " + ChatColor.GRAY + (bagType.equals(DungeonType.ABANDONED_SHIP) ? 4 : 6) + " slots");
                return;
            }
            if (p.getItemInHand().getAmount() > 1) {
                if (!CosmicDungeonsPlugin.get().isDevplanet()) {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot open stacked Dungeon Lootbags.");
                    return;
                }
                p.sendMessage(ChatColor.RED + "You cannot open stacked bags, but since this is devplanet its allowed..");
                is.setAmount(is.getAmount() - 1);
            } else {
                long itemID;
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID((ItemStack)e.getItem())) != -1L) {
                    ItemID.blacklistIID((long)itemID, "dungeonLootbagOpen");
                }
                p.setItemInHand((ItemStack)null);
            }
            List<ItemStack> loot = this.getCoolDungeonLoot(bagType);
            ItemStack heroicBonusItem = null;
            ItemStack heroicUpgradeBonusItem = null;
            int level = UpgradeUtils.getUpgradeLevel((Player)p, "HEROIC_DUNGEON_LOOTBAG");
            if (level > 0) {
                double chance = 0;
                double d = level == 1 ? 0.05 : (level == 2 ? 0.08 : (level == 3 ? 0.15 : (chance = level == 4 ? 0.25 : 0.0)));
                if (Math.random() <= chance) {
                    List<ItemStack> potentialLoot = this.getCoolDungeonLoot(bagType);
                    potentialLoot.addAll(this.getOtherDungeonLoot(bagType));
                    heroicUpgradeBonusItem = potentialLoot.get(ThreadLocalRandom.current().nextInt(potentialLoot.size()));
                    loot.add(heroicUpgradeBonusItem);
                }
            }
            if (p.hasPermission("cosmicpvp.heroic")) {
                List<ItemStack> potentialLoot2 = this.getCoolDungeonLoot(bagType);
                potentialLoot2.addAll(this.getOtherDungeonLoot(bagType));
                heroicBonusItem = potentialLoot2.get(ThreadLocalRandom.current().nextInt(potentialLoot2.size()));
                loot.add(heroicBonusItem);
            }
            if (loot.size() > 0) {
                for (int ind = 0; ind < loot.size(); ++ind) {
                    ItemStack i = loot.get(ind);
                    String itemName = i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : i.getType().name().replace("_", " ");
                    String colorlessName = ChatColor.stripColor(itemName).toLowerCase();
                    if (colorlessName.contains("faction point") || colorlessName.contains("faction crystal") || colorlessName.contains("voucher") || colorlessName.contains("title") || colorlessName.contains("tag") || colorlessName.contains("transmog")) continue;
                    if (i.getType() == Material.ENCHANTED_BOOK) {
                        itemName = "Depth Strider Enchantment Book";
                    }
                    JSONMessage message = new JSONMessage("");
                    message.addText((Player)null, ChatColor.GOLD + "" + ChatColor.BOLD + "(*) " + ChatColor.GOLD + p.getDisplayName() + ChatColor.GOLD + " received a(n) ", ChatColor.GOLD);
                    message.addItem(i, itemName != null ? ItemLinking.formatColors(itemName).getText() : i.getType().name().replace("_", " ").toLowerCase(), ChatColor.GOLD);
                    message.addText((Player)null, ChatColor.GOLD + " from a(n) " + bagType.getDungeonName() + " /dungeon Lootbag", ChatColor.GOLD);
                    if (heroicUpgradeBonusItem != null && heroicUpgradeBonusItem.equals(i)) {
                        message.addText((Player)null, " " + CC.Yellow + "(" + CC.YellowB + "SRC" + CC.Yellow + ": Lootbag Scavanger /f upgrade)", ChatColor.YELLOW);
                        heroicUpgradeBonusItem = null;
                    } else if (heroicBonusItem != null && heroicBonusItem.equals(i)) {
                        message.addText((Player)null, " " + CC.Yellow + "(" + CC.YellowB + "SRC" + CC.Yellow + ":", ChatColor.YELLOW);
                        message.addHoverData(CC.Yellow + " Heroic Rank)", CC.YellowB + "Heroic Rank " + CC.Yellow + "(" + CC.YellowB + "Top Rank+" + CC.Yellow + ") " + CC.Gray + "can be\n" + CC.Gray + "obtained from various in game mechanics\n" + CC.Gray + "as well as unlocked at " + CC.GrayU + "buy.astropvp.net" + CC.Gray + "!", "https://buy.astropvp.net", ChatColor.YELLOW);
                        heroicBonusItem = null;
                    }
                    message.addText((Player)null, "!", ChatColor.GOLD);
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        message.sendToPlayer(pl);
                    }
                }
            }
            loot.addAll(this.getOtherDungeonLoot(bagType));
            boolean dropped = false;
            for (ItemStack j : loot) {
                if (PlayerUtil.giveOrDropProtectedItem((Player)p, (ItemStack)j, -1)) continue;
                dropped = true;
            }
            if (dropped) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "Your inventory was full and some Lootbag items have been dropped on the ground!");
            }
            de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(),p.getLocation(), 0f,1f,0f,0.6f,50);


            p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 0.3f);
            Bukkit.getLogger().info(new StringBuilder().append("[CosmicDungeons] Giving ").append(p.getName()).append(loot.size()).append(" items: ").append(Arrays.toString(loot.toArray(new Object[0]))).append(" from lootbag: ").append(bagType.name()).toString());
            p.updateInventory();
        }
    }

    public List<ItemStack> getAllDungeonLoot(DungeonType dungeonType) {
        List<ItemStack> item = this.getCoolDungeonLoot(dungeonType);
        item.addAll(this.getOtherDungeonLoot(dungeonType));
        return item;
    }

    public List<ItemStack> getCoolDungeonLoot(DungeonType dungeonType) {
        boolean isDestroyed = dungeonType.equals(DungeonType.DESTROYED_OUTPOST);
        ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (Math.random() < 0.04) {
            loot.add(DungeonsRewardsAPI.getHolyWhiteScroll());
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
            if (Math.random() <= 0.1) {
                loot.add(EnchantmentBook.getHeroicBlackscroll(true));
            }
            if (Math.random() <= 0.33) {
                loot.add(KillTrackerAPI.createSoulShard((((Random)random).nextInt(5000) + 5001)));
            }
            if (Math.random() <= 0.25) {
                loot.add(CosmicConquestAPI.createHeroicToken((2 + ((Random)random).nextInt(3))));
            }
            if (Math.random() <= 0.1) {
                loot.add(EnchantmentOrb.getEnchantmentOrb((50 + ((Random)random).nextInt(51)), 13, ((Random)random).nextInt(10) <= 2 ? OrbType.ARMOR : OrbType.WEAPON));
            }
            if (Math.random() <= 0.05 && (enchantment = EnchantmentAPI.getEnchantment("Infinite Luck")) != null) {
                loot.add(EnchantmentBook.getEnchantmentBook(enchantment, (1 + ((Random)random).nextInt(enchantment.getMaxLevel())), (20 + ((Random)random).nextInt(40)), (40 + ((Random)random).nextInt(40))));
            }
            if (Math.random() <= 0.05) {
                loot.add(CosmicArmorSetsAPI.createHeroicWeapon(((Random)random).nextBoolean() ? HeroicWeaponType.SWORD : HeroicWeaponType.AXE));
            }
            // todo: fix disguise
//            if (Math.random() <= 0.05) {
//                loot.add(CosmicDisguiseAPI.createRedeemableDisguise("CHICKEN"));
//            }
            if (Math.random() <= 0.1) {
                loot.add(CosmicArmorSetsAPI.createArmorSetUpgrade(ArmorSet.values()[((Random)random).nextInt(ArmorSet.values().length)], (double)(50 + ((Random)random).nextInt(51))));
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
            loot.add(CosmicArmorSetsAPI.getArmorSet(ArmorSet.MOTHER_OF_YIJKI)[x]);
        } else if (Math.random() < 0.1) {
            int x = new Random().nextInt(4);
            loot.add(CosmicArmorSetsAPI.getArmorSet(ArmorSet.PHANTOM)[x]);
        } else if (Math.random() < 0.02) {
            int x = new Random().nextInt(4);
            loot.add(CosmicArmorSetsAPI.getArmorSet(ArmorSet.valueOf("SUPREME"))[x]);
        }
        if (Math.random() < 0.01) {
            loot.add(DungeonsAPI.get().getDungeon(0).getDungeonPortal());
        }
        if (Math.random() < 0.03) {
            if (Math.random() < 0.3 && !isDestroyed) {
                loot.add(DungeonsRewardsAPI.getSoulAnvil());
            } else {
                loot.add(DungeonsRewardsAPI.getSoulPearl());
            }
        }
        if (Math.random() < 0.15) {
            double x2 = new Random().nextInt(5);
            if (isDestroyed) {
                if (x2 == 0.0) {
                    loot.add(ItemUtils.getTitleVoucher("GodKiller"));
                }
                if (x2 == 1.0) {
                    loot.add(ItemUtils.getTitleVoucher("IAmReality"));
                }
            } else {
                if (x2 == 0.0) {
                    loot.add(ItemUtils.getTitleVoucher("#BetterThanYijki"));
                }
                if (x2 == 1.0) {
                    loot.add(ItemUtils.getTitleVoucher("ChampionOfTheCosmoverse"));
                }
            }
            if (x2 == 2.0) {
                loot.add(ItemUtils.getTitleVoucher("oof"));
            }
            if (x2 == 3.0) {
                loot.add(ItemUtils.getTitleVoucher("Yikes!"));
            }
            if (x2 == 4.0) {
                loot.add(ItemUtils.getTitleVoucher("#WhatClient"));
            }
        }
        if (dungeonType.equals(DungeonType.ABANDONED_SHIP) && Math.random() <= 0.05) {
            loot.add(DungeonsAPI.get().getTeleportationCrystal().clone());
        }
        if (Math.random() <= 0.05) {
            loot.add(CosmicConquestAPI.createHeroicToken((ThreadLocalRandom.current().nextInt(4) + 1)));
        }
        if (Math.random() < 0.1) {
            loot.add(DungeonsRewardsAPI.getRandomHeroicEnchantment());
        }
        if (Math.random() < 0.15) {
            loot.add(ItemUtils.getMysteryDust((ItemRarity)ItemRarity.SOUL, (5 + ThreadLocalRandom.current().nextInt(6))));
        }
        if (Math.random() < 0.15) {
            loot.add(ItemUtils.getDepthStriderBook((1 + ThreadLocalRandom.current().nextInt(3))));
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
        boolean isDestroyedOutpost = dungeonType.equals(DungeonType.DESTROYED_OUTPOST);
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
        int daysSinceAvailable = Math.min(30, dungeonType.getDaysSinceDungeonWasAvailable());
        if (isDestroyedOutpost) {
            moneyToGive = Math.min(30000000, 5000000 + 1000000 * daysSinceAvailable);
            mcmmoCredits = Math.min(600, 100 + 20 * daysSinceAvailable);
        } else {
            moneyToGive = Math.min(10000000, 1000000 + 500000 * daysSinceAvailable);
            mcmmoCredits = Math.min(250, 100 + 10 * daysSinceAvailable);
        }
        loot.add(ItemUtils.getMoneyNote("Cosmic Dungeon", moneyToGive));
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

