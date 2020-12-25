/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ItemSerialization
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.google.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicbosses.struct;

import com.cosmicpvp.cosmicutils.utils.ItemSerialization;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RewardInventory {
    private static HashMap<UUID, RewardInventory> bossRewards = new HashMap();
    private static File bossRewardsFile;
    private static Type token;
    private List<ItemStack> items = new ArrayList<ItemStack>();
    private long expirationTime = -1L;
    private UUID uuid;

    public static void loadBossRewards(JavaPlugin plugin) throws IOException {
        bossRewardsFile = JSONUtils.getOrCreateFile((File)plugin.getDataFolder(), (String)"pendingPlayerBossRewards.json");
        HashMap<UUID, RewardInventorySave> maps = (HashMap)JSONUtils.fromJson(bossRewardsFile, token);
        if (maps != null) {
            maps.forEach((uuid, rewardSave) -> {
                if (rewardSave.getExpiration() > 0L && System.currentTimeMillis() >= rewardSave.getExpiration()) {
                    Bukkit.getLogger().info("Expiring items for " + uuid.toString() + " Expiration: " + rewardSave.getExpiration());
                    return;
                }
                RewardInventory inventory = new RewardInventory((UUID)uuid);
                inventory.setExpirationTime(rewardSave.getExpiration());
                List items = ItemSerialization.itemListFromBase64((String)rewardSave.getRewardsString());
                if (items != null) {
                    inventory.setItems(items);
                }
                if (inventory.getItems().size() > 0) {
                    bossRewards.put((UUID)uuid, inventory);
                }
            });
        }
        try {
            Class.forName("com.cosmicpvp.cosmicutils.utils.ItemSerialization");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveBossRewards() {
        try {
            HashMap maps = new HashMap();
            bossRewards.forEach((uuid, inv) -> maps.put(uuid, new RewardInventorySave(ItemSerialization.itemListToBase64(inv.getItems()), inv.getExpirationTime())));
            JSONUtils.saveJSONToFile((File)bossRewardsFile, maps, (Object)token);
            Bukkit.getLogger().info("[CosmicBosses] Saving " + maps.size() + " Boss Rewards");
        }
        catch (Exception e) {
            Bukkit.getLogger().info("Error saving boss rewards!");
            e.printStackTrace();
        }
    }

    public RewardInventory(Player pl) {
        this(pl.getUniqueId());
    }

    public RewardInventory(UUID uuid) {
        this.uuid = uuid;
    }

    public static RewardInventory getRewardInventory(Player pl) {
        return bossRewards.get(pl.getUniqueId());
    }

    public static RewardInventory removeRewardInventory(Player pl) {
        return bossRewards.remove(pl.getUniqueId());
    }

    public static void addReward(Player player, ItemStack clone) {
        boolean expired;
        RewardInventory inventory = bossRewards.computeIfAbsent(player.getUniqueId(), e -> new RewardInventory(player));
        boolean bl = expired = inventory.getExpirationTime() > 0L && inventory.getExpirationTime() < System.currentTimeMillis();
        if (expired) {
            Bukkit.getLogger().info("Expiring previous collect items for " + player.getName() + "!");
            inventory.getItems().clear();
        }
        if (inventory.getExpirationTime() <= 0L || expired) {
            inventory.setExpirationTime(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24L));
        }
        inventory.getItems().add(clone);
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    static {
        token = new TypeToken<HashMap<UUID, RewardInventorySave>>(){}.getType();
    }

    public static class RewardInventorySave {
        private String rewardsString;
        private long expiration;

        public RewardInventorySave(String rewardsString, long expiration) {
            this.rewardsString = rewardsString;
            this.expiration = expiration;
        }

        public String getRewardsString() {
            return this.rewardsString;
        }

        public long getExpiration() {
            return this.expiration;
        }
    }
}

