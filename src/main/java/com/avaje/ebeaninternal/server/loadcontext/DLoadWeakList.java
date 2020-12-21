/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.loadcontext;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DLoadWeakList<T> {
    protected final ArrayList<WeakReference<T>> list = new ArrayList();
    protected int removedFromTop;

    protected DLoadWeakList() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int add(T e) {
        DLoadWeakList dLoadWeakList = this;
        synchronized (dLoadWeakList) {
            int i = this.list.size();
            this.list.add(new WeakReference<T>(e));
            return i;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<T> getNextBatch(int batchSize) {
        DLoadWeakList dLoadWeakList = this;
        synchronized (dLoadWeakList) {
            return this.getBatch(0, batchSize);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<T> getLoadBatch(int position, int batchSize) {
        DLoadWeakList dLoadWeakList = this;
        synchronized (dLoadWeakList) {
            if (batchSize < 1) {
                throw new RuntimeException("batchSize " + batchSize + " < 1 ??!!");
            }
            int relativePos = position - this.removedFromTop;
            if (relativePos - batchSize < 0) {
                relativePos = 0;
            }
            if (relativePos > 0 && relativePos + batchSize > this.list.size() && (relativePos = this.list.size() - batchSize) < 0) {
                relativePos = 0;
            }
            return this.getBatch(relativePos, batchSize);
        }
    }

    private List<T> getBatch(int relativePos, int batchSize) {
        boolean removeFromTop;
        ArrayList<Object> batch = new ArrayList<Object>();
        int count = 0;
        boolean bl = removeFromTop = relativePos == 0;
        while (count < batchSize && !this.list.isEmpty()) {
            Object ebi;
            WeakReference<T> weakEntry;
            if (removeFromTop) {
                weakEntry = this.list.remove(relativePos);
                ++this.removedFromTop;
            } else {
                if (relativePos >= this.list.size()) break;
                weakEntry = this.list.get(relativePos);
                this.list.set(relativePos, null);
                ++relativePos;
            }
            if ((ebi = weakEntry == null ? null : (Object)weakEntry.get()) == null) continue;
            batch.add(ebi);
            ++count;
        }
        return batch;
    }
}

