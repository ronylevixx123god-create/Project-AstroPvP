/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class BeanEmbeddedMeta {
    final BeanProperty[] properties;

    public BeanEmbeddedMeta(BeanProperty[] properties) {
        this.properties = properties;
    }

    public BeanProperty[] getProperties() {
        return this.properties;
    }

    public boolean isEmbeddedVersion() {
        for (int i = 0; i < this.properties.length; ++i) {
            if (!this.properties[i].isVersion()) continue;
            return true;
        }
        return false;
    }
}

