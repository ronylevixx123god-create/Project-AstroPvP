/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ASound
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.GeneralUtil
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicArmorSets.items;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OmniCrystal
        extends CosmicItem
        implements InventoryInteractable {
    private static String itemKey;

    public OmniCrystal() {
        super("omni_crystal");
    }

    public ItemStack createItem(Player player, Object... objects) {
        Integer chance;
        Integer n = chance = objects.length >= 1 ? NumberUtils.parseIntOrNull(objects[0].toString()) : null;
        if (chance == null) {
            chance = 1;
        }
        return this.applyItemData(new ItemBuilder(Material.SNOW_BALL).lore(CC.translate("&7Apply to any non-specialty"), CC.translate("&7armor piece to"), CC.translate("&7transform it into an &3Omni Item:"), CC.translate("&7An item that acts as any"), CC.translate("&7unique /help armorset piece."), "", CC.translate("&3EX: &71 Omni item, 3 Phantom items equipped"), CC.translate("&7would apply the Phantom Set Bonus to the player")).name(CC.translate(String.format("&3&lOmni Crystal (&7%d%%&3&l)", chance))).build()).setNBTBase("conaxData", new NBTTagCompoundBuilder().setInt("success", chance).get()).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper cursorWrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent event) throws CosmicException {

        if (clickedWrapper == null || !GeneralUtil.isArmor(clicked)) {
            return;
        }
        event.setCancelled(true);
        if (!cursorWrapper.getconaxData().hasKey("success")) {
            return;
        }

        ItemMeta meta = clicked.getItemMeta();
        if (meta.hasLore())  {
            if (meta.getLore().contains(CC.translate("&3&lOMNI SET BONUS"))) return;
        }
        List<ArmorSet> bonusApplied = ArmorSet.getFromArmorBonusCrystal(clickedWrapper);
        if (bonusApplied != null && !bonusApplied.isEmpty()) {
            event.getWhoClicked().sendMessage(CC.translate("&cYou cannot apply an omni crystal to a piece that contains crystals"));
            event.setCancelled(true);
            return;
        }

        if (ArmorSet.getArmorSetFromItem(clicked) != null) {
            return;
        }
        if (player.getItemOnCursor().getAmount() <= 1) {
            player.setItemOnCursor(null);
        } else {
            ItemStack newCursor = player.getItemOnCursor();
            newCursor.setAmount(newCursor.getAmount() - 1);
            player.setItemOnCursor(newCursor);
        }

        int chance = cursorWrapper.getconaxData().getInt("success");
        if (ThreadLocalRandom.current().nextInt(101) >= chance) {
            player.playSound(player.getLocation(), Sound.GLASS, 1.0f, 2.0f);
            return;
        }
        if (meta.hasLore()) {
            List lore = meta.getLore();
            lore.add("");
            lore.add(CC.translate("&3&lOMNI SET BONUS"));
            lore.add(CC.translate("&3&l* &3Acts as ANY Armor Set type/piece"));
            meta.setLore(lore);
        }
        ASound.LEVEL_UP.play(player, 1.1f);
        clicked.setItemMeta(meta);
        clickedWrapper = new NBTWrapper(clicked);
        NBTTagCompoundBuilder cosmicData = clickedWrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(clickedWrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setInt("omniArmorSet", 1);
        NBTTagCompoundBuilder display = clickedWrapper.hasKey("display") ? new NBTTagCompoundBuilder(clickedWrapper.getNBTBase("display")) : new NBTTagCompoundBuilder();
        display.setInt("color", 0x0027FF);
        clickedWrapper.setNBTBase("display", display.get());
        clickedWrapper.setNBTBase("cosmicData", cosmicData.get());



        event.setCurrentItem(clickedWrapper.build());
    }
}

