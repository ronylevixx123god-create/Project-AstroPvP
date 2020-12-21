/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.BeanVisitor;
import com.avaje.ebeaninternal.server.ddl.PropertyVisitor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class VisitorUtil {
    public static void visit(SpiEbeanServer server, BeanVisitor visitor) {
        VisitorUtil.visit(server.getBeanDescriptors(), visitor);
    }

    public static void visit(List<BeanDescriptor<?>> descriptors, BeanVisitor visitor) {
        visitor.visitBegin();
        for (BeanDescriptor<?> desc : descriptors) {
            if (desc.getBaseTable() == null) continue;
            VisitorUtil.visitBean(desc, visitor);
        }
        visitor.visitEnd();
    }

    public static void visitBean(BeanDescriptor<?> desc, BeanVisitor visitor) {
        if (visitor.visitBean(desc)) {
            BeanProperty[] propertiesId = desc.propertiesId();
            for (int i = 0; i < propertiesId.length; ++i) {
                VisitorUtil.visit(visitor, propertiesId[i]);
            }
            BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
            if (unidirectional != null) {
                VisitorUtil.visit(visitor, unidirectional);
            }
            BeanProperty[] propertiesNonTransient = desc.propertiesNonTransient();
            for (int i = 0; i < propertiesNonTransient.length; ++i) {
                BeanProperty p = propertiesNonTransient[i];
                if (p.isFormula() || p.isSecondaryTable()) continue;
                VisitorUtil.visit(visitor, p);
            }
            visitor.visitBeanEnd(desc);
        }
    }

    private static void visit(BeanVisitor visitor, BeanProperty p) {
        PropertyVisitor pv = visitor.visitProperty(p);
        if (pv != null) {
            VisitorUtil.visit(p, pv);
        }
    }

    public static void visit(BeanProperty[] p, PropertyVisitor pv) {
        for (int i = 0; i < p.length; ++i) {
            VisitorUtil.visit(p[i], pv);
        }
    }

    public static void visit(BeanProperty p, PropertyVisitor pv) {
        if (p instanceof BeanPropertyAssocMany) {
            pv.visitMany((BeanPropertyAssocMany)p);
        } else if (p instanceof BeanPropertyAssocOne) {
            BeanPropertyAssocOne assocOne = (BeanPropertyAssocOne)p;
            if (assocOne.isEmbedded()) {
                pv.visitEmbedded(assocOne);
                BeanProperty[] embProps = assocOne.getProperties();
                for (int i = 0; i < embProps.length; ++i) {
                    pv.visitEmbeddedScalar(embProps[i], assocOne);
                }
            } else if (assocOne.isOneToOneExported()) {
                pv.visitOneExported(assocOne);
            } else {
                pv.visitOneImported(assocOne);
            }
        } else if (p instanceof BeanPropertyCompound) {
            BeanPropertyCompound compound = (BeanPropertyCompound)p;
            pv.visitCompound(compound);
            BeanProperty[] properties = compound.getScalarProperties();
            for (int i = 0; i < properties.length; ++i) {
                pv.visitCompoundScalar(compound, properties[i]);
            }
        } else {
            pv.visitScalar(p);
        }
    }
}

