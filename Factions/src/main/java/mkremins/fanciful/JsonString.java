package mkremins.fanciful;

/**
 * Factions-0.1_source_from_Driftay - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 2/7/2020
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

import com.google.gson.stream.JsonWriter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@Immutable
final class JsonString implements JsonRepresentedObject, ConfigurationSerializable {
    private String _value;

    public JsonString(String value) {
        this._value = value;
    }

    public void writeJson(JsonWriter writer) throws IOException {
        writer.value(getValue());
    }

    public String getValue() {
        return this._value;
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> theSingleValue = new HashMap<String, Object>();
        theSingleValue.put("stringValue", this._value);
        return theSingleValue;
    }

    public static JsonString deserialize(Map<String, Object> map) {
        return new JsonString(map.get("stringValue").toString());
    }

    public String toString() {
        return this._value;
    }
}
