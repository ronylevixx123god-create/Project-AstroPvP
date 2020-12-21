/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.server.query.LuceneIndexDataReader;
import com.avaje.ebeaninternal.server.query.SqlTree;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.sql.ResultSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryPlanLucene
extends CQueryPlan {
    private final OrmQueryRequest<?> request;

    public CQueryPlanLucene(OrmQueryRequest<?> request, SqlTree sqlTree) {
        super(request, null, sqlTree, false, "", CQueryPlanLucene.getLuceneDescription(request));
        this.request = request;
    }

    @Override
    public boolean isLucene() {
        return true;
    }

    @Override
    public DataReader createDataReader(ResultSet rset) {
        return new LuceneIndexDataReader(this.request);
    }

    private static String getLuceneDescription(OrmQueryRequest<?> request) {
        LuceneOrmQueryRequest req = request.getLuceneOrmQueryRequest();
        String description = req.getDescription();
        String sortDesc = req.getSortDesc();
        BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
        StringBuilder sb = new StringBuilder();
        sb.append("lucene query from ").append(beanDescriptor.getName());
        sb.append(" ").append(description);
        if (sortDesc != null && sortDesc.length() > 0) {
            sb.append(" order by ").append(sortDesc);
        }
        return sb.toString();
    }
}

