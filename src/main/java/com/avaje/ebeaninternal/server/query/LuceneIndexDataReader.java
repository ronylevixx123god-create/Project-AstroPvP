/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.search.IndexSearcher
 *  org.apache.lucene.search.Query
 *  org.apache.lucene.search.ScoreDoc
 *  org.apache.lucene.search.Sort
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import com.avaje.ebeaninternal.server.lucene.LIndexFields;
import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import javax.persistence.PersistenceException;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LuceneIndexDataReader
implements DataReader {
    private final LIndexFields indexFieldDefn;
    private LIndexField[] readFields;
    private ScoreDoc[] scoreDocs;
    private LIndexSearch indexSearch;
    private IndexSearcher searcher;
    private int maxReadRows;
    private int colIndex;
    private int rowIndex;
    private Document currentDoc;

    public LuceneIndexDataReader(OrmQueryRequest<?> request) {
        LIndex luceneIndex = request.getLuceneIndex();
        this.indexFieldDefn = luceneIndex.getIndexFieldDefn();
        this.readFields = this.indexFieldDefn.getReadFields();
        this.indexSearch = luceneIndex.getIndexSearch();
        this.searcher = this.indexSearch.getIndexSearcher();
        LuceneOrmQueryRequest luceneRequest = request.getLuceneOrmQueryRequest();
        int maxRows = request.getQuery().getMaxRows();
        if (maxRows < 1) {
            maxRows = 100;
        }
        Query luceneQuery = luceneRequest.getLuceneQuery();
        Sort luceneSort = luceneRequest.getLuceneSort();
        try {
            Object topDocs = luceneSort == null ? this.searcher.search(luceneQuery, null, maxRows) : this.searcher.search(luceneQuery, null, maxRows, luceneSort);
            this.scoreDocs = topDocs.scoreDocs;
            this.maxReadRows = this.scoreDocs.length;
        }
        catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    public byte[] readFieldAsBytes() {
        try {
            String fieldName = this.readFields[this.colIndex++].getName();
            return this.currentDoc.getBinaryValue(fieldName);
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public String readFieldAsString() {
        try {
            String fieldName = this.readFields[this.colIndex++].getName();
            return this.currentDoc.get(fieldName);
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        this.indexSearch.releaseClose();
    }

    @Override
    public Array getArray() throws SQLException {
        throw new PersistenceException("Not Supported yet");
    }

    @Override
    public BigDecimal getBigDecimal() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : new BigDecimal(s);
    }

    @Override
    public byte[] getBinaryBytes() throws SQLException {
        return this.readFieldAsBytes();
    }

    @Override
    public byte[] getBlobBytes() throws SQLException {
        return this.readFieldAsBytes();
    }

    @Override
    public Boolean getBoolean() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Boolean.valueOf(s);
    }

    @Override
    public Byte getByte() throws SQLException {
        byte[] bytes = this.readFieldAsBytes();
        return bytes == null ? null : Byte.valueOf(bytes[0]);
    }

    @Override
    public byte[] getBytes() throws SQLException {
        return this.readFieldAsBytes();
    }

    @Override
    public Date getDate() throws SQLException {
        Long longVal = this.getLong();
        return longVal == null ? null : new Date(longVal);
    }

    @Override
    public Double getDouble() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Double.valueOf(s);
    }

    @Override
    public Float getFloat() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Float.valueOf(s);
    }

    @Override
    public Integer getInt() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Integer.valueOf(s);
    }

    @Override
    public Long getLong() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Long.valueOf(s);
    }

    @Override
    public Short getShort() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Short.valueOf(s);
    }

    @Override
    public String getString() throws SQLException {
        return this.readFieldAsString();
    }

    @Override
    public String getStringClob() throws SQLException {
        return this.readFieldAsString();
    }

    @Override
    public String getStringFromStream() throws SQLException {
        return this.readFieldAsString();
    }

    @Override
    public Time getTime() throws SQLException {
        String s = this.readFieldAsString();
        return s == null ? null : Time.valueOf(s);
    }

    @Override
    public Timestamp getTimestamp() throws SQLException {
        Long longVal = this.getLong();
        return longVal == null ? null : new Timestamp(longVal);
    }

    @Override
    public void incrementPos(int increment) {
        this.colIndex += increment;
    }

    @Override
    public boolean next() throws SQLException {
        if (this.rowIndex >= this.maxReadRows) {
            return false;
        }
        try {
            int docIndex = this.scoreDocs[this.rowIndex++].doc;
            this.currentDoc = this.searcher.doc(docIndex);
            return true;
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void resetColumnPosition() {
        this.colIndex = 0;
    }
}

