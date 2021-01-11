/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder
 *  com.cosmicpvp.cosmicutils.nms.EntityAttribute
 *  com.cosmicpvp.cosmicutils.nms.EntityUtils
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EssentialsUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.gmail.filoghost.holograms.api.HolographicDisplaysAPI
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  me.astropvp.astropvpskins.AstroItemSkinsAPI
 *  me.astropvp.astropvpskins.enums.SkinType
 *  me.ifamasssxd.cosmicbosses.CosmicBosses
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.BossDespawnEvent
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Chest
 *  org.bukkit.block.DoubleChest
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftFallingSand
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.MagmaCube
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerPortalEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.event.player.PlayerVelocityEvent
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.util.Vector
 */
package com.compilex.CosmicDungeons.dungeons.events;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost.*;
import com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonState;
import com.compilex.CosmicDungeons.events.DungeonStateChangeEvent;
import com.compilex.CosmicDungeons.utils.FallingBlockUtils;
import com.compilex.CosmicDungeons.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.nms.EntityAttribute;
import com.cosmicpvp.cosmicutils.nms.EntityUtils;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.*;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossDespawnEvent;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFallingSand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DestroyedOutpostEvents
extends DungeonEvents {
    private final int MAX_PORTAL_ID = 5;
    private final List<Location> portalWorldLocations = new LinkedList<Location>();
    private final Map<Location, Location> chestLocations = new HashMap<Location, Location>();
    private Map<Integer, PortalPuzzle> portalPuzzleLocations;
    private final List<Location> lavaSpewLocations = Lists.newArrayList();
    public Map<Integer, PortalDirection> activePortalDestinations = new HashMap<Integer, PortalDirection>();
    private final Set<Entity> worldMobs = Collections.newSetFromMap(new WeakHashMap());
    private final Set<Location> lootedChestBlocks = new HashSet<Location>();
    private final List<Location> pressurePlateLocations = Lists.newArrayList();
    private final List<Location> pressurePlatesToActivate = Lists.newArrayList();
    public Location dimensionalMapChest;
    public Location mainYijkiCave;
    public Location airLockLever;
    public Location victoryShip;
    public Location victoryShipLever;
    public Location lavaRealmCenter;
    public Location parkourStart;
    private DestroyedDungeonParkourRestrictionTask parkourRestrictionTask;
    private JunkDimensionTask junkTask;
    private ItemStack dimensionalMap;
    public boolean reversingPortalOrder = false;
    public boolean sentMapNotification;
    private final Cache<UUID, Long> cache = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.SECONDS).build();
    private int lastExplosionPrimeTick;

    public List<Location> getPortalWorldLocations() {
        return this.portalWorldLocations;
    }

    public Map<Integer, PortalDirection> getActivePortalDestinations() {
        return this.activePortalDestinations;
    }

    public Set<Location> getLootedChestBlocks() {
        return this.lootedChestBlocks;
    }

    public Location getDimensionalMapChest() {
        return this.dimensionalMapChest;
    }

    public Location getMainYijkiCave() {
        return this.mainYijkiCave;
    }

    public Location getAirLockLever() {
        return this.airLockLever;
    }

    public Location getVictoryShip() {
        return this.victoryShip;
    }

    public Location getVictoryShipLever() {
        return this.victoryShipLever;
    }

    public Location getLavaRealmCenter() {
        return this.lavaRealmCenter;
    }

    public ItemStack getDimensionalMap() {
        return this.dimensionalMap;
    }

    public boolean isReversingPortalOrder() {
        return this.reversingPortalOrder;
    }

    public boolean isSentMapNotification() {
        return this.sentMapNotification;
    }

    private void updateAllLocations() {
        World world = this.dungeon.getWorld();
        this.chestLocations.forEach((loc, value) -> {
            loc.setWorld(world);
            value.setWorld(world);
        });
        this.lavaSpewLocations.forEach(loc -> loc.setWorld(world));
        if (this.portalWorldLocations != null) {
            this.portalWorldLocations.forEach(loc -> loc.setWorld(world));
        }
        if (this.parkourStart != null) {
            this.parkourStart.setWorld(world);
        }
        if (this.portalPuzzleLocations != null) {
            this.portalPuzzleLocations.values().forEach(puzzle -> puzzle.updateLocations(world));
        }
        if (this.mainYijkiCave != null) {
            this.mainYijkiCave.setWorld(world);
        }
        if (this.pressurePlateLocations != null) {
            this.pressurePlateLocations.forEach(loc -> loc.setWorld(world));
        }
        if (this.airLockLever != null) {
            this.airLockLever.setWorld(world);
        }
        if (this.victoryShip != null) {
            this.victoryShip.setWorld(world);
        }
        if (this.victoryShipLever != null) {
            this.victoryShipLever.setWorld(world);
        }
    }

    public DestroyedOutpostEvents(Dungeon d) {
        super(d);
        this.mainYijkiCave = new Location(d.getWorld(), -79.0, 78.0, -644.0);
        this.chestLocations.put(new Location(d.getWorld(), 39.0, 125.0, 204.0), new Location(d.getWorld(), 43.0, 124.0, 204.0));
        this.chestLocations.put(new Location(d.getWorld(), 39.0, 125.0, 205.0), new Location(d.getWorld(), 43.0, 124.0, 204.0));
        this.chestLocations.put(new Location(d.getWorld(), -68.0, 115.0, 58.0), new Location(d.getWorld(), -67.0, 114.0, 62.0));
        this.chestLocations.put(new Location(d.getWorld(), -69.0, 115.0, 58.0), new Location(d.getWorld(), -67.0, 114.0, 62.0));
        this.chestLocations.put(new Location(d.getWorld(), -109.0, 131.0, 108.0), new Location(d.getWorld(), -108.0, 131.0, 104.0));
        this.chestLocations.put(new Location(d.getWorld(), -110.0, 131.0, 108.0), new Location(d.getWorld(), -108.0, 131.0, 104.0));
        this.chestLocations.put(new Location(d.getWorld(), 27.0, 128.0, -76.0), new Location(d.getWorld(), 31.0, 127.0, -75.0));
        this.chestLocations.put(new Location(d.getWorld(), 27.0, 128.0, -77.0), new Location(d.getWorld(), 31.0, 127.0, -75.0));
        this.chestLocations.put(new Location(d.getWorld(), 164.0, 118.0, 7.0), new Location(d.getWorld(), 160.0, 117.0, 8.0));
        this.chestLocations.put(new Location(d.getWorld(), 164.0, 118.0, 8.0), new Location(d.getWorld(), 160.0, 117.0, 8.0));
        this.chestLocations.put(new Location(d.getWorld(), 208.0, 127.0, 41.0), new Location(d.getWorld(), 212.0, 126.0, 41.0));
        this.chestLocations.put(new Location(d.getWorld(), 208.0, 127.0, 40.0), new Location(d.getWorld(), 212.0, 126.0, 41.0));
        this.portalWorldLocations.add(new Location(d.getWorld(), 452.0, 44.0, -645.0, -87.3f, 6.8f));
        this.portalWorldLocations.add(new Location(d.getWorld(), 439.0, 44.0, -837.0, -87.3f, 6.8f));
        this.portalWorldLocations.add(new Location(d.getWorld(), 474.0, 44.0, -1142.0, -87.3f, 6.8f));
        this.portalWorldLocations.add(new Location(d.getWorld(), 500.0, 44.0, -1466.0, -87.3f, 6.8f));
        this.portalWorldLocations.add(new Location(d.getWorld(), 509.0, 44.0, -1745.0, -87.3f, 6.8f));
        this.portalWorldLocations.add(new Location(d.getWorld(), 419.0, 44.0, -2022.0, -87.3f, 6.8f));
        this.lavaSpewLocations.add(new Location(d.getWorld(), -444.0, 54.0, -756.0));
        this.lavaSpewLocations.add(new Location(d.getWorld(), -429.0, 54.0, -750.0));
        this.lavaSpewLocations.add(new Location(d.getWorld(), -421.0, 55.0, -767.0));
        this.lavaSpewLocations.add(new Location(d.getWorld(), -438.0, 55.0, -774.0));
        this.lavaRealmCenter = new Location(d.getWorld(), -433.0, 71.0, -762.0);
        this.scanForPressurePlates();
        this.loadRegionTriggerEvents();
    }

    public Location getParkourStart() {
        if (this.parkourStart == null || this.parkourStart.getWorld() == null) {
            this.parkourStart = new Location(this.dungeon.getWorld(), 284.5, 206.0, -12.0);
        }
        return this.parkourStart;
    }

    @Override
    public void loadRegionTriggerEvents() {
        this.regionEvents.put("parkour_tower_start", new ParkourTowerRegionEvent(this.dungeon, "parkour_tower_start"));
        this.regionEvents.put("airlock_b", new AirLockBRegionEvent(this.dungeon, "airlock_b"));
        this.regionEvents.put("parkour_tower", new ParkourARegionEvent(this.dungeon, "parkour_tower"));
        this.regionEvents.put("airlock", new AirlockARegionEvent(this.dungeon, "airlock"));
        this.regionEvents.put("yijki_portal_enter", new PortalRegionEvent(this.dungeon, "yijki_portal_enter"));
        this.regionEvents.put("yijki_portal_exit", new YijkiLairSpawnRegionEvent(this.dungeon, "yijki_portal_exit"));
        this.regionEvents.put("yijki_realm", new YijkiLairAreaRegionEvent(this.dungeon, "yijki_realm"));
    }

    private void loadTasks() {
        this.parkourRestrictionTask = new DestroyedDungeonParkourRestrictionTask(this.dungeon, Lists.newArrayList("parkour_tower", "airlock_a"));
        this.parkourRestrictionTask.runTaskTimer(CosmicDungeonsPlugin.get_(), 10L, 10L);
        this.junkTask = new JunkDimensionTask(this.getDungeon());
        this.junkTask.runTaskTimer(CosmicDungeonsPlugin.get(), 20L, 20L);
    }

    public boolean isParkouring() {
        return this.exploredRegions.contains("parkour_tower") && !this.exploredRegions.contains("airlock");
    }

    public void unregisterTasks() {
        if (this.parkourRestrictionTask != null) {
            Bukkit.getScheduler().cancelTask(this.parkourRestrictionTask.getTaskId());
            this.parkourRestrictionTask = null;
        }
        if (this.junkTask != null) {
            this.junkTask.cancel();
            this.junkTask = null;
        }
    }

    public ItemStack createDimensionalMap() {
        ArrayList lore = Lists.newArrayList();
        this.activePortalDestinations.forEach((key, portaldirection) -> lore.add(CC.WhiteB + (key + 1) + "." + CC.Gray + " " + portaldirection.getLoreName()));
        return new NBTWrapper(new ItemBuilder(Material.PAPER, CC.AquaB + "Dimensional Map").lore(lore).build()).setString("cosmicType", "dungeonDimensionalMap").setNBTBase("ench", new NBTTagListBuilder().build()).build();
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.parkourRestrictionTask != null) {
            this.parkourRestrictionTask.onPlayerMove(event);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onYijkiDeath(EntityDeathEvent event) {
        Boss boss = Boss.bosses.get(event.getEntity().getUniqueId());
        if (boss != null && boss.getBossType() == BossType.MOTHER_OF_YIJKI && boss.equals(this.getDungeon().boss)) {
            this.reversingPortalOrder = true;
            this.dungeon.broadcastMessage(CC.White + "You have slain a deity. This entire reality is collapsing in on itself. You must navigate these interdimensional rift portals in the reverse order you took to arrive here in order to escape.");
            this.dungeon.broadcastMessage(CC.White + "Hurry, or you will be lost in this purgatory forever!");
            this.dungeon.updateTimeRestrictionTask(System.currentTimeMillis() + 300000L, "Return to Destroyed Planet");
            PortalPuzzle puzzle = this.portalPuzzleLocations.get(5);
            if (puzzle != null && puzzle.getSpawnLocation() != null) {
                for (Player pl : this.dungeon.getPlayers()) {
                    if (!WorldGuardUtils.isInRegionsContainsFast(pl.getLocation(), (List)Lists.newArrayList((Object[])new String[]{"yijki_realm"}))) continue;
                    this.dungeon.teleportPlayer(puzzle.getSpawnLocation(), pl, true);
                }
            }
        }
    }

    @EventHandler
    public void onDungeonstatusChange(DungeonStateChangeEvent event) {
        if (!event.getDungeon().equals(this.dungeon)) {
            return;
        }
        if (event.getOldState() == DungeonState.IN_PROGRESS && event.getNewState() == DungeonState.COMPLETE) {
            ((DestroyedOutpostDungeon)this.dungeon).onDungeonComplete();
        } else if (event.getOldState() == DungeonState.LOADING && event.getNewState() == DungeonState.IN_PROGRESS) {
            this.dungeon.setObjectiveHologram(null);
            this.activateRegion("parkour_tower_start");
            this.dungeon.broadcastMessage(ChatColor.WHITE + "The dungeon portal has closed, it is time to begin!");
            for (Player p : this.dungeon.getPlayers()) {
                DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(p);
                dp.logDungeonAttempt(this.dungeon);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().hasMetadata("lavaSlime") && Math.random() <= 0.5) {
            event.getDrops().clear();
            event.setDroppedExp(0);
            Item item = event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), this.getDungeon().createReturningClock());
            item.setMetadata("clockItem", new FixedMetadataValue(CosmicDungeonsPlugin.get(), ""));
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_PICKUP, 4.0f, 1.1f);
            event.getEntity().remove();
        }
        if (this.worldMobs.remove(event.getEntity())) {
            event.setDroppedExp(0);
            event.getEntity().remove();
            event.getDrops().clear();
        }
    }

    private boolean isQuartzBlock(Block block) {
        Material type = block.getType();
        return type == Material.QUARTZ_BLOCK || type == Material.DOUBLE_STEP && block.getData() == 7;
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) throws NoSuchMethodException {
        if (event.getEntity() instanceof FallingBlock) {
            if (event.getEntity().hasMetadata("parkourBlock")) {
                Block fallen = event.getBlock();
                Material type = fallen.getType();
                if (type == Material.AIR) {
                    fallen = fallen.getRelative(BlockFace.DOWN);
                }
                if (this.isQuartzBlock(fallen)) {
                    this.parkourRestrictionTask.setToPortal(fallen, null);
                    event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 1.1f);
                }
                event.setCancelled(true);
                event.getEntity().remove();
            } else if (event.getEntity().hasMetadata("dungeonLavaBlock")) {
                if (WorldGuardUtils.isInRegionsFast(event.getEntity().getLocation(), new String[]{"lava_realm_center"})) {
                    return;
                }
                Collection<Entity> magmas = event.getEntity().getWorld().getEntitiesByClasses(MagmaCube.class);
                if (magmas.size() >= 10) {
                    boolean foundOldMagmas = false;
                    for (Entity magma : magmas) {
                        if (magma.getTicksLived() >= 1200 && magma.getLocation().getY() <= 65.0) {
                            magma.remove();
                            foundOldMagmas = true;
                            continue;
                        }
                        if (magma.getTicksLived() < 4800) continue;
                        magma.remove();
                        foundOldMagmas = true;
                    }
                    if (!foundOldMagmas) {
                        return;
                    }
                }
                MagmaCube cube = (MagmaCube)event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation().add(0.0, 4.0, 0.0), EntityType.MAGMA_CUBE);
                cube.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                EntityUtils.setGenericAttribute(cube, EntityAttribute.MOVEMENT_SPEED, 0.38f);
                cube.getEquipment().setItemInHand(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 4).build());
                cube.getEquipment().setItemInHandDropChance(0.0f);
                cube.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(this.random.nextInt(10) <= 2 ? Enchantment.THORNS : Enchantment.DURABILITY, 1).build());
                cube.getEquipment().setChestplateDropChance(0.0f);
                cube.getWorld().playSound(cube.getLocation(), Sound.GHAST_MOAN, 3.0f, 1.3f);
                cube.setMetadata("lavaSlime", new FixedMetadataValue(CosmicDungeonsPlugin.get(), ""));
                cube.setMetadata("no_slime_split", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), ""));
                cube.setSize(this.random.nextInt(4) + 3);
                cube.setMaxHealth(200.0);
                cube.setHealth(cube.getMaxHealth());
                cube.setCustomName(CC.RedB + "Lava Minion");
                cube.setCustomNameVisible(true);
                this.worldMobs.add(cube);
            } else if (event.getEntity().hasMetadata("dungeonIceBlock")) {
                event.setCancelled(true);
                event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, (Object)Material.ICE);
                event.getEntity().remove();
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntityType() == EntityType.WITHER_SKULL && event.getEntity().getWorld().equals(this.dungeon.getWorld())) {
            this.lastExplosionPrimeTick = MinecraftServerUtils.getCurrentServerTick();
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerVelocityChange(PlayerVelocityEvent event) {
        if (MinecraftServer.currentTick == this.lastExplosionPrimeTick && event.getPlayer().getWorld().getName().equals(this.dungeon.getWorldName())) {
            if (this.cache.asMap().containsKey(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                Bukkit.getLogger().info("Cancelling Velocity for " + event.getPlayer().getName() + " due to being knockedback within the alst 1");
            } else {
                this.cache.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Item && event.getEntity().hasMetadata("clockItem")) {
            event.setCancelled(true);
        } else if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.VOID && event.getEntity().getWorld().getName().equals(this.dungeon.getWorld().getName()) && event.getEntity().getLocation().getY() <= 1.0 && !event.getEntity().isDead()) {
            event.setCancelled(true);
            event.setDamage(0.0);
            ((Player)event.getEntity()).setHealth(0.0);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onEntityDamageByEntityEventLowest(EntityDamageByEntityEvent event) {
        if (event.getDamager() != null && (event.getDamager().getType() == EntityType.WITHER_SKULL || event.getDamager().hasMetadata("boss")) && event.getEntity() instanceof Player) {
            if (!event.getDamager().getWorld().getName().equals(this.dungeon.getWorldName())) {
                return;
            }
            event.getEntity().setMetadata("lastWitherDamage", new FixedMetadataValue(CosmicBosses.get(), System.currentTimeMillis() + 1000L));
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof FallingBlock && event.getEntity() instanceof Player && event.getDamager().hasMetadata("parkourBlock")) {
            event.setDamage(10.0);
            event.getEntity().setVelocity(new Vector(this.random.nextDouble(), 0.1, this.random.nextDouble()));
        } else if (event.getEntity().hasMetadata("lavaSlime")) {
            if (event.getDamager() instanceof Player || event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof Player) {
                return;
            }
            event.setCancelled(true);
        } else if (event.getEntity().hasMetadata("clockItem")) {
            event.setCancelled(true);
        } else if (event.getDamager() instanceof MagmaCube && event.getDamager().hasMetadata("lavaSlime") && event.getEntity() instanceof Player && !event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0) {
            Player damaged = (Player)event.getEntity();
            event.setDamage(0.1);
            double damage = damaged.getHealth() * 0.25;
            if (damage >= damaged.getHealth()) {
                damaged.setHealth(0.0);
                event.setDamage(0.0);
            } else {
                damaged.setHealth(damaged.getHealth() - damage);
            }
        }
    }

    private void teleportFromOtherDimension(Player player) {
        this.dungeon.teleportPlayer(this.mainYijkiCave, player, true);
        if (this.getDungeon().boss != null && this.getDungeon().boss.isInDifferentDimension()) {
            this.getDungeon().boss.teleportBackToSpawn();
        } else if (this.getDungeon().boss == null && this.getDungeon().isBossPendingRespawn()) {
            Bukkit.getLogger().info("Respawning boss due to " + player.getName() + " teleporting back to dimension and Yiji being despawned!");
            this.getDungeon().setBossPendingRespawn(false);
            this.regionEvents.get("yijki_realm").run();
        }
    }

    @EventHandler
    public void onBossDespawn(BossDespawnEvent event) {
        Bukkit.getLogger().info("[CosmicDungeons] Found despawned boss: " + event.getEntity());
        if (event.getBoss() != null && event.getBoss().equals(this.getDungeon().boss)) {
            LivingEntity ent = event.getBoss().getEntity();
            if (ent.getHealth() <= 0.0) {
                Bukkit.getLogger().info("[CosmicDungeons] Found despawned Dungeon boss but its health was " + ent.getHealth() + " so we're not respawning it..");
                return;
            }
            boolean anyoneInYijkiRealm = this.getDungeon().getPlayers().stream().anyMatch(pl -> {
                LinkedList regions = WorldGuardUtils.getRegionsIn(pl);
                return regions != null && regions.contains("yijki_realm");
            });
            if (!anyoneInYijkiRealm) {
                Bukkit.getLogger().info("[CosmicDungeons] Yijki despawned while the players where in another dimension!!!");
                this.getDungeon().bossPendingRespawn = true;
                this.getDungeon().boss = null;
            }
        }
    }

    @Override
    public void onPlayerEnterRegion(Player player, String name) {
        if (name == null) {
            return;
        }
        if (name.equalsIgnoreCase("yijki_portal_enter")) {
            if (!this.sentMapNotification && this.dimensionalMapChest != null && !this.lootedChestBlocks.contains(this.dimensionalMapChest)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicDungeonsPlugin.get(), () -> this.dungeon.broadcastMessage(CC.RedB + "(!) " + CC.Red + "Perhaps there is a mapping of these dimensional portals somewhere back on the planet surface."), 2L);
                this.sentMapNotification = false;
            }
            this.teleportPlayerToFirstPortal(player);
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteractClock(PlayerInteractEvent event) {
        ItemStack clicked = event.getItem();
        Player player = event.getPlayer();
        if (clicked != null && this.getDungeon().isClockOfReturning(clicked)) {
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            LinkedList stored = WorldGuardUtils.getRegionsIn(player);
            if (stored == null || !stored.contains("lava_realm")) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot use the Clock of Returning outside of the Lava Realm!");
                return;
            }
            player.getInventory().remove(clicked);
            this.teleportFromOtherDimension(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 0));
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.0f, 1.1f);
            player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL, 1.0f, 1.6f);
            player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have been transported back into the cave!");
        } else if (this.junkTask != null) {
            this.junkTask.onPlayerInteract(event);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (this.junkTask != null) {
            this.junkTask.onPlayerTeleport(event);
        } else if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && event.getTo() != null && event.getFrom() != null && event.getTo().getWorld().equals(this.dungeon.getWorld())) {
            if (WorldGuardUtils.isInRegionsFast(event.getTo(), new String[]{"yijki_realm"})) {
                if (!WorldGuardUtils.isInRegionsFast(event.getFrom(), new String[]{"yiki_realm"})) {
                    event.setCancelled(true);
                    Bukkit.getLogger().info("[CosmicDungeons] Cancelling Enderpearl teleport for " + event.getPlayer().getName() + ", tried to enter yijki_realm!");
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerInteractChest(PlayerInteractEvent event) {
        if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            Material type = block.getType();
            if (type == Material.CHEST || type == Material.TRAPPED_CHEST) {
                Location blockLocation = block.getLocation();
                if (this.lootChest(blockLocation, block, event.getPlayer())) {
                    return;
                }
            } else if (type == Material.LEVER && block.getLocation().equals(this.airLockLever) && !this.exploredRegions.contains("airlock_b")) {
                int pressed = this.checkAllPressurePlates();
                int correctPressed = this.getRightPressurePlatesPressed();
                if (pressed > this.pressurePlatesToActivate.size()) {
                    this.dungeon.broadcastMessage(ChatColor.RED + "ERROR: Invalid, too many inputs! Correct inputs detected: " + CC.RedB + correctPressed);
                    block.getWorld().playSound(block.getLocation(), Sound.FIZZ, 1.0f, 1.0f);
                } else if (this.pressurePlatesToActivate.size() != correctPressed) {
                    this.dungeon.broadcastMessage(ChatColor.RED + "ERROR: Invalid input data, correct inputs detected: " + CC.RedB + correctPressed);
                    block.getWorld().playSound(block.getLocation(), Sound.FIZZ, 1.0f, 1.0f);
                } else {
                    this.activateRegion("airlock_b");
                }
            } else if (type == Material.LEVER && block.getLocation().equals(this.victoryShipLever) && this.dungeon.getDungeonStatus() != DungeonState.COMPLETE) {
                if (!this.reversingPortalOrder) {
                    event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must defeat the Mother Of Yijki before leaving this planet!");
                    return;
                }
                this.dungeon.setDungeonStatus(DungeonState.COMPLETE);
                this.spawnVictoryShipPortal();
            }
        }
    }

    private boolean activateRegion(String regionName) {
        if (this.exploredRegions.contains(regionName)) {
            return false;
        }
        this.regionEvents.get(regionName).run();
        this.exploredRegions.add(regionName);
        return true;
    }

    public Location getChestValue(Location key) {
        for (Map.Entry<Location, Location> entry : this.chestLocations.entrySet()) {
            Location k = entry.getKey();
            if (k.getWorld() == null || !k.getWorld().getName().equals(key.getWorld().getName()) || k.getBlockX() != key.getBlockX() || k.getBlockZ() != key.getBlockZ() || k.getBlockY() != key.getBlockY()) continue;
            return entry.getValue();
        }
        return null;
    }

    public boolean lootChest(Location blockLocation, Block block, Player player) {
        Chest chest = (Chest)block.getState();
        Location spawnLocation = this.getChestValue(blockLocation);
        if (spawnLocation == null || this.lootedChestBlocks.contains(blockLocation)) {
            return false;
        }
        InventoryHolder holder = chest.getInventory().getHolder();
        if (holder instanceof DoubleChest) {
            DoubleChest doubleChest = (DoubleChest)holder;
            Location leftSide = ((Chest)doubleChest.getLeftSide()).getLocation();
            Location rightSide = ((Chest)doubleChest.getRightSide()).getLocation();
            boolean bool = this.dimensionalMapChest != null && (this.dimensionalMapChest.equals(leftSide) || this.dimensionalMapChest.equals(rightSide) || this.dimensionalMapChest.equals(blockLocation));
            this.lootedChestBlocks.add(leftSide);
            this.lootedChestBlocks.add(rightSide);
            if (!bool) {
                this.spawnMobsNearChest(spawnLocation);
                Bukkit.getLogger().info("Spawning mob!");
            } else {
                this.dimensionalMap = this.createDimensionalMap();
                ((Chest)doubleChest.getLeftSide()).getBlockInventory().clear();
                ((Chest)doubleChest.getRightSide()).getBlockInventory().clear();
                chest.getInventory().addItem(this.dimensionalMap);
                chest.update();
                Bukkit.getLogger().info("adding new map into chest: " + this.dimensionalMap + " Map Chest: " + this.dimensionalMapChest);
                this.dungeon.broadcastMessage(player.getName() + " has located the Dimensional Map!");
            }
            return true;
        }
        boolean mapChest = this.dimensionalMapChest != null && this.dimensionalMapChest.equals(blockLocation);
        Location at = this.getChestValue(blockLocation);
        if (at != null && !this.lootedChestBlocks.contains(blockLocation)) {
            this.lootedChestBlocks.add(blockLocation);
            if (!mapChest) {
                this.dungeon.broadcastMessage(player.getName() + " opened a booby trapped chest, you must defeat the Disciple of Yijki!");
                this.spawnMobsNearChest(at);
            } else {
                this.dimensionalMap = this.createDimensionalMap();
                chest.getInventory().clear();
                chest.getInventory().addItem(this.dimensionalMap);
                this.dungeon.broadcastMessage(player.getName() + " has located the Dimensional Map!");
            }
            Bukkit.getLogger().info("[CosmicDungeons] Looted chest!");
            return true;
        }
        return false;
    }

    private int getRightPressurePlatesPressed() {
        int platesPressed = 0;
        for (Location loc : this.pressurePlatesToActivate) {
            Block block = loc.getBlock();
            if (block.getData() != 1) continue;
            ++platesPressed;
        }
        return platesPressed;
    }

    private int checkAllPressurePlates() {
        int platesPressed = 0;
        for (Location loc : this.pressurePlateLocations) {
            Block block = loc.getBlock();
            if (block.getData() != 1) continue;
            ++platesPressed;
        }
        return platesPressed;
    }

    private void spawnMobsNearChest(Location location) {
        Boss boss = BossType.DUNGEON_DISCIPLE.createInstance(location);
        boss.onBossSpawn();
        LivingEntity livingEntity = boss.getEntity();
        this.worldMobs.add(livingEntity);
        livingEntity.getNearbyEntities(64.0, 64.0, 64.0).stream().filter(ent -> ent instanceof Player).forEach(ent -> {
            Player pl = (Player)ent;
            if (pl.isDead()) {
                return;
            }
            double damage = 10.0;
            pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
            pl.damage(0.1);
            if (pl.getHealth() - damage <= 0.0) {
                pl.setHealth(0.0);
            } else {
                pl.setHealth(pl.getHealth() - damage);
            }
        });
        Bukkit.getLogger().info(String.format("[CosmicDestroyedOutpost] Spawning %s mobs at %s due to looting the wrong chest!", 1, LocationUtils.convertLocationToString(location)));
    }

    public void teleportPlayerToLastPortal(Player player) {
        PortalPuzzle first = this.portalPuzzleLocations.get(5);
        this.dungeon.teleportPlayer(first.getSpawnLocation(), player, true);
    }

    public void teleportPlayerToFirstPortal(Player player) {
        PortalPuzzle first = this.portalPuzzleLocations.get(0);
        this.dungeon.teleportPlayer(first.getSpawnLocation(), player, true);
    }

    public void reset() {
        this.lootedChestBlocks.clear();
        this.worldMobs.clear();
        this.activePortalDestinations.clear();
        this.dimensionalMapChest = null;
        this.exploredRegions.clear();
        this.pressurePlatesToActivate.clear();
        this.unregisterTasks();
    }

    public void restart() {
        BlockState state;
        if (this.dungeon.getWorld() == null) {
            this.dungeon.reset();
            Bukkit.getLogger().info("[CosmicDungeons] Forced to reload: " + this.dungeon.getWorldName() + " new world: " + this.dungeon.getWorld());
        }
        this.updateAllLocations();
        this.chestLocations.keySet().forEach(loc -> {
            if (loc.getWorld() == null) {
                loc.setWorld(this.dungeon.getWorld());
            }
            Block blo = loc.getBlock();
            blo.setType(Material.CHEST);
        });
        if (this.portalPuzzleLocations == null || this.portalPuzzleLocations.isEmpty()) {
            long start = System.currentTimeMillis();
            this.portalPuzzleLocations = new HashMap<Integer, PortalPuzzle>();
            int i = 0;
            for (Location loc2 : this.portalWorldLocations) {
                this.portalPuzzleLocations.put(i++, new PortalPuzzle(loc2));
            }
            Bukkit.getLogger().info("[CosmicDungeons (OutpostEvents)] Loaded " + this.portalPuzzleLocations.size() + " Portal Puzzle Locations in " + (System.currentTimeMillis() - start) + "ms");
        }
        if (this.airLockLever == null) {
            this.airLockLever = new Location(this.dungeon.getWorld(), 219.0, 108.0, -19.0);
        }
        if (this.victoryShip == null) {
            this.victoryShip = new Location(this.dungeon.getWorld(), 69.0, 195.0, 36.0);
        }
        if (this.victoryShipLever == null) {
            this.victoryShipLever = new Location(this.dungeon.getWorld(), 71.0, 196.0, 40.0);
        }
        this.scanForPressurePlates();
        this.selectRandomPlates();
        this.findNewPortalDirections();
        this.loadTasks();
        ArrayList locs = Lists.newArrayList(this.chestLocations.keySet());
        Location chosen = (Location)locs.get(this.random.nextInt(locs.size()));
        Block bl = chosen.getBlock();
        if (!bl.getType().equals(Material.CHEST)) {
            bl.setType(Material.CHEST);
        }
        if ((state = bl.getState()) instanceof Chest) {
            Chest chest = (Chest)state;
            this.dimensionalMapChest = chest.getLocation();
        } else {
            Bukkit.getLogger().info("[CosmicDungeons] unable to find chest for dimensional map.");
        }
    }

    private void findNewPortalDirections() {
        for (int i = 0; i < 6; ++i) {
            PortalDirection dest = PortalDirection.values()[ThreadLocalRandom.current().nextInt(PortalDirection.values().length)];
            this.activePortalDestinations.put(i, dest);
            Bukkit.getLogger().info("[CosmicDungeons (OutpostEvents)] Destination: " + i + " = " + dest);
        }
    }

    private void selectRandomPlates() {
        this.pressurePlatesToActivate.clear();
        if (this.pressurePlateLocations.isEmpty()) {
            Bukkit.getLogger().info("UNABLE TO FIND PRESSURE PLATES!!!");
            return;
        }
        ArrayList valid = Lists.newArrayList(this.pressurePlateLocations);
        for (int i = 0; i < 6; ++i) {
            if (valid.isEmpty()) {
                Bukkit.getLogger().info("Locations empty? Plates found: " + this.pressurePlateLocations.size());
                break;
            }
            Location loc = (Location)valid.get(this.random.nextInt(valid.size()));
            valid.remove(loc);
            this.pressurePlatesToActivate.add(loc);
        }
        Bukkit.getLogger().info("[CosmicDungeons] Selected " + this.pressurePlatesToActivate.size() + " Pressure Plates");
    }

    private void scanForPressurePlates() {
        if (this.pressurePlateLocations.isEmpty()) {
            Location start = new Location(this.dungeon.getWorld(), 229.0, 107.0, -9.0);
            int radius = 9;
            int yRadius = 2;
            for (int x = -9; x < 9; ++x) {
                for (int z = -9; z < 9; ++z) {
                    for (int y = -1; y < 2; ++y) {
                        Location location = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() + y, start.getBlockZ() + z);
                        Block block = location.getBlock();
                        Material type = block.getType();
                        if (type != Material.GOLD_PLATE && type != Material.IRON_PLATE && type != Material.STONE_PLATE && type != Material.WOOD_PLATE) continue;
                        this.pressurePlateLocations.add(location);
                    }
                }
            }
            Bukkit.getLogger().info("[CosmicDungeons] Registered " + this.pressurePlateLocations.size() + " pressure plates.");
        }
    }

    public DestroyedOutpostDungeon getDungeon() {
        return (DestroyedOutpostDungeon)this.dungeon;
    }

    private void teleportToVictoryShip(Player pl) {
        this.dungeon.teleportPlayer(this.victoryShip, pl, true);
        pl.playSound(pl.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
    }

    private void spawnVictoryShipPortal() {
        Location portalBlock = new Location(this.dungeon.getWorld(), 77.0, 196.0, 36.0);
        Block block = portalBlock.getBlock();
        if (block.getType() != Material.PORTAL) {
            block.setTypeIdAndData(Material.PORTAL.getId(), (byte)2, false);
            block.setMetadata("dungeonExitPortalBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), this.dungeon.getDungeonID()));
            Block above = block.getRelative(BlockFace.UP);
            above.setTypeIdAndData(Material.PORTAL.getId(), (byte)2, false);
            above.setMetadata("dungeonExitPortalBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), this.dungeon.getDungeonID()));
        }
        this.dungeon.setObjectiveHologram(HologramsAPI.createHologram(CosmicDungeonsPlugin.get_(), block.getLocation().add(0.5, 2.25, 0.5)));
        this.dungeon.getObjectiveHologram().insertTextLine(0, ChatColor.RED + "" + ChatColor.BOLD + "Exit");
    }

    private boolean checkPortalTravel(PlayerPortalEvent e) {
        return this.checkPortalBlock(e.getPlayer(), e.getFrom().getBlock()) || this.checkPortalBlock(e.getPlayer(), e.getFrom().clone().add(0.0, 1.0, 0.0).getBlock());
    }

    private boolean checkPortalBlock(Player player, Block block) {
        if (block.hasMetadata("dungeonExitPortalBlock")) {
            int dungeonID = block.getMetadata("dungeonExitPortalBlock").get(0).asInt();
            Dungeon dungeon = DungeonsAPI.get().getDungeon(dungeonID);
            if (dungeon.getDungeonStatus() == DungeonState.COMPLETE) {
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                return true;
            }
        }
        return false;
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerPortalTravel(PlayerPortalEvent event) {
        if (event.getFrom() != null && event.getFrom().getWorld().getName().equals(this.dungeon.getWorldName())) {
            event.setCancelled(true);
            Block bl = event.getFrom().getBlock();
            if (this.checkPortalTravel(event)) {
                return;
            }
            Player pl = event.getPlayer();
            Material type = bl.getType();
            if (type == Material.PORTAL) {
                Location blockLoc = bl.getLocation();
                PortalPuzzle puzzleToTeleportTo = null;
                for (Map.Entry<Integer, PortalPuzzle> puzzleEntry : this.portalPuzzleLocations.entrySet()) {
                    int newLevel;
                    int maxLevelToTeleportTo;
                    Integer key = puzzleEntry.getKey();
                    PortalPuzzle puzzle = puzzleEntry.getValue();
                    PortalDirection directionEntered = puzzle.findDirection(blockLoc);
                    if (directionEntered == null) continue;
                    PortalDirection directionWeNeed = this.activePortalDestinations.get(key);
                    if (directionWeNeed == directionEntered) {
                        if (this.reversingPortalOrder) {
                            if (key <= 0) {
                                this.teleportToVictoryShip(pl);
                                continue;
                            }
                            puzzleToTeleportTo = this.portalPuzzleLocations.get(key - 1);
                            if (puzzleToTeleportTo != null) continue;
                            Bukkit.getLogger().info("[CosmicDungeons] Key: " + (key - 1) + " was null as a PortalPuzzle on reverse!");
                            puzzleToTeleportTo = this.portalPuzzleLocations.get(0);
                            continue;
                        }
                        if (key >= 5) {
                            this.dungeon.teleportPlayer(this.mainYijkiCave, pl, true);
                            return;
                        }
                        puzzleToTeleportTo = this.portalPuzzleLocations.get(key + 1);
                        if (puzzleToTeleportTo != null) break;
                        Bukkit.getLogger().info("[CosmicDungeons] Key: " + (key + 1) + " was null as a PortalPuzzle");
                        puzzleToTeleportTo = this.portalPuzzleLocations.get(0);
                        break;
                    }
                    if (this.random.nextBoolean()) {
                        Bukkit.getLogger().info("[CosmicDungeons (OutpostEvents)] Sending " + pl.getName() + " into Junk Dimension for wrong portal: " + directionEntered + " Wanted: " + directionWeNeed + " on level: " + key);
                        this.junkTask.startJunkTimer(pl);
                        pl.playSound(pl.getLocation(), Sound.WITHER_DEATH, 3.0f, 1.1f);
                        return;
                    }
                    int n = maxLevelToTeleportTo = key == 5 ? key - 1 : key;
                    if (key < 2) {
                        maxLevelToTeleportTo = 3;
                    }
                    if ((puzzleToTeleportTo = this.portalPuzzleLocations.get(newLevel = this.random.nextInt(maxLevelToTeleportTo))) == null) {
                        puzzleToTeleportTo = this.portalPuzzleLocations.get(0);
                    }
                    if (puzzleToTeleportTo == null) {
                        Bukkit.getLogger().info("[CosmicDungeons] Unable to find PortalPuzzle at " + newLevel + " and at 0, " + this.portalPuzzleLocations.size() + " total entries.");
                        return;
                    }
                    Bukkit.getLogger().info("[CosmicDungeons] Teleporting " + pl.getName() + " to level: " + newLevel + " from WRONG LEVEL at: " + key + " entered: " + directionEntered + " We need: " + directionWeNeed);
                    break;
                }
                if (puzzleToTeleportTo != null) {
                    this.dungeon.teleportPlayer(puzzleToTeleportTo.getSpawnLocation(), pl, true);
                    pl.playSound(pl.getLocation(), Sound.WITHER_SHOOT, 1.0f, 1.1f);
                    Bukkit.getLogger().info("[CosmicDungeons] Teleporting " + pl.getName() + " to CORRECT portal: " + puzzleToTeleportTo.getSpawnLocation());
                    if (!pl.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                    }
                } else {
                    Bukkit.getLogger().info("Portal Type: " + type + " Didnt find any portal blocks for " + blockLoc);
                }
            } else if (type == Material.ENDER_PORTAL) {
                if (this.parkourRestrictionTask.getPortalBlocks().contains(bl) && !pl.isDead() && !pl.getGameMode().equals(GameMode.CREATIVE)) {
                    event.setCancelled(true);
                    pl.setHealth(0.0);
                    pl.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "The floor underneath you collapsed and you fell into the void of space!");
                } else if (WorldGuardUtils.isInRegionsFast(bl.getLocation(), new String[]{"ice_realm_exit"})) {
                    event.setCancelled(true);
                    this.teleportFromOtherDimension(pl);
                }
            }
        }
    }

    class DestroyedDungeonParkourRestrictionTask
    extends BukkitRunnable {
        Dungeon dungeon;
        private long lastBlockDrop = 0L;
        private long lastLavaBlockSpawn;
        private long lastIcicleDropped;
        private final List<String> regions;
        private final Map<Block, BlockHealth> blockHealth = new HashMap<Block, BlockHealth>();
        private final Set<Block> portalBlocks = new HashSet<Block>();

        public Set<Block> getPortalBlocks() {
            return this.portalBlocks;
        }

        public DestroyedDungeonParkourRestrictionTask(Dungeon d, List<String> regions) {
            this.dungeon = d;
            this.regions = regions;
        }

        public void onPlayerMove(PlayerMoveEvent event) {
            String regionMovingTo;
            DungeonPlayer dungeonPlayer;
            Location to = event.getTo();
            Location from = event.getFrom();
            Player player = event.getPlayer();
            if (from != null && to != null && from.getWorld().getName().equals(to.getWorld().getName()) && (to.getBlockZ() != from.getBlockZ() || to.getBlockZ() != from.getBlockZ() || to.getBlockY() != from.getBlockY()) && to.getWorld().getName().equals(this.dungeon.getWorldName()) && (dungeonPlayer = DungeonsAPI.get().getPlayerDungeonData(player)) != null && dungeonPlayer.getCurrentDungeon() != null && dungeonPlayer.getCurrentDungeon().equals(this.dungeon) && (regionMovingTo = WorldGuardUtils.getRegionName(to)) != null && regionMovingTo.equals("parkour_tower")) {
                Block block = to.getBlock();
                Material blockType = block.getType();
                if (blockType == Material.AIR) {
                    block = block.getRelative(BlockFace.DOWN);
                    blockType = block.getType();
                }
                if (DestroyedOutpostEvents.this.isQuartzBlock(block) || blockType == Material.ENDER_PORTAL && this.portalBlocks.contains(block)) {
                    this.setToPortal(block, player);
                }
            }
        }

        public void setToPortal(Block block, Player player) {
            if (this.portalBlocks.add(block)) {
                WorldUtil.allowEnderPortalOutsideOverworld(true);
                block.setTypeIdAndData(Material.ENDER_PORTAL.getId(), (byte)0, false);
                WorldUtil.allowEnderPortalOutsideOverworld(false);
                if (player != null) {
                    Location l = ((DestroyedOutpostEvents)this.dungeon.getDungeonEvents()).getParkourStart();
                    player.setMetadata("bypassCombatTeleport", new FixedMetadataValue(CosmicDungeonsPlugin.get(), l));
                    player.teleport(l, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.0f, 1.4f);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 0));
                }
                return;
            }
            if (player != null && !player.isDead() && !EssentialsUtils.isInGodMode(player)) {
                player.setHealth(0.0);
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "The floor underneath you collapsed and you fell into the void of space!");
            }
        }

        public void damageBlock(Block block, Player player) {
            BlockHealth health = this.blockHealth.computeIfAbsent(block, bl -> new BlockHealth());
            health.damageBlockIfCan(block, player);
        }

        public void run() {
            boolean dropFallingBlock = this.lastBlockDrop == 0L || System.currentTimeMillis() - this.lastBlockDrop >= 2500L;
            ArrayList<Player> parkourPlayers = Lists.newArrayList();
            ArrayList<Player> playersInLavaRealm = Lists.newArrayList();
            ArrayList<Player> playersInIceRealm = Lists.newArrayList();
            for (Player p : this.dungeon.getPlayers()) {
                String region;
                DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(p);
                if (dp.getCurrentDungeon() == null || !dp.getCurrentDungeon().equals(this.dungeon)) continue;
                if (p.getAllowFlight() && !p.isOp()) {
                    p.setAllowFlight(false);
                }
                if (p.isFlying() && !p.isOp()) {
                    p.setFlying(false);
                }
                if ((region = WorldGuardUtils.getRegionName(p.getLocation())) != null && dropFallingBlock && p.getLocation().getY() < 200.0 && region.equals("parkour_tower") && !EssentialsUtils.isInGodMode(p)) {
                    parkourPlayers.add(p);
                } else if (region != null && region.equals("lava_realm")) {
                    playersInLavaRealm.add(p);
                } else if (region != null && region.equals("ice_realm")) {
                    playersInIceRealm.add(p);
                }
                if (!this.regions.contains(region) || EssentialsUtils.isInGodMode(p)) continue;
                if (p.getInventory().getBoots() != null && AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.SPRING_2020_BOOTS)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 0));
                }
                for (PotionEffect pe : p.getActivePotionEffects()) {
                    if (pe.getType().equals(PotionEffectType.JUMP) && pe.getAmplifier() != 0 && p.getInventory().getBoots() != null && AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.SPRING_2020_BOOTS)) {
                        p.removePotionEffect(pe.getType());
                        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 0));
                        continue;
                    }
                    if (pe.getType().equals(PotionEffectType.JUMP) && pe.getAmplifier() == 0) {
                        return;
                    }
                    p.removePotionEffect(pe.getType());
                }
            }
            if (playersInLavaRealm.size() > 0) {
                if (this.lastLavaBlockSpawn <= System.currentTimeMillis()) {
                    Location loc = DestroyedOutpostEvents.this.lavaSpewLocations.get(DestroyedOutpostEvents.this.random.nextInt(DestroyedOutpostEvents.this.lavaSpewLocations.size()));
                    FallingBlock block = loc.getWorld().spawnFallingBlock(loc.clone().add(0.0, 14.0, 0.0), Material.STAINED_GLASS, DyeColor.ORANGE.getWoolData());
                    block.setMetadata("dungeonLavaBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), ""));
                    Vector vel = DestroyedOutpostEvents.this.lavaRealmCenter.toVector().subtract(loc.toVector());
                    if (vel.length() != 0.0) {
                        vel.normalize();
                    }
                    vel.multiply(new Vector(0.9 + DestroyedOutpostEvents.this.random.nextGaussian(), 1.0, 0.9 + DestroyedOutpostEvents.this.random.nextGaussian())).multiply(-2.6);
                    vel.setY(1.3 + DestroyedOutpostEvents.this.random.nextGaussian());
                    block.setVelocity(vel);
                    loc.getWorld().playSound(loc, Sound.LAVA_POP, 4.0f, 1.1f);
                    block.setDropItem(false);
                    block.setFireTicks(100);
                    this.lastLavaBlockSpawn = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3L);
                }
                double lavaDamage = 2.0;
                for (Player pl : playersInLavaRealm) {
                    Block bl;
                    Material type;
                    if (pl.isDead() || (type = (bl = pl.getLocation().getBlock()).getType()) != Material.LAVA && type != Material.STATIONARY_LAVA) continue;
                    if (pl.getHealth() <= 2.0) {
                        pl.setHealth(0.0);
                    } else {
                        pl.setHealth(pl.getHealth() - 2.0);
                    }
                    pl.damage(0.1);
                }
            }
            if (playersInIceRealm.size() > 0 && this.lastIcicleDropped <= System.currentTimeMillis()) {
                Player player = (Player)playersInIceRealm.get(DestroyedOutpostEvents.this.random.nextInt(playersInIceRealm.size()));
                Location loc2 = LocationUtils.getNearbyLocation(player.getLocation().add(0.0, 2.0, 0.0), 0, 2);
                FallingBlock block2 = FallingBlockUtils.createCustomFallingBlock(loc2.clone().add(0.0, 1.0, 0.0), Material.ICE, (byte)0, false);
                block2.setMetadata("dungeonIceBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), ""));
                loc2.getWorld().playSound(loc2, Sound.ZOMBIE_WOODBREAK, 0.1f, 1.9f);
                this.lastIcicleDropped = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2L);
            }
            if (parkourPlayers.size() > 0) {
                Player random = (Player)parkourPlayers.get(ThreadLocalRandom.current().nextInt(parkourPlayers.size()));
                Location toSpawnAt = LocationUtils.getNearbyLocation(random.getLocation().add(0.0, 5.0, 0.0), 0, 2);
                if (toSpawnAt.getY() >= 204.0) {
                    toSpawnAt.setY(203.0);
                }
                if (toSpawnAt.getBlock().isEmpty()) {
                    this.lastBlockDrop = System.currentTimeMillis();
                    boolean stained = ThreadLocalRandom.current().nextInt(10) == 0;
                    FallingBlock block3 = toSpawnAt.getWorld().spawnFallingBlock(toSpawnAt, stained ? Material.STAINED_GLASS : Material.QUARTZ_BLOCK, stained ? DyeColor.PURPLE.getWoolData() : (byte)0);
                    block3.setMetadata("parkourBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), this.dungeon.getDungeonType().name()));
                    block3.setDropItem(false);
                    ((CraftFallingSand)block3).getHandle().a(true);
                }
            }
        }

        class BlockHealth {
            private final int DEFAULT_BLOCK_DURABILITY = 5;
            public int health = 5;
            public long lastAffected;

            public boolean damageBlockIfCan(Block block, Player damager) {
                if (this.health <= 0) {
                    return false;
                }
                if (this.lastAffected == 0L || System.currentTimeMillis() - this.lastAffected >= 5000L) {
                    this.lastAffected = System.currentTimeMillis();
                    if (this.health == 1) {
                        this.health = 0;
                        block.getWorld().playEffect(block.getLocation().add(0.5, 0.5, 0.5), Effect.STEP_SOUND, (Object)block.getType());
                        block.setTypeIdAndData(0, (byte)0, false);
                    } else {
                        --this.health;
                        int maxBreaks = 10;
                        double percentLeft = (double)this.health * 1.0 / 5.0;
                        int breaks = (int)Math.min(10.0, Math.floor(10.0 - percentLeft * 10.0));
                        ArrayList pls = Lists.newArrayList((Object[])new Player[]{damager});
                        damager.getNearbyEntities(64.0, 64.0, 64.0).forEach(ent -> {
                            if (ent instanceof Player) {
                                pls.add(ent);
                            }
                        });
                        WorldUtil.sendBlockBreakAnimation(block, breaks, pls);
                    }
                    return true;
                }
                return false;
            }
        }
    }
}

