/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.Query
 */
package com.avaje.ebeaninternal.api;

import org.apache.lucene.search.Query;

public interface SpiLuceneExpr {
    public Query mergeLuceneQuery();

    public String getDescription();

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum ExprOccur {
        MUST,
        SHOULD,
        MUST_NOT;

    }
}

