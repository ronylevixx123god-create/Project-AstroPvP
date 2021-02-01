/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.FactionPermission
 *  com.rit.sucy.EnchantmentAPI
 *  de.dustplanet.util.SilkUtil
 *  net.coreprotect.CoreProtect
 *  net.coreprotect.CoreProtectAPI
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.arkhamnetwork.Arkkit.utils.WorldGuardUtils
 *  org.arkhamnetwork.enchantments.mastery.MasteryRotAndDecay
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.craftbukkit.v1_7_R4.block.CraftBlock
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreeper
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockDispenseEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.compilex.CosmicCustomCreepers.listeners;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.FactionPermission;
import com.rit.sucy.EnchantmentAPI;
import de.dustplanet.util.SilkUtil;
import lombok.SneakyThrows;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.arkhamnetwork.CosmicCustomTNT.CosmicCustomTNT;
import org.arkhamnetwork.enchantments.mastery.MasteryRotAndDecay;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreeper;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.*;

public class CreeperListener
implements Listener {
    private boolean coreProtectEnabled = false;
    private static SilkUtil su = null;
    private final List<Material> nonSolidBlocks;

    public CreeperListener() {
        su = SilkUtil.hookIntoSilkSpanwers();
        this.coreProtectEnabled = new File("plugins/CoreProtect.jar").exists();
        this.nonSolidBlocks = new ArrayList<Material>(Arrays.asList(Material.AIR, Material.CHEST));
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerUseCreeperEgg(PlayerInteractEvent e) {
        CreeperTypes ct;
        if (e.isCancelled() && e.useItemInHand() != Event.Result.ALLOW) {
            return;
        }
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (ct = this.getCCType(e.getItem())) != null) {
            this.processCustomCreeperPlaced(e, ct);
        }
    }

    public Creeper processCustomCreeperPlaced(PlayerInteractEvent e, CreeperTypes ct) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Location spawnLoc;
            Block b;
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            Player p = e.getPlayer();
            Block chestBlock = null;
            try {
                Material m2;
                try {
                    b = e.getClickedBlock().getY() < 254 ? p.getTargetBlock((HashSet<Byte>) null, 4) : e.getClickedBlock();
                }
                catch (Exception err) {
                    b = e.getClickedBlock();
                }
                if (e.getClickedBlock().getY() >= 255 && e.getBlockFace() != BlockFace.UP) {
                    return null;
                }
                if (WorldGuardUtils.isPvPDisabled(b.getLocation())) {
                    return null;
                }
                Material m1 = b.getType();
                Material material = m2 = b.getY() >= 254 ? b.getType() : b.getLocation().add(0.0, 1.0, 0.0).getBlock().getType();
                if (b.getY() < 254 && m1 != Material.AIR && m1 != Material.CHEST && m1 != Material.TRAPPED_CHEST && m1 != Material.BEDROCK && m2 != Material.AIR && m2 != Material.CHEST && m2 != Material.TRAPPED_CHEST && m2 != Material.BEDROCK) {
                    Bukkit.getLogger().info("[CosmicCustomCreepers] Blocking cegg due to non air/chest block above spawn location. m=" + m1 + ", m+1=" + m2);
                    return null;
                }
                if (m1 == Material.CHEST || m2 == Material.CHEST || m1 == Material.TRAPPED_CHEST || m2 == Material.TRAPPED_CHEST) {
                    chestBlock = m1 == Material.CHEST || m1 == Material.TRAPPED_CHEST ? e.getClickedBlock() : e.getClickedBlock().getLocation().add(0.0, 1.0, 0.0).getBlock();
                }
                Location location = spawnLoc = b.getLocation().getY() >= 255.0 ? b.getLocation() : b.getLocation().add(0.0, 1.0, 0.0);
                if (spawnLoc.getBlock().isLiquid() || spawnLoc.clone().add(0.0, 1.0, 0.0).getBlock().isLiquid()) {
                    Bukkit.getLogger().info("[CosmicCustomCreepers] Blocking custom cegg due to LIQUID at y or y+1.");
                    return null;
                }
                if (chestBlock == null && spawnLoc.getBlock().getType() == Material.MOB_SPAWNER) {
                    Bukkit.getLogger().info("[CosmicCustomCreepers] Blocking custom cegg due to MOB_SPAWNER at y+1.");
                    return null;
                }
                if (ct == CreeperTypes.Huge && spawnLoc.getWorld().getName().equals("world_end")) {
                    p.sendMessage(ChatColor.RED + "(!) " + ChatColor.RED + "You cannot spawn Gigantic Creepers in the end!");
                    return null;
                }
                FLocation fLocation = new FLocation(spawnLoc);
                Faction f = Board.getFactionAt(fLocation);
                if (f.isNormal() && !f.hasPermission(FPlayers.i.get(p), fLocation, FactionPermission.CREEPER, null)) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have permission to do that in this territory! A Coleader+ of " + f.getTag() + " needs to grant you /f perm to perform this action.");
                    return null;
                }
                ItemStack egg = e.getItem();
                if (egg.getAmount() > 1) {
                    egg.setAmount(egg.getAmount() - 1);
                    p.setItemInHand(egg);
                } else {
                    p.setItemInHand(null);
                }
                spawnLoc.add(0.5, 0.0, 0.5);
            }
            catch (Exception err) {
                err.printStackTrace();
                return null;
            }
            Location toSpawn = chestBlock != null ? chestBlock.getLocation() : spawnLoc;
            Bukkit.getLogger().info("[CosmicCustomCreeper] (" + ct.name() + ") spawnLocation: " + toSpawn);
            Creeper c = (Creeper)b.getWorld().spawnEntity(toSpawn, EntityType.CREEPER);
            if (chestBlock != null) {
                c.setMetadata("noSuffocation", new FixedMetadataValue(CosmicCustomTNT.get(), true));
            }
            c.setCustomName(ct.getMobName());
            c.setCustomNameVisible(true);
            c.setMetadata("customCreeperType", new FixedMetadataValue(CosmicCustomTNT.get(), ct.name()));
            c.setMetadata("creeperOwner", new FixedMetadataValue(CosmicCustomTNT.get(), e.getPlayer().getName()));
            if (ct == CreeperTypes.Stun || ct == CreeperTypes.Vile) {
                ((CraftCreeper)c).getHandle().maxFuseTicks = 15;
                ((CraftCreeper)c).getHandle().co();
            }
            this.logPlacement(toSpawn.getBlock(), p.getName(), (byte)ct.ordinal());
            return c;
        }
        return null;
    }

    private void logPlacement(Block block, String user, byte data) {
        if (!this.coreProtectEnabled) {
            return;
        }
        try {
            CoreProtectAPI api = ((CoreProtect)Bukkit.getPluginManager().getPlugin("CoreProtect")).getAPI();
            if (api != null) {
                api.logPlacement(user, block.getLocation(), Material.MONSTER_EGG.getId(), data);
                Bukkit.getLogger().info("[CustomCreeper] tracking placement location for " + user + "!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onCreeperDispense(BlockDispenseEvent e) {
        CreeperTypes egg_type;
        if (e.getItem().getType() == Material.MONSTER_EGG && (egg_type = this.getCCType(e.getItem())) == null) {
            return;
        }
    }

    @SneakyThrows
    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onCustomCreeperExplode(EntityExplodeEvent e) throws NoSuchFieldException, IllegalAccessException {
        if (e.getEntity() == null || !e.getEntity().hasMetadata("customCreeperType")) {
            return;
        }
        Creeper c = (Creeper)e.getEntity();
        if (e.getEntity().hasMetadata("customCreeperType")) {
            CreeperTypes egg_type = CreeperTypes.valueOf(c.getMetadata("customCreeperType").get(0).asString());
            Bukkit.getLogger().info("[CosmicCustomCreepers] EntityExplodeEvent for CustomCreeperEgg: " + egg_type.name() + " entity: " + c);
            if (egg_type == CreeperTypes.Huge) {
                Location loc = c.getLocation();
                this.spawnFastExplosion(loc.clone().add(0.0, 3.0, 0.0));
                this.spawnFastExplosion(loc.clone().add(0.0, -3.0, 0.0));
                if (new Random().nextBoolean()) {
                    this.spawnFastExplosion(loc.clone().add(3.0, 0.0, 0.0));
                    this.spawnFastExplosion(loc.clone().add(0.0, 0.0, 3.0));
                } else {
                    this.spawnFastExplosion(loc.clone().add(-3.0, 0.0, 0.0));
                    this.spawnFastExplosion(loc.clone().add(0.0, 0.0, -3.0));
                }
            } else if (egg_type == CreeperTypes.Lucky) {
                for (Block b : e.blockList()) {
                    if (b.getType() != Material.MOB_SPAWNER) continue;
                    double rand = Math.random();
                    Bukkit.getLogger().info("[CosmicCustomCreeper] Lucky Creeper processing spawner at " + b.getX() + "x ," + b.getY() + "y ," + b.getZ() + "z: " + rand + " <= 0.60?");
                    if (!(rand <= 0.6)) continue;
                    e.blockList().remove(b);
                    ItemStack is = SilkUtil.hookIntoSilkSpanwers().newSpawnerItem(String.valueOf(b.getTypeId()), CC.translate("&a&l") + b.getType().name() + "&f Spawner", 1, false);
                    CreatureSpawner cs = (CreatureSpawner) b.getState();
                    int spawnerCount = cs.getSpawnerCount();
                    if (spawnerCount == 4 && !Board.getFactionAt(new FLocation(cs.getLocation())).isNormal()) {
                        cs.setSpawnerCount((short)1);
                        spawnerCount = 1;
                    }
                    b.setType(Material.AIR);
                    if (b.getType() != Material.AIR) continue;
                    while (spawnerCount-- > 0) {
                        b.getWorld().dropItem(b.getLocation(), is);
                    }
                    de.inventivegames.particle.ParticleEffect.INSTANT_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), 0f,0.5f,0f,0.6f,20);
                }
            } else if (egg_type == CreeperTypes.Tactical) {
                for (Block b : e.blockList()) {
                    if (b.getType() != Material.MOB_SPAWNER) continue;
                    double rand = Math.random();
                    Bukkit.getLogger().info("[CosmicCustomCreeper] Tactical Creeper processing spawner at " + b.getX() + "x ," + b.getY() + "y ," + b.getZ() + "z: " + rand + " <= 0.30?");
                    if (!(rand <= 0.3)) continue;
                    e.blockList().remove(b);
                    ItemStack is = SilkUtil.hookIntoSilkSpanwers().newSpawnerItem(String.valueOf(b.getTypeId()), CC.translate("&a&l") + b.getType().name() + "&f Spawner", 1, false);
                    CreatureSpawner cs = (CreatureSpawner) b.getState();
                    int spawnerCount = cs.getSpawnerCount();
                    if (spawnerCount == 4 && !Board.getFactionAt(new FLocation(cs.getLocation())).isNormal()) {
                        cs.setSpawnerCount((short)1);
                        spawnerCount = 1;
                    }
                    b.setType(Material.AIR);
                    if (b.getType() != Material.AIR) continue;
                    while (spawnerCount-- > 0) {
                        b.getWorld().dropItem(b.getLocation(), is);
                    }
                    de.inventivegames.particle.ParticleEffect.INSTANT_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), 0f,0.5f,0f,0.6f,20);
                }
                e.setCancelled(true);
                e.setYield(0.0f);
                e.blockList().clear();
            } else if (egg_type == CreeperTypes.Raid) {
                for (Block b : e.blockList()) {
                    if (b.getType() != Material.MOB_SPAWNER) continue;
                    Bukkit.getLogger().info("[CosmicCustomCreeper] Raid Creeper processing spawner at " + b.getX() + "x ," + b.getY() + "y ," + b.getZ() + "z: ");
                    e.blockList().remove(b);
                    ItemStack is = SilkUtil.hookIntoSilkSpanwers().newSpawnerItem(String.valueOf(b.getTypeId()), CC.translate("&a&l") + b.getType().name() + "&f Spawner", 1, false);
                    CreatureSpawner cs = (CreatureSpawner) b.getState();
                    int spawnerCount = cs.getSpawnerCount();
                    if (spawnerCount == 4 && !Board.getFactionAt(new FLocation(cs.getLocation())).isNormal()) {
                        cs.setSpawnerCount((short)1);
                        spawnerCount = 1;
                    }
                    b.setType(Material.AIR);
                    if (b.getType() != Material.AIR) continue;
                    while (spawnerCount-- > 0) {
                        b.getWorld().dropItem(b.getLocation(), is);
                    }
                    de.inventivegames.particle.ParticleEffect.INSTANT_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), 0f,0.5f,0f,0.6f,20);
                }
                e.setCancelled(true);
                e.setYield(0.0f);
                e.blockList().clear();
            } else if (egg_type == CreeperTypes.Stun) {
                int stunTicks = 60;
                for (Entity ent : c.getNearbyEntities(5.0, 5.0, 5.0)) {
                    Player p;
                    if (!(ent instanceof Player) || (p = (Player)ent).getGameMode() != GameMode.SURVIVAL || p.hasMetadata("stunCreeperCooldown") && p.getMetadata("stunCreeperCooldown").get(0).asLong() > System.currentTimeMillis()) continue;
                    p.damage(1.0);
                    p.setFlying(false);
                    p.setWalkSpeed(0.0f);
                    p.removePotionEffect(PotionEffectType.JUMP);
                    p.removePotionEffect(PotionEffectType.SLOW);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, stunTicks, 128));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, stunTicks, 128));
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* STUN CREEPER [" + ChatColor.GRAY + "3s" + ChatColor.RED + ChatColor.BOLD + "] *");
                    p.setMetadata("stunCreeperCooldown", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis() + 30000L));
                    de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), p.getEyeLocation(), 0f,0.5f,0f,1.2f,30);

                    Bukkit.getScheduler().runTaskLater(CosmicCustomTNT.get(), new Runnable(){

                        @Override
                        public void run() {
                            p.setWalkSpeed(0.2f);
                        }
                    }, stunTicks);
                }
                de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(),e.getEntity().getLocation(), 0f,1f,0f,1.2f,30);

                e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                e.getEntity().remove();
                e.setCancelled(true);
                e.setYield(0.0f);
                e.blockList().clear();
            } else if (egg_type == CreeperTypes.Vile) {
                if (e.getEntity().hasMetadata("creeperOwner")) {
                    Player p = Bukkit.getPlayer(e.getEntity().getMetadata("creeperOwner").get(0).asString());
                    if (p != null) {
                        int duration = 5;
                        int radius = 4;
                        ((MasteryRotAndDecay)EnchantmentAPI.getEnchantment("Rot and Decay")).applyRotAndDecayEffect(p, e.getEntity().getLocation().getBlock(), radius, duration, null);
                        de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(),e.getEntity().getLocation(), 0f,1f,0f,1.2f,30);
                        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                        e.getEntity().remove();
                        e.setCancelled(true);
                        e.setYield(0.0f);
                        e.blockList().clear();
                    } else {
                        Bukkit.getLogger().info("[CosmicCustomCreeper] Null player object found for VILE creeper at: " + e.getEntity().getLocation() + "!");
                    }
                } else {
                    Bukkit.getLogger().info("[CosmicCustomCreeper] No creeperOwner metadata found for VILE creeper at: " + e.getEntity().getLocation() + "!");
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onPlayerDamagedByCustomCreeper(EntityDamageByEntityEvent e) {
        CreeperTypes egg_type;
        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || !(e.getDamager() instanceof Creeper)) {
            return;
        }
        if (e.getDamager().hasMetadata("customCreeperType") && ((egg_type = CreeperTypes.valueOf(e.getDamager().getMetadata("customCreeperType").get(0).asString())) == CreeperTypes.Stun || egg_type == CreeperTypes.Vile)) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    private CreeperTypes getCCType(ItemStack creeper_egg) {
        if (!(creeper_egg != null && creeper_egg.hasItemMeta() && creeper_egg.getItemMeta().hasDisplayName() && creeper_egg.getItemMeta().hasLore())) {
            return null;
        }
        CreeperTypes tnt_type = null;
        String name = creeper_egg.getItemMeta().getDisplayName();
        for (CreeperTypes tt : CreeperTypes.values()) {
            if (!name.equals(tt.getItemName())) continue;
            tnt_type = tt;
            break;
        }
        return tnt_type;
    }

    private void spawnFastExplosion(Location l) {
        if (!this.nonSolidBlocks.contains(l.getBlock().getType())) {
            return;
        }
        TNTPrimed tnt = (TNTPrimed)l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
        tnt.setMetadata("ObbyEntity", new FixedMetadataValue(CosmicCustomTNT.get(), true));
        tnt.setMetadata("fromCreeper", new FixedMetadataValue(CosmicCustomTNT.get(), true));
        tnt.setFuseTicks(0);
    }
}

