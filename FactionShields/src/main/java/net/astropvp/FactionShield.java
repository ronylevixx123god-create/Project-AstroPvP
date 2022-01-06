package net.astropvp;

import net.astropvp.listeners.CommandListener;
import net.astropvp.listeners.ExplosionListener;
import net.astropvp.listeners.GuiListener;
import org.bukkit.plugin.java.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import java.io.*;
import org.bukkit.*;
import com.massivecraft.factions.*;
import java.util.*;
import org.bukkit.configuration.file.*;

public class FactionShield extends JavaPlugin
{
    private static FactionShield plugin;
    public FileConfiguration data;
    private File dataFile;

    public static FactionShield getPlugin() {
        return FactionShield.plugin;
    }

    public void onEnable() {
        (FactionShield.plugin = this).saveDefaultConfig();
        this.loadConfigs();
        Bukkit.getPluginManager().registerEvents((Listener)new ExplosionListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new CommandListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new GuiListener(), (Plugin)this);
    }

    public void onDisable() {
        try {
            this.data.save(this.dataFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        FactionShield.plugin = null;
    }

    public boolean isFactionShieldActive(final Location location) {
        if (!Board.getInstance().getFactionAt(new FLocation(location)).isNormal()) {
            return false;
        }
        final List protectedHours = this.data.getIntegerList(Board.getInstance().getIdAt(new FLocation(location)));
        return protectedHours != null && protectedHours.contains(Calendar.getInstance(TimeZone.getTimeZone("US/Eastern")).get(11));
    }

    public boolean isFactionShieldActive(final Faction faction) {
        if (!faction.isNormal()) {
            return false;
        }
        final List protectedHours = this.data.getIntegerList(faction.getId());
        return protectedHours != null && protectedHours.contains(Calendar.getInstance(TimeZone.getTimeZone("US/Eastern")).get(11));
    }

    public List getProtectedHours(Faction faction) {
        final List protectedHours = this.data.getIntegerList(faction.getId());
        if (protectedHours != null) {
            return protectedHours;
        } else {
            ArrayList<String> empty = new ArrayList();
            return empty;
        }

    }

    public synchronized void saveData() {
        final FileConfiguration fileConfiguration = this.data;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)FactionShield.plugin, () -> {
            try {
                fileConfiguration.save(this.dataFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadConfigs() {
        this.dataFile = new File(this.getDataFolder(), "data.yml");
        if (!this.dataFile.exists()) {
            this.saveResource("data.yml", true);
        }
        try {
            this.data = YamlConfiguration.loadConfiguration(this.dataFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
