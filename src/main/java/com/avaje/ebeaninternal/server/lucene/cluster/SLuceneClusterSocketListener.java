/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClusterSocketRequest;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SLuceneClusterSocketListener
implements Runnable,
LuceneClusterListener {
    private static final Logger logger = Logger.getLogger(SLuceneClusterSocketListener.class.getName());
    private final int port;
    private final int listenTimeout = 60000;
    private final ServerSocket serverListenSocket;
    private final Thread listenerThread;
    private final ThreadPool threadPool;
    private final ClusterManager clusterManager;
    private boolean doingShutdown;
    private boolean isActive;

    public SLuceneClusterSocketListener(ClusterManager clusterManager, int port) {
        this.clusterManager = clusterManager;
        this.threadPool = ThreadPoolManager.getThreadPool("EbeanClusterLuceneListener");
        this.port = port;
        try {
            this.serverListenSocket = new ServerSocket(port);
            this.serverListenSocket.setSoTimeout(60000);
            this.listenerThread = new Thread((Runnable)this, "EbeanClusterLuceneListener");
        }
        catch (IOException e) {
            String msg = "Error starting cluster socket listener on port " + port;
            throw new RuntimeException(msg, e);
        }
    }

    public int getPort() {
        return this.port;
    }

    public void startup() {
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
        String msg = "Cluster Lucene Listening address[todo] port[" + this.port + "]";
        logger.info(msg);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        this.doingShutdown = true;
        try {
            if (this.isActive) {
                Thread thread = this.listenerThread;
                synchronized (thread) {
                    try {
                        this.listenerThread.wait(1000L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
            this.listenerThread.interrupt();
            this.serverListenSocket.close();
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (!this.doingShutdown) {
            try {
                Thread thread = this.listenerThread;
                synchronized (thread) {
                    Socket clientSocket = this.serverListenSocket.accept();
                    this.isActive = true;
                    SLuceneClusterSocketRequest request = new SLuceneClusterSocketRequest(this.clusterManager, clientSocket);
                    this.threadPool.assign(request, true);
                    this.isActive = false;
                }
            }
            catch (SocketException e) {
                if (this.doingShutdown) {
                    String msg = "doingShutdown and accept threw:" + e.getMessage();
                    logger.info(msg);
                    continue;
                }
                logger.log(Level.SEVERE, null, e);
            }
            catch (InterruptedIOException e) {
                logger.fine("Possibly expected due to accept timeout?" + e.getMessage());
            }
            catch (IOException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }
    }
}

