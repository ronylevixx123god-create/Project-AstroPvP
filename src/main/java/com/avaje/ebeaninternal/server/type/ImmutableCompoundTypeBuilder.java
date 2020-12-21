/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.CtCompoundType;
import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ImmutableCompoundTypeBuilder {
    private static ThreadLocal<ImmutableCompoundTypeBuilder> local = new ThreadLocal<ImmutableCompoundTypeBuilder>(){

        @Override
        protected synchronized ImmutableCompoundTypeBuilder initialValue() {
            return new ImmutableCompoundTypeBuilder();
        }
    };
    private Map<Class<?>, Entry> entryMap = new HashMap();

    public static void clear() {
        ImmutableCompoundTypeBuilder.local.get().entryMap.clear();
    }

    public static Object set(CtCompoundType<?> ct, String propName, Object value) {
        return local.get().setValue(ct, propName, value);
    }

    private Object setValue(CtCompoundType<?> ct, String propName, Object value) {
        Entry e = this.getEntry(ct);
        Object compoundValue = e.set(propName, value);
        if (compoundValue != null) {
            this.removeEntry(ct);
        }
        return compoundValue;
    }

    private void removeEntry(CtCompoundType<?> ct) {
        this.entryMap.remove(ct.getCompoundTypeClass());
    }

    private Entry getEntry(CtCompoundType<?> ct) {
        Entry e = this.entryMap.get(ct.getCompoundTypeClass());
        if (e == null) {
            e = new Entry(ct);
            this.entryMap.put(ct.getCompoundTypeClass(), e);
        }
        return e;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Entry {
        private final CtCompoundType<?> ct;
        private final Map<String, Object> valueMap;

        private Entry(CtCompoundType<?> ct) {
            this.ct = ct;
            this.valueMap = new HashMap<String, Object>();
        }

        private Object set(String propName, Object value) {
            this.valueMap.put(propName, value);
            return this.ct.create(this.valueMap);
        }
    }
}

