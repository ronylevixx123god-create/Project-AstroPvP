/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.DRawSqlColumnInfo;
import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DeployPropertyParserRawSql
extends DeployParser {
    private final DRawSqlSelect rawSqlSelect;

    public DeployPropertyParserRawSql(DRawSqlSelect rawSqlSelect) {
        this.rawSqlSelect = rawSqlSelect;
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
        DRawSqlColumnInfo columnInfo = this.rawSqlSelect.getRawSqlColumnInfo(expression);
        if (columnInfo == null) {
            return null;
        }
        return columnInfo.getName();
    }
}

