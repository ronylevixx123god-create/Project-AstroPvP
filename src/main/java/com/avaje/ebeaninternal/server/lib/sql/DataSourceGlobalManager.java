/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceManager;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePool;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DataSourceGlobalManager {
    private static final DataSourceManager manager = new DataSourceManager();

    private DataSourceGlobalManager() {
    }

    public static boolean isShuttingDown() {
        return manager.isShuttingDown();
    }

    public static void shutdown() {
        manager.shutdown();
    }

    public static List<DataSourcePool> getPools() {
        return manager.getPools();
    }

    public static DataSourcePool getDataSource(String name) {
        return manager.getDataSource(name);
    }

    public static DataSourcePool getDataSource(String name, DataSourceConfig dsConfig) {
        return manager.getDataSource(name, dsConfig);
    }
}

