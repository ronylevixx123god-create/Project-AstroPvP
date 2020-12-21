/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  scala.collection.JavaConversions
 *  scala.collection.JavaConversions$JSetWrapper
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.CollectionTypeConverter;
import java.util.Set;
import scala.collection.JavaConversions;

public class ScalaSetConverter
implements CollectionTypeConverter {
    public Object toUnderlying(Object wrapped) {
        if (wrapped instanceof JavaConversions.JSetWrapper) {
            return ((JavaConversions.JSetWrapper)wrapped).underlying();
        }
        return null;
    }

    public Object toWrapped(Object wrapped) {
        if (wrapped instanceof Set) {
            return JavaConversions.asSet((Set)((Set)wrapped));
        }
        return wrapped;
    }
}

