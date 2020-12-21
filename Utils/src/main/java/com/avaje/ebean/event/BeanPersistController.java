/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.event;

import com.avaje.ebean.event.BeanPersistRequest;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanPersistController {
    public int getExecutionOrder();

    public boolean isRegisterFor(Class<?> var1);

    public boolean preInsert(BeanPersistRequest<?> var1);

    public boolean preUpdate(BeanPersistRequest<?> var1);

    public boolean preDelete(BeanPersistRequest<?> var1);

    public void postInsert(BeanPersistRequest<?> var1);

    public void postUpdate(BeanPersistRequest<?> var1);

    public void postDelete(BeanPersistRequest<?> var1);

    public void postLoad(Object var1, Set<String> var2);
}

