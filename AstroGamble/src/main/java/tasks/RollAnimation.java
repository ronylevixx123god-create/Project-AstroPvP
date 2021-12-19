package tasks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RollAnimation implements Runnable{
    private ItemStack reward;
    private int count;
    private Player player;

    public RollAnimation(ItemStack itemStack, int count, Player player) {
        this.reward = itemStack;
        this.player = player;
        this.count = 20;
    }
    @Override
    public void run() {
        count -= 1;
        if (!player.isOnline()) return;
    }
}
