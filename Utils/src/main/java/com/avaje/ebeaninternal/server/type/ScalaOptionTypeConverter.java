/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  scala.None$
 *  scala.Option
 *  scala.Some
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.ScalarTypeConverter;
import scala.None$;
import scala.Option;
import scala.Some;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalaOptionTypeConverter<S>
implements ScalarTypeConverter<Option<S>, S> {
    @Override
    public Option<S> getNullValue() {
        return None$.MODULE$;
    }

    @Override
    public S unwrapValue(Option<S> beanType) {
        if (beanType.isEmpty()) {
            return null;
        }
        return (S)beanType.get();
    }

    @Override
    public Option<S> wrapValue(S scalarType) {
        if (scalarType == null) {
            return None$.MODULE$;
        }
        if (scalarType instanceof Some) {
            return (Option)scalarType;
        }
        return new Some(scalarType);
    }
}

