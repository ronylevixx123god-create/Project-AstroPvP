/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.BooleanClause$Occur
 *  org.apache.lucene.search.BooleanQuery
 *  org.apache.lucene.search.Query
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.expression.LuceneExprResponse;
import java.util.List;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JunctionExpressionLucene {
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, List<SpiExpression> list, boolean disjunction) {
        BooleanClause.Occur occur = disjunction ? BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
        StringBuilder sb = new StringBuilder();
        BooleanQuery bq = new BooleanQuery();
        for (int i = 0; i < list.size(); ++i) {
            SpiLuceneExpr luceneExpr = list.get(i).createLuceneExpr(request);
            Query query = luceneExpr.mergeLuceneQuery();
            bq.add(query, occur);
            if (i > 0) {
                sb.append(" ").append((Object)occur).append(" ");
            }
            sb.append(luceneExpr.getDescription());
        }
        return new LuceneExprResponse((Query)bq, sb.toString());
    }
}

