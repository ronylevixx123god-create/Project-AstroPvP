package me.bryside.astrorevive.inventories;

import java.io.Serializable;
import java.util.UUID;

import me.bryside.astrorevive.AstroRevivePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeathInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID player;

    private Long time;

    private ItemStack[] items;

    private ItemStack[] armor;

    private String damage;

    private String killer;

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public void setKiller(String killer) {
        this.killer = killer;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setInst(AstroRevivePlugin inst) {
        this.inst = inst;
    }

    public void setReviver(String reviver) {
        this.reviver = reviver;
    }

    public void setRevived(boolean revived) {
        this.revived = revived;
    }

    public UUID getPlayer() {
        return this.player;
    }

    public Long getTime() {
        return this.time;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public ItemStack[] getArmor() {
        return this.armor;
    }

    public String getDamage() {
        return this.damage;
    }

    public String getKiller() {
        return this.killer;
    }

    private int ID = 0;

    private AstroRevivePlugin inst;

    public AstroRevivePlugin getInst() {
        return this.inst;
    }

    private String reviver = "N/A";

    private boolean revived;

    public String getReviver() {
        return this.reviver;
    }

    public boolean isRevived() {
        return this.revived;
    }

    public DeathInventory(AstroRevivePlugin inst, Player player, String killer, String damage) {
        this.player = player.getUniqueId();
        this.time = Long.valueOf(System.currentTimeMillis());
        this.armor = player.getInventory().getArmorContents();
        this.items = player.getInventory().getContents();
        this.inst = inst;
        setDamage(damage);
        setKiller(killer);
        inst.getDeathInventories().add(this);
    }

    public DeathInventory(AstroRevivePlugin inst, String uuid, long time, ItemStack[] armor, ItemStack[] items, String killer, String damage) {
        this.player = UUID.fromString(uuid);
        this.time = Long.valueOf(time);
        this.armor = armor;
        this.items = items;
        this.inst = inst;
        setDamage(damage);
        setKiller(killer);
        inst.getDeathInventories().add(this);
    }

    public void revive(Player whoRevived) {
        Player player = Bukkit.getPlayer(getPlayer());
        for (ItemStack i : player.getInventory().getContents()) {
            if (i != null && i.getType() != Material.AIR)
                player.getWorld().dropItem(player.getLocation(), i);
        }
        for (ItemStack i : player.getInventory().getArmorContents()) {
            if (i != null && i.getType() != Material.AIR)
                player.getWorld().dropItem(player.getLocation(), i);
        }
        player.getInventory().setArmorContents(getArmor());
        whoRevived.sendMessage("Revive");
        player.getInventory().setContents(getItems());
        setRevived(true);
        setReviver(whoRevived.getName());
    }

    public int getTotal() {
        int total = 0;
        for (ItemStack item : getArmor()) {
            if (item != null)
                total++;
        }
        for (ItemStack item : getItems()) {
            if (item != null)
                total++;
        }
        return total;
    }

    private ItemStack[] getAll() {
        ItemStack[] allItems = new ItemStack[this.items.length + this.armor.length];
        for (ItemStack item : this.armor)
            allItems[allItems.length] = item;
        for (ItemStack item : this.items)
            allItems[allItems.length] = item;
        return allItems;
    }

    public int getID() {
        return (int)(this.time.longValue() / 1000L);
    }
}
