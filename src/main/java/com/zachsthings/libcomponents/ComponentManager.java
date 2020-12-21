/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.AnnotationHandler;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.InvalidComponentException;
import com.zachsthings.libcomponents.loader.ComponentLoader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ComponentManager<T extends AbstractComponent> {
    protected final Logger logger;
    protected final Class<T> componentClass;
    protected final List<ComponentLoader> loaders = new ArrayList<ComponentLoader>();
    protected final Map<String, T> registeredComponents = new LinkedHashMap<String, T>();
    protected final Map<Class<? extends Annotation>, AnnotationHandler<?>> annotationHandlers = new LinkedHashMap();

    public ComponentManager(Logger logger, Class<T> componentCass) {
        this.logger = logger;
        this.componentClass = componentCass;
    }

    public synchronized boolean addComponentLoader(ComponentLoader loader) {
        return this.loaders.add(loader);
    }

    public synchronized boolean loadComponents() throws InvalidComponentException {
        for (ComponentLoader loader : this.loaders) {
            for (AbstractComponent baseComponent : loader.loadComponents()) {
                if (!this.componentClass.isAssignableFrom(baseComponent.getClass())) {
                    throw new InvalidComponentException(baseComponent.getClass(), "Component is not an instance of " + this.componentClass.getCanonicalName());
                }
                AbstractComponent component = (AbstractComponent)this.componentClass.cast(baseComponent);
                ComponentInformation info = component.getClass().getAnnotation(ComponentInformation.class);
                component.setUp(loader, info);
                this.setUpComponent(component);
                this.registeredComponents.put(info.friendlyName().replaceAll(" ", "-").toLowerCase(), component);
            }
        }
        return true;
    }

    protected abstract void setUpComponent(T var1);

    public synchronized void enableComponents() {
        for (AbstractComponent component : this.registeredComponents.values()) {
            for (Field field : component.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                for (Annotation annotation : field.getAnnotations()) {
                    AnnotationHandler<?> handler = this.annotationHandlers.get(annotation.annotationType());
                    if (handler == null || handler.handle(component, field, annotation)) continue;
                    this.logger.log(Level.WARNING, "Component " + component.getInformation().friendlyName() + " could not be enabled! Error in annotation handler for field " + field);
                }
            }
            component.enable();
            component.setEnabled(true);
            component.saveConfig();
            this.logger.log(Level.FINEST, "Component " + component.getInformation().friendlyName() + " successfully enabled!");
        }
    }

    public synchronized void unloadComponents() {
        for (AbstractComponent component : this.registeredComponents.values()) {
            component.disable();
        }
        this.registeredComponents.clear();
    }

    public synchronized void reloadComponents() {
        for (AbstractComponent component : this.registeredComponents.values()) {
            component.reload();
        }
    }

    public synchronized <C> C getComponent(Class<C> type) {
        for (AbstractComponent component : this.registeredComponents.values()) {
            if (!component.getClass().equals(type)) continue;
            return type.cast(component);
        }
        return null;
    }

    public Collection<T> getComponents() {
        return Collections.unmodifiableCollection(this.registeredComponents.values());
    }

    public AbstractComponent getComponent(String friendlyName) {
        return (AbstractComponent)this.registeredComponents.get(friendlyName);
    }

    public synchronized <T extends Annotation> void registerAnnotationHandler(Class<T> annotation, AnnotationHandler<T> handler) {
        this.annotationHandlers.put(annotation, handler);
    }

    public synchronized <T extends Annotation> AnnotationHandler<T> getAnnotationHandler(Class<T> annotation) {
        return this.annotationHandlers.get(annotation);
    }
}

