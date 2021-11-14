package net.astropvp.core.items.coder;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.astropvp.core.AstroCore;
import net.astropvp.core.struct.EnchantmentCoderManager;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HeroicEnchantmentCoderItem extends CosmicItem implements InventoryInteractable {

    public HeroicEnchantmentCoderItem() {
        super("heroic_enchantment_coder");
    }

    public ItemStack createItem(Player player, Object... objects) {
        ItemStack item = new ItemStack(Material.SLIME_BALL);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(CC.translate("&7This item will automatically apply enchantments"),
                CC.translate("&7to your weapons or armor pieces (up to 14 lore)."), "",

                CC.translate("&7&oApply this to a sword or piece of gear to unleash its power"), "",

                CC.translate("&c&lRight-Click &7to open the GUI and add books."),
                CC.translate("&c&lXP Cost &8(on apply) &710,000")));
        meta.setDisplayName(CC.translate("&b&k;;&f&l&nHeroic&d&k;;&c&l Enchantment Coder"));
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder tag = new NBTTagCompoundBuilder();
        tag.setBoolean("enchantmentCoder", Boolean.TRUE);
        tag.setString("uuid", UUID.randomUUID().toString());
        wrapper.setNBTBase("cosmicData", (NBTBase)tag.get());
        item = wrapper.build();
        return item;
    }

    @Override
    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return (inventoryItemType == InventoryItemType.CURSOR);
    }

    @Override
    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null || clicked.getType() == null)
            return;
        if (!EnchantmentAPI.getEnchantments(clicked).isEmpty()) {
            player.sendMessage(CC.translate("&c&l(!) &cPlease blackscroll all the enchantments off this item before using this Heroic Enchantment Coder."));
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
            e.setCancelled(true);
            return;
        }
        Material type = clicked.getType();
        if (type == null)
            return;
        UUID uuid = player.getUniqueId();
        EnchantmentCoderManager manager = AstroCore.getInstance().getEnchantmentCoderManager();
        List<CustomEnchantment> enchantments = manager.getEnchantmentsForUser(uuid, type);
        if (enchantments.isEmpty()) {
            player.sendMessage(CC.translate("&c&l(!) &cThere are no enchantments saved for this item!"));
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
            e.setCancelled(true);
            return;
        }
        for (int i = 0; i < enchantments.size(); i++) {
            CustomEnchantment enchantment = enchantments.get(i);
            if (enchantment.nonHeroicVersion != null)
                enchantment.nonHeroicVersion.addToItem(clicked, enchantment.nonHeroicVersion.getMaxLevel());
            enchantment.addToItem(clicked, enchantment.getMaxLevel());
        }
        e.setCancelled(true);
        e.setCurrentItem(clicked);
        player.sendMessage(CC.translate("&a&l(!) &aSuccessfully added enchantments to your item!"));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
    }
}

