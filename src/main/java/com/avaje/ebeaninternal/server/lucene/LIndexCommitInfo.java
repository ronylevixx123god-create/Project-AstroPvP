/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.index.IndexCommit
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
import com.avaje.ebeaninternal.server.lucene.PersistenceLuceneException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.lucene.index.IndexCommit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexCommitInfo {
    private final String indexDir;
    private final LIndexVersion version;
    private final List<LIndexFileInfo> fileInfo;

    public LIndexCommitInfo(String indexDir, IndexCommit indexCommit) {
        this.indexDir = indexDir;
        this.version = new LIndexVersion(indexCommit.getGeneration(), indexCommit.getVersion());
        this.fileInfo = this.createFileInfo(indexCommit);
    }

    public LIndexCommitInfo(String indexDir, LIndexVersion version, List<LIndexFileInfo> fileInfo) {
        this.indexDir = indexDir;
        this.version = version;
        this.fileInfo = fileInfo;
    }

    public String toString() {
        return this.indexDir + " " + this.version + " " + this.fileInfo;
    }

    public LIndexVersion getVersion() {
        return this.version;
    }

    public List<LIndexFileInfo> getFileInfo() {
        return this.fileInfo;
    }

    public static LIndexCommitInfo read(DataInput dataInput) throws IOException {
        String idxDir = dataInput.readUTF();
        long gen = dataInput.readLong();
        long ver = dataInput.readLong();
        int fileCount = dataInput.readInt();
        ArrayList<LIndexFileInfo> fileInfo = new ArrayList<LIndexFileInfo>(fileCount);
        for (int i = 0; i < fileCount; ++i) {
            fileInfo.add(LIndexFileInfo.read(dataInput));
        }
        return new LIndexCommitInfo(idxDir, new LIndexVersion(gen, ver), fileInfo);
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.indexDir);
        dataOutput.writeLong(this.version.getGeneration());
        dataOutput.writeLong(this.version.getVersion());
        int fileCount = this.fileInfo.size();
        dataOutput.writeInt(fileCount);
        for (int i = 0; i < this.fileInfo.size(); ++i) {
            this.fileInfo.get(i).write(dataOutput);
        }
    }

    private List<LIndexFileInfo> createFileInfo(IndexCommit indexCommit) {
        try {
            Collection fileNames = indexCommit.getFileNames();
            ArrayList<LIndexFileInfo> files = new ArrayList<LIndexFileInfo>(fileNames.size());
            for (String fileName : fileNames) {
                files.add(this.getFileInfo(fileName));
            }
            return files;
        }
        catch (IOException e) {
            throw new PersistenceLuceneException(e);
        }
    }

    private LIndexFileInfo getFileInfo(String fileName) {
        File f = new File(this.indexDir, fileName);
        return new LIndexFileInfo(f);
    }
}

