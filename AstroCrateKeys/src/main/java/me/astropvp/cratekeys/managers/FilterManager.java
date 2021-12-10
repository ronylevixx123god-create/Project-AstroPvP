package me.astropvp.cratekeys.managers;

import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import me.astropvp.cratekeys.data.Filter;
import me.astropvp.cratekeys.enums.CrateType;

import me.ifamasssxd.cosmicmysterychest.utils.Serialization;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FilterManager {
    private boolean loading = false;






    public void save() {
        if (this.loading) {
            System.out.println("(!) Still loading cratekey filter, returning");
            return;
        }
        try {
            PreparedStatement statement = AstroCrateKeysPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("DELETE FROM cratekeyfilter_data");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (Map.Entry<UUID, HashMap<CrateType, Filter>> entry : AstroCrateKeysPlugin.getFilters().entrySet()) {
                UUID uuid = entry.getKey();
                HashMap<CrateType, Filter> hash =  entry.getValue();

                for (Map.Entry<CrateType, Filter> entry2 : hash.entrySet()) {
                    CrateType mysteryChest = entry2.getKey();
                    ArrayList<ItemStack> blockedLoot = entry2.getValue().getBlockedLoot();

                    ItemStack[] blockLootArray = new ItemStack[blockedLoot.size()];
                    int index = 0;
                    for (ItemStack itemStack : blockedLoot) {
                        blockLootArray[index] = itemStack;
                        index++;
                    }

                    PreparedStatement statement = AstroCrateKeysPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO cratekeyfilter_data(uuid, crateType, blockedLoot) VALUES (?, ?, ?);");
                    statement.setString(1, uuid.toString());
                    statement.setString(2, mysteryChest.name());
                    statement.setString(3, Serialization.serialize(blockLootArray));
                    statement.executeUpdate();
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void load() {
        this.loading = true;
        try {
            PreparedStatement statement = AstroCrateKeysPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT * FROM cratekeyfilter_data");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID uuid = UUID.fromString(set.getString("uuid"));
                CrateType crateType = CrateType.valueOf(set.getString("crateType"));
                ItemStack[] blockedLoot = Serialization.deserialize(set.getString("blockedLoot"));

                assert blockedLoot != null;
                ArrayList<ItemStack> blockedItemsList = new ArrayList<>(Arrays.asList(blockedLoot));

                Filter filter = new Filter(crateType, blockedItemsList);
                HashMap<CrateType, Filter>  filterHashMap= new HashMap<>();
                filterHashMap.put(crateType, filter);

                AstroCrateKeysPlugin.getFilters().put(uuid, filterHashMap);
            }
            this.loading = false;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFilterToUser(UUID uuid, Filter filter) {
        if (AstroCrateKeysPlugin.getFilters().containsKey(uuid)) {
            AstroCrateKeysPlugin.getFilters().get(uuid).put(filter.crateType,filter);
        } else {
            HashMap<CrateType, Filter> filterHashMap = new HashMap<>();
            filterHashMap.put(filter.crateType, filter);
            AstroCrateKeysPlugin.getFilters().put(uuid, filterHashMap);
        }
    }

}

