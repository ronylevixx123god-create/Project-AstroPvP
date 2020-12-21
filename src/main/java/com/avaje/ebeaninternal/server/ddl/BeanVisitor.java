/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanVisitor {
    public void visitBegin();

    public boolean visitBean(BeanDescriptor<?> var1);

    public PropertyVisitor visitProperty(BeanProperty var1);

    public void visitBeanEnd(BeanDescriptor<?> var1);

    public void visitEnd();
}

