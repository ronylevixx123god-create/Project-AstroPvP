package me.ifamasssxd.cosmicbosses.struct;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.holidayspirit.HolidaySpirit;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueBloater;
import me.ifamasssxd.cosmicbosses.bosses.soulreaper.SoulReaper;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.BattlefieldSnowman;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.planetnul.CorruptCosmonaut;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbomination;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.struct.enums.Equipable;
import me.ifamasssxd.cosmicbosses.utils.*;
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
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class Boss extends Equipable implements Healable {
    public static ConcurrentHashMap<UUID, Boss> bosses;
    protected int loops;
    public LivingEntity entity;
    public Location location;
    protected UUID uuid;
    private List<Entity> minions;
    private LinkedList<ItemStack> lootPool;
    private DamageTracker damageTracker;
    private Phase phase;
    private HashMap<BossAbility, Long> abilityCooldowns;
    private long getLastDamageDealt;
    protected BlockModifier modifier;
    protected Random random;
    protected AntiFarm antiFarm;
    private BossType bossType;
    private boolean naturallySpawned;
    private boolean dead;
    DecimalFormat format;
    private int totalPlayerDamageReceived;

    public Boss(final Location location, final BossType bossType) {
        this.loops = 0;
        this.lootPool = new LinkedList<ItemStack>();
        this.damageTracker = new DamageTracker();
        this.phase = Phase.ONE;
        this.random = new Random();
        this.naturallySpawned = false;
        this.dead = false;
        this.format = new DecimalFormat("#.##");
        this.totalPlayerDamageReceived = 0;
        this.location = location;
        this.bossType = bossType;
        this.antiFarm = new AntiFarm(this);
    }

    public LivingEntity spawnBoss() {
        final LivingEntity e = (LivingEntity) this.location.getWorld().spawnEntity(this.location.clone(), this.getEntityType());
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

    protected int getSeconds(final int amount) {
        return amount * 4;
    }

    public LivingEntity setupBoss(final LivingEntity e) {
        this.uuid = e.getUniqueId();
        (this.entity = e).setMetadata("do_not_clear", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) ""));
        e.setMetadata("boss", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) ""));
        e.setMaxHealth((double) this.getMaxHealth());
        e.setRemoveWhenFarAway(false);
        e.setHealth(e.getMaxHealth());
        this.setEnt(e);
        e.setCustomName(this.getColor(true) + this.getCustomName());
        e.setCustomNameVisible(true);
        e.setRemoveWhenFarAway(false);
        Boss.bosses.put(e.getUniqueId(), this);
        return e;
    }

    public abstract EntityType getEntityType();

    public void onBossSpawn() {
        this.spawnBoss();
        this.alert();
    }

    public abstract String getCustomName();

    public abstract String getName();

    public void onTick() {
    }

    public void onUpdate() throws Exception {
        final LivingEntity li = this.getEntity();
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
                for (final Entity pl : this.getNearbyEntities(20.0, EntityType.PLAYER)) {
                    final Player p = (Player) pl;
                    this.sendMessage(p, ChatColor.ITALIC + bossMessage.getMessage());
                }
            }
        }
    }

    public abstract void cleanup();

    public void onAttackPlayer(final EntityDamageByEntityEvent event, final Player player) throws Exception {
        final Location loc = this.getEntity().getLocation();
        if (!loc.getWorld().getName().equals("world_private") && !loc.getWorld().getName().startsWith("cosmic-station") && loc.getY() < loc.getWorld().getHighestBlockYAt(loc)) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 3.0);
        }
//        todo: method doesnt exist
//        if (DungeonsAPI.hasDungeonDebuff(player, DungeonDebuff.BOSSES_DEAL_MORE_DAMAGE)) {
//            event.setDamage(event.getDamage() * 2.5D);
//        }
    }

    public void onDamageTaken(final EntityDamageByEntityEvent event, final Entity damager) throws Exception {
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && event.getDamager() instanceof Arrow) {
            if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
                Player p = (Player) ((Projectile) event.getDamager()).getShooter();
//                todo: wtf is this method
//                if (DungeonsAPI.isDungeonType(p, DungeonDebuff.NO_BOW_DAMAGE_TO_BOSSES)) {
//                    p.sendMessage(CC.translate("&c&l(!) &cYou cannot use bows to damage bosses in this heroic dungeon!"));
//                    event.setCancelled(true);
//                }
            }
            return;
        }
        if (event.getDamager() instanceof Player && CosmicBosses.getInstance().isFactionUpgradesEnabled()) {
            final int heroicLevel = UpgradeUtils.getUpgradeLevel((Player) event.getDamager(), "HEROIC_BOSS_MASTERY");
            double mult = 1.0;
            if (heroicLevel > 0) {
                mult = ((heroicLevel == 1) ? 2.05 : ((heroicLevel == 2) ? 2.15 : ((heroicLevel == 3) ? 2.25 : 1.0)));
            } else {
                final int level = FactionUpgradeAPI.getPerkLevel((Player) event.getDamager(), FactionUpgrade.BOSS_MASTERY);
                mult = ((level == 1) ? 1.25 : ((level == 2) ? 1.5 : ((level == 3) ? 1.75 : ((level == 4) ? 2.0 : 1.0))));
            }

//            todo: method doesnt exist
//            if (DungeonsAPI.get().getPlayerDungeonData((Player)damager).getCurrentDungeon() != null && DungeonsAPI.get().getPlayerDungeonData((Player)damager).getCurrentDungeon().getBossSlayerTrinket()) {
//                mult += 0.5;
//            }

            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * mult);
        }
        if (event.getDamager() instanceof Player) {
            // tooo: look into this method
            //            if (DungeonsAPI.isDungeonType((Player)event.getDamager(), DungeonDebuff.BOSSES_TAKE_LESS_DAMAGE)) {
//                event.setDamage(event.getDamage() * 0.25);
//            }
//            if (DungeonsAPI.isDungeonType((Player)event.getDamager(), DungeonDebuff.DEAL_30HP_MAX_PER_HIT)) {
//                if (event.getDamage() > 30) event.setDamage(30);
//            }
        }
        if (this instanceof GuardBoss) {
            ((GuardBoss) this).onDamage(this, event);
        }
    }

    public void onDamageTaken(final EntityDamageEvent event) throws Exception {
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            event.setCancelled(true);
            final Entity ent = event.getEntity();
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) CosmicBosses.getInstance(), () -> {
                if (ent != null && !ent.isDead()) {
                    ent.setFireTicks(0);
                }
                return;
            }, 1L);
        }
        final Location loc = this.getEntity().getLocation();
        if (!loc.getWorld().getName().equals("world_private") && loc.getWorld().getName().startsWith("cosmic-station") && loc.getY() < loc.getWorld().getHighestBlockYAt(loc)) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) / 2.0);
        }
    }

    public void onEntityChangeBlock(final EntityChangeBlockEvent event, final Entity boss) {
    }

    public void onEntityTeleport(final EntityTeleportEvent event, final Entity boss) {
    }

    public abstract String getTitle();

    public abstract void onPhaseChange(final Phase p0);

    public void onDeath(final EntityDeathEvent event) throws Exception {
        if (this.dead) {
            return;
        }
        this.cleanup();
        if (event != null && !(this instanceof MiniBoss) && (!(this instanceof PlagueBloater) || !this.location.getWorld().getName().equals(CosmicBosses.battlefieldWorld)) && !(this instanceof DungeonYijki) && !(this instanceof CorruptCosmonaut)) {
            event.getDrops().clear();
            final String msg = StringUtils.getCenteredMessage(ChatColor.RED + ChatColor.BOLD.toString() + "The " + this.getName() + " has been defeated!");
            final String starting = ChatColor.RED + "Defeated by ";
            final String killMsg = this.getKillMessage();
            for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (this instanceof GlobalBoss || BossUtils.isWarzone(player.getLocation()) || WorldGuardUtils.getRegionName(player.getLocation()).contains("spawn")) {
                    player.sendMessage(msg);
                    if (killMsg == null || killMsg.length() <= starting.length()) {
                        continue;
                    }
                    player.sendMessage(killMsg + "!");
                }
            }
            try {
                final HashMap<UUID, List<ItemStack>> loot = this.givePlayerLootPool();
                this.alertDeathStats(loot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event != null && event.getEntity().hasMetadata("heroicEnvoyBoss")) {
            event.getDrops().clear();
            this.getLootPool().clear();
            this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.GOD));
            this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.GOD));
            try {
                final HashMap<UUID, List<ItemStack>> loot2 = this.givePlayerLootPool();
                this.alertDeathStats(loot2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (event != null) {
            event.getEntity().setMetadata("killed", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) ""));
        }
        this.dead = true;
        this.clearScoreboard();
    }

    private String getKillMessage() {
        final String starting = ChatColor.RED + "Defeated by ";
        final StringBuilder killers = new StringBuilder(starting);
        int place = 0;
        final int minRequiredDamage = (int) (this.getMaxHealth() * 0.1);
        final List<Player> players = this.getTopDamage(10, false);
        for (final Player p : players) {
            ++place;
            final int damage = (this.getDamageTracker()).get(p.getUniqueId());
            if (damage >= minRequiredDamage) {
                ChatColor color;
                if (place == 1) {
                    color = ChatColor.GOLD;
                } else if (place == 2) {
                    color = ChatColor.YELLOW;
                } else if (place == 3) {
                    color = ChatColor.RED;
                } else {
                    color = ChatColor.GREEN;
                }
                killers.append(color + p.getName() + color + ", ");
            }
        }
        String killMsg = killers.toString();
        if (killMsg.equals(starting)) {
            if (players.size() > 0) {
                final Player top = players.get(0);
                if (top != null) {
                    killMsg += top.getName();
                } else {
                    killMsg = "Cosmonauts";
                }
            } else {
                killMsg = "Cosmonauts!";
            }
        }
        if (killMsg.endsWith(", ")) {
            killMsg = killMsg.substring(0, killMsg.length() - 4);
        }
        return StringUtils.getCenteredMessage(killMsg);
    }

    protected void alert() {
        if (this instanceof SoulReaper || this instanceof HolidaySpirit || this instanceof BattlefieldSnowman || this instanceof CorruptCosmonaut || this instanceof DungeonYijki || this instanceof EvilAbomination || (this instanceof PlagueBloater && this.getEntity().getWorld().getName().equals(CosmicBosses.battlefieldWorld))) {
            return;
        }
        String msg;
        if (this.naturallySpawned) {
            msg = this.getColor(true) + "(!) " + this.getColor(false) + "A " + this.getColor(true) + ChatColor.UNDERLINE.toString() + this.getCustomName() + this.getColor(false) + " Boss has been summoned to the /spawn WarZone, at " + this.getColor(true) + this.location.getBlockX() + "x, " + this.location.getBlockY() + "y, " + this.location.getBlockZ() + "z " + this.getColor(false) + "defend DecimateMC!";
//          todo: method doesnt exist
            //            try {
//                for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
//                    ClientCommsAPI.sendEvent(p, "Previous Warzone Boss", EventType.COUNT_UP, System.currentTimeMillis(), 0L);
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
        } else {
            msg = this.getColor(true) + "(!) " + this.getColor(false) + "A " + this.getColor(true) + ChatColor.UNDERLINE.toString() + this.getCustomName() + this.getColor(false) + " Boss has been summoned nearby, defend DecimateMC!";
        }
        if (this instanceof EndDragon) {
            msg = CC.RedB + "(!) " + CC.Red + "A " + CC.DarkRedB + CC.Underline + this.getCustomName() + CC.Red + " has been awoken in the Ender Dimension. Travel to " + CC.DarkRed + "/warp end" + CC.Red + " to subdue it before it finds a way into the Cosmonaut realm!";
        }
        final Location loc = this.getLocation();
        final String debug = ChatColor.YELLOW + "Spawning boss " + this.getCustomName() + ChatColor.YELLOW + " at " + ChatColor.YELLOW + ChatColor.BOLD.toString() + "x" + loc.getBlockX() + " y" + loc.getBlockY() + " z" + loc.getBlockZ() + "!";
        final List<Player> pls = (List<Player>) (this.naturallySpawned ? Arrays.asList(Bukkit.getOnlinePlayers()) : Lists.newArrayList());
        if (!this.naturallySpawned) {
            for (final Entity ent : this.getEntity().getNearbyEntities(32.0, 32.0, 32.0)) {
                if (ent instanceof Player) {
                    final Player pl = (Player) ent;
                    pls.add(pl);
                }
            }
        }
        final boolean dragon = this instanceof EndDragon;
        final Iterator<Player> iterator3 = pls.iterator();
        while (iterator3.hasNext()) {
            final Player pl = iterator3.next();
            pl.sendMessage("");
            pl.sendMessage(msg);
            pl.playSound(pl.getLocation(), dragon ? Sound.ENDERDRAGON_GROWL : Sound.CHICKEN_EGG_POP, 4.0f, 1.2f);
            pl.sendMessage("");
            if (pl.hasPermission("cosmicbosses.alert.coords")) {
                pl.sendMessage(debug);
            }
        }
        Bukkit.getLogger().info("Spawning boss " + this.getCustomName() + " at " + this.getLocation().toString() + "!");
    }

    public abstract void onMiscUpdate(final Event p0) throws Exception;

    public abstract DamageType getDamageType();

    public abstract Difficulty getDifficulty();

    public abstract int getMaxHealth();

    public abstract List<BossMessage> getGeneralMessages();

    public BossMessage getBossMessage(final int phase) {
        for (final BossMessage msg : this.getGeneralMessages()) {
            if (msg.getPhase() == phase) {
                return msg;
            }
        }
        return null;
    }

    public BossMessage getStartingMessage() {
        for (final BossMessage msg : this.getGeneralMessages()) {
            if (msg.getPhase() == -1) {
                return msg;
            }
        }
        return null;
    }

    public BossMessage getGeneralMessage() {
        final List<BossMessage> msg = this.getGeneralMessages().stream().filter(bMsg -> bMsg.getPhase() == 0 && !bMsg.isPhaseDependent()).collect(Collectors.toList());
        if (msg.size() <= 0) {
            return null;
        }
        return msg.get(this.random.nextInt(msg.size()));
    }

    public boolean checkStuck() throws Exception {
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
        for (final Entity minion : this.minions) {
            if (minion != null) {
                minion.remove();
            }
        }
        this.minions.clear();
    }

    public HashMap<BossAbility, Long> getAbilityCooldown() {
        if (this.abilityCooldowns == null) {
            this.abilityCooldowns = new HashMap<BossAbility, Long>();
        }
        return this.abilityCooldowns;
    }

    public List<Entity> getNearbyEntities(final double radius, final EntityType type) {
        final List<Entity> entities = new ArrayList<Entity>();
        for (final Entity e : this.getEntity().getNearbyEntities(radius, radius, radius)) {
            if (type != null && e.getType() != type) {
                continue;
            }
            entities.add(e);
        }
        return entities;
    }

    public boolean hasMinions() {
        return this.minions != null;
    }

    public void removeMinion(final Entity ent) {
        if (this.hasMinions()) {
            this.minions.remove(ent);
        }
    }

    public void updateScoreboard() {
        if (!CosmicBosses.getInstance().isScoreboardEnabled()) {
            return;
        }
        for (final Entity ent : this.getNearbyEntities(32.0, EntityType.PLAYER)) {
            final Player p = (Player) ent;
            ScoreboardUtils.updateBossScoreboard(p, this);
        }
    }

    public void clearScoreboard() {
        final DamageTracker tracker = this.getDamageTracker();
        for (final UUID uuid : (tracker).keySet()) {
            final Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                ScoreboardUtils.clearBossScoreboard(p);
            }
        }
    }

    public Entity spawnMinion(final EntityType type, final Location l) {
        final Entity ent = l.getWorld().spawnEntity(l, type);
        ent.setMetadata("minion", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) this.getEntity().getUniqueId()));
        ent.setMetadata("do_not_clear", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) ""));
        this.getMinions().add(ent);
        if (ent instanceof LivingEntity) {
            final LivingEntity li = (LivingEntity) ent;
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
            final EntityInsentient insent = (EntityInsentient) ((CraftLivingEntity) ent).getHandle();
            BossUtils.clearGoalSelectors(insent);
            BossUtils.addSelector(insent, 0, (PathfinderGoal) new PathfinderGoalFloat(insent));
            BossUtils.addSelector(insent, 8, (PathfinderGoal) new PathfinderGoalRandomLookaround(insent));
        }
        return ent;
    }

    public boolean isVulnerable(final DamageType damageType) {
        return true;
    }

    public boolean isBossSpawned() {
        return this.getEntity() != null && this.getEntity().isValid();
    }

    @Override
    public void onHeal(final int amount) {
        if (this.getEntity().isDead()) {
            return;
        }
        if (this.getEntity().getHealth() + amount > this.getEntity().getMaxHealth()) {
            this.getEntity().setHealth(this.getEntity().getMaxHealth());
        } else {
            this.getEntity().setHealth(this.getEntity().getHealth() + amount);
        }
    }

    public void alertNearby(final int radius, final String message, final Sound sound, final float speed) {
        for (final Entity e : this.getNearbyEntities(radius, EntityType.PLAYER)) {
            final Player p = (Player) e;
            p.sendMessage(message);
            if (sound != null) {
                p.playSound(p.getLocation(), sound, 5.0f, speed);
            }
        }
    }

    public void sendMessage(final String msg) {
        this.sendMessage(msg, null, 0.0f);
    }

    public int getMessageRadius() {
        return 20;
    }

    public void sendMessage(final String msg, final Sound sound, final float speed) {
        final List<Player> players = Lists.newArrayList();
        for (final UUID uuid : (this.getDamageTracker()).keySet()) {
            final Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                players.add(p);
            }
        }
        for (final Entity ent : this.getNearbyEntities(this.getMessageRadius(), EntityType.PLAYER)) {
            final Player pl = (Player) ent;
            if (!players.contains(pl)) {
                players.add(pl);
            }
        }
        for (final Player p2 : players) {
            if (p2 != null && p2.getWorld().equals(this.getEntity().getWorld()) && p2.getLocation().distanceSquared(this.getEntity().getLocation()) <= 1000.0) {
                this.sendMessage(p2, msg);
                if (sound == null) {
                    continue;
                }
                p2.playSound(p2.getLocation(), sound, 2.0f, speed);
            }
        }
    }

    public void sendMessage(final Player player, final String msg) {
        player.sendMessage(this.getColor(true) + this.getName() + " >> " + ChatColor.WHITE + msg.replace("[NAME]", player.getName()));
    }

    public HashMap<UUID, List<ItemStack>> givePlayerLootPool() {
        final HashMap<UUID, List<ItemStack>> loot = new HashMap<UUID, List<ItemStack>>();
        final LinkedList<Player> topDamage = this.getTopDamage(this.getLootPool().size(), true);
        int amountPerPerson = (int) Math.floor(this.getLootPool().size() / (double) topDamage.size());
        if (amountPerPerson > 2) {
            amountPerPerson = 2;
        }
        ItemStack title = null;
        if (this.getTitle() != null) {
            title = ItemUtils.getTitleVoucher(this.getTitle());
        }
        if (topDamage.size() > 0) {
            final Player most = topDamage.get(0);
            if (most != null) {
                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "fquests give TOP_DOG " + most.getName());
                Bukkit.getPluginManager().callEvent((Event) new CustomCosmicPlayerEvent(most, "BOSS_TOP_DAMAGE", new Object[]{this.bossType.name(), this.getEntity()}));
            }
        }
        int placement = 1;
        for (final Player player : topDamage) {
            boolean noRoom = false;
            if (title != null && this.random.nextInt(100) == 0 && !this.addItem(player, title)) {
                noRoom = true;
            }
            System.out.println("Top Damage: " + player.getName() + " Amount Per: " + amountPerPerson);
            final List<ItemStack> itemsReceived = Lists.newArrayList();
            for (int i = 0; i < amountPerPerson && !this.getLootPool().isEmpty(); ++i) {
                final ItemStack reward = this.getLootPool().remove();
                itemsReceived.add(reward);
                if (!this.addItem(player, reward)) {
                    noRoom = true;
                }
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0f, 0.4f);
            }
            if (noRoom) {
                new BukkitRunnable() {
                    public void run() {
                        if (player.isValid()) {
                            player.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You have new items in your " + ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "/collect" + ChatColor.YELLOW + " stash!");
                        }
                    }
                }.runTaskLater((Plugin) CosmicBosses.getInstance(), 5L);
            }
            if (!itemsReceived.isEmpty()) {
                loot.put(player.getUniqueId(), itemsReceived);
            }
            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "contest add " + player.getName() + " bossKills 1");
        }
        if (this.getLootPool().size() > 0) {
            Bukkit.getLogger().info("Found left over loot: " + this.getLootPool());
            for (final Player pl : topDamage) {
                if (this.getLootPool().isEmpty()) {
                    break;
                }
                final List<ItemStack> lootGiven = loot.containsKey(pl.getUniqueId()) ? loot.get(pl.getUniqueId()) : new ArrayList<ItemStack>();
                if (lootGiven.size() >= 2) {
                    continue;
                }
                final ItemStack reward2 = this.getLootPool().remove();
                lootGiven.add(reward2);
                if (!this.addItem(pl, reward2)) {
                    new BukkitRunnable() {
                        public void run() {
                            if (pl.isValid()) {
                                pl.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You have new items in your " + ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "/collect" + ChatColor.YELLOW + " stash!");
                            }
                        }
                    }.runTaskLater((Plugin) CosmicBosses.getInstance(), 5L);
                }
                loot.put(pl.getUniqueId(), lootGiven);
            }
        }
        return loot;
    }

    private boolean addItem(final Player player, final ItemStack item) {
        if (player.getWorld().getName().startsWith("world_duel") || player.getInventory().firstEmpty() == -1) {
            RewardInventory.addReward(player, item.clone());
            return false;
        }
        player.getInventory().addItem(new ItemStack[]{item.clone()});
        return true;
    }

    public LinkedHashMap<Player, Integer> getPlayerMap(final LinkedHashMap<UUID, Integer> uuidMap) {
        final LinkedHashMap<Player, Integer> map = new LinkedHashMap<Player, Integer>();
        for (final Map.Entry<UUID, Integer> entry : uuidMap.entrySet()) {
            final Player pl = Bukkit.getPlayer((UUID) entry.getKey());
            if (pl != null) {
                if (map.containsKey(pl)) {
                    continue;
                }
                map.put(pl, entry.getValue());
            }
        }
        return map;
    }

    public void alertDeathStats(final HashMap<UUID, List<ItemStack>> lootGiven) {
        final LinkedHashMap<UUID, Integer> topPlayers = MapUtils.sortByComparator(this.getDamageTracker(), false, 10);
        final LinkedHashMap<Player, Integer> playerMap = this.getPlayerMap(topPlayers);
        if (this instanceof GlobalBoss) {
            for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                this.alertDeathStatPlayer(player, topPlayers, playerMap, lootGiven);
            }
        } else {
            for (final Entity e : this.getEntity().getNearbyEntities(20.0, 20.0, 20.0)) {
                if (e instanceof Player) {
                    final Player player2 = (Player) e;
                    this.alertDeathStatPlayer(player2, topPlayers, playerMap, lootGiven);
                }
            }
            final int minRequiredDamage = (int) (this.getTotalDamageTaken(playerMap) * 0.1);
            final String centered = StringUtils.getCenteredMessage(ChatColor.RED + "You did not do enough damage to earn loot!");
            for (final Map.Entry<Player, Integer> players : playerMap.entrySet()) {
                final Player pl = players.getKey();
                if (players.getValue() < minRequiredDamage) {
                    pl.sendMessage(centered);
                } else {
                    if (!lootGiven.containsKey(pl.getUniqueId())) {
                        continue;
                    }
                    final List<ItemStack> items = lootGiven.get(pl.getUniqueId());
                    boolean alreadySent = false;
                    for (final ItemStack reward : items) {
                        if (!alreadySent) {
                            pl.sendMessage("");
                            pl.sendMessage(StringUtils.getCenteredMessage(ChatColor.GRAY + "You earned some loot for doing " + NumberUtils.addCommas((this.getDamageTracker()).get(pl.getUniqueId())) + " DMG!"));
                            alreadySent = true;
                        }
                        pl.sendMessage(StringUtils.getCenteredMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + reward.getItemMeta().getDisplayName().replace("(Right Click)", "")));
                    }
                }
            }
        }
    }

    public void alertDeathStatPlayer(final Player p, final LinkedHashMap<UUID, Integer> sortedPlayers, final LinkedHashMap<Player, Integer> playerMap, final HashMap<UUID, List<ItemStack>> items) {
        p.sendMessage("");
        p.sendMessage(StringUtils.getCenteredMessage(ChatColor.RED + ChatColor.BOLD.toString() + "TOP DAMAGE DEALT"));
        int index = 0;
        int sent = 0;
        final int totalDamage = this.getTotalDamageTaken(playerMap);
        for (final Map.Entry<Player, Integer> entry : playerMap.entrySet()) {
            if (sent >= 5) {
                continue;
            }
            final Player topDamageDealer = entry.getKey();
            final int damage = entry.getValue();
            final double percent = damage / (double) totalDamage * 100.0;
            ++sent;
            String message = ChatColor.GREEN + ChatColor.BOLD.toString() + ++index + ". " + ChatColor.GREEN + topDamageDealer.getName() + ChatColor.GREEN + ChatColor.BOLD.toString() + " - " + this.format.format(percent) + "%";
            if (items.containsKey(topDamageDealer.getUniqueId())) {
                message = message + " - " + this.getLootString(items.get(topDamageDealer.getUniqueId()));
            }
            p.sendMessage(StringUtils.getCenteredMessage(message));
        }
        if (index == 0) {
            int shown = 0;
            for (final Map.Entry<Player, Integer> entry2 : playerMap.entrySet()) {
                if (shown >= 10) {
                    break;
                }
                final Player pl = entry2.getKey();
                final double percent = entry2.getValue() / (double) totalDamage * 100.0;
                ++index;
                final String message = StringUtils.getCenteredMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + index + ". " + ChatColor.GREEN + pl.getName() + ChatColor.GREEN + ChatColor.BOLD.toString() + " - " + this.format.format(percent) + "%");
                p.sendMessage(message);
                ++shown;
            }
        }
    }

    public String getLootString(final List<ItemStack> items) {
        int ldp = 0;
        int ultimates = 0;
        int godly = 0;
        for (final ItemStack item : items) {
            if (item.getItemMeta().getDisplayName().contains("Legendary")) {
                ++ldp;
            } else if (item.getItemMeta().getDisplayName().contains("Ultimate")) {
                ++ultimates;
            } else {
                if (!item.getItemMeta().getDisplayName().contains("Godly")) {
                    continue;
                }
                ++godly;
            }
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

    public LinkedList<Player> getTopDamage(final int amount, final boolean checkDamage) {
        final LinkedList<Player> topPlayer = new LinkedList<Player>();
        final LinkedHashMap<UUID, Integer> sortedMap = MapUtils.sortByComparator(this.getDamageTracker(), false, amount);
        final int minRequiredDamage = (int) (this.getTotalDamageTaken(null) * 0.1);
        System.out.println("Min REquired DAmage: " + minRequiredDamage);
        for (final Map.Entry<UUID, Integer> uuid : sortedMap.entrySet()) {
            if (topPlayer.size() < amount && (!checkDamage || uuid.getValue() > minRequiredDamage)) {
                final Player p = Bukkit.getPlayer((UUID) uuid.getKey());
                if (p == null) {
                    continue;
                }
                topPlayer.add(p);
            }
        }
        return topPlayer;
    }

    private int getTotalDamageTaken(final Map<Player, Integer> playerMap) {
        int totalDamage = 0;
        if (playerMap != null) {
            for (final Map.Entry<Player, Integer> entry : playerMap.entrySet()) {
                totalDamage += entry.getValue();
            }
        } else {
            for (final Map.Entry<UUID, Integer> entry2 : this.getDamageTracker().entrySet()) {
                totalDamage += entry2.getValue();
            }
        }
        return totalDamage;
    }

    public void onMinionUpdate(final Entity minion, final Event e) throws Exception {
    }

    public String getColor(final boolean includeBold) {
        return includeBold ? (this.getDifficulty().getColor() + ChatColor.BOLD.toString()) : this.getDifficulty().getColor().toString();
    }

    public int getLoops() {
        return this.loops;
    }

    public void setLoops(final int loops) {
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

    public void setPhase(final Phase phase) {
        this.phase = phase;
    }

    public long getGetLastDamageDealt() {
        return this.getLastDamageDealt;
    }

    public void setGetLastDamageDealt(final long getLastDamageDealt) {
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

    public void setNaturallySpawned(final boolean naturallySpawned) {
        this.naturallySpawned = naturallySpawned;
    }

    static {
        Boss.bosses = new ConcurrentHashMap<UUID, Boss>();
    }
}
