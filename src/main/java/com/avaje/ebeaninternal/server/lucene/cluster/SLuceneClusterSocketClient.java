/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClient;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneSocketMessageTypes;
import com.avaje.ebeaninternal.server.lucene.cluster.SocketClient;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SLuceneClusterSocketClient
implements SLuceneSocketMessageTypes {
    private static final Logger logger = Logger.getLogger(SLuceneClusterSocketClient.class.getName());
    private InetSocketAddress master;
    private LIndex index;
    private SLuceneClient client;
    private File tmpDir;
    private ArrayList<File> addFiles = new ArrayList();
    private ArrayList<File> replaceFiles = new ArrayList();

    public SLuceneClusterSocketClient(LIndex index) {
        this.index = index;
    }

    public boolean isSynchIndex(String masterHost) throws IOException {
        this.master = SocketClient.parseHostPort(masterHost);
        LIndexVersion localVersion = this.index.getLastestVersion();
        System.out.println("-- Got localVersion " + localVersion);
        SocketClient client = new SocketClient(this.master);
        String serverName = this.index.getBeanDescriptor().getServerName();
        this.client = new SLuceneClient(serverName, client, localVersion.getVersion(), this.index);
        try {
            LIndexCommitInfo commitInfo = this.getCommitInfo();
            if (commitInfo == null) {
                logger.info("Lucene index up to date [" + this.index.getName() + "]");
                return false;
            }
            this.getCommitFiles(localVersion, commitInfo);
            return true;
        }
        catch (IOException e) {
            String msg = "Error synch'ing index " + this.index.getName();
            logger.log(Level.SEVERE, msg, e);
            throw e;
        }
    }

    public void transferFiles() {
        File indexDir = this.index.getIndexDir();
        for (int i = 0; i < this.addFiles.size(); ++i) {
            File addFile = this.addFiles.get(i);
            File destFile = new File(indexDir, addFile.getName());
            addFile.renameTo(destFile);
        }
        this.tmpDir.delete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getCommitFiles(LIndexVersion localVersion, LIndexCommitInfo commitInfo) throws IOException {
        try {
            this.client.setRemoteVersion(commitInfo.getVersion().getVersion());
            this.copyFiles(commitInfo);
        }
        finally {
            try {
                this.client.sendRelease();
            }
            catch (IOException e) {
                String msg = "Error sending release for index " + this.client.getIndex();
                logger.log(Level.SEVERE, msg, e);
            }
        }
    }

    private void copyFiles(LIndexCommitInfo commitInfo) throws IOException {
        LIndex index = this.client.getIndex();
        File indexDir = index.getIndexDir();
        this.tmpDir = new File(indexDir, "tmp-" + System.currentTimeMillis());
        if (!this.tmpDir.exists() && !this.tmpDir.mkdirs()) {
            String msg = "Could not create directory tmpDir: " + this.tmpDir;
            throw new IOException(msg);
        }
        List<LIndexFileInfo> fileInfo = commitInfo.getFileInfo();
        logger.info("Lucene index synchonizing from[" + this.master + "] ver[" + commitInfo.getVersion() + "] files[" + fileInfo + "]");
        for (int i = 0; i < fileInfo.size(); ++i) {
            LIndexFileInfo fi = fileInfo.get(i);
            String fileName = fi.getName();
            LIndexFileInfo localFileInfo = index.getLocalFile(fileName);
            if (localFileInfo.exists()) {
                if (localFileInfo.isMatch(fi)) {
                    logger.info("... skip [" + fi.getName() + "]");
                    continue;
                }
                logger.warning("Lucene index file [" + fi.getName() + "] exists but not match size or lastModified?");
                this.downloadFile(false, fi);
                continue;
            }
            this.downloadFile(true, fi);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void downloadFile(boolean addFile, LIndexFileInfo fi) throws IOException {
        try {
            String fileName = fi.getName();
            InputStream fileInputStream = this.client.sendGetFile(fileName);
            BufferedInputStream bufIs = new BufferedInputStream(fileInputStream);
            File fileOut = new File(this.tmpDir, fileName);
            FileOutputStream os = new FileOutputStream(fileOut);
            byte[] buf = new byte[2048];
            int totalLen = 0;
            int len = 0;
            while ((len = bufIs.read(buf)) > -1) {
                os.write(buf, 0, len);
                totalLen += len;
            }
            os.flush();
            os.close();
            fileOut.setLastModified(fi.getLastModified());
            System.out.println("got file len:" + totalLen + " " + fileName);
            if (addFile) {
                this.addFiles.add(fileOut);
            } else {
                this.replaceFiles.add(fileOut);
            }
        }
        finally {
            this.client.disconnect();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private LIndexCommitInfo getCommitInfo() throws IOException {
        try {
            boolean gotInfo = this.client.sendObtainCommit();
            if (!gotInfo) {
                LIndexCommitInfo lIndexCommitInfo = null;
                return lIndexCommitInfo;
            }
            LIndexCommitInfo lIndexCommitInfo = LIndexCommitInfo.read(this.client.getSocketClient().getDataInput());
            return lIndexCommitInfo;
        }
        finally {
            this.client.disconnect();
        }
    }
}

