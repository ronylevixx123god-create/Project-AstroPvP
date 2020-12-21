/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BindableId
extends Bindable {
    public boolean isConcatenated();

    public String getIdentityColumn();

    public boolean deriveConcatenatedId(PersistRequestBean<?> var1);
}

