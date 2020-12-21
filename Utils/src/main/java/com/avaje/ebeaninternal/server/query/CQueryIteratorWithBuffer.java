/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.query.CQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class CQueryIteratorWithBuffer<T>
implements QueryIterator<T> {
    private final CQuery<T> cquery;
    private final int bufferSize;
    private final OrmQueryRequest<T> request;
    private final ArrayList<T> buffer;
    private boolean moreToLoad = true;

    CQueryIteratorWithBuffer(CQuery<T> cquery, OrmQueryRequest<T> request, int bufferSize) {
        this.cquery = cquery;
        this.request = request;
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList(bufferSize);
    }

    @Override
    public boolean hasNext() {
        try {
            if (this.buffer.isEmpty() && this.moreToLoad) {
                int i = -1;
                while (this.moreToLoad && ++i < this.bufferSize) {
                    if (this.cquery.hasNextBean(true)) {
                        this.buffer.add(this.cquery.getLoadedBean());
                        continue;
                    }
                    this.moreToLoad = false;
                }
                this.request.executeSecondaryQueries(this.bufferSize);
            }
            return !this.buffer.isEmpty();
        }
        catch (SQLException e) {
            throw this.cquery.createPersistenceException(e);
        }
    }

    @Override
    public T next() {
        return this.buffer.remove(0);
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

