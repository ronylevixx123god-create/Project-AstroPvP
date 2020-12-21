/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.khelekore.prtree.DataComparators;
import org.khelekore.prtree.DistanceCalculator;
import org.khelekore.prtree.DistanceResult;
import org.khelekore.prtree.InternalNode;
import org.khelekore.prtree.InternalNodeComparators;
import org.khelekore.prtree.LeafBuilder;
import org.khelekore.prtree.LeafNode;
import org.khelekore.prtree.MBR;
import org.khelekore.prtree.MBR2D;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.NearestNeighbour;
import org.khelekore.prtree.Node;
import org.khelekore.prtree.NodeFactory;
import org.khelekore.prtree.NodeFilter;
import org.khelekore.prtree.PointND;
import org.khelekore.prtree.SimpleMBR;
import org.khelekore.prtree.SimpleMBR2D;

public class PRTree<T> {
    private MBRConverter<T> converter;
    private int branchFactor;
    private Node<T> root;
    private int numLeafs;
    private int height;

    public PRTree(MBRConverter<T> converter, int branchFactor) {
        this.converter = converter;
        this.branchFactor = branchFactor;
    }

    public void load(Collection<? extends T> data) {
        if (this.root != null) {
            throw new IllegalStateException("Tree is already loaded");
        }
        this.numLeafs = data.size();
        LeafBuilder lb = new LeafBuilder(this.converter.getDimensions(), this.branchFactor);
        ArrayList leafNodes = new ArrayList(this.estimateSize(this.numLeafs));
        lb.buildLeafs(data, new DataComparators<T>(this.converter), new LeafNodeFactory(), leafNodes);
        this.height = 1;
        ArrayList nodes = leafNodes;
        while (nodes.size() > this.branchFactor) {
            ++this.height;
            ArrayList internalNodes = new ArrayList(this.estimateSize(nodes.size()));
            lb.buildLeafs(nodes, new InternalNodeComparators<T>(this.converter), new InternalNodeFactory(), internalNodes);
            nodes = internalNodes;
        }
        this.setRoot(nodes);
    }

    private int estimateSize(int dataSize) {
        return (int)(1.0 / (double)(this.branchFactor - 1) * (double)dataSize);
    }

    private <N extends Node<T>> void setRoot(List<N> nodes) {
        if (nodes.size() == 0) {
            this.root = new InternalNode(new Object[0]);
        } else if (nodes.size() == 1) {
            this.root = (Node)nodes.get(0);
        } else {
            ++this.height;
            this.root = new InternalNode(nodes.toArray());
        }
    }

    public MBR2D getMBR2D() {
        MBR mbr = this.getMBR();
        if (mbr == null) {
            return null;
        }
        return new SimpleMBR2D(mbr.getMin(0), mbr.getMin(1), mbr.getMax(0), mbr.getMax(1));
    }

    public MBR getMBR() {
        return this.root.getMBR(this.converter);
    }

    public int getNumberOfLeaves() {
        return this.numLeafs;
    }

    public boolean isEmpty() {
        return this.numLeafs == 0;
    }

    public int getHeight() {
        return this.height;
    }

    public void find(double xmin, double ymin, double xmax, double ymax, List<T> resultNodes) {
        this.find(new SimpleMBR(xmin, xmax, ymin, ymax), resultNodes);
    }

    public void find(MBR query, List<T> resultNodes) {
        this.validateRect(query);
        this.root.find(query, this.converter, resultNodes);
    }

    public Iterable<T> find(double xmin, double ymin, double xmax, double ymax) {
        return this.find(new SimpleMBR(xmin, xmax, ymin, ymax));
    }

    public Iterable<T> find(final MBR query) {
        this.validateRect(query);
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return new Finder(query);
            }
        };
    }

    private void validateRect(MBR query) {
        for (int i = 0; i < this.converter.getDimensions(); ++i) {
            double min;
            double max = query.getMax(i);
            if (!(max < (min = query.getMin(i)))) continue;
            throw new IllegalArgumentException("max: " + max + " < min: " + min + ", axis: " + i + ", query: " + query);
        }
    }

    public List<DistanceResult<T>> nearestNeighbour(DistanceCalculator<T> dc, NodeFilter<T> filter, int maxHits, PointND p) {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        NearestNeighbour<T> nn = new NearestNeighbour<T>(this.converter, filter, maxHits, this.root, dc, p);
        return nn.find();
    }

    private class Finder
    implements Iterator<T> {
        private MBR mbr;
        private List<T> ts = new ArrayList();
        private List<Node<T>> toVisit = new ArrayList();
        private T next;
        private int visitedNodes = 0;
        private int dataNodesVisited = 0;

        public Finder(MBR mbr) {
            this.mbr = mbr;
            this.toVisit.add(PRTree.this.root);
            this.findNext();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public T next() {
            Object toReturn = this.next;
            this.findNext();
            return toReturn;
        }

        private void findNext() {
            while (this.ts.isEmpty() && !this.toVisit.isEmpty()) {
                Node n = this.toVisit.remove(this.toVisit.size() - 1);
                ++this.visitedNodes;
                n.expand(this.mbr, PRTree.this.converter, this.ts, this.toVisit);
            }
            if (this.ts.isEmpty()) {
                this.next = null;
            } else {
                this.next = this.ts.remove(this.ts.size() - 1);
                ++this.dataNodesVisited;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

    private class InternalNodeFactory
    implements NodeFactory<InternalNode<T>> {
        private InternalNodeFactory() {
        }

        @Override
        public InternalNode<T> create(Object[] data) {
            return new InternalNode(data);
        }
    }

    private class LeafNodeFactory
    implements NodeFactory<LeafNode<T>> {
        private LeafNodeFactory() {
        }

        @Override
        public LeafNode<T> create(Object[] data) {
            return new LeafNode(data);
        }
    }
}

