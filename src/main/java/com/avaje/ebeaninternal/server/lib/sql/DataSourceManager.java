/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.lib.BackgroundRunnable;
import com.avaje.ebeaninternal.server.lib.BackgroundThread;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceAlertListener;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceException;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceNotify;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePool;
import com.avaje.ebeaninternal.server.lib.sql.SimpleAlerter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DataSourceManager
implements DataSourceNotify {
    private static final Logger logger = Logger.getLogger(DataSourceManager.class.getName());
    private final DataSourceAlertListener alertlistener;
    private final Hashtable<String, DataSourcePool> dsMap = new Hashtable();
    private final Object monitor = new Object();
    private final BackgroundRunnable dbChecker;
    private final int dbUpFreqInSecs;
    private final int dbDownFreqInSecs;
    private boolean shuttingDown;

    public DataSourceManager() {
        this.alertlistener = this.createAlertListener();
        this.dbUpFreqInSecs = GlobalProperties.getInt("datasource.heartbeatfreq", 30);
        this.dbDownFreqInSecs = GlobalProperties.getInt("datasource.deadbeatfreq", 10);
        this.dbChecker = new BackgroundRunnable(new Checker(), this.dbUpFreqInSecs);
        try {
            BackgroundThread.add(this.dbChecker);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    private DataSourceAlertListener createAlertListener() throws DataSourceException {
        String alertCN = GlobalProperties.get("datasource.alert.class", null);
        if (alertCN == null) {
            return new SimpleAlerter();
        }
        try {
            return (DataSourceAlertListener)ClassUtil.newInstance(alertCN, this.getClass());
        }
        catch (Exception ex) {
            throw new DataSourceException(ex);
        }
    }

    @Override
    public void notifyDataSourceUp(String dataSourceName) {
        this.dbChecker.setFreqInSecs(this.dbUpFreqInSecs);
        if (this.alertlistener != null) {
            this.alertlistener.dataSourceUp(dataSourceName);
        }
    }

    @Override
    public void notifyDataSourceDown(String dataSourceName) {
        this.dbChecker.setFreqInSecs(this.dbDownFreqInSecs);
        if (this.alertlistener != null) {
            this.alertlistener.dataSourceDown(dataSourceName);
        }
    }

    @Override
    public void notifyWarning(String subject, String msg) {
        if (this.alertlistener != null) {
            this.alertlistener.warning(subject, msg);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isShuttingDown() {
        Object object = this.monitor;
        synchronized (object) {
            return this.shuttingDown;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        Object object = this.monitor;
        synchronized (object) {
            this.shuttingDown = true;
            Iterator<DataSourcePool> it = this.dsMap.values().iterator();
            while (it.hasNext()) {
                try {
                    DataSourcePool ds = it.next();
                    ds.shutdown();
                }
                catch (DataSourceException e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DataSourcePool> getPools() {
        Object object = this.monitor;
        synchronized (object) {
            ArrayList<DataSourcePool> list = new ArrayList<DataSourcePool>();
            list.addAll(this.dsMap.values());
            return list;
        }
    }

    public DataSourcePool getDataSource(String name) {
        return this.getDataSource(name, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataSourcePool getDataSource(String name, DataSourceConfig dsConfig) {
        if (name == null) {
            throw new IllegalArgumentException("name not defined");
        }
        Object object = this.monitor;
        synchronized (object) {
            DataSourcePool pool = this.dsMap.get(name);
            if (pool == null) {
                if (dsConfig == null) {
                    dsConfig = new DataSourceConfig();
                    dsConfig.loadSettings(name);
                }
                pool = new DataSourcePool(this, name, dsConfig);
                this.dsMap.put(name, pool);
            }
            return pool;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkDataSource() {
        Object object = this.monitor;
        synchronized (object) {
            if (!this.isShuttingDown()) {
                for (DataSourcePool ds : this.dsMap.values()) {
                    ds.checkDataSource();
                }
            }
        }
    }

    private final class Checker
    implements Runnable {
        private Checker() {
        }

        public void run() {
            DataSourceManager.this.checkDataSource();
        }
    }
}

