/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.mcast.AckResendMessages;
import com.avaje.ebeaninternal.server.cluster.mcast.IncomingPacketsLastAck;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageAck;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageResend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class IncomingPacketsProcessed {
    private final ConcurrentHashMap<String, GotAllPoint> mapByMember = new ConcurrentHashMap();
    private final int maxResendIncoming;

    public IncomingPacketsProcessed(int maxResendIncoming) {
        this.maxResendIncoming = maxResendIncoming;
    }

    public void removeMember(String memberKey) {
        this.mapByMember.remove(memberKey);
    }

    public boolean isProcessPacket(String memberKey, long packetId) {
        GotAllPoint memberPackets = this.getMemberPackets(memberKey);
        return memberPackets.processPacket(packetId);
    }

    public AckResendMessages getAckResendMessages(IncomingPacketsLastAck lastAck) {
        AckResendMessages response = new AckResendMessages();
        for (GotAllPoint member : this.mapByMember.values()) {
            MessageAck lastAckMessage = lastAck.getLastAck(member.getMemberKey());
            member.addAckResendMessages(response, lastAckMessage);
        }
        return response;
    }

    private GotAllPoint getMemberPackets(String memberKey) {
        GotAllPoint memberGotAllPoint = this.mapByMember.get(memberKey);
        if (memberGotAllPoint == null) {
            memberGotAllPoint = new GotAllPoint(memberKey, this.maxResendIncoming);
            this.mapByMember.put(memberKey, memberGotAllPoint);
        }
        return memberGotAllPoint;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class GotAllPoint {
        private static final Logger logger = Logger.getLogger(GotAllPoint.class.getName());
        private final String memberKey;
        private final int maxResendIncoming;
        private long gotAllPoint;
        private long gotMaxPoint;
        private ArrayList<Long> outOfOrderList = new ArrayList();
        private HashMap<Long, Integer> resendCountMap = new HashMap();
        private static final Integer ONE = 1;

        public GotAllPoint(String memberKey, int maxResendIncoming) {
            this.memberKey = memberKey;
            this.maxResendIncoming = maxResendIncoming;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void addAckResendMessages(AckResendMessages response, MessageAck lastAckMessage) {
            GotAllPoint gotAllPoint = this;
            synchronized (gotAllPoint) {
                if (lastAckMessage == null || lastAckMessage.getGotAllPacketId() < this.gotAllPoint) {
                    response.add(new MessageAck(this.memberKey, this.gotAllPoint));
                }
                if (this.getMissingPacketCount() > 0) {
                    List<Long> missingPackets = this.getMissingPackets();
                    response.add(new MessageResend(this.memberKey, missingPackets));
                }
            }
        }

        public String getMemberKey() {
            return this.memberKey;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public long getGotAllPoint() {
            GotAllPoint gotAllPoint = this;
            synchronized (gotAllPoint) {
                return this.gotAllPoint;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public long getGotMaxPoint() {
            GotAllPoint gotAllPoint = this;
            synchronized (gotAllPoint) {
                return this.gotMaxPoint;
            }
        }

        private int getMissingPacketCount() {
            if (this.gotMaxPoint <= this.gotAllPoint) {
                if (!this.resendCountMap.isEmpty()) {
                    this.resendCountMap.clear();
                }
                return 0;
            }
            return (int)(this.gotMaxPoint - this.gotAllPoint) - this.outOfOrderList.size();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public List<Long> getMissingPackets() {
            GotAllPoint gotAllPoint = this;
            synchronized (gotAllPoint) {
                ArrayList<Long> missingList = new ArrayList<Long>();
                boolean lostPacket = false;
                for (long i = this.gotAllPoint + 1L; i < this.gotMaxPoint; ++i) {
                    Long packetId = i;
                    if (this.outOfOrderList.contains(packetId)) continue;
                    if (this.incrementResendCount(packetId)) {
                        missingList.add(packetId);
                        continue;
                    }
                    lostPacket = true;
                }
                if (lostPacket) {
                    this.checkOutOfOrderList();
                }
                return missingList;
            }
        }

        private boolean incrementResendCount(Long packetId) {
            Integer resendCount = this.resendCountMap.get(packetId);
            if (resendCount != null) {
                int i = resendCount + 1;
                if (i > this.maxResendIncoming) {
                    logger.warning("Exceeded maxResendIncoming[" + this.maxResendIncoming + "] for packet[" + packetId + "]. Giving up on requesting it.");
                    this.resendCountMap.remove(packetId);
                    this.outOfOrderList.add(packetId);
                    return false;
                }
                resendCount = i;
                this.resendCountMap.put(packetId, resendCount);
            } else {
                this.resendCountMap.put(packetId, ONE);
            }
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean processPacket(long packetId) {
            GotAllPoint gotAllPoint = this;
            synchronized (gotAllPoint) {
                if (this.gotAllPoint == 0L) {
                    this.gotAllPoint = packetId;
                    return true;
                }
                if (packetId <= this.gotAllPoint) {
                    return false;
                }
                if (!this.resendCountMap.isEmpty()) {
                    this.resendCountMap.remove(packetId);
                }
                if (packetId == this.gotAllPoint + 1L) {
                    this.gotAllPoint = packetId;
                } else {
                    if (packetId > this.gotMaxPoint) {
                        this.gotMaxPoint = packetId;
                    }
                    this.outOfOrderList.add(packetId);
                }
                this.checkOutOfOrderList();
                return true;
            }
        }

        private void checkOutOfOrderList() {
            boolean continueCheck;
            if (this.outOfOrderList.size() == 0) {
                return;
            }
            block0: do {
                continueCheck = false;
                long nextPoint = this.gotAllPoint + 1L;
                Iterator<Long> it = this.outOfOrderList.iterator();
                while (it.hasNext()) {
                    Long id = it.next();
                    if (id != nextPoint) continue;
                    it.remove();
                    this.gotAllPoint = nextPoint;
                    continueCheck = true;
                    continue block0;
                }
            } while (continueCheck);
        }
    }
}

