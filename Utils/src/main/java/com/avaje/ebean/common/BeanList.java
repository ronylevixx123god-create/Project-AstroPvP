/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.common.AbstractBeanCollection;
import com.avaje.ebean.common.ModifyIterator;
import com.avaje.ebean.common.ModifyList;
import com.avaje.ebean.common.ModifyListIterator;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanList<E>
extends AbstractBeanCollection<E>
implements List<E>,
BeanCollectionAdd {
    private List<E> list;

    public BeanList(List<E> list) {
        this.list = list;
    }

    public BeanList() {
        this(new ArrayList());
    }

    public BeanList(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
        super(loader, ownerBean, propertyName);
    }

    Object readResolve() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.list;
        }
        return this;
    }

    Object writeReplace() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.list;
        }
        return this;
    }

    @Override
    public void addBean(Object bean) {
        this.list.add(bean);
    }

    @Override
    public void internalAdd(Object bean) {
        this.list.add(bean);
    }

    @Override
    public boolean checkEmptyLazyLoad() {
        if (this.list == null) {
            this.list = new ArrayList();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initClear() {
        BeanList beanList = this;
        synchronized (beanList) {
            if (this.list == null) {
                if (this.modifyListening) {
                    this.lazyLoadCollection(true);
                } else {
                    this.list = new ArrayList();
                }
            }
            this.touched();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        BeanList beanList = this;
        synchronized (beanList) {
            if (this.list == null) {
                this.lazyLoadCollection(false);
            }
            this.touched();
        }
    }

    public void setActualList(List<?> list) {
        this.list = list;
    }

    public List<E> getActualList() {
        return this.list;
    }

    @Override
    public Collection<E> getActualDetails() {
        return this.list;
    }

    @Override
    public Object getActualCollection() {
        return this.list;
    }

    @Override
    public boolean isPopulated() {
        return this.list != null;
    }

    @Override
    public boolean isReference() {
        return this.list == null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BeanList ");
        if (this.isSharedInstance()) {
            sb.append("sharedInstance ");
        } else if (this.isReadOnly()) {
            sb.append("readOnly ");
        }
        if (this.list == null) {
            sb.append("deferred ");
        } else {
            sb.append("size[").append(this.list.size()).append("] ");
            sb.append("hasMoreRows[").append(this.hasMoreRows).append("] ");
            sb.append("list").append(this.list).append("");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        this.init();
        return ((Object)this.list).equals(obj);
    }

    @Override
    public int hashCode() {
        this.init();
        return ((Object)this.list).hashCode();
    }

    @Override
    public void add(int index, E element) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            this.modifyAddition(element);
        }
        this.list.add(index, element);
    }

    @Override
    public boolean add(E o) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            if (this.list.add(o)) {
                this.modifyAddition(o);
                return true;
            }
            return false;
        }
        return this.list.add(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            this.getModifyHolder().modifyAdditionAll(c);
        }
        return this.list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            this.getModifyHolder().modifyAdditionAll(c);
        }
        return this.list.addAll(index, c);
    }

    @Override
    public void clear() {
        this.checkReadOnly();
        this.initClear();
        if (this.modifyRemoveListening) {
            for (int i = 0; i < this.list.size(); ++i) {
                this.getModifyHolder().modifyRemoval(this.list.get(i));
            }
        }
        this.list.clear();
    }

    @Override
    public boolean contains(Object o) {
        this.init();
        return this.list.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        this.init();
        return this.list.containsAll(c);
    }

    @Override
    public E get(int index) {
        this.init();
        return this.list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        this.init();
        return this.list.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        this.init();
        return this.list.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyListIterator<E>(this.list.listIterator());
        }
        if (this.modifyListening) {
            Iterator<E> it = this.list.iterator();
            return new ModifyIterator<E>(this, it);
        }
        return this.list.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        this.init();
        return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyListIterator<E>(this.list.listIterator());
        }
        if (this.modifyListening) {
            ListIterator<E> it = this.list.listIterator();
            return new ModifyListIterator<E>(this, it);
        }
        return this.list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyListIterator<E>(this.list.listIterator(index));
        }
        if (this.modifyListening) {
            ListIterator<E> it = this.list.listIterator(index);
            return new ModifyListIterator<E>(this, it);
        }
        return this.list.listIterator(index);
    }

    @Override
    public E remove(int index) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            E o = this.list.remove(index);
            this.modifyRemoval(o);
            return o;
        }
        return this.list.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean isRemove = this.list.remove(o);
            if (isRemove) {
                this.modifyRemoval(o);
            }
            return isRemove;
        }
        return this.list.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            for (Object o : c) {
                if (!this.list.remove(o)) continue;
                this.modifyRemoval(o);
                changed = true;
            }
            return changed;
        }
        return this.list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            Iterator<E> it = this.list.iterator();
            while (it.hasNext()) {
                E o = it.next();
                if (c.contains(o)) continue;
                it.remove();
                this.modifyRemoval(o);
                changed = true;
            }
            return changed;
        }
        return this.list.retainAll(c);
    }

    @Override
    public E set(int index, E element) {
        this.checkReadOnly();
        this.init();
        if (this.modifyListening) {
            E o = this.list.set(index, element);
            this.modifyAddition(element);
            this.modifyRemoval(o);
            return o;
        }
        return this.list.set(index, element);
    }

    @Override
    public int size() {
        this.init();
        return this.list.size();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableList(this.list.subList(fromIndex, toIndex));
        }
        if (this.modifyListening) {
            return new ModifyList<E>(this, this.list.subList(fromIndex, toIndex));
        }
        return this.list.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        this.init();
        return this.list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        this.init();
        return this.list.toArray(a);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class ReadOnlyListIterator<E>
    implements ListIterator<E>,
    Serializable {
        private static final long serialVersionUID = 3097271091406323699L;
        private final ListIterator<E> i;

        ReadOnlyListIterator(ListIterator<E> i) {
            this.i = i;
        }

        @Override
        public void add(E o) {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }

        @Override
        public void remove() {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }

        @Override
        public void set(E o) {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }

        @Override
        public E next() {
            return this.i.next();
        }

        @Override
        public int nextIndex() {
            return this.i.nextIndex();
        }

        @Override
        public E previous() {
            return this.i.previous();
        }

        @Override
        public int previousIndex() {
            return this.i.previousIndex();
        }
    }
}

