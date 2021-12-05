package net.astropvp.core.conaxgames.staff.staffmodules.moduletypes;

import lombok.Getter;
import net.astropvp.core.AstroCore;
import net.astropvp.core.conaxgames.staff.StaffModeManager;
import net.astropvp.core.conaxgames.staff.staffmodules.StaffModule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class InteractModule extends StaffModule implements Listener {

    @Getter
    private final StaffModeManager staffModeManager;

    public InteractModule(String name, Material material, int slot, String... description) {
        super(name, material, slot, description);
        this.staffModeManager = AstroCore.getInstance().getStaffModeManager();
    }

    protected abstract void onPlayerInteract(PlayerInteractEvent event);

    @EventHandler
    public void onStaffItemClick(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!this.staffModeManager.isStaffModeToggled(event.getPlayer())) return;
        if (event.getItem() == null) return;
        if (this.getMaterial() != event.getItem().getType()) return;
        this.onPlayerInteract(event);
    }

}
