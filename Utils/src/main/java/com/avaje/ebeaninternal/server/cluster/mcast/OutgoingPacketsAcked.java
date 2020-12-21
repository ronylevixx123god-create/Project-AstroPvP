/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.mcast.MessageAck;
import java.util.HashMap;
import java.util.Map;

public class OutgoingPacketsAcked {
    private long minimumGotAllPacketId;
    private Map<String, GroupMemberAck> recievedByMap = new HashMap<String, GroupMemberAck>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getGroupSize() {
        OutgoingPacketsAcked outgoingPacketsAcked = this;
        synchronized (outgoingPacketsAcked) {
            return this.recievedByMap.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getMinimumGotAllPacketId() {
        OutgoingPacketsAcked outgoingPacketsAcked = this;
        synchronized (outgoingPacketsAcked) {
            return this.minimumGotAllPacketId;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeMember(String groupMember) {
        OutgoingPacketsAcked outgoingPacketsAcked = this;
        synchronized (outgoingPacketsAcked) {
            this.recievedByMap.remove(groupMember);
            this.resetGotAllMin();
        }
    }

    private boolean resetGotAllMin() {
        long tempMin = this.recievedByMap.isEmpty() ? Long.MAX_VALUE : Long.MAX_VALUE;
        for (GroupMemberAck groupMemAck : this.recievedByMap.values()) {
            long memberMin = groupMemAck.getGotAllPacketId();
            if (memberMin >= tempMin) continue;
            tempMin = memberMin;
        }
        if (tempMin != this.minimumGotAllPacketId) {
            this.minimumGotAllPacketId = tempMin;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long receivedAck(String groupMember, MessageAck ack) {
        OutgoingPacketsAcked outgoingPacketsAcked = this;
        synchronized (outgoingPacketsAcked) {
            boolean checkMin = false;
            GroupMemberAck groupMemberAck = this.recievedByMap.get(groupMember);
            if (groupMemberAck == null) {
                groupMemberAck = new GroupMemberAck();
                groupMemberAck.setIfBigger(ack.getGotAllPacketId());
                this.recievedByMap.put(groupMember, groupMemberAck);
                checkMin = true;
            } else {
                checkMin = groupMemberAck.getGotAllPacketId() == this.minimumGotAllPacketId;
                groupMemberAck.setIfBigger(ack.getGotAllPacketId());
            }
            boolean minChanged = false;
            if (checkMin || this.minimumGotAllPacketId == 0L) {
                minChanged = this.resetGotAllMin();
            }
            return minChanged ? this.minimumGotAllPacketId : 0L;
        }
    }

    private static class GroupMemberAck {
        private long gotAllPacketId;

        private GroupMemberAck() {
        }

        private long getGotAllPacketId() {
            return this.gotAllPacketId;
        }

        private void setIfBigger(long newGotAll) {
            if (newGotAll > this.gotAllPacketId) {
                this.gotAllPacketId = newGotAll;
            }
        }
    }
}

