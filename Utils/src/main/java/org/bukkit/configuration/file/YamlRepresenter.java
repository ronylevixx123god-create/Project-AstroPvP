/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.configuration.file;

import java.util.LinkedHashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.representer.SafeRepresenter;

public class YamlRepresenter
extends Representer {
    public YamlRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
    }

    private class RepresentConfigurationSerializable
    extends SafeRepresenter.RepresentMap {
        private RepresentConfigurationSerializable() {
            super(YamlRepresenter.this);
        }

        @Override
        public Node representData(Object data) {
            ConfigurationSerializable serializable = (ConfigurationSerializable)data;
            LinkedHashMap<String, Object> values = new LinkedHashMap<String, Object>();
            values.put("==", ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serializable.serialize());
            return super.representData(values);
        }
    }

    private class RepresentConfigurationSection
    extends SafeRepresenter.RepresentMap {
        private RepresentConfigurationSection() {
            super(YamlRepresenter.this);
        }

        @Override
        public Node representData(Object data) {
            return super.representData(((ConfigurationSection)data).getValues(false));
        }
    }
}

