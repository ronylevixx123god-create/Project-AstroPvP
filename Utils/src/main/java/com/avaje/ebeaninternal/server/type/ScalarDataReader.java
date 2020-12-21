/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ScalarDataReader<T> {
    public T read(DataReader var1) throws SQLException;

    public void loadIgnore(DataReader var1);

    public void bind(DataBind var1, T var2) throws SQLException;

    public void accumulateScalarTypes(String var1, CtCompoundTypeScalarList var2);
}

