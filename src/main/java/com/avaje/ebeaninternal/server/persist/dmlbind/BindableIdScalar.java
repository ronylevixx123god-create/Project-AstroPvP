/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindableIdScalar
implements BindableId {
    private final BeanProperty uidProp;

    public BindableIdScalar(BeanProperty uidProp) {
        this.uidProp = uidProp;
    }

    @Override
    public boolean isConcatenated() {
        return false;
    }

    @Override
    public String getIdentityColumn() {
        return this.uidProp.getDbColumn();
    }

    public String toString() {
        return this.uidProp.toString();
    }

    @Override
    public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
    }

    @Override
    public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
        throw new PersistenceException("Should not be called? only for concatinated keys");
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
        request.appendColumn(this.uidProp.getDbColumn());
    }

    @Override
    public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
        request.appendColumn(this.uidProp.getDbColumn());
    }

    @Override
    public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }

    @Override
    public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }

    private void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
        Object value = this.uidProp.getValue(bean);
        bindRequest.bind(value, this.uidProp, this.uidProp.getName(), bindNull);
        bindRequest.setIdValue(value);
    }
}

