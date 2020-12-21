/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexUpdateFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexUpdateFuture
implements Future<Integer>,
IndexUpdateFuture {
    private final Class<?> beanType;
    private final Runnable commitRunnable;
    private final FutureTask<Void> commitFuture;
    private FutureTask<Integer> task;

    public LIndexUpdateFuture(Class<?> beanType) {
        this.beanType = beanType;
        this.commitRunnable = new DummyRunnable();
        this.commitFuture = new FutureTask<Object>(this.commitRunnable, null);
    }

    @Override
    public Class<?> getBeanType() {
        return this.beanType;
    }

    public Runnable getCommitRunnable() {
        return this.commitFuture;
    }

    public void setTask(FutureTask<Integer> task) {
        this.task = task;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public Integer get() throws InterruptedException, ExecutionException {
        this.commitFuture.get();
        return this.task.get();
    }

    @Override
    public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        this.commitFuture.get(timeout, unit);
        return this.task.get(0L, unit);
    }

    @Override
    public boolean isDone() {
        return this.commitFuture.isDone();
    }

    private static class DummyRunnable
    implements Runnable {
        private DummyRunnable() {
        }

        public void run() {
            System.out.println("-- dummy runnable");
        }
    }
}

