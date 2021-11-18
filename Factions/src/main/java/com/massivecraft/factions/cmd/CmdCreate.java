/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.massivecraft.factions.cmd;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.massivecraft.factions.*;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class CmdCreate
        extends FCommand {
    protected static final List<String> badTags = Lists.newArrayList("safe");

    public CmdCreate() {
        this.aliases.add("create");
        this.requiredArgs.add("faction tag");
        this.permission = Permission.CREATE.node;
        this.disableOnLock = true;
        this.senderMustBePlayer = true;
        this.senderMustBeMember = false;
        this.senderMustBeModerator = false;
        this.senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        String tag = this.argAsString(0);
        if (this.fme.hasFaction()) {
            this.msg("<b>You must leave your current faction first.");
            return;
        }
        if (Factions.i.isTagTaken(tag)) {
            this.msg("<b>That tag is already in use.");
            return;
        }
        for (String s : badTags) {
            if (!tag.toLowerCase().contains(s)) continue;
            this.msg("<b>That faction tag cannot be used.");
            return;
        }
        ArrayList<String> tagValidationErrors = Factions.validateTag(tag);
        if (tagValidationErrors.size() > 0) {
            this.sendMessage(tagValidationErrors);
            return;
        }
        if (!this.canAffordCommand(Conf.econCostCreate, "to create a new faction")) {
            return;
        }
        if (this.fme.getPlayer().hasMetadata("lastFactionCreate") && System.currentTimeMillis() - this.fme.getPlayer().getMetadata("lastFactionCreate").get(0).asLong() < 30000L) {
            this.fme.getPlayer().sendMessage(ChatColor.RED + "* You can only use /f create once per 30 seconds.");
            return;
        }
        FactionCreateEvent createEvent = new FactionCreateEvent(this.me, tag);
        Bukkit.getServer().getPluginManager().callEvent(createEvent);
        if (createEvent.isCancelled()) {
            return;
        }
        if (!this.payForCommand(Conf.econCostCreate, "to create a new faction", "for creating a new faction")) {
            return;
        }
        Faction faction = Factions.i.create();
        if (faction == null) {
            this.msg("<b>There was an internal error while trying to create your faction. Please try again.");
            return;
        }
        this.fme.getPlayer().setMetadata("lastFactionCreate", new FixedMetadataValue(P.getP(), System.currentTimeMillis()));
        faction.setTag(tag);
        FPlayerJoinEvent joinEvent = new FPlayerJoinEvent(FPlayers.i.get(this.me), faction, FPlayerJoinEvent.PlayerJoinReason.CREATE);
        Bukkit.getServer().getPluginManager().callEvent(joinEvent);
        this.fme.setRole(Role.ADMIN);
        this.fme.setFaction(faction);
        this.msg("");
        this.msg(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + " >>> " + ChatColor.AQUA + ChatColor.BOLD + "YOUR FACTION HAS BEEN CREATED" + ChatColor.DARK_AQUA + ChatColor.BOLD + " <<<");
        this.msg(ChatColor.DARK_AQUA + "You are now the proud leader of " + ChatColor.AQUA + ChatColor.UNDERLINE + tag);
        this.msg("");
        this.msg(ChatColor.WHITE + "Use " + ChatColor.UNDERLINE + "/f access" + CC.White + " and " + CC.WhiteU + "/f perm" + CC.White + " to edit claim access.");
        this.msg(ChatColor.WHITE + "Use " + ChatColor.UNDERLINE + "/f audit" + ChatColor.WHITE + " to view recent faction activity.");
        this.msg(ChatColor.WHITE + "Use " + ChatColor.UNDERLINE + "/f help" + ChatColor.WHITE + " to review all faction commands.");
        this.msg("");
        if (Conf.bankEnabled) {
            faction.money = 0.0;
            Econ.clearFactionBankIfDoesntExist(faction.getAccountId());
        }
        if (Conf.broadcastFactionCreation) {
            for (FPlayer follower : FPlayers.i.getOnline()) {
                follower.msg("%s<i> created a new faction %s", this.fme.describeToAsync(follower, true), faction.getTag(follower));
            }
        }
        if (Conf.logFactionCreate) {
            P.p.log(this.fme.getNameAsync() + " created a new faction: " + tag);
        }
    }
}

