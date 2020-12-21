/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import com.avaje.ebeaninternal.server.cluster.Packet;
import com.avaje.ebeaninternal.server.cluster.PacketMessages;
import com.avaje.ebeaninternal.server.cluster.PacketTransactionEvent;
import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PacketWriter {
    private final PacketIdGenerator idGenerator = new PacketIdGenerator();
    private final PacketBuilder messagesPacketBuilder;
    private final PacketBuilder transEventPacketBuilder;

    public PacketWriter(int maxPacketSize) {
        this.messagesPacketBuilder = new PacketBuilder(maxPacketSize, this.idGenerator, new MessagesPacketFactory());
        this.transEventPacketBuilder = new PacketBuilder(maxPacketSize, this.idGenerator, new TransPacketFactory());
    }

    public long currentPacketId() {
        return this.idGenerator.currentPacketId();
    }

    public List<Packet> write(boolean requiresAck, List<? extends Message> messages) throws IOException {
        BinaryMessageList binaryMsgList = new BinaryMessageList();
        for (int i = 0; i < messages.size(); ++i) {
            Message message = messages.get(i);
            message.writeBinaryMessage(binaryMsgList);
        }
        return this.messagesPacketBuilder.write(requiresAck, binaryMsgList, "");
    }

    public List<Packet> write(RemoteTransactionEvent transEvent) throws IOException {
        BinaryMessageList messageList = new BinaryMessageList();
        transEvent.writeBinaryMessage(messageList);
        return this.transEventPacketBuilder.write(true, messageList, transEvent.getServerName());
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class PacketBuilder {
        private final PacketIdGenerator idGenerator;
        private final PacketFactory packetFactory;
        private final int maxPacketSize;

        private PacketBuilder(int maxPacketSize, PacketIdGenerator idGenerator, PacketFactory packetFactory) {
            this.maxPacketSize = maxPacketSize;
            this.idGenerator = idGenerator;
            this.packetFactory = packetFactory;
        }

        private List<Packet> write(boolean requiresAck, BinaryMessageList messageList, String serverName) throws IOException {
            List<BinaryMessage> list = messageList.getList();
            ArrayList<Packet> packets = new ArrayList<Packet>(1);
            long timestamp = System.currentTimeMillis();
            long packetId = requiresAck ? this.idGenerator.nextPacketId() : 0L;
            Packet p = this.packetFactory.createPacket(packetId, timestamp, serverName);
            packets.add(p);
            for (int i = 0; i < list.size(); ++i) {
                BinaryMessage binMsg = list.get(i);
                if (p.writeBinaryMessage(binMsg, this.maxPacketSize)) continue;
                packetId = requiresAck ? this.idGenerator.nextPacketId() : 0L;
                p = this.packetFactory.createPacket(packetId, timestamp, serverName);
                packets.add(p);
                p.writeBinaryMessage(binMsg, this.maxPacketSize);
            }
            p.writeEof();
            return packets;
        }
    }

    private static class MessagesPacketFactory
    implements PacketFactory {
        private MessagesPacketFactory() {
        }

        public Packet createPacket(long packetId, long timestamp, String serverName) throws IOException {
            return PacketMessages.forWrite(packetId, timestamp, serverName);
        }
    }

    private static class TransPacketFactory
    implements PacketFactory {
        private TransPacketFactory() {
        }

        public Packet createPacket(long packetId, long timestamp, String serverName) throws IOException {
            return PacketTransactionEvent.forWrite(packetId, timestamp, serverName);
        }
    }

    static interface PacketFactory {
        public Packet createPacket(long var1, long var3, String var5) throws IOException;
    }

    private static class PacketIdGenerator {
        long packetIdCounter;

        private PacketIdGenerator() {
        }

        public long nextPacketId() {
            return ++this.packetIdCounter;
        }

        public long currentPacketId() {
            return this.packetIdCounter;
        }
    }
}

