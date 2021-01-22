package com.cosmicpvp.CosmicSpaceChests.listeners;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.libs.ChestLootSelector;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerInteractChestListener
implements Listener {
    static CosmicSpaceChestsPlugin plugin;

    public PlayerInteractChestListener(CosmicSpaceChestsPlugin fdp) {
        plugin = fdp;
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerOpenDropPackage(PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().getType() == Material.CHEST) {
            ItemStack is = e.getItem();
            if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }
            if (!CosmicSpaceChestsAPI.isDropPackageChest(is)) {
                return;
            }
            String chestName = is.getItemMeta().getDisplayName();
            final Player pl = e.getPlayer();
            ItemRarity ir = null;
            if (chestName.startsWith(ChatColor.WHITE.toString())) {
                ir = ItemRarity.SIMPLE;
            }
            if (chestName.startsWith(ChatColor.GREEN.toString())) {
                ir = ItemRarity.UNIQUE;
            }
            if (chestName.startsWith(ChatColor.AQUA.toString())) {
                ir = ItemRarity.ELITE;
            }
            if (chestName.startsWith(ChatColor.YELLOW.toString())) {
                ir = ItemRarity.ULTIMATE;
            }
            if (chestName.startsWith(ChatColor.GOLD.toString())) {
                ir = ItemRarity.LEGENDARY;
            }
            if (chestName.startsWith(ChatColor.RED.toString())) {
                ir = ItemRarity.GOD;
            }
            if (ir == null) {
                return;
            }
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            if (this.getEmptySlots(pl) < 5) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You need 5 empty inventory slots to open a Cosmic Chest.");
                return;
            }
            if (this.getTarget(e.getPlayer()) != null) {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot be aiming at an NPC while opening your Cosmic Chest.");
                return;
            }
            if (e.getPlayer().hasMetadata("lastInventoryCloseEvent")) {
                long tick = ((MetadataValue)pl.getMetadata("lastInventoryCloseEvent").get(0)).asLong();
                if ((long)MinecraftServer.currentTick <= tick + 2L) {
                    Bukkit.getLogger().info("[Arkkit (InventoryRatelimit)] Blocking playerInteract (" + (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() ? e.getItem().getItemMeta().getDisplayName() : e.getItem().getType().name()) + ") for " + e.getPlayer().getName() + " due to inventoryCloseEvent <= 2ticks ago.");
                    e.setCancelled(true);
                    e.setUseItemInHand(Event.Result.DENY);
                    e.setUseInteractedBlock(Event.Result.DENY);
                    Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                        @Override
                        public void run() {
                            pl.updateInventory();
                        }
                    }, 1L);
                    return;
                }
                e.getPlayer().removeMetadata("lastInventoryCloseEvent", (Plugin)Arkkit.get());
            }
            if (is.getAmount() > 1) {
                is.setAmount(is.getAmount() - 1);
                pl.setItemInHand(is);
            } else {
                pl.setItemInHand(new ItemStack(Material.AIR));
            }
            ChestLootSelector cls = new ChestLootSelector(pl, ir);
            pl.setMetadata("chestLootSelector", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)cls));
            cls.open();
        }
    }

    private int getEmptySlots(Player p) {
        int x = 0;
        for (ItemStack i : p.getInventory()) {
            if (i != null && i.getType() != Material.AIR) continue;
            ++x;
        }
        return x;
    }

    public LivingEntity getTarget(Player player) {
        List<Entity> nearbyE = player.getNearbyEntities(5.0, 5.0, 5.0);
        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
        for (Entity e : nearbyE) {
            if (!(e instanceof LivingEntity)) continue;
            livingE.add((LivingEntity)e);
        }
        BlockIterator bItr = new BlockIterator((LivingEntity)player, 5);
        while (bItr.hasNext()) {
            Block block = bItr.next();
            int bx = block.getX();
            int by = block.getY();
            int bz = block.getZ();
            for (LivingEntity e : livingE) {
                if (!e.hasMetadata("NPC")) continue;
                Location loc = e.getLocation();
                double ex = loc.getX();
                double ey = loc.getY();
                double ez = loc.getZ();
                if (!((double)bx - 0.75 <= ex) || !(ex <= (double)bx + 1.75) || !((double)bz - 0.75 <= ez) || !(ez <= (double)bz + 1.75) || !((double)(by - 1) <= ey) || !(ey <= (double)by + 2.5)) continue;
                return e;
            }
        }
        return null;
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerInteract(PlayerInteractEvent e) throws Exception {
        Block b;
        if (e.hasBlock() && e.getAction() == Action.LEFT_CLICK_BLOCK && (b = e.getClickedBlock()).getType() == Material.CHEST) {
            Chest c;
            if (e.getPlayer().hasMetadata("spectator")) {
                return;
            }
            if (CosmicSpaceChestsPlugin.chest_locations.containsKey((Object)b.getLocation())) {
                Player pl = e.getPlayer();
                final Location loc = b.getLocation();
                ItemRarity ir = CosmicSpaceChestsPlugin.chest_locations.getChestRarity(loc);
                if (!SpaceFlareListener.getSpaceFlares().contains((Object)loc)) {
                    if (ir == ItemRarity.LEGENDARY && Math.random() <= 0.75) {
                        if (Math.random() < 0.4) {
                            this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.GHAST);
                        }
                        if (Math.random() < 0.6) {
                            this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                            this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                        }
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                    } else if (ir == ItemRarity.ULTIMATE) {
                        if (Math.random() <= 0.6) {
                            if (new Random().nextBoolean()) {
                                this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                            } else {
                                this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.GHAST);
                            }
                        }
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                    }
                    String worldName = loc.getWorld().getName();
                    if (worldName.equals("world")) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests add ENVOY_LOOTER " + pl.getName()));
                        if (Bukkit.getPluginManager().isPluginEnabled("CosmicContests")) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("contests add " + pl.getName() + " envBroken 1"));
                        }
                    }
                    if (worldName.equalsIgnoreCase("world") || worldName.equalsIgnoreCase("world_arena")) {
                        Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(pl, "ENVOY_LOOTER", new Object[]{loc}));
                    }
                } else {
                    SpaceFlareListener.getSpaceFlares().remove((Object)loc);
                }
                CosmicSpaceChestsPlugin.chest_locations.removeChest(loc);
                b.setType(Material.AIR);
                pl.playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 0.5f, 1.2f);
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)plugin, new Runnable(){

                    @Override
                    public void run() {
                        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition((int)Math.round(loc.getX()), (int)Math.round(loc.getY()), (int)Math.round(loc.getZ())), 54, false);
                        ((CraftServer)plugin.getServer()).getServer().getPlayerList().sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 24.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                    }
                });
                return;
            }
            boolean warzone = false;
            String region = MiscUtil.getRegionName(e.getClickedBlock().getLocation()).toLowerCase();
            boolean spawn = region.startsWith("spaw");
            if (plugin.getServer().getPluginManager().isPluginEnabled("Factions")) {
                FLocation ploc = new FLocation(e.getClickedBlock().getLocation());
                if (Board.getFactionAt((FLocation)ploc).isWarZone()) {
                    warzone = true;
                }
            } else {
                return;
            }
            if (!spawn && warzone && !region.contains("frozen_") && !region.contains("infernal_") && this.getItemCount((c = (Chest)e.getClickedBlock().getState()).getBlockInventory()) <= 0) {
                Player pl = e.getPlayer();
                final Location loc = b.getLocation();
                b.setType(Material.AIR);
                pl.playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 0.5f, 1.2f);
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)plugin, new Runnable(){

                    @Override
                    public void run() {
                        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition((int)Math.round(loc.getX()), (int)Math.round(loc.getY()), (int)Math.round(loc.getZ())), 54, false);
                        ((CraftServer)plugin.getServer()).getServer().getPlayerList().sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 24.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                    }
                });
            }
        }
    }

    @EventHandler
    public void removeGlitchedChestItemsInventoryClose(InventoryCloseEvent e) {
        if (!e.getPlayer().getInventory().contains(Material.CHEST)) {
            return;
        }
        for (ItemStack is : (ItemStack[])e.getPlayer().getInventory().getContents().clone()) {
            if (!this.isGlitchedChest(is)) continue;
            e.getPlayer().getInventory().remove(is);
        }
    }

    @EventHandler
    public void removeGLitchedChestItemsPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().getInventory().contains(Material.CHEST)) {
            return;
        }
        for (ItemStack is : (ItemStack[])e.getPlayer().getInventory().getContents().clone()) {
            if (!this.isGlitchedChest(is)) continue;
            e.getPlayer().getInventory().remove(is);
        }
    }

    private boolean isGlitchedChest(ItemStack i) {
        return i != null && i.getType() == Material.CHEST && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("???");
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent e) throws Exception {
        Chest c;
        Block b;
        if (!(e.getInventory().getHolder() instanceof Chest)) {
            return;
        }
        if (CosmicSpaceChestsAPI.isEmpty(e.getInventory()) && CosmicSpaceChestsPlugin.chest_locations.containsKey((Object)(b = (c = (Chest)e.getInventory().getHolder()).getBlock()).getLocation()) && b.getType() == Material.CHEST) {
            final Player pl = (Player)e.getPlayer();
            ItemRarity ir = CosmicSpaceChestsPlugin.chest_locations.getChestRarity(b.getLocation());
            if (!SpaceFlareListener.getSpaceFlares().contains((Object)b.getLocation())) {
                if (ir == ItemRarity.LEGENDARY && Math.random() <= 0.75) {
                    if (Math.random() < 0.4) {
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.GHAST);
                    }
                    if (Math.random() < 0.6) {
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                        this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                    }
                    this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                    this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                    this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                } else if (ir == ItemRarity.ULTIMATE) {
                    if (Math.random() <= 0.6) {
                        if (new Random().nextBoolean()) {
                            this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                        } else {
                            this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.GHAST);
                        }
                    }
                    this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                    this.spawnEnvoyMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                }
            } else {
                SpaceFlareListener.getSpaceFlares().remove((Object)b.getLocation());
            }
            plugin.getServer().getScheduler().runTaskLater((Plugin)plugin, new Runnable(){

                @Override
                public void run() {
                    final Location loc = b.getLocation();
                    CosmicSpaceChestsPlugin.chest_locations.removeChest(loc);
                    if (b.getType() == Material.CHEST && !b.getWorld().getName().contains("duel")) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests add ENVOY_LOOTER " + pl.getName()));
                        Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(pl, "ENVOY_LOOTER", new Object[]{loc}));
                    }
                    b.setType(Material.AIR);
                    pl.playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 0.5f, 1.2f);
                    plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)plugin, new Runnable(){

                        @Override
                        public void run() {
                            PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition((int)Math.round(loc.getX()), (int)Math.round(loc.getY()), (int)Math.round(loc.getZ())), 54, false);
                            ((CraftServer)plugin.getServer()).getServer().getPlayerList().sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 24.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                        }
                    });
                }
            }, 1L);
        }
    }

    @EventHandler
    public void onEntityBlockChange(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock && ((FallingBlock)e.getEntity()).getMaterial() == Material.CHEST) {
            Block b;
            int chestAmount;
            Location hit_loc = e.getEntity().getLocation();
            ItemRarity ir = null;
            boolean spaceFlare = e.getEntity().hasMetadata("spaceFlare");
            int n = chestAmount = e.getEntity().hasMetadata("spaceFlareChests") ? ((MetadataValue)e.getEntity().getMetadata("spaceFlareChests").get(0)).asInt() : 1;
            if (e.getEntity().hasMetadata("chestRarity")) {
                ir = ItemRarity.valueOf(((MetadataValue)e.getEntity().getMetadata("chestRarity").get(0)).asString());
            }
            e.getEntity().remove();
            e.setCancelled(true);
            final Block f_b = b = hit_loc.getBlock();
            b.setType(Material.CHEST);
            b.getWorld().playSound(b.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
            Chest chest = (Chest)b.getState();
            ir = ir == null ? CosmicSpaceChestsAPI.getRandomItemRarity() : ir;
            int is_offset = 3;
            CosmicSpaceChestsPlugin.chest_locations.putChest(b.getLocation(), ir);
            boolean battlefield = b.getWorld().getName().contains("_arena");
            if (!spaceFlare) {
                List<ItemStack> items = battlefield ? CosmicSpaceChestsAPI.getItemList(ir, is_offset) : CosmicSpaceChestsAPI.getEnvoyItems(ir, is_offset);
                for (ItemStack is : items) {
                    chest.getBlockInventory().addItem(new ItemStack[]{is});
                }
            } else {
                for (int x = 0; x < chestAmount; ++x) {
                    ItemStack i = CosmicSpaceChestsAPI.getDropPackageChest(ir);
                    chest.getBlockInventory().addItem(new ItemStack[]{i});
                }
                SpaceFlareListener.getSpaceFlares().add(chest.getLocation());
            }
            chest.update();
            Firework fw = (Firework)hit_loc.getWorld().spawnEntity(hit_loc, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            Color c = Color.WHITE;
            if (ir == ItemRarity.SIMPLE) {
                c = Color.WHITE;
            }
            if (ir == ItemRarity.UNIQUE) {
                c = Color.GREEN;
            }
            if (ir == ItemRarity.ELITE) {
                c = Color.AQUA;
            }
            if (ir == ItemRarity.ULTIMATE) {
                c = Color.YELLOW;
            }
            if (ir == ItemRarity.LEGENDARY || ir == ItemRarity.GOD) {
                c = Color.BLACK;
            }
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.STAR).trail(true).build();
            fwm.addEffect(effect);
            fwm.setPower(1);
            fw.setFireworkMeta(fwm);
            Location particle_loc = b.getLocation().subtract(0.0, 2.0, 0.0);
            final int block_id = particle_loc.getBlock().getTypeId();
            plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)plugin, new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition(f_b.getX(), f_b.getY(), f_b.getZ()), block_id, false);
                    ((CraftServer)plugin.getServer()).getServer().getPlayerList().sendPacketNearby((double)f_b.getX(), (double)f_b.getY(), (double)f_b.getZ(), 32.0, ((CraftWorld)f_b.getWorld()).getHandle().dimension, (Packet)particles);
                }
            });
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getInventory().contains(Material.CHEST)) {
            CosmicSpaceChestsAPI.convertDropPackages(e.getPlayer());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().contains(Material.CHEST)) {
            CosmicSpaceChestsAPI.convertDropPackages(e.getInventory());
        }
    }

    @EventHandler
    public void noFireballFire(ExplosionPrimeEvent e) {
        FLocation fl;
        if (e.getEntity() instanceof Fireball && Board.getFactionAt((FLocation)(fl = new FLocation(e.getEntity().getLocation()))).isWarZone()) {
            e.setFire(false);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("envoyMonster")) {
            e.getDrops().clear();
        }
    }

    private int getItemCount(Inventory inv) {
        int x = 0;
        for (ItemStack is : inv.getContents()) {
            if (is == null || is.getType() == Material.AIR) continue;
            ++x;
        }
        return x;
    }

    private void spawnEnvoyMonster(Location l, EntityType et) throws Exception {
        if (l.getWorld().getName().equals("world_duels") || l.getWorld().getName().equals("world_duels2")) {
            return;
        }
        PigZombie le = null;
        if (et == EntityType.PIG_ZOMBIE) {
            PigZombie b = (PigZombie)l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Demon");
            b.setCustomNameVisible(true);
            b.setBaby(true);
            b.setAngry(true);
            le = b;
        } else if (et == EntityType.IRON_GOLEM) {
            IronGolem b = (IronGolem)l.getWorld().spawnEntity(l, EntityType.IRON_GOLEM);
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setPlayerCreated(false);
            b.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Droid");
            b.setCustomNameVisible(true);
            le = (PigZombie) b;
        } else if (et == EntityType.SKELETON) {
            Skeleton b = (Skeleton)l.getWorld().spawnEntity(l, EntityType.SKELETON);
            b.setSkeletonType(Skeleton.SkeletonType.WITHER);
            b.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Undead");
            b.setCustomNameVisible(true);
            le = (PigZombie) b;
        } else if (et == EntityType.GHAST) {
            Ghast b = (Ghast)l.getWorld().spawnEntity(l, EntityType.GHAST);
            b.setMaxHealth(b.getMaxHealth() * 5.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Space " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "Phantom");
            b.setCustomNameVisible(true);
            le = (PigZombie) b;
        }
        le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, new Random().nextInt(3)));
        le.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, new Random().nextInt(2)));
        if (new Random().nextBoolean()) {
            le.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        } else {
            le.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
        }
        le.getEquipment().setHelmet(new ItemStack(new Random().nextBoolean() ? Material.IRON_HELMET : Material.DIAMOND_HELMET));
        le.getEquipment().setChestplate(new ItemStack(new Random().nextBoolean() ? Material.IRON_CHESTPLATE : Material.DIAMOND_CHESTPLATE));
        le.getEquipment().setLeggings(new ItemStack(new Random().nextBoolean() ? Material.IRON_LEGGINGS : Material.DIAMOND_LEGGINGS));
        le.getEquipment().setBoots(new ItemStack(new Random().nextBoolean() ? Material.IRON_BOOTS : Material.DIAMOND_BOOTS));
        le.setCanPickupItems(false);
        le.setMetadata("envoyMonster", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)true));
        de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), le.getLocation().add(0.0, 1.0, 0.0),0f,0f,0f, (float)0.15f, (int)25);
    }
}

