/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompoundRoot;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
import com.avaje.ebeaninternal.server.type.CtCompoundPropertyElAdapter;
import com.avaje.ebeaninternal.server.type.CtCompoundType;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanPropertyCompound
extends BeanProperty {
    private final CtCompoundType<?> compoundType;
    private final ScalarTypeConverter typeConverter;
    private final BeanProperty[] scalarProperties;
    private final LinkedHashMap<String, BeanProperty> propertyMap = new LinkedHashMap();
    private final LinkedHashMap<String, CtCompoundPropertyElAdapter> nonScalarMap = new LinkedHashMap();
    private final BeanPropertyCompoundRoot root;

    public BeanPropertyCompound(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertyCompound deploy) {
        super(owner, descriptor, deploy);
        this.compoundType = deploy.getCompoundType();
        this.typeConverter = deploy.getTypeConverter();
        this.root = deploy.getFlatProperties(owner, descriptor);
        this.scalarProperties = this.root.getScalarProperties();
        for (int i = 0; i < this.scalarProperties.length; ++i) {
            this.propertyMap.put(this.scalarProperties[i].getName(), this.scalarProperties[i]);
        }
        List<CtCompoundProperty> nonScalarPropsList = this.root.getNonScalarProperties();
        for (int i = 0; i < nonScalarPropsList.size(); ++i) {
            CtCompoundProperty ctProp = nonScalarPropsList.get(i);
            CtCompoundPropertyElAdapter adapter = new CtCompoundPropertyElAdapter(ctProp);
            this.nonScalarMap.put(ctProp.getRelativeName(), adapter);
        }
    }

    @Override
    public void initialise() {
        if (!this.isTransient && this.compoundType == null) {
            String msg = "No cvoInternalType assigned to " + this.descriptor.getFullName() + "." + this.getName();
            throw new RuntimeException(msg);
        }
    }

    @Override
    public void setDeployOrder(int deployOrder) {
        this.deployOrder = deployOrder;
        for (CtCompoundPropertyElAdapter adapter : this.nonScalarMap.values()) {
            adapter.setDeployOrder(deployOrder);
        }
    }

    public Object getValueUnderlying(Object bean) {
        Object value = this.getValue(bean);
        if (this.typeConverter != null) {
            value = this.typeConverter.unwrapValue(value);
        }
        return value;
    }

    @Override
    public Object getValue(Object bean) {
        return super.getValue(bean);
    }

    @Override
    public Object getValueIntercept(Object bean) {
        return super.getValueIntercept(bean);
    }

    @Override
    public void setValue(Object bean, Object value) {
        super.setValue(bean, value);
    }

    @Override
    public void setValueIntercept(Object bean, Object value) {
        super.setValueIntercept(bean, value);
    }

    @Override
    public ElPropertyValue buildElPropertyValue(String propName, String remainder, ElPropertyChainBuilder chain, boolean propertyDeploy) {
        if (chain == null) {
            chain = new ElPropertyChainBuilder(true, propName);
        }
        chain.add(this);
        BeanProperty p = this.propertyMap.get(remainder);
        if (p != null) {
            return chain.add(p).build();
        }
        CtCompoundPropertyElAdapter elAdapter = this.nonScalarMap.get(remainder);
        if (elAdapter == null) {
            throw new RuntimeException("property [" + remainder + "] not found in " + this.getFullBeanName());
        }
        return chain.add(elAdapter).build();
    }

    @Override
    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
        if (!this.isTransient) {
            for (int i = 0; i < this.scalarProperties.length; ++i) {
                this.scalarProperties[i].appendSelect(ctx, subQuery);
            }
        }
    }

    public BeanProperty[] getScalarProperties() {
        return this.scalarProperties;
    }

    @Override
    public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
        boolean assignable = type == null || this.owningType.isAssignableFrom(type);
        Object v = this.compoundType.read(ctx.getDataReader());
        if (assignable) {
            this.setValue(bean, v);
        }
        return v;
    }

    @Override
    public Object read(DbReadContext ctx) throws SQLException {
        Object v = this.compoundType.read(ctx.getDataReader());
        if (this.typeConverter != null) {
            v = this.typeConverter.wrapValue(v);
        }
        return v;
    }

    @Override
    public void loadIgnore(DbReadContext ctx) {
        this.compoundType.loadIgnore(ctx.getDataReader());
    }

    @Override
    public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
        sqlBeanLoad.load(this);
    }

    @Override
    public Object elGetReference(Object bean) {
        return bean;
    }

    @Override
    public void jsonWrite(WriteJsonContext ctx, Object bean) {
        Object valueObject = this.getValueIntercept(bean);
        this.compoundType.jsonWrite(ctx, valueObject, this.name);
    }

    @Override
    public void jsonRead(ReadJsonContext ctx, Object bean) {
        Object objValue = this.compoundType.jsonRead(ctx);
        this.setValue(bean, objValue);
    }
}

