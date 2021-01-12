/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.ArrayAsCollection;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000p\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a@\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a@\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u0019\u001a\u0012\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007\u001a\u0015\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u001c\u001a%\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u0007\u00a2\u0006\u0002\u0010 \u001a3\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020\u001e2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007\u00a2\u0006\u0002\u0010\u001c\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u001c\u001a%\u0010\"\u001a\u00020#2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b&\u001a%\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\u0000\u00a2\u0006\u0002\u0010(\u001aS\u0010)\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010\u001f\u001a\u0002H\u00072\u001a\u0010*\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00070+j\n\u0012\u0006\b\u0000\u0012\u0002H\u0007`,2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006\u00a2\u0006\u0002\u0010-\u001a>\u0010)\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u0010)\u001a\u00020\u0006\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070/*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006\u00a2\u0006\u0002\u00100\u001ad\u00101\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007\"\u000e\b\u0001\u00102*\b\u0012\u0004\u0012\u0002H20/*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u00103\u001a\u0004\u0018\u0001H22\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0016\b\u0004\u00104\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H20\u000eH\u0086\b\u00a2\u0006\u0002\u00105\u001a,\u00106\u001a\u000207\"\t\b\u0000\u0010\u0007\u00a2\u0006\u0002\b8*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a\u0019\u00109\u001a\u000207\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a\u001e\u0010:\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\u0000\u001a!\u0010;\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010;\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006<"}, d2={"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "isNotEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"}, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__CollectionsKt
extends CollectionsKt__CollectionsJVMKt {
    @NotNull
    public static final <T> Collection<T> asCollection(@NotNull T[] $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new ArrayAsCollection<T>($receiver, false);
    }

    @NotNull
    public static final <T> List<T> emptyList() {
        return EmptyList.INSTANCE;
    }

    @NotNull
    public static final <T> List<T> listOf(T ... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length > 0 ? ArraysKt.asList(elements) : CollectionsKt.emptyList();
    }

    @InlineOnly
    private static final <T> List<T> listOf() {
        return CollectionsKt.emptyList();
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> List<T> mutableListOf() {
        return new ArrayList();
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> ArrayList<T> arrayListOf() {
        return new ArrayList();
    }

    @NotNull
    public static final <T> List<T> mutableListOf(T ... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? (List)new ArrayList() : (List)new ArrayList((Collection)new ArrayAsCollection<T>(elements, true));
    }

    @NotNull
    public static final <T> ArrayList<T> arrayListOf(T ... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList((Collection)new ArrayAsCollection<T>(elements, true));
    }

    @NotNull
    public static final <T> List<T> listOfNotNull(@Nullable T element) {
        return element != null ? CollectionsKt.listOf(element) : CollectionsKt.emptyList();
    }

    @NotNull
    public static final <T> List<T> listOfNotNull(T ... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return ArraysKt.filterNotNull(elements);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> List<T> List(int size, Function1<? super Integer, ? extends T> init) {
        ArrayList<T> arrayList = new ArrayList<T>(size);
        int n = 0;
        int n2 = size;
        while (n < n2) {
            int n3 = n++;
            arrayList.add(init.invoke(n3));
        }
        return arrayList;
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    private static final <T> List<T> MutableList(int size, Function1<? super Integer, ? extends T> init) {
        ArrayList<T> list = new ArrayList<T>(size);
        int n = 0;
        int n2 = size;
        while (n < n2) {
            int index = n++;
            list.add(init.invoke(index));
        }
        return list;
    }

    @NotNull
    public static final IntRange getIndices(@NotNull Collection<?> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int n = 0;
        return new IntRange(n, $receiver.size() - 1);
    }

    public static final <T> int getLastIndex(@NotNull List<? extends T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() - 1;
    }

    @InlineOnly
    private static final <T> boolean isNotEmpty(@NotNull Collection<? extends T> $receiver) {
        return !$receiver.isEmpty();
    }

    @InlineOnly
    private static final <T> Collection<T> orEmpty(@Nullable Collection<? extends T> $receiver) {
        Collection collection = $receiver;
        if (collection == null) {
            collection = CollectionsKt.emptyList();
        }
        return collection;
    }

    @InlineOnly
    private static final <T> List<T> orEmpty(@Nullable List<? extends T> $receiver) {
        List<Object> list = $receiver;
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    @InlineOnly
    private static final <T> boolean containsAll(@NotNull Collection<? extends T> $receiver, Collection<? extends T> elements) {
        return $receiver.containsAll(elements);
    }

    @NotNull
    public static final <T> List<T> optimizeReadOnlyList(@NotNull List<? extends T> $receiver) {
        List<T> list;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        switch ($receiver.size()) {
            case 0: {
                list = CollectionsKt.emptyList();
                break;
            }
            case 1: {
                list = CollectionsKt.listOf($receiver.get(0));
                break;
            }
            default: {
                list = $receiver;
            }
        }
        return list;
    }

    public static final <T extends Comparable<? super T>> int binarySearch(@NotNull List<? extends T> $receiver, @Nullable T element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt($receiver.size(), fromIndex, toIndex);
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = low + high >>> 1;
            Comparable midVal = (Comparable)$receiver.get(mid);
            int cmp = ComparisonsKt.compareValues(midVal, element);
            if (cmp < 0) {
                low = mid + 1;
                continue;
            }
            if (cmp > 0) {
                high = mid - 1;
                continue;
            }
            return mid;
        }
        return -(low + 1);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(List list, Comparable comparable, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = list.size();
        }
        return CollectionsKt.binarySearch(list, comparable, n, n2);
    }

    public static final <T> int binarySearch(@NotNull List<? extends T> $receiver, T element, @NotNull Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt($receiver.size(), fromIndex, toIndex);
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = low + high >>> 1;
            T midVal = $receiver.get(mid);
            int cmp = comparator.compare(midVal, element);
            if (cmp < 0) {
                low = mid + 1;
                continue;
            }
            if (cmp > 0) {
                high = mid - 1;
                continue;
            }
            return mid;
        }
        return -(low + 1);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(List list, Object object, Comparator comparator, int n, int n2, int n3, Object object2) {
        if ((n3 & 4) != 0) {
            n = 0;
        }
        if ((n3 & 8) != 0) {
            n2 = list.size();
        }
        return CollectionsKt.binarySearch(list, object, comparator, n, n2);
    }

    public static final <T, K extends Comparable<? super K>> int binarySearchBy(@NotNull List<? extends T> $receiver, @Nullable K key, int fromIndex, int toIndex, @NotNull Function1<? super T, ? extends K> selector) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        return CollectionsKt.binarySearch($receiver, fromIndex, toIndex, new Function1<T, Integer>(selector, key){
            final /* synthetic */ Function1 $selector;
            final /* synthetic */ Comparable $key;

            public final int invoke(T it) {
                return ComparisonsKt.compareValues((Comparable)this.$selector.invoke(it), this.$key);
            }
            {
                this.$selector = function1;
                this.$key = comparable;
                super(1);
            }
        });
    }

    public static /* bridge */ /* synthetic */ int binarySearchBy$default(List $receiver, Comparable key, int fromIndex, int toIndex, Function1 selector, int n, Object object) {
        if ((n & 2) != 0) {
            fromIndex = 0;
        }
        if ((n & 4) != 0) {
            toIndex = $receiver.size();
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        return CollectionsKt.binarySearch($receiver, fromIndex, toIndex, new /* invalid duplicate definition of identical inner class */);
    }

    public static final <T> int binarySearch(@NotNull List<? extends T> $receiver, int fromIndex, int toIndex, @NotNull Function1<? super T, Integer> comparison) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(comparison, "comparison");
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt($receiver.size(), fromIndex, toIndex);
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = low + high >>> 1;
            T midVal = $receiver.get(mid);
            int cmp = ((Number)comparison.invoke(midVal)).intValue();
            if (cmp < 0) {
                low = mid + 1;
                continue;
            }
            if (cmp > 0) {
                high = mid - 1;
                continue;
            }
            return mid;
        }
        return -(low + 1);
    }

    public static /* bridge */ /* synthetic */ int binarySearch$default(List list, int n, int n2, Function1 function1, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = list.size();
        }
        return CollectionsKt.binarySearch(list, n, n2, function1);
    }

    private static final void rangeCheck$CollectionsKt__CollectionsKt(int size, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw (Throwable)new IllegalArgumentException("fromIndex (" + fromIndex + ") is greater than toIndex (" + toIndex + ").");
        }
        if (fromIndex < 0) {
            throw (Throwable)new IndexOutOfBoundsException("fromIndex (" + fromIndex + ") is less than zero.");
        }
        if (toIndex > size) {
            throw (Throwable)new IndexOutOfBoundsException("toIndex (" + toIndex + ") is greater than size (" + size + ").");
        }
    }
}

