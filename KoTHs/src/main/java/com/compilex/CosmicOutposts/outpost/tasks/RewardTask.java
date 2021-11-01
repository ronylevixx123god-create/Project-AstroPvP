/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ReflectionUtils
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  com.massivecraft.factions.integration.Econ
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.me.astropvp.cratekeys.enums.BossType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicOutposts.outpost.tasks;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.utils.LocationUtils;
import com.compilex.CosmicOutposts.utils.MonsterUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.integration.Econ;
import java.util.Random;

import lombok.SneakyThrows;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class RewardTask
implements Runnable {
    private Outpost outpost;
    private int iteration = 0;
    public boolean mobKilled = true;
    public long lastMobSpawn = 0L;
    private boolean useNewEconomy = true;

    public RewardTask(Outpost outpost) {
        this.outpost = outpost;
        try {
            if (ReflectionUtils.getMethod(Econ.class, (String)"depositFactionBalance", (Class[])new Class[]{Faction.class, Double.TYPE}) != null) {
                this.useNewEconomy = true;
                Bukkit.getLogger().info("Found depositFactionBalance, using NEW deposit method!");
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.useNewEconomy = false;
        Bukkit.getLogger().info("Unable to find depositFactionBalance, using old deposit method..");
    }

    @SneakyThrows
    @Override
    public void run() {
        ++this.iteration;
        if (this.outpost.getOutpostTier() == OutpostTier.COSMONAUT) {
            if (this.outpost.getControllingFaction() != null && (this.mobKilled || System.currentTimeMillis() - this.lastMobSpawn > 300000L)) {
                this.outpost.broadcastOutpostMessage("&d&l(!) &dSoul Demons are assaulting the &nCosmonaut Outpost!");
                int i = new Random().nextInt(4) + 2;
                while (i-- > 0) {
                    try {
                        //MonsterUtils.spawnSoulDemon(LocationUtils.getNearbyLocation(this.outpost.getDefenderSpawn().clone().subtract(0.0, 8.0, 0.0), 10, 6, 2));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                this.mobKilled = false;
                this.lastMobSpawn = System.currentTimeMillis();
            }
            if (this.iteration % 40 == 0) {
                Bukkit.broadcastMessage((String)"");
                Bukkit.broadcastMessage((String)(ChatColor.LIGHT_PURPLE.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.LIGHT_PURPLE + "A " + (Object)ChatColor.UNDERLINE + "Soul Reaper" + (Object)ChatColor.LIGHT_PURPLE + " boss is attacking the Cosmonaut /outpost, defeat it for treasure, souls, and soul dust!"));
                Bukkit.broadcastMessage((String)"");
                Boss b = BossType.SOUL_REAPER.createInstance(LocationUtils.getNearbyLocation(this.outpost.getDefenderSpawn().clone().subtract(0.0, 8.0, 0.0), 10, 6, 2));
                b.onBossSpawn();
                b.getEntity().setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin) CosmicKOTHPlugin.get_(), (Object)true));
            }
        }
        if (this.outpost.getOutpostTier() == OutpostTier.TRAINEE && this.outpost.getControllingFaction() != null) {
            Faction f = Factions.i.getByTag(this.outpost.getControllingFaction());
            if (f == null) {
                return;
            }
            if (this.useNewEconomy) {
                try {
                    Econ.depositFactionBalance((Faction)f, (double)25000.0);
                }
                catch (Exception e) {
                    this.useNewEconomy = false;
                    e.printStackTrace();
                    String bankId = f.getAccountId();
                    CosmicKOTHPlugin.get_().getEconomy().depositPlayer(bankId, 25000.0);
                }
            } else {
                CosmicKOTHPlugin.get_().getEconomy().depositPlayer(f.getAccountId(), 25000.0);
            }
            f.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "+ $25,000 " + (Object)ChatColor.GREEN + "(/f money" + (Object)ChatColor.GRAY + " [Trainee Outpost Perk]" + (Object)ChatColor.GREEN + ")");
        }
    }
}

