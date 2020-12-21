/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents;

import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.config.ConfigurationBase;
import com.zachsthings.libcomponents.config.ConfigurationFile;
import com.zachsthings.libcomponents.config.ConfigurationNode;
import com.zachsthings.libcomponents.loader.ComponentLoader;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractComponent {
    private ConfigurationNode rawConfiguration;
    private ComponentLoader loader;
    private ComponentInformation info;
    private boolean enabled;

    protected void setUp(ComponentLoader loader, ComponentInformation info) {
        this.loader = loader;
        this.info = info;
    }

    public abstract void enable();

    public void disable() {
    }

    public void reload() {
        if (this.rawConfiguration != null) {
            this.rawConfiguration = this.getComponentLoader().getConfiguration(this);
        }
    }

    protected <T extends ConfigurationBase> T configure(T config) {
        config.load(this.getRawConfiguration());
        return config;
    }

    public <T extends ConfigurationBase> T saveConfig(T config) {
        config.save(this.getRawConfiguration());
        this.saveConfig();
        return config;
    }

    public void saveConfig() {
        if (this.rawConfiguration != null && this.rawConfiguration instanceof ConfigurationFile) {
            ((ConfigurationFile)this.rawConfiguration).save();
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ComponentLoader getComponentLoader() {
        return this.loader;
    }

    public ComponentInformation getInformation() {
        return this.info;
    }

    public ConfigurationNode getRawConfiguration() {
        if (this.rawConfiguration != null) {
            return this.rawConfiguration;
        }
        this.rawConfiguration = this.getComponentLoader().getConfiguration(this);
        return this.rawConfiguration;
    }

    public abstract Map<String, String> getCommands();
}

