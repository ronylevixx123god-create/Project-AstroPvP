/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.subclass;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.enhance.agent.EnhanceConstants;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.subclass.SubClassFactory;
import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SubClassManager
implements EnhanceConstants {
    private static final Logger logger = Logger.getLogger(SubClassManager.class.getName());
    private final ConcurrentHashMap<String, Class<?>> clzMap;
    private final SubClassFactory subclassFactory;
    private final String serverName;
    private final int logLevel;

    public SubClassManager(ServerConfig serverConfig) {
        String s = serverConfig.getProperty("subClassManager.preferContextClassloader", "true");
        final boolean preferContext = "true".equalsIgnoreCase(s);
        this.serverName = serverConfig.getName();
        this.logLevel = serverConfig.getEnhanceLogLevel();
        this.clzMap = new ConcurrentHashMap();
        try {
            this.subclassFactory = (SubClassFactory)AccessController.doPrivileged(new PrivilegedExceptionAction(){

                public Object run() {
                    ClassLoader cl = ClassUtil.getClassLoader(this.getClass(), preferContext);
                    logger.info("SubClassFactory parent ClassLoader [" + cl.getClass().getName() + "]");
                    return new SubClassFactory(cl, SubClassManager.this.logLevel);
                }
            });
        }
        catch (PrivilegedActionException e) {
            throw new PersistenceException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> resolve(String name) {
        SubClassManager subClassManager = this;
        synchronized (subClassManager) {
            String superName = SubClassUtil.getSuperClassName(name);
            Class<?> clz = this.clzMap.get(superName);
            if (clz == null) {
                clz = this.createClass(superName);
                this.clzMap.put(superName, clz);
            }
            return clz;
        }
    }

    private Class<?> createClass(String name) {
        try {
            Class<?> superClass = Class.forName(name, true, this.subclassFactory.getParent());
            return this.subclassFactory.create(superClass, this.serverName);
        }
        catch (Exception ex) {
            String m = "Error creating subclass for [" + name + "]";
            throw new PersistenceException(m, ex);
        }
    }
}

