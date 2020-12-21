/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.common.ModifyCollection;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ModifySet<E>
extends ModifyCollection<E>
implements Set<E> {
    public ModifySet(BeanCollection<E> owner, Set<E> s) {
        super(owner, s);
    }
}

