/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.constructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class BaseConstructor {
    protected final Map<NodeId, Construct> yamlClassConstructors = new EnumMap<NodeId, Construct>(NodeId.class);
    protected final Map<Tag, Construct> yamlConstructors = new HashMap<Tag, Construct>();
    protected final Map<String, Construct> yamlMultiConstructors = new HashMap<String, Construct>();
    private Composer composer;
    private final Map<Node, Object> constructedObjects = new HashMap<Node, Object>();
    private final Set<Node> recursiveObjects = new HashSet<Node>();
    private final ArrayList<RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>>> maps2fill = new ArrayList();
    private final ArrayList<RecursiveTuple<Set<Object>, Object>> sets2fill = new ArrayList();
    protected Tag rootTag = null;
    private PropertyUtils propertyUtils;
    private boolean explicitPropertyUtils = false;

    public void setComposer(Composer composer) {
        this.composer = composer;
    }

    public boolean checkData() {
        return this.composer.checkNode();
    }

    public Object getData() {
        this.composer.checkNode();
        Node node = this.composer.getNode();
        if (this.rootTag != null) {
            node.setTag(this.rootTag);
        }
        return this.constructDocument(node);
    }

    public Object getSingleData(Class<?> type) {
        Node node = this.composer.getSingleNode();
        if (node != null) {
            if (Object.class != type) {
                node.setTag(new Tag(type));
            } else if (this.rootTag != null) {
                node.setTag(this.rootTag);
            }
            return this.constructDocument(node);
        }
        return null;
    }

    private Object constructDocument(Node node) {
        Object data = this.constructObject(node);
        this.fillRecursive();
        this.constructedObjects.clear();
        this.recursiveObjects.clear();
        return data;
    }

    private void fillRecursive() {
        if (!this.maps2fill.isEmpty()) {
            for (RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>> recursiveTuple : this.maps2fill) {
                RecursiveTuple<Object, Object> key_value = recursiveTuple._2();
                recursiveTuple._1().put(key_value._1(), key_value._2());
            }
            this.maps2fill.clear();
        }
        if (!this.sets2fill.isEmpty()) {
            for (RecursiveTuple<Object, Object> recursiveTuple : this.sets2fill) {
                ((Set)recursiveTuple._1()).add(recursiveTuple._2());
            }
            this.sets2fill.clear();
        }
    }

    protected Object constructObject(Node node) {
        if (this.constructedObjects.containsKey(node)) {
            return this.constructedObjects.get(node);
        }
        if (this.recursiveObjects.contains(node)) {
            throw new ConstructorException(null, null, "found unconstructable recursive node", node.getStartMark());
        }
        this.recursiveObjects.add(node);
        Construct constructor = this.getConstructor(node);
        Object data = constructor.construct(node);
        this.constructedObjects.put(node, data);
        this.recursiveObjects.remove(node);
        if (node.isTwoStepsConstruction()) {
            constructor.construct2ndStep(node, data);
        }
        return data;
    }

    protected Construct getConstructor(Node node) {
        if (node.useClassConstructor()) {
            return this.yamlClassConstructors.get((Object)node.getNodeId());
        }
        Construct constructor = this.yamlConstructors.get(node.getTag());
        if (constructor == null) {
            for (String prefix : this.yamlMultiConstructors.keySet()) {
                if (!node.getTag().startsWith(prefix)) continue;
                return this.yamlMultiConstructors.get(prefix);
            }
            return this.yamlConstructors.get(null);
        }
        return constructor;
    }

    protected Object constructScalar(ScalarNode node) {
        return node.getValue();
    }

    protected List<Object> createDefaultList(int initSize) {
        return new ArrayList<Object>(initSize);
    }

    protected Set<Object> createDefaultSet(int initSize) {
        return new LinkedHashSet<Object>(initSize);
    }

    protected <T> T[] createArray(Class<T> type, int size) {
        return (Object[])Array.newInstance(type.getComponentType(), size);
    }

    protected List<? extends Object> constructSequence(SequenceNode node) {
        List result;
        if (List.class.isAssignableFrom(node.getType()) && !node.getType().isInterface()) {
            try {
                result = (List)node.getType().newInstance();
            }
            catch (Exception e) {
                throw new YAMLException(e);
            }
        } else {
            result = this.createDefaultList(node.getValue().size());
        }
        this.constructSequenceStep2(node, result);
        return result;
    }

    protected Set<? extends Object> constructSet(SequenceNode node) {
        Set<Object> result;
        if (!node.getType().isInterface()) {
            try {
                result = (Set<Object>)node.getType().newInstance();
            }
            catch (Exception e) {
                throw new YAMLException(e);
            }
        } else {
            result = this.createDefaultSet(node.getValue().size());
        }
        this.constructSequenceStep2(node, result);
        return result;
    }

    protected Object constructArray(SequenceNode node) {
        return this.constructArrayStep2(node, this.createArray(node.getType(), node.getValue().size()));
    }

    protected void constructSequenceStep2(SequenceNode node, Collection<Object> collection) {
        for (Node child : node.getValue()) {
            collection.add(this.constructObject(child));
        }
    }

    protected Object constructArrayStep2(SequenceNode node, Object array) {
        int index = 0;
        for (Node child : node.getValue()) {
            Array.set(array, index++, this.constructObject(child));
        }
        return array;
    }

    protected Map<Object, Object> createDefaultMap() {
        return new LinkedHashMap<Object, Object>();
    }

    protected Set<Object> createDefaultSet() {
        return new LinkedHashSet<Object>();
    }

    protected Set<Object> constructSet(MappingNode node) {
        Set<Object> set = this.createDefaultSet();
        this.constructSet2ndStep(node, set);
        return set;
    }

    protected Map<Object, Object> constructMapping(MappingNode node) {
        Map<Object, Object> mapping = this.createDefaultMap();
        this.constructMapping2ndStep(node, mapping);
        return mapping;
    }

    protected void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping) {
        List<NodeTuple> nodeValue = node.getValue();
        for (NodeTuple tuple : nodeValue) {
            Node keyNode = tuple.getKeyNode();
            Node valueNode = tuple.getValueNode();
            Object key = this.constructObject(keyNode);
            if (key != null) {
                try {
                    key.hashCode();
                }
                catch (Exception e) {
                    throw new ConstructorException("while constructing a mapping", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
                }
            }
            Object value = this.constructObject(valueNode);
            if (keyNode.isTwoStepsConstruction()) {
                this.maps2fill.add(0, new RecursiveTuple<Map<Object, Object>, RecursiveTuple<Object, Object>>(mapping, new RecursiveTuple<Object, Object>(key, value)));
                continue;
            }
            mapping.put(key, value);
        }
    }

    protected void constructSet2ndStep(MappingNode node, Set<Object> set) {
        List<NodeTuple> nodeValue = node.getValue();
        for (NodeTuple tuple : nodeValue) {
            Node keyNode = tuple.getKeyNode();
            Object key = this.constructObject(keyNode);
            if (key != null) {
                try {
                    key.hashCode();
                }
                catch (Exception e) {
                    throw new ConstructorException("while constructing a Set", node.getStartMark(), "found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
                }
            }
            if (keyNode.isTwoStepsConstruction()) {
                this.sets2fill.add(0, new RecursiveTuple<Set<Object>, Object>(set, key));
                continue;
            }
            set.add(key);
        }
    }

    public void setPropertyUtils(PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        this.explicitPropertyUtils = true;
    }

    public final PropertyUtils getPropertyUtils() {
        if (this.propertyUtils == null) {
            this.propertyUtils = new PropertyUtils();
        }
        return this.propertyUtils;
    }

    public final boolean isExplicitPropertyUtils() {
        return this.explicitPropertyUtils;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class RecursiveTuple<T, K> {
        private final T _1;
        private final K _2;

        public RecursiveTuple(T _1, K _2) {
            this._1 = _1;
            this._2 = _2;
        }

        public K _2() {
            return this._2;
        }

        public T _1() {
            return this._1;
        }
    }
}

