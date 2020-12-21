/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.util.List;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Filter<T> {
    public Filter<T> sort(String var1);

    public Filter<T> maxRows(int var1);

    public Filter<T> eq(String var1, Object var2);

    public Filter<T> ne(String var1, Object var2);

    public Filter<T> ieq(String var1, String var2);

    public Filter<T> between(String var1, Object var2, Object var3);

    public Filter<T> gt(String var1, Object var2);

    public Filter<T> ge(String var1, Object var2);

    public Filter<T> lt(String var1, Object var2);

    public Filter<T> le(String var1, Object var2);

    public Filter<T> isNull(String var1);

    public Filter<T> isNotNull(String var1);

    public Filter<T> startsWith(String var1, String var2);

    public Filter<T> istartsWith(String var1, String var2);

    public Filter<T> endsWith(String var1, String var2);

    public Filter<T> iendsWith(String var1, String var2);

    public Filter<T> contains(String var1, String var2);

    public Filter<T> icontains(String var1, String var2);

    public Filter<T> in(String var1, Set<?> var2);

    public List<T> filter(List<T> var1);
}

