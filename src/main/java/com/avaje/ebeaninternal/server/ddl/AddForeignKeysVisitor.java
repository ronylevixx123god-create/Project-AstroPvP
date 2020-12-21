/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.ddl.AbstractBeanVisitor;
import com.avaje.ebeaninternal.server.ddl.BaseTablePropertyVisitor;
import com.avaje.ebeaninternal.server.ddl.DdlGenContext;
import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AddForeignKeysVisitor
extends AbstractBeanVisitor {
    final DdlGenContext ctx;
    final FkeyPropertyVisitor pv;

    public AddForeignKeysVisitor(DdlGenContext ctx) {
        this.ctx = ctx;
        this.pv = new FkeyPropertyVisitor(this, ctx);
    }

    @Override
    public boolean visitBean(BeanDescriptor<?> descriptor) {
        return descriptor.isInheritanceRoot();
    }

    @Override
    public void visitBeanEnd(BeanDescriptor<?> descriptor) {
        this.visitInheritanceProperties(descriptor, this.pv);
    }

    @Override
    public void visitBegin() {
    }

    @Override
    public void visitEnd() {
        this.ctx.addIntersectionFkeys();
    }

    @Override
    public PropertyVisitor visitProperty(BeanProperty p) {
        return this.pv;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class FkeyPropertyVisitor
    extends BaseTablePropertyVisitor {
        final DdlGenContext ctx;
        final AddForeignKeysVisitor parent;

        public FkeyPropertyVisitor(AddForeignKeysVisitor parent, DdlGenContext ctx) {
            this.parent = parent;
            this.ctx = ctx;
        }

        @Override
        public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) {
        }

        @Override
        public void visitOneImported(BeanPropertyAssocOne<?> p) {
            int i;
            String baseTable = p.getBeanDescriptor().getBaseTable();
            TableJoin tableJoin = p.getTableJoin();
            TableJoinColumn[] columns = tableJoin.columns();
            String tableName = p.getBeanDescriptor().getBaseTable();
            String fkName = this.ctx.getDdlSyntax().getForeignKeyName(tableName, p.getName(), this.ctx.incrementFkCount());
            this.ctx.write("alter table ").write(baseTable).write(" add ");
            if (fkName != null) {
                this.ctx.write("constraint ").write(fkName).write(" ");
            }
            this.ctx.write("foreign key (");
            for (i = 0; i < columns.length; ++i) {
                if (i > 0) {
                    this.ctx.write(",");
                }
                this.ctx.write(columns[i].getLocalDbColumn());
            }
            this.ctx.write(")");
            this.ctx.write(" references ");
            this.ctx.write(tableJoin.getTable());
            this.ctx.write(" (");
            for (i = 0; i < columns.length; ++i) {
                if (i > 0) {
                    this.ctx.write(",");
                }
                this.ctx.write(columns[i].getForeignDbColumn());
            }
            this.ctx.write(")");
            String fkeySuffix = this.ctx.getDdlSyntax().getForeignKeySuffix();
            if (fkeySuffix != null) {
                this.ctx.write(" ").write(fkeySuffix);
            }
            this.ctx.write(";").writeNewLine();
            if (this.ctx.getDdlSyntax().isRenderIndexForFkey()) {
                this.ctx.write("create index ");
                String idxName = this.ctx.getDdlSyntax().getIndexName(tableName, p.getName(), this.ctx.incrementIxCount());
                if (idxName != null) {
                    this.ctx.write(idxName);
                }
                this.ctx.write(" on ").write(baseTable).write(" (");
                for (int i2 = 0; i2 < columns.length; ++i2) {
                    if (i2 > 0) {
                        this.ctx.write(",");
                    }
                    this.ctx.write(columns[i2].getLocalDbColumn());
                }
                this.ctx.write(");").writeNewLine();
            }
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
}

