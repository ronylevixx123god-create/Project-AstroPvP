package org.arkhamnetwork.CosmicCustomTNT.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.Explosion;
import org.arkhamnetwork.CosmicCustomTNT.CosmicCustomTNT;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftTNTPrimed;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
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
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TNTListener
implements Listener {
    public static HashMap<Location, TNTTypes> tnt_tracker = new HashMap();
    private List<Material> nonSolidBlocks;
    private int silentTNTSoundRange = 16;
    private boolean coreProtectEnabled = false;

    public TNTListener() {
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
        if (e.getBlock().getType() == Material.TNT && tnt_tracker.containsKey((Object)e.getBlock().getLocation())) {
            e.setCancelled(true);
            Location loc = e.getBlock().getLocation();
            e.getBlock().setType(Material.AIR);
            e.getBlock().getWorld().dropItemNaturally(loc, TNTTypes.getTNTItem(tnt_tracker.get((Object)loc)));
            tnt_tracker.remove((Object)loc);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onExplosivePrime(ExplosionPrimeEvent e) {
        if (e.getEntity() != null && e.getEntity() instanceof TNTPrimed) {
            EntityTNTPrimed tntPrimed = ((CraftTNTPrimed)e.getEntity()).getHandle();
            TNTTypes tntType = tnt_tracker.get(e.getEntity().getLocation());
            if (tntType != null) {
                e.getEntity().setMetadata("custom_tnt", (MetadataValue)new FixedMetadataValue((Plugin)CosmicCustomTNT.get(), (Object)tntType.name()));
                return;
            }
            Location loc = tntPrimed.sourceLoc;
            TNTTypes tnt_type = tnt_tracker.remove((Object)loc);
            if (tnt_type != null) {
                e.getEntity().setMetadata("custom_tnt", (MetadataValue)new FixedMetadataValue((Plugin)CosmicCustomTNT.get(), (Object)tnt_type.name()));
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
            if (b.getType() == Material.AIR || !b.hasMetadata("hexBlocks") || System.currentTimeMillis() - ((MetadataValue)b.getMetadata("hexBlocks").get(0)).asLong() > 30000L) continue;
            if (c++ > 3) {
                return;
            }
            Bukkit.getScheduler().runTaskLater((Plugin)CosmicCustomTNT.get(), new Runnable(){

                @Override
                public void run() {
                    TNTListener.this.spawnFastExplosion(b.getLocation());
                }
            }, 2 + new Random().nextInt(8));
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onTNTExplode(EntityExplodeEvent e) {
        if (e.getEntity() == null || e.getEntityType() != EntityType.PRIMED_TNT)
            return;
        TNTPrimed tnt = (TNTPrimed)e.getEntity();
        if (tnt.hasMetadata("custom_tnt")) {
            TNTTypes tnt_type = TNTTypes.valueOf(((MetadataValue)tnt.getMetadata("custom_tnt").get(0)).asString());


            if (tnt_type == TNTTypes.Huge) {
                Location loc = tnt.getLocation();
                spawnFastExplosion(loc.clone().add(0.0D, 3.0D, 0.0D), true);
                spawnFastExplosion(loc.clone().add(0.0D, -3.0D, 0.0D), true);
                if (ThreadLocalRandom.current().nextBoolean()) {
                    spawnFastExplosion(loc.clone().add(3.0D, 0.0D, 0.0D), true);
                    spawnFastExplosion(loc.clone().add(0.0D, 0.0D, 3.0D), true);
                } else {
                    spawnFastExplosion(loc.clone().add(-3.0D, 0.0D, 0.0D), true);
                    spawnFastExplosion(loc.clone().add(0.0D, 0.0D, -3.0D), true);
                }


            } else if (tnt_type == TNTTypes.Chemical) {
                for (Entity ent : tnt.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
                    if (ent instanceof LivingEntity) {
                        LivingEntity le = (LivingEntity)ent;
                        le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 240, 1));
                        le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 360, 1));
                        le.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 360, 1));
                        le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 360, 2));
                        if (!(le instanceof Player))
                            continue;
                        ((Player)le).sendMessage(ChatColor.RED + "You were hit with Chemical TNT!");
                    }
                }


            } else if (tnt_type == TNTTypes.Lethal) {
                for (int i = 0; i < 4; i++)
                    spawnFastExplosion(tnt.getLocation());


            } else if (tnt_type == TNTTypes.Incindiary) {
                final Location l = tnt.getLocation();
                l.getWorld().playEffect(l, Effect.FLAME, 50);
                for (Entity ent2 : tnt.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
                    if (ent2 instanceof LivingEntity) {
                        LivingEntity le2 = (LivingEntity)ent2;
                        if (!(le2 instanceof Player))
                            continue;
                        ((Player)le2).setFireTicks(180);
                        ((Player)le2).sendMessage(ChatColor.RED + "You were hit with Incindiary TNT!");
                        ((Player)le2).getWorld().playEffect(le2.getLocation(), Effect.FLAME, 12);
                    }
                }


            } else if (tnt_type == TNTTypes.Lucky) {
                //Logic in spawners plugin

            } else if (tnt_type == TNTTypes.Tactical) {
                System.out.println("Block list size: " + e.blockList().size());
                for (int i = 0; i < e.blockList().size(); ++i) {
                    Block b = e.blockList().get(i);
                    if (b.getType() != Material.MOB_SPAWNER)
                        e.blockList().remove(b);
                    try {
                        de.inventivegames.particle.ParticleEffect.INSTANT_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), b.getLocation(), (float)0.6f, 0.6f, 0.6f, 1f,  (int)20);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }


            } else if (tnt_type == TNTTypes.Mimic) {
                final Location l = tnt.getLocation();
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCustomTNT.get(), new Runnable() {
                    public void run() {
                        TNTListener.this.spawnFastExplosion(l);
                        ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, l, 1.2F, 30);
                    }
                },100L);
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCustomTNT.get(), new Runnable() {
                    public void run() {
                        TNTListener.this.spawnFastExplosion(l);
                        ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, l, 1.2F, 30);
                    }
                },200L);
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicCustomTNT.get(), new Runnable() {
                    public void run() {
                        TNTListener.this.spawnFastExplosion(l);
                        ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, l, 1.2F, 30);
                    }
                },300L);


            } else if (tnt_type == TNTTypes.Hex) {
                final List<Block> blockList = e.blockList();
                for (Block b2 : blockList) {
                    if (b2.getType() == Material.AIR ||
                            b2.getType() == Material.MOB_SPAWNER)
                        continue;
                    b2.setMetadata("hexBlocks", (MetadataValue)new FixedMetadataValue((Plugin)CosmicCustomTNT.get(), Long.valueOf(System.currentTimeMillis())));
                    ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, b2.getLocation(), 1.2F, 20);
                    Bukkit.getScheduler().runTaskLater((Plugin)CosmicCustomTNT.get(), new Runnable() {
                        public void run() {
                            for (Block b : blockList)
                                b.removeMetadata("hexBlocks", (Plugin)CosmicCustomTNT.get());
                        }
                    },600L);
                }


            } else if (tnt_type == TNTTypes.Silent) {
                CosmicCustomTNT.get().getProtocolManager().addPacketListener(
                        new PacketAdapter(CosmicCustomTNT.get(), ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                            @Override
                            public void onPacketSending(PacketEvent event) {
                                if(event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                                    String name = event.getPacket().getStrings().read(0);
                                    if(!name.equals("random.explode"))
                                        return;
                                    event.setCancelled(true);
                                }
                            }
                        }
                );
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
        if (onlyAir && type != Material.AIR || !this.nonSolidBlocks.contains((Object)type)) {
            return;
        }
        TNTPrimed tnt = (TNTPrimed)l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
        tnt.setMetadata("fastExplosion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicCustomTNT.get(), (Object)true));
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

