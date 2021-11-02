package me.ifamasssxd.cosmicmysterychest.managers;

import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.data.Filter;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicmysterychest.utils.Serialization;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FilterManager {
    private boolean loading = false;






    public void save() {
        if (this.loading) {
            System.out.println("(!) Still loading lootboxes, returning");
            return;
        }
        try {
            PreparedStatement statement = CosmicMysteryChest.getInstance().getSqlDatabase().getConnection().prepareStatement("DELETE FROM lootboxfilter_data");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (Map.Entry<UUID, HashMap<MysteryChest, Filter>> entry : CosmicMysteryChest.getFilters().entrySet()) {
                UUID uuid = entry.getKey();
                HashMap<MysteryChest, Filter> hash =  entry.getValue();

                for (Map.Entry<MysteryChest, Filter> entry2 : hash.entrySet()) {
                    MysteryChest mysteryChest = entry2.getKey();
                    ArrayList<ItemStack> blockedLoot = entry2.getValue().getBlockedLoot();

                    ItemStack[] blockLootArray = new ItemStack[blockedLoot.size()];
                    int index = 0;
                    for (ItemStack itemStack : blockedLoot) {
                        blockLootArray[index] = itemStack;
                        index++;
                    }

                    PreparedStatement statement = CosmicMysteryChest.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO lootboxfilter_data(uuid, mysteryChest, blockedLoot) VALUES (?, ?, ?);");
                    statement.setString(1, uuid.toString());
                    statement.setString(2, mysteryChest.getInternalName());
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
            PreparedStatement statement = CosmicMysteryChest.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT * FROM lootboxfilter_data");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UUID uuid = UUID.fromString(set.getString("uuid"));
                MysteryChest mysteryChest = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get(set.getString("mysteryChest"));
                ItemStack[] blockedLoot = Serialization.deserialize(set.getString("blockedLoot"));

                assert blockedLoot != null;
                ArrayList<ItemStack> blockedItemsList = new ArrayList<>(Arrays.asList(blockedLoot));

                Filter filter = new Filter(mysteryChest, blockedItemsList);
                HashMap<MysteryChest, Filter>  filterHashMap= new HashMap<>();
                filterHashMap.put(mysteryChest, filter);

                CosmicMysteryChest.getFilters().put(uuid, filterHashMap);
            }
            this.loading = false;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFilterToUser(UUID uuid, Filter filter) {
        if (CosmicMysteryChest.getFilters().containsKey(uuid)) {
            CosmicMysteryChest.getFilters().get(uuid).put(filter.mysteryChest,filter);
        } else {
            HashMap<MysteryChest, Filter> filterHashMap = new HashMap<>();
            filterHashMap.put(filter.mysteryChest, filter);
            CosmicMysteryChest.getFilters().put(uuid, filterHashMap);
        }
    }

}

