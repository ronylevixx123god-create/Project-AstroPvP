/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebeaninternal.server.lib.sql.PooledConnection;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class FreeConnectionBuffer {
    private PooledConnection[] conns;
    private int removeIndex;
    private int addIndex;
    private int size;

    protected FreeConnectionBuffer(int capacity) {
        this.conns = new PooledConnection[capacity];
    }

    protected int getCapacity() {
        return this.conns.length;
    }

    protected int size() {
        return this.size;
    }

    protected boolean isEmpty() {
        return this.size == 0;
    }

    protected void add(PooledConnection pc) {
        this.conns[this.addIndex] = pc;
        this.addIndex = this.inc(this.addIndex);
        ++this.size;
    }

    protected PooledConnection remove() {
        PooledConnection[] items = this.conns;
        PooledConnection pc = items[this.removeIndex];
        items[this.removeIndex] = null;
        this.removeIndex = this.inc(this.removeIndex);
        --this.size;
        return pc;
    }

    protected List<PooledConnection> getShallowCopy() {
        ArrayList<PooledConnection> copy = new ArrayList<PooledConnection>(this.conns.length);
        for (int i = 0; i < this.conns.length; ++i) {
            if (this.conns[i] == null) continue;
            copy.add(this.conns[i]);
        }
        return copy;
    }

    protected void setShallowCopy(List<PooledConnection> copy) {
        int i;
        this.removeIndex = 0;
        this.addIndex = 0;
        this.size = 0;
        for (i = 0; i < this.conns.length; ++i) {
            this.conns[i] = null;
        }
        for (i = 0; i < copy.size(); ++i) {
            this.add(copy.get(i));
        }
    }

    protected void setCapacity(int newCapacity) {
        if (newCapacity > this.conns.length) {
            List<PooledConnection> copy = this.getShallowCopy();
            this.removeIndex = 0;
            this.addIndex = 0;
            this.size = 0;
            this.conns = new PooledConnection[newCapacity];
            for (int i = 0; i < copy.size(); ++i) {
                this.add(copy.get(i));
            }
        }
    }

    private final int inc(int i) {
        return ++i == this.conns.length ? 0 : i;
    }
}

