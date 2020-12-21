/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.queryParser.ParseException
 *  org.apache.lucene.queryParser.QueryParser
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.LikeType;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.expression.LuceneExprResponse;
import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

public class LikeExpressionLucene {
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request, String propertyName, LikeType type, boolean caseInsensitive, String val) {
        try {
            String exprValue = LikeExpressionLucene.getLuceneValue(val, caseInsensitive, type);
            String desc = propertyName + " like " + exprValue;
            QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
            return new LuceneExprResponse(queryParser.parse(exprValue), desc);
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException(e);
        }
    }

    private static String getLuceneValue(String value, boolean caseInsensitive, LikeType type) {
        if (caseInsensitive) {
            value = value.toLowerCase();
        }
        value = value.replace('%', '*');
        switch (type) {
            case RAW: {
                return value;
            }
            case STARTS_WITH: {
                return value + "*";
            }
            case CONTAINS: {
                return value;
            }
            case EQUAL_TO: {
                return value;
            }
            case ENDS_WITH: {
                throw new RuntimeException("Not Supported - Never get here");
            }
        }
        throw new RuntimeException("LikeType " + (Object)((Object)type) + " missed?");
    }
}

