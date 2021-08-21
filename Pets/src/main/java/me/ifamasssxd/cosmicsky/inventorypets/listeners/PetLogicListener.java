package me.ifamasssxd.cosmicsky.inventorypets.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.managers.InventoryPetManager;
import me.ifamasssxd.cosmicsky.inventorypets.struct.buffs.XpPetBuff;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class PetLogicListener implements Listener {
   private InventoryPetManager manager;

   public PetLogicListener(InventoryPetManager manager) {
      this.manager = manager;
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onEntityDeath(EntityDeathEvent e) {
      if (e.getEntity().getKiller() != null) {
         Player killer = e.getEntity().getKiller();
         XpPetBuff buff = this.manager.getBuff(killer);
         if (buff == null) {
            return;
         }

         if (buff.isExpired()) {
            this.manager.removeBuff(killer);
            return;
         }

         int xp = e.getDroppedExp();
         e.setDroppedExp((int)((double)e.getDroppedExp() * buff.getMultiplier()));
         if (killer.isOp()) {
            killer.sendMessage(CC.Red + "Bunny Buff: " + xp + " -> " + e.getDroppedExp());
         }

         CosmicSkyInventoryPets.get().getLogger().info("Increasing xp from " + buff.getMultiplier() + " buff!");
      }

   }


   @EventHandler
   public void blockPlace(BlockPlaceEvent e) {
      if (e.getBlock().getType() == Material.SKULL_ITEM || e.getBlock().getType() == Material.SKULL) {
         e.setCancelled(true);
      }
   }
}
