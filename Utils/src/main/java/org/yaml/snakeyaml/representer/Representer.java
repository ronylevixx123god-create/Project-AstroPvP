/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.representer;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.SafeRepresenter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Representer
extends SafeRepresenter {
    public Representer() {
        this.representers.put(null, new RepresentJavaBean());
    }

    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        ArrayList<NodeTuple> value = new ArrayList<NodeTuple>(properties.size());
        Tag customTag = (Tag)this.classTags.get(javaBean.getClass());
        Tag tag = customTag != null ? customTag : new Tag(javaBean.getClass());
        MappingNode node = new MappingNode(tag, value, null);
        this.representedObjects.put(javaBean, node);
        boolean bestStyle = true;
        Iterator<Property> i$ = properties.iterator();
        while (i$.hasNext()) {
            Node nodeValue;
            Property property;
            Object memberValue;
            Tag customPropertyTag = (memberValue = (property = i$.next()).get(javaBean)) == null ? null : (Tag)this.classTags.get(memberValue.getClass());
            NodeTuple tuple = this.representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
            if (tuple == null) continue;
            if (((ScalarNode)tuple.getKeyNode()).getStyle() != null) {
                bestStyle = false;
            }
            if (!((nodeValue = tuple.getValueNode()) instanceof ScalarNode) || ((ScalarNode)nodeValue).getStyle() != null) {
                bestStyle = false;
            }
            value.add(tuple);
        }
        if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            node.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
        } else {
            node.setFlowStyle(bestStyle);
        }
        return node;
    }

    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        ScalarNode nodeKey = (ScalarNode)this.representData(property.getName());
        boolean hasAlias = this.representedObjects.containsKey(propertyValue);
        Node nodeValue = this.representData(propertyValue);
        if (propertyValue != null && !hasAlias) {
            NodeId nodeId = nodeValue.getNodeId();
            if (customTag == null) {
                if (nodeId == NodeId.scalar) {
                    if (propertyValue instanceof Enum) {
                        nodeValue.setTag(Tag.STR);
                    }
                } else {
                    if (nodeId == NodeId.mapping && property.getType() == propertyValue.getClass() && !(propertyValue instanceof Map) && !nodeValue.getTag().equals(Tag.SET)) {
                        nodeValue.setTag(Tag.MAP);
                    }
                    this.checkGlobalTag(property, nodeValue, propertyValue);
                }
            }
        }
        return new NodeTuple(nodeKey, nodeValue);
    }

    protected void checkGlobalTag(Property property, Node node, Object object) {
        block3: {
            Class<?>[] arguments;
            block5: {
                block4: {
                    arguments = property.getActualTypeArguments();
                    if (arguments == null) break block3;
                    if (node.getNodeId() != NodeId.sequence) break block4;
                    Class<?> t = arguments[0];
                    SequenceNode snode = (SequenceNode)node;
                    List<Object> memberList = object.getClass().isArray() ? Arrays.asList((Object[])object) : (List<Object>)object;
                    Iterator iter = memberList.iterator();
                    for (Node childNode : snode.getValue()) {
                        Object member = iter.next();
                        if (member == null || !t.equals(member.getClass()) || childNode.getNodeId() != NodeId.mapping) continue;
                        childNode.setTag(Tag.MAP);
                    }
                    break block3;
                }
                if (!(object instanceof Set)) break block5;
                Class<?> t = arguments[0];
                MappingNode mnode = (MappingNode)node;
                Iterator<NodeTuple> iter = mnode.getValue().iterator();
                Set set = (Set)object;
                for (Object member : set) {
                    NodeTuple tuple = iter.next();
                    Node keyNode = tuple.getKeyNode();
                    if (!t.equals(member.getClass()) || keyNode.getNodeId() != NodeId.mapping) continue;
                    keyNode.setTag(Tag.MAP);
                }
                break block3;
            }
            if (!(object instanceof Map)) break block3;
            Class<?> keyType = arguments[0];
            Class<?> valueType = arguments[1];
            MappingNode mnode = (MappingNode)node;
            for (NodeTuple tuple : mnode.getValue()) {
                this.resetTag(keyType, tuple.getKeyNode());
                this.resetTag(valueType, tuple.getValueNode());
            }
        }
    }

    private void resetTag(Class<? extends Object> type, Node node) {
        Tag tag = node.getTag();
        if (tag.matches(type)) {
            if (Enum.class.isAssignableFrom(type)) {
                node.setTag(Tag.STR);
            } else {
                node.setTag(Tag.MAP);
            }
        }
    }

    protected Set<Property> getProperties(Class<? extends Object> type) throws IntrospectionException {
        return this.getPropertyUtils().getProperties(type);
    }

    protected class RepresentJavaBean
    implements Represent {
        protected RepresentJavaBean() {
        }

        public Node representData(Object data) {
            try {
                return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
            }
            catch (IntrospectionException e) {
                throw new YAMLException(e);
            }
        }
    }
}

