package net.astropvp.loottable.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;

import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.LootTablePlugin;
import net.astropvp.loottable.guis.LootTableMainGUI;
import net.astropvp.loottable.struct.LootTableType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("loottable")
@Alias({"loottables", "loot"})
public class LootTableCommand extends CommandBase {
    @Permission("cranked.admin")
    @Default
    public void onDefault(CommandSender sender) {
        LootTableMainGUI lootTableMainGUI = new LootTableMainGUI((Player) sender);
        lootTableMainGUI.getMainGUI().open((Player)sender);
    }


    @Permission("cranked.admin")
    @SubCommand("save")
    public void onSave(CommandSender sender) {
        LootTablePlugin.getInstance().getLootTableManager().saveAll();
        sender.sendMessage(CC.Green + "Saved!!!");
    }

    @Permission("cranked.admin")
    @SubCommand("random")
    public void onRandomItem(CommandSender sender, @Completion("#players") Player receiver, @Completion("#loottables") LootTableType lootTableType) {
        PlayerUtil.giveOrDropProtectedItem(receiver, LootTableAPI.getRandomLootItem(lootTableType), 60);
        sender.sendMessage(CC.translate("&aGiven " + receiver.getName() + " a random " + lootTableType.getFancy() + " item."));
        receiver.sendMessage(CC.translate("&aReceived a random " + lootTableType.getFancy() + " item."));
    }
}
