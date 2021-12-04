package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NebulaSetListener implements Listener {
    private Map<Player, Location> activeAbilities;

    private List<Player> playersOnCooldown;

    private List<Player> activeWarmups;

    public NebulaSetListener() {
        this.activeAbilities = new ConcurrentHashMap<>();
        this.playersOnCooldown = new ArrayList<>();
        this.activeWarmups = new ArrayList<>();
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (e.getPlayer().isBlocking() && !this.activeWarmups.contains(e.getPlayer()) && WorldGuardUtils.isPvPEnabled(e.getPlayer().getLocation())) {
            if(this.playersOnCooldown.contains(e.getPlayer())) {
                e.getPlayer().sendMessage(CC.LightPurpleB + "** Galaxy Time Warp - " + CC.Gray + "STILL ON COOLDOWN " + "**");
                return;
            }
            if (CosmicArmorSetsAPI.getBonusCrystalsEquipped(e.getPlayer()).containsKey(ArmorSet.NEBULA)) {
                this.activeWarmups.add(e.getPlayer());
                e.getPlayer().sendMessage(CC.LightPurpleB + "** Galaxy Time Warp - " + CC.Gray + "ACTIVATING IN 3s " + CC.LightPurpleB + "**");
                Bukkit.getScheduler().runTaskLater(CosmicArmorSetsPlugin.get_(), () -> {
                    if (!e.getPlayer().isSneaking() || !e.getPlayer().isBlocking()) {
                        this.activeWarmups.remove(e.getPlayer());
                        return;
                    }
                    e.getPlayer().sendMessage(CC.LightPurpleB + "** Galaxy Time Warp - " + CC.Gray + "ACTIVATING IN 2s " + CC.LightPurpleB + "**");
                    Bukkit.getScheduler().runTaskLater(CosmicArmorSetsPlugin.get_(), () -> {
                        if (!e.getPlayer().isSneaking() || !e.getPlayer().isBlocking()) {
                            this.activeWarmups.remove(e.getPlayer());
                            return;
                        }
                        e.getPlayer().sendMessage(CC.LightPurpleB + "** Galaxy Time Warp - " + CC.Gray + "ACTIVATION IN 1s " + CC.LightPurpleB + "**");
                        Bukkit.getScheduler().runTaskLater(CosmicArmorSetsPlugin.get_(), () -> {
                            if (!e.getPlayer().isSneaking() || !e.getPlayer().isBlocking()) {
                                this.activeWarmups.remove(e.getPlayer());
                                return;
                            }
                            this.activeAbilities.put(e.getPlayer(), e.getPlayer().getLocation());
                            long duration = CosmicArmorSetsAPI.getBonusCrystalsEquipped(e.getPlayer()).get(ArmorSet.NEBULA) * 3;
                            e.getPlayer().sendTitle(new Title(CC.LightPurpleB + "Galaxy Time Warp Ability", CC.Gray + "Active For " + duration + "s.", 20, 20, 20));
                            for(Entity entity : e.getPlayer().getNearbyEntities(20, 20, 20)) {
                                if(entity instanceof Player) {
                                    entity.sendMessage(CC.LightPurpleB + "** " + e.getPlayer().getName() + "'s Galaxy Time Warp - " + CC.Gray + "ACTIVATED " + CC.LightPurpleB + "**");
                                }
                            }
                            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (duration * 20), 5));
                            Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicArmorSetsPlugin.get_(), () -> {
                                this.activeAbilities.remove(e.getPlayer());
                                e.getPlayer().teleport(this.activeAbilities.get(e.getPlayer()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                e.getPlayer().sendTitle(new Title(CC.LightPurpleB + "** Galaxy Time Warp " + CC.Gray + "ENDED " + CC.LightPurpleB + "**", CC.Gray + "You have been teleported to your original location."));
                            }, duration * 20L);
                            this.playersOnCooldown.add(e.getPlayer());
                            Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicArmorSetsPlugin.get_(), () -> {
                                this.playersOnCooldown.remove(e.getPlayer());
                            }, 6000L);
                        }, 20L);
                    }, 20L);
                }, 20L);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(this.activeAbilities.containsKey(e.getEntity()) && ((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());
                e.getEntity().teleport(this.activeAbilities.get(e.getEntity()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                ((Player) e.getEntity()).sendTitle(new Title(CC.LightPurpleB + "** Galaxy Time Warp " + CC.Gray + "ENDED " + CC.LightPurpleB + "**", CC.Gray + "You have been teleported to your original location."));
                this.activeAbilities.remove(e.getEntity());
            }
            if (this.activeAbilities.containsKey(e.getDamager()) && isPlayerBehind((Player) e.getDamager(), e.getEntity())) {
                e.setDamage(e.getDamage() * 1.5);
                e.getEntity().sendMessage(CC.LightPurpleB + "** " + e.getDamager().getName() + "'s Galaxy Time Warp - " + CC.Gray + "2x DAMAGE " + CC.LightPurpleB + "**");
                e.getDamager().sendMessage(CC.LightPurpleB + "** " + "Galaxy Time Warp - " + CC.Gray + "2x Damage to " + e.getEntity().getName() + CC.LightPurpleB + "**");
            }
        }
    }

    public boolean isPlayerBehind(final Player player, final Entity theEntity) {
        Location playerLoc = player.getLocation();
        Location targetLoc = theEntity.getLocation();
        double pvecy = -Math.sin(Math.toRadians(playerLoc.getPitch()));
        double pvecx = -Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.sin(Math.toRadians(playerLoc.getYaw()));
        double pvecz = Math.cos(Math.toRadians(playerLoc.getPitch())) * Math.cos(Math.toRadians(playerLoc.getYaw()));
        double tvecy = -Math.sin(Math.toRadians(targetLoc.getPitch()));
        double tvecx = -Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.sin(Math.toRadians(targetLoc.getYaw()));
        double tvecz = Math.cos(Math.toRadians(targetLoc.getPitch())) * Math.cos(Math.toRadians(targetLoc.getYaw()));
        double dot = tvecx * pvecx + tvecy * pvecy + tvecz * pvecz;
        return dot > 0.6;
    }
}
