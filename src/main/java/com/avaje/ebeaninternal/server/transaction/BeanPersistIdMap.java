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
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanPersistIdMap {
    private final Map<String, BeanPersistIds> beanMap = new LinkedHashMap<String, BeanPersistIds>();

    public String toString() {
        return this.beanMap.toString();
    }

    public boolean isEmpty() {
        return this.beanMap.isEmpty();
    }

    public Collection<BeanPersistIds> values() {
        return this.beanMap.values();
    }

    public void add(BeanDescriptor<?> desc, PersistRequest.Type type, Object id) {
        BeanPersistIds r = this.getPersistIds(desc);
        r.addId(type, (Serializable)id);
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

