package net.astropvp.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.CollectPlugin;
import net.astropvp.utils.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static net.astropvp.utils.Serialization.*;

public class PlayerCollect {
    private boolean loading = false;

    private Player player;

    private List<CollectCategory> categories;

    public PlayerCollect(Player player) {
        this.player = player;
        categories = new ArrayList<>();
    }

    public void add(CollectCategory category) {
        this.categories.add(category);
    }

    public Player getPlayer() {
        return player;
    }

    public List<CollectCategory> getCategories() { return this.categories; }

    public void save(boolean async) {
        if (this.loading)
            return;
        try {
            PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("DELETE FROM collect_userdata WHERE uuid = ?");
            statement.setString(1, this.player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (CollectCategory category : this.categories) {
                PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO collect_userdata(uuid, items, name, type) VALUES (?, ?, ?, ?);");
                try {
                    statement.setString(1, this.player.getUniqueId().toString());
                    statement.setString(2, Serialization.serialize(category.getItems()));
                    statement.setString(3, category.getName());
                    statement.setString(4, category.getType().name());
                    statement.executeUpdate();

                } finally {
                    if (Collections.singletonList(statement).get(0) != null)
                        statement.close();
                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void load(boolean async) {
        this.loading = true;
        try {
            PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT * FROM collect_userdata WHERE uuid = ?");
            statement.setString(1, this.player.getUniqueId().toString());
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                ItemStack[] items = Serialization.deserialize(set.getString("items"));
                String name = set.getString("name");
                String type = set.getString("type");
                CollectCategory category = new CollectCategory(CollectType.valueOf(type), items, name);
                CollectPlugin.getInstance().getCollectManager().getPlayerCollect(player).add(category);
            }
            this.loading = false;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
