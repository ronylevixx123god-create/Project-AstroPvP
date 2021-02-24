package net.astropvp.core.items;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class HeroicUpgrade extends CosmicItem
        implements InventoryInteractable {
    public HeroicUpgrade() {
        super("heroic_upgrade");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData((new ItemBuilder(Material.BLAZE_POWDER, CC.GoldB + "Heroic Weapon Upgrade"
                , CC.Gray + "Apply this to any weapon"
                , CC.Gray + "to turn it into a heroic weapon.",
                CC.Gray + "",
                CC.Gray + "You can only apply this to diamond swords/axes.").build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }
        Material type = clicked.getType();

        if (type != Material.DIAMOND_AXE && type != Material.DIAMOND_SWORD) {
            throw new PrefixedException("You can only upgrade diamond swords/axes");
        }
        if (cursor.getAmount() > 1) {
            throw new PrefixedException("You cannot apply stacked Heroic Weapon upgrades to an item.");
        }
        NBTTagCompoundBuilder cosmicData = clickedWrapper.getCompoundOrCreate("conaxData");

        cosmicData.setInt("heroicWeaponUpgrade", Integer.valueOf(1));
        e.setCancelled(true);
        e.getWhoClicked().setItemOnCursor((ItemStack)null);
        clickedWrapper.setNBTBase("conaxData", (NBTBase)cosmicData.get());
        clicked = clickedWrapper.build();
        if (type == Material.DIAMOND_AXE) {
            CosmicArmorSetsAPI.convertIntoHeroicWeapon(HeroicWeaponType.AXE, clicked, true);
        } else if (type == Material.DIAMOND_SWORD) {
            CosmicArmorSetsAPI.convertIntoHeroicWeapon(HeroicWeaponType.SWORD, clicked, true);
        }
        e.setCurrentItem(clicked);
        player.sendMessage(CC.translate("&a&l(!)&a Successfully applied Heroic Weapon Upgrade to this item!"));
    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        event.setCancelled(true);
    }
}

