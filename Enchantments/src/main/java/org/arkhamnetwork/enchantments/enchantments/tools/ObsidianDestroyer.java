package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.*;
import com.massivecraft.factions.struct.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.block.*;
import org.bukkit.plugin.*;
import com.massivecraft.factions.*;

public class ObsidianDestroyer extends CustomEnchantment
{
    public ObsidianDestroyer() {
        super("Obsidian Destroyer", GeneralUtil.pickaxe, 6);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyMiscEffect(final Player player, final int enchantLevel, final PlayerInteractEvent event) {
        final Player pl = player;
        final double level = enchantLevel;
        if (pl.getWorld().getEnvironment() == World.Environment.THE_END) {
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.hasBlock() && event.getClickedBlock().getType() == Material.OBSIDIAN) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    final FLocation faction_loc = new FLocation(event.getClickedBlock().getLocation());
                    final Faction l_faction = Board.getFactionAt(faction_loc);
                    boolean can_build = false;
                    if (l_faction == null || l_faction.isNone()) {
                        can_build = true;
                    }
                    if (!can_build) {
                        final FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                        if (l_faction.playerHasOwnershipRights(fplayer, faction_loc) && l_faction.hasPermission(fplayer, faction_loc, FactionPermission.OBBY, "destroy")) {
                            can_build = true;
                        }
                    }
                    if (!can_build || l_faction.isWarZone()) {
                        return;
                    }
                    final double chance = level * 20.0 / 100.0;
                    if (Math.random() < chance) {
                        Bukkit.getScheduler().runTask((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                final Block b = event.getClickedBlock();
                                CoreProtectUtils.logBreak(pl.getName(), b.getLocation(), b.getType().getId(), b.getData());
                                b.breakNaturally(player.getItemInHand());
                            }
                        });
                    }
                }
            });
        }
    }
}
