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
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CollectPlugin.getInstance(), () -> save(false));
            return;
        }
        try {
            for (CollectCategory collectCategory : this.getCategories()) {
                PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO collect_userdata(uuid, items, killed) VALUES (?, ?, ?);");
                try {
                    statement.setString(1, this.player.getUniqueId().toString());
                    statement.setString(2, itemStackArrayToBase64(collectCategory.getItems()));
                    statement.setString(3, collectCategory.getKilledName().toString());
                    statement.executeUpdate();
                } finally {
                    if (Collections.singletonList(statement).get(0) != null)
                        statement.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void load(boolean async, Player player) {
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CollectPlugin.getInstance(), () -> load(false, player));
            return;
        }
        this.loading = true;
        try {
            PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT * FROM collect_userdata WHERE uuid = ?");
            statement.setString(1, this.player.getUniqueId().toString());
            ResultSet set = statement.executeQuery();
            //PlayerCollect collect = new PlayerCollect(this.player);
            if(set.first()) {
                while (set.next()) {
                    System.out.println("DEB 2");
                    String killed = set.getString("killed");
                    ItemStack[] items = Serialization.itemStackArrayFromBase64(set.getString("items"));
                    //collect.add(new CollectCategory(CollectType.DEATH_INVENTORY, items, CC.RedB + "GG" + "'s Death Inventory"));
                    CollectPlugin.getInstance().getCollectManager().getPlayerCollect(this.player).add(new CollectCategory(CollectType.DEATH_INVENTORY, items, CC.RedB + killed + "'s Death Inventory", killed));

                }
            }
            this.loading = false;
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
