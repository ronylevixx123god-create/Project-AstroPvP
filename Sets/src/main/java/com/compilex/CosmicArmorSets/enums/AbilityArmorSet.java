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
import com.compilex.CosmicArmorSets.utils.Pair;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum AbilityArmorSet {
    GHOST("GHOST", 0, (AbilityArmorSetInfo)new GhostAbilitySet(), ChatColor.DARK_AQUA, String.valueOf(String.valueOf(CC.DarkAquaB)) + "Ghost",
            new MaterialData(Material.INK_SACK, DyeColor.SILVER.getDyeData())),
    NECROMANCER("NECROMANCER", 1, (AbilityArmorSetInfo)new NecromancerAbilitySet(), ChatColor.DARK_GREEN,
            String.valueOf(String.valueOf(CC.DarkGreenB)) + "Necromancer",
            new MaterialData(Material.INK_SACK, DyeColor.GREEN.getDyeData())),
    DEATH_KNIGHT("DEATH_KNIGHT", 2, (AbilityArmorSetInfo)new DeathKnightAbilitySet(), ChatColor.BLUE,
            String.valueOf(String.valueOf(CC.BlueB)) + "Death Knight", new MaterialData(Material.INK_SACK, DyeColor.CYAN.getDyeData())),
    ARCHITECT("ARCHITECT", 3, (AbilityArmorSetInfo)new ArchitectAbilitySet(), ChatColor.AQUA, String.valueOf(String.valueOf(CC.AquaB)) + "Architect",
            new MaterialData(Material.INK_SACK, DyeColor.CYAN.getDyeData()));

    public static final String ANTI_MKIT_ARMOR_KEY = "antiMkit";

    public static final String ANTI_MKIT_LIST_ARMOR_KEY = "antiMkits";

    private AbilityArmorSetInfo armorSetInfo;

    private ChatColor color;

    private String displayName;

    private MaterialData masteryShardItem;

    public static AbilityArmorSet fromName(String name) {
        return Arrays.<AbilityArmorSet>stream(values()).filter(ab -> ab.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public static Set<AbilityArmorSet> getAntiMkitCrystal(NBTWrapper wrapper) {
        if (wrapper == null)
            return null;
        NBTTagCompoundBuilder cosmicData = wrapper.getCompound("cosmicData");
        if (cosmicData != null) {
            Set<AbilityArmorSet> retr = null;
            String abilityArmorSetEquipped = cosmicData.getString("antiMkit");
            if (abilityArmorSetEquipped != null && !abilityArmorSetEquipped.isEmpty()) {
                AbilityArmorSet set = fromName(abilityArmorSetEquipped);
                retr = new HashSet<>();
                retr.add(set);
                return retr;
            }
            if (cosmicData.hasKey("antiMkits")) {
                retr = new HashSet<>();
                NBTTagListBuilder list = new NBTTagListBuilder(cosmicData.getNBTBase("antiMkits"));
                for (int i = 0; i < list.size(); i++) {
                    String setName = list.getStringAt(i);
                    AbilityArmorSet set2 = fromName(setName);
                    if (set2 != null)
                        retr.add(set2);
                }
                return retr;
            }
        }
        return null;
    }

    public static boolean hasArmorCrystal(Player player, AbilityArmorSet armorSet) {
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = player.getInventory().getArmorContents()).length, b = 0; b < i; ) {
            ItemStack x = arrayOfItemStack[b];
            if (x != null && x.getType() != Material.AIR) {
                Set<AbilityArmorSet> set = getAntiMkitCrystal(x);
                if (set != null)
                    if (set.contains(armorSet))
                        return true;
            }
            b++;
        }
        return false;
    }

    public static Set<AbilityArmorSet> getAntiMkitCrystal(ItemStack armorItem) {
        return getAntiMkitCrystal(new NBTWrapper(armorItem));
    }

    public static ItemStack removeAntiMkitCrystalFromItem(NBTWrapper wrapper) {
        NBTTagCompoundBuilder data = wrapper.getCompound("cosmicData");
        if (data != null) {
            data.remove("antiMkit");
            data.remove("antiMkits");
            wrapper.setNBTBase("cosmicData", (NBTBase)data.get());
        }
        ItemStack item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();
        if (lore != null) {
            lore.removeIf(s -> s.startsWith(String.valueOf(String.valueOf(CC.GoldB)) + "Anti M-Kit Crystal ("));
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public ItemStack removeAntiMkitCrystalFromItem(ItemStack item) {
        return removeAntiMkitCrystalFromItem(new NBTWrapper(item));
    }

    public static Pair<Set<AbilityArmorSet>, Integer> getFromAntiMkitCrystal(ItemStack item) {
        return getFromAntiMkitCrystal(new NBTWrapper(item));
    }

    public static Pair<Set<AbilityArmorSet>, Integer> getFromAntiMkitCrystal(NBTWrapper wrapper) {
        if (wrapper.getString("cosmicType").equals("ANTI_MKIT")) {
            NBTTagCompoundBuilder builder = wrapper.getCompound("cosmicData");
            if (builder != null) {
                int success = builder.getInt("success").intValue();
                Set<AbilityArmorSet> applied = new HashSet<>();
                if (builder.hasKey("mKit")) {
                    AbilityArmorSet set = fromName(builder.getString("mKit"));
                    if (set != null)
                        applied.add(set);
                } else if (builder.hasKey("mKits")) {
                    NBTTagListBuilder list = new NBTTagListBuilder(builder.getNBTBase("mKits"));
                    for (int i = 0; i < list.size(); i++) {
                        String str = list.getStringAt(i);
                        if (str != null) {
                            AbilityArmorSet set2 = fromName(str);
                            if (set2 != null)
                                applied.add(set2);
                        }
                    }
                }
                return new Pair(applied, Integer.valueOf(success));
            }
        }
        return null;
    }

    @Deprecated
    public ItemStack createAntiMkitShard(int success) {
        return (new CustomItemBuilder("ANTI_MKIT", new Object[] { Integer.valueOf(success), this })).build();
    }

    public ItemStack createMasteryShard(boolean applyIID) {
        ItemStack item = (new NBTWrapper((new ItemBuilder(this.masteryShardItem.getItemType(),
                String.valueOf(String.valueOf(CC.GoldB)) + "Mastery Shard " + CC.Gold + "(" + this.displayName + CC.Gold + ")",
                new String[] { String.valueOf(String.valueOf(CC.Gray)) + "Apply to an enchantment book",
                        String.valueOf(String.valueOf(CC.Gray)) + "to imbue it with the power to",
                        String.valueOf(String.valueOf(CC.Gray)) + "affect " + this.displayName + CC.Gray + " Mastery Kit items." })).data(this.masteryShardItem.getData()).build())).setString("cosmicType", "MASTERY_SHARD")
                .setNBTBase("cosmicData", (NBTBase)(new NBTTagCompoundBuilder())
                        .setString("masteryShard", name()).get())
                .build();
        if (applyIID && ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
            item = ItemID.assignItemID(null, item);
        return item;
    }

    public static AbilityArmorSet getSetFromMasteryShard(ItemStack masteryShard) {
        NBTWrapper cursorWrapper = new NBTWrapper(masteryShard);
        if (cursorWrapper.getString("cosmicType").equals("MASTERY_SHARD")) {
            NBTTagCompoundBuilder builder = cursorWrapper.getCompound("cosmicData");
            if (builder != null && builder.hasKey("masteryShard"))
                return fromName(builder.getString("masteryShard"));
        }
        return null;
    }

    public NBTWrapper applyAbilityNBT(NBTWrapper wrapper) {
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("cosmicData");
        builder.setString("abilitySet", name());
        builder.setShort("noEnchants", Short.valueOf((short)1));
        wrapper.setNBTBase("cosmicData", (NBTBase)builder.get());
        return wrapper;
    }

    public String getFirstDisplayNameLetter() {
        return this.color + CC.Bold + name().substring(0, 1);
    }

    AbilityArmorSet(String name, int ordinal, AbilityArmorSetInfo armorSetInfo, ChatColor color, String displayName, MaterialData masteryShardItem) {
        this.armorSetInfo = armorSetInfo;
        this.color = color;
        this.displayName = displayName;
        this.masteryShardItem = masteryShardItem;
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
