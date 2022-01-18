package net.astropvp.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import net.astropvp.FactionShield;
import net.astropvp.items.GuiItems;
import org.bukkit.configuration.file.*;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.*;

public class GuiListener implements Listener
{
    private FileConfiguration config;
    private FileConfiguration data;

    public GuiListener() {
        this.config = FactionShield.getPlugin().getConfig();
        this.data = FactionShield.getPlugin().data;
    }

    @EventHandler
    public void onGuiClick(final InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getClickedInventory().getTitle() == null) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getWhoClicked();
        if (ChatColor.translateAlternateColorCodes('&', this.config.getString("gui.title")).equalsIgnoreCase(e.getClickedInventory().getName())) {
            e.setResult(Event.Result.DENY);
            int hour = 0;
            boolean found = false;
            for (int i = 0; i < 24; ++i) {
                if (this.config.getInt("gui.items." + i + ".slot") == e.getSlot()) {
                    found = true;
                    hour = i;
                }
            }
            if (found) {
                if (this.data.getIntegerList(FactionUtils.getFaction(p).getId()).contains(hour)) {
                    final List<Integer> protectedHours = (List<Integer>)this.data.getIntegerList(FactionUtils.getFaction(p).getId());
                    protectedHours.remove((Object)hour);
                    this.data.set(FactionUtils.getFaction(p).getId(), (Object)protectedHours);
                    FactionShield.getPlugin().saveData();
                    e.getView().setItem(e.getSlot(), GuiItems.getHourOffMaterial(hour));
                }
                else {
                    final List<Integer> protectedHours = (List<Integer>)this.data.getIntegerList(FactionUtils.getFaction(p).getId());
                    if (protectedHours.size() >= this.config.getInt("max-protected-hours")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("lang.max-hour")));
                        return;
                    }
                    protectedHours.add(hour);
                    this.data.set(FactionUtils.getFaction(p).getId(), (Object)protectedHours);
                    FactionShield.getPlugin().saveData();
                    e.getView().setItem(e.getSlot(), GuiItems.getHourOnMaterial(hour));
                }
            }
        }
    }
}
