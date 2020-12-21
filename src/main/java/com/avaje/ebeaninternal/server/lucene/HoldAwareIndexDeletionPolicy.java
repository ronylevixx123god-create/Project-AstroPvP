/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.index.IndexCommit
 *  org.apache.lucene.index.IndexDeletionPolicy
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexDeletionPolicy;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class HoldAwareIndexDeletionPolicy
implements IndexDeletionPolicy {
    private static final Logger logger = Logger.getLogger(HoldAwareIndexDeletionPolicy.class.getName());
    private final Map<Long, CommitRefCount> commitRefCounts = new HashMap<Long, CommitRefCount>();
    private IndexCommit lastCommit;
    private final String indexDir;

    public HoldAwareIndexDeletionPolicy(String indexDir) {
        this.indexDir = indexDir;
    }

    public void onInit(List<? extends IndexCommit> commits) {
        this.onCommit(commits);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onCommit(List<? extends IndexCommit> commits) {
        Map<Long, CommitRefCount> map = this.commitRefCounts;
        synchronized (map) {
            int size = commits.size();
            this.lastCommit = commits.get(size - 1);
            for (int i = 0; i < size - 1; ++i) {
                IndexCommit indexCommit = commits.get(i);
                if (this.commitRefCounts.containsKey(indexCommit.getVersion())) continue;
                this.potentialIndexCommitDelete(indexCommit);
            }
        }
    }

    private void potentialIndexCommitDelete(IndexCommit indexCommit) {
        indexCommit.delete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getLastVersion() {
        Map<Long, CommitRefCount> map = this.commitRefCounts;
        synchronized (map) {
            if (this.lastCommit == null) {
                return 0L;
            }
            return this.lastCommit.getVersion();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LIndexCommitInfo obtainLastIndexCommitIfNewer(long remoteIndexVersion) {
        Map<Long, CommitRefCount> map = this.commitRefCounts;
        synchronized (map) {
            if (remoteIndexVersion != 0L && remoteIndexVersion == this.lastCommit.getVersion()) {
                return null;
            }
            this.incRefIndexCommit(this.lastCommit);
            return new LIndexCommitInfo(this.indexDir, this.lastCommit);
        }
    }

    private void incRefIndexCommit(IndexCommit indexCommit) {
        Long commitVersion = indexCommit.getVersion();
        CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
        if (refCount == null) {
            refCount = new CommitRefCount();
            this.commitRefCounts.put(commitVersion, refCount);
        }
        refCount.inc();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseIndexCommit(long indexCommitVersion) {
        Map<Long, CommitRefCount> map = this.commitRefCounts;
        synchronized (map) {
            Long commitVersion = indexCommitVersion;
            CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
            if (refCount == null) {
                logger.log(Level.WARNING, "No Reference counter for indexCommitVersion: " + commitVersion);
            } else if (refCount.dec() <= 0) {
                this.commitRefCounts.remove(commitVersion);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void touch(long indexCommitVersion) {
        Map<Long, CommitRefCount> map = this.commitRefCounts;
        synchronized (map) {
            Long commitVersion = indexCommitVersion;
            CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
            if (refCount == null) {
                logger.warning("No Reference counter for indexCommitVersion: " + commitVersion);
            } else {
                refCount.touch();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getLastTouched(long indexCommitVersion) {
        Map<Long, CommitRefCount> map = this.commitRefCounts;
        synchronized (map) {
            Long commitVersion = indexCommitVersion;
            CommitRefCount refCount = this.commitRefCounts.get(commitVersion);
            if (refCount == null) {
                return 0L;
            }
            return refCount.getLastTouched();
        }
    }

    private static class CommitRefCount {
        private int refCount;
        private long lastTouched = System.currentTimeMillis();

        private CommitRefCount() {
        }

        public void inc() {
            ++this.refCount;
        }

        public int dec() {
            return --this.refCount;
        }

        public void touch() {
            this.lastTouched = System.currentTimeMillis();
        }

        public long getLastTouched() {
            return this.lastTouched;
        }
    }
}

