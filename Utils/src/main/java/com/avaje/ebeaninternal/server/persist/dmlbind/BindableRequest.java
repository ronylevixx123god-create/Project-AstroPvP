/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface BindableRequest {
    public void setIdValue(Object var1);

    public Object bind(Object var1, BeanProperty var2, String var3, boolean var4) throws SQLException;

    public Object bind(String var1, Object var2, int var3) throws SQLException;

    public Object bindNoLog(Object var1, int var2, String var3) throws SQLException;

    public Object bindNoLog(Object var1, BeanProperty var2, String var3, boolean var4) throws SQLException;

    public boolean isIncluded(BeanProperty var1);

    public boolean isIncludedWhere(BeanProperty var1);

    public void registerUpdateGenValue(BeanProperty var1, Object var2, Object var3);

    public void registerAdditionalProperty(String var1);

    public PersistRequestBean<?> getPersistRequest();
}

