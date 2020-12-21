/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.io.AppendableWriter;
import com.google.common.io.Closeables;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import com.google.common.io.LineReader;
import com.google.common.io.MultiReader;
import com.google.common.io.OutputSupplier;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
public final class CharStreams {
    private static final int BUF_SIZE = 2048;

    private CharStreams() {
    }

    public static InputSupplier<StringReader> newReaderSupplier(final String value) {
        Preconditions.checkNotNull(value);
        return new InputSupplier<StringReader>(){

            @Override
            public StringReader getInput() {
                return new StringReader(value);
            }
        };
    }

    public static InputSupplier<InputStreamReader> newReaderSupplier(final InputSupplier<? extends InputStream> in, final Charset charset) {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(charset);
        return new InputSupplier<InputStreamReader>(){

            @Override
            public InputStreamReader getInput() throws IOException {
                return new InputStreamReader((InputStream)in.getInput(), charset);
            }
        };
    }

    public static OutputSupplier<OutputStreamWriter> newWriterSupplier(final OutputSupplier<? extends OutputStream> out, final Charset charset) {
        Preconditions.checkNotNull(out);
        Preconditions.checkNotNull(charset);
        return new OutputSupplier<OutputStreamWriter>(){

            @Override
            public OutputStreamWriter getOutput() throws IOException {
                return new OutputStreamWriter((OutputStream)out.getOutput(), charset);
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <W extends Appendable & Closeable> void write(CharSequence from, OutputSupplier<W> to) throws IOException {
        Preconditions.checkNotNull(from);
        boolean threw = true;
        Appendable out = (Appendable)to.getOutput();
        try {
            out.append(from);
            threw = false;
        }
        finally {
            Closeables.close((Closeable)((Object)out), threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <R extends Readable & Closeable, W extends Appendable & Closeable> long copy(InputSupplier<R> from, OutputSupplier<W> to) throws IOException {
        boolean threw = true;
        Readable in = (Readable)from.getInput();
        try {
            long l;
            Appendable out = (Appendable)to.getOutput();
            try {
                long count = CharStreams.copy(in, out);
                threw = false;
                l = count;
            }
            catch (Throwable throwable) {
                Closeables.close((Closeable)((Object)out), threw);
                throw throwable;
            }
            Closeables.close((Closeable)((Object)out), threw);
            return l;
        }
        finally {
            Closeables.close((Closeable)((Object)in), threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <R extends Readable & Closeable> long copy(InputSupplier<R> from, Appendable to) throws IOException {
        boolean threw = true;
        Readable in = (Readable)from.getInput();
        try {
            long count = CharStreams.copy(in, to);
            threw = false;
            long l = count;
            return l;
        }
        finally {
            Closeables.close((Closeable)((Object)in), threw);
        }
    }

    public static long copy(Readable from, Appendable to) throws IOException {
        int r;
        CharBuffer buf = CharBuffer.allocate(2048);
        long total = 0L;
        while ((r = from.read(buf)) != -1) {
            buf.flip();
            to.append(buf, 0, r);
            total += (long)r;
        }
        return total;
    }

    public static String toString(Readable r) throws IOException {
        return CharStreams.toStringBuilder(r).toString();
    }

    public static <R extends Readable & Closeable> String toString(InputSupplier<R> supplier) throws IOException {
        return CharStreams.toStringBuilder(supplier).toString();
    }

    private static StringBuilder toStringBuilder(Readable r) throws IOException {
        StringBuilder sb = new StringBuilder();
        CharStreams.copy(r, (Appendable)sb);
        return sb;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static <R extends Readable & Closeable> StringBuilder toStringBuilder(InputSupplier<R> supplier) throws IOException {
        boolean threw = true;
        Readable r = (Readable)supplier.getInput();
        try {
            StringBuilder result = CharStreams.toStringBuilder(r);
            threw = false;
            StringBuilder stringBuilder = result;
            return stringBuilder;
        }
        finally {
            Closeables.close((Closeable)((Object)r), threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <R extends Readable & Closeable> String readFirstLine(InputSupplier<R> supplier) throws IOException {
        boolean threw = true;
        Readable r = (Readable)supplier.getInput();
        try {
            String line = new LineReader(r).readLine();
            threw = false;
            String string = line;
            return string;
        }
        finally {
            Closeables.close((Closeable)((Object)r), threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <R extends Readable & Closeable> List<String> readLines(InputSupplier<R> supplier) throws IOException {
        boolean threw = true;
        Readable r = (Readable)supplier.getInput();
        try {
            List<String> result = CharStreams.readLines(r);
            threw = false;
            List<String> list = result;
            return list;
        }
        finally {
            Closeables.close((Closeable)((Object)r), threw);
        }
    }

    public static List<String> readLines(Readable r) throws IOException {
        String line;
        ArrayList<String> result = new ArrayList<String>();
        LineReader lineReader = new LineReader(r);
        while ((line = lineReader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <R extends Readable & Closeable, T> T readLines(InputSupplier<R> supplier, LineProcessor<T> callback) throws IOException {
        boolean threw = true;
        Readable r = (Readable)supplier.getInput();
        try {
            String line;
            LineReader lineReader = new LineReader(r);
            while ((line = lineReader.readLine()) != null && callback.processLine(line)) {
            }
            threw = false;
        }
        finally {
            Closeables.close((Closeable)((Object)r), threw);
        }
        return callback.getResult();
    }

    public static InputSupplier<Reader> join(final Iterable<? extends InputSupplier<? extends Reader>> suppliers) {
        return new InputSupplier<Reader>(){

            @Override
            public Reader getInput() throws IOException {
                return new MultiReader(suppliers.iterator());
            }
        };
    }

    public static InputSupplier<Reader> join(InputSupplier<? extends Reader> ... suppliers) {
        return CharStreams.join(Arrays.asList(suppliers));
    }

    public static void skipFully(Reader reader, long n) throws IOException {
        while (n > 0L) {
            long amt = reader.skip(n);
            if (amt == 0L) {
                if (reader.read() == -1) {
                    throw new EOFException();
                }
                --n;
                continue;
            }
            n -= amt;
        }
    }

    public static Writer asWriter(Appendable target) {
        if (target instanceof Writer) {
            return (Writer)target;
        }
        return new AppendableWriter(target);
    }
}

