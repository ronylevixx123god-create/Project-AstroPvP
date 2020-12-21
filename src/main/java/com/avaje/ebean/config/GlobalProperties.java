/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.ConfigPropertyMap;
import com.avaje.ebean.config.PropertyMap;
import com.avaje.ebean.config.PropertyMapLoader;
import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.Map;
import javax.servlet.ServletContext;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class GlobalProperties {
    private static PropertyMap globalMap;
    private static boolean skipPrimaryServer;

    public static synchronized void setSkipPrimaryServer(boolean skip) {
        skipPrimaryServer = skip;
    }

    public static synchronized boolean isSkipPrimaryServer() {
        return skipPrimaryServer;
    }

    public static String evaluateExpressions(String val) {
        return GlobalProperties.getPropertyMap().eval(val);
    }

    public static synchronized void evaluateExpressions() {
        GlobalProperties.getPropertyMap().evaluateProperties();
    }

    public static synchronized void setServletContext(ServletContext servletContext) {
        PropertyMapLoader.setServletContext(servletContext);
    }

    public static synchronized ServletContext getServletContext() {
        return PropertyMapLoader.getServletContext();
    }

    private static void initPropertyMap() {
        String loaderCn;
        String fileName = System.getenv("EBEAN_PROPS_FILE");
        if (fileName == null && (fileName = System.getProperty("ebean.props.file")) == null) {
            fileName = "ebean.properties";
        }
        if ((globalMap = PropertyMapLoader.load(null, fileName)) == null) {
            globalMap = new PropertyMap();
        }
        if ((loaderCn = globalMap.get("ebean.properties.loader")) != null) {
            try {
                Runnable r = (Runnable)ClassUtil.newInstance(loaderCn);
                r.run();
            }
            catch (Exception e) {
                String m = "Error creating or running properties loader " + loaderCn;
                throw new RuntimeException(m, e);
            }
        }
    }

    private static synchronized PropertyMap getPropertyMap() {
        if (globalMap == null) {
            GlobalProperties.initPropertyMap();
        }
        return globalMap;
    }

    public static synchronized String get(String key, String defaultValue) {
        return GlobalProperties.getPropertyMap().get(key, defaultValue);
    }

    public static synchronized int getInt(String key, int defaultValue) {
        return GlobalProperties.getPropertyMap().getInt(key, defaultValue);
    }

    public static synchronized boolean getBoolean(String key, boolean defaultValue) {
        return GlobalProperties.getPropertyMap().getBoolean(key, defaultValue);
    }

    public static synchronized String put(String key, String value) {
        return GlobalProperties.getPropertyMap().putEval(key, value);
    }

    public static synchronized void putAll(Map<String, String> keyValueMap) {
        for (Map.Entry<String, String> e : keyValueMap.entrySet()) {
            GlobalProperties.getPropertyMap().putEval(e.getKey(), e.getValue());
        }
    }

    public static PropertySource getPropertySource(String name) {
        return new ConfigPropertyMap(name);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static interface PropertySource {
        public String getServerName();

        public String get(String var1, String var2);

        public int getInt(String var1, int var2);

        public boolean getBoolean(String var1, boolean var2);

        public <T extends Enum<T>> T getEnum(Class<T> var1, String var2, T var3);
    }
}

