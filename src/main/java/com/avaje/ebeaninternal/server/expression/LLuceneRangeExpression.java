/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.NumericRangeQuery
 *  org.apache.lucene.search.Query
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.expression.SimpleExpression;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;

public class LLuceneRangeExpression {
    private final SimpleExpression.Op op;
    private final Object value;
    private final String propertyName;
    private final int luceneType;
    String description;
    boolean minInclusive;
    boolean maxInclusive;

    public LLuceneRangeExpression(SimpleExpression.Op op, Object value, String propertyName, int luceneType) {
        this.op = op;
        this.value = value;
        this.propertyName = propertyName;
        this.luceneType = luceneType;
        this.minInclusive = SimpleExpression.Op.EQ.equals((Object)op) || SimpleExpression.Op.GT_EQ.equals((Object)op);
        this.maxInclusive = SimpleExpression.Op.EQ.equals((Object)op) || SimpleExpression.Op.LT_EQ.equals((Object)op);
        this.description = propertyName + op.shortDesc() + value;
    }

    public String getDescription() {
        return this.description;
    }

    public Query buildQuery() {
        switch (this.luceneType) {
            case 1: {
                return this.createIntRange();
            }
            case 2: {
                return this.createLongRange();
            }
            case 3: {
                return this.createDoubleRange();
            }
            case 4: {
                return this.createFloatRange();
            }
            case 5: {
                return this.createLongRange();
            }
            case 6: {
                return this.createLongRange();
            }
        }
        throw new RuntimeException("Unhandled type " + this.luceneType);
    }

    private Query createIntRange() {
        Integer intVal;
        Integer min = intVal = BasicTypeConverter.toInteger(this.value);
        Integer max = intVal;
        if (!SimpleExpression.Op.EQ.equals((Object)this.op)) {
            if (SimpleExpression.Op.GT.equals((Object)this.op) || SimpleExpression.Op.GT_EQ.equals((Object)this.op)) {
                max = Integer.MAX_VALUE;
            } else if (SimpleExpression.Op.LT.equals((Object)this.op) || SimpleExpression.Op.LT_EQ.equals((Object)this.op)) {
                min = Integer.MIN_VALUE;
            }
        }
        return NumericRangeQuery.newIntRange((String)this.propertyName, (Integer)min, (Integer)max, (boolean)this.minInclusive, (boolean)this.maxInclusive);
    }

    private Query createLongRange() {
        Long longVal;
        Long min = longVal = BasicTypeConverter.toLong(this.value);
        Long max = longVal;
        if (!SimpleExpression.Op.EQ.equals((Object)this.op)) {
            if (SimpleExpression.Op.GT.equals((Object)this.op) || SimpleExpression.Op.GT_EQ.equals((Object)this.op)) {
                max = Long.MAX_VALUE;
            } else if (SimpleExpression.Op.LT.equals((Object)this.op) || SimpleExpression.Op.LT_EQ.equals((Object)this.op)) {
                min = Long.MIN_VALUE;
            }
        }
        return NumericRangeQuery.newLongRange((String)this.propertyName, (Long)min, (Long)max, (boolean)this.minInclusive, (boolean)this.maxInclusive);
    }

    private Query createFloatRange() {
        Float floatVal;
        Float min = floatVal = BasicTypeConverter.toFloat(this.value);
        Float max = floatVal;
        if (!SimpleExpression.Op.EQ.equals((Object)this.op)) {
            if (SimpleExpression.Op.GT.equals((Object)this.op) || SimpleExpression.Op.GT_EQ.equals((Object)this.op)) {
                max = Float.valueOf(Float.MAX_VALUE);
            } else if (SimpleExpression.Op.LT.equals((Object)this.op) || SimpleExpression.Op.LT_EQ.equals((Object)this.op)) {
                min = Float.valueOf(Float.MIN_VALUE);
            }
        }
        return NumericRangeQuery.newFloatRange((String)this.propertyName, (Float)min, (Float)max, (boolean)this.minInclusive, (boolean)this.maxInclusive);
    }

    private Query createDoubleRange() {
        Double doubleVal;
        Double min = doubleVal = BasicTypeConverter.toDouble(this.value);
        Double max = doubleVal;
        if (!SimpleExpression.Op.EQ.equals((Object)this.op)) {
            if (SimpleExpression.Op.GT.equals((Object)this.op) || SimpleExpression.Op.GT_EQ.equals((Object)this.op)) {
                max = Double.MAX_VALUE;
            } else if (SimpleExpression.Op.LT.equals((Object)this.op) || SimpleExpression.Op.LT_EQ.equals((Object)this.op)) {
                min = Double.MIN_VALUE;
            }
        }
        return NumericRangeQuery.newDoubleRange((String)this.propertyName, (Double)min, (Double)max, (boolean)this.minInclusive, (boolean)this.maxInclusive);
    }
}

