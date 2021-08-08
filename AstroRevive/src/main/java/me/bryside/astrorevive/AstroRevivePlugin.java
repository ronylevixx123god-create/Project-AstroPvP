package me.bryside.astrorevive;

import java.util.ArrayList;
import javax.swing.JProgressBar;

import me.bryside.astrorevive.commands.ReviveCommand;
import me.bryside.astrorevive.inventories.DeathInventory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroRevivePlugin extends JavaPlugin {
    public ArrayList<DeathInventory> deathInventories = new ArrayList<>();

    public void onEnable() {
        this.deathInventories = new ArrayList<>();
        getServer().getPluginManager().registerEvents(new ReviveCommand(this), (Plugin)this);
        getCommand("revive").setExecutor(new ReviveCommand(this));
        int current = 0;
        int total = getConfig().getKeys(false).size();
        JProgressBar bar = new JProgressBar(0, total);
        bar.setValue(current);
        bar.setStringPainted(true);
        for (String s : getConfig().getKeys(false)) {
            bar.setValue(current);
            current++;
            long deathDate = getConfig().getConfigurationSection(s).getLong("time");
            long maxTime = 172800L;
            if ((System.currentTimeMillis() - deathDate) / 1000L > maxTime) {
                getConfig().set(s, null);
                continue;
            }
            load(s);
            if (current % 25 == 0)
                System.out.println("Loading Death Inventories...  " + bar
                        .getString() + " (" + current + "/" + total + ")");
        }
        saveConfig();
    }

    public void onDisable() {
        int current = 0;
        int total = getDeathInventories().size();
        JProgressBar bar = new JProgressBar(0, total);
        bar.setValue(current);
        bar.setStringPainted(true);
        for (DeathInventory deathInventory : getDeathInventories()) {
            current++;
            save(deathInventory);
            if (current % 25 == 0)
                System.out.println("Saving Death Inventories...  " + bar
                        .getString() + " (" + current + "/" + total + ")");
        }
        saveConfig();
    }

    public void save(DeathInventory inv) {
        ConfigurationSection section = getConfig().createSection(inv.getID() + "");
        ItemStack[] armor = inv.getArmor();
        ItemStack[] items = inv.getItems();
        section.set("time", inv.getTime());
        section.set("player", inv.getPlayer().toString());
        section.set("ID", Integer.valueOf(inv.getID()));
        section.set("armor", armor);
        section.set("items", items);
        section.set("killer", inv.getKiller());
        section.set("damage", inv.getDamage());
        section.set("revived", Boolean.valueOf(inv.isRevived()));
        section.set("reviver", inv.getReviver());
    }

    public void load(String ID) {
        ConfigurationSection section = getConfig().getConfigurationSection(ID);
        ArrayList<ItemStack> invItems = (ArrayList<ItemStack>)section.getList("items");
        ArrayList<ItemStack> invArmor = (ArrayList<ItemStack>)section.getList("armor");
        ItemStack[] items = new ItemStack[invItems.size()];
        ItemStack[] armor = new ItemStack[invArmor.size()];
        int i;
        for (i = 0; i < invItems.size(); i++)
            items[i] = invItems.get(i);
        for (i = 0; i < invArmor.size(); i++)
            armor[i] = invArmor.get(i);
        DeathInventory inv = new DeathInventory(this, section.getString("player"), section.getLong("time"), armor, items, section.getString("killer"), section.getString("damage"));
        inv.setID(section.getInt("ID"));
        inv.setReviver(section.getString("reviver"));
        inv.setRevived(section.getBoolean("revived"));
    }

    public ArrayList<DeathInventory> getDeathInventories() {
        return this.deathInventories;
    }
}
