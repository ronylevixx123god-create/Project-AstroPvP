/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {
    private static final Logger logger = Logger.getLogger(SocketClient.class.getName());
    private final InetSocketAddress address;
    private boolean keepAlive = false;
    private final String hostPort;
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private DataInput dataInput;
    private DataOutput dataOutput;

    public SocketClient(InetSocketAddress address) {
        this.address = address;
        this.hostPort = address.getHostName() + ":" + address.getPort();
    }

    public String getHostPort() {
        return this.hostPort;
    }

    public int getPort() {
        return this.address.getPort();
    }

    public OutputStream getOutputStream() {
        return this.os;
    }

    public InputStream getInputStream() {
        return this.is;
    }

    public DataInput getDataInput() {
        return this.dataInput;
    }

    public DataOutput getDataOutput() {
        return this.dataOutput;
    }

    public void reconnect() throws IOException {
        this.disconnect();
        this.connect();
    }

    public void connect() throws IOException {
        if (this.socket != null) {
            throw new IllegalStateException("Already got a socket connection?");
        }
        Socket s = new Socket();
        s.setKeepAlive(this.keepAlive);
        s.connect(this.address);
        this.socket = s;
        this.os = this.socket.getOutputStream();
        this.is = this.socket.getInputStream();
    }

    public void initData() {
        this.dataOutput = new DataOutputStream(this.os);
        this.dataInput = new DataInputStream(this.is);
    }

    public void disconnect() {
        if (this.socket != null) {
            try {
                this.socket.close();
            }
            catch (IOException e) {
                String msg = "Error disconnecting from Cluster member " + this.hostPort;
                logger.log(Level.INFO, msg, e);
            }
            this.os = null;
            this.socket = null;
        }
    }

    public static InetSocketAddress parseHostPort(String hostAndPort) {
        try {
            hostAndPort = hostAndPort.trim();
            int colonPos = hostAndPort.indexOf(":");
            if (colonPos == -1) {
                String msg = "No colon \":\" in " + hostAndPort;
                throw new IllegalArgumentException(msg);
            }
            String host = hostAndPort.substring(0, colonPos);
            String sPort = hostAndPort.substring(colonPos + 1, hostAndPort.length());
            int port = Integer.parseInt(sPort);
            return new InetSocketAddress(host, port);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error parsing [" + hostAndPort + "] for the form [host:port]", ex);
        }
    }
}

