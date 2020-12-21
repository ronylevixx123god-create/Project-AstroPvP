/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.Fieldable
 *  org.apache.lucene.document.NumericField
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
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class LIndexFieldNumeric
extends LIndexFieldBase {
    public LIndexFieldNumeric(Analyzer queryAnalyzer, String fieldName, FieldFactory fieldFactory, int luceneType, ElPropertyValue property) {
        super(queryAnalyzer, fieldName, luceneType, property, fieldFactory);
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
        return this.isIndexed() ? this.propertyName : null;
    }

    @Override
    public DocFieldWriter createDocFieldWriter() {
        NumericField f = (NumericField)this.fieldFactory.createFieldable();
        return new Writer(this.luceneType, this.property, this.scalarType, f);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Writer
    implements DocFieldWriter {
        private final int luceneType;
        private final ElPropertyValue property;
        private final ScalarType<?> scalarType;
        private final NumericField field;

        Writer(int luceneType, ElPropertyValue property, ScalarType<?> scalarType, NumericField field) {
            this.luceneType = luceneType;
            this.property = property;
            this.scalarType = scalarType;
            this.field = field;
        }

        @Override
        public void writeValue(Object bean, Document document) {
            Object value = this.property.elGetValue(bean);
            if (value != null) {
                System.out.println("- write " + this.field.name() + " " + value);
                value = this.scalarType.luceneToIndexValue(value);
                this.setValueToField(value);
                document.add((Fieldable)this.field);
            }
        }

        protected void setValueToField(Object value) {
            switch (this.luceneType) {
                case 1: {
                    this.field.setIntValue(((Integer)value).intValue());
                    break;
                }
                case 2: {
                    this.field.setLongValue(((Long)value).longValue());
                    break;
                }
                case 5: {
                    this.field.setLongValue(((Long)value).longValue());
                    break;
                }
                case 6: {
                    this.field.setLongValue(((Long)value).longValue());
                    break;
                }
                case 3: {
                    this.field.setDoubleValue(((Double)value).doubleValue());
                    break;
                }
                case 4: {
                    this.field.setFloatValue(((Float)value).floatValue());
                    break;
                }
                default: {
                    throw new RuntimeException("Unhandled type " + this.luceneType);
                }
            }
        }
    }
}

