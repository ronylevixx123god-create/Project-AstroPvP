/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.decimatemc.decimatelootboxes.api.Lootbox
 *  com.decimatemc.decimatelootboxes.api.PluginLootboxManager
 *  com.decimatemc.decimatelootboxes.api.RewardItem
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicDungeons.cmds;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.decimatemc.decimatelootboxes.api.Lootbox;
import com.decimatemc.decimatelootboxes.api.PluginLootboxManager;
import com.decimatemc.decimatelootboxes.api.RewardItem;
import java.util.List;
import java.util.Optional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandLootboxRefresh
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("refresh")) {
            PluginLootboxManager lootboxManager = CosmicDungeonsPlugin.get().getLootboxManager();
            List lootboxes = lootboxManager.getLootboxes();
            for (int i = lootboxes.size() - 1; i >= 0; --i) {
                Lootbox lootbox = (Lootbox)lootboxes.get(i);
                List items = lootbox.getRandomRewards();
                for (int j = items.size() - 1; j >= 0; --j) {
                    ItemStack stack = ((RewardItem)items.get(j)).getItem();
                    if (stack == null) {
                        items.remove(j);
                        continue;
                    }
                    Optional l = lootboxManager.getLootboxFrom(stack);
                    if (l != null && l.isPresent()) {
                        Lootbox lo = (Lootbox)l.get();
                        RewardItem rItem = (RewardItem)items.get(j);
                        if (lo.getDisplayItem().getItemMeta().hasDisplayName()) {
                            rItem.setDisplayName(lo.getDisplayItem().getItemMeta().getDisplayName());
                        }
                        if (!lo.getDisplayItem().getItemMeta().hasLore()) continue;
                        ItemStack reward = rItem.getReward();
                        ItemMeta meta = reward.getItemMeta();
                        meta.setLore(lo.getDisplayItem().getItemMeta().getLore());
                        reward.setItemMeta(meta);
                        continue;
                    }
                    if (!new NBTWrapper(stack).getCompoundOrCreate(lootboxManager.getNBTCompound()).getBoolean(lootboxManager.getNBTIsLootbox()).booleanValue() || l != null && l.isPresent()) continue;
                    items.remove(j);
                }
            }
            sender.sendMessage(CC.translate((String)"&aDone!"));
        }
        return false;
    }
}

