/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ASound
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.exceptions.PrefixedException
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.GeneralUtil
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.arkhamnetwork.enchantments.MasteryCustomEnchantment
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.libs.com.google.gson.internal.Pair
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicArmorSets.items;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import javafx.util.Pair;
import me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AntiMKitCrystalItem
        extends CosmicItem
        implements InventoryInteractable {
    public AntiMKitCrystalItem() {
        super("ANTI_MKIT");
    }

    public ItemStack createItem(Player player, Object... objects) {
        if (objects != null && objects.length >= 2) {
            Integer success = NumberUtils.parseIntOrNull((String) objects[0].toString());
            if (success == null) {
                success = 1;
            }
            Set<AbilityArmorSet> set = new HashSet();
            for (int i = 1; i < objects.length; ++i) {
                Object object = objects[i];
                AbilityArmorSet armorSet = null;
                if (object instanceof AbilityArmorSet) {
                    armorSet = (AbilityArmorSet) ((Object) object);
                } else if (object instanceof Set) {
                    set = (Set) object;
                } else {
                    armorSet = AbilityArmorSet.fromName(object.toString());
                }
                if (armorSet == null) continue;
                set.add(armorSet);
            }
            ArrayList lore = Lists.newArrayList((Object[]) new String[]{CC.Green + success + "% Success Rate", CC.Gray + "Apply to any piece of armor", CC.Gray + "to negate the following", CC.Gray + "custom Mastery Enchantments:"});
            NBTTagListBuilder list = new NBTTagListBuilder();
            for (AbilityArmorSet found : set) {
                if (set.size() > 1) {
                    lore.add(found.getDisplayName());
                }
                list.addString(found.name());
                for (CustomEnchantment ench : EnchantmentAPI.getEnchantments()) {
                    if (!(ench instanceof MasteryCustomEnchantment) || !found.name().equals(((MasteryCustomEnchantment) ench).getArmorSet()))
                        continue;
                    lore.add(String.valueOf(CC.GoldB) + " * " + CC.DarkRed + ench.name());
                }
            }
            String name = null;
            if (set.size() > 1) {
                StringBuilder builder = new StringBuilder();
                for (AbilityArmorSet found2 : set) {
                    builder.append(found2.getFirstDisplayNameLetter()).append(CC.Gold).append(", ");
                }
                String combined = builder.toString();
                if (combined.endsWith(", ")) {
                    combined = combined.substring(0, combined.length() - 2);
                }
                name = String.valueOf(CC.GoldB) + "Multi Anti M-Kit Crystal (" + combined + CC.GoldB + ")";
            } else if (!set.isEmpty()) {
                name = String.valueOf(CC.GoldB) + "Anti M-Kit Crystal (" + ((AbilityArmorSet) set.stream().findFirst().orElse(null)).getDisplayName() + CC.GoldB + ")";
            }
            if (name == null) {
                return null;
            }
            return this.applyItemData(new ItemBuilder(Material.INK_SACK, name, new String[0]).lore((List) lore).data((short) DyeColor.YELLOW.getDyeData()).build()).setNBTBase("cosmicData", (NBTBase) new NBTTagCompoundBuilder().setNBTBase("mKits", (NBTBase) list.build()).setInt("success", success).get()).assignIID().build();
        }
        return null;
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack itemStack, NBTWrapper nbtWrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent inventoryClickEvent) throws CosmicException {
        Pair<Set<AbilityArmorSet>, Integer> crystal;
        if (GeneralUtil.isArmor((ItemStack) clicked) && (crystal = AbilityArmorSet.getFromAntiMkitCrystal(nbtWrapper)) != null) {
            if (AbilityArmorSet.getAntiMkitCrystal(clickedWrapper) != null) {
                throw new PrefixedException("You can not apply more than 1 Anti M-Kit Crystal to a single piece of gear!");
            }
            if (nbtWrapper.isBlacklistedIID()) {
                throw new ItemDupedException("Anti M-Kit Crystal already used!");
            }
            inventoryClickEvent.setCancelled(true);
            if (itemStack.getAmount() > 1) {
                itemStack.setAmount(itemStack.getAmount() - 1);
            } else {
                player.setItemOnCursor((ItemStack) null);
            }
            nbtWrapper.blacklistIID("anti m-kit player: " + player.getName());
            if (Math.random() > (double) ((Integer) crystal.second).intValue() * 0.01) {
                player.getWorld().playSound(player.getLocation(), Sound.FIZZ, 3.0f, 1.1f);
                ASound.GLASS.play(player, 3.0f, 1.1f);
                player.updateInventory();
                throw new PrefixedException(String.valueOf(ItemUtils.getDisplayName((ItemStack) itemStack)) + CC.Red + " fails to apply and has been destroyed!");
            }
            inventoryClickEvent.setCurrentItem(this.applyToItem(clickedWrapper, clicked, crystal));
            ASound.LEVEL_UP.play(player, 3.0f, 1.1f);
            player.sendMessage(String.valueOf(CC.GoldB) + "(!) " + CC.Gold + ItemUtils.getDisplayName((ItemStack) itemStack) + CC.Gold + " has been applied to " + ItemUtils.getDisplayName((ItemStack) clicked) + CC.Gold + "!");
            player.sendMessage(String.valueOf(CC.Gray) + "While equipped you will be immune to any Mastery Enchantments associated with that /mkit(s)!");
            player.updateInventory();
        }
    }

    private ItemStack applyToItem(NBTWrapper wrapper, ItemStack item, Pair pair) {
        String str;
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("cosmicData");
        NBTTagListBuilder list = new NBTTagListBuilder();
        Set set = (Set) pair.first;
        StringBuilder loreBuild = new StringBuilder();
        for (AbilityArmorSet armor : set) {
            list.addString(armor.name());
            loreBuild.append(armor.getDisplayName()).append(CC.Gray).append(", ");
        }
        builder.setNBTBase("antiMkits", (NBTBase) list.build());
        wrapper.setNBTBase("cosmicData", (NBTBase) builder.get());
        item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List lore = im.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }
        if ((str = loreBuild.toString()).endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        lore.add(CC.GoldB + "Anti M-Kit Crystal (" + str + CC.GoldB + ")");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }
}

