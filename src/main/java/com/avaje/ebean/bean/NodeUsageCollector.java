/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.bean;

import com.avaje.ebean.bean.NodeUsageListener;
import com.avaje.ebean.bean.ObjectGraphNode;
import java.lang.ref.WeakReference;
import java.util.HashSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class NodeUsageCollector {
    private final ObjectGraphNode node;
    private final WeakReference<NodeUsageListener> managerRef;
    private final HashSet<String> used = new HashSet();
    private boolean modified;
    private String loadProperty;

    public NodeUsageCollector(ObjectGraphNode node, WeakReference<NodeUsageListener> managerRef) {
        this.node = node;
        this.managerRef = managerRef;
    }

    public void setModified() {
        this.modified = true;
    }

    public void addUsed(String property) {
        this.used.add(property);
    }

    public void setLoadProperty(String loadProperty) {
        this.loadProperty = loadProperty;
    }

    private void publishUsageInfo() {
        NodeUsageListener manager = (NodeUsageListener)this.managerRef.get();
        if (manager != null) {
            manager.collectNodeUsage(this);
        }
    }

    protected void finalize() throws Throwable {
        this.publishUsageInfo();
        super.finalize();
    }

    public ObjectGraphNode getNode() {
        return this.node;
    }

    public boolean isEmpty() {
        return this.used.isEmpty();
    }

    public HashSet<String> getUsed() {
        return this.used;
    }

    public boolean isModified() {
        return this.modified;
    }

    public String getLoadProperty() {
        return this.loadProperty;
    }

    public String toString() {
        return this.node + " read:" + this.used + " modified:" + this.modified;
    }
}

