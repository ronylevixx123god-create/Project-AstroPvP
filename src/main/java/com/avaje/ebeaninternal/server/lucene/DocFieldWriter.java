/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Document
 */
package com.avaje.ebeaninternal.server.lucene;

import org.apache.lucene.document.Document;

public interface DocFieldWriter {
    public void writeValue(Object var1, Document var2);
}

