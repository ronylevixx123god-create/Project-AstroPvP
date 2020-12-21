/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BindParams
implements Serializable {
    private static final long serialVersionUID = 4541081933302086285L;
    private ArrayList<Param> positionedParameters = new ArrayList();
    private HashMap<String, Param> namedParameters = new HashMap();
    private int queryPlanHash = 1;
    private String preparedSql;

    public BindParams copy() {
        BindParams copy = new BindParams();
        for (Param p : this.positionedParameters) {
            copy.positionedParameters.add(p.copy());
        }
        for (Map.Entry<String, Param> entry : this.namedParameters.entrySet()) {
            copy.namedParameters.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    public int queryBindHash() {
        int hc = this.namedParameters.hashCode();
        for (int i = 0; i < this.positionedParameters.size(); ++i) {
            hc = hc * 31 + this.positionedParameters.get(i).hashCode();
        }
        return hc;
    }

    public int hashCode() {
        int hc = this.getClass().hashCode();
        hc = hc * 31 + this.namedParameters.hashCode();
        for (int i = 0; i < this.positionedParameters.size(); ++i) {
            hc = hc * 31 + this.positionedParameters.get(i).hashCode();
        }
        hc = hc * 31 + (this.preparedSql == null ? 0 : this.preparedSql.hashCode());
        return hc;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof BindParams) {
            return this.hashCode() == o.hashCode();
        }
        return false;
    }

    public boolean isEmpty() {
        return this.positionedParameters.isEmpty() && this.namedParameters.isEmpty();
    }

    public int size() {
        return this.positionedParameters.size();
    }

    public boolean requiresNamedParamsPrepare() {
        return !this.namedParameters.isEmpty() && this.positionedParameters.isEmpty();
    }

    public void setNullParameter(int position, int jdbcType) {
        Param p = this.getParam(position);
        p.setInNullType(jdbcType);
    }

    public void setParameter(int position, Object value, int outType) {
        this.addToQueryPlanHash(String.valueOf(position), value);
        Param p = this.getParam(position);
        p.setInValue(value);
        p.setOutType(outType);
    }

    public void setParameter(int position, Object value) {
        this.addToQueryPlanHash(String.valueOf(position), value);
        Param p = this.getParam(position);
        p.setInValue(value);
    }

    public void registerOut(int position, int outType) {
        Param p = this.getParam(position);
        p.setOutType(outType);
    }

    private Param getParam(String name) {
        Param p = this.namedParameters.get(name);
        if (p == null) {
            p = new Param();
            this.namedParameters.put(name, p);
        }
        return p;
    }

    private Param getParam(int position) {
        int more = position - this.positionedParameters.size();
        if (more > 0) {
            for (int i = 0; i < more; ++i) {
                this.positionedParameters.add(new Param());
            }
        }
        return this.positionedParameters.get(position - 1);
    }

    public void setParameter(String name, Object value, int outType) {
        this.addToQueryPlanHash(name, value);
        Param p = this.getParam(name);
        p.setInValue(value);
        p.setOutType(outType);
    }

    public void setNullParameter(String name, int jdbcType) {
        Param p = this.getParam(name);
        p.setInNullType(jdbcType);
    }

    public Param setParameter(String name, Object value) {
        this.addToQueryPlanHash(name, value);
        Param p = this.getParam(name);
        p.setInValue(value);
        return p;
    }

    private void addToQueryPlanHash(String name, Object value) {
        if (value != null && value instanceof Collection) {
            this.queryPlanHash = this.queryPlanHash * 31 + name.hashCode();
            this.queryPlanHash = this.queryPlanHash * 31 + ((Collection)value).size();
        }
    }

    public int getQueryPlanHash() {
        return this.queryPlanHash;
    }

    public Param setEncryptionKey(String name, Object value) {
        Param p = this.getParam(name);
        p.setEncryptionKey(value);
        return p;
    }

    public void registerOut(String name, int outType) {
        Param p = this.getParam(name);
        p.setOutType(outType);
    }

    public Param getParameter(int position) {
        return this.getParam(position);
    }

    public Param getParameter(String name) {
        return this.getParam(name);
    }

    public List<Param> positionedParameters() {
        return this.positionedParameters;
    }

    public void setPreparedSql(String preparedSql) {
        this.preparedSql = preparedSql;
    }

    public String getPreparedSql() {
        return this.preparedSql;
    }

    public static final class Param {
        private boolean encryptionKey;
        private boolean isInParam;
        private boolean isOutParam;
        private int type;
        private Object inValue;
        private Object outValue;
        private int textLocation;

        public Param copy() {
            Param copy = new Param();
            copy.isInParam = this.isInParam;
            copy.isOutParam = this.isOutParam;
            copy.type = this.type;
            copy.inValue = this.inValue;
            copy.outValue = this.outValue;
            return copy;
        }

        public int hashCode() {
            int hc = this.getClass().hashCode();
            hc = hc * 31 + (this.isInParam ? 0 : 1);
            hc = hc * 31 + (this.isOutParam ? 0 : 1);
            hc = hc * 31 + this.type;
            hc = hc * 31 + (this.inValue == null ? 0 : this.inValue.hashCode());
            return hc;
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o == this) {
                return true;
            }
            if (o instanceof Param) {
                return this.hashCode() == o.hashCode();
            }
            return false;
        }

        public boolean isInParam() {
            return this.isInParam;
        }

        public boolean isOutParam() {
            return this.isOutParam;
        }

        public int getType() {
            return this.type;
        }

        public void setOutType(int type) {
            this.type = type;
            this.isOutParam = true;
        }

        public void setInValue(Object in) {
            this.inValue = in;
            this.isInParam = true;
        }

        public void setEncryptionKey(Object in) {
            this.inValue = in;
            this.isInParam = true;
            this.encryptionKey = true;
        }

        public void setInNullType(int type) {
            this.type = type;
            this.inValue = null;
            this.isInParam = true;
        }

        public Object getOutValue() {
            return this.outValue;
        }

        public Object getInValue() {
            return this.inValue;
        }

        public void setOutValue(Object out) {
            this.outValue = out;
        }

        public int getTextLocation() {
            return this.textLocation;
        }

        public void setTextLocation(int textLocation) {
            this.textLocation = textLocation;
        }

        public boolean isEncryptionKey() {
            return this.encryptionKey;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class OrderedList {
        final List<Param> paramList;
        final StringBuilder preparedSql;

        public OrderedList() {
            this(new ArrayList<Param>());
        }

        public OrderedList(List<Param> paramList) {
            this.paramList = paramList;
            this.preparedSql = new StringBuilder();
        }

        public void add(Param param) {
            this.paramList.add(param);
        }

        public int size() {
            return this.paramList.size();
        }

        public List<Param> list() {
            return this.paramList;
        }

        public void appendSql(String parsedSql) {
            this.preparedSql.append(parsedSql);
        }

        public String getPreparedSql() {
            return this.preparedSql.toString();
        }
    }
}

