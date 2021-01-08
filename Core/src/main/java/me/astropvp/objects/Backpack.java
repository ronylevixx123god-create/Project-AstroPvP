/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.astropvp.objects;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Backpack {
    public abstract BackpackType getType();

    public abstract List<String> getLore();

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent e) {
    }

    public void onDamaged(Player damaged, Entity damager, EntityDamageByEntityEvent e) {
    }

    public void onEquip(Player player) {
    }

    public void onUnequip(Player player) {
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.WhiteB + "Item Skin (" + this.getType().getName() + CC.WhiteB + ")");
        List<String> lore = this.getLore();
        lore.add("");
        lore.add(CC.GrayI + "Attach this skin to any " + CC.WhiteU + "CHESTPLATE");
        lore.add(CC.GrayI + "to override its visual appearance.");
        lore.add("");
        lore.add(CC.Red + ChatColor.BOLD.toString() + "NOTE: " + (Object)ChatColor.RED + "Requires the Cosmic Client");
        lore.add(CC.Red + "to render custom item skins.");
        lore.add(CC.Gray + "* Download at " + CC.GrayU + "cosmicclient.com");
        lore.add("");
        lore.add(CC.Gray + "Drag n' Drop onto item to attach.");
        lore.add(CC.Gray + "Right-Click item to detach skin.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setString("backpackType", this.getType().getId());
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        wrapper.setString("random", UUID.randomUUID().toString());
        wrapper.setString("cosmicType", "backpack_skin");
        return wrapper.build();
    }

    public ItemStack applyBackpack(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList() : meta.getLore();
        lore.add(CC.WhiteB + "BACKPACK SKIN: " + this.getType().getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setString("backpackType", this.getType().getId());
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }
}

