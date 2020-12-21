/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Page<T> {
    public List<T> getList();

    public int getTotalRowCount();

    public int getTotalPageCount();

    public int getPageIndex();

    public boolean hasNext();

    public boolean hasPrev();

    public Page<T> next();

    public Page<T> prev();

    public String getDisplayXtoYofZ(String var1, String var2);
}

