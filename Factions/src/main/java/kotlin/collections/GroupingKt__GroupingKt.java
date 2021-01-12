/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt__GroupingJVMKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009b\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u001a\u00b4\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u00a2\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007\u00a2\u0006\u0002\u0010\u0016\u001a\u00bc\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u001a|\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b\u00a2\u0006\u0002\u0010\u001c\u001a\u00d5\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u00a2\u0006\u0002\u0010\u001e\u001a\u0090\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b\u00a2\u0006\u0002\u0010\u001f\u001a\u0088\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u001a\u00a1\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u00a2\u0006\u0002\u0010#\u00a8\u0006$"}, d2={"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"}, xs="kotlin/collections/GroupingKt")
class GroupingKt__GroupingKt
extends GroupingKt__GroupingJVMKt {
    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R> Map<K, R> aggregate(@NotNull Grouping<T, ? extends K> $receiver, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        void destination$iv;
        void $receiver$iv;
        Iterator iterator2;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, ? extends K> grouping = $receiver;
        Map map = new LinkedHashMap();
        Iterator iterator3 = iterator2 = $receiver$iv.sourceIterator();
        while (iterator3.hasNext()) {
            Object e$iv = iterator3.next();
            Object key$iv = $receiver$iv.keyOf(e$iv);
            Object accumulator$iv = destination$iv.get(key$iv);
            void var9_8 = destination$iv;
            R r = operation.invoke(key$iv, accumulator$iv, e$iv, accumulator$iv == null && !destination$iv.containsKey(key$iv));
            var9_8.put(key$iv, r);
        }
        return destination$iv;
    }

    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R, M extends Map<? super K, R>> M aggregateTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
        Iterator<T> iterator2;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Iterator<T> iterator3 = iterator2 = $receiver.sourceIterator();
        while (iterator3.hasNext()) {
            T e = iterator3.next();
            K key = $receiver.keyOf(e);
            R accumulator = destination.get(key);
            M m = destination;
            R r = operation.invoke(key, accumulator, e, accumulator == null && !destination.containsKey(key));
            m.put(key, r);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $receiver, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        void destination$iv$iv;
        void $receiver$iv$iv;
        Iterator iterator2;
        Grouping<T, ? extends K> $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, ? extends K> grouping = $receiver$iv = $receiver;
        Map map = new LinkedHashMap();
        Iterator iterator3 = iterator2 = $receiver$iv$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void acc;
            void e;
            void first;
            Object e$iv$iv = iterator3.next();
            Object key$iv$iv = $receiver$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            void var11_10 = destination$iv$iv;
            boolean bl = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object key = key$iv$iv;
            R r = operation.invoke(key, first != false ? initialValueSelector.invoke(key, e) : acc, e);
            var11_10.put(key$iv$iv, r);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
        Iterator<T> iterator2;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, K> $receiver$iv = $receiver;
        Iterator<T> iterator3 = iterator2 = $receiver$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void acc;
            void e;
            void first;
            T e$iv = iterator3.next();
            K key$iv = $receiver$iv.keyOf(e$iv);
            R accumulator$iv = destination.get(key$iv);
            M m = destination;
            boolean bl = accumulator$iv == null && !destination.containsKey(key$iv);
            T t = e$iv;
            R r = accumulator$iv;
            K key = key$iv;
            R r2 = operation.invoke(key, first != false ? initialValueSelector.invoke(key, e) : acc, e);
            m.put(key$iv, r2);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $receiver, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
        void destination$iv$iv;
        void $receiver$iv$iv;
        Iterator iterator2;
        Grouping<T, ? extends K> $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, ? extends K> grouping = $receiver$iv = $receiver;
        Map map = new LinkedHashMap();
        Iterator iterator3 = iterator2 = $receiver$iv$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void e;
            void acc;
            void first;
            Object e$iv$iv = iterator3.next();
            Object key$iv$iv = $receiver$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            void var11_10 = destination$iv$iv;
            boolean bl = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object $noName_0 = key$iv$iv;
            R r = operation.invoke(first != false ? initialValue : acc, e);
            var11_10.put(key$iv$iv, r);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
        Iterator<T> iterator2;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, K> $receiver$iv = $receiver;
        Iterator<T> iterator3 = iterator2 = $receiver$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void e;
            void acc;
            void first;
            T e$iv = iterator3.next();
            K key$iv = $receiver$iv.keyOf(e$iv);
            R accumulator$iv = destination.get(key$iv);
            M m = destination;
            boolean bl = accumulator$iv == null && !destination.containsKey(key$iv);
            T t = e$iv;
            R r = accumulator$iv;
            K $noName_0 = key$iv;
            R r2 = operation.invoke(first != false ? initialValue : acc, e);
            m.put(key$iv, r2);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <S, T extends S, K> Map<K, S> reduce(@NotNull Grouping<T, ? extends K> $receiver, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        void destination$iv$iv;
        void $receiver$iv$iv;
        Iterator iterator2;
        Grouping<T, ? extends K> $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, ? extends K> grouping = $receiver$iv = $receiver;
        Map map = new LinkedHashMap();
        Iterator iterator3 = iterator2 = $receiver$iv$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void acc;
            void e;
            void first;
            Object e$iv$iv = iterator3.next();
            Object key$iv$iv = $receiver$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
            void var10_9 = destination$iv$iv;
            boolean bl = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object key = key$iv$iv;
            void var16_14 = first != false ? e : operation.invoke(key, acc, e);
            var10_9.put(key$iv$iv, var16_14);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <S, T extends S, K, M extends Map<? super K, S>> M reduceTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
        Iterator<T> iterator2;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Grouping<T, K> $receiver$iv = $receiver;
        Iterator<T> iterator3 = iterator2 = $receiver$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void acc;
            void e;
            void first;
            T e$iv = iterator3.next();
            K key$iv = $receiver$iv.keyOf(e$iv);
            S accumulator$iv = destination.get(key$iv);
            M m = destination;
            boolean bl = accumulator$iv == null && !destination.containsKey(key$iv);
            T t = e$iv;
            S s = accumulator$iv;
            K key = key$iv;
            void var13_12 = first != false ? e : operation.invoke(key, acc, e);
            m.put(key$iv, var13_12);
        }
        return destination;
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K, M extends Map<? super K, Integer>> M eachCountTo(@NotNull Grouping<T, ? extends K> $receiver, @NotNull M destination) {
        Iterator iterator2;
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Grouping<T, ? extends K> grouping = $receiver;
        Integer initialValue$iv = 0;
        void $receiver$iv$iv = $receiver$iv;
        Iterator iterator3 = iterator2 = $receiver$iv$iv.sourceIterator();
        while (iterator3.hasNext()) {
            void acc$iv;
            void first$iv;
            void e$iv;
            Object e$iv$iv = iterator3.next();
            Object key$iv$iv = $receiver$iv$iv.keyOf(e$iv$iv);
            Integer accumulator$iv$iv = destination.get(key$iv$iv);
            M m = destination;
            boolean bl = accumulator$iv$iv == null && !destination.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Integer n = accumulator$iv$iv;
            Object $noName_0$iv = key$iv$iv;
            void var14_14 = e$iv;
            int acc = ((Number)(first$iv != false ? initialValue$iv : acc$iv)).intValue();
            Integer n2 = acc + 1;
            m.put(key$iv$iv, (Integer)n2);
        }
        return destination;
    }
}

