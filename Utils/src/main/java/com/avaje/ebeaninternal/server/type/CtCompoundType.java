/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.CompoundType;
import com.avaje.ebean.config.CompoundTypeProperty;
import com.avaje.ebean.text.json.JsonElement;
import com.avaje.ebean.text.json.JsonElementObject;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarDataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class CtCompoundType<V>
implements ScalarDataReader<V> {
    private final Class<V> cvoClass;
    private final CompoundType<V> cvoType;
    private final Map<String, CompoundTypeProperty<V, ?>> propertyMap;
    private final ScalarDataReader<Object>[] propReaders;
    private final CompoundTypeProperty<V, ?>[] properties;

    public CtCompoundType(Class<V> cvoClass, CompoundType<V> cvoType, ScalarDataReader<Object>[] propReaders) {
        this.cvoClass = cvoClass;
        this.cvoType = cvoType;
        this.properties = cvoType.getProperties();
        this.propReaders = propReaders;
        this.propertyMap = new LinkedHashMap();
        for (CompoundTypeProperty<V, ?> cp : this.properties) {
            this.propertyMap.put(cp.getName(), cp);
        }
    }

    public String toString() {
        return this.cvoClass.toString();
    }

    public Class<V> getCompoundTypeClass() {
        return this.cvoClass;
    }

    public V create(Object[] propertyValues) {
        return this.cvoType.create(propertyValues);
    }

    public V create(Map<String, Object> valueMap) {
        if (valueMap.size() != this.properties.length) {
            return null;
        }
        Object[] propertyValues = new Object[this.properties.length];
        for (int i = 0; i < this.properties.length; ++i) {
            propertyValues[i] = valueMap.get(this.properties[i].getName());
            if (propertyValues[i] != null) continue;
            String m = "Null value for " + this.properties[i].getName() + " in map " + valueMap;
            throw new RuntimeException(m);
        }
        return this.create(propertyValues);
    }

    public CompoundTypeProperty<V, ?>[] getProperties() {
        return this.cvoType.getProperties();
    }

    public Object[] getPropertyValues(V valueObject) {
        Object[] values = new Object[this.properties.length];
        for (int i = 0; i < this.properties.length; ++i) {
            values[i] = this.properties[i].getValue(valueObject);
        }
        return values;
    }

    @Override
    public V read(DataReader source) throws SQLException {
        boolean nullValue = false;
        Object[] values = new Object[this.propReaders.length];
        for (int i = 0; i < this.propReaders.length; ++i) {
            Object o;
            values[i] = o = this.propReaders[i].read(source);
            if (o != null) continue;
            nullValue = true;
        }
        if (nullValue) {
            return null;
        }
        return this.create(values);
    }

    @Override
    public void loadIgnore(DataReader dataReader) {
        for (int i = 0; i < this.propReaders.length; ++i) {
            this.propReaders[i].loadIgnore(dataReader);
        }
    }

    @Override
    public void bind(DataBind b, V value) throws SQLException {
        CompoundTypeProperty<V, ?>[] props = this.cvoType.getProperties();
        for (int i = 0; i < props.length; ++i) {
            Object o = props[i].getValue(value);
            this.propReaders[i].bind(b, o);
        }
    }

    @Override
    public void accumulateScalarTypes(String parent, CtCompoundTypeScalarList list) {
        CompoundTypeProperty<V, ?>[] props = this.cvoType.getProperties();
        for (int i = 0; i < this.propReaders.length; ++i) {
            String propName = this.getFullPropName(parent, props[i].getName());
            list.addCompoundProperty(propName, this, props[i]);
            this.propReaders[i].accumulateScalarTypes(propName, list);
        }
    }

    private String getFullPropName(String parent, String propName) {
        if (parent == null) {
            return propName;
        }
        return parent + "." + propName;
    }

    public Object jsonRead(ReadJsonContext ctx) {
        if (!ctx.readObjectBegin()) {
            return null;
        }
        JsonElementObject jsonObject = new JsonElementObject();
        while (ctx.readKeyNext()) {
            String propName = ctx.getTokenKey();
            JsonElement unmappedJson = ctx.readUnmappedJson(propName);
            jsonObject.put(propName, unmappedJson);
            if (ctx.readValueNext()) continue;
            break;
        }
        return this.readJsonElementObject(ctx, jsonObject);
    }

    private Object readJsonElementObject(ReadJsonContext ctx, JsonElementObject jsonObject) {
        boolean nullValue = false;
        Object[] values = new Object[this.propReaders.length];
        for (int i = 0; i < this.propReaders.length; ++i) {
            String propName = this.properties[i].getName();
            JsonElement jsonElement = jsonObject.get(propName);
            values[i] = this.propReaders[i] instanceof CtCompoundType ? ((CtCompoundType)this.propReaders[i]).readJsonElementObject(ctx, (JsonElementObject)jsonElement) : ((ScalarType)this.propReaders[i]).jsonFromString(jsonElement.toPrimitiveString(), ctx.getValueAdapter());
            if (values[i] != null) continue;
            nullValue = true;
        }
        if (nullValue) {
            return null;
        }
        return this.create(values);
    }

    public void jsonWrite(WriteJsonContext ctx, Object valueObject, String propertyName) {
        if (valueObject == null) {
            ctx.beginAssocOneIsNull(propertyName);
        } else {
            ctx.pushParentBean(valueObject);
            ctx.beginAssocOne(propertyName);
            this.jsonWriteProps(ctx, valueObject, propertyName);
            ctx.endAssocOne();
            ctx.popParentBean();
        }
    }

    private void jsonWriteProps(WriteJsonContext ctx, Object valueObject, String propertyName) {
        ctx.appendObjectBegin();
        WriteJsonContext.WriteBeanState prevState = ctx.pushBeanState(valueObject);
        for (int i = 0; i < this.properties.length; ++i) {
            String propName = this.properties[i].getName();
            Object value = this.properties[i].getValue(valueObject);
            if (this.propReaders[i] instanceof CtCompoundType) {
                ((CtCompoundType)this.propReaders[i]).jsonWrite(ctx, value, propName);
                continue;
            }
            String js = ((ScalarType)this.propReaders[i]).jsonToString(value, ctx.getValueAdapter());
            ctx.appendKeyValue(propName, js);
        }
        ctx.pushPreviousState(prevState);
        ctx.appendObjectEnd();
    }
}

