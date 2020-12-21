/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.NodeUsageCollector;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.autofetch.StatisticsNodeUsage;
import com.avaje.ebeaninternal.server.autofetch.StatisticsQuery;
import com.avaje.ebeaninternal.server.autofetch.TunedQueryInfo;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Statistics
implements Serializable {
    private static final long serialVersionUID = -5586783791097230766L;
    private final ObjectGraphOrigin origin;
    private final boolean queryTuningAddVersion;
    private int counter;
    private Map<String, StatisticsQuery> queryStatsMap = new LinkedHashMap<String, StatisticsQuery>();
    private Map<String, StatisticsNodeUsage> nodeUsageMap = new LinkedHashMap<String, StatisticsNodeUsage>();
    private final String monitor = new String();

    public Statistics(ObjectGraphOrigin origin, boolean queryTuningAddVersion) {
        this.origin = origin;
        this.queryTuningAddVersion = queryTuningAddVersion;
    }

    public ObjectGraphOrigin getOrigin() {
        return this.origin;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TunedQueryInfo createTunedFetch(OrmQueryDetail newFetchDetail) {
        String string = this.monitor;
        synchronized (string) {
            return new TunedQueryInfo(this.origin, newFetchDetail, this.counter);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MetaAutoFetchStatistic createPublicMeta() {
        String string = this.monitor;
        synchronized (string) {
            StatisticsQuery[] sourceQueryStats = this.queryStatsMap.values().toArray(new StatisticsQuery[this.queryStatsMap.size()]);
            ArrayList<MetaAutoFetchStatistic.QueryStats> destQueryStats = new ArrayList<MetaAutoFetchStatistic.QueryStats>(sourceQueryStats.length);
            for (int i = 0; i < sourceQueryStats.length; ++i) {
                destQueryStats.add(sourceQueryStats[i].createPublicMeta());
            }
            StatisticsNodeUsage[] sourceNodeUsage = this.nodeUsageMap.values().toArray(new StatisticsNodeUsage[this.nodeUsageMap.size()]);
            ArrayList<MetaAutoFetchStatistic.NodeUsageStats> destNodeUsage = new ArrayList<MetaAutoFetchStatistic.NodeUsageStats>(sourceNodeUsage.length);
            for (int i = 0; i < sourceNodeUsage.length; ++i) {
                destNodeUsage.add(sourceNodeUsage[i].createPublicMeta());
            }
            return new MetaAutoFetchStatistic(this.origin, this.counter, destQueryStats, destNodeUsage);
        }
    }

    public int getCounter() {
        return this.counter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean hasUsage() {
        String string = this.monitor;
        synchronized (string) {
            return !this.nodeUsageMap.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public OrmQueryDetail buildTunedFetch(BeanDescriptor<?> rootDesc) {
        String string = this.monitor;
        synchronized (string) {
            if (this.nodeUsageMap.isEmpty()) {
                return null;
            }
            PathProperties pathProps = new PathProperties();
            for (StatisticsNodeUsage statsNode : this.nodeUsageMap.values()) {
                statsNode.buildTunedFetch(pathProps, rootDesc);
            }
            OrmQueryDetail detail = new OrmQueryDetail();
            Collection<PathProperties.Props> pathProperties = pathProps.getPathProps();
            for (PathProperties.Props props : pathProperties) {
                if (props.isEmpty()) continue;
                detail.addFetch(props.getPath(), props.getPropertiesAsString(), null);
            }
            detail.sortFetchPaths(rootDesc);
            return detail;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void collectQueryInfo(ObjectGraphNode node, int beansLoaded, int micros) {
        String string = this.monitor;
        synchronized (string) {
            StatisticsQuery stats;
            String key = node.getPath();
            if (key == null) {
                key = "";
                ++this.counter;
            }
            if ((stats = this.queryStatsMap.get(key)) == null) {
                stats = new StatisticsQuery(key);
                this.queryStatsMap.put(key, stats);
            }
            stats.add(beansLoaded, micros);
        }
    }

    public void collectUsageInfo(NodeUsageCollector profile) {
        if (!profile.isEmpty()) {
            ObjectGraphNode node = profile.getNode();
            StatisticsNodeUsage nodeStats = this.getNodeStats(node.getPath());
            nodeStats.publish(profile);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private StatisticsNodeUsage getNodeStats(String path) {
        String string = this.monitor;
        synchronized (string) {
            StatisticsNodeUsage nodeStats = this.nodeUsageMap.get(path);
            if (nodeStats == null) {
                nodeStats = new StatisticsNodeUsage(path, this.queryTuningAddVersion);
                this.nodeUsageMap.put(path, nodeStats);
            }
            return nodeStats;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getUsageDebug() {
        String string = this.monitor;
        synchronized (string) {
            StringBuilder sb = new StringBuilder();
            sb.append("root[").append(this.origin.getBeanType()).append("] ");
            for (StatisticsNodeUsage node : this.nodeUsageMap.values()) {
                sb.append(node.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getQueryStatDebug() {
        String string = this.monitor;
        synchronized (string) {
            StringBuilder sb = new StringBuilder();
            for (StatisticsQuery queryStat : this.queryStatsMap.values()) {
                sb.append(queryStat.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        String string = this.monitor;
        synchronized (string) {
            return this.getUsageDebug();
        }
    }
}

