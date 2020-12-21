/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.plugin.java;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.Warning;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.TimedRegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.PluginClassLoader;
import org.yaml.snakeyaml.error.YAMLException;

public final class JavaPluginLoader
implements PluginLoader {
    final Server server;
    private final Pattern[] fileFilters = new Pattern[]{Pattern.compile("\\.jar$")};
    private final Map<String, Class<?>> classes = new HashMap();
    private final Map<String, PluginClassLoader> loaders = new LinkedHashMap<String, PluginClassLoader>();

    @Deprecated
    public JavaPluginLoader(Server instance) {
        Validate.notNull(instance, "Server cannot be null");
        this.server = instance;
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException {
        PluginClassLoader loader;
        PluginDescriptionFile description;
        Validate.notNull(file, "File cannot be null");
        if (!file.exists()) {
            throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " does not exist"));
        }
        try {
            description = this.getPluginDescription(file);
        }
        catch (InvalidDescriptionException ex) {
            throw new InvalidPluginException(ex);
        }
        File dataFolder = new File(file.getParentFile(), description.getName());
        File oldDataFolder = this.getDataFolder(file);
        if (!dataFolder.equals(oldDataFolder)) {
            if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
                this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) found old-data folder: %s next to the new one: %s", description.getName(), file, oldDataFolder, dataFolder));
            } else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
                if (!oldDataFolder.renameTo(dataFolder)) {
                    throw new InvalidPluginException("Unable to rename old data folder: '" + oldDataFolder + "' to: '" + dataFolder + "'");
                }
                this.server.getLogger().log(Level.INFO, String.format("While loading %s (%s) renamed data folder: '%s' to '%s'", description.getName(), file, oldDataFolder, dataFolder));
            }
        }
        if (dataFolder.exists() && !dataFolder.isDirectory()) {
            throw new InvalidPluginException(String.format("Projected datafolder: '%s' for %s (%s) exists and is not a directory", dataFolder, description.getName(), file));
        }
        List<String> depend = description.getDepend();
        if (depend == null) {
            depend = ImmutableList.of();
        }
        for (String pluginName : depend) {
            if (this.loaders == null) {
                throw new UnknownDependencyException(pluginName);
            }
            PluginClassLoader current = this.loaders.get(pluginName);
            if (current != null) continue;
            throw new UnknownDependencyException(pluginName);
        }
        try {
            loader = new PluginClassLoader(this, this.getClass().getClassLoader(), description, dataFolder, file);
        }
        catch (InvalidPluginException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new InvalidPluginException(ex);
        }
        this.loaders.put(description.getName(), loader);
        return loader.plugin;
    }

    private File getDataFolder(File file) {
        File dataFolder = null;
        String filename = file.getName();
        int index = file.getName().lastIndexOf(".");
        if (index != -1) {
            String name = filename.substring(0, index);
            dataFolder = new File(file.getParentFile(), name);
        } else {
            dataFolder = new File(file.getParentFile(), filename + "_");
        }
        return dataFolder;
    }

    @Override
    public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
        Validate.notNull(file, "File cannot be null");
        JarFile jar = null;
        InputStream stream = null;
        try {
            jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("plugin.yml");
            if (entry == null) {
                throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain plugin.yml"));
            }
            stream = jar.getInputStream(entry);
            PluginDescriptionFile pluginDescriptionFile = new PluginDescriptionFile(stream);
            return pluginDescriptionFile;
        }
        catch (IOException ex) {
            throw new InvalidDescriptionException(ex);
        }
        catch (YAMLException ex) {
            throw new InvalidDescriptionException(ex);
        }
        finally {
            if (jar != null) {
                try {
                    jar.close();
                }
                catch (IOException e) {}
            }
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException e) {}
            }
        }
    }

    @Override
    public Pattern[] getPluginFileFilters() {
        return (Pattern[])this.fileFilters.clone();
    }

    Class<?> getClassByName(String name) {
        Class<?> cachedClass = this.classes.get(name);
        if (cachedClass != null) {
            return cachedClass;
        }
        for (String current : this.loaders.keySet()) {
            PluginClassLoader loader = this.loaders.get(current);
            try {
                cachedClass = loader.findClass(name, false);
            }
            catch (ClassNotFoundException cnfe) {
                // empty catch block
            }
            if (cachedClass == null) continue;
            return cachedClass;
        }
        return null;
    }

    void setClass(String name, Class<?> clazz) {
        if (!this.classes.containsKey(name)) {
            this.classes.put(name, clazz);
            if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                Class<ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.registerClass(serializable);
            }
        }
    }

    private void removeClass(String name) {
        Class<?> clazz = this.classes.remove(name);
        try {
            if (clazz != null && ConfigurationSerializable.class.isAssignableFrom(clazz)) {
                Class<ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
                ConfigurationSerialization.unregisterClass(serializable);
            }
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    @Override
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin) {
        HashSet<Method> methods;
        Validate.notNull(plugin, "Plugin can not be null");
        Validate.notNull(listener, "Listener can not be null");
        boolean useTimings = this.server.getPluginManager().useTimings();
        HashMap<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            methods = new HashSet<Method>(publicMethods.length, Float.MAX_VALUE);
            for (Method method : publicMethods) {
                methods.add(method);
            }
            for (Method method : listener.getClass().getDeclaredMethods()) {
                methods.add(method);
            }
        }
        catch (NoClassDefFoundError e) {
            plugin.getLogger().severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
            return ret;
        }
        for (final Method method : methods) {
            Class<?> checkClass;
            EventHandler eh = method.getAnnotation(EventHandler.class);
            if (eh == null) continue;
            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                plugin.getLogger().severe(plugin.getDescription().getFullName() + " attempted to register an invalid EventHandler method signature \"" + method.toGenericString() + "\" in " + listener.getClass());
                continue;
            }
            final Class<Event> eventClass = checkClass.asSubclass(Event.class);
            method.setAccessible(true);
            HashSet<RegisteredListener> eventSet = (HashSet<RegisteredListener>)ret.get(eventClass);
            if (eventSet == null) {
                eventSet = new HashSet<RegisteredListener>();
                ret.put(eventClass, eventSet);
            }
            Class<Event> clazz = eventClass;
            while (Event.class.isAssignableFrom(clazz)) {
                if (clazz.getAnnotation(Deprecated.class) != null) {
                    Warning warning = clazz.getAnnotation(Warning.class);
                    Warning.WarningState warningState = this.server.getWarningState();
                    if (!warningState.printFor(warning)) break;
                    plugin.getLogger().log(Level.WARNING, String.format("\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.", plugin.getDescription().getFullName(), clazz.getName(), method.toGenericString(), warning != null && warning.reason().length() != 0 ? warning.reason() : "Server performance will be affected", Arrays.toString(plugin.getDescription().getAuthors().toArray())), warningState == Warning.WarningState.ON ? new AuthorNagException(null) : null);
                    break;
                }
                clazz = clazz.getSuperclass();
            }
            EventExecutor executor = new EventExecutor(){

                @Override
                public void execute(Listener listener, Event event) throws EventException {
                    try {
                        if (!eventClass.isAssignableFrom(event.getClass())) {
                            return;
                        }
                        method.invoke(listener, event);
                    }
                    catch (InvocationTargetException ex) {
                        throw new EventException(ex.getCause());
                    }
                    catch (Throwable t) {
                        throw new EventException(t);
                    }
                }
            };
            if (useTimings) {
                eventSet.add(new TimedRegisteredListener(listener, executor, eh.priority(), plugin, eh.ignoreCancelled()));
                continue;
            }
            eventSet.add(new RegisteredListener(listener, executor, eh.priority(), plugin, eh.ignoreCancelled()));
        }
        return ret;
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
        if (!plugin.isEnabled()) {
            plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
            JavaPlugin jPlugin = (JavaPlugin)plugin;
            String pluginName = jPlugin.getDescription().getName();
            if (!this.loaders.containsKey(pluginName)) {
                this.loaders.put(pluginName, (PluginClassLoader)jPlugin.getClassLoader());
            }
            try {
                jPlugin.setEnabled(true);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            this.server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
        }
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
        if (plugin.isEnabled()) {
            String message = String.format("Disabling %s", plugin.getDescription().getFullName());
            plugin.getLogger().info(message);
            this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));
            JavaPlugin jPlugin = (JavaPlugin)plugin;
            ClassLoader cloader = jPlugin.getClassLoader();
            try {
                jPlugin.setEnabled(false);
            }
            catch (Throwable ex) {
                this.server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
            }
            this.loaders.remove(jPlugin.getDescription().getName());
            if (cloader instanceof PluginClassLoader) {
                PluginClassLoader loader = (PluginClassLoader)cloader;
                Set<String> names = loader.getClasses();
                for (String name : names) {
                    this.removeClass(name);
                }
            }
        }
    }
}

