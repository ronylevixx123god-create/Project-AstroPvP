/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.cluster.SocketClient;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;

public class SLuceneClient {
    private final String serverName;
    private final LIndex index;
    private final long localVersion;
    private long remoteVersion;
    private final SocketClient client;

    public SLuceneClient(String serverName, SocketClient client, long localVersion, LIndex index) {
        this.serverName = serverName;
        this.client = client;
        this.localVersion = localVersion;
        this.index = index;
    }

    public void setRemoteVersion(long remoteVersion) {
        this.remoteVersion = remoteVersion;
    }

    public LIndex getIndex() {
        return this.index;
    }

    public void disconnect() {
        this.client.disconnect();
    }

    public SocketClient getSocketClient() {
        return this.client;
    }

    private void sendMessageHeader(short msgType, long version) throws IOException {
        this.client.connect();
        this.client.initData();
        DataOutput dataOutput = this.client.getDataOutput();
        dataOutput.writeUTF(this.serverName);
        dataOutput.writeShort(msgType);
        dataOutput.writeUTF(this.index.getName());
        dataOutput.writeLong(version);
    }

    private boolean sendMessageHeader2(short msgType, long version) throws IOException {
        this.sendMessageHeader(msgType, version);
        this.client.getOutputStream().flush();
        return this.client.getDataInput().readBoolean();
    }

    public boolean sendObtainCommit() throws IOException {
        return this.sendMessageHeader2((short)2, this.localVersion);
    }

    public void sendRelease() throws IOException {
        this.sendMessageHeader2((short)3, this.remoteVersion);
    }

    public InputStream sendGetFile(String fileName) throws IOException {
        this.sendMessageHeader((short)4, this.remoteVersion);
        this.client.getDataOutput().writeUTF(fileName);
        this.client.getOutputStream().flush();
        boolean exists = this.client.getDataInput().readBoolean();
        if (!exists) {
            return null;
        }
        return this.client.getInputStream();
    }
}

