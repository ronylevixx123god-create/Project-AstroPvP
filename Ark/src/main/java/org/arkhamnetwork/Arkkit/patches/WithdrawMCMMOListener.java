/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  com.gmail.nossr50.datatypes.player.PlayerProfile
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  com.gmail.nossr50.util.player.UserManager
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.player.PlayerProfile;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.util.player.UserManager;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.DecimalFormat;

final class WithdrawMCMMOListener
        implements Listener {
    private static final DecimalFormat format = new DecimalFormat("#,###");
    private final String xpGainReason = "UNKNOWN";

    WithdrawMCMMOListener() {
    }

    @EventHandler
    public void onPlayerUseSkillCredit(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            long itemID;
            int xpValue = WithdrawMCMMO.getMCMMOXPCreditValue(e.getItem());
            SkillType type = WithdrawMCMMO.getMCMMOXPCreditType(e.getItem());
            if (xpValue <= 0) {
                return;
            }
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            if (ExperienceAPI.getLevel(e.getPlayer(), type.name()) >= ExperienceAPI.getLevelCap(type.name())) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot add more EXP to " + type.name() + ", as it would exceed the level cap.");
                return;
            }
            if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID(e.getItem())) != -1L) {
                ItemID.blacklistIID(itemID, "mcMMOLevelRedeem");
            }
            WithdrawMCMMOCommand.addXPAndLevel(e.getPlayer(), type.name(), xpValue);
            e.getPlayer().setItemInHand(null);
            e.getPlayer().updateInventory();
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 0.33f);
            if (ExperienceAPI.getLevel(e.getPlayer(), type.name()) < ExperienceAPI.getLevelCap(type.name())) {
                e.getPlayer().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "+" + format.format(xpValue) + " " + type.name() + " XP!");
            } else {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You added more EXP to " + type.name() + ", than the max level cap, your level was adjusted to " + ExperienceAPI.getLevelCap(type.name()));
                int extraXp = this.getXPAboveMaxLevel(e.getPlayer(), type.name());
                ExperienceAPI.setLevel(e.getPlayer(), type.name(), ExperienceAPI.getLevelCap(type.name()));
                ExperienceAPI.setXP(e.getPlayer(), type.name(), 0);
                if (e.getPlayer().getInventory().firstEmpty() != -1) {
                    e.getPlayer().getInventory().addItem(WithdrawMCMMO.getMCMMOXpCert(extraXp, type.name(), e.getPlayer().getName()));
                } else {
                    e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), WithdrawMCMMO.getMCMMOXpCert(extraXp, type.name(), e.getPlayer().getName()));
                }
            }
            PlayerProfile pp = UserManager.getPlayer(e.getPlayer()).getProfile();
            pp.changed = true;
            pp.scheduleAsyncSave();
        }
    }

    private int getXPAboveMaxLevel(Player p, String skillType) {
        int level = ExperienceAPI.getLevel(p, skillType);
        int maxLevel = ExperienceAPI.getLevelCap(skillType);
        int extraXp = 0;
        while (level > maxLevel) {
            extraXp += ExperienceAPI.getXpNeededToLevel(--level);
        }
        return extraXp + ExperienceAPI.getXP(p, skillType);
    }
}

