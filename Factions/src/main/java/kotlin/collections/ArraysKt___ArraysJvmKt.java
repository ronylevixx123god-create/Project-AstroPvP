/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.ArraysKt__ArraysKt;
import kotlin.collections.ArraysUtilJVM;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000\u0096\u0001\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0018\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0013\n\u0002\u0010\u0007\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\n\n\u0002\u0010\u0017\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000f\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0006\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00020\b\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\u00020\n\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\f\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\r0\u0001*\u00020\u000e\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\u00020\u0010\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00110\u0001*\u00020\u0012\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00130\u0001*\u00020\u0014\u001aU\u0010\u0015\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u001c\u001a9\u0010\u0015\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u001d\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a0\u0010\u001e\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\f\u00a2\u0006\u0002\u0010 \u001a \u0010!\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010\"\u001a \u0010#\u001a\u00020$\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010%\u001a0\u0010&\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\f\u00a2\u0006\u0002\u0010 \u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\b2\u0006\u0010\u001f\u001a\u00020\bH\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u001f\u001a\u00020\fH\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010H\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\u0087\f\u001a\u0015\u0010&\u001a\u00020\u0005*\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H\u0087\f\u001a \u0010'\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010\"\u001a\r\u0010'\u001a\u00020\u000f*\u00020\u0006H\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\bH\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\nH\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\fH\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\u000eH\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\u0010H\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\u0012H\u0087\b\u001a\r\u0010'\u001a\u00020\u000f*\u00020\u0014H\u0087\b\u001a \u0010(\u001a\u00020$\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010%\u001a\r\u0010(\u001a\u00020$*\u00020\u0006H\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\bH\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\nH\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\fH\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\u000eH\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\u0010H\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\u0012H\u0087\b\u001a\r\u0010(\u001a\u00020$*\u00020\u0014H\u0087\b\u001a$\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010*\u001a.\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u00a2\u0006\u0002\u0010,\u001a\r\u0010)\u001a\u00020\u0006*\u00020\u0006H\u0087\b\u001a\u0015\u0010)\u001a\u00020\u0006*\u00020\u00062\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\b*\u00020\bH\u0087\b\u001a\u0015\u0010)\u001a\u00020\b*\u00020\b2\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\n*\u00020\nH\u0087\b\u001a\u0015\u0010)\u001a\u00020\n*\u00020\n2\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\f*\u00020\fH\u0087\b\u001a\u0015\u0010)\u001a\u00020\f*\u00020\f2\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\u000e*\u00020\u000eH\u0087\b\u001a\u0015\u0010)\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\u0010*\u00020\u0010H\u0087\b\u001a\u0015\u0010)\u001a\u00020\u0010*\u00020\u00102\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\u0012*\u00020\u0012H\u0087\b\u001a\u0015\u0010)\u001a\u00020\u0012*\u00020\u00122\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a\r\u0010)\u001a\u00020\u0014*\u00020\u0014H\u0087\b\u001a\u0015\u0010)\u001a\u00020\u0014*\u00020\u00142\u0006\u0010+\u001a\u00020\u000fH\u0087\b\u001a4\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u00a2\u0006\u0002\u0010.\u001a\u001d\u0010-\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010-\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0087\b\u001a7\u0010/\u001a\u000200\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u00a2\u0006\u0002\u00101\u001a&\u0010/\u001a\u000200*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010/\u001a\u000200*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a-\u00102\u001a\b\u0012\u0004\u0012\u0002H30\u0001\"\u0004\b\u0000\u00103*\u0006\u0012\u0002\b\u00030\u00032\f\u00104\u001a\b\u0012\u0004\u0012\u0002H305\u00a2\u0006\u0002\u00106\u001aA\u00107\u001a\u0002H8\"\u0010\b\u0000\u00108*\n\u0012\u0006\b\u0000\u0012\u0002H309\"\u0004\b\u0001\u00103*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010:\u001a\u0002H82\f\u00104\u001a\b\u0012\u0004\u0012\u0002H305\u00a2\u0006\u0002\u0010;\u001a,\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0086\u0002\u00a2\u0006\u0002\u0010=\u001a4\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0010>\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0086\u0002\u00a2\u0006\u0002\u0010?\u001a2\u0010<\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H\u00020@H\u0086\u0002\u00a2\u0006\u0002\u0010A\u001a\u0015\u0010<\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0005H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0006*\u00020\u00062\u0006\u0010>\u001a\u00020\u0006H\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\u0006*\u00020\u00062\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00050@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\b*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0007H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\b*\u00020\b2\u0006\u0010>\u001a\u00020\bH\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\b*\u00020\b2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00070@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\n*\u00020\n2\u0006\u0010\u0016\u001a\u00020\tH\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\n*\u00020\n2\u0006\u0010>\u001a\u00020\nH\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\n*\u00020\n2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\t0@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000bH\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\f*\u00020\f2\u0006\u0010>\u001a\u00020\fH\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\f*\u00020\f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u000b0@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010>\u001a\u00020\u000eH\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\u000e*\u00020\u000e2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\r0@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000fH\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0010*\u00020\u00102\u0006\u0010>\u001a\u00020\u0010H\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\u0010*\u00020\u00102\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u000f0@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0011H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0012*\u00020\u00122\u0006\u0010>\u001a\u00020\u0012H\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\u0012*\u00020\u00122\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00110@H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0013H\u0086\u0002\u001a\u0015\u0010<\u001a\u00020\u0014*\u00020\u00142\u0006\u0010>\u001a\u00020\u0014H\u0086\u0002\u001a\u001b\u0010<\u001a\u00020\u0014*\u00020\u00142\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00130@H\u0086\u0002\u001a,\u0010B\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0087\b\u00a2\u0006\u0002\u0010=\u001a\u001d\u0010C\u001a\u000200\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003\u00a2\u0006\u0002\u0010D\u001a*\u0010C\u001a\u000200\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020E*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010F\u001a1\u0010C\u001a\u000200\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u00a2\u0006\u0002\u0010G\u001a\n\u0010C\u001a\u000200*\u00020\b\u001a\u001e\u0010C\u001a\u000200*\u00020\b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010C\u001a\u000200*\u00020\n\u001a\u001e\u0010C\u001a\u000200*\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010C\u001a\u000200*\u00020\f\u001a\u001e\u0010C\u001a\u000200*\u00020\f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010C\u001a\u000200*\u00020\u000e\u001a\u001e\u0010C\u001a\u000200*\u00020\u000e2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010C\u001a\u000200*\u00020\u0010\u001a\u001e\u0010C\u001a\u000200*\u00020\u00102\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010C\u001a\u000200*\u00020\u0012\u001a\u001e\u0010C\u001a\u000200*\u00020\u00122\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010C\u001a\u000200*\u00020\u0014\u001a\u001e\u0010C\u001a\u000200*\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a9\u0010H\u001a\u000200\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0019\u00a2\u0006\u0002\u0010I\u001aM\u0010H\u001a\u000200\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u00a2\u0006\u0002\u0010J\u001a-\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u00020L\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020E*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003\u00a2\u0006\u0002\u0010M\u001a?\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u00020L\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0019\u00a2\u0006\u0002\u0010N\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00050L*\u00020\u0006\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00070L*\u00020\b\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\t0L*\u00020\n\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000b0L*\u00020\f\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\r0L*\u00020\u000e\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000f0L*\u00020\u0010\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00110L*\u00020\u0012\u001a\u0010\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00130L*\u00020\u0014\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003*\u00020\u0006\u00a2\u0006\u0002\u0010P\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003*\u00020\b\u00a2\u0006\u0002\u0010Q\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\t0\u0003*\u00020\n\u00a2\u0006\u0002\u0010R\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003*\u00020\f\u00a2\u0006\u0002\u0010S\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\r0\u0003*\u00020\u000e\u00a2\u0006\u0002\u0010T\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003*\u00020\u0010\u00a2\u0006\u0002\u0010U\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003*\u00020\u0012\u00a2\u0006\u0002\u0010V\u001a\u0015\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003*\u00020\u0014\u00a2\u0006\u0002\u0010W\u00a8\u0006X"}, d2={"asList", "", "T", "", "([Ljava/lang/Object;)Ljava/util/List;", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "binarySearch", "element", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;II)I", "([Ljava/lang/Object;Ljava/lang/Object;II)I", "contentDeepEquals", "other", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepHashCode", "([Ljava/lang/Object;)I", "contentDeepToString", "", "([Ljava/lang/Object;)Ljava/lang/String;", "contentEquals", "contentHashCode", "contentToString", "copyOf", "([Ljava/lang/Object;)[Ljava/lang/Object;", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRange", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "fill", "", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "filterIsInstance", "R", "klass", "Ljava/lang/Class;", "([Ljava/lang/Object;Ljava/lang/Class;)Ljava/util/List;", "filterIsInstanceTo", "C", "", "destination", "([Ljava/lang/Object;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "plus", "([Ljava/lang/Object;Ljava/lang/Object;)[Ljava/lang/Object;", "elements", "([Ljava/lang/Object;[Ljava/lang/Object;)[Ljava/lang/Object;", "", "([Ljava/lang/Object;Ljava/util/Collection;)[Ljava/lang/Object;", "plusElement", "sort", "([Ljava/lang/Object;)V", "", "([Ljava/lang/Comparable;)V", "([Ljava/lang/Object;II)V", "sortWith", "([Ljava/lang/Object;Ljava/util/Comparator;)V", "([Ljava/lang/Object;Ljava/util/Comparator;II)V", "toSortedSet", "Ljava/util/SortedSet;", "([Ljava/lang/Comparable;)Ljava/util/SortedSet;", "([Ljava/lang/Object;Ljava/util/Comparator;)Ljava/util/SortedSet;", "toTypedArray", "([Z)[Ljava/lang/Boolean;", "([B)[Ljava/lang/Byte;", "([C)[Ljava/lang/Character;", "([D)[Ljava/lang/Double;", "([F)[Ljava/lang/Float;", "([I)[Ljava/lang/Integer;", "([J)[Ljava/lang/Long;", "([S)[Ljava/lang/Short;", "kotlin-stdlib"}, xs="kotlin/collections/ArraysKt")
class ArraysKt___ArraysJvmKt
extends ArraysKt__ArraysKt {
    @NotNull
    public static final <R> List<R> filterIsInstance(@NotNull Object[] $receiver, @NotNull Class<R> klass) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        return (List)ArraysKt.filterIsInstanceTo($receiver, (Collection)new ArrayList(), klass);
    }

    @NotNull
    public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(@NotNull Object[] $receiver, @NotNull C destination, @NotNull Class<R> klass) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        for (Object element : $receiver) {
            if (!klass.isInstance(element)) continue;
            destination.add((Object)element);
        }
        return destination;
    }

    @NotNull
    public static final <T> List<T> asList(@NotNull T[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        List<T> list = ArraysUtilJVM.asList($receiver);
        Intrinsics.checkExpressionValueIsNotNull(list, "ArraysUtilJVM.asList(this)");
        return list;
    }

    @NotNull
    public static final List<Byte> asList(@NotNull byte[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ byte[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                byte[] arrby = this.receiver$0;
                return arrby.length == 0;
            }

            public boolean contains(byte element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Byte get(int index) {
                return this.receiver$0[index];
            }

            public int indexOf(byte element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(byte element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Short> asList(@NotNull short[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ short[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                short[] arrs = this.receiver$0;
                return arrs.length == 0;
            }

            public boolean contains(short element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Short get(int index) {
                return this.receiver$0[index];
            }

            public int indexOf(short element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(short element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Integer> asList(@NotNull int[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ int[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                int[] arrn = this.receiver$0;
                return arrn.length == 0;
            }

            public boolean contains(int element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Integer get(int index) {
                return this.receiver$0[index];
            }

            public int indexOf(int element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(int element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Long> asList(@NotNull long[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ long[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                long[] arrl = this.receiver$0;
                return arrl.length == 0;
            }

            public boolean contains(long element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Long get(int index) {
                return this.receiver$0[index];
            }

            public int indexOf(long element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(long element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Float> asList(@NotNull float[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ float[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                float[] arrf = this.receiver$0;
                return arrf.length == 0;
            }

            public boolean contains(float element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Float get(int index) {
                return Float.valueOf(this.receiver$0[index]);
            }

            public int indexOf(float element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(float element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Double> asList(@NotNull double[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ double[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                double[] arrd = this.receiver$0;
                return arrd.length == 0;
            }

            public boolean contains(double element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Double get(int index) {
                return this.receiver$0[index];
            }

            public int indexOf(double element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(double element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Boolean> asList(@NotNull boolean[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ boolean[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                boolean[] arrbl = this.receiver$0;
                return arrbl.length == 0;
            }

            public boolean contains(boolean element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Boolean get(int index) {
                return this.receiver$0[index];
            }

            public int indexOf(boolean element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(boolean element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    @NotNull
    public static final List<Character> asList(@NotNull char[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)((Object)new RandomAccess($receiver){
            final /* synthetic */ char[] receiver$0;

            public int getSize() {
                return this.receiver$0.length;
            }

            public boolean isEmpty() {
                char[] arrc = this.receiver$0;
                return arrc.length == 0;
            }

            public boolean contains(char element) {
                return ArraysKt.contains(this.receiver$0, element);
            }

            @NotNull
            public Character get(int index) {
                return Character.valueOf(this.receiver$0[index]);
            }

            public int indexOf(char element) {
                return ArraysKt.indexOf(this.receiver$0, element);
            }

            public int lastIndexOf(char element) {
                return ArraysKt.lastIndexOf(this.receiver$0, element);
            }
            {
                this.receiver$0 = $receiver;
            }
        });
    }

    public static final <T> int binarySearch(@NotNull T[] $receiver, T element, @NotNull Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element, comparator);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(Object[] arrobject, Object object, Comparator comparator, int n, int n2, int n3, Object object2) {
        if ((n3 & 4) != 0) {
            n = 0;
        }
        if ((n3 & 8) != 0) {
            n2 = arrobject.length;
        }
        return ArraysKt.binarySearch(arrobject, object, comparator, n, n2);
    }

    public static final <T> int binarySearch(@NotNull T[] $receiver, T element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(Object[] arrobject, Object object, int n, int n2, int n3, Object object2) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrobject.length;
        }
        return ArraysKt.binarySearch(arrobject, object, n, n2);
    }

    public static final int binarySearch(@NotNull byte[] $receiver, byte element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(byte[] arrby, byte by, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrby.length;
        }
        return ArraysKt.binarySearch(arrby, by, n, n2);
    }

    public static final int binarySearch(@NotNull short[] $receiver, short element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(short[] arrs, short s, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrs.length;
        }
        return ArraysKt.binarySearch(arrs, s, n, n2);
    }

    public static final int binarySearch(@NotNull int[] $receiver, int element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(int[] arrn, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n2 = 0;
        }
        if ((n4 & 4) != 0) {
            n3 = arrn.length;
        }
        return ArraysKt.binarySearch(arrn, n, n2, n3);
    }

    public static final int binarySearch(@NotNull long[] $receiver, long element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(long[] arrl, long l, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrl.length;
        }
        return ArraysKt.binarySearch(arrl, l, n, n2);
    }

    public static final int binarySearch(@NotNull float[] $receiver, float element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(float[] arrf, float f, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrf.length;
        }
        return ArraysKt.binarySearch(arrf, f, n, n2);
    }

    public static final int binarySearch(@NotNull double[] $receiver, double element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(double[] arrd, double d, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrd.length;
        }
        return ArraysKt.binarySearch(arrd, d, n, n2);
    }

    public static final int binarySearch(@NotNull char[] $receiver, char element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Arrays.binarySearch($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(char[] arrc, char c, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrc.length;
        }
        return ArraysKt.binarySearch(arrc, c, n, n2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> boolean contentDeepEquals(@NotNull T[] $receiver, T[] other) {
        return Arrays.deepEquals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> int contentDeepHashCode(@NotNull T[] $receiver) {
        return Arrays.deepHashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> String contentDeepToString(@NotNull T[] $receiver) {
        String string = Arrays.deepToString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.deepToString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> boolean contentEquals(@NotNull T[] $receiver, T[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull byte[] $receiver, byte[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull short[] $receiver, short[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull int[] $receiver, int[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull long[] $receiver, long[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull float[] $receiver, float[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull double[] $receiver, double[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull boolean[] $receiver, boolean[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final boolean contentEquals(@NotNull char[] $receiver, char[] other) {
        return Arrays.equals($receiver, other);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> int contentHashCode(@NotNull T[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull byte[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull short[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull int[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull long[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull float[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull double[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull boolean[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final int contentHashCode(@NotNull char[] $receiver) {
        return Arrays.hashCode($receiver);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> String contentToString(@NotNull T[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull byte[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull short[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull int[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull long[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull float[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull double[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull boolean[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final String contentToString(@NotNull char[] $receiver) {
        String string = Arrays.toString($receiver);
        Intrinsics.checkExpressionValueIsNotNull(string, "java.util.Arrays.toString(this)");
        return string;
    }

    @InlineOnly
    private static final <T> T[] copyOf(@NotNull T[] $receiver) {
        T[] arrT = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrT, "java.util.Arrays.copyOf(this, size)");
        return arrT;
    }

    @InlineOnly
    private static final byte[] copyOf(@NotNull byte[] $receiver) {
        byte[] arrby = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        return arrby;
    }

    @InlineOnly
    private static final short[] copyOf(@NotNull short[] $receiver) {
        short[] arrs = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrs, "java.util.Arrays.copyOf(this, size)");
        return arrs;
    }

    @InlineOnly
    private static final int[] copyOf(@NotNull int[] $receiver) {
        int[] arrn = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrn, "java.util.Arrays.copyOf(this, size)");
        return arrn;
    }

    @InlineOnly
    private static final long[] copyOf(@NotNull long[] $receiver) {
        long[] arrl = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrl, "java.util.Arrays.copyOf(this, size)");
        return arrl;
    }

    @InlineOnly
    private static final float[] copyOf(@NotNull float[] $receiver) {
        float[] arrf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrf, "java.util.Arrays.copyOf(this, size)");
        return arrf;
    }

    @InlineOnly
    private static final double[] copyOf(@NotNull double[] $receiver) {
        double[] arrd = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrd, "java.util.Arrays.copyOf(this, size)");
        return arrd;
    }

    @InlineOnly
    private static final boolean[] copyOf(@NotNull boolean[] $receiver) {
        boolean[] arrbl = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrbl, "java.util.Arrays.copyOf(this, size)");
        return arrbl;
    }

    @InlineOnly
    private static final char[] copyOf(@NotNull char[] $receiver) {
        char[] arrc = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(arrc, "java.util.Arrays.copyOf(this, size)");
        return arrc;
    }

    @InlineOnly
    private static final byte[] copyOf(@NotNull byte[] $receiver, int newSize) {
        byte[] arrby = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, newSize)");
        return arrby;
    }

    @InlineOnly
    private static final short[] copyOf(@NotNull short[] $receiver, int newSize) {
        short[] arrs = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrs, "java.util.Arrays.copyOf(this, newSize)");
        return arrs;
    }

    @InlineOnly
    private static final int[] copyOf(@NotNull int[] $receiver, int newSize) {
        int[] arrn = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrn, "java.util.Arrays.copyOf(this, newSize)");
        return arrn;
    }

    @InlineOnly
    private static final long[] copyOf(@NotNull long[] $receiver, int newSize) {
        long[] arrl = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrl, "java.util.Arrays.copyOf(this, newSize)");
        return arrl;
    }

    @InlineOnly
    private static final float[] copyOf(@NotNull float[] $receiver, int newSize) {
        float[] arrf = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrf, "java.util.Arrays.copyOf(this, newSize)");
        return arrf;
    }

    @InlineOnly
    private static final double[] copyOf(@NotNull double[] $receiver, int newSize) {
        double[] arrd = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrd, "java.util.Arrays.copyOf(this, newSize)");
        return arrd;
    }

    @InlineOnly
    private static final boolean[] copyOf(@NotNull boolean[] $receiver, int newSize) {
        boolean[] arrbl = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrbl, "java.util.Arrays.copyOf(this, newSize)");
        return arrbl;
    }

    @InlineOnly
    private static final char[] copyOf(@NotNull char[] $receiver, int newSize) {
        char[] arrc = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrc, "java.util.Arrays.copyOf(this, newSize)");
        return arrc;
    }

    @InlineOnly
    private static final <T> T[] copyOf(@NotNull T[] $receiver, int newSize) {
        T[] arrT = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(arrT, "java.util.Arrays.copyOf(this, newSize)");
        return arrT;
    }

    @InlineOnly
    private static final <T> T[] copyOfRange(@NotNull T[] $receiver, int fromIndex, int toIndex) {
        T[] arrT = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrT, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrT;
    }

    @InlineOnly
    private static final byte[] copyOfRange(@NotNull byte[] $receiver, int fromIndex, int toIndex) {
        byte[] arrby = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrby;
    }

    @InlineOnly
    private static final short[] copyOfRange(@NotNull short[] $receiver, int fromIndex, int toIndex) {
        short[] arrs = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrs, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrs;
    }

    @InlineOnly
    private static final int[] copyOfRange(@NotNull int[] $receiver, int fromIndex, int toIndex) {
        int[] arrn = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrn, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrn;
    }

    @InlineOnly
    private static final long[] copyOfRange(@NotNull long[] $receiver, int fromIndex, int toIndex) {
        long[] arrl = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrl, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrl;
    }

    @InlineOnly
    private static final float[] copyOfRange(@NotNull float[] $receiver, int fromIndex, int toIndex) {
        float[] arrf = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrf, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrf;
    }

    @InlineOnly
    private static final double[] copyOfRange(@NotNull double[] $receiver, int fromIndex, int toIndex) {
        double[] arrd = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrd, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrd;
    }

    @InlineOnly
    private static final boolean[] copyOfRange(@NotNull boolean[] $receiver, int fromIndex, int toIndex) {
        boolean[] arrbl = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrbl, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrbl;
    }

    @InlineOnly
    private static final char[] copyOfRange(@NotNull char[] $receiver, int fromIndex, int toIndex) {
        char[] arrc = Arrays.copyOfRange($receiver, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(arrc, "java.util.Arrays.copyOfR\u2026this, fromIndex, toIndex)");
        return arrc;
    }

    public static final <T> void fill(@NotNull T[] $receiver, T element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(Object[] arrobject, Object object, int n, int n2, int n3, Object object2) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrobject.length;
        }
        ArraysKt.fill(arrobject, object, n, n2);
    }

    public static final void fill(@NotNull byte[] $receiver, byte element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(byte[] arrby, byte by, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrby.length;
        }
        ArraysKt.fill(arrby, by, n, n2);
    }

    public static final void fill(@NotNull short[] $receiver, short element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(short[] arrs, short s, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrs.length;
        }
        ArraysKt.fill(arrs, s, n, n2);
    }

    public static final void fill(@NotNull int[] $receiver, int element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(int[] arrn, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n2 = 0;
        }
        if ((n4 & 4) != 0) {
            n3 = arrn.length;
        }
        ArraysKt.fill(arrn, n, n2, n3);
    }

    public static final void fill(@NotNull long[] $receiver, long element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(long[] arrl, long l, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrl.length;
        }
        ArraysKt.fill(arrl, l, n, n2);
    }

    public static final void fill(@NotNull float[] $receiver, float element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(float[] arrf, float f, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrf.length;
        }
        ArraysKt.fill(arrf, f, n, n2);
    }

    public static final void fill(@NotNull double[] $receiver, double element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(double[] arrd, double d, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrd.length;
        }
        ArraysKt.fill(arrd, d, n, n2);
    }

    public static final void fill(@NotNull boolean[] $receiver, boolean element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(boolean[] arrbl, boolean bl, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrbl.length;
        }
        ArraysKt.fill(arrbl, bl, n, n2);
    }

    public static final void fill(@NotNull char[] $receiver, char element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.fill($receiver, fromIndex, toIndex, element);
    }

    public static /* bridge */ /* synthetic */ void fill$default(char[] arrc, char c, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrc.length;
        }
        ArraysKt.fill(arrc, c, n, n2);
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] $receiver, T element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        T[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] $receiver, byte element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        byte[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] $receiver, short element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        short[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] $receiver, int element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        int[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] $receiver, long element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        long[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] $receiver, float element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        float[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] $receiver, double element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        double[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] $receiver, boolean element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        boolean[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] $receiver, char element) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.length;
        char[] result = Arrays.copyOf($receiver, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] $receiver, @NotNull Collection<? extends T> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        T[] result = Arrays.copyOf($receiver, index + elements.size());
        for (T element : elements) {
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] $receiver, @NotNull Collection<Byte> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        byte[] result = Arrays.copyOf($receiver, index + elements.size());
        Iterator<Byte> iterator2 = elements.iterator();
        while (iterator2.hasNext()) {
            byte element = ((Number)iterator2.next()).byteValue();
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] $receiver, @NotNull Collection<Short> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        short[] result = Arrays.copyOf($receiver, index + elements.size());
        Iterator<Short> iterator2 = elements.iterator();
        while (iterator2.hasNext()) {
            short element = ((Number)iterator2.next()).shortValue();
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] $receiver, @NotNull Collection<Integer> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        int[] result = Arrays.copyOf($receiver, index + elements.size());
        Iterator<Integer> iterator2 = elements.iterator();
        while (iterator2.hasNext()) {
            int element = ((Number)iterator2.next()).intValue();
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] $receiver, @NotNull Collection<Long> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        long[] result = Arrays.copyOf($receiver, index + elements.size());
        Iterator<Long> iterator2 = elements.iterator();
        while (iterator2.hasNext()) {
            long element = ((Number)iterator2.next()).longValue();
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] $receiver, @NotNull Collection<Float> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        float[] result = Arrays.copyOf($receiver, index + elements.size());
        Iterator<Float> iterator2 = elements.iterator();
        while (iterator2.hasNext()) {
            float element = ((Number)iterator2.next()).floatValue();
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] $receiver, @NotNull Collection<Double> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        double[] result = Arrays.copyOf($receiver, index + elements.size());
        Iterator<Double> iterator2 = elements.iterator();
        while (iterator2.hasNext()) {
            double element = ((Number)iterator2.next()).doubleValue();
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] $receiver, @NotNull Collection<Boolean> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        boolean[] result = Arrays.copyOf($receiver, index + elements.size());
        for (boolean element : elements) {
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] $receiver, @NotNull Collection<Character> elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $receiver.length;
        char[] result = Arrays.copyOf($receiver, index + elements.size());
        for (char element : elements) {
            result[index++] = element;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final <T> T[] plus(@NotNull T[] $receiver, @NotNull T[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        T[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final byte[] plus(@NotNull byte[] $receiver, @NotNull byte[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        byte[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final short[] plus(@NotNull short[] $receiver, @NotNull short[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        short[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final int[] plus(@NotNull int[] $receiver, @NotNull int[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        int[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final long[] plus(@NotNull long[] $receiver, @NotNull long[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        long[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final float[] plus(@NotNull float[] $receiver, @NotNull float[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        float[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final double[] plus(@NotNull double[] $receiver, @NotNull double[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        double[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final boolean[] plus(@NotNull boolean[] $receiver, @NotNull boolean[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        boolean[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @NotNull
    public static final char[] plus(@NotNull char[] $receiver, @NotNull char[] elements) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $receiver.length;
        int arraySize = elements.length;
        char[] result = Arrays.copyOf($receiver, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    @InlineOnly
    private static final <T> T[] plusElement(@NotNull T[] $receiver, T element) {
        return ArraysKt.plus($receiver, element);
    }

    public static final void sort(@NotNull int[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final void sort(@NotNull long[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final void sort(@NotNull byte[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final void sort(@NotNull short[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final void sort(@NotNull double[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final void sort(@NotNull float[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final void sort(@NotNull char[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    @InlineOnly
    private static final <T extends Comparable<? super T>> void sort(@NotNull T[] $receiver) {
        if ($receiver == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        ArraysKt.sort($receiver);
    }

    public static final <T> void sort(@NotNull T[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length > 1) {
            Arrays.sort($receiver);
        }
    }

    public static final <T> void sort(@NotNull T[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(Object[] arrobject, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrobject.length;
        }
        ArraysKt.sort(arrobject, n, n2);
    }

    public static final void sort(@NotNull byte[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(byte[] arrby, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrby.length;
        }
        ArraysKt.sort(arrby, n, n2);
    }

    public static final void sort(@NotNull short[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(short[] arrs, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrs.length;
        }
        ArraysKt.sort(arrs, n, n2);
    }

    public static final void sort(@NotNull int[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(int[] arrn, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrn.length;
        }
        ArraysKt.sort(arrn, n, n2);
    }

    public static final void sort(@NotNull long[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(long[] arrl, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrl.length;
        }
        ArraysKt.sort(arrl, n, n2);
    }

    public static final void sort(@NotNull float[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(float[] arrf, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrf.length;
        }
        ArraysKt.sort(arrf, n, n2);
    }

    public static final void sort(@NotNull double[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(double[] arrd, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrd.length;
        }
        ArraysKt.sort(arrd, n, n2);
    }

    public static final void sort(@NotNull char[] $receiver, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Arrays.sort($receiver, fromIndex, toIndex);
    }

    public static /* bridge */ /* synthetic */ void sort$default(char[] arrc, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = arrc.length;
        }
        ArraysKt.sort(arrc, n, n2);
    }

    public static final <T> void sortWith(@NotNull T[] $receiver, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if ($receiver.length > 1) {
            Arrays.sort($receiver, comparator);
        }
    }

    public static final <T> void sortWith(@NotNull T[] $receiver, @NotNull Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        Arrays.sort($receiver, fromIndex, toIndex, comparator);
    }

    public static /* bridge */ /* synthetic */ void sortWith$default(Object[] arrobject, Comparator comparator, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrobject.length;
        }
        ArraysKt.sortWith(arrobject, comparator, n, n2);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Byte[] toTypedArray(@NotNull byte[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Byte[] result = new Byte[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = $receiver[index];
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Short[] toTypedArray(@NotNull short[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Short[] result = new Short[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = $receiver[index];
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Integer[] toTypedArray(@NotNull int[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Integer[] result = new Integer[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = $receiver[index];
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Long[] toTypedArray(@NotNull long[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Long[] result = new Long[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = $receiver[index];
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Float[] toTypedArray(@NotNull float[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Float[] result = new Float[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = Float.valueOf($receiver[index]);
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Double[] toTypedArray(@NotNull double[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Double[] result = new Double[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = $receiver[index];
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Boolean[] toTypedArray(@NotNull boolean[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Boolean[] result = new Boolean[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = $receiver[index];
            ++index;
        }
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Character[] toTypedArray(@NotNull char[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Character[] result = new Character[$receiver.length];
        int n = 0;
        int n2 = $receiver.length;
        while (n < n2) {
            void index;
            result[index] = Character.valueOf($receiver[index]);
            ++index;
        }
        return result;
    }

    @NotNull
    public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(@NotNull T[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Byte> toSortedSet(@NotNull byte[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Short> toSortedSet(@NotNull short[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Integer> toSortedSet(@NotNull int[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Long> toSortedSet(@NotNull long[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Float> toSortedSet(@NotNull float[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Double> toSortedSet(@NotNull double[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Boolean> toSortedSet(@NotNull boolean[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final SortedSet<Character> toSortedSet(@NotNull char[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet());
    }

    @NotNull
    public static final <T> SortedSet<T> toSortedSet(@NotNull T[] $receiver, @NotNull Comparator<? super T> comparator) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return (SortedSet)ArraysKt.toCollection($receiver, (Collection)new TreeSet<T>(comparator));
    }
}

