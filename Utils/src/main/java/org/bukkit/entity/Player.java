/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import java.net.InetSocketAddress;
import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.map.MapView;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.scoreboard.Scoreboard;

public interface Player
extends HumanEntity,
Conversable,
CommandSender,
OfflinePlayer,
PluginMessageRecipient {
    public String getDisplayName();

    public void setDisplayName(String var1);

    public String getPlayerListName();

    public void setPlayerListName(String var1);

    public void setCompassTarget(Location var1);

    public Location getCompassTarget();

    public InetSocketAddress getAddress();

    @Override
    public void sendRawMessage(String var1);

    public void kickPlayer(String var1);

    public void chat(String var1);

    public boolean performCommand(String var1);

    public boolean isSneaking();

    public void setSneaking(boolean var1);

    public boolean isSprinting();

    public void setSprinting(boolean var1);

    public void saveData();

    public void loadData();

    public void setSleepingIgnored(boolean var1);

    public boolean isSleepingIgnored();

    @Deprecated
    public void playNote(Location var1, byte var2, byte var3);

    public void playNote(Location var1, Instrument var2, Note var3);

    public void playSound(Location var1, Sound var2, float var3, float var4);

    @Deprecated
    public void playSound(Location var1, String var2, float var3, float var4);

    @Deprecated
    public void playEffect(Location var1, Effect var2, int var3);

    public <T> void playEffect(Location var1, Effect var2, T var3);

    @Deprecated
    public void sendBlockChange(Location var1, Material var2, byte var3);

    @Deprecated
    public boolean sendChunkChange(Location var1, int var2, int var3, int var4, byte[] var5);

    @Deprecated
    public void sendBlockChange(Location var1, int var2, byte var3);

    public void sendMap(MapView var1);

    @Deprecated
    public void updateInventory();

    public void awardAchievement(Achievement var1);

    public void removeAchievement(Achievement var1);

    public boolean hasAchievement(Achievement var1);

    public void incrementStatistic(Statistic var1) throws IllegalArgumentException;

    public void decrementStatistic(Statistic var1) throws IllegalArgumentException;

    public void incrementStatistic(Statistic var1, int var2) throws IllegalArgumentException;

    public void decrementStatistic(Statistic var1, int var2) throws IllegalArgumentException;

    public void setStatistic(Statistic var1, int var2) throws IllegalArgumentException;

    public int getStatistic(Statistic var1) throws IllegalArgumentException;

    public void incrementStatistic(Statistic var1, Material var2) throws IllegalArgumentException;

    public void decrementStatistic(Statistic var1, Material var2) throws IllegalArgumentException;

    public int getStatistic(Statistic var1, Material var2) throws IllegalArgumentException;

    public void incrementStatistic(Statistic var1, Material var2, int var3) throws IllegalArgumentException;

    public void decrementStatistic(Statistic var1, Material var2, int var3) throws IllegalArgumentException;

    public void setStatistic(Statistic var1, Material var2, int var3) throws IllegalArgumentException;

    public void incrementStatistic(Statistic var1, EntityType var2) throws IllegalArgumentException;

    public void decrementStatistic(Statistic var1, EntityType var2) throws IllegalArgumentException;

    public int getStatistic(Statistic var1, EntityType var2) throws IllegalArgumentException;

    public void incrementStatistic(Statistic var1, EntityType var2, int var3) throws IllegalArgumentException;

    public void decrementStatistic(Statistic var1, EntityType var2, int var3);

    public void setStatistic(Statistic var1, EntityType var2, int var3);

    public void setPlayerTime(long var1, boolean var3);

    public long getPlayerTime();

    public long getPlayerTimeOffset();

    public boolean isPlayerTimeRelative();

    public void resetPlayerTime();

    public void setPlayerWeather(WeatherType var1);

    public WeatherType getPlayerWeather();

    public void resetPlayerWeather();

    public void giveExp(int var1);

    public void giveExpLevels(int var1);

    public float getExp();

    public void setExp(float var1);

    public int getLevel();

    public void setLevel(int var1);

    public int getTotalExperience();

    public void setTotalExperience(int var1);

    public float getExhaustion();

    public void setExhaustion(float var1);

    public float getSaturation();

    public void setSaturation(float var1);

    public int getFoodLevel();

    public void setFoodLevel(int var1);

    @Override
    public Location getBedSpawnLocation();

    public void setBedSpawnLocation(Location var1);

    public void setBedSpawnLocation(Location var1, boolean var2);

    public boolean getAllowFlight();

    public void setAllowFlight(boolean var1);

    public void hidePlayer(Player var1);

    public void showPlayer(Player var1);

    public boolean canSee(Player var1);

    @Override
    @Deprecated
    public boolean isOnGround();

    public boolean isFlying();

    public void setFlying(boolean var1);

    public void setFlySpeed(float var1) throws IllegalArgumentException;

    public void setWalkSpeed(float var1) throws IllegalArgumentException;

    public float getFlySpeed();

    public float getWalkSpeed();

    @Deprecated
    public void setTexturePack(String var1);

    public void setResourcePack(String var1);

    public Scoreboard getScoreboard();

    public void setScoreboard(Scoreboard var1) throws IllegalArgumentException, IllegalStateException;

    public boolean isHealthScaled();

    public void setHealthScaled(boolean var1);

    public void setHealthScale(double var1) throws IllegalArgumentException;

    public double getHealthScale();
}

