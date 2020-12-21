/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindableDiscriminator
implements Bindable {
    private final String columnName;
    private final Object discValue;
    private final int sqlType;

    public BindableDiscriminator(InheritInfo inheritInfo) {
        this.columnName = inheritInfo.getDiscriminatorColumn();
        this.discValue = inheritInfo.getDiscriminatorValue();
        this.sqlType = inheritInfo.getDiscriminatorType();
    }

    public String toString() {
        return this.columnName + " = " + this.discValue;
    }

    @Override
    public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
        throw new PersistenceException("Never called (only for inserts)");
    }

    @Override
    public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
        request.appendColumn(this.columnName);
    }

    @Override
    public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
        bindRequest.bind(this.columnName, this.discValue, this.sqlType);
    }

    @Override
    public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
        bindRequest.bind(this.columnName, this.discValue, this.sqlType);
    }
}

