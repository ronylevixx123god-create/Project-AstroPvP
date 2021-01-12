/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin;

import kotlin.MathKt__BigIntegersKt;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000&\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0015\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\b\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0001H\u0087\b\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0005H\u0087\b\u001a\r\u0010\n\u001a\u00020\t*\u00020\u0001H\u0087\b\u001a\r\u0010\n\u001a\u00020\t*\u00020\u0005H\u0087\b\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0001H\u0087\b\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0005H\u0087\b\u001a\r\u0010\f\u001a\u00020\u0004*\u00020\u0001H\u0087\b\u001a\r\u0010\f\u001a\u00020\u0007*\u00020\u0005H\u0087\b\u001a\r\u0010\r\u001a\u00020\u0004*\u00020\u0001H\u0087\b\u001a\r\u0010\r\u001a\u00020\u0007*\u00020\u0005H\u0087\b\u00a8\u0006\u000e"}, d2={"fromBits", "", "Lkotlin/Double$Companion;", "bits", "", "", "Lkotlin/Float$Companion;", "", "isFinite", "", "isInfinite", "isNaN", "toBits", "toRawBits", "kotlin-stdlib"}, xs="kotlin/MathKt")
class MathKt__NumbersKt
extends MathKt__BigIntegersKt {
    @InlineOnly
    private static final boolean isNaN(double $receiver) {
        return Double.isNaN($receiver);
    }

    @InlineOnly
    private static final boolean isNaN(float $receiver) {
        return Float.isNaN($receiver);
    }

    @InlineOnly
    private static final boolean isInfinite(double $receiver) {
        return Double.isInfinite($receiver);
    }

    @InlineOnly
    private static final boolean isInfinite(float $receiver) {
        return Float.isInfinite($receiver);
    }

    @InlineOnly
    private static final boolean isFinite(double $receiver) {
        double d = $receiver;
        return !Double.isInfinite(d) && !Double.isNaN(d = $receiver);
    }

    @InlineOnly
    private static final boolean isFinite(float $receiver) {
        float f = $receiver;
        return !Float.isInfinite(f) && !Float.isNaN(f = $receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long toBits(double $receiver) {
        return Double.doubleToLongBits($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long toRawBits(double $receiver) {
        return Double.doubleToRawLongBits($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double fromBits(@NotNull DoubleCompanionObject $receiver, long bits) {
        return Double.longBitsToDouble(bits);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int toBits(float $receiver) {
        return Float.floatToIntBits($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int toRawBits(float $receiver) {
        return Float.floatToRawIntBits($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float fromBits(@NotNull FloatCompanionObject $receiver, int bits) {
        return Float.intBitsToFloat(bits);
    }
}

