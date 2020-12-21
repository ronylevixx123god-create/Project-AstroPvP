/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.Transaction;

public interface IdGenerator {
    public static final String AUTO_UUID = "auto.uuid";

    public String getName();

    public boolean isDbSequence();

    public Object nextId(Transaction var1);

    public void preAllocateIds(int var1);
}

