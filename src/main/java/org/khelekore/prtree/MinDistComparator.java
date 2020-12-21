/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.Comparator;
import org.khelekore.prtree.MBR;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.MinDist;
import org.khelekore.prtree.Node;
import org.khelekore.prtree.PointND;

class MinDistComparator<T, S extends Node<T>>
implements Comparator<S> {
    public final MBRConverter<T> converter;
    public final PointND p;

    public MinDistComparator(MBRConverter<T> converter, PointND p) {
        this.converter = converter;
        this.p = p;
    }

    @Override
    public int compare(S t1, S t2) {
        MBR mbr1 = t1.getMBR(this.converter);
        MBR mbr2 = t2.getMBR(this.converter);
        return Double.compare(MinDist.get(mbr1, this.p), MinDist.get(mbr2, this.p));
    }
}

