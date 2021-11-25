// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventory;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import net.astropvp.util.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import net.astropvp.config.ShieldConfig;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.Inventory;
import net.astropvp.FactionShieldsPlugin;

public class InventoryHandler
{
    private FactionShieldsPlugin plugin;
    private Inventory defaultInventory;
    private List<ItemData> timeButtons;
    private boolean infoButtonEnabled;
    private String infoButtonName;
    private String infoButtonType;
    private int infoButtonSlot;
    private List<String> infoButtonLore;
    private boolean shieldsButtonEnabled;
    private String shieldsButtonName;
    private String shieldsButtonType;
    private int shieldsButtonSlot;
    private List<String> shieldsButtonLore;
    
    public InventoryHandler(final FactionShieldsPlugin plugin) {
        this.timeButtons = new ArrayList<ItemData>();
        this.plugin = plugin;
        new Thread(() -> {
            ShieldConfig config = ShieldConfig.get();
            String pathInfo = "inventory.info-button.";
            this.infoButtonEnabled = config.getBoolean("inventory.info-button.enabled");
            this.infoButtonName = config.getString("inventory.info-button.name");
            this.infoButtonType = config.getString("inventory.info-button.type");
            this.infoButtonSlot = config.getInt("inventory.info-button.slot");
            this.infoButtonLore = config.getList("inventory.info-button.lore");
            String pathShields = "inventory.shields-button.";
            this.shieldsButtonEnabled = config.getBoolean("inventory.shields-button.enabled");
            this.shieldsButtonName = config.getString("inventory.shields-button.name");
            this.shieldsButtonType = config.getString("inventory.shields-button.type");
            this.shieldsButtonSlot = config.getInt("inventory.shields-button.slot");
            this.shieldsButtonLore = config.getList("inventory.shields-button.lore");
            this.build();
            return;
        }).start();
        final DefaultInventory defaultInventory = new DefaultInventory();
    }
    
    private void build() {
        this.defaultInventory = Bukkit.createInventory(null, ShieldConfig.INVENTORY_SIZE, ShieldConfig.INVENTORY_TITLE);
        final Material material = Material.getMaterial(ShieldConfig.ITEM_TYPES);
        final ItemStack borderColor = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.valueOf(ShieldConfig.BORDER_COLOR).ordinal());
        int time = 1;
        String dayOrNight = "AM";
        for (int i = ShieldConfig.START_SLOT; i <= ShieldConfig.END_SLOT; ++i, time = ((time == 12) ? 1 : (time + 1))) {
            if (ShieldConfig.IGNORE_SIDES) {
                if (i % 9 == 0) {
                    i += 2;
                }
                else if ((i + 1) % 9 == 0) {
                    i += 2;
                }
            }
            if (time > 11 && dayOrNight.equalsIgnoreCase("AM")) {
                dayOrNight = "PM";
            }
            else if (time > 11 & dayOrNight.equalsIgnoreCase("PM")) {
                dayOrNight = "AM";
            }
            final String stringTime = time + " " + dayOrNight + " " + ShieldConfig.TIMEZONE;
            final String name = ShieldConfig.NAMES.replaceAll("%time%", stringTime);
            ItemBuilder itemBuilder;
            if (material == Material.STAINED_GLASS_PANE) {
                itemBuilder = new ItemBuilder(material, DyeColor.RED);
            }
            else {
                itemBuilder = new ItemBuilder(material);
            }
            itemBuilder.setName(name);
            if (!ShieldConfig.LORES.isEmpty()) {
                itemBuilder.setLore(this.updatePlaceholders(dayOrNight, time, stringTime, ShieldConfig.LORES));
            }
            final ItemStack item = itemBuilder.build();
            this.defaultInventory.setItem(i, item);
            this.timeButtons.add(new ItemData(item, i, null, stringTime));
        }
        if (this.infoButtonEnabled) {
            this.loadInfoButton();
        }
        if (this.shieldsButtonEnabled) {
            this.loadShieldsButton();
        }
        for (int i = 0; i < this.defaultInventory.getSize(); ++i) {
            final ItemStack item2 = this.defaultInventory.getItem(i);
            if (item2 == null || item2.getType() == Material.AIR) {
                this.defaultInventory.setItem(i, borderColor);
            }
            if (i == ShieldConfig.STOP_BACKGROUND) {
                break;
            }
        }
        FactionShieldsPlugin.getInstance().getShieldHandler().loadAll();
    }
    
    private List<String> updatePlaceholders(final String dayOrNight, final int intTime, final String time, final List<String> configLore) {
        final List<String> updated = new ArrayList<String>();
        configLore.forEach(line -> updated.add(line.replaceAll("%time%", time).replaceAll("%end%", this.plugin.getShieldHandler().getEndTime(dayOrNight, intTime)).replaceAll("%duration%", String.valueOf(ShieldConfig.SHIELD_DURATION))));
        return updated;
    }
    
    private void loadInfoButton() {
        final String duration = ShieldConfig.SHIELD_DURATION + " hours";
        this.defaultInventory.setItem(this.infoButtonSlot, new ItemBuilder(Material.getMaterial(this.infoButtonType)).setName(this.infoButtonName).setLore(this.infoButtonLore, ImmutableList.of(Pair.of("%duration%", duration), Pair.of("%status%", "§cNOT ACTIVE"))).build());
    }
    
    private void loadShieldsButton() {
        this.defaultInventory.setItem(this.shieldsButtonSlot, new ItemBuilder(Material.getMaterial(this.shieldsButtonType)).setName(this.shieldsButtonName).setLore(this.shieldsButtonLore).build());
    }
    
    public List<ItemData> getTimeButtons() {
        return this.timeButtons;
    }
    
    public Inventory getDefaultInventory() {
        return this.defaultInventory;
    }
    
    public int getInfoButtonSlot() {
        return this.infoButtonSlot;
    }
    
    public int getShieldsButtonSlot() {
        return this.shieldsButtonSlot;
    }
    
    public List<String> getInfoButtonLore() {
        return this.infoButtonLore;
    }
}
