/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import java.util.List;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PersistControllerManager {
    private static final Logger logger = Logger.getLogger(PersistControllerManager.class.getName());
    private final List<BeanPersistController> list;

    public PersistControllerManager(BootupClasses bootupClasses) {
        this.list = bootupClasses.getBeanPersistControllers();
    }

    public int getRegisterCount() {
        return this.list.size();
    }

    public void addPersistControllers(DeployBeanDescriptor<?> deployDesc) {
        for (int i = 0; i < this.list.size(); ++i) {
            BeanPersistController c = this.list.get(i);
            if (!c.isRegisterFor(deployDesc.getBeanType())) continue;
            logger.fine("BeanPersistController on[" + deployDesc.getFullName() + "] " + c.getClass().getName());
            deployDesc.addPersistController(c);
        }
    }
}

