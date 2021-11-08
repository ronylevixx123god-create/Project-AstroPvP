package me.astropvp.astrospawners.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.MobCoinUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickMobcoinsNoteListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getPlayer() == null) return;
        if (e.getPlayer().getItemInHand() == null) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (MobCoinUtils.isCoinsNote(e.getPlayer().getItemInHand())) {
                if (e.getPlayer().getItemInHand().getAmount() > 1) {
                    e.getPlayer().sendMessage(CC.translate("&cYou cannot apply stacked mobcoins."));
                    e.setCancelled(true);
                    return;
                }
                int amount = MobCoinUtils.getCoinsAmountFromNote(e.getPlayer().getItemInHand());
                AstroSpawners.getInstance().getManager().getPlayerData(e.getPlayer()).setCoins(amount + AstroSpawners.getInstance().getManager().getPlayerData(e.getPlayer()).getCoins());
                e.getPlayer().sendMessage(CC.translate("&a&l+ " + amount + "&a&l Mobcoins"));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 2, 2);
                e.getPlayer().setItemInHand(null);
            }
        }
    }
}
