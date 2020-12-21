/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  net.minecraft.util.com.google.gson.JsonParser
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.json.JSONObject
 */
package org.arkhamnetwork.Arkkit.patches.xenforosyncaccount;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.minecraft.util.com.google.gson.JsonParser;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_SQLManager;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_Website_SQLConnection;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XF_Website_SQLManager;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XenforoSyncAccount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.JSONObject;

final class CommandSyncAccount
extends AbstractCommand {
    JsonParser parser = new JsonParser();

    public CommandSyncAccount(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().info("/syncaccount is a player-only command.");
            return true;
        }
        final Player pl = (Player)sender;
        if (args.length != 2) {
            sender.sendMessage((Object)ChatColor.YELLOW + "/syncaccount " + (Object)ChatColor.GRAY + "[forum e-mail] [forum password]");
            sender.sendMessage((Object)ChatColor.GRAY + "Link your minecraft character to your forum.cosmicpvp.com account.");
            return true;
        }
        if (args[0].equalsIgnoreCase("unsync") && sender.isOp()) {
            String xenforoUsername = args[1];
            try {
                this.removeUserFromSyncDatabase(xenforoUsername);
                sender.sendMessage("Xenforo User " + xenforoUsername + " has been unsynced from all associated minecraft accounts.");
            }
            catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.AQUA + "We are attempting to sync your account...");
        Arkkit.get().getServer().getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                String inputLine;
                String forum_username = XenforoSyncAccount.getForumUsernameFromPlayerUUID(pl.getUniqueId());
                if (forum_username != null) {
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Your account is already synced to the website account..        " + (Object)ChatColor.GRAY + forum_username);
                    pl.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.RED + "/unsync" + (Object)ChatColor.GRAY + " to un-link your account from " + forum_username + " before trying to link a new account.");
                    return;
                }
                String forum_email = args[0];
                String forum_password = args[1];
                String link = "api.php?email=" + forum_email + "&password=" + forum_password + "&key=" + XenforoSyncAccount.API_KEY;
                URL url = new URL(XenforoSyncAccount.API_URL + link);
                System.out.println(XenforoSyncAccount.API_URL + link);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    try {
                        JSONObject object = new JSONObject(inputLine);
                        if (object.get("valid") != null && object.get("valid").toString().equals("false")) {
                            pl.sendMessage((Object)ChatColor.RED + "You entered an incorrect username/password. If you don't have a website account create one today at " + (Object)ChatColor.UNDERLINE + "forum.cosmicpvp.com");
                            return;
                        }
                        if (object.get("xf_user_id") != null) {
                            String xf_user_id = object.get("xf_user_id").toString().replaceAll("\"", "");
                            String xf_username = object.get("xf_username").toString().replaceAll("\"", "");
                            String xf_email = object.get("xf_email").toString().replaceAll("\"", "");
                            String uuid = pl.getUniqueId().toString();
                            String username = pl.getName();
                            CommandSyncAccount.this.addUserToSyncDatabase(xf_user_id, xf_username, xf_email, uuid, username);
                            pl.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.LIGHT_PURPLE + "Your account " + (Object)ChatColor.AQUA + pl.getName() + (Object)ChatColor.LIGHT_PURPLE + " has been synced to your website account (" + (Object)ChatColor.AQUA + xf_username + (Object)ChatColor.LIGHT_PURPLE + ")!");
                            if (CommandSyncAccount.this.getForumAccountState(xf_user_id).equals("moderated")) {
                                Bukkit.getLogger().info("[Arkkit (XenforoSyncAccount)] Attempting to set user_state of " + xf_user_id + " from moderated to valid");
                                CommandSyncAccount.this.setUserStateApproved(xf_user_id);
                                pl.sendMessage((Object)ChatColor.GRAY + "Your forum.cosmicpvp.com account is now " + CC.Underline + "fully activated!");
                            }
                            if (CommandSyncAccount.this.isForumUsernameAvailable(username)) {
                                Bukkit.getLogger().info("[Arkkit (XenforoSyncAccount)] Attempting to update forum username of " + xf_user_id + " from " + xf_username + " to " + username);
                                CommandSyncAccount.this.attemptToSetForumUsername(xf_user_id, username);
                                pl.sendMessage(CC.Gray + "(Your forum username has been updated to match your in-game synced account: " + CC.LightPurple + username + CC.Gray + ")");
                            } else {
                                Bukkit.getLogger().info("[Arkkit (XenforoSyncAccount)] Skipping forum username update for " + xf_user_id + " from " + xf_username + " to " + username + " (already claimed!)");
                            }
                        } else {
                            pl.sendMessage((Object)ChatColor.RED + "You entered an incorrect username/password. If you don't have a website account create one today at " + (Object)ChatColor.UNDERLINE + "forum.cosmicpvp.com");
                        }
                        return;
                    }
                    catch (Exception err) {
                        try {
                            err.printStackTrace();
                        }
                        catch (Exception err2) {
                            err2.printStackTrace();
                            return;
                        }
                    }
                }
            }
        });
        return true;
    }

    private void addUserToSyncDatabase(String xf_user_id, String xf_username, String xf_email, String uuid, String username) throws SQLException {
        XF_SQLManager.sql_query.add("INSERT INTO mc_users (`xf_user_id`, `xf_username`, `xf_email`, `uuid`, `username`) VALUES ('" + xf_user_id + "', '" + xf_username + "', '" + xf_email + "', '" + uuid + "', '" + username + "') ON DUPLICATE KEY UPDATE uuid='" + uuid + "', username='" + username + "'");
    }

    private void removeUserFromSyncDatabase(String xf_username) throws SQLException {
        XF_SQLManager.sql_query.add("DELETE FROM mc_users WHERE xf_username='" + xf_username + "'");
    }

    private void setUserStateApproved(String xf_user_id) {
        XF_Website_SQLManager.sql_query.add("UPDATE xf_user SET user_state='valid' WHERE user_state='moderated' && user_id=" + xf_user_id);
    }

    private void attemptToSetForumUsername(String xf_user_id, String name) {
        XF_Website_SQLManager.sql_query.add("UPDATE xf_user SET username='" + name + "' WHERE user_id=" + xf_user_id);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getForumAccountState(String xf_user_id) throws SQLException {
        try (Statement statement = null;){
            statement = XF_Website_SQLConnection.getConnection().prepareStatement("SELECT user_state FROM xf_user WHERE user_id=" + xf_user_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String string = rs.getString("user_state");
                return string;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean isForumUsernameAvailable(String playerName) throws SQLException {
        try (Statement statement = null;){
            statement = XF_Website_SQLConnection.getConnection().prepareStatement("SELECT user_id FROM xf_user WHERE username='" + playerName + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                boolean bl = false;
                return bl;
            }
        }
        return true;
    }
}

