/*
 * Decompiled with CFR 0.150.
 */
package com.cosmicpvp.cosmicutils.struct.managers;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class CosmicManager<T> {
    public abstract void onEnable(T var1) throws IOException;

    public abstract void onDisable(T var1) throws IOException;
}

