/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.el;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface ElPropertyDeploy {
    public static final String ROOT_ELPREFIX = "${}";

    public boolean containsMany();

    public boolean containsManySince(String var1);

    public String getElPrefix();

    public String getElPlaceholder(boolean var1);

    public String getName();

    public String getElName();

    public String getDbColumn();

    public BeanProperty getBeanProperty();
}

