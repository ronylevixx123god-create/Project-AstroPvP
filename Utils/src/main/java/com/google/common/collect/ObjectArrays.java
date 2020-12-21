/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Platform;
import java.util.Collection;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class ObjectArrays {
    private ObjectArrays() {
    }

    @GwtIncompatible(value="Array.newInstance(Class, int)")
    public static <T> T[] newArray(Class<T> type, int length) {
        return Platform.newArray(type, length);
    }

    public static <T> T[] newArray(T[] reference, int length) {
        return Platform.newArray(reference, length);
    }

    @GwtIncompatible(value="Array.newInstance(Class, int)")
    public static <T> T[] concat(T[] first, T[] second, Class<T> type) {
        Object[] result = ObjectArrays.newArray(type, first.length + second.length);
        Platform.unsafeArrayCopy(first, 0, result, 0, first.length);
        Platform.unsafeArrayCopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static <T> T[] concat(@Nullable T element, T[] array) {
        Object[] result = ObjectArrays.newArray(array, array.length + 1);
        result[0] = element;
        Platform.unsafeArrayCopy(array, 0, result, 1, array.length);
        return result;
    }

    public static <T> T[] concat(T[] array, @Nullable T element) {
        T[] result = ObjectArrays.arraysCopyOf(array, array.length + 1);
        result[array.length] = element;
        return result;
    }

    static <T> T[] arraysCopyOf(T[] original, int newLength) {
        Object[] copy = ObjectArrays.newArray(original, newLength);
        Platform.unsafeArrayCopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    static <T> T[] toArrayImpl(Collection<?> c, T[] array) {
        int size = c.size();
        if (array.length < size) {
            array = ObjectArrays.newArray(array, size);
        }
        ObjectArrays.fillArray(c, array);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }

    static Object[] toArrayImpl(Collection<?> c) {
        return ObjectArrays.fillArray(c, new Object[c.size()]);
    }

    private static Object[] fillArray(Iterable<?> elements, Object[] array) {
        int i = 0;
        for (Object element : elements) {
            array[i++] = element;
        }
        return array;
    }

    static void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

