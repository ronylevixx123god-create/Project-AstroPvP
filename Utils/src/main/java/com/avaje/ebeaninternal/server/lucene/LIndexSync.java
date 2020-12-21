/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.lucene.LIndex;

public class LIndexSync {
    private final String masterHost;
    private final LIndex index;

    public LIndexSync(LIndex index, String masterHost) {
        this.index = index;
        this.masterHost = masterHost;
    }

    public String getMasterHost() {
        return this.masterHost;
    }

    public LIndex getIndex() {
        return this.index;
    }
}

