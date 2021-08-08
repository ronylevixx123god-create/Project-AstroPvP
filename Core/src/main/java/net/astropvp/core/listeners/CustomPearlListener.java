package net.astropvp.core.listeners;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.core.entities.EscapistPearlEntity;
import net.astropvp.core.items.pearls.EscapistPearlItem;
import net.astropvp.core.items.pearls.OmniPearlItem;
import net.astropvp.core.items.pearls.SuperPearlItem;
import net.minecraft.server.v1_8_R3.EntityEnderPearl;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class CustomPearlListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThrow(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item != null && item.getType() == Material.ENDER_PEARL) {
            if (!EnderpearlCooldown.isEnderpearlCooldownOver(player) || !canThrowPearl(event.getItem(), player))
                return;
            EscapistPearlEntity escapistPearlEntity = (EscapistPearlItem.isEscapistPearl(item) ||
                    AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.SEXY_BELT)) ? new EscapistPearlEntity(player) : null;
            if (escapistPearlEntity == null)
                return;
            ((CraftWorld)player.getLocation().getWorld()).getHandle().addEntity(escapistPearlEntity);
            event.setCancelled(true);
        }
    }

    private boolean canThrowPearl(ItemStack item, Player pl) {
        if (pl.hasMetadata("lastEnderpearlThrow")) {
            long lastEnderpearlThrow = ((MetadataValue)pl.getMetadata("lastEnderpearlThrow").get(0)).asLong();
            long cd = 400L;
            long timeSinceThrow = System.currentTimeMillis() - lastEnderpearlThrow;
            return timeSinceThrow >= 400L;
        }
        return true;
    }

    public void onThrow(ProjectileLaunchEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        if(e.getEntity() instanceof EnderPearl) {
            if(OmniPearlItem.isOmniPearl(((Player) e.getEntity().getShooter()).getItemInHand())) {
                EscapistPearlEntity escapistPearlEntity = new EscapistPearlEntity(player);
                escapistPearlEntity.getBukkitEntity().setVelocity(escapistPearlEntity.getBukkitEntity().getVelocity().multiply(2.5));
                ((CraftWorld)player.getLocation().getWorld()).getHandle().addEntity(escapistPearlEntity);
                e.setCancelled(true);
                //TODO: NOT WORKING
                return;
            }
            if(SuperPearlItem.isSuperPearl(((Player) e.getEntity().getShooter()).getItemInHand())) {
                e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(2.5));
            }
        }
    }
}
