/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.index.IndexCommit
 *  org.apache.lucene.index.IndexReader
 *  org.apache.lucene.index.IndexWriter
 *  org.apache.lucene.search.IndexSearcher
 *  org.apache.lucene.store.Directory
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.lucene.LIndexIoSearcher;
import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
import com.avaje.ebeaninternal.server.lucene.PersistenceLuceneException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

public class LIndexIoSearcherDefault
implements LIndexIoSearcher {
    private static final Logger logger = Logger.getLogger(LIndexIoSearcherDefault.class.getName());
    private final String name;
    private final IndexWriter indexWriter;
    private volatile LIndexSearch indexSearch;

    public LIndexIoSearcherDefault(IndexWriter indexWriter, String name) {
        this.name = name;
        this.indexWriter = indexWriter;
        this.indexSearch = this.refreshIndexSearch();
    }

    public void postCommit() {
        try {
            this.refreshIndexSearch();
        }
        catch (Exception e) {
            String msg = "Error postCommit() refreshing IndexSearcher";
            logger.log(Level.SEVERE, msg, e);
        }
    }

    public void refresh(boolean nearRealTime) {
        try {
            this.refreshIndexSearch();
        }
        catch (Exception e) {
            String msg = "Error refreshing IndexSearch";
            logger.log(Level.SEVERE, msg, e);
        }
    }

    public LIndexVersion getLastestVersion() {
        LIndexSearch s = this.indexSearch;
        try {
            IndexCommit c = s.getIndexReader().getIndexCommit();
            return new LIndexVersion(c.getGeneration(), c.getVersion());
        }
        catch (IOException e) {
            throw new PersistenceLuceneException(e);
        }
    }

    public LIndexSearch getIndexSearch() {
        LIndexSearch s = this.indexSearch;
        if (s.isOpenAcquire()) {
            return s;
        }
        return this.refreshIndexSearch();
    }

    private LIndexSearch refreshIndexSearch() {
        LIndexIoSearcherDefault lIndexIoSearcherDefault = this;
        synchronized (lIndexIoSearcherDefault) {
            try {
                IndexReader newReader;
                LIndexSearch currentSearch = this.indexSearch;
                if (currentSearch == null) {
                    newReader = this.createIndexReader();
                } else {
                    newReader = currentSearch.getIndexReader().reopen();
                    if (newReader == currentSearch.getIndexReader()) {
                        return currentSearch;
                    }
                }
                IndexSearcher searcher = new IndexSearcher(newReader);
                LIndexSearch newSearch = new LIndexSearch(searcher, newReader);
                if (currentSearch != null) {
                    currentSearch.markForClose();
                }
                logger.info("Lucene Searcher refreshed " + this.name);
                this.indexSearch = newSearch;
                return newSearch;
            }
            catch (IOException e) {
                throw new PersistenceLuceneException(e);
            }
        }
    }

    private IndexReader createIndexReader() {
        try {
            Directory directory = this.indexWriter.getDirectory();
            return IndexReader.open((Directory)directory);
        }
        catch (IOException e) {
            throw new PersistenceLuceneException(e);
        }
    }
}

