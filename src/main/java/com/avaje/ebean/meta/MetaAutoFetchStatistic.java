/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.meta;

import com.avaje.ebean.bean.ObjectGraphOrigin;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Entity
public class MetaAutoFetchStatistic
implements Serializable {
    private static final long serialVersionUID = -6640406753257176803L;
    @Id
    private String id;
    private ObjectGraphOrigin origin;
    private String beanType;
    private int counter;
    @Transient
    private List<QueryStats> queryStats;
    @Transient
    private List<NodeUsageStats> nodeUsageStats;

    public MetaAutoFetchStatistic() {
    }

    public MetaAutoFetchStatistic(ObjectGraphOrigin origin, int counter, List<QueryStats> queryStats, List<NodeUsageStats> nodeUsageStats) {
        this.origin = origin;
        this.beanType = origin == null ? null : origin.getBeanType();
        this.id = origin == null ? null : origin.getKey();
        this.counter = counter;
        this.queryStats = queryStats;
        this.nodeUsageStats = nodeUsageStats;
    }

    public String getId() {
        return this.id;
    }

    public String getBeanType() {
        return this.beanType;
    }

    public ObjectGraphOrigin getOrigin() {
        return this.origin;
    }

    public int getCounter() {
        return this.counter;
    }

    public List<QueryStats> getQueryStats() {
        return this.queryStats;
    }

    public List<NodeUsageStats> getNodeUsageStats() {
        return this.nodeUsageStats;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class NodeUsageStats
    implements Serializable {
        private static final long serialVersionUID = 1786787832374844739L;
        private final String path;
        private final int profileCount;
        private final int profileUsedCount;
        private final String[] usedProperties;

        public NodeUsageStats(String path, int profileCount, int profileUsedCount, String[] usedProperties) {
            this.path = path == null ? "" : path;
            this.profileCount = profileCount;
            this.profileUsedCount = profileUsedCount;
            this.usedProperties = usedProperties;
        }

        public String getPath() {
            return this.path;
        }

        public int getProfileCount() {
            return this.profileCount;
        }

        public int getProfileUsedCount() {
            return this.profileUsedCount;
        }

        public String[] getUsedProperties() {
            return this.usedProperties;
        }

        public Set<String> getUsedPropertiesSet() {
            LinkedHashSet<String> s = new LinkedHashSet<String>();
            for (int i = 0; i < this.usedProperties.length; ++i) {
                s.add(this.usedProperties[i]);
            }
            return s;
        }

        public String toString() {
            return "path[" + this.path + "] profileCount[" + this.profileCount + "] used[" + this.profileUsedCount + "] props" + Arrays.toString(this.usedProperties);
        }
    }

    public static class QueryStats
    implements Serializable {
        private static final long serialVersionUID = -5517935732867671387L;
        private final String path;
        private final int exeCount;
        private final int totalBeanLoaded;
        private final int totalMicros;

        public QueryStats(String path, int exeCount, int totalBeanLoaded, int totalMicros) {
            this.path = path;
            this.exeCount = exeCount;
            this.totalBeanLoaded = totalBeanLoaded;
            this.totalMicros = totalMicros;
        }

        public String getPath() {
            return this.path;
        }

        public int getExeCount() {
            return this.exeCount;
        }

        public int getTotalBeanLoaded() {
            return this.totalBeanLoaded;
        }

        public int getTotalMicros() {
            return this.totalMicros;
        }

        public String toString() {
            long avgMicros = this.exeCount == 0 ? 0L : (long)(this.totalMicros / this.exeCount);
            return "queryExe path[" + this.path + "] count[" + this.exeCount + "] totalBeansLoaded[" + this.totalBeanLoaded + "] avgMicros[" + avgMicros + "] totalMicros[" + this.totalMicros + "]";
        }
    }
}

