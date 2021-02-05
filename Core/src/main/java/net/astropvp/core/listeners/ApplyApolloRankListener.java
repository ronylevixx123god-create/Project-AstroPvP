package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.commands.GiveApolloCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ApplyApolloRankListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() == null) {
                return;
            }
            if (!event.getItem().getType().equals(Material.NETHER_STAR)) {
                return;
            }
            if (!event.getItem().getItemMeta().hasLore()) {
                return;
            } // must be a netherstar with a lore, then we check again.
            ItemStack itemStack = event.getItem();
            if (GiveApolloCommand.isApolloRank(itemStack)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + event.getPlayer().getName() + " group add apollo");
                event.getPlayer().sendMessage(CC.translate("&a&l(!)&a You have claimed the Apollo Rank!"));
                int amount = itemStack.getAmount();
                if (amount <= 1) {
                    event.getPlayer().setItemInHand(null);
                }
                itemStack.setAmount(amount - 1);
                event.getPlayer().setItemInHand(itemStack);
                Player player = event.getPlayer();
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 5);
            }
        }
    }
}
