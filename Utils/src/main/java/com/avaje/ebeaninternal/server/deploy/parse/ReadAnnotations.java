/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationAssocManys;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationAssocOnes;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationClass;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationFields;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationSql;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ReadAnnotations {
    public void readInitial(DeployBeanInfo<?> info) {
        try {
            new AnnotationClass(info).parse();
            new AnnotationFields(info).parse();
        }
        catch (RuntimeException e) {
            String msg = "Error reading annotations for " + info;
            throw new RuntimeException(msg, e);
        }
    }

    public void readAssociations(DeployBeanInfo<?> info, BeanDescriptorManager factory) {
        try {
            new AnnotationAssocOnes(info, factory).parse();
            new AnnotationAssocManys(info, factory).parse();
            new AnnotationSql(info).parse();
        }
        catch (RuntimeException e) {
            String msg = "Error reading annotations for " + info;
            throw new RuntimeException(msg, e);
        }
    }
}

