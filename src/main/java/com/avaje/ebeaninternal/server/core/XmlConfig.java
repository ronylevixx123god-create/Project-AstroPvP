/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.server.lib.util.Dnode;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class XmlConfig {
    private final List<Dnode> ebeanOrmXml;
    private final List<Dnode> ormXml;
    private final List<Dnode> allXml;

    public XmlConfig(List<Dnode> ormXml, List<Dnode> ebeanOrmXml) {
        this.ormXml = ormXml;
        this.ebeanOrmXml = ebeanOrmXml;
        this.allXml = new ArrayList<Dnode>(ormXml.size() + ebeanOrmXml.size());
        this.allXml.addAll(ormXml);
        this.allXml.addAll(ebeanOrmXml);
    }

    public List<Dnode> getEbeanOrmXml() {
        return this.ebeanOrmXml;
    }

    public List<Dnode> getOrmXml() {
        return this.ormXml;
    }

    public List<Dnode> find(List<Dnode> entityXml, String element) {
        ArrayList<Dnode> hits = new ArrayList<Dnode>();
        for (int i = 0; i < entityXml.size(); ++i) {
            hits.addAll(entityXml.get(i).findAll(element, 1));
        }
        return hits;
    }

    public List<Dnode> findEntityXml(String className) {
        ArrayList<Dnode> hits = new ArrayList<Dnode>(2);
        for (Dnode ormXml : this.allXml) {
            Dnode entityMappings = ormXml.find("entity-mappings");
            List<Dnode> entities = entityMappings.findAll("entity", "class", className, 1);
            if (entities.size() != 1) continue;
            hits.add(entities.get(0));
        }
        return hits;
    }
}

