/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeClass
extends ScalarTypeBaseVarchar<Class> {
    public ScalarTypeClass() {
        super(Class.class);
    }

    @Override
    public int getLength() {
        return 255;
    }

    @Override
    public Class<?> convertFromDbString(String dbValue) {
        return this.parse(dbValue);
    }

    @Override
    public String convertToDbString(Class beanValue) {
        return beanValue.getCanonicalName();
    }

    @Override
    public String formatValue(Class v) {
        return v.getCanonicalName();
    }

    @Override
    public Class<?> parse(String value) {
        try {
            return Class.forName(value);
        }
        catch (Exception e) {
            String msg = "Unable to find Class " + value;
            throw new PersistenceException(msg, e);
        }
    }
}

