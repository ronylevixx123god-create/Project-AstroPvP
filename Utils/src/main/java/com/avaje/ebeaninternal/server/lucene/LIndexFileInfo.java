/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;

public class LIndexFileInfo {
    private final transient File file;
    private final String name;
    private final long length;
    private final long lastModified;

    public LIndexFileInfo(File file) {
        this.file = file;
        this.name = file.getName();
        this.length = file.length();
        this.lastModified = file.lastModified();
    }

    public LIndexFileInfo(String name, long length, long lastModified) {
        this.file = null;
        this.name = name;
        this.length = length;
        this.lastModified = lastModified;
    }

    public String toString() {
        return this.name + " length[" + this.length + "] lastModified[" + this.lastModified + "]";
    }

    public static LIndexFileInfo read(DataInput dataInput) throws IOException {
        String name = dataInput.readUTF();
        long len = dataInput.readLong();
        long lastMod = dataInput.readLong();
        return new LIndexFileInfo(name, len, lastMod);
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.name);
        dataOutput.writeLong(this.length);
        dataOutput.writeLong(this.lastModified);
    }

    public boolean exists() {
        return this.file.exists();
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public long getLength() {
        return this.length;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public boolean isMatch(LIndexFileInfo otherFile) {
        return otherFile.length == this.length && otherFile.lastModified == this.lastModified;
    }
}

