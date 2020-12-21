/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
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
public interface IdBinder {
    public void initialise();

    public String writeTerm(Object var1);

    public Object readTerm(String var1);

    public void writeData(DataOutput var1, Object var2) throws IOException;

    public Object readData(DataInput var1) throws IOException;

    public void createLdapNameById(LdapName var1, Object var2) throws InvalidNameException;

    public void createLdapNameByBean(LdapName var1, Object var2) throws InvalidNameException;

    public String getIdProperty();

    public BeanProperty findBeanProperty(String var1);

    public int getPropertyCount();

    public boolean isComplexId();

    public String getDefaultOrderBy();

    public String getOrderBy(String var1, boolean var2);

    public Object[] getBindValues(Object var1);

    public Object[] getIdValues(Object var1);

    public String getAssocOneIdExpr(String var1, String var2);

    public String getAssocIdInExpr(String var1);

    public void bindId(DataBind var1, Object var2) throws SQLException;

    public void bindId(DefaultSqlUpdate var1, Object var2);

    public void addIdInBindValue(SpiExpressionRequest var1, Object var2);

    public String getBindIdInSql(String var1);

    public String getIdInValueExpr(int var1);

    public String getIdInValueExprDelete(int var1);

    public void buildSelectExpressionChain(String var1, List<String> var2);

    public Object readSet(DbReadContext var1, Object var2) throws SQLException;

    public void loadIgnore(DbReadContext var1);

    public Object read(DbReadContext var1) throws SQLException;

    public void appendSelect(DbSqlContext var1, boolean var2);

    public String getBindIdSql(String var1);

    public BeanProperty[] getProperties();

    public Object convertSetId(Object var1, Object var2);
}

