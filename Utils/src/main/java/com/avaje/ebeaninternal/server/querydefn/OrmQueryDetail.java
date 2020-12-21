/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.querydefn;

import com.avaje.ebean.FetchConfig;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class OrmQueryDetail
implements Serializable {
    private static final long serialVersionUID = -2510486880141461807L;
    private OrmQueryProperties baseProps = new OrmQueryProperties();
    private LinkedHashMap<String, OrmQueryProperties> fetchPaths = new LinkedHashMap(8);
    private LinkedHashSet<String> includes = new LinkedHashSet(8);

    public OrmQueryDetail copy() {
        OrmQueryDetail copy = new OrmQueryDetail();
        copy.baseProps = this.baseProps.copy();
        for (Map.Entry<String, OrmQueryProperties> entry : this.fetchPaths.entrySet()) {
            copy.fetchPaths.put(entry.getKey(), entry.getValue().copy());
        }
        copy.includes = new LinkedHashSet<String>(this.includes);
        return copy;
    }

    public int queryPlanHash(BeanQueryRequest<?> request) {
        int hc;
        int n = hc = this.baseProps == null ? 1 : this.baseProps.queryPlanHash(request);
        if (this.fetchPaths != null) {
            for (OrmQueryProperties p : this.fetchPaths.values()) {
                hc = hc * 31 + p.queryPlanHash(request);
            }
        }
        return hc;
    }

    public boolean isAutoFetchEqual(OrmQueryDetail otherDetail) {
        return this.autofetchPlanHash() == otherDetail.autofetchPlanHash();
    }

    private int autofetchPlanHash() {
        int hc;
        int n = hc = this.baseProps == null ? 1 : this.baseProps.autofetchPlanHash();
        if (this.fetchPaths != null) {
            for (OrmQueryProperties p : this.fetchPaths.values()) {
                hc = hc * 31 + p.autofetchPlanHash();
            }
        }
        return hc;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.baseProps != null) {
            sb.append("select ").append(this.baseProps);
        }
        if (this.fetchPaths != null) {
            for (OrmQueryProperties join : this.fetchPaths.values()) {
                sb.append(" fetch ").append(join);
            }
        }
        return sb.toString();
    }

    public int hashCode() {
        throw new RuntimeException("should not use");
    }

    public void select(String columns) {
        this.baseProps = new OrmQueryProperties(null, columns);
    }

    public boolean containsProperty(String property) {
        if (this.baseProps == null) {
            return true;
        }
        return this.baseProps.isIncluded(property);
    }

    public void setBase(OrmQueryProperties baseProps) {
        this.baseProps = baseProps;
    }

    public List<OrmQueryProperties> removeSecondaryQueries() {
        return this.removeSecondaryQueries(false);
    }

    public List<OrmQueryProperties> removeSecondaryLazyQueries() {
        return this.removeSecondaryQueries(true);
    }

    private List<OrmQueryProperties> removeSecondaryQueries(boolean lazyQuery) {
        int i;
        ArrayList<String> matchingPaths = new ArrayList<String>(2);
        for (OrmQueryProperties chunk : this.fetchPaths.values()) {
            boolean match = lazyQuery ? chunk.isLazyFetch() : chunk.isQueryFetch();
            if (!match) continue;
            matchingPaths.add(chunk.getPath());
        }
        if (matchingPaths.size() == 0) {
            return null;
        }
        Collections.sort(matchingPaths);
        ArrayList<OrmQueryProperties> props = new ArrayList<OrmQueryProperties>(2);
        for (i = 0; i < matchingPaths.size(); ++i) {
            String path = (String)matchingPaths.get(i);
            this.includes.remove(path);
            OrmQueryProperties secQuery = (OrmQueryProperties)this.fetchPaths.remove(path);
            if (secQuery == null) continue;
            props.add(secQuery);
            Iterator<OrmQueryProperties> pass2It = this.fetchPaths.values().iterator();
            while (pass2It.hasNext()) {
                OrmQueryProperties pass2Prop = pass2It.next();
                if (!secQuery.isChild(pass2Prop)) continue;
                pass2It.remove();
                this.includes.remove(pass2Prop.getPath());
                secQuery.add(pass2Prop);
            }
        }
        for (i = 0; i < props.size(); ++i) {
            String path = props.get(i).getPath();
            String[] split = SplitName.split(path);
            OrmQueryProperties chunk = this.getChunk(split[0], true);
            chunk.addSecondaryQueryJoin(split[1]);
        }
        return props;
    }

    public boolean tuneFetchProperties(OrmQueryDetail tunedDetail) {
        boolean tuned = false;
        OrmQueryProperties tunedRoot = tunedDetail.getChunk(null, false);
        if (tunedRoot != null && tunedRoot.hasProperties()) {
            tuned = true;
            this.baseProps.setTunedProperties(tunedRoot);
            for (OrmQueryProperties tunedChunk : tunedDetail.fetchPaths.values()) {
                OrmQueryProperties chunk = this.getChunk(tunedChunk.getPath(), false);
                if (chunk != null) {
                    chunk.setTunedProperties(tunedChunk);
                    continue;
                }
                this.putFetchPath(tunedChunk.copy());
            }
        }
        return tuned;
    }

    public void putFetchPath(OrmQueryProperties chunk) {
        String path = chunk.getPath();
        this.fetchPaths.put(path, chunk);
        this.includes.add(path);
    }

    public void clear() {
        this.includes.clear();
        this.fetchPaths.clear();
    }

    public OrmQueryProperties addFetch(String path, String partialProps, FetchConfig fetchConfig) {
        OrmQueryProperties chunk = this.getChunk(path, true);
        chunk.setProperties(partialProps);
        chunk.setFetchConfig(fetchConfig);
        return chunk;
    }

    public void sortFetchPaths(BeanDescriptor<?> d) {
        LinkedHashMap<String, OrmQueryProperties> sorted = new LinkedHashMap<String, OrmQueryProperties>(this.fetchPaths.size());
        for (OrmQueryProperties p : this.fetchPaths.values()) {
            this.sortFetchPaths(d, p, sorted);
        }
        this.fetchPaths = sorted;
    }

    private void sortFetchPaths(BeanDescriptor<?> d, OrmQueryProperties p, LinkedHashMap<String, OrmQueryProperties> sorted) {
        String path = p.getPath();
        if (!sorted.containsKey(path)) {
            String parentPath = p.getParentPath();
            if (parentPath == null || sorted.containsKey(parentPath)) {
                sorted.put(path, p);
            } else {
                OrmQueryProperties parentProp = this.fetchPaths.get(parentPath);
                if (parentProp == null) {
                    ElPropertyValue el = d.getElGetValue(parentPath);
                    if (el == null) {
                        String msg = "Path [" + parentPath + "] not valid from " + d.getFullName();
                        throw new PersistenceException(msg);
                    }
                    BeanPropertyAssoc assocOne = (BeanPropertyAssoc)el.getBeanProperty();
                    parentProp = new OrmQueryProperties(parentPath, assocOne.getTargetIdProperty());
                }
                if (parentProp != null) {
                    this.sortFetchPaths(d, parentProp, sorted);
                }
                sorted.put(path, p);
            }
        }
    }

    public void convertManyFetchJoinsToQueryJoins(BeanDescriptor<?> beanDescriptor, String lazyLoadManyPath, boolean allowOne, int queryBatch) {
        ArrayList<OrmQueryProperties> manyChunks = new ArrayList<OrmQueryProperties>(3);
        String manyFetchProperty = null;
        boolean fetchJoinFirstMany = allowOne;
        this.sortFetchPaths(beanDescriptor);
        for (String fetchPath : this.fetchPaths.keySet()) {
            OrmQueryProperties chunk;
            ElPropertyDeploy elProp = beanDescriptor.getElPropertyDeploy(fetchPath);
            if (!elProp.containsManySince(manyFetchProperty) || !(chunk = this.fetchPaths.get(fetchPath)).isFetchJoin() || this.isLazyLoadManyRoot(lazyLoadManyPath, chunk) || this.hasParentSecJoin(lazyLoadManyPath, chunk)) continue;
            if (fetchJoinFirstMany) {
                fetchJoinFirstMany = false;
                manyFetchProperty = fetchPath;
                continue;
            }
            manyChunks.add(chunk);
        }
        for (int i = 0; i < manyChunks.size(); ++i) {
            ((OrmQueryProperties)manyChunks.get(i)).setQueryFetch(queryBatch, true);
        }
    }

    private boolean isLazyLoadManyRoot(String lazyLoadManyPath, OrmQueryProperties chunk) {
        return lazyLoadManyPath != null && lazyLoadManyPath.equals(chunk.getPath());
    }

    private boolean hasParentSecJoin(String lazyLoadManyPath, OrmQueryProperties chunk) {
        OrmQueryProperties parent = this.getParent(chunk);
        if (parent == null) {
            return false;
        }
        if (lazyLoadManyPath != null && lazyLoadManyPath.equals(parent.getPath())) {
            return false;
        }
        if (!parent.isFetchJoin()) {
            return true;
        }
        return this.hasParentSecJoin(lazyLoadManyPath, parent);
    }

    private OrmQueryProperties getParent(OrmQueryProperties chunk) {
        String parentPath = chunk.getParentPath();
        return parentPath == null ? null : this.fetchPaths.get(parentPath);
    }

    public void setDefaultSelectClause(BeanDescriptor<?> desc) {
        if (desc.hasDefaultSelectClause() && !this.hasSelectClause()) {
            if (this.baseProps == null) {
                this.baseProps = new OrmQueryProperties();
            }
            this.baseProps.setDefaultProperties(desc.getDefaultSelectClause(), desc.getDefaultSelectClauseSet());
        }
        for (OrmQueryProperties joinProps : this.fetchPaths.values()) {
            BeanDescriptor<?> assocDesc;
            if (joinProps.hasSelectClause() || !(assocDesc = desc.getBeanDescriptor(joinProps.getPath())).hasDefaultSelectClause()) continue;
            joinProps.setDefaultProperties(assocDesc.getDefaultSelectClause(), assocDesc.getDefaultSelectClauseSet());
        }
    }

    public boolean hasSelectClause() {
        return this.baseProps != null && this.baseProps.hasSelectClause();
    }

    public boolean isEmpty() {
        return this.fetchPaths.isEmpty() && (this.baseProps == null || !this.baseProps.hasProperties());
    }

    public boolean isJoinsEmpty() {
        return this.fetchPaths.isEmpty();
    }

    public void includeBeanJoin(String parentPath, String propertyName) {
        OrmQueryProperties parentChunk = this.getChunk(parentPath, true);
        parentChunk.includeBeanJoin(propertyName);
    }

    public OrmQueryProperties getChunk(String path, boolean create) {
        if (path == null) {
            return this.baseProps;
        }
        OrmQueryProperties props = this.fetchPaths.get(path);
        if (create && props == null) {
            props = new OrmQueryProperties(path);
            this.putFetchPath(props);
            return props;
        }
        return props;
    }

    public boolean includes(String path) {
        OrmQueryProperties chunk = this.fetchPaths.get(path);
        return chunk != null && !chunk.isCache();
    }

    public HashSet<String> getIncludes() {
        return this.includes;
    }
}

