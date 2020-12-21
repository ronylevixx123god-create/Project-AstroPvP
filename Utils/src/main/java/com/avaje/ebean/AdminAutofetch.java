/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

public interface AdminAutofetch {
    public boolean isProfiling();

    public void setProfiling(boolean var1);

    public boolean isQueryTuning();

    public void setQueryTuning(boolean var1);

    public double getProfilingRate();

    public void setProfilingRate(double var1);

    public int getProfilingBase();

    public void setProfilingBase(int var1);

    public int getProfilingMin();

    public void setProfilingMin(int var1);

    public String collectUsageViaGC();

    public String updateTunedQueryInfo();

    public int clearTunedQueryInfo();

    public int clearProfilingInfo();

    public void clearQueryStatistics();

    public int getTotalTunedQueryCount();

    public int getTotalTunedQuerySize();

    public int getTotalProfileSize();
}

