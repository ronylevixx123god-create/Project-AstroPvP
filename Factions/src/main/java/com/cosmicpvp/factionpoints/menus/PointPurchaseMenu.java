/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EconomyUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.P
 *  com.massivecraft.factions.struct.FLogType
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 */
package com.cosmicpvp.factionpoints.menus;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import com.cosmicpvp.factionpoints.struct.PointData;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.FLogType;
import java.util.Calendar;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PointPurchaseMenu
extends CosmicGUI {
    public static int COST_PER_POINT = 1000000;
    public static int POINTS_PER_DAY = 400;
    private Faction faction;
    private static long nextResetTime;
    private static Calendar calendar;

    public PointPurchaseMenu(Player player, Faction faction) {
        super(player, "Purchase Faction Points", 9);
        this.faction = faction;
    }

    public static void calculateResetTime() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(6, 1);
        nextResetTime = calendar.getTimeInMillis();
    }

    public void redraw() {
        if (this.faction == null || !this.faction.attached()) {
            this.player.closeInventory();
            return;
        }
        long points = 0L;
        long pointsUnlockedToday = 0L;
        PointData data2 = FactionsPointsAPI.getPointData(this.faction, false);
        if (data2 != null) {
            points = data2.getPoints();
            pointsUnlockedToday = data2.getPointsPurchasedToday();
        }
        this.setItem(3, this.createPurchaseItem(1, points, pointsUnlockedToday));
        this.setItem(4, this.createPurchaseItem(10, points, pointsUnlockedToday));
        this.setItem(5, this.createPurchaseItem(100, points, pointsUnlockedToday));
    }

    public static long getNextResetTime() {
        if (nextResetTime < System.currentTimeMillis()) {
            PointPurchaseMenu.calculateResetTime();
        }
        return nextResetTime;
    }

    public InventoryItem createPurchaseItem(int amount, long currentPoints2, long pointsPurchasedToday) {
        String number = NumberUtils.formatSeconds((double)amount).intern();
        double cost = amount * COST_PER_POINT;
        return new InventoryItem(new ItemBuilder(Material.PAPER, CC.GreenB + number + "x Faction Points", new String[]{CC.Gray + "Click to purchase " + CC.Green + number + CC.Gray + "x Faction Points", CC.Gray + "for " + CC.GreenB + "$" + CC.Green + NumberUtils.formatMoney((double)cost) + CC.Gray + "!", "", CC.GreenB + "Current Faction Points", CC.White + " " + NumberUtils.formatSeconds((double)currentPoints2), "", CC.RedB + "Daily Purchases", CC.White + " " + NumberUtils.formatSeconds((double)pointsPurchasedToday) + " / " + NumberUtils.formatSeconds((double)POINTS_PER_DAY), "", CC.Red + "Daily limit resets in " + TimeUtils.formatFutureTime((long)PointPurchaseMenu.getNextResetTime()), "", CC.GreenB + "What are Faction Points?", CC.Gray + "Faction points are used in /f top", CC.Gray + "to determine the #1 Faction!"}).build()).click(() -> {
            if (!EconomyUtils.hasBalance((OfflinePlayer)this.player, (double)cost)) {
                this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot afford " + amount + "x Faction Points for " + CC.RedB + "$" + CC.Red + NumberUtils.formatMoney((double)cost) + "!");
                return;
            }
            PointData data2 = FactionsPointsAPI.getPointData(this.faction, true);
            if (data2 == null) {
                this.closeWithDelay();
                this.player.sendMessage(CC.Red + "Unable to find proper point data!");
                CosmicFactionPoints.log("Unable to create or find point data for " + this.faction.getTag() + "!");
                return;
            }
            if (data2.getPointsPurchasedToday() >= POINTS_PER_DAY) {
                this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have reached your daily limit of purchasable Faction Points!");
                this.player.sendMessage(CC.Gray + "Daily Limit Resets in " + CC.GrayU + TimeUtils.formatFutureTime((long)PointPurchaseMenu.getNextResetTime()) + CC.Gray + "!");
                this.closeWithDelay();
                return;
            }
            if (data2.getPointsPurchasedToday() + amount > POINTS_PER_DAY) {
                this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have almost reached your daily limit of purchasable Faction Points and cannot purchase this amount!");
                this.player.sendMessage(CC.Gray + "Daily Limit Resets in " + CC.GrayU + TimeUtils.formatFutureTime((long)PointPurchaseMenu.getNextResetTime()) + CC.Gray + "!");
                this.closeWithDelay();
                return;
            }
            EconomyUtils.withdrawBalance((OfflinePlayer)this.player, (double)cost);
            Long newPoints = data2.addPointsPurchased(amount, this.faction);
            this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
            this.player.sendMessage(CC.RedB + "- $" + CC.Red + NumberUtils.formatMoney((double)cost));
            try {
                P.p.getFlogManager().log(this.faction, FLogType.F_POINTS, new String[]{this.player.getName(), CC.GreenB + "PURCHASED", String.valueOf(amount) + " Faction Points " + CC.Gray + "for " + CC.GreenB + "$" + CC.Green + NumberUtils.formatMoney((double)cost)});
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.faction.sendMessage(CC.GreenB + "(!) " + CC.Green + this.player.getName() + " has purchased " + NumberUtils.formatSeconds((double)amount) + CC.Green + " Faction Point(s)! (" + NumberUtils.formatSeconds((double)newPoints.longValue()) + ")");
            this.redraw();
        });
    }

    static {
        calendar = Calendar.getInstance();
        PointPurchaseMenu.calculateResetTime();
    }
}

