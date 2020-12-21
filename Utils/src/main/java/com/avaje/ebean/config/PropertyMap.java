/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.PropertyExpression;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class PropertyMap {
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, String> map = new LinkedHashMap();

    PropertyMap() {
    }

    public String toString() {
        return this.map.toString();
    }

    public void evaluateProperties() {
        for (Map.Entry<String, String> e : this.entrySet()) {
            String key = e.getKey();
            String val = e.getValue();
            String eval = this.eval(val);
            if (eval == null || eval.equals(val)) continue;
            this.put(key, eval);
        }
    }

    public synchronized String eval(String val) {
        return PropertyExpression.eval(val, this);
    }

    public synchronized boolean getBoolean(String key, boolean defaultValue) {
        String value = this.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    public synchronized int getInt(String key, int defaultValue) {
        String value = this.get(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public synchronized String get(String key, String defaultValue) {
        String value = this.map.get(key.toLowerCase());
        return value == null ? defaultValue : value;
    }

    public synchronized String get(String key) {
        return this.map.get(key.toLowerCase());
    }

    synchronized void putAll(Map<String, String> keyValueMap) {
        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    synchronized String putEval(String key, String value) {
        value = PropertyExpression.eval(value, this);
        return this.map.put(key.toLowerCase(), value);
    }

    synchronized String put(String key, String value) {
        return this.map.put(key.toLowerCase(), value);
    }

    synchronized String remove(String key) {
        return (String)this.map.remove(key.toLowerCase());
    }

    synchronized Set<Map.Entry<String, String>> entrySet() {
        return this.map.entrySet();
    }
}

