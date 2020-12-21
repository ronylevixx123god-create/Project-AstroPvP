/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.meta;

import com.avaje.ebean.bean.ObjectGraphOrigin;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MetaAutoFetchTunedQueryInfo
implements Serializable {
    private static final long serialVersionUID = 3119991928889170215L;
    @Id
    private String id;
    private String beanType;
    private ObjectGraphOrigin origin;
    private String tunedDetail;
    private int profileCount;
    private int tunedCount;
    private long lastTuneTime;

    public MetaAutoFetchTunedQueryInfo() {
    }

    public MetaAutoFetchTunedQueryInfo(ObjectGraphOrigin origin, String tunedDetail, int profileCount, int tunedCount, long lastTuneTime) {
        this.origin = origin;
        this.beanType = origin == null ? null : origin.getBeanType();
        this.id = origin == null ? null : origin.getKey();
        this.tunedDetail = tunedDetail;
        this.profileCount = profileCount;
        this.tunedCount = tunedCount;
        this.lastTuneTime = lastTuneTime;
    }

    public String getId() {
        return this.id;
    }

    public String getBeanType() {
        return this.beanType;
    }

    public ObjectGraphOrigin getOrigin() {
        return this.origin;
    }

    public String getTunedDetail() {
        return this.tunedDetail;
    }

    public int getProfileCount() {
        return this.profileCount;
    }

    public int getTunedCount() {
        return this.tunedCount;
    }

    public long getLastTuneTime() {
        return this.lastTuneTime;
    }

    public String toString() {
        return "origin[" + this.origin + "] query[" + this.tunedDetail + "] profileCount[" + this.profileCount + "]";
    }
}

