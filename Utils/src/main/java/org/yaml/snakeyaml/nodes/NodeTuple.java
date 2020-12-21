/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.nodes.Node;

public final class NodeTuple {
    private final Node keyNode;
    private final Node valueNode;

    public NodeTuple(Node keyNode, Node valueNode) {
        if (keyNode == null || valueNode == null) {
            throw new NullPointerException("Nodes must be provided.");
        }
        this.keyNode = keyNode;
        this.valueNode = valueNode;
    }

    public final Node getKeyNode() {
        return this.keyNode;
    }

    public final Node getValueNode() {
        return this.valueNode;
    }

    public String toString() {
        return "<NodeTuple keyNode=" + this.keyNode.toString() + "; valueNode=" + this.valueNode.toString() + ">";
    }
}

