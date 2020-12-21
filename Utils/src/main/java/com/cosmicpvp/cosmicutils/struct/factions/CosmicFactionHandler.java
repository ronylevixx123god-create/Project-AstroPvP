/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.integration.Econ
 *  com.massivecraft.factions.struct.Relation
 *  com.massivecraft.factions.zcore.persist.Entity
 *  com.massivecraft.factions.zcore.persist.PlayerEntity
 *  com.massivecraft.factions.zcore.persist.PlayerEntityCollection
 */
package com.cosmicpvp.cosmicutils.struct.factions;

import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.struct.factions.FactionHandler;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.zcore.persist.Entity;
import com.massivecraft.factions.zcore.persist.PlayerEntity;
import com.massivecraft.factions.zcore.persist.PlayerEntityCollection;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CosmicFactionHandler
extends FactionHandler {
    private Factions instance;
    private FPlayers fPlayers;
    private Board board;
    private Field focusPlayerField;
    private Method entityGetId;
    private Method getPlayerFromFPlayer;

    @Override
    public void initHandler() {
        try {
            Object fPlayers = ReflectionUtils.getObject(FPlayers.class, null, "i");
            this.fPlayers = (FPlayers)fPlayers;
            Object fInstance = ReflectionUtils.getObject(Factions.class, null, "i");
            this.instance = (Factions)fInstance;
            this.focusPlayerField = ReflectionUtils.getField(Faction.class, "focusedPlayer");
            this.entityGetId = ReflectionUtils.getMethod(Entity.class, "getId");
            this.getPlayerFromFPlayer = ReflectionUtils.getMethod(PlayerEntity.class, "getPlayer");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInOwnFaction(Player player) {
        Faction at = Board.getFactionAt((FLocation)new FLocation(player.getLocation()));
        FPlayer fpla = this.getFPlayer(player);
        return at != null && fpla != null && at.getFPlayers().contains((Object)fpla);
    }

    @Override
    public boolean inSameFaction(Player player, Player other) {
        if (player != null && other != null && other.equals(player)) {
            return true;
        }
        FPlayer pl = this.getFPlayer(player);
        FPlayer otherFPlayer = this.getFPlayer(other);
        return pl != null && otherFPlayer != null && pl.hasFaction() && pl.getFaction().getFPlayers().contains((Object)otherFPlayer);
    }

    @Override
    public boolean inInEnemyFaction(Player player, Player other) {
        return this.getRelation(player, other) == Relation.ENEMY;
    }

    private Relation getRelation(Player player, Player other) {
        if (player == other) {
            return Relation.MEMBER;
        }
        FPlayer fPlayer = this.getFPlayer(player);
        FPlayer otherfPlayer = this.getFPlayer(other);
        if (fPlayer == null || otherfPlayer == null) {
            return Relation.NEUTRAL;
        }
        return fPlayer.getRelationTo((RelationParticipator)otherfPlayer);
    }

    @Override
    public boolean inInAllyFaction(Player player, Player other) {
        return this.getRelation(player, other) == Relation.ALLY;
    }

    @Override
    public String getFactionTag(Player seeingMsg, Player checking) {
        FPlayer fplayer = this.getFPlayer(seeingMsg);
        FPlayer other = this.getFPlayer(checking);
        return other.getChatTag(fplayer);
    }

    @Override
    public String getFactionTag(Player seeingMsg) {
        FPlayer fplayer = this.getFPlayer(seeingMsg);
        if (!fplayer.hasFaction()) {
            return null;
        }
        return fplayer.getFaction().getTag();
    }

    @Override
    public String getFactionId(Player seeingMsg) {
        FPlayer fplayer = this.getFPlayer(seeingMsg);
        if (!fplayer.hasFaction()) {
            return null;
        }
        return fplayer.getFaction().getId();
    }

    @Override
    public String getFPlayerName(FPlayer fplayer) {
        return fplayer.getNameAsync();
    }

    @Override
    public String getFPlayerNameAndTag(FPlayer fplayer) {
        return (String)FactionUtils.callFPlayerMethod(fplayer, "getNameAndTagAsync", null);
    }

    @Override
    public Collection<FPlayer> getAllFPlayers(boolean online) {
        return online ? this.fPlayers.getOnline() : this.fPlayers.get();
    }

    @Override
    public Collection<Faction> getAllFactions() {
        return this.instance.get();
    }

    @Override
    public Map<String, Map<String, String>> dumpAsSaveFormat() {
        try {
            Method saveFormat = ReflectionUtils.getMethod(Board.class, "dumpAsSaveFormat");
            return (Map)saveFormat.invoke(null, new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, Map<String, String>>();
        }
    }

    @Override
    public Faction getFactionAt(Location location) {
        return Board.getFactionAt((FLocation)new FLocation(location));
    }

    @Override
    public Faction getFactionAt(FLocation location) {
        return Board.getFactionAt((FLocation)location);
    }

    @Override
    public List<String> getFPlayerIdsByTag(String tag) {
        Faction faction = this.getFactionByTag(tag);
        if (faction == null) {
            return null;
        }
        ArrayList<String> retr = Lists.newArrayList();
        faction.getFPlayers().forEach(mem -> retr.add(mem.getId()));
        return retr;
    }

    @Override
    public String getFactionIdByTag(String tag) {
        Faction faction = this.getFactionByTag(tag);
        if (faction == null) {
            return null;
        }
        return faction.getId();
    }

    @Override
    public FPlayer getFPlayerByName(String name) {
        return this.fPlayers.get(name);
    }

    @Override
    public Player getPlayer(FPlayer player) {
        try {
            return (Player)this.getPlayerFromFPlayer.invoke((Object)player, new Object[0]);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Faction getFaction(FPlayer player, boolean includeWilderness) {
        Faction faction = player.getFaction();
        if (!includeWilderness && faction.isNone()) {
            return null;
        }
        return faction;
    }

    @Override
    public void sendMessage(FPlayer fplayer, String msg) {
        ReflectionUtils.callReflectionMethod((Object)fplayer, PlayerEntity.class, "sendMessage", new ParamBuilder(String.class, msg));
    }

    @Override
    public boolean isFocusedPlayer(Faction faction, String name) {
        try {
            FPlayer focusing = (FPlayer)this.focusPlayerField.get((Object)faction);
            if (focusing == null) {
                return false;
            }
            if (focusing.getNameAsync().equals(name)) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public FPlayer getFocusing(Faction faction) {
        return (FPlayer)ReflectionUtils.getObject(Faction.class, (Object)faction, "focusedPlayer");
    }

    @Override
    public void setFocusing(Faction faction, FPlayer player) {
        ReflectionUtils.setObject((Object)faction, this.focusPlayerField, (Object)player);
    }

    @Override
    public boolean attemptClaim(FPlayer fplayer, Faction faction, Location location, boolean notifyFailure) {
        return fplayer.attemptClaimAsync(faction, location, notifyFailure);
    }

    @Override
    public Class<?> getMainFactionClass() {
        return Faction.class;
    }

    @Override
    public Class<?> getMainFPlayerClass() {
        return FPlayer.class;
    }

    @Override
    public List<String> getFPlayerIdsById(String id) {
        Faction faction = this.getFactionById(id);
        if (faction == null) {
            return null;
        }
        ArrayList<String> retr = Lists.newArrayList();
        faction.getFPlayers().forEach(mem -> retr.add(mem.getId()));
        return retr;
    }

    @Override
    public Faction getFaction(Player player, boolean includeWilderness) {
        FPlayer fplayer = this.getFPlayer(player);
        if (fplayer == null) {
            if (includeWilderness) {
                return this.instance.getNone();
            }
            return null;
        }
        Faction fac = fplayer.getFaction();
        if (!includeWilderness && fac.isNone()) {
            return null;
        }
        return fac;
    }

    @Override
    public Faction getFactionByTag(String tag) {
        return this.instance.getByTag(tag);
    }

    @Override
    public Faction getFactionById(String id) {
        return this.instance.get(id);
    }

    @Override
    public FPlayer getFPlayer(Player player) {
        try {
            Method method = ReflectionUtils.getMethod(PlayerEntityCollection.class, "get", OfflinePlayer.class);
            return (FPlayer)method.invoke((Object)this.fPlayers, player);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getFactionId(Faction faction) {
        return (String)ReflectionUtils.invokeMethod(this.entityGetId, (Object)faction);
    }

    @Override
    public String getFPlayerId(FPlayer fplayer) {
        return (String)ReflectionUtils.invokeMethod(this.entityGetId, (Object)fplayer);
    }

    @Override
    public int getLand(Faction faction) {
        return faction.getLandRounded();
    }

    @Override
    public int getLandMax(Faction faction) {
        return (int)Math.round(this.getPowerMax(faction));
    }

    @Override
    public double getPower(Faction faction) {
        return faction.getPower();
    }

    @Override
    public double getPowerMax(Faction faction) {
        return faction.getPowerMax();
    }

    @Override
    public double getDTR(Faction faction) {
        return 0.0;
    }

    @Override
    public double getDTRMax(Faction faction) {
        return 0.0;
    }

    @Override
    public double getDTRMin(Faction faction) {
        return 0.0;
    }

    @Override
    public double getFactionBalance(Faction faction) {
        return (Double)ReflectionUtils.callReflectionMethod(null, Econ.class, "getFactionBalance", null);
    }

    private FPlayers getFPlayers() {
        return this.fPlayers;
    }
}

