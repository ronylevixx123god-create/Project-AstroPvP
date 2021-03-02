package com.conaxgames.customskulls.blocks.impl;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.data.DeleteCustomBlocks;
import com.conaxgames.customskulls.data.MakeCustomBlocks;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.ItemBuilderUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
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

public class AstroTrophy extends CustomBlock {

    public AstroTrophy() {
        super("ASTROTROPHY", CC.translate("&6&lAstro Trophy"), Material.SKULL_ITEM, AbilityType.ASTROTROPHY);
    }

    @Override
    public List<String> addLore() {
        return Arrays.asList(
                CC.translate("&6Cost to Mine: &f$" + AbilityType.ASTROTROPHY.getPickupPrice()),
                "",
                CC.translate("&7Receive all of the following"),
                CC.translate("&7buffs when standing in the same chunk."),
                "",
                CC.translate("&710% chance to roll the Astro-Slot"),
                CC.translate("&7Bot without consuming your ticket."),
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
        FPlayer fPlayer = FPlayers.i.get(p0);
        if (fPlayer == null) {
            p0.sendMessage(CC.RED + "You must have a faction to place a block of this type.");
            return;
        }
        if (fPlayer.getRelationToLocation().isMember()) {
            MakeCustomBlocks.newFileItem(p0, AbilityType.ASTROTROPHY, p1.getBlock(), this);
        } else {
            p0.sendMessage(CC.RED + "You must be in your own claim to place a block of this type");
            p1.setCancelled(true);
        }
    }

    @Override
    public void breakBlockAction(Player player, BlockBreakEvent p1, Block p2, ItemStack p3, CustomBlock cb, AbilityData data) {
        FPlayer fPlayer = FPlayers.i.get(player);
        Player breaker = p1.getPlayer();
        if (fPlayer == null) {
            // The block must be in the land to remove it.
            return;
        }
        // If the custom block is being broken in the wilderness.
        if (FactionUtils.isWilderness(p2.getLocation())) {
            if (CoreSkulls.getEconomy().has(breaker, AbilityType.ASTROTROPHY.getPickupPrice())) {
                CoreSkulls.getEconomy().withdrawPlayer(breaker, AbilityType.ASTROTROPHY.getPickupPrice());
                breaker.sendMessage(CC.B_RED + "-$" + AbilityType.ASTROTROPHY.getPickupPrice());
                breaker.sendMessage(CC.GREEN + "You broke a " + AbilityType.ASTROTROPHY.getName() + CC.GREEN + "!");

                ItemBuilderUtils item = new ItemBuilderUtils(this.getItem()).setLore(this.addLore());
                p1.getBlock().getDrops().clear(); // clears the events item stack
                p2.breakNaturally(new ItemBuilderUtils(Material.AIR).toItemStack()); // break the block
                p2.getWorld().dropItemNaturally(p2.getLocation(), item.toItemStack()); // dropping our item stack
                DeleteCustomBlocks.func(p2.getLocation()); // delete file and memory.
            } else {
                breaker.sendMessage(CC.RED + "You do not have the required $" + AbilityType.ASTROTROPHY.getPickupPrice() + " to pick this block up.");
                p1.setCancelled(true);
            }
        }
    }

    @Override
    public void clickedInInventoryAction(Player p0, InventoryClickEvent p1) {
    }

    @Override
    public void damageGivenEvent(Player p0, EntityDamageByEntityEvent p1, Chunk p2) {
    }

    @Override
    public void mobDeathEvent(Player p0, EntityDeathEvent p1b) {
    }
}
