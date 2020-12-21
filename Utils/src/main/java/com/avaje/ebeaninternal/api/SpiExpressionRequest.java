/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiExpressionRequest {
    public LIndex getLuceneIndex();

    public String parseDeploy(String var1);

    public BeanDescriptor<?> getBeanDescriptor();

    public SpiOrmQueryRequest<?> getQueryRequest();

    public SpiExpressionRequest append(String var1);

    public void addBindValue(Object var1);

    public String getSql();

    public ArrayList<Object> getBindValues();

    public int nextParameter();
}

