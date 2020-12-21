/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import org.khelekore.prtree.MBR;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.Node;

abstract class NodeBase<N, T>
implements Node<T> {
    private MBR mbr;
    private Object[] data;

    public NodeBase(Object[] data) {
        this.data = data;
    }

    @Override
    public int size() {
        return this.data.length;
    }

    public N get(int i) {
        return (N)this.data[i];
    }

    @Override
    public MBR getMBR(MBRConverter<T> converter) {
        if (this.mbr == null) {
            this.mbr = this.computeMBR(converter);
        }
        return this.mbr;
    }

    public abstract MBR computeMBR(MBRConverter<T> var1);

    public MBR getUnion(MBR m1, MBR m2) {
        if (m1 == null) {
            return m2;
        }
        return m1.union(m2);
    }
}

