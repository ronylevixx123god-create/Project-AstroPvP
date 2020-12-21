/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.deploy.ParamTypeUtil;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PersistListenerManager {
    private static final Logger logger = Logger.getLogger(PersistListenerManager.class.getName());
    private final List<BeanPersistListener<?>> list;

    public PersistListenerManager(BootupClasses bootupClasses) {
        this.list = bootupClasses.getBeanPersistListeners();
    }

    public int getRegisterCount() {
        return this.list.size();
    }

    public <T> void addPersistListeners(DeployBeanDescriptor<T> deployDesc) {
        for (int i = 0; i < this.list.size(); ++i) {
            BeanPersistListener<?> c = this.list.get(i);
            if (!PersistListenerManager.isRegisterFor(deployDesc.getBeanType(), c)) continue;
            logger.fine("BeanPersistListener on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
            deployDesc.addPersistListener(c);
        }
    }

    public static boolean isRegisterFor(Class<?> beanType, BeanPersistListener<?> c) {
        Class<?> listenerEntity = PersistListenerManager.getEntityClass(c.getClass());
        return beanType.equals(listenerEntity);
    }

    private static Class<?> getEntityClass(Class<?> controller) {
        Class<?> cls = ParamTypeUtil.findParamType(controller, BeanPersistListener.class);
        if (cls == null) {
            String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
            throw new PersistenceException(msg);
        }
        return cls;
    }
}

