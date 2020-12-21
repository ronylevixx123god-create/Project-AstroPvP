/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.config.EncryptKeyManager;
import com.avaje.ebean.config.Encryptor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DataEncryptSupport {
    private final EncryptKeyManager encryptKeyManager;
    private final Encryptor encryptor;
    private final String table;
    private final String column;

    public DataEncryptSupport(EncryptKeyManager encryptKeyManager, Encryptor encryptor, String table, String column) {
        this.encryptKeyManager = encryptKeyManager;
        this.encryptor = encryptor;
        this.table = table;
        this.column = column;
    }

    public byte[] encrypt(byte[] data) {
        EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.encrypt(data, key);
    }

    public byte[] decrypt(byte[] data) {
        EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.decrypt(data, key);
    }

    public String decryptObject(byte[] data) {
        EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.decryptString(data, key);
    }

    public <T> byte[] encryptObject(String formattedValue) {
        EncryptKey key = this.encryptKeyManager.getEncryptKey(this.table, this.column);
        return this.encryptor.encryptString(formattedValue, key);
    }
}

