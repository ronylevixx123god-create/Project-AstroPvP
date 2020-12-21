/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.query.CQuery;
import java.sql.SQLException;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class CQueryIteratorSimple<T>
implements QueryIterator<T> {
    private final CQuery<T> cquery;
    private final OrmQueryRequest<T> request;

    CQueryIteratorSimple(CQuery<T> cquery, OrmQueryRequest<T> request) {
        this.cquery = cquery;
        this.request = request;
    }

    @Override
    public boolean hasNext() {
        try {
            return this.cquery.hasNextBean(true);
        }
        catch (SQLException e) {
            throw this.cquery.createPersistenceException(e);
        }
    }

    @Override
    public T next() {
        return this.cquery.getLoadedBean();
    }

    @Override
    public void close() {
        this.cquery.updateExecutionStatistics();
        this.cquery.close();
        this.request.endTransIfRequired();
    }

    @Override
    public void remove() {
        throw new PersistenceException("Remove not allowed");
    }
}

