/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InvalidComponentException
extends Exception {
    private static final long serialVersionUID = 6023653129909836161L;
    private final Class<?> componentClass;

    public InvalidComponentException(Class<?> componentClass, String message) {
        super(message);
        this.componentClass = componentClass;
    }

    @Override
    public String getMessage() {
        return "Component " + this.componentClass.getCanonicalName() + " could not be loaded due to an error in the structure of the component: " + super.getMessage();
    }
}

