package net.astropvp.core.conaxgames.staff.staffmodules.modules;

import net.astropvp.core.conaxgames.staff.staffmodules.moduletypes.EntityInteractModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class InventoryExaminerModule extends EntityInteractModule {

    public InventoryExaminerModule() {
        super("Invsee Player", Material.BOOK, 0, "&7Right-Click a player", "to examin his inventory");
    }

    @Override
    protected void onPlayerInteract(PlayerInteractAtEntityEvent event, Player clickedPlayer) {
        Bukkit.dispatchCommand(event.getPlayer(), "invsee " + clickedPlayer.getName());
    }
}
