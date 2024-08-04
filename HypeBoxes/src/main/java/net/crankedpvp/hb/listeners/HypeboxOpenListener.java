package net.crankedpvp.hb.listeners;

import de.tr7zw.nbtapi.NBTItem;
import net.crankedpvp.hb.struct.HypeBox;
import net.crankedpvp.hb.struct.HypeBoxManager;
import net.crankedpvp.hb.HypeboxesPlugin;
import net.crankedpvp.hb.inventory.HypeBoxAnimationInventory;
import net.crankedpvp.hb.language.Language;
import net.crankedpvp.hb.utils.CC;
import net.crankedpvp.hb.utils.Clickable;
import net.crankedpvp.hb.utils.InventoryUtils;
import net.crankedpvp.hb.utils.LuckpermUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class HypeboxOpenListener implements Listener {

    private final HypeboxesPlugin plugin;
    public HypeboxOpenListener(HypeboxesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == null) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.getItem();
            if(!HypeBoxManager.isHypebox(itemStack)) return;
            event.setCancelled(true);
            final NBTItem nbtItem = new NBTItem(itemStack);
            final HypeBox hypeBox = plugin.getHypeBoxRegister().getHypebox(nbtItem.getCompound("cosmicData").getString("hypeboxID"));
            if(hypeBox == null) return;
            if(hypeBox.getRewards().isEmpty()) return;

            if(!InventoryUtils.hasAvaliableSlot(event.getPlayer(), 1)) {
                event.getPlayer().sendMessage(Language.PREFIX.getMessage() + Language.FULL_INVENTORY.getMessage());
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                return;
            }

            final int amount = itemStack.getAmount();
            if (amount <= 1) {
                event.getPlayer().setItemInHand(null);
            } else {
                itemStack.setAmount(amount - 1);
                event.getPlayer().setItemInHand(itemStack);
            }

            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
            HypeBoxAnimationInventory hypeboxAnimationInventory = new HypeBoxAnimationInventory(event.getPlayer(), hypeBox);
            hypeboxAnimationInventory.getUI().open(event.getPlayer());

            if(hypeBox.doesBroadcast()) {
                UUID uuid = UUID.randomUUID();
                this.plugin.getHypeboxesBeingOpened().put(uuid, hypeboxAnimationInventory);
                Clickable clickable = new Clickable("&f✧ &eClick to watch &6" + LuckpermUtils.getDisplayName(event.getPlayer()) + " &eopen a " + hypeBox.getDisplayName(), "&eClick to view &6" + event.getPlayer().getName() + "'s &ehypebox &f✧", "/viewhypeboxopen " + uuid);
                clickable.add(CC.translate("\n&eYou can purchase this hypebox at &6buy.astropvp.net&e!"));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage("");
                    clickable.sendToPlayer(onlinePlayer);
                    onlinePlayer.sendMessage("");
                }
            }

        }
    }
}