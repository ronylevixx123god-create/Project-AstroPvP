/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class WeakValueMap<K, V> {
    protected final ReferenceQueue<V> refQueue = new ReferenceQueue();
    private final Map<K, WeakReferenceWithKey<K, V>> backing = new HashMap<K, WeakReferenceWithKey<K, V>>();

    private WeakReferenceWithKey<K, V> createReference(K key, V value) {
        return new WeakReferenceWithKey<K, V>(key, value, this.refQueue);
    }

    private void expunge() {
        Reference<V> ref;
        while ((ref = this.refQueue.poll()) != null) {
            this.backing.remove(((WeakReferenceWithKey)ref).getKey());
        }
    }

    public Object putIfAbsent(K key, V value) {
        Object existingValue;
        this.expunge();
        Reference ref = this.backing.get(key);
        if (ref != null && (existingValue = ref.get()) != null) {
            return existingValue;
        }
        this.backing.put(key, this.createReference(key, value));
        return null;
    }

    public void put(K key, V value) {
        this.expunge();
        this.backing.put(key, this.createReference(key, value));
    }

    public V get(K key) {
        this.expunge();
        Reference v = this.backing.get(key);
        return v == null ? null : (V)v.get();
    }

    public int size() {
        this.expunge();
        return this.backing.size();
    }

    public boolean isEmpty() {
        this.expunge();
        return this.backing.isEmpty();
    }

    public boolean containsKey(Object key) {
        this.expunge();
        return this.backing.containsKey(key);
    }

    public V remove(K key) {
        this.expunge();
        Reference v = this.backing.remove(key);
        return v == null ? null : (V)v.get();
    }

    public void clear() {
        this.expunge();
        this.backing.clear();
        this.expunge();
    }

    public String toString() {
        this.expunge();
        return this.backing.toString();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class WeakReferenceWithKey<K, V>
    extends WeakReference<V> {
        private final K key;

        public WeakReferenceWithKey(K key, V referent, ReferenceQueue<? super V> q) {
            super(referent, q);
            this.key = key;
        }

        public K getKey() {
            return this.key;
        }
    }
}

