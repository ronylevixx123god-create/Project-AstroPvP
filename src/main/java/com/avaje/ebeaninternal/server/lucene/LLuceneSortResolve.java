/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.Sort
 *  org.apache.lucene.search.SortField
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.OrderBy;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.List;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LLuceneSortResolve {
    private final LuceneResolvableRequest req;
    private final OrderBy<?> orderBy;
    private Sort sort;
    private boolean isResolved;
    private String unsortableField;

    public LLuceneSortResolve(LuceneResolvableRequest req, OrderBy<?> orderBy) {
        this.req = req;
        this.orderBy = orderBy;
        this.isResolved = this.resolve();
    }

    public boolean isResolved() {
        return this.isResolved;
    }

    public Sort getSort() {
        return this.sort;
    }

    public String getUnsortableField() {
        return this.unsortableField;
    }

    private boolean resolve() {
        BeanDescriptor<?> beanDescriptor = this.req.getBeanDescriptor();
        if (this.orderBy != null) {
            List<OrderBy.Property> properties = this.orderBy.getProperties();
            SortField[] sortFields = new SortField[properties.size()];
            for (int i = 0; i < properties.size(); ++i) {
                OrderBy.Property property = properties.get(i);
                SortField sf = this.createSortField(property, beanDescriptor);
                if (sf == null) {
                    this.unsortableField = property.getProperty();
                    return false;
                }
                sortFields[i] = sf;
            }
            this.sort = new Sort(sortFields);
        }
        return true;
    }

    private SortField createSortField(OrderBy.Property property, BeanDescriptor<?> beanDescriptor) {
        String propName = property.getProperty();
        LIndexField sortField = this.req.getSortableProperty(propName);
        if (sortField == null) {
            return null;
        }
        int sortType = sortField.getSortType();
        return sortType == -1 ? null : new SortField(sortField.getName(), sortType, !property.isAscending());
    }
}

