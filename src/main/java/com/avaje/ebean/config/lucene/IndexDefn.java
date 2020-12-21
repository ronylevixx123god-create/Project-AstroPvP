/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.index.IndexWriter$MaxFieldLength
 */
package com.avaje.ebean.config.lucene;

import com.avaje.ebean.config.lucene.IndexDefnBuilder;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface IndexDefn<T> {
    public void initialise(IndexDefnBuilder var1);

    public String getDefaultField();

    public List<IndexFieldDefn> getFields();

    public boolean isUpdateSinceSupported();

    public String[] getUpdateSinceProperties();

    public Analyzer getAnalyzer();

    public IndexWriter.MaxFieldLength getMaxFieldLength();

    public int getMaxBufferedDocs();

    public double getRAMBufferSizeMB();

    public int getTermIndexInterval();
}

