/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.config.ConfigurationFile;
import com.zachsthings.libcomponents.config.ConfigurationNode;
import com.zachsthings.libcomponents.loader.AbstractComponentLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ConfigListedComponentLoader
extends AbstractComponentLoader {
    private final ConfigurationNode jarComponentAliases;
    private final ConfigurationNode globalConfig;

    public ConfigListedComponentLoader(Logger logger, ConfigurationNode globalConfig, ConfigurationNode aliasList, File configDir) {
        super(logger, configDir);
        this.jarComponentAliases = aliasList;
        this.globalConfig = globalConfig;
    }

    @Override
    public Collection<AbstractComponent> loadComponents() {
        ArrayList<AbstractComponent> components = new ArrayList<AbstractComponent>();
        LinkedHashSet<String> disabledComponents = new LinkedHashSet<String>(this.globalConfig.getStringList("components.disabled", null));
        LinkedHashSet<String> stagedEnabled = new LinkedHashSet<String>(this.globalConfig.getStringList("components.enabled", null));
        for (String key : this.jarComponentAliases.getKeys(null)) {
            if (stagedEnabled.contains(key) || stagedEnabled.contains(this.jarComponentAliases.getString(key + ".class", key))) continue;
            if (this.jarComponentAliases.getBoolean(key + ".default")) {
                stagedEnabled.add(key);
                continue;
            }
            disabledComponents.add(key);
        }
        stagedEnabled.removeAll(disabledComponents);
        Iterator i = stagedEnabled.iterator();
        while (i.hasNext()) {
            String nextName = (String)i.next();
            nextName = this.jarComponentAliases.getString(nextName + ".class", nextName);
            Class<?> clazz = null;
            try {
                clazz = Class.forName(nextName);
            }
            catch (ClassNotFoundException ignore) {
                // empty catch block
            }
            if (!this.isComponentClass(clazz)) {
                this.getLogger().warning("Invalid or unknown class found in enabled components: " + nextName + ". Moving to disabled components list.");
                i.remove();
                disabledComponents.add(nextName);
                continue;
            }
            try {
                components.add(this.instantiateComponent(clazz));
            }
            catch (Throwable t) {
                this.getLogger().warning("Error initializing component " + clazz + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        this.globalConfig.setProperty("components.disabled", new ArrayList<String>(disabledComponents));
        this.globalConfig.setProperty("components.enabled", new ArrayList<String>(stagedEnabled));
        return components;
    }

    @Override
    public ConfigurationNode getConfiguration(AbstractComponent component) {
        return this.globalConfig.getNode("component." + this.toFileName(component));
    }

    @Override
    public ConfigurationFile createConfigurationNode(File configFile) {
        throw new UnsupportedOperationException("This is not used in ConfigListedComponentLoader!");
    }
}

