/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.annotation.LdapDomain;
import com.avaje.ebean.config.CompoundType;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BootupClasses
implements ClassPathSearchMatcher {
    private static final Logger logger = Logger.getLogger(BootupClasses.class.getName());
    private ArrayList<Class<?>> xmlBeanList = new ArrayList();
    private ArrayList<Class<?>> embeddableList = new ArrayList();
    private ArrayList<Class<?>> entityList = new ArrayList();
    private ArrayList<Class<?>> scalarTypeList = new ArrayList();
    private ArrayList<Class<?>> scalarConverterList = new ArrayList();
    private ArrayList<Class<?>> compoundTypeList = new ArrayList();
    private ArrayList<Class<?>> beanControllerList = new ArrayList();
    private ArrayList<Class<?>> beanFinderList = new ArrayList();
    private ArrayList<Class<?>> beanListenerList = new ArrayList();
    private ArrayList<Class<?>> beanQueryAdapterList = new ArrayList();
    private ArrayList<Class<?>> luceneIndexList = new ArrayList();
    private List<BeanPersistController> persistControllerInstances = new ArrayList<BeanPersistController>();
    private List<BeanPersistListener<?>> persistListenerInstances = new ArrayList();
    private List<BeanQueryAdapter> queryAdapterInstances = new ArrayList<BeanQueryAdapter>();
    private List<IndexDefn<?>> luceneIndexInstances = new ArrayList();

    public BootupClasses() {
    }

    public BootupClasses(List<Class<?>> list) {
        if (list != null) {
            this.process(list.iterator());
        }
    }

    private BootupClasses(BootupClasses parent) {
        this.xmlBeanList.addAll(parent.xmlBeanList);
        this.embeddableList.addAll(parent.embeddableList);
        this.entityList.addAll(parent.entityList);
        this.scalarTypeList.addAll(parent.scalarTypeList);
        this.scalarConverterList.addAll(parent.scalarConverterList);
        this.compoundTypeList.addAll(parent.compoundTypeList);
        this.beanControllerList.addAll(parent.beanControllerList);
        this.beanFinderList.addAll(parent.beanFinderList);
        this.beanListenerList.addAll(parent.beanListenerList);
        this.beanQueryAdapterList.addAll(parent.beanQueryAdapterList);
        this.luceneIndexList.addAll(parent.luceneIndexList);
    }

    private void process(Iterator<Class<?>> it) {
        while (it.hasNext()) {
            Class<?> cls = it.next();
            this.isMatch(cls);
        }
    }

    public BootupClasses createCopy() {
        return new BootupClasses(this);
    }

    public void addIndexDefns(List<IndexDefn<?>> indexInstances) {
        if (indexInstances != null) {
            this.luceneIndexInstances.addAll(indexInstances);
        }
    }

    public void addQueryAdapters(List<BeanQueryAdapter> queryAdapterInstances) {
        if (queryAdapterInstances != null) {
            this.queryAdapterInstances.addAll(queryAdapterInstances);
        }
    }

    public void addPersistControllers(List<BeanPersistController> beanControllerInstances) {
        if (beanControllerInstances != null) {
            this.persistControllerInstances.addAll(beanControllerInstances);
        }
    }

    public void addPersistListeners(List<BeanPersistListener<?>> listenerInstances) {
        if (listenerInstances != null) {
            this.persistListenerInstances.addAll(listenerInstances);
        }
    }

    public List<BeanQueryAdapter> getBeanQueryAdapters() {
        for (Class<?> cls : this.beanQueryAdapterList) {
            try {
                BeanQueryAdapter newInstance = (BeanQueryAdapter)cls.newInstance();
                this.queryAdapterInstances.add(newInstance);
            }
            catch (Exception e) {
                String msg = "Error creating BeanQueryAdapter " + cls;
                logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.queryAdapterInstances;
    }

    public List<BeanPersistListener<?>> getBeanPersistListeners() {
        for (Class<?> cls : this.beanListenerList) {
            try {
                BeanPersistListener newInstance = (BeanPersistListener)cls.newInstance();
                this.persistListenerInstances.add(newInstance);
            }
            catch (Exception e) {
                String msg = "Error creating BeanPersistController " + cls;
                logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.persistListenerInstances;
    }

    public List<BeanPersistController> getBeanPersistControllers() {
        for (Class<?> cls : this.beanControllerList) {
            try {
                BeanPersistController newInstance = (BeanPersistController)cls.newInstance();
                this.persistControllerInstances.add(newInstance);
            }
            catch (Exception e) {
                String msg = "Error creating BeanPersistController " + cls;
                logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.persistControllerInstances;
    }

    public List<IndexDefn<?>> getLuceneIndexInstances() {
        for (Class<?> cls : this.luceneIndexList) {
            try {
                IndexDefn indexDefn = (IndexDefn)cls.newInstance();
                this.luceneIndexInstances.add(indexDefn);
            }
            catch (Exception e) {
                String msg = "Error creating BeanPersistController " + cls;
                logger.log(Level.SEVERE, msg, e);
            }
        }
        return this.luceneIndexInstances;
    }

    public ArrayList<Class<?>> getEmbeddables() {
        return this.embeddableList;
    }

    public ArrayList<Class<?>> getEntities() {
        return this.entityList;
    }

    public ArrayList<Class<?>> getScalarTypes() {
        return this.scalarTypeList;
    }

    public ArrayList<Class<?>> getScalarConverters() {
        return this.scalarConverterList;
    }

    public ArrayList<Class<?>> getCompoundTypes() {
        return this.compoundTypeList;
    }

    public ArrayList<Class<?>> getBeanControllers() {
        return this.beanControllerList;
    }

    public ArrayList<Class<?>> getBeanFinders() {
        return this.beanFinderList;
    }

    public ArrayList<Class<?>> getBeanListeners() {
        return this.beanListenerList;
    }

    public ArrayList<Class<?>> getXmlBeanList() {
        return this.xmlBeanList;
    }

    public void add(Iterator<Class<?>> it) {
        while (it.hasNext()) {
            Class<?> clazz = it.next();
            this.isMatch(clazz);
        }
    }

    @Override
    public boolean isMatch(Class<?> cls) {
        if (this.isEmbeddable(cls)) {
            this.embeddableList.add(cls);
        } else if (this.isEntity(cls)) {
            this.entityList.add(cls);
        } else if (this.isXmlBean(cls)) {
            this.entityList.add(cls);
        } else {
            return this.isInterestingInterface(cls);
        }
        return true;
    }

    private boolean isInterestingInterface(Class<?> cls) {
        boolean interesting = false;
        if (BeanPersistController.class.isAssignableFrom(cls)) {
            this.beanControllerList.add(cls);
            interesting = true;
        }
        if (ScalarType.class.isAssignableFrom(cls)) {
            this.scalarTypeList.add(cls);
            interesting = true;
        }
        if (ScalarTypeConverter.class.isAssignableFrom(cls)) {
            this.scalarConverterList.add(cls);
            interesting = true;
        }
        if (CompoundType.class.isAssignableFrom(cls)) {
            this.compoundTypeList.add(cls);
            interesting = true;
        }
        if (BeanFinder.class.isAssignableFrom(cls)) {
            this.beanFinderList.add(cls);
            interesting = true;
        }
        if (BeanPersistListener.class.isAssignableFrom(cls)) {
            this.beanListenerList.add(cls);
            interesting = true;
        }
        if (BeanQueryAdapter.class.isAssignableFrom(cls)) {
            this.beanQueryAdapterList.add(cls);
            interesting = true;
        }
        if (IndexDefn.class.isAssignableFrom(cls)) {
            this.luceneIndexList.add(cls);
            interesting = true;
        }
        return interesting;
    }

    private boolean isEntity(Class<?> cls) {
        Annotation ann = cls.getAnnotation(Entity.class);
        if (ann != null) {
            return true;
        }
        ann = cls.getAnnotation(Table.class);
        if (ann != null) {
            return true;
        }
        ann = cls.getAnnotation(LdapDomain.class);
        return ann != null;
    }

    private boolean isEmbeddable(Class<?> cls) {
        Embeddable ann = cls.getAnnotation(Embeddable.class);
        return ann != null;
    }

    private boolean isXmlBean(Class<?> cls) {
        XmlRootElement ann = cls.getAnnotation(XmlRootElement.class);
        if (ann != null) {
            return true;
        }
        ann = cls.getAnnotation(XmlType.class);
        if (ann != null) {
            return !cls.isEnum();
        }
        return false;
    }
}

