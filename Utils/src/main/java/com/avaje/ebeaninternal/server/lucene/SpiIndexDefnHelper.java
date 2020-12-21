/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Field$Index
 *  org.apache.lucene.document.Field$Store
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexDefnBuilder;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import org.apache.lucene.document.Field;

public interface SpiIndexDefnHelper
extends IndexDefnBuilder {
    public IndexFieldDefn addPrefixField(String var1, String var2, Field.Store var3, Field.Index var4, IndexFieldDefn.Sortable var5);

    public IndexFieldDefn addPrefixFieldConcat(String var1, String var2, Field.Store var3, Field.Index var4, String[] var5);
}

