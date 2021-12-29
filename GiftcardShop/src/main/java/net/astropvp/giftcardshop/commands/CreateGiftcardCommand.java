package net.astropvp.giftcardshop.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.SneakyThrows;
import net.astropvp.giftcardshop.GiftcardAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateGiftcardCommand implements CommandExecutor {

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        GiftcardAPI.createGiftCard((Player) commandSender, 6969);
        return true;
    }
}
