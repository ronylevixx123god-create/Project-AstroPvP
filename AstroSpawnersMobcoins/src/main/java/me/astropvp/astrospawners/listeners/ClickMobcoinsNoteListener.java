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
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (MobCoinUtils.isCoinsNote(e.getPlayer().getItemInHand())) {
                int amount = MobCoinUtils.getCoinsAmountFromNote(e.getPlayer().getItemInHand());
                AstroSpawners.getPlayerDataManager().getPlayerData(e.getPlayer()).setCoins(amount + AstroSpawners.getPlayerDataManager().getPlayerData(e.getPlayer()).getCoins());
                e.getPlayer().sendMessage(CC.translate("&a&l+ " + amount + "&a&l Mobcoins"));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 2, 2);
            }
        }
    }
}
