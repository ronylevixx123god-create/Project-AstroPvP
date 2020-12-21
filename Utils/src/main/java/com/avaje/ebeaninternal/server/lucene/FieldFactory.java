/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Field
 *  org.apache.lucene.document.Field$Index
 *  org.apache.lucene.document.Field$Store
 *  org.apache.lucene.document.Fieldable
 *  org.apache.lucene.document.NumericField
 */
package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;

public class FieldFactory {
    private final boolean numericField;
    private final String fieldName;
    private final Field.Store store;
    private final Field.Index index;
    private final float boost;
    private final int precisionStep;

    public static FieldFactory numeric(String fieldName, Field.Store store, Field.Index index, float boost, int precisionStep) {
        return new FieldFactory(true, fieldName, store, index, boost, precisionStep);
    }

    public static FieldFactory normal(String fieldName, Field.Store store, Field.Index index, float boost) {
        return new FieldFactory(false, fieldName, store, index, boost, 0);
    }

    private FieldFactory(boolean numericField, String fieldName, Field.Store store, Field.Index index, float boost, int precisionStep) {
        this.numericField = numericField;
        this.fieldName = fieldName;
        this.store = store;
        this.index = index;
        this.boost = boost;
        this.precisionStep = precisionStep;
    }

    public Fieldable createFieldable() {
        return this.numericField ? this.createNumericField() : this.createNormalField();
    }

    private Fieldable createNormalField() {
        Field f = new Field(this.fieldName, "", this.store, this.index);
        if (this.boost > 0.0f) {
            f.setBoost(this.boost);
        }
        return f;
    }

    private Fieldable createNumericField() {
        boolean indexed = this.index.isIndexed();
        NumericField f = new NumericField(this.fieldName, this.precisionStep, this.store, indexed);
        if (this.boost > 0.0f) {
            f.setBoost(this.boost);
        }
        return f;
    }
}

