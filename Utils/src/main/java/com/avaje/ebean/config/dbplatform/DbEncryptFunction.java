/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.dbplatform;

public interface DbEncryptFunction {
    public String getDecryptSql(String var1);

    public String getEncryptBindSql();
}

