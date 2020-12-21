/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.Comparator;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.Node;
import org.khelekore.prtree.NodeComparators;

class InternalNodeComparators<T>
implements NodeComparators<Node<T>> {
    private final MBRConverter<T> converter;

    public InternalNodeComparators(MBRConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public Comparator<Node<T>> getMinComparator(final int axis) {
        return new Comparator<Node<T>>(){

            @Override
            public int compare(Node<T> n1, Node<T> n2) {
                double d1 = n1.getMBR(InternalNodeComparators.this.converter).getMin(axis);
                double d2 = n2.getMBR(InternalNodeComparators.this.converter).getMin(axis);
                return Double.compare(d1, d2);
            }
        };
    }

    @Override
    public Comparator<Node<T>> getMaxComparator(final int axis) {
        return new Comparator<Node<T>>(){

            @Override
            public int compare(Node<T> n1, Node<T> n2) {
                double d1 = n1.getMBR(InternalNodeComparators.this.converter).getMax(axis);
                double d2 = n2.getMBR(InternalNodeComparators.this.converter).getMax(axis);
                return Double.compare(d1, d2);
            }
        };
    }
}

