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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

public class LogicExpressionLucene {
    public SpiLuceneExpr addLuceneQuery(String joinType, SpiExpressionRequest request, SpiExpression expOne, SpiExpression expTwo) {
        SpiLuceneExpr e1 = expOne.createLuceneExpr(request);
        SpiLuceneExpr e2 = expTwo.createLuceneExpr(request);
        Query q1 = e1.mergeLuceneQuery();
        Query q2 = e2.mergeLuceneQuery();
        BooleanQuery q = new BooleanQuery();
        BooleanClause.Occur occur = " or ".equals(joinType) ? BooleanClause.Occur.SHOULD : BooleanClause.Occur.MUST;
        q.add(q1, occur);
        q.add(q2, occur);
        String desc = e1.getDescription() + joinType + e2.getDescription();
        return new LuceneExprResponse((Query)q, desc);
    }
}

