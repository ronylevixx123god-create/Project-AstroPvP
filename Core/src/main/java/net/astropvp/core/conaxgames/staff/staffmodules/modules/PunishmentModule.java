package net.astropvp.core.conaxgames.staff.staffmodules.modules;

import net.astropvp.core.conaxgames.staff.staffmodules.moduletypes.EntityInteractModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PunishmentModule extends EntityInteractModule {

    public PunishmentModule() {
        super("Punish Player", Material.ANVIL, 3, "Right-Click a player to", "open the punishment GUI");
    }

    @Override
    protected void onPlayerInteract(PlayerInteractAtEntityEvent event, Player clickedPlayer) {
        //TODO: Open punishment menu
    }
}