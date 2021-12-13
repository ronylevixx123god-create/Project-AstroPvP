package me.astropvp.astropearls;

import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import me.astropvp.astropearls.commands.AstroPearlsCommand;
import me.astropvp.astropearls.utils.ConfigFile;
import me.astropvp.astropearls.utils.Timer;
import net.astropvp.core.AstroCore;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Gate;
import org.bukkit.material.Stairs;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AstroPearls extends JavaPlugin implements Listener {
    private ArrayList<Player> playersToLastTeleport;
    public ConcurrentMap<Object, Object> pearlMap;
    private static AstroPearls instance;
    private ConfigFile configfile;
    private Timer timer;
    public static boolean PEARL_THROUGH_SLABS = false;
    public static boolean PEARL_THROUGH_STAIRS = false;
    public static boolean PEARL_THROUGH_WALL = false;
    private Map<UUID, Location> locationMap = Maps.newHashMap();
    double subtract_offset = 0.0D;

    public void onEnable() {
        instance = this;
        this.playersToLastTeleport = new ArrayList();
        this.saveDefaultConfig();
        this.timer = new Timer(this);
        this.timer.enable();
        Bukkit.getPluginManager().registerEvents(this, this);
        this.configfile = new ConfigFile();
        this.getCommand("axpearl").setExecutor(new AstroPearlsCommand());
        this.getLogger().info("The config file has been loaded!");
        this.getLogger().info("Pearling methods have been loaded!");
        this.getLogger().info("Commands have been loaded!");
    }

    public void onDisable() {
        instance = null;
    }

    public static boolean isSoulPearl(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_PEARL && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().equals(AstroCore.getInstance().getSoulPearl().getItemMeta().getLore());
    }

    public void handlePearlRefund(Player player, boolean soulPearl, String source) {
        if (soulPearl) {
            player.setItemInHand(AstroCore.getSoulPearl());
        }

    }

    boolean canThrowPearl(ItemStack item, Player pl) {
        if (pl.hasMetadata("lastEnderpearlThrow")) {
            long lastEnderpearlThrow = ((MetadataValue)pl.getMetadata("lastEnderpearlThrow").get(0)).asLong();
            long cd = 400L;
            long timeSinceThrow = System.currentTimeMillis() - lastEnderpearlThrow;
            if (timeSinceThrow < cd) {
                return false;
            }
        }

        return true;
    }

    public void onDmg(EntityDamageEvent event) {
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL && !EnderpearlCooldown.isEnderpearlCooldownOver(event.getPlayer())) {
            event.setUseItemInHand(Result.DENY);
            event.setCancelled(true);
        }

    }

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onPearlLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof EnderPearl && event.getEntity().getShooter() instanceof Player) {
            EnderPearl enderPearl = (EnderPearl)event.getEntity();
            Player player = (Player)enderPearl.getShooter();
            Block block = this.getBlockTali(player);
            Block block2 = this.getBlockTali2(player);
            int slot = player.getInventory().getHeldItemSlot();
            boolean isSoulPearl = isSoulPearl(player.getItemInHand());
            this.locationMap.put(player.getUniqueId(), player.getLocation());
            if (event.getEntity() instanceof EnderPearl) {
                if (getInstance().getConfig().getBoolean("PearlMethods.Block-Below-IsAir") && block.getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && block.getRelative(BlockFace.UP).getType().isSolid() && !block.getRelative(BlockFace.SELF).getType().isSolid() && block.getRelative(BlockFace.DOWN).getType().isSolid() && !block.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid() && !block2.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid()) {
                    enderPearl.setMetadata("tali", new FixedMetadataValue(this, true));
                }

                if (block.getRelative(BlockFace.UP).getType() == Material.FENCE_GATE && !((Gate)block.getRelative(BlockFace.UP).getState().getData()).isOpen() && block.getRelative(BlockFace.SELF).getType() == Material.FENCE_GATE && !enderPearl.hasMetadata("cpearl") && ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).isOpen() && block.getRelative(BlockFace.DOWN).getType().isSolid() && !block.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid() && !block2.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid()) {
                    enderPearl.setMetadata("tali", new FixedMetadataValue(this, true));
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Cobblewall") && this.isCobbleWall(block) && block.getRelative(BlockFace.UP).getType().isSolid()) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (getInstance().getConfig().getBoolean("PearlMethods.Fencegate") && block.getRelative(BlockFace.SELF).getType() == Material.FENCE_GATE && block.getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && block.getRelative(BlockFace.UP).getType().isSolid() && block.getRelative(BlockFace.DOWN).getType().isSolid() && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.NORTH && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.NORTH || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.SOUTH && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.NORTH || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.NORTH && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.SOUTH || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.SOUTH && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.SOUTH || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.WEST && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.WEST || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.EAST && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.WEST || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.WEST && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.EAST || ((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.EAST && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.EAST)) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (block.getRelative(BlockFace.SELF).getType() == Material.FENCE_GATE && block.getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && block.getRelative(BlockFace.UP).getType().isSolid() && block.getRelative(BlockFace.DOWN).getType().isSolid() && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.NORTH || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.NORTH) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.SOUTH || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.NORTH) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.NORTH || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.SOUTH) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.SOUTH || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.SOUTH) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.WEST || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.WEST) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.EAST || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.WEST) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.WEST || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.EAST) && (((Gate)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() != BlockFace.EAST || BlockFace.valueOf(this.getDirectionCompleteName(player)) != BlockFace.EAST)) {
                    this.handlePearlRefund(player, isSoulPearl, "a");
                    event.setCancelled(true);
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Chest")) {
                    if (this.isChest(block) && block.getRelative(BlockFace.UP).getType().isSolid() && block.getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && !block.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid()) {
                        enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                    }

                    if (this.isChest(block) && block.getRelative(BlockFace.UP).getType().isSolid() && block.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid()) {
                        this.handlePearlRefund(player, isSoulPearl, "b");
                        event.setCancelled(true);
                    }
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Chest") && (block.getRelative(BlockFace.DOWN).getType() == Material.CHEST || block.getRelative(BlockFace.DOWN).getType() == Material.TRAPPED_CHEST) && block.getRelative(BlockFace.UP).getType() != Material.CHEST && block.getRelative(BlockFace.UP).getType() != Material.TRAPPED_CHEST && block.getRelative(BlockFace.UP).getType().isSolid() && block.getRelative(BlockFace.UP).getType() != Material.FENCE_GATE) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Trapdoor") && block.getRelative(BlockFace.SELF).getType() == Material.TRAP_DOOR && block.getRelative(BlockFace.UP).getType().isSolid()) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Piston") && (block.getRelative(BlockFace.DOWN).getType() == Material.PISTON_BASE || block.getRelative(BlockFace.DOWN).getType() == Material.PISTON_STICKY_BASE) && block.getRelative(BlockFace.UP).getType().isSolid()) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Endportal") && this.isEndPortal(block) && block.getRelative(BlockFace.UP).getType().isSolid()) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Bed") && this.isBed(block) && block.getRelative(BlockFace.UP).getType().isSolid()) {
                    enderPearl.setMetadata("cpearl", new FixedMetadataValue(this, true));
                }

                if (block != null && (getInstance().getConfig().getBoolean("PearlMethods.Slabs") && this.isSlab(block) || getInstance().getConfig().getBoolean("PearlMethods.Stairs") && this.isStair(block))) {
                    enderPearl.setMetadata("tali", new FixedMetadataValue(this, true));
                    if (isSoulPearl) {
                        enderPearl.setMetadata("soulPearl", new FixedMetadataValue(this, slot));
                    }
                }

                if (block != null && block2.getLocation().getBlock().getRelative(BlockFace.valueOf(this.getDirectionCompleteName(player))).getType().isSolid() && getInstance().getConfig().getBoolean("PearlMethods.CritBlock") && (getInstance().getConfig().getBoolean("PearlMethods.Slabs") && this.isSlab(block) || getInstance().getConfig().getBoolean("PearlMethods.Stairs") && this.isStair(block))) {
                    this.handlePearlRefund(player, isSoulPearl, "c");
                    event.setCancelled(true);
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlMethods.Stairs-block-if-incorrect-facing") && getInstance().getConfig().getBoolean("PearlMethods.Stairs") && this.isStair(block) && (((Stairs)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.WEST && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.EAST || ((Stairs)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.EAST && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.WEST || ((Stairs)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.NORTH && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.SOUTH || ((Stairs)block.getRelative(BlockFace.SELF).getState().getData()).getFacing() == BlockFace.SOUTH && BlockFace.valueOf(this.getDirectionCompleteName(player)) == BlockFace.NORTH)) {
                    this.handlePearlRefund(player, isSoulPearl, "d");
                    event.setCancelled(true);
                }

                if (block != null && getInstance().getConfig().getBoolean("PearlRefund.Refund-if-block-in-front") && block.getType().isSolid() && block2.getType().isSolid() && !this.isStair(block) && !this.isSlab(block) && !this.isCobbleWall(block) && !this.isEndPortal(block) && !this.isBed(block)) {
                    this.handlePearlRefund(player, isSoulPearl, "e");
                    event.setCancelled(true);
                }
            }

        }
    }

    private boolean isEndPortal(Block block) {
        Material material = block.getType();
        return material != null && material != Material.AIR && material == Material.ENDER_PORTAL_FRAME;
    }

    private boolean isBed(Block block) {
        Material material = block.getType();
        return material != null && material != Material.AIR && (material == Material.BED_BLOCK || material == Material.BED);
    }

    private boolean isChest(Block block) {
        Material material = block.getType();
        return material != null && material != Material.AIR && (material == Material.ENDER_CHEST || material == Material.CHEST || material == Material.TRAPPED_CHEST);
    }

    private boolean isCobbleWall(Block block) {
        Material material = block.getType();
        return material != null && material != Material.AIR && material == Material.COBBLE_WALL;
    }

    private boolean isSlab(Block block) {
        Material material = block.getType();
        return material != null && material != Material.AIR && (material == Material.WOOD_STEP || material == Material.STEP);
    }

    private boolean isStair(Block block) {
        Material material = block.getType();
        return material != null && material != Material.AIR && (material == Material.ACACIA_STAIRS || material == Material.BIRCH_WOOD_STAIRS || material == Material.BRICK_STAIRS || material == Material.COBBLESTONE_STAIRS || material == Material.DARK_OAK_STAIRS || material == Material.JUNGLE_WOOD_STAIRS || material == Material.NETHER_BRICK_STAIRS || material == Material.QUARTZ_STAIRS || material == Material.SANDSTONE_STAIRS || material == Material.SPRUCE_WOOD_STAIRS || material == Material.SMOOTH_STAIRS || material == Material.WOOD_STAIRS);
    }

    @EventHandler
    public void onPearlLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof EnderPearl && event.getEntity().getShooter() instanceof Player) {
            EnderPearl enderPearl = (EnderPearl)event.getEntity();
            Location enderLocation = enderPearl.getLocation().clone();
            Player player = (Player)enderPearl.getShooter();
            if (enderPearl.hasMetadata("soulPearl")) {
                int slot = ((MetadataValue)enderPearl.getMetadata("soulPearl").get(0)).asInt();
                player.getInventory().setItem(slot, AstroCore.getSoulPearl());
            }

            if (!EnderpearlCooldown.isEnderpearlCooldownOver(player) && enderPearl.hasMetadata("cpearl") && this.getLocTali(enderLocation, player) != null) {
                if (this.getLocTali(enderLocation, player) != null) {
                    player.teleport(this.getLocTali(enderLocation, player), TeleportCause.UNKNOWN);
                }

            } else if (!EnderpearlCooldown.isEnderpearlCooldownOver(player) && enderPearl.hasMetadata("tali") && this.getLocTali(enderLocation, player) != null) {
                if (this.getLocTali(enderLocation, player) != null) {
                    player.teleport(this.getLocTali(enderLocation, player), TeleportCause.UNKNOWN);
                }

            } else {
                Block block = this.getBlockTali(enderLocation, player);
            }
        }
    }

    public static void tpNow(Player player, Location loc) {
        player.setMetadata("queuedTeleportPass", new FixedMetadataValue(Arkkit.get(), loc));
        player.setMetadata("bypassCombatTeleport", new FixedMetadataValue(Enchantments.getInstance(), loc));
        player.teleport(loc);
    }

    @EventHandler
    public void onClip(PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        Location to = e.getTo().clone();
        boolean isSoulPearl = false;

        for(int i = 0; i < 8; ++i) {
            if (p.getInventory().getItem(i) != null && isSoulPearl(p.getInventory().getItem(i))) {
                isSoulPearl = true;
            }
        }

        if (e.getCause() != null && e.getCause() == TeleportCause.ENDER_PEARL && p.getItemInHand().getType() == Material.ENDER_PEARL) {
            if (to.getBlock() != null && (to.getBlock().getRelative(BlockFace.SELF).getType() == Material.ACACIA_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.BIRCH_WOOD_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.BRICK_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.COBBLESTONE_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.DARK_OAK_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.JUNGLE_WOOD_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.NETHER_BRICK_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.QUARTZ_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.SANDSTONE_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.SMOOTH_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.SPRUCE_WOOD_STAIRS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.WOOD_STAIRS) && to.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                to.setY(to.getY() - this.subtract_offset);
                e.setTo(to);
            }

            if (to.getBlock() != null && (to.getBlock().getType() == Material.STEP || to.getBlock().getType() == Material.WOOD_STEP) && to.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                to.setY(to.getY() - this.subtract_offset);
                e.setTo(to);
            }

            if (to.getBlock().getRelative(BlockFace.UP).getType() == Material.FENCE_GATE && to.getBlock().getRelative(BlockFace.SELF).getType() == Material.FENCE_GATE && !((Gate)to.getBlock().getRelative(BlockFace.SELF).getState().getData()).isOpen() && to.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                this.handlePearlRefund(p, isSoulPearl, "f");
                e.setCancelled(true);
            }

            if (to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && to.getBlock().getRelative(BlockFace.SELF).getType() == Material.FENCE_GATE && !((Gate)to.getBlock().getRelative(BlockFace.SELF).getState().getData()).isOpen() && to.getBlock().getRelative(BlockFace.DOWN).getType() == Material.FENCE_GATE && !((Gate)to.getBlock().getRelative(BlockFace.DOWN).getState().getData()).isOpen()) {
                this.handlePearlRefund(p, isSoulPearl, "g");
                e.setCancelled(true);
            }

            if (!getInstance().getConfig().getBoolean("PearlMethods.chest") && (to.getBlock().getRelative(BlockFace.SELF).getType() == Material.CHEST || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.TRAPPED_CHEST) && to.getBlock().getRelative(BlockFace.UP).getType().isSolid() && to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE) {
                if (this.getConfig().getBoolean("PearlRefund.PearlRefund-Command")) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.getConfig().getString("PearlRefund.Command").replace("%player%", e.getPlayer().getName()));
                }

                if (this.getConfig().getBoolean("PearlRefund.PearlRefund-Message")) {
                    p.sendMessage(CC.translate(this.getConfig().getString("PearlRefund.Message")));
                }

                (new BukkitRunnable() {
                    public void run() {
                        if (AstroPearls.getInstance().getConfig().getBoolean("PearlRefund.PearlRefund-Remove-Timer")) {
                            AstroPearls.getInstance().getTimer().cooldown.remove(p.getUniqueId());
                        }

                    }
                }).runTaskLater(this, 2L);
                if (this.getConfig().getBoolean("Settings.PearlRefund")) {
                    p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.ENDER_PEARL, 1)});
                }

                e.setCancelled(true);
            }

            if (!getInstance().getConfig().getBoolean("PearlMethods.Endportal") && to.getBlock().getRelative(BlockFace.SELF).getType() == Material.ENDER_PORTAL_FRAME && to.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                if (this.getConfig().getBoolean("PearlRefund.PearlRefund-Command")) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.getConfig().getString("PearlRefund.Command").replace("%player%", e.getPlayer().getName()));
                }

                if (this.getConfig().getBoolean("PearlRefund.PearlRefund-Message")) {
                    p.sendMessage(CC.translate(this.getConfig().getString("PearlRefund.Message")));
                }

                (new BukkitRunnable() {
                    public void run() {
                        if (AstroPearls.getInstance().getConfig().getBoolean("PearlRefund.PearlRefund-Remove-Timer")) {
                            AstroPearls.getInstance().getTimer().cooldown.remove(p.getUniqueId());
                        }

                    }
                }).runTaskLater(this, 2L);
                if (this.getConfig().getBoolean("Settings.PearlRefund")) {
                    p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.ENDER_PEARL, 1)});
                }

                e.setCancelled(true);
            }

            if (!getInstance().getConfig().getBoolean("PearlMethods.Bed") && (to.getBlock().getRelative(BlockFace.SELF).getType() == Material.BED_BLOCK || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.BED) && to.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                this.handlePearlRefund(p, isSoulPearl, "h");
                e.setCancelled(true);
            }

            if (!getInstance().getConfig().getBoolean("PearlMethods.Cobblewall") && to.getBlock().getRelative(BlockFace.SELF).getType() == Material.COBBLE_WALL) {
                this.handlePearlRefund(p, isSoulPearl, "i");
                e.setCancelled(true);
            }

            if (to.getBlock().getRelative(BlockFace.SELF).getType() == Material.IRON_FENCE || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.NETHER_FENCE || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.ANVIL || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.THIN_GLASS || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.IRON_DOOR || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.WOOD_DOOR || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.WOODEN_DOOR || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.FENCE || to.getBlock().getRelative(BlockFace.SELF).getType() == Material.STAINED_GLASS_PANE) {
                this.handlePearlRefund(p, isSoulPearl, "j");
                e.setCancelled(true);
            }

            to.setX((double)to.getBlockX() + 0.5D);
            to.setZ((double)to.getBlockZ() + 0.5D);
            e.setTo(to);
        }

        if (!this.getConfig().getBoolean("Settings.Anti-Glitch")) {
            if (to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && to.getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
                e.setTo(to.subtract(0.0D, this.subtract_offset, 0.0D));
            }

            if (to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && to.getBlock().getRelative(BlockFace.UP).getType().isSolid() && to.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                e.setTo(to.subtract(0.0D, this.subtract_offset, 0.0D));
                e.setTo(to);
            }

            if (!to.getBlock().getRelative(BlockFace.SELF).getType().isSolid() && to.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR || to.getBlock().getRelative(BlockFace.UP).getType() == Material.GLASS || to.getBlock().getRelative(BlockFace.UP).getType() == Material.STAINED_GLASS) {
                e.setTo(to.subtract(0.0D, this.subtract_offset, 0.0D));
                e.setTo(to);
            }

            if (to.getBlock().getRelative(BlockFace.SELF).getType().isSolid() && to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE) {
                e.setTo(to.add(0.0D, 0.0D, 0.0D));
                e.setTo(to);
            }

            if (to.getBlock().getRelative(BlockFace.UP).getType().isTransparent() && to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE) {
                e.setTo(to.add(0.0D, 0.0D, 0.0D));
                e.setTo(to);
            }

            if (to.getBlock().getRelative(BlockFace.UP).getType().isTransparent() && to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE) {
                e.setTo(to.add(0.0D, 0.0D, 0.0D));
                e.setTo(to);
            }
        } else if (this.getConfig().getBoolean("Settings.Anti-Glitch")) {
            Block block2 = this.getBlockTali2(e.getPlayer());
            if (to.getBlock().getRelative(BlockFace.UP).getType() != Material.FENCE_GATE && to.getBlock().getRelative(BlockFace.UP).getType().isSolid() && !to.getBlock().getRelative(BlockFace.SELF).getType().isSolid() && block2.getRelative(BlockFace.UP).getType() == Material.AIR && to.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                this.handlePearlRefund(p, isSoulPearl, "k");
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (this.playersToLastTeleport.contains(e.getPlayer())) {
            this.playersToLastTeleport.remove(e.getPlayer());
        }

    }

    private Location getLocTali(Location location, Player player) {
        String direction = this.getDirectionName(player);
        byte var6 = -1;
        switch(direction.hashCode()) {
            case 69:
                if (direction.equals("E")) {
                    var6 = 6;
                }
                break;
            case 78:
                if (direction.equals("N")) {
                    var6 = 0;
                }
                break;
            case 83:
                if (direction.equals("S")) {
                    var6 = 4;
                }
                break;
            case 87:
                if (direction.equals("W")) {
                    var6 = 2;
                }
                break;
            case 2487:
                if (direction.equals("NE")) {
                    var6 = 7;
                }
                break;
            case 2505:
                if (direction.equals("NW")) {
                    var6 = 1;
                }
                break;
            case 2642:
                if (direction.equals("SE")) {
                    var6 = 5;
                }
                break;
            case 2660:
                if (direction.equals("SW")) {
                    var6 = 3;
                }
        }

        Location newLoc;
        switch(var6) {
            case 0:
            case 1:
                newLoc = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ() - 1.5D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 2:
            case 3:
                newLoc = new Location(player.getWorld(), location.getX() - 1.5D, location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 4:
            case 5:
                newLoc = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ() + 1.5D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 6:
            case 7:
                newLoc = new Location(player.getWorld(), location.getX() + 1.5D, location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            default:
                return null;
        }

        return newLoc == null ? null : newLoc;
    }

    private Block getBlockTali(Location location, Player player) {
        String direction = this.getDirectionName(location);
        byte var6 = -1;
        switch(direction.hashCode()) {
            case 69:
                if (direction.equals("E")) {
                    var6 = 6;
                }
                break;
            case 78:
                if (direction.equals("N")) {
                    var6 = 0;
                }
                break;
            case 83:
                if (direction.equals("S")) {
                    var6 = 4;
                }
                break;
            case 87:
                if (direction.equals("W")) {
                    var6 = 2;
                }
                break;
            case 2487:
                if (direction.equals("NE")) {
                    var6 = 7;
                }
                break;
            case 2505:
                if (direction.equals("NW")) {
                    var6 = 1;
                }
                break;
            case 2642:
                if (direction.equals("SE")) {
                    var6 = 5;
                }
                break;
            case 2660:
                if (direction.equals("SW")) {
                    var6 = 3;
                }
        }

        Location newLoc;
        switch(var6) {
            case 0:
            case 1:
                newLoc = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ() - 0.7D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 2:
            case 3:
                newLoc = new Location(player.getWorld(), location.getX() - 0.7D, location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 4:
            case 5:
                newLoc = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ() + 0.7D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 6:
            case 7:
                newLoc = new Location(player.getWorld(), location.getX() + 0.7D, location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            default:
                return null;
        }

        return newLoc == null ? null : newLoc.getBlock();
    }

    private Block getBlockTali2(Player player) {
        String direction = this.getDirectionName(player);
        byte var5 = -1;
        switch(direction.hashCode()) {
            case 69:
                if (direction.equals("E")) {
                    var5 = 6;
                }
                break;
            case 78:
                if (direction.equals("N")) {
                    var5 = 0;
                }
                break;
            case 83:
                if (direction.equals("S")) {
                    var5 = 4;
                }
                break;
            case 87:
                if (direction.equals("W")) {
                    var5 = 2;
                }
                break;
            case 2487:
                if (direction.equals("NE")) {
                    var5 = 7;
                }
                break;
            case 2505:
                if (direction.equals("NW")) {
                    var5 = 1;
                }
                break;
            case 2642:
                if (direction.equals("SE")) {
                    var5 = 5;
                }
                break;
            case 2660:
                if (direction.equals("SW")) {
                    var5 = 3;
                }
        }

        Location newLoc;
        switch(var5) {
            case 0:
            case 1:
                newLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.0D, player.getLocation().getZ() - 0.7D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 2:
            case 3:
                newLoc = new Location(player.getWorld(), player.getLocation().getX() - 0.7D, player.getLocation().getY() + 1.0D, player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 4:
            case 5:
                newLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.0D, player.getLocation().getZ() + 0.7D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 6:
            case 7:
                newLoc = new Location(player.getWorld(), player.getLocation().getX() + 0.7D, player.getLocation().getY() + 1.0D, player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            default:
                return null;
        }

        return newLoc == null ? null : newLoc.getBlock();
    }

    private Block getBlockTali(Player player) {
        String direction = this.getDirectionName(player);
        byte var5 = -1;
        switch(direction.hashCode()) {
            case 69:
                if (direction.equals("E")) {
                    var5 = 6;
                }
                break;
            case 78:
                if (direction.equals("N")) {
                    var5 = 0;
                }
                break;
            case 83:
                if (direction.equals("S")) {
                    var5 = 4;
                }
                break;
            case 87:
                if (direction.equals("W")) {
                    var5 = 2;
                }
                break;
            case 2487:
                if (direction.equals("NE")) {
                    var5 = 7;
                }
                break;
            case 2505:
                if (direction.equals("NW")) {
                    var5 = 1;
                }
                break;
            case 2642:
                if (direction.equals("SE")) {
                    var5 = 5;
                }
                break;
            case 2660:
                if (direction.equals("SW")) {
                    var5 = 3;
                }
        }

        Location newLoc;
        switch(var5) {
            case 0:
            case 1:
                newLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ() - 0.7D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 2:
            case 3:
                newLoc = new Location(player.getWorld(), player.getLocation().getX() - 0.7D, player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 4:
            case 5:
                newLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ() + 0.7D, player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            case 6:
            case 7:
                newLoc = new Location(player.getWorld(), player.getLocation().getX() + 0.7D, player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                break;
            default:
                return null;
        }

        return newLoc == null ? null : newLoc.getBlock();
    }

    private String getDirectionName(Location location) {
        double rotation = (double)((location.getYaw() - 90.0F) % 360.0F);
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }

        if (0.0D <= rotation && rotation < 22.5D) {
            return "W";
        } else if (22.5D <= rotation && rotation < 67.5D) {
            return "NW";
        } else if (67.5D <= rotation && rotation < 112.5D) {
            return "N";
        } else if (112.5D <= rotation && rotation < 157.5D) {
            return "NE";
        } else if (157.5D <= rotation && rotation < 202.5D) {
            return "E";
        } else if (202.5D <= rotation && rotation < 247.5D) {
            return "SE";
        } else if (247.5D <= rotation && rotation < 292.5D) {
            return "S";
        } else if (292.5D <= rotation && rotation < 337.5D) {
            return "SW";
        } else {
            return 337.5D <= rotation && rotation < 360.0D ? "W" : null;
        }
    }

    private String getDirectionName(Player player) {
        double rotation = (double)((player.getLocation().getYaw() - 90.0F) % 360.0F);
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }

        if (0.0D <= rotation && rotation < 22.5D) {
            return "W";
        } else if (22.5D <= rotation && rotation < 67.5D) {
            return "NW";
        } else if (67.5D <= rotation && rotation < 112.5D) {
            return "N";
        } else if (112.5D <= rotation && rotation < 157.5D) {
            return "NE";
        } else if (157.5D <= rotation && rotation < 202.5D) {
            return "E";
        } else if (202.5D <= rotation && rotation < 247.5D) {
            return "SE";
        } else if (247.5D <= rotation && rotation < 292.5D) {
            return "S";
        } else if (292.5D <= rotation && rotation < 337.5D) {
            return "SW";
        } else {
            return 337.5D <= rotation && rotation < 360.0D ? "W" : null;
        }
    }

    private String getDirectionCompleteName(Block block) {
        double rotation = (double)((block.getLocation().getYaw() - 90.0F) % 360.0F);
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }

        if (0.0D <= rotation && rotation < 22.5D) {
            return "WEST";
        } else if (22.5D <= rotation && rotation < 67.5D) {
            return "NORTH_WEST";
        } else if (67.5D <= rotation && rotation < 112.5D) {
            return "NORTH";
        } else if (112.5D <= rotation && rotation < 157.5D) {
            return "NORTH_EAST";
        } else if (157.5D <= rotation && rotation < 202.5D) {
            return "EAST";
        } else if (202.5D <= rotation && rotation < 247.5D) {
            return "SOUTH_EAST";
        } else if (247.5D <= rotation && rotation < 292.5D) {
            return "SOUTH";
        } else if (292.5D <= rotation && rotation < 337.5D) {
            return "SOUTH_WEST";
        } else {
            return 337.5D <= rotation && rotation < 360.0D ? "WEST" : null;
        }
    }

    private String getBackDirectionCompleteName(Player player) {
        double rotation = (double)((player.getLocation().getYaw() - 90.0F) % 360.0F);
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }

        if (0.0D <= rotation && rotation < 22.5D) {
            return "EAST";
        } else if (22.5D <= rotation && rotation < 67.5D) {
            return "SOUTH_WEST";
        } else if (67.5D <= rotation && rotation < 112.5D) {
            return "SOUTH";
        } else if (112.5D <= rotation && rotation < 157.5D) {
            return "SOUTH_EAST";
        } else if (157.5D <= rotation && rotation < 202.5D) {
            return "WEST";
        } else if (202.5D <= rotation && rotation < 247.5D) {
            return "NORTH_EAST";
        } else if (247.5D <= rotation && rotation < 292.5D) {
            return "NORTH";
        } else if (292.5D <= rotation && rotation < 337.5D) {
            return "NORTH_WEST";
        } else {
            return 337.5D <= rotation && rotation < 360.0D ? "EAST" : null;
        }
    }

    private String getDirectionCompleteName(Player player) {
        double rotation = (double)((player.getLocation().getYaw() - 90.0F) % 360.0F);
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }

        if (0.0D <= rotation && rotation < 22.5D) {
            return "WEST";
        } else if (22.5D <= rotation && rotation < 67.5D) {
            return "NORTH_WEST";
        } else if (67.5D <= rotation && rotation < 112.5D) {
            return "NORTH";
        } else if (112.5D <= rotation && rotation < 157.5D) {
            return "NORTH_EAST";
        } else if (157.5D <= rotation && rotation < 202.5D) {
            return "EAST";
        } else if (202.5D <= rotation && rotation < 247.5D) {
            return "SOUTH_EAST";
        } else if (247.5D <= rotation && rotation < 292.5D) {
            return "SOUTH";
        } else if (292.5D <= rotation && rotation < 337.5D) {
            return "SOUTH_WEST";
        } else {
            return 337.5D <= rotation && rotation < 360.0D ? "WEST" : null;
        }
    }

    public static AstroPearls getInstance() {
        return instance;
    }

    public ConfigFile getconfigfile() {
        return this.configfile;
    }

    public Timer getTimer() {
        return this.timer;
    }
}
