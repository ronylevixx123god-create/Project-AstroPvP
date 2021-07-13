package io.apice.clientcomms;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ExpiringLruSet<E>
extends AbstractSet<E>
implements Serializable {
    static final Object PRESENT = new Object();
    private final ConcurrentMap<E, Object> map;

    public ExpiringLruSet(long expireMillis) {
        LoadingCache<Object, Object> cache = CacheBuilder.newBuilder().expireAfterWrite(expireMillis, TimeUnit.MILLISECONDS).build(new CacheLoader<Object, Object>(){

            @Override
            public Object load(Object o) throws Exception {
                return PRESENT;
            }
        });
        this.map = (ConcurrentMap<E, Object>) cache.asMap();
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean add(E e) {
        return this.map.put(e, PRESENT) == null;
    }
}

