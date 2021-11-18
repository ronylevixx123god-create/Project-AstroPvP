package me.astropvp;

import com.avaje.ebean.EbeanServer;
import java.io.InputStream;
import java.util.List;

import me.astropvp.command.GenBucketCommand;
import me.astropvp.command.GenTaskCommand;
import me.astropvp.handler.GenBucketHandler;
import me.astropvp.listener.GenBucketListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class GenBucketPlugin extends JavaPlugin {
    private static GenBucketPlugin instance;
    private GenBucketHandler bucketHandler;

    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        instance = this;
        this.bucketHandler = new GenBucketHandler(this);
        GenBucketCommand genBucketCommand;
        this.getCommand("genbucket").setExecutor(genBucketCommand = new GenBucketCommand(this));
        GenTaskCommand genTaskCommand;
        this.getCommand("gentask").setExecutor(genTaskCommand = new GenTaskCommand(this));
        this.getServer().getPluginManager().registerEvents(new GenBucketListener(this, genBucketCommand, genTaskCommand), this);
    }


    public static GenBucketPlugin getInstance() {
        return instance;
    }

    public PluginCommand getCommand(String name) {
        return super.getCommand(name);
    }

    public InputStream getResource(String filename) {
        return super.getResource(filename);
    }

    public FileConfiguration getConfig() {
        return super.getConfig();
    }

    public EbeanServer getDatabase() {
        return super.getDatabase();
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return super.getDefaultWorldGenerator(worldName, id);
    }

    public GenBucketHandler getBucketHandler() {
        return this.bucketHandler;
    }

    public List<Class<?>> getDatabaseClasses() {
        return super.getDatabaseClasses();
    }
}
