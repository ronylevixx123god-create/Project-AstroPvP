/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.AnnotationHandler;
import com.zachsthings.libcomponents.ComponentManager;
import com.zachsthings.libcomponents.InjectComponent;
import java.lang.reflect.Field;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InjectComponentAnnotationHandler
implements AnnotationHandler<InjectComponent> {
    private final ComponentManager<?> componentManager;

    public InjectComponentAnnotationHandler(ComponentManager<?> componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public boolean handle(AbstractComponent component, Field field, InjectComponent annotation) {
        try {
            Object target = this.componentManager.getComponent(field.getType());
            if (target != null) {
                field.set(component, target);
                return true;
            }
        }
        catch (IllegalAccessException ignore) {
            // empty catch block
        }
        return false;
    }
}

