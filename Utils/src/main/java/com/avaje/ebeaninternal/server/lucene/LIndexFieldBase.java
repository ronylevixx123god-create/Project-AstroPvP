/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.Fieldable
 *  org.apache.lucene.queryParser.QueryParser
 *  org.apache.lucene.util.Version
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.FieldFactory;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class LIndexFieldBase
implements LIndexField {
    protected final Analyzer queryAnalyzer;
    protected final String fieldName;
    protected final String propertyName;
    protected final int luceneType;
    protected final int sortType;
    protected final ElPropertyValue property;
    protected final ScalarType<?> scalarType;
    protected final FieldFactory fieldFactory;
    protected final boolean indexed;
    protected final boolean stored;
    protected final boolean tokenized;

    public LIndexFieldBase(Analyzer queryAnalyzer, String fieldName, int luceneType, ElPropertyValue property, FieldFactory fieldFactory) {
        this.queryAnalyzer = queryAnalyzer;
        this.fieldName = fieldName;
        this.luceneType = luceneType;
        this.sortType = this.getSortType(luceneType);
        this.property = property;
        this.fieldFactory = fieldFactory;
        Fieldable fieldPrototype = fieldFactory.createFieldable();
        this.indexed = fieldPrototype.isIndexed();
        this.stored = fieldPrototype.isStored();
        this.tokenized = fieldPrototype.isTokenized();
        if (property == null) {
            this.scalarType = null;
            this.propertyName = null;
        } else {
            this.scalarType = property.getBeanProperty().getScalarType();
            this.propertyName = SplitName.add(property.getElPrefix(), property.getName());
        }
    }

    public String toString() {
        return this.propertyName;
    }

    @Override
    public void addIndexRequiredPropertyNames(Set<String> requiredPropertyNames) {
        if (this.propertyName != null) {
            requiredPropertyNames.add(this.propertyName);
        }
    }

    @Override
    public int getSortType() {
        return this.sortType;
    }

    @Override
    public QueryParser createQueryParser() {
        return new QueryParser(Version.LUCENE_30, this.fieldName, this.queryAnalyzer);
    }

    @Override
    public String getName() {
        return this.fieldName;
    }

    @Override
    public boolean isIndexed() {
        return this.indexed;
    }

    @Override
    public boolean isStored() {
        return this.stored;
    }

    public boolean isTokenized() {
        return this.tokenized;
    }

    @Override
    public boolean isBeanProperty() {
        return this.property != null;
    }

    @Override
    public int getPropertyOrder() {
        return this.property == null ? 0 : this.property.getDeployOrder();
    }

    @Override
    public ElPropertyValue getElBeanProperty() {
        return this.property;
    }

    @Override
    public void readValue(Document doc, Object bean) {
        Object v = this.readIndexValue(doc);
        if (v != null) {
            v = this.scalarType.luceneFromIndexValue(v);
        }
        this.property.elSetValue(bean, v, true, false);
    }

    protected Object readIndexValue(Document doc) {
        String s = doc.get(this.fieldName);
        if (s == null) {
            return null;
        }
        switch (this.luceneType) {
            case 1: {
                return Integer.parseInt(s);
            }
            case 2: {
                return Long.parseLong(s);
            }
            case 5: {
                return Long.parseLong(s);
            }
            case 6: {
                return Long.parseLong(s);
            }
            case 3: {
                return Double.parseDouble(s);
            }
            case 4: {
                return Float.valueOf(Float.parseFloat(s));
            }
        }
        throw new RuntimeException("Unhandled type " + this.luceneType);
    }

    private int getSortType(int luceneType) {
        switch (luceneType) {
            case 1: {
                return 4;
            }
            case 2: {
                return 6;
            }
            case 5: {
                return 6;
            }
            case 6: {
                return 6;
            }
            case 3: {
                return 7;
            }
            case 4: {
                return 5;
            }
            case 0: {
                return 3;
            }
        }
        return -1;
    }
}

