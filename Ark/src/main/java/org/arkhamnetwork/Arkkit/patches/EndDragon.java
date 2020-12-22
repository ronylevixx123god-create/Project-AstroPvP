/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicFund.CosmicFundAPI
 *  com.cosmicpvp.cosmicutils.struct.StorableLocation
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EntityUtils
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.google.common.collect.Lists
 *  com.google.gson.reflect.TypeToken
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.utils.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.EnderDragon
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.struct.StorableLocation;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.arkhamnetwork.Arkkit.libs.ArkPluginCommand;
import org.arkhamnetwork.Arkkit.libs.ArkPluginListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EndDragon
extends ArkPlugin {
    private EndDragonData endDragonData;
    private File endDragonFile;
    private Type token = new TypeToken<EndDragonData>(){}.getType();

    public EndDragon(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        this.endDragonFile = JSONUtils.getOrCreateFile((File)this.getBukkitPlugin().getDataFolder(), (String)"endDragonData.json");
        this.endDragonData = (EndDragonData)JSONUtils.fromJson((File)this.endDragonFile, (Type)this.token);
        if (this.endDragonData == null) {
            this.endDragonData = new EndDragonData();
        }
        if (this.endDragonData.getNextSpawnTime() <= 0L) {
            this.endDragonData.createNextSpawnTime();
        }
        this.registerTask(new BukkitRunnable(){

            public void run() {
                if (!EndGrinding.fundEnabled) {
                    return;
                }
                long lastSpawn = EndDragon.this.endDragonData.getLastSpawnTime();
                long nextSpawn = EndDragon.this.endDragonData.getNextSpawnTime();
                if ((System.currentTimeMillis() - lastSpawn >= TimeUnit.HOURS.toMillis(7L) || lastSpawn <= 0L) && nextSpawn > 0L && nextSpawn < System.currentTimeMillis()) {
                    EndDragon.this.endDragonData.createNextSpawnTime();
                    EndDragon.this.attemptSpawn();
                }
            }
        }.runTaskTimer(this.getBukkitPlugin(), 200L, 20L), EndDragon.class);
        this.registerListener(new EndDragonListener(this));
        this.registerCommand(new EndDragonEggCommand(this));
    }

    public boolean isEndDragonEgg(Block block) {
        return this.endDragonData.getEggLocation() != null && this.endDragonData.getEggLocation().isLocation(block.getLocation());
    }

    public LivingEntity attemptSpawn() {
        if (EntityUtils.isEndDragonSpawned()) {
            this.getBukkitPlugin().getLogger().info("[EndDragon] Ignoring spawn for EndDragon due to already being spawned...");
            return null;
        }
        this.endDragonData.lastSpawnTime = System.currentTimeMillis();
        Location spawnLocation = new Location(Bukkit.getWorld((String)"world_end"), 62.0, 39.0, 69.0);
        spawnLocation.getChunk().load();
        Bukkit.getLogger().info("[Arkkit (EndDragon)] Spawning timeless dragon at " + (Object)spawnLocation);
        Boss boss = BossType.END_DRAGON.createInstance(spawnLocation);
        boss.setNaturallySpawned(true);
        boss.onBossSpawn();
        return boss.getEntity();
    }

    public void describeEggData(CommandSender sender) {
        sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.Red + "*** " + CC.RedB + "Timeless Dragon " + CC.Red + "***")));
        if (!EndGrinding.fundEnabled) {
            sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.Red + "Unlock access by using /fund!")));
            return;
        }
        long lastSpawned = this.endDragonData.getLastSpawnTime();
        if (lastSpawned > 0L) {
            sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.Red + "Last Spawned: " + CC.White + TimeUtils.formatSeconds((long)((System.currentTimeMillis() - lastSpawned) / 1000L)) + " ago")));
        }
        if (EntityUtils.isEndDragonSpawned()) {
            sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.Red + "Currently alive in The End!")));
        } else if (this.endDragonData.getNextSpawnTime() > 0L) {
            sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.Red + "Next Spawn: " + CC.White + TimeUtils.formatSeconds((long)((this.endDragonData.getNextSpawnTime() - System.currentTimeMillis()) / 1000L)))));
        }
        if (this.endDragonData.getDragonDamager() != null && this.endDragonData.getDragonDamager().size() > 0) {
            sender.sendMessage("");
            sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.RedB + "Last Top Damagers")));
            ArrayList<EndDragonDamager> damagers = Lists.newArrayList(this.endDragonData.getDragonDamager().values());
            Collections.sort(damagers);
            int index = 1;
            for (EndDragonDamager damager : damagers) {
                if (index > 5) break;
                sender.sendMessage(StringUtils.getCenteredMessage((String)(CC.RedB + index++ + ". " + CC.White + damager.getUsername() + " " + CC.Red + NumberUtils.formatMoney((double)damager.getDealth()) + " DMG")));
            }
        }
        sender.sendMessage("");
    }

    @Override
    public void disable() throws IOException {
        super.disable();
        JSONUtils.saveJSONToFile((File)this.endDragonFile, (Object)this.endDragonData, (Object)this.token);
        this.unregisterTasks();
        this.unregisterListeners();
        this.unregisterCommands();
    }

    public EndDragonData getEndDragonData() {
        return this.endDragonData;
    }

    public class EndDragonDamager
    implements Comparable<EndDragonDamager> {
        private double percentDealth;
        private double dealth;
        private String username;

        @Override
        public int compareTo(EndDragonDamager o) {
            return Double.compare(this.getPercentDealth(), o.getPercentDealth());
        }

        public EndDragonDamager(double percentDealth, double dealth, String username) {
            this.percentDealth = percentDealth;
            this.dealth = dealth;
            this.username = username;
        }

        public double getPercentDealth() {
            return this.percentDealth;
        }

        public double getDealth() {
            return this.dealth;
        }

        public String getUsername() {
            return this.username;
        }
    }

    public class EndDragonData {
        private long lastSpawnTime;
        private long nextSpawnTime;
        private StorableLocation eggLocation;
        private Map<UUID, EndDragonDamager> dragonDamager = new HashMap<UUID, EndDragonDamager>();

        public long createNextSpawnTime() {
            this.nextSpawnTime = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(7L) + (long)ThreadLocalRandom.current().nextInt((int)TimeUnit.HOURS.toMillis(3L));
            return this.nextSpawnTime;
        }

        public long getLastSpawnTime() {
            return this.lastSpawnTime;
        }

        public long getNextSpawnTime() {
            return this.nextSpawnTime;
        }

        public StorableLocation getEggLocation() {
            return this.eggLocation;
        }

        public Map<UUID, EndDragonDamager> getDragonDamager() {
            return this.dragonDamager;
        }
    }

    class EndDragonEggCommand
    extends ArkPluginCommand<EndDragon> {
        public EndDragonEggCommand(EndDragon patch) {
            super(patch, "enddragonegg");
        }

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!sender.isOp()) {
                return true;
            }
            if (args.length == 1 && args[0].equals("setegg")) {
                Player block = (Player)sender;
                Block looking = block.getTargetBlock(Collections.singleton(Material.AIR), 4);
                if (looking == null || looking.getType() != Material.DRAGON_EGG) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be looking at the egg to set!");
                    return true;
                }
                ((EndDragon)this.patch).getEndDragonData().eggLocation = new StorableLocation(looking.getLocation());
                sender.sendMessage(CC.Red + "End Dragon egg set!");
                return true;
            }
            sender.sendMessage(CC.Red + "/endragonegg setegg - Sets the egg looking at.");
            return false;
        }
    }

    class EndDragonListener
    extends ArkPluginListener<EndDragon> {
        public EndDragonListener(EndDragon patch) {
            super(patch);
        }

        @EventHandler(priority=EventPriority.LOWEST)
        public void onPlayerInteractEvent(PlayerInteractEvent event) {
            Block clicked;
            if (event.hasBlock() && ((EndDragon)this.patch).isEndDragonEgg(clicked = event.getClickedBlock())) {
                event.setCancelled(true);
                ((EndDragon)this.patch).describeEggData((CommandSender)event.getPlayer());
            }
        }

        @EventHandler
        public void onBlockPhysics(BlockPhysicsEvent event) {
            if (event.getChangedType() == Material.DRAGON_EGG && ((EndDragon)this.patch).isEndDragonEgg(event.getBlock())) {
                event.setCancelled(true);
            }
        }

        @EventHandler(priority=EventPriority.LOWEST)
        public void onBossDeath(EntityDeathEvent event) {
            Boss boss;
            if (event.getEntity() instanceof EnderDragon && (boss = (Boss)Boss.bosses.get(event.getEntity().getUniqueId())) != null && boss.getBossType() == BossType.END_DRAGON) {
                LinkedList<Player> damagers = boss.getTopDamage(5, true);
                ((EndDragon)this.patch).getEndDragonData().dragonDamager.clear();
                if (damagers != null && !damagers.isEmpty()) {
                    for (Player pl : damagers) {
                        Integer damage = (Integer)boss.getDamageTracker().get((Object)pl.getUniqueId());
                        if (damage == null || damage <= 0) continue;
                        ((EndDragon)this.patch).getEndDragonData().dragonDamager.put(pl.getUniqueId(), new EndDragonDamager(0.0, damage.intValue(), pl.getName()));
                    }
                }
            }
        }
    }
}

