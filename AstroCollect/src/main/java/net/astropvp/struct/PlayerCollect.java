package net.astropvp.struct;

import net.astropvp.CollectPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.astropvp.utils.Serialization.getAsList;
import static net.astropvp.utils.Serialization.getAsString;

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
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CollectPlugin.getInstance(), () -> save(false));
            return;
        }
        try {
            PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO collect_userdata(uuid, items) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE items = ?;");
            try {
                statement.setString(1, this.player.getUniqueId().toString());
                statement.setString(2, getAsString(this.getCategories()));
                statement.executeQuery();
            } finally {
                if (Collections.<PreparedStatement>singletonList(statement).get(0) != null)
                    statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void load(boolean async) {
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CollectPlugin.getInstance(), () -> load(false));
            return;
        }
        this.loading = true;
        try {
            PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT items FROM collect_userdata WHERE uuid = '" + this.player.getUniqueId() + "'");
            ResultSet set = statement.executeQuery();
            if(set.first()) {
                PlayerCollect collect = new PlayerCollect(this.player);
                List<Object> playerCollects = getAsList(set.getString("items"));
                assert playerCollects != null;
                for(Object o : playerCollects) {
                    collect.add((CollectCategory) o);
                }
            }
            this.loading = false;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
