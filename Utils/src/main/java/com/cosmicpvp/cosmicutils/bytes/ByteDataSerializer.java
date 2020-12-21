/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.PacketDataSerializer
 *  net.minecraft.util.io.netty.buffer.ByteBuf
 */
package com.cosmicpvp.cosmicutils.bytes;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.util.io.netty.buffer.ByteBuf;

public class ByteDataSerializer
extends PacketDataSerializer {
    public ByteDataSerializer(ByteBuf bytebuf) {
        super(bytebuf);
    }

    public String readString(int maxLength) throws IOException {
        return this.c(maxLength);
    }

    public ByteDataSerializer writeString(String string) throws IOException {
        this.a(string);
        return this;
    }

    public UUID readUUID() {
        return new UUID(this.readLong(), this.readLong());
    }
}

