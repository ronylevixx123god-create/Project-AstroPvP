/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.validation.factory.Validator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.BeanPropertySimpleCollection;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertySimpleCollection;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployBeanPropertyLists {
    private BeanProperty derivedFirstVersionProp;
    private final BeanDescriptor<?> desc;
    private final LinkedHashMap<String, BeanProperty> propertyMap;
    private final ArrayList<BeanProperty> ids = new ArrayList();
    private final ArrayList<BeanProperty> version = new ArrayList();
    private final ArrayList<BeanProperty> local = new ArrayList();
    private final ArrayList<BeanProperty> manys = new ArrayList();
    private final ArrayList<BeanProperty> ones = new ArrayList();
    private final ArrayList<BeanProperty> onesExported = new ArrayList();
    private final ArrayList<BeanProperty> onesImported = new ArrayList();
    private final ArrayList<BeanProperty> embedded = new ArrayList();
    private final ArrayList<BeanProperty> baseScalar = new ArrayList();
    private final ArrayList<BeanPropertyCompound> baseCompound = new ArrayList();
    private final ArrayList<BeanProperty> transients = new ArrayList();
    private final ArrayList<BeanProperty> nonTransients = new ArrayList();
    private final TableJoin[] tableJoins;
    private final BeanPropertyAssocOne<?> unidirectional;

    public DeployBeanPropertyLists(BeanDescriptorMap owner, BeanDescriptor<?> desc, DeployBeanDescriptor<?> deploy) {
        this.desc = desc;
        DeployBeanPropertyAssocOne<?> deployUnidirectional = deploy.getUnidirectional();
        this.unidirectional = deployUnidirectional == null ? null : new BeanPropertyAssocOne(owner, desc, deployUnidirectional);
        this.propertyMap = new LinkedHashMap();
        Iterator<DeployBeanProperty> deployIt = deploy.propertiesAll();
        while (deployIt.hasNext()) {
            DeployBeanProperty deployProp = deployIt.next();
            BeanProperty beanProp = this.createBeanProperty(owner, deployProp);
            this.propertyMap.put(beanProp.getName(), beanProp);
        }
        Iterator<BeanProperty> it = this.propertyMap.values().iterator();
        int order = 0;
        while (it.hasNext()) {
            BeanProperty prop = it.next();
            prop.setDeployOrder(order++);
            this.allocateToList(prop);
        }
        List<DeployTableJoin> deployTableJoins = deploy.getTableJoins();
        this.tableJoins = new TableJoin[deployTableJoins.size()];
        for (int i = 0; i < deployTableJoins.size(); ++i) {
            this.tableJoins[i] = new TableJoin(deployTableJoins.get(i), this.propertyMap);
        }
    }

    public BeanPropertyAssocOne<?> getUnidirectional() {
        return this.unidirectional;
    }

    private void allocateToList(BeanProperty prop) {
        if (prop.isTransient()) {
            this.transients.add(prop);
            return;
        }
        if (prop.isId()) {
            this.ids.add(prop);
            return;
        }
        this.nonTransients.add(prop);
        if (this.desc.getInheritInfo() != null && prop.isLocal()) {
            this.local.add(prop);
        }
        if (prop instanceof BeanPropertyAssocMany) {
            this.manys.add(prop);
        } else if (prop instanceof BeanPropertyAssocOne) {
            if (prop.isEmbedded()) {
                this.embedded.add(prop);
            } else {
                this.ones.add(prop);
                BeanPropertyAssocOne assocOne = (BeanPropertyAssocOne)prop;
                if (assocOne.isOneToOneExported()) {
                    this.onesExported.add(prop);
                } else {
                    this.onesImported.add(prop);
                }
            }
        } else {
            if (prop.isVersion()) {
                this.version.add(prop);
                if (this.derivedFirstVersionProp == null) {
                    this.derivedFirstVersionProp = prop;
                }
            }
            if (prop instanceof BeanPropertyCompound) {
                this.baseCompound.add((BeanPropertyCompound)prop);
            } else {
                this.baseScalar.add(prop);
            }
        }
    }

    public BeanProperty getFirstVersion() {
        return this.derivedFirstVersionProp;
    }

    public BeanProperty[] getPropertiesWithValidators(boolean recurse) {
        ArrayList<BeanProperty> list = new ArrayList<BeanProperty>();
        for (BeanProperty property : this.propertyMap.values()) {
            if (!property.hasValidationRules(recurse)) continue;
            list.add(property);
        }
        return list.toArray(new BeanProperty[list.size()]);
    }

    public Validator[] getBeanValidators() {
        return new Validator[0];
    }

    public LinkedHashMap<String, BeanProperty> getPropertyMap() {
        return this.propertyMap;
    }

    public TableJoin[] getTableJoin() {
        return this.tableJoins;
    }

    public BeanProperty[] getBaseScalar() {
        return this.baseScalar.toArray(new BeanProperty[this.baseScalar.size()]);
    }

    public BeanPropertyCompound[] getBaseCompound() {
        return this.baseCompound.toArray(new BeanPropertyCompound[this.baseCompound.size()]);
    }

    public BeanProperty[] getId() {
        return this.ids.toArray(new BeanProperty[this.ids.size()]);
    }

    public BeanProperty[] getNonTransients() {
        return this.nonTransients.toArray(new BeanProperty[this.nonTransients.size()]);
    }

    public BeanProperty[] getTransients() {
        return this.transients.toArray(new BeanProperty[this.transients.size()]);
    }

    public BeanProperty[] getVersion() {
        return this.version.toArray(new BeanProperty[this.version.size()]);
    }

    public BeanProperty[] getLocal() {
        return this.local.toArray(new BeanProperty[this.local.size()]);
    }

    public BeanPropertyAssocOne<?>[] getEmbedded() {
        return this.embedded.toArray(new BeanPropertyAssocOne[this.embedded.size()]);
    }

    public BeanPropertyAssocOne<?>[] getOneExported() {
        return this.onesExported.toArray(new BeanPropertyAssocOne[this.onesExported.size()]);
    }

    public BeanPropertyAssocOne<?>[] getOneImported() {
        return this.onesImported.toArray(new BeanPropertyAssocOne[this.onesImported.size()]);
    }

    public BeanPropertyAssocOne<?>[] getOnes() {
        return this.ones.toArray(new BeanPropertyAssocOne[this.ones.size()]);
    }

    public BeanPropertyAssocOne<?>[] getOneExportedSave() {
        return this.getOne(false, Mode.Save);
    }

    public BeanPropertyAssocOne<?>[] getOneExportedDelete() {
        return this.getOne(false, Mode.Delete);
    }

    public BeanPropertyAssocOne<?>[] getOneImportedSave() {
        return this.getOne(true, Mode.Save);
    }

    public BeanPropertyAssocOne<?>[] getOneImportedDelete() {
        return this.getOne(true, Mode.Delete);
    }

    public BeanPropertyAssocMany<?>[] getMany() {
        return this.manys.toArray(new BeanPropertyAssocMany[this.manys.size()]);
    }

    public BeanPropertyAssocMany<?>[] getManySave() {
        return this.getMany(Mode.Save);
    }

    public BeanPropertyAssocMany<?>[] getManyDelete() {
        return this.getMany(Mode.Delete);
    }

    public BeanPropertyAssocMany<?>[] getManyToMany() {
        return this.getMany2Many();
    }

    private BeanPropertyAssocOne<?>[] getOne(boolean imported, Mode mode) {
        ArrayList<BeanPropertyAssocOne> list = new ArrayList<BeanPropertyAssocOne>();
        block5: for (int i = 0; i < this.ones.size(); ++i) {
            BeanPropertyAssocOne prop = (BeanPropertyAssocOne)this.ones.get(i);
            if (imported == prop.isOneToOneExported()) continue;
            switch (mode) {
                case Save: {
                    if (!prop.getCascadeInfo().isSave()) continue block5;
                    list.add(prop);
                    continue block5;
                }
                case Delete: {
                    if (!prop.getCascadeInfo().isDelete()) continue block5;
                    list.add(prop);
                    continue block5;
                }
                case Validate: {
                    if (!prop.getCascadeInfo().isValidate()) continue block5;
                    list.add(prop);
                    continue block5;
                }
            }
        }
        return list.toArray(new BeanPropertyAssocOne[list.size()]);
    }

    private BeanPropertyAssocMany<?>[] getMany2Many() {
        ArrayList<BeanPropertyAssocMany> list = new ArrayList<BeanPropertyAssocMany>();
        for (int i = 0; i < this.manys.size(); ++i) {
            BeanPropertyAssocMany prop = (BeanPropertyAssocMany)this.manys.get(i);
            if (!prop.isManyToMany()) continue;
            list.add(prop);
        }
        return list.toArray(new BeanPropertyAssocMany[list.size()]);
    }

    private BeanPropertyAssocMany<?>[] getMany(Mode mode) {
        ArrayList<BeanPropertyAssocMany> list = new ArrayList<BeanPropertyAssocMany>();
        block5: for (int i = 0; i < this.manys.size(); ++i) {
            BeanPropertyAssocMany prop = (BeanPropertyAssocMany)this.manys.get(i);
            switch (mode) {
                case Save: {
                    if (!prop.getCascadeInfo().isSave() && !prop.isManyToMany() && !BeanCollection.ModifyListenMode.REMOVALS.equals((Object)prop.getModifyListenMode())) continue block5;
                    list.add(prop);
                    continue block5;
                }
                case Delete: {
                    if (!prop.getCascadeInfo().isDelete() && !BeanCollection.ModifyListenMode.REMOVALS.equals((Object)prop.getModifyListenMode())) continue block5;
                    list.add(prop);
                    continue block5;
                }
                case Validate: {
                    if (!prop.getCascadeInfo().isValidate()) continue block5;
                    list.add(prop);
                    continue block5;
                }
            }
        }
        return list.toArray(new BeanPropertyAssocMany[list.size()]);
    }

    private BeanProperty createBeanProperty(BeanDescriptorMap owner, DeployBeanProperty deployProp) {
        if (deployProp instanceof DeployBeanPropertyAssocOne) {
            return new BeanPropertyAssocOne(owner, this.desc, (DeployBeanPropertyAssocOne)deployProp);
        }
        if (deployProp instanceof DeployBeanPropertySimpleCollection) {
            return new BeanPropertySimpleCollection(owner, this.desc, (DeployBeanPropertySimpleCollection)deployProp);
        }
        if (deployProp instanceof DeployBeanPropertyAssocMany) {
            return new BeanPropertyAssocMany(owner, this.desc, (DeployBeanPropertyAssocMany)deployProp);
        }
        if (deployProp instanceof DeployBeanPropertyCompound) {
            return new BeanPropertyCompound(owner, this.desc, (DeployBeanPropertyCompound)deployProp);
        }
        return new BeanProperty(owner, this.desc, deployProp);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static enum Mode {
        Save,
        Delete,
        Validate;

    }
}

