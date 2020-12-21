/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config;

import com.zachsthings.libcomponents.config.ConfigUtil;
import com.zachsthings.libcomponents.config.ConfigurationNode;
import com.zachsthings.libcomponents.config.Setting;
import com.zachsthings.libcomponents.config.SettingBase;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ConfigurationBase {
    private static final Logger logger = Logger.getLogger(ConfigurationBase.class.getCanonicalName());
    private boolean isConfigured;

    public boolean isConfigured() {
        return this.isConfigured;
    }

    public void load(ConfigurationNode node) {
        if (this.getClass().isAnnotationPresent(SettingBase.class)) {
            node = node.getNode(this.getClass().getAnnotation(SettingBase.class).value());
        }
        for (Field field : ConfigurationBase.getFieldsRecur(this.getClass())) {
            if (!field.isAnnotationPresent(Setting.class)) continue;
            String key = field.getAnnotation(Setting.class).value();
            Object value = ConfigUtil.smartCast(field.getGenericType(), node.getProperty(key));
            try {
                field.setAccessible(true);
                if (value != null) {
                    field.set(this, value);
                    continue;
                }
                node.setProperty(key, ConfigUtil.prepareSerialization(field.get(this)));
            }
            catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, "Error setting configuration value of field: ", e);
                e.printStackTrace();
            }
        }
        this.isConfigured = true;
    }

    public void save(ConfigurationNode node) {
        if (this.getClass().isAnnotationPresent(SettingBase.class)) {
            node = node.getNode(this.getClass().getAnnotation(SettingBase.class).value());
        }
        for (Field field : ConfigurationBase.getFieldsRecur(this.getClass())) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Setting.class)) continue;
            String key = field.getAnnotation(Setting.class).value();
            try {
                node.setProperty(key, ConfigUtil.prepareSerialization(field.get(this)));
            }
            catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, "Error getting configuration value of field: ", e);
                e.printStackTrace();
            }
        }
    }

    private static List<Field> getFieldsRecur(Class<?> clazz) {
        return ConfigurationBase.getFieldsRecur(clazz, false);
    }

    private static List<Field> getFieldsRecur(Class<?> clazz, boolean includeObject) {
        ArrayList<Field> fields = new ArrayList<Field>();
        while (clazz != null && (includeObject || !Object.class.equals(clazz))) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}

