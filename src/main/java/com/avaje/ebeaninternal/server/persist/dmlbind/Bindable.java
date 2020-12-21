/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
import java.sql.SQLException;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Bindable {
    public void addChanged(PersistRequestBean<?> var1, List<Bindable> var2);

    public void dmlInsert(GenerateDmlRequest var1, boolean var2);

    public void dmlAppend(GenerateDmlRequest var1, boolean var2);

    public void dmlWhere(GenerateDmlRequest var1, boolean var2, Object var3);

    public void dmlBind(BindableRequest var1, boolean var2, Object var3) throws SQLException;

    public void dmlBindWhere(BindableRequest var1, boolean var2, Object var3) throws SQLException;
}

