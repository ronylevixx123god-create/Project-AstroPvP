/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.io.IOException;

public interface LuceneClusterIndexSync {
    public boolean sync(LIndex var1, String var2) throws IOException;

    public boolean isMaster();

    public Mode getMode();

    public void setMode(Mode var1);

    public String getMasterHost();

    public void setMasterHost(String var1);

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Mode {
        MASTER_MODE,
        SLAVE_MODE;

    }
}

