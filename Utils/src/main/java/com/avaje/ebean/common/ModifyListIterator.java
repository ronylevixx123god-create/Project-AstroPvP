/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import java.util.ListIterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ModifyListIterator<E>
implements ListIterator<E> {
    private final BeanCollection<E> owner;
    private final ListIterator<E> it;
    private E last;

    ModifyListIterator(BeanCollection<E> owner, ListIterator<E> it) {
        this.owner = owner;
        this.it = it;
    }

    @Override
    public void add(E bean) {
        this.owner.modifyAddition(bean);
        this.last = null;
        this.it.add(bean);
    }

    @Override
    public boolean hasNext() {
        return this.it.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return this.it.hasPrevious();
    }

    @Override
    public E next() {
        this.last = this.it.next();
        return this.last;
    }

    @Override
    public int nextIndex() {
        return this.it.nextIndex();
    }

    @Override
    public E previous() {
        this.last = this.it.previous();
        return this.last;
    }

    @Override
    public int previousIndex() {
        return this.it.previousIndex();
    }

    @Override
    public void remove() {
        this.owner.modifyRemoval(this.last);
        this.last = null;
        this.it.remove();
    }

    @Override
    public void set(E o) {
        if (this.last != null) {
            this.owner.modifyRemoval(this.last);
            this.owner.modifyAddition(o);
        }
        this.it.set(o);
    }
}

