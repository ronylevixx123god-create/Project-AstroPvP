/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class IdBinderEmbedded
implements IdBinder {
    private final BeanPropertyAssocOne<?> embIdProperty;
    private final boolean idInExpandedForm;
    private BeanProperty[] props;
    private BeanDescriptor<?> idDesc;
    private String idInValueSql;

    public IdBinderEmbedded(boolean idInExpandedForm, BeanPropertyAssocOne<?> embIdProperty) {
        this.idInExpandedForm = idInExpandedForm;
        this.embIdProperty = embIdProperty;
    }

    @Override
    public void initialise() {
        this.idDesc = this.embIdProperty.getTargetDescriptor();
        this.props = this.embIdProperty.getProperties();
        this.idInValueSql = this.idInExpandedForm ? this.idInExpanded() : this.idInCompressed();
    }

    private String idInExpanded() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(" and ");
            }
            sb.append(this.embIdProperty.getName());
            sb.append(".");
            sb.append(this.props[i].getName());
            sb.append("=?");
        }
        sb.append(")");
        return sb.toString();
    }

    private String idInCompressed() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getOrderBy(String pathPrefix, boolean ascending) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            if (pathPrefix != null) {
                sb.append(pathPrefix).append(".");
            }
            sb.append(this.embIdProperty.getName()).append(".");
            sb.append(this.props[i].getName());
            if (ascending) continue;
            sb.append(" desc");
        }
        return sb.toString();
    }

    @Override
    public void createLdapNameById(LdapName name, Object id) throws InvalidNameException {
        for (int i = 0; i < this.props.length; ++i) {
            Object v = this.props[i].getValue(id);
            Rdn rdn = new Rdn(this.props[i].getDbColumn(), v);
            name.add(rdn);
        }
    }

    @Override
    public void createLdapNameByBean(LdapName name, Object bean) throws InvalidNameException {
        Object id = this.embIdProperty.getValue(bean);
        this.createLdapNameById(name, id);
    }

    public BeanDescriptor<?> getIdBeanDescriptor() {
        return this.idDesc;
    }

    @Override
    public int getPropertyCount() {
        return this.props.length;
    }

    @Override
    public String getIdProperty() {
        return this.embIdProperty.getName();
    }

    @Override
    public void buildSelectExpressionChain(String prefix, List<String> selectChain) {
        prefix = SplitName.add(prefix, this.embIdProperty.getName());
        for (int i = 0; i < this.props.length; ++i) {
            this.props[i].buildSelectExpressionChain(prefix, selectChain);
        }
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
            sb.append(this.embIdProperty.getName());
            sb.append(".");
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
        if (!this.idInExpandedForm) {
            return this.getIdInValueExpr(size);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int j = 0; j < size; ++j) {
            if (j > 0) {
                sb.append(" or ");
            }
            sb.append("(");
            for (int i = 0; i < this.props.length; ++i) {
                if (i > 0) {
                    sb.append(" and ");
                }
                sb.append(this.props[i].getDbColumn());
                sb.append("=?");
            }
            sb.append(")");
        }
        sb.append(") ");
        return sb.toString();
    }

    @Override
    public String getIdInValueExpr(int size) {
        StringBuilder sb = new StringBuilder();
        if (!this.idInExpandedForm) {
            sb.append(" in");
        }
        sb.append(" (");
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                if (this.idInExpandedForm) {
                    sb.append(" or ");
                } else {
                    sb.append(",");
                }
            }
            sb.append(this.idInValueSql);
        }
        sb.append(") ");
        return sb.toString();
    }

    public String getIdInValueExpr() {
        return this.idInValueSql;
    }

    @Override
    public Object readTerm(String idTermValue) {
        String[] split = idTermValue.split("|");
        if (split.length != this.props.length) {
            String msg = "Failed to split [" + idTermValue + "] using | for id.";
            throw new PersistenceException(msg);
        }
        Object embId = this.idDesc.createVanillaBean();
        for (int i = 0; i < this.props.length; ++i) {
            Object v = this.props[i].getScalarType().parse(split[i]);
            this.props[i].setValue(embId, v);
        }
        return embId;
    }

    @Override
    public String writeTerm(Object idValue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.props.length; ++i) {
            Object v = this.props[i].getValue(idValue);
            String formatValue = this.props[i].getScalarType().format(v);
            if (i > 0) {
                sb.append("|");
            }
            sb.append(formatValue);
        }
        return sb.toString();
    }

    @Override
    public Object[] getIdValues(Object bean) {
        bean = this.embIdProperty.getValue(bean);
        Object[] bindvalues = new Object[this.props.length];
        for (int i = 0; i < this.props.length; ++i) {
            bindvalues[i] = this.props[i].getValue(bean);
        }
        return bindvalues;
    }

    @Override
    public Object[] getBindValues(Object value) {
        Object[] bindvalues = new Object[this.props.length];
        for (int i = 0; i < this.props.length; ++i) {
            bindvalues[i] = this.props[i].getValue(value);
        }
        return bindvalues;
    }

    @Override
    public void bindId(DefaultSqlUpdate sqlUpdate, Object value) {
        for (int i = 0; i < this.props.length; ++i) {
            Object embFieldValue = this.props[i].getValue(value);
            sqlUpdate.addParameter(embFieldValue);
        }
    }

    @Override
    public void bindId(DataBind dataBind, Object value) throws SQLException {
        for (int i = 0; i < this.props.length; ++i) {
            Object embFieldValue = this.props[i].getValue(value);
            this.props[i].bind(dataBind, embFieldValue);
        }
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        Object embId = this.idDesc.createVanillaBean();
        boolean notNull = true;
        for (int i = 0; i < this.props.length; ++i) {
            Object value = this.props[i].readData(dataInput);
            this.props[i].setValue(embId, value);
            if (value != null) continue;
            notNull = false;
        }
        if (notNull) {
            return embId;
        }
        return null;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object idValue) throws IOException {
        for (int i = 0; i < this.props.length; ++i) {
            Object embFieldValue = this.props[i].getValue(idValue);
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
    public Object read(DbReadContext ctx) throws SQLException {
        Object embId = this.idDesc.createVanillaBean();
        boolean notNull = true;
        for (int i = 0; i < this.props.length; ++i) {
            Object value = this.props[i].readSet(ctx, embId, null);
            if (value != null) continue;
            notNull = false;
        }
        if (notNull) {
            return embId;
        }
        return null;
    }

    @Override
    public Object readSet(DbReadContext ctx, Object bean) throws SQLException {
        Object embId = this.read(ctx);
        if (embId != null) {
            this.embIdProperty.setValue(bean, embId);
            return embId;
        }
        return null;
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
            sb.append(this.embIdProperty.getName());
            sb.append(".");
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
    public String getBindIdInSql(String baseTableAlias) {
        if (this.idInExpandedForm) {
            return "";
        }
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
    public Object convertSetId(Object idValue, Object bean) {
        if (bean != null) {
            this.embIdProperty.setValueIntercept(bean, idValue);
        }
        return idValue;
    }
}

