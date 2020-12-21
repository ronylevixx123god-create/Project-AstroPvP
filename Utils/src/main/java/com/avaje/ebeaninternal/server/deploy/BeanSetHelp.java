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
import com.avaje.ebean.common.BeanSet;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanSetHelp<T>
implements BeanCollectionHelp<T> {
    private final BeanPropertyAssocMany<T> many;
    private final BeanDescriptor<T> targetDescriptor;
    private BeanCollectionLoader loader;

    public BeanSetHelp(BeanPropertyAssocMany<T> many) {
        this.many = many;
        this.targetDescriptor = many.getTargetDescriptor();
    }

    public BeanSetHelp() {
        this.many = null;
        this.targetDescriptor = null;
    }

    @Override
    public void setLoader(BeanCollectionLoader loader) {
        this.loader = loader;
    }

    @Override
    public Iterator<?> getIterator(Object collection) {
        return ((Set)collection).iterator();
    }

    @Override
    public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
        if (bc instanceof BeanSet) {
            BeanSet beanSet = (BeanSet)bc;
            if (beanSet.getActualSet() == null) {
                beanSet.setActualSet(new LinkedHashSet());
            }
            return beanSet;
        }
        if (bc instanceof Set) {
            return new VanillaAdd((Set)bc);
        }
        throw new RuntimeException("Unhandled type " + bc);
    }

    @Override
    public void add(BeanCollection<?> collection, Object bean) {
        collection.internalAdd(bean);
    }

    @Override
    public Object copyCollection(Object source, CopyContext ctx, int maxDepth, Object parentBean) {
        Set<T> s;
        if (!(source instanceof Set)) {
            return null;
        }
        Set set = s = ctx.isVanillaMode() ? new LinkedHashSet() : new BeanSet();
        if (!(source instanceof BeanSet)) {
            s.addAll((Set)source);
            return s;
        }
        BeanSet bc = (BeanSet)source;
        if (!bc.isPopulated()) {
            if (ctx.isVanillaMode() || parentBean == null) {
                return null;
            }
            return this.createReference(parentBean, this.many.getName());
        }
        Set actual = bc.getActualSet();
        for (Object sourceDetail : actual) {
            T destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
            s.add(destDetail);
        }
        return s;
    }

    @Override
    public Object createEmpty(boolean vanilla) {
        return vanilla ? new LinkedHashSet() : new BeanSet();
    }

    @Override
    public BeanCollection<T> createReference(Object parentBean, String propertyName) {
        return new BeanSet(this.loader, parentBean, propertyName);
    }

    @Override
    public ArrayList<InvalidValue> validate(Object manyValue) {
        ArrayList<InvalidValue> errs = null;
        Set set = (Set)manyValue;
        for (Object detailBean : set) {
            InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
            if (invalid == null) continue;
            if (errs == null) {
                errs = new ArrayList<InvalidValue>();
            }
            errs.add(invalid);
        }
        return errs;
    }

    @Override
    public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
        BeanSet newBeanSet = (BeanSet)server.findSet(query, t);
        this.refresh(newBeanSet, parentBean);
    }

    @Override
    public void refresh(BeanCollection<?> bc, Object parentBean) {
        BeanSet newBeanSet = (BeanSet)bc;
        Set current = (Set)this.many.getValueUnderlying(parentBean);
        newBeanSet.setModifyListening(this.many.getModifyListenMode());
        if (current == null) {
            this.many.setValue(parentBean, newBeanSet);
        } else if (current instanceof BeanSet) {
            BeanSet currentBeanSet = (BeanSet)current;
            currentBeanSet.setActualSet(newBeanSet.getActualSet());
            currentBeanSet.setModifyListening(this.many.getModifyListenMode());
        } else {
            this.many.setValue(parentBean, newBeanSet);
        }
    }

    @Override
    public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
        Set set;
        if (collection instanceof BeanCollection) {
            BeanSet bc = (BeanSet)collection;
            if (!bc.isPopulated()) {
                if (explicitInclude) {
                    bc.size();
                } else {
                    return;
                }
            }
            set = bc.getActualSet();
        } else {
            set = (Set)collection;
        }
        int count = 0;
        ctx.beginAssocMany(name);
        for (Object detailBean : set) {
            if (count++ > 0) {
                ctx.appendComma();
            }
            this.targetDescriptor.jsonWrite(ctx, detailBean);
        }
        ctx.endAssocMany();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class VanillaAdd
    implements BeanCollectionAdd {
        private final Set set;

        private VanillaAdd(Set<?> set) {
            this.set = set;
        }

        @Override
        public void addBean(Object bean) {
            this.set.add(bean);
        }
    }
}

