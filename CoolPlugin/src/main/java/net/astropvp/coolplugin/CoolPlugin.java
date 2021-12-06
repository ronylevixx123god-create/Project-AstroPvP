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
        this.playerNames.add("Gucci_Mane5");
        this.playerNames.add("Verunzy");
        this.playerNames.add("_CamoPixel_");
        this.playerNames.add("TrueMose");
        this.playerNames.add("sunrang2540");
        this.playerNames.add("TheBillieEllish");
        this.playerNames.add("YetzurIdol");
        this.playerNames.add("Black_Beast86");
        this.playerNames.add("_Queen_Sunset_");
        this.playerNames.add("Nalbarm");
        this.playerNames.add("Seriza_");
        this.playerNames.add("MAXXIMLOOL");
        this.playerNames.add("uskas");
        this.playerNames.add("Baby_yoga");
        this.playerNames.add("DevilNashor");
        this.playerNames.add("SweetShades29");
        this.playerNames.add("Friisk_");
        this.playerNames.add("PureGarlicBread");
        this.playerNames.add("summer_one");
        this.playerNames.add("T_arihara");
        this.playerNames.add("darckmomie");
        this.playerNames.add("Teal_Wolf_25");
        this.playerNames.add("wpscans");
        this.playerNames.add("Ozelots");
        this.playerNames.add("SciFi40rkyboi");
        this.playerNames.add("Nifty_03");
        this.playerNames.add("ISherbI");
        this.playerNames.add("HDGameMaster");
        this.playerNames.add("MoVan0902");
        this.playerNames.add("Damian_Esk");
        this.playerNames.add("dead_outsidee");
        this.playerNames.add("SkildOF");
        this.playerNames.add("MarkTheBoy");
        this.playerNames.add("PsychoTeddyNesa");
        this.playerNames.add("");
        this.playerNames.add("Damian_Esk");
        this.playerNames.add("dead_outsidee");
        this.playerNames.add("SkildOF");
        this.playerNames.add("MarkTheBoy");
        this.playerNames.add("PsychoTeddyNesa");
        this.playerNames.add("HykeMC");
        this.playerNames.add("xGloVesZ_");
        this.playerNames.add("santagon");
        this.playerNames.add("IsIffy");
        this.playerNames.add("riley_090");
        this.playerNames.add("NateKrismas");
        this.playerNames.add("Actually__Ash");
        this.playerNames.add("SmiledOsprey231");
        this.playerNames.add("HaokeCity");
        this.playerNames.add("RY_LEN");
        this.playerNames.add("LuKoala");
        this.playerNames.add("Saskrosken");
        this.playerNames.add("RouterRage");
        this.playerNames.add("Vizr31p");
        this.playerNames.add("torin239");
        this.playerNames.add("Hey_Trolls");
        this.playerNames.add("RAPTOREstonia");
        this.playerNames.add("Tvoj");
        this.playerNames.add("T_owl164");
        this.playerNames.add("InsectDuck62683");
        this.playerNames.add("manuelk2010");
        this.playerNames.add("QNati");
        this.playerNames.add("BANP2006");
        this.playerNames.add("xxnin23");
        this.playerNames.add("Missbubonic");
        this.playerNames.add("xxnin23");
        this.playerNames.add("Missbubonic");
        this.playerNames.add("Lt_Noob");
        this.playerNames.add("Diphi");
        this.playerNames.add("LilTorres");
        this.playerNames.add("Sasaki_PC_Geek");
        this.playerNames.add("Aqauzi");
        this.playerNames.add("nyangjji_");
        this.playerNames.add("Licoviski");
        this.playerNames.add("Swiills");
        this.playerNames.add("AlbiNoel");
        this.playerNames.add("Nitroukun7");
        this.playerNames.add("MyRaign");
        this.playerNames.add("Queebi");
        this.playerNames.add("TisChristmasZem");
        this.playerNames.add("oORoverzOo");
        this.playerNames.add("Kirodon");
        this.playerNames.add("lak__");
        this.playerNames.add("Minenkaempfer");
        this.playerNames.add("minekuraft000");
        this.playerNames.add("FrostyFerrqt");
        this.playerNames.add("oKitnick");
        this.playerNames.add("Nx51");
        this.playerNames.add("Estous");
        this.playerNames.add("tamikoii");
        this.playerNames.add("Sally_Yun");
        this.playerNames.add("Covariant");
        this.playerNames.add("ihaveaZZZ");
        this.playerNames.add("Jollibees");
        this.playerNames.add("Sally_Yun");
        this.playerNames.add("Covariant");
        this.playerNames.add("ihaveaZZZ");
        this.playerNames.add("Jollibees");
        this.playerNames.add("Nepetome");
        this.playerNames.add("Rankqd");
        this.playerNames.add("Hyli_");
        this.playerNames.add("ArcticFox0Nyt");
        this.playerNames.add("36ly");
        this.playerNames.add("StabBee");
        this.playerNames.add("paper232");
        this.playerNames.add("ollieeternity");
        this.playerNames.add("wendiga");
        this.playerNames.add("lnquisitormaster");
        this.playerNames.add("totallybuttr");
        this.playerNames.add("Linger__");
        this.playerNames.add("yumi_495");
        this.playerNames.add("ENRAGED_ANIMAL");
        this.playerNames.add("qichsia");
        this.playerNames.add("TheEmeraldLiker");
        this.playerNames.add("engmong");
        this.playerNames.add("paper232");
        this.playerNames.add("ollieeternity");
        this.playerNames.add("wendiga");
        this.playerNames.add("lnquisitormaster");
        this.playerNames.add("totallybuttr");
        this.playerNames.add("Linger__");
        this.playerNames.add("yumi_495");
        this.playerNames.add("ENRAGED_ANIMAL");
        this.playerNames.add("qichsia");
        this.playerNames.add("TheEmeraldLiker");
        this.playerNames.add("engmong");
        this.playerNames.add("nicklesmart");
        this.playerNames.add("Enderbob");
        this.playerNames.add("Nano38");
        this.playerNames.add("fluffybunnys23");
        this.playerNames.add("ilytraq");
        this.playerNames.add("sub2_bartix");
        this.playerNames.add("YeHolyPotato");
        this.playerNames.add("Jotmesq");
        this.playerNames.add("gwifin_");
        this.playerNames.add("Hyli_");
        this.playerNames.add("Nobibba");
        this.playerNames.add("sunrrae");
        this.playerNames.add("Krylet");
        this.playerNames.add("Drialin");
        this.playerNames.add("Thundrite");
        this.playerNames.add("Niunop");
        this.playerNames.add("eatyourmom");
        this.playerNames.add("KidStyx");
        this.playerNames.add("sunrrae");
        this.playerNames.add("Krylet");
        this.playerNames.add("Drialin");
        this.playerNames.add("Thundrite");
        this.playerNames.add("Niunop");
        this.playerNames.add("eatyourmom");
        this.playerNames.add("KidStyx");
        this.playerNames.add("pilfow");
        this.playerNames.add("DeeYx");
        this.playerNames.add("hoopi");
        this.playerNames.add("Wonderkogs");
        this.playerNames.add("CreeperElliot");
        this.playerNames.add("OscarTheCrafter");
        this.playerNames.add("ambsu");
        this.playerNames.add("Ender2030");
        this.playerNames.add("GMRO");
        this.playerNames.add("Kqr_n");
        this.playerNames.add("BrodyPro1000");
        this.playerNames.add("Whysker");
        this.playerNames.add("Dastishka");
        this.playerNames.add("Stalker181");
        this.playerNames.add("piesel3434");
        this.playerNames.add("Toopz_");
        this.playerNames.add("77brn");
        this.playerNames.add("puzzelman");
        this.playerNames.add("ZEK4S");
        this.playerNames.add("Zampini");
        this.playerNames.add("BrodyPro1000");
        this.playerNames.add("Whysker");
        this.playerNames.add("Dastishka");
        this.playerNames.add("Stalker181");
        this.playerNames.add("piesel3434");
        this.playerNames.add("Toopz_");
        this.playerNames.add("77brn");
        this.playerNames.add("puzzelman");
        this.playerNames.add("ZEK4S");
        this.playerNames.add("Zampini");
        this.playerNames.add("KTM_KAPUS_60tka");
        this.playerNames.add("tamwee");
        this.playerNames.add("Cloud6god");
        this.playerNames.add("Eillene");
        this.playerNames.add("nathan_d09");
        this.playerNames.add("OB3R");
        this.playerNames.add("_Terox_");
        this.playerNames.add("Lisenskiy");
        this.playerNames.add("Zynxra");
        this.playerNames.add("Smqt");
        this.playerNames.add("Serdeczek");
        this.playerNames.add("samwi");
        this.playerNames.add("H_seob");
        this.playerNames.add("TeeKubz");
        this.playerNames.add("Marawah");
        this.playerNames.add("C_aar0n");
        this.playerNames.add("Baxori");
        this.playerNames.add("Cactusman8930");
        this.playerNames.add("TeeKubz");
        this.playerNames.add("Marawah");
        this.playerNames.add("C_aar0n");
        this.playerNames.add("Baxori");
        this.playerNames.add("Cactusman8930");
        this.playerNames.add("NoxRapiens");
        this.playerNames.add("Kokichi_Oum4");
        this.playerNames.add("PemiCZE");
        this.playerNames.add("SadApple");
        this.playerNames.add("EJWK");
        this.playerNames.add("BearBeastly5");
        this.playerNames.add("RockInBlood");
        this.playerNames.add("wifea");
        this.playerNames.add("joker_xxl_3");
        this.playerNames.add("YoshiLoverStar");
        this.playerNames.add("extpy");
        this.playerNames.add("phaze58");
        this.playerNames.add("_Oatyy_");
        this.playerNames.add("chookens123");
        this.playerNames.add("bagelomori");
        this.playerNames.add("KiritoLIVE");
        this.playerNames.add("3920509235802409");
        this.playerNames.add("90kk");
        this.playerNames.add("Mqtthew");
        this.playerNames.add("CrazyGreen2131");
        this.playerNames.add("Samucenci");


    }
}
