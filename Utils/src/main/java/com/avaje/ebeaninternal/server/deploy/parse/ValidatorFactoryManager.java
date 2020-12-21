/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.validation.ValidatorMeta;
import com.avaje.ebean.validation.factory.Validator;
import com.avaje.ebean.validation.factory.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ValidatorFactoryManager {
    static final Logger logger = Logger.getLogger(ValidatorFactoryManager.class.getName());
    Map<Class<?>, ValidatorFactory> factoryMap = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Validator create(Annotation ann, Class<?> type) {
        ValidatorFactoryManager validatorFactoryManager = this;
        synchronized (validatorFactoryManager) {
            ValidatorMeta meta = ann.annotationType().getAnnotation(ValidatorMeta.class);
            if (meta == null) {
                return null;
            }
            Class<?> factoryClass = meta.factory();
            ValidatorFactory factory = this.getFactory(factoryClass);
            return factory.create(ann, type);
        }
    }

    private ValidatorFactory getFactory(Class<?> factoryClass) {
        try {
            ValidatorFactory factory = this.factoryMap.get(factoryClass);
            if (factory == null) {
                factory = (ValidatorFactory)factoryClass.newInstance();
                this.factoryMap.put(factoryClass, factory);
            }
            return factory;
        }
        catch (Exception e) {
            String msg = "Error creating ValidatorFactory " + factoryClass.getName();
            logger.log(Level.SEVERE, msg, e);
            return null;
        }
    }
}

