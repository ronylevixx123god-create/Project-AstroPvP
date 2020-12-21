/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.Comparator;

interface NodeComparators<T> {
    public Comparator<T> getMinComparator(int var1);

    public Comparator<T> getMaxComparator(int var1);
}

