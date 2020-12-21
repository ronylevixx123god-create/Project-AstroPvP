/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.ScalarTypeConverter;
import java.sql.Timestamp;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LongToTimestampConverter
implements ScalarTypeConverter<Long, Timestamp> {
    @Override
    public Long getNullValue() {
        return null;
    }

    @Override
    public Timestamp unwrapValue(Long beanType) {
        return new Timestamp(beanType);
    }

    @Override
    public Long wrapValue(Timestamp scalarType) {
        return scalarType.getTime();
    }
}

