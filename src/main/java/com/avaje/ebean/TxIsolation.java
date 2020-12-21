/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum TxIsolation {
    READ_COMMITED(2),
    READ_UNCOMMITTED(1),
    REPEATABLE_READ(4),
    SERIALIZABLE(8),
    NONE(0),
    DEFAULT(-1);

    final int level;

    private TxIsolation(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public static TxIsolation fromLevel(int connectionIsolationLevel) {
        switch (connectionIsolationLevel) {
            case 1: {
                return READ_UNCOMMITTED;
            }
            case 2: {
                return READ_COMMITED;
            }
            case 4: {
                return REPEATABLE_READ;
            }
            case 8: {
                return SERIALIZABLE;
            }
            case 0: {
                return NONE;
            }
            case -1: {
                return DEFAULT;
            }
        }
        throw new RuntimeException("Unknown isolation level " + connectionIsolationLevel);
    }
}

