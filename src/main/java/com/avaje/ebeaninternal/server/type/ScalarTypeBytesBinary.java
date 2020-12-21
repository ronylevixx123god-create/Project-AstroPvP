/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesBase;
import java.sql.SQLException;

public class ScalarTypeBytesBinary
extends ScalarTypeBytesBase {
    public ScalarTypeBytesBinary() {
        super(true, -2);
    }

    public byte[] read(DataReader dataReader) throws SQLException {
        return dataReader.getBytes();
    }
}

