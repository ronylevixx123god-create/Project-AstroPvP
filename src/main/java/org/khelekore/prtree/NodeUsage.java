/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

class NodeUsage<T> {
    private final T data;
    private int id;

    public NodeUsage(T data, int id) {
        this.data = data;
        this.id = id;
    }

    public T getData() {
        return this.data;
    }

    public int getOwner() {
        return this.id;
    }

    public void changeOwner(int id) {
        this.id = id;
    }

    public void use() {
        if (this.id < 0) {
            throw new RuntimeException("Trying to use already used node");
        }
        this.id = -this.id;
    }

    public boolean isUsed() {
        return this.id < 0;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "{data: " + this.data + ", id: " + this.id + "}";
    }
}

