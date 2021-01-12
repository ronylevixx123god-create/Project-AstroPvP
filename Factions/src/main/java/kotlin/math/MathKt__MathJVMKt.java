/*
 * Decompiled with CFR 0.150.
 */
package kotlin.math;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.math.Constants;
import kotlin.math.MathKt;
import kotlin.math.MathKt__MathHKt;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000\"\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b7\u001a\u0011\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fH\u0087\b\u001a\u0011\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010!\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010!\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\"\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\"\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010#\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010#\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010$\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010$\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010%\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010%\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010&\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010&\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010'\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010'\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010(\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010(\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010)\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010)\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0018\u0010*\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u0001H\u0007\u001a\u0018\u0010*\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u0006H\u0007\u001a\u0011\u0010,\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010,\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u0010-\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u0010-\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0019\u0010.\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\u0087\b\u001a\u0019\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\u0087\b\u001a\u0019\u0010.\u001a\u00020\t2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0087\b\u001a\u0019\u0010.\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\u0087\b\u001a\u0019\u00101\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\u0087\b\u001a\u0019\u00101\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\u0087\b\u001a\u0019\u00101\u001a\u00020\t2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0087\b\u001a\u0019\u00101\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\u0087\b\u001a\u0011\u00102\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00102\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00103\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00103\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00104\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00104\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00105\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00105\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00106\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00106\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0011\u00107\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0011\u00107\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0010\u00108\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u00108\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0015\u00109\u001a\u00020\u0001*\u00020\u00012\u0006\u0010:\u001a\u00020\u0001H\u0087\b\u001a\u0015\u00109\u001a\u00020\u0006*\u00020\u00062\u0006\u0010:\u001a\u00020\u0006H\u0087\b\u001a\r\u0010;\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010;\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010<\u001a\u00020\u0001*\u00020\u00012\u0006\u0010=\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010<\u001a\u00020\u0006*\u00020\u00062\u0006\u0010=\u001a\u00020\u0006H\u0087\b\u001a\r\u0010>\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010>\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0018\u001a\u00020\tH\u0087\b\u001a\f\u0010@\u001a\u00020\t*\u00020\u0001H\u0007\u001a\f\u0010@\u001a\u00020\t*\u00020\u0006H\u0007\u001a\f\u0010A\u001a\u00020\f*\u00020\u0001H\u0007\u001a\f\u0010A\u001a\u00020\f*\u00020\u0006H\u0007\u001a\u0015\u0010B\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010B\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\tH\u0087\b\u001a\u0015\u0010B\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010B\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\u0087\b\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001f\u0010\u0000\u001a\u00020\u0006*\u00020\u00068\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\u0007\u001a\u0004\b\u0004\u0010\b\"\u001f\u0010\u0000\u001a\u00020\t*\u00020\t8\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\n\u001a\u0004\b\u0004\u0010\u000b\"\u001f\u0010\u0000\u001a\u00020\f*\u00020\f8\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0002\u0010\r\u001a\u0004\b\u0004\u0010\u000e\"\u001f\u0010\u000f\u001a\u00020\u0001*\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u0011\u0010\u0005\"\u001f\u0010\u000f\u001a\u00020\u0006*\u00020\u00068\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u0011\u0010\b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\t8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\n\u001a\u0004\b\u0011\u0010\u000b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\f8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u0012\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00018\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0003\u001a\u0004\b\u0015\u0010\u0005\"\u001f\u0010\u0013\u001a\u00020\u0006*\u00020\u00068\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0007\u001a\u0004\b\u0015\u0010\b\u00a8\u0006C"}, d2={"absoluteValue", "", "absoluteValue$annotations", "(D)V", "getAbsoluteValue", "(D)D", "", "(F)V", "(F)F", "", "(I)V", "(I)I", "", "(J)V", "(J)J", "sign", "sign$annotations", "getSign", "(J)I", "ulp", "ulp$annotations", "getUlp", "abs", "x", "n", "acos", "acosh", "asin", "asinh", "atan", "atan2", "y", "atanh", "ceil", "cos", "cosh", "exp", "expm1", "floor", "hypot", "ln", "ln1p", "log", "base", "log10", "log2", "max", "a", "b", "min", "round", "sin", "sinh", "sqrt", "tan", "tanh", "truncate", "IEEErem", "divisor", "nextDown", "nextTowards", "to", "nextUp", "pow", "roundToInt", "roundToLong", "withSign", "kotlin-stdlib"}, xs="kotlin/math/MathKt")
class MathKt__MathJVMKt
extends MathKt__MathHKt {
    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sin(double x) {
        return Math.sin(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double cos(double x) {
        return Math.cos(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double tan(double x) {
        return Math.tan(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double asin(double x) {
        return Math.asin(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double acos(double x) {
        return Math.acos(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double atan(double x) {
        return Math.atan(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sinh(double x) {
        return Math.sinh(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double cosh(double x) {
        return Math.cosh(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double tanh(double x) {
        return Math.tanh(x);
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.2")
    public static final double asinh(double x) {
        double d;
        if (x >= Constants.taylor_n_bound) {
            d = x > Constants.upper_taylor_n_bound ? (x > Constants.upper_taylor_2_bound ? Math.log(x) + Constants.LN2 : Math.log(x * (double)2 + 1.0 / (x * (double)2))) : Math.log(x + Math.sqrt(x * x + 1.0));
        } else if (x <= -Constants.taylor_n_bound) {
            d = -MathKt.asinh(-x);
        } else {
            void var2_1;
            double result = x;
            if (Math.abs(x) >= Constants.taylor_2_bound) {
                result -= x * x * x / (double)6;
            }
            d = var2_1;
        }
        return d;
    }

    @SinceKotlin(version="1.2")
    public static final double acosh(double x) {
        double d;
        if (x < 1.0) {
            d = DoubleCompanionObject.INSTANCE.getNaN();
        } else if (x > Constants.upper_taylor_2_bound) {
            d = Math.log(x) + Constants.LN2;
        } else if (x - 1.0 >= Constants.taylor_n_bound) {
            d = Math.log(x + Math.sqrt(x * x - 1.0));
        } else {
            double y;
            double result = y = Math.sqrt(x - 1.0);
            if (y >= Constants.taylor_2_bound) {
                result -= y * y * y / (double)12;
            }
            d = Math.sqrt(2.0) * result;
        }
        return d;
    }

    @SinceKotlin(version="1.2")
    public static final double atanh(double x) {
        if (Math.abs(x) < Constants.taylor_n_bound) {
            double result = x;
            if (Math.abs(x) > Constants.taylor_2_bound) {
                result += x * x * x / (double)3;
            }
            return result;
        }
        return Math.log((1.0 + x) / (1.0 - x)) / (double)2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double hypot(double x, double y) {
        return Math.hypot(x, y);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sqrt(double x) {
        return Math.sqrt(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double exp(double x) {
        return Math.exp(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double expm1(double x) {
        return Math.expm1(x);
    }

    @SinceKotlin(version="1.2")
    public static final double log(double x, double base) {
        if (base <= 0.0 || base == 1.0) {
            return DoubleCompanionObject.INSTANCE.getNaN();
        }
        return Math.log(x) / Math.log(base);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double ln(double x) {
        return Math.log(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double log10(double x) {
        return Math.log10(x);
    }

    @SinceKotlin(version="1.2")
    public static final double log2(double x) {
        return Math.log(x) / Constants.LN2;
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double ln1p(double x) {
        return Math.log1p(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double ceil(double x) {
        return Math.ceil(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double floor(double x) {
        return Math.floor(x);
    }

    @SinceKotlin(version="1.2")
    public static final double truncate(double x) {
        double d = x;
        return Double.isNaN(d) || Double.isInfinite(d = x) ? x : (x > 0.0 ? Math.floor(x) : Math.ceil(x));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double round(double x) {
        return Math.rint(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double abs(double x) {
        return Math.abs(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double sign(double x) {
        return Math.signum(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double min(double a, double b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double max(double a, double b) {
        return Math.max(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double pow(double $receiver, double x) {
        return Math.pow($receiver, x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double pow(double $receiver, int n) {
        return Math.pow($receiver, n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double IEEErem(double $receiver, double divisor) {
        return Math.IEEEremainder($receiver, divisor);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(double d) {
    }

    private static final double getAbsoluteValue(double $receiver) {
        return Math.abs($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void sign$annotations(double d) {
    }

    private static final double getSign(double $receiver) {
        return Math.signum($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double withSign(double $receiver, double sign) {
        return Math.copySign($receiver, sign);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double withSign(double $receiver, int sign) {
        return Math.copySign($receiver, (double)sign);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void ulp$annotations(double d) {
    }

    private static final double getUlp(double $receiver) {
        return Math.ulp($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double nextUp(double $receiver) {
        return Math.nextUp($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double nextDown(double $receiver) {
        return Math.nextAfter($receiver, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final double nextTowards(double $receiver, double to) {
        return Math.nextAfter($receiver, to);
    }

    @SinceKotlin(version="1.2")
    public static final int roundToInt(double $receiver) {
        double d = $receiver;
        if (Double.isNaN(d)) {
            throw (Throwable)new IllegalArgumentException("Cannot round NaN value.");
        }
        return $receiver > (double)Integer.MAX_VALUE ? Integer.MAX_VALUE : ($receiver < (double)Integer.MIN_VALUE ? Integer.MIN_VALUE : (int)Math.round($receiver));
    }

    @SinceKotlin(version="1.2")
    public static final long roundToLong(double $receiver) {
        double d = $receiver;
        if (Double.isNaN(d)) {
            throw (Throwable)new IllegalArgumentException("Cannot round NaN value.");
        }
        return Math.round($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sin(float x) {
        return (float)Math.sin(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float cos(float x) {
        return (float)Math.cos(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float tan(float x) {
        return (float)Math.tan(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float asin(float x) {
        return (float)Math.asin(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float acos(float x) {
        return (float)Math.acos(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float atan(float x) {
        return (float)Math.atan(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float atan2(float y, float x) {
        return (float)Math.atan2(y, x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sinh(float x) {
        return (float)Math.sinh(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float cosh(float x) {
        return (float)Math.cosh(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float tanh(float x) {
        return (float)Math.tanh(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float asinh(float x) {
        return (float)MathKt.asinh((double)x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float acosh(float x) {
        return (float)MathKt.acosh((double)x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float atanh(float x) {
        return (float)MathKt.atanh((double)x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float hypot(float x, float y) {
        return (float)Math.hypot(x, y);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sqrt(float x) {
        return (float)Math.sqrt(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float exp(float x) {
        return (float)Math.exp(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float expm1(float x) {
        return (float)Math.expm1(x);
    }

    @SinceKotlin(version="1.2")
    public static final float log(float x, float base) {
        if (base <= 0.0f || base == 1.0f) {
            return FloatCompanionObject.INSTANCE.getNaN();
        }
        return (float)(Math.log(x) / Math.log(base));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float ln(float x) {
        return (float)Math.log(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float log10(float x) {
        return (float)Math.log10(x);
    }

    @SinceKotlin(version="1.2")
    public static final float log2(float x) {
        return (float)(Math.log(x) / Constants.LN2);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float ln1p(float x) {
        return (float)Math.log1p(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float ceil(float x) {
        return (float)Math.ceil(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float floor(float x) {
        return (float)Math.floor(x);
    }

    @SinceKotlin(version="1.2")
    public static final float truncate(float x) {
        float f = x;
        return Float.isNaN(f) || Float.isInfinite(f = x) ? x : (x > 0.0f ? (float)Math.floor(x) : (float)Math.ceil(x));
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float round(float x) {
        return (float)Math.rint(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float abs(float x) {
        return Math.abs(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float sign(float x) {
        return Math.signum(x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float min(float a, float b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float max(float a, float b) {
        return Math.max(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float pow(float $receiver, float x) {
        return (float)Math.pow($receiver, x);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float pow(float $receiver, int n) {
        return (float)Math.pow($receiver, n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float IEEErem(float $receiver, float divisor) {
        return (float)Math.IEEEremainder($receiver, divisor);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(float f) {
    }

    private static final float getAbsoluteValue(float $receiver) {
        return Math.abs($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void sign$annotations(float f) {
    }

    private static final float getSign(float $receiver) {
        return Math.signum($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float withSign(float $receiver, float sign) {
        return Math.copySign($receiver, sign);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float withSign(float $receiver, int sign) {
        return Math.copySign($receiver, sign);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void ulp$annotations(float f) {
    }

    private static final float getUlp(float $receiver) {
        return Math.ulp($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float nextUp(float $receiver) {
        return Math.nextUp($receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float nextDown(float $receiver) {
        return Math.nextAfter($receiver, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final float nextTowards(float $receiver, float to) {
        return Math.nextAfter($receiver, (double)to);
    }

    @SinceKotlin(version="1.2")
    public static final int roundToInt(float $receiver) {
        float f = $receiver;
        if (Float.isNaN(f)) {
            throw (Throwable)new IllegalArgumentException("Cannot round NaN value.");
        }
        return Math.round($receiver);
    }

    @SinceKotlin(version="1.2")
    public static final long roundToLong(float $receiver) {
        return MathKt.roundToLong((double)$receiver);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int abs(int n) {
        return Math.abs(n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int min(int a, int b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final int max(int a, int b) {
        return Math.max(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(int n) {
    }

    private static final int getAbsoluteValue(int $receiver) {
        return Math.abs($receiver);
    }

    @SinceKotlin(version="1.2")
    public static /* synthetic */ void sign$annotations(int n) {
    }

    public static final int getSign(int $receiver) {
        return $receiver < 0 ? -1 : ($receiver > 0 ? 1 : 0);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long abs(long n) {
        return Math.abs(n);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long min(long a, long b) {
        return Math.min(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    private static final long max(long a, long b) {
        return Math.max(a, b);
    }

    @SinceKotlin(version="1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(long l) {
    }

    private static final long getAbsoluteValue(long $receiver) {
        return Math.abs($receiver);
    }

    @SinceKotlin(version="1.2")
    public static /* synthetic */ void sign$annotations(long l) {
    }

    public static final int getSign(long $receiver) {
        return $receiver < 0L ? -1 : ($receiver > 0L ? 1 : 0);
    }
}

