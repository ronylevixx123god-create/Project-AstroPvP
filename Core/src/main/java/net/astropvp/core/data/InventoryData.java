package net.astropvp.core.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.astropvp.core.utils.ConfigUtils;
import net.astropvp.core.utils.InventoryUtils;
import net.astropvp.core.utils.PlayerData;
import net.astropvp.core.utils.PlayerDataHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryData {
    private static Map<UUID, InventoryData[]> players = Maps.newHashMap();

    private String armorContents;

    private String contents;

    private InventoryData(Player player) {
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        ItemStack[] contents = player.getInventory().getContents();
        this.armorContents = InventoryUtils.toBase64(armorContents, 9);
        this.contents = InventoryUtils.toBase64(contents, 45);
    }

    public InventoryData(String contents, String armorContents) {
        this.contents = contents;
        this.armorContents = armorContents;
    }

    public ItemStack[] getArmorContents() {
        return InventoryUtils.fromBase64(this.armorContents).getContents();
    }

    public ItemStack[] getContents() {
        return InventoryUtils.fromBase64(this.contents).getContents();
    }

    public static InventoryData createInstance(Player player) {
        UUID uuid = player.getUniqueId();
        boolean containsKey = players.containsKey(uuid);
        InventoryData[] data = containsKey ? players.get(uuid) : new InventoryData[10];
        if (!containsKey)
            players.put(player.getUniqueId(), data);
        int slot = -1;
        InventoryData invData = new InventoryData(player);
        int i;
        for (i = 0; i < data.length; i++) {
            InventoryData x = data[i];
            if (x == null) {
                InventoryData[] temporary = new InventoryData[10];
                for (int it = 0; it < data.length - 1; it++)
                    temporary[it + 1] = data[it];
                temporary[0] = invData;
                players.put(uuid, temporary);
                return invData;
            }
        }
        data[data.length - 1] = null;
        for (i = data.length - 2; i >= 0; i--) {
            InventoryData x = data[i];
            data[i + 1] = x;
        }
        data[0] = invData;
        return invData;
    }

    public static void saveToDisk(Player player, InventoryData[] contents) {
        PlayerData data = PlayerDataHelper.findPlayerData(player);
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < contents.length; i++) {
            InventoryData invData = contents[i];
            if (invData != null) {
                ItemStack[] armorContents = invData.getArmorContents();
                ItemStack[] inventoryContents = invData.getContents();
                list.add(String.valueOf(ConfigUtils.toBase64(inventoryContents, inventoryContents.length)) + ":" + ConfigUtils.toBase64(armorContents, armorContents.length));
            }
        }
        data.setDefaultPath("revive-contents", list);
    }

    public static void loadFromDisk(Player player) {
        UUID uuid = player.getUniqueId();
        if (players.containsKey(uuid))
            players.remove(uuid);
        PlayerData pdata = PlayerDataHelper.findPlayerData(player);
        Object value = pdata.getValue("revive-contents");
        if (value == null)
            return;
        List<String> list = (List<String>)value;
        InventoryData[] invData = new InventoryData[10];
        for (int i = 0; i < invData.length; i++) {
            if (list.size() > i) {
                String[] split = ((String)list.get(i)).split(":");
                InventoryData inv_obj = new InventoryData(split[0], split[1]);
                invData[i] = inv_obj;
            }
        }
        players.put(player.getUniqueId(), invData);
    }

    public static Map<UUID, InventoryData[]> getData() {
        return players;
    }
}
