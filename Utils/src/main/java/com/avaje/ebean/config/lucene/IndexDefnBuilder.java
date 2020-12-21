/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Field$Index
 *  org.apache.lucene.document.Field$Store
 */
package com.avaje.ebean.config.lucene;

import com.avaje.ebean.config.lucene.IndexFieldDefn;
import java.util.List;
import org.apache.lucene.document.Field;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface IndexDefnBuilder {
    public void addAllFields();

    public IndexDefnBuilder assocOne(String var1);

    public IndexFieldDefn addField(IndexFieldDefn var1);

    public IndexFieldDefn addField(String var1);

    public IndexFieldDefn addField(String var1, IndexFieldDefn.Sortable var2);

    public IndexFieldDefn addField(String var1, Field.Store var2, Field.Index var3, IndexFieldDefn.Sortable var4);

    public IndexFieldDefn addFieldConcat(String var1, String ... var2);

    public IndexFieldDefn addFieldConcat(String var1, Field.Store var2, Field.Index var3, String ... var4);

    public IndexFieldDefn getField(String var1);

    public List<IndexFieldDefn> getFields();
}

