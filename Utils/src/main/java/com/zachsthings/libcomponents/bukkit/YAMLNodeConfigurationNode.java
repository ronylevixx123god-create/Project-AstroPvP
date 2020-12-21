/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.util.yaml.YAMLNode
 */
package com.zachsthings.libcomponents.bukkit;

import com.sk89q.util.yaml.YAMLNode;
import com.zachsthings.libcomponents.config.ConfigurationNode;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class YAMLNodeConfigurationNode
implements ConfigurationNode {
    private final YAMLNode wrapped;

    public YAMLNodeConfigurationNode(YAMLNode wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ConfigurationNode getNode(String node) {
        YAMLNode ret = this.wrapped.getNode(node);
        if (ret == null) {
            ret = this.wrapped.addNode(node);
        }
        return new YAMLNodeConfigurationNode(ret);
    }

    @Override
    public Object getProperty(String node) {
        return this.wrapped.getProperty(node);
    }

    @Override
    public void setProperty(String node, Object value) {
        this.wrapped.setProperty(node, value);
    }

    @Override
    public void removeProperty(String node) {
        this.wrapped.removeProperty(node);
    }

    @Override
    public List<String> getKeys(String node) {
        return this.wrapped.getKeys(node);
    }

    @Override
    public boolean getBoolean(String node) {
        return this.wrapped.getBoolean(node);
    }

    @Override
    public String getString(String node, String def) {
        return this.wrapped.getString(node, def);
    }

    @Override
    public List<String> getStringList(String node, List<String> def) {
        return this.wrapped.getStringList(node, def);
    }
}

