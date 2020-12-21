/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.annotation.Sql;
import com.avaje.ebean.annotation.SqlSelect;
import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationParser;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnnotationSql
extends AnnotationParser {
    public AnnotationSql(DeployBeanInfo<?> info) {
        super(info);
    }

    @Override
    public void parse() {
        SqlSelect sqlSelect;
        Class cls = this.descriptor.getBeanType();
        Sql sql = cls.getAnnotation(Sql.class);
        if (sql != null) {
            this.setSql(sql);
        }
        if ((sqlSelect = cls.getAnnotation(SqlSelect.class)) != null) {
            this.setSqlSelect(sqlSelect);
        }
    }

    private void setSql(Sql sql) {
        SqlSelect[] select = sql.select();
        for (int i = 0; i < select.length; ++i) {
            this.setSqlSelect(select[i]);
        }
    }

    private void setSqlSelect(SqlSelect sqlSelect) {
        DRawSqlMeta rawSqlMeta = new DRawSqlMeta(sqlSelect);
        this.descriptor.add(rawSqlMeta);
    }
}

