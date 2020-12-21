/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.query.CQuery;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BackgroundFetch
implements Callable<Integer> {
    private static final Logger logger = Logger.getLogger(BackgroundFetch.class.getName());
    private final CQuery<?> cquery;
    private final SpiTransaction transaction;

    public BackgroundFetch(CQuery<?> cquery) {
        this.cquery = cquery;
        this.transaction = cquery.getTransaction();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Integer call() {
        try {
            BeanCollection<?> bc = this.cquery.continueFetchingInBackground();
            Integer n = bc.size();
            return n;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            Integer n = 0;
            return n;
        }
        finally {
            try {
                this.cquery.close();
            }
            catch (Exception e) {
                logger.log(Level.SEVERE, null, e);
            }
            try {
                this.transaction.rollback();
            }
            catch (Exception e) {
                logger.log(Level.SEVERE, null, e);
            }
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BackgroundFetch ").append(this.cquery);
        return sb.toString();
    }
}

