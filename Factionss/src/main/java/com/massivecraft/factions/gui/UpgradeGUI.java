package com.massivecraft.factions.gui;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.data.MemoryFaction;
import com.massivecraft.factions.upgrade.FactionUpgrade;
import com.massivecraft.factions.upgrade.FactionUpgradeInfo;
import com.massivecraft.factions.upgrade.Upgrade;
import com.massivecraft.factions.upgrade.UpgradeManager;
import com.massivecraft.factions.util.TextUtil;
import com.massivecraft.factions.util.material.MaterialDb;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpgradeGUI extends GUI<FactionUpgrade>{
    private Map<FactionUpgrade, SimpleItem> items;


    public UpgradeGUI(FPlayer user) {
        super(user, 9);

        UpgradeManager manager = FactionsPlugin.getInstance().getUpgradeManager();
        FactionUpgradeInfo upgradeInfo = manager.getFactionUpgradeInfo(this.user.getFaction());
        for(FactionUpgrade upgrade : FactionUpgrade.values()) {
            SimpleItem item = upgrade.createUpgradeItem(this.user.getFactionId(), upgradeInfo);
            items.put(upgrade, item);
        }
        build();
    }

    @Override
    protected String getName() {
        return TextUtil.parseColor("Faction Upgrades");
    }

    @Override
    protected String parse(String toParse, FactionUpgrade upgradeType) {
        String toReturn = toParse;
        return toReturn;
    }

    @Override
    protected void onClick(FactionUpgrade action, ClickType clickType) {
        if(user.getFaction().isWilderness()){
            user.msg(TextUtil.parseColor("&cYou cannot use upgrades while in wilderness"));
            user.getPlayer().closeInventory();
            return;
        }
        if (((MemoryFaction) user.getFaction()).doUpgrade(action, user)) {
            ((MemoryFaction) user.getFaction()).commitUpgrade(action);

            user.getPlayer().closeInventory();
        }
    }

    @Override
    protected Map<Integer, FactionUpgrade> createSlotMap() {
        Map<Integer, FactionUpgrade> map = new HashMap<>();
        int slot = 0;
        for(Map.Entry<FactionUpgrade, SimpleItem> entry : this.items.entrySet()) {
            boolean heroic = entry.getKey().isHeroic();
            if (heroic && slot < 29)
                slot = 29;
            if (slot == 9)
                slot = 10;
            if (slot == 34)
                slot = 38;
            int n = slot++;
            map.put(n, entry.getKey());
        }
        return map;
    }

    @Override
    protected SimpleItem getItem(FactionUpgrade upgradeType) {
        return new SimpleItem(items.get(upgradeType));
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        Map<Integer, SimpleItem> map = new HashMap<>();
        SimpleItem.Builder stained_glass_pane = SimpleItem.builder()
                .setData((short) 7)
                .setMaterial(MaterialDb.get("STAINED_GLASS_PANE"))
                .setName(TextUtil.parseColor("&7")
                );
        for(int z = 0; z < this.size; z++){
            map.put(z, stained_glass_pane.build());
        }
        return map;
    }
}
