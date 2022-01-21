package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.manager.CacheManager;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.compilex.CompilexPermissions.structure.PermissionUser;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.perms.Relation;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.*;

public class Near
        extends ArkPlugin {
    private static final DecimalFormat format = new DecimalFormat("#");
    private Essentials essentials;

    public Near(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            Bukkit.getLogger().info("[Arkkit (Near)] Disabling Near patch due to no Essentials.jar loaded.");
            this.kill();
            return;
        }
        this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        this.registerCommand(new CommandNearAbstact("near", "/<command> [playername] [radius]", "Lists the players near by or around a player.", new ArrayList<String>(Arrays.asList("cnear", "enear", "nearby", "enearby"))));
    }

    @Override
    public void disable() {
        this.essentials = null;
    }

    class CommandNearAbstact
            extends AbstractCommand {
        public CommandNearAbstact(String command, String usage, String description, List<String> aliases) {
            super(command, usage, description, aliases);
        }

        @Override
        public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
            if (!sender.hasPermission("essentials.near") && !sender.hasPermission("staff.near")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do this.");
                return true;
            }
            Player player = (Player) sender;
            boolean op = player.isOp();
            if (player.getName().equals("Woofless") || player.getName().equals("TBNRfrags") || player.getName().equals("Lachlan") || player.getName().equals("Vikkstar123")) {
                op = false;
            }
            double maxRadius = 200.0;
            double radius = 200.0;
            String syntax = null;
            if (args.length == 1 || args.length == 2) {
                int index = args.length - 1;
                if (sender.hasPermission("essentials.near.others")) {
                    Player found = Bukkit.getPlayer(args[0]);
                    if (found != null) {
                        player = found;
                    }
                } else {
                    syntax = args[0];
                }
                if (StringUtils.isNumeric(args[index])) {
                    radius = Double.parseDouble(args[index]);
                }
            }
            if (radius <= 0.0) {
                radius = 200.0;
            }
            if (radius > maxRadius && !sender.hasPermission("essentials.near.maxexempt")) {
                radius = maxRadius;
            }
            User user = Near.this.essentials.getUser(player);
            boolean showHidden = user.canInteractVanished();
            HashMap<Player, Double> distanceMap = new HashMap<Player, Double>();
            Location startLoc = player.getLocation();
            FPlayer fuser = FPlayers.getInstance().getByPlayer(user.getBase().getPlayer());
            ArrayList<UUID> discombobulatedUUIDs = new ArrayList<UUID>();
            for (Player pl : Bukkit.getOnlinePlayers()) {
                double distance;
                User playerUser;
                if (pl.hasMetadata("NPC") || Near.this.essentials == null || (playerUser = Near.this.essentials.getUser(pl)).equals(user) || playerUser.isHidden(user.getBase()) && !showHidden && !user.getBase().canSee(playerUser.getBase()) || pl.getWorld() != player.getWorld() || !((distance = startLoc.distance(pl.getLocation())) < radius))
                    continue;
                if (op) {
                    distanceMap.put(pl, distance);
                    continue;
                }
                if (pl.hasMetadata("ghostEnchantment") || pl.hasMetadata("heroicGhostEnchantment")) {
                    double maxGhostDistance;
                    boolean heroic = pl.hasMetadata("heroicGhostEnchantment");
                    int ghostLevel = pl.getMetadata(heroic ? "heroicGhostEnchantment" : "ghostEnchantment").get(0).asInt();
                    FPlayer fp = FPlayers.getInstance().getByPlayer(pl);
                    if (fp.getRelationTo(fuser).isAtMost(Relation.NEUTRAL)) {
                        Relation r = fp.getRelationToLocation();
                        if (ghostLevel >= 1 && r.isMember() || ghostLevel >= 2 && r.isAlly() || ghostLevel >= 3 && r.isTruce())
                            continue;
                    }
                    if (heroic && distance >= (maxGhostDistance = 125 - (ghostLevel - 1) * 50)) continue;
                }
                if (pl.hasMetadata("discombobulateEnchantment")) {
                    FPlayer fp;
                    double distanceSpoof;
                    int level = pl.getMetadata("discombobulateEnchantment").get(0).asInt();
                    if (level == 1) {
                        level = 4;
                    }
                    if ((distanceSpoof = 2 + new Random().nextInt((int) Math.min(distance, level * 20))) < distance - 2.0 && new Random().nextBoolean()) {
                        distanceSpoof = -1 * new Random().nextInt((int) distance + 2);
                    }
                    if ((fp = FPlayers.getInstance().getByPlayer(pl)).getRelationTo(fuser).isAtMost(Relation.NEUTRAL)) {
                        if (distance + distanceSpoof > radius) continue;
                        distanceMap.put(pl, Math.max(2.0, distance + distanceSpoof));
                        discombobulatedUUIDs.add(pl.getUniqueId());
                        continue;
                    }
                }
                distanceMap.put(pl, distance);
            }
            LinkedHashMap<Player, Double> sortedDistance = this.sortByValue(distanceMap);
            if (sortedDistance.size() <= 0) {
                sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "There is no one visible nearby...");
                return true;
            }
            sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "(!) " + ChatColor.AQUA + "There are " + ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + sortedDistance.size() + ChatColor.AQUA + " player(s) nearby:");
            StringBuilder builder = new StringBuilder();
            FPlayer fpl = FPlayers.getInstance().getByPlayer(player);
            int MAX_SEND = 30;
            int sent = 0;
            for (Map.Entry<Player, Double> distance : sortedDistance.entrySet()) {
                FPlayer fplayer;
                if (sent >= MAX_SEND) break;
                boolean discombobulate = discombobulatedUUIDs.contains(distance.getKey().getUniqueId());
                if (syntax != null && (discombobulate || !distance.getKey().getName().toLowerCase().startsWith(syntax) && !distance.getKey().getName().equalsIgnoreCase(syntax)))
                    continue;
                StringBuilder nearbyPlayer = new StringBuilder();
                String rank = this.getRankPrefix(distance.getKey().getUniqueId());
                if (rank != null) {
                    if (!rank.endsWith(" ")) {
                        rank = rank + " ";
                    }
                    nearbyPlayer.append(discombobulate ? ChatColor.RESET + "" + ChatColor.MAGIC + "rank" : rank);
                }
                if ((fplayer = FPlayers.getInstance().getByPlayer(distance.getKey())).hasFaction() && fpl.hasFaction() && fplayer.getFaction().isNormal()) {
                    nearbyPlayer.append(fplayer.getFaction().getRelationTo(fpl.getFaction()).getColor().toString());
                } else {
                    nearbyPlayer.append(ChatColor.WHITE.toString());
                }
                nearbyPlayer.append(discombobulate ? ChatColor.MAGIC : "").append(distance.getKey().getName()).append(ChatColor.RESET).append(ChatColor.AQUA).append(ChatColor.BOLD.toString()).append(" (").append(ChatColor.DARK_PURPLE).append(format.format(distance.getValue())).append("m").append(ChatColor.AQUA).append(ChatColor.BOLD.toString()).append(")");
                if (sortedDistance.size() <= 10) {
                    nearbyPlayer.append("\n");
                } else {
                    nearbyPlayer.append(ChatColor.WHITE).append(", ");
                }
                if (builder.length() + nearbyPlayer.length() >= 13000) {
                    player.sendMessage(this.trimBuilder(builder));
                    builder = new StringBuilder();
                }
                builder.append(nearbyPlayer.toString());
                ++sent;
            }
            sender.sendMessage(this.trimBuilder(builder));
            distanceMap.clear();
            sortedDistance.clear();
            return false;
        }

        private String trimBuilder(StringBuilder builder) {
            String msg = builder.toString();
            if (msg.endsWith(", ")) {
                msg = msg.substring(0, msg.length() - 2);
            }
            return msg;
        }

        public <T> LinkedHashMap<T, Double> sortByValue(Map<T, Double> map) {
            LinkedList<Map.Entry<T, Double>> list = new LinkedList<Map.Entry<T, Double>>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<T, Double>>() {

                @Override
                public int compare(Map.Entry<T, Double> o1, Map.Entry<T, Double> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            LinkedHashMap result = new LinkedHashMap();
            for (Map.Entry entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }

        public String getRankPrefix(UUID uuid) {
            String prefix;
            try {
                prefix = PermissionsAPI.getGlobalRank(uuid);
                if (prefix == null) {
                    List groups;
                    PermissionUser localUser = PermissionsAPI.getPermissionUser(uuid);
                    if (localUser != null) {
                        prefix = localUser.getPrefix();
                    } else if (CacheManager.getCachedPlayerGroups().containsKey(uuid.toString()) && (groups = CacheManager.getCachedPlayerGroups().get(uuid.toString())).size() > 0) {
                        prefix = (String) groups.get(0);
                    }
                }
                if (prefix != null && !prefix.contains("&")) {
                    PermissionGroup group = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(prefix.toLowerCase());
                    prefix = group != null ? group.getPrefix() : StringUtils.capitaliseAllWords(prefix);
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                return null;
            }
            if (prefix != null && !prefix.isEmpty()) {
                prefix = ChatColor.translateAlternateColorCodes('&', prefix).replace(ChatColor.ITALIC.toString(), "").replace(ChatColor.STRIKETHROUGH.toString(), "").replace(ChatColor.BOLD.toString(), "").replace(ChatColor.MAGIC.toString(), "").replace("<", "").replace(">", "");
            }
            return prefix;
        }
    }
}