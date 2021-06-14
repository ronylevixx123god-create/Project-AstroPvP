package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import net.astropvp.core.guis.ArmorColorGUI;
import net.astropvp.core.guis.SpawnerShopGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpawnerShopListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().startsWith(CC.translate("&8Spawner Shop ("))) {
            e.setCancelled(true);
            if (e.getClickedInventory() == p.getOpenInventory().getTopInventory()) {


                SpawnerShopGUI collectorInventory = (SpawnerShopGUI) e.getClickedInventory().getHolder();
                if (collectorInventory != null) {
                    collectorInventory.click(e);
                }
            }
        }
    }

}
