/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.bean;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.BeanCollectionTouched;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BeanCollection<E>
extends Serializable {
    public static final int DEFAULT = 0;
    public static final int READONLY = 2;
    public static final int SHARED = 3;

    public Object getOwnerBean();

    public String getPropertyName();

    public int getLoaderIndex();

    public boolean checkEmptyLazyLoad();

    public ExpressionList<?> getFilterMany();

    public void setFilterMany(ExpressionList<?> var1);

    public void setBackgroundFetch(Future<Integer> var1);

    public void backgroundFetchWait(long var1, TimeUnit var3);

    public void backgroundFetchWait();

    public boolean isSharedInstance();

    public void setSharedInstance();

    public void setBeanCollectionTouched(BeanCollectionTouched var1);

    public void setLoader(int var1, BeanCollectionLoader var2);

    public void setReadOnly(boolean var1);

    public boolean isReadOnly();

    public void internalAdd(Object var1);

    public Object getActualCollection();

    public int size();

    public boolean isEmpty();

    public Collection<E> getActualDetails();

    public boolean hasMoreRows();

    public void setHasMoreRows(boolean var1);

    public boolean isFinishedFetch();

    public void setFinishedFetch(boolean var1);

    public boolean isPopulated();

    public boolean isReference();

    public void setModifyListening(ModifyListenMode var1);

    public void modifyAddition(E var1);

    public void modifyRemoval(Object var1);

    public Set<E> getModifyAdditions();

    public Set<E> getModifyRemovals();

    public void modifyReset();

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum ModifyListenMode {
        NONE,
        REMOVALS,
        ALL;

    }
}

