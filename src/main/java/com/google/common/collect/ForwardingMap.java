/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Supplier;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public abstract class ForwardingMap<K, V>
extends ForwardingObject
implements Map<K, V> {
    protected ForwardingMap() {
    }

    @Override
    protected abstract Map<K, V> delegate();

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public V remove(Object object) {
        return this.delegate().remove(object);
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegate().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegate().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return this.delegate().get(key);
    }

    @Override
    public V put(K key, V value) {
        return this.delegate().put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        this.delegate().putAll(map);
    }

    @Override
    public Set<K> keySet() {
        return this.delegate().keySet();
    }

    @Override
    public Collection<V> values() {
        return this.delegate().values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Beta
    protected void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.putAllImpl(this, map);
    }

    @Beta
    protected V standardRemove(@Nullable Object key) {
        Iterator<Map.Entry<K, V>> entryIterator = this.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<K, V> entry = entryIterator.next();
            if (!Objects.equal(entry.getKey(), key)) continue;
            V value = entry.getValue();
            entryIterator.remove();
            return value;
        }
        return null;
    }

    @Beta
    protected void standardClear() {
        Iterator<Map.Entry<K, V>> entryIterator = this.entrySet().iterator();
        while (entryIterator.hasNext()) {
            entryIterator.next();
            entryIterator.remove();
        }
    }

    @Deprecated
    @Beta
    protected Set<K> standardKeySet() {
        return new StandardKeySet();
    }

    @Beta
    protected boolean standardContainsKey(@Nullable Object key) {
        return Maps.containsKeyImpl(this, key);
    }

    @Deprecated
    @Beta
    protected Collection<V> standardValues() {
        return new StandardValues();
    }

    @Beta
    protected boolean standardContainsValue(@Nullable Object value) {
        return Maps.containsValueImpl(this, value);
    }

    @Deprecated
    @Beta
    protected Set<Map.Entry<K, V>> standardEntrySet(final Supplier<Iterator<Map.Entry<K, V>>> entryIteratorSupplier) {
        return new StandardEntrySet(){

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return (Iterator)entryIteratorSupplier.get();
            }
        };
    }

    @Beta
    protected boolean standardIsEmpty() {
        return !this.entrySet().iterator().hasNext();
    }

    @Beta
    protected boolean standardEquals(@Nullable Object object) {
        return Maps.equalsImpl(this, object);
    }

    @Beta
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    @Beta
    protected String standardToString() {
        return Maps.toStringImpl(this);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Beta
    protected abstract class StandardEntrySet
    extends Maps.EntrySet<K, V> {
        @Override
        Map<K, V> map() {
            return ForwardingMap.this;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Beta
    protected class StandardValues
    extends Maps.Values<K, V> {
        @Override
        Map<K, V> map() {
            return ForwardingMap.this;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Beta
    protected class StandardKeySet
    extends Maps.KeySet<K, V> {
        @Override
        Map<K, V> map() {
            return ForwardingMap.this;
        }
    }
}

