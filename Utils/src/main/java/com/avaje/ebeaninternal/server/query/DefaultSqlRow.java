/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.SqlRow;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultSqlRow
implements SqlRow {
    static final long serialVersionUID = -3120927797041336242L;
    private final String dbTrueValue;
    Map<String, Object> map;

    public DefaultSqlRow(Map<String, Object> map, String dbTrueValue) {
        this.map = map;
        this.dbTrueValue = dbTrueValue;
    }

    public DefaultSqlRow(String dbTrueValue) {
        this.map = new LinkedHashMap<String, Object>();
        this.dbTrueValue = dbTrueValue;
    }

    public DefaultSqlRow(int initialCapacity, float loadFactor, String dbTrueValue) {
        this.map = new LinkedHashMap<String, Object>(initialCapacity, loadFactor);
        this.dbTrueValue = dbTrueValue;
    }

    @Override
    public Iterator<String> keys() {
        return this.map.keySet().iterator();
    }

    @Override
    public Object remove(Object name) {
        name = ((String)name).toLowerCase();
        return this.map.remove(name);
    }

    @Override
    public Object get(Object name) {
        name = ((String)name).toLowerCase();
        return this.map.get(name);
    }

    @Override
    public Object put(String name, Object value) {
        return this.setInternal(name, value);
    }

    @Override
    public Object set(String name, Object value) {
        return this.setInternal(name, value);
    }

    private Object setInternal(String name, Object newValue) {
        name = name.toLowerCase();
        return this.map.put(name, newValue);
    }

    @Override
    public UUID getUUID(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toUUID(val);
    }

    @Override
    public Boolean getBoolean(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toBoolean(val, this.dbTrueValue);
    }

    @Override
    public Integer getInteger(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toInteger(val);
    }

    @Override
    public BigDecimal getBigDecimal(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toBigDecimal(val);
    }

    @Override
    public Long getLong(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toLong(val);
    }

    @Override
    public Double getDouble(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toDouble(val);
    }

    @Override
    public Float getFloat(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toFloat(val);
    }

    @Override
    public String getString(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toString(val);
    }

    @Override
    public Date getUtilDate(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toUtilDate(val);
    }

    @Override
    public java.sql.Date getDate(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toDate(val);
    }

    @Override
    public Timestamp getTimestamp(String name) {
        Object val = this.get(name);
        return BasicTypeConverter.toTimestamp(val);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        key = ((String)key).toLowerCase();
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> t) {
        this.map.putAll(t);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection<Object> values() {
        return this.map.values();
    }
}

