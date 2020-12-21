/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanListHelp;
import com.avaje.ebeaninternal.server.deploy.BeanMapHelp;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanSetHelp;
import com.avaje.ebeaninternal.server.deploy.ManyType;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanCollectionHelpFactory {
    public static <T> BeanCollectionHelp<T> create(BeanPropertyAssocMany<T> manyProperty) {
        ManyType manyType = manyProperty.getManyType();
        switch (manyType.getUnderlying()) {
            case LIST: {
                return new BeanListHelp<T>(manyProperty);
            }
            case SET: {
                return new BeanSetHelp<T>(manyProperty);
            }
            case MAP: {
                return new BeanMapHelp<T>(manyProperty);
            }
        }
        throw new RuntimeException("Invalid type " + manyType);
    }

    public static <T> BeanCollectionHelp<T> create(OrmQueryRequest<T> request) {
        Query.Type manyType = request.getQuery().getType();
        if (manyType.equals((Object)Query.Type.LIST)) {
            return new BeanListHelp();
        }
        if (manyType.equals((Object)Query.Type.SET)) {
            return new BeanSetHelp();
        }
        BeanDescriptor<T> target = request.getBeanDescriptor();
        String mapKey = request.getQuery().getMapKey();
        return new BeanMapHelp<T>(target, mapKey);
    }
}

