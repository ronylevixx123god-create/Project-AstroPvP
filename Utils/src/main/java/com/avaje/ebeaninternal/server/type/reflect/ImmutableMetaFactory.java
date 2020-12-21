/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type.reflect;

import com.avaje.ebeaninternal.server.type.reflect.ImmutableMeta;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ImmutableMetaFactory {
    private static final Logger logger = Logger.getLogger(ImmutableMetaFactory.class.getName());

    public ImmutableMeta createImmutableMeta(Class<?> cls) {
        ScoreConstructor[] scoreConstructors = this.scoreConstructors(cls);
        ArrayList<RuntimeException> errors = new ArrayList<RuntimeException>();
        for (int i = 0; i < scoreConstructors.length; ++i) {
            Constructor<?> constructor = scoreConstructors[i].constructor;
            try {
                Method[] getters = this.findGetters(cls, constructor);
                return new ImmutableMeta(constructor, getters);
            }
            catch (NoSuchMethodException e) {
                String msg = "Error finding getter method on " + cls + " with constructor " + constructor;
                errors.add(new RuntimeException(msg, e));
                continue;
            }
        }
        String msg = "Was unable to use reflection to find a constructor and appropriate getters forimmutable type " + cls + ".  The errors while looking for the getter methods follow:";
        logger.severe(msg);
        for (RuntimeException runtimeException : errors) {
            logger.log(Level.SEVERE, "Error with " + cls, runtimeException);
        }
        msg = "Unable to use reflection to build ImmutableMeta for " + cls + ".  Associated Errors trying to find a constructor and getter methods have been logged";
        throw new RuntimeException(msg);
    }

    private ScoreConstructor getScore(Constructor<?> c) {
        Class<?>[] parameterTypes = c.getParameterTypes();
        int score = -1000 * parameterTypes.length;
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (parameterTypes[i].equals(String.class)) {
                ++score;
                continue;
            }
            if (parameterTypes[i].equals(BigDecimal.class)) {
                score -= 10;
                continue;
            }
            if (parameterTypes[i].equals(Timestamp.class)) {
                score -= 10;
                continue;
            }
            if (parameterTypes[i].equals(Double.TYPE)) {
                score -= 9;
                continue;
            }
            if (parameterTypes[i].equals(Double.class)) {
                score -= 8;
                continue;
            }
            if (parameterTypes[i].equals(Float.TYPE)) {
                score -= 7;
                continue;
            }
            if (parameterTypes[i].equals(Float.class)) {
                score -= 6;
                continue;
            }
            if (parameterTypes[i].equals(Long.TYPE)) {
                score -= 5;
                continue;
            }
            if (parameterTypes[i].equals(Long.class)) {
                score -= 4;
                continue;
            }
            if (parameterTypes[i].equals(Integer.TYPE)) {
                score -= 3;
                continue;
            }
            if (!parameterTypes[i].equals(Integer.class)) continue;
            score -= 2;
        }
        return new ScoreConstructor(score, c);
    }

    private ScoreConstructor[] scoreConstructors(Class<?> cls) {
        int maxParamCount = 0;
        Constructor<?>[] constructors = cls.getConstructors();
        Object[] score = new ScoreConstructor[constructors.length];
        for (int i = 0; i < constructors.length; ++i) {
            score[i] = this.getScore(constructors[i]);
            if (score[i].hasDuplicateParamTypes()) {
                String msg = "Duplicate parameter types in " + ((ScoreConstructor)score[i]).constructor;
                throw new IllegalStateException(msg);
            }
            if (score[i].getParamCount() <= maxParamCount) continue;
            maxParamCount = ((ScoreConstructor)score[i]).getParamCount();
        }
        ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < score.length; ++i) {
            if (score[i].getParamCount() != maxParamCount) continue;
            list.add(score[i]);
        }
        score = list.toArray(new ScoreConstructor[list.size()]);
        Arrays.sort(score);
        return score;
    }

    private Method[] findGetters(Class<?> cls, Constructor<?> c) throws NoSuchMethodException {
        Method[] methods = cls.getMethods();
        Class<?>[] paramTypes = c.getParameterTypes();
        Method[] readers = new Method[paramTypes.length];
        for (int i = 0; i < paramTypes.length; ++i) {
            Method getter = this.findGetter(paramTypes[i], methods);
            if (getter == null && paramTypes.length == 1 && paramTypes[i].equals(String.class)) {
                getter = this.findToString(cls);
            }
            if (getter == null) {
                throw new NoSuchMethodException("Get Method not found for " + paramTypes[i] + " in " + cls);
            }
            readers[i] = getter;
        }
        return readers;
    }

    private Method findToString(Class<?> cls) throws NoSuchMethodException {
        try {
            return cls.getDeclaredMethod("toString", new Class[0]);
        }
        catch (SecurityException e) {
            throw new NoSuchMethodException("SecurityException " + e + " trying to find toString method on " + cls);
        }
    }

    private Method findGetter(Class<?> paramType, Method[] methods) {
        for (int i = 0; i < methods.length; ++i) {
            Class<?> returnType;
            String methName;
            if (Modifier.isStatic(methods[i].getModifiers()) || methods[i].getParameterTypes().length != 0 || (methName = methods[i].getName()).equals("hashCode") || methName.equals("toString") || !paramType.equals(returnType = methods[i].getReturnType())) continue;
            return methods[i];
        }
        return null;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class ScoreConstructor
    implements Comparable<ScoreConstructor> {
        final int score;
        final Constructor<?> constructor;

        private ScoreConstructor(int score, Constructor<?> constructor) {
            this.score = score;
            this.constructor = constructor;
        }

        public boolean equals(Object obj) {
            return obj == this;
        }

        @Override
        public int compareTo(ScoreConstructor o) {
            return this.score < o.score ? -1 : (this.score == o.score ? 0 : 1);
        }

        public int getParamCount() {
            return this.constructor.getParameterTypes().length;
        }

        public boolean hasDuplicateParamTypes() {
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            if (parameterTypes.length < 2) {
                return false;
            }
            HashSet set = new HashSet();
            for (int i = 0; i < parameterTypes.length; ++i) {
                if (set.add(parameterTypes[i])) continue;
                return true;
            }
            return false;
        }
    }
}

