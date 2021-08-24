package me.astropvp.astrospawners.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import java.util.List;

import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobCoinShopListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void click(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || !(event.getWhoClicked() instanceof Player))
            return;
        if (event.getInventory().getTitle().contentEquals(String.valueOf(CC.DarkGray) + "Mob Coin Shop")) {
            Player player = (Player)event.getWhoClicked();
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            NBTWrapper wrapper = new NBTWrapper(item);
            if (!wrapper.hasKey("shopData"))
                return;
            NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("shopData"));
            int price = builder.getInt("cost");
            PlayerDataManager manager = AstroSpawners.getInstance().getManager();
            PlayerData data = manager.getPlayerData(player);
            double balance = ((data == null) ? 0 : data.getCoins());
            if (price > balance) {
                player.sendMessage(CC.translate("&c&l(!)&c You can not afford this item for " + NumberUtils.formatMoney(price) + " mob coins!"));
                player.sendMessage(CC.translate("&7You can earn more mob coins by killing mobs!"));
                player.closeInventory();
                return;
            }
            data.setCoins(data.getCoins() - price);
            manager.savePlayerData(data);
            player.sendMessage(String.valueOf(CC.GreenB) + "- " + NumberUtils.formatMoney(price) + " Mob Coins");
            wrapper.remove("shopData");
            item = wrapper.build();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            lore.remove(lore.size() - 1);
            lore.remove(lore.size() - 1);
            meta.setLore(lore);
            item.setItemMeta(meta);
            if (isInventoryFull(player)) {
                player.sendMessage(CC.translate("&cYou inventory is full. Clear some space."));
                return;
            }
            player.getInventory().addItem(item);
        }
    }

    public static boolean isInventoryFull(Player player) {
        for (ItemStack x : player.getInventory().getContents()) {
            if (x == null) {
                return false;
            }
        }
        return true;
    }
}

