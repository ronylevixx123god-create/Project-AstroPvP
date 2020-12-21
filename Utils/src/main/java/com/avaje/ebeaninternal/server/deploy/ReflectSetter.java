/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;

public class ReflectSetter {
    public static BeanReflectSetter create(DeployBeanProperty prop) {
        return new NeverCalled(prop.getFullBeanName());
    }

    public static class NeverCalled
    implements BeanReflectSetter {
        private final String property;

        public NeverCalled(String property) {
            this.property = property;
        }

        public void set(Object bean, Object value) {
            throw new RuntimeException("Should never be called on " + this.property);
        }

        public void setIntercept(Object bean, Object value) {
            throw new RuntimeException("Should never be called on " + this.property);
        }
    }
}

