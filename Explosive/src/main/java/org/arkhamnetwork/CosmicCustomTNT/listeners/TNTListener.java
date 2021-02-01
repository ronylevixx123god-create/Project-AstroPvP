package org.arkhamnetwork.CosmicCustomTNT.listeners;

import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import de.dustplanet.util.SilkUtil;
import lombok.SneakyThrows;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import org.arkhamnetwork.CosmicCustomTNT.CosmicCustomTNT;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftTNTPrimed;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TNTListener
implements Listener {
    public static HashMap<Location, TNTTypes> tnt_tracker = new HashMap();
    private static SilkUtil su = null;
    private final List<Material> nonSolidBlocks;
    private final int silentTNTSoundRange = 16;
    private boolean coreProtectEnabled = false;

    public TNTListener() {
        su = SilkUtil.hookIntoSilkSpanwers();
        this.coreProtectEnabled = new File("plugins/CoreProtect.jar").exists();
        this.nonSolidBlocks = new ArrayList<Material>(Arrays.asList(Material.AIR, Material.SAND, Material.WATER, Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER));
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onTNTPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == Material.TNT) {
            TNTTypes tnt_type = this.getTNTType(e.getItemInHand());
            if (tnt_type == null) {
                return;
            }
            tnt_tracker.put(e.getBlockPlaced().getLocation(), tnt_type);
        }
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent e) {
        if (e.getItem().getType() == Material.TNT) {
            TNTTypes tnt_type = this.getTNTType(e.getItem());
            if (tnt_type == null) {
                return;
            }
            tnt_tracker.put(e.getBlock().getLocation(), tnt_type);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onTNTBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.TNT && tnt_tracker.containsKey(e.getBlock().getLocation())) {
            e.setCancelled(true);
            Location loc = e.getBlock().getLocation();
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().dropItemNaturally(loc, TNTTypes.getTNTItem(tnt_tracker.get(loc)));
            tnt_tracker.remove(loc);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onExplosivePrime(ExplosionPrimeEvent e) {
        if (e.getEntity() != null && e.getEntity() instanceof TNTPrimed) {
            EntityTNTPrimed tntPrimed = ((CraftTNTPrimed) e.getEntity()).getHandle();
            ItemStack tntItem = new ItemStack(Material.TNT);
            TNTTypes tntType = this.getTNTType(tntItem);
            if (tntType != null) {
                e.getEntity().setMetadata("custom_tnt", new FixedMetadataValue(CosmicCustomTNT.get(), tntType.name()));
                return;
            }
            Location loc = tntPrimed.getSource().getBukkitEntity().getLocation();
            TNTTypes tnt_type = tnt_tracker.remove(loc);
            if (tnt_type != null) {
                e.getEntity().setMetadata("custom_tnt", new FixedMetadataValue(CosmicCustomTNT.get(), tnt_type.name()));
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onTNTExplodeHex(EntityExplodeEvent e) {
        if (e.getEntity() != null && e.getEntity().hasMetadata("fastExplosion")) {
            return;
        }
        int c = 0;
        for (final Block b : e.blockList()) {
            if (b.getType() == Material.AIR || !b.hasMetadata("hexBlocks") || System.currentTimeMillis() - b.getMetadata("hexBlocks").get(0).asLong() > 30000L) continue;
            if (c++ > 3) {
                return;
            }
            Bukkit.getScheduler().runTaskLater(CosmicCustomTNT.get(), new Runnable(){

                @Override
                public void run() {
                    TNTListener.this.spawnFastExplosion(b.getLocation());
                }
            }, 2 + new Random().nextInt(8));
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onTNTExplode(EntityExplodeEvent e) throws Exception {
        block18: {
            TNTTypes tnt_type;
            TNTPrimed tnt;
            block26: {
                block25: {
                    block24: {
                        block23: {
                            block22: {
                                block21: {
                                    block20: {
                                        block19: {
                                            if (e.getEntity() == null || e.getEntityType() != EntityType.PRIMED_TNT) {
                                                return;
                                            }
                                            tnt = (TNTPrimed)e.getEntity();
                                            if (!tnt.hasMetadata("custom_tnt")) break block18;
                                            tnt_type = TNTTypes.valueOf(tnt.getMetadata("custom_tnt").get(0).asString());
                                            if (tnt_type != TNTTypes.Huge) break block19;
                                            Location loc = tnt.getLocation();
                                            this.spawnFastExplosion(loc.clone().add(0.0, 3.0, 0.0), true);
                                            this.spawnFastExplosion(loc.clone().add(0.0, -3.0, 0.0), true);
                                            if (ThreadLocalRandom.current().nextBoolean()) {
                                                this.spawnFastExplosion(loc.clone().add(3.0, 0.0, 0.0), true);
                                                this.spawnFastExplosion(loc.clone().add(0.0, 0.0, 3.0), true);
                                            } else {
                                                this.spawnFastExplosion(loc.clone().add(-3.0, 0.0, 0.0), true);
                                                this.spawnFastExplosion(loc.clone().add(0.0, 0.0, -3.0), true);
                                            }
                                            break block18;
                                        }
                                        if (tnt_type != TNTTypes.Chemical) break block20;
                                        for (Entity ent : tnt.getNearbyEntities(8.0, 8.0, 8.0)) {
                                            if (!(ent instanceof LivingEntity)) continue;
                                            LivingEntity le = (LivingEntity)ent;
                                            le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 240, 1));
                                            le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 360, 1));
                                            le.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 360, 1));
                                            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 360, 2));
                                            if (!(le instanceof Player)) continue;
                                            le.sendMessage(ChatColor.RED + "You were hit with Chemical TNT!");
                                        }
                                        break block18;
                                    }
                                    if (tnt_type != TNTTypes.Lethal) break block21;
                                    for (int i = 0; i < 4; ++i) {
                                        this.spawnFastExplosion(tnt.getLocation());
                                    }
                                    break block18;
                                }
                                if (tnt_type != TNTTypes.Incindiary) break block22;
                                Location l = tnt.getLocation();
                                l.getWorld().playEffect(l, Effect.FLAME, 50);
                                for (Entity ent : tnt.getNearbyEntities(8.0, 8.0, 8.0)) {
                                    LivingEntity le;
                                    if (!(ent instanceof LivingEntity) || !((le = (LivingEntity)ent) instanceof Player)) continue;
                                    le.setFireTicks(180);
                                    le.sendMessage(ChatColor.RED + "You were hit with Incindiary TNT!");
                                    le.getWorld().playEffect(le.getLocation(), Effect.FLAME, 12);
                                }
                                break block18;
                            }
                            if (tnt_type != TNTTypes.Lucky) break block23;
                            for (int i = 0; i < e.blockList().size(); ++i) {
                                Block b = e.blockList().get(i);
                                if (b.getType() != Material.MOB_SPAWNER) continue;
                                double rand = Math.random();
                                Bukkit.getLogger().info("[CosmicCustomTNT] Lucky TNT processing spawner at " + b.getX() + "x ," + b.getY() + "y ," + b.getZ() + "z: " + rand + " <= 0.60?");
                                if (!(rand <= 0.6)) continue;
                                e.blockList().remove(i);
                                byte data = b.getData();
                                int typeID = b.getType().getId();
                                CreatureSpawner cs = (CreatureSpawner) b.getState();
                                ItemStack is = SilkUtil.hookIntoSilkSpanwers().newSpawnerItem(String.valueOf(cs.getSpawnedType().getTypeId()), CC.translate("&a&l") + cs.getSpawnedType().name() + "&f Spawner", 1, false);

                                int spawnerCount = cs.getSpawnerCount();
                                if (spawnerCount == 4 && !Board.getFactionAt(new FLocation(cs.getLocation())).isNormal()) {
                                    cs.setSpawnerCount((short)1);
                                    spawnerCount = 1;
                                }
                                try {
                                    this.logBreak(b, typeID, data, "Lucky TNT");
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                                b.setType(Material.AIR);
                                if (b.getType() != Material.AIR) continue;
                                while (spawnerCount-- > 0) {
                                    b.getWorld().dropItem(b.getLocation(), is);
                                }
                                de.inventivegames.particle.ParticleEffect.INSTANT_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), 0f, 1f, 0f, 0.6f, 20);

                            }
                            break block18;
                        }
                        if (tnt_type != TNTTypes.Tactical) break block24;
                        for (int i = 0; i < e.blockList().size(); ++i) {
                            Block b = e.blockList().get(i);
                            if (b.getType() != Material.MOB_SPAWNER) continue;
                            double rand = Math.random();
                            Bukkit.getLogger().info("[CosmicCustomTNT] Tatical TNT processing spawner at " + b.getX() + "x ," + b.getY() + "y ," + b.getZ() + "z: " + rand + " <= 0.30?");
                            e.blockList().remove(i);
                            int id = b.getTypeId();
                            byte data = b.getData();
                            CreatureSpawner cs = (CreatureSpawner) b.getState();
                            ItemStack is = SilkUtil.hookIntoSilkSpanwers().newSpawnerItem(String.valueOf(cs.getSpawnedType().getTypeId()), CC.translate("&a&l") + cs.getSpawnedType().name() + "&f Spawner", 1, false);

                            int spawnerCount = cs.getSpawnerCount();
                            if (spawnerCount == 4 && !Board.getFactionAt(new FLocation(cs.getLocation())).isNormal()) {
                                cs.setSpawnerCount((short)1);
                                spawnerCount = 1;
                            }
                            try {
                                this.logBreak(b, id, data, "Tactical TNT");
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            b.setType(Material.AIR);
                            if (!(rand <= 0.3) || b.getType() != Material.AIR) continue;
                            while (spawnerCount-- > 0) {
                                b.getWorld().dropItem(b.getLocation(), is);
                            }
                            de.inventivegames.particle.ParticleEffect.INSTANT_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), 0f, 0.5f, 0f, 0.5f, 20);

                        }
                        e.setCancelled(true);
                        e.setYield(0.0f);
                        e.blockList().clear();
                        break block18;
                    }
                    if (tnt_type != TNTTypes.Mimic) break block25;
                    final Location l = tnt.getLocation();
                    Bukkit.getScheduler().runTaskLater(CosmicCustomTNT.get(), new Runnable(){

                        @SneakyThrows
                        @Override
                        public void run() {
                            TNTListener.this.spawnFastExplosion(l);
                            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), l, 0f, 0.0f, 0f, 1.2f, 30);

                        }
                    }, 100L);
                    Bukkit.getScheduler().runTaskLater(CosmicCustomTNT.get(), new Runnable(){

                        @SneakyThrows
                        @Override
                        public void run() {
                            TNTListener.this.spawnFastExplosion(l);
                            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), l, 0f, 0.0f, 0f, 1.2f, 30);
                        }
                    }, 200L);
                    Bukkit.getScheduler().runTaskLater(CosmicCustomTNT.get(), new Runnable(){

                        @SneakyThrows
                        @Override
                        public void run() {
                            TNTListener.this.spawnFastExplosion(l);
                            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), l, 0f, 0.0f, 0f, 1.2f, 30);
                        }
                    }, 300L);
                    break block18;
                }
                if (tnt_type != TNTTypes.Hex) break block26;
                final List<Block> blockList = e.blockList();
                for (Block b : blockList) {
                    if (b.getType() == Material.AIR || b.getType() == Material.MOB_SPAWNER) continue;
                    b.setMetadata("hexBlocks", new FixedMetadataValue(CosmicCustomTNT.get(), System.currentTimeMillis()));
                    de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), 0f, 0.0f, 0f, 1.2f, 20);

                    Bukkit.getScheduler().runTaskLater(CosmicCustomTNT.get(), new Runnable(){

                        @Override
                        public void run() {
                            for (Block b : blockList) {
                                b.removeMetadata("hexBlocks", CosmicCustomTNT.get());
                            }
                        }
                    }, 600L);
                }
                break block18;
            }
            if (tnt_type != TNTTypes.Silent) break block18;
            double distance = this.silentTNTSoundRange;
            for (Entity ent : tnt.getNearbyEntities(distance, distance * 2.0, distance)) {
                if (!(ent instanceof Player)) continue;
                ((Player)ent).playSound(tnt.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            }
        }
    }

    private TNTTypes getTNTType(ItemStack tnt_is) {
        if (!(tnt_is != null && tnt_is.hasItemMeta() && tnt_is.getItemMeta().hasDisplayName() && tnt_is.getItemMeta().hasLore())) {
            return null;
        }
        TNTTypes tnt_type = null;
        String name = tnt_is.getItemMeta().getDisplayName();
        for (TNTTypes tt : TNTTypes.values()) {
            if (!name.equals(tt.getItemName())) continue;
            tnt_type = tt;
            break;
        }
        return tnt_type;
    }

    private void spawnFastExplosion(Location l) {
        this.spawnFastExplosion(l, true);
    }

    private void spawnFastExplosion(Location l, boolean onlyAir) {
        Material type = l.getBlock().getType();
        if (onlyAir && type != Material.AIR || !this.nonSolidBlocks.contains(type)) {
            return;
        }
        TNTPrimed tnt = (TNTPrimed)l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
        tnt.setMetadata("fastExplosion", new FixedMetadataValue(CosmicCustomTNT.get(), true));
        tnt.setFuseTicks(0);
    }

    private void logBreak(Block block, int id, byte data, String tnt) {
        if (!this.coreProtectEnabled) {
            return;
        }
        try {
            CoreProtectAPI api = ((CoreProtect)Bukkit.getPluginManager().getPlugin("CoreProtect")).getAPI();
            if (api != null) {
                api.logRemoval(tnt, block.getLocation(), id, data);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

