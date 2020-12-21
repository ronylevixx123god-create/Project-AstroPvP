/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.query.LuceneIndexDataReader;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.EnumToDbValueMap;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.ScalarTypeEnum;
import com.avaje.ebeaninternal.server.type.ScalarTypeEnumStandard;
import java.sql.SQLException;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeEnumWithMapping
extends ScalarTypeEnumStandard.EnumBase
implements ScalarType,
ScalarTypeEnum {
    private final EnumToDbValueMap beanDbMap;
    private final int length;

    public ScalarTypeEnumWithMapping(EnumToDbValueMap<?> beanDbMap, Class<?> enumType, int length) {
        super(enumType, false, beanDbMap.getDbType());
        this.beanDbMap = beanDbMap;
        this.length = length;
    }

    @Override
    public String getContraintInValues() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("(");
        Iterator it = this.beanDbMap.dbValues();
        while (it.hasNext()) {
            Object dbValue = it.next();
            if (i++ > 0) {
                sb.append(",");
            }
            if (!this.beanDbMap.isIntegerType()) {
                sb.append("'");
            }
            sb.append(dbValue.toString());
            if (this.beanDbMap.isIntegerType()) continue;
            sb.append("'");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public void bind(DataBind b, Object value) throws SQLException {
        this.beanDbMap.bind(b, value);
    }

    @Override
    public Object read(DataReader dataReader) throws SQLException {
        if (dataReader instanceof LuceneIndexDataReader) {
            String s = dataReader.getString();
            return s == null ? null : this.parse(s);
        }
        return this.beanDbMap.read(dataReader);
    }

    public Object toBeanType(Object dbValue) {
        return this.beanDbMap.getBeanValue(dbValue);
    }

    @Override
    public Object toJdbcType(Object beanValue) {
        return this.beanDbMap.getDbValue(beanValue);
    }
}

