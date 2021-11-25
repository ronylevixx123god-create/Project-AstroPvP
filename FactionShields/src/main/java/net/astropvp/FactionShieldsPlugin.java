// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp;

import net.astropvp.inventory.InventoryHandler;
import net.astropvp.shield.ShieldCmd;
import net.astropvp.shield.ShieldHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import net.astropvp.shield.FactionEventCmd;
import net.astropvp.config.ShieldConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionShieldsPlugin extends JavaPlugin
{
    private static FactionShieldsPlugin instance;
    private ShieldHandler shieldHandler;
    private InventoryHandler inventoryHandler;
    
    public void onEnable() {

        instance = this;
        new Thread(() -> {
            Thread loadThread = ShieldConfig.get().getLoadThread();
            loadThread.start();
            try {
                loadThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.shieldHandler = new ShieldHandler();
            this.inventoryHandler = new InventoryHandler(this);
            new ShieldCmd();
            new FactionEventCmd();
            new ShieldBackups().start();
        }).start();
    }
    
    public void onDisable() {
        final Thread saveShields = new Thread(() -> this.getShieldHandler().saveAll("shields.json"));
        saveShields.start();
        try {
            saveShields.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void registerListener(final Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, (Plugin)this);
    }
    
    public void unregisterListener(final Listener listener) {
        HandlerList.unregisterAll(listener);
    }
    
    public static FactionShieldsPlugin getInstance() {
        return instance;
    }
    
    public ShieldHandler getShieldHandler() {
        return this.shieldHandler;
    }
    
    public InventoryHandler getInventoryHandler() {
        return this.inventoryHandler;
    }
}
