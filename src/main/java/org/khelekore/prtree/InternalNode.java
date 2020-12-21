/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.List;
import java.util.PriorityQueue;
import org.khelekore.prtree.DistanceCalculator;
import org.khelekore.prtree.DistanceResult;
import org.khelekore.prtree.MBR;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.MinDist;
import org.khelekore.prtree.MinDistComparator;
import org.khelekore.prtree.Node;
import org.khelekore.prtree.NodeBase;
import org.khelekore.prtree.NodeFilter;

class InternalNode<T>
extends NodeBase<Node<T>, T> {
    public InternalNode(Object[] data) {
        super(data);
    }

    @Override
    public MBR computeMBR(MBRConverter<T> converter) {
        MBR ret = null;
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            ret = this.getUnion(ret, ((Node)this.get(i)).getMBR(converter));
        }
        return ret;
    }

    @Override
    public void expand(MBR mbr, MBRConverter<T> converter, List<T> found, List<Node<T>> nodesToExpand) {
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            Node n = (Node)this.get(i);
            if (!mbr.intersects(n.getMBR(converter))) continue;
            nodesToExpand.add(n);
        }
    }

    @Override
    public void find(MBR mbr, MBRConverter<T> converter, List<T> result) {
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            Node n = (Node)this.get(i);
            if (!mbr.intersects(n.getMBR(converter))) continue;
            n.find(mbr, converter, result);
        }
    }

    @Override
    public void nnExpand(DistanceCalculator<T> dc, NodeFilter<T> filter, List<DistanceResult<T>> drs, int maxHits, PriorityQueue<Node<T>> queue, MinDistComparator<T, Node<T>> mdc) {
        int s = this.size();
        for (int i = 0; i < s; ++i) {
            Node n = (Node)this.get(i);
            MBR mbr = n.getMBR(mdc.converter);
            double minDist = MinDist.get(mbr, mdc.p);
            int t = drs.size();
            if (t >= maxHits && !(minDist <= drs.get(t - 1).getDistance())) continue;
            queue.add(n);
        }
    }
}

