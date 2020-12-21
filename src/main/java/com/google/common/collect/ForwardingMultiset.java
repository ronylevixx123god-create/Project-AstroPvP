/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public abstract class ForwardingMultiset<E>
extends ForwardingCollection<E>
implements Multiset<E> {
    protected ForwardingMultiset() {
    }

    @Override
    protected abstract Multiset<E> delegate();

    @Override
    public int count(Object element) {
        return this.delegate().count(element);
    }

    @Override
    public int add(E element, int occurrences) {
        return this.delegate().add(element, occurrences);
    }

    @Override
    public int remove(Object element, int occurrences) {
        return this.delegate().remove(element, occurrences);
    }

    @Override
    public Set<E> elementSet() {
        return this.delegate().elementSet();
    }

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || ((Object)this.delegate()).equals(object);
    }

    @Override
    public int hashCode() {
        return ((Object)this.delegate()).hashCode();
    }

    @Override
    public int setCount(E element, int count) {
        return this.delegate().setCount(element, count);
    }

    @Override
    public boolean setCount(E element, int oldCount, int newCount) {
        return this.delegate().setCount(element, oldCount, newCount);
    }

    @Override
    @Beta
    protected boolean standardContains(@Nullable Object object) {
        return this.count(object) > 0;
    }

    @Override
    @Beta
    protected void standardClear() {
        Iterator<Multiset.Entry<E>> entryIterator = this.entrySet().iterator();
        while (entryIterator.hasNext()) {
            entryIterator.next();
            entryIterator.remove();
        }
    }

    @Beta
    protected int standardCount(@Nullable Object object) {
        for (Multiset.Entry<E> entry : this.entrySet()) {
            if (!Objects.equal(entry.getElement(), object)) continue;
            return entry.getCount();
        }
        return 0;
    }

    @Beta
    protected boolean standardAdd(E element) {
        this.add(element, 1);
        return true;
    }

    @Override
    @Beta
    protected boolean standardAddAll(Collection<? extends E> elementsToAdd) {
        return Multisets.addAllImpl(this, elementsToAdd);
    }

    @Override
    @Beta
    protected boolean standardRemove(Object element) {
        return this.remove(element, 1) > 0;
    }

    @Override
    @Beta
    protected boolean standardRemoveAll(Collection<?> elementsToRemove) {
        return Multisets.removeAllImpl(this, elementsToRemove);
    }

    @Override
    @Beta
    protected boolean standardRetainAll(Collection<?> elementsToRetain) {
        return Multisets.retainAllImpl(this, elementsToRetain);
    }

    @Beta
    protected int standardSetCount(E element, int count) {
        return Multisets.setCountImpl(this, element, count);
    }

    @Beta
    protected boolean standardSetCount(E element, int oldCount, int newCount) {
        return Multisets.setCountImpl(this, element, oldCount, newCount);
    }

    @Deprecated
    @Beta
    protected Set<E> standardElementSet() {
        return new StandardElementSet();
    }

    @Beta
    protected Iterator<E> standardIterator() {
        return Multisets.iteratorImpl(this);
    }

    @Beta
    protected int standardSize() {
        return Multisets.sizeImpl(this);
    }

    @Beta
    protected boolean standardEquals(@Nullable Object object) {
        return Multisets.equalsImpl(this, object);
    }

    @Beta
    protected int standardHashCode() {
        return ((Object)this.entrySet()).hashCode();
    }

    @Override
    @Beta
    protected String standardToString() {
        return this.entrySet().toString();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Beta
    protected class StandardElementSet
    extends Multisets.ElementSet<E> {
        @Override
        Multiset<E> multiset() {
            return ForwardingMultiset.this;
        }
    }
}

