package net.astropvp.giftcardshop;

import lombok.Getter;
import net.astropvp.giftcardshop.commands.CreateGiftcardCommand;
import net.astropvp.giftcardshop.commands.GiftcardsCommand;
import net.astropvp.giftcardshop.data.GiftcardData;
import net.astropvp.giftcardshop.data.LoadGiftcards;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
@Getter
public class GiftcardShopPlugin extends JavaPlugin {
    private static GiftcardShopPlugin instance;
    private GiftcardData giftcardData;


    @Override
    public void onEnable() {
        instance = this;
        getCommand("creategiftcard").setExecutor(new CreateGiftcardCommand());
        getCommand("giftcards").setExecutor(new GiftcardsCommand());

        this.giftcardData = new GiftcardData();
        File file = new File(this.getDataFolder() + File.separator + "data");
        if(!file.exists()) {
            file.mkdir();
        }
        LoadGiftcards.load();
        System.out.println("[GiftcardPlugin] GiftcardShopPlugin has been Loaded!");
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static GiftcardShopPlugin getInstance() {
        return instance;
    }


}
