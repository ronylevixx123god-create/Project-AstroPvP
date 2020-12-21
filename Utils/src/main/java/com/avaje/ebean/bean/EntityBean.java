/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.bean;

import com.avaje.ebean.bean.EntityBeanIntercept;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public interface EntityBean
extends Serializable {
    public String _ebean_getMarker();

    public Object _ebean_newInstance();

    public void addPropertyChangeListener(PropertyChangeListener var1);

    public void removePropertyChangeListener(PropertyChangeListener var1);

    public void _ebean_setEmbeddedLoaded();

    public boolean _ebean_isEmbeddedNewOrDirty();

    public EntityBeanIntercept _ebean_getIntercept();

    public EntityBeanIntercept _ebean_intercept();

    public Object _ebean_createCopy();

    public String[] _ebean_getFieldNames();

    public void _ebean_setField(int var1, Object var2, Object var3);

    public void _ebean_setFieldIntercept(int var1, Object var2, Object var3);

    public Object _ebean_getField(int var1, Object var2);

    public Object _ebean_getFieldIntercept(int var1, Object var2);
}

