package org.arkhamnetwork.enchantments.enchantments.tools;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;
import com.rit.sucy.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.inventory.*;
import com.massivecraft.factions.*;

public class DetonateLegacy extends CustomEnchantment
{
    public DetonateLegacy() {
        super("Detonate", GeneralUtil.tools, 6);
        this.max = 9;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            final BlockBreakEvent bbe = (BlockBreakEvent)event;
            final double chance = enchantLevel * 0.1;
            if (bbe.isCancelled()) {
                return;
            }
            if (Math.random() < chance) {
                this.explode(player, bbe.getBlock(), enchantLevel);
            }
        }
    }
    
    private void newExplode(final Player pl, final Block brokenBlock, final int level) {
        final boolean aboveUs = brokenBlock.getLocation().getY() > pl.getEyeLocation().getY();
        final boolean underUs = brokenBlock.getLocation().getY() < pl.getLocation().getY();
        final boolean vertical = aboveUs || underUs;
        final boolean horizontal = !vertical;
        final boolean hasAntiSmelt = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Anti Smelt");
        final boolean telepathy = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Telepathy");
        int yMod = 1;
        if (level >= 5) {
            yMod = 2;
        }
        if (level >= 8) {
            yMod = 3;
        }
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                if (x != 0 || z != 0) {
                    final Block blockExploding = null;
                    final Material m = blockExploding.getType();
                    if (m != Material.LADDER && m != Material.GLOWSTONE && m != Material.BEDROCK && m != Material.WATER && m != Material.STATIONARY_WATER && m != Material.LAVA && m != Material.STATIONARY_LAVA && m != Material.SIGN && m != Material.SIGN_POST && m != Material.AIR && m != Material.BEDROCK && m != Material.OBSIDIAN) {
                        if (m != Material.SPONGE) {
                            final FLocation floc = new FLocation(blockExploding.getLocation());
                            final Faction f = Board.getFactionAt(floc);
                            if (!f.isNone()) {
                                final FactionWrapper wrapper = FactionWrappers.get(f);
                                final FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
                                if (wrapper.isLocationTracked(floc)) {
                                    if (!wrapper.doesPlayerHaveChunkAccess(floc, fp, true)) {
                                        continue;
                                    }
                                }
                                else if (!fp.getRelationToLocation().isMember()) {
                                    continue;
                                }
                            }
                            if (!f.isWarZone()) {
                                ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, blockExploding.getLocation().add(0.0, 0.5, 0.0), 0.025f, 1);
                                if (hasAntiSmelt) {
                                    if (m == Material.IRON_ORE) {
                                        continue;
                                    }
                                    if (m == Material.GOLD_ORE) {
                                        continue;
                                    }
                                }
                                if (blockExploding.getDrops(pl.getItemInHand()).size() > 0 && telepathy) {
                                    ItemStack loot = null;
                                    try {
                                        loot = (ItemStack)blockExploding.getDrops(pl.getItemInHand()).toArray()[0];
                                    }
                                    catch (Exception err) {
                                        continue;
                                    }
                                    if (pl.getInventory().firstEmpty() != -1) {
                                        pl.getInventory().addItem(new ItemStack[] { loot });
                                    }
                                    else {
                                        pl.getWorld().dropItem(pl.getLocation(), loot);
                                    }
                                    blockExploding.setTypeIdAndData(0, (byte)0, false);
                                }
                                else {
                                    blockExploding.breakNaturally(pl.getItemInHand());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void explode(final Player pl, final Block b, final int level) {
        int max_blocks_to_explode = level;
        final boolean hasAntiSmelt = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Anti Smelt");
        final boolean telepathy = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Telepathy");
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                for (int y = -1; y <= 1 && max_blocks_to_explode > 0; ++y) {
                    if (x == 0 && y == 0 && z == 0) {
                        --max_blocks_to_explode;
                    }
                    else {
                        final Block bl = b.getLocation().add((double)x, (double)y, (double)z).getBlock();
                        final Material m = bl.getType();
                        if (m != Material.LADDER && m != Material.GLOWSTONE && m != Material.BEDROCK && m != Material.WATER && m != Material.STATIONARY_WATER && m != Material.LAVA && m != Material.STATIONARY_LAVA && m != Material.SIGN && m != Material.SIGN_POST && m != Material.AIR && m != Material.BEDROCK && m != Material.OBSIDIAN) {
                            if (m != Material.SPONGE) {
                                final FLocation floc = new FLocation(bl.getLocation());
                                final Faction f = Board.getFactionAt(floc);
                                if (!f.isNone()) {
                                    final FactionWrapper wrapper = FactionWrappers.get(f);
                                    final FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
                                    if (wrapper.isLocationTracked(floc)) {
                                        if (!wrapper.doesPlayerHaveChunkAccess(floc, fp, true)) {
                                            continue;
                                        }
                                    }
                                    else if (!fp.getRelationToLocation().isMember()) {
                                        continue;
                                    }
                                }
                                if (!f.isWarZone()) {
                                    ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, bl.getLocation().add(0.0, 0.5, 0.0), 0.025f, 1);
                                    if (hasAntiSmelt) {
                                        if (m == Material.IRON_ORE) {
                                            continue;
                                        }
                                        if (m == Material.GOLD_ORE) {
                                            continue;
                                        }
                                    }
                                    if (bl.getDrops(pl.getItemInHand()).size() > 0 && telepathy) {
                                        ItemStack loot = null;
                                        try {
                                            loot = (ItemStack)bl.getDrops(pl.getItemInHand()).toArray()[0];
                                        }
                                        catch (Exception err) {
                                            continue;
                                        }
                                        if (pl.getInventory().firstEmpty() != -1) {
                                            pl.getInventory().addItem(new ItemStack[] { loot });
                                        }
                                        else {
                                            pl.getWorld().dropItem(pl.getLocation(), loot);
                                        }
                                        bl.setTypeIdAndData(0, (byte)0, false);
                                    }
                                    else {
                                        bl.breakNaturally(pl.getItemInHand());
                                    }
                                    --max_blocks_to_explode;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
