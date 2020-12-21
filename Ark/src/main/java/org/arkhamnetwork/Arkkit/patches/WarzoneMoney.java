/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CommandWarzone;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoneyLMSTask;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoneyListener;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoneyParticleTask;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoneyTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WarzoneMoney
extends ArkPlugin {
    protected static HashMap<UUID, Integer> warzoneTimes;
    protected static HashMap<UUID, Integer> warzoneMoney;
    protected static HashMap<UUID, Integer> warzoneXp;
    protected static ProtectedRegion warzoneRegion;
    protected static int warzoneMinX;
    protected static int warzoneMinZ;
    protected static int warzoneMaxX;
    protected static int warzoneMaxZ;
    protected static Location warzoneCenter;
    protected static boolean isVanillaGalaxy;
    public static final String WARZONE_LMS;
    public static final String LMS_SURVIVED = ", they have survived in the main /spawn warzone for ";
    public static final String LMS_PREFIX;
    public static final String MINUTES = "minutes(s)";
    private static List<Player> cachedPlayersInMainWarzone;
    private static long lastWarzoneScan;

    public WarzoneMoney(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        for (ProtectedRegion pr : WorldGuardUtils.getWorldGuard().getGlobalRegionManager().get(Bukkit.getWorld((String)"world")).getRegions().values()) {
            if (!pr.getId().startsWith("warzone_")) continue;
            warzoneRegion = pr;
            warzoneMinX = warzoneRegion.getMinimumPoint().getBlockX();
            warzoneMaxX = warzoneRegion.getMaximumPoint().getBlockX();
            warzoneMinZ = warzoneRegion.getMinimumPoint().getBlockZ();
            warzoneMaxZ = warzoneRegion.getMaximumPoint().getBlockZ();
            warzoneCenter = Bukkit.getWorld((String)"world").getSpawnLocation();
            Bukkit.getLogger().info("[Arkkit (WarzoneMoney)] Found warzoneRegion: '" + warzoneRegion.getId() + "' minX=" + warzoneMinX + " minZ=" + warzoneMinZ + " maxX=" + warzoneMaxX + " maxZ=" + warzoneMaxZ);
            break;
        }
        if (warzoneRegion == null) {
            Bukkit.getLogger().info("[Arkkit (WarzoneMoney) Failed to find warzoneRegion, disabling...");
            this.kill();
            return;
        }
        isVanillaGalaxy = !new File("plugins/Enchantments.jar").exists();
        warzoneTimes = new HashMap();
        warzoneMoney = new HashMap();
        warzoneXp = new HashMap();
        new BukkitRunnable(){

            public void run() {
                if (!WarzoneMoney.this.isRunning()) {
                    this.cancel();
                    return;
                }
                Player warzonePlayer = WarzoneMoneyLMSTask.pLMS;
                if (warzonePlayer != null && warzonePlayer.isValid() && warzoneTimes.containsKey(warzonePlayer.getUniqueId())) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests add LMS_DEFENDER " + warzonePlayer.getName()));
                }
            }
        }.runTaskTimer(this.getBukkitPlugin(), 20L, 20L);
        this.registerTask(new WarzoneMoneyTask().runTaskTimer((Plugin)Arkkit.get(), 100L, 100L), WarzoneMoneyTask.class);
        this.registerTask(new WarzoneMoneyLMSTask().runTaskTimer((Plugin)Arkkit.get(), 160L, 160L), WarzoneMoneyLMSTask.class);
        this.registerTask(new WarzoneMoneyParticleTask().runTaskTimer((Plugin)Arkkit.get(), 1L, 1L), WarzoneMoneyParticleTask.class);
        this.registerCommand(new CommandWarzone("warzone", "/<command>", "Command for Warzone Rewards.", Arrays.asList("lms")));
        this.registerListener(new WarzoneMoneyListener());
    }

    @Override
    public void disable() {
        this.unregisterCommands();
        this.unregisterListeners();
        this.unregisterTasks();
    }

    public static boolean isLMS(Player p) {
        return WarzoneMoneyLMSTask.pLMS != null && p.getUniqueId().equals(WarzoneMoneyLMSTask.pLMS.getUniqueId()) && !((CraftPlayer)WarzoneMoneyLMSTask.pLMS).getHandle().playerConnection.isDisconnected();
    }

    public static List<Player> getPlayersInMainWarzone() {
        ArrayList<Player> pL = new ArrayList<Player>();
        if (cachedPlayersInMainWarzone != null && System.currentTimeMillis() - lastWarzoneScan < 1000L) {
            return cachedPlayersInMainWarzone;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getWorld().getName().equals("world") || p.isDead() || !WorldGuardUtils.getRegionName(p.getLocation()).startsWith("warzone_") || WorldGuardUtils.isPvPDisabled(p.getLocation())) continue;
            pL.add(p);
        }
        cachedPlayersInMainWarzone = pL;
        lastWarzoneScan = System.currentTimeMillis();
        return pL;
    }

    static {
        isVanillaGalaxy = false;
        WARZONE_LMS = (Object)ChatColor.YELLOW + " is the new " + (Object)ChatColor.UNDERLINE + "Warzone LMS" + (Object)ChatColor.YELLOW;
        LMS_PREFIX = CC.GoldB + "LMS > ";
        cachedPlayersInMainWarzone = new ArrayList<Player>();
        lastWarzoneScan = 0L;
    }
}

