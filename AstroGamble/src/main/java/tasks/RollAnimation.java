package tasks;

import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RollAnimation implements Runnable{
    private ItemStack reward;
    private final PaginatedGui inventory;
    private int count;
    private Player player;

    public RollAnimation(PaginatedGui inventory, ItemStack itemStack, int count, Player player) {
        this.reward = itemStack;
        this.player = player;
        this.count = 20;
        this.inventory = inventory;
    }
    @Override
    public void run() {
        count--;
        if (!player.isOnline()) return;
        if (inventory != null) {
            if (count % 2 == 0) {
                inventory.setItem(4, new GuiItem(Material.DIRT));
            } else {
                inventory.setItem(4, new GuiItem(Material.GOLD_BLOCK));
            }
        }
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.5F);
        player.updateInventory();
    }
}
