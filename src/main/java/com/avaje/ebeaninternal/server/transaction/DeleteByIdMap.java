/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DeleteByIdMap {
    private final Map<String, BeanPersistIds> beanMap = new LinkedHashMap<String, BeanPersistIds>();

    public String toString() {
        return this.beanMap.toString();
    }

    public void notifyCache() {
        for (BeanPersistIds deleteIds : this.beanMap.values()) {
            BeanDescriptor<?> d = deleteIds.getBeanDescriptor();
            List<Serializable> idValues = deleteIds.getDeleteIds();
            if (idValues == null) continue;
            d.queryCacheClear();
            for (int i = 0; i < idValues.size(); ++i) {
                d.cacheRemove(idValues.get(i));
            }
        }
    }

    public boolean isEmpty() {
        return this.beanMap.isEmpty();
    }

    public Collection<BeanPersistIds> values() {
        return this.beanMap.values();
    }

    public void add(BeanDescriptor<?> desc, Object id) {
        BeanPersistIds r = this.getPersistIds(desc);
        r.addId(PersistRequest.Type.DELETE, (Serializable)id);
    }

    public void addList(BeanDescriptor<?> desc, List<Object> idList) {
        BeanPersistIds r = this.getPersistIds(desc);
        for (int i = 0; i < idList.size(); ++i) {
            r.addId(PersistRequest.Type.DELETE, (Serializable)idList.get(i));
        }
    }

    private BeanPersistIds getPersistIds(BeanDescriptor<?> desc) {
        String beanType = desc.getFullName();
        BeanPersistIds r = this.beanMap.get(beanType);
        if (r == null) {
            r = new BeanPersistIds(desc);
            this.beanMap.put(beanType, r);
        }
        return r;
    }
}

