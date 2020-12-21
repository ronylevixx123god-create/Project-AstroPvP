/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.common.ModifyIterator;
import java.util.Collection;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ModifyCollection<E>
implements Collection<E> {
    protected final BeanCollection<E> owner;
    protected final Collection<E> c;

    public ModifyCollection(BeanCollection<E> owner, Collection<E> c) {
        this.owner = owner;
        this.c = c;
    }

    @Override
    public boolean add(E o) {
        if (this.c.add(o)) {
            this.owner.modifyAddition(o);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean changed = false;
        for (E o : collection) {
            if (!this.c.add(o)) continue;
            this.owner.modifyAddition(o);
            changed = true;
        }
        return changed;
    }

    @Override
    public void clear() {
        this.c.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.c.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.c.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = this.c.iterator();
        return new ModifyIterator<E>(this.owner, it);
    }

    @Override
    public boolean remove(Object o) {
        if (this.c.remove(o)) {
            this.owner.modifyRemoval(o);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed = false;
        for (Object o : collection) {
            if (!this.c.remove(o)) continue;
            this.owner.modifyRemoval(o);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean changed = false;
        Iterator<E> it = this.c.iterator();
        while (it.hasNext()) {
            E o = it.next();
            if (collection.contains(o)) continue;
            it.remove();
            this.owner.modifyRemoval(o);
            changed = true;
        }
        return changed;
    }

    @Override
    public int size() {
        return this.c.size();
    }

    @Override
    public Object[] toArray() {
        return this.c.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.c.toArray(a);
    }
}

