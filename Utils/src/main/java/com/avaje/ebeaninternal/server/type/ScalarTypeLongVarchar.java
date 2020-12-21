/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeClob;
import java.sql.SQLException;

public class ScalarTypeLongVarchar
extends ScalarTypeClob {
    public ScalarTypeLongVarchar() {
        super(true, -1);
    }

    public String read(DataReader dataReader) throws SQLException {
        return dataReader.getStringFromStream();
    }
}

