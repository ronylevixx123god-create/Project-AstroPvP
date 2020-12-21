/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.plugin.java;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.Warning;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.PluginClassLoader;

public abstract class JavaPlugin
extends PluginBase {
    private boolean isEnabled = false;
    private PluginLoader loader = null;
    private Server server = null;
    private File file = null;
    private PluginDescriptionFile description = null;
    private File dataFolder = null;
    private ClassLoader classLoader = null;
    private boolean naggable = true;
    private EbeanServer ebean = null;
    private FileConfiguration newConfig = null;
    private File configFile = null;
    private PluginLogger logger = null;

    public JavaPlugin() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof PluginClassLoader)) {
            throw new IllegalStateException("JavaPlugin requires " + PluginClassLoader.class.getName());
        }
        ((PluginClassLoader)classLoader).initialize(this);
    }

    @Deprecated
    protected JavaPlugin(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader instanceof PluginClassLoader) {
            throw new IllegalStateException("Cannot use initialization constructor at runtime");
        }
        this.init(loader, server, description, dataFolder, file, classLoader);
    }

    protected JavaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        if (classLoader instanceof PluginClassLoader) {
            throw new IllegalStateException("Cannot use initialization constructor at runtime");
        }
        this.init(loader, loader.server, description, dataFolder, file, classLoader);
    }

    @Override
    public final File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public final PluginLoader getPluginLoader() {
        return this.loader;
    }

    @Override
    public final Server getServer() {
        return this.server;
    }

    @Override
    public final boolean isEnabled() {
        return this.isEnabled;
    }

    protected File getFile() {
        return this.file;
    }

    @Override
    public final PluginDescriptionFile getDescription() {
        return this.description;
    }

    @Override
    public FileConfiguration getConfig() {
        if (this.newConfig == null) {
            this.reloadConfig();
        }
        return this.newConfig;
    }

    @Override
    public void reloadConfig() {
        this.newConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defConfigStream = this.getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.newConfig.setDefaults(defConfig);
        }
    }

    @Override
    public void saveConfig() {
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
        }
    }

    @Override
    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            this.saveResource("config.yml", false);
        }
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
        InputStream in = this.getResource(resourcePath = resourcePath.replace('\\', '/'));
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
        }
        File outFile = new File(this.dataFolder, resourcePath);
        int lastIndex = resourcePath.lastIndexOf(47);
        File outDir = new File(this.dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        try {
            if (!outFile.exists() || replace) {
                int len;
                FileOutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    ((OutputStream)out).write(buf, 0, len);
                }
                ((OutputStream)out).close();
                in.close();
            } else {
                this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        }
        catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    @Override
    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }
        try {
            URL url = this.getClassLoader().getResource(filename);
            if (url == null) {
                return null;
            }
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        }
        catch (IOException ex) {
            return null;
        }
    }

    protected final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    protected final void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            if (this.isEnabled) {
                this.onEnable();
            } else {
                this.onDisable();
            }
        }
    }

    @Deprecated
    protected final void initialize(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
        if (server.getWarningState() == Warning.WarningState.OFF) {
            return;
        }
        this.getLogger().log(Level.WARNING, this.getClass().getName() + " is already initialized", server.getWarningState() == Warning.WarningState.DEFAULT ? null : new AuthorNagException("Explicit initialization"));
    }

    final void init(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
        this.loader = loader;
        this.server = server;
        this.file = file;
        this.description = description;
        this.dataFolder = dataFolder;
        this.classLoader = classLoader;
        this.configFile = new File(dataFolder, "config.yml");
        this.logger = new PluginLogger(this);
        if (description.isDatabaseEnabled()) {
            ServerConfig db = new ServerConfig();
            db.setDefaultServer(false);
            db.setRegister(false);
            db.setClasses(this.getDatabaseClasses());
            db.setName(description.getName());
            server.configureDbConfig(db);
            DataSourceConfig ds = db.getDataSourceConfig();
            ds.setUrl(this.replaceDatabaseString(ds.getUrl()));
            dataFolder.mkdirs();
            ClassLoader previous = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(classLoader);
            this.ebean = EbeanServerFactory.create(db);
            Thread.currentThread().setContextClassLoader(previous);
        }
    }

    public List<Class<?>> getDatabaseClasses() {
        return new ArrayList();
    }

    private String replaceDatabaseString(String input) {
        input = input.replaceAll("\\{DIR\\}", this.dataFolder.getPath().replaceAll("\\\\", "/") + "/");
        input = input.replaceAll("\\{NAME\\}", this.description.getName().replaceAll("[^\\w_-]", ""));
        return input;
    }

    @Deprecated
    public final boolean isInitialized() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public PluginCommand getCommand(String name) {
        String alias = name.toLowerCase();
        PluginCommand command = this.getServer().getPluginCommand(alias);
        if (command == null || command.getPlugin() != this) {
            command = this.getServer().getPluginCommand(this.description.getName().toLowerCase() + ":" + alias);
        }
        if (command != null && command.getPlugin() == this) {
            return command;
        }
        return null;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return null;
    }

    @Override
    public final boolean isNaggable() {
        return this.naggable;
    }

    @Override
    public final void setNaggable(boolean canNag) {
        this.naggable = canNag;
    }

    @Override
    public EbeanServer getDatabase() {
        return this.ebean;
    }

    protected void installDDL() {
        SpiEbeanServer serv = (SpiEbeanServer)this.getDatabase();
        DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(false, gen.generateCreateDdl());
    }

    protected void removeDDL() {
        SpiEbeanServer serv = (SpiEbeanServer)this.getDatabase();
        DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(true, gen.generateDropDdl());
    }

    @Override
    public final Logger getLogger() {
        return this.logger;
    }

    public String toString() {
        return this.description.getFullName();
    }

    public static <T extends JavaPlugin> T getPlugin(Class<T> clazz) {
        Validate.notNull(clazz, "Null class cannot have a plugin");
        if (!JavaPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz + " does not extend " + JavaPlugin.class);
        }
        ClassLoader cl = clazz.getClassLoader();
        if (!(cl instanceof PluginClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not initialized by " + PluginClassLoader.class);
        }
        JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
        if (plugin == null) {
            throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
        }
        return (T)((JavaPlugin)clazz.cast(plugin));
    }

    public static JavaPlugin getProvidingPlugin(Class<?> clazz) {
        Validate.notNull(clazz, "Null class cannot have a plugin");
        ClassLoader cl = clazz.getClassLoader();
        if (!(cl instanceof PluginClassLoader)) {
            throw new IllegalArgumentException(clazz + " is not provided by " + PluginClassLoader.class);
        }
        JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
        if (plugin == null) {
            throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
        }
        return plugin;
    }
}

