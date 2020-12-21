/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.Query
 *  org.apache.lucene.search.Sort
 */
package com.avaje.ebeaninternal.server.core;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

public class LuceneOrmQueryRequest {
    private final Query luceneQuery;
    private final Sort luceneSort;
    private final String description;
    private final String sortDesc;

    public LuceneOrmQueryRequest(Query luceneQuery, Sort luceneSort, String description, String sortDesc) {
        this.luceneQuery = luceneQuery;
        this.luceneSort = luceneSort;
        this.description = description;
        this.sortDesc = sortDesc;
    }

    public Query getLuceneQuery() {
        return this.luceneQuery;
    }

    public Sort getLuceneSort() {
        return this.luceneSort;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSortDesc() {
        return this.sortDesc;
    }
}

