/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.ddl.BeanVisitor;
import com.avaje.ebeaninternal.server.ddl.DdlGenContext;
import com.avaje.ebeaninternal.server.ddl.DropSequenceVisitor;
import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CreateSequenceVisitor
implements BeanVisitor {
    private static final Logger logger = Logger.getLogger(DropSequenceVisitor.class.getName());
    private final DdlGenContext ctx;
    private final boolean supportsSequence;

    public CreateSequenceVisitor(DdlGenContext ctx) {
        this.ctx = ctx;
        this.supportsSequence = ctx.getDbPlatform().getDbIdentity().isSupportsSequence();
    }

    @Override
    public boolean visitBean(BeanDescriptor<?> descriptor) {
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        if (descriptor.getSequenceName() == null) {
            return false;
        }
        if (!this.supportsSequence) {
            String msg = "Not creating sequence " + descriptor.getSequenceName() + " on Bean " + descriptor.getName() + " as DatabasePlatform does not support sequences";
            logger.warning(msg);
            return false;
        }
        if (descriptor.getSequenceName() != null) {
            this.ctx.write("create sequence ");
            this.ctx.write(descriptor.getSequenceName());
            this.ctx.write(";").writeNewLine().writeNewLine();
        }
        return true;
    }

    @Override
    public void visitBeanEnd(BeanDescriptor<?> descriptor) {
    }

    @Override
    public void visitBegin() {
    }

    @Override
    public void visitEnd() {
    }

    @Override
    public PropertyVisitor visitProperty(BeanProperty p) {
        return null;
    }
}

