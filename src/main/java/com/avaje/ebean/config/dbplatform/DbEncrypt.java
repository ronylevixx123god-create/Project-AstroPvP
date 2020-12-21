/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.config.dbplatform.DbEncryptFunction;

public interface DbEncrypt {
    public DbEncryptFunction getDbEncryptFunction(int var1);

    public int getEncryptDbType();

    public boolean isBindEncryptDataFirst();
}

