package net.astropvp.coolplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoolPlugin extends JavaPlugin implements Listener {
    private List<String> playerNames;

    private int playerCount;

    private int currentCount;

    public void onEnable() {
        this.playerNames = new ArrayList<>();
        this.playerCount = 0;
        this.currentCount = 0;
        addPlayersToBots();
        this.loadConfig();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            String playerName = this.playerNames.get(new Random().nextInt(this.playerNames.size()));
            if ((Bukkit.getPlayer(playerName) == null)) {
                //Bukkit.getPlayer("Bryside").chat("/spoof add " + playerName);
                //Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spoof add " + playerName);
                ServerCommandEvent serverCommandEvent= new ServerCommandEvent(Bukkit.getConsoleSender(), "spoof add " + playerName);
                serverCommandEvent.callEvent();
            }
        }, 400L, 40L);
    }

    public void onDisable() {
        getConfig().set("player-count", this.playerCount);
    }


    private void loadConfig() {
        File file = new File("plugins/CoolPlugin/config.yml");
        if (!file.exists())
            saveDefaultConfig();
        this.playerCount = this.getConfig().getInt("player-count");
    }


    private void loadPlayers() {
        int amount = 0;
        for (String playerName : this.playerNames) {
            if (amount != this.playerCount) {
                Bukkit.getScheduler().runTaskLater(this, () -> {
                    Bukkit.broadcastMessage(ChatColor.RED + "1 Added");
                }, 80L);
                amount++;
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();
        if (!message.startsWith("/setplayers"))
            return;
        if (player.getName().equals("MattHawkk") || player.getName().equals("Bryside")) {
            e.setCancelled(true);
            int count = Integer.parseInt(message.split("")[1]);
            this.playerCount = count;
            player.sendMessage(ChatColor.GREEN + "Player count is now + " + count);
        }
    }


    public void addPlayersToBots() {

        this.playerNames.add("Haru_s");
        this.playerNames.add("Xx_Madr1");
        this.playerNames.add("Charo");
        this.playerNames.add("jestq");
        this.playerNames.add("Aywi");
        this.playerNames.add("Sloanie");
        this.playerNames.add("Herluigi4");
        this.playerNames.add("Remus_Bla");
        this.playerNames.add("SiriusBlack0");
        this.playerNames.add("DoctorHashis");
        this.playerNames.add("GayAtHomeMo");
        this.playerNames.add("Xx_Madr1x");
        this.playerNames.add("j_pigg");
        this.playerNames.add("usel3sm");
        this.playerNames.add("Jawastim");
        this.playerNames.add("Nyrium");
        this.playerNames.add("Izyaliom");
        this.playerNames.add("Nordhagem");
        this.playerNames.add("Xx_Madr1xm");
        this.playerNames.add("ThatDarkPhoenixm");
        this.playerNames.add("ff8ff888m");
        this.playerNames.add("SBruh0518m");
        this.playerNames.add("TechlandDispm");
        this.playerNames.add("Hismario12m");
        this.playerNames.add("Kingyyym");
        this.playerNames.add("Xx_Madr1xm");
        this.playerNames.add("Its_oogm");
        this.playerNames.add("Awxsxmem");
        this.playerNames.add("tigernipm");
        this.playerNames.add("Hismario12m");
        this.playerNames.add("Kingyyym");
        this.playerNames.add("Xx_Madr1xm");
        this.playerNames.add("Its_oogm");
        this.playerNames.add("Awxsxmem");
        this.playerNames.add("tigernipm");
        this.playerNames.add("DS_Lonzo100m");
        this.playerNames.add("elmaco7m");
        this.playerNames.add("tnalsl1gm");
        this.playerNames.add("Nyrium");
        this.playerNames.add("Keyosm");
        this.playerNames.add("WhyYouReadThim");
        this.playerNames.add("Ludm");
        this.playerNames.add("Target_is_Nexum");
        this.playerNames.add("I52m");
        this.playerNames.add("CloudyCrafter0m");
        this.playerNames.add("laharm");
        this.playerNames.add("PIERDZIOm");
        this.playerNames.add("the_bowmastem");
        this.playerNames.add("nonkinahayatakm");
        this.playerNames.add("Flowkignasm");
        this.playerNames.add("FrostyFerrqm");
        this.playerNames.add("Tocsm");
        this.playerNames.add("Just_Xivm");
        this.playerNames.add("k1mng4nm");
        this.playerNames.add("Strich_m");
        this.playerNames.add("Nikname_m");
        this.playerNames.add("FearHem");
        this.playerNames.add("MinecraftPhysicm");
        this.playerNames.add("Tocsm");
        this.playerNames.add("Just_Xivm");
        this.playerNames.add("k1mng4nm");
        this.playerNames.add("Strich_m");
        this.playerNames.add("Nikname_m");
        this.playerNames.add("FearHem");
        this.playerNames.add("MinecraftPhysicm");
        this.playerNames.add("CONJURERm");
        this.playerNames.add("YoLochka_m");
        this.playerNames.add("phrogm");
        this.playerNames.add("SharkBriam");
        this.playerNames.add("Houkm");
        this.playerNames.add("knariam");
        this.playerNames.add("_Safflowem");
        this.playerNames.add("Blakkom");
        this.playerNames.add("MrHotom");
        this.playerNames.add("25minm");
        this.playerNames.add("TakenFinam");
        this.playerNames.add("Barezkm");
        this.playerNames.add("MangoModem");
        this.playerNames.add("ChriMuem");
        this.playerNames.add("mokindacoom");
        this.playerNames.add("michaltygm");
        this.playerNames.add("Chatterfilm");
        this.playerNames.add("ELLIS_TEm");
        this.playerNames.add("Nylum");
        this.playerNames.add("k1mng4nm");
        this.playerNames.add("floixm");
        this.playerNames.add("ELLIS_TEm");
        this.playerNames.add("Nylum");
        this.playerNames.add("k1mng4nm");
        this.playerNames.add("floixm");
        this.playerNames.add("Galzym");
        this.playerNames.add("Pencm");
        this.playerNames.add("KanCi00m");
        this.playerNames.add("karo_12m");
        this.playerNames.add("A33vX_m");
        this.playerNames.add("Quindo_m");
        this.playerNames.add("MonsIsAwaym");
        this.playerNames.add("iQpOhZum");
        this.playerNames.add("rayeofsun4189m");
        this.playerNames.add("SpiderRiatam");
        this.playerNames.add("lo_darkm");
        this.playerNames.add("tenkaippinm");
        this.playerNames.add("wxnteryim");
        this.playerNames.add("ItzPhoebe1531m");
        this.playerNames.add("driftiolm");
        this.playerNames.add("4uTw_m");
        this.playerNames.add("Lerrfiovm");
        this.playerNames.add("flxmerity");
        this.playerNames.add("lara1719");
        this.playerNames.add("ShadowPiggy_");
        this.playerNames.add("Paginou");
        this.playerNames.add("Lentful");
        this.playerNames.add("flxmerity");
        this.playerNames.add("lara1719");
        this.playerNames.add("ShadowPiggy_");
        this.playerNames.add("Paginou");
        this.playerNames.add("Lentful");
        this.playerNames.add("Wettle2");
        this.playerNames.add("klix2");
        this.playerNames.add("SternBloom");
        this.playerNames.add("Samdasoo5428");
        this.playerNames.add("ponana7");
        this.playerNames.add("Jessikeks");
        this.playerNames.add("Yunei_Lawliet");
        this.playerNames.add("Wiroox");
        this.playerNames.add("itayYT");
        this.playerNames.add("Niunop");
        this.playerNames.add("SerPennDragon");
        this.playerNames.add("K3xs1um");
        this.playerNames.add("emroo");
        this.playerNames.add("Mantatiflex");
        this.playerNames.add("dy5010");
        this.playerNames.add("ThyLayered1");
        this.playerNames.add("EyJay1710");
        this.playerNames.add("AngelaKou");


    }
}
