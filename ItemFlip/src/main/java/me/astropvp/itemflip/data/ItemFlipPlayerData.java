package me.astropvp.itemflip.data;

import com.google.common.collect.Lists;
import java.util.List;
import me.astropvp.itemflip.enums.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFlipPlayerData {
    private Player player;
    private Color color;
    private List<ItemStack> items;
    private boolean accepted;

    public ItemFlipPlayerData(Player player, Color color) {
        this.player = player;
        this.color = color;
        this.items = Lists.newArrayList();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Color getColor() {
        return this.color;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }
}
