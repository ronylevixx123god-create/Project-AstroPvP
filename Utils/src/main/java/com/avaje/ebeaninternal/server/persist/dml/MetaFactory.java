/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.DbEncrypt;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dml.DeleteMeta;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.persist.dml.InsertMeta;
import com.avaje.ebeaninternal.server.persist.dml.UpdateMeta;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableUnidirectional;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryAssocOnes;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryBaseProperties;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryEmbedded;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryId;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryVersion;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MetaFactory {
    private final FactoryBaseProperties baseFact;
    private final FactoryEmbedded embeddedFact;
    private final FactoryVersion versionFact = new FactoryVersion();
    private final FactoryAssocOnes assocOneFact = new FactoryAssocOnes();
    private final FactoryId idFact = new FactoryId();
    private static final boolean includeLobs = true;
    private final DatabasePlatform dbPlatform;
    private final boolean emptyStringAsNull;

    public MetaFactory(DatabasePlatform dbPlatform) {
        this.dbPlatform = dbPlatform;
        this.emptyStringAsNull = dbPlatform.isTreatEmptyStringsAsNull();
        DbEncrypt dbEncrypt = dbPlatform.getDbEncrypt();
        boolean bindEncryptDataFirst = dbEncrypt == null ? true : dbEncrypt.isBindEncryptDataFirst();
        this.baseFact = new FactoryBaseProperties(bindEncryptDataFirst);
        this.embeddedFact = new FactoryEmbedded(bindEncryptDataFirst);
    }

    public UpdateMeta createUpdate(BeanDescriptor<?> desc) {
        ArrayList<Bindable> setList = new ArrayList<Bindable>();
        this.baseFact.create(setList, desc, DmlMode.UPDATE, true);
        this.embeddedFact.create(setList, desc, DmlMode.UPDATE, true);
        this.assocOneFact.create(setList, desc, DmlMode.UPDATE);
        BindableId id = this.idFact.createId(desc);
        Bindable ver = this.versionFact.create(desc);
        ArrayList<Bindable> allList = new ArrayList<Bindable>();
        this.baseFact.create(allList, desc, DmlMode.WHERE, false);
        this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
        this.assocOneFact.create(allList, desc, DmlMode.WHERE);
        BindableList setBindable = new BindableList(setList);
        BindableList allBindable = new BindableList(allList);
        return new UpdateMeta(this.emptyStringAsNull, desc, setBindable, id, ver, allBindable);
    }

    public DeleteMeta createDelete(BeanDescriptor<?> desc) {
        BindableId id = this.idFact.createId(desc);
        Bindable ver = this.versionFact.create(desc);
        ArrayList<Bindable> allList = new ArrayList<Bindable>();
        this.baseFact.create(allList, desc, DmlMode.WHERE, false);
        this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
        this.assocOneFact.create(allList, desc, DmlMode.WHERE);
        BindableList allBindable = new BindableList(allList);
        return new DeleteMeta(this.emptyStringAsNull, desc, id, ver, allBindable);
    }

    public InsertMeta createInsert(BeanDescriptor<?> desc) {
        BindableId id = this.idFact.createId(desc);
        ArrayList<Bindable> allList = new ArrayList<Bindable>();
        this.baseFact.create(allList, desc, DmlMode.INSERT, true);
        this.embeddedFact.create(allList, desc, DmlMode.INSERT, true);
        this.assocOneFact.create(allList, desc, DmlMode.INSERT);
        BindableList allBindable = new BindableList(allList);
        BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
        BindableUnidirectional shadowFkey = unidirectional == null ? null : new BindableUnidirectional(desc, unidirectional);
        return new InsertMeta(this.dbPlatform, desc, shadowFkey, id, allBindable);
    }
}

