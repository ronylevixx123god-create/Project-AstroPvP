/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.NodeUsageListener;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.config.AutofetchMode;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.autofetch.Statistics;
import com.avaje.ebeaninternal.server.autofetch.TunedQueryInfo;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface AutoFetchManager
extends NodeUsageListener {
    public void setOwner(SpiEbeanServer var1, ServerConfig var2);

    public void clearQueryStatistics();

    public int clearTunedQueryInfo();

    public int clearProfilingInfo();

    public void shutdown();

    public TunedQueryInfo getTunedQueryInfo(String var1);

    public Statistics getStatistics(String var1);

    public Iterator<TunedQueryInfo> iterateTunedQueryInfo();

    public Iterator<Statistics> iterateStatistics();

    public boolean isProfiling();

    public void setProfiling(boolean var1);

    public boolean isQueryTuning();

    public void setQueryTuning(boolean var1);

    public AutofetchMode getMode();

    public void setMode(AutofetchMode var1);

    public double getProfilingRate();

    public void setProfilingRate(double var1);

    public int getProfilingBase();

    public void setProfilingBase(int var1);

    public int getProfilingMin();

    public void setProfilingMin(int var1);

    public String collectUsageViaGC(long var1);

    public String updateTunedQueryInfo();

    public boolean tuneQuery(SpiQuery<?> var1);

    public void collectQueryInfo(ObjectGraphNode var1, int var2, int var3);

    public int getTotalTunedQueryCount();

    public int getTotalTunedQuerySize();

    public int getTotalProfileSize();
}

