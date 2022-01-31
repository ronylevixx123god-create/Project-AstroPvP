package me.venir.astrocollectors.listeners.collectorlisteners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.collector.Collector;
import me.venir.astrocollectors.collector.CollectorManager;
import me.venir.astrocollectors.inventories.CollectorsUI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener  implements Listener {

    private final AstroCollectors plugin;

    public PlayerInteractListener(AstroCollectors plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null ||event.getClickedBlock().getType() == Material.AIR) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() == Material.BEACON) {
            Player player = event.getPlayer();
            Location location = event.getClickedBlock().getLocation();
            if (!CollectorManager.DELEGATE.isCollectorContained(location)) return;
            if (player.getGameMode().equals(GameMode.SPECTATOR)) return;
            event.setCancelled(true);
            Location collectorLocation = CollectorManager.DELEGATE.getCollector(location).getLocation();
            assert collectorLocation != null;
            if (!collectorLocation.equals(location)) return;
            Collector collector = CollectorManager.DELEGATE.getCollector(event.getClickedBlock().getLocation());

            if (FactionUtils.getFaction(event.getPlayer()) != FactionUtils.getFactionAt(collectorLocation) && !event.getPlayer().isOp()) {
                event.getPlayer().sendMessage(CC.translate("&c&l(!)&c You need to be in the " + FactionUtils.getFactionAt(collectorLocation)) + " to open this collector!");
                return;
            }


            if (player.isSneaking()) {
                collector.sellAll(player);
                return;
            }

            CollectorsUI collectorUI = new CollectorsUI(plugin, player, location);
            collectorUI.getCollectorUI().open(player);
        }
    }
}