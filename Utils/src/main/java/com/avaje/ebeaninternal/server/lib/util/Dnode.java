/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Dnode {
    int level = 0;
    String nodeName;
    String nodeContent;
    ArrayList<Dnode> children;
    LinkedHashMap<String, String> attrList = new LinkedHashMap();

    public String toXml() {
        StringBuilder sb = new StringBuilder();
        this.generate(sb);
        return sb.toString();
    }

    public StringBuilder generate(StringBuilder sb) {
        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append("<").append(this.nodeName);
        Iterator<String> it = this.attributeNames();
        while (it.hasNext()) {
            String attr = it.next();
            String attrValue = this.getAttribute(attr);
            sb.append(" ").append(attr).append("=\"");
            if (attrValue != null) {
                sb.append((Object)attrValue);
            }
            sb.append("\"");
        }
        if (this.nodeContent == null && !this.hasChildren()) {
            sb.append(" />");
        } else {
            sb.append(">");
            if (this.children != null && this.children.size() > 0) {
                for (int i = 0; i < this.children.size(); ++i) {
                    Dnode child = this.children.get(i);
                    child.generate(sb);
                }
            }
            if (this.nodeContent != null) {
                sb.append(this.nodeContent);
            }
            sb.append("</").append(this.nodeName).append(">");
        }
        return sb;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeContent() {
        return this.nodeContent;
    }

    public void setNodeContent(String nodeContent) {
        this.nodeContent = nodeContent;
    }

    public boolean hasChildren() {
        return this.getChildrenCount() > 0;
    }

    public int getChildrenCount() {
        if (this.children == null) {
            return 0;
        }
        return this.children.size();
    }

    public boolean remove(Dnode node) {
        if (this.children == null) {
            return false;
        }
        if (this.children.remove(node)) {
            return true;
        }
        for (Dnode child : this.children) {
            if (!child.remove(node)) continue;
            return true;
        }
        return false;
    }

    public List<Dnode> children() {
        if (this.children == null) {
            return null;
        }
        return this.children;
    }

    public void addChild(Dnode child) {
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(child);
        child.setLevel(this.level + 1);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
        if (this.children != null) {
            for (int i = 0; i < this.children.size(); ++i) {
                Dnode child = this.children.get(i);
                child.setLevel(level + 1);
            }
        }
    }

    public Dnode find(String nodeName) {
        return this.find(nodeName, null, null);
    }

    public Dnode find(String nodeName, String attrName, Object value) {
        return this.find(nodeName, attrName, value, -1);
    }

    public Dnode find(String nodeName, String attrName, Object value, int maxLevel) {
        ArrayList<Dnode> list = new ArrayList<Dnode>();
        this.findByNode(list, nodeName, true, attrName, value, maxLevel);
        if (list.size() >= 1) {
            return list.get(0);
        }
        return null;
    }

    public List<Dnode> findAll(String nodeName, int maxLevel) {
        int level = -1;
        if (maxLevel > 0) {
            level = this.level + maxLevel;
        }
        return this.findAll(nodeName, null, null, level);
    }

    public List<Dnode> findAll(String nodeName, String attrName, Object value, int maxLevel) {
        if (nodeName == null && attrName == null) {
            throw new RuntimeException("You can not have both nodeName and attrName null");
        }
        ArrayList<Dnode> list = new ArrayList<Dnode>();
        this.findByNode(list, nodeName, false, attrName, value, maxLevel);
        return list;
    }

    private void findByNode(List<Dnode> list, String node, boolean findOne, String attrName, Object value, int maxLevel) {
        if (findOne && list.size() == 1) {
            return;
        }
        if ((node == null || node.equals(this.nodeName)) && (attrName == null || value.equals(this.getAttribute(attrName)))) {
            list.add(this);
            if (findOne) {
                return;
            }
        }
        if ((maxLevel <= 0 || this.level < maxLevel) && this.children != null) {
            for (int i = 0; i < this.children.size(); ++i) {
                Dnode child = this.children.get(i);
                child.findByNode(list, node, findOne, attrName, value, maxLevel);
            }
        }
    }

    public Iterator<String> attributeNames() {
        return this.attrList.keySet().iterator();
    }

    public String getAttribute(String name) {
        return this.attrList.get(name);
    }

    public String getStringAttr(String name, String defaultValue) {
        String o = this.attrList.get(name);
        if (o == null) {
            return defaultValue;
        }
        return o.toString();
    }

    public void setAttribute(String name, String value) {
        this.attrList.put(name, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(this.getNodeName()).append(" ").append(this.attrList).append("]");
        return sb.toString();
    }
}

