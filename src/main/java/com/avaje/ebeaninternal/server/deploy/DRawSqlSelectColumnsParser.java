/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.DRawSqlColumnInfo;
import com.avaje.ebeaninternal.server.deploy.DRawSqlSelectBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DRawSqlSelectColumnsParser {
    private static Logger logger = Logger.getLogger(DRawSqlSelectColumnsParser.class.getName());
    private String matchDescription;
    private String searchColumn;
    private int columnIndex;
    private int pos;
    private final int end;
    private final String sqlSelect;
    private final List<DRawSqlColumnInfo> columns = new ArrayList<DRawSqlColumnInfo>();
    private final BeanDescriptor<?> desc;
    private final NamingConvention namingConvention;
    private final DRawSqlSelectBuilder parent;
    private final boolean debug;

    public DRawSqlSelectColumnsParser(DRawSqlSelectBuilder parent, String sqlSelect) {
        this.parent = parent;
        this.debug = parent.isDebug();
        this.namingConvention = parent.getNamingConvention();
        this.desc = parent.getBeanDescriptor();
        this.sqlSelect = sqlSelect;
        this.end = sqlSelect.length();
    }

    public List<DRawSqlColumnInfo> parse() {
        while (this.pos <= this.end) {
            this.nextColumnInfo();
        }
        return this.columns;
    }

    private void nextColumnInfo() {
        int start = this.pos;
        this.nextComma();
        String colInfo = this.sqlSelect.substring(start, this.pos);
        ++this.pos;
        colInfo = colInfo.trim();
        int secLastSpace = -1;
        int lastSpace = colInfo.lastIndexOf(32);
        if (lastSpace > -1) {
            secLastSpace = colInfo.lastIndexOf(32, lastSpace - 1);
        }
        String colName = null;
        String colLabel = null;
        if (lastSpace == -1) {
            colLabel = colName = colInfo;
        } else if (secLastSpace == -1) {
            colName = colInfo.substring(0, lastSpace);
            colLabel = colInfo.substring(lastSpace + 1);
            if (colName.equals("")) {
                colName = colLabel;
            }
        } else {
            String expectedAs = colInfo.substring(secLastSpace + 1, lastSpace);
            if (expectedAs.toLowerCase().equals("as")) {
                colName = colInfo.substring(0, secLastSpace);
                colLabel = colInfo.substring(lastSpace + 1);
            } else {
                String msg = "Error in " + this.parent.getErrName() + ". ";
                msg = msg + "Expected \"AS\" keyword but got [" + expectedAs + "] in select clause [" + colInfo + "]";
                throw new PersistenceException(msg);
            }
        }
        BeanProperty prop = this.findProperty(colLabel);
        if (prop == null) {
            String msg;
            if (this.debug) {
                msg = "ColumnMapping ... idx[" + this.columnIndex + "] ERROR, no property found to match... column[" + colName + "] label[" + colLabel + "] search[" + this.searchColumn + "]";
                this.parent.debug(msg);
            }
            msg = "Error in " + this.parent.getErrName() + ". ";
            msg = msg + "No matching bean property for column[" + colName + "] columnLabel[" + colLabel + "] idx[" + this.columnIndex + "] using search[" + this.searchColumn + "] found?";
            logger.log(Level.SEVERE, msg);
        } else {
            String msg = null;
            if (this.debug || logger.isLoggable(Level.FINE)) {
                msg = "ColumnMapping ... idx[" + this.columnIndex + "] match column[" + colName + "] label[" + colLabel + "] to property[" + prop + "]" + this.matchDescription;
            }
            if (this.debug) {
                this.parent.debug(msg);
            }
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(msg);
            }
            DRawSqlColumnInfo info = new DRawSqlColumnInfo(colName, colLabel, prop.getName(), prop.isScalar());
            this.columns.add(info);
            ++this.columnIndex;
        }
    }

    private String removeQuotedIdentifierChars(String columnLabel) {
        char c = columnLabel.charAt(0);
        if (Character.isJavaIdentifierStart(c)) {
            return columnLabel;
        }
        String result = columnLabel.substring(1, columnLabel.length() - 1);
        String msg = "sql-select trimming quoted identifier from[" + columnLabel + "] to[" + result + "]";
        logger.fine(msg);
        return result;
    }

    private BeanProperty findProperty(String column) {
        this.searchColumn = column;
        int dotPos = this.searchColumn.indexOf(".");
        if (dotPos > -1) {
            this.searchColumn = this.searchColumn.substring(dotPos + 1);
        }
        this.searchColumn = this.removeQuotedIdentifierChars(this.searchColumn);
        BeanProperty matchingProp = this.desc.getBeanProperty(this.searchColumn);
        if (matchingProp != null) {
            this.matchDescription = "";
            return matchingProp;
        }
        String propertyName = this.namingConvention.getPropertyFromColumn(this.desc.getBeanType(), this.searchColumn);
        matchingProp = this.desc.getBeanProperty(propertyName);
        if (matchingProp != null) {
            this.matchDescription = " ... using naming convention";
            return matchingProp;
        }
        this.matchDescription = " ... by linear search";
        BeanProperty[] propertiesBase = this.desc.propertiesBaseScalar();
        for (int i = 0; i < propertiesBase.length; ++i) {
            BeanProperty prop = propertiesBase[i];
            if (!this.isMatch(prop, this.searchColumn)) continue;
            return prop;
        }
        BeanProperty[] propertiesId = this.desc.propertiesId();
        for (int i = 0; i < propertiesId.length; ++i) {
            BeanProperty prop = propertiesId[i];
            if (!this.isMatch(prop, this.searchColumn)) continue;
            return prop;
        }
        BeanPropertyAssocOne<?>[] propertiesAssocOne = this.desc.propertiesOne();
        for (int i = 0; i < propertiesAssocOne.length; ++i) {
            BeanPropertyAssocOne<?> prop = propertiesAssocOne[i];
            if (!this.isMatch(prop, this.searchColumn)) continue;
            return prop;
        }
        return null;
    }

    private boolean isMatch(BeanProperty prop, String columnLabel) {
        if (columnLabel.equalsIgnoreCase(prop.getDbColumn())) {
            return true;
        }
        return columnLabel.equalsIgnoreCase(prop.getName());
    }

    private int nextComma() {
        boolean inQuote = false;
        while (this.pos < this.end) {
            char c = this.sqlSelect.charAt(this.pos);
            if (c == '\'') {
                inQuote = !inQuote;
            } else if (!inQuote && c == ',') {
                return this.pos;
            }
            ++this.pos;
        }
        return this.pos;
    }
}

