/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LogListBlock {
    private LinkedHashMap<String, Object> items = new LinkedHashMap();
    private int maxKeyLength = 0;

    private void updateKey(String key) {
        if (key.length() > this.maxKeyLength) {
            this.maxKeyLength = key.length();
        }
    }

    public LogListBlock put(String key, String value) {
        this.updateKey(key);
        this.items.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, LogListBlock value) {
        this.updateKey(key);
        this.items.put(key, value);
        return this;
    }

    public LogListBlock put(String key, Object value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, String value, Object ... args) {
        this.put(key, String.format(value, args));
        return this;
    }

    public LogListBlock put(String key, int value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, byte value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, double value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, float value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, short value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, long value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock put(String key, boolean value) {
        this.put(key, String.valueOf(value));
        return this;
    }

    public LogListBlock putChild(String key) {
        this.updateKey(key);
        LogListBlock block = new LogListBlock();
        this.items.put(key, block);
        return block;
    }

    private String padKey(String key, int len) {
        return String.format("%-" + len + "s", key);
    }

    protected String getOutput(String prefix) {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String, Object> entry : this.items.entrySet()) {
            Object val = entry.getValue();
            if (val instanceof LogListBlock) {
                out.append(prefix);
                out.append(this.padKey(entry.getKey(), this.maxKeyLength));
                out.append(":\r\n");
                out.append(((LogListBlock)val).getOutput(prefix + "    "));
                continue;
            }
            out.append(prefix);
            out.append(this.padKey(entry.getKey(), this.maxKeyLength));
            out.append(": ");
            out.append(val.toString());
            out.append("\r\n");
        }
        return out.toString();
    }

    public String toString() {
        return this.getOutput("");
    }
}

