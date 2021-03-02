package com.conaxgames.customskulls.blocks;


import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.ItemBuilderUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public abstract class CustomBlock {
    private final String identifier;
    private final String name;
    private final Material material;
    private final AbilityType abilityType;

    public CustomBlock(String identifier, String name, Material material, AbilityType abilityType) {
        this.identifier = identifier;
        this.name = name;
        this.material = material;
        this.abilityType = abilityType;
    }

    public abstract List<String> addLore();

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ItemStack getItem() {
        ItemBuilderUtils item = new ItemBuilderUtils(material).setDurability((short) 3).setName(name).setLore();
        NBTItem nbtItem = new NBTItem(item.toItemStack());
        nbtItem.setString("conaxCustomBlock", abilityType.getName());

        SkullMeta meta = (SkullMeta) nbtItem.getItem().getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", abilityType.getSkinTexture()));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        nbtItem.getItem().setItemMeta(meta);
        return nbtItem.getItem();
    }

    public abstract void leftClickAirAction(final Player p0, final ItemStack p1);

    public abstract void leftClickBlockAction(final Player p0, final PlayerInteractEvent p1, final Block p2, final ItemStack p3);

    public abstract void rightClickAirAction(final Player p0, final ItemStack p1);

    public abstract void rightClickBlockAction(final Player p0, final PlayerInteractEvent p1, final Block p2, final ItemStack p3);

    public abstract void shiftLeftClickAirAction(final Player p0, final ItemStack p1);

    public abstract void shiftLeftClickBlockAction(final Player p0, final PlayerInteractEvent p1, final Block p2, final ItemStack p3);

    public abstract void shiftRightClickAirAction(final Player p0, final ItemStack p1);

    public abstract void shiftRightClickBlockAction(final Player p0, final PlayerInteractEvent p1, final Block p2, final ItemStack p3);

    public abstract void middleClickAction(final Player p0, final ItemStack p1);

    public abstract void hitEntityAction(final Player p0, final EntityDamageByEntityEvent p1, final Entity p2, final ItemStack p3);

    public abstract void placeBlockAction(final Player p0, final BlockPlaceEvent p1, final Block p2, final ItemStack p3);

    public abstract void breakBlockAction(final Player p0, final BlockBreakEvent p1, final Block p2, final ItemStack p3, CustomBlock customBlock, AbilityData data);

    public abstract void clickedInInventoryAction(final Player p0, final InventoryClickEvent p1);

    // p0 in this case is giving damage to an enemy while a custom block is active.
    public abstract void damageGivenEvent(final Player p0, final EntityDamageByEntityEvent p1, final Chunk p2);

    // p0 in this case is the killer of the mob.
    public abstract void mobDeathEvent(Player p0, EntityDeathEvent p1);

}