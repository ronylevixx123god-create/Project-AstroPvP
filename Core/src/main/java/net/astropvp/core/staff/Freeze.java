package net.astropvp.core.staff;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Freeze implements Listener {

    @Getter
    public static List<UUID> frozenPlayers = new ArrayList<>();

    public static void handle(Player target, Player staff) {
        if (frozenPlayers.contains(target.getUniqueId())) {
            unfreeze(target, staff);
        } else {
            freeze(target, staff);
        }
    }

    public static void freeze(Player target, Player staff) {
        StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Aqua + target.getName() + CC.Gray + " has been frozen by " + CC.White +  staff.getName());
        frozenPlayers.add(target.getUniqueId());
    }

    public static void unfreeze(Player target, Player staff) {
        StaffChat.sendGlobal(CC.White + "[Freeze] " + CC.Aqua + target.getName() + CC.Gray + " has been unfrozen by " + CC.White +  staff.getName());
        frozenPlayers.remove(target.getUniqueId());
    }
}
