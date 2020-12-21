/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LoadedPropertiesCache {
    static ConcurrentHashMap<Integer, Set<String>> cache = new ConcurrentHashMap(250, 0.75f, 16);

    public static Set<String> get(int partialHash, Set<String> partialProps, BeanDescriptor<?> desc) {
        int manyHash = desc.getNamesOfManyPropsHash();
        int totalHash = 37 * partialHash + manyHash;
        Integer key = totalHash;
        Set<String> includedProps = cache.get(key);
        if (includedProps == null) {
            LinkedHashSet<String> mergeNames = new LinkedHashSet<String>();
            mergeNames.addAll(partialProps);
            if (manyHash != 0) {
                mergeNames.addAll(desc.getNamesOfManyProps());
            }
            includedProps = Collections.unmodifiableSet(mergeNames);
            cache.put(key, includedProps);
        }
        return includedProps;
    }
}

