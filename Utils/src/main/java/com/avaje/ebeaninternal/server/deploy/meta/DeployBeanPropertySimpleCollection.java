/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.deploy.ManyType;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.type.ScalarType;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployBeanPropertySimpleCollection<T>
extends DeployBeanPropertyAssocMany<T> {
    private final ScalarType<T> collectionScalarType;

    public DeployBeanPropertySimpleCollection(DeployBeanDescriptor<?> desc, Class<T> targetType, ScalarType<T> scalarType, ManyType manyType) {
        super(desc, targetType, manyType);
        this.collectionScalarType = scalarType;
        this.modifyListenMode = BeanCollection.ModifyListenMode.ALL;
    }

    public ScalarType<T> getCollectionScalarType() {
        return this.collectionScalarType;
    }

    @Override
    public boolean isManyToMany() {
        return false;
    }

    @Override
    public boolean isUnidirectional() {
        return true;
    }
}

