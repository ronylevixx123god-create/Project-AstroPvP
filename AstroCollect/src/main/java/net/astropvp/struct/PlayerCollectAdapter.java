package net.astropvp.struct;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.astropvp.utils.Serialization;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerCollectAdapter extends TypeAdapter<PlayerCollect> {


    @Override
    public void write(JsonWriter jsonWriter, PlayerCollect playerCollect) {
        try {
            jsonWriter.beginObject();
            jsonWriter.name("uuid").value(playerCollect.getKey());
            jsonWriter.name("categories");
            jsonWriter.beginArray();
            for (CollectCategory category : playerCollect.getCategories()) {
                jsonWriter.beginObject();
                jsonWriter.name("type").value(category.getType().name());
                jsonWriter.name("name").value(category.getName());
                jsonWriter.name("time").value(category.getTimeCollected());
                jsonWriter.name("items").value(Serialization.serialize(category.getItems()));
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            jsonWriter.flush();
            jsonWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerCollect read(JsonReader jsonReader) {
        try {
            jsonReader.beginObject();
            UUID uuid = UUID.fromString(jsonReader.nextString());
            jsonReader.beginArray();
            List<CollectCategory> categories = new ArrayList<>();
            while (jsonReader.hasNext()) {
                CollectType type = CollectType.valueOf(jsonReader.nextString());
                String name = jsonReader.nextString();
                long time = jsonReader.nextLong();
                ItemStack[] items = Serialization.deserialize(jsonReader.nextString());
                categories.add(new CollectCategory(type, items, name, time));
            }
            jsonReader.endArray();
            PlayerCollect playerCollect = new PlayerCollect(uuid);
            playerCollect.add(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
