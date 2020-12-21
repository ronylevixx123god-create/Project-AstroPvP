/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.khelekore.prtree.Circle;
import org.khelekore.prtree.NodeComparators;
import org.khelekore.prtree.NodeFactory;
import org.khelekore.prtree.NodeUsage;

class LeafBuilder {
    private final int dimensions;
    private final int branchFactor;

    public LeafBuilder(int dimensions, int branchFactor) {
        this.dimensions = dimensions;
        this.branchFactor = branchFactor;
    }

    public <T, N> void buildLeafs(Collection<? extends T> ls, NodeComparators<T> comparators, NodeFactory<N> nf, List<N> leafNodes) {
        int i;
        ArrayList<NodeUsage<T>> nodes = new ArrayList<NodeUsage<T>>(ls.size());
        for (T t : ls) {
            nodes.add(new NodeUsage<T>(t, 1));
        }
        Circle<Noder<T, N>> getters = new Circle<Noder<T, N>>(this.dimensions * 2);
        for (i = 0; i < this.dimensions; ++i) {
            this.addGetterAndSplitter(nodes, comparators.getMinComparator(i), getters);
        }
        for (i = 0; i < this.dimensions; ++i) {
            this.addGetterAndSplitter(nodes, comparators.getMaxComparator(i), getters);
        }
        this.getLeafs(1, ls.size(), getters, nf, leafNodes);
    }

    private <T, N> void addGetterAndSplitter(List<NodeUsage<T>> nodes, Comparator<T> tcomp, Circle<Noder<T, N>> getters) {
        NodeUsageComparator<T> comp = new NodeUsageComparator<T>(tcomp);
        Collections.sort(nodes, comp);
        ArrayList<NodeUsage<T>> sortedNodes = new ArrayList<NodeUsage<T>>(nodes);
        getters.add(new Noder(sortedNodes));
    }

    private <T, N> void getLeafs(int id, int totalNumberOfElements, Circle<Noder<T, N>> getters, NodeFactory<N> nf, List<N> leafNodes) {
        ArrayList<Partition> partitionsToExpand = new ArrayList<Partition>();
        int[] pos = new int[2 * this.dimensions];
        partitionsToExpand.add(new Partition(id, totalNumberOfElements, pos));
        while (!partitionsToExpand.isEmpty()) {
            int nodesToGet;
            Partition p = (Partition)partitionsToExpand.remove(0);
            getters.reset();
            for (int i = 0; i < getters.getNumElements() && (nodesToGet = Math.min(p.numElementsLeft, this.branchFactor)) != 0; ++i) {
                Noder<T, N> noder = getters.getNext();
                leafNodes.add(((Noder)noder).getNextNode(p, i, nodesToGet, nf));
                p.numElementsLeft -= nodesToGet;
            }
            if (p.numElementsLeft <= 0) continue;
            int splitPos = this.getSplitPos(p.id) % getters.getNumElements();
            Noder<T, N> s = getters.get(splitPos);
            ((Noder)s).split(p, splitPos, p.numElementsLeft, p.id, 2 * p.id, 2 * p.id + 1, partitionsToExpand);
        }
    }

    private int getSplitPos(int n) {
        int splitPos = 0;
        while (n >= 2) {
            n >>= 1;
            ++splitPos;
        }
        return splitPos;
    }

    private static class Partition {
        private final int id;
        private int numElementsLeft;
        private int[] currentPositions;

        public Partition(int id, int numElementsLeft, int[] currentPositions) {
            this.id = id;
            this.numElementsLeft = numElementsLeft;
            this.currentPositions = currentPositions;
        }

        public String toString() {
            return this.getClass().getSimpleName() + "{id: " + this.id + ", numElementsLeft: " + this.numElementsLeft + ", currentPositions: " + Arrays.toString(this.currentPositions) + "}";
        }
    }

    private static class Noder<T, N> {
        private final List<NodeUsage<T>> data;

        private Noder(List<NodeUsage<T>> data) {
            this.data = data;
        }

        private N getNextNode(Partition p, int gi, int maxObjects, NodeFactory<N> nf) {
            int i;
            Object[] nodeData = new Object[maxObjects];
            int s = this.data.size();
            for (i = 0; i < maxObjects; ++i) {
                while (p.currentPositions[gi] < s && this.isUsedNode(p, p.currentPositions[gi])) {
                    int[] arrn = p.currentPositions;
                    int n = gi;
                    arrn[n] = arrn[n] + 1;
                }
                NodeUsage nu = this.data.set(p.currentPositions[gi], null);
                nodeData[i] = nu.getData();
                nu.use();
            }
            for (i = 0; i < nodeData.length; ++i) {
                if (nodeData[i] != null) continue;
                for (int j = 0; j < this.data.size(); ++j) {
                    System.err.println(j + ": " + this.data.get(j));
                }
                throw new NullPointerException("Null data found at: " + i);
            }
            return nf.create(nodeData);
        }

        private boolean isUsedNode(Partition p, int pos) {
            NodeUsage<T> nu = this.data.get(pos);
            return nu == null || nu.isUsed() || nu.getOwner() != p.id;
        }

        private void split(Partition p, int gi, int nodesToMark, int fromId, int toId1, int toId2, List<Partition> partitionsToExpand) {
            int sizePart2 = nodesToMark / 2;
            int sizePart1 = nodesToMark - sizePart2;
            int startPos = p.currentPositions[gi];
            int startPos2 = this.markPart(sizePart1, fromId, toId1, startPos);
            this.markPart(sizePart2, fromId, toId2, startPos2);
            partitionsToExpand.add(0, new Partition(toId1, sizePart1, p.currentPositions));
            int[] pos = (int[])p.currentPositions.clone();
            pos[gi] = startPos2;
            partitionsToExpand.add(1, new Partition(toId2, sizePart2, pos));
        }

        private int markPart(int numToMark, int fromId, int toId, int startPos) {
            while (numToMark > 0) {
                NodeUsage<T> nu;
                while ((nu = this.data.get(startPos)) == null || nu.getOwner() != fromId) {
                    ++startPos;
                }
                nu.changeOwner(toId);
                --numToMark;
            }
            return startPos;
        }
    }

    private static class NodeUsageComparator<T>
    implements Comparator<NodeUsage<T>> {
        private Comparator<T> sorter;

        public NodeUsageComparator(Comparator<T> sorter) {
            this.sorter = sorter;
        }

        @Override
        public int compare(NodeUsage<T> n1, NodeUsage<T> n2) {
            return this.sorter.compare(n1.getData(), n2.getData());
        }
    }
}

