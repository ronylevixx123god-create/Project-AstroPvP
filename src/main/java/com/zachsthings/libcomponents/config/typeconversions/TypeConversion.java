/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class TypeConversion
implements Comparable<TypeConversion> {
    public Object handle(Class<?> target, Type[] neededGenerics, Object value) {
        if (this.isApplicable(target, value) && (this.getParametersRequired() == -1 || neededGenerics.length == this.getParametersRequired())) {
            return this.cast(target, neededGenerics, value);
        }
        return null;
    }

    protected abstract Object cast(Class<?> var1, Type[] var2, Object var3);

    public abstract boolean isApplicable(Class<?> var1, Object var2);

    protected abstract int getParametersRequired();

    @Override
    public int compareTo(TypeConversion other) {
        return Integer.valueOf(this.getParametersRequired()).compareTo(other.getParametersRequired());
    }
}

