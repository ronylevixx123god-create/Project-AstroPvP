/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import javax.persistence.PersistenceException;

public class PersistenceLuceneException
extends PersistenceException {
    private static final long serialVersionUID = 1495423311592521260L;

    public PersistenceLuceneException(Throwable e) {
        super(e);
    }

    public PersistenceLuceneException(String msg, Throwable e) {
        super(msg, e);
    }

    public PersistenceLuceneException(String msg) {
        super(msg);
    }
}

