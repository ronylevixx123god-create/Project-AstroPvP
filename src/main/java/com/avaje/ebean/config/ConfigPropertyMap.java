/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.GlobalProperties;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class ConfigPropertyMap
implements GlobalProperties.PropertySource {
    private final String serverName;

    public ConfigPropertyMap(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public String get(String key, String defaultValue) {
        String namedKey = "ebean." + this.serverName + "." + key;
        String inheritKey = "ebean." + key;
        String value = GlobalProperties.get(namedKey, null);
        if (value == null) {
            value = GlobalProperties.get(inheritKey, null);
        }
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        String value = this.get(key, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = this.get(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }

    @Override
    public <T extends Enum<T>> T getEnum(Class<T> enumType, String key, T defaultValue) {
        String level = this.get(key, defaultValue.name());
        return Enum.valueOf(enumType, level.toUpperCase());
    }
}

