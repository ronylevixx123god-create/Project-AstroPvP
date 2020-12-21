/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ModifyHolder<E>
implements Serializable {
    private static final long serialVersionUID = 2572572897923801083L;
    private Set<E> modifyDeletions = new LinkedHashSet();
    private Set<E> modifyAdditions = new LinkedHashSet();

    ModifyHolder() {
    }

    void reset() {
        this.modifyDeletions = new LinkedHashSet();
        this.modifyAdditions = new LinkedHashSet();
    }

    void modifyAdditionAll(Collection<? extends E> c) {
        if (c != null) {
            this.modifyAdditions.addAll(c);
        }
    }

    void modifyAddition(E bean) {
        if (bean != null) {
            this.modifyAdditions.add(bean);
        }
    }

    void modifyRemoval(Object bean) {
        if (bean != null) {
            this.modifyDeletions.add(bean);
        }
    }

    Set<E> getModifyAdditions() {
        return this.modifyAdditions;
    }

    Set<E> getModifyRemovals() {
        return this.modifyDeletions;
    }
}

