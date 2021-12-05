package net.astropvp.core.conaxgames.staff;

import net.astropvp.core.AstroCore;
import net.astropvp.core.conaxgames.staff.staffmodules.StaffModulesRegister;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffModeManager {

    private final Map<UUID, StaffMember> staffInStaffMode;
    private final StaffModulesRegister staffModulesRegister;

    public StaffModeManager() {
        this.staffInStaffMode = new HashMap<>();
        this.staffModulesRegister = AstroCore.getInstance().getStaffModulesRegister();
    }

    public void toggleStaffMode(Player player) {
        if (this.isStaffModeToggled(player)) {
            this.toggleStaffModeOff(player);
        } else {
            this.toggleStaffModeOn(player);
        }
    }

    private void toggleStaffModeOn(Player player) {
        this.staffInStaffMode.put(player.getUniqueId(), new StaffMember(player));

        player.setGameMode(GameMode.CREATIVE);
        this.getStaffMember(player).vanishPlayer();
        this.clearInventory(player);

        this.staffModulesRegister.getModules().forEach(staffModule -> {
            player.getInventory().setItem(staffModule.getSlot(), staffModule.getModuleItem());
        });
    }

    private void toggleStaffModeOff(Player player) {
        this.getStaffMember(player).getPlayerInventoryContents().restore(player);
        this.staffInStaffMode.remove(player.getUniqueId());
    }

    public boolean isStaffModeToggled(Player player) {
        return this.staffInStaffMode.containsKey(player.getUniqueId());
    }

    public StaffMember getStaffMember(Player player) {
        return this.staffInStaffMode.get(player.getUniqueId());
    }


    private void clearInventory(Player player){
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().clear();
        player.updateInventory();
    }

}
