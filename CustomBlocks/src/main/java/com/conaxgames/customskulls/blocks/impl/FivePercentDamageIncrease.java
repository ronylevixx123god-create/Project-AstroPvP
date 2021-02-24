package com.conaxgames.customskulls.blocks.impl;

import com.conaxgames.customskulls.blocks.CustomBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FivePercentDamageIncrease extends CustomBlock {

    public FivePercentDamageIncrease(String name, Material material) {
        super(name, material);
    }

    @Override
    public void onItemStackCreate(ItemStack p0) {

    }

    @Override
    public void getSpecificLorePrefix(List<String> p0, ItemStack p1) {

    }

    @Override
    public void getSpecificLoreSuffix(List<String> p0, ItemStack p1) {

    }

    @Override
    public void leftClickAirAction(Player p0, ItemStack p1) {

    }

    @Override
    public void leftClickBlockAction(Player p0, PlayerInteractEvent p1, Block p2, ItemStack p3) {

    }

    @Override
    public void rightClickAirAction(Player p0, ItemStack p1) {

    }

    @Override
    public void rightClickBlockAction(Player p0, PlayerInteractEvent p1, Block p2, ItemStack p3) {

    }

    @Override
    public void shiftLeftClickAirAction(Player p0, ItemStack p1) {

    }

    @Override
    public void shiftLeftClickBlockAction(Player p0, PlayerInteractEvent p1, Block p2, ItemStack p3) {

    }

    @Override
    public void shiftRightClickAirAction(Player p0, ItemStack p1) {

    }

    @Override
    public void shiftRightClickBlockAction(Player p0, PlayerInteractEvent p1, Block p2, ItemStack p3) {

    }

    @Override
    public void middleClickAction(Player p0, ItemStack p1) {

    }

    @Override
    public void hitEntityAction(Player p0, EntityDamageByEntityEvent p1, Entity p2, ItemStack p3) {

    }

    @Override
    public void breakBlockAction(Player p0, BlockBreakEvent p1, Block p2, ItemStack p3) {

    }

    @Override
    public void clickedInInventoryAction(Player p0, InventoryClickEvent p1) {

    }

    @Override
    public void activeEffect(Player p0, ItemStack p1) {

    }
}
