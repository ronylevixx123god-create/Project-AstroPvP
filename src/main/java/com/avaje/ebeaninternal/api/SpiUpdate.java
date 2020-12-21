/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Update;
import com.avaje.ebeaninternal.api.BindParams;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiUpdate<T>
extends Update<T> {
    public Class<?> getBeanType();

    public OrmUpdateType getOrmUpdateType();

    public String getBaseTable();

    public String getUpdateStatement();

    public int getTimeout();

    public boolean isNotifyCache();

    public BindParams getBindParams();

    public void setGeneratedSql(String var1);

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum OrmUpdateType {
        INSERT{

            public String toString() {
                return "Insert";
            }
        }
        ,
        UPDATE{

            public String toString() {
                return "Update";
            }
        }
        ,
        DELETE{

            public String toString() {
                return "Delete";
            }
        }
        ,
        UNKNOWN{

            public String toString() {
                return "Unknown";
            }
        };

    }
}

