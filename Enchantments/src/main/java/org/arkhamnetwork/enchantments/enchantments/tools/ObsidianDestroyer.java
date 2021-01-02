package org.arkhamnetwork.enchantments.enchantments.tools;

import com.cosmicpvp.cosmicutils.utils.CoreProtectUtils;
import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.FactionPermission;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class ObsidianDestroyer extends CustomEnchantment {
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
            Bukkit.getScheduler().runTaskAsynchronously((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                @Override
                public void run() {
                    final FLocation faction_loc = new FLocation(event.getClickedBlock().getLocation());
                    final Faction l_faction = Board.getFactionAt(faction_loc);
                    boolean can_build = false;
                    if (l_faction == null || l_faction.isNone()) {
                        can_build = true;
                    }
                    if (!can_build) {
                        final FPlayer fplayer = (FPlayer) FPlayers.i.get((OfflinePlayer) player);
                        if (l_faction.playerHasOwnershipRights(fplayer, faction_loc) && l_faction.hasPermission(fplayer, faction_loc, FactionPermission.OBBY, "destroy")) {
                            can_build = true;
                        }
                    }
                    if (!can_build || l_faction.isWarZone()) {
                        return;
                    }
                    final double chance = level * 20.0 / 100.0;
                    if (Math.random() < chance) {
                        Bukkit.getScheduler().runTask((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
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
