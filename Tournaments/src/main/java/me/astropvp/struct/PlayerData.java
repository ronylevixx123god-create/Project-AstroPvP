package me.astropvp.struct;

import lombok.Getter;
import lombok.Setter;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.utils.Base64Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.UUID;

public class PlayerData {
    @Getter @Setter private Player player;
    @Getter @Setter private UUID uuid;

    @Getter @Setter private Inventory inventory;

    @Getter @Setter private int tournamentwins;
    @Getter @Setter private int tournamentlosses;

    @Getter @Setter private SkinType helmetSkin;
    @Getter @Setter private SkinType chestplateSkin;
    @Getter @Setter private SkinType leggingsSkin;
    @Getter @Setter private SkinType bootsSkin;

    @Getter @Setter private int kills;
    @Getter @Setter private int deaths;
    @Getter @Setter private int matchwins;
    @Getter @Setter private int matchlosses;

    @Getter @Setter private double kd;
    @Getter @Setter private double twl;
    @Getter @Setter private double mwl;

    @Getter @Setter private int elo;
    @Getter @Setter private int points;

    public boolean loading = false;

    public PlayerData(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        loadData(false);
        if(this.helmetSkin == null)
            this.helmetSkin = SkinType.RUINS_HELMET;
        if(this.chestplateSkin == null)
            this.chestplateSkin = SkinType.RUINS_AMULET;
        if(this.leggingsSkin == null)
            this.leggingsSkin = SkinType.VALENTINES_2021_BELT;
        if(this.bootsSkin == null)
            this.bootsSkin = SkinType.COSMIC_CITIZEN_BOOTS;

    }


    public void loadData(boolean async) {
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CosmicTournamentsPlugin.getInstance(), () -> {
                this.loadData(false);
            });
        } else {
            this.loading = true;
            PreparedStatement statement = null;
            try {
                statement = CosmicTournamentsPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT inventory, tournamentwins, tournamentlosses, matchwins, matchlosses, kills, deaths, elo, points, helmet, chestplate, leggings, boots FROM `tournament_userdata` WHERE player_uuid = ?;");
                statement.setString(1, this.uuid.toString());
                ResultSet results = statement.executeQuery();
                while(results.next()) {
                    Bukkit.broadcastMessage(results.getString("inventory"));
                    this.inventory = Base64Utils.fromBase64(results.getString("inventory").split(":")[0]);
                    this.tournamentlosses = results.getInt("tournamentlosses");
                    this.matchwins = results.getInt("matchwins");
                    this.matchlosses = results.getInt("matchlosses");
                    this.kills = results.getInt("kills");
                    this.deaths = results.getInt("deaths");
                    this.elo = results.getInt("elo");
                    this.twl = this.tournamentwins / this.tournamentlosses;
                    this.mwl = this.matchwins / this.matchlosses;
                    this.kd = this.kills / this.deaths;
                    this.points = results.getInt("points");
                    this.helmetSkin = SkinType.valueOf(results.getString("helmet"));
                    this.chestplateSkin = SkinType.valueOf(results.getString("chestplate"));
                    this.leggingsSkin = SkinType.valueOf(results.getString("leggings"));
                    this.bootsSkin = SkinType.valueOf(results.getString("boots"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    statement.close();
                } catch (Exception var14) {
                    e.printStackTrace();
                }
            }finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception var13) {
                        var13.printStackTrace();
                    }
                }

            }
            this.loading = false;
            CosmicTournamentsPlugin.getInstance().getPlayerDataManager().registerData(this.player, this);
        }
    }

    public void unloadData(boolean async) {
        if (this.loading) {
            Bukkit.getLogger().info("Not saving duel data since we couldnt finish loading in time.");
            return;
        }
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)CosmicTournamentsPlugin.getInstance(), () -> unloadData(false));
            return;
        }
        try {
            PreparedStatement statement = CosmicTournamentsPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO tournament_userdata(inventory, tournamentwins, tournamentlosses, matchwins, matchlosses, kills, deaths, elo, points, helmet, chestplate, leggings, boots) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE inventory = ?, tournamentwins = ?, tournamentlosses = ?, matchwins = ?, matchlosses = ?, kills = ?, deaths = ?, elo = ?, points = ?, helmet = ?, chestplate = ?, leggings = ?, boots = ?;");
            try {
                statement.setString(1, this.uuid.toString());
                statement.setString(2, Base64Utils.itemStackArrayToBase64(player.getInventory().getContents()));
                statement.setInt(3, this.tournamentwins);
                statement.setInt(4, this.tournamentlosses);
                statement.setInt(5, this.matchwins);
                statement.setInt(6, this.matchlosses);
                statement.setInt(7, this.kills);
                statement.setInt(8, this.deaths);
                statement.setInt(9, this.elo);
                statement.setInt(10, this.points);
                statement.setString(11, this.helmetSkin.name());
                statement.setString(12, this.chestplateSkin.name());
                statement.setString(13, this.leggingsSkin.name());
                statement.setString(14, this.bootsSkin.name());

                statement.executeUpdate();
            } finally {
                if (Collections.<PreparedStatement>singletonList(statement).get(0) != null)
                    statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
