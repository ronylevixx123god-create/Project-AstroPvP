package me.bryside.weaponrunes.items;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponRuneExtractor extends CosmicItem
        implements InventoryInteractable {


    public WeaponRuneExtractor() {
        super("weaponrune_extractor");
    }

    public ItemStack createItem(Player player, Object... objects) {
        Integer chance;
        Integer n = chance = objects.length >= 1 ? NumberUtils.parseIntOrNull(objects[0].toString()) : null;
        if (chance == null) {
            chance = 1;
        }

        return this.applyItemData((new ItemBuilder(Material.GOLD_NUGGET, CC.translate("&c&lWeapon Rune Extractor &7(" + chance + "%)")
                , CC.translate("&7Apply this onto an item with a weapon"),
                CC.translate("&7bonus to extract the rune from the item.")

        ).build())).setNBTBase("astroData", new NBTTagCompoundBuilder().setInt("success", chance).get()).build();
    }


    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }


        if (!WeaponBonusUtils.hasWeaponBonus(clicked)) {
            return;
        }

        int chance = wrapper.getCompound("astroData").getInt("success");


        WeaponBonusType weaponBonusType = WeaponBonusUtils.getWeaponBonusByItem(clicked);

        if (cursor.getAmount() > 1) {
            throw new PrefixedException("You cannot apply stacked Weapon Bonus Extractors onto an item.");
        }

        NBTTagCompoundBuilder cosmicType = clickedWrapper.getCompound("cosmicData");
        cosmicType.remove("bonusType");
        clickedWrapper.setNBTBase("cosmicData", (NBTBase)cosmicType.get());

        e.setCancelled(true);
        e.getWhoClicked().setItemOnCursor((ItemStack)null);

        clicked = clickedWrapper.build();

        ItemMeta meta = clicked.getItemMeta();
        List<String> newLore = new ArrayList<>();
        List<String> lore = meta.getLore();
        for (String line : lore) {
            if (!ChatColor.stripColor(line).contains(" ➥ ") && !ChatColor.stripColor(line).contains("(!)")) {
                newLore.add(line);
            }
        }

        meta.setLore(newLore);
        clicked.setItemMeta(meta);
        e.setCurrentItem(clicked);
        e.setCursor(weaponBonusType.getInstance().getItem(chance));
        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 5,5);
        player.sendMessage(CC.translate("&a&l(!)&a Successfully applied Weapon Bonus Extractor!"));
    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        event.setCancelled(true);
    }

}

