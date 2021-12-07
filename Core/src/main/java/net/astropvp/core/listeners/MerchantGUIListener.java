package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import net.astropvp.core.guis.MerchantGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MerchantGUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(CC.translate("&8Scratch Card Merchant"))) {
            e.setCancelled(true);
            if (e.getClickedInventory() == p.getOpenInventory().getTopInventory()) {


                MerchantGUI collectorInventory = (MerchantGUI) e.getClickedInventory().getHolder();
                if (collectorInventory != null) {
                    collectorInventory.click(e);
                }
            }
        }
    }


}
