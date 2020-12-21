/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LuceneResolvableRequest {
    private final BeanDescriptor<?> beanDescriptor;
    private final LIndex luceneIndex;
    private final Set<String> resolvePropertyNames;

    public LuceneResolvableRequest(BeanDescriptor<?> beanDescriptor, LIndex luceneIndex) {
        this.beanDescriptor = beanDescriptor;
        this.luceneIndex = luceneIndex;
        this.resolvePropertyNames = luceneIndex.getResolvePropertyNames();
    }

    public boolean indexContains(String propertyName) {
        return this.resolvePropertyNames.contains(propertyName);
    }

    public LIndexField getSortableProperty(String propertyName) {
        return this.luceneIndex.getIndexFieldDefn().getSortableField(propertyName);
    }

    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }
}

