/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  me.vaqxine.NetworkManager.NetworkManager
 *  net.citizensnpcs.api.CitizensAPI
 *  net.citizensnpcs.api.npc.NPC
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.EntityEffect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.PotionSplashEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.event.player.PlayerVelocityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package org.arkhamnetwork.Arkkit.patches.combatlog;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.vaqxine.NetworkManager.NetworkManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.MinecraftThreadMonitor;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLoggerDeathEvent;
import org.arkhamnetwork.Arkkit.patches.combatlog.PlayerLogoutMonitorTask;
import org.arkhamnetwork.Arkkit.utils.FactionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CombatLogLogoutListener
implements Listener {
    private PlayerLogoutMonitorTask logoutMonitor = new PlayerLogoutMonitorTask();
    private CombatLog cl = null;
    public static boolean spawnNPC = false;
    public static ConcurrentHashMap<String, NPC> combatNPCs;
    public static HashMap<String, ItemStack[]> combatNPCs_loot;
    protected static HashSet<String> playersToKill;

    CombatLogLogoutListener(CombatLog cl) {
        combatNPCs = new ConcurrentHashMap();
        combatNPCs_loot = new HashMap();
        this.cl = cl;
        this.cl.registerTask(this.logoutMonitor.runTaskTimer((Plugin)Arkkit.get(), 5L, 5L), CombatLogLogoutListener.class);
        spawnNPC = cl.getBukkitPlugin().getConfig().getBoolean("settings.combat_log.spawnNPC");
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (this.logoutMonitor.logouts++ > 2) {
            Bukkit.getLogger().info("Skipping combatLog check for " + e.getPlayer().getName() + " due to >2 logouts in 5 ticks.");
            return;
        }
        if (e.getPlayer().isDead() || e.getPlayer().getHealth() <= 0.0) {
            Bukkit.getLogger().info("Skipping combatLog check for " + e.getPlayer().getName() + " because their entity is dead.");
            return;
        }
        if (e.getPlayer().hasMetadata("lastDeathEvent") && (long)MinecraftServer.currentTick - ((MetadataValue)e.getPlayer().getMetadata("lastDeathEvent").get(0)).asLong() < 20L) {
            Bukkit.getLogger().info("Skipping combatLog check for " + e.getPlayer().getName() + " because they had a death event in last 20 ticks.");
            return;
        }
        if (e.getPlayer().getWorld().getName().startsWith("dungeon_")) {
            Bukkit.getLogger().info("Skipping combatLog check for " + e.getPlayer().getName() + " due to worldName: " + e.getPlayer().getWorld().getName());
            return;
        }
        if (!(Arkkit.get().shutting_down || AntiCrash.crash || MinecraftThreadMonitor.non_response > 0 || !CombatLog.inCombat(e.getPlayer()) || Bukkit.getPluginManager().getPlugin("NetworkManager") != null && NetworkManager.getPlugin().rebooting || CombatLog.isPvPDisabled(e.getPlayer().getLocation()))) {
            if (e.getPlayer().hasMetadata("noCombatLog")) {
                e.getPlayer().removeMetadata("noCombatLog", (Plugin)Arkkit.get());
                return;
            }
            if (spawnNPC && e.getPlayer().getHealth() >= 2.0) {
                Player pKiller;
                if (e.getPlayer().getWorld().getName().equals("world_duels2")) {
                    return;
                }
                if (combatNPCs.containsKey(e.getPlayer().getUniqueId().toString())) {
                    Bukkit.getLogger().info("[Arkkit (CombatLog)] Skipping Combat NPC spawn for " + (Object)e.getPlayer() + " due to existing NPC: " + (Object)combatNPCs.get(e.getPlayer().getUniqueId().toString()));
                    return;
                }
                String playerName = e.getPlayer().getName();
                String playerUUID = e.getPlayer().getUniqueId().toString();
                NPC n = CitizensAPI.getNPCRegistry().createNPC(EntityType.ZOMBIE, ChatColor.RED.toString() + (Object)ChatColor.BOLD + "*OFFLINE* " + (Object)ChatColor.RESET + playerName);
                Player p = e.getPlayer();
                Bukkit.getLogger().info("[Arkkit (CombatLog)] Attempting to spawn combatNPC for player " + p.getName() + " at " + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ() + " with HP: " + p.getHealth());
                Location npcLocation = p.getLocation();
                if (e.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END && e.getPlayer().getLocation().getY() < 35.0 && p.hasMetadata("lastPlayerDamagerUUID") && (pKiller = Bukkit.getPlayer((UUID)((UUID)((MetadataValue)p.getMetadata("lastPlayerDamagerUUID").get(0)).value()))) != null && pKiller.isOnline()) {
                    npcLocation = pKiller.getLocation().add(0.0, 1.0, 0.0);
                }
                n.spawn(npcLocation);
                Zombie z = (Zombie)n.getEntity();
                z.setMaxHealth(p.getMaxHealth());
                z.setHealth(p.getHealth());
                z.setCustomNameVisible(true);
                z.setBaby(false);
                z.setVillager(true);
                z.setCanPickupItems(false);
                z.setRemoveWhenFarAway(false);
                z.setNoDamageTicks(0);
                z.setFireTicks(0);
                if (e.getPlayer().hasMetadata("resilienceEnchant")) {
                    z.setMetadata("resilienceEnchant", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)((MetadataValue)e.getPlayer().getMetadata("resilienceEnchant").get(0)).asInt()));
                }
                ArrayList<ItemStack> loot = new ArrayList<ItemStack>();
                for (ItemStack i : (ItemStack[])p.getInventory().getContents().clone()) {
                    if (i == null || i.getTypeId() == 0) continue;
                    loot.add(i);
                }
                for (ItemStack i : (ItemStack[])p.getEquipment().getArmorContents().clone()) {
                    if (i == null || i.getTypeId() == 0) continue;
                    loot.add(i);
                }
                n.getEntity().setMetadata("combatNPC_PlayerName", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)playerName));
                n.getEntity().setMetadata("combatNPC_PlayerUUID", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)e.getPlayer().getUniqueId().toString()));
                n.getEntity().setMetadata("combatNPC_ID", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)n.getId()));
                n.getEntity().setMetadata("combatNPC_TimeoutTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + CombatLog.getTimeLeftInCombat(p))));
                n.getEntity().setMetadata("combatNPC_Faction", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)FactionUtils.getFaction((Player)p, (boolean)true)));
                n.setFlyable(false);
                n.setProtected(false);
                combatNPCs.put(playerUUID, n);
                combatNPCs_loot.put(playerUUID, loot.toArray((T[])new ItemStack[loot.size()]));
                for (Entity ent : e.getPlayer().getNearbyEntities(48.0, 48.0, 48.0)) {
                    if (!(ent instanceof Player)) continue;
                    ((Player)ent).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + e.getPlayer().getName() + " has logged out while in combat, kill their NPC before they reconnect!");
                }
                Bukkit.getLogger().info("[Arkkit (CombatLog)] Spawned combatNPC for player " + p.getName() + " at " + p.getLocation().getBlockX() + "," + p.getLocation().getBlockY() + "," + p.getLocation().getBlockZ() + " with HP: " + p.getHealth() + " / Inventory: " + combatNPCs_loot.get(playerUUID));
            } else {
                Bukkit.getLogger().info("[Arkkit (CombatLog)] Killing " + e.getPlayer().getName() + " due to logging out while still in cl_combat!");
                e.getPlayer().setHealth(0.0);
                for (Entity ent : e.getPlayer().getNearbyEntities(64.0, 64.0, 64.0)) {
                    if (!(ent instanceof Player)) continue;
                    ((Player)ent).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + e.getPlayer().getName() + " was killed due to logging out in combat.");
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p;
            ((Player)e.getEntity()).setMetadata("lastDeathEvent", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)MinecraftServer.currentTick));
            if (!e.getEntity().hasMetadata("NPC") && combatNPCs.containsKey((p = (Player)e.getEntity()).getUniqueId().toString())) {
                NPC n;
                String pName = p.getName();
                Bukkit.getLogger().info("[Arkkit (CombatLog)] Adding Player:" + p.getName() + ", UUID:" + p.getUniqueId().toString() + " to playersToKill due to NPC EntityDeathEvent.");
                playersToKill.add(p.getUniqueId().toString());
                combatNPCs.remove(p.getUniqueId().toString());
                combatNPCs_loot.remove(p.getUniqueId().toString());
                int id = e.getEntity().hasMetadata("combatNPC_ID") ? ((MetadataValue)e.getEntity().getMetadata("combatNPC_ID").get(0)).asInt() : -1;
                NPC nPC = n = id != -1 ? CitizensAPI.getNPCRegistry().getById(id) : CitizensAPI.getNPCRegistry().getNPC((Entity)e.getEntity());
                if (n != null) {
                    n.despawn();
                    n.destroy();
                    CitizensAPI.getNPCRegistry().deregister(n);
                } else {
                    Bukkit.getLogger().info("[Arkkit (CombatLog)] Null NPC object for: " + pName + " onEntityDeath.");
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onEntityCombust(final EntityCombustEvent e) {
        if (e.getEntity().hasMetadata("combatNPC_PlayerName")) {
            e.setCancelled(true);
            e.setDuration(0);
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    e.getEntity().setFireTicks(0);
                }
            }, 1L);
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity().hasMetadata("combatNPC_PlayerName") && e.getAmount() > 0.0) {
            LivingEntity le = (LivingEntity)e.getEntity();
            e.setAmount(le.getMaxHealth() * 0.1);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPotionSplashEvent(PotionSplashEvent e) {
        for (LivingEntity le : new ArrayList(e.getAffectedEntities())) {
            if (!le.hasMetadata("combatNPC_PlayerName")) continue;
            for (PotionEffect pe : e.getPotion().getEffects()) {
                if (!pe.getType().equals((Object)PotionEffectType.HEAL)) continue;
                le.setHealth(Math.min(le.getMaxHealth(), le.getHealth() + le.getMaxHealth() * 0.1));
            }
            e.getAffectedEntities().remove((Object)le);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onNPCDamage(EntityDamageEvent e) {
        Player p;
        if (e.getEntity() instanceof Player && !e.getEntity().hasMetadata("NPC") && combatNPCs.containsKey((p = (Player)e.getEntity()).getUniqueId().toString())) {
            e.setCancelled(true);
            e.setDamage(0.0);
            return;
        }
        if (e.getEntity().hasMetadata("combatNPC_PlayerName") && e.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            Player p2;
            Faction faction;
            EntityDamageByEntityEvent edbee;
            LivingEntity le = (LivingEntity)e.getEntity();
            double dmgMod = 0.1;
            if (le.hasMetadata("resilienceEnchant")) {
                int resilienceLevel = ((MetadataValue)le.getMetadata("resilienceEnchant").get(0)).asInt();
                dmgMod -= (double)resilienceLevel * 0.015;
            }
            double dmg = le.getMaxHealth() * dmgMod;
            e.setCancelled(true);
            e.setDamage(0.0);
            if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                return;
            }
            Faction f = (Faction)((MetadataValue)e.getEntity().getMetadata("combatNPC_Faction").get(0)).value();
            String pName = ((MetadataValue)e.getEntity().getMetadata("combatNPC_PlayerName").get(0)).asString();
            if ((e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) && (edbee = (EntityDamageByEntityEvent)e).getDamager() instanceof Player && FactionUtil.getRelation((RelationParticipator)(faction = FactionUtils.getFaction((Player)(p2 = (Player)edbee.getDamager()))), (RelationParticipator)f).isAtLeast(Relation.TRUCE)) {
                p2.sendMessage((Object)ChatColor.RED + "** " + (Object)ChatColor.GRAY + "You cannot hurt " + (Object)ChatColor.RED + pName + "'s NPC" + (Object)ChatColor.GRAY + " due to your faction relationship.");
                return;
            }
            if (le.getHealth() > 0.0) {
                double newHealth = Math.max(0.0, le.getHealth() - dmg);
                le.setHealth(newHealth);
                le.playEffect(EntityEffect.HURT);
                le.setMetadata("combatNPC_TimeoutTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + 10000L)));
                if (newHealth <= 0.0 && le.hasMetadata("combatNPC_PlayerName")) {
                    String playerUUID = ((MetadataValue)le.getMetadata("combatNPC_PlayerUUID").get(0)).asString();
                    ItemStack[] storedItems = combatNPCs_loot.get(playerUUID);
                    CombatLoggerDeathEvent combatLoggerDeathEvent = new CombatLoggerDeathEvent(le, playerUUID, pName, e, storedItems);
                    Bukkit.getPluginManager().callEvent((Event)combatLoggerDeathEvent);
                    if (combatLoggerDeathEvent.getInventoryItems() != null) {
                        for (ItemStack i : combatLoggerDeathEvent.getInventoryItems()) {
                            if (i == null || i.getType() == Material.AIR) continue;
                            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), i);
                        }
                        combatLoggerDeathEvent.getInventoryItems().clear();
                    }
                    if (!playersToKill.contains(playerUUID)) {
                        Bukkit.getLogger().info("[Arkkit (CombatLog)] Adding UUID:" + playerUUID + " to playersToKill due to NPC death.");
                        playersToKill.add(playerUUID);
                    }
                    combatNPCs.remove(playerUUID);
                    combatNPCs_loot.remove(playerUUID);
                    int id = ((MetadataValue)e.getEntity().getMetadata("combatNPC_ID").get(0)).asInt();
                    NPC n = CitizensAPI.getNPCRegistry().getById(id);
                    if (n != null) {
                        n.despawn();
                        n.destroy();
                        CitizensAPI.getNPCRegistry().deregister(n);
                    }
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerVelocity(PlayerVelocityEvent e) {
        if (e.getPlayer().hasMetadata("combatNPC_PlayerName")) {
            e.setCancelled(true);
            e.setVelocity(new Vector(0, 0, 0));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        String uuid;
        if (!e.getPlayer().isOnline()) {
            return;
        }
        if (combatNPCs.containsKey(e.getPlayer().getUniqueId().toString())) {
            Bukkit.getLogger().info("[Arkkit (CombatLog)] Removing " + e.getPlayer().getName() + "'s NPC due to relog.");
            NPC n = combatNPCs.get(e.getPlayer().getUniqueId().toString());
            if (n.getEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity)n.getEntity();
                e.getPlayer().setHealth(Math.max(0.0, Math.min(20.0, le.getHealth())));
                if (!n.getEntity().getWorld().getName().startsWith("dungeon_")) {
                    e.getPlayer().teleport(n.getEntity().getLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                }
            }
            n.despawn();
            n.destroy();
            CitizensAPI.getNPCRegistry().deregister(n);
            combatNPCs.remove(e.getPlayer().getUniqueId().toString());
            CombatLog.flagCombat(e.getPlayer());
        }
        if (playersToKill.contains(uuid = e.getPlayer().getUniqueId().toString())) {
            Bukkit.getLogger().info("[Arkkit (CombatLog)] Killing " + e.getPlayer().getName() + " due to their combat NPC dieing.");
            e.getPlayer().getInventory().clear();
            e.getPlayer().getEquipment().setHelmet(null);
            e.getPlayer().getEquipment().setChestplate(null);
            e.getPlayer().getEquipment().setLeggings(null);
            e.getPlayer().getEquipment().setBoots(null);
            if (!e.getPlayer().isDead()) {
                e.getPlayer().setHealth(0.0);
            }
            playersToKill.remove(uuid);
            if (playersToKill.contains(uuid)) {
                Bukkit.getLogger().info("[Arkkit (CombatLog] playersToKill still contains uuid: " + uuid + " despite .remove() call?");
                playersToKill.remove(uuid);
            }
            final Player p = e.getPlayer();
            CombatLog.unflagCombat(p);
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    if (p.isOnline()) {
                        p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You logged out in combat and your NPC was killed while you were away.");
                    }
                }
            }, 60L);
            for (NPC n : CitizensAPI.getNPCRegistry()) {
                if (!n.isSpawned() || n.getEntity() == null || !n.getEntity().hasMetadata("combatNPC_PlayerUUID") || !((MetadataValue)n.getEntity().getMetadata("combatNPC_PlayerUUID").get(0)).asString().equals(e.getPlayer().getUniqueId().toString())) continue;
                Bukkit.getLogger().info("[Arkkit (CombatLog)] Invalid NPC found for UUID (" + e.getPlayer().getUniqueId() + "): " + n.getFullName() + "(" + n.getId() + ") / " + (Object)n.getEntity());
                n.despawn();
                n.destroy();
            }
        }
    }

    static {
        playersToKill = new HashSet();
    }
}

