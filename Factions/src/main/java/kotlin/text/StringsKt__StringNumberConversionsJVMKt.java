/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.ScreenFloatValueRegEx;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringBuilderKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000X\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0005H\u0082\b\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u0003H\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0003H\u0087\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0003H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0017*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0019\u001a\r\u0010\u001a\u001a\u00020\u001b*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u001b*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u001d\u001a\r\u0010\u001e\u001a\u00020\u0010*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001e\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u001f\u001a\u00020 *\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010!\u001a\u00020\"*\u00020\u0003H\u0087\b\u001a\u0015\u0010!\u001a\u00020\"*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u00a8\u0006$"}, d2={"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsJVMKt
extends StringsKt__StringBuilderKt {
    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(byte $receiver, int radix) {
        byte by = $receiver;
        int n = CharsKt.checkRadix(radix);
        String string = Integer.toString(by, CharsKt.checkRadix(n));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(short $receiver, int radix) {
        short s = $receiver;
        int n = CharsKt.checkRadix(radix);
        String string = Integer.toString(s, CharsKt.checkRadix(n));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(int $receiver, int radix) {
        String string = Integer.toString($receiver, CharsKt.checkRadix(radix));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String toString(long $receiver, int radix) {
        String string = Long.toString($receiver, CharsKt.checkRadix(radix));
        Intrinsics.checkExpressionValueIsNotNull(string, "java.lang.Long.toString(this, checkRadix(radix))");
        return string;
    }

    @InlineOnly
    private static final boolean toBoolean(@NotNull String $receiver) {
        return Boolean.parseBoolean($receiver);
    }

    @InlineOnly
    private static final byte toByte(@NotNull String $receiver) {
        return Byte.parseByte($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final byte toByte(@NotNull String $receiver, int radix) {
        return Byte.parseByte($receiver, CharsKt.checkRadix(radix));
    }

    @InlineOnly
    private static final short toShort(@NotNull String $receiver) {
        return Short.parseShort($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final short toShort(@NotNull String $receiver, int radix) {
        return Short.parseShort($receiver, CharsKt.checkRadix(radix));
    }

    @InlineOnly
    private static final int toInt(@NotNull String $receiver) {
        return Integer.parseInt($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int toInt(@NotNull String $receiver, int radix) {
        return Integer.parseInt($receiver, CharsKt.checkRadix(radix));
    }

    @InlineOnly
    private static final long toLong(@NotNull String $receiver) {
        return Long.parseLong($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final long toLong(@NotNull String $receiver, int radix) {
        return Long.parseLong($receiver, CharsKt.checkRadix(radix));
    }

    @InlineOnly
    private static final float toFloat(@NotNull String $receiver) {
        return Float.parseFloat($receiver);
    }

    @InlineOnly
    private static final double toDouble(@NotNull String $receiver) {
        return Double.parseDouble($receiver);
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Float toFloatOrNull(@NotNull String $receiver) {
        Float f;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        try {
            Float f2;
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                String p1 = $receiver;
                f2 = Float.valueOf(Float.parseFloat(p1));
            } else {
                f2 = null;
            }
            f = f2;
        }
        catch (NumberFormatException e$iv) {
            f = null;
        }
        return f;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final Double toDoubleOrNull(@NotNull String $receiver) {
        Double d;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        try {
            Double d2;
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                String p1 = $receiver;
                d2 = Double.parseDouble(p1);
            } else {
                d2 = null;
            }
            d = d2;
        }
        catch (NumberFormatException e$iv) {
            d = null;
        }
        return d;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(@NotNull String $receiver) {
        return new BigInteger($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(@NotNull String $receiver, int radix) {
        return new BigInteger($receiver, CharsKt.checkRadix(radix));
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigInteger toBigIntegerOrNull(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.toBigIntegerOrNull($receiver, 10);
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigInteger toBigIntegerOrNull(@NotNull String $receiver, int radix) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharsKt.checkRadix(radix);
        int length = $receiver.length();
        switch (length) {
            case 0: {
                return null;
            }
            case 1: {
                if (CharsKt.digitOf($receiver.charAt(0), radix) >= 0) break;
                return null;
            }
            default: {
                int start;
                int n = start = $receiver.charAt(0) == '-' ? 1 : 0;
                int n2 = length;
                while (n < n2) {
                    void index;
                    if (CharsKt.digitOf($receiver.charAt((int)index), radix) < 0) {
                        return null;
                    }
                    ++index;
                }
                break block0;
            }
        }
        String string = $receiver;
        return new BigInteger(string, CharsKt.checkRadix(radix));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull String $receiver) {
        return new BigDecimal($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull String $receiver, MathContext mathContext) {
        return new BigDecimal($receiver, mathContext);
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigDecimal toBigDecimalOrNull(@NotNull String $receiver) {
        BigDecimal bigDecimal;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        try {
            BigDecimal bigDecimal2;
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                String it;
                String string = it = $receiver;
                bigDecimal2 = new BigDecimal(string);
            } else {
                bigDecimal2 = null;
            }
            bigDecimal = bigDecimal2;
        }
        catch (NumberFormatException e$iv) {
            bigDecimal = null;
        }
        return bigDecimal;
    }

    @SinceKotlin(version="1.2")
    @Nullable
    public static final BigDecimal toBigDecimalOrNull(@NotNull String $receiver, @NotNull MathContext mathContext) {
        BigDecimal bigDecimal;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(mathContext, "mathContext");
        try {
            BigDecimal bigDecimal2;
            if (ScreenFloatValueRegEx.value.matches($receiver)) {
                String it;
                String string = it = $receiver;
                MathContext mathContext2 = mathContext;
                bigDecimal2 = new BigDecimal(string, mathContext2);
            } else {
                bigDecimal2 = null;
            }
            bigDecimal = bigDecimal2;
        }
        catch (NumberFormatException e$iv) {
            bigDecimal = null;
        }
        return bigDecimal;
    }

    private static final <T> T screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String str, Function1<? super String, ? extends T> parse) {
        Object var3_2;
        try {
            var3_2 = ScreenFloatValueRegEx.value.matches(str) ? parse.invoke(str) : null;
        }
        catch (NumberFormatException e) {
            var3_2 = null;
        }
        return var3_2;
    }
}

