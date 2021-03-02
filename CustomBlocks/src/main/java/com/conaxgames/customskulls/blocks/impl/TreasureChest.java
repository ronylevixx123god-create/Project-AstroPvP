package com.conaxgames.customskulls.blocks.impl;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.data.MakeCustomBlocks;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import org.bukkit.Bukkit;
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

import java.util.Arrays;
import java.util.List;

public class TreasureChest extends CustomBlock {

    public TreasureChest() {
        super("TREASURECHEST", CC.translate("&6&lTreasure Chest"), Material.SKULL_ITEM, AbilityType.TREASURECHEST);
    }

    @Override
    public List<String> addLore() {
        return Arrays.asList(
                CC.translate("&6Cost to Mine: &f$" + AbilityType.TREASURECHEST.getPickupPrice()),
                "",
                CC.translate("&7Receive all of the following"),
                CC.translate("&7buffs when standing in the same chunk."),
                "",
                CC.translate("&7Increase the percentage of EXP"),
                CC.translate("&7dropped by 50% when mobs are killed"),
                CC.translate("&7in the chunk it's placed in."),
                "",
                CC.translate("&7Place custom block to use!")
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
        // check if the player has a faction.
        // check if the player is in faction land
        MakeCustomBlocks.newFileItem(p0, AbilityType.TREASURECHEST, p1.getBlock(), this);
    }

    @Override
    public void breakBlockAction(Player p0, BlockBreakEvent p1, Block p2, ItemStack p3, CustomBlock cb, AbilityData data) {
    }

    @Override
    public void clickedInInventoryAction(Player p0, InventoryClickEvent p1) {
    }

    @Override
    public void damageGivenEvent(Player p0, EntityDamageByEntityEvent p1, Chunk p2) {
    }

    @Override
    public void mobDeathEvent(Player p0, EntityDeathEvent p1) {
        if (p1 == null) {
            Bukkit.broadcastMessage("boof2");
            return;
        }
        p1.getEntity().getKiller().sendMessage("boofaoda");
        //p1.setDroppedExp(p1.getDroppedExp() * 2);
        p0.sendMessage(CC.GREEN + "You got extra XP because of " + this.getName());
    }
}
