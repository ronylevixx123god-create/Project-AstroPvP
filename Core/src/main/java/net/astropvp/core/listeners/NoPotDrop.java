package net.astropvp.core.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class NoPotDrop implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Iterator<ItemStack> iterator = e.getDrops().iterator();
        while (iterator.hasNext()) {
            ItemStack i = iterator.next();
            if (i.getType() == Material.POTION) {
                iterator.remove();
            }
        }
    }
}
