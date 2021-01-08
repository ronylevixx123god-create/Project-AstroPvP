/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  org.arkhamnetwork.Arkkit.utils.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicOutposts.outpost;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.outpost.tasks.CapTask;
import com.compilex.CosmicOutposts.outpost.tasks.RewardTask;
import com.compilex.CosmicOutposts.outpost.tasks.SanityTask;
import com.compilex.CosmicOutposts.outpost.tasks.ScoreboardTask;
import com.compilex.CosmicOutposts.outpost.tasks.VanillaMonsterSpawnTask;
import com.compilex.CosmicOutposts.utils.TimeUtils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Outpost {
    public static String OUTPOST_CAP_REGION = "outpost_cap_region";
    private final CosmicKOTHPlugin plugin;
    private final OutpostTier outpostTier;
    private final CapTask capTask;
    private final ScoreboardTask scoreboardTask;
    private final RewardTask rewardTask;
    private final SanityTask sanityTask;
    private VanillaMonsterSpawnTask vanillaMonsterTask;
    private int capTaskId;
    private int scoreboardTaskId;
    private int rewardTaskId;
    private int sanityTaskId;
    private int vanillaMonsterTaskId;
    private String controllingFaction;
    private Location attackerSpawn;
    private Location defenderSpawn;
    private World world;
    private long capTime;
    private String lastCapStatus = null;
    private long lastCapStatusTick = 0L;

    public Outpost(CosmicKOTHPlugin plugin, OutpostTier tier) {
        this.plugin = plugin;
        this.outpostTier = tier;
        this.capTask = new CapTask(this);
        this.scoreboardTask = new ScoreboardTask(this);
        this.rewardTask = new RewardTask(this);
        this.sanityTask = new SanityTask(this);
        if (this.outpostTier == OutpostTier.VANILLA) {
            this.vanillaMonsterTask = new VanillaMonsterSpawnTask(this);
        }
    }

    public void startTasks() {
        this.capTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this.capTask, 20L, 20L).getTaskId();
        this.scoreboardTaskId = Bukkit.getScheduler().runTaskTimer(this.plugin, this.scoreboardTask, 40L, 40L).getTaskId();
        this.rewardTaskId = Bukkit.getScheduler().runTaskTimer(this.plugin, this.rewardTask, 6000L, 6000L).getTaskId();
        this.sanityTaskId = Bukkit.getScheduler().runTaskTimer(this.plugin, this.sanityTask, 15L, 15L).getTaskId();
        if (this.vanillaMonsterTask != null) {
            this.vanillaMonsterTaskId = Bukkit.getScheduler().runTaskTimer(this.plugin, this.vanillaMonsterTask, 1200L, 1200L).getTaskId();
        }
    }

    public void end() {
        Bukkit.getScheduler().cancelTask(this.capTaskId);
        Bukkit.getScheduler().cancelTask(this.scoreboardTaskId);
        Bukkit.getScheduler().cancelTask(this.rewardTaskId);
        Bukkit.getScheduler().cancelTask(this.sanityTaskId);
        if (this.vanillaMonsterTask != null) {
            Bukkit.getScheduler().cancelTask(this.vanillaMonsterTaskId);
        }
    }

    public void broadcastOutpostMessage(String message) {
        String m = ChatColor.translateAlternateColorCodes('&', message);
        for (Player p : this.world.getPlayers()) {
            if (WorldGuardUtils.isPvPDisabled(p.getLocation())) continue;
            p.sendMessage(m);
        }
    }

    public String getTimeStringSinceLastCap() {
        if (this.capTime == 0L) {
            return "N/A";
        }
        long l = (System.currentTimeMillis() - this.capTime) / 1000L;
        return TimeUtils.getSecondsAsString(l);
    }

    public String getCachedCapStatus() {
        return this.lastCapStatus != null ? this.lastCapStatus : this.getCapStatus();
    }

    public String getCapStatus() {
        if (this.lastCapStatusTick + 20L > (long)MinecraftServer.currentTick) {
            if (this.lastCapStatus != null) {
                return this.lastCapStatus;
            }
            this.lastCapStatus = this.recalculateCapStatus();
            this.lastCapStatusTick = MinecraftServer.currentTick;
        } else {
            this.lastCapStatus = this.recalculateCapStatus();
            this.lastCapStatusTick = MinecraftServer.currentTick;
        }
        return this.lastCapStatus;
    }

    public String recalculateCapStatus() {
        if (this.getCapTask().getPlayersInCapZone().size() == 0 && this.getCapTask().getCapPercent() > 0.0f && this.getCapTask().getCapPercent() < 1.0f) {
            if (this.getControllingFaction() != null) {
                return ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "CONTROLLED";
            }
            return ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "IDLE";
        }
        if (this.getControllingFaction() != null) {
            if (this.getCapTask().getCapPercent() < 1.0f && this.getCapTask().getFactionMemberCounts(this.getCapTask().getPlayersInCapZone()).size() == 1 && this.getCapTask().getFactionMemberCounts(this.getCapTask().getPlayersInCapZone()).keySet().iterator().next().equals(this.controllingFaction)) {
                return ChatColor.GREEN.toString() + ChatColor.BOLD + "SECURING";
            }
            if (this.getCapTask().getAttackingFaction() == null || this.getCapTask().getAttackingFaction().equals(this.getCapTask().getDefendingFaction())) {
                return ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "CONTROLLED";
            }
            if (this.getCapTask().getFactionMemberCounts(this.getCapTask().getPlayersInCapZone()).containsKey(this.getControllingFaction())) {
                return ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "CONTESTED";
            }
            return ChatColor.RED.toString() + ChatColor.BOLD.toString() + "UNDER ATTACK";
        }
        if (this.getCapTask().getAttackingFaction() == null && this.getCapTask().getDefendingFaction() == null) {
            return ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "UNCONTESTED";
        }
        if (this.getCapTask().getFactionMemberCounts(this.getCapTask().getPlayersInCapZone()).size() > 1) {
            return ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "BLOCKED";
        }
        if (this.getCapTask().getAttackingFaction() != null && this.getCapTask().getDefendingFaction() != null && !this.getCapTask().getAttackingFaction().equals(this.getCapTask().getDefendingFaction())) {
            return ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "CONTESTED";
        }
        if (this.getCapTask().getAttackingFaction() != null && this.getCapTask().doesFactionControlAnOutpost(this.getCapTask().getAttackingFaction().getTag())) {
            return ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + "BLOCKED";
        }
        return ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "CAPTURING";
    }

    public OutpostTier getOutpostTier() {
        return this.outpostTier;
    }

    public CapTask getCapTask() {
        return this.capTask;
    }

    public ScoreboardTask getScoreboardTask() {
        return this.scoreboardTask;
    }

    public RewardTask getRewardTask() {
        return this.rewardTask;
    }

    public SanityTask getSanityTask() {
        return this.sanityTask;
    }

    public VanillaMonsterSpawnTask getVanillaMonsterTask() {
        return this.vanillaMonsterTask;
    }

    public String getControllingFaction() {
        return this.controllingFaction;
    }

    public void setControllingFaction(String controllingFaction) {
        this.controllingFaction = controllingFaction;
    }

    public Location getAttackerSpawn() {
        return this.attackerSpawn;
    }

    public Location getDefenderSpawn() {
        return this.defenderSpawn;
    }

    public void setAttackerSpawn(Location attackerSpawn) {
        this.attackerSpawn = attackerSpawn;
    }

    public void setDefenderSpawn(Location defenderSpawn) {
        this.defenderSpawn = defenderSpawn;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public long getCapTime() {
        return this.capTime;
    }

    public void setCapTime(long capTime) {
        this.capTime = capTime;
    }
}

