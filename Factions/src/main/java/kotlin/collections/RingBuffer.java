/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractIterator;
import kotlin.collections.AbstractList;
import kotlin.collections.RingBuffer;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\f\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0006H\u0096\u0002\u00a2\u0006\u0002\u0010\u0019J\u0006\u0010\u001a\u001a\u00020\u001bJ\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dH\u0096\u0002J\u000e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0006J\u0015\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014\u00a2\u0006\u0002\u0010!J'\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0014\u00a2\u0006\u0002\u0010#J9\u0010$\u001a\u00020\u0014\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\t2\u0006\u0010\u0015\u001a\u0002H\u00012\b\b\u0002\u0010%\u001a\u00020\u00062\b\b\u0002\u0010&\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u0010'J\u0015\u0010(\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0082\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR$\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u0096\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u0007R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "[Ljava/lang/Object;", "getCapacity", "()I", "<set-?>", "size", "getSize", "setSize", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "fill", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "forward", "kotlin-stdlib"})
final class RingBuffer<T>
extends AbstractList<T>
implements RandomAccess {
    private final Object[] buffer;
    private int startIndex;
    private int size;
    private final int capacity;

    @Override
    public int getSize() {
        return this.size;
    }

    private void setSize(int n) {
        this.size = n;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public T get(int index) {
        void this_$iv;
        void $receiver$iv;
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.size());
        int n = this.startIndex;
        RingBuffer ringBuffer = this;
        Object[] arrobject = this.buffer;
        void var6_5 = ($receiver$iv + index) % this_$iv.getCapacity();
        return (T)arrobject[var6_5];
    }

    public final boolean isFull() {
        return this.size() == this.capacity;
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new AbstractIterator<T>(this){
            private int count;
            private int index;
            final /* synthetic */ RingBuffer this$0;

            /*
             * WARNING - void declaration
             */
            protected void computeNext() {
                if (this.count == 0) {
                    this.done();
                } else {
                    void this_$iv;
                    void n$iv;
                    void $receiver$iv;
                    this.setNext(RingBuffer.access$getBuffer$p(this.this$0)[this.index]);
                    int n = this.index;
                    RingBuffer ringBuffer = this.this$0;
                    boolean bl = true;
                    iterator.1 var5_5 = this;
                    void var6_6 = ($receiver$iv + n$iv) % this_$iv.getCapacity();
                    var5_5.index = var6_6;
                    int n2 = this.count;
                    this.count = n2 + -1;
                }
            }
            {
                this.this$0 = $outer;
                this.count = $outer.size();
                this.index = RingBuffer.access$getStartIndex$p($outer);
            }
        };
    }

    @Override
    @NotNull
    public <T> T[] toArray(@NotNull T[] array) {
        int idx;
        T[] arrT;
        Intrinsics.checkParameterIsNotNull(array, "array");
        if (array.length < this.size()) {
            T[] arrT2 = array;
            int n = this.size();
            T[] arrT3 = Arrays.copyOf(arrT2, n);
            arrT = arrT3;
            Intrinsics.checkExpressionValueIsNotNull(arrT3, "java.util.Arrays.copyOf(this, newSize)");
        } else {
            arrT = array;
        }
        T[] result = arrT;
        int size = this.size();
        int widx = 0;
        for (idx = this.startIndex; widx < size && idx < this.capacity; ++widx, ++idx) {
            result[widx] = this.buffer[idx];
        }
        idx = 0;
        while (widx < size) {
            result[widx] = this.buffer[idx];
            ++widx;
            ++idx;
        }
        if (result.length > this.size()) {
            result[this.size()] = null;
        }
        if (result == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return result;
    }

    @Override
    @NotNull
    public Object[] toArray() {
        return this.toArray(new Object[this.size()]);
    }

    /*
     * WARNING - void declaration
     */
    public final void add(T element) {
        void this_$iv;
        void n$iv;
        void $receiver$iv;
        if (this.isFull()) {
            throw (Throwable)new IllegalStateException("ring buffer is full");
        }
        int n = this.startIndex;
        RingBuffer ringBuffer = this;
        int n2 = this.size();
        Object[] arrobject = this.buffer;
        void var7_7 = ($receiver$iv + n$iv) % this_$iv.getCapacity();
        arrobject[var7_7] = element;
        RingBuffer ringBuffer2 = this;
        int n3 = ringBuffer2.size();
        ringBuffer2.setSize(n3 + 1);
    }

    /*
     * WARNING - void declaration
     */
    public final void removeFirst(int n) {
        boolean bl;
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            String string = "n shouldn't be negative but it is " + n;
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        boolean bl3 = bl = n <= this.size();
        if (!bl) {
            String string = "n shouldn't be greater than the buffer size: n = " + n + ", size = " + this.size();
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        if (n > 0) {
            void $receiver$iv;
            int start;
            int n2 = start = this.startIndex;
            RingBuffer this_$iv = this;
            void end = ($receiver$iv + n) % this_$iv.getCapacity();
            if (start > end) {
                this.fill(this.buffer, null, start, this.capacity);
                this.fill(this.buffer, null, 0, (int)end);
            } else {
                this.fill(this.buffer, null, start, (int)end);
            }
            this.startIndex = end;
            RingBuffer ringBuffer = this;
            ringBuffer.setSize(ringBuffer.size() - n);
        }
    }

    private final int forward(int $receiver, int n) {
        return ($receiver + n) % this.getCapacity();
    }

    /*
     * WARNING - void declaration
     */
    private final <T> void fill(@NotNull T[] $receiver, T element, int fromIndex, int toIndex) {
        int n = fromIndex;
        int n2 = toIndex;
        while (n < n2) {
            void idx;
            $receiver[idx] = element;
            ++idx;
        }
    }

    static /* bridge */ /* synthetic */ void fill$default(RingBuffer ringBuffer, Object[] arrobject, Object object, int n, int n2, int n3, Object object2) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = arrobject.length;
        }
        ringBuffer.fill(arrobject, object, n, n2);
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public RingBuffer(int capacity) {
        boolean bl;
        this.capacity = capacity;
        boolean bl2 = bl = this.capacity >= 0;
        if (!bl) {
            String string = "ring buffer capacity should not be negative but it is " + this.capacity;
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        this.buffer = new Object[this.capacity];
    }

    @NotNull
    public static final /* synthetic */ Object[] access$getBuffer$p(RingBuffer $this) {
        return $this.buffer;
    }

    public static final /* synthetic */ int access$forward(RingBuffer $this, int $receiver, int n) {
        return $this.forward($receiver, n);
    }

    public static final /* synthetic */ int access$getSize$p(RingBuffer $this) {
        return $this.size();
    }

    public static final /* synthetic */ void access$setSize$p(RingBuffer $this, int n) {
        $this.setSize(n);
    }

    public static final /* synthetic */ int access$getStartIndex$p(RingBuffer $this) {
        return $this.startIndex;
    }

    public static final /* synthetic */ void access$setStartIndex$p(RingBuffer $this, int n) {
        $this.startIndex = n;
    }
}

