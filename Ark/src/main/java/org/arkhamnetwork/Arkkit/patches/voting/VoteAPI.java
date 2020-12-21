/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.arkhamnetwork.Arkkit.patches.voting.VS_SQLConnection;
import org.arkhamnetwork.Arkkit.patches.voting.VS_SQLManager;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;

public class VoteAPI {
    private static HashMap<String, Integer> cached_player_vote_counts = new HashMap();
    private static HashMap<String, Long> last_sql_lookup = new HashMap();

    protected static void clearPlayerVoteCache(String player_uuid) {
        cached_player_vote_counts.remove(player_uuid);
        last_sql_lookup.remove(player_uuid);
    }

    protected static void updateLastVotedTime(String player_uuid) {
        VS_SQLManager.sql_query.add("INSERT INTO player_votes(player_uuid, last_vote) VALUES('" + player_uuid + "', " + System.currentTimeMillis() + ") ON DUPLICATE KEY UPDATE last_vote=" + System.currentTimeMillis());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long getLastVotedTime(String player_uuid) {
        if (!ArkPlugins.VOTING.getArkPlugin().isRunning()) {
            return -1L;
        }
        ResultSet rs = null;
        try {
            rs = VS_SQLConnection.getResultSet("SELECT last_vote from player_votes where player_uuid='" + player_uuid + "'");
            if (rs.next()) {
                long l = rs.getLong("last_vote");
                return l;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getPlayerVotes(String player_uuid) {
        if (!ArkPlugins.VOTING.getArkPlugin().isRunning()) {
            return -1;
        }
        if (player_uuid == null) {
            return 0;
        }
        if (cached_player_vote_counts.containsKey(player_uuid) && last_sql_lookup.containsKey(player_uuid) && System.currentTimeMillis() - last_sql_lookup.get(player_uuid) < 30000L) {
            return cached_player_vote_counts.get(player_uuid);
        }
        ResultSet rs = null;
        try {
            rs = VS_SQLConnection.getResultSet("SELECT votes from player_votes where player_uuid='" + player_uuid + "'");
            if (rs.next()) {
                int votes = rs.getInt("votes");
                cached_player_vote_counts.put(player_uuid, votes);
                last_sql_lookup.put(player_uuid, System.currentTimeMillis());
                int n = votes;
                return n;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    protected static void setPlayerVotes(String player_uuid, String player_name, int newVotes) {
        VS_SQLManager.sql_query.add("INSERT INTO player_votes(player_uuid, player_name, votes) VALUES('" + player_uuid + "', '" + player_name + "', " + newVotes + ") ON DUPLICATE KEY UPDATE votes=" + newVotes);
    }

    public static int getTotalVotes() {
        if (!ArkPlugins.VOTING.getArkPlugin().isRunning()) {
            return -1;
        }
        ResultSet rs = null;
        try {
            rs = VS_SQLConnection.getResultSet("SELECT SUM(`votes`) as `total` FROM `player_votes`");
            if (rs.next()) {
                int n = rs.getInt("total");
                return n;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}

