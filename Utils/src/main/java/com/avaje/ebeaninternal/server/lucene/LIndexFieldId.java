/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.Field
 *  org.apache.lucene.document.Fieldable
 *  org.apache.lucene.index.Term
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import com.avaje.ebeaninternal.server.lucene.FieldFactory;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldBase;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.Term;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class LIndexFieldId
extends LIndexFieldBase {
    private final IdBinder idBinder;

    public LIndexFieldId(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, ElPropertyValue property, IdBinder idBinder) {
        super(queryAnalyzer, fieldName, 0, property, fieldFactory);
        this.idBinder = idBinder;
    }

    public Term createTerm(Object id) {
        String termVal = this.idBinder.writeTerm(id);
        return new Term(this.fieldName, termVal);
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
        String v = doc.get(this.fieldName);
        if (v != null) {
            Object id = this.idBinder.readTerm(v);
            this.property.elSetValue(bean, id, true, false);
        }
    }

    @Override
    public DocFieldWriter createDocFieldWriter() {
        Field f = (Field)this.fieldFactory.createFieldable();
        return new Writer(this.property, f, this.idBinder);
    }

    private static class Writer
    implements DocFieldWriter {
        private final IdBinder idBinder;
        private final ElPropertyValue property;
        private final Field field;

        Writer(ElPropertyValue property, Field field, IdBinder idBinder) {
            this.property = property;
            this.field = field;
            this.idBinder = idBinder;
        }

        public void writeValue(Object bean, Document document) {
            Object value = this.property.elGetValue(bean);
            if (value != null) {
                System.out.println("- write " + this.field.name() + " " + value);
                String writeTerm = this.idBinder.writeTerm(value);
                this.field.setValue(writeTerm);
                document.add((Fieldable)this.field);
            }
        }
    }
}

