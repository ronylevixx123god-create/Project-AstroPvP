package net.astropvp.listeners;

import net.astropvp.FactionShield;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.massivecraft.factions.*;
import org.bukkit.event.*;

public class ExplosionListener implements Listener
{
    @EventHandler
    public void onPrimeEvent(final ExplosionPrimeEvent e) {
        if (!FactionShield.getPlugin().isFactionShieldActive(e.getEntity().getLocation())) {
            return;
        }
        if (!(e.getEntity() instanceof TNTPrimed)) {
            e.setCancelled(true);
            return;
        }
        final TNTPrimed tnt = (TNTPrimed)e.getEntity();
        final Faction source = Board.getInstance().getFactionAt(new FLocation(tnt.getSourceLoc()));
        final Faction destination = Board.getInstance().getFactionAt(new FLocation(tnt.getLocation()));
        if (source != destination) {
            e.setCancelled(true);
        }
    }
}
