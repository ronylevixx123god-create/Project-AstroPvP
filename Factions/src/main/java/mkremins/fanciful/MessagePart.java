package mkremins.fanciful;

/**
 * Factions-0.1_source_from_Driftay - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 2/7/2020
 */
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;

final class MessagePart implements JsonRepresentedObject, ConfigurationSerializable, Cloneable {
    ChatColor color = ChatColor.WHITE;

    ArrayList<ChatColor> styles = new ArrayList<ChatColor>();

    String clickActionName = null, clickActionData = null;

    String hoverActionName = null;

    JsonRepresentedObject hoverActionData = null;

    TextualComponent text = null;

    static final BiMap<ChatColor, String> stylesToNames;

    MessagePart(TextualComponent text) {
        this.text = text;
    }

    MessagePart() {
        this.text = null;
    }

    boolean hasText() {
        return (this.text != null);
    }

    public MessagePart clone() throws CloneNotSupportedException {
        MessagePart obj = (MessagePart)super.clone();
        obj.styles = (ArrayList<ChatColor>)this.styles.clone();
        if (this.hoverActionData instanceof JsonString) {
            obj.hoverActionData = new JsonString(((JsonString)this.hoverActionData).getValue());
        } else if (this.hoverActionData instanceof FancyMessage) {
            obj.hoverActionData = ((FancyMessage)this.hoverActionData).clone();
        }
        return obj;
    }

    static {
        ImmutableBiMap.Builder<ChatColor, String> builder = ImmutableBiMap.builder();
        byte b;
        int i;
        ChatColor[] arrayOfChatColor;
        for (i = (arrayOfChatColor = ChatColor.values()).length, b = 0; b < i; ) {
            ChatColor style = arrayOfChatColor[b];
            if (style.isFormat()) {
                String styleName;
                switch (style) {
                    case MAGIC:
                        styleName = "obfuscated";
                        break;
                    case UNDERLINE:
                        styleName = "underlined";
                        break;
                    default:
                        styleName = style.name().toLowerCase();
                        break;
                }
                builder.put(style, styleName);
            }
            b++;
        }
        stylesToNames = (BiMap<ChatColor, String>)builder.build();
        ConfigurationSerialization.registerClass(MessagePart.class);
    }

    public void writeJson(JsonWriter json) {
        try {
            json.beginObject();
            this.text.writeJson(json);
            json.name("color").value(this.color.name().toLowerCase());
            for (ChatColor style : this.styles)
                json.name((String)stylesToNames.get(style)).value(true);
            if (this.clickActionName != null && this.clickActionData != null)
                json.name("clickEvent").beginObject().name("action").value(this.clickActionName).name("value").value(this.clickActionData).endObject();
            if (this.hoverActionName != null && this.hoverActionData != null) {
                json.name("hoverEvent").beginObject().name("action").value(this.hoverActionName).name("value");
                this.hoverActionData.writeJson(json);
                json.endObject();
            }
            json.endObject();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "A problem occured during writing of JSON string", e);
        }
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("text", this.text);
        map.put("styles", this.styles);
        map.put("color", Character.valueOf(this.color.getChar()));
        map.put("hoverActionName", this.hoverActionName);
        map.put("hoverActionData", this.hoverActionData);
        map.put("clickActionName", this.clickActionName);
        map.put("clickActionData", this.clickActionData);
        return map;
    }

    public static MessagePart deserialize(Map<String, Object> serialized) {
        MessagePart part = new MessagePart((TextualComponent)serialized.get("text"));
        part.styles = (ArrayList<ChatColor>)serialized.get("styles");
        part.color = ChatColor.getByChar(serialized.get("color").toString());
        part.hoverActionName = serialized.get("hoverActionName").toString();
        part.hoverActionData = (JsonRepresentedObject)serialized.get("hoverActionData");
        part.clickActionName = serialized.get("clickActionName").toString();
        part.clickActionData = serialized.get("clickActionData").toString();
        return part;
    }
}
