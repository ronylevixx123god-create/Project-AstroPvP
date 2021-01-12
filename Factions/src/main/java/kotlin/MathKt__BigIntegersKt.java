/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.MathKt__BigDecimalsKt;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0001H\u0087\b\u001a!\u0010\u0010\u001a\u00020\u0011*\u00020\u00012\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\rH\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0016H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u00a8\u0006\u0019"}, d2={"and", "Ljava/math/BigInteger;", "other", "dec", "div", "inc", "inv", "minus", "or", "plus", "rem", "shl", "n", "", "shr", "times", "toBigDecimal", "Ljava/math/BigDecimal;", "scale", "mathContext", "Ljava/math/MathContext;", "toBigInteger", "", "unaryMinus", "xor", "kotlin-stdlib"}, xs="kotlin/MathKt")
class MathKt__BigIntegersKt
extends MathKt__BigDecimalsKt {
    @InlineOnly
    private static final BigInteger plus(@NotNull BigInteger $receiver, BigInteger other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.add(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.add(other)");
        return bigInteger;
    }

    @InlineOnly
    private static final BigInteger minus(@NotNull BigInteger $receiver, BigInteger other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.subtract(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.subtract(other)");
        return bigInteger;
    }

    @InlineOnly
    private static final BigInteger times(@NotNull BigInteger $receiver, BigInteger other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.multiply(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.multiply(other)");
        return bigInteger;
    }

    @InlineOnly
    private static final BigInteger div(@NotNull BigInteger $receiver, BigInteger other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.divide(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.divide(other)");
        return bigInteger;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final BigInteger rem(@NotNull BigInteger $receiver, BigInteger other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.remainder(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.remainder(other)");
        return bigInteger;
    }

    @InlineOnly
    private static final BigInteger unaryMinus(@NotNull BigInteger $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.negate();
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.negate()");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger inc(@NotNull BigInteger $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.add(BigInteger.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.add(BigInteger.ONE)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger dec(@NotNull BigInteger $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        BigInteger bigInteger = $receiver.subtract(BigInteger.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.subtract(BigInteger.ONE)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger inv(@NotNull BigInteger $receiver) {
        BigInteger bigInteger = $receiver.not();
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.not()");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger and(@NotNull BigInteger $receiver, BigInteger other) {
        BigInteger bigInteger = $receiver.and(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.and(other)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger or(@NotNull BigInteger $receiver, BigInteger other) {
        BigInteger bigInteger = $receiver.or(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.or(other)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger xor(@NotNull BigInteger $receiver, BigInteger other) {
        BigInteger bigInteger = $receiver.xor(other);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.xor(other)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger shl(@NotNull BigInteger $receiver, int n) {
        BigInteger bigInteger = $receiver.shiftLeft(n);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.shiftLeft(n)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger shr(@NotNull BigInteger $receiver, int n) {
        BigInteger bigInteger = $receiver.shiftRight(n);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "this.shiftRight(n)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(int $receiver) {
        BigInteger bigInteger = BigInteger.valueOf($receiver);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "BigInteger.valueOf(this.toLong())");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(long $receiver) {
        BigInteger bigInteger = BigInteger.valueOf($receiver);
        Intrinsics.checkExpressionValueIsNotNull(bigInteger, "BigInteger.valueOf(this)");
        return bigInteger;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull BigInteger $receiver) {
        return new BigDecimal($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull BigInteger $receiver, int scale, MathContext mathContext) {
        return new BigDecimal($receiver, scale, mathContext);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    static /* bridge */ /* synthetic */ BigDecimal toBigDecimal$default(BigInteger $receiver, int scale, MathContext mathContext, int n, Object object) {
        if ((n & 1) != 0) {
            scale = 0;
        }
        if ((n & 2) != 0) {
            MathContext mathContext2 = MathContext.UNLIMITED;
            Intrinsics.checkExpressionValueIsNotNull(mathContext2, "MathContext.UNLIMITED");
            mathContext = mathContext2;
        }
        return new BigDecimal($receiver, scale, mathContext);
    }
}

