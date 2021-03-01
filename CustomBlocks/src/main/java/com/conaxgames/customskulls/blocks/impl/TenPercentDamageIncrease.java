package com.conaxgames.customskulls.blocks.impl;

import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TenPercentDamageIncrease extends CustomBlock {

    public TenPercentDamageIncrease() {
        super(CC.B_GOLD + "10% " + CC.GOLD + "Damage Increase", Material.SKULL_ITEM, AbilityType.TEN_PERCENT_DAMAGE_INCREASE);
    }

    @Override
    public List<String> addLore() {
        return Arrays.asList(
                CC.GRAY + "Use this item within your faction chunk",
                CC.GRAY + "to gain an extra " + CC.RED + "10% Damage Output",
                CC.GRAY + "onto your enemies, during your battles!",
                " ",
                CC.YELLOW + "Place this item to active your buff!"
        );
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
    public void placeBlockAction(Player p0, BlockPlaceEvent p1, Block p2, ItemStack p3) {
        Bukkit.broadcastMessage("You placed TenPercentDamageIncrease item!");
    }

    @Override
    public void breakBlockAction(Player p0, BlockBreakEvent p1, Block p2, ItemStack p3) {
    }

    @Override
    public void clickedInInventoryAction(Player p0, InventoryClickEvent p1) {
    }
}
