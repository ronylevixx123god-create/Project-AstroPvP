/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ModifyIterator<E>
implements Iterator<E> {
    private final BeanCollection<E> owner;
    private final Iterator<E> it;
    private E last;

    ModifyIterator(BeanCollection<E> owner, Iterator<E> it) {
        this.owner = owner;
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return this.it.hasNext();
    }

    @Override
    public E next() {
        this.last = this.it.next();
        return this.last;
    }

    @Override
    public void remove() {
        this.owner.modifyRemoval(this.last);
        this.it.remove();
    }
}

