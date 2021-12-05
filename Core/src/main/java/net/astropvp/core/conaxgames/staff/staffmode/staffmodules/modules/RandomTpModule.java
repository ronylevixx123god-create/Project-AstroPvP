package net.astropvp.core.conaxgames.staff.staffmode.staffmodules.modules;

import net.astropvp.core.conaxgames.staff.staffmode.StaffMember;
import net.astropvp.core.conaxgames.staff.staffmode.staffmodules.moduletypes.InteractModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Random;

public class RandomTpModule extends InteractModule {

    public RandomTpModule() {
        super("Random TP", Material.MINECART, 3, "&7Right-Click this item to", "&7teleport to a random player on the server");
    }

    @Override
    protected void onPlayerInteract(PlayerInteractEvent event) {
        Random random = new Random();
        Player player = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];

        StaffMember staffMember = super.getStaffModeManager().getStaffMember(player);

        staffMember.getPlayer().teleport(player, PlayerTeleportEvent.TeleportCause.UNKNOWN);
    }
}
