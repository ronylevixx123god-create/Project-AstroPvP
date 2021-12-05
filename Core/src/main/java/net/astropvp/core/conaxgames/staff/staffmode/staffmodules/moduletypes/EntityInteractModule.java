package net.astropvp.core.conaxgames.staff.staffmode.staffmodules.moduletypes;

import lombok.Getter;
import net.astropvp.core.AstroCore;
import net.astropvp.core.conaxgames.staff.staffmode.StaffModeManager;
import net.astropvp.core.conaxgames.staff.staffmode.staffmodules.StaffModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public abstract class EntityInteractModule extends StaffModule implements Listener {

    @Getter
    private final StaffModeManager staffModeManager;

    public EntityInteractModule(String name, Material material, int slot, String... description) {
        super(name, material, slot, description);
        this.staffModeManager = AstroCore.getInstance().getStaffModeManager();
    }

    protected abstract void onPlayerInteract(PlayerInteractAtEntityEvent event, Player clickedPlayer);

    @EventHandler
    public void onStaffItemClick(PlayerInteractAtEntityEvent event) {
        if (!this.staffModeManager.isStaffModeToggled(event.getPlayer())) return;
        if (event.getRightClicked() == null) return;
        if (!(event.getRightClicked() instanceof Player)) return;

        this.onPlayerInteract(event, (Player) event.getRightClicked());
    }

}
