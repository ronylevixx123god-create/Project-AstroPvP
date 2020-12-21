/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import java.net.URI;
import java.net.URISyntaxException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeURI
extends ScalarTypeBaseVarchar<URI> {
    public ScalarTypeURI() {
        super(URI.class);
    }

    @Override
    public URI convertFromDbString(String dbValue) {
        try {
            return new URI(dbValue);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Error with URI [" + dbValue + "] " + e);
        }
    }

    @Override
    public String convertToDbString(URI beanValue) {
        return beanValue.toString();
    }

    @Override
    public String formatValue(URI v) {
        return v.toString();
    }

    @Override
    public URI parse(String value) {
        try {
            return new URI(value);
        }
        catch (URISyntaxException e) {
            throw new TextException("Error with URI [" + value + "] ", e);
        }
    }
}

