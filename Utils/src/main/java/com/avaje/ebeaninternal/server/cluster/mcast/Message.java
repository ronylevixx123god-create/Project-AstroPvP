/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;

public interface Message {
    public void writeBinaryMessage(BinaryMessageList var1) throws IOException;

    public boolean isControlMessage();

    public String getToHostPort();
}

