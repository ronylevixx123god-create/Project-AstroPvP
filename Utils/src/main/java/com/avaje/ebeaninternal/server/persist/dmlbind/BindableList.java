/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindableList
implements Bindable {
    private final Bindable[] items;

    public BindableList(List<Bindable> list) {
        this.items = list.toArray(new Bindable[list.size()]);
    }

    @Override
    public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].addChanged(request, list);
        }
    }

    @Override
    public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlInsert(request, checkIncludes);
        }
    }

    @Override
    public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlAppend(request, checkIncludes);
        }
    }

    @Override
    public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object bean) {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlWhere(request, checkIncludes, bean);
        }
    }

    @Override
    public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBind(bindRequest, checkIncludes, bean);
        }
    }

    @Override
    public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i].dmlBindWhere(bindRequest, checkIncludes, bean);
        }
    }
}

