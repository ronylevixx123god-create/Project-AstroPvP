/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt___StringsJvmKt;
import kotlin.text.StringsKt___StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000\u00d4\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a4\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001d0\n*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u001a4\u0010!\u001a\b\u0012\u0004\u0012\u0002H 0\n\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a\r\u0010\"\u001a\u00020\u001f*\u00020\u0002H\u0087\b\u001a!\u0010\"\u001a\u00020\u001f*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0012\u0010#\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a\u0012\u0010%\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0012\u0010%\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a!\u0010&\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010&\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010'\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010'\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0015\u0010(\u001a\u00020\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001fH\u0087\b\u001a)\u0010*\u001a\u00020\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001f2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010,\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001fH\u0087\b\u00a2\u0006\u0002\u0010-\u001a!\u0010.\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010.\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u0010/\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000100H\u0086\b\u001a6\u0010/\u001a\u00020\u001d*\u00020\u001d2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000100H\u0086\b\u001aQ\u00103\u001a\u0002H4\"\f\b\u0000\u00104*\u000605j\u0002`6*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000100H\u0086\b\u00a2\u0006\u0002\u00107\u001a!\u00108\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00108\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u00109\u001a\u0002H4\"\f\b\u0000\u00104*\u000605j\u0002`6*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010:\u001a<\u0010;\u001a\u0002H4\"\f\b\u0000\u00104*\u000605j\u0002`6*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010:\u001a(\u0010<\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010=\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010=\u001a\n\u0010?\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010?\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010A\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010=\u001a3\u0010B\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H 0\b0\u0004H\u0086\b\u001aL\u0010C\u001a\u0002H4\"\u0004\b\u0000\u0010 \"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H 0\b0\u0004H\u0086\b\u00a2\u0006\u0002\u0010E\u001aI\u0010F\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2'\u0010H\u001a#\u0012\u0013\u0012\u0011H \u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 00H\u0086\b\u00a2\u0006\u0002\u0010J\u001a^\u0010K\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0013\u0012\u0011H \u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0LH\u0086\b\u00a2\u0006\u0002\u0010M\u001aI\u0010N\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2'\u0010H\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H \u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u0002H 00H\u0086\b\u00a2\u0006\u0002\u0010J\u001a^\u0010O\u001a\u0002H \"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010G\u001a\u0002H 2<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H \u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u0002H 0LH\u0086\b\u00a2\u0006\u0002\u0010M\u001a!\u0010P\u001a\u00020Q*\u00020\u00022\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020Q0\u0004H\u0086\b\u001a6\u0010S\u001a\u00020Q*\u00020\u00022'\u0010R\u001a#\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020Q00H\u0086\b\u001a)\u0010T\u001a\u00020\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001f2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010U\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010)\u001a\u00020\u001f\u00a2\u0006\u0002\u0010-\u001a9\u0010V\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001c0\f\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010V\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001c0\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010W\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050X0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001al\u0010W\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\u0000\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0X0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0019\u001a5\u0010Y\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0Z\"\u0004\b\u0000\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010[\u001a\u00020\u001f*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010\\\u001a\u00020\u001f*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010]\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010]\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010^\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010A\u001a(\u0010^\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010=\u001a-\u0010_\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\u0086\b\u001aB\u0010`\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 00H\u0086\b\u001aH\u0010a\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\b\b\u0000\u0010 *\u00020b*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 00H\u0086\b\u001aa\u0010c\u001a\u0002H4\"\b\b\u0000\u0010 *\u00020b\"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 00H\u0086\b\u00a2\u0006\u0002\u0010d\u001a[\u0010e\u001a\u0002H4\"\u0004\b\u0000\u0010 \"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 00H\u0086\b\u00a2\u0006\u0002\u0010d\u001a3\u0010f\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\b\b\u0000\u0010 *\u00020b*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 0\u0004H\u0086\b\u001aL\u0010g\u001a\u0002H4\"\b\b\u0000\u0010 *\u00020b\"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H 0\u0004H\u0086\b\u00a2\u0006\u0002\u0010E\u001aF\u0010h\u001a\u0002H4\"\u0004\b\u0000\u0010 \"\u0010\b\u0001\u00104*\n\u0012\u0006\b\u0000\u0012\u0002H 0D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H42\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\u0086\b\u00a2\u0006\u0002\u0010E\u001a\u0011\u0010i\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010A\u001a8\u0010j\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010 *\b\u0012\u0004\u0012\u0002H 0k*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\u0086\b\u00a2\u0006\u0002\u0010=\u001a-\u0010m\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010n\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050oj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`p\u00a2\u0006\u0002\u0010q\u001a\u0011\u0010r\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010A\u001a8\u0010s\u001a\u0004\u0018\u00010\u0005\"\u000e\b\u0000\u0010 *\b\u0012\u0004\u0012\u0002H 0k*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H 0\u0004H\u0086\b\u00a2\u0006\u0002\u0010=\u001a-\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010n\u001a\u0016\u0012\u0006\b\u0000\u0012\u00020\u00050oj\n\u0012\u0006\b\u0000\u0012\u00020\u0005`p\u00a2\u0006\u0002\u0010q\u001a\n\u0010u\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010u\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010v\u001a\u0002Hw\"\b\b\u0000\u0010w*\u00020\u0002*\u0002Hw2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020Q0\u0004H\u0087\b\u00a2\u0006\u0002\u0010x\u001a-\u0010y\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010y\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u0010*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u0010z\u001a\u00020\u0005*\u00020\u00022'\u0010H\u001a#\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000500H\u0086\b\u001aK\u0010{\u001a\u00020\u0005*\u00020\u00022<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050LH\u0086\b\u001a6\u0010|\u001a\u00020\u0005*\u00020\u00022'\u0010H\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u000500H\u0086\b\u001aK\u0010}\u001a\u00020\u0005*\u00020\u00022<\u0010H\u001a8\u0012\u0013\u0012\u00110\u001f\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b1\u0012\b\b2\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u00050LH\u0086\b\u001a\n\u0010~\u001a\u00020\u0002*\u00020\u0002\u001a\r\u0010~\u001a\u00020\u001d*\u00020\u001dH\u0087\b\u001a\n\u0010\u007f\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010\u007f\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010A\u001a)\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010=\u001a\u001a\u0010\u0081\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u001f0\b\u001a\u0015\u0010\u0081\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001\u001a\u001d\u0010\u0081\u0001\u001a\u00020\u001d*\u00020\u001d2\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u001f0\bH\u0087\b\u001a\u0015\u0010\u0081\u0001\u001a\u00020\u001d*\u00020\u001d2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001\u001a\"\u0010\u0084\u0001\u001a\u00020\u001f*\u00020\u00022\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001f0\u0004H\u0086\b\u001a$\u0010\u0085\u0001\u001a\u00030\u0086\u0001*\u00020\u00022\u0013\u0010l\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u0086\u00010\u0004H\u0086\b\u001a\u0013\u0010\u0087\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0013\u0010\u0087\u0001\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a\u0013\u0010\u0088\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010$\u001a\u00020\u001f\u001a\u0013\u0010\u0088\u0001\u001a\u00020\u001d*\u00020\u001d2\u0006\u0010$\u001a\u00020\u001f\u001a\"\u0010\u0089\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u0089\u0001\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008a\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008a\u0001\u001a\u00020\u001d*\u00020\u001d2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u008b\u0001\u001a\u0002H4\"\u0010\b\u0000\u00104*\n\u0012\u0006\b\u0000\u0012\u00020\u00050D*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H4\u00a2\u0006\u0003\u0010\u008c\u0001\u001a\u001d\u0010\u008d\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u008e\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u008f\u0001*\u00020\u0002\u001a\u0011\u0010\u0090\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001c*\u00020\u0002\u001a\u0011\u0010\u0091\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050X*\u00020\u0002\u001a\u0012\u0010\u0092\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0093\u0001*\u00020\u0002\u001a1\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0095\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0095\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a1\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020\u001d0\n*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0095\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u0002H 0\n\"\u0004\b\u0000\u0010 *\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\t\b\u0002\u0010\u0095\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H 0\u0004H\u0007\u001a\u0018\u0010\u0098\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u0099\u00010\b*\u00020\u0002\u001a)\u0010\u009a\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001c*\u00020\u00022\u0007\u0010\u009b\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001c\"\u0004\b\u0000\u0010\u000e*\u00020\u00022\u0007\u0010\u009b\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u009c\u0001\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u009d\u0001\u0012\u0004\u0012\u0002H\u000e00H\u0086\b\u001a\u001f\u0010\u009e\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001c*\u00020\u0002H\u0007\u001aT\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u0002H 0\u001c\"\u0004\b\u0000\u0010 *\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u009c\u0001\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b1\u0012\t\b2\u0012\u0005\b\b(\u009d\u0001\u0012\u0004\u0012\u0002H 00H\u0087\b\u00a8\u0006\u009f\u0001"}, d2={"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAt", "index", "elementAtOrElse", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "reduce", "reduceIndexed", "reduceRight", "reduceRightIndexed", "reversed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, xs="kotlin/text/StringsKt")
class StringsKt___StringsKt
extends StringsKt___StringsJvmKt {
    @InlineOnly
    private static final char elementAt(@NotNull CharSequence $receiver, int index) {
        return $receiver.charAt(index);
    }

    @InlineOnly
    private static final char elementAtOrElse(@NotNull CharSequence $receiver, int index, Function1<? super Integer, Character> defaultValue) {
        return index >= 0 && index <= StringsKt.getLastIndex($receiver) ? $receiver.charAt(index) : defaultValue.invoke((Integer)index).charValue();
    }

    @InlineOnly
    private static final Character elementAtOrNull(@NotNull CharSequence $receiver, int index) {
        return StringsKt.getOrNull($receiver, index);
    }

    @InlineOnly
    private static final Character find(@NotNull CharSequence $receiver, Function1<? super Character, Boolean> predicate) {
        Character c;
        block1: {
            CharSequence $receiver$iv;
            CharSequence charSequence = $receiver$iv = $receiver;
            for (int i = 0; i < charSequence.length(); ++i) {
                char element$iv = charSequence.charAt(i);
                if (!predicate.invoke(Character.valueOf(element$iv)).booleanValue()) continue;
                c = Character.valueOf(element$iv);
                break block1;
            }
            c = null;
        }
        return c;
    }

    /*
     * WARNING - void declaration
     */
    @InlineOnly
    private static final Character findLast(@NotNull CharSequence $receiver, Function1<? super Character, Boolean> predicate) {
        Character c;
        block2: {
            CharSequence $receiver$iv = $receiver;
            int n = $receiver$iv.length();
            boolean bl = false;
            while (--n >= 0) {
                void index$iv;
                char element$iv = $receiver$iv.charAt((int)index$iv);
                if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                    c = Character.valueOf(element$iv);
                    break block2;
                }
                --index$iv;
            }
            c = null;
        }
        return c;
    }

    public static final char first(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            throw (Throwable)new NoSuchElementException("Char sequence is empty.");
        }
        return $receiver.charAt(0);
    }

    public static final char first(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            return element;
        }
        throw (Throwable)new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    @Nullable
    public static final Character firstOrNull(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        return charSequence.length() == 0 ? null : Character.valueOf($receiver.charAt(0));
    }

    @Nullable
    public static final Character firstOrNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            return Character.valueOf(element);
        }
        return null;
    }

    @InlineOnly
    private static final char getOrElse(@NotNull CharSequence $receiver, int index, Function1<? super Integer, Character> defaultValue) {
        return index >= 0 && index <= StringsKt.getLastIndex($receiver) ? $receiver.charAt(index) : defaultValue.invoke((Integer)index).charValue();
    }

    @Nullable
    public static final Character getOrNull(@NotNull CharSequence $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return index >= 0 && index <= StringsKt.getLastIndex($receiver) ? Character.valueOf($receiver.charAt(index)) : null;
    }

    /*
     * WARNING - void declaration
     */
    public static final int indexOfFirst(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = 0;
        int n2 = $receiver.length();
        while (n < n2) {
            void index;
            if (predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                return (int)index;
            }
            ++index;
        }
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    public static final int indexOfLast(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = $receiver.length();
        boolean bl = false;
        while (--n >= 0) {
            void index;
            if (predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                return (int)index;
            }
            --index;
        }
        return -1;
    }

    public static final char last(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            throw (Throwable)new NoSuchElementException("Char sequence is empty.");
        }
        return $receiver.charAt(StringsKt.getLastIndex($receiver));
    }

    /*
     * WARNING - void declaration
     */
    public static final char last(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = $receiver.length();
        boolean bl = false;
        while (--n >= 0) {
            void index;
            char element = $receiver.charAt((int)index);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return element;
            }
            --index;
        }
        throw (Throwable)new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    @Nullable
    public static final Character lastOrNull(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        return charSequence.length() == 0 ? null : Character.valueOf($receiver.charAt($receiver.length() - 1));
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final Character lastOrNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = $receiver.length();
        boolean bl = false;
        while (--n >= 0) {
            void index;
            char element = $receiver.charAt((int)index);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                return Character.valueOf(element);
            }
            --index;
        }
        return null;
    }

    public static final char single(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.length()) {
            case 0: {
                throw (Throwable)new NoSuchElementException("Char sequence is empty.");
            }
            case 1: {
                break;
            }
            default: {
                throw (Throwable)new IllegalArgumentException("Char sequence has more than one element.");
            }
        }
        return $receiver.charAt(0);
    }

    public static final char single(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character single = null;
        boolean found = false;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            if (found) {
                throw (Throwable)new IllegalArgumentException("Char sequence contains more than one matching element.");
            }
            single = Character.valueOf(element);
            found = true;
        }
        if (!found) {
            throw (Throwable)new NoSuchElementException("Char sequence contains no character matching the predicate.");
        }
        Character c = single;
        if (c == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
        }
        return c.charValue();
    }

    @Nullable
    public static final Character singleOrNull(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.length() == 1 ? Character.valueOf($receiver.charAt(0)) : null;
    }

    @Nullable
    public static final Character singleOrNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character single = null;
        boolean found = false;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            if (found) {
                return null;
            }
            single = Character.valueOf(element);
            found = true;
        }
        if (!found) {
            return null;
        }
        return single;
    }

    @NotNull
    public static final CharSequence drop(@NotNull CharSequence $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return $receiver.subSequence(RangesKt.coerceAtMost(n, $receiver.length()), $receiver.length());
    }

    @NotNull
    public static final String drop(@NotNull String $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        String string = $receiver;
        int n2 = RangesKt.coerceAtMost(n, $receiver.length());
        String string2 = string.substring(n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        return string2;
    }

    @NotNull
    public static final CharSequence dropLast(@NotNull CharSequence $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return StringsKt.take($receiver, RangesKt.coerceAtLeast($receiver.length() - n, 0));
    }

    @NotNull
    public static final String dropLast(@NotNull String $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return StringsKt.take($receiver, RangesKt.coerceAtLeast($receiver.length() - n, 0));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence dropLastWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = StringsKt.getLastIndex($receiver);
        boolean bl = false;
        while (n >= 0) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                return $receiver.subSequence(0, (int)(index + true));
            }
            --index;
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String dropLastWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = StringsKt.getLastIndex($receiver);
        boolean bl = false;
        while (n >= 0) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                String string = $receiver;
                int n2 = 0;
                void var7_6 = index + true;
                String string2 = string.substring(n2, (int)var7_6);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                return string2;
            }
            --index;
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence dropWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = 0;
        int n2 = $receiver.length();
        while (n < n2) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                return $receiver.subSequence((int)index, $receiver.length());
            }
            ++index;
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String dropWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = 0;
        int n2 = ((CharSequence)$receiver).length();
        while (n < n2) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                String string = $receiver;
                String string2 = string.substring((int)index);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                return string2;
            }
            ++index;
        }
        return "";
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence filter(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        Appendable destination$iv = new StringBuilder();
        int n = 0;
        int n2 = $receiver$iv.length();
        while (n < n2) {
            void index$iv;
            char element$iv = $receiver$iv.charAt((int)index$iv);
            if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                destination$iv.append(element$iv);
            }
            ++index$iv;
        }
        return (CharSequence)((Object)destination$iv);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String filter(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        Appendable destination$iv = new StringBuilder();
        int n = 0;
        int n2 = $receiver$iv.length();
        while (n < n2) {
            void index$iv;
            char element$iv = $receiver$iv.charAt((int)index$iv);
            if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) {
                destination$iv.append(element$iv);
            }
            ++index$iv;
        }
        String string = ((StringBuilder)destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterTo(StringBuilder(), predicate).toString()");
        return string;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence filterIndexed(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        Appendable destination$iv = new StringBuilder();
        void $receiver$iv$iv = $receiver$iv;
        int index$iv$iv = 0;
        void var7_6 = $receiver$iv$iv;
        for (int i = 0; i < var7_6.length(); ++i) {
            void element$iv;
            char item$iv$iv = var7_6.charAt(i);
            int n = index$iv$iv++;
            char c = item$iv$iv;
            int index$iv = n;
            if (!predicate.invoke((Integer)index$iv, Character.valueOf((char)element$iv)).booleanValue()) continue;
            destination$iv.append((char)element$iv);
        }
        return (CharSequence)((Object)destination$iv);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String filterIndexed(@NotNull String $receiver, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        Appendable destination$iv = new StringBuilder();
        void $receiver$iv$iv = $receiver$iv;
        int index$iv$iv = 0;
        void var7_6 = $receiver$iv$iv;
        for (int i = 0; i < var7_6.length(); ++i) {
            void element$iv;
            char item$iv$iv = var7_6.charAt(i);
            int n = index$iv$iv++;
            char c = item$iv$iv;
            int index$iv = n;
            if (!predicate.invoke((Integer)index$iv, Character.valueOf((char)element$iv)).booleanValue()) continue;
            destination$iv.append((char)element$iv);
        }
        String string = ((StringBuilder)destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterIndexedTo(StringBu\u2026(), predicate).toString()");
        return string;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <C extends Appendable> C filterIndexedTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence $receiver$iv = $receiver;
        int index$iv = 0;
        CharSequence charSequence = $receiver$iv;
        for (int i = 0; i < charSequence.length(); ++i) {
            void element;
            char item$iv = charSequence.charAt(i);
            int n = index$iv++;
            char c = item$iv;
            int index = n;
            if (!predicate.invoke((Integer)index, Character.valueOf((char)element)).booleanValue()) continue;
            destination.append((char)element);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence filterNot(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        Appendable destination$iv = new StringBuilder();
        void var5_4 = $receiver$iv;
        for (int i = 0; i < var5_4.length(); ++i) {
            char element$iv = var5_4.charAt(i);
            if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) continue;
            destination$iv.append(element$iv);
        }
        return (CharSequence)((Object)destination$iv);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String filterNot(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        Appendable destination$iv = new StringBuilder();
        void var5_4 = $receiver$iv;
        for (int i = 0; i < var5_4.length(); ++i) {
            char element$iv = var5_4.charAt(i);
            if (predicate.invoke(Character.valueOf(element$iv)).booleanValue()) continue;
            destination$iv.append(element$iv);
        }
        String string = ((StringBuilder)destination$iv).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterNotTo(StringBuilder(), predicate).toString()");
        return string;
    }

    @NotNull
    public static final <C extends Appendable> C filterNotTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            destination.append(element);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <C extends Appendable> C filterTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = 0;
        int n2 = $receiver.length();
        while (n < n2) {
            void index;
            char element = $receiver.charAt((int)index);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                destination.append(element);
            }
            ++index;
        }
        return destination;
    }

    @NotNull
    public static final CharSequence slice(@NotNull CharSequence $receiver, @NotNull IntRange indices) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        if (indices.isEmpty()) {
            return "";
        }
        return StringsKt.subSequence($receiver, indices);
    }

    @NotNull
    public static final String slice(@NotNull String $receiver, @NotNull IntRange indices) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        if (indices.isEmpty()) {
            return "";
        }
        return StringsKt.substring($receiver, indices);
    }

    @NotNull
    public static final CharSequence slice(@NotNull CharSequence $receiver, @NotNull Iterable<Integer> indices) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        int size = CollectionsKt.collectionSizeOrDefault(indices, 10);
        if (size == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder(size);
        Iterator<Integer> iterator2 = indices.iterator();
        while (iterator2.hasNext()) {
            int i = ((Number)iterator2.next()).intValue();
            result.append($receiver.charAt(i));
        }
        return result;
    }

    @InlineOnly
    private static final String slice(@NotNull String $receiver, Iterable<Integer> indices) {
        String string = $receiver;
        if (string == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        return ((Object)StringsKt.slice((CharSequence)string, indices)).toString();
    }

    @NotNull
    public static final CharSequence take(@NotNull CharSequence $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        return $receiver.subSequence(0, RangesKt.coerceAtMost(n, $receiver.length()));
    }

    @NotNull
    public static final String take(@NotNull String $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        String string = $receiver;
        int n2 = 0;
        int n3 = RangesKt.coerceAtMost(n, $receiver.length());
        String string2 = string.substring(n2, n3);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return string2;
    }

    @NotNull
    public static final CharSequence takeLast(@NotNull CharSequence $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        int length = $receiver.length();
        return $receiver.subSequence(length - RangesKt.coerceAtMost(n, length), length);
    }

    @NotNull
    public static final String takeLast(@NotNull String $receiver, int n) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "Requested character count " + n + " is less than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        int length = $receiver.length();
        String string = $receiver;
        int n2 = length - RangesKt.coerceAtMost(n, length);
        String string2 = string.substring(n2);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
        return string2;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence takeLastWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = StringsKt.getLastIndex($receiver);
        boolean bl = false;
        while (n >= 0) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                return $receiver.subSequence((int)(index + true), $receiver.length());
            }
            --index;
        }
        return $receiver.subSequence(0, $receiver.length());
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String takeLastWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = StringsKt.getLastIndex($receiver);
        boolean bl = false;
        while (n >= 0) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                String string = $receiver;
                void var6_5 = index + true;
                String string2 = string.substring((int)var6_5);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                return string2;
            }
            --index;
        }
        return $receiver;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final CharSequence takeWhile(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = 0;
        int n2 = $receiver.length();
        while (n < n2) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                return $receiver.subSequence(0, (int)index);
            }
            ++index;
        }
        return $receiver.subSequence(0, $receiver.length());
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String takeWhile(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int n = 0;
        int n2 = $receiver.length();
        while (n < n2) {
            void index;
            if (!predicate.invoke(Character.valueOf($receiver.charAt((int)index))).booleanValue()) {
                String string = $receiver;
                int n3 = 0;
                String string2 = string.substring(n3, (int)index);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                return string2;
            }
            ++index;
        }
        return $receiver;
    }

    @NotNull
    public static final CharSequence reversed(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        StringBuilder stringBuilder = new StringBuilder($receiver).reverse();
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "StringBuilder(this).reverse()");
        return stringBuilder;
    }

    @InlineOnly
    private static final String reversed(@NotNull String $receiver) {
        String string = $receiver;
        if (string == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        return ((Object)StringsKt.reversed((CharSequence)string)).toString();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <K, V> Map<K, V> associate(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends Pair<? extends K, ? extends V>> transform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity($receiver.length()), 16);
        CharSequence charSequence = $receiver;
        Map destination$iv = new LinkedHashMap(capacity);
        void var6_5 = $receiver$iv;
        for (int i = 0; i < var6_5.length(); ++i) {
            char element$iv = var6_5.charAt(i);
            Map map = destination$iv;
            Pair<K, V> pair = transform.invoke(Character.valueOf(element$iv));
            map.put(pair.getFirst(), pair.getSecond());
        }
        return destination$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <K> Map<K, Character> associateBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity($receiver.length()), 16);
        CharSequence charSequence = $receiver;
        Map destination$iv = new LinkedHashMap(capacity);
        void var6_5 = $receiver$iv;
        for (int i = 0; i < var6_5.length(); ++i) {
            char element$iv = var6_5.charAt(i);
            destination$iv.put(keySelector.invoke(Character.valueOf(element$iv)), Character.valueOf(element$iv));
        }
        return destination$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <K, V> Map<K, V> associateBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        int capacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity($receiver.length()), 16);
        CharSequence charSequence = $receiver;
        Map destination$iv = new LinkedHashMap(capacity);
        void var7_6 = $receiver$iv;
        for (int i = 0; i < var7_6.length(); ++i) {
            char element$iv = var7_6.charAt(i);
            destination$iv.put(keySelector.invoke(Character.valueOf(element$iv)), valueTransform.invoke(Character.valueOf(element$iv)));
        }
        return destination$iv;
    }

    @NotNull
    public static final <K, M extends Map<? super K, ? super Character>> M associateByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(element)), (Character)Character.valueOf(element));
        }
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M associateByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(element)), valueTransform.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M associateTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends Pair<? extends K, ? extends V>> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            M m = destination;
            Pair<K, V> pair = transform.invoke(Character.valueOf(element));
            m.put(pair.getFirst(), pair.getSecond());
        }
        return destination;
    }

    @NotNull
    public static final <C extends Collection<? super Character>> C toCollection(@NotNull CharSequence $receiver, @NotNull C destination) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char item = charSequence.charAt(i);
            destination.add((Character)Character.valueOf(item));
        }
        return destination;
    }

    @NotNull
    public static final HashSet<Character> toHashSet(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (HashSet)StringsKt.toCollection($receiver, (Collection)new HashSet(MapsKt.mapCapacity($receiver.length())));
    }

    @NotNull
    public static final List<Character> toList(@NotNull CharSequence $receiver) {
        List<Character> list;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.length()) {
            case 0: {
                list = CollectionsKt.emptyList();
                break;
            }
            case 1: {
                list = CollectionsKt.listOf(Character.valueOf($receiver.charAt(0)));
                break;
            }
            default: {
                list = StringsKt.toMutableList($receiver);
            }
        }
        return list;
    }

    @NotNull
    public static final List<Character> toMutableList(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return (List)StringsKt.toCollection($receiver, (Collection)new ArrayList($receiver.length()));
    }

    @NotNull
    public static final Set<Character> toSet(@NotNull CharSequence $receiver) {
        Set set;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.length()) {
            case 0: {
                set = SetsKt.emptySet();
                break;
            }
            case 1: {
                set = SetsKt.setOf(Character.valueOf($receiver.charAt(0)));
                break;
            }
            default: {
                set = (Set)StringsKt.toCollection($receiver, (Collection)new LinkedHashSet(MapsKt.mapCapacity($receiver.length())));
            }
        }
        return set;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <R> List<R> flatMap(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends Iterable<? extends R>> transform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        Collection destination$iv = new ArrayList();
        void var5_4 = $receiver$iv;
        for (int i = 0; i < var5_4.length(); ++i) {
            char element$iv = var5_4.charAt(i);
            Iterable<? extends R> list$iv = transform.invoke(Character.valueOf(element$iv));
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (List)destination$iv;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C flatMapTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, ? extends Iterable<? extends R>> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            Iterable<? extends R> list = transform.invoke(Character.valueOf(element));
            CollectionsKt.addAll(destination, list);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <K> Map<K, List<Character>> groupBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        CharSequence charSequence = $receiver;
        Map destination$iv = new LinkedHashMap();
        void var5_4 = $receiver$iv;
        for (int i = 0; i < var5_4.length(); ++i) {
            Object object;
            Map $receiver$iv$iv = destination$iv;
            char element$iv = var5_4.charAt(i);
            K key$iv = keySelector.invoke(Character.valueOf(element$iv));
            Object value$iv$iv = $receiver$iv$iv.get(key$iv);
            if (value$iv$iv == null) {
                ArrayList answer$iv$iv = new ArrayList();
                $receiver$iv$iv.put(key$iv, answer$iv$iv);
                object = answer$iv$iv;
            } else {
                object = value$iv$iv;
            }
            List list$iv = (List)object;
            list$iv.add(Character.valueOf(element$iv));
        }
        return destination$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <K, V> Map<K, List<V>> groupBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        CharSequence charSequence = $receiver;
        Map destination$iv = new LinkedHashMap();
        void var6_5 = $receiver$iv;
        for (int i = 0; i < var6_5.length(); ++i) {
            Object object;
            Map $receiver$iv$iv = destination$iv;
            char element$iv = var6_5.charAt(i);
            K key$iv = keySelector.invoke(Character.valueOf(element$iv));
            Object value$iv$iv = $receiver$iv$iv.get(key$iv);
            if (value$iv$iv == null) {
                ArrayList answer$iv$iv = new ArrayList();
                $receiver$iv$iv.put(key$iv, answer$iv$iv);
                object = answer$iv$iv;
            } else {
                object = value$iv$iv;
            }
            List list$iv = (List)object;
            list$iv.add(valueTransform.invoke(Character.valueOf(element$iv)));
        }
        return destination$iv;
    }

    @NotNull
    public static final <K, M extends Map<? super K, List<Character>>> M groupByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            List<Character> list;
            M $receiver$iv = destination;
            char element = charSequence.charAt(i);
            K key = keySelector.invoke(Character.valueOf(element));
            List<Character> value$iv = $receiver$iv.get(key);
            if (value$iv == null) {
                ArrayList<Character> answer$iv = new ArrayList<Character>();
                $receiver$iv.put(key, answer$iv);
                list = answer$iv;
            } else {
                list = value$iv;
            }
            List<Character> list2 = list;
            list2.add(Character.valueOf(element));
        }
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, List<V>>> M groupByTo(@NotNull CharSequence $receiver, @NotNull M destination, @NotNull Function1<? super Character, ? extends K> keySelector, @NotNull Function1<? super Character, ? extends V> valueTransform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            List<V> list;
            M $receiver$iv = destination;
            char element = charSequence.charAt(i);
            K key = keySelector.invoke(Character.valueOf(element));
            List<V> value$iv = $receiver$iv.get(key);
            if (value$iv == null) {
                ArrayList<V> answer$iv = new ArrayList<V>();
                $receiver$iv.put(key, answer$iv);
                list = answer$iv;
            } else {
                list = value$iv;
            }
            List<V> list2 = list;
            list2.add(valueTransform.invoke(Character.valueOf(element)));
        }
        return destination;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <K> Grouping<Character, K> groupingBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends K> keySelector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        return new Grouping<Character, K>($receiver, keySelector){
            final /* synthetic */ CharSequence receiver$0;
            final /* synthetic */ Function1 $keySelector;

            @NotNull
            public Iterator<Character> sourceIterator() {
                return StringsKt.iterator(this.receiver$0);
            }

            public K keyOf(char element) {
                return (K)this.$keySelector.invoke(Character.valueOf(element));
            }
            {
                this.receiver$0 = $receiver;
                this.$keySelector = $captured_local_variable$1;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <R> List<R> map(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> transform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        Collection destination$iv = new ArrayList($receiver.length());
        void var5_4 = $receiver$iv;
        for (int i = 0; i < var5_4.length(); ++i) {
            char item$iv = var5_4.charAt(i);
            destination$iv.add(transform.invoke(Character.valueOf(item$iv)));
        }
        return (List)destination$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <R> List<R> mapIndexed(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        Collection destination$iv = new ArrayList($receiver.length());
        int index$iv = 0;
        void var6_5 = $receiver$iv;
        for (int i = 0; i < var6_5.length(); ++i) {
            char item$iv = var6_5.charAt(i);
            Integer n = index$iv;
            ++index$iv;
            destination$iv.add(transform.invoke(n, Character.valueOf(item$iv)));
        }
        return (List)destination$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <R> List<R> mapIndexedNotNull(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        Collection destination$iv = new ArrayList();
        void $receiver$iv$iv = $receiver$iv;
        int index$iv$iv = 0;
        void var7_6 = $receiver$iv$iv;
        for (int i = 0; i < var7_6.length(); ++i) {
            R r;
            void element$iv;
            char item$iv$iv = var7_6.charAt(i);
            int n = index$iv$iv++;
            char c = item$iv$iv;
            int index$iv = n;
            if (transform.invoke(index$iv, Character.valueOf((char)element$iv)) == null) continue;
            R it$iv = r;
            destination$iv.add(it$iv);
        }
        return (List)destination$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <R, C extends Collection<? super R>> C mapIndexedNotNullTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence $receiver$iv = $receiver;
        int index$iv = 0;
        CharSequence charSequence = $receiver$iv;
        for (int i = 0; i < charSequence.length(); ++i) {
            R r;
            void element;
            char item$iv = charSequence.charAt(i);
            int n = index$iv++;
            char c = item$iv;
            int index = n;
            if (transform.invoke(index, Character.valueOf((char)element)) == null) continue;
            R it = r;
            destination.add(it);
        }
        return destination;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapIndexedTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function2<? super Integer, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int index = 0;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char item = charSequence.charAt(i);
            Integer n = index;
            ++index;
            destination.add(transform.invoke(n, Character.valueOf(item)));
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <R> List<R> mapNotNull(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> transform) {
        void $receiver$iv;
        void $receiver$iv$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        Collection destination$iv = new ArrayList();
        void var6_5 = $receiver$iv$iv = $receiver$iv;
        for (int i = 0; i < var6_5.length(); ++i) {
            R r;
            char element$iv$iv = var6_5.charAt(i);
            char element$iv = element$iv$iv;
            if (transform.invoke(Character.valueOf(element$iv)) == null) continue;
            R it$iv = r;
            destination$iv.add(it$iv);
        }
        return (List)destination$iv;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapNotNullTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, ? extends R> transform) {
        CharSequence $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver$iv = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            R r;
            char element$iv = charSequence.charAt(i);
            char element = element$iv;
            if (transform.invoke(Character.valueOf(element)) == null) continue;
            R it = r;
            destination.add(it);
        }
        return destination;
    }

    @NotNull
    public static final <R, C extends Collection<? super R>> C mapTo(@NotNull CharSequence $receiver, @NotNull C destination, @NotNull Function1<? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char item = charSequence.charAt(i);
            destination.add(transform.invoke(Character.valueOf(item)));
        }
        return destination;
    }

    @NotNull
    public static final Iterable<IndexedValue<Character>> withIndex(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new IndexingIterable((Function0)new Function0<CharIterator>($receiver){
            final /* synthetic */ CharSequence receiver$0;

            @NotNull
            public final CharIterator invoke() {
                return StringsKt.iterator(this.receiver$0);
            }
            {
                this.receiver$0 = charSequence;
                super(0);
            }
        });
    }

    public static final boolean all(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            return false;
        }
        return true;
    }

    public static final boolean any(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        return !(charSequence.length() == 0);
    }

    public static final boolean any(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            return true;
        }
        return false;
    }

    @InlineOnly
    private static final int count(@NotNull CharSequence $receiver) {
        return $receiver.length();
    }

    public static final int count(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int count = 0;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            ++count;
        }
        return count;
    }

    public static final <R> R fold(@NotNull CharSequence $receiver, R initial, @NotNull Function2<? super R, ? super Character, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        R accumulator = initial;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            accumulator = operation.invoke(accumulator, Character.valueOf(element));
        }
        return accumulator;
    }

    public static final <R> R foldIndexed(@NotNull CharSequence $receiver, R initial, @NotNull Function3<? super Integer, ? super R, ? super Character, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = 0;
        R accumulator = initial;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            Integer n = index;
            ++index;
            accumulator = operation.invoke(n, accumulator, Character.valueOf(element));
        }
        return accumulator;
    }

    public static final <R> R foldRight(@NotNull CharSequence $receiver, R initial, @NotNull Function2<? super Character, ? super R, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = StringsKt.getLastIndex($receiver);
        R accumulator = initial;
        while (index >= 0) {
            accumulator = operation.invoke(Character.valueOf($receiver.charAt(index--)), accumulator);
        }
        return accumulator;
    }

    public static final <R> R foldRightIndexed(@NotNull CharSequence $receiver, R initial, @NotNull Function3<? super Integer, ? super Character, ? super R, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        R accumulator = initial;
        for (int index = StringsKt.getLastIndex($receiver); index >= 0; --index) {
            accumulator = operation.invoke(index, Character.valueOf($receiver.charAt(index)), accumulator);
        }
        return accumulator;
    }

    public static final void forEach(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            action.invoke(Character.valueOf(element));
        }
    }

    public static final void forEachIndexed(@NotNull CharSequence $receiver, @NotNull Function2<? super Integer, ? super Character, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int index = 0;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char item = charSequence.charAt(i);
            Integer n = index;
            ++index;
            action.invoke(n, Character.valueOf(item));
        }
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final Character max(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            return null;
        }
        char max = $receiver.charAt(0);
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void i;
            do {
                char e;
                if (max >= (e = $receiver.charAt((int)(++i)))) continue;
                max = e;
            } while (i != n2);
        }
        return Character.valueOf(max);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final <R extends Comparable<? super R>> Character maxBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            return null;
        }
        char maxElem = $receiver.charAt(0);
        Comparable maxValue = (Comparable)selector.invoke(Character.valueOf(maxElem));
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void i;
            do {
                char e;
                Comparable v;
                if (maxValue.compareTo(v = (Comparable)selector.invoke(Character.valueOf(e = $receiver.charAt((int)(++i))))) >= 0) continue;
                maxElem = e;
                maxValue = v;
            } while (i != n2);
        }
        return Character.valueOf(maxElem);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final Character maxWith(@NotNull CharSequence $receiver, @NotNull Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            return null;
        }
        char max = $receiver.charAt(0);
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void i;
            do {
                char e = $receiver.charAt((int)(++i));
                if (comparator.compare(Character.valueOf(max), Character.valueOf(e)) >= 0) continue;
                max = e;
            } while (i != n2);
        }
        return Character.valueOf(max);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final Character min(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            return null;
        }
        char min = $receiver.charAt(0);
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void i;
            do {
                char e;
                if (min <= (e = $receiver.charAt((int)(++i)))) continue;
                min = e;
            } while (i != n2);
        }
        return Character.valueOf(min);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final <R extends Comparable<? super R>> Character minBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, ? extends R> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            return null;
        }
        char minElem = $receiver.charAt(0);
        Comparable minValue = (Comparable)selector.invoke(Character.valueOf(minElem));
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void i;
            do {
                char e;
                Comparable v;
                if (minValue.compareTo(v = (Comparable)selector.invoke(Character.valueOf(e = $receiver.charAt((int)(++i))))) <= 0) continue;
                minElem = e;
                minValue = v;
            } while (i != n2);
        }
        return Character.valueOf(minElem);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final Character minWith(@NotNull CharSequence $receiver, @NotNull Comparator<? super Character> comparator) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            return null;
        }
        char min = $receiver.charAt(0);
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void i;
            do {
                char e = $receiver.charAt((int)(++i));
                if (comparator.compare(Character.valueOf(min), Character.valueOf(e)) <= 0) continue;
                min = e;
            } while (i != n2);
        }
        return Character.valueOf(min);
    }

    public static final boolean none(@NotNull CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence charSequence = $receiver;
        return charSequence.length() == 0;
    }

    public static final boolean none(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (!predicate.invoke(Character.valueOf(element)).booleanValue()) continue;
            return false;
        }
        return true;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <S extends CharSequence> S onEach(@NotNull S $receiver, @NotNull Function1<? super Character, Unit> action) {
        S s;
        S $receiver2;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        S s2 = $receiver2 = (s = $receiver);
        for (int i = 0; i < s2.length(); ++i) {
            char element = s2.charAt(i);
            action.invoke(Character.valueOf(element));
        }
        return s;
    }

    /*
     * WARNING - void declaration
     */
    public static final char reduce(@NotNull CharSequence $receiver, @NotNull Function2<? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = $receiver.charAt(0);
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void index;
            do {
                accumulator = operation.invoke(Character.valueOf(accumulator), Character.valueOf($receiver.charAt((int)(++index)))).charValue();
            } while (index != n2);
        }
        return accumulator;
    }

    /*
     * WARNING - void declaration
     */
    public static final char reduceIndexed(@NotNull CharSequence $receiver, @NotNull Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        CharSequence charSequence = $receiver;
        if (charSequence.length() == 0) {
            throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = $receiver.charAt(0);
        int n = 1;
        int n2 = StringsKt.getLastIndex($receiver);
        if (n <= n2) {
            void index;
            do {
                accumulator = operation.invoke((Integer)((int)(++index)), Character.valueOf(accumulator), Character.valueOf($receiver.charAt((int)index))).charValue();
            } while (index != n2);
        }
        return accumulator;
    }

    public static final char reduceRight(@NotNull CharSequence $receiver, @NotNull Function2<? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = StringsKt.getLastIndex($receiver);
        if (index < 0) {
            throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = $receiver.charAt(index--);
        while (index >= 0) {
            accumulator = operation.invoke(Character.valueOf($receiver.charAt(index--)), Character.valueOf(accumulator)).charValue();
        }
        return accumulator;
    }

    public static final char reduceRightIndexed(@NotNull CharSequence $receiver, @NotNull Function3<? super Integer, ? super Character, ? super Character, Character> operation) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int index = StringsKt.getLastIndex($receiver);
        if (index < 0) {
            throw (Throwable)new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char accumulator = $receiver.charAt(index--);
        while (index >= 0) {
            accumulator = operation.invoke((Integer)index, Character.valueOf($receiver.charAt(index)), Character.valueOf(accumulator)).charValue();
            --index;
        }
        return accumulator;
    }

    public static final int sumBy(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Integer> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int sum = 0;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            sum += ((Number)selector.invoke(Character.valueOf(element))).intValue();
        }
        return sum;
    }

    public static final double sumByDouble(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Double> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        double sum = 0.0;
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            sum += ((Number)selector.invoke(Character.valueOf(element))).doubleValue();
        }
        return sum;
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final List<String> chunked(@NotNull CharSequence $receiver, int size) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.windowed($receiver, size, size, true);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final <R> List<R> chunked(@NotNull CharSequence $receiver, int size, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return StringsKt.windowed($receiver, size, size, true, transform);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final Sequence<String> chunkedSequence(@NotNull CharSequence $receiver, int size) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.chunkedSequence($receiver, size, chunkedSequence.1.INSTANCE);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final <R> Sequence<R> chunkedSequence(@NotNull CharSequence $receiver, int size, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return StringsKt.windowedSequence($receiver, size, size, true, transform);
    }

    @NotNull
    public static final Pair<CharSequence, CharSequence> partition(@NotNull CharSequence $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        CharSequence charSequence = $receiver;
        for (int i = 0; i < charSequence.length(); ++i) {
            char element = charSequence.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                first.append(element);
                continue;
            }
            second.append(element);
        }
        return new Pair<CharSequence, CharSequence>(first, second);
    }

    @NotNull
    public static final Pair<String, String> partition(@NotNull String $receiver, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        String string = $receiver;
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char element = string.charAt(i);
            if (predicate.invoke(Character.valueOf(element)).booleanValue()) {
                first.append(element);
                continue;
            }
            second.append(element);
        }
        return new Pair<String, String>(first.toString(), second.toString());
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final List<String> windowed(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.windowed($receiver, size, step, partialWindows, windowed.1.INSTANCE);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static /* bridge */ /* synthetic */ List windowed$default(CharSequence charSequence, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return StringsKt.windowed(charSequence, n, n2, bl);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final <R> List<R> windowed(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindowKt.checkWindowSizeStep(size, step);
        int thisSize = $receiver.length();
        ArrayList<R> result = new ArrayList<R>((thisSize + step - 1) / step);
        for (int index = 0; index < thisSize; index += step) {
            int n;
            int end = index + size;
            if (end > thisSize) {
                if (!partialWindows) break;
                n = thisSize;
            } else {
                n = end;
            }
            int coercedEnd = n;
            result.add(transform.invoke($receiver.subSequence(index, coercedEnd)));
        }
        return result;
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static /* bridge */ /* synthetic */ List windowed$default(CharSequence charSequence, int n, int n2, boolean bl, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return StringsKt.windowed(charSequence, n, n2, bl, function1);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final Sequence<String> windowedSequence(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return StringsKt.windowedSequence($receiver, size, step, partialWindows, windowedSequence.1.INSTANCE);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static /* bridge */ /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int n, int n2, boolean bl, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return StringsKt.windowedSequence(charSequence, n, n2, bl);
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static final <R> Sequence<R> windowedSequence(@NotNull CharSequence $receiver, int size, int step, boolean partialWindows, @NotNull Function1<? super CharSequence, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindowKt.checkWindowSizeStep(size, step);
        IntProgression windows = RangesKt.step(partialWindows ? StringsKt.getIndices($receiver) : RangesKt.until(0, $receiver.length() - size + 1), step);
        return SequencesKt.map(CollectionsKt.asSequence(windows), new Function1<Integer, R>($receiver, transform, size){
            final /* synthetic */ CharSequence receiver$0;
            final /* synthetic */ Function1 $transform;
            final /* synthetic */ int $size;

            public final R invoke(int index) {
                return this.$transform.invoke(this.receiver$0.subSequence(index, RangesKt.coerceAtMost(index + this.$size, this.receiver$0.length())));
            }
            {
                this.receiver$0 = charSequence;
                this.$transform = function1;
                this.$size = n;
                super(1);
            }
        });
    }

    @SinceKotlin(version="1.2")
    @NotNull
    public static /* bridge */ /* synthetic */ Sequence windowedSequence$default(CharSequence charSequence, int n, int n2, boolean bl, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n2 = 1;
        }
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return StringsKt.windowedSequence(charSequence, n, n2, bl, function1);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<Pair<Character, Character>> zip(@NotNull CharSequence $receiver, @NotNull CharSequence other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        CharSequence $receiver$iv = $receiver;
        int n = $receiver$iv.length();
        int n2 = other.length();
        int length$iv = Math.min(n, n2);
        ArrayList<Pair<Character, Character>> list$iv = new ArrayList<Pair<Character, Character>>(length$iv);
        n2 = 0;
        int n3 = length$iv;
        while (n2 < n3) {
            void c2;
            void c1;
            void i$iv;
            char c = other.charAt((int)i$iv);
            char c3 = $receiver$iv.charAt((int)i$iv);
            ArrayList<Pair<Character, Character>> arrayList = list$iv;
            Pair<Character, Character> pair = TuplesKt.to(Character.valueOf((char)c1), Character.valueOf((char)c2));
            arrayList.add(pair);
            ++i$iv;
        }
        return list$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final <V> List<V> zip(@NotNull CharSequence $receiver, @NotNull CharSequence other, @NotNull Function2<? super Character, ? super Character, ? extends V> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int n = $receiver.length();
        int n2 = other.length();
        int length = Math.min(n, n2);
        ArrayList<V> list = new ArrayList<V>(length);
        n2 = 0;
        int n3 = length;
        while (n2 < n3) {
            void i;
            list.add(transform.invoke(Character.valueOf($receiver.charAt((int)i)), Character.valueOf(other.charAt((int)i))));
            ++i;
        }
        return list;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.2")
    @NotNull
    public static final List<Pair<Character, Character>> zipWithNext(@NotNull CharSequence $receiver) {
        List list;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CharSequence $receiver$iv = $receiver;
        int size$iv = $receiver$iv.length() - 1;
        if (size$iv < 1) {
            list = CollectionsKt.emptyList();
        } else {
            ArrayList<Pair<Character, Character>> result$iv = new ArrayList<Pair<Character, Character>>(size$iv);
            int n = 0;
            int n2 = size$iv;
            while (n < n2) {
                void b;
                void a;
                void index$iv;
                char c = $receiver$iv.charAt((int)(index$iv + true));
                char c2 = $receiver$iv.charAt((int)index$iv);
                ArrayList<Pair<Character, Character>> arrayList = result$iv;
                Pair<Character, Character> pair = TuplesKt.to(Character.valueOf((char)a), Character.valueOf((char)b));
                arrayList.add(pair);
                ++index$iv;
            }
            list = result$iv;
        }
        return list;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.2")
    @NotNull
    public static final <R> List<R> zipWithNext(@NotNull CharSequence $receiver, @NotNull Function2<? super Character, ? super Character, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int size = $receiver.length() - 1;
        if (size < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList<R> result = new ArrayList<R>(size);
        int n = 0;
        int n2 = size;
        while (n < n2) {
            void index;
            result.add(transform.invoke(Character.valueOf($receiver.charAt((int)index)), Character.valueOf($receiver.charAt((int)(index + true)))));
            ++index;
        }
        return result;
    }

    @NotNull
    public static final Iterable<Character> asIterable(@NotNull CharSequence $receiver) {
        CharSequence charSequence;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver instanceof String && (charSequence = $receiver).length() == 0) {
            return CollectionsKt.emptyList();
        }
        return new Iterable<Character>($receiver){
            final /* synthetic */ CharSequence receiver$0$inlined;
            {
                this.receiver$0$inlined = charSequence;
            }

            @NotNull
            public Iterator<Character> iterator() {
                return StringsKt.iterator(this.receiver$0$inlined);
            }
        };
    }

    @NotNull
    public static final Sequence<Character> asSequence(@NotNull CharSequence $receiver) {
        CharSequence charSequence;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver instanceof String && (charSequence = $receiver).length() == 0) {
            return SequencesKt.emptySequence();
        }
        return new Sequence<Character>($receiver){
            final /* synthetic */ CharSequence receiver$0$inlined;
            {
                this.receiver$0$inlined = charSequence;
            }

            @NotNull
            public Iterator<Character> iterator() {
                return StringsKt.iterator(this.receiver$0$inlined);
            }
        };
    }
}

