/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.common.AbstractBeanCollection;
import com.avaje.ebean.common.ModifyIterator;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanSet<E>
extends AbstractBeanCollection<E>
implements Set<E>,
BeanCollectionAdd {
    private Set<E> set;

    public BeanSet(Set<E> set) {
        this.set = set;
    }

    public BeanSet() {
        this(new LinkedHashSet());
    }

    public BeanSet(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
        super(loader, ownerBean, propertyName);
    }

    Object readResolve() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.set;
        }
        return this;
    }

    Object writeReplace() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.set;
        }
        return this;
    }

    @Override
    public void addBean(Object bean) {
        this.set.add(bean);
    }

    @Override
    public void internalAdd(Object bean) {
        this.set.add(bean);
    }

    @Override
    public boolean isPopulated() {
        return this.set != null;
    }

    @Override
    public boolean isReference() {
        return this.set == null;
    }

    @Override
    public boolean checkEmptyLazyLoad() {
        if (this.set == null) {
            this.set = new LinkedHashSet();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initClear() {
        BeanSet beanSet = this;
        synchronized (beanSet) {
            if (this.set == null) {
                if (this.modifyListening) {
                    this.lazyLoadCollection(true);
                } else {
                    this.set = new LinkedHashSet();
                }
            }
            this.touched();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        BeanSet beanSet = this;
        synchronized (beanSet) {
            if (this.set == null) {
                this.lazyLoadCollection(true);
            }
            this.touched();
        }
    }

    public void setActualSet(Set<?> set) {
        this.set = set;
    }

    public Set<E> getActualSet() {
        return this.set;
    }

    @Override
    public Collection<E> getActualDetails() {
        return this.set;
    }

    @Override
    public Object getActualCollection() {
        return this.set;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BeanSet ");
        if (this.isSharedInstance()) {
            sb.append("sharedInstance ");
        } else if (this.isReadOnly()) {
            sb.append("readOnly ");
        }
        if (this.set == null) {
            sb.append("deferred ");
        } else {
            sb.append("size[").append(this.set.size()).append("]");
            sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
            sb.append(" set").append(this.set);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        this.init();
        return ((Object)this.set).equals(obj);
    }

    @Override
    public int hashCode() {
        this.init();
        return ((Object)this.set).hashCode();
    }

    @Override
    public boolean add(E o) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            if (this.set.add(o)) {
                this.modifyAddition(o);
                return true;
            }
            return false;
        }
        return this.set.add(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyAddListening) {
            boolean changed = false;
            for (E o : c) {
                if (!this.set.add(o)) continue;
                this.modifyAddition(o);
                changed = true;
            }
            return changed;
        }
        return this.set.addAll(c);
    }

    @Override
    public void clear() {
        this.checkReadOnly();
        this.initClear();
        if (this.modifyRemoveListening) {
            for (E e : this.set) {
                this.modifyRemoval(e);
            }
        }
        this.set.clear();
    }

    @Override
    public boolean contains(Object o) {
        this.init();
        return this.set.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        this.init();
        return this.set.containsAll(c);
    }

    @Override
    public boolean isEmpty() {
        this.init();
        return this.set.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        this.init();
        if (this.isReadOnly()) {
            return new ReadOnlyIterator<E>(this.set.iterator());
        }
        if (this.modifyListening) {
            return new ModifyIterator<E>(this, this.set.iterator());
        }
        return this.set.iterator();
    }

    @Override
    public boolean remove(Object o) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            if (this.set.remove(o)) {
                this.modifyRemoval(o);
                return true;
            }
            return false;
        }
        return this.set.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            for (Object o : c) {
                if (!this.set.remove(o)) continue;
                this.modifyRemoval(o);
                changed = true;
            }
            return changed;
        }
        return this.set.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            boolean changed = false;
            Iterator<E> it = this.set.iterator();
            while (it.hasNext()) {
                E o = it.next();
                if (c.contains(o)) continue;
                it.remove();
                this.modifyRemoval(o);
                changed = true;
            }
            return changed;
        }
        return this.set.retainAll(c);
    }

    @Override
    public int size() {
        this.init();
        return this.set.size();
    }

    @Override
    public Object[] toArray() {
        this.init();
        return this.set.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        this.init();
        return this.set.toArray(a);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class ReadOnlyIterator<E>
    implements Iterator<E>,
    Serializable {
        private static final long serialVersionUID = 2577697326745352605L;
        private final Iterator<E> it;

        ReadOnlyIterator(Iterator<E> it) {
            this.it = it;
        }

        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public E next() {
            return this.it.next();
        }

        @Override
        public void remove() {
            throw new IllegalStateException("This collection is in ReadOnly mode");
        }
    }
}

