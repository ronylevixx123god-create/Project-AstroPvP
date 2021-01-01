/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.massivecraft.factions.Faction
 *  org.bukkit.ChatColor
 */
package com.conaxgames.factions.boosters.tasks;

import com.conaxgames.factions.boosters.enums.BoosterType;
import com.conaxgames.factions.boosters.struct.ActiveBooster;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import java.util.Map;

import com.massivecraft.factions.P;
import org.bukkit.ChatColor;

public class BoosterTask
implements Runnable {
    @Override
    public void run() {
        P.getP().getBoosterManager().getFactionBoosters().forEach((fId, boosters) -> {
            for (Map.Entry booster : boosters.entrySet()) {
                ActiveBooster boost = (ActiveBooster)booster.getValue();
                if (boost.getSecondsElapsed() >= boost.getMaxSeconds()) {
                    boosters.remove(booster.getKey());
                    Faction faction = FactionUtils.getFactionById((String)fId);
                    if (faction != null) {
                        faction.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + boost.getMultiplier() + "x " + (Object)ChatColor.RED + ((BoosterType)((Object)((Object)booster.getKey()))).getName() + " Booster from " + (Object)ChatColor.RED + ChatColor.BOLD.toString() + boost.getWhoApplied() + (Object)ChatColor.RED + " has expired.");
                    }
                    if (!boosters.isEmpty()) continue;
                    P.getP().getBoosterManager().getFactionBoosters().remove(fId);
                    P.getP().getBoosterManager().saveActiveBoosters();
                    continue;
                }
                boost.setSecondsElapsed(boost.getSecondsElapsed() + 1);
            }
        });
    }
}

