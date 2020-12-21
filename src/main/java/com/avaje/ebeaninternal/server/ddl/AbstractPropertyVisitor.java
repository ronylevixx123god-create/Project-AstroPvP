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
public abstract class AbstractPropertyVisitor
implements PropertyVisitor {
    @Override
    public void visitEmbedded(BeanPropertyAssocOne<?> p) {
    }

    @Override
    public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) {
    }

    @Override
    public void visitMany(BeanPropertyAssocMany<?> p) {
    }

    @Override
    public void visitOneExported(BeanPropertyAssocOne<?> p) {
    }

    @Override
    public void visitOneImported(BeanPropertyAssocOne<?> p) {
    }

    @Override
    public void visitScalar(BeanProperty p) {
    }

    @Override
    public void visitCompound(BeanPropertyCompound p) {
    }

    @Override
    public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) {
    }
}

