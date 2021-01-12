/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.FilesKt__FilePathComponentsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a?\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0011(+0\u0001\u00a8\u00061"}, d2={"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, xs="kotlin/io/FilesKt")
class FilesKt__FileReadWriteKt
extends FilesKt__FilePathComponentsKt {
    @InlineOnly
    private static final InputStreamReader reader(@NotNull File $receiver, Charset charset) {
        Object object = $receiver;
        object = new FileInputStream((File)object);
        return new InputStreamReader((InputStream)object, charset);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ InputStreamReader reader$default(File $receiver, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        object = $receiver;
        object = new FileInputStream((File)object);
        return new InputStreamReader((InputStream)object, charset);
    }

    @InlineOnly
    private static final BufferedReader bufferedReader(@NotNull File $receiver, Charset charset, int bufferSize) {
        Object object;
        Object object2 = object = $receiver;
        object = new InputStreamReader((InputStream)(object2 = (InputStream)new FileInputStream((File)object2)), charset);
        return object instanceof BufferedReader ? (BufferedReader)object : new BufferedReader((Reader)object, bufferSize);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ BufferedReader bufferedReader$default(File $receiver, Charset charset, int bufferSize, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((n & 2) != 0) {
            bufferSize = 8192;
        }
        Object object2 = object = $receiver;
        object = new InputStreamReader((InputStream)(object2 = (InputStream)new FileInputStream((File)object2)), charset);
        return object instanceof BufferedReader ? (BufferedReader)object : new BufferedReader((Reader)object, bufferSize);
    }

    @InlineOnly
    private static final OutputStreamWriter writer(@NotNull File $receiver, Charset charset) {
        Object object = $receiver;
        object = new FileOutputStream((File)object);
        return new OutputStreamWriter((OutputStream)object, charset);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ OutputStreamWriter writer$default(File $receiver, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        object = $receiver;
        object = new FileOutputStream((File)object);
        return new OutputStreamWriter((OutputStream)object, charset);
    }

    @InlineOnly
    private static final BufferedWriter bufferedWriter(@NotNull File $receiver, Charset charset, int bufferSize) {
        Object object;
        Object object2 = object = $receiver;
        object = new OutputStreamWriter((OutputStream)(object2 = (OutputStream)new FileOutputStream((File)object2)), charset);
        return object instanceof BufferedWriter ? (BufferedWriter)object : new BufferedWriter((Writer)object, bufferSize);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ BufferedWriter bufferedWriter$default(File $receiver, Charset charset, int bufferSize, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((n & 2) != 0) {
            bufferSize = 8192;
        }
        Object object2 = object = $receiver;
        object = new OutputStreamWriter((OutputStream)(object2 = (OutputStream)new FileOutputStream((File)object2)), charset);
        return object instanceof BufferedWriter ? (BufferedWriter)object : new BufferedWriter((Writer)object, bufferSize);
    }

    @InlineOnly
    private static final PrintWriter printWriter(@NotNull File $receiver, Charset charset) {
        Object object;
        File file = $receiver;
        int n = 8192;
        Object object2 = object = file;
        object = new OutputStreamWriter((OutputStream)(object2 = (OutputStream)new FileOutputStream((File)object2)), charset);
        BufferedWriter bufferedWriter = object instanceof BufferedWriter ? (BufferedWriter)object : new BufferedWriter((Writer)object, n);
        Writer writer = bufferedWriter;
        return new PrintWriter(writer);
    }

    @InlineOnly
    static /* bridge */ /* synthetic */ PrintWriter printWriter$default(File $receiver, Charset charset, int n, Object object) {
        Object object2;
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        object = $receiver;
        int n2 = 8192;
        Object object3 = object2 = object;
        object2 = new OutputStreamWriter((OutputStream)(object3 = (OutputStream)new FileOutputStream((File)object3)), charset);
        BufferedWriter bufferedWriter = object2 instanceof BufferedWriter ? (BufferedWriter)object2 : new BufferedWriter((Writer)object2, n2);
        Writer writer = bufferedWriter;
        return new PrintWriter(writer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public static final byte[] readBytes(@NotNull File $receiver) {
        byte[] arrby;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Object object = $receiver;
        object = new FileInputStream((File)object);
        Throwable throwable = null;
        try {
            byte[] arrby2;
            int read;
            long l;
            FileInputStream input = (FileInputStream)object;
            int offset = 0;
            long length = l = $receiver.length();
            if (length > (long)Integer.MAX_VALUE) {
                throw (Throwable)new OutOfMemoryError("File " + $receiver + " is too big (" + length + " bytes) to fit in memory.");
            }
            int remaining = (int)l;
            byte[] result = new byte[remaining];
            while (remaining > 0 && (read = input.read(result, offset, remaining)) >= 0) {
                remaining -= read;
                offset += read;
            }
            if (remaining == 0) {
                arrby2 = result;
            } else {
                byte[] arrby3 = result;
                byte[] arrby4 = Arrays.copyOf(arrby3, offset);
                arrby2 = arrby4;
                Intrinsics.checkExpressionValueIsNotNull(arrby4, "java.util.Arrays.copyOf(this, newSize)");
            }
            arrby = arrby2;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)object, throwable);
        }
        return arrby;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void writeBytes(@NotNull File $receiver, @NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(array, "array");
        Closeable closeable = new FileOutputStream($receiver);
        Throwable throwable = null;
        try {
            FileOutputStream it = (FileOutputStream)closeable;
            it.write(array);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void appendBytes(@NotNull File $receiver, @NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(array, "array");
        Closeable closeable = new FileOutputStream($receiver, true);
        Throwable throwable = null;
        try {
            FileOutputStream it = (FileOutputStream)closeable;
            it.write(array);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    @NotNull
    public static final String readText(@NotNull File $receiver, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] arrby = FilesKt.readBytes($receiver);
        return new String(arrby, charset);
    }

    @NotNull
    public static /* bridge */ /* synthetic */ String readText$default(File file, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readText(file, charset);
    }

    public static final void writeText(@NotNull File $receiver, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        String string = text;
        File file = $receiver;
        byte[] arrby = string.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        byte[] arrby2 = arrby;
        FilesKt.writeBytes(file, arrby2);
    }

    public static /* bridge */ /* synthetic */ void writeText$default(File file, String string, Charset charset, int n, Object object) {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, string, charset);
    }

    public static final void appendText(@NotNull File $receiver, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        String string = text;
        File file = $receiver;
        byte[] arrby = string.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        byte[] arrby2 = arrby;
        FilesKt.appendBytes(file, arrby2);
    }

    public static /* bridge */ /* synthetic */ void appendText$default(File file, String string, Charset charset, int n, Object object) {
        if ((n & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, string, charset);
    }

    public static final void forEachBlock(@NotNull File $receiver, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        FilesKt.forEachBlock($receiver, 4096, action);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void forEachBlock(@NotNull File $receiver, int blockSize, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        byte[] arr = new byte[RangesKt.coerceAtLeast(blockSize, 512)];
        Object object = $receiver;
        object = new FileInputStream((File)object);
        Throwable throwable = null;
        try {
            int size;
            FileInputStream input = (FileInputStream)object;
            while ((size = input.read(arr)) > 0) {
                action.invoke((byte[])arr, (Integer)size);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)object, throwable);
        }
    }

    public static final void forEachLine(@NotNull File $receiver, @NotNull Charset charset, @NotNull Function1<? super String, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(action, "action");
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader((InputStream)new FileInputStream($receiver), charset)), action);
    }

    public static /* bridge */ /* synthetic */ void forEachLine$default(File file, Charset charset, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    @InlineOnly
    private static final FileInputStream inputStream(@NotNull File $receiver) {
        return new FileInputStream($receiver);
    }

    @InlineOnly
    private static final FileOutputStream outputStream(@NotNull File $receiver) {
        return new FileOutputStream($receiver);
    }

    @NotNull
    public static final List<String> readLines(@NotNull File $receiver, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        ArrayList result = new ArrayList();
        FilesKt.forEachLine($receiver, charset, (Function1<? super String, Unit>)new Function1<String, Unit>(result){
            final /* synthetic */ ArrayList $result;

            public final void invoke(@NotNull String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                this.$result.add(it);
            }
            {
                this.$result = arrayList;
                super(1);
            }
        });
        return result;
    }

    @NotNull
    public static /* bridge */ /* synthetic */ List readLines$default(File file, Charset charset, int n, Object object) {
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readLines(file, charset);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final <T> T useLines(@NotNull File $receiver, @NotNull Charset charset, @NotNull Function1<? super Sequence<String>, ? extends T> block) {
        T t;
        Object object;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Object object2 = $receiver;
        int n = 8192;
        Object object3 = object = object2;
        object3 = new FileInputStream((File)object3);
        object = new InputStreamReader((InputStream)object3, charset);
        object2 = object instanceof BufferedReader ? (BufferedReader)object : new BufferedReader((Reader)object, n);
        Throwable throwable = null;
        try {
            BufferedReader it = (BufferedReader)object2;
            t = block.invoke(TextStreamsKt.lineSequence(it));
        }
        catch (Throwable throwable2) {
            try {
                throwable = throwable2;
                throw throwable2;
            }
            catch (Throwable throwable3) {
                InlineMarker.finallyStart(1);
                if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                    CloseableKt.closeFinally((Closeable)object2, throwable);
                } else if (throwable == null) {
                    object2.close();
                } else {
                    try {
                        object2.close();
                    }
                    catch (Throwable throwable4) {
                        // empty catch block
                    }
                }
                InlineMarker.finallyEnd(1);
                throw throwable3;
            }
        }
        InlineMarker.finallyStart(1);
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
            CloseableKt.closeFinally((Closeable)object2, throwable);
        } else {
            object2.close();
        }
        InlineMarker.finallyEnd(1);
        return t;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static /* bridge */ /* synthetic */ Object useLines$default(File $receiver, Charset charset, Function1 block, int n, Object object) {
        Object r;
        Object object2;
        if ((n & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(block, "block");
        object = $receiver;
        int n2 = 8192;
        Object object3 = object2 = object;
        object3 = new FileInputStream((File)object3);
        object2 = new InputStreamReader((InputStream)object3, charset);
        object = object2 instanceof BufferedReader ? (BufferedReader)object2 : new BufferedReader((Reader)object2, n2);
        Throwable throwable = null;
        try {
            BufferedReader it = (BufferedReader)object;
            r = block.invoke(TextStreamsKt.lineSequence(it));
        }
        catch (Throwable throwable2) {
            try {
                throwable = throwable2;
                throw throwable2;
            }
            catch (Throwable throwable3) {
                InlineMarker.finallyStart(1);
                if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                    CloseableKt.closeFinally((Closeable)object, throwable);
                } else if (throwable == null) {
                    object.close();
                } else {
                    try {
                        object.close();
                    }
                    catch (Throwable throwable4) {
                        // empty catch block
                    }
                }
                InlineMarker.finallyEnd(1);
                throw throwable3;
            }
        }
        InlineMarker.finallyStart(1);
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
            CloseableKt.closeFinally((Closeable)object, throwable);
        } else {
            object.close();
        }
        InlineMarker.finallyEnd(1);
        return r;
    }
}

