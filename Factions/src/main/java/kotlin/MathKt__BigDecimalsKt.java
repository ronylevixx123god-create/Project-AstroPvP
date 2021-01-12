/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000eH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000fH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u0010H\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\u0011\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u00a8\u0006\u0012"}, d2={"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "mod", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"}, xs="kotlin/MathKt")
class MathKt__BigDecimalsKt {
    @InlineOnly
    private static final BigDecimal plus(@NotNull BigDecimal $receiver, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.add(other);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.add(other)");
        return bigDecimal;
    }

    @InlineOnly
    private static final BigDecimal minus(@NotNull BigDecimal $receiver, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.subtract(other);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.subtract(other)");
        return bigDecimal;
    }

    @InlineOnly
    private static final BigDecimal times(@NotNull BigDecimal $receiver, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.multiply(other);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.multiply(other)");
        return bigDecimal;
    }

    @InlineOnly
    private static final BigDecimal div(@NotNull BigDecimal $receiver, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.divide(other, RoundingMode.HALF_EVEN);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.divide(other, RoundingMode.HALF_EVEN)");
        return bigDecimal;
    }

    @Deprecated(message="Use rem(other) instead", replaceWith=@ReplaceWith(imports={}, expression="rem(other)"), level=DeprecationLevel.WARNING)
    @InlineOnly
    private static final BigDecimal mod(@NotNull BigDecimal $receiver, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.remainder(other);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.remainder(other)");
        return bigDecimal;
    }

    @InlineOnly
    private static final BigDecimal rem(@NotNull BigDecimal $receiver, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.remainder(other);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.remainder(other)");
        return bigDecimal;
    }

    @InlineOnly
    private static final BigDecimal unaryMinus(@NotNull BigDecimal $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.negate();
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.negate()");
        return bigDecimal;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal inc(@NotNull BigDecimal $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.add(BigDecimal.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.add(BigDecimal.ONE)");
        return bigDecimal;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal dec(@NotNull BigDecimal $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigDecimal bigDecimal = $receiver.subtract(BigDecimal.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "this.subtract(BigDecimal.ONE)");
        return bigDecimal;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(int $receiver) {
        BigDecimal bigDecimal = BigDecimal.valueOf($receiver);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "BigDecimal.valueOf(this.toLong())");
        return bigDecimal;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(int $receiver, MathContext mathContext) {
        return new BigDecimal($receiver, mathContext);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(long $receiver) {
        BigDecimal bigDecimal = BigDecimal.valueOf($receiver);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimal, "BigDecimal.valueOf(this)");
        return bigDecimal;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(long $receiver, MathContext mathContext) {
        return new BigDecimal($receiver, mathContext);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(float $receiver) {
        return new BigDecimal(String.valueOf($receiver));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(float $receiver, MathContext mathContext) {
        return new BigDecimal(String.valueOf($receiver), mathContext);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(double $receiver) {
        return new BigDecimal(String.valueOf($receiver));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(double $receiver, MathContext mathContext) {
        return new BigDecimal(String.valueOf($receiver), mathContext);
    }
}

