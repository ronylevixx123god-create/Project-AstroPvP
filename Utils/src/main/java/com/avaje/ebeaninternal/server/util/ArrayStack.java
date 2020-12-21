/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.util;

import java.util.ArrayList;
import java.util.EmptyStackException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ArrayStack<E> {
    private final ArrayList<E> list;

    public ArrayStack(int size) {
        this.list = new ArrayList(size);
    }

    public ArrayStack() {
        this.list = new ArrayList();
    }

    public E push(E item) {
        this.list.add(item);
        return item;
    }

    public E pop() {
        int len = this.list.size();
        E obj = this.peek();
        this.list.remove(len - 1);
        return obj;
    }

    protected E peekZero(boolean retNull) {
        int len = this.list.size();
        if (len == 0) {
            if (retNull) {
                return null;
            }
            throw new EmptyStackException();
        }
        return this.list.get(len - 1);
    }

    public E peek() {
        return this.peekZero(false);
    }

    public E peekWithNull() {
        return this.peekZero(true);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public int size() {
        return this.list.size();
    }
}

