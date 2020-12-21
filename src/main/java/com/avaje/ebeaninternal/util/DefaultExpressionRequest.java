/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.util;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultExpressionRequest
implements SpiExpressionRequest {
    private final SpiOrmQueryRequest<?> queryRequest;
    private final BeanDescriptor<?> beanDescriptor;
    private final StringBuilder sb = new StringBuilder();
    private final ArrayList<Object> bindValues = new ArrayList();
    private final DeployParser deployParser;
    private int paramIndex;
    private LIndex luceneIndex;

    public DefaultExpressionRequest(SpiOrmQueryRequest<?> queryRequest, DeployParser deployParser) {
        this.queryRequest = queryRequest;
        this.beanDescriptor = queryRequest.getBeanDescriptor();
        this.deployParser = deployParser;
    }

    public DefaultExpressionRequest(SpiOrmQueryRequest<?> queryRequest, LIndex index) {
        this.queryRequest = queryRequest;
        this.beanDescriptor = queryRequest.getBeanDescriptor();
        this.deployParser = null;
        this.luceneIndex = index;
    }

    public DefaultExpressionRequest(BeanDescriptor<?> beanDescriptor) {
        this.beanDescriptor = beanDescriptor;
        this.queryRequest = null;
        this.deployParser = null;
    }

    @Override
    public LIndex getLuceneIndex() {
        return this.luceneIndex;
    }

    @Override
    public String parseDeploy(String logicalProp) {
        String s = this.deployParser.getDeployWord(logicalProp);
        return s == null ? logicalProp : s;
    }

    @Override
    public int nextParameter() {
        return ++this.paramIndex;
    }

    @Override
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }

    @Override
    public SpiOrmQueryRequest<?> getQueryRequest() {
        return this.queryRequest;
    }

    @Override
    public SpiExpressionRequest append(String sql) {
        this.sb.append(sql);
        return this;
    }

    @Override
    public void addBindValue(Object bindValue) {
        this.bindValues.add(bindValue);
    }

    public boolean includeProperty(String propertyName) {
        return true;
    }

    @Override
    public String getSql() {
        return this.sb.toString();
    }

    @Override
    public ArrayList<Object> getBindValues() {
        return this.bindValues;
    }
}

