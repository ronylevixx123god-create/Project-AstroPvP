// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.shield;

import java.util.TimeZone;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import net.astropvp.FactionShieldsPlugin;
import net.astropvp.config.ShieldConfig;
import net.astropvp.inventory.InventoryHandler;
import net.astropvp.inventory.ItemData;
import net.astropvp.util.InvUtil;
import net.astropvp.util.ItemBuilder;

import java.util.Arrays;

import com.massivecraft.factions.Board;
import java.io.IOException;

import com.massivecraft.factions.Faction;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import com.massivecraft.factions.FLocation;
import java.util.List;

import org.bukkit.inventory.Inventory;
import java.text.SimpleDateFormat;
import org.bukkit.event.Listener;
import org.github.paperspigot.Title;

public class FactionShield implements Listener
{
    private static final SimpleDateFormat sdf;
    private transient Inventory inventory;
    private String factionId;
    private ItemData itemData;
    private boolean enabled;
    private long lastActivation;
    private long selectionTime;
    private String waiting;
    private String inventoryBase64;
    private String lastElapsedString;
    private boolean destroyed;
    private List<FLocation> region;
    
    public FactionShield(final String factionId, final ItemData data) {
        this.factionId = factionId;
        this.itemData = data;
        this.enabled = false;
        this.lastActivation = -1L;
        this.selectionTime = System.currentTimeMillis();
        this.waiting = " §8- §7Waiting for " + this.itemData.getTime() + "§7...";
        this.destroyed = false;
        this.region = new ArrayList<FLocation>();
        this.buildInventory();
        FactionShieldsPlugin.getInstance().registerListener((Listener)this);
    }
    
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent event) {
        if (this.isEnabled() && this.region.contains(new FLocation(event.getLocation()))) {
            final EntityType entityType = event.getEntityType();
            if (entityType == EntityType.PRIMED_TNT || entityType == EntityType.CREEPER) {
                event.setCancelled(true);
            }
        }
    }
    
    public void destroy() {
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        plugin.unregisterListener((Listener)this);
        plugin.getShieldHandler().removeShield(this);
        this.destroyed = true;
    }
    
    public void buildInventory() {
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        this.inventory = Bukkit.createInventory((InventoryHolder)null, ShieldConfig.INVENTORY_SIZE, ShieldConfig.INVENTORY_TITLE);
        final InventoryHandler inventoryHandler = plugin.getInventoryHandler();
        final Inventory defaultInventory = inventoryHandler.getDefaultInventory();
        for (int i = 0; i < defaultInventory.getSize(); ++i) {
            this.inventory.setItem(i, defaultInventory.getItem(i));
            if (this.itemData.getSlot() == i) {
                this.inventory.getItem(i).setType(Material.getMaterial(ShieldConfig.SELECTED_TIME));
            }
            else if (i >= ShieldConfig.START_SLOT && i <= ShieldConfig.END_SLOT) {
                this.inventory.getItem(i).setType(Material.getMaterial(ShieldConfig.SELECTED_BACKGROUND));
            }
            if (inventoryHandler.getInfoButtonSlot() == i) {
                this.updateInfoButton(this.enabled, this.waiting, inventoryHandler);
            }
        }
        this.inventoryBase64 = InvUtil.toBase64(this.inventory);
    }
    
    public void update() {
        this.updateStatus();
    }
    
    public void updateStatus() {
        final InventoryHandler inventoryHandler = FactionShieldsPlugin.getInstance().getInventoryHandler();
        final Date current = new Date(System.currentTimeMillis());
        if (!this.isEnabled()) {
            final String date = FactionShield.sdf.format(current);
            final String[] elements = date.split(" ");
            final String[] time = elements[0].split(":");
            final String hour = time[0];
            final String minutes = time[1];
            final String amOrPm = elements[1];
            final String[] currentTime = this.itemData.getTime().split(" ");
            final String currentHour = currentTime[0];
            final String currentDayOrNight = currentTime[1];
            if (amOrPm.equalsIgnoreCase(currentDayOrNight) && hour.equalsIgnoreCase(currentHour) && minutes.equalsIgnoreCase("00")) {
                this.activate();
            }
            return;
        }
        if (this.lastActivation != -1L) {
            final long timeElapsed = System.currentTimeMillis() - this.lastActivation;
            long seconds = timeElapsed / 1000L;
            long minutes2 = seconds / 60L;
            final long hours = minutes2 / 60L;
            if ((int)hours >= ShieldConfig.SHIELD_DURATION) {
                this.deactivate();
                return;
            }
            seconds -= minutes2 * 60L;
            minutes2 -= hours * 60L;
            final int hoursRemaining = ShieldConfig.SHIELD_DURATION - 1 - (int)hours;
            final int minutesRemaining = 59 - (int)minutes2;
            final int secondsRemaining = 59 - (int)seconds;
            final String time2 = String.format("%dh %dm %ds", hoursRemaining, minutesRemaining, secondsRemaining);
            this.updateInfoButton(this.enabled, " §8- §7" + time2, inventoryHandler);
            this.lastElapsedString = time2;
        }
    }
    
    public String getLastElapsedString() {
        return this.lastElapsedString;
    }
    
    private void updateInfoButton(final boolean active, final String addon, final InventoryHandler inventoryHandler) {
        final Inventory defaultInventory = inventoryHandler.getDefaultInventory();
        final ItemBuilder itemBuilder = new ItemBuilder(defaultInventory.getItem(inventoryHandler.getInfoButtonSlot()).clone());
        final String status = active ? "§aACTIVE" : "§cNOT ACTIVE";
        itemBuilder.setLore(inventoryHandler.getInfoButtonLore(), ImmutableList.of(Pair.of("%duration%", (ShieldConfig.SHIELD_DURATION + " hours")), Pair.of("%status%", (status + addon))));
        this.getInventory().setItem(inventoryHandler.getInfoButtonSlot(), itemBuilder.build());
    }
    
    private void deactivate() {
        this.enabled = false;
        this.notifyStateChange(ShieldConfig.SHIELD_INACTIVE);
        this.updateInfoButton(false, this.waiting, FactionShieldsPlugin.getInstance().getInventoryHandler());
    }
    
    private void activate() {
        this.enabled = true;
        this.notifyStateChange(ShieldConfig.SHIELD_ACTIVE);
        this.lastActivation = System.currentTimeMillis();
    }
    
    private void notifyStateChange(final String message) {
        try {
            this.getFaction().getOnlinePlayers().forEach(player -> {
                if (this.inventory.getViewers().contains(player.getPlayer())) {
                    player.closeInventory();
                }
                player.sendTitle(new Title(ShieldConfig.SHIELD_TITLE, message, 20, 20, 20));
            });
        }
        catch (NullPointerException ex) {}
    }
    
    public Faction getFaction() {
        return FactionUtils.getFactionById(this.factionId);
    }
    
    public String getFactionId() {
        return this.factionId;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public Inventory getInventory() {
        if (this.inventory == null) {
            try {
                this.inventory = InvUtil.fromBase64(this.inventoryBase64, ShieldConfig.INVENTORY_TITLE);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.inventory;
    }
    
    public ItemData getItemData() {
        return this.itemData;
    }
    
    public void setInventoryBase64(final Inventory inventory) {
        this.inventoryBase64 = InvUtil.toBase64(inventory);
    }
    
    public boolean isDestroyed() {
        return this.destroyed;
    }
    
    public List<FLocation> getRegion() {
        return this.region;
    }
    
    public void setRegion(final FLocation start) {
        this.region.add(start);
        this.loop(start);
    }
    
    public void loop(final FLocation loc) {
        final List<FLocation> nearby = this.getRelativeLocations(loc);
        for (final FLocation chunk : nearby) {
            if (Board.getInstance().getFactionAt(chunk).getId().equalsIgnoreCase(this.factionId) && !this.region.contains(chunk)) {
                this.region.add(chunk);
                this.loop(chunk);
            }
        }
    }
    
    private List<FLocation> getRelativeLocations(final FLocation location) {
        final FLocation a = location.getRelative(1, 0);
        final FLocation b = location.getRelative(-1, 0);
        final FLocation c = location.getRelative(0, 1);
        final FLocation d = location.getRelative(0, -1);
        return Arrays.asList(a, b, c, d);
    }
    
    public long getSelectionTime() {
        return this.selectionTime;
    }
    
    static {
        (sdf = new SimpleDateFormat("h:mm a z")).setTimeZone(TimeZone.getTimeZone(ShieldConfig.TIMEZONE.replace("EST", "America/New_York")));
    }
}
