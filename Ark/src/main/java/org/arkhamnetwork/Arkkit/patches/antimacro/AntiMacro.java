/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.antimacro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.antimacro.AA_SQLManager;
import org.arkhamnetwork.Arkkit.patches.antimacro.AA_SQLQueryThread;
import org.arkhamnetwork.Arkkit.patches.antimacro.AntiMacroListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AntiMacro
extends ArkPlugin {
    protected static volatile CopyOnWriteArrayList<String> sql_query = new CopyOnWriteArrayList();
    protected static AA_SQLQueryThread sql_worker;
    protected static HashMap<String, Integer> playerXpGained;
    protected static HashMap<String, Integer> nextPlayerXpThreshold;
    protected static List<Integer> xpThresholds;
    protected static int minXp;
    protected static int maxXp;
    protected static int handSells;
    protected static int captchaTimeSeconds;

    public AntiMacro(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        playerXpGained = new HashMap();
        nextPlayerXpThreshold = new HashMap();
        xpThresholds = new ArrayList<Integer>();
        minXp = Arkkit.get().getConfig().getInt("settings.anti_macro.minXp");
        maxXp = Arkkit.get().getConfig().getInt("settings.anti_macro.maxXp");
        handSells = Arkkit.get().getConfig().getInt("settings.anti_macro.handSells");
        captchaTimeSeconds = Arkkit.get().getConfig().getInt("settings.anti_macro.captchaTime");
        Bukkit.getLogger().info("[Arkkit (AntiMacro)] minXp = " + minXp + ", maxXp = " + maxXp + ", captchaTime = " + captchaTimeSeconds);
        for (int i = new Random().nextInt(maxXp - minXp) + minXp; i < maxXp * 10; i += new Random().nextInt(maxXp - minXp) + minXp) {
            xpThresholds.add(i);
            Bukkit.getLogger().info("xpThreshold => " + i);
        }
        AA_SQLManager.initializeSQL((Plugin)Arkkit.get());
        sql_worker = new AA_SQLQueryThread();
        sql_worker.start();
        sql_query.add("CREATE TABLE IF NOT EXISTS `anti_macro` (`player_uuid` VARCHAR(36), `player_name` VARCHAR(16), `CPS_A` INT, `CPS_B` INT, `server_name` VARCHAR(16), `time` DATETIME)");
        this.registerListener(new AntiMacroListener());
    }

    @Override
    public void disable() {
        sql_worker.interrupt();
        sql_worker.doWork();
        sql_worker = null;
        this.unregisterListeners();
        this.unregisterTasks();
    }

    protected static int getNextPlayerThreshold(Player p) {
        if (nextPlayerXpThreshold.containsKey(p.getName())) {
            return nextPlayerXpThreshold.get(p.getName());
        }
        return xpThresholds.get(0);
    }

    protected static void incrementPlayerThreshold(Player p) {
        boolean op = p.isOp();
        if (nextPlayerXpThreshold.containsKey(p.getName())) {
            int mod = new Random().nextInt(maxXp - minXp) + minXp;
            nextPlayerXpThreshold.put(p.getName(), nextPlayerXpThreshold.get(p.getName()) + mod * (op ? 10 : 1));
        } else {
            int mod = new Random().nextInt(maxXp - minXp) + minXp;
            nextPlayerXpThreshold.put(p.getName(), xpThresholds.get(0) + mod * (op ? 10 : 1));
        }
    }

    protected static int addXpGained(String pName, int amount) {
        if (playerXpGained.containsKey(pName)) {
            playerXpGained.put(pName, playerXpGained.get(pName) + amount);
        } else {
            playerXpGained.put(pName, amount);
        }
        return playerXpGained.get(pName);
    }

    protected static int getXpGained(String pName) {
        return playerXpGained.containsKey(pName) ? playerXpGained.get(pName) : 0;
    }

    protected static void resetXpGained(String pName) {
        playerXpGained.remove(pName);
    }

    static {
        minXp = 5000;
        maxXp = 15000;
        handSells = 15;
        captchaTimeSeconds = 15;
    }
}

