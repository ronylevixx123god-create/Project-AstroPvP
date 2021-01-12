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
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.EmptyIterator;
import kotlin.collections.RingBuffer;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.SequenceBuilder;
import kotlin.coroutines.experimental.SequenceBuilderKt;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=2, d1={"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u00a8\u0006\u000f"}, d2={"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"})
public final class SlidingWindowKt {
    public static final void checkWindowSizeStep(int size, int step) {
        boolean bl;
        boolean bl2 = bl = size > 0 && step > 0;
        if (!bl) {
            String string = size != step ? "Both size " + size + " and step " + step + " must be greater than zero." : "size " + size + " must be greater than zero.";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
    }

    @NotNull
    public static final <T> Sequence<List<T>> windowedSequence(@NotNull Sequence<? extends T> $receiver, int size, int step, boolean partialWindows, boolean reuseBuffer) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        SlidingWindowKt.checkWindowSizeStep(size, step);
        return new Sequence<List<? extends T>>($receiver, size, step, partialWindows, reuseBuffer){
            final /* synthetic */ Sequence receiver$0$inlined;
            final /* synthetic */ int $size$inlined;
            final /* synthetic */ int $step$inlined;
            final /* synthetic */ boolean $partialWindows$inlined;
            final /* synthetic */ boolean $reuseBuffer$inlined;
            {
                this.receiver$0$inlined = sequence;
                this.$size$inlined = n;
                this.$step$inlined = n2;
                this.$partialWindows$inlined = bl;
                this.$reuseBuffer$inlined = bl2;
            }

            @NotNull
            public Iterator<List<? extends T>> iterator() {
                return SlidingWindowKt.windowedIterator(this.receiver$0$inlined.iterator(), this.$size$inlined, this.$step$inlined, this.$partialWindows$inlined, this.$reuseBuffer$inlined);
            }
        };
    }

    @NotNull
    public static final <T> Iterator<List<T>> windowedIterator(@NotNull Iterator<? extends T> iterator2, int size, int step, boolean partialWindows, boolean reuseBuffer) {
        Intrinsics.checkParameterIsNotNull(iterator2, "iterator");
        if (!iterator2.hasNext()) {
            return EmptyIterator.INSTANCE;
        }
        return SequenceBuilderKt.buildIterator(new Function2<SequenceBuilder<? super List<? extends T>>, Continuation<? super Unit>, Object>(step, size, iterator2, reuseBuffer, partialWindows, null){
            private SequenceBuilder p$;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int I$0;
            int I$1;
            final /* synthetic */ int $step;
            final /* synthetic */ int $size;
            final /* synthetic */ Iterator $iterator;
            final /* synthetic */ boolean $reuseBuffer;
            final /* synthetic */ boolean $partialWindows;

            /*
             * Unable to fully structure code
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Nullable
            public final Object doResume(@Nullable Object var1_1, @Nullable Throwable var2_2) {
                var10_3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0: {
                        v0 = var2_2;
                        if (v0 != null) {
                            throw v0;
                        }
                        var3_4 = this.p$;
                        gap = this.$step - this.$size;
                        if (gap < 0) break;
                        buffer = new ArrayList<Object>(this.$size);
                        skip = 0;
                        var8_16 = var9_15 = this.$iterator;
lbl13:
                        // 4 sources

                        while (var8_16.hasNext()) {
                            e = var8_16.next();
                            if (skip > 0) {
                                --skip;
                                continue;
                            }
                            buffer.add(e);
                            if (buffer.size() != this.$size) continue;
                            this.L$0 = var3_4;
                            this.I$0 = gap;
                            this.L$1 = buffer;
                            this.I$1 = skip;
                            this.L$2 = e;
                            this.L$3 = var8_16;
                            this.label = 1;
                            v1 = var3_4.yield(buffer, this);
                            if (v1 == var10_3) {
                                return var10_3;
                            }
                            ** GOTO lbl44
                        }
                        break;
                    }
                    case 1: {
                        var8_16 = (Iterator)this.L$3;
                        e = this.L$2;
                        skip = this.I$1;
                        buffer = (ArrayList<Object>)this.L$1;
                        gap = this.I$0;
                        var3_4 = (SequenceBuilder)this.L$0;
                        v2 = throwable;
                        if (v2 != null) {
                            throw v2;
                        }
                        v1 = data;
lbl44:
                        // 2 sources

                        if (this.$reuseBuffer) {
                            buffer.clear();
                        } else {
                            buffer = new ArrayList<E>(this.$size);
                        }
                        skip = gap;
                        ** GOTO lbl13
                    }
                }
                var7_18 = buffer;
                if (!(var7_18.isEmpty() == false) || !this.$partialWindows && buffer.size() != this.$size) return Unit.INSTANCE;
                this.I$0 = gap;
                this.L$0 = buffer;
                this.I$1 = skip;
                this.label = 2;
                v3 = var3_4.yield(buffer, this);
                if (v3 != var10_3) return Unit.INSTANCE;
                return var10_3;
                {
                    case 2: {
                        skip = this.I$1;
                        buffer = (ArrayList)this.L$0;
                        gap = this.I$0;
                        v4 = throwable;
                        if (v4 != null) {
                            throw v4;
                        }
                        v3 = data;
                        return Unit.INSTANCE;
                    }
                }
                buffer = new RingBuffer<Object>(this.$size);
                var7_19 = var8_17 = this.$iterator;
lbl70:
                // 3 sources

                while (var7_19.hasNext()) {
                    e = var7_19.next();
                    buffer.add(e);
                    if (!buffer.isFull()) continue;
                    this.L$0 = var3_4;
                    this.I$0 = gap;
                    this.L$1 = buffer;
                    this.L$2 = e;
                    this.L$3 = var7_19;
                    this.label = 3;
                    v5 = var3_4.yield(this.$reuseBuffer != false ? (List)buffer : (List)new ArrayList<E>((Collection)buffer), this);
                    if (v5 == var10_3) {
                        return var10_3;
                    }
                    ** GOTO lbl95
                }
                {
                    break;
                    case 3: {
                        var7_19 = (Iterator)this.L$3;
                        e = this.L$2;
                        buffer = (RingBuffer)this.L$1;
                        gap = this.I$0;
                        var3_4 = (SequenceBuilder)this.L$0;
                        v6 = throwable;
                        if (v6 != null) {
                            throw v6;
                        }
                        v5 = data;
lbl95:
                        // 2 sources

                        buffer.removeFirst(this.$step);
                        ** GOTO lbl70
                    }
                }
                if (!this.$partialWindows) return Unit.INSTANCE;
lbl98:
                // 2 sources

                while (buffer.size() > this.$step) {
                    this.L$0 = var3_4;
                    this.I$0 = gap;
                    this.L$1 = buffer;
                    this.label = 4;
                    v7 = var3_4.yield(this.$reuseBuffer != false ? (List)buffer : (List)new ArrayList<E>((Collection)buffer), this);
                    if (v7 == var10_3) {
                        return var10_3;
                    }
                    ** GOTO lbl116
                }
                {
                    break;
                    case 4: {
                        buffer = (RingBuffer<Object>)this.L$1;
                        gap = this.I$0;
                        var3_4 = (SequenceBuilder)this.L$0;
                        v8 = throwable;
                        if (v8 != null) {
                            throw v8;
                        }
                        v7 = data;
lbl116:
                        // 2 sources

                        buffer.removeFirst(this.$step);
                        ** GOTO lbl98
                    }
                }
                var6_14 = buffer;
                if (!(var6_14.isEmpty() == false)) return Unit.INSTANCE;
                this.I$0 = gap;
                this.L$0 = buffer;
                this.label = 5;
                v9 = var3_4.yield(buffer, this);
                if (v9 != var10_3) return Unit.INSTANCE;
                return var10_3;
                {
                    case 5: {
                        buffer = (RingBuffer)this.L$0;
                        gap = this.I$0;
                        v10 = throwable;
                        if (v10 != null) {
                            throw v10;
                        }
                        v9 = data;
                        return Unit.INSTANCE;
                    }
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            {
                this.$step = n;
                this.$size = n2;
                this.$iterator = iterator2;
                this.$reuseBuffer = bl;
                this.$partialWindows = bl2;
                super(2, continuation2);
            }

            @NotNull
            public final Continuation<Unit> create(@NotNull SequenceBuilder<? super List<? extends T>> $receiver, @NotNull Continuation<? super Unit> continuation2) {
                Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                Intrinsics.checkParameterIsNotNull(continuation2, "continuation");
                Function2<SequenceBuilder<? super List<? extends T>>, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                SequenceBuilder<List<List<List<? super List<? extends T>>>>> sequenceBuilder = function2.p$ = $receiver;
                return function2;
            }

            @Nullable
            public final Object invoke(@NotNull SequenceBuilder<? super List<? extends T>> $receiver, @NotNull Continuation<? super Unit> continuation2) {
                Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                Intrinsics.checkParameterIsNotNull(continuation2, "continuation");
                return (this.create($receiver, continuation2)).doResume(Unit.INSTANCE, null);
            }
        });
    }
}

