/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.analysis.standard.StandardAnalyzer
 *  org.apache.lucene.util.Version
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.Query;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.lucene.LuceneConfig;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.lucene.DefaultLuceneIndexManager;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class LuceneManagerFactory {
    public static LuceneIndexManager createLuceneManager(ClusterManager clusterManager, BackgroundExecutor executor, ServerConfig serverConfig) {
        StandardAnalyzer defaultAnalyzer = null;
        String baseDir = null;
        Query.UseIndex defaultUseIndex = null;
        LuceneConfig luceneConfig = serverConfig.getLuceneConfig();
        if (luceneConfig != null) {
            defaultAnalyzer = null;
            baseDir = luceneConfig.getBaseDirectory();
            defaultUseIndex = luceneConfig.getDefaultUseIndex();
        }
        if (defaultAnalyzer == null) {
            defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
        }
        if (defaultUseIndex == null) {
            defaultUseIndex = Query.UseIndex.NO;
        }
        if (baseDir == null) {
            baseDir = serverConfig.getPropertySource().get("lucene.baseDirectory", "lucene");
        }
        baseDir = GlobalProperties.evaluateExpressions(baseDir);
        return new DefaultLuceneIndexManager(clusterManager, executor, (Analyzer)defaultAnalyzer, baseDir, serverConfig.getName(), defaultUseIndex);
    }
}

