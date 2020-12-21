/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.Page;
import java.util.List;
import java.util.concurrent.Future;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface PagingList<T> {
    public void refresh();

    public PagingList<T> setFetchAhead(boolean var1);

    public Future<Integer> getFutureRowCount();

    public List<T> getAsList();

    public int getPageSize();

    public int getTotalRowCount();

    public int getTotalPageCount();

    public Page<T> getPage(int var1);
}

