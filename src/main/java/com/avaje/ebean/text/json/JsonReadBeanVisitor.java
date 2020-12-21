/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonElement;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface JsonReadBeanVisitor<T> {
    public void visit(T var1, Map<String, JsonElement> var2);
}

