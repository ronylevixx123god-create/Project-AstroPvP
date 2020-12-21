/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.config.GlobalProperties;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DebugLazyLoad {
    private final String[] ignoreList = this.buildLazyLoadIgnoreList();
    private final boolean debug;

    public DebugLazyLoad(boolean lazyLoadDebug) {
        this.debug = lazyLoadDebug;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public StackTraceElement getStackTraceElement(Class<?> beanType) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; ++i) {
            if (!this.isStackLine(stackTrace[i], beanType)) continue;
            return stackTrace[i];
        }
        return null;
    }

    private boolean isStackLine(StackTraceElement element, Class<?> beanType) {
        String stackClass = element.getClassName();
        if (this.isBeanClass(beanType, stackClass)) {
            return false;
        }
        for (int i = 0; i < this.ignoreList.length; ++i) {
            if (!stackClass.startsWith(this.ignoreList[i])) continue;
            return false;
        }
        return true;
    }

    private boolean isBeanClass(Class<?> beanType, String stackClass) {
        if (stackClass.startsWith(beanType.getName())) {
            return true;
        }
        Class<?> superCls = beanType.getSuperclass();
        if (superCls.equals(Object.class)) {
            return false;
        }
        return this.isBeanClass(superCls, stackClass);
    }

    private String[] buildLazyLoadIgnoreList() {
        ArrayList<String> ignore = new ArrayList<String>();
        ignore.add("com.avaje.ebean");
        ignore.add("java");
        ignore.add("sun.reflect");
        ignore.add("org.codehaus.groovy.runtime.");
        String extraIgnore = GlobalProperties.get("debug.lazyload.ignore", null);
        if (extraIgnore != null) {
            String[] split = extraIgnore.split(",");
            for (int i = 0; i < split.length; ++i) {
                ignore.add(split[i].trim());
            }
        }
        return ignore.toArray(new String[ignore.size()]);
    }
}

