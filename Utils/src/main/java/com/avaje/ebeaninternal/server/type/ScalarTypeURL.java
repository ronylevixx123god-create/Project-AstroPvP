/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeURL
extends ScalarTypeBaseVarchar<URL> {
    public ScalarTypeURL() {
        super(URL.class);
    }

    @Override
    public URL convertFromDbString(String dbValue) {
        try {
            return new URL(dbValue);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error with URL [" + dbValue + "] " + e);
        }
    }

    @Override
    public String convertToDbString(URL beanValue) {
        return this.formatValue(beanValue);
    }

    @Override
    public String formatValue(URL v) {
        return v.toString();
    }

    @Override
    public URL parse(String value) {
        try {
            return new URL(value);
        }
        catch (MalformedURLException e) {
            throw new TextException(e);
        }
    }
}

