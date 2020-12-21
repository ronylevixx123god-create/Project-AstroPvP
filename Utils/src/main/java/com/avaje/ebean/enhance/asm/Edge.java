/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.asm;

import com.avaje.ebean.enhance.asm.Label;

class Edge {
    static final int NORMAL = 0;
    static final int EXCEPTION = Integer.MAX_VALUE;
    int info;
    Label successor;
    Edge next;

    Edge() {
    }
}

