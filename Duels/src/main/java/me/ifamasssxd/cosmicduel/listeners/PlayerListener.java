/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.EnumClientCommand
 *  net.minecraft.server.v1_7_R4.PacketPlayInClientCommand
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicduel.listeners;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.*;
import me.ifamasssxd.cosmicduel.struct.enums.*;
import me.ifamasssxd.cosmicduel.struct.newduel.GlobalQueuePopInventory;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.tasks.DuelArenaTask;
import me.ifamasssxd.cosmicduel.utils.CollectionBin;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import me.ifamasssxd.cosmicduel.utils.WorldGuardUtils;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerListener
implements Listener {
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().startsWith("world_duel") && DuelArenaTask.arena_regions.contains(WorldGuardUtils.getRegionName(p.getLocation()))) {
            p.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
            p.teleport(((World)Bukkit.getWorlds().get(0)).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            p.removeMetadata("ignore_combat_tag", (Plugin)CosmicDuel.get());
            p.sendMessage((Object)ChatColor.RED + "You were inside of a duel arena without a duel.");
        }
        if (CollectionBin.getCollectionItems(e.getPlayer()).size() > 0) {
            new BukkitRunnable(){

                public void run() {
                    if (e.getPlayer() != null && e.getPlayer().isOnline()) {
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0f, 0.4f);
                        e.getPlayer().sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "You have items inside of your " + (Object)ChatColor.YELLOW + "/duel collect" + (Object)ChatColor.GREEN + " bin.");
                    }
                }
            }.runTaskLater((Plugin)CosmicDuel.get(), 60L);
        }
        new DuelPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName()).loadData(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        NewDuel duel;
        Player player;
        e.setQuitMessage((String)null);
        if (e.getPlayer().getOpenInventory() != null && e.getPlayer().getOpenInventory().getTopInventory() != null && e.getPlayer().getOpenInventory().getTopInventory().getName().endsWith("'s Death Loot")) {
            e.getPlayer().getOpenInventory().getTopInventory().clear();
            Bukkit.getLogger().info("Cleared the exploited duel inventory for " + e.getPlayer().getName());
        }
        if ((player = e.getPlayer()).hasMetadata("in_ranked_queue_menu") && player.getMetadata("in_ranked_queue_menu").size() > 0) {
            GlobalQueuePopInventory queue = (GlobalQueuePopInventory)((MetadataValue)player.getMetadata("in_ranked_queue_menu").get(0)).value();
            if (queue != null) {
                queue.declineDuel(player, true, true);
            } else {
                player.removeMetadata("in_ranked_queue_menu", (Plugin)CosmicDuel.get());
                player.sendMessage((Object)ChatColor.RED + "Your duel queue menu data has been cleared!");
            }
        }
        if ((duel = DuelAPI.getDuel(player)) != null) {
            if (DuelAPI.isDuelQueued(duel)) {
                DuelAPI.cancelDuel(duel, CancelReason.LEFT_IN_QUEUE);
                return;
            }
            duel.removePlayer(player, Reason.LEFT_DUEL, false);
        } else {
            DuelPlayer duelPlayer;
            DuelInvite duelInvite;
            duel = DuelAPI.getDuelSpectating(player);
            if (duel != null) {
                duel.removeSpectator(player);
                return;
            }
            QueueManager queueManager = CosmicDuel.get().getQueueManager();
            NewDuel globalQueuedDuel = queueManager.getDuel(player);
            if (globalQueuedDuel != null) {
                queueManager.removeDuelFromQueue(globalQueuedDuel);
            }
            if (queueManager.isSearching(player)) {
                queueManager.removePlayerFromQueue(player);
            }
            if (queueManager.getRankedQueue().isInRankedQueue(player)) {
                queueManager.getRankedQueue().removeFromQueue(player);
            }
            if ((duelInvite = DuelAPI.getDuelInvite(player, DuelAPI.InviteType.INVITED)) != null) {
                DuelAPI.declineDuel(player, duelInvite.getInviter(), false);
                return;
            }
            duelInvite = DuelAPI.getDuelInvite(player, DuelAPI.InviteType.INVITER);
            if (duelInvite != null) {
                DuelAPI.invite_timer.remove(duelInvite);
            }
            if ((duelPlayer = DuelPlayer.getDuelPlayer(player)) != null) {
                duelPlayer.saveData(true);
                duelPlayer.saveRankedStats(true);
                DuelPlayer.removeDuelPlayer(player);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().hasMetadata("clear_dropped_items")) {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (DuelAPI.hasDuel(player)) {
            NewDuel d = DuelAPI.getDuel(player);
            if (d != null && d.getCreateState() == DuelCreateState.STAKES) {
                return;
            }
            if (d != null && d.getDuelState() == DuelState.PLAYING) {
                d.removePlayer(player, Reason.DEAD, true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        NewDuel d;
        final Player p = e.getEntity();
        if (DuelAPI.hasDuel(p) && (d = DuelAPI.getDuel(p)) != null && DuelAPI.isDuelValid(d)) {
            if (!PlayerUtils.isInventoryEmpty(p) && !d.getEndingInventoryCache().containsKey(p.getName())) {
                UUID uuid = CosmicDuel.get().getDuelHistoryManager().storeInventorySnapshot(p);
                d.getEndingInventoryCache().put(p.getName(), uuid);
            }
            for (ItemStack is : e.getDrops()) {
                e.getDrops().remove((Object)is);
            }
            e.setKeepLevel(true);
            System.out.println("EXP: " + p.getExp() + " Level: " + p.getLevel());
            new BukkitRunnable(){

                public void run() {
                    ((CraftPlayer)p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                }
            }.runTaskLater((Plugin)CosmicDuel.get(), 2L);
        }
    }

    @EventHandler
    public void onPlayerSoup(PlayerInteractEvent e) {
        Player p;
        if (e.hasItem() && e.getItem().getType() == Material.MUSHROOM_SOUP && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && DuelAPI.hasDuel(p = e.getPlayer())) {
            NewDuel d = DuelAPI.getDuel(p);
            if (!d.getSettings().hasSetting(Setting.HEALING)) {
                e.setCancelled(true);
                p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Your duel has Healing " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "disabled.");
                return;
            }
            ItemStack is = e.getItem();
            e.setCancelled(true);
            if (is.getAmount() >= 1) {
                p.setItemInHand(null);
                p.updateInventory();
            } else {
                is.setAmount(is.getAmount() - 1);
                p.setItemInHand(is);
                p.updateInventory();
            }
            if (PlayerUtils.getHealth(p) + 7.0 >= PlayerUtils.getMaxHealth(p)) {
                p.setHealth(PlayerUtils.getMaxHealth(p));
            } else {
                p.setHealth(PlayerUtils.getHealth(p) + 7.0);
            }
            p.getWorld().playSound(p.getLocation(), Sound.EAT, 1.0f, 1.2f);
            p.updateInventory();
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        NewDuel duel;
        Player ent;
        Player damager;
        if (e.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0 && e.getEntity() instanceof Player && (e.getDamager() instanceof Player || e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) && DuelAPI.inSameDuel(damager = (Player)(e.getDamager() instanceof Projectile ? ((Projectile)e.getDamager()).getShooter() : e.getDamager()), ent = (Player)e.getEntity()) && (duel = DuelAPI.getDuel(ent)).getDuelState() == DuelState.PLAYING && duel.getCreateState() == DuelCreateState.DONE) {
            e.setCancelled(false);
            if (!e.isCancelled()) {
                NewDuel newDuel = duel;
                duel.getClass();
                newDuel.setPvpTimer(120);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerDamageLow(EntityDamageByEntityEvent e) {
        NewDuel d;
        Player ent;
        Player damager;
        if (e.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0 && e.getEntity() instanceof Player && (e.getDamager() instanceof Player || e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) && DuelAPI.inSameDuel(damager = (Player)(e.getDamager() instanceof Projectile ? ((Projectile)e.getDamager()).getShooter() : e.getDamager()), ent = (Player)e.getEntity()) && (d = DuelAPI.getDuel(ent)).getDuelState() == DuelState.PLAYING && d.getCreateState() == DuelCreateState.DONE) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void onPlayerDeath(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (!e.isCancelled() && p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName().equalsIgnoreCase("Select an Arena")) {
                p.closeInventory();
            }
            if (p.hasMetadata("in_ranked_queue_menu") && p.getMetadata("in_ranked_queue_menu").size() > 0 && !e.isCancelled()) {
                e.setCancelled(true);
                GlobalQueuePopInventory queue = (GlobalQueuePopInventory)((MetadataValue)p.getMetadata("in_ranked_queue_menu").get(0)).value();
                if (queue != null) {
                    queue.declineDuel(p, true, true);
                    return;
                }
            }
            if (DuelAPI.hasDuel(p)) {
                NewDuel d = DuelAPI.getDuel(p);
                if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    e.setDamage(PlayerUtils.getMaxHealth(p));
                } else {
                    if (d.getDuelState() != DuelState.PLAYING && d.getCreateState() == DuelCreateState.DONE) {
                        e.setCancelled(true);
                        return;
                    }
                    if (d.getCreateState() == DuelCreateState.STAKES && !e.isCancelled()) {
                        e.setCancelled(true);
                        e.setDamage(0.0);
                        p.closeInventory();
                        return;
                    }
                    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (e.getFinalDamage() >= p.getHealth() && !e.isCancelled()) {
                    e.setCancelled(true);
                    e.setDamage(0.0);
                    for (PotionEffect pe : p.getActivePotionEffects()) {
                        p.removePotionEffect(pe.getType());
                    }
                    p.setHealth(PlayerUtils.getMaxHealth(p));
                    d.removePlayer(p, Reason.DEAD, true);
                    return;
                }
                if (!e.isCancelled()) {
                    p.setMetadata("last_damage", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2L))));
                }
                if (d.getGameTimer() >= 300 && !e.isCancelled()) {
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 2.0);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onCommandPreProccess(PlayerTeleportEvent e) {
        if (DuelAPI.hasDuel(e.getPlayer())) {
            NewDuel d = DuelAPI.getDuel(e.getPlayer());
            if (d.getDuelState() == DuelState.IN_QUEUE) {
                if (e.getTo() != null && e.getTo().getWorld().getName().equals(Arena.getDuelWorldName())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot teleport to the duel world while in queue.");
                }
            } else if (d.getDuelState() != DuelState.ENDED && d.getDuelState() != DuelState.ENDING && !e.getPlayer().isOp() && e.getTo().getWorld() != e.getFrom().getWorld() && e.getFrom().getWorld().getName().equals(Arena.getDuelWorldName())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot leave the duel world while in a duel!");
            }
        } else if (!DuelAPI.isSpectator(e.getPlayer()) && !e.getPlayer().isOp() && !DuelAPI.hasDuel(e.getPlayer()) && e.getFrom() != null && e.getFrom().getWorld() != e.getTo().getWorld() && e.getTo() != null && e.getTo().getWorld() != null && e.getTo().getWorld().getName().equals(Arena.getDuelWorldName())) {
            e.setCancelled(true);
        }
    }

    public Player getLastPlayerToDamage(Player p) {
        EntityDamageByEntityEvent event;
        if (p != null && p.getLastDamageCause() != null && p.getLastDamageCause() instanceof EntityDamageByEntityEvent && (event = (EntityDamageByEntityEvent)p.getLastDamageCause()).getDamager() instanceof Player) {
            Player killer = (Player)event.getDamager();
            return killer;
        }
        return null;
    }
}

