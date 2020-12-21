/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.Packet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class OutgoingPacketsCache {
    private final Map<Long, Packet> packetMap = new TreeMap<Long, Packet>();

    public int size() {
        return this.packetMap.size();
    }

    public Packet getPacket(Long packetId) {
        return this.packetMap.get(packetId);
    }

    public String toString() {
        return this.packetMap.keySet().toString();
    }

    public void remove(Packet packet) {
        this.packetMap.remove(packet.getPacketId());
    }

    public void registerPackets(List<Packet> packets) {
        for (int i = 0; i < packets.size(); ++i) {
            Packet p = packets.get(i);
            this.packetMap.put(p.getPacketId(), p);
        }
    }

    public int trimAll() {
        int size = this.packetMap.size();
        this.packetMap.clear();
        return size;
    }

    public void trimAcknowledgedMessages(long minAcked) {
        Iterator<Long> it = this.packetMap.keySet().iterator();
        while (it.hasNext()) {
            Long pktId = it.next();
            if (minAcked < pktId) continue;
            it.remove();
        }
    }
}

