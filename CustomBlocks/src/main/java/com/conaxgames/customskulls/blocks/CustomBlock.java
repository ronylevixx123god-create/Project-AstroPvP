package com.conaxgames.customskulls.blocks;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomBlock {
    private final String name;
    private final Material material;

    public CustomBlock(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public List<String> getLore(ItemStack item) {
        final List<String> lore = new ArrayList<String>();
        lore.add("" + this.name);
        return lore;
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

    public abstract void breakBlockAction(final Player p0, final BlockBreakEvent p1, final Block p2, final ItemStack p3);

    public abstract void clickedInInventoryAction(final Player p0, final InventoryClickEvent p1);

}