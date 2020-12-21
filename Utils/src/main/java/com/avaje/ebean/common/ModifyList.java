/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.common.ModifyCollection;
import com.avaje.ebean.common.ModifyListIterator;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ModifyList<E>
extends ModifyCollection<E>
implements List<E> {
    private final List<E> list;

    ModifyList(BeanCollection<E> owner, List<E> list) {
        super(owner, list);
        this.list = list;
    }

    @Override
    public void add(int index, E element) {
        this.list.add(index, element);
        this.owner.modifyAddition(element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> co) {
        if (this.list.addAll(index, co)) {
            for (E o : co) {
                this.owner.modifyAddition(o);
            }
            return true;
        }
        return false;
    }

    @Override
    public E get(int index) {
        return this.list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ModifyListIterator<E>(this.owner, this.list.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ModifyListIterator<E>(this.owner, this.list.listIterator(index));
    }

    @Override
    public E remove(int index) {
        E o = this.list.remove(index);
        this.owner.modifyRemoval(o);
        return o;
    }

    @Override
    public E set(int index, E element) {
        E o = this.list.set(index, element);
        this.owner.modifyAddition(element);
        this.owner.modifyRemoval(o);
        return o;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ModifyList<E>(this.owner, this.list.subList(fromIndex, toIndex));
    }
}

