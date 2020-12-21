/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.deploy.CollectionTypeConverter;

public class ManyType {
    public static final ManyType JAVA_LIST = new ManyType(Underlying.LIST);
    public static final ManyType JAVA_SET = new ManyType(Underlying.SET);
    public static final ManyType JAVA_MAP = new ManyType(Underlying.MAP);
    private final Query.Type queryType;
    private final Underlying underlying;
    private final CollectionTypeConverter typeConverter;

    private ManyType(Underlying underlying) {
        this(underlying, null);
    }

    public ManyType(Underlying underlying, CollectionTypeConverter typeConverter) {
        this.underlying = underlying;
        this.typeConverter = typeConverter;
        switch (underlying) {
            case LIST: {
                this.queryType = Query.Type.LIST;
                break;
            }
            case SET: {
                this.queryType = Query.Type.SET;
                break;
            }
            default: {
                this.queryType = Query.Type.MAP;
            }
        }
    }

    public Query.Type getQueryType() {
        return this.queryType;
    }

    public Underlying getUnderlying() {
        return this.underlying;
    }

    public CollectionTypeConverter getTypeConverter() {
        return this.typeConverter;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Underlying {
        LIST,
        SET,
        MAP;

    }
}

