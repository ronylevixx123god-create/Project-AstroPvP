package com.compilex.CosmicArmorSets.items;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.github.paperspigot.Title;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CrystalInfuser
        extends CosmicItem
        implements InventoryInteractable {
    private static String itemKey;

    public CrystalInfuser() {
        super("crystal_infuser");
    }

    public ItemStack createItem(Player player, Object... objects) {
        return new ItemBuilder(Material.NETHER_STAR).lore
                (CC.translate("&7&nRight-Click&7 to open the"),
                        CC.translate("&5crystal infuser&7 menu and"),
                        CC.translate("&7combine 2 crystals into 1"),
                        CC.translate("&7and create the best crystals"))
                .name(CC.translate("&d&l✧ &5&lCrystal Infuser &d&l✧")).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CLICKED;
    }

    @Override
    public void onInventoryClick(Player var1, ItemStack var2, NBTWrapper var3, NBTWrapper var4, ItemStack var5, InventoryClickEvent var6) throws CosmicException {

    }

    @Override
    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        if (item == null) {
            return;
        }
        if (wrapper == null) {
            return;
        }
        if (item.getType() == Material.AIR) {
            return;
        }

        if (item.getType() != Material.NETHER_STAR) {
            return;
        }

        if (!(player.getWorld().getName().equals("world")) && !(player.getWorld().getName().equals("flat"))) {
            throw new PrefixedException("You cannot use that item in this world!");
        }


        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("conaxData");

        cosmicData.setInt("crystal_infusor", Integer.valueOf(1));
        event.setCancelled(true);
        wrapper.setNBTBase("conaxData", (NBTBase)cosmicData.get());
        item = wrapper.build();

        if(player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
        } else {
            player.setItemInHand((ItemStack) null);
        }


    }
}



