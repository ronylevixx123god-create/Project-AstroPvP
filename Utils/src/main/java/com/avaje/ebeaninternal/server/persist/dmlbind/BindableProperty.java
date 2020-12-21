/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindableProperty
implements Bindable {
    protected final BeanProperty prop;

    public BindableProperty(BeanProperty prop) {
        this.prop = prop;
    }

    public String toString() {
        return this.prop.toString();
    }

    @Override
    public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
        if (request.hasChanged(this.prop)) {
            list.add(this);
        }
    }

    @Override
    public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        request.appendColumn(this.prop.getDbColumn());
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
        if (checkIncludes && !request.isIncludedWhere(this.prop)) {
            return;
        }
        if (bean == null || request.isDbNull(this.prop.getValue(bean))) {
            request.appendColumnIsNull(this.prop.getDbColumn());
        } else {
            request.appendColumn(this.prop.getDbColumn());
        }
    }

    @Override
    public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
        if (checkIncludes && !request.isIncluded(this.prop)) {
            return;
        }
        this.dmlBind(request, bean, true);
    }

    @Override
    public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
        if (checkIncludes && !request.isIncludedWhere(this.prop)) {
            return;
        }
        this.dmlBind(request, bean, false);
    }

    private void dmlBind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
        Object value = null;
        if (bean != null) {
            value = this.prop.getValue(bean);
        }
        request.bind(value, this.prop, this.prop.getName(), bindNull);
    }
}

