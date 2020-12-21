/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Field$Index
 *  org.apache.lucene.document.Field$Store
 */
package com.avaje.ebean.config.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;

public class IndexFieldDefn {
    protected final String name;
    protected String propertyName;
    protected Field.Index index;
    protected Field.Store store;
    protected Sortable sortable;
    protected int precisionStep = -1;
    protected float boost;
    protected Analyzer queryAnalyzer;
    protected Analyzer indexAnalyzer;
    protected String[] properties;

    public IndexFieldDefn(String name) {
        this.name = name;
        this.propertyName = name;
    }

    public IndexFieldDefn(String name, Field.Store store, Field.Index index, Sortable sortable) {
        this(name);
        this.store = store;
        this.index = index;
        this.sortable = sortable;
    }

    public String toString() {
        return this.name;
    }

    public IndexFieldDefn copyField(String name) {
        IndexFieldDefn copy = new IndexFieldDefn(name, this.store, this.index, this.sortable);
        copy.setPropertyName(name);
        copy.setIndexAnalyzer(this.indexAnalyzer);
        copy.setQueryAnalyzer(this.queryAnalyzer);
        copy.setPrecisionStep(this.precisionStep);
        copy.setBoost(this.boost);
        return copy;
    }

    public IndexFieldDefn copyFieldConcat(String fieldName, String[] properties) {
        IndexFieldDefn copy = this.copyField(fieldName);
        copy.setPropertyName(null);
        copy.setPropertyNames(properties);
        return copy;
    }

    public String getName() {
        return this.name;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public IndexFieldDefn setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public Field.Index getIndex() {
        return this.index;
    }

    public IndexFieldDefn setIndex(Field.Index index) {
        this.index = index;
        return this;
    }

    public Field.Store getStore() {
        return this.store;
    }

    public IndexFieldDefn setStore(Field.Store store) {
        this.store = store;
        return this;
    }

    public Sortable getSortable() {
        return this.sortable;
    }

    public IndexFieldDefn setSortable(Sortable sortable) {
        this.sortable = sortable;
        return this;
    }

    public int getPrecisionStep() {
        return this.precisionStep;
    }

    public IndexFieldDefn setPrecisionStep(int precisionStep) {
        this.precisionStep = precisionStep;
        return this;
    }

    public float getBoost() {
        return this.boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public Analyzer getQueryAnalyzer() {
        return this.queryAnalyzer;
    }

    public Analyzer getIndexAnalyzer() {
        return this.indexAnalyzer;
    }

    public IndexFieldDefn setQueryAnalyzer(Analyzer queryAnalyzer) {
        this.queryAnalyzer = queryAnalyzer;
        return this;
    }

    public IndexFieldDefn setIndexAnalyzer(Analyzer indexAnalyzer) {
        this.indexAnalyzer = indexAnalyzer;
        return this;
    }

    public IndexFieldDefn setBothAnalyzers(Analyzer analyzer) {
        this.queryAnalyzer = analyzer;
        this.indexAnalyzer = analyzer;
        return this;
    }

    public String[] getPropertyNames() {
        return this.properties;
    }

    public void setPropertyNames(String[] properties) {
        this.properties = properties;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Sortable {
        YES,
        DEFAULT;

    }
}

