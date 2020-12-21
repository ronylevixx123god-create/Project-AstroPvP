/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
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
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class LIndexFieldBinary
extends LIndexFieldBase {
    public LIndexFieldBinary(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue property) {
        super(queryAnalyzer, fieldName, 7, property, fieldFactory);
    }

    @Override
    public void addIndexResolvePropertyNames(Set<String> resolvePropertyNames) {
    }

    @Override
    public void addIndexRestorePropertyNames(Set<String> restorePropertyNames) {
        if (this.propertyName != null && this.isStored()) {
            restorePropertyNames.add(this.propertyName);
        }
    }

    @Override
    public String getSortableProperty() {
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
        return new Writer(this.property, this.scalarType, f);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Writer
    implements DocFieldWriter {
        private final ElPropertyValue property;
        private final ScalarType<?> scalarType;
        private final Field field;

        private Writer(ElPropertyValue property, ScalarType<?> scalarType, Field field) {
            this.property = property;
            this.scalarType = scalarType;
            this.field = field;
        }

        @Override
        public void writeValue(Object bean, Document document) {
            Object value = this.property.elGetValue(bean);
            if (value != null) {
                byte[] s = (byte[])this.scalarType.luceneToIndexValue(value);
                this.field.setValue(s);
                document.add((Fieldable)this.field);
            }
        }
    }
}

