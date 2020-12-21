/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;

public interface LIndexIoSearcher {
    public void postCommit();

    public void refresh(boolean var1);

    public LIndexSearch getIndexSearch();

    public LIndexVersion getLastestVersion();
}

