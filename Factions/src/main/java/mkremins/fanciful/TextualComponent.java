package mkremins.fanciful;

/**
 * Factions-0.1_source_from_Driftay - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 2/7/2020
 */

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class TextualComponent implements Cloneable {
    static {
        ConfigurationSerialization.registerClass(TextualComponent.ArbitraryTextTypeComponent.class);
        ConfigurationSerialization.registerClass(TextualComponent.ComplexTextTypeComponent.class);
    }

    public TextualComponent() {
    }

    public String toString() {
        return this.getReadableString();
    }

    public abstract String getKey();

    public abstract String getReadableString();

    public abstract TextualComponent clone() throws CloneNotSupportedException;

    public abstract void writeJson(JsonWriter var1) throws IOException;

    static TextualComponent deserialize(Map<String, Object> map) {
        if (map.containsKey("key") && map.size() == 2 && map.containsKey("value")) {
            return TextualComponent.ArbitraryTextTypeComponent.deserialize(map);
        } else {
            return map.size() >= 2 && map.containsKey("key") && !map.containsKey("value") ? TextualComponent.ComplexTextTypeComponent.deserialize(map) : null;
        }
    }

    static boolean isTextKey(String key) {
        return key.equals("translate") || key.equals("text") || key.equals("score") || key.equals("selector");
    }

    public static TextualComponent rawText(String textValue) {
        return new TextualComponent.ArbitraryTextTypeComponent("text", textValue);
    }

    public static TextualComponent localizedText(String translateKey) {
        return new TextualComponent.ArbitraryTextTypeComponent("translate", translateKey);
    }

    private static void throwUnsupportedSnapshot() {
        throw new UnsupportedOperationException("This feature is only supported in snapshot releases.");
    }

    public static TextualComponent objectiveScore(final String scoreboardObjective) {
        return objectiveScore("*", scoreboardObjective);
    }

    public static ComplexTextTypeComponent objectiveScore(final String playerName, final String scoreboardObjective) {
        throwUnsupportedSnapshot();
        return new ComplexTextTypeComponent("score", ImmutableMap.<String, String>builder().put("name", playerName).put("objective", scoreboardObjective).build());
    }

    public static TextualComponent selector(String selector) {
        throwUnsupportedSnapshot();
        return new TextualComponent.ArbitraryTextTypeComponent("selector", selector);
    }

    private static final class ArbitraryTextTypeComponent extends TextualComponent implements ConfigurationSerializable {
        private String _key;
        private String _value;

        public ArbitraryTextTypeComponent(String key, String value) {
            this.setKey(key);
            this.setValue(value);
        }

        public String getKey() {
            return this._key;
        }

        public void setKey(String key) {
            Preconditions.checkArgument(key != null && !key.isEmpty(), "The key must be specified.");
            this._key = key;
        }

        public String getValue() {
            return this._value;
        }

        public void setValue(String value) {
            Preconditions.checkArgument(value != null, "The value must be specified.");
            this._value = value;
        }

        public TextualComponent clone() throws CloneNotSupportedException {
            return new TextualComponent.ArbitraryTextTypeComponent(this.getKey(), this.getValue());
        }

        public void writeJson(JsonWriter writer) throws IOException {
            writer.name(this.getKey()).value(this.getValue());
        }

        public Map<String, Object> serialize() {
            return new HashMap<String, Object>() {
                {
                    this.put("key", ArbitraryTextTypeComponent.this.getKey());
                    this.put("value", ArbitraryTextTypeComponent.this.getValue());
                }
            };
        }

        public static TextualComponent.ArbitraryTextTypeComponent deserialize(Map<String, Object> map) {
            return new TextualComponent.ArbitraryTextTypeComponent(map.get("key").toString(), map.get("value").toString());
        }

        public String getReadableString() {
            return this.getValue();
        }
    }

    private static final class ComplexTextTypeComponent extends TextualComponent implements ConfigurationSerializable {
        private String _key;
        private Map<String, String> _value;

        public ComplexTextTypeComponent(String key, Map<String, String> values) {
            this.setKey(key);
            this.setValue(values);
        }


        public String getKey() {
            return this._key;
        }

        public void setKey(String key) {
            Preconditions.checkArgument(key != null && !key.isEmpty(), "The key must be specified.");
            this._key = key;
        }

        public Map<String, String> getValue() {
            return this._value;
        }

        public void setValue(Map<String, String> value) {
            Preconditions.checkArgument(value != null, "The value must be specified.");
            this._value = value;
        }

        public TextualComponent clone() throws CloneNotSupportedException {
            return new TextualComponent.ComplexTextTypeComponent(this.getKey(), this.getValue());
        }

        public void writeJson(JsonWriter writer) throws IOException {
            writer.name(this.getKey());
            writer.beginObject();

            for (Map.Entry<String, String> stringStringEntry : this._value.entrySet()) {
                writer.name(stringStringEntry.getKey()).value(stringStringEntry.getValue());
            }

            writer.endObject();
        }

        public Map<String, Object> serialize() {
            return new HashMap<String, Object>() {
                {
                    this.put("key", ComplexTextTypeComponent.this.getKey());

                    for (Entry<String, String> stringStringEntry : ComplexTextTypeComponent.this.getValue().entrySet()) {
                        this.put("value." + stringStringEntry.getKey(), stringStringEntry.getValue());
                    }

                }
            };
        }

        public static TextualComponent.ComplexTextTypeComponent deserialize(Map<String, Object> map) {
            String key = null;
            Map<String, String> value = new HashMap<>();

            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                if (stringObjectEntry.getKey().equals("key")) {
                    key = (String) stringObjectEntry.getValue();
                } else if (stringObjectEntry.getKey().startsWith("value.")) {
                    value.put(stringObjectEntry.getKey().substring(6), stringObjectEntry.getValue().toString());
                }
            }

            return new TextualComponent.ComplexTextTypeComponent(key, value);
        }

        public String getReadableString() {
            return this.getKey();
        }
    }
}