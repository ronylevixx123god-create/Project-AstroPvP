/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.queryParser.ParseException
 *  org.apache.lucene.queryParser.QueryParser
 *  org.apache.lucene.queryParser.QueryParser$Operator
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.expression.LuceneAwareExpression;
import com.avaje.ebeaninternal.server.expression.LuceneExprResponse;
import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class LuceneExpression
extends AbstractExpression
implements LuceneAwareExpression {
    private static final long serialVersionUID = 8959252357123977939L;
    private final String val;
    private final boolean andOperator;

    LuceneExpression(FilterExprPath pathPrefix, String propertyName, String value, boolean andOperator) {
        super(pathPrefix, propertyName);
        this.val = value;
        this.andOperator = andOperator;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        return true;
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        try {
            String propertyName = this.getPropertyName();
            String desc = propertyName + " " + this.val;
            QueryParser p = request.getLuceneIndex().createQueryParser(propertyName);
            p.setDefaultOperator(QueryParser.Operator.OR);
            return new LuceneExprResponse(p.parse(this.val), desc);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException(e);
        }
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = LuceneExpression.class.getName().hashCode();
        hc = hc * 31 + (this.andOperator ? 0 : 1);
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }

    @Override
    public int queryBindHash() {
        return this.val.hashCode();
    }
}

