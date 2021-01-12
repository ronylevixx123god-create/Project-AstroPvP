/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.Grouping;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aW\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\b\u00a8\u0006\r"}, d2={"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, xs="kotlin/collections/GroupingKt")
class GroupingKt__GroupingJVMKt {
    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.1")
    @NotNull
    public static final <T, K> Map<K, Integer> eachCount(@NotNull Grouping<T, ? extends K> $receiver) {
        Serializable serializable;
        Map.Entry entry;
        Object e$iv$iv;
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Object object = $receiver;
        Map destination$iv = new LinkedHashMap();
        void $receiver$iv$iv = $receiver$iv;
        Iterator iterator22 = $receiver$iv$iv.sourceIterator();
        Object object2 = iterator22;
        while (object2.hasNext()) {
            void acc;
            void var16_16;
            Serializable serializable2;
            void e$iv;
            void first$iv;
            Object key$iv;
            e$iv$iv = object2.next();
            Object key$iv$iv = $receiver$iv$iv.keyOf(e$iv$iv);
            Object accumulator$iv$iv = destination$iv.get(key$iv$iv);
            Map map = destination$iv;
            boolean bl = accumulator$iv$iv == null && !destination$iv.containsKey(key$iv$iv);
            Object t = e$iv$iv;
            Object v = accumulator$iv$iv;
            Object object3 = key$iv = key$iv$iv;
            if (first$iv != false) {
                void var13_13 = e$iv;
                Object k = key$iv;
                entry = object3;
                serializable = new Ref.IntRef();
                object3 = entry;
                serializable2 = serializable;
            } else {
                void acc$iv;
                serializable2 = acc$iv;
            }
            void $i$a$1$foldTo = e$iv;
            Ref.IntRef $noName_1 = (Ref.IntRef)serializable2;
            Object $noName_0 = object3;
            void $receiver2 = var16_16 = acc;
            ++$receiver2.element;
            void var12_12 = var16_16;
            map.put(key$iv$iv, var12_12);
        }
        object = destination$iv;
        Iterable iterable = object.entrySet();
        for (Iterator iterator22 : iterable) {
            void it;
            Object object4 = object2 = (Map.Entry)((Object)iterator22);
            if (object4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
            }
            e$iv$iv = object2;
            entry = TypeIntrinsics.asMutableMapEntry(object4);
            serializable = ((Ref.IntRef)it.getValue()).element;
            entry.setValue((Integer)serializable);
        }
        return TypeIntrinsics.asMutableMap(object);
    }

    @PublishedApi
    @InlineOnly
    private static final <K, V, R> Map<K, R> mapValuesInPlace(@NotNull Map<K, V> $receiver, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> f) {
        Iterable $receiver$iv = $receiver.entrySet();
        for (Object element$iv : $receiver$iv) {
            Map.Entry it;
            Map.Entry entry = it = (Map.Entry)element$iv;
            if (entry == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
            }
            TypeIntrinsics.asMutableMapEntry(entry).setValue(f.invoke(it));
        }
        Map<K, V> map = $receiver;
        if (map == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
        }
        return TypeIntrinsics.asMutableMap(map);
    }
}

