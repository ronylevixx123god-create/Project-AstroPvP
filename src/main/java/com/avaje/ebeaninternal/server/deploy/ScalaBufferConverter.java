/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  scala.collection.JavaConversions
 *  scala.collection.JavaConversions$JListWrapper
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.CollectionTypeConverter;
import java.util.List;
import scala.collection.JavaConversions;

public class ScalaBufferConverter
implements CollectionTypeConverter {
    public Object toUnderlying(Object wrapped) {
        if (wrapped instanceof JavaConversions.JListWrapper) {
            return ((JavaConversions.JListWrapper)wrapped).underlying();
        }
        return null;
    }

    public Object toWrapped(Object wrapped) {
        if (wrapped instanceof List) {
            return JavaConversions.asBuffer((List)((List)wrapped));
        }
        return wrapped;
    }
}

