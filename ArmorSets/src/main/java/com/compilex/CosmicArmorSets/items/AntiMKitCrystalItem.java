package com.compilex.CosmicArmorSets.items;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AntiMKitCrystalItem extends CosmicItem implements InventoryInteractable {
    public AntiMKitCrystalItem() {
        super("ANTI_MKIT");
    }

    public ItemStack createItem(Player player, Object... objects) {
        if (objects != null && objects.length >= 2) {
            Integer success = NumberUtils.parseIntOrNull(objects[0].toString());
            if (success == null) {
                success = 1;
            }

            Set set = new HashSet();

            for(int i = 1; i < objects.length; ++i) {
                Object object = objects[i];
                AbilityArmorSet armorSet = null;
                if (object instanceof AbilityArmorSet) {
                    armorSet = (AbilityArmorSet)object;
                } else if (object instanceof Set) {
                    set = (Set)object;
                } else {
                    armorSet = AbilityArmorSet.fromName(object.toString());
                }

                if (armorSet != null) {
                    ((Set)set).add(armorSet);
                }
            }

            List lore = Lists.newArrayList(new String[]{CC.Green + success + "% Success Rate", CC.Gray + "Apply to any piece of armor", CC.Gray + "to negate the following", CC.Gray + "custom Mastery Enchantments:"});
            NBTTagListBuilder list = new NBTTagListBuilder();
            Iterator var13 = ((Set)set).iterator();

            Iterator var9;
            while(var13.hasNext()) {
                AbilityArmorSet found = (AbilityArmorSet)var13.next();
                if (((Set)set).size() > 1) {
                    lore.add(found.getDisplayName());
                }

                list.addString(found.name());
                var9 = EnchantmentAPI.getEnchantments().iterator();

                while(var9.hasNext()) {
                    CustomEnchantment ench = (CustomEnchantment)var9.next();
                    if (ench instanceof MasteryCustomEnchantment && found.name().equals(((MasteryCustomEnchantment)ench).getArmorSet())) {
                        lore.add(String.valueOf(CC.GoldB) + " * " + CC.DarkRed + ench.name());
                    }
                }
            }

            String name = null;
            if (((Set)set).size() > 1) {
                StringBuilder builder = new StringBuilder();
                var9 = ((Set)set).iterator();

                while(var9.hasNext()) {
                    AbilityArmorSet found2 = (AbilityArmorSet)var9.next();
                    builder.append(found2.getFirstDisplayNameLetter()).append(CC.Gold).append(", ");
                }

                String combined = builder.toString();
                if (combined.endsWith(", ")) {
                    combined = combined.substring(0, combined.length() - 2);
                }

                name = String.valueOf(CC.GoldB) + "Multi Anti M-Kit Crystal (" + combined + CC.GoldB + ")";
            } else if (!((Set)set).isEmpty()) {
                name = String.valueOf(CC.GoldB) + "Anti M-Kit Crystal (" + ((AbilityArmorSet)((Set)set).stream().findFirst().orElse((Object)null)).getDisplayName() + CC.GoldB + ")";
            }

            if (name == null) {
                return null;
            } else {
                return this.applyItemData((new ItemBuilder(Material.INK_SACK, name, new String[0])).lore(lore).data((short)DyeColor.YELLOW.getDyeData()).build()).setNBTBase("cosmicData", (new NBTTagCompoundBuilder()).setNBTBase("mKits", list.build()).setInt("success", success).get()).assignIID().build();
            }
        } else {
            return null;
        }
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack itemStack, NBTWrapper nbtWrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent inventoryClickEvent) throws CosmicException {
        if (GeneralUtil.isArmor(clicked)) {
            Pair crystal = AbilityArmorSet.getFromAntiMkitCrystal(nbtWrapper);
            if (crystal != null) {
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
                    player.setItemOnCursor((ItemStack)null);
                }

                nbtWrapper.blacklistIID("anti m-kit player: " + player.getName());
                if (Math.random() > (double)(Integer)crystal.second * 0.01D) {
                    player.getWorld().playSound(player.getLocation(), Sound.FIZZ, 3.0F, 1.1F);
                    ASound.GLASS.play(player, 3.0F, 1.1F);
                    player.updateInventory();
                    throw new PrefixedException(String.valueOf(ItemUtils.getDisplayName(itemStack)) + CC.Red + " fails to apply and has been destroyed!");
                }

                inventoryClickEvent.setCurrentItem(this.applyToItem(clickedWrapper, clicked, crystal));
                ASound.LEVEL_UP.play(player, 3.0F, 1.1F);
                player.sendMessage(String.valueOf(CC.GoldB) + "(!) " + CC.Gold + ItemUtils.getDisplayName(itemStack) + CC.Gold + " has been applied to " + ItemUtils.getDisplayName(clicked) + CC.Gold + "!");
                player.sendMessage(String.valueOf(CC.Gray) + "While equipped you will be immune to any Mastery Enchantments associated with that /mkit(s)!");
                player.updateInventory();
            }

        }
    }

    private ItemStack applyToItem(NBTWrapper wrapper, ItemStack item, Pair pair) {
        NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("cosmicData");
        NBTTagListBuilder list = new NBTTagListBuilder();
        Set set = (Set)pair.first;
        StringBuilder loreBuild = new StringBuilder();
        Iterator var8 = set.iterator();

        while(var8.hasNext()) {
            AbilityArmorSet armor = (AbilityArmorSet)var8.next();
            list.addString(armor.name());
            loreBuild.append(armor.getDisplayName()).append(CC.Gray).append(", ");
        }

        builder.setNBTBase("antiMkits", list.build());
        wrapper.setNBTBase("cosmicData", builder.get());
        item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List lore = im.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }

        String str = loreBuild.toString();
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }

        ((List)lore).add(String.valueOf(CC.GoldB) + "Anti M-Kit Crystal (" + str + CC.GoldB + ")");
        im.setLore((List)lore);
        item.setItemMeta(im);
        return item;
    }
}
