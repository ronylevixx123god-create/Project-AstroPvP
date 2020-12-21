/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.plugin.java;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

final class PluginClassLoader
extends URLClassLoader {
    private final JavaPluginLoader loader;
    private final Map<String, Class<?>> classes = new HashMap();
    private final PluginDescriptionFile description;
    private final File dataFolder;
    private final File file;
    final JavaPlugin plugin;
    private JavaPlugin pluginInit;
    private IllegalStateException pluginState;

    PluginClassLoader(JavaPluginLoader loader, ClassLoader parent, PluginDescriptionFile description, File dataFolder, File file) throws InvalidPluginException, MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, parent);
        Validate.notNull(loader, "Loader cannot be null");
        this.loader = loader;
        this.description = description;
        this.dataFolder = dataFolder;
        this.file = file;
        try {
            Class<JavaPlugin> pluginClass;
            Class<?> jarClass;
            try {
                jarClass = Class.forName(description.getMain(), true, this);
            }
            catch (ClassNotFoundException ex) {
                throw new InvalidPluginException("Cannot find main class `" + description.getMain() + "'", ex);
            }
            try {
                pluginClass = jarClass.asSubclass(JavaPlugin.class);
            }
            catch (ClassCastException ex) {
                throw new InvalidPluginException("main class `" + description.getMain() + "' does not extend JavaPlugin", ex);
            }
            this.plugin = pluginClass.newInstance();
        }
        catch (IllegalAccessException ex) {
            throw new InvalidPluginException("No public constructor", ex);
        }
        catch (InstantiationException ex) {
            throw new InvalidPluginException("Abnormal plugin type", ex);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return this.findClass(name, true);
    }

    Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("org.bukkit.") || name.startsWith("net.minecraft.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = this.classes.get(name);
        if (result == null) {
            if (checkGlobal) {
                result = this.loader.getClassByName(name);
            }
            if (result == null && (result = super.findClass(name)) != null) {
                this.loader.setClass(name, result);
            }
            this.classes.put(name, result);
        }
        return result;
    }

    Set<String> getClasses() {
        return this.classes.keySet();
    }

    synchronized void initialize(JavaPlugin javaPlugin) {
        Validate.notNull(javaPlugin, "Initializing plugin cannot be null");
        Validate.isTrue(javaPlugin.getClass().getClassLoader() == this, "Cannot initialize plugin outside of this class loader");
        if (this.plugin != null || this.pluginInit != null) {
            throw new IllegalArgumentException("Plugin already initialized!", this.pluginState);
        }
        this.pluginState = new IllegalStateException("Initial initialization");
        this.pluginInit = javaPlugin;
        javaPlugin.init(this.loader, this.loader.server, this.description, this.dataFolder, this.file, this);
    }
}

