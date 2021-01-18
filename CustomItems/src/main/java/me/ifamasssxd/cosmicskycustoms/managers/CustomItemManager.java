/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 */
package me.ifamasssxd.cosmicskycustoms.managers;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicPersistentItem;
import me.ifamasssxd.cosmicskycustoms.struct.ItemPersistentData;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CustomItemManager {
    private Map<String, CosmicItem> customRegisteredItems = new HashMap<>();
    private CosmicSkyCustomItems plugin;

    public CustomItemManager(CosmicSkyCustomItems plugin) {
        this.plugin = plugin;
    }

    public void onEnable(CosmicSkyCustomItems plugin) {
        for (CosmicItems item : CosmicItems.values()) {
            this.registerCustomItem(item.getItem());
        }
    }

    public CosmicItem getCustomItem(String internalName) {
        return this.customRegisteredItems.get(internalName);
    }

    public void registerCustomItem(CosmicItem instance) {
        this.customRegisteredItems.put(instance.getInternalName(), instance);
        this.plugin.log(CC.GreenB + "Registering CustomItem: " + instance + " Class: " + instance.getClass().getName());
    }

    public CosmicPersistentItem registerPersistentItem(String name, Class clazz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if (name != null) {
            this.plugin.log(CC.GreenB + "Registering PersistentCustomItem name: " + name + " Class: " + clazz.getSimpleName());
        }
        // up to this point is fine.
        if (clazz != null) {
            ItemPersistentData data = CosmicPersistentItem.registerPersistentItem(name, clazz);
            this.customRegisteredItems.put(data.getInternalName(), data.getSampleVersion());
            this.plugin.log(CC.GreenB + "***** NULL");
            return data.getSampleVersion();
        }
        return null;
    }

    public void onDisable(CosmicSkyCustomItems plugin) {
    }

    public Map<String, CosmicItem> getCustomRegisteredItems() {
        return this.customRegisteredItems;
    }
}

