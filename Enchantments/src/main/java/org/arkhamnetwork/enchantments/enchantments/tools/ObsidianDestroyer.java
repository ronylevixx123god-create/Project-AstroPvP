/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.CoreProtectUtils
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.net.astropvp.armorsets.struct.FactionPermission
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.tools;

import com.cosmicpvp.cosmicutils.utils.CoreProtectUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.*;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class ObsidianDestroyer
extends CustomEnchantment {
    public ObsidianDestroyer() {
        super("Obsidian Destroyer", GeneralUtil.pickaxe, 6);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyMiscEffect(final Player player, int enchantLevel, final PlayerInteractEvent event) {
        final Player pl = player;
        final double level = enchantLevel;
        if (pl.getWorld().getEnvironment() == World.Environment.THE_END) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.hasBlock() && event.getClickedBlock().getType() == Material.OBSIDIAN) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    FPlayer fplayer;
                    FLocation faction_loc = new FLocation(event.getClickedBlock().getLocation());
                    Faction l_faction = FactionUtils.getFactionAt((FLocation)faction_loc);
                    boolean can_build = false;
                    if (l_faction == null || l_faction.isNone()) {
                        can_build = true;
                    }
                    if (!can_build && l_faction.playerHasOwnershipRights(fplayer = (FPlayer)FPlayers.getInstance().getByPlayer(player), faction_loc)) {
                        can_build = true;
                    }
                    if (!can_build || l_faction.isWarZone()) {
                        return;
                    }
                    double chance = level * 20.0 / 100.0;
                    if (Math.random() < chance) {
                        Bukkit.getScheduler().runTask((Plugin)Enchantments.getInstance(), new Runnable(){

                            @Override
                            public void run() {
                                Block b = event.getClickedBlock();
                                CoreProtectUtils.logBreak((String)pl.getName(), (Location)b.getLocation(), (int)b.getType().getId(), (byte)b.getData());
                                b.breakNaturally(player.getItemInHand());
                            }
                        });
                    }
                }
            });
        }
    }
}

