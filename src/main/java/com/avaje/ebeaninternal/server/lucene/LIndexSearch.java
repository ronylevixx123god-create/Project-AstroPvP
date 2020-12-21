/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.index.IndexReader
 *  org.apache.lucene.search.IndexSearcher
 */
package com.avaje.ebeaninternal.server.lucene;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

public class LIndexSearch {
    private static final Logger logger = Logger.getLogger(LIndexSearch.class.getName());
    private final IndexSearcher indexSearcher;
    private final IndexReader indexReader;
    private int refCount;
    private boolean markForClose;
    private boolean closed;

    public LIndexSearch(IndexSearcher indexSearcher, IndexReader indexReader) {
        this.indexSearcher = indexSearcher;
        this.indexReader = indexReader;
    }

    public IndexSearcher getIndexSearcher() {
        return this.indexSearcher;
    }

    public IndexReader getIndexReader() {
        return this.indexReader;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isOpenAcquire() {
        LIndexSearch lIndexSearch = this;
        synchronized (lIndexSearch) {
            if (this.markForClose) {
                return false;
            }
            ++this.refCount;
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseClose() {
        LIndexSearch lIndexSearch = this;
        synchronized (lIndexSearch) {
            --this.refCount;
            this.closeIfMarked();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void markForClose() {
        LIndexSearch lIndexSearch = this;
        synchronized (lIndexSearch) {
            this.markForClose = true;
            this.closeIfMarked();
        }
    }

    private void closeIfMarked() {
        if (this.markForClose && this.refCount <= 0 && !this.closed) {
            this.closed = true;
            try {
                this.indexSearcher.close();
            }
            catch (Exception e) {
                logger.log(Level.WARNING, "Error when closing indexSearcher", e);
            }
            try {
                this.indexReader.close();
            }
            catch (Exception e) {
                logger.log(Level.WARNING, "Error when closing indexReader", e);
            }
        }
    }
}

