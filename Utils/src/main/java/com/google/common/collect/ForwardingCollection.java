/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public abstract class ForwardingCollection<E>
extends ForwardingObject
implements Collection<E> {
    protected ForwardingCollection() {
    }

    @Override
    protected abstract Collection<E> delegate();

    @Override
    public Iterator<E> iterator() {
        return this.delegate().iterator();
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.delegate().removeAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate().contains(object);
    }

    @Override
    public boolean add(E element) {
        return this.delegate().add(element);
    }

    @Override
    public boolean remove(Object object) {
        return this.delegate().remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.delegate().containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return this.delegate().addAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.delegate().retainAll(collection);
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public Object[] toArray() {
        return this.delegate().toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return this.delegate().toArray(array);
    }

    @Beta
    protected boolean standardContains(@Nullable Object object) {
        return Iterators.contains(this.iterator(), object);
    }

    @Beta
    protected boolean standardContainsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (this.contains(o)) continue;
            return false;
        }
        return true;
    }

    @Beta
    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Iterators.addAll(this, collection.iterator());
    }

    @Beta
    protected boolean standardRemove(@Nullable Object object) {
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!Objects.equal(iterator.next(), object)) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    @Beta
    protected boolean standardRemoveAll(Collection<?> collection) {
        return Iterators.removeAll(this.iterator(), collection);
    }

    @Beta
    protected boolean standardRetainAll(Collection<?> collection) {
        return Iterators.retainAll(this.iterator(), collection);
    }

    @Beta
    protected void standardClear() {
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @Beta
    protected boolean standardIsEmpty() {
        return !this.iterator().hasNext();
    }

    @Beta
    protected String standardToString() {
        return Collections2.toStringImpl(this);
    }

    @Beta
    protected Object[] standardToArray() {
        Object[] newArray = new Object[this.size()];
        return this.toArray(newArray);
    }

    @Beta
    protected <T> T[] standardToArray(T[] array) {
        return ObjectArrays.toArrayImpl(this, array);
    }
}

