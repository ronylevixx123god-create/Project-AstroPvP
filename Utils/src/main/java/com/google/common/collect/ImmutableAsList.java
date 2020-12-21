/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.RegularImmutableList;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class ImmutableAsList<E>
extends RegularImmutableList<E> {
    private final transient ImmutableCollection<E> collection;

    ImmutableAsList(Object[] array, ImmutableCollection<E> collection) {
        super(array, 0, array.length);
        this.collection = collection;
    }

    @Override
    public boolean contains(Object target) {
        return this.collection.contains(target);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.collection);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class SerializedForm
    implements Serializable {
        final ImmutableCollection<?> collection;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableCollection<?> collection) {
            this.collection = collection;
        }

        Object readResolve() {
            return this.collection.asList();
        }
    }
}

