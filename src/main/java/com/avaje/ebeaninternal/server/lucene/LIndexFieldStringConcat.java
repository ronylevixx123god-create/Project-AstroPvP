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
import com.avaje.ebeaninternal.server.query.SplitName;
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
public final class LIndexFieldStringConcat
extends LIndexFieldBase {
    private final ElPropertyValue[] properties;
    private final ScalarType<?>[] scalarTypes;
    private final Analyzer indexAnalyzer;

    public LIndexFieldStringConcat(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue[] properties, Analyzer indexAnalyzer) {
        super(queryAnalyzer, fieldName, 0, null, fieldFactory);
        this.properties = properties;
        this.indexAnalyzer = indexAnalyzer;
        this.scalarTypes = new ScalarType[properties.length];
        for (int i = 0; i < this.scalarTypes.length; ++i) {
            this.scalarTypes[i] = properties[i].getBeanProperty().getScalarType();
        }
    }

    @Override
    public void addIndexRequiredPropertyNames(Set<String> requiredPropertyNames) {
        for (int i = 0; i < this.properties.length; ++i) {
            String prefix = this.properties[i].getElPrefix();
            String name = this.properties[i].getName();
            String fullPath = SplitName.add(prefix, name);
            requiredPropertyNames.add(fullPath);
        }
    }

    @Override
    public void addIndexResolvePropertyNames(Set<String> resolvePropertyNames) {
    }

    @Override
    public void addIndexRestorePropertyNames(Set<String> restorePropertyNames) {
    }

    @Override
    public String getSortableProperty() {
        return null;
    }

    @Override
    public boolean isBeanProperty() {
        return false;
    }

    @Override
    public void readValue(Document doc, Object bean) {
    }

    @Override
    public DocFieldWriter createDocFieldWriter() {
        Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(f, this.properties, this.scalarTypes, this.indexAnalyzer);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Writer
    implements DocFieldWriter {
        private final Field field;
        private final ElPropertyValue[] properties;
        private final ScalarType<?>[] scalarTypes;
        private final Analyzer indexAnalyzer;

        private Writer(Field field, ElPropertyValue[] properties, ScalarType<?>[] scalarTypes, Analyzer indexAnalyzer) {
            this.field = field;
            this.properties = properties;
            this.scalarTypes = scalarTypes;
            this.indexAnalyzer = indexAnalyzer;
        }

        @Override
        public void writeValue(Object bean, Document document) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.properties.length; ++i) {
                Object value = this.properties[i].elGetValue(bean);
                if (value == null) continue;
                String s = (String)this.scalarTypes[i].luceneToIndexValue(value);
                sb.append(s);
                sb.append(" ");
            }
            String s = sb.toString();
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

