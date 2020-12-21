/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.config.ConfigurationNode;
import java.util.Collection;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ComponentLoader {
    public Collection<AbstractComponent> loadComponents();

    public ConfigurationNode getConfiguration(AbstractComponent var1);
}

