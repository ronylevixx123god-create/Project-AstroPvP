package net.astropvp.giftcardshop.data;


import net.astropvp.giftcardshop.GiftcardShopPlugin;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddGiftcards {

    public static void addNewGiftcard(UUID uuid, String code) {
        File file = new File(GiftcardShopPlugin.getInstance().getDataFolder().toString() + "/data/" + uuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        GiftcardData giftcardData = GiftcardShopPlugin.getInstance().getGiftcardData();
        YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);


        if (!giftcardData.getGiftcardDataMap().containsKey(uuid)) {
            List<String> codes = new ArrayList<>();
            codes.add(code);
            giftcardData.getGiftcardDataMap().put(uuid,codes);
        } else {
            giftcardData.getGiftcardDataMap().get(uuid).add(code);
        }

        List<String> codes = giftcardData.getGiftcardDataMap().get(uuid);
        ymlFile.set("uuid", uuid.toString());
        ymlFile.set("codes", codes);




        try {
            ymlFile.save(file);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
