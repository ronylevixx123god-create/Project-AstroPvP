/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

public interface LdapAttributeAdapter {
    public Object readAttribute(Attribute var1) throws NamingException;

    public Attribute createAttribute(Object var1);
}

