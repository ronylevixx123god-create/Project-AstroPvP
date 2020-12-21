/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import com.avaje.ebean.config.dbplatform.DbType;
import com.avaje.ebeaninternal.server.ddl.AbstractBeanVisitor;
import com.avaje.ebeaninternal.server.ddl.AbstractPropertyVisitor;
import com.avaje.ebeaninternal.server.ddl.CreateTableColumnVisitor;
import com.avaje.ebeaninternal.server.ddl.DdlGenContext;
import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.ddl.VisitorUtil;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.parse.SqlReservedWords;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CreateTableVisitor
extends AbstractBeanVisitor {
    protected static final Logger logger = Logger.getLogger(CreateTableVisitor.class.getName());
    final DdlGenContext ctx;
    final PropertyVisitor pv;
    final DbDdlSyntax ddl;
    final int columnNameWidth;
    private final Set<String> wroteColumns = new HashSet<String>();
    private ArrayList<String> checkConstraints = new ArrayList();
    private ArrayList<String> uniqueConstraints = new ArrayList();
    private String table;
    private String schema;

    public Set<String> getWroteColumns() {
        return this.wroteColumns;
    }

    public CreateTableVisitor(DdlGenContext ctx) {
        this.ctx = ctx;
        this.ddl = ctx.getDdlSyntax();
        this.columnNameWidth = this.ddl.getColumnNameWidth();
        this.pv = new CreateTableColumnVisitor(this, ctx);
    }

    public boolean isDbColumnWritten(String dbColumn) {
        return this.wroteColumns.contains(dbColumn.toLowerCase());
    }

    public void addDbColumnWritten(String dbColumn) {
        this.wroteColumns.add(dbColumn.toLowerCase());
    }

    protected void writeTableName(BeanDescriptor<?> descriptor) {
        String tableName = descriptor.getBaseTable();
        int dotPos = tableName.lastIndexOf(46);
        if (dotPos > -1) {
            this.schema = tableName.substring(0, dotPos);
            this.table = tableName.substring(dotPos + 1);
        } else {
            this.table = tableName;
        }
        if (SqlReservedWords.isKeyword(this.table)) {
            logger.warning("Table name [" + this.table + "] is a suspected SQL reserved word for bean " + descriptor.getFullName());
        }
        this.ctx.write(tableName);
    }

    protected String getTable() {
        return this.table;
    }

    protected String getSchema() {
        return this.schema;
    }

    protected void writeColumnName(String columnName, BeanProperty p) {
        this.addDbColumnWritten(columnName);
        if (SqlReservedWords.isKeyword(columnName)) {
            String propName = p == null ? "(Unknown)" : p.getFullBeanName();
            logger.warning("Column name [" + columnName + "] is a suspected SQL reserved word for property " + propName);
        }
        this.ctx.write("  ").write(columnName, this.columnNameWidth).write(" ");
    }

    protected void addCheckConstraint(BeanProperty p, String prefix, String constraintExpression) {
        if (p != null && constraintExpression != null) {
            String s = "constraint " + this.getConstraintName(prefix, p) + " " + constraintExpression;
            this.checkConstraints.add(s);
        }
    }

    protected String getConstraintName(String prefix, BeanProperty p) {
        return prefix + this.table + "_" + p.getDbColumn();
    }

    protected void addUniqueConstraint(String constraintExpression) {
        this.uniqueConstraints.add(constraintExpression);
    }

    protected void addCheckConstraint(String constraintExpression) {
        this.checkConstraints.add(constraintExpression);
    }

    protected void addCheckConstraint(BeanProperty p) {
        this.addCheckConstraint(p, "ck_", p.getDbConstraintExpression());
    }

    @Override
    public boolean visitBean(BeanDescriptor<?> descriptor) {
        this.wroteColumns.clear();
        if (!descriptor.isInheritanceRoot()) {
            return false;
        }
        this.ctx.write("create table ");
        this.writeTableName(descriptor);
        this.ctx.write(" (").writeNewLine();
        InheritInfo inheritInfo = descriptor.getInheritInfo();
        if (inheritInfo != null && inheritInfo.isRoot()) {
            String discColumn = inheritInfo.getDiscriminatorColumn();
            int discType = inheritInfo.getDiscriminatorType();
            int discLength = inheritInfo.getDiscriminatorLength();
            DbType dbType = this.ctx.getDbTypeMap().get(discType);
            String discDbType = dbType.renderType(discLength, 0);
            this.writeColumnName(discColumn, null);
            this.ctx.write(discDbType);
            this.ctx.write(" not null,");
            this.ctx.writeNewLine();
        }
        return true;
    }

    @Override
    public void visitBeanEnd(BeanDescriptor<?> descriptor) {
        BeanProperty[] ids;
        CompoundUniqueContraint[] compoundUniqueConstraints;
        this.visitInheritanceProperties(descriptor, this.pv);
        if (this.checkConstraints.size() > 0) {
            for (String checkConstraint : this.checkConstraints) {
                this.ctx.write("  ").write(checkConstraint).write(",").writeNewLine();
            }
            this.checkConstraints = new ArrayList();
        }
        if (this.uniqueConstraints.size() > 0) {
            for (String constraint : this.uniqueConstraints) {
                this.ctx.write("  ").write(constraint).write(",").writeNewLine();
            }
            this.uniqueConstraints = new ArrayList();
        }
        if ((compoundUniqueConstraints = descriptor.getCompoundUniqueConstraints()) != null) {
            String table = descriptor.getBaseTable();
            for (int i = 0; i < compoundUniqueConstraints.length; ++i) {
                String constraint = this.createUniqueConstraint(table, i, compoundUniqueConstraints[i]);
                this.ctx.write("  ").write(constraint).write(",").writeNewLine();
            }
        }
        if ((ids = descriptor.propertiesId()).length == 0) {
            this.ctx.removeLast().removeLast();
        } else if (ids.length > 1 || this.ddl.isInlinePrimaryKeyConstraint()) {
            this.ctx.removeLast().removeLast();
        } else {
            String pkName = this.ddl.getPrimaryKeyName(this.table);
            this.ctx.write("  constraint ").write(pkName).write(" primary key (");
            VisitorUtil.visit(ids, (PropertyVisitor)new AbstractPropertyVisitor(){

                @Override
                public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) {
                    CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
                }

                @Override
                public void visitScalar(BeanProperty p) {
                    CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
                }

                @Override
                public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) {
                    CreateTableVisitor.this.ctx.write(p.getDbColumn()).write(", ");
                }
            });
            this.ctx.removeLast().write(")");
        }
        this.ctx.write(")").writeNewLine();
        this.ctx.write(";").writeNewLine().writeNewLine();
        this.ctx.flush();
    }

    private String createUniqueConstraint(String table, int idx, CompoundUniqueContraint uc) {
        String uqConstraintName = "uq_" + table + "_" + (idx + 1);
        StringBuilder sb = new StringBuilder();
        sb.append("constraint ").append(uqConstraintName).append(" unique (");
        String[] columns = uc.getColumns();
        for (int i = 0; i < columns.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(columns[i]);
        }
        sb.append(")");
        return sb.toString();
    }

    public void visitBeanDescriptorEnd() {
        this.ctx.write(");").writeNewLine().writeNewLine();
    }

    @Override
    public PropertyVisitor visitProperty(BeanProperty p) {
        return this.pv;
    }

    @Override
    public void visitBegin() {
    }

    @Override
    public void visitEnd() {
        this.ctx.addIntersectionCreateTables();
        this.ctx.flush();
    }
}

