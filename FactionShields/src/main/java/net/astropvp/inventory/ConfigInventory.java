// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventory;

import java.util.ArrayList;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import net.astropvp.config.ShieldConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConfigInventory
{
    protected String title;
    protected int size;
    protected boolean fill;
    protected Map<Integer, ItemData> items;
    private String globalName;
    private List<String> globalLore;
    
    public ConfigInventory(final String configName) {
        this.items = new HashMap<Integer, ItemData>();
        this.load(configName);
    }
    
    private void load(final String configName) {
        new Thread(() -> {
            ShieldConfig config = ShieldConfig.get();
            String path = configName + ".";
            if (config.contains(path + "title")) {
                this.title = config.getString(path + "title");
            }
            if (config.contains(path + "size")) {
                this.size = config.getInt(path + "size");
            }
            if (config.contains(path + "fill")) {
                this.fill = config.getBoolean(path + "fill");
            }
            if (config.contains(path + "name")) {
                this.globalName = config.getString(path + "name");
            }
            if (config.contains(path + "lore")) {
                this.globalLore = config.getList(path + "lore");
            }
            if (config.contains(path + "items")) {
                Iterator<String> iterator = config.getSection(path + "items").iterator();
                while (iterator.hasNext()) {
                    String slot = iterator.next();
                    String itemPath = path + "items." + slot + ".";
                    String name = config.getString(itemPath + "name");
                    Material material = Material.getMaterial(config.getString(itemPath + "type"));
                    DyeColor value = null;
                    if (config.contains(itemPath + "color")) {
                        value = DyeColor.valueOf(config.getString(itemPath + "color"));
                    }
                    DyeColor color = value;
                    Action value2 = null;
                    if (config.contains(itemPath + "action")) {
                        value2 = Action.valueOf(config.getString(itemPath + "action"));
                    }
                    Action action = value2;
                    List<String> lore = config.getList(itemPath + "lore");
                    int slotInt = Integer.parseInt(slot);
                    ItemStack itemToAdd;
                    if (color != null) {
                        itemToAdd = new ItemStack(material, 1, (short)color.ordinal());
                    }
                    else {
                        itemToAdd = new ItemStack(material);
                    }
                    ItemMeta meta = itemToAdd.getItemMeta();
                    meta.setDisplayName(name);
                    meta.setLore((List)lore);
                    itemToAdd.setItemMeta(meta);
                    this.items.put(slotInt, new ItemData(itemToAdd, slotInt, action, null));
                }
            }
        }).start();
    }
    
    protected List<String> convertPlaceholder(final List<String> list, final String key, final String value) {
        final List<String> converted = new ArrayList<String>();
        list.forEach(line -> converted.add(line.replaceAll(key, value)));
        return converted;
    }
    
    public List<String> getGlobalLore() {
        return this.globalLore;
    }
    
    public String getGlobalName() {
        return this.globalName;
    }
}
