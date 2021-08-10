package net.astropvp.core.tasks;

import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import net.astropvp.core.AstroCore;
import net.astropvp.core.items.DimensionalGatewayItem;
import net.astropvp.core.objects.GatewayData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DimensionalGatewayTask extends BukkitRunnable {
    private long dontUpdateHoloUntil;
    private Player player;
    private Hologram hologram;
    private DimensionalGatewayItem item;
    private  ItemStack itemStack;
    private  GatewayData data;
    int i = 20;
    @Override
    public void run() {
        i--;
        Bukkit.broadcastMessage(String.valueOf(i));
        hologram.delete();
        hologram = (Hologram) HolographicDisplaysAPI.createHologram(AstroCore.getInstance(), this.hologram.getLocation(), item.getHologramText(player, i));
        if(i < 0) {
            item.endGateway(player, itemStack, data);
        }
    }

    public DimensionalGatewayTask(Player player, Hologram hologram, DimensionalGatewayItem item, ItemStack itemStack, GatewayData data) {
        this.player = player;
        this.hologram = hologram;
        this.item = item;
        this.itemStack = itemStack;
        this.data = data;
    }
}
