/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.util;

import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ArrayStack<T> {
    private ArrayList<T> stack;

    public ArrayStack(int initSize) {
        this.stack = new ArrayList(initSize);
    }

    public void push(T obj) {
        this.stack.add(obj);
    }

    public T pop() {
        return this.stack.remove(this.stack.size() - 1);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public void clear() {
        this.stack.clear();
    }
}

