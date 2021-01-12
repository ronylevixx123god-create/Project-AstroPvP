/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.cosmicpvp.factionpoints.struct;

import org.jetbrains.annotations.NotNull;

public class PointChangeLog
implements Comparable {
    private int amount;
    private String reason;
    private String pl;
    private long time;

    public int compareTo(@NotNull Object o) {
        if (o == null) {
            PointChangeLog.$$$reportNull$$$0(0);
        }
        if (o instanceof PointChangeLog) {
            PointChangeLog other = (PointChangeLog)o;
            if (other.getTime() > this.getTime()) {
                return 1;
            }
            return -1;
        }
        return 0;
    }

    public PointChangeLog(int amount, String reason, String pl, long time) {
        this.amount = amount;
        this.reason = reason;
        this.pl = pl;
        this.time = time;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getReason() {
        return this.reason;
    }

    public String getPl() {
        return this.pl;
    }

    public long getTime() {
        return this.time;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "o", "com/cosmicpvp/factionpoints/struct/PointChangeLog", "compareTo"));
    }
}

