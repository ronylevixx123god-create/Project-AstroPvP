/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.event;

import com.avaje.ebean.event.BeanQueryRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanQueryAdapter {
    public boolean isRegisterFor(Class<?> var1);

    public int getExecutionOrder();

    public void preQuery(BeanQueryRequest<?> var1);
}

