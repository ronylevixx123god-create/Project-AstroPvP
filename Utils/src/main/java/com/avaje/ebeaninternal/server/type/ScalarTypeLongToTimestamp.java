/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.LongToTimestampConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeTimestamp;
import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
import java.sql.Timestamp;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeLongToTimestamp
extends ScalarTypeWrapper<Long, Timestamp> {
    public ScalarTypeLongToTimestamp() {
        super(Long.class, new ScalarTypeTimestamp(), new LongToTimestampConverter());
    }
}

