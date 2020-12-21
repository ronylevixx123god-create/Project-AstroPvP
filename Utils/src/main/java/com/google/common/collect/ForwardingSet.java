/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public abstract class ForwardingSet<E>
extends ForwardingCollection<E>
implements Set<E> {
    protected ForwardingSet() {
    }

    @Override
    protected abstract Set<E> delegate();

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || ((Object)this.delegate()).equals(object);
    }

    @Override
    public int hashCode() {
        return ((Object)this.delegate()).hashCode();
    }

    @Beta
    protected boolean standardEquals(@Nullable Object object) {
        return Sets.equalsImpl(this, object);
    }

    @Beta
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this);
    }
}

