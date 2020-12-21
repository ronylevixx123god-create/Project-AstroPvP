/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.BooleanClause$Occur
 *  org.apache.lucene.search.BooleanQuery
 *  org.apache.lucene.search.Query
 */
package com.avaje.ebeaninternal.util;

import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import java.util.ArrayList;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LuceneQueryList
implements SpiLuceneExpr {
    private final SpiLuceneExpr.ExprOccur localOccur;
    private final ArrayList<SpiLuceneExpr> list = new ArrayList();
    private String description;

    public LuceneQueryList(SpiLuceneExpr.ExprOccur loccur) {
        this.localOccur = loccur;
    }

    public void add(SpiLuceneExpr q) {
        this.list.add(q);
    }

    public ArrayList<SpiLuceneExpr> getList() {
        return this.list;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Query mergeLuceneQuery() {
        BooleanClause.Occur luceneOccur = this.getLuceneOccur();
        StringBuilder sb = new StringBuilder();
        BooleanQuery bq = new BooleanQuery();
        for (int i = 0; i < this.list.size(); ++i) {
            SpiLuceneExpr luceneExpr = this.list.get(i);
            Query lucQuery = luceneExpr.mergeLuceneQuery();
            bq.add(lucQuery, luceneOccur);
            if (i > 0) {
                sb.append(" ").append((Object)luceneOccur).append(" ");
            }
            sb.append(luceneExpr.getDescription());
        }
        this.description = sb.toString();
        return bq;
    }

    private BooleanClause.Occur getLuceneOccur() {
        switch (this.localOccur) {
            case MUST: {
                return BooleanClause.Occur.MUST;
            }
            case MUST_NOT: {
                return BooleanClause.Occur.MUST_NOT;
            }
            case SHOULD: {
                return BooleanClause.Occur.SHOULD;
            }
        }
        throw new RuntimeException("Invalid type " + (Object)((Object)this.localOccur));
    }
}

