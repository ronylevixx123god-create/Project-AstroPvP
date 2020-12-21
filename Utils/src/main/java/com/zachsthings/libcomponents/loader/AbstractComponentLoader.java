/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.InvalidComponentException;
import com.zachsthings.libcomponents.config.ConfigurationFile;
import com.zachsthings.libcomponents.config.ConfigurationNode;
import com.zachsthings.libcomponents.loader.ComponentLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractComponentLoader
implements ComponentLoader {
    private final File configDir;
    private final Logger logger;

    protected AbstractComponentLoader(Logger logger, File configDir) {
        this.configDir = configDir;
        this.logger = logger;
        if (!configDir.exists() || !configDir.isDirectory()) {
            configDir.mkdirs();
        }
    }

    @Override
    public ConfigurationNode getConfiguration(AbstractComponent component) {
        File configFile = new File(this.configDir, this.toFileName(component) + ".yml");
        ConfigurationFile config = this.createConfigurationNode(configFile);
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            config.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public abstract ConfigurationFile createConfigurationNode(File var1);

    protected AbstractComponent instantiateComponent(Class<?> clazz) throws Throwable {
        if (!clazz.isAnnotationPresent(ComponentInformation.class)) {
            throw new InvalidComponentException(clazz, "No ComponentInformation annotation!");
        }
        Constructor<AbstractComponent> construct = clazz.asSubclass(AbstractComponent.class).getConstructor(new Class[0]);
        return construct.newInstance(new Object[0]);
    }

    public boolean isComponentClass(Class<?> clazz) {
        return clazz != null && AbstractComponent.class.isAssignableFrom(clazz);
    }

    public String toFileName(AbstractComponent component) {
        return component.getInformation().friendlyName().replaceAll(" ", "-").toLowerCase();
    }

    protected Logger getLogger() {
        return this.logger;
    }
}

