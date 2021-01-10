/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.libs.com.google.gson.internal.Pair
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.material.MaterialData
 */
package com.compilex.CosmicArmorSets.enums;

import com.compilex.CosmicArmorSets.enums.abilityarmorsets.ArchitectAbilitySet;
import com.compilex.CosmicArmorSets.enums.abilityarmorsets.DeathKnightAbilitySet;
import com.compilex.CosmicArmorSets.enums.abilityarmorsets.GhostAbilitySet;
import com.compilex.CosmicArmorSets.enums.abilityarmorsets.NecromancerAbilitySet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import javafx.util.Pair;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum AbilityArmorSet {
    GHOST("GHOST", 0, new GhostAbilitySet(), ChatColor.DARK_AQUA, CC.DarkAquaB + "Ghost", new MaterialData(Material.INK_SACK, DyeColor.SILVER.getDyeData())),
    NECROMANCER("NECROMANCER", 1, new NecromancerAbilitySet(), ChatColor.DARK_GREEN, CC.DarkGreenB + "Necromancer", new MaterialData(Material.INK_SACK, DyeColor.GREEN.getDyeData())),
    DEATH_KNIGHT("DEATH_KNIGHT", 2, new DeathKnightAbilitySet(), ChatColor.BLUE, CC.BlueB + "Death Knight", new MaterialData(Material.INK_SACK, DyeColor.CYAN.getDyeData())),
    ARCHITECT("ARCHITECT", 3, new ArchitectAbilitySet(), ChatColor.AQUA, CC.AquaB + "Architect", new MaterialData(Material.INK_SACK, DyeColor.CYAN.getDyeData()));

    public static final String ANTI_MKIT_ARMOR_KEY = "antiMkit";
    public static final String ANTI_MKIT_LIST_ARMOR_KEY = "antiMkits";
    private final AbilityArmorSetInfo armorSetInfo;
    private final ChatColor color;
    private final String displayName;
    private final MaterialData masteryShardItem;

    AbilityArmorSet(String name, int ordinal, AbilityArmorSetInfo armorSetInfo, ChatColor color, String displayName, MaterialData masteryShardItem) {
        this.armorSetInfo = armorSetInfo;
        this.color = color;
        this.displayName = displayName;
        this.masteryShardItem = masteryShardItem;
    }

    public static AbilityArmorSet fromName(String name) {
        return Arrays.stream(AbilityArmorSet.values()).filter(ab -> ab.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Set<AbilityArmorSet> getAntiMkitCrystal(NBTWrapper wrapper) {
        if (wrapper == null) {
            return null;
        }
        NBTTagCompoundBuilder cosmicData = wrapper.getCompound("conaxData");
        if (cosmicData != null) {
            HashSet<AbilityArmorSet> retr = null;
            String abilityArmorSetEquipped = cosmicData.getString(ANTI_MKIT_ARMOR_KEY);
            if (abilityArmorSetEquipped != null && !abilityArmorSetEquipped.isEmpty()) {
                AbilityArmorSet set = AbilityArmorSet.fromName(abilityArmorSetEquipped);
                retr = new HashSet();
                retr.add(set);
                return retr;
            }
            if (cosmicData.hasKey(ANTI_MKIT_LIST_ARMOR_KEY)) {
                retr = new HashSet<AbilityArmorSet>();
                NBTTagListBuilder list = new NBTTagListBuilder(cosmicData.getNBTBase(ANTI_MKIT_LIST_ARMOR_KEY));
                for (int i = 0; i < list.size(); ++i) {
                    String setName = list.getStringAt(i);
                    AbilityArmorSet set2 = AbilityArmorSet.fromName(setName);
                    if (set2 == null) continue;
                    retr.add(set2);
                }
                return retr;
            }
        }
        return null;
    }

    public static Set<AbilityArmorSet> getAntiMkitCrystal(ItemStack armorItem) {
        return AbilityArmorSet.getAntiMkitCrystal(new NBTWrapper(armorItem));
    }

    public static ItemStack removeAntiMkitCrystalFromItem(NBTWrapper wrapper) {
        ItemStack item;
        ItemMeta im;
        List lore;
        NBTTagCompoundBuilder data = wrapper.getCompound("conaxData");
        if (data != null) {
            data.remove(ANTI_MKIT_ARMOR_KEY);
            data.remove(ANTI_MKIT_LIST_ARMOR_KEY);
            wrapper.setNBTBase("conaxData", data.get());
        }
        if ((lore = (im = (item = wrapper.build()).getItemMeta()).getLore()) != null) {
            // TODO: .startsWith again ?!?!?
            lore.removeIf(s -> s.equals(CC.GoldB + "Anti M-Kit Crystal ("));
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public static Pair<Set<AbilityArmorSet>, Integer> getFromAntiMkitCrystal(ItemStack item) {
        return AbilityArmorSet.getFromAntiMkitCrystal(new NBTWrapper(item));
    }

    public static Pair<Set<AbilityArmorSet>, Integer> getFromAntiMkitCrystal(NBTWrapper wrapper) {
        NBTTagCompoundBuilder builder;
        if (wrapper.getString("cosmicType").equals("ANTI_MKIT") && (builder = wrapper.getCompound("conaxData")) != null) {
            int success = builder.getInt("success");
            HashSet<AbilityArmorSet> applied = new HashSet<AbilityArmorSet>();
            if (builder.hasKey("mKit")) {
                AbilityArmorSet set = AbilityArmorSet.fromName(builder.getString("mKit"));
                if (set != null) {
                    applied.add(set);
                }
            } else if (builder.hasKey("mKits")) {
                NBTTagListBuilder list = new NBTTagListBuilder(builder.getNBTBase("mKits"));
                for (int i = 0; i < list.size(); ++i) {
                    AbilityArmorSet set2;
                    String str = list.getStringAt(i);
                    if (str == null || (set2 = AbilityArmorSet.fromName(str)) == null) continue;
                    applied.add(set2);
                }
            }
            return new Pair(applied, success);
        }
        return null;
    }

    public static AbilityArmorSet getSetFromMasteryShard(ItemStack masteryShard) {
        NBTTagCompoundBuilder builder;
        NBTWrapper cursorWrapper = new NBTWrapper(masteryShard);
        if (cursorWrapper.getString("cosmicType").equals("MASTERY_SHARD") && (builder = cursorWrapper.getCompound("conaxData")) != null && builder.hasKey("masteryShard")) {
            return AbilityArmorSet.fromName(builder.getString("masteryShard"));
        }
        return null;
    }

    public ItemStack removeAntiMkitCrystalFromItem(ItemStack item) {
        return AbilityArmorSet.removeAntiMkitCrystalFromItem(new NBTWrapper(item));
    }

    @Deprecated
    public ItemStack createAntiMkitShard(int success) {
        return new CustomItemBuilder("ANTI_MKIT", success, this).build();
    }

    public ItemStack createMasteryShard(boolean applyIID) {
        ItemStack item = new NBTWrapper(new ItemBuilder(this.masteryShardItem.getItemType(), CC.GoldB + "Mastery Shard " + CC.Gold + "(" + this.displayName + CC.Gold + ")", CC.Gray + "Apply to an enchantment book", String.valueOf(CC.Gray) + "to imbue it with the power to", String.valueOf(CC.Gray) + "affect " + this.displayName + CC.Gray + " Mastery Kit items.").data(this.masteryShardItem.getData()).build()).setString("cosmicType", "MASTERY_SHARD").setNBTBase("conaxData", new NBTTagCompoundBuilder().setString("masteryShard", this.name()).get()).build();
        if (applyIID && ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            item = ItemID.assignItemID(null, item);
        }
        return item;
    }

    public NBTWrapper applyAbilityNBT(NBTWrapper wrapper) {
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("conaxData");
        builder.setString("abilitySet", this.name());
        builder.setShort("noEnchants", (short) 1);
        wrapper.setNBTBase("conaxData", builder.get());
        return wrapper;
    }

    public String getFirstDisplayNameLetter() {
        return this.color + CC.Bold + this.name().charAt(0);
    }

    public AbilityArmorSetInfo getArmorSetInfo() {
        return this.armorSetInfo;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

