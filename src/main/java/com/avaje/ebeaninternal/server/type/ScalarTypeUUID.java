/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import java.util.UUID;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeUUID
extends ScalarTypeBaseVarchar<UUID> {
    public ScalarTypeUUID() {
        super(UUID.class);
    }

    @Override
    public int getLength() {
        return 40;
    }

    @Override
    public UUID convertFromDbString(String dbValue) {
        return UUID.fromString(dbValue);
    }

    @Override
    public String convertToDbString(UUID beanValue) {
        return this.formatValue(beanValue);
    }

    @Override
    public UUID toBeanType(Object value) {
        return BasicTypeConverter.toUUID(value);
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.convert(value, this.jdbcType);
    }

    @Override
    public String formatValue(UUID v) {
        return v.toString();
    }

    @Override
    public UUID parse(String value) {
        return UUID.fromString(value);
    }
}

