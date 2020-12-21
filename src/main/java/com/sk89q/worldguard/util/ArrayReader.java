/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.util;

public class ArrayReader<T> {
    private T[] arr;

    public ArrayReader(T[] arr) {
        this.arr = arr;
    }

    public T get(int index) {
        return this.get(index, null);
    }

    public T get(int index, T def) {
        if (index >= 0 && this.arr.length > index) {
            return this.arr[index];
        }
        return def;
    }
}

