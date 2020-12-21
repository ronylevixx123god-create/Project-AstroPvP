/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.loader.AbstractComponentLoader;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class StaticComponentLoader
extends AbstractComponentLoader {
    private final List<AbstractComponent> components;

    public StaticComponentLoader(Logger logger, File configDir, AbstractComponent ... components) {
        super(logger, configDir);
        this.components = Arrays.asList(components);
    }

    @Override
    public Collection<AbstractComponent> loadComponents() {
        return this.components;
    }
}

