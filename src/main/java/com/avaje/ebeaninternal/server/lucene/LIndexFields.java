/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.queryParser.QueryParser
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.LuceneIndex;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldId;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexFields {
    private static final Logger logger = Logger.getLogger(LIndexFields.class.getName());
    private final String defaultFieldName;
    private final BeanDescriptor<?> descriptor;
    private final LIndexField[] fields;
    private final LIndexField[] readFields;
    private final PathProperties pathProperties;
    private final OrmQueryDetail ormQueryDetail;
    private final HashMap<String, LIndexField> fieldMap = new HashMap();
    private final HashMap<String, LIndexField> sortableExpressionMap = new HashMap();
    private final LinkedHashSet<String> requiredPropertyNames = new LinkedHashSet();
    private final LinkedHashSet<String> resolvePropertyNames = new LinkedHashSet();
    private final LinkedHashSet<String> restorePropertyNames = new LinkedHashSet();
    private final Set<String> nonRestorablePropertyNames;
    private final LIndexFieldId idField;

    public LIndexFields(List<LIndexField> definedFields, BeanDescriptor<?> descriptor, String defaultFieldName) {
        this.descriptor = descriptor;
        this.defaultFieldName = defaultFieldName;
        this.fields = definedFields.toArray(new LIndexField[definedFields.size()]);
        LIndexFieldId tempIdField = null;
        for (LIndexField field : this.fields) {
            String fieldName = field.getName();
            if (field instanceof LIndexFieldId && fieldName.indexOf(46) == -1) {
                tempIdField = (LIndexFieldId)field;
            }
            this.fieldMap.put(fieldName, field);
            field.addIndexRequiredPropertyNames(this.requiredPropertyNames);
            field.addIndexResolvePropertyNames(this.resolvePropertyNames);
            field.addIndexRestorePropertyNames(this.restorePropertyNames);
            String sortableProperty = field.getSortableProperty();
            if (sortableProperty == null) continue;
            this.sortableExpressionMap.put(sortableProperty, field);
        }
        this.idField = tempIdField;
        this.nonRestorablePropertyNames = this.getNonRestorableProperties();
        if (!this.nonRestorablePropertyNames.isEmpty()) {
            logger.info("Index has properties [" + this.nonRestorablePropertyNames + "] that are not stored");
        }
        this.readFields = this.createReadFields();
        this.pathProperties = this.createPathProperties();
        this.ormQueryDetail = this.createOrmQueryDetail();
    }

    public LIndexFieldId getIdField() {
        return this.idField;
    }

    public void registerIndexWithProperties(LuceneIndex luceneIndex) {
        IndexInvalidate invalidate = new IndexInvalidate(this.descriptor.getBeanType().getName());
        for (String prop : this.restorePropertyNames) {
            ElPropertyValue elProp = this.descriptor.getElGetValue(prop);
            if (elProp.isAssocProperty()) {
                elProp.getBeanProperty().getBeanDescriptor().addIndexInvalidate(invalidate);
                continue;
            }
            elProp.getBeanProperty().registerLuceneIndex(luceneIndex);
        }
    }

    private Set<String> getNonRestorableProperties() {
        HashSet<String> nonRestoreable = new HashSet<String>();
        for (String reqrProp : this.requiredPropertyNames) {
            if (this.restorePropertyNames.contains(reqrProp)) continue;
            nonRestoreable.add(reqrProp);
        }
        return nonRestoreable;
    }

    public LIndexField getSortableField(String propertyName) {
        return this.sortableExpressionMap.get(propertyName);
    }

    public QueryParser createQueryParser(String fieldName) {
        LIndexField fld;
        if (fieldName == null) {
            fieldName = this.defaultFieldName;
        }
        if ((fld = this.fieldMap.get(fieldName)) == null) {
            throw new NullPointerException("fieldName [" + fieldName + "] not in index?");
        }
        return fld.createQueryParser();
    }

    public OrmQueryDetail getOrmQueryDetail() {
        return this.ormQueryDetail;
    }

    public Set<String> getResolvePropertyNames() {
        return this.resolvePropertyNames;
    }

    public LinkedHashSet<String> getRequiredPropertyNames() {
        return this.requiredPropertyNames;
    }

    public DocFieldWriter createDocFieldWriter() {
        DocFieldWriter[] dw = new DocFieldWriter[this.fields.length];
        for (int j = 0; j < dw.length; ++j) {
            dw[j] = this.fields[j].createDocFieldWriter();
        }
        return new Writer(dw);
    }

    public void readDocument(Document doc, Object bean) {
        for (LIndexField indexField : this.readFields) {
            indexField.readValue(doc, bean);
        }
    }

    public LIndexField[] getReadFields() {
        return this.readFields;
    }

    private LIndexField[] createReadFields() {
        ArrayList<LIndexField> readFieldList = new ArrayList<LIndexField>();
        for (int i = 0; i < this.fields.length; ++i) {
            LIndexField field = this.fields[i];
            if (!field.isStored() || !field.isBeanProperty()) continue;
            readFieldList.add(field);
        }
        Collections.sort(readFieldList, new Comparator<LIndexField>(){

            @Override
            public int compare(LIndexField o1, LIndexField o2) {
                int v2;
                int v1 = o1.getPropertyOrder();
                return v1 < (v2 = o2.getPropertyOrder()) ? -1 : (v1 == v2 ? 0 : 1);
            }
        });
        return readFieldList.toArray(new LIndexField[readFieldList.size()]);
    }

    private PathProperties createPathProperties() {
        PathProperties pathProps = new PathProperties();
        for (int i = 0; i < this.readFields.length; ++i) {
            LIndexField field = this.readFields[i];
            String propertyName = field.getName();
            ElPropertyValue el = field.getElBeanProperty();
            if (el.getBeanProperty().isId()) {
                propertyName = SplitName.parent(propertyName);
            }
            if (propertyName == null) continue;
            String[] pathProp = SplitName.split(propertyName);
            pathProps.addToPath(pathProp[0], pathProp[1]);
        }
        return pathProps;
    }

    private OrmQueryDetail createOrmQueryDetail() {
        OrmQueryDetail detail = new OrmQueryDetail();
        for (String path : this.pathProperties.getPaths()) {
            Set<String> props = this.pathProperties.get(path);
            detail.getChunk(path, true).setDefaultProperties(null, props);
        }
        return detail;
    }

    static class Writer
    implements DocFieldWriter {
        private final DocFieldWriter[] dw;

        private Writer(DocFieldWriter[] dw) {
            this.dw = dw;
        }

        public void writeValue(Object bean, Document document) {
            for (DocFieldWriter w : this.dw) {
                w.writeValue(bean, document);
            }
        }
    }
}

