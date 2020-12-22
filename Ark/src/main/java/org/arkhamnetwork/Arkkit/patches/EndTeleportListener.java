/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicFund.CosmicFundAPI
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.commands.WarpNotFoundException
 *  com.massivecraft.factions.event.LandClaimEvent
 *  net.ess3.api.InvalidWorldException
 *  org.arkhamnetwork.enchantments.Enchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerPortalEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.event.player.PlayerToggleFlightEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicFund.CosmicFundAPI;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.WarpNotFoundException;
import com.massivecraft.factions.event.LandClaimEvent;
import java.util.UUID;
import net.ess3.api.InvalidWorldException;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class EndTeleportListener
implements Listener {
    Location endWarp;

    EndTeleportListener() {
        try {
            if (EssentialsWarpUI.ess == null) {
                EssentialsWarpUI.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
            }
            this.endWarp = EssentialsWarpUI.ess.getWarps().getWarp("end");
            Bukkit.getLogger().info("[EndGrinding] Set endWarp spawn location to: " + (Object)this.endWarp);
        }
        catch (WarpNotFoundException | InvalidWorldException e) {
            this.endWarp = Bukkit.getWorld((String)"world_end").getSpawnLocation();
            e.printStackTrace();
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerPortal(PlayerPortalEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && e.getFrom().getWorld().getName().equals("world_end") && WorldGuardUtils.getRegionName(e.getFrom()).equals("end_exit")) {
            Player p = e.getPlayer();
            e.setCancelled(true);
            if (p.hasMetadata("exitPortalMovementLock")) {
                return;
            }
            Location tp = Bukkit.getWorld((String)"world").getSpawnLocation();
            p.setMetadata("exitPortalMovementLock", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)p.getLocation()));
            boolean owned = false;
            try {
                owned = CosmicOutpostsAPI.controlsCosmonautOutpost((Player)p);
                if (owned) {
                    p.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                    if (CosmicUtils.get().isEnchantmentsEnabled()) {
                        p.setMetadata("bypassCombatTeleport", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)tp));
                    }
                    p.setMetadata("queuedTeleportPass", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)tp));
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            p.teleport(tp, PlayerTeleportEvent.TeleportCause.PLUGIN);
            if (owned) {
                p.removeMetadata("ignore_combat_tag", (Plugin)Arkkit.get());
                if (CosmicUtils.get().isEnchantmentsEnabled()) {
                    p.removeMetadata("bypassCombatTeleport", (Plugin)Enchantments.getInstance());
                }
                p.removeMetadata("queuedTeleportPass", (Plugin)Arkkit.get());
            }
        }
    }

    @EventHandler
    public void onPlayerMoveInExitPortal(PlayerMoveEvent e) {
        Location from = e.getPlayer().getLocation();
        if ((from.getX() != e.getTo().getX() || from.getY() != e.getTo().getY() || from.getZ() != e.getTo().getZ()) && e.getPlayer().hasMetadata("exitPortalMovementLock")) {
            e.getPlayer().removeMetadata("exitPortalMovementLock", (Plugin)Arkkit.get());
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if ((e.getBlock().getType() == Material.AIR || e.getBlock().getType() == Material.ENDER_PORTAL) && e.getBlock().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerEnterVoid(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID && e.getEntity() instanceof Player && e.getEntity().getLocation().getY() < 0.0 && e.getEntity().getWorld().getName().equals("world_end") && !e.getEntity().isDead()) {
            Player p = (Player)e.getEntity();
            p.closeInventory();
            final Location l = p.getLocation();
            l.setY(0.0);
            if (CombatLog.inCombat(p)) {
                e.setCancelled(true);
                e.setDamage(0.0);
                if (p.hasMetadata("lastPlayerDamagerUUID")) {
                    Player pKiller = Bukkit.getPlayer((UUID)((UUID)((MetadataValue)p.getMetadata("lastPlayerDamagerUUID").get(0)).value()));
                    if (pKiller != null && pKiller.isOnline()) {
                        try {
                            for (ItemStack i : p.getInventory()) {
                                if (i == null || i.getType() == Material.AIR) continue;
                                pKiller.getWorld().dropItem(pKiller.getLocation().add(0.0, 1.0, 0.0), i);
                            }
                            for (ItemStack i : p.getInventory().getArmorContents()) {
                                if (i == null || i.getType() == Material.AIR) continue;
                                pKiller.getWorld().dropItem(pKiller.getLocation().add(0.0, 1.0, 0.0), i);
                            }
                            if (!pKiller.hasMetadata("knocked" + p.getName() + "OffEdge")) {
                                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("contest add " + pKiller.getUniqueId() + " endKnockoff 1"));
                                pKiller.setMetadata("knocked" + p.getName() + "OffEdge", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                            }
                        }
                        catch (Exception err) {
                            err.printStackTrace();
                        }
                        p.getInventory().clear();
                        p.getInventory().setHelmet(new ItemStack(Material.AIR));
                        p.getInventory().setChestplate(new ItemStack(Material.AIR));
                        p.getInventory().setLeggings(new ItemStack(Material.AIR));
                        p.getInventory().setBoots(new ItemStack(Material.AIR));
                        if (p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName().equals("container.crafting")) {
                            p.getOpenInventory().getTopInventory().clear();
                        }
                        p.setHealth(0.0);
                        p.sendMessage((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "*** You were AUTO-KILLED and TELEPORTED to the LAST PLAYER WHO DAMAGED YOU! ***");
                        p.sendMessage((Object)ChatColor.GRAY + "This happens when you enter the Void in the End. You surrender.");
                        pKiller.sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + p.getName() + (Object)ChatColor.GREEN + " died in the void, and you were the last player to damage them. So their loot is yours!");
                    } else {
                        p.setHealth(0.0);
                    }
                } else {
                    p.setHealth(0.0);
                }
            } else {
                p.setHealth(0.0);
            }
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    l.getBlock().setType(Material.AIR);
                }
            }, 20L);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().getWorld().getName().equals("world_end") && e.getPlayer().getLocation().getY() <= 25.0) {
            e.setCancelled(true);
            e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot drop items while falling into the void.");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onLandClaimInEnd_LOWEST(LandClaimEvent e) {
        if (e.getLocation().getWorld().getEnvironment() == World.Environment.THE_END && !e.getPlayer().isOp()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot claim land in The End dimension.");
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onLandClaimInEnd_MONITOR(LandClaimEvent e) {
        if (e.getLocation().getWorld().getEnvironment() == World.Environment.THE_END && !e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        if (e.isFlying() && !e.getPlayer().hasPermission("arkkit.staff.hidden") && e.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setCancelled(true);
            e.getPlayer().setAllowFlight(false);
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END) {
            if (EndGrinding.fundEnabled && !EndGrinding.hcf && !CosmicFundAPI.isEndEnabled() && !e.getPlayer().isOp()) {
                e.getPlayer().teleport(((World)Bukkit.getWorlds().get(0)).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                return;
            }
            e.getPlayer().setFlying(false);
            e.getPlayer().setAllowFlight(false);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if ((cmd.equals("/f warp") || cmd.startsWith("/f warp") || cmd.equals("/warp koth") || cmd.startsWith("/f allywarp") || cmd.equals("/f listwarps") || cmd.equals("/f setwarp") || cmd.equals("/f home") || cmd.startsWith("/f setwarp")) && e.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setMessage("/null");
            e.setCancelled(true);
        }
        if (cmd.equals("/spawn") && e.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setCancelled(true);
            e.getPlayer().sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + "Trying to leave The End? The only way out is through the Exit Portal, located across the map from the spawn platform!");
        }
        if (cmd.equals("/warp end") && EndGrinding.fundEnabled && !EndGrinding.hcf && !CosmicFundAPI.isEndEnabled() && !e.getPlayer().isOp()) {
            e.setCancelled(true);
            e.setMessage("/null");
            e.getPlayer().sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "Your planet has not yet unlocked access to the End Dimension. To unlock, your planet needs to reach the " + (Object)ChatColor.UNDERLINE + "$75m /fund" + (Object)ChatColor.RED + " goal.");
            e.getPlayer().sendMessage((Object)ChatColor.GRAY + "* Until the goal is met, NO PLAYERS can use /warp end.");
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerFallDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }
}

