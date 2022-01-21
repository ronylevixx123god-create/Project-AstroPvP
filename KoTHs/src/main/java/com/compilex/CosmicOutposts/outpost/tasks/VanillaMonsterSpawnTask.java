/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.struct.factions.points.FactionPointUtils
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  org.bukkit.entity.Player
 */
package com.compilex.CosmicOutposts.outpost.tasks;

import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.utils.LocationUtils;
import com.compilex.CosmicOutposts.utils.MonsterUtils;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;

public class VanillaMonsterSpawnTask
implements Runnable {
    private Outpost outpost;
    public boolean mobKilled = true;
    public long lastMobSpawn = 0L;
    private long lastFactionPointsGiven = 0L;

    public VanillaMonsterSpawnTask(Outpost outpost) {
        this.outpost = outpost;
        this.lastFactionPointsGiven = System.currentTimeMillis();
    }

    @SneakyThrows
    @Override
    public void run() {
        if (this.outpost.getOutpostTier() == OutpostTier.VANILLA) {
            Faction faction;
            String controlling = this.outpost.getControllingFaction();
            if (controlling != null && System.currentTimeMillis() - this.lastFactionPointsGiven >= TimeUnit.MINUTES.toMillis(12L) && (faction = Factions.getInstance().getByTag(controlling)) != null) {
                this.lastFactionPointsGiven = System.currentTimeMillis();
                //FactionPointUtils.giveFactionPoints((Faction)faction, (Player)null, (String)"outpost_vanilla", (Object)this.outpost);
            }
            if (controlling != null && (this.mobKilled || System.currentTimeMillis() - this.lastMobSpawn > 300000L && this.outpost.getWorld().getPlayers().size() > 0)) {
                if (this.outpost.getWorld().getLivingEntities().size() > 100) {
                    return;
                }
                this.outpost.broadcastOutpostMessage("&c&l(!) &cMonsters are attacking the &nVanilla Outpost!");
                this.outpost.broadcastOutpostMessage("&7They drop potions and vanilla enchantment books!");
                int i = new Random().nextInt(3) + 3;
                this.mobKilled = false;
                this.lastMobSpawn = System.currentTimeMillis();
            }
        }
    }
}

