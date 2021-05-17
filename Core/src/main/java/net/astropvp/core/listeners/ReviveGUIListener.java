package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;

import net.astropvp.core.data.InventoryData;
import net.astropvp.core.guis.ReviveGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ReviveGUIListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().contentEquals(String.valueOf(CC.DarkGray) + "Revive GUI"))
            return;
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR)
            return;
        NBTWrapper wrapper = new NBTWrapper(item);
        if (wrapper.hasKey("page")) {
            int page = wrapper.getInt("page").intValue();
            Player player = (Player)event.getWhoClicked();
            if (event.getCurrentItem().getType() == Material.ARROW) {
                ItemStack[] items = ReviveGUI.getInventory(Bukkit.getPlayer(wrapper.getString("player")), page).getContents();
                for (int i = 0; i < items.length; i++)
                    event.getInventory().setItem(i, items[i]);
                player.updateInventory();
            } else {
                player.closeInventory();
                Player target = Bukkit.getPlayer(wrapper.getString("player"));
                player.sendMessage(String.valueOf(CC.GreenB) + "(!) " + CC.Green + "You revived " + target.getName() + "!");
                InventoryData[] data = (InventoryData[])InventoryData.getData().get(target.getUniqueId());
                ItemStack[] contents = data[page].getContents();
                ItemStack[] armorContents = data[page].getArmorContents();
                for (int i = 0; i < 36; i++)
                    target.getInventory().setItem(i, contents[i]);
                target.getInventory().setHelmet(armorContents[3]);
                target.getInventory().setChestplate(armorContents[2]);
                target.getInventory().setLeggings(armorContents[1]);
                target.getInventory().setBoots(armorContents[0]);
                target.updateInventory();
            }
        }
        event.setCancelled(true);
    }
}
