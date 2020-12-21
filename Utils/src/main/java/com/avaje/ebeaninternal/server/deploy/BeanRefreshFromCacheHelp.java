/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import java.util.HashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanRefreshFromCacheHelp {
    private final BeanDescriptor<?> desc;
    private final EntityBeanIntercept ebi;
    private final EntityBean bean;
    private final Object cacheBean;
    private final Object originalOldValues;
    private final boolean isLazyLoad;
    private final boolean readOnly;
    private final boolean sharedInstance;
    private final int parentState;
    private final Set<String> excludes;
    private final Set<String> cacheBeanLoadedProps;
    private final Set<String> loadedProps;
    private final boolean setOriginalOldValues;

    public BeanRefreshFromCacheHelp(BeanDescriptor<?> desc, EntityBeanIntercept ebi, Object cacheBean, boolean isLazyLoad) {
        this.desc = desc;
        this.ebi = ebi;
        this.bean = ebi.getOwner();
        this.cacheBean = cacheBean;
        this.cacheBeanLoadedProps = ((EntityBean)cacheBean)._ebean_getIntercept().getLoadedProps();
        this.loadedProps = this.cacheBeanLoadedProps != null ? new HashSet<String>() : null;
        this.isLazyLoad = isLazyLoad;
        this.readOnly = ebi.isReadOnly();
        this.sharedInstance = ebi.isSharedInstance();
        this.parentState = this.sharedInstance ? 1 : (this.readOnly ? 2 : 0);
        this.excludes = isLazyLoad ? ebi.getLoadedProps() : null;
        this.originalOldValues = this.excludes != null ? ebi.getOldValues() : null;
        this.setOriginalOldValues = this.originalOldValues != null;
    }

    private boolean includeProperty(BeanProperty prop) {
        String name = prop.getName();
        if (this.excludes != null && this.excludes.contains(name)) {
            return false;
        }
        if (this.cacheBeanLoadedProps != null && !this.cacheBeanLoadedProps.contains(name)) {
            return false;
        }
        if (this.loadedProps != null) {
            this.loadedProps.add(name);
        }
        return true;
    }

    private void propagateParentState(Object bean) {
        if (bean != null && this.parentState > 0) {
            ((EntityBean)bean)._ebean_getIntercept().setState(this.parentState);
        }
    }

    public void refresh() {
        this.ebi.setIntercepting(false);
        BeanProperty[] props = this.desc.propertiesBaseScalar();
        for (int i = 0; i < props.length; ++i) {
            BeanProperty prop = props[i];
            if (!this.includeProperty(prop)) continue;
            Object val = prop.getValue(this.cacheBean);
            if (this.isLazyLoad) {
                prop.setValue(this.bean, val);
            } else {
                prop.setValueIntercept(this.bean, val);
            }
            if (!this.setOriginalOldValues) continue;
            prop.setValue(this.originalOldValues, val);
        }
        BeanPropertyAssocOne<?>[] ones = this.desc.propertiesOne();
        for (int i = 0; i < ones.length; ++i) {
            BeanPropertyAssocOne<?> prop = ones[i];
            if (!this.includeProperty(prop)) continue;
            Object val = prop.getValue(this.cacheBean);
            if (!this.sharedInstance) {
                val = prop.getTargetDescriptor().createCopyForUpdate(val, false);
            }
            if (this.isLazyLoad) {
                prop.setValue(this.bean, val);
            } else {
                prop.setValueIntercept(this.bean, val);
            }
            if (this.setOriginalOldValues) {
                prop.setValue(this.originalOldValues, val);
            }
            this.propagateParentState(val);
        }
        this.refreshEmbedded();
        BeanPropertyAssocMany<?>[] manys = this.desc.propertiesMany();
        for (int i = 0; i < manys.length; ++i) {
            BeanPropertyAssocMany<?> prop = manys[i];
            if (!this.includeProperty(prop)) continue;
            prop.createReference(this.bean);
        }
        this.ebi.setLoadedProps(this.loadedProps);
        this.ebi.setLoaded();
    }

    private void refreshEmbedded() {
        BeanPropertyAssocOne<?>[] embeds = this.desc.propertiesEmbedded();
        for (int i = 0; i < embeds.length; ++i) {
            BeanPropertyAssocOne<?> prop = embeds[i];
            if (!this.includeProperty(prop)) continue;
            Object oEmb = prop.getValue(this.bean);
            Object cacheEmb = prop.getValue(this.cacheBean);
            if (oEmb == null) {
                if (cacheEmb == null) {
                    prop.setValueIntercept(this.bean, null);
                    continue;
                }
                Object copyEmb = prop.getTargetDescriptor().createCopyForUpdate(cacheEmb, false);
                prop.setValueIntercept(this.bean, copyEmb);
                this.propagateParentState(copyEmb);
                continue;
            }
            if (oEmb instanceof EntityBean) {
                ((EntityBean)oEmb)._ebean_getIntercept().setIntercepting(false);
            }
            BeanProperty[] props = prop.getProperties();
            for (int j = 0; j < props.length; ++j) {
                Object v = props[j].getValue(cacheEmb);
                props[j].setValueIntercept(oEmb, v);
            }
        }
    }
}

