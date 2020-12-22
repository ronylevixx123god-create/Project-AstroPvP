/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.PermissionsAPI
 *  com.compilex.CompilexServerSync.ServerSyncAPI
 *  com.compilex.CompilexServerSync.libs.GameServerState
 *  com.cosmicpvp.CosmicRankQuests.utils.PlayerRankUtils
 *  com.cosmicpvp.cosmicutils.compat.ParamBuilder
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.earth2me.essentials.User
 *  com.gmail.nossr50.skills.mining.MiningManager
 *  com.gmail.nossr50.util.player.UserManager
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.Relation
 *  net.ess3.api.events.NickChangeEvent
 *  net.minecraft.util.org.apache.commons.lang3.text.WordUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerChatTabCompleteEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.event.player.PlayerVelocityEvent
 *  org.bukkit.event.player.VelocityTrigger
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexServerSync.ServerSyncAPI;
import com.compilex.CompilexServerSync.libs.GameServerState;
import com.cosmicpvp.CosmicRankQuests.utils.PlayerRankUtils;
import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.earth2me.essentials.User;
import com.gmail.nossr50.skills.mining.MiningManager;
import com.gmail.nossr50.util.player.UserManager;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import net.ess3.api.events.NickChangeEvent;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

final class SmarterEssentialsListener
implements Listener {
    List<String> blockedMutedCommands;
    boolean factions = false;
    boolean essentials = false;
    private List<String> message_aliases = new ArrayList<String>(Arrays.asList("msg", "message", "tell", "whisper", "m", "emsg", "w", "t", "etell", "ewhisper"));
    private List<String> reply_aliases = new ArrayList<String>(Arrays.asList("reply", "r", "repl"));
    private LinkedList<String> listCommandCache = new LinkedList();
    private long listCommandCacheExpiration = 0L;

    SmarterEssentialsListener() {
        this.blockedMutedCommands = new ArrayList<String>(Arrays.asList("bounty", "f"));
        this.factions = Bukkit.getPluginManager().isPluginEnabled("Factions");
        this.essentials = Bukkit.getPluginManager().isPluginEnabled("Essentials");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("essentials.god")) {
            FPlayer fp;
            if (this.essentials) {
                User u = SmarterEssentials.ess3.getUser(e.getPlayer());
                if (u.isGodModeEnabled()) {
                    u.setGodModeEnabled(false);
                }
                if (u.isSocialSpyEnabled()) {
                    u.setSocialSpyEnabled(false);
                }
            }
            if (this.factions && (fp = FactionUtils.getFPlayer((Player)e.getPlayer())) != null && ((Boolean)FactionUtils.callFPlayerMethod((FPlayer)fp, (String)"isSpyingChat")).booleanValue()) {
                FactionUtils.callFPlayerMethod((FPlayer)fp, (String)"setSpyingChat", (ParamBuilder)new ParamBuilder(Boolean.TYPE, (Object)false));
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().hasPermission("*")) {
            Bukkit.getLogger().info(e.getMessage());
            Bukkit.getLogger().info(e.getFormat());
            e.setFormat(e.getFormat().replace(ChatColor.GRAY.toString(), ChatColor.RED.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.GRAY.toString(), ChatColor.RED.toString()));
            e.setFormat(e.getFormat().replace(ChatColor.WHITE.toString(), ChatColor.RED.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.WHITE.toString(), ChatColor.RED.toString()));
            e.setFormat(e.getFormat().replace(ChatColor.LIGHT_PURPLE.toString(), ChatColor.RED.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.LIGHT_PURPLE.toString(), ChatColor.RED.toString()));
            return;
        }
        if (e.getPlayer().hasPermission("bm.command.ban")) {
            e.setFormat(e.getFormat().replace(ChatColor.GRAY.toString(), ChatColor.AQUA.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.GRAY.toString(), ChatColor.AQUA.toString()));
            e.setFormat(e.getFormat().replace(ChatColor.WHITE.toString(), ChatColor.AQUA.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.WHITE.toString(), ChatColor.AQUA.toString()));
            e.setFormat(e.getFormat().replace(ChatColor.LIGHT_PURPLE.toString(), ChatColor.AQUA.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.LIGHT_PURPLE.toString(), ChatColor.AQUA.toString()));
            return;
        }
        if (e.getPlayer().hasPermission("bm.command.warn")) {
            e.setFormat(e.getFormat().replace(ChatColor.GRAY.toString(), ChatColor.LIGHT_PURPLE.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.GRAY.toString(), ChatColor.LIGHT_PURPLE.toString()));
            e.setFormat(e.getFormat().replace(ChatColor.WHITE.toString(), ChatColor.LIGHT_PURPLE.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.WHITE.toString(), ChatColor.LIGHT_PURPLE.toString()));
            return;
        }
        if (e.getPlayer().hasPermission("setchannel.youtube") || e.getPlayer().hasPermission("setchannel.twitch")) {
            e.setFormat(e.getFormat().replace(ChatColor.GRAY.toString(), ChatColor.DARK_PURPLE.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.GRAY.toString(), ChatColor.DARK_PURPLE.toString()));
            e.setFormat(e.getFormat().replace(ChatColor.WHITE.toString(), ChatColor.DARK_PURPLE.toString()));
            e.setMessage(e.getMessage().replace(ChatColor.WHITE.toString(), ChatColor.DARK_PURPLE.toString()));
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onVanishedPlayerItemPickup(PlayerPickupItemEvent e) {
        User u = SmarterEssentials.ess3.getUser(e.getPlayer());
        if (u.isVanished()) {
            e.setCancelled(true);
        }
    }

    private Relation getRelationWish(Faction original, Faction fac) {
        return (Relation)FactionUtils.callFactionMethod((Faction)original, (String)"getRelationWish", (ParamBuilder)new ParamBuilder(Faction.class, (Object)fac));
    }

    @EventHandler
    public void onIllegalPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND && SmarterEssentials.factions) {
            Player p = e.getPlayer();
            Faction fpFaction = FactionUtils.getFaction((Player)p);
            Faction locationFaction = FactionUtils.getFactionAt((Location)e.getTo());
            Relation relation = this.getRelationWish(fpFaction, locationFaction);
            if (!(locationFaction == fpFaction || relation.isAlly() || relation.isTruce() || relation.isNeutral() || ((Boolean)FactionUtils.callFactionMethod((Faction)locationFaction, (String)"isWarZone", null)).booleanValue() || ((Boolean)FactionUtils.callFactionMethod((Faction)locationFaction, (String)"isSafeZone", null)).booleanValue() || ((Boolean)FactionUtils.callFactionMethod((Faction)locationFaction, (String)"isNone", null)).booleanValue() || p.isOp())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot teleport to an enemy's territory.");
            }
        }
    }

    @EventHandler
    public void onNickChangeEvent(NickChangeEvent e) {
        if (e.getValue() != null && e.getValue().length() < 3 && !e.getController().getBase().isOp()) {
            e.setCancelled(true);
            e.getController().sendMessage((Object)ChatColor.RED + "Error: " + (Object)ChatColor.DARK_RED + "That nickname is too short.");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().replace("/", "").toLowerCase();
        if (cmd.contains(" ")) {
            cmd = cmd.split(" ")[0];
        }
        if (SmarterEssentials.ess3.getUser(e.getPlayer()).isMuted() && this.blockedMutedCommands.contains(cmd)) {
            e.setCancelled(true);
            e.setMessage("/null");
            return;
        }
        if (cmd.equals("list") || cmd.equals("elist") || cmd.equals("who") || cmd.equals("ewho") || cmd.equals("online") || cmd.equals("eonline") || cmd.equals("playerlist") || cmd.equals("eplayerlist")) {
            e.setCancelled(true);
            e.setMessage("/null");
            Player p = e.getPlayer();
            if (this.listCommandCache != null && System.currentTimeMillis() < this.listCommandCacheExpiration) {
                for (String m : this.listCommandCache) {
                    p.sendMessage(m);
                }
                return;
            }
            this.listCommandCache.clear();
            this.listCommandCacheExpiration = System.currentTimeMillis() + 10000L;
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int maxPlayers = Bukkit.getMaxPlayers();
            this.listCommandCache.add("");
            this.listCommandCache.add((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "--- " + (Object)ChatColor.UNDERLINE + "CosmicPvP Player List" + (Object)ChatColor.AQUA + (Object)ChatColor.BOLD + " ---");
            this.listCommandCache.add("");
            this.listCommandCache.add((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "Players: " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + onlinePlayers + (Object)ChatColor.GRAY + (Object)ChatColor.BOLD + " / " + (Object)ChatColor.GRAY + (Object)ChatColor.BOLD + (maxPlayers - 50) + (Object)ChatColor.GRAY + " players on your planet!");
            if (Bukkit.getPluginManager().isPluginEnabled("CosmicRankQuests")) {
                double traineePlayers = this.getPlayersInRankTier(-1);
                this.listCommandCache.add((Object)ChatColor.GRAY + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Trainee: " + (Object)ChatColor.AQUA + (int)traineePlayers + (Object)ChatColor.GRAY + " [" + (int)(traineePlayers / (double)Bukkit.getOnlinePlayers().size() * 100.0) + "%]");
                for (int rankTier = 1; rankTier <= 6; ++rankTier) {
                    double playersInRankTier = this.getPlayersInRankTier(rankTier);
                    String rank = PlayerRankUtils.getRank((int)rankTier);
                    if (rank == null) continue;
                    ChatColor rankColor = rankTier == 2 ? ChatColor.AQUA : (rankTier == 3 ? ChatColor.BLUE : PlayerRankUtils.getRankColor(String.valueOf(rankTier)));
                    this.listCommandCache.add((Object)rankColor + "" + (Object)ChatColor.BOLD + "* " + (Object)rankColor + WordUtils.capitalize((String)(rank.replace("_plus", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "+" + (Object)ChatColor.YELLOW) + ": ")) + (Object)ChatColor.AQUA + (int)playersInRankTier + (Object)ChatColor.GRAY + " [" + (int)(playersInRankTier / (double)Bukkit.getOnlinePlayers().size() * 100.0) + "%]");
                }
            }
            this.listCommandCache.add("");
            this.listCommandCache.add((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "Factions: " + (Object)ChatColor.AQUA + this.getOnlinePlayersInFactions() + (Object)ChatColor.GRAY + " players" + (Object)ChatColor.GRAY + " online in " + (Object)ChatColor.AQUA + this.getOnlineFactionCount() + " different factions.");
            if (ServerSyncAPI.getCurrentGameServerState() != GameServerState.ONLINE) {
                this.listCommandCache.add((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "VIP Level: " + ServerSyncAPI.getCurrentGameServerState().getName());
            }
            this.listCommandCache.add("");
            for (String msg : this.listCommandCache) {
                p.sendMessage(msg);
            }
            return;
        }
        if (this.message_aliases.contains(cmd) || this.reply_aliases.contains(cmd)) {
            if (e.getMessage().contains("&")) {
                e.setCancelled(true);
                e.setMessage("/null");
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot use &color codes in private messages.");
            } else {
                e.setMessage(ChatColor.stripColor((String)e.getMessage()));
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerDamageLOWEST(EntityDamageEvent e) {
        User u;
        if (e.getEntity() instanceof Player && ((u = SmarterEssentials.ess3.getUser((Player)e.getEntity())).isVanished() || u.isGodModeEnabled())) {
            Player p = (Player)e.getEntity();
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerDamageMONITOR(EntityDamageEvent e) {
        User u;
        if (e.getEntity() instanceof Player && ((u = SmarterEssentials.ess3.getUser((Player)e.getEntity())).isVanished() || u.isGodModeEnabled())) {
            Player p = (Player)e.getEntity();
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler
    public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent e) {
        if (!e.getPlayer().hasPermission("arkkit.staff.hidden")) {
            for (String s : e.getTabCompletions()) {
                if (Bukkit.getPlayer((String)s) == null || !Bukkit.getPlayer((String)s).hasPermission("arkkit.staff.hidden")) continue;
                e.getTabCompletions().remove(s);
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            return;
        }
        if (!e.getFrom().getWorld().equals((Object)e.getTo().getWorld()) || e.getFrom().distanceSquared(e.getTo()) >= 256.0) {
            this.clearTeleportRequests(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerVelocity(PlayerVelocityEvent e) {
        MiningManager miningManager;
        if (e.getTrigger() == VelocityTrigger.EXPLOSION && (miningManager = UserManager.getPlayer((Player)e.getPlayer()).getMiningManager()).getSkillLevel() >= 1000) {
            e.setCancelled(true);
        }
    }

    private void clearTeleportRequests(Player p) {
        if (SmarterEssentials.ess3 != null) {
            try {
                User u = SmarterEssentials.ess3.getUser(p);
                if (u.getTeleportRequest() != null) {
                    u.setTeleportRequest(null);
                    u.setTpRequestLocation(null);
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "A pending /tpa request has been cleared due to you teleporting.");
                    Bukkit.getLogger().info("[Arkkit (SmarterEssentials)] Cleared pending TeleportRequest for: " + p.getName());
                }
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    private int getPlayersInRankTier(int rankTier) {
        int x = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (rankTier == 6) {
                if (!p.hasPermission("cosmicpvp.heroic")) continue;
                ++x;
                continue;
            }
            if (PermissionsAPI.getHighestRankID((String)p.getUniqueId().toString()) != rankTier || rankTier == -1 && p.hasPermission("cosmicpvp.heroic")) continue;
            ++x;
        }
        return x;
    }

    private boolean isNormalFaction(Player player) {
        Faction faction = FactionUtils.getFaction((Player)player);
        return faction != null && (Boolean)FactionUtils.callFactionMethod((Faction)faction, (String)"isNormal", null) != false;
    }

    private int getOnlineFactionCount() {
        HashSet<String> onlineFactionIds = new HashSet<String>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            String factionId = FactionUtils.getFactionId((Player)p);
            if (factionId == null || !this.isNormalFaction(p)) continue;
            onlineFactionIds.add(factionId);
        }
        return onlineFactionIds.size();
    }

    private int getOnlinePlayersInFactions() {
        int x = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!this.isNormalFaction(p)) continue;
            ++x;
        }
        return x;
    }
}

