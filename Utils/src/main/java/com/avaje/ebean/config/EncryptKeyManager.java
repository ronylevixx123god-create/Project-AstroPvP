/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.EncryptKey;

public interface EncryptKeyManager {
    public void initialise();

    public EncryptKey getEncryptKey(String var1, String var2);
}

