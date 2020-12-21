/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Count;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import com.google.common.collect.SortedIterable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class TreeMultiset<E>
extends AbstractMapBasedMultiset<E>
implements SortedIterable<E> {
    private final Comparator<? super E> comparator;
    @GwtIncompatible(value="not needed in emulated source")
    private static final long serialVersionUID = 0L;

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset<E>();
    }

    public static <E> TreeMultiset<E> create(@Nullable Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset<E>() : new TreeMultiset<E>(comparator);
    }

    @Override
    public Iterator<E> iterator() {
        return super.iterator();
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> elements) {
        TreeMultiset<E> multiset = TreeMultiset.create();
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    private TreeMultiset() {
        this(Ordering.natural());
    }

    private TreeMultiset(@Nullable Comparator<? super E> comparator) {
        super(new TreeMap(Preconditions.checkNotNull(comparator)));
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public SortedSet<E> elementSet() {
        return (SortedSet)super.elementSet();
    }

    @Override
    public int count(@Nullable Object element) {
        try {
            return super.count(element);
        }
        catch (NullPointerException e) {
            return 0;
        }
        catch (ClassCastException e) {
            return 0;
        }
    }

    @Override
    public int add(E element, int occurrences) {
        if (element == null) {
            this.comparator.compare(element, element);
        }
        return super.add(element, occurrences);
    }

    @Override
    Set<E> createElementSet() {
        return new SortedMapBasedElementSet((SortedMap)this.backingMap());
    }

    @GwtIncompatible(value="java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.elementSet().comparator());
        Serialization.writeMultiset(this, stream);
    }

    @GwtIncompatible(value="java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        Comparator comparator = (Comparator)stream.readObject();
        this.setBackingMap(new TreeMap(comparator));
        Serialization.populateMultiset(this, stream);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class SortedMapBasedElementSet
    extends AbstractMapBasedMultiset.MapBasedElementSet
    implements SortedSet<E>,
    SortedIterable<E> {
        SortedMapBasedElementSet(SortedMap<E, Count> map) {
            super(map);
        }

        SortedMap<E, Count> sortedMap() {
            return (SortedMap)this.getMap();
        }

        @Override
        public Comparator<? super E> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        public E first() {
            return this.sortedMap().firstKey();
        }

        @Override
        public E last() {
            return this.sortedMap().lastKey();
        }

        @Override
        public SortedSet<E> headSet(E toElement) {
            return new SortedMapBasedElementSet(this.sortedMap().headMap(toElement));
        }

        @Override
        public SortedSet<E> subSet(E fromElement, E toElement) {
            return new SortedMapBasedElementSet(this.sortedMap().subMap(fromElement, toElement));
        }

        @Override
        public SortedSet<E> tailSet(E fromElement) {
            return new SortedMapBasedElementSet(this.sortedMap().tailMap(fromElement));
        }

        @Override
        public boolean remove(Object element) {
            try {
                return super.remove(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e) {
                return false;
            }
        }
    }
}

