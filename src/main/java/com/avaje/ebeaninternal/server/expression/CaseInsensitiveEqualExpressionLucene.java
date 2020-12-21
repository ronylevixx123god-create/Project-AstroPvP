/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.queryParser.ParseException
 *  org.apache.lucene.queryParser.QueryParser
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.expression.LuceneExprResponse;
import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

public class CaseInsensitiveEqualExpressionLucene {
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, String propertyName, String value) {
        try {
            String desc = propertyName + " ieq " + value;
            QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
            return new LuceneExprResponse(queryParser.parse(value), desc);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException(e);
        }
    }
}

