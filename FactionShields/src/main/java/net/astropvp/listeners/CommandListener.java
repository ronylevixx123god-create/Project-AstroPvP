package net.astropvp.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import net.astropvp.FactionShield;
import net.astropvp.items.GuiItems;
import org.bukkit.configuration.file.*;
import org.bukkit.event.player.*;
import com.massivecraft.factions.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class CommandListener implements Listener {
    private List<String> alias;
    private List<String> infoAlias;
    private FileConfiguration config;
    private FileConfiguration data;

    public CommandListener() {
        this.config = FactionShield.getPlugin().getConfig();
        this.data = FactionShield.getPlugin().data;
        this.alias = new ArrayList<String>();
        (this.infoAlias = new ArrayList<String>()).add("/f shield info");
        this.infoAlias.add("/f shields info");
        this.infoAlias.add("/fshield info");
        this.infoAlias.add("/fshields info");
        this.infoAlias.add("/fshieldinfo");
        this.infoAlias.add("/fshieldsinfo");
        this.infoAlias.add("/f shieldinfo");
        this.infoAlias.add("/f shieldsinfo");
        this.alias.add("/f shield");
        this.alias.add("/f shields");
        this.alias.add("/fshield");
        this.alias.add("/fshields");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFactionCommand(final PlayerCommandPreprocessEvent event) {
        final String cmd = event.getMessage().toLowerCase();
        for (final String cmdAlias : this.alias) {
            if (cmd.startsWith(cmdAlias)) {
                event.setCancelled(true);

                if (!Conf.gracePeriod) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lFactionShields: &7You cannot edit your /f shield anymore! This is only available while grace period is enabled!"));
                    event.setCancelled(true);
                    return;
                }

                if (!FactionUtils.getFaction(event.getPlayer()).isNormal()) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("lang.no-factions")));
                    event.setCancelled(true);
                    return;
                }
                this.openGui(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    private void openGui(final Player player) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)player, this.config.getInt("gui.rowsize") * 9, ChatColor.translateAlternateColorCodes('&', this.config.getString("gui.title")));
        final ItemStack fillMaterial = new ItemStack(this.config.getInt("gui.items.fill-material.id"), 1, (short)this.config.getInt("gui.items.fill-material.subid"));
        final ItemMeta fillMeta = fillMaterial.getItemMeta();
        fillMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.config.getString("gui.items.fill-material.name")));
        fillMaterial.setItemMeta(fillMeta);
        for (int i = 0; i < 24; ++i) {
            if (this.data.getIntegerList(FactionUtils.getFaction(player).getId()).contains(i)) {
                inventory.setItem(this.config.getInt("gui.items." + i + ".slot"), GuiItems.getHourOnMaterial(i));
            }
            else {
                inventory.setItem(this.config.getInt("gui.items." + i + ".slot"), GuiItems.getHourOffMaterial(i));
            }
        }
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillMaterial);
            }
        }
        player.openInventory(inventory);
    }
}
