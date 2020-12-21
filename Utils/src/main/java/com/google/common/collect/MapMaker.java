/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.collect.ComputationException;
import com.google.common.collect.ComputingConcurrentHashMap;
import com.google.common.collect.CustomConcurrentHashMap;
import com.google.common.collect.GenericMapMaker;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.MapEvictionListener;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class MapMaker
extends GenericMapMaker<Object, Object> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    static final int UNSET_INT = -1;
    boolean useCustomMap;
    int initialCapacity = -1;
    int concurrencyLevel = -1;
    int maximumSize = -1;
    CustomConcurrentHashMap.Strength keyStrength;
    CustomConcurrentHashMap.Strength valueStrength;
    long expireAfterWriteNanos = -1L;
    long expireAfterAccessNanos = -1L;
    RemovalCause nullRemovalCause;
    Equivalence<Object> keyEquivalence;
    Equivalence<Object> valueEquivalence;
    Ticker ticker;

    private boolean useNullMap() {
        return this.nullRemovalCause == null;
    }

    @GwtIncompatible(value="To be supported")
    MapMaker keyEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    Equivalence<Object> getKeyEquivalence() {
        return Objects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
    }

    @GwtIncompatible(value="To be supported")
    MapMaker valueEquivalence(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", this.valueEquivalence);
        this.valueEquivalence = Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    Equivalence<Object> getValueEquivalence() {
        return Objects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
    }

    public MapMaker initialCapacity(int initialCapacity) {
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
        Preconditions.checkArgument(initialCapacity >= 0);
        this.initialCapacity = initialCapacity;
        return this;
    }

    int getInitialCapacity() {
        return this.initialCapacity == -1 ? 16 : this.initialCapacity;
    }

    @Deprecated
    @Beta
    public MapMaker maximumSize(int size) {
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkArgument(size >= 0, "maximum size must not be negative");
        this.maximumSize = size;
        this.useCustomMap = true;
        if (this.maximumSize == 0) {
            this.nullRemovalCause = RemovalCause.SIZE;
        }
        return this;
    }

    public MapMaker concurrencyLevel(int concurrencyLevel) {
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
        Preconditions.checkArgument(concurrencyLevel > 0);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }

    int getConcurrencyLevel() {
        return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
    }

    MapMaker strongKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
    }

    @GwtIncompatible(value="java.lang.ref.WeakReference")
    public MapMaker weakKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
    }

    @Deprecated
    @GwtIncompatible(value="java.lang.ref.SoftReference")
    public MapMaker softKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.SOFT);
    }

    MapMaker setKeyStrength(CustomConcurrentHashMap.Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", new Object[]{this.keyStrength});
        this.keyStrength = Preconditions.checkNotNull(strength);
        if (strength != CustomConcurrentHashMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    CustomConcurrentHashMap.Strength getKeyStrength() {
        return Objects.firstNonNull(this.keyStrength, CustomConcurrentHashMap.Strength.STRONG);
    }

    MapMaker strongValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
    }

    @GwtIncompatible(value="java.lang.ref.WeakReference")
    public MapMaker weakValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
    }

    @GwtIncompatible(value="java.lang.ref.SoftReference")
    public MapMaker softValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
    }

    MapMaker setValueStrength(CustomConcurrentHashMap.Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", new Object[]{this.valueStrength});
        this.valueStrength = Preconditions.checkNotNull(strength);
        if (strength != CustomConcurrentHashMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    CustomConcurrentHashMap.Strength getValueStrength() {
        return Objects.firstNonNull(this.valueStrength, CustomConcurrentHashMap.Strength.STRONG);
    }

    @Deprecated
    public MapMaker expiration(long duration, TimeUnit unit) {
        return this.expireAfterWrite(duration, unit);
    }

    @Deprecated
    public MapMaker expireAfterWrite(long duration, TimeUnit unit) {
        this.checkExpiration(duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        if (duration == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    private void checkExpiration(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", new Object[]{duration, unit});
    }

    long getExpireAfterWriteNanos() {
        return this.expireAfterWriteNanos == -1L ? 0L : this.expireAfterWriteNanos;
    }

    @Deprecated
    @GwtIncompatible(value="To be supported")
    public MapMaker expireAfterAccess(long duration, TimeUnit unit) {
        this.checkExpiration(duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        if (duration == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    long getExpireAfterAccessNanos() {
        return this.expireAfterAccessNanos == -1L ? 0L : this.expireAfterAccessNanos;
    }

    Ticker getTicker() {
        return Objects.firstNonNull(this.ticker, Ticker.systemTicker());
    }

    @GwtIncompatible(value="To be supported")
    <K, V> GenericMapMaker<K, V> removalListener(RemovalListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        MapMaker me = this;
        me.removalListener = Preconditions.checkNotNull(listener);
        this.useCustomMap = true;
        return me;
    }

    @Deprecated
    @Beta
    @GwtIncompatible(value="To be supported")
    public <K, V> GenericMapMaker<K, V> evictionListener(MapEvictionListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        MapMaker me = this;
        me.removalListener = new MapMakerRemovalListener(listener);
        this.useCustomMap = true;
        return me;
    }

    @Override
    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.useCustomMap) {
            return new ConcurrentHashMap(this.getInitialCapacity(), 0.75f, this.getConcurrencyLevel());
        }
        return this.nullRemovalCause == null ? new CustomConcurrentHashMap(this) : new NullConcurrentMap(this);
    }

    @Override
    @GwtIncompatible(value="CustomConcurrentHashMap")
    <K, V> CustomConcurrentHashMap<K, V> makeCustomMap() {
        return new CustomConcurrentHashMap(this);
    }

    @Override
    @Deprecated
    public <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {
        return this.useNullMap() ? new ComputingConcurrentHashMap.ComputingMapAdapter<K, V>(this, computingFunction) : new NullComputingConcurrentMap<K, V>(this, computingFunction);
    }

    public String toString() {
        Objects.ToStringHelper s = Objects.toStringHelper(this);
        if (this.initialCapacity != -1) {
            s.add("initialCapacity", this.initialCapacity);
        }
        if (this.concurrencyLevel != -1) {
            s.add("concurrencyLevel", this.concurrencyLevel);
        }
        if (this.maximumSize != -1) {
            s.add("maximumSize", this.maximumSize);
        }
        if (this.expireAfterWriteNanos != -1L) {
            s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
        }
        if (this.expireAfterAccessNanos != -1L) {
            s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
        }
        if (this.keyStrength != null) {
            s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
        }
        if (this.valueStrength != null) {
            s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
        }
        if (this.keyEquivalence != null) {
            s.addValue("keyEquivalence");
        }
        if (this.valueEquivalence != null) {
            s.addValue("valueEquivalence");
        }
        if (this.removalListener != null) {
            s.addValue("removalListener");
        }
        return s.toString();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static final class NullComputingConcurrentMap<K, V>
    extends NullConcurrentMap<K, V> {
        private static final long serialVersionUID = 0L;
        final Function<? super K, ? extends V> computingFunction;

        NullComputingConcurrentMap(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
            super(mapMaker);
            this.computingFunction = Preconditions.checkNotNull(computingFunction);
        }

        @Override
        public V get(Object k) {
            Object key = k;
            V value = this.compute(key);
            Preconditions.checkNotNull(value, this.computingFunction + " returned null for key " + key + ".");
            this.notifyRemoval(key, value);
            return value;
        }

        private V compute(K key) {
            Preconditions.checkNotNull(key);
            try {
                return this.computingFunction.apply(key);
            }
            catch (ComputationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw new ComputationException(t);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class NullConcurrentMap<K, V>
    extends AbstractMap<K, V>
    implements ConcurrentMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final RemovalListener<K, V> removalListener;
        private final RemovalCause removalCause;

        NullConcurrentMap(MapMaker mapMaker) {
            this.removalListener = mapMaker.getRemovalListener();
            this.removalCause = mapMaker.nullRemovalCause;
        }

        @Override
        public boolean containsKey(@Nullable Object key) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable Object value) {
            return false;
        }

        @Override
        public V get(@Nullable Object key) {
            return null;
        }

        void notifyRemoval(K key, V value) {
            RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, this.removalCause);
            this.removalListener.onRemoval(notification);
        }

        @Override
        public V put(K key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            this.notifyRemoval(key, value);
            return null;
        }

        @Override
        public V putIfAbsent(K key, V value) {
            return this.put(key, value);
        }

        @Override
        public V remove(@Nullable Object key) {
            return null;
        }

        @Override
        public boolean remove(@Nullable Object key, @Nullable Object value) {
            return false;
        }

        @Override
        public V replace(K key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            return null;
        }

        @Override
        public boolean replace(K key, @Nullable V oldValue, V newValue) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(newValue);
            return false;
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static enum RemovalCause {
        EXPLICIT{

            boolean wasEvicted() {
                return false;
            }
        }
        ,
        REPLACED{

            boolean wasEvicted() {
                return false;
            }
        }
        ,
        COLLECTED{

            boolean wasEvicted() {
                return true;
            }
        }
        ,
        EXPIRED{

            boolean wasEvicted() {
                return true;
            }
        }
        ,
        SIZE{

            boolean wasEvicted() {
                return true;
            }
        };


        abstract boolean wasEvicted();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static final class RemovalNotification<K, V>
    extends ImmutableEntry<K, V> {
        private static final long serialVersionUID = 0L;
        private final RemovalCause cause;

        RemovalNotification(@Nullable K key, @Nullable V value, RemovalCause cause) {
            super(key, value);
            this.cause = cause;
        }

        public RemovalCause getCause() {
            return this.cause;
        }

        public boolean wasEvicted() {
            return this.cause.wasEvicted();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static interface RemovalListener<K, V> {
        public void onRemoval(RemovalNotification<K, V> var1);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static final class MapMakerRemovalListener<K, V>
    implements RemovalListener<K, V>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final MapEvictionListener<K, V> listener;

        public MapMakerRemovalListener(MapEvictionListener<K, V> listener) {
            this.listener = Preconditions.checkNotNull(listener);
        }

        @Override
        public void onRemoval(RemovalNotification<K, V> notification) {
            if (notification.wasEvicted()) {
                this.listener.onEviction(notification.getKey(), notification.getValue());
            }
        }
    }
}

