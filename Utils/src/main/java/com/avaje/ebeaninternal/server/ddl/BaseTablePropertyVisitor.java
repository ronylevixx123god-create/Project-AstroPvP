/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class BaseTablePropertyVisitor
implements PropertyVisitor {
    @Override
    public void visitEmbedded(BeanPropertyAssocOne<?> p) {
    }

    @Override
    public abstract void visitEmbeddedScalar(BeanProperty var1, BeanPropertyAssocOne<?> var2);

    @Override
    public void visitMany(BeanPropertyAssocMany<?> p) {
    }

    @Override
    public void visitOneExported(BeanPropertyAssocOne<?> p) {
    }

    @Override
    public abstract void visitOneImported(BeanPropertyAssocOne<?> var1);

    @Override
    public abstract void visitScalar(BeanProperty var1);

    @Override
    public void visitCompound(BeanPropertyCompound p) {
    }

    @Override
    public abstract void visitCompoundScalar(BeanPropertyCompound var1, BeanProperty var2);
}

