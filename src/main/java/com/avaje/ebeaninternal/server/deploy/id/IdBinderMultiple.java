/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.lib.util.MapFromString;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class IdBinderMultiple
implements IdBinder {
    private final BeanProperty[] props;
    private final String idProperties;
    private final String idInValueSql;

    public IdBinderMultiple(BeanProperty[] idProps) {
        int i;
        this.props = idProps;
        StringBuilder sb = new StringBuilder();
        for (i = 0; i < idProps.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(idProps[i].getName());
        }
        this.idProperties = InternString.intern(sb.toString());
        sb = new StringBuilder();
        sb.append("(");
        for (i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        sb.append(")");
        this.idInValueSql = sb.toString();
    }

    @Override
    public void initialise() {
    }

    @Override
    public String getOrderBy(String pathPrefix, boolean ascending) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" ");
            }
            if (pathPrefix != null) {
                sb.append(pathPrefix).append(".");
            }
            sb.append(this.props[i].getName());
            if (ascending) continue;
            sb.append(" desc");
        }
        return sb.toString();
    }

    @Override
    public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {
        if (!(id instanceof Map)) {
            throw new RuntimeException("Expecting a Map for concatinated key");
        }
        Map mapId = (Map)id;
        for (int i = 0; i < this.props.length; ++i) {
            Object v = mapId.get(this.props[i].getName());
            if (v == null) {
                throw new RuntimeException("No value in Map for key " + this.props[i].getName());
            }
            Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
            name.add(rdn);
        }
    }

    @Override
    public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].buildSelectExpressionChain(prefix, selectChain);
        }
    }

    @Override
    public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {
        for (int i = 0; i < this.props.length; ++i) {
            Object v = this.props[i].getValue(bean);
            Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
            name.add(rdn);
        }
    }

    @Override
    public int getPropertyCount() {
        return this.props.length;
    }

    @Override
    public String getIdProperty() {
        return this.idProperties;
    }

    @Override
    public BeanProperty findBeanProperty(String dbColumnName) {
        for (int i = 0; i < this.props.length; ++i) {
            if (!dbColumnName.equalsIgnoreCase(this.props[i].getDbColumn())) continue;
            return this.props[i];
        }
        return null;
    }

    @Override
    public boolean isComplexId() {
        return true;
    }

    @Override
    public String getDefaultOrderBy() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.props[i].getName());
        }
        return sb.toString();
    }

    @Override
    public BeanProperty[] getProperties() {
        return this.props;
    }

    @Override
    public void addIdInBindValue(SpiExpressionRequest request, Object value) {
        for (int i = 0; i < this.props.length; ++i) {
            request.addBindValue(this.props[i].getValue(value));
        }
    }

    @Override
    public String getIdInValueExprDelete(int size) {
        return this.getIdInValueExpr(size);
    }

    @Override
    public String getIdInValueExpr(int size) {
        StringBuilder sb = new StringBuilder();
        sb.append(" in");
        sb.append(" (");
        sb.append(this.idInValueSql);
        for (int i = 1; i < size; ++i) {
            sb.append(",").append(this.idInValueSql);
        }
        sb.append(") ");
        return sb.toString();
    }

    @Override
    public String getBindIdInSql(String baseTableAlias) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            if (baseTableAlias != null) {
                sb.append(baseTableAlias);
                sb.append(".");
            }
            sb.append(this.props[i].getDbColumn());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Object[] getIdValues(Object bean) {
        Object[] bindvalues = new Object[this.props.length];
        for (int i = 0; i < this.props.length; ++i) {
            bindvalues[i] = this.props[i].getValue(bean);
        }
        return bindvalues;
    }

    @Override
    public Object[] getBindValues(Object idValue) {
        Object[] bindvalues = new Object[this.props.length];
        try {
            Map uidMap = (Map)idValue;
            for (int i = 0; i < this.props.length; ++i) {
                Object value = uidMap.get(this.props[i].getName());
                bindvalues[i] = value;
            }
            return bindvalues;
        }
        catch (ClassCastException e) {
            String msg = "Expecting concatinated idValue to be a Map";
            throw new PersistenceException(msg, e);
        }
    }

    @Override
    public Object readTerm(String idTermValue) {
        String[] split = idTermValue.split("|");
        if (split.length != this.props.length) {
            String msg = "Failed to split [" + idTermValue + "] using | for id.";
            throw new PersistenceException(msg);
        }
        LinkedHashMap uidMap = new LinkedHashMap();
        for (int i = 0; i < this.props.length; ++i) {
            Object v = this.props[i].getScalarType().parse(split[i]);
            uidMap.put(this.props[i].getName(), v);
        }
        return uidMap;
    }

    @Override
    public String writeTerm(Object idValue) {
        Map uidMap = (Map)idValue;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            Object v = uidMap.get(this.props[i].getName());
            String formatValue = this.props[i].getScalarType().format(v);
            if (i > 0) {
                sb.append("|");
            }
            sb.append(formatValue);
        }
        return sb.toString();
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        boolean notNull = true;
        for (int i = 0; i < this.props.length; ++i) {
            Object value = this.props[i].readData(dataInput);
            map.put(this.props[i].getName(), value);
            if (value != null) continue;
            notNull = false;
        }
        if (notNull) {
            return map;
        }
        return null;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object idValue) throws IOException {
        Map map = (Map)idValue;
        for (int i = 0; i < this.props.length; ++i) {
            Object embFieldValue = map.get(this.props[i].getName());
            this.props[i].writeData(dataOutput, embFieldValue);
        }
    }

    @Override
    public void loadIgnore(DbReadContext ctx) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].loadIgnore(ctx);
        }
    }

    @Override
    public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        boolean notNull = false;
        for (int i = 0; i < this.props.length; ++i) {
            Object value = this.props[i].readSet(ctx, bean, null);
            if (value == null) continue;
            map.put(this.props[i].getName(), value);
            notNull = true;
        }
        if (notNull) {
            return map;
        }
        return null;
    }

    @Override
    public Object read(DbReadContext ctx) throws SQLException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        boolean notNull = false;
        for (int i = 0; i < this.props.length; ++i) {
            Object value = this.props[i].read(ctx);
            if (value == null) continue;
            map.put(this.props[i].getName(), value);
            notNull = true;
        }
        if (notNull) {
            return map;
        }
        return null;
    }

    @Override
    public void bindId(DefaultSqlUpdate sqlUpdate, Object idValue) {
        try {
            Map uidMap = (Map)idValue;
            for (int i = 0; i < this.props.length; ++i) {
                Object value = uidMap.get(this.props[i].getName());
                sqlUpdate.addParameter(value);
            }
        }
        catch (ClassCastException e) {
            String msg = "Expecting concatinated idValue to be a Map";
            throw new PersistenceException(msg, e);
        }
    }

    @Override
    public void bindId(DataBind bind, Object idValue) throws SQLException {
        try {
            Map uidMap = (Map)idValue;
            for (int i = 0; i < this.props.length; ++i) {
                Object value = uidMap.get(this.props[i].getName());
                this.props[i].bind(bind, value);
            }
        }
        catch (ClassCastException e) {
            String msg = "Expecting concatinated idValue to be a Map";
            throw new PersistenceException(msg, e);
        }
    }

    @Override
    public void appendSelect(DbSqlContext ctx, boolean subQuery) {
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].appendSelect(ctx, subQuery);
        }
    }

    @Override
    public String getAssocIdInExpr(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            if (prefix != null) {
                sb.append(prefix);
                sb.append(".");
            }
            sb.append(this.props[i].getName());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getAssocOneIdExpr(String prefix, String operator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" and ");
            }
            if (prefix != null) {
                sb.append(prefix);
                sb.append(".");
            }
            sb.append(this.props[i].getName());
            sb.append(operator);
        }
        return sb.toString();
    }

    @Override
    public String getBindIdSql(String baseTableAlias) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" and ");
            }
            if (baseTableAlias != null) {
                sb.append(baseTableAlias);
                sb.append(".");
            }
            sb.append(this.props[i].getDbColumn());
            sb.append(" = ? ");
        }
        return sb.toString();
    }

    @Override
    public Object convertSetId(Object idValue, Object bean) {
        LinkedHashMap<String, String> mapVal = null;
        mapVal = idValue instanceof Map ? (LinkedHashMap<String, String>)idValue : MapFromString.parse(idValue.toString());
        LinkedHashMap newMap = new LinkedHashMap();
        for (int i = 0; i < this.props.length; ++i) {
            BeanProperty prop = this.props[i];
            Object value = mapVal.get(prop.getName());
            value = this.props[i].getScalarType().toBeanType(value);
            newMap.put(prop.getName(), value);
            if (bean == null) continue;
            prop.setValueIntercept(bean, value);
        }
        return newMap;
    }
}

