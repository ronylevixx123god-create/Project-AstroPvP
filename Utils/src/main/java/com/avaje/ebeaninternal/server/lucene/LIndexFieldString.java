/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.analysis.TokenStream
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.Field
 *  org.apache.lucene.document.Fieldable
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import com.avaje.ebeaninternal.server.lucene.FieldFactory;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldBase;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class LIndexFieldString
extends LIndexFieldBase {
    private final Analyzer indexAnalyzer;

    public LIndexFieldString(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue property, Analyzer indexAnalyzer) {
        super(queryAnalyzer, fieldName, 0, property, fieldFactory);
        this.indexAnalyzer = indexAnalyzer;
    }

    @Override
    public void addIndexResolvePropertyNames(Set<String> resolvePropertyNames) {
        if (this.propertyName != null && this.isIndexed()) {
            resolvePropertyNames.add(this.propertyName);
        }
    }

    @Override
    public void addIndexRestorePropertyNames(Set<String> restorePropertyNames) {
        if (this.propertyName != null && this.isStored()) {
            restorePropertyNames.add(this.propertyName);
        }
    }

    @Override
    public String getSortableProperty() {
        if (this.isIndexed() && !this.isTokenized()) {
            return this.propertyName;
        }
        return null;
    }

    @Override
    public void readValue(Document doc, Object bean) {
        Object v = doc.get(this.fieldName);
        if (v != null) {
            v = this.scalarType.luceneFromIndexValue(v);
        }
        this.property.elSetValue(bean, v, true, false);
    }

    @Override
    public DocFieldWriter createDocFieldWriter() {
        Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(this.property, this.scalarType, f, this.indexAnalyzer);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Writer
    implements DocFieldWriter {
        private final ElPropertyValue property;
        private final ScalarType<?> scalarType;
        private final Field field;
        private final Analyzer indexAnalyzer;

        Writer(ElPropertyValue property, ScalarType<?> scalarType, Field field, Analyzer indexAnalyzer) {
            this.property = property;
            this.scalarType = scalarType;
            this.field = field;
            this.indexAnalyzer = indexAnalyzer;
        }

        @Override
        public void writeValue(Object bean, Document document) {
            Object value = this.property.elGetValue(bean);
            if (value != null) {
                System.out.println("- write " + this.field.name() + " " + value);
                String s = (String)this.scalarType.luceneToIndexValue(value);
                if (this.indexAnalyzer == null) {
                    this.field.setValue(s);
                } else {
                    StringReader sr = new StringReader(s);
                    TokenStream tokenStream = this.indexAnalyzer.tokenStream(this.field.name(), (Reader)sr);
                    this.field.setTokenStream(tokenStream);
                }
                document.add((Fieldable)this.field);
            }
        }
    }
}

