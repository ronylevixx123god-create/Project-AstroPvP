/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageAck;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageResend;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AckResendMessages {
    ArrayList<Message> messages = new ArrayList();

    public String toString() {
        return this.messages.toString();
    }

    public int size() {
        return this.messages.size();
    }

    public void add(MessageAck ack) {
        this.messages.add(ack);
    }

    public void add(MessageResend resend) {
        this.messages.add(resend);
    }

    public List<Message> getMessages() {
        return this.messages;
    }
}

