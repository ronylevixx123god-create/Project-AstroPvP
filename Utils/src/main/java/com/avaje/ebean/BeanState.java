/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.beans.PropertyChangeListener;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanState {
    public boolean isReference();

    public boolean isNew();

    public boolean isNewOrDirty();

    public boolean isDirty();

    public Set<String> getLoadedProps();

    public Set<String> getChangedProps();

    public boolean isReadOnly();

    public void setReadOnly(boolean var1);

    public boolean isSharedInstance();

    public void addPropertyChangeListener(PropertyChangeListener var1);

    public void removePropertyChangeListener(PropertyChangeListener var1);

    public void setReference();

    public void setLoaded(Set<String> var1);
}

