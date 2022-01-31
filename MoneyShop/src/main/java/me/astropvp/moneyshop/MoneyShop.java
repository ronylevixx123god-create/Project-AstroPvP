package me.astropvp.moneyshop;

import me.astropvp.moneyshop.commands.MoneyShopCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyShop extends JavaPlugin {
    private static MoneyShop instance;


    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[MoneyShop] MoneyShop has been Loaded!");

        getCommand("moneyshop").setExecutor(new MoneyShopCommand());


    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static MoneyShop getInstance() {
        return instance;
    }
}
