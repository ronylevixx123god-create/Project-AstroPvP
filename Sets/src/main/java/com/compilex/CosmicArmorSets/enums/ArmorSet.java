/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicArmorSets.enums;

import com.compilex.CosmicArmorSets.enums.armorsets.*;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ArmorSet {
    PHANTOM(new PhantomArmorSet(), ChatColor.RED, CC.RedB + "Phantom", Lists.newArrayList("+5% DMG")),
    YETI(new YetiArmorSet(), ChatColor.AQUA, CC.AquaB + "Yeti", Lists.newArrayList("-2.5% Incoming DMG", "+2.5% Outgoing DMG")),
    MOTHER_OF_YIJKI(new MotherOfYijki(), ChatColor.WHITE, CC.WhiteB + "Mother of Yijki", Lists.newArrayList("-5% Incoming DMG", "20% Revenge of Yijki Ability")),
    RANGER(new RangerArmorSet(), ChatColor.GREEN, CC.GreenB + "Ranger", Lists.newArrayList("-5% Incoming Bow DMG", "+6% Outgoing Bow DMG", "20% Immune to Teleblock")),
    SUPREME(new SupremeArmorSet(), ChatColor.DARK_RED, CC.DarkRedB + "Supreme", Lists.newArrayList("+3% Outgoing DMG")),
    DIMENSIONAL_TRAVELER(new DimensionalTravelerSet(), ChatColor.DARK_PURPLE, CC.DarkPurpleB + "Dimensional Traveler", Lists.newArrayList("+7.5% Outgoing DMG", "20% Dimensional Shift Ability")),
    DRAGON_SLAYER(new DragonSlayerArmorSet(), ChatColor.YELLOW, CC.YellowB + "Dragon Slayer", Lists.newArrayList("+3% PvP DMG", "-5% Incoming DMG", "10% Silence Immunity", "10% Freeze Immunity")),
    ENGINEER(new EngineerSet(), ChatColor.GOLD, CC.GoldB + "Engineer", Lists.newArrayList("-9% Incoming DMG", "20% B-2 Bomber Passive Ability")),
    KOTH(new KothArmorSet(), ChatColor.AQUA, CC.WhiteB + CC.Magic + ";" + CC.RedB + " K" + CC.GoldB + "." + CC.YellowB + "O" + CC.GreenB + "." + CC.AquaB + "T" + CC.DarkPurpleB + "." + CC.LightPurpleB + "H " + CC.WhiteB + CC.Magic + ";", Lists.newArrayList("+5% PvP DMG", "+12.5% PvE DMG")),
    ASTRO(new AstroSet(), ChatColor.GREEN, CC.GreenB + "Astro", Lists.newArrayList("+7% Outgoing DMG", "-3% Incoming DMG", "Astro Cage Ability",  "+5% Monopoly Chance")),
    LUNA(new LunaSet(), ChatColor.GRAY, CC.GrayB + "Luna", Lists.newArrayList("+6% Outgoing DMG", "-4% Incoming DMG", "+10% Soul Enchant Damage", "Ultra Luna Strike Ability")),
    OMNI(new OmniArmorSet(), ChatColor.DARK_BLUE, CC.DarkBlueB + "Omni", Lists.newArrayList("")),
    GRINDER(new GrinderArmorSet(), ChatColor.DARK_GRAY, CC.DarkGrayB + "Grinder", Lists.newArrayList("")),
    ANCIENT(new AncientArmorSet(), ChatColor.DARK_AQUA, CC.DarkAquaB + "Ancient", Lists.newArrayList("+7% Outgoing DMG", "-3% Incoming Damage", "10% Leviathan's Curse Ability"));


    public ItemStack helmet;
    public ItemStack chestplate;
    public ItemStack leggings;
    public ItemStack boots;
    public ItemStack[] armorSetItems;
    public ArmorSetInfo armorSetInfo;
    private ChatColor color;
    private String displayName;
    private List<String> crystalDescription;

    ArmorSet(ArmorSetInfo info, ChatColor color, String displayName, List<String> crystalDescription) {
        this.armorSetInfo = info;
        this.color = color;
        this.crystalDescription = crystalDescription;
        this.displayName = displayName;
    }

    ArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        ItemStack[] arritemStack = new ItemStack[4];
        this.armorSetItems = arritemStack;
        arritemStack[0] = this.helmet;
        this.armorSetItems[1] = this.chestplate;
        this.armorSetItems[2] = this.leggings;
        this.armorSetItems[3] = this.boots;
    }

    public static ItemStack createRangerBow() {
        return ArmorSet.createRangerBow(true);
    }

    public static ItemStack createRangerBow(boolean transmog) {
        ItemStack item = new ItemBuilder(Material.BOW, CC.GreenB + "Ranger Bow", CC.Green + "A finely crafted bow imbued", CC.Green + "with the energy of the Sun.", "", CC.GreenB + "RANGER WEAPON BONUS", CC.GreenB + "* " + CC.Green + "Ranger Bow grants 30% increased bow damage.", CC.GreenB + "* " + CC.Green + "Enemies deal 25% less damage to you.", CC.GrayI + "(Requires all 4 ranger armor items.)").build();
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 2);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        ArmorSet.addMaxedEnchant(item, "Sniper");
        ArmorSet.addMaxedEnchant(item, "Lethal Sniper");
        ArmorSet.addMaxedEnchant(item, "Silence");
        ArmorSet.addMaxedEnchant(item, "Rage");
        ArmorSet.addMaxedEnchant(item, "Eagle Eye");
        ArmorSet.addMaxedEnchant(item, "Arrow Lifesteal");
        ArmorSet.addMaxedEnchant(item, "Hellfire");
        ArmorSet.addMaxedEnchant(item, "Piercing");
        ArmorSet.addMaxedEnchant(item, "Blind");
        ArmorSet.addMaxedEnchant(item, "Venom");
        ArmorSet.addMaxedEnchant(item, "Snare");
        ArmorSet.addMaxedEnchant(item, "Virus");
        ArmorSet.addMaxedEnchant(item, "Healing");
        return transmog ? EnchantmentBook.applyTransmogScroll(item) : item;
    }

    public static boolean isRangerBow(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getType() == Material.BOW && item.getItemMeta().getDisplayName().startsWith(CC.GreenB + "Ranger Bow");
    }

    public static void addEnchantmentLevel(ItemStack item, String enchantName, int level) {
        CustomEnchantment enchant = EnchantmentAPI.getEnchantment(enchantName);
        if (enchant != null) {
            enchant.addToItem(item, level);
        }
    }

    public static void addMaxedEnchant(ItemStack item, String enchantName) {
        CustomEnchantment enchant = EnchantmentAPI.getEnchantment(enchantName);
        if (enchant != null) {
            enchant.addToItem(item, enchant.getMaxLevel());
        }
    }

    private static ItemStack addNearlyMaxedEnchantmentLevel(ItemStack item, String enchantName) {
        CustomEnchantment enchant = EnchantmentAPI.getEnchantment(enchantName);
        if (enchant.heroicEnchantment) {
            enchant.addToItem(item, 1 + ThreadLocalRandom.current().nextInt(enchant.getMaxLevel()));
        } else {
            enchant.addToItem(item, Math.min(enchant.getMaxLevel(), Math.max(1, enchant.getMaxLevel() - 2) + ThreadLocalRandom.current().nextInt(3)));
        }
        return item;
    }

    public static ArmorSet fromName(String name) {
        for (ArmorSet set : ArmorSet.values()) {
            if (!set.name().equalsIgnoreCase(name)) continue;
            return set;
        }
        return null;
    }

    public static ItemStack createCrystal(List<ArmorSet> armorSet, double applyChance) {
        return new CustomItemBuilder("bonusArmorCrystal", armorSet, applyChance).build();
    }

    public static List<ArmorSet> getArmorCrystalBonusesFromCrystal(NBTTagCompoundBuilder wrapper) {
        if (wrapper.hasKey("bonus")) {
            ArmorSet set = ArmorSet.fromName(wrapper.getString("bonus"));
            if (set != null) {
                List<ArmorSet> retr = Arrays.asList(set);
                return retr;
            }
        } else if (wrapper.hasKey("bonuses")) {
            ArrayList retr2 = Lists.newArrayList();
            NBTTagListBuilder list = new NBTTagListBuilder(wrapper.getNBTBase("bonuses"));
            for (int i = 0; i < list.size(); ++i) {
                ArmorSet from;
                String bonus = list.getStringAt(i);
                if (bonus == null || bonus.isEmpty() || (from = ArmorSet.fromName(bonus)) == null) continue;
                retr2.add(from);
            }
            return retr2;
        }
        return Collections.emptyList();
    }

    public static List<ArmorSet> getFromArmorBonusCrystal(NBTWrapper wrapper) {
        NBTTagCompoundBuilder cosmicData = wrapper.getCompound("conaxData");
        if (cosmicData == null) {
            return null;
        }
        ArrayList retr = Lists.newArrayList();
        if (cosmicData.hasKey("crystalBonus")) {
            String upgrade = cosmicData.getString("crystalBonus");
            if (upgrade == null) {
                return null;
            }
            ArmorSet from = ArmorSet.fromName(upgrade);
            if (from != null) {
                retr.add(from);
            }
        } else if (cosmicData.hasKey("crystalBonuses")) {
            NBTTagListBuilder list = new NBTTagListBuilder(cosmicData.getNBTBase("crystalBonuses"));
            for (int i = 0; i < list.size(); ++i) {
                ArmorSet from2;
                String bonus = list.getStringAt(i);
                if (bonus == null || bonus.isEmpty() || (from2 = ArmorSet.fromName(bonus)) == null) continue;
                retr.add(from2);
            }
        }
        return retr;
    }

    public static List<ArmorSet> getFromArmorBonusCrystal(ItemStack item) {
        if (item == null) {
            return null;
        }
        return ArmorSet.getFromArmorBonusCrystal(new NBTWrapper(item));
    }

    public static ItemStack applyCrystalToItem(List<ArmorSet> set, ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("conaxData");
        NBTTagListBuilder nbtTagListBuilder = new NBTTagListBuilder();
        StringBuilder sb = new StringBuilder();
        set.forEach(s -> {
            nbtTagListBuilder.addString(s.name());
            sb.append(s.getDisplayName()).append(CC.Gray).append(", ");
        });
        data.setNBTBase("crystalBonuses", nbtTagListBuilder.build());
        wrapper.setNBTBase("conaxData", data.get());
        item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore() == null ? Lists.newArrayList() : im.getLore();
        String str = sb.toString();
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        lore.add(CC.GoldB + "Armor Crystal (" + str + CC.GoldB + ")");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack removeCrystalFromItem(NBTWrapper wrapper) {
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("conaxData");
        data.remove("crystalBonus");
        data.remove("crystalBonuses");
        wrapper.setNBTBase("conaxData", data.get());
        ItemStack item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();
        if (lore != null) {
            lore.removeIf(s -> s.startsWith(CC.GoldB + "Armor Crystal ("));
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack removeCrystalFromItem(ItemStack item) {
        return ArmorSet.removeCrystalFromItem(new NBTWrapper(item));
    }

    public static ArmorSet getArmorSetFromItem(ItemStack i) {
        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
            for (int reverseIndex = i.getItemMeta().getLore().size() - 1; reverseIndex >= 0; --reverseIndex) {
                String s = i.getItemMeta().getLore().get(reverseIndex);
                if (!s.contains("SET BONUS") || s.contains("~")) continue;
                String set = ChatColor.stripColor(s.split(" ")[0]);
                if (set.equals("YIJKI")) {
                    set = "MOTHER_OF_YIJKI";
                } else if (set.equals("TRAVELER")) {
                    set = "DIMENSIONAL_TRAVELER";
                } else if (set.equalsIgnoreCase("DRAGON")) {
                    set = "DRAGON_SLAYER";
                }
                try {
                    return ArmorSet.valueOf(set);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public ItemStack getRandomPeice(boolean applyTransmog) {
        return this.getArmorPeice(ThreadLocalRandom.current().nextInt(this.armorSetItems.length), applyTransmog);
    }

    public ItemStack getArmorPeice(int index, boolean applyTransmog) {
        if (index >= this.armorSetItems.length) {
            index = 0;
        }
        if (this.armorSetInfo != null) {
            if (index == 0) {
                return this.armorSetInfo.getHelmet(applyTransmog);
            }
            if (index == 1) {
                return this.armorSetInfo.getChestplate(applyTransmog);
            }
            if (index == 2) {
                return this.armorSetInfo.getLeggings(applyTransmog);
            }
            if (index == 3) {
                return this.armorSetInfo.getBoots(applyTransmog);
            }
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(this.armorSetItems[index]) : this.armorSetItems[index];
    }

    public String getFirstDisplayNameLetter() {
        return this.color + CC.Bold + this.name().charAt(0);
    }

    public ArmorSetInfo getArmorSetInfo() {
        return this.armorSetInfo;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getCrystalDescription() {
        return this.crystalDescription;
    }
}

