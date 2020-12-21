/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class RegularImmutableMap<K, V>
extends ImmutableMap<K, V> {
    private final transient LinkedEntry<K, V>[] entries;
    private final transient LinkedEntry<K, V>[] table;
    private final transient int mask;
    private final transient int keySetHashCode;
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    private transient ImmutableSet<K> keySet;
    private transient ImmutableCollection<V> values;
    private static final long serialVersionUID = 0L;

    RegularImmutableMap(Map.Entry<?, ?> ... immutableEntries) {
        int size = immutableEntries.length;
        this.entries = this.createEntryArray(size);
        int tableSize = RegularImmutableMap.chooseTableSize(size);
        this.table = this.createEntryArray(tableSize);
        this.mask = tableSize - 1;
        int keySetHashCodeMutable = 0;
        for (int entryIndex = 0; entryIndex < size; ++entryIndex) {
            LinkedEntry<K, V> existing;
            Map.Entry<?, ?> entry = immutableEntries[entryIndex];
            Object key = entry.getKey();
            int keyHashCode = key.hashCode();
            keySetHashCodeMutable += keyHashCode;
            int tableIndex = Hashing.smear(keyHashCode) & this.mask;
            LinkedEntry<?, ?> linkedEntry = RegularImmutableMap.newLinkedEntry(key, entry.getValue(), existing);
            this.table[tableIndex] = linkedEntry;
            this.entries[entryIndex] = linkedEntry;
            for (existing = this.table[tableIndex]; existing != null; existing = existing.next()) {
                Preconditions.checkArgument(!key.equals(existing.getKey()), "duplicate key: %s", key);
            }
        }
        this.keySetHashCode = keySetHashCodeMutable;
    }

    private static int chooseTableSize(int size) {
        int tableSize = Integer.highestOneBit(size) << 1;
        Preconditions.checkArgument(tableSize > 0, "table too large: %s", size);
        return tableSize;
    }

    private LinkedEntry<K, V>[] createEntryArray(int size) {
        return new LinkedEntry[size];
    }

    private static <K, V> LinkedEntry<K, V> newLinkedEntry(K key, V value, @Nullable LinkedEntry<K, V> next) {
        return next == null ? new TerminalEntry<K, V>(key, value) : new NonTerminalEntry<K, V>(key, value, next);
    }

    @Override
    public V get(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int index = Hashing.smear(key.hashCode()) & this.mask;
        for (LinkedEntry<K, V> entry = this.table[index]; entry != null; entry = entry.next()) {
            Object candidateKey = entry.getKey();
            if (!key.equals(candidateKey)) continue;
            return entry.getValue();
        }
        return null;
    }

    @Override
    public int size() {
        return this.entries.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        if (value == null) {
            return false;
        }
        for (LinkedEntry<K, V> entry : this.entries) {
            if (!entry.getValue().equals(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
        return es == null ? (this.entrySet = new EntrySet(this)) : es;
    }

    @Override
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> ks = this.keySet;
        return ks == null ? (this.keySet = new KeySet(this)) : ks;
    }

    @Override
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> v = this.values;
        return v == null ? (this.values = new Values(this)) : v;
    }

    @Override
    public String toString() {
        StringBuilder result = Collections2.newStringBuilderForCollection(this.size()).append('{');
        Collections2.STANDARD_JOINER.appendTo(result, (Object[])this.entries);
        return result.append('}').toString();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Values<V>
    extends ImmutableCollection<V> {
        final RegularImmutableMap<?, V> map;

        Values(RegularImmutableMap<?, V> map) {
            this.map = map;
        }

        @Override
        public int size() {
            return ((RegularImmutableMap)this.map).entries.length;
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            return new AbstractIndexedListIterator<V>(((RegularImmutableMap)this.map).entries.length){

                @Override
                protected V get(int index) {
                    return Values.this.map.entries[index].getValue();
                }
            };
        }

        @Override
        public boolean contains(Object target) {
            return this.map.containsValue(target);
        }

        @Override
        boolean isPartialView() {
            return true;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class KeySet<K, V>
    extends ImmutableSet.TransformedImmutableSet<Map.Entry<K, V>, K> {
        final RegularImmutableMap<K, V> map;

        KeySet(RegularImmutableMap<K, V> map) {
            super(((RegularImmutableMap)map).entries, ((RegularImmutableMap)map).keySetHashCode);
            this.map = map;
        }

        @Override
        K transform(Map.Entry<K, V> element) {
            return element.getKey();
        }

        @Override
        public boolean contains(Object target) {
            return this.map.containsKey(target);
        }

        @Override
        boolean isPartialView() {
            return true;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class EntrySet<K, V>
    extends ImmutableSet.ArrayImmutableSet<Map.Entry<K, V>> {
        final transient RegularImmutableMap<K, V> map;

        EntrySet(RegularImmutableMap<K, V> map) {
            super(((RegularImmutableMap)map).entries);
            this.map = map;
        }

        @Override
        public boolean contains(Object target) {
            if (target instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)target;
                V mappedValue = this.map.get(entry.getKey());
                return mappedValue != null && mappedValue.equals(entry.getValue());
            }
            return false;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Immutable
    private static final class TerminalEntry<K, V>
    extends ImmutableEntry<K, V>
    implements LinkedEntry<K, V> {
        TerminalEntry(K key, V value) {
            super(key, value);
        }

        @Override
        @Nullable
        public LinkedEntry<K, V> next() {
            return null;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Immutable
    private static final class NonTerminalEntry<K, V>
    extends ImmutableEntry<K, V>
    implements LinkedEntry<K, V> {
        final LinkedEntry<K, V> next;

        NonTerminalEntry(K key, V value, LinkedEntry<K, V> next) {
            super(key, value);
            this.next = next;
        }

        @Override
        public LinkedEntry<K, V> next() {
            return this.next;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static interface LinkedEntry<K, V>
    extends Map.Entry<K, V> {
        @Nullable
        public LinkedEntry<K, V> next();
    }
}

