/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.id;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderEmbedded;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderEmpty;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderMultiple;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderSimple;

public class IdBinderFactory {
    private static final IdBinderEmpty EMPTY = new IdBinderEmpty();
    private final boolean idInExpandedForm;

    public IdBinderFactory(boolean idInExpandedForm) {
        this.idInExpandedForm = idInExpandedForm;
    }

    public IdBinder createIdBinder(BeanProperty[] uids) {
        if (uids.length == 0) {
            return EMPTY;
        }
        if (uids.length == 1) {
            if (uids[0].isEmbedded()) {
                return new IdBinderEmbedded(this.idInExpandedForm, (BeanPropertyAssocOne)uids[0]);
            }
            return new IdBinderSimple(uids[0]);
        }
        return new IdBinderMultiple(uids);
    }
}

