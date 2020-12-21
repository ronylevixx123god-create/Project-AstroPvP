/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Junction;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.Query;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ExpressionFactory {
    public String getLang();

    public Expression eq(String var1, Object var2);

    public Expression ne(String var1, Object var2);

    public Expression ieq(String var1, String var2);

    public Expression between(String var1, Object var2, Object var3);

    public Expression betweenProperties(String var1, String var2, Object var3);

    public Expression gt(String var1, Object var2);

    public Expression ge(String var1, Object var2);

    public Expression lt(String var1, Object var2);

    public Expression le(String var1, Object var2);

    public Expression isNull(String var1);

    public Expression isNotNull(String var1);

    public ExampleExpression iexampleLike(Object var1);

    public ExampleExpression exampleLike(Object var1);

    public ExampleExpression exampleLike(Object var1, boolean var2, LikeType var3);

    public Expression like(String var1, String var2);

    public Expression ilike(String var1, String var2);

    public Expression startsWith(String var1, String var2);

    public Expression istartsWith(String var1, String var2);

    public Expression endsWith(String var1, String var2);

    public Expression iendsWith(String var1, String var2);

    public Expression contains(String var1, String var2);

    public Expression lucene(String var1);

    public Expression lucene(String var1, String var2);

    public Expression icontains(String var1, String var2);

    public Expression in(String var1, Object[] var2);

    public Expression in(String var1, Query<?> var2);

    public Expression in(String var1, Collection<?> var2);

    public Expression idEq(Object var1);

    public Expression idIn(List<?> var1);

    public Expression allEq(Map<String, Object> var1);

    public Expression raw(String var1, Object var2);

    public Expression raw(String var1, Object[] var2);

    public Expression raw(String var1);

    public Expression and(Expression var1, Expression var2);

    public Expression or(Expression var1, Expression var2);

    public Expression not(Expression var1);

    public <T> Junction<T> conjunction(Query<T> var1);

    public <T> Junction<T> disjunction(Query<T> var1);

    public <T> Junction<T> conjunction(Query<T> var1, ExpressionList<T> var2);

    public <T> Junction<T> disjunction(Query<T> var1, ExpressionList<T> var2);
}

