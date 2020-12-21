/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Field$Index
 *  org.apache.lucene.document.Field$Store
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexDefnBuilder;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.SpiIndexDefnHelper;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.lucene.document.Field;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexFieldsBuilder
implements SpiIndexDefnHelper {
    private final BeanDescriptor<?> desc;
    private final LinkedHashMap<String, IndexFieldDefn> fieldDefnMap = new LinkedHashMap();

    public LIndexFieldsBuilder(BeanDescriptor<?> desc) {
        this.desc = desc;
    }

    @Override
    public Nested assocOne(String propertyName) {
        BeanProperty beanProperty = this.desc.getBeanProperty(propertyName);
        if (beanProperty instanceof BeanPropertyAssocOne) {
            BeanPropertyAssocOne assocOne = (BeanPropertyAssocOne)beanProperty;
            BeanDescriptor targetDescriptor = assocOne.getTargetDescriptor();
            return new Nested(this, propertyName, targetDescriptor);
        }
        throw new IllegalArgumentException("Expecing " + propertyName + " to be an AssocOne property of " + this.desc.getFullName());
    }

    @Override
    public void addAllFields() {
        Iterator<BeanProperty> it = this.desc.propertiesAll();
        while (it.hasNext()) {
            BeanProperty beanProperty = it.next();
            if (beanProperty instanceof BeanPropertyAssocMany || beanProperty instanceof BeanPropertyAssocOne) continue;
            this.addField(beanProperty.getName());
        }
    }

    @Override
    public IndexFieldDefn addField(String propertyName) {
        return this.addField(propertyName, null);
    }

    @Override
    public IndexFieldDefn addField(String propertyName, IndexFieldDefn.Sortable sortable) {
        return this.addField(propertyName, null, null, sortable);
    }

    @Override
    public IndexFieldDefn addFieldConcat(String fieldName, String ... propertyNames) {
        return this.addFieldConcat(fieldName, Field.Store.NO, Field.Index.ANALYZED, propertyNames);
    }

    @Override
    public IndexFieldDefn addFieldConcat(String fieldName, Field.Store store, Field.Index index, String ... propertyNames) {
        return this.addPrefixFieldConcat(null, fieldName, store, index, propertyNames);
    }

    @Override
    public IndexFieldDefn addPrefixField(String prefix, String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
        String fullPath = prefix + "." + propertyName;
        return this.addField(fullPath, store, index, sortable);
    }

    @Override
    public IndexFieldDefn addPrefixFieldConcat(String prefix, String fieldName, Field.Store store, Field.Index index, String[] propertyNames) {
        if (prefix != null) {
            for (int i = 0; i < propertyNames.length; ++i) {
                propertyNames[i] = prefix + "." + propertyNames;
            }
        }
        IndexFieldDefn fieldDefn = new IndexFieldDefn(fieldName, store, index, null);
        fieldDefn.setPropertyNames(propertyNames);
        this.fieldDefnMap.put(fieldName, fieldDefn);
        return fieldDefn;
    }

    @Override
    public IndexFieldDefn addField(IndexFieldDefn fieldDefn) {
        this.fieldDefnMap.put(fieldDefn.getName(), fieldDefn);
        return fieldDefn;
    }

    @Override
    public IndexFieldDefn addField(String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
        boolean luceneStringType;
        ElPropertyValue prop = this.desc.getElGetValue(propertyName);
        if (prop == null) {
            String msg = "Property [" + propertyName + "] not found on " + this.desc.getFullName();
            throw new NullPointerException(msg);
        }
        BeanProperty beanProperty = prop.getBeanProperty();
        ScalarType<?> scalarType = beanProperty.getScalarType();
        if (store == null) {
            store = this.isLob(scalarType) ? Field.Store.NO : Field.Store.YES;
        }
        boolean bl = luceneStringType = beanProperty.isId() || this.isLuceneString(scalarType.getLuceneType());
        if (index == null) {
            index = beanProperty.isId() || !luceneStringType ? Field.Index.NOT_ANALYZED : Field.Index.ANALYZED;
        }
        IndexFieldDefn fieldDefn = new IndexFieldDefn(propertyName, store, index, sortable);
        this.fieldDefnMap.put(propertyName, fieldDefn);
        if (luceneStringType && index.isAnalyzed() && IndexFieldDefn.Sortable.YES.equals((Object)sortable)) {
            IndexFieldDefn extraFieldDefn = new IndexFieldDefn(propertyName + "_sortonly", Field.Store.NO, Field.Index.NOT_ANALYZED, IndexFieldDefn.Sortable.YES);
            extraFieldDefn.setPropertyName(propertyName);
            this.fieldDefnMap.put(extraFieldDefn.getName(), extraFieldDefn);
        }
        return fieldDefn;
    }

    @Override
    public IndexFieldDefn getField(String fieldName) {
        return this.fieldDefnMap.get(fieldName);
    }

    private boolean isLuceneString(int luceneType) {
        return 0 == luceneType;
    }

    private boolean isLob(ScalarType<?> scalarType) {
        int jdbcType = scalarType.getJdbcType();
        switch (jdbcType) {
            case 2005: {
                return true;
            }
            case 2004: {
                return true;
            }
            case -1: {
                return true;
            }
            case -4: {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<IndexFieldDefn> getFields() {
        ArrayList<IndexFieldDefn> fields = new ArrayList<IndexFieldDefn>();
        fields.addAll(this.fieldDefnMap.values());
        return fields;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Nested
    implements SpiIndexDefnHelper {
        private final String path;
        private final BeanDescriptor<?> targetDescriptor;
        private final SpiIndexDefnHelper parent;

        Nested(SpiIndexDefnHelper parent, String path, BeanDescriptor<?> targetDescriptor) {
            this.parent = parent;
            this.path = path;
            this.targetDescriptor = targetDescriptor;
        }

        @Override
        public IndexDefnBuilder assocOne(String propertyName) {
            BeanProperty beanProperty = this.targetDescriptor.getBeanProperty(propertyName);
            if (beanProperty instanceof BeanPropertyAssocOne) {
                BeanPropertyAssocOne assocOne = (BeanPropertyAssocOne)beanProperty;
                BeanDescriptor targetDescriptor = assocOne.getTargetDescriptor();
                return new Nested(this, propertyName, targetDescriptor);
            }
            throw new IllegalArgumentException("Expecing " + propertyName + " to be an AssocOne property of " + this.targetDescriptor.getFullName());
        }

        @Override
        public void addAllFields() {
            Iterator<BeanProperty> it = this.targetDescriptor.propertiesAll();
            while (it.hasNext()) {
                BeanProperty beanProperty = it.next();
                if (beanProperty instanceof BeanPropertyAssocMany || beanProperty instanceof BeanPropertyAssocOne || beanProperty.isTransient()) continue;
                this.addField(beanProperty.getName());
            }
        }

        @Override
        public IndexFieldDefn addField(IndexFieldDefn fieldDefn) {
            this.parent.addField(fieldDefn);
            return fieldDefn;
        }

        @Override
        public IndexFieldDefn addField(String propertyName) {
            return this.addField(propertyName, null);
        }

        @Override
        public IndexFieldDefn addField(String propertyName, IndexFieldDefn.Sortable sortable) {
            return this.addField(propertyName, null, null, sortable);
        }

        @Override
        public IndexFieldDefn addField(String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
            return this.parent.addPrefixField(this.path, propertyName, store, index, sortable);
        }

        @Override
        public IndexFieldDefn addFieldConcat(String fieldName, String ... propertyNames) {
            return this.addFieldConcat(fieldName, Field.Store.NO, Field.Index.ANALYZED, propertyNames);
        }

        @Override
        public IndexFieldDefn addFieldConcat(String fieldName, Field.Store store, Field.Index index, String ... propertyNames) {
            return this.parent.addPrefixFieldConcat(this.path, fieldName, store, index, propertyNames);
        }

        @Override
        public IndexFieldDefn addPrefixFieldConcat(String prefix, String fieldName, Field.Store store, Field.Index index, String[] propertyNames) {
            String nestedPath = this.path + "." + prefix;
            return this.parent.addPrefixFieldConcat(nestedPath, fieldName, store, index, propertyNames);
        }

        @Override
        public IndexFieldDefn addPrefixField(String prefix, String propertyName, Field.Store store, Field.Index index, IndexFieldDefn.Sortable sortable) {
            String nestedPath = prefix + "." + propertyName;
            return this.addField(nestedPath, store, index, sortable);
        }

        @Override
        public IndexFieldDefn getField(String fieldName) {
            return this.parent.getField(fieldName);
        }

        @Override
        public List<IndexFieldDefn> getFields() {
            return this.parent.getFields();
        }
    }
}

