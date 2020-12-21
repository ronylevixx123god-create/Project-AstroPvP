/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  scala.collection.mutable.Buffer
 *  scala.collection.mutable.Map
 *  scala.collection.mutable.Set
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.ManyType;
import com.avaje.ebeaninternal.server.deploy.ScalaBufferConverter;
import com.avaje.ebeaninternal.server.deploy.ScalaMapConverter;
import com.avaje.ebeaninternal.server.deploy.ScalaSetConverter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import scala.collection.mutable.Buffer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DetermineManyType {
    private final boolean withScalaSupport;
    private final ManyType scalaBufMany;
    private final ManyType scalaSetMany;
    private final ManyType scalaMapMany;

    public DetermineManyType(boolean withScalaSupport) {
        this.withScalaSupport = withScalaSupport;
        if (withScalaSupport) {
            ScalaBufferConverter bufConverter = new ScalaBufferConverter();
            ScalaSetConverter setConverter = new ScalaSetConverter();
            ScalaMapConverter mapConverter = new ScalaMapConverter();
            this.scalaBufMany = new ManyType(ManyType.Underlying.LIST, bufConverter);
            this.scalaSetMany = new ManyType(ManyType.Underlying.SET, setConverter);
            this.scalaMapMany = new ManyType(ManyType.Underlying.MAP, mapConverter);
        } else {
            this.scalaBufMany = null;
            this.scalaSetMany = null;
            this.scalaMapMany = null;
        }
    }

    public ManyType getManyType(Class<?> type) {
        if (type.equals(List.class)) {
            return ManyType.JAVA_LIST;
        }
        if (type.equals(Set.class)) {
            return ManyType.JAVA_SET;
        }
        if (type.equals(Map.class)) {
            return ManyType.JAVA_MAP;
        }
        if (this.withScalaSupport) {
            if (type.equals(Buffer.class)) {
                return this.scalaBufMany;
            }
            if (type.equals(scala.collection.mutable.Set.class)) {
                return this.scalaSetMany;
            }
            if (type.equals(scala.collection.mutable.Map.class)) {
                return this.scalaMapMany;
            }
        }
        return null;
    }
}

