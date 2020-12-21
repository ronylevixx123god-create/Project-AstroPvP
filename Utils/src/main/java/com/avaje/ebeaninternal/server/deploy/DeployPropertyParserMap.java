/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.DeployParser;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DeployPropertyParserMap
extends DeployParser {
    private final Map<String, String> map;

    public DeployPropertyParserMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public Set<String> getIncludes() {
        return null;
    }

    @Override
    public String convertWord() {
        String r = this.getDeployWord(this.word);
        return r == null ? this.word : r;
    }

    @Override
    public String getDeployWord(String expression) {
        String deployExpr = this.map.get(expression);
        if (deployExpr == null) {
            return null;
        }
        return deployExpr;
    }
}

