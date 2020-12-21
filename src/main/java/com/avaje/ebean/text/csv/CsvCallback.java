/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.csv;

import com.avaje.ebean.EbeanServer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface CsvCallback<T> {
    public void begin(EbeanServer var1);

    public void readHeader(String[] var1);

    public boolean processLine(int var1, String[] var2);

    public void processBean(int var1, String[] var2, T var3);

    public void end(int var1);

    public void endWithError(int var1, Exception var2);
}

