package net.astropvp.core.listeners;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InstaBreakSponge implements Listener {
    @EventHandler
    public void blockBuild(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL)
            return;
        Faction factionAt = Board.getInstance().getFactionAt(new FLocation(event.getPlayer().getLocation()));
        if (event.getClickedBlock().getType() == Material.SPONGE && (FPlayers.i.get(event.getPlayer().getUniqueId().toString()).getFaction() == factionAt || factionAt.isWilderness()))
            event.getClickedBlock().breakNaturally();
    }
}
