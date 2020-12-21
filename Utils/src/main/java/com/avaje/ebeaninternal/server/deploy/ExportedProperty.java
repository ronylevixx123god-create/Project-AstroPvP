/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class ExportedProperty {
    private final String foreignDbColumn;
    private final BeanProperty property;
    private final boolean embedded;

    public ExportedProperty(boolean embedded, String foreignDbColumn, BeanProperty property) {
        this.embedded = embedded;
        this.foreignDbColumn = InternString.intern(foreignDbColumn);
        this.property = property;
    }

    public boolean isEmbedded() {
        return this.embedded;
    }

    public Object getValue(Object bean) {
        return this.property.getValue(bean);
    }

    public String getForeignDbColumn() {
        return this.foreignDbColumn;
    }
}

