/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteProcessor;
import com.google.common.io.Closeables;
import com.google.common.io.InputSupplier;
import com.google.common.io.LimitInputStream;
import com.google.common.io.MultiInputStream;
import com.google.common.io.OutputSupplier;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.zip.Checksum;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
public final class ByteStreams {
    private static final int BUF_SIZE = 4096;

    private ByteStreams() {
    }

    public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(byte[] b) {
        return ByteStreams.newInputStreamSupplier(b, 0, b.length);
    }

    public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(final byte[] b, final int off, final int len) {
        return new InputSupplier<ByteArrayInputStream>(){

            @Override
            public ByteArrayInputStream getInput() {
                return new ByteArrayInputStream(b, off, len);
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void write(byte[] from, OutputSupplier<? extends OutputStream> to) throws IOException {
        Preconditions.checkNotNull(from);
        boolean threw = true;
        OutputStream out = to.getOutput();
        try {
            out.write(from);
            threw = false;
        }
        finally {
            Closeables.close(out, threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long copy(InputSupplier<? extends InputStream> from, OutputSupplier<? extends OutputStream> to) throws IOException {
        boolean threw = true;
        InputStream in = from.getInput();
        try {
            long l;
            OutputStream out = to.getOutput();
            try {
                long count = ByteStreams.copy(in, out);
                threw = false;
                l = count;
            }
            catch (Throwable throwable) {
                Closeables.close(out, threw);
                throw throwable;
            }
            Closeables.close(out, threw);
            return l;
        }
        finally {
            Closeables.close(in, threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long copy(InputSupplier<? extends InputStream> from, OutputStream to) throws IOException {
        boolean threw = true;
        InputStream in = from.getInput();
        try {
            long count = ByteStreams.copy(in, to);
            threw = false;
            long l = count;
            return l;
        }
        finally {
            Closeables.close(in, threw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long copy(InputStream from, OutputSupplier<? extends OutputStream> to) throws IOException {
        boolean threw = true;
        OutputStream out = to.getOutput();
        try {
            long count = ByteStreams.copy(from, out);
            threw = false;
            long l = count;
            return l;
        }
        finally {
            Closeables.close(out, threw);
        }
    }

    public static long copy(InputStream from, OutputStream to) throws IOException {
        int r;
        byte[] buf = new byte[4096];
        long total = 0L;
        while ((r = from.read(buf)) != -1) {
            to.write(buf, 0, r);
            total += (long)r;
        }
        return total;
    }

    public static long copy(ReadableByteChannel from, WritableByteChannel to) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(4096);
        long total = 0L;
        while (from.read(buf) != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                total += (long)to.write(buf);
            }
            buf.clear();
        }
        return total;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteStreams.copy(in, (OutputStream)out);
        return out.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] toByteArray(InputSupplier<? extends InputStream> supplier) throws IOException {
        boolean threw = true;
        InputStream in = supplier.getInput();
        try {
            byte[] result = ByteStreams.toByteArray(in);
            threw = false;
            byte[] arrby = result;
            return arrby;
        }
        finally {
            Closeables.close(in, threw);
        }
    }

    public static ByteArrayDataInput newDataInput(byte[] bytes) {
        return new ByteArrayDataInputStream(bytes);
    }

    public static ByteArrayDataInput newDataInput(byte[] bytes, int start) {
        Preconditions.checkPositionIndex(start, bytes.length);
        return new ByteArrayDataInputStream(bytes, start);
    }

    public static ByteArrayDataOutput newDataOutput() {
        return new ByteArrayDataOutputStream();
    }

    public static ByteArrayDataOutput newDataOutput(int size) {
        Preconditions.checkArgument(size >= 0, "Invalid size: %s", size);
        return new ByteArrayDataOutputStream(size);
    }

    public static long length(InputSupplier<? extends InputStream> supplier) throws IOException {
        long count = 0L;
        boolean threw = true;
        InputStream in = supplier.getInput();
        try {
            while (true) {
                long amt;
                if ((amt = in.skip(Integer.MAX_VALUE)) == 0L) {
                    if (in.read() == -1) {
                        threw = false;
                        long l = count;
                        return l;
                    }
                    ++count;
                    continue;
                }
                count += amt;
            }
        }
        finally {
            Closeables.close(in, threw);
        }
    }

    public static boolean equal(InputSupplier<? extends InputStream> supplier1, InputSupplier<? extends InputStream> supplier2) throws IOException {
        byte[] buf1 = new byte[4096];
        byte[] buf2 = new byte[4096];
        boolean threw = true;
        InputStream in1 = supplier1.getInput();
        try {
            InputStream in2 = supplier2.getInput();
            try {
                while (true) {
                    int read2;
                    int read1;
                    if ((read1 = ByteStreams.read(in1, buf1, 0, 4096)) != (read2 = ByteStreams.read(in2, buf2, 0, 4096)) || !Arrays.equals(buf1, buf2)) {
                        threw = false;
                        boolean bl = false;
                        return bl;
                    }
                    if (read1 == 4096) continue;
                    threw = false;
                    boolean bl = true;
                    return bl;
                }
            }
            finally {
                Closeables.close(in2, threw);
            }
        }
        finally {
            Closeables.close(in1, threw);
        }
    }

    public static void readFully(InputStream in, byte[] b) throws IOException {
        ByteStreams.readFully(in, b, 0, b.length);
    }

    public static void readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        if (ByteStreams.read(in, b, off, len) != len) {
            throw new EOFException();
        }
    }

    public static void skipFully(InputStream in, long n) throws IOException {
        while (n > 0L) {
            long amt = in.skip(n);
            if (amt == 0L) {
                if (in.read() == -1) {
                    throw new EOFException();
                }
                --n;
                continue;
            }
            n -= amt;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <T> T readBytes(InputSupplier<? extends InputStream> supplier, ByteProcessor<T> processor) throws IOException {
        byte[] buf = new byte[4096];
        boolean threw = true;
        InputStream in = supplier.getInput();
        try {
            int amt;
            do {
                if ((amt = in.read(buf)) != -1) continue;
                threw = false;
                break;
            } while (processor.processBytes(buf, 0, amt));
            T t = processor.getResult();
            return t;
        }
        finally {
            Closeables.close(in, threw);
        }
    }

    public static long getChecksum(InputSupplier<? extends InputStream> supplier, final Checksum checksum) throws IOException {
        return ByteStreams.readBytes(supplier, new ByteProcessor<Long>(){

            @Override
            public boolean processBytes(byte[] buf, int off, int len) {
                checksum.update(buf, off, len);
                return true;
            }

            @Override
            public Long getResult() {
                long result = checksum.getValue();
                checksum.reset();
                return result;
            }
        });
    }

    public static byte[] getDigest(InputSupplier<? extends InputStream> supplier, final MessageDigest md) throws IOException {
        return ByteStreams.readBytes(supplier, new ByteProcessor<byte[]>(){

            @Override
            public boolean processBytes(byte[] buf, int off, int len) {
                md.update(buf, off, len);
                return true;
            }

            @Override
            public byte[] getResult() {
                return md.digest();
            }
        });
    }

    public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
        int total;
        int result;
        if (len < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        for (total = 0; total < len && (result = in.read(b, off + total, len - total)) != -1; total += result) {
        }
        return total;
    }

    public static InputSupplier<InputStream> slice(final InputSupplier<? extends InputStream> supplier, final long offset, final long length) {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkArgument(offset >= 0L, "offset is negative");
        Preconditions.checkArgument(length >= 0L, "length is negative");
        return new InputSupplier<InputStream>(){

            @Override
            public InputStream getInput() throws IOException {
                InputStream in = (InputStream)supplier.getInput();
                if (offset > 0L) {
                    try {
                        ByteStreams.skipFully(in, offset);
                    }
                    catch (IOException e) {
                        Closeables.closeQuietly(in);
                        throw e;
                    }
                }
                return new LimitInputStream(in, length);
            }
        };
    }

    public static InputSupplier<InputStream> join(final Iterable<? extends InputSupplier<? extends InputStream>> suppliers) {
        return new InputSupplier<InputStream>(){

            @Override
            public InputStream getInput() throws IOException {
                return new MultiInputStream(suppliers.iterator());
            }
        };
    }

    public static InputSupplier<InputStream> join(InputSupplier<? extends InputStream> ... suppliers) {
        return ByteStreams.join(Arrays.asList(suppliers));
    }

    private static class ByteArrayDataOutputStream
    implements ByteArrayDataOutput {
        final DataOutput output;
        final ByteArrayOutputStream byteArrayOutputSteam;

        ByteArrayDataOutputStream() {
            this(new ByteArrayOutputStream());
        }

        ByteArrayDataOutputStream(int size) {
            this(new ByteArrayOutputStream(size));
        }

        ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputSteam) {
            this.byteArrayOutputSteam = byteArrayOutputSteam;
            this.output = new DataOutputStream(byteArrayOutputSteam);
        }

        public void write(int b) {
            try {
                this.output.write(b);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void write(byte[] b) {
            try {
                this.output.write(b);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void write(byte[] b, int off, int len) {
            try {
                this.output.write(b, off, len);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeBoolean(boolean v) {
            try {
                this.output.writeBoolean(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeByte(int v) {
            try {
                this.output.writeByte(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeBytes(String s) {
            try {
                this.output.writeBytes(s);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeChar(int v) {
            try {
                this.output.writeChar(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeChars(String s) {
            try {
                this.output.writeChars(s);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeDouble(double v) {
            try {
                this.output.writeDouble(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeFloat(float v) {
            try {
                this.output.writeFloat(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeInt(int v) {
            try {
                this.output.writeInt(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeLong(long v) {
            try {
                this.output.writeLong(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeShort(int v) {
            try {
                this.output.writeShort(v);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public void writeUTF(String s) {
            try {
                this.output.writeUTF(s);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }
    }

    private static class ByteArrayDataInputStream
    implements ByteArrayDataInput {
        final DataInput input;

        ByteArrayDataInputStream(byte[] bytes) {
            this.input = new DataInputStream(new ByteArrayInputStream(bytes));
        }

        ByteArrayDataInputStream(byte[] bytes, int start) {
            this.input = new DataInputStream(new ByteArrayInputStream(bytes, start, bytes.length - start));
        }

        public void readFully(byte[] b) {
            try {
                this.input.readFully(b);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public void readFully(byte[] b, int off, int len) {
            try {
                this.input.readFully(b, off, len);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public int skipBytes(int n) {
            try {
                return this.input.skipBytes(n);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public byte readByte() {
            try {
                return this.input.readByte();
            }
            catch (EOFException e) {
                throw new IllegalStateException(e);
            }
            catch (IOException impossible) {
                throw new AssertionError((Object)impossible);
            }
        }

        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public short readShort() {
            try {
                return this.input.readShort();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public char readChar() {
            try {
                return this.input.readChar();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public int readInt() {
            try {
                return this.input.readInt();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public long readLong() {
            try {
                return this.input.readLong();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public float readFloat() {
            try {
                return this.input.readFloat();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public double readDouble() {
            try {
                return this.input.readDouble();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public String readLine() {
            try {
                return this.input.readLine();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public String readUTF() {
            try {
                return this.input.readUTF();
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

