/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.Label;

public interface TableSwitchGenerator {
    public void generateCase(int var1, Label var2);

    public void generateDefault();
}

