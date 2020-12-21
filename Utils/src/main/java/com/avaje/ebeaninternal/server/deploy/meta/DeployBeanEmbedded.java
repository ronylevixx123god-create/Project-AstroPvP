/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployBeanEmbedded {
    Map<String, String> propMap = new HashMap<String, String>();

    public void put(String propertyName, String dbCoumn) {
        this.propMap.put(propertyName, dbCoumn);
    }

    public void putAll(Map<String, String> propertyColumnMap) {
        this.propMap.putAll(propertyColumnMap);
    }

    public Map<String, String> getPropertyColumnMap() {
        return this.propMap;
    }
}

