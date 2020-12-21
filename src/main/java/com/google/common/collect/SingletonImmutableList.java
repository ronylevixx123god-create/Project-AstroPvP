/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class SingletonImmutableList<E>
extends ImmutableList<E> {
    final transient E element;

    SingletonImmutableList(E element) {
        this.element = Preconditions.checkNotNull(element);
    }

    @Override
    public E get(int index) {
        Preconditions.checkElementIndex(index, 1);
        return this.element;
    }

    @Override
    public int indexOf(@Nullable Object object) {
        return this.element.equals(object) ? 0 : -1;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override
    public int lastIndexOf(@Nullable Object object) {
        return this.element.equals(object) ? 0 : -1;
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(final int start) {
        Preconditions.checkPositionIndex(start, 1);
        return new UnmodifiableListIterator<E>(){
            boolean hasNext;
            {
                this.hasNext = start == 0;
            }

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public boolean hasPrevious() {
                return !this.hasNext;
            }

            @Override
            public E next() {
                if (!this.hasNext) {
                    throw new NoSuchElementException();
                }
                this.hasNext = false;
                return SingletonImmutableList.this.element;
            }

            @Override
            public int nextIndex() {
                return this.hasNext ? 0 : 1;
            }

            @Override
            public E previous() {
                if (this.hasNext) {
                    throw new NoSuchElementException();
                }
                this.hasNext = true;
                return SingletonImmutableList.this.element;
            }

            @Override
            public int previousIndex() {
                return this.hasNext ? -1 : 0;
            }
        };
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, 1);
        return fromIndex == toIndex ? ImmutableList.of() : this;
    }

    @Override
    public ImmutableList<E> reverse() {
        return this;
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.element.equals(object);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof List) {
            List that = (List)object;
            return that.size() == 1 && this.element.equals(that.get(0));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 + this.element.hashCode();
    }

    @Override
    public String toString() {
        String elementToString = this.element.toString();
        return new StringBuilder(elementToString.length() + 2).append('[').append(elementToString).append(']').toString();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{this.element};
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length == 0) {
            array = ObjectArrays.newArray(array, 1);
        } else if (array.length > 1) {
            array[1] = null;
        }
        T[] objectArray = array;
        objectArray[0] = this.element;
        return array;
    }
}

