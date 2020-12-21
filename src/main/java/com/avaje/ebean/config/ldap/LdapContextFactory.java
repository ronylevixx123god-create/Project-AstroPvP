/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config.ldap;

import javax.naming.directory.DirContext;

public interface LdapContextFactory {
    public DirContext createContext();
}

