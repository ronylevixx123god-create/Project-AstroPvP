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

        this.playerNames.add("Christopher");
        this.playerNames.add("Jessica");
        this.playerNames.add("Matthew");
        this.playerNames.add("Ashley");
        this.playerNames.add("Jennifer");
        this.playerNames.add("Joshua");
        this.playerNames.add("Amanda");
        this.playerNames.add("Daniel");
        this.playerNames.add("David");
        this.playerNames.add("James");
        this.playerNames.add("Robert");
        this.playerNames.add("John");
        this.playerNames.add("Joseph");
        this.playerNames.add("Andrew");
        this.playerNames.add("Ryan");
        this.playerNames.add("Brandon");
        this.playerNames.add("Jason");
        this.playerNames.add("Justin");
        this.playerNames.add("Sarah");
        this.playerNames.add("William");
        this.playerNames.add("Jonathan");
        this.playerNames.add("Stephanie");
        this.playerNames.add("Brian");
        this.playerNames.add("Nicole");
        this.playerNames.add("Nicholas");
        this.playerNames.add("Anthony");
        this.playerNames.add("Heather");
        this.playerNames.add("Eric");
        this.playerNames.add("Elizabeth");
        this.playerNames.add("Adam");
        this.playerNames.add("Megan");
        this.playerNames.add("Melissa");
        this.playerNames.add("Kevin");
        this.playerNames.add("Steven");
        this.playerNames.add("Thomas");
        this.playerNames.add("Timothy");
        this.playerNames.add("Christina");
        this.playerNames.add("Kyle");
        this.playerNames.add("Rachel");
        this.playerNames.add("Laura");
        this.playerNames.add("Lauren");
        this.playerNames.add("Amber");
        this.playerNames.add("Brittany");
        this.playerNames.add("Danielle");
        this.playerNames.add("Richard");
        this.playerNames.add("Kimberly");
        this.playerNames.add("Jeffrey");
        this.playerNames.add("Amy");
        this.playerNames.add("Crystal");
        this.playerNames.add("Michelle");
        this.playerNames.add("Tiffany");
        this.playerNames.add("Jeremy");
        this.playerNames.add("Benjamin");
        this.playerNames.add("Mark");
        this.playerNames.add("Emily");
        this.playerNames.add("Aaron");
        this.playerNames.add("Charles");
        this.playerNames.add("Rebecca");
        this.playerNames.add("Jacob");
        this.playerNames.add("Stephen");
        this.playerNames.add("Patrick");
        this.playerNames.add("Sean");
        this.playerNames.add("Erin");
        this.playerNames.add("Zachary");
        this.playerNames.add("Jamie");
        this.playerNames.add("Kelly");
        this.playerNames.add("Samantha");
        this.playerNames.add("Nathan");
        this.playerNames.add("Sara");
        this.playerNames.add("Dustin");
        this.playerNames.add("Paul");
        this.playerNames.add("Angela");
        this.playerNames.add("Tyler");
        this.playerNames.add("Scott");
        this.playerNames.add("Katherine");
        this.playerNames.add("Andrea");
        this.playerNames.add("Gregory");
        this.playerNames.add("Erica");
        this.playerNames.add("Mary");
        this.playerNames.add("Travis");
        this.playerNames.add("Lisa");
        this.playerNames.add("Kenneth");
        this.playerNames.add("Bryan");
        this.playerNames.add("Lindsey");
        this.playerNames.add("Kristen");
        this.playerNames.add("Jose");
        this.playerNames.add("Alexander");
        this.playerNames.add("Jesse");
        this.playerNames.add("Katie");
        this.playerNames.add("Lindsay");
        this.playerNames.add("Shannon");
        this.playerNames.add("Vanessa");
        this.playerNames.add("Courtney");
        this.playerNames.add("Christine");
        this.playerNames.add("Alicia");
        this.playerNames.add("Cody");
        this.playerNames.add("Allison");
        this.playerNames.add("Bradley");
        this.playerNames.add("Samuel");
        this.playerNames.add("Shawn");
        this.playerNames.add("April");
        this.playerNames.add("Derek");
        this.playerNames.add("Kathryn");
        this.playerNames.add("Kristin");
        this.playerNames.add("Chad");
        this.playerNames.add("Jenna");
        this.playerNames.add("Tara");
        this.playerNames.add("Maria");
        this.playerNames.add("Krystal");
        this.playerNames.add("Jared");
        this.playerNames.add("Anna");
        this.playerNames.add("Edward");
        this.playerNames.add("Julie");
        this.playerNames.add("Peter");
        this.playerNames.add("Holly");
        this.playerNames.add("Marcus");
        this.playerNames.add("Kristina");
        this.playerNames.add("Natalie");
        this.playerNames.add("Jordan");
        this.playerNames.add("Victoria");
        this.playerNames.add("Jacqueline");
        this.playerNames.add("Corey");
        this.playerNames.add("Keith");
        this.playerNames.add("Monica");
        this.playerNames.add("Juan");
        this.playerNames.add("Donald");
        this.playerNames.add("Cassandra");
        this.playerNames.add("Meghan");
        this.playerNames.add("Joel");
        this.playerNames.add("Shane");
        this.playerNames.add("Phillip");
        this.playerNames.add("Patricia");
        this.playerNames.add("Brett");
        this.playerNames.add("Ronald");
        this.playerNames.add("Catherine");
        this.playerNames.add("George");
        this.playerNames.add("Antonio");
        this.playerNames.add("Cynthia");
        this.playerNames.add("Stacy");
        this.playerNames.add("Kathleen");
        this.playerNames.add("Raymond");
        this.playerNames.add("Carlos");
        this.playerNames.add("Brandi");
        this.playerNames.add("Douglas");
        this.playerNames.add("Nathaniel");
        this.playerNames.add("Ian");
        this.playerNames.add("Craig");
        this.playerNames.add("Brandy");
        this.playerNames.add("Alex");
        this.playerNames.add("Valerie");
        this.playerNames.add("Veronica");
        this.playerNames.add("Cory");
        this.playerNames.add("Whitney");
        this.playerNames.add("Gary");
        this.playerNames.add("Derrick");
        this.playerNames.add("Philip");
        this.playerNames.add("Luis");
        this.playerNames.add("Diana");
        this.playerNames.add("Chelsea");
        this.playerNames.add("Leslie");
        this.playerNames.add("Caitlin");
        this.playerNames.add("Leah");
        this.playerNames.add("Natasha");
        this.playerNames.add("Erika");
        this.playerNames.add("Casey");
        this.playerNames.add("Latoya");
        this.playerNames.add("Erik");
        this.playerNames.add("Dana");
        this.playerNames.add("Victor");
        this.playerNames.add("Brent");
        this.playerNames.add("Dominique");
        this.playerNames.add("Frank");
        this.playerNames.add("Brittney");
        this.playerNames.add("Evan");
        this.playerNames.add("Gabriel");
        this.playerNames.add("Julia");
        this.playerNames.add("Candice");
        this.playerNames.add("Karen");
        this.playerNames.add("Melanie");
        this.playerNames.add("Adrian");
        this.playerNames.add("Stacey");
        this.playerNames.add("Margaret");
        this.playerNames.add("Sheena");
        this.playerNames.add("Wesley");
        this.playerNames.add("Vincent");
        this.playerNames.add("Alexandra");
        this.playerNames.add("Katrina");
        this.playerNames.add("Bethany");
        this.playerNames.add("Nichole");
        this.playerNames.add("Larry");
        this.playerNames.add("Jeffery");
        this.playerNames.add("Curtis");
        this.playerNames.add("Carrie");
        this.playerNames.add("Todd");
        this.playerNames.add("Blake");
        this.playerNames.add("Christian");
        this.playerNames.add("Randy");
        this.playerNames.add("Dennis");
        this.playerNames.add("Alison");
        this.playerNames.add("Trevor");
        this.playerNames.add("Seth");
        this.playerNames.add("Kara");
        this.playerNames.add("Joanna");
        this.playerNames.add("Rachael");
        this.playerNames.add("Luke");
        this.playerNames.add("Felicia");
        this.playerNames.add("Brooke");
        this.playerNames.add("Austin");
        this.playerNames.add("Candace");
        this.playerNames.add("Jasmine");
        this.playerNames.add("Jesus");
        this.playerNames.add("Alan");
        this.playerNames.add("Susan");
        this.playerNames.add("Sandra");
        this.playerNames.add("Tracy");
        this.playerNames.add("Kayla");
        this.playerNames.add("Nancy");
        this.playerNames.add("Tina");
        this.playerNames.add("Krystle");

    }
}
