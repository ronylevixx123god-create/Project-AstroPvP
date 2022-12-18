package me.astropvp.proxypach;
import org.bukkit.plugin.java.*;
import org.bukkit.event.server.*;
import org.bukkit.command.*;
import org.bukkit.plugin.*;
import java.io.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class ProxyPatch extends JavaPlugin implements Listener
{
    public static List<String> playerNames;

    public void onEnable() {
        playerNames = new ArrayList<String>();
        this.addPlayersToBots();
        this.loadConfig();

/*        for (String playerName : playerNames) {
            if (Bukkit.getPlayer(playerName) == null) {
                new ServerCommandEvent(Bukkit.getConsoleSender(), "spoof add " + playerName);
                ServerCommandEvent serverCommandEvent = new ServerCommandEvent(Bukkit.getConsoleSender(), "spoof add " + playerName);
                Bukkit.getScheduler().runTaskLater(this, serverCommandEvent::callEvent, 40L);
            }
        }*/

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
             String playerName = playerNames.get(new Random().nextInt(playerNames.size()));
            if (Bukkit.getPlayer(playerName) == null) {
                new ServerCommandEvent(Bukkit.getConsoleSender(), "spoof add " + playerName);
                ServerCommandEvent serverCommandEvent = new ServerCommandEvent(Bukkit.getConsoleSender(), "spoof add " + playerName);
                serverCommandEvent.callEvent();
            }
        }, 200L, 20L);
    }

    public void onDisable() {
    }

    private void loadConfig() {

    }

    private void loadPlayers() {
        for (final String playerName : playerNames) {
                Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.broadcastMessage(ChatColor.RED + "1 Added"), 80L);
            }
    }


    public void addPlayersToBots() {
        playerNames.add("Haru_s");
        playerNames.add("Xx_Madr1");
        playerNames.add("Charo");
        playerNames.add("jestq");
        playerNames.add("Aywi");
        playerNames.add("1eyn");
        playerNames.add("Clafory");
        playerNames.add("babiw");
        playerNames.add("Socks");
        playerNames.add("Eniva");
        playerNames.add("Hushpuppyzac");
        playerNames.add("niaT_T");
        playerNames.add("Fuddruckers");
        playerNames.add("huric4ne");
        playerNames.add("revclts");
        playerNames.add("sohv");
        playerNames.add("breadmoists");
        playerNames.add("IMakeSkins");
        playerNames.add("PhaserPaul");
        playerNames.add("7kya");
        playerNames.add("Kosted");
        playerNames.add("scoa");
        playerNames.add("waiferthin");
        playerNames.add("DarthVaderJonah");
        playerNames.add("poisonz");
        playerNames.add("revclts");
        playerNames.add("xeyshh");
        playerNames.add("_Caramelii");
        playerNames.add("CosmiKazie");
        playerNames.add("Aloriie");
        playerNames.add("Hurrache");
        playerNames.add("oAnxiety");
        playerNames.add("Bearz_z");
        playerNames.add("xxpz");
        playerNames.add("Clyne");
        playerNames.add("iRafaNove");
        playerNames.add("bbito");
        playerNames.add("intercaetera");
        playerNames.add("babiw");
        playerNames.add("WhyYouReadThis");
        playerNames.add("Blocklist");
        playerNames.add("dumme0504");
        playerNames.add("Aestheitc");
        playerNames.add("babiw");
        playerNames.add("Clafory");
        playerNames.add("CraLey");
        playerNames.add("1eyn");
        playerNames.add("hwurt");
        playerNames.add("Eniva");
        playerNames.add("tomazzzekt");
        playerNames.add("PeteParker");
        playerNames.add("Trench");
        playerNames.add("urgoodlilgirl");
        playerNames.add("iDqxha_");
        playerNames.add("Dehu");
        playerNames.add("toxci");
        playerNames.add("FULLSENDVINCENT");
        playerNames.add("MissEurope");
        playerNames.add("SebTheRandomOne");
        playerNames.add("babiw");
        playerNames.add("snve");
        playerNames.add("Miilyh__");
        playerNames.add("Genesong");
        playerNames.add("Th3K1ng_");
        playerNames.add("ChristmasJonas");
        playerNames.add("GiackyB");
        playerNames.add("wod1s");
        playerNames.add("WhyYouReadThis");
        playerNames.add("_DKN_");
        playerNames.add("Xx_Madr1x");
        playerNames.add("j_pigg");


    }
}
