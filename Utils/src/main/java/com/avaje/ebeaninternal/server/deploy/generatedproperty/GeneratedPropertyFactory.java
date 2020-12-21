/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.generatedproperty.CounterFactory;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.InsertTimestampFactory;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.UpdateTimestampFactory;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import java.math.BigDecimal;
import java.util.HashSet;

public class GeneratedPropertyFactory {
    CounterFactory counterFactory;
    InsertTimestampFactory insertFactory;
    UpdateTimestampFactory updateFactory;
    HashSet<String> numberTypes = new HashSet();

    public GeneratedPropertyFactory() {
        this.counterFactory = new CounterFactory();
        this.insertFactory = new InsertTimestampFactory();
        this.updateFactory = new UpdateTimestampFactory();
        this.numberTypes.add(Integer.class.getName());
        this.numberTypes.add(Integer.TYPE.getName());
        this.numberTypes.add(Long.class.getName());
        this.numberTypes.add(Long.TYPE.getName());
        this.numberTypes.add(Short.class.getName());
        this.numberTypes.add(Short.TYPE.getName());
        this.numberTypes.add(Double.class.getName());
        this.numberTypes.add(Double.TYPE.getName());
        this.numberTypes.add(BigDecimal.class.getName());
    }

    private boolean isNumberType(String typeClassName) {
        return this.numberTypes.contains(typeClassName);
    }

    public void setVersion(DeployBeanProperty property) {
        if (this.isNumberType(property.getPropertyType().getName())) {
            this.setCounter(property);
        } else {
            this.setUpdateTimestamp(property);
        }
    }

    public void setCounter(DeployBeanProperty property) {
        this.counterFactory.setCounter(property);
    }

    public void setInsertTimestamp(DeployBeanProperty property) {
        this.insertFactory.setInsertTimestamp(property);
    }

    public void setUpdateTimestamp(DeployBeanProperty property) {
        this.updateFactory.setUpdateTimestamp(property);
    }
}

