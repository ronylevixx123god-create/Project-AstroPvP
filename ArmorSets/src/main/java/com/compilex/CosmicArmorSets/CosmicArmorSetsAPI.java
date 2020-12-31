package com.compilex.CosmicArmorSets;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.CachedArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.minecraft.server.v1_7_R4.NBTBase;
import net.minecraft.util.io.netty.util.internal.ThreadLocalRandom;
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

public class CosmicArmorSetsAPI {
    public static HashMap<UUID, ArmorSet> oldArmorSet = new HashMap<>();

    public static ItemStack createRandomArmorSetUpgrade() {
        String star = CC.GoldB + "* " + CC.Gold;
        ItemStack retr = (new ItemBuilder(Material.CHEST, CC.GoldB + "Random Heroic Upgrade " + CC.Gray + "(Right Click)", new String[]{CC.Gray + "Click to receive a random " + CC.GoldU + "Heroic Armorset Upgrade", CC.Gray + "from one of the following armor sets!", "", CC.GoldB + "Available Armor Sets", star + ArmorSet.YETI.getDisplayName(), star + ArmorSet.MOTHER_OF_YIJKI.getDisplayName(), star + ArmorSet.SUPREME.getDisplayName(), star + ArmorSet.RANGER.getDisplayName(), star + ArmorSet.PHANTOM.getDisplayName(), star + ArmorSet.DIMENSIONAL_TRAVELER.getDisplayName()})).build();
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
            retr = ItemID.assignItemID(null, retr);
        return (new NBTWrapper(retr)).setString("cosmicType", "RANDOM_ARMOR_UPGRADE").build();
    }

    public static ArmorSet getRandomArmorSetFromUpgrade() {
        List<ArmorSet> avail;
        if (Math.random() <= 0.7D) {
            avail = Lists.newArrayList(new ArmorSet[]{ArmorSet.YETI, ArmorSet.SUPREME, ArmorSet.RANGER});
        } else {
            avail = Lists.newArrayList(new ArmorSet[]{ArmorSet.PHANTOM, ArmorSet.MOTHER_OF_YIJKI, ArmorSet.DIMENSIONAL_TRAVELER});
        }
        return avail.get(ThreadLocalRandom.current().nextInt(avail.size()));
    }

    //bs

    public static ItemStack createArmorSetUpgrade(ArmorSet armorset, double successRate) {
        if (armorset == ArmorSet.KOTH)
            armorset = ArmorSet.RANGER;
        String armorLore = (armorset == null) ? (CC.Gray + "armor set") : (armorset.getDisplayName() + CC.Gray + " armor");
        ItemStack retr = (new NBTWrapper((new ItemBuilder(Material.INK_SACK, CC.GoldB + "Heroic " + ((armorset != null) ? (CC.Gold + "(" + armorset.getDisplayName() + CC.Gold + ") " + CC.GoldB) : "Armor") + "Upgrade", new String[]{"", CC.Gray + "Apply to any " + armorLore, CC.Gray + "for a " + CC.Gold + (int) successRate + "%" + CC.Gray + " chance to imbue it", CC.Gray + "with the power of Heroic Armor!", "", CC.Gray + "This will increase the armor's base", CC.Gray + "stats as well as add a " + CC.Gold + "12.5% negation", CC.Gray + "to enemy " + CC.Gold + "Infinite Luck" + CC.Gray + " enchantments."})).data((short) DyeColor.YELLOW.getDyeData()).build())).setString("cosmicType", "HEROIC_ARMOR_UPGRADE").setNBTBase("cosmicData", (NBTBase) (new NBTTagCompoundBuilder()).setDouble("success", Double.valueOf(successRate)).setString("setType", (armorset == null) ? "ANY" : armorset.name()).get()).build();
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
            retr = ItemID.assignItemID(null, retr);
        return retr;
    }

    public static ItemStack[] getArmorSet(ArmorSet as) {
        ItemStack[] item = new ItemStack[4];
        item[0] = as.getArmorSetInfo().getHelmet(true);
        item[1] = as.getArmorSetInfo().getChestplate(true);
        item[2] = as.getArmorSetInfo().getLeggings(true);
        item[3] = as.getArmorSetInfo().getBoots(true);
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
            for (ItemStack i : item) {
                if (ItemID.hasItemID(i))
                    ItemID.removeItemID(i);
            }
        return item;
    }

    public static ItemStack[] createAbilityArmorSet(AbilityArmorSet as, boolean applyIID) {
        ItemStack[] item = new ItemStack[4];
        item[0] = as.getArmorSetInfo().getHelmet(true);
        item[1] = as.getArmorSetInfo().getChestplate(true);
        item[2] = as.getArmorSetInfo().getLeggings(true);
        item[3] = as.getArmorSetInfo().getBoots(true);
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
            for (ItemStack i : item) {
                if (ItemID.hasItemID(i))
                    ItemID.removeItemID(i);
                if (applyIID)
                    ItemID.assignItemID(null, i);
            }
        return item;
    }

    public static ItemStack createHeroicWeapon(HeroicWeaponType type) {
        return createHeroicWeapon(type, true);
    }

    public static ItemStack createHeroicWeapon(HeroicWeaponType type, boolean addLore) {
        return convertIntoHeroicWeapon(type, (new ItemBuilder(type.getMaterial())).name(type.getName()).build(), addLore);
    }

    public static ItemStack convertIntoHeroicWeapon(HeroicWeaponType type, ItemStack retr, boolean addLore) {
        retr.setType(type.getMaterial());
        retr = createHeroicWeaponNBT(new NBTWrapper(retr), type).build();
        if (addLore) {
            ItemMeta im = retr.getItemMeta();
            List<String> existing = im.hasLore() ? im.getLore() : Lists.newArrayList();
            existing.add("");
            existing.add(CC.Gray + type.getDurability() + " Durability");
            existing.add(CC.DarkRed + "+" + getTrimmedNumber(type.getAttackModifier()) + " Bonus Attack Damage");
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
        if (modString.endsWith(".0"))
            modString = modString.substring(0, modString.length() - 2);
        return modString;
    }

    public static NBTWrapper createHeroicWeaponNBT(NBTWrapper wrapper, HeroicWeaponType type) {
        NBTTagCompoundBuilder compoundBuilder = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        compoundBuilder.setString("type", type.name()).setInt("durability", Integer.valueOf(type.getDurability())).setDouble("dmgIncr", Double.valueOf(type.getAttackModifier())).setInt("maxDurability", Integer.valueOf(type.getDurability())).get();
        return wrapper.setString("cosmicType", "HEROIC_WEAPON").setNBTBase("cosmicData", (NBTBase) compoundBuilder.get());
    }

    public static boolean isHeroicArmor(ItemStack item) {
        if (item == null)
            return false;
        NBTWrapper wrapper = new NBTWrapper(item);
        return wrapper.getString("cosmicType").equals("HEROIC_ARMOR");
    }

    public static boolean hasInfiniteLuck(Player player, int level, Player attacker) {
        if (!player.hasMetadata("infiniteLucky"))
            return false;
        int luckyLevel = ((MetadataValue) player.getMetadata("infiniteLucky").get(0)).asInt();
        boolean canNegate = (luckyLevel >= level);
        if (canNegate && attacker != null) {
            double chanceToNegate = 0.0D;
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null) {
                    Material type = item.getType();
                    if ((type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET || type == Material.LEATHER_BOOTS || type == Material.LEATHER_LEGGINGS) && isHeroicArmor(item))
                        chanceToNegate += 12.5D;
                }
            }
            if (chanceToNegate * 0.01D >= Math.random())
                return false;
        }
        return canNegate;
    }

    public static double getPoltergeistChance(Player player) {
        List<MetadataValue> values = player.getMetadata("poltergeist");
        if (values == null || values.isEmpty())
            return 0.0D;
        return ((MetadataValue) values.get(0)).asInt() * 12.5D;
    }

    public static ItemStack getWeaponFromArmorSet(ArmorSet set) {
        if (set.getArmorSetInfo() instanceof WeaponedArmorSet)
            return ((WeaponedArmorSet) set.getArmorSetInfo()).getWeapon(true);
        return null;
    }

    public static ArmorSet getCachedArmorSet(Player player) {
        if (player.hasMetadata("armorSet") && player.hasMetadata("armorLock") && player.hasMetadata("armorSet_cacheTimeout") && System.currentTimeMillis() < ((MetadataValue) player.getMetadata("armorSet_cacheTimeout").get(0)).asLong()) {
            String setName = ((MetadataValue) player.getMetadata("armorSet").get(0)).asString();
            return (setName != null && setName.length() > 0) ? ArmorSet.valueOf(setName) : null;
        }
        return null;
    }

    public static AbilityArmorSet getCachedAbilityArmorSet(Player player) {
        CachedArmorSet<AbilityArmorSet> calculated = CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().get(player);
        if (calculated != null)
            return (AbilityArmorSet) calculated.getSet();
        return null;
    }

    public static AbilityArmorSet getAbilityArmorSet(Player player) {
        CachedArmorSet<AbilityArmorSet> calculated = CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().get(player);
        if (calculated != null && System.currentTimeMillis() - calculated.getLastUpdated() < 1000L)
            return (AbilityArmorSet) calculated.getSet();
        AbilityArmorSet found = getAbilityArmorSet(player.getInventory().getArmorContents());
        if (calculated != null && found != null && found == calculated.getSet()) {
            calculated.setLastUpdated(System.currentTimeMillis());
            return found;
        }
        if (found != null) {
            if (calculated != null) {
                ((AbilityArmorSet) calculated.getSet()).getArmorSetInfo().onUnequip(player);
                found.getArmorSetInfo().onEquip(player);
                calculated.setSet(found);
            } else {
                CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().put(player, new CachedArmorSet(found, System.currentTimeMillis()));
                found.getArmorSetInfo().onEquip(player);
            }
            found.getArmorSetInfo().getEquipDescription().forEach(player::sendMessage);
        } else if (calculated != null) {
            ((AbilityArmorSet) calculated.getSet()).getArmorSetInfo().onUnequip(player);
            CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().remove(player);
        }
        return found;
    }

    public static AbilityArmorSet getAbilityArmorSetFromItem(ItemStack item) {
        String armorSet = getAbilityArmorSetStringFromItem(item);
        return AbilityArmorSet.fromName(armorSet);
    }

    public static String getAbilityArmorSetStringFromItem(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        if (wrapper.hasKey("cosmicData")) {
            NBTTagCompoundBuilder cosmicData = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
            String abilityArmorSet = cosmicData.getString("abilitySet");
            if (abilityArmorSet == null || abilityArmorSet.isEmpty())
                return null;
            return abilityArmorSet;
        }
        return null;
    }

    public static AbilityArmorSet getAbilityArmorSet(ItemStack[] armor) {
        String lastArmorSetFound = null;
        int matched = 0;
        int omni = 0;
        for (ItemStack item : armor) {
            if (item != null && item.getType() != Material.AIR) {
                String armorSetFound = getAbilityArmorSetStringFromItem(item);
                if (armorSetFound != null && !armorSetFound.isEmpty()) {
                    if (lastArmorSetFound == null && !armorSetFound.equalsIgnoreCase("OMNI"))
                        lastArmorSetFound = armorSetFound;
                    if (!armorSetFound.equals(lastArmorSetFound) && !armorSetFound.equalsIgnoreCase("OMNI"))
                        return null;
                    if (armorSetFound.equalsIgnoreCase("OMNI")) {
                        omni++;
                    } else {
                        matched++;
                    }
                } else {
                    return null;
                }
            }
        }
        if (lastArmorSetFound != null && matched + omni == 4)
            return AbilityArmorSet.fromName(lastArmorSetFound);
        return null;
    }

    public static Map<ArmorSet, Integer> getBonusCrystalsEquipped(Player player) {
        Map<ArmorSet, Integer> found = null;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                List<ArmorSet> sets = ArmorSet.getFromArmorBonusCrystal(armor);
                if (sets != null && !sets.isEmpty()) {
                    if (found == null)
                        found = new HashMap<>();
                    for (ArmorSet set : sets) {
                        Integer current = found.get(set);
                        found.put(set, Integer.valueOf((current == null) ? 1 : (current.intValue() + 1)));
                    }
                }
            }
        }
        return (found == null) ? Collections.<ArmorSet, Integer>emptyMap() : found;
    }

    public static Map<ArmorSet, Integer> getBonusCrystalsEquippedWithCorruptionIncluded(Player player, Entity other) {
        Map<ArmorSet, Integer> found = null;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                List<ArmorSet> sets = ArmorSet.getFromArmorBonusCrystal(armor);
                if (other instanceof Player && (AstroItemSkinsAPI.hasSkinEquipped((Player) other, SkinType.CORRUPT_AMULET_CORRUPTION) && armor.getType().toString().endsWith("_CHESTPLATE"))) {
                    if (!AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.LEVIATHAN_TROPHY)) {
                        sets.removeAll(ArmorSet.getFromArmorBonusCrystal(((Player) other).getInventory().getChestplate()));
                    } else {

                    }
                }
                if (sets != null && !sets.isEmpty()) {
                    if (found == null)
                        found = new HashMap<>();
                    for (ArmorSet set : sets) {
                        Integer current = found.get(set);
                        found.put(set, Integer.valueOf((current == null) ? 1 : (current.intValue() + 1)));
                    }
                }
            }
        }
        return (found == null) ? Collections.<ArmorSet, Integer>emptyMap() : found;
    }

    public static ArmorSet getArmorSet(Player p) {
        boolean armorSetMetadata = p.hasMetadata("armorSet");
        if (armorSetMetadata && p.hasMetadata("armorLock") && p.hasMetadata("armorSet_cacheTimeout") && System.currentTimeMillis() < ((MetadataValue) p.getMetadata("armorSet_cacheTimeout").get(0)).asLong()) {
            String setName = ((MetadataValue) p.getMetadata("armorSet").get(0)).asString();
            return (setName != null && setName.length() > 0) ? ArmorSet.valueOf(setName) : null;
        }
        ArmorSet previous = null;
        if (armorSetMetadata) {
            String setName = ((MetadataValue) p.getMetadata("armorSet").get(0)).asString();
            if (setName != null && setName.length() > 0)
                previous = ArmorSet.valueOf(setName);
        }
        ArmorSet as = getArmorSet(p.getEquipment().getArmorContents());
        if (as != null) {
            if (previous != null)
                previous.getArmorSetInfo().onUnequip(p);
            as.getArmorSetInfo().onEquip(p);
            if (previous == null || previous != as)
                as.getArmorSetInfo().getEquipDescription().forEach(p::sendMessage);
        } else if (previous != null) {
            previous.getArmorSetInfo().onUnequip(p);
        }
        p.setMetadata("armorSet", (MetadataValue) new FixedMetadataValue((Plugin) CosmicArmorSetsPlugin.get_(), (as != null) ? as.name() : null));
        p.setMetadata("armorSet_cacheTimeout", (MetadataValue) new FixedMetadataValue((Plugin) CosmicArmorSetsPlugin.get_(), Long.valueOf(System.currentTimeMillis() + 30000L)));
        p.setMetadata("armorLock", (MetadataValue) new FixedMetadataValue((Plugin) Arkkit.get(), Long.valueOf(System.currentTimeMillis())));
        return as;
    }

    public static ArmorSet getArmorSet(ItemStack[] items) {
        int setCount = 0;
        int omniCount = 0;
        ArmorSet armorSet = null;
        for (ItemStack i : items) {
            ArmorSet foundSet = ArmorSet.getArmorSetFromItem(i);
            if (foundSet != null) {
                boolean isOmni = ChatColor.stripColor(foundSet.getDisplayName()).equalsIgnoreCase("omni");
                if (armorSet == null && !isOmni)
                    armorSet = foundSet;
                if (armorSet != foundSet && !isOmni)
                    return null;
                if (isOmni) {
                    omniCount++;
                } else {
                    setCount++;
                }
            }
        }
        if (setCount + omniCount == 4 && armorSet != null)
            return armorSet;
        return null;
    }

    public static ItemStack createHeroicPeice(HeroicArmorType type) {
        return createHeroicPeice(type.getMaterial(), CC.DarkRedB + "Heroic " + type.getName(), type.getArmorModifier(), type.getDurability());
    }

    public static ItemStack createHeroicPeice(Material type, String name, int armorModifier, int durability) {
        if (name == null)
            name = CC.DarkRedB + "Heroic " + StringUtils.capitaliseAllWords(type.name().split("_")[1].toLowerCase());
        List<String> lore = Lists.newArrayList(new String[]{"", CC.Gray + "+" + armorModifier + " Armor Value", CC.Gray + durability + " Durability", CC.DarkRed + "This armor is stronger than diamond."});
        ItemStack item = (new ItemBuilder(type, name, new String[0])).lore(lore).build();
        ItemMeta im = item.getItemMeta();
        if (im instanceof LeatherArmorMeta) {
            LeatherArmorMeta lm = (LeatherArmorMeta) im;
            lm.setColor(Color.RED);
            item.setItemMeta((ItemMeta) lm);
        }
        if (type.name().endsWith("_BOOTS"))
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("cosmicType", "HEROIC_ARMOR").setNBTBase("cosmicData", (NBTBase) wrapper.getCompoundOrCreate("cosmicData").setInt("durability", Integer.valueOf(durability)).setInt("armorModifier", Integer.valueOf(armorModifier)).setInt("maxDurability", Integer.valueOf(durability)).get());
        return wrapper.build();
    }

    public static ItemStack convertItemToHeroic(ItemStack item, HeroicArmorType type, boolean addLore) {
        item.setType(type.getMaterial());
        int modifier = type.getArmorModifier();
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("cosmicData");
        if (builder.hasKey("abilitySet"))
            modifier = (int) Math.ceil(modifier / 2.0D);
        wrapper.setString("cosmicType", "HEROIC_ARMOR");
        wrapper.setNBTBase("cosmicData", (NBTBase) builder.setInt("durability", Integer.valueOf(type.getDurability())).setInt("maxDurability", Integer.valueOf(type.getDurability())).setInt("armorModifier", Integer.valueOf(modifier)).get());
        ItemStack finished = wrapper.build();
        ItemMeta im = finished.getItemMeta();
        if (addLore) {
            List<String> lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
            String strongerLine = CC.DarkRed + "This armor is stronger than diamond.";
            if (!lore.contains(strongerLine)) {
                lore.add("");
                lore.add(CC.Gray + "+" + modifier + " Armor Value");
                lore.add(CC.Gray + type.getDurability() + " Durability");
                lore.add(strongerLine);
            }
            im.setLore(lore);
        }
        if (im instanceof LeatherArmorMeta)
            ((LeatherArmorMeta) im).setColor(Color.RED);
        item.setItemMeta(im);
        return item;
    }
}
