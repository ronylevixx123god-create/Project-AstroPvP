package net.astropvp.managers;

import lombok.Getter;
import net.astropvp.Carnage;
import net.astropvp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarnageManager {
    @Getter
    private List<ItemStack> carnageItems;

    private HashMap<Player, Long> playersOnCooldown;

    public CarnageManager() {
        playersOnCooldown = new HashMap<>();
        carnageItems = new ArrayList<>();
        for(String string : Carnage.getInstance().getConfig().getStringList("carnage-items")) {
            this.carnageItems.add(ConfigUtils.getItem(string));
        }
    }

    public boolean isPlayerOnCooldown(Player player) {
        return playersOnCooldown.get(player) - 300000L <= 0;
    }

    public void registerCooldown(Player player) {
        playersOnCooldown.put(player, System.currentTimeMillis());
        Bukkit.getScheduler().runTaskLaterAsynchronously(Carnage.getInstance(), () -> {
            playersOnCooldown.remove(player);
        }, 6000L);
    }

    public void addCarnageItem(ItemStack item) {
        carnageItems.add(item);
    }

    public boolean removeCarnageItem(ItemStack item) {
        if(!carnageItems.contains(item))
            return false;
        carnageItems.remove(item);
        return true;
    }

    public long getPlayerCooldown(Player player) {
        return (playersOnCooldown.get(player) - System.currentTimeMillis()) / 1000L;
    }

    public void saveConfig() {
        List<String> strings = new ArrayList<>();
        for(ItemStack item : this.carnageItems) {
            strings.add(ConfigUtils.toBase64(new ItemStack[]{item}, 9));
        }
        Carnage.getInstance().getConfig().set("carnage-items", strings);
    }
}
