/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.util;

import com.avaje.ebeaninternal.server.lib.util.Dnode;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DnodeParser
extends DefaultHandler {
    Dnode root;
    Dnode currentNode;
    StringBuffer buffer;
    Stack<Dnode> stack = new Stack();
    Class<?> nodeClass = Dnode.class;
    int depth = 0;
    boolean trimWhitespace = true;
    String contentName;
    int contentDepth;

    public boolean isTrimWhitespace() {
        return this.trimWhitespace;
    }

    public void setTrimWhitespace(boolean trimWhitespace) {
        this.trimWhitespace = trimWhitespace;
    }

    public Dnode getRoot() {
        return this.root;
    }

    public void setNodeClass(Class<?> nodeClass) {
        this.nodeClass = nodeClass;
    }

    private Dnode createNewNode() {
        try {
            return (Dnode)this.nodeClass.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        boolean isContent;
        super.startElement(uri, localName, qName, attributes);
        ++this.depth;
        boolean bl = isContent = this.contentName != null;
        if (isContent) {
            this.buffer.append("<").append(localName);
            for (int i = 0; i < attributes.getLength(); ++i) {
                String key = attributes.getLocalName(i);
                String val = attributes.getValue(i);
                this.buffer.append(" ").append(key).append("='").append(val).append("'");
            }
            this.buffer.append(">");
            return;
        }
        this.buffer = new StringBuffer();
        Dnode node = this.createNewNode();
        node.setNodeName(localName);
        for (int i = 0; i < attributes.getLength(); ++i) {
            String key = attributes.getLocalName(i);
            String val = attributes.getValue(i);
            node.setAttribute(key, val);
            if (!"type".equalsIgnoreCase(key) || !"content".equalsIgnoreCase(val)) continue;
            this.contentName = localName;
            this.contentDepth = this.depth - 1;
        }
        if (this.root == null) {
            this.root = node;
        }
        if (this.currentNode != null) {
            this.currentNode.addChild(node);
        }
        this.stack.push(node);
        this.currentNode = node;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String s = new String(ch, start, length);
        int p = s.indexOf(13);
        int p2 = s.indexOf(10);
        if (p == -1 && p2 > -1) {
            s = StringHelper.replaceString(s, "\n", "\r\n");
        }
        this.buffer.append(s);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        --this.depth;
        if (this.contentName != null) {
            if (this.contentName.equals(localName) && this.contentDepth == this.depth) {
                this.contentName = null;
            } else {
                this.buffer.append("</").append(localName).append(">");
            }
            return;
        }
        String content = this.buffer.toString();
        if (content.length() > 0) {
            if (this.trimWhitespace) {
                content = content.trim();
            }
            if (content.length() > 0) {
                this.currentNode.setNodeContent(content);
            }
        }
        this.stack.pop();
        if (!this.stack.isEmpty()) {
            this.currentNode = this.stack.pop();
            this.stack.push(this.currentNode);
        }
    }
}

