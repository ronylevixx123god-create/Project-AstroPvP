/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class MatchedImportedProperty {
    private final BeanPropertyAssocOne<?> assocOne;
    private final BeanProperty foreignProp;
    private final BeanProperty localProp;

    protected MatchedImportedProperty(BeanPropertyAssocOne<?> assocOne, BeanProperty foreignProp, BeanProperty localProp) {
        this.assocOne = assocOne;
        this.foreignProp = foreignProp;
        this.localProp = localProp;
    }

    protected void populate(Object sourceBean, Object destBean) {
        Object assocBean = this.assocOne.getValue(sourceBean);
        if (assocBean == null) {
            String msg = "The assoc bean for " + this.assocOne + " is null?";
            throw new NullPointerException(msg);
        }
        Object value = this.foreignProp.getValue(assocBean);
        this.localProp.setValue(destBean, value);
    }

    protected static MatchedImportedProperty[] build(BeanProperty[] props, BeanDescriptor<?> desc) {
        MatchedImportedProperty[] matches = new MatchedImportedProperty[props.length];
        for (int i = 0; i < props.length; ++i) {
            matches[i] = MatchedImportedProperty.findMatch(props[i], desc);
            if (matches[i] != null) continue;
            return null;
        }
        return matches;
    }

    private static MatchedImportedProperty findMatch(BeanProperty prop, BeanDescriptor<?> desc) {
        String dbColumn = prop.getDbColumn();
        BeanPropertyAssocOne<?>[] assocOnes = desc.propertiesOne();
        for (int i = 0; i < assocOnes.length; ++i) {
            BeanProperty foreignMatch;
            if (!assocOnes[i].isImportedPrimaryKey() || (foreignMatch = assocOnes[i].getImportedId().findMatchImport(dbColumn)) == null) continue;
            return new MatchedImportedProperty(assocOnes[i], foreignMatch, prop);
        }
        return null;
    }
}

