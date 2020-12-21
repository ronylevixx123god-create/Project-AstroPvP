/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.BeanCollectionTouched;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.common.ModifyHolder;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractBeanCollection<E>
implements BeanCollection<E> {
    private static final long serialVersionUID = 3365725236140187588L;
    protected int state;
    protected transient BeanCollectionLoader loader;
    protected transient ExpressionList<?> filterMany;
    protected int loaderIndex;
    protected String ebeanServerName;
    protected transient BeanCollectionTouched beanCollectionTouched;
    protected transient Future<Integer> fetchFuture;
    protected final Object ownerBean;
    protected final String propertyName;
    protected boolean finishedFetch = true;
    protected boolean hasMoreRows;
    protected ModifyHolder<E> modifyHolder;
    protected BeanCollection.ModifyListenMode modifyListenMode;
    protected boolean modifyAddListening;
    protected boolean modifyRemoveListening;
    protected boolean modifyListening;

    public AbstractBeanCollection() {
        this.ownerBean = null;
        this.propertyName = null;
    }

    public AbstractBeanCollection(BeanCollectionLoader loader, Object ownerBean, String propertyName) {
        EntityBeanIntercept ebi;
        int parentState;
        this.loader = loader;
        this.ebeanServerName = loader.getName();
        this.ownerBean = ownerBean;
        this.propertyName = propertyName;
        if (ownerBean instanceof EntityBean && (parentState = (ebi = ((EntityBean)ownerBean)._ebean_getIntercept()).getState()) != 0) {
            this.state = parentState;
        }
    }

    @Override
    public Object getOwnerBean() {
        return this.ownerBean;
    }

    @Override
    public String getPropertyName() {
        return this.propertyName;
    }

    @Override
    public int getLoaderIndex() {
        return this.loaderIndex;
    }

    @Override
    public ExpressionList<?> getFilterMany() {
        return this.filterMany;
    }

    @Override
    public void setFilterMany(ExpressionList<?> filterMany) {
        this.filterMany = filterMany;
    }

    protected void lazyLoadCollection(boolean onlyIds) {
        if (this.loader == null) {
            this.loader = (BeanCollectionLoader)((Object)Ebean.getServer(this.ebeanServerName));
        }
        if (this.loader == null) {
            String msg = "Lazy loading but LazyLoadEbeanServer is null? The LazyLoadEbeanServer needs to be set after deserialization to support lazy loading.";
            throw new PersistenceException(msg);
        }
        this.loader.loadMany(this, onlyIds);
        this.checkEmptyLazyLoad();
    }

    protected void touched() {
        if (this.beanCollectionTouched != null) {
            this.beanCollectionTouched.notifyTouched(this);
            this.beanCollectionTouched = null;
        }
    }

    @Override
    public void setBeanCollectionTouched(BeanCollectionTouched notify) {
        this.beanCollectionTouched = notify;
    }

    @Override
    public void setLoader(int beanLoaderIndex, BeanCollectionLoader loader) {
        this.loaderIndex = beanLoaderIndex;
        this.loader = loader;
        this.ebeanServerName = loader.getName();
    }

    @Override
    public boolean isSharedInstance() {
        return this.state == 3;
    }

    @Override
    public void setSharedInstance() {
        this.state = 3;
    }

    @Override
    public boolean isReadOnly() {
        return this.state >= 2;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        if (this.state == 3) {
            if (!readOnly) {
                String msg = "This collection is a sharedInstance and must always be read only";
                throw new IllegalStateException(msg);
            }
        } else {
            this.state = readOnly ? 2 : 0;
        }
    }

    @Override
    public boolean hasMoreRows() {
        return this.hasMoreRows;
    }

    @Override
    public void setHasMoreRows(boolean hasMoreRows) {
        this.hasMoreRows = hasMoreRows;
    }

    @Override
    public boolean isFinishedFetch() {
        return this.finishedFetch;
    }

    @Override
    public void setFinishedFetch(boolean finishedFetch) {
        this.finishedFetch = finishedFetch;
    }

    @Override
    public void setBackgroundFetch(Future<Integer> fetchFuture) {
        this.fetchFuture = fetchFuture;
    }

    @Override
    public void backgroundFetchWait(long wait, TimeUnit timeUnit) {
        if (this.fetchFuture != null) {
            try {
                this.fetchFuture.get(wait, timeUnit);
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Override
    public void backgroundFetchWait() {
        if (this.fetchFuture != null) {
            try {
                this.fetchFuture.get();
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
    }

    protected void checkReadOnly() {
        if (this.state >= 2) {
            String msg = "This collection is in ReadOnly mode";
            throw new IllegalStateException(msg);
        }
    }

    @Override
    public void setModifyListening(BeanCollection.ModifyListenMode mode) {
        this.modifyListenMode = mode;
        this.modifyAddListening = BeanCollection.ModifyListenMode.ALL.equals((Object)mode);
        this.modifyRemoveListening = this.modifyAddListening || BeanCollection.ModifyListenMode.REMOVALS.equals((Object)mode);
        boolean bl = this.modifyListening = this.modifyRemoveListening || this.modifyAddListening;
        if (this.modifyListening) {
            this.modifyHolder = null;
        }
    }

    public BeanCollection.ModifyListenMode getModifyListenMode() {
        return this.modifyListenMode;
    }

    protected ModifyHolder<E> getModifyHolder() {
        if (this.modifyHolder == null) {
            this.modifyHolder = new ModifyHolder();
        }
        return this.modifyHolder;
    }

    @Override
    public void modifyAddition(E bean) {
        if (this.modifyAddListening) {
            this.getModifyHolder().modifyAddition(bean);
        }
    }

    @Override
    public void modifyRemoval(Object bean) {
        if (this.modifyRemoveListening) {
            this.getModifyHolder().modifyRemoval(bean);
        }
    }

    @Override
    public void modifyReset() {
        if (this.modifyHolder != null) {
            this.modifyHolder.reset();
        }
    }

    @Override
    public Set<E> getModifyAdditions() {
        if (this.modifyHolder == null) {
            return null;
        }
        return this.modifyHolder.getModifyAdditions();
    }

    @Override
    public Set<E> getModifyRemovals() {
        if (this.modifyHolder == null) {
            return null;
        }
        return this.modifyHolder.getModifyRemovals();
    }
}

