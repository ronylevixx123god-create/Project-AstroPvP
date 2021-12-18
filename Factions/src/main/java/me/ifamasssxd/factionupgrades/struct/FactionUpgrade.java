package me.ifamasssxd.factionupgrades.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.google.common.collect.Lists;
import com.massivecraft.factions.P;
import me.ifamasssxd.factionupgrades.struct.upgrades.*;
import me.ifamasssxd.factionupgrades.struct.upgrades.heroic.*;
import me.ifamasssxd.factionupgrades.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.text.DecimalFormat;
import java.util.ArrayList;

public enum FactionUpgrade {
    ENHANCED_FLIGHT(Material.FEATHER, (Upgrade)new EnhancedFlight(3, "Enhanced Flight", Lists.newArrayList((Object)ChatColor.GRAY + "Increased flight abilities in your claims!"))),
    EXPLOSIVES_EXPERT(Material.TNT, (Upgrade)new ExplosivesExpert(4, "Explosives Expert", Lists.newArrayList("Smelt Gun Powder into TNT."))),
    WARP_MASTER(Material.ENDER_PEARL, (Upgrade)new WarpMaster(12, "Warp Master", Lists.newArrayList("Create more /f warps."))),
    MAX_FACTION_SIZE(Material.EMERALD, (Upgrade)new MaxFactionSize(5, "Max Faction Size", Lists.newArrayList("Increases max faction size."))),
    FACTION_POWER_BOOST(Material.DIAMOND, (Upgrade)new FactionPowerBoost(5, "Faction Power Boost", Lists.newArrayList("Adds passive faction power boosts."))),
    //MCMMO_MASTERY(Material.BOOK, (Upgrade)new mcMMOMastery(4, "mcMMO Mastery", Lists.newArrayList("Shorter mcMMO Active Ability Cooldowns"))),
    MONSTER_FARM(Material.BLAZE_ROD, (Upgrade)new MonsterFarm(4, "Monster Farm", Lists.newArrayList("Increased mob spawn rates in claims."))),
    EXP_HARVEST(Material.EXP_BOTTLE, (Upgrade)new EXPHarvest(3, "XP Harvest", Lists.newArrayList("Increases Vanilla XP gained from monsters."))),
    TP_MASTERY(Material.BOOK, (Upgrade)new TPMastery(4, "TP Mastery", Lists.newArrayList("Decreases teleportation casting time."))),
    WELL_FED(Material.COOKED_BEEF, (Upgrade)new WellFed(4, "Well Fed", Lists.newArrayList("Reduces rate at which faction members", "get hungry and need to /feed."))),
    NATURAL_GROWTH(Material.WHEAT, (Upgrade)new NaturalGrowth(5, "Natural Growth", Lists.newArrayList("Increase growth speed of crops in claims."))),
    SOUL_MASTER(Material.NETHER_STAR, (Upgrade)new SoulMastery(3, "Soul Mastery", Lists.newArrayList("Decreases soul cost of passive soul enchantments."))),
    WARZONE_CONTROL(Material.DIAMOND_SWORD, (Upgrade)new WarzoneControl(3, "Warzone Control", Lists.newArrayList("Increases /warzone \"Last Man Standing\"", "$ and XP payouts."))),
    OUTPOST_CONTROL(Material.DIAMOND_CHESTPLATE, (Upgrade)new OutpostControl(5, "Outpost Control", Lists.newArrayList("Capture and Assault /outposts faster."))),
    CONQUEST_MASTERY(Material.CHEST, (Upgrade)new ConquestMastery(4, "Conquest Mastery", Lists.newArrayList("Deal more damage to Conquest Chest bosses."))),
    ENDER_FARMING(Material.ENDER_PORTAL_FRAME, (Upgrade)new EnderFarming(3, "Ender Farming", Lists.newArrayList("Multiplies Ender Monster killstreak rewards."))),
    BOSS_MASTERY(Material.SKULL_ITEM, (Upgrade)new BossMastery(4, "Boss Mastery", Lists.newArrayList("Increases damage dealt to Boss monsters."))),
    HEROIC_WELL_FED(Material.COOKED_BEEF, new HeroicWellFed(4, "Heroic Well Fed", Lists.newArrayList("Reduces rate at which faction members", "get hungry and need to /feed.")), true),
    HEROIC_COMBAT_TAG(Material.WATCH, new HeroicCombatTag(4, "Escape Artist", Lists.newArrayList("Reduces Combat Tag Timer")), true),
    HEROIC_ENDERPEARL_COOLDOWN(Material.ENDER_PEARL, new HeroicEnderPearlCooldown(4, "Fast Enderpearl", Lists.newArrayList("Reduces Enderpearl Cooldown")), true),
    HEROIC_DUNGEON_PORTAL(Material.EYE_OF_ENDER, new HeroicDungeonPortalCountdown(3, "Dungeon Runner", Lists.newArrayList("Reduces the time it takes for", "a Dungeon Portal to close.")), true),
    HEROIC_DUNGEON_LOOT(Material.CHEST, new HeroicDungeonLootCooldown(6, "Dungeon Looter", Lists.newArrayList("Reduces the time it takes to", "receive loot for completing a dungeon.")), true),
    HEROIC_SOUL_MASTERY(Material.NETHER_STAR, new HeroicSoulMastery(2, "Heroic Soul Mastery", Lists.newArrayList("Decreases soul cost of passive soul enchantments.")), true),
    HEROIC_VKIT_LEVELING(Material.DIAMOND_AXE, new HeroicVKitLeveling(5, "Kit Evolution", Lists.newArrayList("Increases the chance to level up a /vkit.")), true),
    HEROIC_DUNGEON_LOOTBAG(Material.CHEST, new HeroicDungeonLootBuff(4, "Dungeon Master", Lists.newArrayList("Gain an increasing chance to unlock", "a bonus item from a Dungeon Lootbag.")), true),
    HEROIC_BOSS_MASTERY(Material.SKULL_ITEM, new HeroicBossMastery(3, "Heroic Boss Mastery", Lists.newArrayList("Increases damage dealt to Boss monsters.")), true),
    HEROIC_ARMOR_SET(Material.DIAMOND_CHESTPLATE, new HeroicArmorSetDamage(4, "Maverick", Lists.newArrayList("Reduces damage received from Armor Set", "bonuses and passive abilities.")), true);

    private MaterialData displayData;
    private Upgrade upgrade;
    private boolean heroicUpgrade;
    private static DecimalFormat format;

    private FactionUpgrade(MaterialData data, Upgrade upgrade) {
        this.displayData = data;
        this.upgrade = upgrade;
    }

    private FactionUpgrade(Material data, Upgrade upgrade) {
        this.displayData = new MaterialData(data);
        this.upgrade = upgrade;
    }

    private FactionUpgrade(Material data, Upgrade upgrade, boolean heroic) {
        this.displayData = new MaterialData(data);
        this.upgrade = upgrade;
        this.heroicUpgrade = heroic;
    }

    public boolean isHeroic() {
        return this.heroicUpgrade;
    }

    public FactionUpgrade getChildUpgrade() {
        if (this == HEROIC_BOSS_MASTERY) {
            return BOSS_MASTERY;
        }
        if (this == HEROIC_SOUL_MASTERY) {
            return SOUL_MASTER;
        }
        if (this == HEROIC_WELL_FED) {
            return WELL_FED;
        }
        return null;
    }

    public int getHeroicTokenCost(int tier) {
        Upgrade upgrade = this.getUpgrade();
        if (upgrade instanceof HeroicUpgrade) {
            int retr = ((HeroicUpgrade)upgrade).getHeroicCrystalCost(tier);
            return (int)Math.max(0.0, Math.ceil((double)retr * P.getP().getUpgradeManager().getUpgradePriceMultiplier()));
        }
        return 0;
    }

    public int getTokenCost(int tier) {
        int cost = this.getUpgrade().getTokenCost(tier);
        return (int)Math.floor((double)cost * P.getP().getUpgradeManager().getUpgradePriceMultiplier()) * 8;
    }

    public double getMoneyCost(int tier) {
        double cost = this.getUpgrade().getMoneyCost(tier);
        return Math.floor(cost * P.getP().getUpgradeManager().getUpgradePriceMultiplier());
    }

    public ItemStack createUpgradeItem(String factionId, FactionUpgradeInfo upgradeInfo) {
        ChatColor secondaryColor;
        int tier = upgradeInfo != null ? upgradeInfo.getUpgradeTier(this) : 0;
        Upgrade upgrade = this.getUpgrade();
        ArrayList lore = Lists.newArrayList();
        upgrade.getLore(tier).forEach(line -> lore.add((Object)ChatColor.GRAY + line));
        ChatColor tierColor = this.isHeroic() ? ChatColor.YELLOW : ChatColor.LIGHT_PURPLE;
        lore.add("");
        lore.add((Object)tierColor + ChatColor.BOLD.toString() + "Tier");
        lore.add((Object)ChatColor.GRAY + " " + (tier == 0 ? ChatColor.RED.toString() + (Object)ChatColor.BOLD + "LOCKED" : tier + " / " + upgrade.getMaxUpgradeLevel()));
        lore.add("");
        lore.add((Object)tierColor + ChatColor.BOLD.toString() + "Perks");
        ArrayList perkLore = (ArrayList) upgrade.getPerkList(tier);
        if (perkLore == null) {
            perkLore = Lists.newArrayList(new String[]{ChatColor.GRAY.toString() + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Vaq pls fill me in.", ChatColor.GRAY.toString() + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Good faction upgrades.", ChatColor.GRAY.toString() + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "All allies can fly."});
        }
        lore.addAll(perkLore);
        lore.add("");
        int heroicTokenCost = this.getHeroicTokenCost(tier + 1);
        long balanceRequired = 0L;
        boolean hasAccess = true;
        if (upgrade instanceof HeroicUpgrade) {
            hasAccess = P.getP().getUpgradeManager().canAccessHeroic(factionId, this);
        }
        ChatColor chatColor = secondaryColor = this.isHeroic() ? ChatColor.GOLD : ChatColor.AQUA;
        if (upgrade.getMaxUpgradeLevel() > tier) {
            lore.add((Object)tierColor + ChatColor.BOLD.toString() + (this.heroicUpgrade ? "Requirements" : "Price"));
            int tokenCost = this.getTokenCost(tier + 1);
            double money = this.getMoneyCost(tier + 1);
            FactionUpgrade childRequired = this.getChildUpgrade();
            if (childRequired != null) {
                lore.add((Object)secondaryColor + CC.Bold + " * " + CC.Gold + childRequired.getUpgrade().getDisplayName(0) + " " + JSONMessage.getNumber((int)childRequired.getUpgrade().getMaxUpgradeLevel()) + " /f upgrade");
            }
            if (balanceRequired > 0L) {
                lore.add((Object)secondaryColor + ChatColor.BOLD.toString() + " * " + (Object)secondaryColor + ChatColor.BOLD.toString() + (hasAccess ? CC.Strike : (Object)secondaryColor + ChatColor.BOLD.toString()) + "$" + (hasAccess ? (Object)secondaryColor + CC.Strike : secondaryColor) + format.format(balanceRequired));
            }
            if (heroicTokenCost > 0) {
                lore.add((Object)secondaryColor + ChatColor.BOLD.toString() + " * " + CC.Gold + heroicTokenCost + "x " + CC.Gold + "Heroic Faction Crystal" + (tokenCost > 1 ? "s" : ""));
            }
            if (tokenCost > 0) {
                lore.add((Object)secondaryColor + ChatColor.BOLD.toString() + " * " + (Object)secondaryColor + tokenCost + "x Faction Crystal" + (tokenCost > 1 ? "s" : ""));
            }
            if (money > 0.0) {
                lore.add((Object)secondaryColor + ChatColor.BOLD.toString() + " * $" + (Object)secondaryColor + format.format(money));
            }
            lore.add("");
            if (tier == 0) {
                lore.add(secondaryColor.toString() + (Object)ChatColor.BOLD + "Click to " + (Object)ChatColor.UNDERLINE + "Unlock");
            } else {
                lore.add((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "Click to " + (Object)ChatColor.UNDERLINE + "Upgrade");
            }
        } else {
            if (balanceRequired > 0L) {
                lore.add((Object)tierColor + ChatColor.BOLD.toString() + "Requires");
                lore.add((Object)secondaryColor + ChatColor.BOLD.toString() + " * " + (Object)secondaryColor + ChatColor.BOLD.toString() + (hasAccess ? CC.Strike : (Object)secondaryColor + ChatColor.BOLD.toString()) + "$" + (hasAccess ? (Object)secondaryColor + CC.Strike : secondaryColor) + format.format(balanceRequired) + " /f top Spawner Value");
                lore.add("");
            }
            lore.add((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Upgrade is " + (Object)ChatColor.AQUA + (Object)ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "MAXED!");
        }
        return ItemBuilder.buildItem(tier == 0 || !hasAccess ? Material.STAINED_GLASS_PANE : this.getDisplayData().getItemType(), tier == 0 ? 1 : tier, tier == 0 ? (short)DyeColor.RED.getWoolData() : (hasAccess ? (short)this.getDisplayData().getData() : (short)DyeColor.YELLOW.getWoolData()), (Object)(this.heroicUpgrade ? ChatColor.GOLD : ChatColor.AQUA) + ChatColor.BOLD.toString() + upgrade.getDisplayName(tier), lore);
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

