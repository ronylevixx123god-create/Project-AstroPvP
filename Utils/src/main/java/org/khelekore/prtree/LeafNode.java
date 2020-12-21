/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.khelekore.prtree.DistanceCalculator;
import org.khelekore.prtree.DistanceResult;
import org.khelekore.prtree.MBR;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.MinDistComparator;
import org.khelekore.prtree.Node;
import org.khelekore.prtree.NodeBase;
import org.khelekore.prtree.NodeFilter;
import org.khelekore.prtree.SimpleMBR;

class LeafNode<T>
extends NodeBase<T, T> {
    private static final Comparator<DistanceResult<?>> comp = new Comparator<DistanceResult<?>>(){

        @Override
        public int compare(DistanceResult<?> d1, DistanceResult<?> d2) {
            return Double.compare(d1.getDistance(), d2.getDistance());
        }
    };

    public LeafNode(Object[] data) {
        super(data);
    }

    public MBR getMBR(T t, MBRConverter<T> converter) {
        return new SimpleMBR(t, converter);
    }

    @Override
    public MBR computeMBR(MBRConverter<T> converter) {
        MBR ret = null;
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            ret = this.getUnion(ret, this.getMBR(this.get(i), converter));
        }
        return ret;
    }

    @Override
    public void expand(MBR mbr, MBRConverter<T> converter, List<T> found, List<Node<T>> nodesToExpand) {
        this.find(mbr, converter, found);
    }

    @Override
    public void find(MBR mbr, MBRConverter<T> converter, List<T> result) {
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            Object t = this.get(i);
            if (!mbr.intersects(t, converter)) continue;
            result.add(t);
        }
    }

    @Override
    public void nnExpand(DistanceCalculator<T> dc, NodeFilter<T> filter, List<DistanceResult<T>> drs, int maxHits, PriorityQueue<Node<T>> queue, MinDistComparator<T, Node<T>> mdc) {
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            Object t = this.get(i);
            if (!filter.accept(t)) continue;
            double dist = dc.distanceTo(t, mdc.p);
            int n = drs.size();
            if (n >= maxHits && !(dist < drs.get(n - 1).getDistance())) continue;
            this.add(drs, new DistanceResult(t, dist), maxHits);
        }
    }

    private void add(List<DistanceResult<T>> drs, DistanceResult<T> dr, int maxHits) {
        int pos;
        int n = drs.size();
        if (n == maxHits) {
            drs.remove(n - 1);
        }
        if ((pos = Collections.binarySearch(drs, dr, comp)) < 0) {
            pos = -(pos + 1);
        }
        drs.add(pos, dr);
    }
}

