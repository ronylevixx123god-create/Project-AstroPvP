/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebeaninternal.server.deploy.BeanFinderManager;
import com.avaje.ebeaninternal.server.deploy.ParamTypeUtil;
import java.util.HashMap;
import java.util.List;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultBeanFinderManager
implements BeanFinderManager {
    HashMap<Class<?>, BeanFinder<?>> registerFor = new HashMap();

    @Override
    public int createBeanFinders(List<Class<?>> finderClassList) {
        for (Class<?> cls : finderClassList) {
            Class<?> entityType = this.getEntityClass(cls);
            try {
                BeanFinder beanFinder = (BeanFinder)cls.newInstance();
                this.registerFor.put(entityType, beanFinder);
            }
            catch (Exception ex) {
                throw new PersistenceException(ex);
            }
        }
        return this.registerFor.size();
    }

    @Override
    public int getRegisterCount() {
        return this.registerFor.size();
    }

    @Override
    public <T> BeanFinder<T> getBeanFinder(Class<T> entityType) {
        return this.registerFor.get(entityType);
    }

    private Class<?> getEntityClass(Class<?> controller) {
        Class<?> cls = ParamTypeUtil.findParamType(controller, BeanFinder.class);
        if (cls == null) {
            String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
            throw new PersistenceException(msg);
        }
        return cls;
    }
}

