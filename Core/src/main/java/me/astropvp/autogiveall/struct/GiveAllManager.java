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
package me.astropvp.autogiveall.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectType;
import net.astropvp.struct.PlayerCollect;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class GiveAllManager {
    private static long nextGiveAll = 2400;
    private static MysteryChest giveAllItem = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("MysteryLootbox");

    public static long getNextGiveAll() {
        return nextGiveAll;
    }

    public static void setNextGiveAll(long time) {
        nextGiveAll = time;
    }

    public static void doGiveAll() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            PlayerCollect collect = CollectPlugin.getInstance().getCollectManager().getPlayerCollect(p);
                boolean b = false;
                CollectCategory giveall = null;
                for(CollectCategory cat : collect.getCategories()) {
                    if(cat.getType() == CollectType.GIVEALL) {
                        b = true;
                        giveall = cat;
                        break;
                    }
                }
                if(b) {
                    ItemStack[] items = giveall.getItems();
                    ItemStack[] newarray = new ItemStack[items.length];
                    for(int i = 0; i < items.length; i++) {
                        newarray[i] = items[i];
                    }
                    newarray[newarray.length - 1] = giveAllItem.createClaimableItem(true);
                    giveall.setItems(newarray);
                } else {
                    ItemStack[] items = new ItemStack[0];
                    items[0] = giveAllItem.createClaimableItem(false);
                    collect.add(new CollectCategory(CollectType.GIVEALL, items, CC.YellowB + "Giveall Lootboxes"));
                }
            nextGiveAll = 2400;
        }
    }

    public static ItemStack getGiveAllItem() {
        return giveAllItem.createClaimableItem(true);
    }
}

