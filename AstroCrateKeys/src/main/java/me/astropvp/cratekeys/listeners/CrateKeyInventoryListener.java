package me.astropvp.cratekeys.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.data.CrateData;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.astropvp.cratekeys.utils.ConfigUtils;
import net.minecraft.server.v1_8_R3.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CrateKeyInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();


        if ((block.getState() instanceof Jukebox)) {
            Jukebox chest = (Jukebox) block.getState();
            CrateData data = AstroCrateKeysAPI.findCrate(chest.getLocation());
            if (data == null) {
                return;
            }

            if (!AstroCrateKeysAPI.isCrateKey(player.getItemInHand())) {
                player.sendMessage(CC.translate("&c&l(!)&c Please hold a " + data.getCrateType().getPrefix() + CC.Bold +
                        data.getCrateType().getName() + "&f&l Crate Key&c to open this crate."));
                event.setCancelled(true);
                return;
            }


            if (CrateKeyItem.getCrateType(player.getItemInHand()) != data.getCrateType()) {
                player.sendMessage(CC.translate("&c&l(!)&c This is not the correct crate type. You need to click the " + CrateKeyItem.getCrateType(player.getItemInHand()).getPrefix() +
                        CC.Bold + CrateKeyItem.getCrateType(player.getItemInHand()).getName() + CC.Bold + "&f&l Crate&c to open this key."));
                event.setCancelled(true);
                return;
            }
            if (isPlayerInventoryFull(player)) {
                player.sendMessage(CC.translate("&cYour inventory is full. Clear some space before opening more keys!"));
                event.setCancelled(true);
                return;
            }
            addItemToPlayer(player, ConfigUtils.getRandomItem(AstroCrateKeysAPI.getItems(CrateKeyItem.getCrateType(player.getItemInHand()))));

            if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.setItemInHand((ItemStack) null);
            }

            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);


            event.setCancelled(true);
        }

    }

    public static boolean isPlayerInventoryFull(Player player) {
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i; ) {
            ItemStack item = arrayOfItemStack[b];
            if (item == null)
                return false;
            b++;
        }
        return true;
    }

    public static void addItemToPlayer(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return;
        player.getInventory().addItem(item);
    }
}


