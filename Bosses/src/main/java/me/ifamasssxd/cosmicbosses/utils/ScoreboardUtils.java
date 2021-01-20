package me.ifamasssxd.cosmicbosses.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.EndOfWorld;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.planetnul.CorruptCosmonaut;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ScoreboardUtils {
    private static HashMap<UUID, ObjectiveWrapper> wrappers;
    private static DecimalFormat format;

    public static void setupBossScoreboard(final Player p, final Boss boss) {
        p.setMetadata("boss_scoreboard", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) (System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L))));
        final Scoreboard sb = p.getScoreboard();
        if (sb.getObjective("boss") != null) {
            sb.getObjective("boss").unregister();
        }
        final Objective objective = sb.registerNewObjective("boss", "dummy");
        objective.setDisplayName(boss.getDifficulty().getColor() + ChatColor.BOLD.toString() + boss.getName());
        objective.getScore(ChatColor.RED.toString()).setScore(15);
        objective.getScore(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Health").setScore(14);
        objective.getScore(ChatColor.RED.toString() + Math.ceil(boss.getEntity().getHealth())).setScore(13);
        objective.getScore(ChatColor.WHITE.toString()).setScore(12);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public static void clearBossScoreboard(final Player p) {
        final Scoreboard sb = p.getScoreboard();
        if (sb.getObjective("boss") != null) {
            sb.getObjective("boss").unregister();
        }
        p.removeMetadata("boss_scoreboard", (Plugin) CosmicBosses.getInstance());
        ScoreboardUtils.wrappers.remove(p.getUniqueId());
    }

    public static void updateBossScoreboard(final Player p, final Boss boss) {
        p.setMetadata("boss_scoreboard", (MetadataValue) new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), (Object) (System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L))));
        final Scoreboard sb = p.getScoreboard();
        Objective objective;
        if (sb.getObjective("boss") == null) {
            objective = sb.registerNewObjective("boss", "dummy");
            objective.setDisplayName(boss.getColor(true) + boss.getName());
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        } else {
            objective = sb.getObjective("boss");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        final ObjectiveWrapper wrapper = ScoreboardUtils.wrappers.containsKey(p.getUniqueId()) ? ScoreboardUtils.wrappers.get(p.getUniqueId()) : new ObjectiveWrapper(objective, sb, boss.getName());
        if (!wrapper.getBossName().equals(boss.getName())) {
            objective.setDisplayName(boss.getDifficulty().getColor() + ChatColor.BOLD.toString() + boss.getName());
            wrapper.clear();
            wrapper.setBossName(boss.getName());
        }
        final int index = 15;
        wrapper.setScore(15, ChatColor.RED.toString());
        wrapper.setScore(14, ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Health");
        wrapper.setScore(13, ChatColor.RED.toString() + Math.ceil(boss.getEntity().getHealth()));
        wrapper.setScore(12, ChatColor.WHITE.toString());
        if (boss.hasMinions() && boss.getMinions().size() > 0) {
            wrapper.setScore(11, ChatColor.YELLOW + ChatColor.BOLD.toString() + "Minions");
            wrapper.setScore(10, ChatColor.YELLOW.toString() + boss.getMinions().size());
            wrapper.setScore(9, ChatColor.YELLOW.toString());
        } else if (wrapper.lines.containsKey(11)) {
            wrapper.remove(11);
            wrapper.remove(10);
            wrapper.remove(9);
        }
        boolean showDamageDealt = true;
        if (boss instanceof Yijki) {
            final Yijki.WitherPhase phase = Yijki.WitherPhase.getPhase(boss.getPhase());
            wrapper.setScore(8, ChatColor.GREEN + ChatColor.BOLD.toString() + "Weakness");
            wrapper.setScore(7, ChatColor.GREEN + phase.getPhaseName());
            wrapper.setScore(6, ChatColor.GRAY.toString());
        } else if (boss instanceof DungeonYijki) {
            final DungeonYijki dungeonYijki = (DungeonYijki) boss;
            wrapper.setScore(8, ChatColor.AQUA + ChatColor.BOLD.toString() + "Ability");
            String name = (dungeonYijki.getActiveAbility() != null) ? dungeonYijki.getActiveAbility().getAbilityName() : "None";
            if (name.length() > 14) {
                name = name.substring(0, 14);
            }
            wrapper.setScore(7, ChatColor.AQUA + name);
            wrapper.setScore(6, ChatColor.BLUE.toString());
            final Yijki.WitherPhase phase2 = Yijki.WitherPhase.getPhase(boss.getPhase());
            wrapper.setScore(5, ChatColor.GREEN + ChatColor.BOLD.toString() + "Weakness");
            String phaseName = phase2.getPhaseName();
            if (dungeonYijki instanceof MotherOfYijki && ((MotherOfYijki) dungeonYijki).inDifferentDimension) {
                phaseName = "Invincible";
            }
            wrapper.setScore(4, ChatColor.GREEN + phaseName);
            wrapper.setScore(3, ChatColor.GRAY.toString());
            if (boss instanceof MotherOfYijki) {
                final MotherOfYijki moy = (MotherOfYijki) boss;
                final EndOfWorld eowAbility = moy.getEndOfWorldAbility();
                if (eowAbility != null && eowAbility.getSecondsCounted() > 0) {
                    final int secondsTillEnd = eowAbility.getCOUNTDOWN_TIME() - eowAbility.getSecondsCounted();
                    showDamageDealt = false;
                    wrapper.setScore(2, CC.RedB + "World Ending");
                    wrapper.setScore(1, CC.White + TimeUtils.formatSeconds((long) secondsTillEnd));
                }
            }
        } else if (boss instanceof CorruptCosmonaut) {
            CorruptCosmonaut cosmonaut = (CorruptCosmonaut) boss;
            wrapper.setScore(8, ChatColor.AQUA + ChatColor.BOLD.toString() + "Ability");
            String name = (cosmonaut.getActiveAbility() != null) ? cosmonaut.getActiveAbility().getAbilityName() : "None";
            if (name.length() > 14) {
                name = name.substring(0, 14);
            }
            wrapper.setScore(7, ChatColor.AQUA + name);
            wrapper.setScore(6, ChatColor.BLUE.toString());
        } else if (boss instanceof EndDragon && boss.isBossSpawned()) {
            wrapper.setScore(8, CC.RedB + "Distance");
            if (boss.getEntity().getLocation().getWorld().getName().equals(p.getWorld().getName())) {
                final double distanceFromBoss = boss.getEntity().getLocation().distance(p.getLocation());
                wrapper.setScore(7, CC.White + " " + NumberUtils.formatMoney(distanceFromBoss) + "m");
            } else {
                wrapper.setScore(7, CC.White + "/warp end");
            }
            wrapper.setScore(6, ChatColor.BLUE.toString());
        }
        final Integer damage = (boss.getDamageTracker()).get(p.getUniqueId());
        if (damage != null && damage > 0 && showDamageDealt) {
            int totalDamage = 0;
            for (final Integer dmg : (boss.getDamageTracker()).values()) {
                totalDamage += dmg;
            }
            final double percent = (double) damage / totalDamage * 100.0;
            String string = ChatColor.GREEN.toString() + damage + ChatColor.GRAY + " [" + ScoreboardUtils.format.format(percent) + "%]";
            if (string.length() > 16) {
                string = string.replace("[", "").replace("]", "");
                if (string.length() > 16) {
                    string = string.substring(0, 16);
                }
            }
            wrapper.setScore(2, ChatColor.GREEN + ChatColor.BOLD.toString() + "Damage Dealt");
            wrapper.setScore(1, string);
        }
        ScoreboardUtils.wrappers.put(p.getUniqueId(), wrapper);
    }

    static {
        ScoreboardUtils.wrappers = new HashMap<UUID, ObjectiveWrapper>();
        ScoreboardUtils.format = new DecimalFormat("###.#");
    }

    public static class ObjectiveWrapper {
        private Objective objective;
        private Scoreboard scoreboard;
        private HashMap<Integer, String> lines;
        private String bossName;

        public ObjectiveWrapper(final Objective obj, final Scoreboard sb, final String bossName) {
            this.lines = new HashMap<Integer, String>();
            this.objective = obj;
            this.scoreboard = sb;
            this.bossName = bossName;
        }

        public String getString(final int slot) {
            if (this.lines.containsKey(slot)) {
                return this.lines.get(slot);
            }
            return null;
        }

        public void clear() {
            this.lines.values().forEach(line -> this.scoreboard.resetScores(line));
            this.lines.clear();
        }

        public void remove(final int number) {
            final String line = this.lines.get(number);
            if (line != null) {
                this.scoreboard.resetScores(line);
            }
            this.lines.remove(number);
        }

        public void setScore(final int score, final String name) {
            final String current = this.getString(score);
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

        public void setScoreboard(final Scoreboard scoreboard) {
            this.scoreboard = scoreboard;
        }

        public String getBossName() {
            return this.bossName;
        }

        public void setBossName(final String bossName) {
            this.bossName = bossName;
        }
    }
}
