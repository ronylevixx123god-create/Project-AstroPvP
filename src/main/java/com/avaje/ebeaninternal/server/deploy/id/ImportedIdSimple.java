/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.util.ValueUtil;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ImportedIdSimple
implements ImportedId,
Comparable<ImportedIdSimple> {
    private static final EntryComparator COMPARATOR = new EntryComparator();
    protected final BeanPropertyAssoc<?> owner;
    protected final String localDbColumn;
    protected final String logicalName;
    protected final BeanProperty foreignProperty;
    protected final int position;

    public ImportedIdSimple(BeanPropertyAssoc<?> owner, String localDbColumn, BeanProperty foreignProperty, int position) {
        this.owner = owner;
        this.localDbColumn = InternString.intern(localDbColumn);
        this.foreignProperty = foreignProperty;
        this.position = position;
        this.logicalName = InternString.intern(owner.getName() + "." + foreignProperty.getName());
    }

    public static ImportedIdSimple[] sort(List<ImportedIdSimple> list) {
        ImportedIdSimple[] importedIds = list.toArray(new ImportedIdSimple[list.size()]);
        Arrays.sort(importedIds, COMPARATOR);
        return importedIds;
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override
    public int compareTo(ImportedIdSimple other) {
        return this.position < other.position ? -1 : (this.position == other.position ? 0 : 1);
    }

    @Override
    public void addFkeys(String name) {
        BeanFkeyProperty fkey = new BeanFkeyProperty(null, name + "." + this.foreignProperty.getName(), this.localDbColumn, this.owner.getDeployOrder());
        this.owner.getBeanDescriptor().add(fkey);
    }

    @Override
    public boolean isScalar() {
        return true;
    }

    @Override
    public String getLogicalName() {
        return this.logicalName;
    }

    @Override
    public String getDbColumn() {
        return this.localDbColumn;
    }

    private Object getIdValue(Object bean) {
        return this.foreignProperty.getValueWithInheritance(bean);
    }

    @Override
    public void buildImport(IntersectionRow row, Object other) {
        Object value = this.getIdValue(other);
        if (value == null) {
            String msg = "Foreign Key value null?";
            throw new PersistenceException(msg);
        }
        row.put(this.localDbColumn, value);
    }

    @Override
    public void sqlAppend(DbSqlContext ctx) {
        ctx.appendColumn(this.localDbColumn);
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request) {
        request.appendColumn(this.localDbColumn);
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, Object bean) {
        if (this.owner.isDbUpdatable()) {
            Object value = null;
            if (bean != null) {
                value = this.getIdValue(bean);
            }
            if (value == null) {
                request.appendColumnIsNull(this.localDbColumn);
            } else {
                request.appendColumn(this.localDbColumn);
            }
        }
    }

    @Override
    public boolean hasChanged(Object bean, Object oldValues) {
        Object id = this.getIdValue(bean);
        if (oldValues != null) {
            Object oldId = this.getIdValue(oldValues);
            return !ValueUtil.areEqual(id, oldId);
        }
        return true;
    }

    @Override
    public void bind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
        Object value = null;
        if (bean != null) {
            value = this.getIdValue(bean);
        }
        request.bind(value, this.foreignProperty, this.localDbColumn, bindNull);
    }

    @Override
    public BeanProperty findMatchImport(String matchDbColumn) {
        if (matchDbColumn.equals(this.localDbColumn)) {
            return this.foreignProperty;
        }
        return null;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class EntryComparator
    implements Comparator<ImportedIdSimple> {
        private EntryComparator() {
        }

        @Override
        public int compare(ImportedIdSimple o1, ImportedIdSimple o2) {
            return o1.compareTo(o2);
        }
    }
}

