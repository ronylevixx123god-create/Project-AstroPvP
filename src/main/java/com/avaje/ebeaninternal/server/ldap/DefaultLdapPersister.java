/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ldap;

import com.avaje.ebean.config.ldap.LdapContextFactory;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.ldap.LdapPersistBeanRequest;
import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapName;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultLdapPersister {
    private static final Logger logger = Logger.getLogger(DefaultLdapPersister.class.getName());
    private final LdapContextFactory contextFactory;

    public DefaultLdapPersister(LdapContextFactory dirContextFactory) {
        this.contextFactory = dirContextFactory;
    }

    public int persist(LdapPersistBeanRequest<?> request) {
        switch (request.getType()) {
            case INSERT: {
                return this.insert(request);
            }
            case UPDATE: {
                return this.update(request);
            }
            case DELETE: {
                return this.delete(request);
            }
        }
        throw new LdapPersistenceException("Invalid type " + (Object)((Object)request.getType()));
    }

    private int insert(LdapPersistBeanRequest<?> request) {
        DirContext dc = this.contextFactory.createContext();
        LdapName name = request.createLdapName();
        Attributes attrs = this.createAttributes(request, false, request.getLoadedProperties());
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Ldap Insert Name:" + name + " Attrs:" + attrs);
        }
        try {
            dc.bind(name, null, attrs);
            return 1;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }

    private int delete(LdapPersistBeanRequest<?> request) {
        DirContext dc = this.contextFactory.createContext();
        LdapName name = request.createLdapName();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Ldap Delete Name:" + name);
        }
        try {
            dc.unbind(name);
            return 1;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }

    private int update(LdapPersistBeanRequest<?> request) {
        LdapName name = request.createLdapName();
        Set<String> updatedProperties = request.getUpdatedProperties();
        if (updatedProperties == null || updatedProperties.isEmpty()) {
            logger.info("Ldap Update has no changed properties?  Name:" + name);
            return 0;
        }
        DirContext dc = this.contextFactory.createContext();
        Attributes attrs = this.createAttributes(request, true, updatedProperties);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Ldap Update Name:" + name + " Attrs:" + attrs);
        }
        try {
            dc.modifyAttributes(name, 2, attrs);
            return 1;
        }
        catch (NamingException e) {
            throw new LdapPersistenceException(e);
        }
    }

    private Attributes createAttributes(LdapPersistBeanRequest<?> request, boolean update, Set<String> props) {
        BeanDescriptor desc = request.getBeanDescriptor();
        Attributes attrs = desc.createAttributes();
        attrs = update ? new BasicAttributes(true) : desc.createAttributes();
        Object bean = request.getBean();
        if (props != null) {
            for (String propName : props) {
                BeanProperty p = desc.getBeanPropertyFromPath(propName);
                Attribute attr = p.createAttribute(bean);
                if (attr == null) continue;
                attrs.put(attr);
            }
        } else {
            Iterator<BeanProperty> it = desc.propertiesAll();
            while (it.hasNext()) {
                BeanProperty p = it.next();
                Attribute attr = p.createAttribute(bean);
                if (attr == null) continue;
                attrs.put(attr);
            }
        }
        return attrs;
    }
}

