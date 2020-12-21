/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config;

import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ConfigurationNode {
    public ConfigurationNode getNode(String var1);

    public Object getProperty(String var1);

    public void setProperty(String var1, Object var2);

    public void removeProperty(String var1);

    public List<String> getKeys(String var1);

    public boolean getBoolean(String var1);

    public String getString(String var1, String var2);

    public List<String> getStringList(String var1, List<String> var2);
}

