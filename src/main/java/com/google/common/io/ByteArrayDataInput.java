/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import java.io.DataInput;

public interface ByteArrayDataInput
extends DataInput {
    public void readFully(byte[] var1);

    public void readFully(byte[] var1, int var2, int var3);

    public int skipBytes(int var1);

    public boolean readBoolean();

    public byte readByte();

    public int readUnsignedByte();

    public short readShort();

    public int readUnsignedShort();

    public char readChar();

    public int readInt();

    public long readLong();

    public float readFloat();

    public double readDouble();

    public String readLine();

    public String readUTF();
}

