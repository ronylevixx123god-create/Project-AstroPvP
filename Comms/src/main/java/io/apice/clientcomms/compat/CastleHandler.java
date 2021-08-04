package io.apice.clientcomms.compat;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import io.apice.clientcomms.ClientComms;
import io.apice.clientcomms.OCMCMessage;
import io.apice.clientcomms.OCMCUser;
import io.apice.clientcomms.ServerType;
import io.apice.clientcomms.compat.ClientHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatTagEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CastleHandler
extends ClientHandler {
    private static LinkedList<Faction> cachedFactionList;
    private static long lastSort;
    private DecimalFormat format = new DecimalFormat("#,###");

    public CastleHandler(ServerType serverType) {
        super(serverType);
        this.channels.put("getfactioninfo", this::handleFactionInfo);
        this.channels.put("chunkdata", this::handleChunkData);
        this.channels.put("claimcoord", this::handleClaimCoord);
        this.channels.put("getcooldown", this::handleCooldown);
        this.channels.put("getfactionlist", this::handleFactionsList);
        this.channels.put("getplayerfaction", this::handlePlayerFaction);
        this.channels.put("getworldborder", this::handleWorldBorder);
        this.channels.put("pingallies", this::handlePingAllies);
    }

    protected void handleFactionInfo(final Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        if (this.isRateLimited(player, incoming, 5000L)) {
            return;
        }
        String requested = (String)incoming.getData().get("name");
        if (requested.startsWith("\u00a7")) {
            requested = requested.substring(2);
        }
        final boolean formatted = incoming.getData().containsKey("formatted") ? (Boolean)incoming.getData().get("formatted") : false;
        final FPlayer mPlayer = FPlayers.i.get(player);
        final Faction faction = requested == null ? mPlayer.getFaction() : Factions.i.getByTag(requested);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)ClientComms.getInstance(), () -> {
            HashMap<String, Object> data = new HashMap<String, Object>();
            if (faction != null) {
                data.put("uuid", faction.getId());
                data.put("name", formatted ? faction.getTag(mPlayer) : faction.getTag());
                data.put("land_power_maxpower", faction.getLandRounded() + "," + faction.getPower() + "," + faction.getPowerMax());
                HashMap<String, Object> relationNames = new HashMap<String, Object>();
                for (Faction otherFaction : Factions.i.get()) {
                    Relation relation;
                    if (otherFaction == faction || otherFaction.getId().equals(faction.getId()) || (relation = faction.getRelationTo((RelationParticipator)otherFaction)) == null || relation == Relation.NEUTRAL) continue;
                    List factionNames = (List) relationNames.getOrDefault(relation.toString(), new ArrayList());
                    factionNames.add(otherFaction.getTag());
                    relationNames.put(relation.toString(), factionNames);
                }
                data.put("relations", relationNames);
                ArrayList<String> followerNamesOnline = new ArrayList<String>();
                ArrayList<String> followerNamesOffline = new ArrayList<String>();
                Set<FPlayer> followers = faction.getFPlayers();
                for (FPlayer follower : followers) {
                    if (follower.isOnlineAndVisibleTo(mPlayer.getPlayer())) {
                        followerNamesOnline.add(formatted ? (Object)follower.getRole() + "," + follower.getTitle() + "," + follower.getNameAsync() : follower.getNameAsync());
                        continue;
                    }
                    if (!faction.isNormal()) continue;
                    followerNamesOffline.add(formatted ? (Object)follower.getRole() + "," + follower.getTitle() + "," + follower.getNameAsync() : follower.getNameAsync());
                }
                data.put("followers_online", followerNamesOnline);
                data.put("followers_offline", followerNamesOffline);
//                    TopFaction tf = FactionsTop.get().getTopManager().getTopFaction(faction.getId());
//                    if (tf != null) {
//                        data.put("wealth", tf.getStoredFaction().getTotalWorth());
//                        data.put("spawnerwealth", tf.getStoredFaction().getTotalSpawnerWorth());
//                        data.put("rank", tf.getRank());
//                    }
                data.put("status", 0);
            } else {
                data.put("status", 1);
            }
            OCMCMessage outgoing = new OCMCMessage("getfactioninfo", data);
            ClientComms.sendMessage(player, outgoing);
        });
    }

    protected void handleChunkData(Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        if (incoming.getData().containsKey("bulk")) {
            this.sendAllChunkData(player);
        } else {
            Faction factionAt;
            FPlayer mPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            int x = (Integer)incoming.getData().get("x");
            int z = (Integer)incoming.getData().get("z");
            if (Math.abs(player.getLocation().getChunk().getX() - x) <= 24 && Math.abs(player.getLocation().getChunk().getZ() - z) <= 24 && (factionAt = Board.getFactionAt((FLocation)new FLocation(player.getWorld().getName(), x, z))) != null && !factionAt.isNone()) {
                this.sendChunkData(player, x, z, factionAt.getTag(), factionAt.getId(), factionAt.getColorTo((RelationParticipator)mPlayer));
            }
        }
    }

    protected void handleClaimCoord(Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        FPlayer mPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        int x = (Integer)incoming.getData().get("x");
        int z = (Integer)incoming.getData().get("z");
        if (Math.abs(player.getLocation().getChunk().getX() - x) <= 24 && Math.abs(player.getLocation().getChunk().getZ() - z) <= 24) {
            Faction newFaction = mPlayer.getFaction();
            if (newFaction == null) {
                this.sendNotification(player, "You are not in a faction!", 5000);
                return;
            }
            if (newFaction.isNormal() && mPlayer.getRole().value < Role.MODERATOR.value) {
                this.sendNotification(player, "Your faction does not allow you to claim!", 5000);
                return;
            }
            if (Math.abs(mPlayer.getPlayer().getLocation().getChunk().getX() - x) > 20 || Math.abs(mPlayer.getPlayer().getLocation().getChunk().getZ() - z) > 20) {
                this.sendNotification(player, "You cannot claim so far away from your current position!", 5000);
                return;
            }
            if (!mPlayer.attemptClaimAsync(newFaction, new Location(player.getWorld(), (double)(x << 4), 65.0, (double)(z << 4)), false)) {
                this.sendNotification(player, "You are not able to claim there!", 5000);
            }
        } else {
            this.sendNotification(player, "You cannot claim so far away from your current position!", 5000);
        }
    }

    protected void handleCooldown(Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        String name = (String)incoming.getData().get("name");
        this.sendCooldown(player, name);
    }

    @Override
    protected void onUserRegister(OCMCUser user) {
        super.onUserRegister(user);
        Player player = user.getPlayer();
        this.sendWorldName(player, player.getWorld().getName());
        this.sendAllChunkData(player);
    }

    protected void handleFactionsList(final Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        if (this.isRateLimited(player, incoming, 5000L)) {
            return;
        }
        final FPlayer mPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)ClientComms.getInstance(), new Runnable(){

            @Override
            public void run() {
                ArrayList<Faction> factionList;
                HashMap<String, Object> data = new HashMap<String, Object>();
                HashMap factionsList = new HashMap();
                if (System.currentTimeMillis() - lastSort > 300000L) {
                    Bukkit.getLogger().info("[Factions] Updating /f list cachedFactionList object...");
                    factionList = new ArrayList(Factions.i.get());
                    factionList.remove((Object)Factions.i.getNone());
                    factionList.remove((Object)Factions.i.getSafeZone());
                    factionList.remove((Object)Factions.i.getWarZone());
                    Collections.sort(factionList, (f1, f2) -> {
                        int f2Size;
                        int f1Size = f1.getFPlayers().size();
                        return f1Size < (f2Size = f2.getFPlayers().size()) ? 1 : (f1Size > f2Size ? -1 : 0);
                    });
                    Collections.sort(factionList, (f1, f2) -> {
                        int f2Size;
                        int f1Size = f1.getFPlayersWhereOnline(true).size();
                        return f1Size < (f2Size = f2.getFPlayersWhereOnline(true).size()) ? 1 : (f1Size > f2Size ? -1 : 0);
                    });
                    factionList.add(0, Factions.i.getNone());
                    cachedFactionList = new LinkedList(factionList);
                    lastSort = System.currentTimeMillis();
                } else {
                    factionList = new ArrayList<Faction>(cachedFactionList);
                }
                int count = 0;
                for (Faction faction : factionList) {
//                    TopFaction tf = FactionsTop.get().getTopManager().getTopFaction(faction.getId());
                    HashMap<String, Object> factionMap = new HashMap<String, Object>();
                    factionMap.put("uuid", faction.getId());
                    factionMap.put("leader", faction.getFPlayersWhereRole(Role.ADMIN).size() != 0 ? ((FPlayer)faction.getFPlayersWhereRole(Role.ADMIN).get(0)).getId() : null);
                    factionMap.put("leadername", faction.getFPlayersWhereRole(Role.ADMIN).size() != 0 ? ((FPlayer)faction.getFPlayersWhereRole(Role.ADMIN).get(0)).getNameAsync() : null);
                    factionMap.put("name", faction.getTag(mPlayer));
                    factionMap.put("size", faction.getFPlayers().size());
                    factionMap.put("land", faction.getLandRounded());
                    factionMap.put("power", faction.getPowerRounded());
                    factionMap.put("powermaxrounded", faction.getPowerMaxRounded());
                    factionMap.put("balance", faction.getFactionBalance());
//                    if (tf != null) {
//                        factionMap.put("wealth", tf.getStoredFaction().getTotalWorth());
//                    }
                    factionsList.put(faction.getId(), factionMap);
                    if (++count < 100) continue;
                    data.put("factions", factionsList);
                    data.put("status", 0);
                    OCMCMessage message = new OCMCMessage("getfactionslist", data);
                    ClientComms.sendMessage(player, message);
                    data = new HashMap();
                    factionsList = new HashMap();
                    count = 0;
                }
                if (count > 0) {
                    data.put("factions", factionsList);
                    data.put("status", 0);
                    OCMCMessage message = new OCMCMessage("getfactionslist", data);
                    ClientComms.sendMessage(player, message);
                }
            }
        });
    }

    protected void handlePingAllies(Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        if (this.isRateLimited(player, incoming, 10000L)) {
            this.sendNotification(player, "You're doing that too fast!", 5000);
            return;
        }
        FPlayer mPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        if (mPlayer.getFaction() == null || mPlayer.getFaction().isNone()) {
            return;
        }
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", player.getName());
        data.put("x", new Double((Double)incoming.getData().get("x")));
        data.put("y", new Double((Double)incoming.getData().get("y")));
        data.put("z", new Double((Double)incoming.getData().get("z")));
        data.put("dimension", new Integer((Integer)incoming.getData().get("dimension")));
        data.put("status", 0);
        if (ClientComms.getInstance().getServer().getWorlds().size() <= (Integer)data.get("dimension")) {
            return;
        }
        Location location = new Location((World)ClientComms.getInstance().getServer().getWorlds().get((Integer)data.get("dimension")), ((Double)data.get("x")).doubleValue(), ((Double)data.get("y")).doubleValue(), ((Double)data.get("z")).doubleValue());
        boolean allies = (Boolean)incoming.getData().get("allies");
        if (allies) {
            for (FPlayer fplayer : FPlayers.i.getOnline()) {
                Player pl;
                if (fplayer.getRelationTo((RelationParticipator)mPlayer.getFaction()).value < Relation.ALLY.value || (pl = fplayer.getPlayer()) == null) continue;
                data.put("allies", new Boolean((Boolean)incoming.getData().get("allies")));
                OCMCMessage ocmcMessage = new OCMCMessage("pingallies", data);
                ClientComms.sendMessage(pl, ocmcMessage);
                double distance = location.getWorld().equals((Object)pl.getWorld()) ? location.distance(pl.getLocation()) : -1.0;
                fplayer.sendMessage(this.getMessage(location, fplayer.getFaction(), mPlayer, distance));
            }
        } else {
            data.put("allies", new Boolean((Boolean)incoming.getData().get("allies")));
            OCMCMessage ocmcMessage = new OCMCMessage("pingallies", data);
            for (Player ally : mPlayer.getFaction().getOnlinePlayers()) {
                ClientComms.sendMessage(ally, ocmcMessage);
                double distance = location.getWorld().equals((Object)ally.getWorld()) ? location.distance(ally.getLocation()) : -1.0;
                FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)ally);
                fplayer.sendMessage(this.getMessage(location, fplayer.getFaction(), mPlayer, distance));
            }
        }
    }

    private String getMessage(Location location, Faction faction, FPlayer fme, double distance) {
        ChatColor colorTo = faction.getColorTo((RelationParticipator)fme);
        return (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + (Object)colorTo + fme.getNameAsync() + "'s" + (Object)ChatColor.GREEN + " ping coordinates: " + (Object)ChatColor.WHITE + ChatColor.BOLD.toString() + location.getBlockX() + "x " + location.getBlockY() + "y " + location.getBlockZ() + "z" + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + (distance > 0.0 ? " (" + (Object)ChatColor.GREEN + this.format.format(distance) + "m" + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + ")" : "") + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + " (!)";
    }

    protected void handlePlayerFaction(Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        HashMap<String, Object> data = new HashMap<String, Object>();
        UUID uuid = UUID.fromString((String)incoming.getData().get("uuid"));
        Player otherPlayer = Bukkit.getPlayer((UUID)uuid);
        if (otherPlayer == null) {
            return;
        }
        FPlayer mPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)otherPlayer);
        data.put("uuid", uuid.toString());
        data.put("factionid", mPlayer.getFaction().getId());
        data.put("status", 0);
        ClientComms.sendMessage(player, new OCMCMessage("getplayerfaction", data));
    }

    @Override
    protected void handleWorldBorder(Player player, OCMCMessage incoming) {
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            return;
        }
        this.sendWorldBorder(player);
    }

    public void sendWorldBorder(Player player) {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("WorldBorder")) {
            return;
        }
        WorldBorder worldBorder = WorldBorder.plugin;
        BorderData border = worldBorder.getWorldBorder(player.getWorld().getName());
        if (border != null) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("radius", Math.max(border.getRadiusX(), border.getRadiusZ()));
            data.put("radiusX", border.getRadiusX());
            data.put("radiusZ", border.getRadiusZ());
            data.put("centerX", (int)border.getX());
            data.put("centerZ", (int)border.getZ());
            data.put("status", 0);
            ClientComms.sendMessage(player, new OCMCMessage("getworldborder", data));
        } else {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("status", 0);
            ClientComms.sendMessage(player, new OCMCMessage("getworldborder", data));
        }
    }

    public void sendWorldName(Player player, String worldName) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        if (ClientComms.getServerName() != null) {
            data.put("worldname", "factions_" + ClientComms.getServerName() + "_" + worldName);
        } else {
            data.put("worldname", "factions_" + worldName);
        }
        data.put("status", 0);
        ClientComms.sendMessage(player, new OCMCMessage("setworldname", data));
    }

    public void sendChunkData(Player player, int x, int z, String name, String UUID2, ChatColor color) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        HashMap<String, String> subData = new HashMap<String, String>();
        subData.put("uuid", UUID2);
        subData.put("name", name);
        subData.put("color", color.toString());
        data.put(x + "," + z, subData);
        data.put("status", 0);
        OCMCMessage message = new OCMCMessage("chunkdata", data);
        ClientComms.sendMessage(player, message);
    }

    public void sendAllChunkData(Player player) {
        FPlayer mPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        HashMap<String, Object> data = new HashMap<String, Object>();
        for (int x = player.getLocation().getChunk().getX() - 8; x <= player.getLocation().getChunk().getX() + 8; ++x) {
            for (int z = player.getLocation().getChunk().getZ() - 8; z <= player.getLocation().getChunk().getZ() + 8; ++z) {
                HashMap<String, String> subData = new HashMap<String, String>();
                Faction factionAt = Board.getFactionAt((FLocation)new FLocation(player.getWorld().getName(), x, z));
                subData.put("uuid", factionAt.getId());
                subData.put("name", factionAt.getTag());
                subData.put("color", factionAt.getColorTo((RelationParticipator)mPlayer).toString());
                data.put(x + "," + z, subData);
            }
        }
        data.put("status", 0);
        OCMCMessage message = new OCMCMessage("chunkdata", data);
        ClientComms.sendMessage(player, message);
    }

    public void sendCooldown(Player player, String name) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("status", 0);
        switch (name) {
            case "enderpearl": {
                int duration = EnderpearlCooldown.getEnderpearlCooldownLeft((Player)player);
                data.put("name", "Enderpearl");
                data.put("duration", duration);
                data.put("max", Arkkit.get().getConfig().getInt("settings.enderpearlCooldown.cooldown"));
                data.put("itemid", Material.ENDER_PEARL.getId());
                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
                break;
            }
            case "combattag": {
                int duration = (int)CombatLog.getTimeLeftInCombat((Player)player);
                data.put("name", "Combat Tag");
                data.put("duration", duration);
                data.put("max", (int)Arkkit.get().getConfig().getLong("settings.combat_log.combat_duration"));
                data.put("itemid", Material.GOLD_SWORD.getId());
                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
                break;
            }
//            case "Serrated_Strikes": {
//                int duration = 0;
//                McMMOPlayer mcMMOPlayer = UserManager.getPlayer((Player)player);
//                if (mcMMOPlayer != null) {
//                    duration = mcMMOPlayer.calculateTimeRemaining(AbilityType.SERRATED_STRIKES) * 1000;
//                }
//                data.put("name", name);
//                data.put("duration", duration);
//                data.put("max", PerksUtils.handleCooldownPerks((Player)player, (int)AbilityType.SERRATED_STRIKES.getCooldown()) * 1000);
//                data.put("itemid", Material.DIAMOND_SWORD.getId());
//                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
//                break;
//            }
//            case "Skull_Splitter": {
//                int duration = 0;
//                McMMOPlayer mcMMOPlayer = UserManager.getPlayer((Player)player);
//                if (mcMMOPlayer != null) {
//                    duration = mcMMOPlayer.calculateTimeRemaining(AbilityType.SKULL_SPLITTER) * 1000;
//                }
//                data.put("name", name);
//                data.put("duration", duration);
//                data.put("max", PerksUtils.handleCooldownPerks((Player)player, (int)AbilityType.SKULL_SPLITTER.getCooldown()) * 1000);
//                data.put("itemid", Material.DIAMOND_AXE.getId());
//                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
//                break;
//            }
//            case "Super_Breaker": {
//                int duration = 0;
//                McMMOPlayer mcMMOPlayer = UserManager.getPlayer((Player)player);
//                if (mcMMOPlayer != null) {
//                    duration = mcMMOPlayer.calculateTimeRemaining(AbilityType.SUPER_BREAKER) * 1000;
//                }
//                data.put("name", name);
//                data.put("duration", duration);
//                data.put("max", PerksUtils.handleCooldownPerks((Player)player, (int)AbilityType.SUPER_BREAKER.getCooldown()) * 1000);
//                data.put("itemid", Material.DIAMOND_PICKAXE.getId());
//                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
//                break;
//            }
//            case "Giga_Drill_Breaker": {
//                int duration = 0;
//                McMMOPlayer mcMMOPlayer = UserManager.getPlayer((Player)player);
//                if (mcMMOPlayer != null) {
//                    duration = mcMMOPlayer.calculateTimeRemaining(AbilityType.GIGA_DRILL_BREAKER) * 1000;
//                }
//                data.put("name", name);
//                data.put("duration", duration);
//                data.put("max", PerksUtils.handleCooldownPerks((Player)player, (int)AbilityType.GIGA_DRILL_BREAKER.getCooldown()) * 1000);
//                data.put("itemid", Material.DIAMOND_SPADE.getId());
//                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
//                break;
//            }
//            case "Tree_Feller": {
//                int duration = 0;
//                McMMOPlayer mcMMOPlayer = UserManager.getPlayer((Player)player);
//                if (mcMMOPlayer != null) {
//                    duration = mcMMOPlayer.calculateTimeRemaining(AbilityType.TREE_FELLER) * 1000;
//                }
//                data.put("name", name);
//                data.put("duration", duration);
//                data.put("max", PerksUtils.handleCooldownPerks((Player)player, (int)AbilityType.TREE_FELLER.getCooldown()) * 1000);
//                data.put("itemid", Material.LOG.getId());
//                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
//                break;
//            }
//            case "Green_Terra": {
//                int duration = 0;
//                McMMOPlayer mcMMOPlayer = UserManager.getPlayer((Player)player);
//                if (mcMMOPlayer != null) {
//                    duration = mcMMOPlayer.calculateTimeRemaining(AbilityType.GREEN_TERRA) * 1000;
//                }
//                data.put("name", name);
//                data.put("duration", duration);
//                data.put("max", PerksUtils.handleCooldownPerks((Player)player, (int)AbilityType.GREEN_TERRA.getCooldown()) * 1000);
//                data.put("itemid", Material.VINE.getId());
//                ClientComms.sendMessage(player, new OCMCMessage("getcooldown", data));
//                break;
//            }
        }
    }

    @Override
    public void handleTeleport(PlayerTeleportEvent event) {
        if (!event.getFrom().getWorld().equals((Object)event.getTo().getWorld()) || event.getFrom().distanceSquared(event.getTo()) >= 256.0) {
            this.sendWorldName(event.getPlayer(), event.getTo().getWorld().getName());
            this.sendAllChunkData(event.getPlayer());
        }
    }

    @Override
    public void handleWorldChange(PlayerChangedWorldEvent event) {
        this.sendWorldBorder(event.getPlayer());
    }

    @Override
    public void handleRespawn(PlayerRespawnEvent event) {
        this.sendAllChunkData(event.getPlayer());
    }

    @Override
    public void handleJoin(PlayerJoinEvent event) {
        this.sendAllChunkData(event.getPlayer());
        Bukkit.getScheduler().runTaskLater((Plugin)ClientComms.getInstance(), () -> {
            if (!(event.getPlayer() != null && event.getPlayer().isOnline() && ClientComms.getRegisteredPlayers().containsKey((Object)event.getPlayer()) && Bukkit.getPluginManager().isPluginEnabled("WorldBorder"))) {
                return;
            }
            this.sendWorldBorder(event.getPlayer());
        }, 50L);
    }

    public void sendFocusedPlayer(Player player, UUID target) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("target", target != null ? target.toString() : null);
        data.put("status", 0);
        OCMCMessage message = new OCMCMessage("sendfocusedplayer", data);
        ClientComms.sendMessage(player, message);
    }

    @EventHandler
    public void onCombatTag(CombatTagEvent e) {
        Player pTagged = e.getPlayer();
        if (!ClientComms.getRegisteredPlayers().containsKey((Object)pTagged)) {
            return;
        }
        if (pTagged.hasMetadata("lastCombatTagUpdatePacket") && System.currentTimeMillis() < ((MetadataValue)pTagged.getMetadata("lastCombatTagUpdatePacket").get(0)).asLong() + 1000L) {
            return;
        }
        this.sendCooldown(pTagged, "combattag");
        pTagged.setMetadata("lastCombatTagUpdatePacket", (MetadataValue)new FixedMetadataValue((Plugin)ClientComms.getInstance(), (Object)System.currentTimeMillis()));
    }

//    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
//    public void onMcMMOPlayerAbilityDeactivateEvent(McMMOPlayerAbilityDeactivateEvent event) {
//        Player player = event.getPlayer();
//        if (ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
//            this.sendCooldown(player, event.getAbility().toString());
//        }
//    }
//
//    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
//    public void onMcMMOPlayerAbilityDeactivateEvent(McMMOPlayerAbilityActivateEvent event) {
//        Player player = event.getPlayer();
//        if (ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
//            this.sendCooldown(player, event.getAbility().toString());
//        }
//    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onLandClaim(LandClaimEvent event) {
        for (Player player : ClientComms.getRegisteredPlayers().keySet()) {
            if (Math.abs((long)(player.getLocation().getBlockX() >> 4) - event.getLocation().getX()) > 8L || Math.abs((long)(player.getLocation().getBlockZ() >> 4) - event.getLocation().getZ()) > 8L) continue;
            FPlayer fPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            this.sendChunkData(player, (int)event.getLocation().getX(), (int)event.getLocation().getZ(), event.getFaction().getTag(), event.getFaction().getId(), fPlayer.getColorTo((RelationParticipator)event.getFaction()));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onLandUnclaim(LandUnclaimEvent event) {
        for (Player player : ClientComms.getRegisteredPlayers().keySet()) {
            if (Math.abs((long)(player.getLocation().getBlockX() >> 4) - event.getLocation().getX()) > 8L || Math.abs((long)(player.getLocation().getBlockZ() >> 4) - event.getLocation().getZ()) > 8L) continue;
            FPlayer fPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            this.sendChunkData(player, (int)event.getLocation().getX(), (int)event.getLocation().getZ(), ChatColor.stripColor((String)Factions.i.getNone().getTag()), Factions.i.getNone().getId(), fPlayer.getColorTo((RelationParticipator)Factions.i.getNone()));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onLandUnclaimAllEvent(LandUnclaimAllEvent event) {
        for (Map.Entry entry : Board.flocationIds.entrySet()) {
            if (!((String)entry.getValue()).equals(event.getFactionId())) continue;
            for (Player player : ClientComms.getRegisteredPlayers().keySet()) {
                FLocation fl = (FLocation)entry.getKey();
                if (Math.abs((long)(player.getLocation().getBlockX() >> 4) - (fl.getX() >> 4)) > 8L || Math.abs((long)(player.getLocation().getBlockZ() >> 4) - (fl.getZ() >> 4)) > 8L) continue;
                FPlayer fPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                this.sendChunkData(player, (int)fl.getX(), (int)fl.getZ(), ChatColor.stripColor((String)Factions.i.getNone().getTag()), Factions.i.getNone().getId(), fPlayer.getColorTo((RelationParticipator)Factions.i.getNone()));
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPearlLand(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }
        Bukkit.getScheduler().runTask((Plugin)ClientComms.getInstance(), () -> {
            Player player = event.getPlayer();
            if (ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
                this.sendCooldown(player, "enderpearl");
            }
        });
    }

    @Override
    protected void handlePermission(Player player, OCMCMessage incoming) {
        String permission = (String)incoming.getData().get("permission");
        HashMap<String, Object> data = new HashMap<String, Object>();
        switch (permission) {
            case "printer": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("clientcomms.node.printer"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "freeroam": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("clientcomms.node.freefroam") || player.hasPermission("clientcomms.node.freeroam"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "feed": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("essentials.feed"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "fastplace": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("clientcomms.node.fastplace"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "hideplants": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("clientcomms.node.hideplants"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "breadcrumbs": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("clientcomms.node.breadcrumbs"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
            }
        }
    }

    static {
        lastSort = 0L;
    }
}

