package com.massivecraft.factions.upgrade;

import com.google.common.collect.Lists;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.gui.SimpleItem;
import com.massivecraft.factions.upgrade.upgrades.*;
import com.massivecraft.factions.upgrade.upgrades.heroic.*;
import com.massivecraft.factions.util.CC;
import com.massivecraft.factions.util.material.MaterialDb;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public enum FactionUpgrade {
    ENHANCED_FLIGHT("FEATHER", (Upgrade) new EnhancedFlight(3, "Enhanced Flight", Lists.newArrayList(new String[]{ChatColor.GRAY + "Increased flight abilities in your claims!"}))),
    EXPLOSIVES_EXPERT("TNT", (Upgrade) new ExplosivesExpert(4, "Explosives Expert", Lists.newArrayList(new String[]{"Smelt Gun Powder into TNT."}))),
    WARP_MASTER("ENDER_PEARL", (Upgrade) new WarpMaster(12, "Warp Master", Lists.newArrayList(new String[]{"Create more /f warps."}))),
    MAX_FACTION_SIZE("EMERALD", (Upgrade) new MaxFactionSize(10, "Max Faction Size", Lists.newArrayList(new String[]{"Increases max faction size."}))),
    FACTION_POWER_BOOST("DIAMOND", (Upgrade) new FactionPowerBoost(5, "Faction Power Boost", Lists.newArrayList(new String[]{"Adds passive faction power boosts."}))),
    MONSTER_FARM("BLAZE_ROD", (Upgrade) new MonsterFarm(4, "Monster Farm", Lists.newArrayList(new String[]{"Increased mob spawn rates in claims."}))),
    EXP_HARVEST("EXP_BOTTLE", (Upgrade) new EXPHarvest(3, "XP Harvest", Lists.newArrayList(new String[]{"Increases Vanilla XP gained from monsters."}))),
    TP_MASTERY("BOOK", (Upgrade) new TPMastery(4, "TP Mastery", Lists.newArrayList(new String[]{"Decreases teleportation casting time."}))),
    WELL_FED("COOKED_BEEF", (Upgrade) new WellFed(4, "Well Fed", Lists.newArrayList(new String[]{"Reduces rate at which faction members", "get hungry and need to /feed."}))),
    NATURAL_GROWTH("WHEAT", (Upgrade) new NaturalGrowth(5, "Natural Growth", Lists.newArrayList(new String[]{"Increase growth speed of crops in claims."}))),
    SOUL_MASTER("NETHER_STAR", (Upgrade) new SoulMastery(3, "Soul Mastery", Lists.newArrayList(new String[]{"Decreases soul cost of passive soul enchantments."}))),
    WARZONE_CONTROL("DIAMOND_SWORD", (Upgrade) new WarzoneControl(3, "Warzone Control", Lists.newArrayList(new String[]{"Increases /warzone \"Last Man Standing\"", "$ and XP payouts."}))),
    OUTPOST_CONTROL("DIAMOND_CHESTPLATE", (Upgrade) new OutpostControl(5, "Outpost Control", Lists.newArrayList(new String[]{"Capture and Assault /outposts faster."}))),
    CONQUEST_MASTERY("CHEST", (Upgrade) new ConquestMastery(4, "Conquest Mastery", Lists.newArrayList(new String[]{"Deal more damage to Conquest Chest bosses."}))),
    ENDER_FARMING("ENDER_PORTAL_FRAME", (Upgrade) new EnderFarming(3, "Ender Farming", Lists.newArrayList(new String[]{"Multiplies Ender Monster killstreak rewards."}))),
    BOSS_MASTERY("SKULL_ITEM", (Upgrade) new BossMastery(4, "Boss Mastery", Lists.newArrayList(new String[]{"Increases damage dealt to Boss monsters."}))),
    HEROIC_WELL_FED("COOKED_BEEF", (Upgrade) new HeroicWellFed(4, "Heroic Well Fed", Lists.newArrayList(new String[]{"Reduces rate at which faction members", "get hungry and need to /feed."})), true),
    HEROIC_COMBAT_TAG("WATCH", (Upgrade) new HeroicCombatTag(4, "Escape Artist", Lists.newArrayList(new String[]{"Reduces Combat Tag Timer"})), true),
    HEROIC_ENDERPEARL_COOLDOWN("ENDER_PEARL", (Upgrade) new HeroicEnderPearlCooldown(4, "Fast Enderpearl", Lists.newArrayList(new String[]{"Reduces Enderpearl Cooldown"})), true),
    HEROIC_DUNGEON_PORTAL("EYE_OF_ENDER", (Upgrade) new HeroicDungeonPortalCountdown(3, "Dungeon Runner", Lists.newArrayList(new String[]{"Reduces the time it takes for", "a Dungeon Portal to close."})), true),
    HEROIC_DUNGEON_LOOT("CHEST", (Upgrade) new HeroicDungeonLootCooldown(6, "Dungeon Looter", Lists.newArrayList(new String[]{"Reduces the time it takes to", "receive loot for completing a dungeon."})), true),
    HEROIC_SOUL_MASTERY("NETHER_STAR", (Upgrade) new HeroicSoulMastery(2, "Heroic Soul Mastery", Lists.newArrayList(new String[]{"Decreases soul cost of passive soul enchantments."})), true),
    HEROIC_VKIT_LEVELING("DIAMOND_AXE", (Upgrade) new HeroicVKitLeveling(5, "Kit Evolution", Lists.newArrayList(new String[]{"Increases the chance to level up a /vkit."})), true),
    HEROIC_DUNGEON_LOOTBAG("CHEST", (Upgrade) new HeroicDungeonLootBuff(4, "Dungeon Master", Lists.newArrayList(new String[]{"Gain an increasing chance to unlock", "a bonus item from a Dungeon Lootbag."})), true),
    HEROIC_BOSS_MASTERY("SKULL_ITEM", (Upgrade) new HeroicBossMastery(3, "Heroic Boss Mastery", Lists.newArrayList(new String[]{"Increases damage dealt to Boss monsters."})), true),
    HEROIC_ARMOR_SET("DIAMOND_CHESTPLATE", (Upgrade) new HeroicArmorSetDamage(4, "Maverick", Lists.newArrayList(new String[]{"Reduces damage received from Armor Set", "bonuses and passive abilities."})), true);

    private MaterialData displayData;

    private Upgrade upgrade;

    private boolean heroicUpgrade;

    private static DecimalFormat format;

    FactionUpgrade(MaterialData data, Upgrade upgrade) {
        this.displayData = data;
        this.upgrade = upgrade;
    }

    FactionUpgrade(String data, Upgrade upgrade) {
        this.displayData = new MaterialData(MaterialDb.get(data));
        this.upgrade = upgrade;
    }

    FactionUpgrade(String data, Upgrade upgrade, boolean heroic) {
        this.displayData = new MaterialData(MaterialDb.get(data));
        this.upgrade = upgrade;
        this.heroicUpgrade = heroic;
    }

    public boolean isHeroic() {
        return this.heroicUpgrade;
    }

    public FactionUpgrade getChildUpgrade() {
        if (this == HEROIC_BOSS_MASTERY)
            return BOSS_MASTERY;
        if (this == HEROIC_SOUL_MASTERY)
            return SOUL_MASTER;
        if (this == HEROIC_WELL_FED)
            return WELL_FED;
        return null;
    }

    public int getHeroicTokenCost(int tier) {
        Upgrade upgrade = getUpgrade();
        if (upgrade instanceof HeroicUpgrade) {
            int retr = ((HeroicUpgrade) upgrade).getHeroicCrystalCost(tier);
            return (int) Math.max(0.0D, Math.ceil(retr * FactionsPlugin.getInstance().getUpgradeManager().getUpgradePriceMultiplier()));
        }
        return 0;
    }

    public int getTokenCost(int tier) {
        int cost = getUpgrade().getTokenCost(tier);
        return (int) Math.floor(cost * FactionsPlugin.getInstance().getUpgradeManager().getUpgradePriceMultiplier()) * 8;
    }

    public double getMoneyCost(int tier) {
        double cost = getUpgrade().getMoneyCost(tier);
        return Math.floor(cost * FactionsPlugin.getInstance().getUpgradeManager().getUpgradePriceMultiplier());
    }

    public SimpleItem createUpgradeItem(String factionId, FactionUpgradeInfo upgradeInfo) {
        int tier = (upgradeInfo != null) ? upgradeInfo.getUpgradeTier(this) : 0;
        Upgrade upgrade = getUpgrade();
        ArrayList<String> lore = Lists.newArrayList();
        upgrade.getLore(tier).forEach(line -> lore.add(ChatColor.GRAY + line));
        ChatColor tierColor = isHeroic() ? ChatColor.YELLOW : ChatColor.LIGHT_PURPLE;
        lore.add("");
        lore.add(tierColor + ChatColor.BOLD.toString() + "Tier");
        lore.add(ChatColor.GRAY + " " + ((tier == 0) ? (ChatColor.RED.toString() + ChatColor.BOLD + "LOCKED") : (tier + " / " + upgrade.getMaxUpgradeLevel())));
        lore.add("");
        lore.add(tierColor + ChatColor.BOLD.toString() + "Perks");
        ArrayList<? extends String> perkLore = (ArrayList) upgrade.getPerkList(tier);
        if (perkLore == null)
            perkLore = Lists.newArrayList(ChatColor.GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.GRAY + "Vaq pls fill me in.", ChatColor.GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.GRAY + "Good faction upgrades.", ChatColor.GRAY.toString() + ChatColor.BOLD + "* " + ChatColor.GRAY + "All allies can fly.");
        lore.addAll(perkLore);
        lore.add("");
        int heroicTokenCost = getHeroicTokenCost(tier + 1);
        long balanceRequired = 0L;
        boolean hasAccess = true;
        if (upgrade instanceof HeroicUpgrade)
            hasAccess = FactionsPlugin.getInstance().getUpgradeManager().canAccessHeroic(factionId, this);
        ChatColor secondaryColor = isHeroic() ? ChatColor.GOLD : ChatColor.AQUA, chatColor = secondaryColor;
        if (upgrade.getMaxUpgradeLevel() > tier) {
            lore.add(tierColor + ChatColor.BOLD.toString() + (this.heroicUpgrade ? "Requirements" : "Price"));
            int tokenCost = getTokenCost(tier + 1);
            double money = getMoneyCost(tier + 1);
            FactionUpgrade childRequired = getChildUpgrade();
            if (childRequired != null)
                lore.add(secondaryColor + CC.Bold + " * " + CC.Gold + childRequired.getUpgrade().getDisplayName(0) + " " + childRequired.getUpgrade().getMaxUpgradeLevel() + " /f upgrade");
            if (balanceRequired > 0L)
                lore.add(secondaryColor + ChatColor.BOLD.toString() + " * " + secondaryColor + ChatColor.BOLD.toString() + (hasAccess ? CC.Strike : (secondaryColor + ChatColor.BOLD.toString())) + "$" + (hasAccess ? (secondaryColor + CC.Strike) : secondaryColor) + format.format(balanceRequired));
            if (heroicTokenCost > 0)
                lore.add(secondaryColor + ChatColor.BOLD.toString() + " * " + CC.Gold + heroicTokenCost + "x " + CC.Gold + "Heroic Faction Crystal" + ((tokenCost > 1) ? "s" : ""));
            if (tokenCost > 0)
                lore.add(secondaryColor + ChatColor.BOLD.toString() + " * " + secondaryColor + tokenCost + "x Faction Crystal" + ((tokenCost > 1) ? "s" : ""));
            if (money > 0.0D)
                lore.add(secondaryColor + ChatColor.BOLD.toString() + " * $" + secondaryColor + format.format(money));
            lore.add("");
            if (tier == 0) {
                lore.add(secondaryColor.toString() + ChatColor.BOLD + "Click to " + ChatColor.UNDERLINE + "Unlock");
            } else {
                lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to " + ChatColor.UNDERLINE + "Upgrade");
            }
        } else {
            if (balanceRequired > 0L) {
                lore.add(tierColor + ChatColor.BOLD.toString() + "Requires");
                lore.add(secondaryColor + ChatColor.BOLD.toString() + " * " + secondaryColor + ChatColor.BOLD.toString() + (hasAccess ? CC.Strike : (secondaryColor + ChatColor.BOLD.toString())) + "$" + (hasAccess ? (secondaryColor + CC.Strike) : secondaryColor) + format.format(balanceRequired) + " /f top Spawner Value");
                lore.add("");
            }
            lore.add(ChatColor.AQUA + ChatColor.BOLD.toString() + "Upgrade is " + ChatColor.AQUA + ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "MAXED!");
        }
        SimpleItem item = SimpleItem.builder().build();
        item.setLore(lore);
        item.setMaterial((tier == 0 || !hasAccess) ? MaterialDb.get("RED_STAINED_GLASS_PANE") : MaterialDb.get("YELLOW_STAINED_GLASS_PANE"));
        item.setName((this.heroicUpgrade ? ChatColor.GOLD : ChatColor.AQUA) + ChatColor.BOLD.toString() + upgrade.getDisplayName(tier));
        item.setLore(lore);
        return item;
    }

    public MaterialData getDisplayData() {
        return this.displayData;
    }

    public Upgrade getUpgrade() {
        return this.upgrade;
    }

    static {
        format = new DecimalFormat("#,###");
    }
}
