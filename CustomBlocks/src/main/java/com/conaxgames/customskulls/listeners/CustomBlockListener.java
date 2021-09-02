package com.conaxgames.customskulls.listeners;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CustomBlockUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CustomBlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(final PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }
        if (CustomBlockUtil.isCustomItem(event.getPlayer().getItemInHand())) {
            this.interactCustomBlockItem(event, event.getPlayer().getItemInHand());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(final PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }
        if (CustomBlockUtil.isCustomItem(event.getPlayer().getItemInHand())) {
            this.interactCustomBlockItem(event, event.getPlayer().getItemInHand());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHit(final EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player player = (Player) event.getDamager();
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            return;
        }
        final ItemStack playerItemInHand = player.getItemInHand();
        if (CustomBlockUtil.isCustomItem(playerItemInHand)) {
            Objects.requireNonNull(CustomBlockUtil.getCustomItem(playerItemInHand)).hitEntityAction(player, event, event.getEntity(), playerItemInHand);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();
        AbilityData data = AbilityData.getAbilityDataMap().get(AbilityData.getUUID(event.getBlock().getLocation()));
        if (data == null) {
            return;
        }

        CustomBlock customBlock = CoreSkulls.customBlocks.get(data.getAbilityType().getName());

        if (customBlock == null) {
            return;
        }

        customBlock.breakBlockAction(player, event, event.getBlock(), item, customBlock, data);



    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();
        if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }

        if (!(CustomBlockUtil.isCustomItem(item))) {
            return;
        }
        if (CustomBlockUtil.getBlockDatasInChunk(player) == null) {
            return;
        }

        if (event.getPlayer().getLocation().getChunk() != event.getBlockAgainst().getLocation().getChunk()) {
            player.sendMessage(CC.translate("&c&l(!)&c You must stand in the same chunk as the block you are placing against."));
            event.setCancelled(true);
            return;
        }


        for (AbilityData data : CustomBlockUtil.getBlockDatasInChunk(player)) {

            if (data.getAbilityType().name().equals(CustomBlockUtil.getCustomItem(item).getIdentifier())) {
                player.sendMessage(CC.translate("&c&l(!)&c This chunk already contains this custom block."));
                event.setCancelled(true);
                return;
            }
        }

        if (CustomBlockUtil.isCustomItem(item)) {
/*            CraftWorld cw = (CraftWorld) player.getWorld();
            TileEntity tileEntity = cw.getTileEntityAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("type", CustomBlockUtil.getCustomItem(item).getIdentifier());
            compound.setString("customBlockData", CustomBlockUtil.getCustomItem(item).getIdentifier());
            compound.setString("type", CustomBlockUtil.getCustomItem(item).getIdentifier());*/


            /*NBTCompound entBlock = NBTInjector.getNbtData(event.getBlock().getState());
            entBlock.addCompound("tag").addCompound("customBlockData").setString("type", CustomBlockUtil.getCustomItem(item).getIdentifier());
            NBTCompound tag = entBlock.getCompound("tag");
            NBTCompound customBlockData = tag.addCompound("customBlockData");
            customBlockData.setString("type", CustomBlockUtil.getCustomItem(item).getName());*/

            //event.setCancelled(true);


            (CustomBlockUtil.getCustomItem(item)).placeBlockAction(player, event, event.getBlock(), item);

        }


    }


    private void interactCustomBlockItem(final PlayerInteractEvent event, final ItemStack item) {
        final Player player = event.getPlayer();
        if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }
        if (CustomBlockUtil.isCustomItem(item)) {
            CustomBlock customBlock = CustomBlockUtil.getCustomItem(item);
            if (customBlock != null) {
                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    if (!player.isSneaking()) {
                        customBlock.leftClickAirAction(player, item);
                    } else {
                        customBlock.shiftLeftClickAirAction(player, item);
                    }
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (!player.isSneaking()) {
                        customBlock.leftClickBlockAction(player, event, event.getClickedBlock(), item);
                    } else {
                        customBlock.shiftLeftClickBlockAction(player, event, event.getClickedBlock(), item);
                    }
                } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!player.isSneaking()) {
                        customBlock.rightClickAirAction(player, item);
                    } else {
                        customBlock.shiftRightClickAirAction(player, item);
                    }
                } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!player.isSneaking()) {
                        customBlock.rightClickBlockAction(player, event, event.getClickedBlock(), item);
                    } else {
                        customBlock.shiftRightClickBlockAction(player, event, event.getClickedBlock(), item);
                    }
                }
            }
        }
    }
}
