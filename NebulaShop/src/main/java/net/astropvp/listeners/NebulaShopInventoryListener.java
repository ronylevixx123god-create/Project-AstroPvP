package net.astropvp.listeners;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.massivecraft.factions.P;
import net.astropvp.NebulaShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NebulaShopInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(!e.getInventory().getTitle().equals("Nebula Shop"))
            return;
        Player player = (Player) e.getWhoClicked();
        if(!player.hasPermission("astrocore.nebula"))
            player.closeInventory();
        e.setCancelled(true);
        if(e.getSlot() == 3) {
            if(NebulaShop.getInstance().getNebulaShopManager().hasAlreadyBought(player)) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You may only buy this item once per restock.");
                return;
            }
            double price = NebulaShop.getInstance().getNebulaShopManager().getCurrentPrice();
            if(!EconomyUtils.hasBalance(player, price)) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You require $" + price + " to purchase this item.");
                return;
            }
            if(player.getInventory().firstEmpty() < 0) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must have an empty slot to buy this item.");
                return;
            }
            EconomyUtils.withdrawBalance(player, price);
            NebulaShop.getInstance().getNebulaShopManager().setAlreadyBought(player);
            player.getInventory().addItem(NebulaShop.getInstance().getNebulaShopManager().getCurrentItem());
            Bukkit.broadcastMessage(CC.DarkPurpleB + "(" + CC.LightPurpleB + "Nebula Shop" + CC.DarkPurpleB + ") " + CC.DarkPurple + player.getName() + CC.LightPurple + " has purchased " + NebulaShop.getInstance().getNebulaShopManager().getCurrentItem().getItemMeta().getDisplayName() + CC.LightPurple + "from the " + CC.LightPurpleU + "Nebula Shop!");
        }
    }
}
