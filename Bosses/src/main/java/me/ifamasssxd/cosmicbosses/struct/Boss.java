/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.UpgradeUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  io.apice.clientcomms.ClientCommsAPI
 *  io.apice.clientcomms.EventType
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalFloat
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicbosses.struct;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import io.apice.clientcomms.ClientCommsAPI;
import io.apice.clientcomms.EventType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.holidayspirit.HolidaySpirit;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueBloater;
import me.ifamasssxd.cosmicbosses.bosses.soulreaper.SoulReaper;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.BattlefieldSnowman;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbomination;
import me.ifamasssxd.cosmicbosses.struct.AntiFarm;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.DamageTracker;
import me.ifamasssxd.cosmicbosses.struct.GlobalBoss;
import me.ifamasssxd.cosmicbosses.struct.Healable;
import me.ifamasssxd.cosmicbosses.struct.MiniBoss;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.RewardInventory;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.struct.enums.Equipable;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.MapUtils;
import me.ifamasssxd.cosmicbosses.utils.NumberUtils;
import me.ifamasssxd.cosmicbosses.utils.ScoreboardUtils;
import me.ifamasssxd.cosmicbosses.utils.StringUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Boss
extends Equipable
implements Healable {
    public static ConcurrentHashMap<UUID, Boss> bosses = new ConcurrentHashMap();
    protected int loops = 0;
    public LivingEntity entity;
    public Location location;
    protected UUID uuid;
    private List<Entity> minions;
    private LinkedList<ItemStack> lootPool = new LinkedList();
    private DamageTracker damageTracker = new DamageTracker();
    private Phase phase = Phase.ONE;
    private HashMap<BossAbility, Long> abilityCooldowns;
    private long getLastDamageDealt;
    protected BlockModifier modifier;
    protected Random random = new Random();
    protected AntiFarm antiFarm;
    private BossType bossType;
    private boolean naturallySpawned = false;
    private boolean dead = false;
    DecimalFormat format = new DecimalFormat("#.##");
    private int totalPlayerDamageReceived = 0;

    public Boss(Location location, BossType bossType) {
        this.location = location;
        this.bossType = bossType;
        this.antiFarm = new AntiFarm(this);
    }

    public LivingEntity spawnBoss() {
        LivingEntity e = (LivingEntity)this.location.getWorld().spawnEntity(this.location.clone(), this.getEntityType());
        e.setRemoveWhenFarAway(false);
        this.setupBoss(e);
        return e;
    }

    public double getHealthPercent() {
        if (this.getEntity() == null || !this.getEntity().isValid()) {
            return 0.0;
        }
        return this.getEntity().getHealth() / this.getEntity().getMaxHealth() * 100.0;
    }

    protected int getSeconds(int amount) {
        return amount * 4;
    }

    public LivingEntity setupBoss(LivingEntity e) {
        this.uuid = e.getUniqueId();
        this.entity = e;
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMetadata("boss", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMaxHealth((double)this.getMaxHealth());
        e.setRemoveWhenFarAway(false);
        e.setHealth(e.getMaxHealth());
        this.setEnt(e);
        e.setCustomName(this.getColor(true) + this.getCustomName());
        e.setCustomNameVisible(true);
        e.setRemoveWhenFarAway(false);
        bosses.put(e.getUniqueId(), this);
        return e;
    }

    public abstract EntityType getEntityType();

    public void onBossSpawn() {
        this.spawnBoss();
        this.alert();
    }

    public abstract String getCustomName();

    public abstract String getName();

    public void onUpdate() {
        LivingEntity li = this.getEntity();
        if (li.hasPotionEffect(PotionEffectType.SLOW)) {
            li.removePotionEffect(PotionEffectType.SLOW);
        }
        if (li.hasPotionEffect(PotionEffectType.WEAKNESS)) {
            li.removePotionEffect(PotionEffectType.WEAKNESS);
        }
        if (this.loops % 120 == 0 && this.loops > 1) {
            BossMessage bossMessage = this.getBossMessage(this.getPhase().getPhase());
            if (bossMessage == null || this.random.nextInt(4) == 0) {
                bossMessage = this.getGeneralMessage();
            }
            if (bossMessage != null) {
                for (Entity pl : this.getNearbyEntities(20.0, EntityType.PLAYER)) {
                    Player p = (Player)pl;
                    this.sendMessage(p, (Object)ChatColor.ITALIC + bossMessage.getMessage());
                }
            }
        }
    }

    public abstract void cleanup();

    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        Location loc = this.getEntity().getLocation();
        if (!loc.getWorld().getName().equals("world_private") && !loc.getWorld().getName().startsWith("cosmic-station") && loc.getY() < (double)loc.getWorld().getHighestBlockYAt(loc)) {
            event.setDamage(event.getDamage() * 3.0);
        }
    }

    public void onDamageTaken(EntityDamageByEntityEvent event, Entity damager) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getDamager() instanceof Player && CosmicBosses.getInstance().isFactionUpgradesEnabled()) {
            double mult;
            int heroicLevel = UpgradeUtils.getUpgradeLevel((Player)((Player)event.getDamager()), (String)"HEROIC_BOSS_MASTERY");
            if (heroicLevel > 0) {
                mult = heroicLevel == 1 ? 2.05 : (heroicLevel == 2 ? 2.15 : (heroicLevel == 3 ? 2.25 : 1.0));
            } else {
                int level = FactionUpgradeAPI.getPerkLevel((Player)((Player)event.getDamager()), (FactionUpgrade)FactionUpgrade.BOSS_MASTERY);
                double d = level == 1 ? 1.25 : (level == 2 ? 1.5 : (level == 3 ? 1.75 : (mult = level == 4 ? 2.0 : 1.0)));
            }
            if (mult > 1.0) {
                event.setDamage(event.getDamage() * mult);
            }
        }
        if (this instanceof GuardBoss) {
            ((GuardBoss)((Object)this)).onDamage(this, event);
        }
    }

    public void onDamageTaken(EntityDamageEvent event) {
        Location loc;
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            event.setCancelled(true);
            Entity ent = event.getEntity();
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                if (ent != null && !ent.isDead()) {
                    ent.setFireTicks(0);
                }
            }, 1L);
        }
        if (!(loc = this.getEntity().getLocation()).getWorld().getName().equals("world_private") && loc.getWorld().getName().startsWith("cosmic-station") && loc.getY() < (double)loc.getWorld().getHighestBlockYAt(loc)) {
            event.setDamage(event.getDamage() / 2.0);
        }
    }

    public void onEntityChangeBlock(EntityChangeBlockEvent event, Entity boss) {
    }

    public void onEntityTeleport(EntityTeleportEvent event, Entity boss) {
    }

    public abstract String getTitle();

    public abstract void onPhaseChange(Phase var1);

    public void onDeath(EntityDeathEvent event) {
        if (this.dead) {
            return;
        }
        this.cleanup();
        if (!(event == null || this instanceof MiniBoss || this instanceof PlagueBloater && this.location.getWorld().getName().equals(CosmicBosses.battlefieldWorld) || this instanceof DungeonYijki)) {
            event.getDrops().clear();
            String msg = StringUtils.getCenteredMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "The " + this.getName() + " has been defeated!");
            String starting = (Object)ChatColor.RED + "Defeated by ";
            String killMsg = this.getKillMessage();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!(this instanceof GlobalBoss) && !BossUtils.isWarzone(player.getLocation()) && !WorldGuardUtils.getRegionName(player.getLocation()).contains("spawn")) continue;
                player.sendMessage(msg);
                if (killMsg == null || killMsg.length() <= starting.length()) continue;
                player.sendMessage(killMsg + "!");
            }
            try {
                HashMap<UUID, List<ItemStack>> loot = this.givePlayerLootPool();
                this.alertDeathStats(loot);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event != null && event.getEntity().hasMetadata("heroicEnvoyBoss")) {
            event.getDrops().clear();
            this.getLootPool().clear();
            this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
            this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
            try {
                HashMap<UUID, List<ItemStack>> loot = this.givePlayerLootPool();
                this.alertDeathStats(loot);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event != null) {
            event.getEntity().setMetadata("killed", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        }
        this.dead = true;
        this.clearScoreboard();
    }

    private String getKillMessage() {
        String starting = (Object)ChatColor.RED + "Defeated by ";
        StringBuilder killers = new StringBuilder(starting);
        int place = 0;
        int minRequiredDamage = (int)((double)this.getMaxHealth() * 0.1);
        LinkedList<Player> players = this.getTopDamage(10, false);
        for (Player p : players) {
            ++place;
            int damage = (Integer)this.getDamageTracker().get(p.getUniqueId());
            if (damage < minRequiredDamage) continue;
            ChatColor color = place == 1 ? ChatColor.GOLD : (place == 2 ? ChatColor.YELLOW : (place == 3 ? ChatColor.RED : ChatColor.GREEN));
            killers.append((Object)color + p.getName() + (Object)color + ", ");
        }
        String killMsg = killers.toString();
        if (killMsg.equals(starting)) {
            Player top;
            killMsg = players.size() > 0 ? ((top = (Player)players.get(0)) != null ? killMsg + top.getName() : "Cosmonauts") : "Cosmonauts!";
        }
        if (killMsg.endsWith(", ")) {
            killMsg = killMsg.substring(0, killMsg.length() - 4);
        }
        return StringUtils.getCenteredMessage(killMsg);
    }

    protected void alert() {
        ArrayList pls;
        String msg;
        if (this instanceof SoulReaper || this instanceof HolidaySpirit || this instanceof BattlefieldSnowman || this instanceof DungeonYijki || this instanceof EvilAbomination || this instanceof PlagueBloater && this.getEntity().getWorld().getName().equals(CosmicBosses.battlefieldWorld)) {
            return;
        }
        if (this.naturallySpawned) {
            msg = this.getColor(true) + "(!) " + this.getColor(false) + "A " + this.getColor(true) + ChatColor.UNDERLINE.toString() + this.getCustomName() + this.getColor(false) + " Boss has been summoned to the /spawn WarZone, at " + this.getColor(true) + this.location.getBlockX() + "x, " + this.location.getBlockY() + "y, " + this.location.getBlockZ() + "z " + this.getColor(false) + "defend the planet!";
            try {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    ClientCommsAPI.sendEvent((Player)p, (String)"Previous Warzone Boss", (EventType)EventType.COUNT_UP, (long)System.currentTimeMillis(), (long)0L);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            msg = this.getColor(true) + "(!) " + this.getColor(false) + "A " + this.getColor(true) + ChatColor.UNDERLINE.toString() + this.getCustomName() + this.getColor(false) + " Boss has been summoned nearby, defend the planet!";
        }
        if (this instanceof EndDragon) {
            msg = CC.RedB + "(!) " + CC.Red + "A " + CC.DarkRedB + CC.Underline + this.getCustomName() + CC.Red + " has been awoken in the Ender Dimension. Travel to " + CC.DarkRed + "/warp end" + CC.Red + " to subdue it before it finds a way into the Cosmonaut realm!";
        }
        Location loc = this.getLocation();
        String debug = (Object)ChatColor.YELLOW + "Spawning boss " + this.getCustomName() + (Object)ChatColor.YELLOW + " at " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "x" + loc.getBlockX() + " y" + loc.getBlockY() + " z" + loc.getBlockZ() + "!";
        ArrayList arrayList = pls = this.naturallySpawned ? Lists.newArrayList((Iterable)Bukkit.getOnlinePlayers()) : Lists.newArrayList();
        if (!this.naturallySpawned) {
            for (Entity ent : this.getEntity().getNearbyEntities(32.0, 32.0, 32.0)) {
                if (!(ent instanceof Player)) continue;
                Player pl = (Player)ent;
                pls.add(pl);
            }
        }
        boolean dragon = this instanceof EndDragon;
        for (Player pl : pls) {
            pl.sendMessage("");
            pl.sendMessage(msg);
            pl.playSound(pl.getLocation(), dragon ? Sound.ENDERDRAGON_GROWL : Sound.CHICKEN_EGG_POP, 4.0f, 1.2f);
            pl.sendMessage("");
            if (!pl.hasPermission("cosmicbosses.alert.coords")) continue;
            pl.sendMessage(debug);
        }
        Bukkit.getLogger().info("Spawning boss " + this.getCustomName() + " at " + this.getLocation().toString() + "!");
    }

    public abstract void onMiscUpdate(Event var1);

    public abstract DamageType getDamageType();

    public abstract Difficulty getDifficulty();

    public abstract int getMaxHealth();

    public abstract List<BossMessage> getGeneralMessages();

    public BossMessage getBossMessage(int phase) {
        for (BossMessage msg : this.getGeneralMessages()) {
            if (msg.getPhase() != phase) continue;
            return msg;
        }
        return null;
    }

    public BossMessage getStartingMessage() {
        for (BossMessage msg : this.getGeneralMessages()) {
            if (msg.getPhase() != -1) continue;
            return msg;
        }
        return null;
    }

    public BossMessage getGeneralMessage() {
        List msg = this.getGeneralMessages().stream().filter(bMsg -> bMsg.getPhase() == 0 && !bMsg.isPhaseDependent()).collect(Collectors.toList());
        if (msg.size() <= 0) {
            return null;
        }
        return (BossMessage)msg.get(this.random.nextInt(msg.size()));
    }

    public boolean checkStuck() {
        return this.getAntiFarm().checkStuck();
    }

    public List<Entity> getMinions() {
        if (this.minions == null) {
            this.minions = new ArrayList<Entity>();
        }
        return this.minions;
    }

    public void removeMinions() {
        if (this.minions == null) {
            return;
        }
        Bukkit.getLogger().info("Clearing minions for " + this.getName());
        for (Entity minion : this.minions) {
            if (minion == null) continue;
            minion.remove();
        }
        this.minions.clear();
    }

    public HashMap<BossAbility, Long> getAbilityCooldown() {
        if (this.abilityCooldowns == null) {
            this.abilityCooldowns = new HashMap();
        }
        return this.abilityCooldowns;
    }

    public List<Entity> getNearbyEntities(double radius, EntityType type) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (Entity e : this.getEntity().getNearbyEntities(radius, radius, radius)) {
            if (type != null && e.getType() != type) continue;
            entities.add(e);
        }
        return entities;
    }

    public boolean hasMinions() {
        return this.minions != null;
    }

    public void removeMinion(Entity ent) {
        if (this.hasMinions()) {
            this.minions.remove((Object)ent);
        }
    }

    public void updateScoreboard() {
        if (!CosmicBosses.getInstance().isScoreboardEnabled()) {
            return;
        }
        for (Entity ent : this.getNearbyEntities(32.0, EntityType.PLAYER)) {
            Player p = (Player)ent;
            ScoreboardUtils.updateBossScoreboard(p, this);
        }
    }

    public void clearScoreboard() {
        DamageTracker tracker = this.getDamageTracker();
        for (UUID uuid : tracker.keySet()) {
            Player p = Bukkit.getPlayer((UUID)uuid);
            if (p == null) continue;
            ScoreboardUtils.clearBossScoreboard(p);
        }
    }

    public Entity spawnMinion(EntityType type, Location l) {
        Entity ent = l.getWorld().spawnEntity(l, type);
        ent.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.getEntity().getUniqueId()));
        ent.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        this.getMinions().add(ent);
        if (ent instanceof LivingEntity) {
            LivingEntity li = (LivingEntity)ent;
            li.setRemoveWhenFarAway(false);
            li.setCustomName(this.getColor(true) + this.getName() + "'s Minion");
            li.setCustomNameVisible(true);
            if (li.getEquipment() != null) {
                li.getEquipment().setItemInHandDropChance(0.0f);
                li.getEquipment().setHelmetDropChance(0.0f);
                li.getEquipment().setChestplateDropChance(0.0f);
                li.getEquipment().setLeggingsDropChance(0.0f);
                li.getEquipment().setBootsDropChance(0.0f);
            }
        }
        if (type == EntityType.HORSE) {
            EntityInsentient insent = (EntityInsentient)((CraftLivingEntity)ent).getHandle();
            BossUtils.clearGoalSelectors(insent);
            BossUtils.addSelector(insent, 0, (PathfinderGoal)new PathfinderGoalFloat(insent));
            BossUtils.addSelector(insent, 8, (PathfinderGoal)new PathfinderGoalRandomLookaround(insent));
        }
        return ent;
    }

    public boolean isVulnerable(DamageType damageType) {
        return true;
    }

    public boolean isBossSpawned() {
        return this.getEntity() != null && this.getEntity().isValid();
    }

    @Override
    public void onHeal(int amount) {
        if (this.getEntity().isDead()) {
            return;
        }
        if (this.getEntity().getHealth() + (double)amount > this.getEntity().getMaxHealth()) {
            this.getEntity().setHealth(this.getEntity().getMaxHealth());
        } else {
            this.getEntity().setHealth(this.getEntity().getHealth() + (double)amount);
        }
    }

    public void alertNearby(int radius, String message, Sound sound, float speed) {
        for (Entity e : this.getNearbyEntities(radius, EntityType.PLAYER)) {
            Player p = (Player)e;
            p.sendMessage(message);
            if (sound == null) continue;
            p.playSound(p.getLocation(), sound, 5.0f, speed);
        }
    }

    public void sendMessage(String msg) {
        this.sendMessage(msg, null, 0.0f);
    }

    public int getMessageRadius() {
        return 20;
    }

    public void sendMessage(String msg, Sound sound, float speed) {
        ArrayList players = Lists.newArrayList();
        for (UUID uuid : this.getDamageTracker().keySet()) {
            Player p = Bukkit.getPlayer((UUID)uuid);
            if (p == null) continue;
            players.add(p);
        }
        for (Entity ent : this.getNearbyEntities(this.getMessageRadius(), EntityType.PLAYER)) {
            Player pl = (Player)ent;
            if (players.contains((Object)pl)) continue;
            players.add(pl);
        }
        for (Player p : players) {
            if (p == null || !p.getWorld().equals((Object)this.getEntity().getWorld()) || !(p.getLocation().distanceSquared(this.getEntity().getLocation()) <= 1000.0)) continue;
            this.sendMessage(p, msg);
            if (sound == null) continue;
            p.playSound(p.getLocation(), sound, 2.0f, speed);
        }
    }

    public void sendMessage(Player player, String msg) {
        player.sendMessage(this.getColor(true) + this.getName() + " >> " + (Object)ChatColor.WHITE + msg.replace("[NAME]", player.getName()));
    }

    public HashMap<UUID, List<ItemStack>> givePlayerLootPool() {
        Player most;
        HashMap<UUID, List<ItemStack>> loot = new HashMap<UUID, List<ItemStack>>();
        LinkedList<Player> topDamage = this.getTopDamage(this.getLootPool().size(), true);
        int amountPerPerson = (int)Math.floor(Double.valueOf(this.getLootPool().size()) / Double.valueOf(topDamage.size()));
        if (amountPerPerson > 2) {
            amountPerPerson = 2;
        }
        ItemStack title = null;
        if (this.getTitle() != null) {
            title = ItemUtils.getTitleVoucher((String)this.getTitle());
        }
        if (topDamage.size() > 0 && (most = topDamage.get(0)) != null) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests give TOP_DOG " + most.getName()));
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(most, "BOSS_TOP_DAMAGE", new Object[]{this.bossType.name(), this.getEntity()}));
        }
        int placement = 1;
        for (final Player player : topDamage) {
            if (placement++ == 1) {
                FactionPointUtils.giveFactionPoints((Faction)FactionUtils.getFaction((Player)player), (Player)player, (String)"boss_death", (Object)new Object[]{this.getName(), this.getEntity()});
            }
            boolean noRoom = false;
            if (title != null && this.random.nextInt(100) == 0 && !this.addItem(player, title)) {
                noRoom = true;
            }
            System.out.println("Top Damage: " + player.getName() + " Amount Per: " + amountPerPerson);
            ArrayList itemsReceived = Lists.newArrayList();
            for (int i = 0; i < amountPerPerson && !this.getLootPool().isEmpty(); ++i) {
                ItemStack reward = this.getLootPool().remove();
                itemsReceived.add(reward);
                if (!this.addItem(player, reward)) {
                    noRoom = true;
                }
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0f, 0.4f);
            }
            if (noRoom) {
                new BukkitRunnable(){

                    public void run() {
                        if (player.isValid()) {
                            player.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You have new items in your " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "/collect" + (Object)ChatColor.YELLOW + " stash!");
                        }
                    }
                }.runTaskLater((Plugin)CosmicBosses.getInstance(), 5L);
            }
            if (!itemsReceived.isEmpty()) {
                loot.put(player.getUniqueId(), itemsReceived);
            }
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("contest add " + player.getName() + " bossKills 1"));
        }
        if (this.getLootPool().size() > 0) {
            Bukkit.getLogger().info("Found left over loot: " + this.getLootPool());
            for (final Player pl : topDamage) {
                ArrayList<ItemStack> lootGiven;
                if (this.getLootPool().isEmpty()) break;
                List<ItemStack> list = lootGiven = loot.containsKey(pl.getUniqueId()) ? loot.get(pl.getUniqueId()) : new ArrayList<ItemStack>();
                if (lootGiven.size() >= 2) continue;
                ItemStack reward = this.getLootPool().remove();
                lootGiven.add(reward);
                if (!this.addItem(pl, reward)) {
                    new BukkitRunnable(){

                        public void run() {
                            if (pl.isValid()) {
                                pl.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You have new items in your " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "/collect" + (Object)ChatColor.YELLOW + " stash!");
                            }
                        }
                    }.runTaskLater((Plugin)CosmicBosses.getInstance(), 5L);
                }
                loot.put(pl.getUniqueId(), lootGiven);
            }
        }
        return loot;
    }

    private boolean addItem(Player player, ItemStack item) {
        if (player.getWorld().getName().startsWith("world_duel") || player.getInventory().firstEmpty() == -1) {
            RewardInventory.addReward(player, item.clone());
            return false;
        }
        player.getInventory().addItem(new ItemStack[]{item.clone()});
        return true;
    }

    public LinkedHashMap<Player, Integer> getPlayerMap(LinkedHashMap<UUID, Integer> uuidMap) {
        LinkedHashMap<Player, Integer> map = new LinkedHashMap<Player, Integer>();
        for (Map.Entry<UUID, Integer> entry : uuidMap.entrySet()) {
            Player pl = Bukkit.getPlayer((UUID)entry.getKey());
            if (pl == null || map.containsKey((Object)pl)) continue;
            map.put(pl, entry.getValue());
        }
        return map;
    }

    public void alertDeathStats(HashMap<UUID, List<ItemStack>> lootGiven) {
        LinkedHashMap<UUID, Integer> topPlayers = MapUtils.sortByComparator(this.getDamageTracker(), false, 10);
        LinkedHashMap<Player, Integer> playerMap = this.getPlayerMap(topPlayers);
        if (this instanceof GlobalBoss) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.alertDeathStatPlayer(player, topPlayers, playerMap, lootGiven);
            }
        } else {
            for (Entity e : this.getEntity().getNearbyEntities(20.0, 20.0, 20.0)) {
                if (!(e instanceof Player)) continue;
                Player player = (Player)e;
                this.alertDeathStatPlayer(player, topPlayers, playerMap, lootGiven);
            }
            int minRequiredDamage = (int)((double)this.getTotalDamageTaken(playerMap) * 0.1);
            String centered = StringUtils.getCenteredMessage((Object)ChatColor.RED + "You did not do enough damage to earn loot!");
            for (Map.Entry<Player, Integer> players : playerMap.entrySet()) {
                Player pl = players.getKey();
                if (players.getValue() < minRequiredDamage) {
                    pl.sendMessage(centered);
                    continue;
                }
                if (!lootGiven.containsKey(pl.getUniqueId())) continue;
                List<ItemStack> items = lootGiven.get(pl.getUniqueId());
                boolean alreadySent = false;
                for (ItemStack reward : items) {
                    if (!alreadySent) {
                        pl.sendMessage("");
                        pl.sendMessage(StringUtils.getCenteredMessage((Object)ChatColor.GRAY + "You earned some loot for doing " + NumberUtils.addCommas((Integer)this.getDamageTracker().get(pl.getUniqueId())) + " DMG!"));
                        alreadySent = true;
                    }
                    pl.sendMessage(StringUtils.getCenteredMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + reward.getItemMeta().getDisplayName().replace("(Right Click)", "")));
                }
            }
        }
    }

    public void alertDeathStatPlayer(Player p, LinkedHashMap<UUID, Integer> sortedPlayers, LinkedHashMap<Player, Integer> playerMap, HashMap<UUID, List<ItemStack>> items) {
        String message;
        double percent;
        p.sendMessage("");
        p.sendMessage(StringUtils.getCenteredMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "TOP DAMAGE DEALT"));
        int index = 0;
        int sent = 0;
        int totalDamage = this.getTotalDamageTaken(playerMap);
        for (Map.Entry<Player, Integer> entry : playerMap.entrySet()) {
            if (sent >= 5) continue;
            Player topDamageDealer = entry.getKey();
            int damage = entry.getValue();
            percent = (double)damage / (double)totalDamage * 100.0;
            ++sent;
            message = (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + ++index + ". " + (Object)ChatColor.GREEN + topDamageDealer.getName() + (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + " - " + this.format.format(percent) + "%";
            if (items.containsKey(topDamageDealer.getUniqueId())) {
                message = message + " - " + this.getLootString(items.get(topDamageDealer.getUniqueId()));
            }
            p.sendMessage(StringUtils.getCenteredMessage(message));
        }
        if (index == 0) {
            int shown = 0;
            for (Map.Entry<Player, Integer> entry : playerMap.entrySet()) {
                if (shown >= 10) break;
                Player pl = entry.getKey();
                percent = Double.valueOf(entry.getValue().intValue()) / Double.valueOf(totalDamage) * 100.0;
                message = StringUtils.getCenteredMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + ++index + ". " + (Object)ChatColor.GREEN + pl.getName() + (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + " - " + this.format.format(percent) + "%");
                p.sendMessage(message);
                ++shown;
            }
        }
    }

    public String getLootString(List<ItemStack> items) {
        int ldp = 0;
        int ultimates = 0;
        int godly = 0;
        for (ItemStack item : items) {
            if (item.getItemMeta().getDisplayName().contains("Legendary")) {
                ++ldp;
                continue;
            }
            if (item.getItemMeta().getDisplayName().contains("Ultimate")) {
                ++ultimates;
                continue;
            }
            if (!item.getItemMeta().getDisplayName().contains("Godly")) continue;
            ++godly;
        }
        String retr = "";
        if (ultimates > 0) {
            retr = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ultimates + "x ULTIMATE ";
        }
        if (ldp > 0) {
            retr = retr + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ldp + "x LEGENDARY";
        }
        if (godly > 0) {
            retr = retr + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + godly + "x GODLY";
        }
        return retr.trim();
    }

    public LinkedList<Player> getTopDamage(int amount, boolean checkDamage) {
        LinkedList<Player> topPlayer = new LinkedList<Player>();
        LinkedHashMap<UUID, Integer> sortedMap = MapUtils.sortByComparator(this.getDamageTracker(), false, amount);
        int minRequiredDamage = (int)((double)this.getTotalDamageTaken(null) * 0.1);
        System.out.println("Min REquired DAmage: " + minRequiredDamage);
        for (Map.Entry<UUID, Integer> uuid : sortedMap.entrySet()) {
            Player p;
            if (topPlayer.size() >= amount || checkDamage && uuid.getValue() <= minRequiredDamage || (p = Bukkit.getPlayer((UUID)uuid.getKey())) == null) continue;
            topPlayer.add(p);
        }
        return topPlayer;
    }

    private int getTotalDamageTaken(Map<Player, Integer> playerMap) {
        int totalDamage = 0;
        if (playerMap != null) {
            for (Map.Entry<Player, Integer> entry : playerMap.entrySet()) {
                totalDamage += entry.getValue().intValue();
            }
        } else {
            for (Map.Entry entry : this.getDamageTracker().entrySet()) {
                totalDamage += ((Integer)entry.getValue()).intValue();
            }
        }
        return totalDamage;
    }

    public void onMinionUpdate(Entity minion, Event e) {
    }

    public String getColor(boolean includeBold) {
        return includeBold ? (Object)this.getDifficulty().getColor() + ChatColor.BOLD.toString() : this.getDifficulty().getColor().toString();
    }

    public int getLoops() {
        return this.loops;
    }

    public void setLoops(int loops) {
        this.loops = loops;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public Location getLocation() {
        return this.location;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public LinkedList<ItemStack> getLootPool() {
        return this.lootPool;
    }

    public DamageTracker getDamageTracker() {
        return this.damageTracker;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public long getGetLastDamageDealt() {
        return this.getLastDamageDealt;
    }

    public void setGetLastDamageDealt(long getLastDamageDealt) {
        this.getLastDamageDealt = getLastDamageDealt;
    }

    public BlockModifier getModifier() {
        return this.modifier;
    }

    public AntiFarm getAntiFarm() {
        return this.antiFarm;
    }

    public BossType getBossType() {
        return this.bossType;
    }

    public boolean isNaturallySpawned() {
        return this.naturallySpawned;
    }

    public void setNaturallySpawned(boolean naturallySpawned) {
        this.naturallySpawned = naturallySpawned;
    }
}

