package me.ifamasssxd.cosmicskycustoms.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import java.util.Arrays;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandCustomItem
implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.isOp()) {
            return true;
        }
        if (args.length >= 3 && args[0].equalsIgnoreCase("give")) {
            ItemStack is;
            Player pl = Bukkit.getPlayer((String)args[1]);
            if (pl == null) {
                commandSender.sendMessage(CC.RedB + "(!) " + CC.Red + "No player found for " + args[1]);
                return true;
            }
            CosmicItem item = CosmicItems.getCustomItem(args[2]);
            if (item == null) {
                commandSender.sendMessage("Unable to find CosmicItem for name " + args[2] + ", valid: " + Arrays.toString(CosmicSkyCustomItems.get().getItemManager().getCustomRegisteredItems().keySet().stream().sorted().toArray()));
                return true;
            }
            if (args.length > 3) {
                Object[] data = new Object[args.length - 3];
                int index = 0;
                for (int i = 3; i < args.length; ++i) {
                    data[index++] = args[i];
                }
                is = item.createItem(null, data);
            } else {
                is = item.createItem(null, new Object[0]);
            }
            PlayerUtil.giveOrDropProtectedItem((Player)pl, (ItemStack)is, (int)-1);
            commandSender.sendMessage(CC.Green + "Item given!");
            return true;
        }
        commandSender.sendMessage(CC.Red + "/customitems give <player> <custom>");
        return false;
    }
}

