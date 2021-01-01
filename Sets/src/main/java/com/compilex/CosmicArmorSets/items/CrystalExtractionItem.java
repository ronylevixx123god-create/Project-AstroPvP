package com.compilex.CosmicArmorSets.items;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.itembuilder.CosmicItemBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class CrystalExtractionItem
        extends CosmicItem
        implements InventoryInteractable {
    public CrystalExtractionItem() {
        super("extractCrystal");
    }

    public ItemStack createItem(Player player, Object... objects) {
        Integer successRate;
        Integer n = successRate = objects != null && objects.length >= 1 ? NumberUtils.parseIntOrNull((String) objects[0].toString()) : null;
        if (successRate == null) {
            successRate = 1;
        }
        return this.applyItemData(new CosmicItemBuilder(Material.GHAST_TEAR, CC.GoldB + "Crystal Extractor", CC.Gray + "Removes 1 random crystal from", CC.Gray + "an armor piece and converts it", CC.Gray + "into its applicable form:", CC.WhiteB + " * " + CC.GoldB + "Anti M-Kit Crystal (" + CC.White + successRate + "% Success" + CC.GoldB + ")", CC.WhiteB + " " + CC.WhiteB + CC.Underline + "OR", CC.WhiteB + " * " + CC.GoldB + "Armor Crystal (" + CC.White + successRate + "% Success" + CC.GoldB + ")").build()).setNBTBase("cosmicData", new NBTTagCompoundBuilder().setInt("success", successRate).get()).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack itemStack, NBTWrapper crystal, NBTWrapper clicked, ItemStack other, InventoryClickEvent e) throws CosmicException {
        if (!GeneralUtil.isArmor((ItemStack) other)) {
            return;
        }
        List<ArmorSet> armorCrystalApplied = ArmorSet.getFromArmorBonusCrystal(clicked);
        NBTTagCompoundBuilder data = crystal.getCompound("cosmicData");
        if (data == null) {
            return;
        }
        Integer crystalExtractionSuccess = data.getInt("success");
        Set<AbilityArmorSet> antiMkitCrystalApplied = AbilityArmorSet.getAntiMkitCrystal(clicked);
        if (armorCrystalApplied == null && (antiMkitCrystalApplied == null || antiMkitCrystalApplied.isEmpty())) {
            throw new PrefixedException("No armor crystals to extract from this armor piece!");
        }
        ItemStack updated = null;
        ItemStack returnCursor = null;
        if (armorCrystalApplied != null && !armorCrystalApplied.isEmpty() && (antiMkitCrystalApplied == null || Math.random() <= 0.5)) {
            updated = ArmorSet.removeCrystalFromItem(clicked);
            returnCursor = ArmorSet.createCrystal(armorCrystalApplied, crystalExtractionSuccess);
        } else {
            updated = AbilityArmorSet.removeAntiMkitCrystalFromItem(clicked);
            returnCursor = new CustomItemBuilder("ANTI_MKIT", crystalExtractionSuccess, antiMkitCrystalApplied).build();
        }
        if (updated != null) {
            e.setCancelled(true);
            e.setCurrentItem(updated);
            if (itemStack.getAmount() > 1) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                player.setItemOnCursor(itemStack);
                PlayerUtil.giveOrDropProtectedItem((Player) player, (ItemStack) returnCursor, (int) -1);
            } else {
                player.setItemOnCursor(returnCursor);
            }
            player.sendMessage(CC.GreenB + "(!) " + ItemUtils.getDisplayName((ItemStack) returnCursor) + CC.Green + " extracted from " + ItemUtils.getDisplayName((ItemStack) updated) + CC.Green + "!");
            ASound.LEVEL_UP.play(player, 10.0f, 1.1f);
            player.updateInventory();
            ASound.WITHER_IDLE.play(player, 1.0f, 3.0f);
        }
    }
}

