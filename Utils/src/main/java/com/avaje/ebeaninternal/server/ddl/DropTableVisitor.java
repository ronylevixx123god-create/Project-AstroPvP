/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import com.avaje.ebeaninternal.server.ddl.BeanVisitor;
import com.avaje.ebeaninternal.server.ddl.DdlGenContext;
import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DropTableVisitor
implements BeanVisitor {
    final DdlGenContext ctx;
    final DbDdlSyntax ddlSyntax;

    public DropTableVisitor(DdlGenContext ctx) {
        this.ctx = ctx;
        this.ddlSyntax = ctx.getDdlSyntax();
    }

    protected void writeDropTable(BeanDescriptor<?> descriptor) {
        this.writeDropTable(descriptor.getBaseTable());
    }

    protected void writeDropTable(String tableName) {
        this.ctx.write("drop table ");
        if (this.ddlSyntax.getDropIfExists() != null) {
            this.ctx.write(this.ddlSyntax.getDropIfExists()).write(" ");
        }
        this.ctx.write(tableName);
        if (this.ddlSyntax.getDropTableCascade() != null) {
            this.ctx.write(" ").write(this.ddlSyntax.getDropTableCascade());
        }
        this.ctx.write(";").writeNewLine().writeNewLine();
    }

    @Override
    public boolean visitBean(BeanDescriptor<?> descriptor) {
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        this.writeDropTable(descriptor);
        this.dropIntersectionTables(descriptor);
        return true;
    }

    private void dropIntersectionTables(BeanDescriptor<?> descriptor) {
        BeanPropertyAssocMany<?>[] manyProps = descriptor.propertiesMany();
        for (int i = 0; i < manyProps.length; ++i) {
            String intTable;
            if (!manyProps[i].isManyToMany() || !this.ctx.isProcessIntersectionTable(intTable = manyProps[i].getIntersectionTableJoin().getTable())) continue;
            this.writeDropTable(intTable);
        }
    }

    @Override
    public void visitBeanEnd(BeanDescriptor<?> descriptor) {
    }

    @Override
    public void visitBegin() {
        if (this.ddlSyntax.getDisableReferentialIntegrity() != null) {
            this.ctx.write(this.ddlSyntax.getDisableReferentialIntegrity());
            this.ctx.write(";").writeNewLine().writeNewLine();
        }
    }

    @Override
    public void visitEnd() {
        if (this.ddlSyntax.getEnableReferentialIntegrity() != null) {
            this.ctx.write(this.ddlSyntax.getEnableReferentialIntegrity());
            this.ctx.write(";").writeNewLine().writeNewLine();
        }
    }

    @Override
    public PropertyVisitor visitProperty(BeanProperty p) {
        return null;
    }
}

