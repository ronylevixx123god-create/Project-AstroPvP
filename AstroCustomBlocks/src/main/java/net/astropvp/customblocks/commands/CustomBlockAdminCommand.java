package net.astropvp.customblocks.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

@Command("acustomblock")
@Alias({"acblock", "ablock"})
public class CustomBlockAdminCommand extends CommandBase {

    @Default
    @Permission("astro.admin")
    public void onDefault(Player player) {
        player.sendMessage("Help Message!");
    }

    @SubCommand("give")
    @Permission("astro.admin")
    public void onGive(Player player, String type, Integer level) {
        player.getInventory().addItem(CustomBlockType.valueOf(type).getCustomBlock().getCustomBlockItem(CustomBlockType.valueOf(type), level));
        player.sendMessage(CC.Green + "Received Custom Block!");
    }
}
