/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.util;

import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindParamsParser {
    public static final String ENCRYPTKEY_PREFIX = "encryptkey_";
    public static final String ENCRYPTKEY_GAP = "___";
    private static final int ENCRYPTKEY_PREFIX_LEN = "encryptkey_".length();
    private static final int ENCRYPTKEY_GAP_LEN = "___".length();
    private static final String quote = "'";
    private static final String colon = ":";
    private final BindParams params;
    private final String sql;
    private final BeanDescriptor<?> beanDescriptor;

    public static String parse(BindParams params, String sql) {
        return BindParamsParser.parse(params, sql, null);
    }

    public static String parse(BindParams params, String sql, BeanDescriptor<?> beanDescriptor) {
        return new BindParamsParser(params, sql, beanDescriptor).parseSql();
    }

    public static BindParams.OrderedList parseNamedParams(BindParams params, String sql) {
        return new BindParamsParser(params, sql, null).parseSqlNamedParams();
    }

    private BindParamsParser(BindParams params, String sql, BeanDescriptor<?> beanDescriptor) {
        this.params = params;
        this.sql = sql;
        this.beanDescriptor = beanDescriptor;
    }

    private BindParams.OrderedList parseSqlNamedParams() {
        BindParams.OrderedList orderedList = new BindParams.OrderedList();
        this.parseNamedParams(orderedList);
        return orderedList;
    }

    private String parseSql() {
        String prepardSql;
        String preparedSql = this.params.getPreparedSql();
        if (preparedSql != null && preparedSql.length() > 0) {
            return preparedSql;
        }
        if (this.params.requiresNamedParamsPrepare()) {
            BindParams.OrderedList orderedList = new BindParams.OrderedList(this.params.positionedParameters());
            this.parseNamedParams(orderedList);
            prepardSql = orderedList.getPreparedSql();
        } else {
            prepardSql = this.sql;
        }
        this.params.setPreparedSql(prepardSql);
        return prepardSql;
    }

    private void parseNamedParams(BindParams.OrderedList orderedList) {
        this.parseNamedParams(0, orderedList);
    }

    private void parseNamedParams(int startPos, BindParams.OrderedList orderedList) {
        if (this.sql == null) {
            throw new PersistenceException("query does not contain any named bind parameters?");
        }
        if (startPos > this.sql.length()) {
            return;
        }
        int beginQuotePos = this.sql.indexOf(quote, startPos);
        int nameParamStart = this.sql.indexOf(colon, startPos);
        if (beginQuotePos > 0 && beginQuotePos < nameParamStart) {
            int endQuotePos = this.sql.indexOf(quote, beginQuotePos + 1);
            String sub = this.sql.substring(startPos, endQuotePos + 1);
            orderedList.appendSql(sub);
            this.parseNamedParams(endQuotePos + 1, orderedList);
        } else if (nameParamStart < 0) {
            String sub = this.sql.substring(startPos, this.sql.length());
            orderedList.appendSql(sub);
        } else {
            char c;
            int endOfParam = nameParamStart + 1;
            while (((c = this.sql.charAt(endOfParam)) == '_' || Character.isLetterOrDigit(c)) && ++endOfParam < this.sql.length()) {
            }
            String paramName = this.sql.substring(nameParamStart + 1, endOfParam);
            BindParams.Param param = paramName.startsWith(ENCRYPTKEY_PREFIX) ? this.addEncryptKeyParam(paramName) : this.params.getParameter(paramName);
            if (param == null) {
                String msg = "Bind value is not set or null for [" + paramName + "] in [" + this.sql + "]";
                throw new PersistenceException(msg);
            }
            String sub = this.sql.substring(startPos, nameParamStart);
            orderedList.appendSql(sub);
            Object inValue = param.getInValue();
            if (inValue != null && inValue instanceof Collection) {
                Collection collection = (Collection)inValue;
                Iterator it = collection.iterator();
                int c2 = 0;
                while (it.hasNext()) {
                    Object elVal = it.next();
                    if (++c2 > 1) {
                        orderedList.appendSql(",");
                    }
                    orderedList.appendSql("?");
                    BindParams.Param elParam = new BindParams.Param();
                    elParam.setInValue(elVal);
                    orderedList.add(elParam);
                }
            } else {
                orderedList.add(param);
                orderedList.appendSql("?");
            }
            this.parseNamedParams(endOfParam, orderedList);
        }
    }

    private BindParams.Param addEncryptKeyParam(String keyNamedParam) {
        int pos = keyNamedParam.indexOf(ENCRYPTKEY_GAP, ENCRYPTKEY_PREFIX_LEN);
        String tableName = keyNamedParam.substring(ENCRYPTKEY_PREFIX_LEN, pos);
        String columnName = keyNamedParam.substring(pos + ENCRYPTKEY_GAP_LEN);
        EncryptKey key = this.beanDescriptor.getEncryptKey(tableName, columnName);
        String strKey = key.getStringValue();
        return this.params.setEncryptionKey(keyNamedParam, strKey);
    }
}

