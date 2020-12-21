/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebeaninternal.server.util.ArrayStack;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PathStack
extends ArrayStack<String> {
    public String peekFullPath(String key) {
        String prefix = (String)this.peekWithNull();
        if (prefix != null) {
            return prefix + "." + key;
        }
        return key;
    }

    public void pushPathKey(String key) {
        String prefix = (String)this.peekWithNull();
        if (prefix != null) {
            key = prefix + "." + key;
        }
        this.push(key);
    }
}

