package net.astropvp.core.items.encoder;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Multimap;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.enchanting.EListener;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EnchantEncoderItem extends CosmicItem implements InventoryInteractable {
    public EnchantEncoderItem() {
        super("enchant_encoder");
    }

    public ItemStack createItem(Player player, Object... objects) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.SLIME_BALL);
        itemBuilder.name(CC.translate("&c&lEnchant Encoder"));
        itemBuilder.addLoreLine(CC.translate("&7Add this encoder on an item to"));
        itemBuilder.addLoreLine(CC.translate("&7max out all enchants on this item."));
        NBTWrapper wrapper = new NBTWrapper(itemBuilder.build());
        wrapper.setString("cosmicType", "enchant_encoder");
        wrapper.setString("random", UUID.randomUUID().toString());
        return wrapper.build();
    }

    @Override
    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    @Override
    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if(clicked == null || clicked.getType() == Material.AIR)
            return;
        e.setCancelled(true);
        Map<CustomEnchantment, Integer> enchants = EnchantmentAPI.getAllEnchantments(clicked);
        for(Map.Entry<CustomEnchantment, Integer> entry : enchants.entrySet()) {
            if(entry.getKey().getMaxLevel() == entry.getValue())
                continue;
            EnchantmentAPI.getEnchantment(entry.getKey().name()).addToItem(clicked, entry.getKey().getMaxLevel());
        }
        EnchantmentBook.applyTransmogScroll(clicked);
        if(cursor.getAmount() > 1) {
            e.getWhoClicked().getItemOnCursor().setAmount(cursor.getAmount() - 1);
        } else {
            e.getWhoClicked().setItemOnCursor((ItemStack) null);
        }

        e.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a Maxed out all enchants on " + e.getWhoClicked().getItemOnCursor().getItemMeta().getDisplayName()));
    }

}
