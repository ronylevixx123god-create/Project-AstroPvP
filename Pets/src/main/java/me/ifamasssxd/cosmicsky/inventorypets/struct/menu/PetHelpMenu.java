package me.ifamasssxd.cosmicsky.inventorypets.struct.menu;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.entity.Player;

public class PetHelpMenu extends CosmicGUI {
   public PetHelpMenu(Player player) {
      super(player, "Pet Help", 54);
   }

   public void redraw() {
      int index = 0;
      if (player != null) {
         for (InventoryPets pet : InventoryPets.values()) {
            if (pet != null) {
               InventoryItem guiItem = new InventoryItem(CosmicSkyInventoryPets.get().getPetItem().createItem(null, pet));
               this.setItem(index++, guiItem);
            }
         }
      }
   }
}
