/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.RawSql;
import com.avaje.ebeaninternal.server.querydefn.SimpleTextParser;

class DRawSqlParser {
    public static final String $_AND_HAVING = "${andHaving}";
    public static final String $_HAVING = "${having}";
    public static final String $_AND_WHERE = "${andWhere}";
    public static final String $_WHERE = "${where}";
    private static final String ORDER_BY = "order by";
    private final SimpleTextParser textParser;
    private String sql;
    private int placeHolderWhere;
    private int placeHolderAndWhere;
    private int placeHolderHaving;
    private int placeHolderAndHaving;
    private boolean hasPlaceHolders;
    private int selectPos = -1;
    private int fromPos = -1;
    private int wherePos = -1;
    private int groupByPos = -1;
    private int havingPos = -1;
    private int orderByPos = -1;
    private boolean whereExprAnd;
    private int whereExprPos = -1;
    private boolean havingExprAnd;
    private int havingExprPos = -1;

    public static RawSql.Sql parse(String sql) {
        return new DRawSqlParser(sql).parse();
    }

    private DRawSqlParser(String sql) {
        this.sql = sql;
        this.hasPlaceHolders = this.findAndRemovePlaceHolders();
        this.textParser = new SimpleTextParser(sql);
    }

    private RawSql.Sql parse() {
        if (!this.hasPlaceHolders()) {
            this.parseSqlFindKeywords(true);
        }
        this.whereExprPos = this.findWhereExprPosition();
        this.havingExprPos = this.findHavingExprPosition();
        String preFrom = this.removeWhitespace(this.findPreFromSql());
        String preWhere = this.removeWhitespace(this.findPreWhereSql());
        String preHaving = this.removeWhitespace(this.findPreHavingSql());
        String orderBySql = this.findOrderBySql();
        preFrom = this.trimSelectKeyword(preFrom);
        return new RawSql.Sql(this.sql.hashCode(), preFrom, preWhere, this.whereExprAnd, preHaving, this.havingExprAnd, orderBySql);
    }

    private boolean findAndRemovePlaceHolders() {
        this.placeHolderWhere = this.removePlaceHolder($_WHERE);
        this.placeHolderAndWhere = this.removePlaceHolder($_AND_WHERE);
        this.placeHolderHaving = this.removePlaceHolder($_HAVING);
        this.placeHolderAndHaving = this.removePlaceHolder($_AND_HAVING);
        return this.hasPlaceHolders();
    }

    private int removePlaceHolder(String placeHolder) {
        int pos = this.sql.indexOf(placeHolder);
        if (pos > -1) {
            int after = pos + placeHolder.length() + 1;
            this.sql = after > this.sql.length() ? this.sql.substring(0, pos) : this.sql.substring(0, pos) + this.sql.substring(after);
        }
        return pos;
    }

    private boolean hasPlaceHolders() {
        if (this.placeHolderWhere > -1) {
            return true;
        }
        if (this.placeHolderAndWhere > -1) {
            return true;
        }
        if (this.placeHolderHaving > -1) {
            return true;
        }
        return this.placeHolderAndHaving > -1;
    }

    private String trimSelectKeyword(String preWhereExprSql) {
        if ((preWhereExprSql = preWhereExprSql.trim()).length() < 7) {
            throw new RuntimeException("Expecting at least 7 chars in [" + preWhereExprSql + "]");
        }
        String select = preWhereExprSql.substring(0, 7);
        if (!select.equalsIgnoreCase("select ")) {
            throw new RuntimeException("Expecting [" + preWhereExprSql + "] to start with \"select\"");
        }
        return preWhereExprSql.substring(7);
    }

    private String findOrderBySql() {
        if (this.orderByPos > -1) {
            int pos = this.orderByPos + ORDER_BY.length();
            return this.sql.substring(pos).trim();
        }
        return null;
    }

    private String findPreHavingSql() {
        if (this.havingExprPos > this.whereExprPos) {
            return this.sql.substring(this.whereExprPos, this.havingExprPos - 1);
        }
        if (this.whereExprPos > -1) {
            if (this.orderByPos == -1) {
                return this.sql.substring(this.whereExprPos);
            }
            if (this.whereExprPos == this.orderByPos) {
                return "";
            }
            return this.sql.substring(this.whereExprPos, this.orderByPos - 1);
        }
        return null;
    }

    private String findPreFromSql() {
        return this.sql.substring(0, this.fromPos - 1);
    }

    private String findPreWhereSql() {
        if (this.whereExprPos > -1) {
            return this.sql.substring(this.fromPos, this.whereExprPos - 1);
        }
        return this.sql.substring(this.fromPos);
    }

    private void parseSqlFindKeywords(boolean allKeywords) {
        int startOrderBy;
        this.selectPos = this.textParser.findWordLower("select");
        if (this.selectPos == -1) {
            String msg = "Error parsing sql, can not find SELECT keyword in:";
            throw new RuntimeException(msg + this.sql);
        }
        this.fromPos = this.textParser.findWordLower("from");
        if (this.fromPos == -1) {
            String msg = "Error parsing sql, can not find FROM keyword in:";
            throw new RuntimeException(msg + this.sql);
        }
        if (!allKeywords) {
            return;
        }
        this.wherePos = this.textParser.findWordLower("where");
        this.groupByPos = this.wherePos == -1 ? this.textParser.findWordLower("group", this.fromPos + 5) : this.textParser.findWordLower("group");
        if (this.groupByPos > -1) {
            this.havingPos = this.textParser.findWordLower("having");
        }
        if ((startOrderBy = this.havingPos) == -1) {
            startOrderBy = this.groupByPos;
        }
        if (startOrderBy == -1) {
            startOrderBy = this.wherePos;
        }
        if (startOrderBy == -1) {
            startOrderBy = this.fromPos;
        }
        this.orderByPos = this.textParser.findWordLower("order", startOrderBy);
    }

    private int findWhereExprPosition() {
        if (this.hasPlaceHolders) {
            if (this.placeHolderWhere > -1) {
                return this.placeHolderWhere;
            }
            this.whereExprAnd = true;
            return this.placeHolderAndWhere;
        }
        boolean bl = this.whereExprAnd = this.wherePos > 0;
        if (this.groupByPos > 0) {
            return this.groupByPos;
        }
        if (this.havingPos > 0) {
            return this.havingPos;
        }
        if (this.orderByPos > 0) {
            return this.orderByPos;
        }
        return -1;
    }

    private int findHavingExprPosition() {
        if (this.hasPlaceHolders) {
            if (this.placeHolderHaving > -1) {
                return this.placeHolderHaving;
            }
            this.havingExprAnd = true;
            return this.placeHolderAndHaving;
        }
        boolean bl = this.havingExprAnd = this.havingPos > 0;
        if (this.orderByPos > 0) {
            return this.orderByPos;
        }
        return -1;
    }

    private String removeWhitespace(String sql) {
        if (sql == null) {
            return "";
        }
        boolean removeWhitespace = false;
        int length = sql.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            char c = sql.charAt(i);
            if (removeWhitespace) {
                if (Character.isWhitespace(c)) continue;
                sb.append(c);
                removeWhitespace = false;
                continue;
            }
            if (c == '\r' || c == '\n') {
                sb.append('\n');
                removeWhitespace = true;
                continue;
            }
            sb.append(c);
        }
        String s = sb.toString();
        return s.trim();
    }
}

