/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.persist.BatchDepthComparator;
import com.avaje.ebeaninternal.server.persist.BatchedBeanControl;
import com.avaje.ebeaninternal.server.persist.BatchedBeanHolder;
import com.avaje.ebeaninternal.server.persist.BatchedPstmtHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BatchControl {
    private static final Logger logger = Logger.getLogger(BatchControl.class.getName());
    private static final BatchDepthComparator depthComparator = new BatchDepthComparator();
    private final SpiTransaction transaction;
    private final BatchedPstmtHolder pstmtHolder = new BatchedPstmtHolder();
    private int batchSize;
    private boolean getGeneratedKeys;
    private boolean batchFlushOnMixed = true;
    private final BatchedBeanControl beanControl;

    public BatchControl(SpiTransaction t, int batchSize, boolean getGenKeys) {
        this.transaction = t;
        this.batchSize = batchSize;
        this.getGeneratedKeys = getGenKeys;
        this.beanControl = new BatchedBeanControl(t, this);
        this.transaction.setBatchControl(this);
    }

    public void setBatchFlushOnMixed(boolean flushBatchOnMixed) {
        this.batchFlushOnMixed = flushBatchOnMixed;
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        if (batchSize > 1) {
            this.batchSize = batchSize;
        }
    }

    public void setGetGeneratedKeys(Boolean getGeneratedKeys) {
        if (getGeneratedKeys != null) {
            this.getGeneratedKeys = getGeneratedKeys;
        }
    }

    public int executeStatementOrBatch(PersistRequest request, boolean batch) {
        if (!batch || this.batchFlushOnMixed && !this.beanControl.isEmpty()) {
            this.flush();
        }
        if (!batch) {
            return request.executeNow();
        }
        if (this.pstmtHolder.getMaxSize() >= this.batchSize) {
            this.flush();
        }
        request.executeNow();
        return -1;
    }

    public int executeOrQueue(PersistRequestBean<?> request, boolean batch) {
        if (!batch || this.batchFlushOnMixed && !this.pstmtHolder.isEmpty()) {
            this.flush();
        }
        if (!batch) {
            return request.executeNow();
        }
        ArrayList<PersistRequest> persistList = this.beanControl.getPersistList(request);
        if (persistList == null) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Bean instance already in this batch: " + request.getBean());
            }
            return -1;
        }
        if (persistList.size() >= this.batchSize) {
            this.flush();
            persistList = this.beanControl.getPersistList(request);
        }
        persistList.add(request);
        return -1;
    }

    public BatchedPstmtHolder getPstmtHolder() {
        return this.pstmtHolder;
    }

    public boolean isEmpty() {
        return this.beanControl.isEmpty() && this.pstmtHolder.isEmpty();
    }

    protected void flushPstmtHolder() {
        this.pstmtHolder.flush(this.getGeneratedKeys);
    }

    protected void executeNow(ArrayList<PersistRequest> list) {
        for (int i = 0; i < list.size(); ++i) {
            PersistRequest request = list.get(i);
            request.executeNow();
        }
    }

    public void flush() throws PersistenceException {
        if (!this.pstmtHolder.isEmpty()) {
            this.flushPstmtHolder();
        }
        if (this.beanControl.isEmpty()) {
            return;
        }
        Object[] bsArray = this.beanControl.getArray();
        Arrays.sort(bsArray, depthComparator);
        if (this.transaction.isLogSummary()) {
            this.transaction.logInternal("BatchControl flush " + Arrays.toString(bsArray));
        }
        for (int i = 0; i < bsArray.length; ++i) {
            Object bs = bsArray[i];
            ((BatchedBeanHolder)bs).executeNow();
            this.flushPstmtHolder();
        }
    }
}

