/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.CallStack;
import com.avaje.ebean.bean.NodeUsageCollector;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import com.avaje.ebean.config.AutofetchConfig;
import com.avaje.ebean.config.AutofetchMode;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.autofetch.DefaultAutoFetchManagerLogging;
import com.avaje.ebeaninternal.server.autofetch.Statistics;
import com.avaje.ebeaninternal.server.autofetch.TunedQueryInfo;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultAutoFetchManager
implements AutoFetchManager,
Serializable {
    private static final long serialVersionUID = -6826119882781771722L;
    private final String statisticsMonitor = new String();
    private final String fileName;
    private Map<String, Statistics> statisticsMap = new ConcurrentHashMap<String, Statistics>();
    private Map<String, TunedQueryInfo> tunedQueryInfoMap = new ConcurrentHashMap<String, TunedQueryInfo>();
    private transient long defaultGarbageCollectionWait = 100L;
    private transient int tunedQueryCount;
    private transient double profilingRate = 0.1;
    private transient int profilingBase = 10;
    private transient int profilingMin = 1;
    private transient boolean profiling;
    private transient boolean queryTuning;
    private transient boolean queryTuningAddVersion;
    private transient AutofetchMode mode;
    private transient boolean useFileLogging;
    private transient SpiEbeanServer server;
    private transient DefaultAutoFetchManagerLogging logging;

    public DefaultAutoFetchManager(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setOwner(SpiEbeanServer server, ServerConfig serverConfig) {
        this.server = server;
        this.logging = new DefaultAutoFetchManagerLogging(serverConfig, this);
        AutofetchConfig autofetchConfig = serverConfig.getAutofetchConfig();
        this.useFileLogging = autofetchConfig.isUseFileLogging();
        this.queryTuning = autofetchConfig.isQueryTuning();
        this.queryTuningAddVersion = autofetchConfig.isQueryTuningAddVersion();
        this.profiling = autofetchConfig.isProfiling();
        this.profilingMin = autofetchConfig.getProfilingMin();
        this.profilingBase = autofetchConfig.getProfilingBase();
        this.setProfilingRate(autofetchConfig.getProfilingRate());
        this.defaultGarbageCollectionWait = autofetchConfig.getGarbageCollectionWait();
        this.mode = autofetchConfig.getMode();
        if (this.profiling || this.queryTuning) {
            String msg = "AutoFetch queryTuning[" + this.queryTuning + "] profiling[" + this.profiling + "] mode[" + (Object)((Object)this.mode) + "]  profiling rate[" + this.profilingRate + "] min[" + this.profilingMin + "] base[" + this.profilingBase + "]";
            this.logging.logToJavaLogger(msg);
        }
    }

    @Override
    public void clearQueryStatistics() {
        this.server.clearQueryStatistics();
    }

    @Override
    public int getTotalTunedQueryCount() {
        return this.tunedQueryCount;
    }

    @Override
    public int getTotalTunedQuerySize() {
        return this.tunedQueryInfoMap.size();
    }

    @Override
    public int getTotalProfileSize() {
        return this.statisticsMap.size();
    }

    @Override
    public int clearTunedQueryInfo() {
        this.tunedQueryCount = 0;
        int size = this.tunedQueryInfoMap.size();
        this.tunedQueryInfoMap.clear();
        return size;
    }

    @Override
    public int clearProfilingInfo() {
        int size = this.statisticsMap.size();
        this.statisticsMap.clear();
        return size;
    }

    public void serialize() {
        File autoFetchFile = new File(this.fileName);
        try {
            FileOutputStream fout = new FileOutputStream(autoFetchFile);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(this);
            oout.flush();
            oout.close();
        }
        catch (Exception e) {
            String msg = "Error serializing autofetch file";
            this.logging.logError(Level.SEVERE, msg, e);
        }
    }

    @Override
    public TunedQueryInfo getTunedQueryInfo(String originKey) {
        return this.tunedQueryInfoMap.get(originKey);
    }

    @Override
    public Statistics getStatistics(String originKey) {
        return this.statisticsMap.get(originKey);
    }

    @Override
    public Iterator<TunedQueryInfo> iterateTunedQueryInfo() {
        return this.tunedQueryInfoMap.values().iterator();
    }

    @Override
    public Iterator<Statistics> iterateStatistics() {
        return this.statisticsMap.values().iterator();
    }

    @Override
    public boolean isProfiling() {
        return this.profiling;
    }

    @Override
    public void setProfiling(boolean profiling) {
        this.profiling = profiling;
    }

    @Override
    public boolean isQueryTuning() {
        return this.queryTuning;
    }

    @Override
    public void setQueryTuning(boolean queryTuning) {
        this.queryTuning = queryTuning;
    }

    @Override
    public double getProfilingRate() {
        return this.profilingRate;
    }

    @Override
    public AutofetchMode getMode() {
        return this.mode;
    }

    @Override
    public void setMode(AutofetchMode mode) {
        this.mode = mode;
    }

    @Override
    public void setProfilingRate(double rate) {
        if (rate < 0.0) {
            rate = 0.0;
        } else if (rate > 1.0) {
            rate = 1.0;
        }
        this.profilingRate = rate;
    }

    @Override
    public int getProfilingBase() {
        return this.profilingBase;
    }

    @Override
    public void setProfilingBase(int profilingBase) {
        this.profilingBase = profilingBase;
    }

    @Override
    public int getProfilingMin() {
        return this.profilingMin;
    }

    @Override
    public void setProfilingMin(int profilingMin) {
        this.profilingMin = profilingMin;
    }

    @Override
    public void shutdown() {
        this.collectUsageViaGC(-1L);
        if (this.useFileLogging) {
            this.serialize();
        }
    }

    @Override
    public String collectUsageViaGC(long waitMillis) {
        System.gc();
        try {
            if (waitMillis < 0L) {
                waitMillis = this.defaultGarbageCollectionWait;
            }
            Thread.sleep(waitMillis);
        }
        catch (InterruptedException e) {
            String msg = "Error while sleeping after System.gc() request.";
            this.logging.logError(Level.SEVERE, msg, e);
            return msg;
        }
        return this.updateTunedQueryInfo();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String updateTunedQueryInfo() {
        if (!this.profiling) {
            return "Not profiling";
        }
        String string = this.statisticsMonitor;
        synchronized (string) {
            Counters counters = new Counters();
            for (Statistics queryPointStatistics : this.statisticsMap.values()) {
                if (!queryPointStatistics.hasUsage()) {
                    counters.incrementNoUsage();
                    continue;
                }
                this.updateTunedQueryFromUsage(counters, queryPointStatistics);
            }
            String summaryInfo = counters.toString();
            if (counters.isInteresting()) {
                this.logging.logSummary(summaryInfo);
            }
            return summaryInfo;
        }
    }

    private void updateTunedQueryFromUsage(Counters counters, Statistics statistics) {
        ObjectGraphOrigin queryPoint = statistics.getOrigin();
        String beanType = queryPoint.getBeanType();
        try {
            Class<?> beanClass = ClassUtil.forName(beanType, this.getClass());
            BeanDescriptor<?> beanDescriptor = this.server.getBeanDescriptor(beanClass);
            if (beanDescriptor != null) {
                OrmQueryDetail newFetchDetail = statistics.buildTunedFetch(beanDescriptor);
                TunedQueryInfo currentFetch = this.tunedQueryInfoMap.get(queryPoint.getKey());
                if (currentFetch == null) {
                    counters.incrementNew();
                    currentFetch = statistics.createTunedFetch(newFetchDetail);
                    this.logging.logNew(currentFetch);
                    this.tunedQueryInfoMap.put(queryPoint.getKey(), currentFetch);
                } else if (!currentFetch.isSame(newFetchDetail)) {
                    counters.incrementModified();
                    this.logging.logChanged(currentFetch, newFetchDetail);
                    currentFetch.setTunedDetail(newFetchDetail);
                } else {
                    counters.incrementUnchanged();
                }
                currentFetch.setProfileCount(statistics.getCounter());
            }
        }
        catch (ClassNotFoundException e) {
            String msg = e.toString() + " updating autoFetch tuned query for " + beanType + ". It isLikely this bean has been renamed or moved";
            this.logging.logError(Level.INFO, msg, null);
            this.statisticsMap.remove(statistics.getOrigin().getKey());
        }
    }

    private boolean useAutoFetch(SpiQuery<?> query) {
        if (query.isLoadBeanCache() || query.isSharedInstance()) {
            return false;
        }
        Boolean autoFetch = query.isAutofetch();
        if (autoFetch != null) {
            return autoFetch;
        }
        switch (this.mode) {
            case DEFAULT_ON: {
                return true;
            }
            case DEFAULT_OFF: {
                return false;
            }
            case DEFAULT_ONIFEMPTY: {
                return query.isDetailEmpty();
            }
        }
        throw new PersistenceException("Invalid autoFetchMode " + (Object)((Object)this.mode));
    }

    @Override
    public boolean tuneQuery(SpiQuery<?> query) {
        int profileCount;
        if (!this.queryTuning && !this.profiling) {
            return false;
        }
        if (!this.useAutoFetch(query)) {
            return false;
        }
        ObjectGraphNode parentAutoFetchNode = query.getParentNode();
        if (parentAutoFetchNode != null) {
            query.setAutoFetchManager(this);
            return true;
        }
        CallStack stack = this.server.createCallStack();
        ObjectGraphNode origin = query.setOrigin(stack);
        TunedQueryInfo tunedFetch = this.tunedQueryInfoMap.get(origin.getOriginQueryPoint().getKey());
        int n = profileCount = tunedFetch == null ? 0 : tunedFetch.getProfileCount();
        if (this.profiling) {
            if (tunedFetch == null) {
                query.setAutoFetchManager(this);
            } else if (profileCount < this.profilingBase) {
                query.setAutoFetchManager(this);
            } else if (tunedFetch.isPercentageProfile(this.profilingRate)) {
                query.setAutoFetchManager(this);
            }
        }
        if (this.queryTuning && tunedFetch != null && profileCount >= this.profilingMin) {
            if (tunedFetch.autoFetchTune(query)) {
                ++this.tunedQueryCount;
            }
            return true;
        }
        return false;
    }

    @Override
    public void collectQueryInfo(ObjectGraphNode node, int beans, int micros) {
        ObjectGraphOrigin origin;
        if (node != null && (origin = node.getOriginQueryPoint()) != null) {
            Statistics stats = this.getQueryPointStats(origin);
            stats.collectQueryInfo(node, beans, micros);
        }
    }

    @Override
    public void collectNodeUsage(NodeUsageCollector usageCollector) {
        ObjectGraphOrigin origin = usageCollector.getNode().getOriginQueryPoint();
        Statistics stats = this.getQueryPointStats(origin);
        if (this.logging.isTraceUsageCollection()) {
            System.out.println("... NodeUsageCollector " + usageCollector);
        }
        stats.collectUsageInfo(usageCollector);
        if (this.logging.isTraceUsageCollection()) {
            System.out.println("stats\n" + stats);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Statistics getQueryPointStats(ObjectGraphOrigin originQueryPoint) {
        String string = this.statisticsMonitor;
        synchronized (string) {
            Statistics stats = this.statisticsMap.get(originQueryPoint.getKey());
            if (stats == null) {
                stats = new Statistics(originQueryPoint, this.queryTuningAddVersion);
                this.statisticsMap.put(originQueryPoint.getKey(), stats);
            }
            return stats;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        String string = this.statisticsMonitor;
        synchronized (string) {
            return this.statisticsMap.values().toString();
        }
    }

    private static class Counters {
        int newPlan;
        int modified;
        int unchanged;
        int noUsage;

        private Counters() {
        }

        void incrementNoUsage() {
            ++this.noUsage;
        }

        void incrementNew() {
            ++this.newPlan;
        }

        void incrementModified() {
            ++this.modified;
        }

        void incrementUnchanged() {
            ++this.unchanged;
        }

        boolean isInteresting() {
            return this.newPlan > 0 || this.modified > 0;
        }

        public String toString() {
            return "new[" + this.newPlan + "] modified[" + this.modified + "] unchanged[" + this.unchanged + "] nousage[" + this.noUsage + "]";
        }
    }
}

