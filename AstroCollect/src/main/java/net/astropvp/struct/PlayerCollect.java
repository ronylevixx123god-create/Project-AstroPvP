package net.astropvp.struct;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class PlayerCollect {
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
}
