/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

public interface ReadJsonSource {
    public char nextChar(String var1);

    public void ignoreWhiteSpace();

    public void back();

    public int pos();

    public String getErrorHelp();
}

