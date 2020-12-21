/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindableUnidirectional
implements Bindable {
    private final BeanPropertyAssocOne<?> unidirectional;
    private final ImportedId importedId;
    private final BeanDescriptor<?> desc;

    public BindableUnidirectional(BeanDescriptor<?> desc, BeanPropertyAssocOne<?> unidirectional) {
        this.desc = desc;
        this.unidirectional = unidirectional;
        this.importedId = unidirectional.getImportedId();
    }

    public String toString() {
        return "BindableShadowFKey " + this.unidirectional;
    }

    @Override
    public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
        throw new PersistenceException("Never called (for insert only)");
    }

    @Override
    public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
        this.dmlAppend(request, checkIncludes);
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
        this.importedId.dmlAppend(request);
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
        throw new RuntimeException("Never called");
    }

    @Override
    public void dmlBind(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, true);
    }

    @Override
    public void dmlBindWhere(BindableRequest request, boolean checkIncludes, Object bean) throws SQLException {
        this.dmlBind(request, checkIncludes, bean, false);
    }

    private void dmlBind(BindableRequest request, boolean checkIncludes, Object bean, boolean bindNull) throws SQLException {
        PersistRequestBean<?> persistRequest = request.getPersistRequest();
        Object parentBean = persistRequest.getParentBean();
        if (parentBean == null) {
            Class<?> localType = this.desc.getBeanType();
            Class<?> targetType = this.unidirectional.getTargetType();
            String msg = "Error inserting bean [" + localType + "] with unidirectional relationship. ";
            msg = msg + "For inserts you must use cascade save on the master bean [" + targetType + "].";
            throw new PersistenceException(msg);
        }
        this.importedId.bind(request, parentBean, bindNull);
    }
}

