/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.mcast.AckResendMessages;
import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageAck;
import java.util.HashMap;
import java.util.List;

public class IncomingPacketsLastAck {
    private HashMap<String, MessageAck> lastAckMap = new HashMap();

    public String toString() {
        return this.lastAckMap.values().toString();
    }

    public void remove(String memberHostPort) {
        this.lastAckMap.remove(memberHostPort);
    }

    public MessageAck getLastAck(String memberHostPort) {
        return this.lastAckMap.get(memberHostPort);
    }

    public void updateLastAck(AckResendMessages ackResendMessages) {
        List<Message> messages = ackResendMessages.getMessages();
        for (int i = 0; i < messages.size(); ++i) {
            Message msg = messages.get(i);
            if (!(msg instanceof MessageAck)) continue;
            MessageAck lastAck = (MessageAck)msg;
            this.lastAckMap.put(lastAck.getToHostPort(), lastAck);
        }
    }
}

