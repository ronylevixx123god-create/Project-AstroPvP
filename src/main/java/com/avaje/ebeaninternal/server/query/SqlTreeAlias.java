/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.query.SplitName;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SqlTreeAlias {
    private int counter;
    private int manyWhereCounter;
    private TreeSet<String> joinProps = new TreeSet();
    private HashSet<String> embeddedPropertyJoins;
    private TreeSet<String> manyWhereJoinProps = new TreeSet();
    private HashMap<String, String> aliasMap = new HashMap();
    private HashMap<String, String> manyWhereAliasMap = new HashMap();
    private final String rootTableAlias;

    public SqlTreeAlias(String rootTableAlias) {
        this.rootTableAlias = rootTableAlias;
    }

    public void addManyWhereJoins(Set<String> manyWhereJoins) {
        if (manyWhereJoins != null) {
            for (String include : manyWhereJoins) {
                this.addPropertyJoin(include, this.manyWhereJoinProps);
            }
        }
    }

    private void addEmbeddedPropertyJoin(String embProp) {
        if (this.embeddedPropertyJoins == null) {
            this.embeddedPropertyJoins = new HashSet();
        }
        this.embeddedPropertyJoins.add(embProp);
    }

    public void addJoin(Set<String> propJoins, BeanDescriptor<?> desc) {
        if (propJoins != null) {
            for (String propJoin : propJoins) {
                ElPropertyDeploy elProp = desc.getElPropertyDeploy(propJoin);
                if (elProp != null && elProp.getBeanProperty().isEmbedded()) {
                    String[] split = SplitName.split(propJoin);
                    this.addPropertyJoin(split[0], this.joinProps);
                    this.addEmbeddedPropertyJoin(propJoin);
                    continue;
                }
                this.addPropertyJoin(propJoin, this.joinProps);
            }
        }
    }

    private void addPropertyJoin(String include, TreeSet<String> set) {
        String[] split;
        if (set.add(include) && (split = SplitName.split(include))[0] != null) {
            this.addPropertyJoin(split[0], set);
        }
    }

    public void buildAlias() {
        Iterator<String> i = this.joinProps.iterator();
        while (i.hasNext()) {
            this.calcAlias(i.next());
        }
        i = this.manyWhereJoinProps.iterator();
        while (i.hasNext()) {
            this.calcAliasManyWhere(i.next());
        }
        this.mapEmbeddedPropertyAlias();
    }

    private void mapEmbeddedPropertyAlias() {
        if (this.embeddedPropertyJoins != null) {
            for (String propJoin : this.embeddedPropertyJoins) {
                String[] split = SplitName.split(propJoin);
                String alias = this.getTableAlias(split[0]);
                this.aliasMap.put(propJoin, alias);
            }
        }
    }

    private String calcAlias(String prefix) {
        String alias = this.nextTableAlias();
        this.aliasMap.put(prefix, alias);
        return alias;
    }

    private String calcAliasManyWhere(String prefix) {
        String alias = this.nextManyWhereTableAlias();
        this.manyWhereAliasMap.put(prefix, alias);
        return alias;
    }

    public String getTableAlias(String prefix) {
        if (prefix == null) {
            return this.rootTableAlias;
        }
        String s = this.aliasMap.get(prefix);
        if (s == null) {
            return this.calcAlias(prefix);
        }
        return s;
    }

    public String getTableAliasManyWhere(String prefix) {
        if (prefix == null) {
            return this.rootTableAlias;
        }
        String s = this.manyWhereAliasMap.get(prefix);
        if (s == null) {
            s = this.aliasMap.get(prefix);
        }
        if (s == null) {
            String msg = "Could not determine table alias for [" + prefix + "] manyMap[" + this.manyWhereAliasMap + "] aliasMap[" + this.aliasMap + "]";
            throw new RuntimeException(msg);
        }
        return s;
    }

    public String parseWhere(String clause) {
        clause = this.parseRootAlias(clause);
        clause = this.parseAliasMap(clause, this.manyWhereAliasMap);
        return this.parseAliasMap(clause, this.aliasMap);
    }

    public String parse(String clause) {
        clause = this.parseRootAlias(clause);
        return this.parseAliasMap(clause, this.aliasMap);
    }

    private String parseRootAlias(String clause) {
        if (this.rootTableAlias == null) {
            return clause.replace("${}", "");
        }
        return clause.replace("${}", this.rootTableAlias + ".");
    }

    private String parseAliasMap(String clause, HashMap<String, String> parseAliasMap) {
        for (Map.Entry<String, String> e : parseAliasMap.entrySet()) {
            String k = "${" + e.getKey() + "}";
            clause = clause.replace(k, e.getValue() + ".");
        }
        return clause;
    }

    private String nextTableAlias() {
        return "t" + ++this.counter;
    }

    private String nextManyWhereTableAlias() {
        return "u" + ++this.manyWhereCounter;
    }
}

