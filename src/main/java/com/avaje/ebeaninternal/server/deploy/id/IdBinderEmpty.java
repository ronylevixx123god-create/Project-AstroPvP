/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class IdBinderEmpty
implements IdBinder {
    private static final String bindIdSql = "";
    private static final BeanProperty[] properties = new BeanProperty[0];

    @Override
    public void initialise() {
    }

    @Override
    public Object readTerm(String idTermValue) {
        return null;
    }

    @Override
    public String writeTerm(Object idValue) {
        return null;
    }

    @Override
    public String getOrderBy(String pathPrefix, boolean ascending) {
        return pathPrefix;
    }

    @Override
    public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
    }

    @Override
    public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {
    }

    @Override
    public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public String getIdProperty() {
        return null;
    }

    @Override
    public BeanProperty findBeanProperty(String dbColumnName) {
        return null;
    }

    @Override
    public boolean isComplexId() {
        return true;
    }

    @Override
    public String getDefaultOrderBy() {
        return bindIdSql;
    }

    @Override
    public BeanProperty[] getProperties() {
        return properties;
    }

    @Override
    public String getBindIdSql(String baseTableAlias) {
        return bindIdSql;
    }

    @Override
    public String getAssocOneIdExpr(String prefix, String operator) {
        return null;
    }

    @Override
    public String getAssocIdInExpr(String prefix) {
        return null;
    }

    @Override
    public void addIdInBindValue(SpiExpressionRequest request, Object value) {
    }

    @Override
    public String getIdInValueExprDelete(int size) {
        return this.getIdInValueExpr(size);
    }

    @Override
    public String getIdInValueExpr(int size) {
        return bindIdSql;
    }

    @Override
    public String getBindIdInSql(String baseTableAlias) {
        return null;
    }

    @Override
    public Object[] getIdValues(Object bean) {
        return null;
    }

    @Override
    public Object[] getBindValues(Object idValue) {
        return new Object[]{idValue};
    }

    @Override
    public void bindId(DefaultSqlUpdate sqlUpdate, Object value) {
    }

    @Override
    public void bindId(DataBind dataBind, Object value) throws SQLException {
    }

    @Override
    public void loadIgnore(DbReadContext ctx) {
    }

    @Override
    public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
        return null;
    }

    @Override
    public Object read(DbReadContext ctx) throws SQLException {
        return null;
    }

    @Override
    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
    }

    @Override
    public Object convertSetId(Object idValue, Object bean) {
        return idValue;
    }

    @Override
    public Object readData(DataInput dataOutput) throws IOException {
        return null;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object idValue) throws IOException {
    }
}

