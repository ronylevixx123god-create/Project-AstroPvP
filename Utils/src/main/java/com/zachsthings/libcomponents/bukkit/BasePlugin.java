/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandPermissionsException
 *  com.sk89q.minecraft.util.commands.CommandsManager
 *  com.sk89q.minecraft.util.commands.Injector
 *  com.sk89q.minecraft.util.commands.SimpleInjector
 *  com.sk89q.util.yaml.YAMLProcessor
 *  com.sk89q.wepif.PermissionsResolverManager
 */
package com.zachsthings.libcomponents.bukkit;

import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.Injector;
import com.sk89q.minecraft.util.commands.SimpleInjector;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.wepif.PermissionsResolverManager;
import com.zachsthings.libcomponents.ComponentManager;
import com.zachsthings.libcomponents.InvalidComponentException;
import com.zachsthings.libcomponents.bukkit.BukkitComponent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class BasePlugin
extends JavaPlugin {
    public boolean lowPriorityCommandRegistration;
    private boolean opPermissions;
    protected YAMLProcessor config;
    protected ComponentManager<BukkitComponent> componentManager;

    public static Server server() {
        return Bukkit.getServer();
    }

    public static <T extends Event> T callEvent(T event) {
        BasePlugin.server().getPluginManager().callEvent(event);
        return event;
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        this.componentManager.unloadComponents();
    }

    @Override
    public void onEnable() {
        this.getDataFolder().mkdirs();
        this.loadConfiguration();
        PermissionsResolverManager.initialize((Plugin)this);
        this.componentManager = new ComponentManager<BukkitComponent>(this.getLogger(), BukkitComponent.class){

            @Override
            protected void setUpComponent(BukkitComponent component) {
                CommandsManager<CommandSender> commands = new CommandsManager<CommandSender>(){

                    public boolean hasPermission(CommandSender sender, String permission) {
                        return BasePlugin.this.hasPermission(sender, permission);
                    }
                };
                commands.setInjector((Injector)new SimpleInjector(new Object[]{component}));
                component.setUp(BasePlugin.this, commands);
            }
        };
        this.registerComponentLoaders();
        try {
            this.componentManager.loadComponents();
        }
        catch (InvalidComponentException e) {
            this.getLogger().severe(e.getMessage());
        }
        this.componentManager.enableComponents();
        this.config.save();
    }

    public abstract void registerComponentLoaders();

    public YAMLProcessor createConfiguration() {
        return this.populateConfiguration();
    }

    public void loadConfiguration() {
        if (this.config == null) {
            this.config = this.createConfiguration();
        }
        this.populateConfiguration(this.config);
        this.lowPriorityCommandRegistration = this.config.getBoolean("low-priority-command-registration", false);
        this.opPermissions = this.config.getBoolean("op-permissions", true);
    }

    @Deprecated
    public YAMLProcessor populateConfiguration() {
        return this.config;
    }

    public void populateConfiguration(YAMLProcessor processor) {
    }

    public PermissionsResolverManager getPermissionsResolver() {
        return PermissionsResolverManager.getInstance();
    }

    public YAMLProcessor getGlobalConfiguration() {
        return this.config;
    }

    public ComponentManager<BukkitComponent> getComponentManager() {
        return this.componentManager;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createDefaultConfiguration(String name) {
        File actual = new File(this.getDataFolder(), name);
        if (!actual.exists()) {
            InputStream input = null;
            try {
                JarFile file = new JarFile(this.getFile());
                ZipEntry copy = file.getEntry("defaults/" + name);
                if (copy == null) {
                    throw new FileNotFoundException();
                }
                input = file.getInputStream(copy);
            }
            catch (IOException e) {
                this.getLogger().severe("Unable to read default configuration: " + name);
            }
            if (input != null) {
                FileOutputStream output = null;
                try {
                    int length;
                    output = new FileOutputStream(actual);
                    byte[] buf = new byte[8192];
                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }
                    this.getLogger().info("Default configuration file written: " + name);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        input.close();
                    }
                    catch (IOException ignore) {}
                    try {
                        if (output != null) {
                            output.close();
                        }
                    }
                    catch (IOException ignore) {}
                }
            }
        }
    }

    public boolean hasPermission(CommandSender sender, String perm) {
        if (!(sender instanceof Player)) {
            return sender.isOp() && this.opPermissions || sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender || this.getPermissionsResolver().hasPermission(sender.getName(), perm);
        }
        return this.hasPermission(sender, ((Player)sender).getWorld(), perm);
    }

    public boolean hasPermission(CommandSender sender, World world, String perm) {
        if (sender.isOp() && this.opPermissions || sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender) {
            return true;
        }
        if (sender instanceof Player) {
            Player player = (Player)sender;
            return this.getPermissionsResolver().hasPermission(world.getName(), player.getName(), perm);
        }
        return false;
    }

    public void checkPermission(CommandSender sender, String perm) throws CommandPermissionsException {
        if (!this.hasPermission(sender, perm)) {
            throw new CommandPermissionsException();
        }
    }

    public void checkPermission(CommandSender sender, World world, String perm) throws CommandPermissionsException {
        if (!this.hasPermission(sender, world, perm)) {
            throw new CommandPermissionsException();
        }
    }
}

