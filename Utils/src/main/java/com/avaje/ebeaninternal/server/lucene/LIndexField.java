/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.queryParser.QueryParser
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import java.util.Set;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface LIndexField {
    public String getName();

    public DocFieldWriter createDocFieldWriter();

    public void addIndexResolvePropertyNames(Set<String> var1);

    public void addIndexRestorePropertyNames(Set<String> var1);

    public void addIndexRequiredPropertyNames(Set<String> var1);

    public String getSortableProperty();

    public int getSortType();

    public boolean isIndexed();

    public boolean isStored();

    public boolean isBeanProperty();

    public ElPropertyValue getElBeanProperty();

    public void readValue(Document var1, Object var2);

    public QueryParser createQueryParser();

    public int getPropertyOrder();
}

