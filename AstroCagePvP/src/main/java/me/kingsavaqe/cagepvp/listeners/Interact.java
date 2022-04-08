package me.kingsavaqe.cagepvp.listeners;

import java.util.Iterator;

import me.kingsavaqe.cagepvp.AstroCagePvP;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {
   private final AstroCagePvP instance;

   public Interact(AstroCagePvP instance) {
      this.instance = instance;
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
         Player player = event.getPlayer();
         if (this.instance.getWildPvPManager().hasWildPvP(player)) {
            FileConfiguration configLoad = this.instance.getConfig();

            for(String block : configLoad.getStringList("WildPvP.Match.BlockInteractBlacklist")) {
               if(event.getClickedBlock().getType().name().equalsIgnoreCase(block)) {
                  event.setCancelled(true);
                  player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad.getString("Listeners.Interact.Match.Message")));
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  break;
               }
            }

         }
      }
   }
}
