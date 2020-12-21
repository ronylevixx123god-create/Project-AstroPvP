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
import org.khelekore.prtree.MinDistComparator;
import org.khelekore.prtree.NodeFilter;

interface Node<T> {
    public int size();

    public MBR getMBR(MBRConverter<T> var1);

    public void expand(MBR var1, MBRConverter<T> var2, List<T> var3, List<Node<T>> var4);

    public void find(MBR var1, MBRConverter<T> var2, List<T> var3);

    public void nnExpand(DistanceCalculator<T> var1, NodeFilter<T> var2, List<DistanceResult<T>> var3, int var4, PriorityQueue<Node<T>> var5, MinDistComparator<T, Node<T>> var6);
}

