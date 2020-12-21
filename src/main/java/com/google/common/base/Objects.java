/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public final class Objects {
    private Objects() {
    }

    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        return a == b || a != null && a.equals(b);
    }

    public static int hashCode(Object ... objects) {
        return Arrays.hashCode(objects);
    }

    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(Objects.simpleName(self.getClass()));
    }

    public static ToStringHelper toStringHelper(Class<?> clazz) {
        return new ToStringHelper(Objects.simpleName(clazz));
    }

    public static ToStringHelper toStringHelper(String className) {
        return new ToStringHelper(className);
    }

    private static String simpleName(Class<?> clazz) {
        String name = clazz.getName();
        int start = (name = name.replaceAll("\\$[0-9]+", "\\$")).lastIndexOf(36);
        if (start == -1) {
            start = name.lastIndexOf(46);
        }
        return name.substring(start + 1);
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return first != null ? first : Preconditions.checkNotNull(second);
    }

    public static final class ToStringHelper {
        private final StringBuilder builder;
        private boolean needsSeparator = false;

        private ToStringHelper(String className) {
            Preconditions.checkNotNull(className);
            this.builder = new StringBuilder(32).append(className).append('{');
        }

        public ToStringHelper add(String name, @Nullable Object value) {
            Preconditions.checkNotNull(name);
            this.maybeAppendSeparator().append(name).append('=').append(value);
            return this;
        }

        public ToStringHelper addValue(@Nullable Object value) {
            this.maybeAppendSeparator().append(value);
            return this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            try {
                String string = this.builder.append('}').toString();
                return string;
            }
            finally {
                this.builder.setLength(this.builder.length() - 1);
            }
        }

        private StringBuilder maybeAppendSeparator() {
            if (this.needsSeparator) {
                return this.builder.append(", ");
            }
            this.needsSeparator = true;
            return this.builder;
        }
    }
}

