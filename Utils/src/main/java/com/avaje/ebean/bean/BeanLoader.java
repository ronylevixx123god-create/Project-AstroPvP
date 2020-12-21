/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.bean;

import com.avaje.ebean.bean.EntityBeanIntercept;

public interface BeanLoader {
    public String getName();

    public void loadBean(EntityBeanIntercept var1);
}

