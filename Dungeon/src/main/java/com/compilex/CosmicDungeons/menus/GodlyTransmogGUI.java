/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.decimatemc.decimatecommons.DecimateCommons
 *  com.decimatemc.decimatecommons.inventory.ClickableItem
 *  com.decimatemc.decimatecommons.inventory.InventoryListener
 *  com.decimatemc.decimatecommons.inventory.SmartInventory
 *  com.decimatemc.decimatecommons.inventory.content.InventoryContents
 *  com.decimatemc.decimatecommons.inventory.content.InventoryProvider
 *  com.decimatemc.decimatecommons.util.CommonsUtil
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  com.rit.sucy.service.ERomanNumeral
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  org.arkhamnetwork.enchantments.libs.EnchanterRarity
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicDungeons.menus;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ERomanNumeral;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.arkhamnetwork.enchantments.libs.EnchanterRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GodlyTransmogGUI
implements InventoryProvider {
    private SmartInventory inventory;
    private final ItemStack originalItem;
    private ItemStack displayItem;
    private boolean didConfirm = false;
    private int currentSelectedIdx = -1;
    private LinkedList<CustomEnchantment> enchantmentsOrder;
    private Map<CustomEnchantment, Integer> enchantments;

    public GodlyTransmogGUI(Player player, ItemStack item) {
        this.originalItem = item;
        this.displayItem = new NBTWrapper(item).build().clone();
        this.enchantments = EnchantmentAPI.getEnchantments((ItemStack)this.originalItem);
        this.enchantmentsOrder = new LinkedList(EnchantmentAPI.getEnchantments((ItemStack)this.originalItem).keySet());
        this.inventory = SmartInventory.builder().id(UUID.randomUUID().toString()).type(InventoryType.CHEST).size(4, 9).manager(DecimateCommons.getInstance().getInventoryManager()).title("Godly Transmog Scroll").provider((InventoryProvider)this).listener(new InventoryListener(InventoryCloseEvent.class, event -> {
            if (!this.didConfirm) {
                player.getInventory().addItem(new ItemStack[]{this.originalItem});
                player.getInventory().addItem(new ItemStack[]{CosmicSkyCustomItems.get().getItemManager().getCustomItem("godly_transmog_scroll").createItem(null, new Object[0])});
            }
        })).build();
    }

    private void removeEnchantsFromDisplay() {
        for (Map.Entry<CustomEnchantment, Integer> data : this.enchantments.entrySet()) {
            data.getKey().removeFromItem(this.displayItem);
        }
    }

    public void init(Player player, InventoryContents contents) {
        CustomEnchantment enchantment;
        int i;
        contents.fill(null);
        this.removeEnchantsFromDisplay();
        ItemMeta meta = this.displayItem.getItemMeta();
        List lore = meta.getLore();
        for (i = this.enchantmentsOrder.size() - 1; i >= 0; --i) {
            enchantment = this.enchantmentsOrder.get(i);
            lore.add(0, (Object)EnchantmentAPI.getChatColor((CustomEnchantment)enchantment) + enchantment.name() + " " + ERomanNumeral.numeralOf((int)this.enchantments.get((Object)enchantment)));
        }
        meta.setLore(lore);
        this.displayItem.setItemMeta(meta);
        i = 0;
        while (i < this.enchantmentsOrder.size()) {
            enchantment = this.enchantmentsOrder.get(i);
            int finalIdx = i++;
            Arrays.stream(EnchanterRarity.values()).filter(r -> r.getTier() == enchantment.getTier()).findFirst().ifPresent(enchanterRarity -> contents.add(ClickableItem.of((ItemStack)(this.currentSelectedIdx != finalIdx ? CommonsUtil.createItem((Material)Material.STAINED_GLASS_PANE, (String)((Object)EnchantmentAPI.getChatColor((CustomEnchantment)enchantment) + enchantment.name() + " " + ERomanNumeral.numeralOf((int)this.enchantments.get((Object)enchantment))), (int)1, (int)(enchanterRarity.getPaneColor().getWoolData() == 6 ? 2 : (int)enchanterRarity.getPaneColor().getWoolData()), (String[])new String[0]) : CommonsUtil.addGlow((ItemStack)CommonsUtil.createItem((Material)Material.STAINED_GLASS_PANE, (String)((Object)EnchantmentAPI.getChatColor((CustomEnchantment)enchantment) + enchantment.name() + " " + ERomanNumeral.numeralOf((int)this.enchantments.get((Object)enchantment))), (int)1, (int)(enchanterRarity.getPaneColor().getWoolData() == 6 ? 2 : (int)enchanterRarity.getPaneColor().getWoolData()), (String[])new String[]{"&a&l*SELECTED*", "&7Click another enchantment to", "&7swap its position with this one."}))), event -> {
                if (this.currentSelectedIdx != -1) {
                    CustomEnchantment toSwap = this.enchantmentsOrder.get(finalIdx);
                    this.enchantmentsOrder.set(finalIdx, this.enchantmentsOrder.get(this.currentSelectedIdx));
                    this.enchantmentsOrder.set(this.currentSelectedIdx, toSwap);
                    this.currentSelectedIdx = -1;
                    this.init(player, contents);
                } else {
                    this.currentSelectedIdx = finalIdx;
                    this.init(player, contents);
                }
            })));
        }
        contents.set(3, 4, ClickableItem.of((ItemStack)this.displayItem, event -> {
            this.didConfirm = true;
            player.getInventory().addItem(new ItemStack[]{this.displayItem});
            player.closeInventory();
        }));
    }

    public SmartInventory getInventory() {
        return this.inventory;
    }
}

