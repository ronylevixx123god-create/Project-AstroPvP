package net.astropvp.core.conaxgames.staff.staffmodules.modules;

import net.astropvp.core.conaxgames.staff.StaffMember;
import net.astropvp.core.conaxgames.staff.staffmodules.moduletypes.InteractModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTpModule extends InteractModule {

    public RandomTpModule() {
        super("Random TP", Material.MINECART, 1, "&7Right-Click this item to", "&7teleport to a random player on the server");
    }

    @Override
    protected void onPlayerInteract(PlayerInteractEvent event) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int random = new Random().nextInt(players.size());
        Player player = players.get(random);

        StaffMember staffMember = super.getStaffModeManager().getStaffMember(player);

        staffMember.getPlayer().teleport(player, PlayerTeleportEvent.TeleportCause.UNKNOWN);
    }
}
