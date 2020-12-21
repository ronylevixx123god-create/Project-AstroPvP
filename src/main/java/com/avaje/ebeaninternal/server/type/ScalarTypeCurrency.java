/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import java.util.Currency;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeCurrency
extends ScalarTypeBaseVarchar<Currency> {
    public ScalarTypeCurrency() {
        super(Currency.class);
    }

    @Override
    public int getLength() {
        return 3;
    }

    @Override
    public Currency convertFromDbString(String dbValue) {
        return Currency.getInstance(dbValue);
    }

    @Override
    public String convertToDbString(Currency beanValue) {
        return beanValue.getCurrencyCode();
    }

    @Override
    public String formatValue(Currency v) {
        return v.toString();
    }

    @Override
    public Currency parse(String value) {
        return Currency.getInstance(value);
    }
}

