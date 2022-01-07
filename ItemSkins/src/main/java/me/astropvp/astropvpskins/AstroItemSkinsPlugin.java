package me.astropvp.astropvpskins;

import me.astropvp.astropvpskins.armorequip.ArmorListener;
import me.astropvp.astropvpskins.commands.CommandItemSkin;
import me.astropvp.astropvpskins.commands.CommandMultiSkin;
import me.astropvp.astropvpskins.commands.RemoveBrokenSkinCommand;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.listeners.ItemSkinInventoryClickListener;
import me.astropvp.astropvpskins.listeners.ItemSkinListener;
import me.astropvp.astropvpskins.listeners.MauiHookListener;
import me.astropvp.astropvpskins.listeners.SnowflakeListener;
import me.astropvp.astropvpskins.objects.BackpackSocketItem;
import me.astropvp.astropvpskins.objects.ItemSkin;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AstroItemSkinsPlugin extends JavaPlugin {
    private static AstroItemSkinsPlugin instance;

    private HashMap<ItemSkinType, List<SkinType>> categorisedSkins;

    public List<SkinType> getSkins(ItemSkinType type) {
        return categorisedSkins.get(type);
    }

    public void onEnable() {
        instance = this;
        getCommand("itemskin").setExecutor(new CommandItemSkin());
        getCommand("multiskin").setExecutor(new CommandMultiSkin());
        getCommand("removeskin").setExecutor(new RemoveBrokenSkinCommand());
        getServer().getPluginManager().registerEvents(new ItemSkinListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
        getServer().getPluginManager().registerEvents(new ItemSkinInventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new MauiHookListener(), this);
        getServer().getPluginManager().registerEvents(new SnowflakeListener(), this);
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new BackpackSocketItem());
        this.categorisedSkins = new HashMap<>();
        categoriseSkins();
    }

    public void onDisable() {
        instance = null;
    }

    public static AstroItemSkinsPlugin getInstance() { return instance; }

    private void categoriseSkins() {
        for(SkinType type : SkinType.values()) {
            List<SkinType> skins = this.categorisedSkins.containsKey(type.getItemSkinType()) ? this.categorisedSkins.get(type.getItemSkinType()) : new ArrayList<>();
            skins.add(type);
            this.categorisedSkins.put(type.getItemSkinType(), skins);
        }
    }
}
