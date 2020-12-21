/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.EncryptKey;

public interface Encryptor {
    public byte[] encrypt(byte[] var1, EncryptKey var2);

    public byte[] decrypt(byte[] var1, EncryptKey var2);

    public byte[] encryptString(String var1, EncryptKey var2);

    public String decryptString(byte[] var1, EncryptKey var2);
}

