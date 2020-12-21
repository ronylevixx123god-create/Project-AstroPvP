/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.MapMaker;
import java.lang.reflect.Array;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
class Platform {
    static <T> T[] clone(T[] array) {
        return (Object[])array.clone();
    }

    static void unsafeArrayCopy(Object[] src, int srcPos, Object[] dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    @GwtIncompatible(value="Array.newInstance(Class, int)")
    static <T> T[] newArray(Class<T> type, int length) {
        return (Object[])Array.newInstance(type, length);
    }

    static <T> T[] newArray(T[] reference, int length) {
        Class<?> type = reference.getClass().getComponentType();
        Object[] result = (Object[])Array.newInstance(type, length);
        return result;
    }

    static MapMaker tryWeakKeys(MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }

    private Platform() {
    }
}

