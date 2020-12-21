/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml;

import org.yaml.snakeyaml.TypeDescription;

public class LoaderOptions {
    private TypeDescription rootTypeDescription;

    public LoaderOptions() {
        this(new TypeDescription(Object.class));
    }

    public LoaderOptions(TypeDescription rootTypeDescription) {
        this.rootTypeDescription = rootTypeDescription;
    }

    public TypeDescription getRootTypeDescription() {
        return this.rootTypeDescription;
    }

    public void setRootTypeDescription(TypeDescription rootTypeDescription) {
        this.rootTypeDescription = rootTypeDescription;
    }
}

