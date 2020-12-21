/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.reflect;

import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import com.avaje.ebeaninternal.server.reflect.BeanReflectFactory;
import com.avaje.ebeaninternal.server.reflect.EnhanceBeanReflect;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class EnhanceBeanReflectFactory
implements BeanReflectFactory {
    @Override
    public BeanReflect create(Class<?> vanillaType, Class<?> entityBeanType) {
        return new EnhanceBeanReflect(vanillaType, entityBeanType);
    }
}

