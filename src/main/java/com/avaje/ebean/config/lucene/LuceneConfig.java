/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 */
package com.avaje.ebean.config.lucene;

import com.avaje.ebean.Query;
import com.avaje.ebean.config.GlobalProperties;
import org.apache.lucene.analysis.Analyzer;

public class LuceneConfig {
    protected String baseDirectory;
    protected Analyzer defaultAnalyzer;
    protected Query.UseIndex defaultUseIndex;

    public Analyzer getDefaultAnalyzer() {
        return this.defaultAnalyzer;
    }

    public void setDefaultAnalyzer(Analyzer defaultAnalyzer) {
        this.defaultAnalyzer = defaultAnalyzer;
    }

    public String getBaseDirectory() {
        return this.baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public Query.UseIndex getDefaultUseIndex() {
        return this.defaultUseIndex;
    }

    public void setDefaultUseIndex(Query.UseIndex defaultUseIndex) {
        this.defaultUseIndex = defaultUseIndex;
    }

    public void loadSettings(String serverName) {
        GlobalProperties.PropertySource p = GlobalProperties.getPropertySource(serverName);
        this.baseDirectory = p.get("lucene.baseDirectory", "lucene");
        this.defaultUseIndex = p.getEnum(Query.UseIndex.class, "lucene.useIndex", Query.UseIndex.NO);
    }
}

