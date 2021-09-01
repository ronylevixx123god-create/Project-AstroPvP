package net.astropvp.struct;

import net.astropvp.CollectPlugin;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
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
        if(collectList.containsKey(player))
            return collectList.get(player);
        PlayerCollect collect = new PlayerCollect(player);
        collectList.put(player, collect);
        collect.load(false);
        return collect;
    }

}
