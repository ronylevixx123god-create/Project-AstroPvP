/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Field$Index
 *  org.apache.lucene.document.Field$Store
 *  org.apache.lucene.index.IndexWriter$MaxFieldLength
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.DefaultLuceneIndexManager;
import com.avaje.ebeaninternal.server.lucene.FieldFactory;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldBinary;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldId;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldNumeric;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldString;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldStringConcat;
import com.avaje.ebeaninternal.server.lucene.LIndexFields;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldsBuilder;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexFactory {
    private final DefaultLuceneIndexManager manager;

    public LIndexFactory(DefaultLuceneIndexManager manager) {
        this.manager = manager;
    }

    public LIndex create(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor) throws IOException {
        Analyzer defaultAnalyzer = this.manager.getDefaultAnalyzer();
        return new Factory(indexDefn, descriptor, this.manager, defaultAnalyzer).create();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class Factory {
        private final Analyzer defaultAnalyzer;
        private final DefaultLuceneIndexManager manager;
        private final IndexDefn<?> indexDefn;
        private final BeanDescriptor<?> descriptor;

        private Factory(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor, DefaultLuceneIndexManager manager, Analyzer defaultAnalyzer) {
            this.indexDefn = indexDefn;
            this.descriptor = descriptor;
            this.defaultAnalyzer = defaultAnalyzer;
            this.manager = manager;
        }

        public LIndex create() throws IOException {
            IndexWriter.MaxFieldLength maxFieldLength;
            LIndexFieldsBuilder helper = new LIndexFieldsBuilder(this.descriptor);
            this.indexDefn.initialise(helper);
            ArrayList<LIndexField> definedFields = new ArrayList<LIndexField>();
            List<IndexFieldDefn> fields = this.indexDefn.getFields();
            for (int i = 0; i < fields.size(); ++i) {
                IndexFieldDefn fieldDefn = fields.get(i);
                LIndexField field = this.creatField(fieldDefn);
                definedFields.add(field);
            }
            String defaultField = this.indexDefn.getDefaultField();
            LIndexFields fieldGroup = new LIndexFields(definedFields, this.descriptor, defaultField);
            Analyzer analyzer = this.indexDefn.getAnalyzer();
            if (analyzer == null) {
                analyzer = this.defaultAnalyzer;
            }
            if ((maxFieldLength = this.indexDefn.getMaxFieldLength()) == null) {
                maxFieldLength = IndexWriter.MaxFieldLength.UNLIMITED;
            }
            String indexName = this.indexDefn.getClass().getName();
            String indexDir = this.manager.getIndexDirectory(indexName);
            return new LIndex(this.manager, indexName, indexDir, analyzer, maxFieldLength, this.descriptor, fieldGroup, this.indexDefn.getUpdateSinceProperties());
        }

        private ElPropertyValue getProperty(String name) {
            ElPropertyValue prop = this.descriptor.getElGetValue(name);
            if (prop == null) {
                String msg = "Property [" + name + "] not found on " + this.descriptor.getFullName();
                throw new NullPointerException(msg);
            }
            return prop;
        }

        private LIndexField creatField(IndexFieldDefn fieldDefn) {
            String fieldName = fieldDefn.getName();
            Analyzer queryAnalyzer = this.getQueryAnalyzer(fieldDefn);
            Field.Store store = fieldDefn.getStore();
            Field.Index index = fieldDefn.getIndex();
            int luceneType = 0;
            Analyzer indexAnalyzer = fieldDefn.getIndexAnalyzer();
            float boost = fieldDefn.getBoost();
            String[] propertyNames = fieldDefn.getPropertyNames();
            if (propertyNames != null && propertyNames.length > 0) {
                ElPropertyValue[] props = new ElPropertyValue[propertyNames.length];
                for (int i = 0; i < props.length; ++i) {
                    props[i] = this.getProperty(propertyNames[i]);
                }
                FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldStringConcat(queryAnalyzer, fieldName, fieldFactory, props, indexAnalyzer);
            }
            ElPropertyValue prop = this.getProperty(fieldDefn.getPropertyName());
            BeanProperty beanProperty = prop.getBeanProperty();
            ScalarType<?> scalarType = beanProperty.getScalarType();
            luceneType = scalarType.getLuceneType();
            if (beanProperty.isId()) {
                IdBinder idBinder = beanProperty.getBeanDescriptor().getIdBinder();
                FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldId(queryAnalyzer, fieldName, fieldFactory, prop, idBinder);
            }
            if (luceneType == 7) {
                FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldBinary(queryAnalyzer, fieldName, fieldFactory, prop);
            }
            if (luceneType == 0) {
                FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldString(queryAnalyzer, fieldName, fieldFactory, prop, indexAnalyzer);
            }
            int precisionStep = fieldDefn.getPrecisionStep();
            if (precisionStep < 0) {
                precisionStep = 8;
            }
            FieldFactory fieldFactory = FieldFactory.numeric(fieldName, store, index, boost, precisionStep);
            return new LIndexFieldNumeric(queryAnalyzer, fieldName, fieldFactory, luceneType, prop);
        }

        private Analyzer getQueryAnalyzer(IndexFieldDefn fieldDefn) {
            Analyzer analyzer = fieldDefn.getQueryAnalyzer();
            if (analyzer == null) {
                analyzer = this.indexDefn.getAnalyzer();
            }
            return analyzer == null ? this.defaultAnalyzer : analyzer;
        }
    }
}

