package me.ifamasssxd.corechunkfinder;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import net.astropvp.core.AstroCore;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftTNTPrimed;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CoreChunkListener implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!AstroCore.getInstance().isTntDebug()) {
            return;
        }
        if (!(event.getEntity() instanceof TNTPrimed)) {
            return;
        }
        FLocation location = new FLocation(event.getLocation());
        Faction inside = Board.getFactionAt((FLocation)location);
        if (inside == null || !inside.isNormal()) {
            AstroCore.getInstance().getLogger().info("TNT Exploded not inside a faction: " + this.getLocation(event.getLocation()));
            return;
        }
        TNTPrimed primed = (TNTPrimed)event.getEntity();
        Block source = ((CraftTNTPrimed)primed).getHandle().sourceLoc.getBlock();
        Faction cameFrom = Board.getFactionAt((FLocation)new FLocation(source));
        if (cameFrom != null && cameFrom.equals((Object)inside)) {
            AstroCore.getInstance().getLogger().info("TNT from inside itself!");
            return;
        }
        if (cameFrom != null && cameFrom.isNormal()) {
            AstroCore.getInstance().getLogger().info("Calling onRaidedByFaction: " + this.getLocation(event.getLocation()) + " source: " + this.getLocation(source.getLocation()) + " raiding: " + inside.getTag() + " came from: " + cameFrom.getTag());
        } else {
            AstroCore.getInstance().getLogger().info("Didnt come from a valid chunk: " + (Object)cameFrom + " Location: " + this.getLocation(source.getLocation()));
        }
    }

    private String getLocation(Location location) {
        return location.getX() + "z, " + location.getY() + "y, " + location.getZ() + "z";
    }
}
