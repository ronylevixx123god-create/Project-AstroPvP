/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.csv;

import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.csv.CsvCallback;
import java.io.Reader;
import java.util.Locale;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface CsvReader<T> {
    public void setDefaultLocale(Locale var1);

    public void setDefaultTimeFormat(String var1);

    public void setDefaultDateFormat(String var1);

    public void setDefaultTimestampFormat(String var1);

    public void setPersistBatchSize(int var1);

    public void setHasHeader(boolean var1, boolean var2);

    public void setAddPropertiesFromHeader();

    public void setIgnoreHeader();

    public void setLogInfoFrequency(int var1);

    public void addIgnore();

    public void addProperty(String var1);

    public void addReference(String var1);

    public void addProperty(String var1, StringParser var2);

    public void addDateTime(String var1, String var2);

    public void addDateTime(String var1, String var2, Locale var3);

    public void process(Reader var1) throws Exception;

    public void process(Reader var1, CsvCallback<T> var2) throws Exception;
}

