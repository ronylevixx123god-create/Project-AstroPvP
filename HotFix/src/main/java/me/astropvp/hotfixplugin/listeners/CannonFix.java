package me.astropvp.hotfixplugin.listeners;

import org.bukkit.event.Listener;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CannonFix implements Listener {
    private double ptf;
    private int ignoreUnderY;
    private Map<WorldLongPair, Integer> currentExplosionMap;
    private volatile int x;
    private volatile int z;
    private volatile int lastTick;
    private volatile boolean cancelled;
    private int timeFrame;

    public CannonFix() {
        this.currentExplosionMap = new ConcurrentHashMap<WorldLongPair, Integer>();
        this.ignoreUnderY = 2;
        this.ptf = 3;
        this.setTimeFrame();
    }

    private void setTimeFrame() {
        this.timeFrame = (int)(this.ptf * 100.0 / 5.0);
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntityType() != EntityType.PRIMED_TNT) {
            return;
        }
        if (event.blockList().isEmpty()) {
            return;
        }
        Location source = event.getLocation();
        if (source.getBlockY() < this.ignoreUnderY) {
            return;
        }
        TNTPrimed tntPrimed = (TNTPrimed)((Object)event.getEntity());
        Material type = source.getBlock().getType();
        if (type != Material.SAND && type != Material.GRAVEL && type != Material.ANVIL) {
            return;
        }
        String idAt = Board.getInstance().getIdAt(new FLocation(tntPrimed.getLocation()));
        if (idAt.equals(Board.getInstance().getIdAt(new FLocation(source)))) {
            return;
        }
        if (source.getBlockX() == this.x && source.getBlockZ() == this.z && MinecraftServer.currentTick == this.lastTick) {
            if (this.cancelled) {
                event.setCancelled(true);
            }
            return;
        }
        this.x = source.getBlockX();
        this.z = source.getBlockZ();
        this.lastTick = MinecraftServer.currentTick;
        Map.Entry<WorldLongPair, Integer> worldLongPair = this.searchForNearest(source);
        if (worldLongPair == null) {
            this.currentExplosionMap.put(new WorldLongPair(source.getWorld(), source.getBlockX() >> 4, source.getBlockZ() >> 4, source.getBlockX(), source.getBlockZ()), MinecraftServer.currentTick);
        } else {
            int current = worldLongPair.getValue();
            if (MinecraftServer.currentTick - current >= this.timeFrame) {
                this.currentExplosionMap.replace(worldLongPair.getKey(), MinecraftServer.currentTick);
            } else if (worldLongPair.getKey().getRealX() != source.getBlockX() || worldLongPair.getKey().getRealZ() != source.getBlockZ()) {
                event.setCancelled(true);
                this.cancelled = true;
                return;
            }
        }
        this.cancelled = false;
    }

    private Map.Entry<WorldLongPair, Integer> searchForNearest(Location location) {
        int x = location.getBlockX() >> 4;
        int z = location.getBlockZ() >> 4;
        for (Map.Entry<WorldLongPair, Integer> entry : this.currentExplosionMap.entrySet()) {
            if (entry.getKey().getX() != x || entry.getKey().getZ() != z || !location.getWorld().equals(entry.getKey().getWorld())) continue;
            return entry;
        }
        return null;
    }
}
