/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicCustomCreepers.me.astropvp.cratekeys.enums.CreeperTypes
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.cosmicutils.itembuilder.BlackScrollBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.BoosterBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.BossEggBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CosmicCrateBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.DungeonPortalIngredientBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.DungeonPortalIngredientBuilder$PortalIngredient
 *  com.cosmicpvp.cosmicutils.itembuilder.EnchantDustBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.EnchantmentOrbBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.FallenHeroBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.FatBucketBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.HeroicUpgradeBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.KitGemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.McMMOSkillVoucherBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.MysteryEnchantBookBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.RandomizationScrollBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.SpaceChestBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.YijkiStaffBuilder
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.struct.ItemTier
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.struct.LootTable
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CustomItemUtils
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicskycustoms.net.astropvp.armorsets.struct.CustomItemBuilder
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  me.ifamasssxd.killtracker.me.astropvp.cratekeys.net.astropvp.armorsets.listeners.Tracker
 *  org.arkhamnetwork.Arkkit.patches.ExpBottles
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.enchantments.me.astropvp.cratekeys.items.EMPPulseItem
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.cosmicutils.itembuilder.BlackScrollBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.BoosterBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.BossEggBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.DungeonPortalIngredientBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.EnchantDustBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.EnchantmentOrbBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.FallenHeroBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.FatBucketBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.HeroicUpgradeBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.KitGemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.McMMOSkillVoucherBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MysteryEnchantBookBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.RandomizationScrollBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.SpaceChestBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.YijkiStaffBuilder;
import com.cosmicpvp.cosmicutils.struct.ItemTier;
import com.cosmicpvp.cosmicutils.struct.LootTable;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;

import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.enchantments.items.EMPPulseItem;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Lucky
extends EvolutionKit {
    private final ChatColor mainColor = ChatColor.WHITE;
    private final Map<Integer, LootTable<ItemBuilder>> lootTable = new HashMap<Integer, LootTable<ItemBuilder>>();

    public Lucky() {
        this.addItem(1, new CustomItemBuilder(CustomItemBuilder.CustomItemType.ITEM_NAMETAG), 0.2);
        this.addItem(1, new CustomItemBuilder(CustomItemBuilder.CustomItemType.LORETAG), 0.2);
        this.addItem(1, new CustomItemBuilder(CustomItemBuilder.CustomItemType.TRANSMOG_SCROLL), 0.2);
        this.addItem(1, new CustomItemBuilder(CustomItemBuilder.CustomItemType.SHOWCASE_EXPANDER), 0.2);
        this.addItem(1, new CustomItemBuilder(CustomItemBuilder.CustomItemType.ADD_HOME_ITEM), 0.2);
        this.addItem(1, new CustomItemBuilder(CustomItemBuilder.CustomItemType.BLACKSCROLL), 0.2);
        this.addItem(1, new BlackScrollBuilder(false, 75, 100), 0.2);
        this.addItem(1, new ItemBuilder(ItemID.removeItemID(ExpBottles.getXpBottle(25000, "Lucky Box"))), 0.2);
        this.addItem(1, new KitGemBuilder(null, false), 0.2);
        this.addItem(2, new SpaceChestBuilder(ItemTier.LEGENDARY), 0.2);
        this.addItem(2, new CustomItemBuilder(CustomItemBuilder.CustomItemType.WHITE_SCROLL), 0.2);
        this.addItem(2, new CustomItemBuilder(CustomItemBuilder.CustomItemType.RESET_KIT), 0.2);
        this.addItem(3, new RandomizationScrollBuilder(ItemTier.LEGENDARY, 1), 0.2);
        this.addItem(3, new FallenHeroBuilder(null, true), 0.2);
        this.addItem(3, new CustomItemBuilder(CustomItemBuilder.CustomItemType.RESET_KIT), 0.2);
        this.addItem(3, new BoosterBuilder("EXP", 900, 2.0), 0.2);
        this.addItem(3, new BlackScrollBuilder(false, 90, 100), 0.2);
        this.addItem(4, new CustomItemBuilder(CustomItemBuilder.CustomItemType.MYSTERY_MOBSPAWNER), 0.2);
        this.addItem(4, new BossEggBuilder("KING_SLIME"), 0.2);
        this.addItem(4, new BossEggBuilder("BROOD_MOTHER"), 0.2);
        this.addItem(4, new FallenHeroBuilder(null, false), 0.2);
        this.addItem(5, new DungeonPortalIngredientBuilder(DungeonPortalIngredientBuilder.PortalIngredient.TELEPORTATION_CRYSTAL), 0.1);
        this.addItem(5, new DungeonPortalIngredientBuilder(DungeonPortalIngredientBuilder.PortalIngredient.COSMNOAUT_FUEL), 0.1);
        this.addItem(5, new DungeonPortalIngredientBuilder(DungeonPortalIngredientBuilder.PortalIngredient.DIMENSIONAL_WEB), 0.1);
        this.addItem(5, new DungeonPortalIngredientBuilder(DungeonPortalIngredientBuilder.PortalIngredient.INTERDIMENSIONAL_CORE), 0.1);
        this.addItem(5, new KitGemBuilder(null, false), 0.2);
        this.addItem(6, new EnchantmentOrbBuilder(OrbType.ARMOR, 12, 70, 95), 0.2);
        this.addItem(6, new BossEggBuilder("SKELETON_HORSE"), 0.2);
        this.addItem(6, new SpaceChestBuilder(ItemTier.GOD), 0.2);
        this.addItem(7, new EnchantmentOrbBuilder(OrbType.WEAPON, 12, 70, 95), 0.2);
        this.addItem(7, new MysteryEnchantBookBuilder(ItemTier.HEROIC), 0.15);
        this.addItem(7, new KitGemBuilder(true, true), 0.15);
        this.addItem(7, new BossEggBuilder("PLAGUE_BLOATER"), 0.2);
        this.addItem(8, new EnchantDustBuilder(ItemRarity.SOUL, true, 2, 4), 0.2);
        this.addItem(8, new EnchantDustBuilder(ItemRarity.SOUL, true, 4, 8), 0.15);
        this.addItem(8, new EnchantDustBuilder(ItemRarity.SOUL, true, 8, 16), 0.1);
        this.addItem(8, new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR), 0.2);
        this.addItem(8, new BossEggBuilder("DEMONIC_WITHER"), 0.2);
        this.addItem(8, new RandomizationScrollBuilder(ItemTier.GOD, 1), 0.2);
        this.addItem(8, new FatBucketBuilder(), 0.1);
        this.addItem(9, new BlackScrollBuilder(true, 10, 25), 0.2);
        this.addItem(9, new CustomItemBuilder(CustomItemBuilder.CustomItemType.EQUIPMENT_LOOTBOX), 0.2);
        this.addItem(9, new PhoenixFeatherBuilder(), 0.05);
        this.addItem(9, new YijkiStaffBuilder(), 0.025);
        this.addItem(9, new ItemBuilder(new EMPPulseItem().createItem(null)), 0.1);
        this.addItem(10, new CustomItemBuilder(CustomItemBuilder.CustomItemType.HOLY_WHITE_SCROLL), 0.15);
        this.addItem(10, new HeroicUpgradeBuilder(true, 60.0, 90.0), 0.2);
        this.addItem(10, new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK), 0.2);
        this.addItem(10, new EnchantmentOrbBuilder(Math.random() <= 0.5 ? OrbType.WEAPON : OrbType.WEAPON, 13, 70, 95), 0.2);
        this.addItem(10, new ItemBuilder(new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("mystery_lootbox").build()), 0.05);
        this.addItem(10, new ItemBuilder(new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("wildcard_gkit").build()), 0.075);
    }

    public void addItem(int level, ItemBuilder builder, double chance) {
        this.lootTable.computeIfAbsent(level, table -> new LootTable()).addItem(builder, chance);
    }

    @Override
    public List<ItemStack> getItems(Player player, int level) {
        ArrayList items = Lists.newArrayList();
        items.add(EvolutionKits.getLuckyBox(level, true));
        return items;
    }

    public List<ItemStack> getLuckyBoxItems(int level, double cash) {
        ArrayList items = Lists.newArrayList();
        items.add(CustomItemUtils.getMoneyNote("Lucky Box", (int)cash));
        int amount = Math.min(5, Math.max(1, level / 3 + new Random().nextInt(5 - level / 5)));
        LootTable ourTable = new LootTable();
        this.populateLootTable((LootTable<ItemBuilder>)ourTable, level);
        if (level == 10) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 9, 8, 7, 6);
        } else if (level == 9) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 8, 7, 6, 5);
        } else if (level == 8) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 7, 6, 5, 4);
        } else if (level == 7) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 6, 5, 4, 3);
        } else if (level == 6) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 5, 4, 3, 2);
        } else if (level == 5) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 4, 3, 2, 1);
        } else if (level == 4) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 3, 2, 1);
        } else if (level == 3) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 2, 1);
        } else if (level == 2) {
            this.populateLootTable((LootTable<ItemBuilder>)ourTable, 1);
        }
        for (int i = 0; i < amount; ++i) {
            ItemStack toAdd = null;
            for (int attempts = 0; (toAdd = ((ItemBuilder)ourTable.getRandomLoot()).build()) == null && attempts < 100; ++attempts) {
            }
            if (toAdd == null) continue;
            items.add(toAdd);
        }
        Bukkit.getLogger().info("[EvolutionKits] Lucky returning " + items.size() + " Items / " + amount + ", level: " + level);
        return items;
    }

    public LootTable populateLootTable(LootTable<ItemBuilder> lootTable, int ... levels) {
        for (int level : levels) {
            LootTable<ItemBuilder> found = this.lootTable.get(level);
            if (found == null) continue;
            found.getLootRates().forEach((arg_0, arg_1) -> lootTable.addItem(arg_0, arg_1));
        }
        return lootTable;
    }

    @Override
    public String getItemName() {
        return CC.WhiteB + "Lucky";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", this.mainColor + CC.Bold + "* " + this.mainColor + "Lucky Box", "", CC.Gray + "Feeling Lucky? Test your luck with a Lucky Box!", CC.Gold + "* Fall 2018");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.CHEST;
    }

    @Override
    public int getInventorySlot() {
        return 6;
    }
}

