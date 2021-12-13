/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  me.astropvp.astropvpskins.AstroItemSkinsAPI
 *  me.astropvp.astropvpskins.me.astropvp.cratekeys.enums.SkinType
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  net.minecraft.util.io.netty.util.internal.ThreadLocalRandom
 *  org.apache.commons.lang.StringUtils
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.me.astropvp.cratekeys.inventory.meta.ItemMeta
 *  org.bukkit.me.astropvp.cratekeys.inventory.meta.LeatherArmorMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicArmorSets;

import com.compilex.CosmicArmorSets.enums.*;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CosmicArmorSetsAPI {
    public static HashMap oldArmorSet = new HashMap();

    public static ItemStack createRandomArmorSetUpgrade() {
        String star = CC.GoldB + "* " + CC.Gold;
        ItemStack retr = new ItemBuilder(Material.CHEST, CC.GoldB + "Random Heroic Upgrade " + CC.Gray + "(Right Click)", CC.Gray + "Click to receive a random " + CC.GoldU + "Heroic Armorset Upgrade", CC.Gray + "from one of the following armor sets!", "", CC.GoldB + "Available Armor Sets", star + ArmorSet.YETI.getDisplayName(), star + ArmorSet.MOTHER_OF_YIJKI.getDisplayName(), star + ArmorSet.SUPREME.getDisplayName(), star + ArmorSet.RANGER.getDisplayName(), star + ArmorSet.PHANTOM.getDisplayName(), star + ArmorSet.DIMENSIONAL_TRAVELER.getDisplayName()).build();
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            retr = ItemID.assignItemID(null, retr);
        }
        return new NBTWrapper(retr).setString("cosmicType", "RANDOM_ARMOR_UPGRADE").build();
    }

    public static ArmorSet getRandomArmorSetFromUpgrade() {
        ArrayList avail = Math.random() <= 0.7 ? Lists.newArrayList( new ArmorSet[]{ArmorSet.YETI, ArmorSet.SUPREME, ArmorSet.RANGER}) : Lists.newArrayList( new ArmorSet[]{ArmorSet.PHANTOM, ArmorSet.MOTHER_OF_YIJKI, ArmorSet.DIMENSIONAL_TRAVELER});
        return (ArmorSet) avail.get(ThreadLocalRandom.current().nextInt(avail.size()));
    }

    public static ItemStack createArmorSetUpgrade(ArmorSet armorset, double successRate) {
        if (armorset == ArmorSet.KOTH) {
            armorset = ArmorSet.RANGER;
        }
        String armorLore = armorset == null ? CC.Gray + "armor set" : armorset.getDisplayName() + CC.Gray + " armor";
        ItemStack retr = new NBTWrapper(new ItemBuilder(Material.INK_SACK, CC.GoldB + "Heroic " + (armorset != null ? CC.Gold + "(" + armorset.getDisplayName() + CC.Gold + ") " + CC.GoldB : "Armor") + "Upgrade", "", CC.Gray + "Apply to any " + armorLore, CC.Gray + "for a " + CC.Gold + (int) successRate + "%" + CC.Gray + " chance to imbue it", CC.Gray + "with the power of Heroic Armor!", "", CC.Gray + "This will increase the armor's base", CC.Gray + "stats as well as add a " + CC.Gold + "12.5% negation", CC.Gray + "to enemy " + CC.Gold + "Infinite Luck" + CC.Gray + " enchantments.").data(DyeColor.YELLOW.getDyeData()).build()).setString("cosmicType", "HEROIC_ARMOR_UPGRADE").setNBTBase("conaxData", new NBTTagCompoundBuilder().setDouble("success", Double.valueOf(successRate)).setString("setType", armorset == null ? "ANY" : armorset.name()).get()).build();
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            retr = ItemID.assignItemID(null, retr);
        }
        return retr;
    }

    public static ItemStack[] getArmorSet(ArmorSet as) {
        ItemStack[] item = new ItemStack[]{as.getArmorSetInfo().getHelmet(true), as.getArmorSetInfo().getChestplate(true), as.getArmorSetInfo().getLeggings(true), as.getArmorSetInfo().getBoots(true)};
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            for (ItemStack i : item) {
                if (!ItemID.hasItemID(i)) continue;
                ItemID.removeItemID(i);
            }
        }
        return item;
    }

    public static ItemStack[] createAbilityArmorSet(AbilityArmorSet as, boolean applyIID) {
        ItemStack[] item = new ItemStack[]{as.getArmorSetInfo().getHelmet(true), as.getArmorSetInfo().getChestplate(true), as.getArmorSetInfo().getLeggings(true), as.getArmorSetInfo().getBoots(true)};
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            for (ItemStack i : item) {
                if (ItemID.hasItemID(i)) {
                    ItemID.removeItemID(i);
                }
                if (!applyIID) continue;
                ItemID.assignItemID(null, i);
            }
        }
        return item;
    }

    public static ItemStack createHeroicWeapon(HeroicWeaponType type) {
        return CosmicArmorSetsAPI.createHeroicWeapon(type, true);
    }

    public static ItemStack createHeroicWeapon(HeroicWeaponType type, boolean addLore) {
        return CosmicArmorSetsAPI.convertIntoHeroicWeapon(type, new ItemBuilder(type.getMaterial()).name(type.getName()).build(), addLore);
    }

    public static ItemStack convertIntoHeroicWeapon(HeroicWeaponType type, ItemStack retr, boolean addLore) {
        retr.setType(type.getMaterial());
        retr = CosmicArmorSetsAPI.createHeroicWeaponNBT(new NBTWrapper(retr), type).build();
        if (addLore) {
            ItemMeta im = retr.getItemMeta();
            List<String> existing = im.hasLore() ? im.getLore() : Lists.newArrayList();
            existing.add("");
            existing.add(CC.Gray + type.getDurability() + " Durability");
            existing.add(CC.DarkRed + "+" + CosmicArmorSetsAPI.getTrimmedNumber(type.getAttackModifier()) + " Bonus Attack Damage");
            existing.add(CC.DarkRed + "This weapon is stronger than diamond.");
            im.setLore(existing);
            retr.setItemMeta(im);
            if (type == HeroicWeaponType.AXE) {
                ArmorSet.addMaxedEnchant(retr, "Silence");
                ArmorSet.addMaxedEnchant(retr, "Lifesteal");
            }
        }
        return retr;
    }

    private static String getTrimmedNumber(double value) {
        String modString = String.valueOf(value);
        if (modString.endsWith(".0")) {
            modString = modString.substring(0, modString.length() - 2);
        }
        return modString;
    }

    public static NBTWrapper createHeroicWeaponNBT(NBTWrapper wrapper, HeroicWeaponType type) {
        NBTTagCompoundBuilder compoundBuilder = wrapper.hasKey("conaxData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData")) : new NBTTagCompoundBuilder();
        compoundBuilder.setString("type", type.name()).setInt("durability", type.getDurability()).setDouble("dmgIncr", type.getAttackModifier()).setInt("maxDurability", type.getDurability()).get();
        return wrapper.setString("cosmicType", "HEROIC_WEAPON").setNBTBase("conaxData", compoundBuilder.get());
    }

    public static boolean isHeroicArmor(ItemStack item) {
        if (item == null) {
            return false;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        return wrapper.getString("cosmicType").equals("HEROIC_ARMOR");
    }

    public static boolean hasInfiniteLuck(Player player, int level, Player attacker) {
        boolean canNegate;
        if (!player.hasMetadata("infiniteLucky")) {
            return false;
        }
        int luckyLevel = player.getMetadata("infiniteLucky").get(0).asInt();
        boolean bl = canNegate = luckyLevel >= level;
        if (canNegate && attacker != null) {
            double chanceToNegate = 0.0;
            for (ItemStack item : player.getInventory().getArmorContents()) {
                Material type;
                if (item == null || (type = item.getType()) != Material.LEATHER_CHESTPLATE && type != Material.LEATHER_HELMET && type != Material.LEATHER_BOOTS && type != Material.LEATHER_LEGGINGS || !CosmicArmorSetsAPI.isHeroicArmor(item))
                    continue;
                chanceToNegate += 12.5;
            }
            if (chanceToNegate * 0.01 >= Math.random()) {
                return false;
            }
        }
        return canNegate;
    }

    public static double getPoltergeistChance(Player player) {
        List values = player.getMetadata("poltergeist");
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        return (double) ((MetadataValue) values.get(0)).asInt() * 12.5;
    }

    public static ItemStack getWeaponFromArmorSet(ArmorSet set) {
        if (set.getArmorSetInfo() instanceof WeaponedArmorSet) {
            return ((WeaponedArmorSet) set.getArmorSetInfo()).getWeapon(true);
        }
        return null;
    }

    public static ArmorSet getCachedArmorSet(Player player) {
        if (player.hasMetadata("armorSet") && player.hasMetadata("armorLock") && player.hasMetadata("armorSet_cacheTimeout") && System.currentTimeMillis() < player.getMetadata("armorSet_cacheTimeout").get(0).asLong()) {
            String setName = player.getMetadata("armorSet").get(0).asString();
            return setName != null && setName.length() > 0 ? ArmorSet.valueOf(setName) : null;
        }
        return null;
    }

    public static AbilityArmorSet getCachedAbilityArmorSet(Player player) {
        CachedArmorSet<AbilityArmorSet> calculated = CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().get(player);
        if (calculated != null) {
            return calculated.getSet();
        }
        return null;
    }

    public static AbilityArmorSet getAbilityArmorSet(Player player) {
        CachedArmorSet<AbilityArmorSet> calculated = CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().get(player);
        if (calculated != null && System.currentTimeMillis() - calculated.getLastUpdated() < 1000L) {
            return calculated.getSet();
        }
        AbilityArmorSet found = CosmicArmorSetsAPI.getAbilityArmorSet(player.getInventory().getArmorContents());
        if (calculated != null && found != null && found == calculated.getSet()) {
            calculated.setLastUpdated(System.currentTimeMillis());
            return found;
        }
        if (found != null) {
            if (calculated != null) {
                calculated.getSet().getArmorSetInfo().onUnequip(player);
                found.getArmorSetInfo().onEquip(player);
                calculated.setSet(found);
            } else {
                CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().put(player, new CachedArmorSet<AbilityArmorSet>(found, System.currentTimeMillis()));
                found.getArmorSetInfo().onEquip(player);
            }
            found.getArmorSetInfo().getEquipDescription().forEach(((Player) player)::sendMessage);
        } else if (calculated != null) {
            calculated.getSet().getArmorSetInfo().onUnequip(player);
            CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().remove(player);
        }
        return found;
    }

    public static AbilityArmorSet getAbilityArmorSetFromItem(ItemStack item) {
        String armorSet = CosmicArmorSetsAPI.getAbilityArmorSetStringFromItem(item);
        return AbilityArmorSet.fromName(armorSet);
    }

    public static String getAbilityArmorSetStringFromItem(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        if (wrapper.hasKey("conaxData")) {
            NBTTagCompoundBuilder cosmicData = new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData"));
            String abilityArmorSet = cosmicData.getString("abilitySet");
            if (abilityArmorSet == null || abilityArmorSet.isEmpty()) {
                return null;
            }
            return abilityArmorSet;
        }
        return null;
    }

    public static AbilityArmorSet getAbilityArmorSet(ItemStack[] armor) {
        String lastArmorSetFound = null;
        int matched = 0;
        int omni = 0;
        for (ItemStack item : armor) {
            if (item == null || item.getType() == Material.AIR) continue;
            String armorSetFound = CosmicArmorSetsAPI.getAbilityArmorSetStringFromItem(item);
            if (armorSetFound != null && !armorSetFound.isEmpty()) {
                if (lastArmorSetFound == null && !armorSetFound.equalsIgnoreCase("OMNI")) {
                    lastArmorSetFound = armorSetFound;
                }
                if (!armorSetFound.equals(lastArmorSetFound) && !armorSetFound.equalsIgnoreCase("OMNI")) {
                    return null;
                }
                if (armorSetFound.equalsIgnoreCase("OMNI")) {
                    ++omni;
                    continue;
                }
                ++matched;
                continue;
            }
            return null;
        }
        if (lastArmorSetFound != null && matched + omni == 4) {
            return AbilityArmorSet.fromName(lastArmorSetFound);
        }
        return null;
    }

    public static Map<ArmorSet, Integer> getBonusCrystalsEquipped(Player player) {
        HashMap<ArmorSet, Integer> found = null;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            List<ArmorSet> sets;
            if (armor == null || armor.getType() == Material.AIR || (sets = ArmorSet.getFromArmorBonusCrystal(armor)) == null || sets.isEmpty())
                continue;
            if (found == null) {
                found = new HashMap<ArmorSet, Integer>();
            }
            Iterator<ArmorSet> iterator = sets.iterator();
            while (iterator.hasNext()) {
                ArmorSet set;
                Integer current = found.get(set = iterator.next());
                found.put(set, current == null ? 1 : current + 1);
            }
        }
        return found == null ? Collections.emptyMap() : found;
    }

    public static Map<ArmorSet, Integer> getBonusCrystalsEquippedWithCorruptionIncluded(Player player, Entity other) {
        HashMap<ArmorSet, Integer> found = null;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor == null || armor.getType() == Material.AIR) continue;
            List<ArmorSet> sets = ArmorSet.getFromArmorBonusCrystal(armor);
            if (other instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) other, SkinType.CORRUPT_AMULET_CORRUPTION) && armor.getType().toString().endsWith("_CHESTPLATE") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.RUINS_HELMET)) {
                if (sets == null) return null;
                if (ArmorSet.getFromArmorBonusCrystal(((Player) other).getInventory().getChestplate()) == null) return null;
                sets.removeAll(ArmorSet.getFromArmorBonusCrystal(((Player) other).getInventory().getChestplate()));
            }
            if (sets == null || sets.isEmpty()) continue;
            if (found == null) {
                found = new HashMap<ArmorSet, Integer>();
            }
            Iterator<ArmorSet> iterator = sets.iterator();
            while (iterator.hasNext()) {
                ArmorSet set;
                Integer current = found.get(set = iterator.next());
                found.put(set, current == null ? 1 : current + 1);
            }
        }
        return found == null ? Collections.emptyMap() : found;
    }

    public static ArmorSet getArmorSet(Player p) {
        ArmorSet as;
        block8:
        {
            ArmorSet previous;
            block6:
            {
                block7:
                {
                    String setName;
                    boolean armorSetMetadata = p.hasMetadata("armorSet");
                    if (armorSetMetadata && p.hasMetadata("armorLock") && p.hasMetadata("armorSet_cacheTimeout") && System.currentTimeMillis() < p.getMetadata("armorSet_cacheTimeout").get(0).asLong()) {
                        String setName2 = p.getMetadata("armorSet").get(0).asString();
                        return setName2 != null && setName2.length() > 0 ? ArmorSet.valueOf(setName2) : null;
                    }
                    previous = null;
                    if (armorSetMetadata && (setName = p.getMetadata("armorSet").get(0).asString()) != null && setName.length() > 0) {
                        previous = ArmorSet.valueOf(setName);
                    }
                    if ((as = CosmicArmorSetsAPI.getArmorSet(p.getEquipment().getArmorContents())) == null)
                        break block6;
                    if (previous != null) {
                        previous.getArmorSetInfo().onUnequip(p);
                    }
                    as.getArmorSetInfo().onEquip(p);
                    if (previous == null) break block7;
                    if (previous == as) break block8;
                }
                as.getArmorSetInfo().getEquipDescription().forEach(((Player) p)::sendMessage);
                break block8;
            }
            if (previous != null) {
                previous.getArmorSetInfo().onUnequip(p);
            }
        }
        p.setMetadata("armorSet", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), as != null ? as.name() : null));
        p.setMetadata("armorSet_cacheTimeout", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis() + 30000L));
        p.setMetadata("armorLock", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis()));
        return as;
    }

    public static ArmorSet getArmorSet(ItemStack[] items) {
        int setCount = 0;
        int omniCount = 0;
        ArmorSet armorSet = null;
        for (ItemStack i : items) {
            ArmorSet foundSet = ArmorSet.getArmorSetFromItem(i);
            if (foundSet == null) continue;
            boolean isOmni = ChatColor.stripColor(foundSet.getDisplayName()).equalsIgnoreCase("omni");
            if (armorSet == null && !isOmni) {
                armorSet = foundSet;
            }
            if (armorSet != foundSet && !isOmni) {
                return null;
            }
            if (isOmni) {
                ++omniCount;
                continue;
            }
            ++setCount;
        }
        if (setCount + omniCount == 4 && armorSet != null) {
            return armorSet;
        }
        return null;
    }

    public static ItemStack createHeroicPeice(HeroicArmorType type) {
        return CosmicArmorSetsAPI.createHeroicPeice(type.getMaterial(), CC.DarkRedB + "Heroic " + type.getName(), type.getArmorModifier(), type.getDurability());
    }

    public static ItemStack createHeroicPeice(Material type, String name, int armorModifier, int durability) {
        ArrayList lore;
        ItemStack item;
        ItemMeta im;
        if (name == null) {
            name = CC.DarkRedB + "Heroic " + StringUtils.capitaliseAllWords(type.name().split("_")[1].toLowerCase());
        }
        if ((im = (item = new ItemBuilder(type, name).lore(lore = Lists.newArrayList( new String[]{"", CC.Gray + "+" + armorModifier + " Armor Value", CC.Gray + durability + " Durability", CC.DarkRed + "This armor is stronger than diamond."})).build()).getItemMeta()) instanceof LeatherArmorMeta) {
            LeatherArmorMeta lm = (LeatherArmorMeta) im;
            lm.setColor(Color.RED);
            item.setItemMeta(lm);
        }
        if (type.name().endsWith("_BOOTS")) {
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        }
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("cosmicType", "HEROIC_ARMOR").setNBTBase("conaxData", wrapper.getCompoundOrCreate("conaxData").setInt("durability", Integer.valueOf(durability)).setInt("armorModifier", Integer.valueOf(armorModifier)).setInt("maxDurability", Integer.valueOf(durability)).get());
        return wrapper.build();
    }

    public static ItemStack convertItemToHeroic(ItemStack item, HeroicArmorType type, boolean addLore) {
        item.setType(type.getMaterial());
        int modifier = type.getArmorModifier();
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("conaxData");
        if (builder.hasKey("abilitySet")) {
            modifier = (int) Math.ceil((double) modifier / 2.0);
        }
        wrapper.setString("cosmicType", "HEROIC_ARMOR");
        wrapper.setNBTBase("conaxData", builder.setInt("durability", Integer.valueOf(type.getDurability())).setInt("maxDurability", Integer.valueOf(type.getDurability())).setInt("armorModifier", Integer.valueOf(modifier)).get());
        ItemStack finished = wrapper.build();
        ItemMeta im = finished.getItemMeta();
        if (addLore) {
            String strongerLine;
            List lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
            if (!lore.contains(strongerLine = CC.DarkRed + "This armor is stronger than diamond.")) {
                lore.add("");
                lore.add(CC.Gray + "+" + modifier + " Armor Value");
                lore.add(CC.Gray + type.getDurability() + " Durability");
                lore.add(strongerLine);
            }
            im.setLore(lore);
        }
        if (im instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) im).setColor(Color.RED);
        }
        item.setItemMeta(im);
        return item;
    }
}

