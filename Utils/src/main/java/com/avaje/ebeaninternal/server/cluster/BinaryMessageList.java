/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BinaryMessageList {
    ArrayList<BinaryMessage> list = new ArrayList();

    public void add(BinaryMessage msg) {
        this.list.add(msg);
    }

    public List<BinaryMessage> getList() {
        return this.list;
    }
}

