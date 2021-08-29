package net.astropvp.struct;

import io.netty.handler.codec.base64.Base64Decoder;
import net.astropvp.CollectPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CollectManager {
    private HashMap<Player, PlayerCollect> collectList;

    public CollectManager() {
        collectList = new HashMap<>();
    }

    public PlayerCollect getPlayerCollect(Player player) {
        if(!collectList.containsKey(player))
            registerPlayer(player);
        return collectList.get(player);
    }

    private void registerPlayer(Player player) {
        PreparedStatement statement = null;
        try {
            statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT items FROM collect_userdata WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet set = statement.executeQuery();
            if(set.first()) {
                String items = set.getString("items");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getAsString(List<ItemStack> items) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream output = new BukkitObjectOutputStream(os);
            output.writeInt(items.size());
            for(int i = 0; i < items.size(); i++) {
                output.writeObject(items.get(i));
            }
            output.close();;
            String serialized = Base64.getEncoder().encodeToString(os.toByteArray());
            return serialized;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ItemStack> getAsList(String string) {
    }

}
