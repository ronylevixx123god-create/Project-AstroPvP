/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.lucene;

import com.avaje.ebean.config.lucene.IndexUpdateFuture;

public interface LuceneIndex {
    public IndexUpdateFuture update();

    public IndexUpdateFuture rebuild();
}

