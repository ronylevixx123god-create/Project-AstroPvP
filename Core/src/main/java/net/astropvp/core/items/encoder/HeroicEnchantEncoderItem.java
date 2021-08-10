package net.astropvp.core.items.encoder;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicduel.utils.CustomItem;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HeroicEnchantEncoderItem extends CosmicItem implements InventoryInteractable {
    public HeroicEnchantEncoderItem() {
        super("heroic_enchant_encoder");
    }

    public ItemStack createItem(Player player, Object... objects) {
        ItemStack item = new ItemStack(Material.SLIME_BALL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.White + CC.Magic + "ll " + CC.White + "HEROIC" + CC.Magic + "ll" + CC.RedB + " Enchantment Encoder");
        List<String> lore = new ArrayList<>();
        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("cosmicType", "heroic_enchant_encoder");
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
    }
}
