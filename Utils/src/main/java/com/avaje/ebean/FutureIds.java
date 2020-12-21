/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.Query;
import java.util.List;
import java.util.concurrent.Future;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface FutureIds<T>
extends Future<List<Object>> {
    public Query<T> getQuery();

    public List<Object> getPartialIds();
}

