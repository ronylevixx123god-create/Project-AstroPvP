/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PstmtCache
extends LinkedHashMap<String, ExtendedPreparedStatement> {
    private static final Logger logger = Logger.getLogger(PstmtCache.class.getName());
    static final long serialVersionUID = -3096406924865550697L;
    final String cacheName;
    final int maxSize;
    int removeCounter;
    int hitCounter;
    int missCounter;
    int putCounter;

    public PstmtCache(String cacheName, int maxCacheSize) {
        super(maxCacheSize * 3, 0.75f, true);
        this.cacheName = cacheName;
        this.maxSize = maxCacheSize;
    }

    public String getDescription() {
        return this.cacheName + " size:" + this.size() + " max:" + this.maxSize + " totalHits:" + this.hitCounter + " hitRatio:" + this.getHitRatio() + " removes:" + this.removeCounter;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public int getHitRatio() {
        if (this.hitCounter == 0) {
            return 0;
        }
        return this.hitCounter * 100 / (this.hitCounter + this.missCounter);
    }

    public int getHitCounter() {
        return this.hitCounter;
    }

    public int getMissCounter() {
        return this.missCounter;
    }

    public int getPutCounter() {
        return this.putCounter;
    }

    @Override
    public ExtendedPreparedStatement get(Object key) {
        ExtendedPreparedStatement o = (ExtendedPreparedStatement)super.get(key);
        if (o == null) {
            ++this.missCounter;
        } else {
            ++this.hitCounter;
        }
        return o;
    }

    @Override
    public ExtendedPreparedStatement remove(Object key) {
        ExtendedPreparedStatement o = (ExtendedPreparedStatement)super.remove(key);
        if (o == null) {
            ++this.missCounter;
        } else {
            ++this.hitCounter;
        }
        return o;
    }

    @Override
    public ExtendedPreparedStatement put(String key, ExtendedPreparedStatement value) {
        ++this.putCounter;
        return super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, ExtendedPreparedStatement> eldest) {
        if (this.size() < this.maxSize) {
            return false;
        }
        ++this.removeCounter;
        try {
            ExtendedPreparedStatement pstmt = eldest.getValue();
            pstmt.closeDestroy();
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing ExtendedPreparedStatement", e);
        }
        return true;
    }
}

