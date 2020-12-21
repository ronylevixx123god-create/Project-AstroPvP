/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class Throwables {
    private Throwables() {
    }

    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> declaredType) throws X {
        if (throwable != null && declaredType.isInstance(throwable)) {
            throw (Throwable)declaredType.cast(throwable);
        }
    }

    public static void propagateIfPossible(@Nullable Throwable throwable) {
        Throwables.propagateIfInstanceOf(throwable, Error.class);
        Throwables.propagateIfInstanceOf(throwable, RuntimeException.class);
    }

    public static <X extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X> declaredType) throws X {
        Throwables.propagateIfInstanceOf(throwable, declaredType);
        Throwables.propagateIfPossible(throwable);
    }

    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X1> declaredType1, Class<X2> declaredType2) throws X1, X2 {
        Preconditions.checkNotNull(declaredType2);
        Throwables.propagateIfInstanceOf(throwable, declaredType1);
        Throwables.propagateIfPossible(throwable, declaredType2);
    }

    public static RuntimeException propagate(Throwable throwable) {
        Throwables.propagateIfPossible(Preconditions.checkNotNull(throwable));
        throw new RuntimeException(throwable);
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }

    @Beta
    public static List<Throwable> getCausalChain(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        ArrayList<Throwable> causes = new ArrayList<Throwable>(4);
        while (throwable != null) {
            causes.add(throwable);
            throwable = throwable.getCause();
        }
        return Collections.unmodifiableList(causes);
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Deprecated
    @Beta
    public static Exception throwCause(Exception exception, boolean combineStackTraces) throws Exception {
        Throwable cause = exception.getCause();
        if (cause == null) {
            throw exception;
        }
        if (combineStackTraces) {
            StackTraceElement[] causeTrace = cause.getStackTrace();
            StackTraceElement[] outerTrace = exception.getStackTrace();
            StackTraceElement[] combined = new StackTraceElement[causeTrace.length + outerTrace.length];
            System.arraycopy(causeTrace, 0, combined, 0, causeTrace.length);
            System.arraycopy(outerTrace, 0, combined, causeTrace.length, outerTrace.length);
            cause.setStackTrace(combined);
        }
        if (cause instanceof Exception) {
            throw (Exception)cause;
        }
        if (cause instanceof Error) {
            throw (Error)cause;
        }
        throw exception;
    }
}

