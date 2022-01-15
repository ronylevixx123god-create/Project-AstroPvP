/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.TimeUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.enchanting.EListener
 *  org.arkhamnetwork.Arkkit.patches.CombatLog
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicOutposts.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.enchanting.EListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class OutpostSanityListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().getType() == Material.WEB && e.getPlayer().getWorld().getName().startsWith("cosmic-station-")) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.getPlayer().setItemInHand((ItemStack)null);
            e.getPlayer().sendMessage("" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "The Astro Outpost absorbs your cobweb blocks!");
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (e.getBlock().getWorld().getName().startsWith("cosmic-station")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && CosmicOutpostsAPI.getOutpost(e.getEntity()) != null) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerChangeFactionName(PlayerCommandPreprocessEvent e) {
        Player p;
        Faction f;
        if (e.getMessage().startsWith("/f tag") && (f = ((FPlayer)FPlayers.i.get((OfflinePlayer)(p = e.getPlayer()))).getFaction()).isNormal()) {
            for (Outpost o : CosmicKOTHPlugin.get_().getOutposts()) {
                if (!(o.getControllingFaction() != null && o.getControllingFaction().equals(f.getTag()) || o.getCapTask().getAttackingFaction() != null && o.getCapTask().getAttackingFaction().equals((Object)f)) && (o.getCapTask().getDefendingFaction() == null || !o.getCapTask().getDefendingFaction().equals((Object)f))) continue;
                e.setCancelled(true);
                e.setMessage("/null");
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot use /f tag while your faction is controlling, attacking, or defending a Cosmic /outpost.");
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByVoid(EntityDamageEvent e) {
        Player p;
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID && e.getEntity() instanceof Player && CosmicOutpostsAPI.getOutpostTierOfWorld(e.getEntity()) != OutpostTier.NONE && (p = (Player)e.getEntity()).getLocation().getY() < 0.0) {
            if (CombatLog.inCombat((Player)p)) {
                p.damage(p.getHealth());
                if (p.getHealth() > 0.0) {
                    p.setHealth(0.0);
                }
            } else {
                e.setCancelled(true);
                e.setDamage(0.0);
                p.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicKOTHPlugin.get_(), (Object)true));
                try {
                    Outpost o = CosmicOutpostsAPI.getOutpost((Entity)p);
                    if (CosmicOutpostsAPI.isDefender(p, o)) {
                        p.teleport(CosmicOutpostsAPI.getOutpost((Entity)p).getDefenderSpawn(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    } else {
                        p.teleport(CosmicOutpostsAPI.getOutpost((Entity)p).getAttackerSpawn(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    }
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
                p.removeMetadata("ignore_combat_tag", (Plugin)CosmicKOTHPlugin.get_());
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerConsumeGoldenApple(PlayerItemConsumeEvent e) {
        Player pl;
        if ((e.getItem().getType() == Material.GOLDEN_APPLE || e.getItem().getTypeId() == 322) && CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)(pl = e.getPlayer())) == OutpostTier.VANILLA) {
            e.setCancelled(true);
            pl.updateInventory();
            pl.sendMessage((Object)ChatColor.RED + "* Golden Apples are " + (Object)ChatColor.UNDERLINE + "disabled" + (Object)ChatColor.RED + " at this Outpost.");
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().contains("cosmic-station")) {
            long deathBanDuration = e.getEntity().hasPermission("cosmicpvp.heroic") ? TimeUnit.SECONDS.toMillis(60L) : TimeUnit.SECONDS.toMillis(120L);
            e.getEntity().setMetadata("deathBan_" + e.getEntity().getWorld().getName(), (MetadataValue)new FixedMetadataValue((Plugin)CosmicKOTHPlugin.get_(), (Object)(System.currentTimeMillis() + deathBanDuration)));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerTeleportToOutpost(PlayerTeleportEvent e) {
        if (e.getTo().getWorld().getName().contains("cosmic-station") && !e.getFrom().getWorld().equals((Object)e.getTo().getWorld())) {
            long deathBan;
            final Player p = e.getPlayer();
            final World toWorld = e.getTo().getWorld();
            if (p.hasMetadata("deathBan_" + toWorld.getName()) && (deathBan = ((MetadataValue)p.getMetadata("deathBan_" + toWorld.getName()).get(0)).asLong()) > System.currentTimeMillis()) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot teleport back to that /outpost for: " + CC.Underline + TimeUtils.formatFutureTime((long)deathBan));
                e.setCancelled(true);
                return;
            }
            Bukkit.getScheduler().runTaskLater((Plugin)CosmicKOTHPlugin.get_(), new Runnable(){

                @Override
                public void run() {
                    if (p.getWorld().equals((Object)toWorld)) {
                        ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
                        for (ItemStack i : p.getEquipment().getArmorContents()) {
                            armor.add(i);
                        }
                        for (Map.Entry data : EListener.getValidEnchantments(armor).entries()) {
                            ((CustomEnchantment)data.getKey()).applyUnequipEffect(p, ((Integer)data.getValue()).intValue());
                        }
                        if (toWorld.getName().contains("vanilla")) {
                            return;
                        }
                        for (Map.Entry data : EListener.getValidEnchantments((LivingEntity)p, armor).entries()) {
                            ((CustomEnchantment)data.getKey()).applyEquipEffect(p, ((Integer)data.getValue()).intValue());
                        }
                    }
                }
            }, 2L);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerJoinOutpostWorld(PlayerJoinEvent e) {
        OutpostTier outpost = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)e.getPlayer());
        if (outpost != OutpostTier.NONE) {
            Player p = e.getPlayer();
            ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
            for (ItemStack i : p.getEquipment().getArmorContents()) {
                armor.add(i);
            }
            for (Map.Entry data : EListener.getValidEnchantments(armor).entries()) {
                ((CustomEnchantment)data.getKey()).applyUnequipEffect(p, ((Integer)data.getValue()).intValue());
            }
            if (outpost == OutpostTier.VANILLA) {
                return;
            }
            for (Map.Entry data : EListener.getValidEnchantments((LivingEntity)p, armor).entries()) {
                ((CustomEnchantment)data.getKey()).applyEquipEffect(p, ((Integer)data.getValue()).intValue());
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerAttemptSalvage(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.IRON_BLOCK && e.getClickedBlock().getWorld().getName().startsWith("cosmic-station")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        Player p;
        Outpost o;
        if (e.getEntity() instanceof Player && (o = CosmicOutpostsAPI.getOutpost((Entity)(p = (Player)e.getEntity()))) != null && WorldGuardUtils.getRegionName(p.getLocation()).contains("defender") && (o.getCapTask().getDefendingFaction() == null || !((FPlayer)FPlayers.i.get((OfflinePlayer)p)).getFaction().getTag().equals(o.getCapTask().getDefendingFaction().getTag()))) {
            e.setCancelled(true);
            p.sendMessage((Object)ChatColor.GRAY + "* You cannot shoot bows from the defender spawn.");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerUseSign(PlayerInteractEvent e) {
        Outpost o = CosmicOutpostsAPI.getOutpost((Entity)e.getPlayer());
        if (o != null) {
            Player p = e.getPlayer();
            if (p.isOp()) {
                return;
            }
            if (WorldGuardUtils.getRegionName(e.getPlayer().getLocation()).contains("defender") && (o.getCapTask().getDefendingFaction() == null || !((FPlayer)FPlayers.i.get((OfflinePlayer)p)).getFaction().getTag().equals(o.getCapTask().getDefendingFaction().getTag()))) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Outpost o;
        Player pAttacker = null;
        if (e.getDamager() instanceof Player) {
            pAttacker = (Player)e.getDamager();
        } else if (e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) {
            pAttacker = (Player)((Projectile)e.getDamager()).getShooter();
        }
        if (pAttacker != null && (o = CosmicOutpostsAPI.getOutpost((Entity)pAttacker)) != null && WorldGuardUtils.getRegionName(pAttacker.getLocation()).contains("defender") && (o.getCapTask().getDefendingFaction() == null || !((FPlayer)FPlayers.i.get((OfflinePlayer)pAttacker)).getFaction().getTag().equals(o.getCapTask().getDefendingFaction().getTag()))) {
            e.setCancelled(true);
            e.setDamage(0.0);
            pAttacker.sendMessage((Object)ChatColor.GRAY + "* You cannot deal damage to defenders inside their spawn.");
        }
    }
}

