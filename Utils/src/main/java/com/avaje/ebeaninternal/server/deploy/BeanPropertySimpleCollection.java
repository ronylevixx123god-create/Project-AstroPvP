/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.util.Iterator;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanPropertySimpleCollection<T>
extends BeanPropertyAssocMany<T> {
    private final ScalarType<T> collectionScalarType;

    public BeanPropertySimpleCollection(BeanDescriptorMap owner, BeanDescriptor<?> descriptor, DeployBeanPropertySimpleCollection<T> deploy) {
        super(owner, descriptor, deploy);
        this.collectionScalarType = deploy.getCollectionScalarType();
    }

    @Override
    public void initialise() {
        super.initialise();
    }

    @Override
    public void copyProperty(Object sourceBean, Object destBean, CopyContext ctx, int maxDepth) {
        Object srcValue = this.getValueUnderlying(sourceBean);
        Object dstValue = this.help.copyCollection(srcValue, ctx, maxDepth, destBean);
        this.setValue(destBean, dstValue);
    }

    @Override
    public Attribute createAttribute(Object bean) {
        Object v = this.getValue(bean);
        if (v == null) {
            return null;
        }
        if (this.ldapAttributeAdapter != null) {
            return this.ldapAttributeAdapter.createAttribute(v);
        }
        BasicAttribute attrs = new BasicAttribute(this.getDbColumn());
        Iterator<?> it = this.help.getIterator(v);
        if (it != null) {
            while (it.hasNext()) {
                Object beanValue = it.next();
                Object attrValue = this.collectionScalarType.toJdbcType(beanValue);
                attrs.add(attrValue);
            }
        }
        return attrs;
    }

    @Override
    public void setAttributeValue(Object bean, Attribute attr) {
        try {
            if (attr != null) {
                Object beanValue;
                if (this.ldapAttributeAdapter != null) {
                    beanValue = this.ldapAttributeAdapter.readAttribute(attr);
                } else {
                    boolean vanilla = true;
                    beanValue = this.help.createEmpty(vanilla);
                    BeanCollectionAdd collAdd = this.help.getBeanCollectionAdd(beanValue, this.mapKey);
                    NamingEnumeration<?> en = attr.getAll();
                    while (en.hasMoreElements()) {
                        Object attrValue = en.nextElement();
                        T collValue = this.collectionScalarType.toBeanType(attrValue);
                        collAdd.addBean(collValue);
                    }
                }
                this.setValue(bean, beanValue);
            }
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }
}

