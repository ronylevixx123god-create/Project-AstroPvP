/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.composer.ComposerException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.resolver.Resolver;

public class Composer {
    private final Parser parser;
    private final Resolver resolver;
    private final Map<String, Node> anchors;
    private final Set<Node> recursiveNodes;

    public Composer(Parser parser, Resolver resolver) {
        this.parser = parser;
        this.resolver = resolver;
        this.anchors = new HashMap<String, Node>();
        this.recursiveNodes = new HashSet<Node>();
    }

    public boolean checkNode() {
        if (this.parser.checkEvent(Event.ID.StreamStart)) {
            this.parser.getEvent();
        }
        return !this.parser.checkEvent(Event.ID.StreamEnd);
    }

    public Node getNode() {
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            return this.composeDocument();
        }
        return null;
    }

    public Node getSingleNode() {
        this.parser.getEvent();
        Node document = null;
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            document = this.composeDocument();
        }
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            Event event = this.parser.getEvent();
            throw new ComposerException("expected a single document in the stream", document.getStartMark(), "but found another document", event.getStartMark());
        }
        this.parser.getEvent();
        return document;
    }

    private Node composeDocument() {
        this.parser.getEvent();
        Node node = this.composeNode(null, null);
        this.parser.getEvent();
        this.anchors.clear();
        this.recursiveNodes.clear();
        return node;
    }

    private Node composeNode(Node parent, Object index) {
        this.recursiveNodes.add(parent);
        if (this.parser.checkEvent(Event.ID.Alias)) {
            AliasEvent event = (AliasEvent)this.parser.getEvent();
            String anchor = event.getAnchor();
            if (!this.anchors.containsKey(anchor)) {
                throw new ComposerException(null, null, "found undefined alias " + anchor, event.getStartMark());
            }
            Node result = this.anchors.get(anchor);
            if (this.recursiveNodes.remove(result)) {
                result.setTwoStepsConstruction(true);
            }
            return result;
        }
        NodeEvent event = (NodeEvent)this.parser.peekEvent();
        String anchor = null;
        anchor = event.getAnchor();
        if (anchor != null && this.anchors.containsKey(anchor)) {
            throw new ComposerException("found duplicate anchor " + anchor + "; first occurence", this.anchors.get(anchor).getStartMark(), "second occurence", event.getStartMark());
        }
        Node node = null;
        node = this.parser.checkEvent(Event.ID.Scalar) ? this.composeScalarNode(anchor) : (this.parser.checkEvent(Event.ID.SequenceStart) ? this.composeSequenceNode(anchor) : this.composeMappingNode(anchor));
        this.recursiveNodes.remove(parent);
        return node;
    }

    private Node composeScalarNode(String anchor) {
        Tag nodeTag;
        ScalarEvent ev = (ScalarEvent)this.parser.getEvent();
        String tag = ev.getTag();
        boolean resolved = false;
        if (tag == null || tag.equals("!")) {
            nodeTag = this.resolver.resolve(NodeId.scalar, ev.getValue(), ev.getImplicit().canOmitTagInPlainScalar());
            resolved = true;
        } else {
            nodeTag = new Tag(tag);
        }
        ScalarNode node = new ScalarNode(nodeTag, resolved, ev.getValue(), ev.getStartMark(), ev.getEndMark(), ev.getStyle());
        if (anchor != null) {
            this.anchors.put(anchor, node);
        }
        return node;
    }

    private Node composeSequenceNode(String anchor) {
        Tag nodeTag;
        SequenceStartEvent startEvent = (SequenceStartEvent)this.parser.getEvent();
        String tag = startEvent.getTag();
        boolean resolved = false;
        if (tag == null || tag.equals("!")) {
            nodeTag = this.resolver.resolve(NodeId.sequence, null, startEvent.getImplicit());
            resolved = true;
        } else {
            nodeTag = new Tag(tag);
        }
        ArrayList<Node> children = new ArrayList<Node>();
        SequenceNode node = new SequenceNode(nodeTag, resolved, children, startEvent.getStartMark(), null, startEvent.getFlowStyle());
        if (anchor != null) {
            this.anchors.put(anchor, node);
        }
        int index = 0;
        while (!this.parser.checkEvent(Event.ID.SequenceEnd)) {
            children.add(this.composeNode(node, index));
            ++index;
        }
        Event endEvent = this.parser.getEvent();
        node.setEndMark(endEvent.getEndMark());
        return node;
    }

    private Node composeMappingNode(String anchor) {
        Tag nodeTag;
        MappingStartEvent startEvent = (MappingStartEvent)this.parser.getEvent();
        String tag = startEvent.getTag();
        boolean resolved = false;
        if (tag == null || tag.equals("!")) {
            nodeTag = this.resolver.resolve(NodeId.mapping, null, startEvent.getImplicit());
            resolved = true;
        } else {
            nodeTag = new Tag(tag);
        }
        ArrayList<NodeTuple> children = new ArrayList<NodeTuple>();
        MappingNode node = new MappingNode(nodeTag, resolved, children, startEvent.getStartMark(), null, startEvent.getFlowStyle());
        if (anchor != null) {
            this.anchors.put(anchor, node);
        }
        while (!this.parser.checkEvent(Event.ID.MappingEnd)) {
            Node itemKey = this.composeNode(node, null);
            if (itemKey.getTag().equals(Tag.MERGE)) {
                node.setMerged(true);
            } else if (itemKey.getTag().equals(Tag.VALUE)) {
                itemKey.setTag(Tag.STR);
            }
            Node itemValue = this.composeNode(node, itemKey);
            children.add(new NodeTuple(itemKey, itemValue));
        }
        Event endEvent = this.parser.getEvent();
        node.setEndMark(endEvent.getEndMark());
        return node;
    }
}

