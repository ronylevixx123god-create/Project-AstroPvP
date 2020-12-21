/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Update<T> {
    public String getName();

    public Update<T> setNotifyCache(boolean var1);

    public Update<T> setTimeout(int var1);

    public int execute();

    public Update<T> set(int var1, Object var2);

    public Update<T> setParameter(int var1, Object var2);

    public Update<T> setNull(int var1, int var2);

    public Update<T> setNullParameter(int var1, int var2);

    public Update<T> set(String var1, Object var2);

    public Update<T> setParameter(String var1, Object var2);

    public Update<T> setNull(String var1, int var2);

    public Update<T> setNullParameter(String var1, int var2);

    public String getGeneratedSql();
}

