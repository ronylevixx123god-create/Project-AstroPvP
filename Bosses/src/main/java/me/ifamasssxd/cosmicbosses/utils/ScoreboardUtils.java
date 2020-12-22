/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 */
package me.ifamasssxd.cosmicbosses.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.EndOfWorld;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {
    private static HashMap<UUID, ObjectiveWrapper> wrappers = new HashMap();
    private static DecimalFormat format = new DecimalFormat("###.#");

    public static void setupBossScoreboard(Player p, Boss boss) {
        p.setMetadata("boss_scoreboard", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L))));
        Scoreboard sb = p.getScoreboard();
        if (sb.getObjective("boss") != null) {
            sb.getObjective("boss").unregister();
        }
        Objective objective = sb.registerNewObjective("boss", "dummy");
        objective.setDisplayName((Object)boss.getDifficulty().getColor() + ChatColor.BOLD.toString() + boss.getName());
        objective.getScore(ChatColor.RED.toString()).setScore(15);
        objective.getScore(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Health").setScore(14);
        objective.getScore(ChatColor.RED.toString() + Math.ceil(boss.getEntity().getHealth())).setScore(13);
        objective.getScore(ChatColor.WHITE.toString()).setScore(12);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public static void clearBossScoreboard(Player p) {
        Scoreboard sb = p.getScoreboard();
        if (sb.getObjective("boss") != null) {
            sb.getObjective("boss").unregister();
        }
        p.removeMetadata("boss_scoreboard", (Plugin)CosmicBosses.getInstance());
        wrappers.remove(p.getUniqueId());
    }

    public static void updateBossScoreboard(Player p, Boss boss) {
        ObjectiveWrapper wrapper;
        Objective objective;
        p.setMetadata("boss_scoreboard", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L))));
        Scoreboard sb = p.getScoreboard();
        if (sb.getObjective("boss") == null) {
            objective = sb.registerNewObjective("boss", "dummy");
            objective.setDisplayName(boss.getColor(true) + boss.getName());
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        } else {
            objective = sb.getObjective("boss");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        ObjectiveWrapper objectiveWrapper = wrapper = wrappers.containsKey(p.getUniqueId()) ? wrappers.get(p.getUniqueId()) : new ObjectiveWrapper(objective, sb, boss.getName());
        if (!wrapper.getBossName().equals(boss.getName())) {
            objective.setDisplayName((Object)boss.getDifficulty().getColor() + ChatColor.BOLD.toString() + boss.getName());
            wrapper.clear();
            wrapper.setBossName(boss.getName());
        }
        int index = 15;
        wrapper.setScore(15, ChatColor.RED.toString());
        wrapper.setScore(14, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Health");
        wrapper.setScore(13, ChatColor.RED.toString() + Math.ceil(boss.getEntity().getHealth()));
        wrapper.setScore(12, ChatColor.WHITE.toString());
        if (boss.hasMinions() && boss.getMinions().size() > 0) {
            wrapper.setScore(11, (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "Minions");
            wrapper.setScore(10, ChatColor.YELLOW.toString() + boss.getMinions().size());
            wrapper.setScore(9, ChatColor.YELLOW.toString());
        } else if (wrapper.lines.containsKey(11)) {
            wrapper.remove(11);
            wrapper.remove(10);
            wrapper.remove(9);
        }
        boolean showDamageDealt = true;
        if (boss instanceof Yijki) {
            Yijki.WitherPhase phase = Yijki.WitherPhase.getPhase(boss.getPhase());
            wrapper.setScore(8, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Weakness");
            wrapper.setScore(7, (Object)ChatColor.GREEN + phase.getPhaseName());
            wrapper.setScore(6, ChatColor.GRAY.toString());
        } else if (boss instanceof DungeonYijki) {
            MotherOfYijki moy;
            EndOfWorld eowAbility;
            String name;
            DungeonYijki dungeonYijki = (DungeonYijki)boss;
            wrapper.setScore(8, (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Ability");
            String string = name = dungeonYijki.getActiveAbility() != null ? dungeonYijki.getActiveAbility().getAbilityName() : "None";
            if (name.length() > 14) {
                name = name.substring(0, 14);
            }
            wrapper.setScore(7, (Object)ChatColor.AQUA + name);
            wrapper.setScore(6, ChatColor.BLUE.toString());
            Yijki.WitherPhase phase = Yijki.WitherPhase.getPhase(boss.getPhase());
            wrapper.setScore(5, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Weakness");
            String phaseName = phase.getPhaseName();
            if (dungeonYijki instanceof MotherOfYijki && ((MotherOfYijki)dungeonYijki).inDifferentDimension) {
                phaseName = "Invincible";
            }
            wrapper.setScore(4, (Object)ChatColor.GREEN + phaseName);
            wrapper.setScore(3, ChatColor.GRAY.toString());
            if (boss instanceof MotherOfYijki && (eowAbility = (moy = (MotherOfYijki)boss).getEndOfWorldAbility()) != null && eowAbility.getSecondsCounted() > 0) {
                int secondsTillEnd = eowAbility.getCOUNTDOWN_TIME() - eowAbility.getSecondsCounted();
                showDamageDealt = false;
                wrapper.setScore(2, CC.RedB + "World Ending");
                wrapper.setScore(1, CC.White + TimeUtils.formatSeconds((long)secondsTillEnd));
            }
        } else if (boss instanceof EndDragon && boss.isBossSpawned()) {
            wrapper.setScore(8, CC.RedB + "Distance");
            if (boss.getEntity().getLocation().getWorld().getName().equals(p.getWorld().getName())) {
                double distanceFromBoss = boss.getEntity().getLocation().distance(p.getLocation());
                wrapper.setScore(7, CC.White + " " + NumberUtils.formatMoney((double)distanceFromBoss) + "m");
            } else {
                wrapper.setScore(7, CC.White + "/warp end");
            }
            wrapper.setScore(6, ChatColor.BLUE.toString());
        }
        Integer damage = (Integer)boss.getDamageTracker().get(p.getUniqueId());
        if (damage != null && damage > 0 && showDamageDealt) {
            int totalDamage = 0;
            for (Integer dmg : boss.getDamageTracker().values()) {
                totalDamage += dmg.intValue();
            }
            double percent = Double.valueOf(damage.intValue()) / (double)totalDamage * 100.0;
            String string = ChatColor.GREEN.toString() + damage + (Object)ChatColor.GRAY + " [" + format.format(percent) + "%]";
            if (string.length() > 16 && (string = string.replace("[", "").replace("]", "")).length() > 16) {
                string = string.substring(0, 16);
            }
            wrapper.setScore(2, (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "Damage Dealt");
            wrapper.setScore(1, string);
        }
        wrappers.put(p.getUniqueId(), wrapper);
    }

    public static class ObjectiveWrapper {
        private Objective objective;
        private Scoreboard scoreboard;
        private HashMap<Integer, String> lines = new HashMap();
        private String bossName;

        public ObjectiveWrapper(Objective obj, Scoreboard sb, String bossName) {
            this.objective = obj;
            this.scoreboard = sb;
            this.bossName = bossName;
        }

        public String getString(int slot) {
            if (this.lines.containsKey(slot)) {
                return this.lines.get(slot);
            }
            return null;
        }

        public void clear() {
            this.lines.values().forEach(line -> this.scoreboard.resetScores(line));
            this.lines.clear();
        }

        public void remove(int number) {
            String line = this.lines.get(number);
            if (line != null) {
                this.scoreboard.resetScores(line);
            }
            this.lines.remove(number);
        }

        public void setScore(int score, String name) {
            String current = this.getString(score);
            this.objective = this.scoreboard.getObjective("boss");
            if (current != null) {
                System.out.println();
                this.scoreboard.resetScores(current);
            }
            if (this.objective != null) {
                this.objective.getScore(name).setScore(score);
                this.lines.put(score, name);
            }
        }

        public Scoreboard getScoreboard() {
            return this.scoreboard;
        }

        public void setScoreboard(Scoreboard scoreboard) {
            this.scoreboard = scoreboard;
        }

        public String getBossName() {
            return this.bossName;
        }

        public void setBossName(String bossName) {
            this.bossName = bossName;
        }
    }
}

