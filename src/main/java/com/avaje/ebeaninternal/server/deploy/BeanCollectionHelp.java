/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanCollectionHelp<T> {
    public Object copyCollection(Object var1, CopyContext var2, int var3, Object var4);

    public void setLoader(BeanCollectionLoader var1);

    public BeanCollectionAdd getBeanCollectionAdd(Object var1, String var2);

    public Object createEmpty(boolean var1);

    public Iterator<?> getIterator(Object var1);

    public void add(BeanCollection<?> var1, Object var2);

    public BeanCollection<T> createReference(Object var1, String var2);

    public ArrayList<InvalidValue> validate(Object var1);

    public void refresh(EbeanServer var1, Query<?> var2, Transaction var3, Object var4);

    public void refresh(BeanCollection<?> var1, Object var2);

    public void jsonWrite(WriteJsonContext var1, String var2, Object var3, boolean var4);
}

