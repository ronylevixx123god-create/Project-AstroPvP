// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventory;

import java.lang.reflect.InvocationTargetException;
import net.astropvp.util.ItemStackUtil;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import java.io.Serializable;

public class ItemData implements Serializable
{
    private transient ItemStack item;
    private int slot;
    private Action action;
    private String time;
    private Map<String, Object> itemAsMap;
    
    public ItemData(final ItemStack item, final int slot, final Action action, final String time) {
        this.item = item;
        this.slot = slot;
        this.action = action;
        this.time = time;
        this.itemAsMap = ItemStackUtil.serialize(item);
    }
    
    public ItemStack getItem() {
        if (this.item == null) {
            try {
                return this.item = ItemStackUtil.deserialize(this.itemAsMap);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return this.item;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public String getTime() {
        return this.time;
    }
}
