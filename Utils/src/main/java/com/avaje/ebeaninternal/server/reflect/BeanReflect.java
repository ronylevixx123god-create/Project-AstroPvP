/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.reflect;

import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;

public interface BeanReflect {
    public Object createEntityBean();

    public Object createVanillaBean();

    public boolean isVanillaOnly();

    public BeanReflectGetter getGetter(String var1);

    public BeanReflectSetter getSetter(String var1);
}

