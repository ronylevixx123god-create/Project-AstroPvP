/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.CustomConcurrentHashMap;
import com.google.common.collect.MapMaker;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
@GwtCompatible(emulated=true)
public abstract class GenericMapMaker<K0, V0> {
    @GwtIncompatible(value="To be supported")
    MapMaker.RemovalListener<K0, V0> removalListener;

    GenericMapMaker() {
    }

    @GwtIncompatible(value="To be supported")
    abstract GenericMapMaker<K0, V0> keyEquivalence(Equivalence<Object> var1);

    @GwtIncompatible(value="To be supported")
    abstract GenericMapMaker<K0, V0> valueEquivalence(Equivalence<Object> var1);

    public abstract GenericMapMaker<K0, V0> initialCapacity(int var1);

    @Deprecated
    @Beta
    public abstract GenericMapMaker<K0, V0> maximumSize(int var1);

    abstract GenericMapMaker<K0, V0> strongKeys();

    public abstract GenericMapMaker<K0, V0> concurrencyLevel(int var1);

    @GwtIncompatible(value="java.lang.ref.WeakReference")
    public abstract GenericMapMaker<K0, V0> weakKeys();

    abstract GenericMapMaker<K0, V0> strongValues();

    @Deprecated
    @GwtIncompatible(value="java.lang.ref.SoftReference")
    public abstract GenericMapMaker<K0, V0> softKeys();

    @GwtIncompatible(value="java.lang.ref.WeakReference")
    public abstract GenericMapMaker<K0, V0> weakValues();

    @GwtIncompatible(value="java.lang.ref.SoftReference")
    public abstract GenericMapMaker<K0, V0> softValues();

    @Deprecated
    public abstract GenericMapMaker<K0, V0> expiration(long var1, TimeUnit var3);

    @Deprecated
    public abstract GenericMapMaker<K0, V0> expireAfterWrite(long var1, TimeUnit var3);

    @Deprecated
    @GwtIncompatible(value="To be supported")
    public abstract GenericMapMaker<K0, V0> expireAfterAccess(long var1, TimeUnit var3);

    @GwtIncompatible(value="To be supported")
    <K extends K0, V extends V0> MapMaker.RemovalListener<K, V> getRemovalListener() {
        return Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
    }

    public abstract <K extends K0, V extends V0> ConcurrentMap<K, V> makeMap();

    @GwtIncompatible(value="CustomConcurrentHashMap")
    abstract <K, V> CustomConcurrentHashMap<K, V> makeCustomMap();

    @Deprecated
    public abstract <K extends K0, V extends V0> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> var1);

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @GwtIncompatible(value="To be supported")
    static enum NullListener implements MapMaker.RemovalListener<Object, Object>
    {
        INSTANCE;


        @Override
        public void onRemoval(MapMaker.RemovalNotification<Object, Object> notification) {
        }
    }
}

