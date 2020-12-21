/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.transaction.HeuristicMixedException
 *  javax.transaction.HeuristicRollbackException
 *  javax.transaction.NotSupportedException
 *  javax.transaction.RollbackException
 *  javax.transaction.Synchronization
 *  javax.transaction.SystemException
 *  javax.transaction.TransactionSynchronizationRegistry
 *  javax.transaction.UserTransaction
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.ExternalTransactionManager;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.transaction.DefaultTransactionThreadLocal;
import com.avaje.ebeaninternal.server.transaction.JtaTransaction;
import com.avaje.ebeaninternal.server.transaction.TransactionManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

public class JtaTransactionManager
implements ExternalTransactionManager {
    private static final Logger logger = Logger.getLogger(JtaTransactionManager.class.getName());
    private static final String EBEAN_TXN_RESOURCE = "EBEAN_TXN_RESOURCE";
    private DataSource dataSource;
    private TransactionManager transactionManager;
    private String serverName;

    public void setTransactionManager(Object txnMgr) {
        this.transactionManager = (TransactionManager)txnMgr;
        this.dataSource = this.transactionManager.getDataSource();
        this.serverName = this.transactionManager.getServerName();
    }

    private TransactionSynchronizationRegistry getSyncRegistry() {
        try {
            InitialContext ctx = new InitialContext();
            return (TransactionSynchronizationRegistry)ctx.lookup("java:comp/TransactionSynchronizationRegistry");
        }
        catch (NamingException e) {
            throw new PersistenceException(e);
        }
    }

    private UserTransaction getUserTransaction() {
        try {
            InitialContext ctx = new InitialContext();
            return (UserTransaction)ctx.lookup("java:comp/UserTransaction");
        }
        catch (NamingException e) {
            return new DummyUserTransaction();
        }
    }

    public Object getCurrentTransaction() {
        TransactionSynchronizationRegistry syncRegistry = this.getSyncRegistry();
        SpiTransaction t = (SpiTransaction)syncRegistry.getResource((Object)EBEAN_TXN_RESOURCE);
        if (t != null) {
            return t;
        }
        SpiTransaction currentEbeanTransaction = DefaultTransactionThreadLocal.get(this.serverName);
        if (currentEbeanTransaction != null) {
            String msg = "JTA Transaction - no current txn BUT using current Ebean one " + currentEbeanTransaction.getId();
            logger.log(Level.WARNING, msg);
            return currentEbeanTransaction;
        }
        UserTransaction ut = this.getUserTransaction();
        if (ut == null) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("JTA Transaction - no current txn");
            }
            return null;
        }
        String txnId = String.valueOf(System.currentTimeMillis());
        JtaTransaction newTrans = new JtaTransaction(txnId, true, LogLevel.NONE, ut, this.dataSource, this.transactionManager);
        JtaTxnListener txnListener = this.createJtaTxnListener(newTrans);
        syncRegistry.putResource((Object)EBEAN_TXN_RESOURCE, (Object)newTrans);
        syncRegistry.registerInterposedSynchronization((Synchronization)txnListener);
        DefaultTransactionThreadLocal.set(this.serverName, newTrans);
        return newTrans;
    }

    private JtaTxnListener createJtaTxnListener(SpiTransaction t) {
        return new JtaTxnListener(this.transactionManager, t);
    }

    private static class JtaTxnListener
    implements Synchronization {
        private final TransactionManager transactionManager;
        private final SpiTransaction transaction;
        private final String serverName;

        private JtaTxnListener(TransactionManager transactionManager, SpiTransaction t) {
            this.transactionManager = transactionManager;
            this.transaction = t;
            this.serverName = transactionManager.getServerName();
        }

        public void beforeCompletion() {
        }

        public void afterCompletion(int status) {
            switch (status) {
                case 3: {
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("Jta Txn [" + this.transaction.getId() + "] committed");
                    }
                    this.transactionManager.notifyOfCommit(this.transaction);
                    DefaultTransactionThreadLocal.replace(this.serverName, null);
                    break;
                }
                case 4: {
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("Jta Txn [" + this.transaction.getId() + "] rollback");
                    }
                    this.transactionManager.notifyOfRollback(this.transaction, null);
                    DefaultTransactionThreadLocal.replace(this.serverName, null);
                    break;
                }
                default: {
                    logger.fine("Jta Txn [" + this.transaction.getId() + "] status:" + status);
                }
            }
        }
    }

    private static class DummyUserTransaction
    implements UserTransaction {
        private DummyUserTransaction() {
        }

        public void begin() throws NotSupportedException, SystemException {
        }

        public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        }

        public int getStatus() throws SystemException {
            return 0;
        }

        public void rollback() throws IllegalStateException, SecurityException, SystemException {
        }

        public void setRollbackOnly() throws IllegalStateException, SystemException {
        }

        public void setTransactionTimeout(int seconds) throws SystemException {
        }
    }
}

