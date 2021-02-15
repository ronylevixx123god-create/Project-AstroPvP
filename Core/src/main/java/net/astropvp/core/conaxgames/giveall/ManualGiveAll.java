package net.astropvp.core.conaxgames.giveall;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ManualGiveAll {

    public static HashMap<Player, List<ItemStack>> claim = new HashMap<>();

    public static void func(ItemStack i) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getInventory().firstEmpty() == -1) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage(CC.Red + "Couldn't put the give all item in your inventory. Put it in your /claim until reboot.");
                    if (claim.get(player) != null) {
                        List<ItemStack> currentClaim = claim.get(player);
                        currentClaim.add(i);
                        claim.put(player, currentClaim);
                    } else {
                        claim.get(player).add(i);
                    }
                } else {
                    player.getInventory().addItem(i);
                }
            }
        });
    }
}
