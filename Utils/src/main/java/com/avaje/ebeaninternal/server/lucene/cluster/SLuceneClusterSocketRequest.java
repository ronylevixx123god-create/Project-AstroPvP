/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneSocketMessageTypes;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SLuceneClusterSocketRequest
implements Runnable,
SLuceneSocketMessageTypes {
    private static final Logger logger = Logger.getLogger(SLuceneClusterSocketRequest.class.getName());
    private final LuceneIndexManager manager;
    private final OutputStream os;
    private final DataInput dataInput;
    private final DataOutput dataOutput;
    private final String serverName;
    private final short msgType;
    private final String idxName;
    private final long remoteIndexVersion;

    public SLuceneClusterSocketRequest(ClusterManager clusterManager, Socket socket) throws IOException {
        this.os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        this.dataInput = new DataInputStream(is);
        this.dataOutput = new DataOutputStream(this.os);
        this.serverName = this.dataInput.readUTF();
        this.msgType = this.dataInput.readShort();
        this.idxName = this.dataInput.readUTF();
        this.remoteIndexVersion = this.dataInput.readLong();
        SpiEbeanServer server = (SpiEbeanServer)clusterManager.getServer(this.serverName);
        this.manager = server.getLuceneIndexManager();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void run() {
        try {
            switch (this.msgType) {
                case 2: {
                    this.obtainCommit();
                    return;
                }
                case 3: {
                    this.releaseCommit();
                    return;
                }
                case 4: {
                    this.getFile();
                    return;
                }
                default: {
                    throw new IOException("Invalid msgType " + this.msgType);
                }
            }
        }
        catch (IOException e) {
            String msg = "Error processing msg " + this.msgType + " " + this.idxName;
            logger.log(Level.SEVERE, msg, e);
            return;
        }
        finally {
            this.flush();
        }
    }

    private void flush() {
        String msg;
        try {
            this.os.flush();
        }
        catch (IOException e) {
            msg = "Error flushing Socket OuputStream";
            logger.log(Level.SEVERE, msg, e);
        }
        try {
            this.os.close();
        }
        catch (IOException e) {
            msg = "Error closing Socket OuputStream";
            logger.log(Level.SEVERE, msg, e);
        }
    }

    private void releaseCommit() throws IOException {
        LIndex index = this.manager.getIndex(this.idxName);
        index.releaseIndexCommit(this.remoteIndexVersion);
        this.dataOutput.writeBoolean(true);
    }

    private void obtainCommit() throws IOException {
        LIndex index = this.manager.getIndex(this.idxName);
        LIndexCommitInfo commitInfo = index.obtainLastIndexCommitIfNewer(this.remoteIndexVersion);
        if (commitInfo == null) {
            this.dataOutput.writeBoolean(false);
        } else {
            this.dataOutput.writeBoolean(true);
            commitInfo.write(this.dataOutput);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getFile() throws IOException {
        String fileName;
        LIndex index = this.manager.getIndex(this.idxName);
        LIndexFileInfo fileInfo = index.getFile(this.remoteIndexVersion, fileName = this.dataInput.readUTF());
        File f = fileInfo.getFile();
        if (!f.exists()) {
            this.dataOutput.writeBoolean(false);
        } else {
            this.dataOutput.writeBoolean(true);
            FileInputStream fis = new FileInputStream(f);
            try {
                byte[] buf = new byte[2048];
                BufferedInputStream bis = new BufferedInputStream(fis);
                int len = 0;
                while ((len = bis.read(buf)) > -1) {
                    this.dataOutput.write(buf, 0, len);
                }
            }
            finally {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    String msg = "Error closing InputStream on " + f.getAbsolutePath();
                    logger.log(Level.SEVERE, msg, e);
                }
            }
        }
    }
}

