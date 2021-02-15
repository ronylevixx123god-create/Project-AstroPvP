/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest
 *  me.ifamasssxd.cosmicmysterychest.struct.MysteryChest
 *  org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.matthawkk.autogiveall.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class GiveAllManager {
    public static HashMap<Player, Integer> lootboxes = new HashMap();
    private static long nextGiveAll = 1800L;
    private static final ItemStack giveAllItem = (CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("BetaBundle")).createClaimableItem(false);

    public static long getNextGiveAll() {
        return nextGiveAll;
    }

    public static void setNextGiveAll(long time) {
        nextGiveAll = time;
    }

    public static void doGiveAll() {
        ArrayList<InetSocketAddress> ipList = Lists.newArrayList();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            InetSocketAddress ip = p.getAddress();
            if (ipList.contains(ip)) {
                p.sendMessage(CC.Red + "You have already claimed a giveall lootbox on this ip");
                continue;
            }
            ipList.add(ip);
            if (CombatLog.inCombat((Player)p)) {
                if (lootboxes.containsKey((Object)p)) {
                    lootboxes.put(p, lootboxes.get((Object)p) + 1);
                } else {
                    lootboxes.put(p, 1);
                }
                p.sendMessage(CC.Green + "Since you were in combat, your giveall lootbox has been transferred to your /claim until the next reboot!");
                continue;
            }
            if (p.getInventory().firstEmpty() == -1) {
                if (lootboxes.containsKey((Object)p)) {
                    lootboxes.put(p, lootboxes.get((Object)p) + 1);
                } else {
                    lootboxes.put(p, 1);
                }
                p.sendMessage(CC.Green + "Your inventory was full, so your lootbox has been transferred to your /claim until the next reboot!");
                continue;
            }
            p.sendMessage(CC.Green + "You received a lootbox from the give all!");
            p.getInventory().addItem(new ItemStack[]{giveAllItem});
            nextGiveAll = 1800L;
        }
    }

    public static int getAvailableLootboxes(Player player) {
        return lootboxes.get((Object)player);
    }

    public static boolean hasAvailableLootboxes(Player player) {
        return lootboxes.containsKey((Object)player);
    }

    public static void setAvailableLootboxes(Player player, int amount) {
        lootboxes.put(player, amount);
    }

    public static ItemStack getGiveAllItem() {
        return giveAllItem;
    }
}

