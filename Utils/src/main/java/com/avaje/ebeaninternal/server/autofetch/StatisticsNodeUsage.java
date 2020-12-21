/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.NodeUsageCollector;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.SplitName;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class StatisticsNodeUsage
implements Serializable {
    private static final long serialVersionUID = -1663951463963779547L;
    private static final Logger logger = Logger.getLogger(StatisticsNodeUsage.class.getName());
    private final String monitor = new String();
    private final String path;
    private final boolean queryTuningAddVersion;
    private int profileCount;
    private int profileUsedCount;
    private boolean modified;
    private Set<String> aggregateUsed = new LinkedHashSet<String>();

    public StatisticsNodeUsage(String path, boolean queryTuningAddVersion) {
        this.path = path;
        this.queryTuningAddVersion = queryTuningAddVersion;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MetaAutoFetchStatistic.NodeUsageStats createPublicMeta() {
        String string = this.monitor;
        synchronized (string) {
            String[] usedProps = this.aggregateUsed.toArray(new String[this.aggregateUsed.size()]);
            return new MetaAutoFetchStatistic.NodeUsageStats(this.path, this.profileCount, this.profileUsedCount, usedProps);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void buildTunedFetch(PathProperties pathProps, BeanDescriptor<?> rootDesc) {
        String string = this.monitor;
        synchronized (string) {
            BeanProperty[] versionProps;
            BeanDescriptor<Object> desc = rootDesc;
            if (this.path != null) {
                ElPropertyValue elGetValue = rootDesc.getElGetValue(this.path);
                if (elGetValue == null) {
                    desc = null;
                    logger.warning("Autofetch: Can't find join for path[" + this.path + "] for " + rootDesc.getName());
                } else {
                    BeanProperty beanProperty = elGetValue.getBeanProperty();
                    if (beanProperty instanceof BeanPropertyAssoc) {
                        desc = ((BeanPropertyAssoc)beanProperty).getTargetDescriptor();
                    }
                }
            }
            for (String propName : this.aggregateUsed) {
                BeanProperty beanProp = desc.getBeanPropertyFromPath(propName);
                if (beanProp == null) {
                    logger.warning("Autofetch: Can't find property[" + propName + "] for " + desc.getName());
                    continue;
                }
                if (beanProp instanceof BeanPropertyAssoc) {
                    BeanPropertyAssoc assocProp = (BeanPropertyAssoc)beanProp;
                    String targetIdProp = assocProp.getTargetIdProperty();
                    String manyPath = SplitName.add(this.path, assocProp.getName());
                    pathProps.addToPath(manyPath, targetIdProp);
                    continue;
                }
                if (beanProp.isLob() && !beanProp.isFetchEager()) continue;
                pathProps.addToPath(this.path, beanProp.getName());
            }
            if ((this.modified || this.queryTuningAddVersion) && desc != null && (versionProps = desc.propertiesVersion()).length > 0) {
                pathProps.addToPath(this.path, versionProps[0].getName());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void publish(NodeUsageCollector profile) {
        String string = this.monitor;
        synchronized (string) {
            HashSet<String> used = profile.getUsed();
            ++this.profileCount;
            if (!used.isEmpty()) {
                ++this.profileUsedCount;
                this.aggregateUsed.addAll(used);
            }
            if (profile.isModified()) {
                this.modified = true;
            }
        }
    }

    public String toString() {
        return "path[" + this.path + "] profileCount[" + this.profileCount + "] used[" + this.profileUsedCount + "] props" + this.aggregateUsed;
    }
}

