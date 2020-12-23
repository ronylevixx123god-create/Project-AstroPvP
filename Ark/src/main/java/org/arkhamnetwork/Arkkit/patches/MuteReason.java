/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches;

class MuteReason {
    private final long expire;
    private final String reason;

    public MuteReason(long expire, String reason) {
        this.expire = expire;
        this.reason = reason;
    }

    public long getExpire() {
        return this.expire;
    }

    public String getReason() {
        return this.reason;
    }
}

