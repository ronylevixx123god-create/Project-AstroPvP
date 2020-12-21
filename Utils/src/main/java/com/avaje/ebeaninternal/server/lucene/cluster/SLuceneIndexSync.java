/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClusterSocketClient;
import java.io.IOException;

public class SLuceneIndexSync
implements LuceneClusterIndexSync {
    private LuceneClusterIndexSync.Mode mode;
    private String masterHost;

    public boolean sync(LIndex index, String masterHost) throws IOException {
        SLuceneClusterSocketClient c = new SLuceneClusterSocketClient(index);
        if (c.isSynchIndex(masterHost)) {
            c.transferFiles();
            index.refresh(true);
            return true;
        }
        return false;
    }

    public boolean isMaster() {
        return LuceneClusterIndexSync.Mode.MASTER_MODE.equals((Object)this.mode);
    }

    public String getMasterHost() {
        return this.masterHost;
    }

    public LuceneClusterIndexSync.Mode getMode() {
        return this.mode;
    }

    public void setMasterHost(String masterHost) {
        this.masterHost = masterHost;
    }

    public void setMode(LuceneClusterIndexSync.Mode mode) {
        this.mode = mode;
    }
}

