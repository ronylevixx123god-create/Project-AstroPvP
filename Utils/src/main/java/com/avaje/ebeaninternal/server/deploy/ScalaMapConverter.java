/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  scala.collection.JavaConversions
 *  scala.collection.JavaConversions$JMapWrapper
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.CollectionTypeConverter;
import java.util.Map;
import scala.collection.JavaConversions;

public class ScalaMapConverter
implements CollectionTypeConverter {
    public Object toUnderlying(Object wrapped) {
        if (wrapped instanceof JavaConversions.JMapWrapper) {
            return ((JavaConversions.JMapWrapper)wrapped).underlying();
        }
        return null;
    }

    public Object toWrapped(Object wrapped) {
        if (wrapped instanceof Map) {
            return JavaConversions.asMap((Map)((Map)wrapped));
        }
        return wrapped;
    }
}

