/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.Query
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import org.apache.lucene.search.Query;

public class LuceneExprResponse
implements SpiLuceneExpr {
    private final Query query;
    private final String description;

    public LuceneExprResponse(Query query, String description) {
        this.query = query;
        this.description = description;
    }

    public Query mergeLuceneQuery() {
        return this.query;
    }

    public String getDescription() {
        return this.description;
    }
}

