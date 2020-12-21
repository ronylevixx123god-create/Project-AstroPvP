/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Equivalence;
import com.google.common.base.PairwiseEquivalence;
import java.io.Serializable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
@GwtCompatible
public final class Equivalences {
    private Equivalences() {
    }

    public static Equivalence<Object> equals() {
        return Equals.INSTANCE;
    }

    public static Equivalence<Object> identity() {
        return Identity.INSTANCE;
    }

    @Deprecated
    @GwtCompatible(serializable=true)
    public static <T> Equivalence<Iterable<T>> pairwise(Equivalence<? super T> elementEquivalence) {
        return new PairwiseEquivalence<T>(elementEquivalence);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class Identity
    extends Equivalence<Object>
    implements Serializable {
        static final Identity INSTANCE = new Identity();
        private static final long serialVersionUID = 1L;

        private Identity() {
        }

        @Override
        protected boolean doEquivalent(Object a, Object b) {
            return false;
        }

        @Override
        protected int doHash(Object o) {
            return System.identityHashCode(o);
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class Equals
    extends Equivalence<Object>
    implements Serializable {
        static final Equals INSTANCE = new Equals();
        private static final long serialVersionUID = 1L;

        private Equals() {
        }

        @Override
        protected boolean doEquivalent(Object a, Object b) {
            return a.equals(b);
        }

        @Override
        public int doHash(Object o) {
            return o.hashCode();
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }
}

