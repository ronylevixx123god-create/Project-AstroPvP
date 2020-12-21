/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.event;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.event.BeanQueryRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanFinder<T> {
    public T find(BeanQueryRequest<T> var1);

    public BeanCollection<T> findMany(BeanQueryRequest<T> var1);
}

