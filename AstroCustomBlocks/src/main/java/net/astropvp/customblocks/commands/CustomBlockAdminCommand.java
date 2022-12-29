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
    public void onGive(Player player, String type) {
        player.getInventory().addItem(new CustomBlock(1, CustomBlockType.valueOf(type)).getCustomBlockItem());
        player.sendMessage(CC.Green + "Received Custom Block!");
    }
}
