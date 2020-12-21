/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import java.util.List;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanQueryAdapterManager {
    private static final Logger logger = Logger.getLogger(BeanQueryAdapterManager.class.getName());
    private final List<BeanQueryAdapter> list;

    public BeanQueryAdapterManager(BootupClasses bootupClasses) {
        this.list = bootupClasses.getBeanQueryAdapters();
    }

    public int getRegisterCount() {
        return this.list.size();
    }

    public void addQueryAdapter(DeployBeanDescriptor<?> deployDesc) {
        for (int i = 0; i < this.list.size(); ++i) {
            BeanQueryAdapter c = this.list.get(i);
            if (!c.isRegisterFor(deployDesc.getBeanType())) continue;
            logger.fine("BeanQueryAdapter on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
            deployDesc.addQueryAdapter(c);
        }
    }
}

