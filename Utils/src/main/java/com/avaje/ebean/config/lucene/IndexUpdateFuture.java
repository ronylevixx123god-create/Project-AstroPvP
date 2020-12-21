/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.lucene;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface IndexUpdateFuture {
    public Class<?> getBeanType();

    public boolean isCancelled();

    public boolean cancel(boolean var1);

    public Integer get() throws InterruptedException, ExecutionException;

    public Integer get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException;

    public boolean isDone();
}

