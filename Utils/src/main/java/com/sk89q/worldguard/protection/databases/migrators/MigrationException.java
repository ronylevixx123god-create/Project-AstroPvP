/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases.migrators;

public class MigrationException
extends Exception {
    private static final long serialVersionUID = 1L;

    public MigrationException() {
    }

    public MigrationException(String message) {
        super(message);
    }

    public MigrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MigrationException(Throwable cause) {
        super(cause);
    }
}

