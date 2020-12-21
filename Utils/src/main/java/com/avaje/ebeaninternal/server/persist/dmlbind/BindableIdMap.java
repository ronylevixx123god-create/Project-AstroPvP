/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.MatchedImportedProperty;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindableIdMap
implements BindableId {
    private final BeanProperty[] uids;
    private final MatchedImportedProperty[] matches;

    public BindableIdMap(BeanProperty[] uids, BeanDescriptor<?> desc) {
        this.uids = uids;
        this.matches = MatchedImportedProperty.build(uids, desc);
    }

    @Override
    public boolean isConcatenated() {
        return true;
    }

    @Override
    public String getIdentityColumn() {
        return null;
    }

    public String toString() {
        return Arrays.toString(this.uids);
    }

    @Override
    public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
        this.dmlAppend(request, false);
    }

    @Override
    public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
        for (int i = 0; i < this.uids.length; ++i) {
            request.appendColumn(this.uids[i].getDbColumn());
        }
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
        LinkedHashMap<String, Object> mapId = new LinkedHashMap<String, Object>();
        for (int i = 0; i < this.uids.length; ++i) {
            Object value = this.uids[i].getValue(bean);
            bindRequest.bind(value, this.uids[i], this.uids[i].getName(), bindNull);
            mapId.put(this.uids[i].getName(), value);
        }
        bindRequest.setIdValue(mapId);
    }

    @Override
    public boolean deriveConcatenatedId(PersistRequestBean<?> persist) {
        if (this.matches == null) {
            String m = "Matches for the concatinated key columns where not found? I expect that the concatinated key was null, and this bean does not have ManyToOne assoc beans matching the primary key columns?";
            throw new PersistenceException(m);
        }
        Object bean = persist.getBean();
        for (int i = 0; i < this.matches.length; ++i) {
            this.matches[i].populate(bean, bean);
        }
        return true;
    }
}

