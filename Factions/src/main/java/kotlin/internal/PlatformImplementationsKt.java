/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.PlatformImplementations;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\b\u0010\b\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "getJavaVersion", "kotlin-stdlib"})
public final class PlatformImplementationsKt {
    @JvmField
    @NotNull
    public static final PlatformImplementations IMPLEMENTATIONS;

    private static final int getJavaVersion() {
        int n;
        int n2 = 65542;
        String string = System.getProperty("java.specification.version");
        if (string == null) {
            return n2;
        }
        String version = string;
        int firstDot = StringsKt.indexOf$default((CharSequence)version, '.', 0, false, 6, null);
        if (firstDot < 0) {
            int n3;
            try {
                String string2 = version;
                n3 = Integer.parseInt(string2) * 65536;
            }
            catch (NumberFormatException e) {
                n3 = n2;
            }
            return n3;
        }
        int secondDot = StringsKt.indexOf$default((CharSequence)version, '.', firstDot + 1, false, 4, null);
        if (secondDot < 0) {
            secondDot = version.length();
        }
        String string3 = version;
        int n4 = 0;
        String string4 = string3;
        if (string4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string5 = string4.substring(n4, firstDot);
        Intrinsics.checkExpressionValueIsNotNull(string5, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        String firstPart = string5;
        String string6 = version;
        int n5 = firstDot + 1;
        String string7 = string6;
        if (string7 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String string8 = string7.substring(n5, secondDot);
        Intrinsics.checkExpressionValueIsNotNull(string8, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        String secondPart = string8;
        try {
            string6 = firstPart;
            int n6 = Integer.parseInt(string6) * 65536;
            string6 = secondPart;
            int n7 = n6;
            int n8 = Integer.parseInt(string6);
            n = n7 + n8;
        }
        catch (NumberFormatException e) {
            n = n2;
        }
        return n;
    }

    @PublishedApi
    @SinceKotlin(version="1.2")
    public static final boolean apiVersionIsAtLeast(int major, int minor, int patch) {
        return KotlinVersion.CURRENT.isAtLeast(major, minor, patch);
    }

    static {
        PlatformImplementations platformImplementations;
        block14: {
            int version = PlatformImplementationsKt.getJavaVersion();
            if (version >= 65544) {
                try {
                    Object obj = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                    platformImplementations = (PlatformImplementations)obj;
                    break block14;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    try {
                        Object obj = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                        if (obj == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                        }
                        platformImplementations = (PlatformImplementations)obj;
                        break block14;
                    }
                    catch (ClassNotFoundException classNotFoundException2) {
                        // empty catch block
                    }
                }
            }
            if (version >= 65543) {
                try {
                    Object obj = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    }
                    platformImplementations = (PlatformImplementations)obj;
                    break block14;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    try {
                        Object obj = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                        if (obj == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                        }
                        platformImplementations = (PlatformImplementations)obj;
                        break block14;
                    }
                    catch (ClassNotFoundException classNotFoundException3) {
                        // empty catch block
                    }
                }
            }
            platformImplementations = new PlatformImplementations();
        }
        IMPLEMENTATIONS = platformImplementations;
    }
}

