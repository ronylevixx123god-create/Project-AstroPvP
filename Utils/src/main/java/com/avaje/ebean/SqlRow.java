/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SqlRow
extends Serializable,
Map<String, Object> {
    public Iterator<String> keys();

    @Override
    public Object remove(Object var1);

    @Override
    public Object get(Object var1);

    @Override
    public Object put(String var1, Object var2);

    public Object set(String var1, Object var2);

    public Boolean getBoolean(String var1);

    public UUID getUUID(String var1);

    public Integer getInteger(String var1);

    public BigDecimal getBigDecimal(String var1);

    public Long getLong(String var1);

    public Double getDouble(String var1);

    public Float getFloat(String var1);

    public String getString(String var1);

    public Date getUtilDate(String var1);

    public java.sql.Date getDate(String var1);

    public Timestamp getTimestamp(String var1);

    public String toString();

    @Override
    public void clear();

    @Override
    public boolean containsKey(Object var1);

    @Override
    public boolean containsValue(Object var1);

    @Override
    public Set<Map.Entry<String, Object>> entrySet();

    @Override
    public boolean isEmpty();

    @Override
    public Set<String> keySet();

    @Override
    public void putAll(Map<? extends String, ? extends Object> var1);

    @Override
    public int size();

    @Override
    public Collection<Object> values();
}

