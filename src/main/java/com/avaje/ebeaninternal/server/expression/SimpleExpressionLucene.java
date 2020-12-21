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
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.expression.LLuceneRangeExpression;
import com.avaje.ebeaninternal.server.expression.LuceneExprResponse;
import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
import com.avaje.ebeaninternal.server.expression.SimpleExpression;
import com.avaje.ebeaninternal.server.type.ScalarType;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

public class SimpleExpressionLucene {
    public SpiLuceneExpr addLuceneQuery(SpiExpressionRequest request, SimpleExpression.Op type, String propertyName, Object value, ElPropertyValue prop) {
        try {
            if (prop == null) {
                throw new RuntimeException("Property not found? " + propertyName);
            }
            BeanProperty beanProperty = prop.getBeanProperty();
            ScalarType<?> scalarType = beanProperty.getScalarType();
            int luceneType = scalarType.getLuceneType();
            if (0 == luceneType) {
                String lucVal = (String)scalarType.luceneToIndexValue(value);
                if (SimpleExpression.Op.EQ.equals((Object)type)) {
                    String desc = propertyName + " = " + lucVal.toString();
                    QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
                    return new LuceneExprResponse(queryParser.parse(lucVal.toString()), desc);
                }
                if (SimpleExpression.Op.NOT_EQ.equals((Object)type)) {
                    String desc = propertyName + " != " + lucVal.toString();
                    QueryParser queryParser = request.getLuceneIndex().createQueryParser(propertyName);
                    return new LuceneExprResponse(queryParser.parse("-" + propertyName + "(" + lucVal.toString() + ")"), desc);
                }
                throw new RuntimeException("String type only supports EQ and NOT_EQ - " + (Object)((Object)type));
            }
            LLuceneRangeExpression exp = new LLuceneRangeExpression(type, value, propertyName, luceneType);
            return new LuceneExprResponse(exp.buildQuery(), exp.getDescription());
        }
        catch (ParseException e) {
            throw new PersistenceLuceneParseException(e);
        }
    }
}

