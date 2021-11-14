package net.astropvp.core.items.coder;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantmentCoderItem extends CosmicItem implements InventoryInteractable {
    public EnchantmentCoderItem() {
        super("enchantment_coder");
    }

    public ItemStack createItem(Player player, Object... objects) {
        return applyItemData(ItemUtils.addGlow((new ItemBuilder(Material.SLIME_BALL, CC.translate("&c&lEnchantment Coder &7(Drag n' Drop)"), new String[] { CC.translate("&7Drag n' Drop this item onto a weapon/armor"), CC.translate("&7piece to MAX out all of its Custom Enchantments.") })).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return (inventoryItemType == InventoryItemType.CURSOR);
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        Material type = clicked.getType();
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null || type == null)
            return;
        ItemStack finalClicked = clickedWrapper.build();
        EnchantmentAPI.getEnchantments(clicked).forEach((enchantment, unused) -> enchantment.addToItem(finalClicked, enchantment.getMaxLevel()));
        EnchantmentBook.applyTransmogScroll(finalClicked);
        if (cursor.getAmount() > 1) {
            cursor.setAmount(cursor.getAmount() - 1);
        } else {
            e.getWhoClicked().setItemOnCursor(null);
        }
        e.setCancelled(true);
        e.setCurrentItem(finalClicked);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.2F);
        player.sendMessage(CC.translate("&a&l(!) &aSuccessfully MAXED out all custom enchantments on this item!"));
    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        event.setCancelled(true);
    }
}
