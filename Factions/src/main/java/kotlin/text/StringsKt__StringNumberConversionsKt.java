/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.text;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000&\n\u0000\n\u0002\u0010\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0013\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0007\u00a2\u0006\u0002\u0010\u0003\u001a\u001b\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\u0010\u0006\u001a\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005*\u00020\u0002H\u0007\u00a2\u0006\u0002\u0010\b\u001a\u001b\u0010\u0007\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u0002H\u0007\u00a2\u0006\u0002\u0010\f\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\u0010\r\u001a\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u0002H\u0007\u00a2\u0006\u0002\u0010\u0010\u001a\u001b\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2={"toByteOrNull", "", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsKt
extends StringsKt__StringNumberConversionsJVMKt {
    @SinceKotlin(version="1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.toByteOrNull($receiver, 10);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Byte toByteOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Integer n = StringsKt.toIntOrNull($receiver, radix);
        if (n == null) {
            return null;
        }
        int n2 = n;
        if (n2 < -128 || n2 > 127) {
            return null;
        }
        return (byte)n2;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.toShortOrNull($receiver, 10);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Short toShortOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Integer n = StringsKt.toIntOrNull($receiver, radix);
        if (n == null) {
            return null;
        }
        int n2 = n;
        if (n2 < -32768 || n2 > 32767) {
            return null;
        }
        return (short)n2;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.toIntOrNull($receiver, 10);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SinceKotlin(version="1.1")
    @Nullable
    public static final Integer toIntOrNull(@NotNull String $receiver, int radix) {
        void i;
        int limit;
        boolean isNegative;
        int start;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharsKt.checkRadix(radix);
        int length = $receiver.length();
        if (length == 0) {
            return null;
        }
        char firstChar = $receiver.charAt(0);
        if (firstChar < '0') {
            if (length == 1) {
                return null;
            }
            start = 1;
            if (firstChar == '-') {
                isNegative = true;
                limit = Integer.MIN_VALUE;
            } else {
                if (firstChar != '+') return null;
                isNegative = false;
                limit = -2147483647;
            }
        } else {
            start = 0;
            isNegative = false;
            limit = -2147483647;
        }
        int limitBeforeMul = limit / radix;
        int result = 0;
        int n = start;
        int n2 = length - 1;
        if (n > n2) return isNegative ? Integer.valueOf(result) : Integer.valueOf(-result);
        do {
            int digit;
            if ((digit = CharsKt.digitOf($receiver.charAt((int)(++i)), radix)) < 0) {
                return null;
            }
            if (result < limitBeforeMul) {
                return null;
            }
            if ((result *= radix) < limit + digit) {
                return null;
            }
            result -= digit;
        } while (i != n2);
        return isNegative ? Integer.valueOf(result) : Integer.valueOf(-result);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.toLongOrNull($receiver, 10);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SinceKotlin(version="1.1")
    @Nullable
    public static final Long toLongOrNull(@NotNull String $receiver, int radix) {
        void i;
        long limit;
        boolean isNegative;
        int start;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharsKt.checkRadix(radix);
        int length = $receiver.length();
        if (length == 0) {
            return null;
        }
        char firstChar = $receiver.charAt(0);
        if (firstChar < '0') {
            if (length == 1) {
                return null;
            }
            start = 1;
            if (firstChar == '-') {
                isNegative = true;
                limit = Long.MIN_VALUE;
            } else {
                if (firstChar != '+') return null;
                isNegative = false;
                limit = -9223372036854775807L;
            }
        } else {
            start = 0;
            isNegative = false;
            limit = -9223372036854775807L;
        }
        long limitBeforeMul = limit / (long)radix;
        long result = 0L;
        int n = start;
        int n2 = length - 1;
        if (n > n2) return isNegative ? Long.valueOf(result) : Long.valueOf(-result);
        do {
            int digit;
            if ((digit = CharsKt.digitOf($receiver.charAt((int)(++i)), radix)) < 0) {
                return null;
            }
            if (result < limitBeforeMul) {
                return null;
            }
            if ((result *= (long)radix) < limit + (long)digit) {
                return null;
            }
            result -= (long)digit;
        } while (i != n2);
        return isNegative ? Long.valueOf(result) : Long.valueOf(-result);
    }
}

