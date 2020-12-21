/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type.reflect;

import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
import com.avaje.ebeaninternal.server.type.reflect.KnownImmutable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CheckImmutable {
    private static Logger logger = Logger.getLogger(CheckImmutable.class.getName());
    private final KnownImmutable knownImmutable;

    public CheckImmutable(KnownImmutable knownImmutable) {
        this.knownImmutable = knownImmutable;
    }

    public CheckImmutableResponse checkImmutable(Class<?> cls) {
        CheckImmutableResponse res = new CheckImmutableResponse();
        this.isImmutable(cls, res);
        if (res.isImmutable()) {
            res.setCompoundType(this.isCompoundType(cls));
        }
        return res;
    }

    private boolean isCompoundType(Class<?> cls) {
        int maxLength = 0;
        Constructor<?> chosen = null;
        Constructor<?>[] constructors = cls.getConstructors();
        for (int i = 0; i < constructors.length; ++i) {
            Class<?>[] parameterTypes = constructors[i].getParameterTypes();
            if (parameterTypes.length <= maxLength) continue;
            maxLength = parameterTypes.length;
            chosen = constructors[i];
        }
        logger.fine("checkImmutable " + cls + " constructor " + chosen);
        return maxLength > 1;
    }

    private boolean isImmutable(Class<?> cls, CheckImmutableResponse res) {
        if (this.knownImmutable.isKnownImmutable(cls)) {
            return true;
        }
        if (cls.isArray()) {
            return false;
        }
        if (this.hasDefaultConstructor(cls)) {
            res.setReasonNotImmutable(cls + " has a default constructor");
            return false;
        }
        Class<?> superClass = cls.getSuperclass();
        if (!this.isImmutable(superClass, res)) {
            res.setReasonNotImmutable("Super not Immutable " + superClass);
            return false;
        }
        return this.hasAllFinalFields(cls, res);
    }

    private boolean hasAllFinalFields(Class<?> cls, CheckImmutableResponse res) {
        Field[] objFields = cls.getDeclaredFields();
        for (int i = 0; i < objFields.length; ++i) {
            if (Modifier.isStatic(objFields[i].getModifiers())) continue;
            if (!Modifier.isFinal(objFields[i].getModifiers())) {
                res.setReasonNotImmutable("Non final field " + cls + "." + objFields[i].getName());
                return false;
            }
            if (this.isImmutable(objFields[i].getType(), res)) continue;
            res.setReasonNotImmutable("Non Immutable field type " + objFields[i].getType());
            return false;
        }
        return true;
    }

    private boolean hasDefaultConstructor(Class<?> cls) {
        Class[] noParams = new Class[]{};
        try {
            cls.getDeclaredConstructor(noParams);
            return true;
        }
        catch (SecurityException e) {
            return false;
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }
}

