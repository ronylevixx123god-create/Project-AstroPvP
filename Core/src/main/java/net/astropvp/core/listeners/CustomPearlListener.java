package net.astropvp.core.listeners;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import net.astropvp.core.entities.EscapistPearlEntity;
import net.astropvp.core.items.pearls.EscapistPearlItem;
import net.astropvp.core.items.pearls.OmniPearlItem;
import net.astropvp.core.items.pearls.SuperPearlItem;
import net.minecraft.server.v1_8_R3.EntityEnderPearl;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.bukkit.ChatColor;
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
            if (!EnderpearlCooldown.isEnderpearlCooldownOver(player) || !canThrowPearl(event.getItem(), player)) {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
            if (OmniPearlItem.isOmniPearl(item)) {
                if (!KillTrackerAPI.hasSoulAmount((Player)player, (int)5)) {
                    player.sendMessage((Object) ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 5)" + (Object)ChatColor.BOLD + " ***");
                    event.setCancelled(true);
                    player.updateInventory();

                    return;
                } else {
                    KillTrackerAPI.removeSoulsFromGems((Player)player, (int)5);
                }
            }


            EscapistPearlEntity escapistPearlEntity = ((EscapistPearlItem.isEscapistPearl(item) || OmniPearlItem.isOmniPearl(item) || AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.MEDIEVAL_HELMET_KNIGHT) ||
            AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.SEXY_BELT))) ? new EscapistPearlEntity(player) : null;
            if (escapistPearlEntity == null) {
                //event.setCancelled(true);
                //player.updateInventory();
                return;
            }
            event.setCancelled(true);

            ((CraftWorld)player.getLocation().getWorld()).getHandle().addEntity(escapistPearlEntity);
            if (EscapistPearlItem.isEscapistPearl(item)) {
                if (item.getAmount() == 1) {
                    player.setItemInHand(null);
                } else {
                    item.setAmount(item.getAmount() - 1);
                }
            }
            player.updateInventory();
            //event.setCancelled(true);
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

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        if(e.getEntity() instanceof EnderPearl) {

            if(SuperPearlItem.isSuperPearl(((Player) e.getEntity().getShooter()).getItemInHand())) {
                e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(1.5));
                return;
            }

            if(OmniPearlItem.isOmniPearl(((Player) e.getEntity().getShooter()).getItemInHand())) {
                //EscapistPearlEntity escapistPearlEntity = new EscapistPearlEntity(player);
                //escapistPearlEntity.getBukkitEntity().setVelocity(escapistPearlEntity.getBukkitEntity().getVelocity().multiply(2.5));
                //((CraftWorld)player.getLocation().getWorld()).getHandle().addEntity(escapistPearlEntity);
                //e.setCancelled(true);

                if (!KillTrackerAPI.hasSoulAmount((Player)player, (int)5)) {
                    player.sendMessage((Object) ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 5)" + (Object)ChatColor.BOLD + " ***");
                    player.updateInventory();
                    e.setCancelled(true);
                } else {
                    KillTrackerAPI.removeSoulsFromGems((Player)player, (int)5);
                    e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(1.5));
                }
                return;
            }

        }
    }
}
