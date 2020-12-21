/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface PropertyVisitor {
    public void visitMany(BeanPropertyAssocMany<?> var1);

    public void visitOneImported(BeanPropertyAssocOne<?> var1);

    public void visitOneExported(BeanPropertyAssocOne<?> var1);

    public void visitEmbedded(BeanPropertyAssocOne<?> var1);

    public void visitEmbeddedScalar(BeanProperty var1, BeanPropertyAssocOne<?> var2);

    public void visitScalar(BeanProperty var1);

    public void visitCompound(BeanPropertyCompound var1);

    public void visitCompoundScalar(BeanPropertyCompound var1, BeanProperty var2);
}

