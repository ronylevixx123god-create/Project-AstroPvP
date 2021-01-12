/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.io;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.io.ConsoleKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=2, d1={"\u0000d\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\t\u0010\u0015\u001a\u00020\nH\u0087\b\u001a\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\f\u0010\u001a\u001a\u00020\r*\u00020\u001bH\u0002\u001a\f\u0010\u001c\u001a\u00020\u000f*\u00020\u001bH\u0002\u001a\f\u0010\u001d\u001a\u00020\n*\u00020\u001eH\u0002\u001a$\u0010\u001f\u001a\u00020\r*\u00020\u00042\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\rH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006$"}, d2={"BUFFER_SIZE", "", "LINE_SEPARATOR_MAX_LENGTH", "decoder", "Ljava/nio/charset/CharsetDecoder;", "getDecoder", "()Ljava/nio/charset/CharsetDecoder;", "decoder$delegate", "Lkotlin/Lazy;", "print", "", "message", "", "", "", "", "", "", "", "", "", "println", "readLine", "", "inputStream", "Ljava/io/InputStream;", "containsLineSeparator", "Ljava/nio/CharBuffer;", "dequeue", "flipBack", "Ljava/nio/Buffer;", "tryDecode", "byteBuffer", "Ljava/nio/ByteBuffer;", "charBuffer", "isEndOfStream", "kotlin-stdlib"})
@JvmName(name="ConsoleKt")
public final class ConsoleKt {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private static final int BUFFER_SIZE = 32;
    private static final int LINE_SEPARATOR_MAX_LENGTH = 2;
    private static final Lazy decoder$delegate;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(ConsoleKt.class, "kotlin-stdlib"), "decoder", "getDecoder()Ljava/nio/charset/CharsetDecoder;"))};
        decoder$delegate = LazyKt.lazy(decoder.2.INSTANCE);
    }

    @InlineOnly
    private static final void print(Object message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(int message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(long message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(byte message) {
        System.out.print((Object)message);
    }

    @InlineOnly
    private static final void print(short message) {
        System.out.print((Object)message);
    }

    @InlineOnly
    private static final void print(char message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(boolean message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(float message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(double message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void print(char[] message) {
        System.out.print(message);
    }

    @InlineOnly
    private static final void println(Object message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(int message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(long message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(byte message) {
        System.out.println((Object)message);
    }

    @InlineOnly
    private static final void println(short message) {
        System.out.println((Object)message);
    }

    @InlineOnly
    private static final void println(char message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(boolean message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(float message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(double message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println(char[] message) {
        System.out.println(message);
    }

    @InlineOnly
    private static final void println() {
        System.out.println();
    }

    private static final CharsetDecoder getDecoder() {
        Lazy lazy = decoder$delegate;
        Object var1_1 = null;
        KProperty kProperty = $$delegatedProperties[0];
        return (CharsetDecoder)lazy.getValue();
    }

    @Nullable
    public static final String readLine() {
        InputStream inputStream = System.in;
        Intrinsics.checkExpressionValueIsNotNull(inputStream, "System.`in`");
        return ConsoleKt.readLine(inputStream, ConsoleKt.getDecoder());
    }

    @Nullable
    public static final String readLine(@NotNull InputStream inputStream, @NotNull CharsetDecoder decoder2) {
        boolean bl;
        Intrinsics.checkParameterIsNotNull(inputStream, "inputStream");
        Intrinsics.checkParameterIsNotNull(decoder2, "decoder");
        boolean bl2 = bl = decoder2.maxCharsPerByte() <= 1.0f;
        if (!bl) {
            String string = "Encodings with multiple chars per byte are not supported";
            throw (Throwable)new IllegalArgumentException(string.toString());
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        CharBuffer charBuffer = CharBuffer.allocate(2);
        StringBuilder stringBuilder = new StringBuilder();
        int read = inputStream.read();
        if (read == -1) {
            return null;
        }
        do {
            byteBuffer.put((byte)read);
            ByteBuffer byteBuffer2 = byteBuffer;
            Intrinsics.checkExpressionValueIsNotNull(byteBuffer2, "byteBuffer");
            CharBuffer charBuffer2 = charBuffer;
            Intrinsics.checkExpressionValueIsNotNull(charBuffer2, "charBuffer");
            if (!ConsoleKt.tryDecode(decoder2, byteBuffer2, charBuffer2, false)) continue;
            if (ConsoleKt.containsLineSeparator(charBuffer)) break;
            if (charBuffer.hasRemaining()) continue;
            stringBuilder.append(ConsoleKt.dequeue(charBuffer));
        } while ((read = inputStream.read()) != -1);
        Object $receiver = decoder2;
        ConsoleKt.tryDecode((CharsetDecoder)$receiver, byteBuffer, charBuffer, true);
        ((CharsetDecoder)$receiver).reset();
        $receiver = charBuffer;
        int length = ((Buffer)$receiver).position();
        char first = ((CharBuffer)$receiver).get(0);
        char second = ((CharBuffer)$receiver).get(1);
        switch (length) {
            case 2: {
                if (first != '\r' || second != '\n') {
                    stringBuilder.append(first);
                }
                if (second == '\n') break;
                stringBuilder.append(second);
                break;
            }
            case 1: {
                if (first == '\n') break;
                stringBuilder.append(first);
            }
        }
        return stringBuilder.toString();
    }

    private static final boolean tryDecode(@NotNull CharsetDecoder $receiver, ByteBuffer byteBuffer, CharBuffer charBuffer, boolean isEndOfStream) {
        boolean bl;
        CoderResult coderResult;
        int positionBefore = charBuffer.position();
        byteBuffer.flip();
        CoderResult $receiver2 = coderResult = $receiver.decode(byteBuffer, charBuffer, isEndOfStream);
        if ($receiver2.isError()) {
            $receiver2.throwException();
        }
        boolean isDecoded = bl = charBuffer.position() > positionBefore;
        if (isDecoded) {
            byteBuffer.clear();
        } else {
            ConsoleKt.flipBack(byteBuffer);
        }
        return bl;
    }

    private static final boolean containsLineSeparator(@NotNull CharBuffer $receiver) {
        return $receiver.get(1) == '\n' || $receiver.get(0) == '\n';
    }

    private static final void flipBack(@NotNull Buffer $receiver) {
        $receiver.position($receiver.limit());
        $receiver.limit($receiver.capacity());
    }

    private static final char dequeue(@NotNull CharBuffer $receiver) {
        char c;
        $receiver.flip();
        char it = c = $receiver.get();
        $receiver.compact();
        return c;
    }
}

