package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import net.astropvp.core.guis.RevokeScratchCardGUI;
import net.astropvp.core.guis.SlotbotScratchCardGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SlotbotScratchCardListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(CC.translate("&8Slotbot ScratchCard"))) {
            e.setCancelled(true);
            if (e.getClickedInventory() == p.getOpenInventory().getTopInventory()) {


                SlotbotScratchCardGUI collectorInventory = (SlotbotScratchCardGUI) e.getClickedInventory().getHolder();
                if (collectorInventory != null) {
                    collectorInventory.click(e);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) throws UserDoesNotExistException, NoLoanPermittedException {
        if (e.getInventory().getTitle().equals(CC.translate("&8Slotbot ScratchCard"))) {
            SlotbotScratchCardGUI collectorInventory = (SlotbotScratchCardGUI) e.getInventory().getHolder();
            if (collectorInventory != null) {

                collectorInventory.cancelReward();

            }
        }
    }

}
