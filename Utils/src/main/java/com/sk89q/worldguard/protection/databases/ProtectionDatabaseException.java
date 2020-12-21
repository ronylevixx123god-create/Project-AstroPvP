/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases;

public class ProtectionDatabaseException
extends Exception {
    private static final long serialVersionUID = 1L;

    public ProtectionDatabaseException() {
    }

    public ProtectionDatabaseException(String message) {
        super(message);
    }

    public ProtectionDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtectionDatabaseException(Throwable cause) {
        super(cause);
    }
}

