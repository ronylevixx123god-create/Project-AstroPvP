package net.astropvp.giftcardshop.data;

import net.astropvp.giftcardshop.GiftcardShopPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadGiftcards {
    public static void load() {

        // Looping through all the files in a directory.
        File directory = new File(GiftcardShopPlugin.getInstance().getDataFolder() + File.separator + "data");
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file == null) { // Null check because those pesky files!
                return;
            }
            // Load the file into YAML because we need to get some values from it!
            YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);
            GiftcardData data = GiftcardShopPlugin.getInstance().getGiftcardData();

            List<String> codes = ymlFile.getStringList("codes");
            UUID uuid = UUID.fromString(ymlFile.getString("uuid"));

            data.getGiftcardDataMap().put(uuid,codes);


            // This is a debug message which can be deleted at a later date!
            System.out.println("[GiftcardsPlugin] Put " + uuid + " into giftcard codes memory");
        }
    }

}

