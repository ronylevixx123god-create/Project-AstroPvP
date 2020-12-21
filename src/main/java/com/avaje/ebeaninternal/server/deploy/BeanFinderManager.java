/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanFinder;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanFinderManager {
    public int getRegisterCount();

    public int createBeanFinders(List<Class<?>> var1);

    public <T> BeanFinder<T> getBeanFinder(Class<T> var1);
}

