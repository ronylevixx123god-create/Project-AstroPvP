/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Junction;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiExpressionFactory;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.expression.AllEqualsExpression;
import com.avaje.ebeaninternal.server.expression.BetweenExpression;
import com.avaje.ebeaninternal.server.expression.BetweenPropertyExpression;
import com.avaje.ebeaninternal.server.expression.CaseInsensitiveEqualExpression;
import com.avaje.ebeaninternal.server.expression.DefaultExampleExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.expression.IdExpression;
import com.avaje.ebeaninternal.server.expression.IdInExpression;
import com.avaje.ebeaninternal.server.expression.InExpression;
import com.avaje.ebeaninternal.server.expression.InQueryExpression;
import com.avaje.ebeaninternal.server.expression.JunctionExpression;
import com.avaje.ebeaninternal.server.expression.LikeExpression;
import com.avaje.ebeaninternal.server.expression.LogicExpression;
import com.avaje.ebeaninternal.server.expression.LuceneExpression;
import com.avaje.ebeaninternal.server.expression.NotExpression;
import com.avaje.ebeaninternal.server.expression.NullExpression;
import com.avaje.ebeaninternal.server.expression.RawExpression;
import com.avaje.ebeaninternal.server.expression.SimpleExpression;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultExpressionFactory
implements SpiExpressionFactory {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final FilterExprPath prefix;

    public DefaultExpressionFactory() {
        this(null);
    }

    public DefaultExpressionFactory(FilterExprPath prefix) {
        this.prefix = prefix;
    }

    @Override
    public ExpressionFactory createExpressionFactory(FilterExprPath prefix) {
        return new DefaultExpressionFactory(prefix);
    }

    @Override
    public String getLang() {
        return "sql";
    }

    @Override
    public Expression eq(String propertyName, Object value) {
        if (value == null) {
            return this.isNull(propertyName);
        }
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.EQ, value);
    }

    @Override
    public Expression ne(String propertyName, Object value) {
        if (value == null) {
            return this.isNotNull(propertyName);
        }
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.NOT_EQ, value);
    }

    @Override
    public Expression ieq(String propertyName, String value) {
        if (value == null) {
            return this.isNull(propertyName);
        }
        return new CaseInsensitiveEqualExpression(this.prefix, propertyName, value);
    }

    @Override
    public Expression between(String propertyName, Object value1, Object value2) {
        return new BetweenExpression(this.prefix, propertyName, value1, value2);
    }

    @Override
    public Expression betweenProperties(String lowProperty, String highProperty, Object value) {
        return new BetweenPropertyExpression(this.prefix, lowProperty, highProperty, value);
    }

    @Override
    public Expression gt(String propertyName, Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT, value);
    }

    @Override
    public Expression ge(String propertyName, Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT_EQ, value);
    }

    @Override
    public Expression lt(String propertyName, Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT, value);
    }

    @Override
    public Expression le(String propertyName, Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT_EQ, value);
    }

    @Override
    public Expression isNull(String propertyName) {
        return new NullExpression(this.prefix, propertyName, false);
    }

    @Override
    public Expression isNotNull(String propertyName) {
        return new NullExpression(this.prefix, propertyName, true);
    }

    @Override
    public ExampleExpression iexampleLike(Object example) {
        return new DefaultExampleExpression(this.prefix, example, true, LikeType.RAW);
    }

    @Override
    public ExampleExpression exampleLike(Object example) {
        return new DefaultExampleExpression(this.prefix, example, false, LikeType.RAW);
    }

    @Override
    public ExampleExpression exampleLike(Object example, boolean caseInsensitive, LikeType likeType) {
        return new DefaultExampleExpression(this.prefix, example, caseInsensitive, likeType);
    }

    @Override
    public Expression like(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.RAW);
    }

    @Override
    public Expression ilike(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.RAW);
    }

    @Override
    public Expression startsWith(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.STARTS_WITH);
    }

    @Override
    public Expression istartsWith(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.STARTS_WITH);
    }

    @Override
    public Expression endsWith(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.ENDS_WITH);
    }

    @Override
    public Expression iendsWith(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.ENDS_WITH);
    }

    @Override
    public Expression contains(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.CONTAINS);
    }

    @Override
    public Expression lucene(String propertyName, String value) {
        return new LuceneExpression(this.prefix, propertyName, value, true);
    }

    @Override
    public Expression lucene(String value) {
        return new LuceneExpression(this.prefix, null, value, true);
    }

    @Override
    public Expression icontains(String propertyName, String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.CONTAINS);
    }

    @Override
    public Expression in(String propertyName, Object[] values) {
        return new InExpression(this.prefix, propertyName, values);
    }

    @Override
    public Expression in(String propertyName, Query<?> subQuery) {
        return new InQueryExpression(this.prefix, propertyName, (SpiQuery)subQuery);
    }

    @Override
    public Expression in(String propertyName, Collection<?> values) {
        return new InExpression(this.prefix, propertyName, values);
    }

    @Override
    public Expression idEq(Object value) {
        return new IdExpression(value);
    }

    @Override
    public Expression idIn(List<?> idList) {
        return new IdInExpression(idList);
    }

    @Override
    public Expression allEq(Map<String, Object> propertyMap) {
        return new AllEqualsExpression(this.prefix, propertyMap);
    }

    @Override
    public Expression raw(String raw, Object value) {
        return new RawExpression(raw, new Object[]{value});
    }

    @Override
    public Expression raw(String raw, Object[] values) {
        return new RawExpression(raw, values);
    }

    @Override
    public Expression raw(String raw) {
        return new RawExpression(raw, EMPTY_ARRAY);
    }

    @Override
    public Expression and(Expression expOne, Expression expTwo) {
        return new LogicExpression.And(expOne, expTwo);
    }

    @Override
    public Expression or(Expression expOne, Expression expTwo) {
        return new LogicExpression.Or(expOne, expTwo);
    }

    @Override
    public Expression not(Expression exp) {
        return new NotExpression(exp);
    }

    @Override
    public <T> Junction<T> conjunction(Query<T> query) {
        return new JunctionExpression.Conjunction<T>(query, query.where());
    }

    @Override
    public <T> Junction<T> disjunction(Query<T> query) {
        return new JunctionExpression.Disjunction<T>(query, query.where());
    }

    @Override
    public <T> Junction<T> conjunction(Query<T> query, ExpressionList<T> parent) {
        return new JunctionExpression.Conjunction<T>(query, parent);
    }

    @Override
    public <T> Junction<T> disjunction(Query<T> query, ExpressionList<T> parent) {
        return new JunctionExpression.Disjunction<T>(query, parent);
    }
}

