package net.astropvp.loottable.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;

import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.LootTablePlugin;
import net.astropvp.loottable.guis.LootTableMainGUI;
import net.astropvp.loottable.struct.LootTable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("loottable")
@Alias({"loottables", "loot"})
public class LootTableCommand extends CommandBase {

    @Permission("astro.admin")
    @Default
    public void onDefault(CommandSender sender) {
        LootTableMainGUI lootTableMainGUI = new LootTableMainGUI((Player) sender);
        lootTableMainGUI.getMainGUI().open((Player)sender);
    }


    @Permission("astro.admin")
    @SubCommand("save")
    public void onSave(CommandSender sender) {
        LootTablePlugin.getInstance().getLootTableManager().saveAll();
        sender.sendMessage(CC.Green + "Saved!!!");
    }

    @Permission("astro.admin")
    @SubCommand("fix")
    public void onFix(CommandSender sender) {
        for (LootTable table : LootTable.values()) {
            LootTableAPI.fixLoot(table);
        }
        sender.sendMessage(CC.Green + "Fixed!!!");
    }

    @Permission("astro.admin")
    @SubCommand("additem")
    public void addItem(Player player, @Completion("#loottables") LootTable lootTableType, Double chance) {
        if (ItemUtils.isAirOrNull(player.getItemInHand())) {
            player.sendMessage(CC.translate("&c&l(!)&c Please hold an item!"));
            return;
        }
        if (chance < 0) {
            player.sendMessage(CC.translate("&c&l(!)&c Higher the chance retard!"));
            return;
        }
        player.sendMessage(CC.translate("&a&l(!)&a Added " + ItemUtils.getDisplayName(player.getItemInHand()) + " &ato the " + lootTableType.getFancy() + " &aLoottable!"));
        LootTablePlugin.getInstance().getLootTableManager().getLootTable(lootTableType).addItem(chance, player.getItemInHand().clone());

    }

    @Permission("astro.admin")
    @SubCommand("random")
    public void onRandomItem(CommandSender sender, @Completion("#players") Player receiver, @Completion("#loottables") LootTable lootTableType) {
        PlayerUtil.giveOrDropProtectedItem(receiver, LootTableAPI.getRandomLootItem(lootTableType), 60);
        sender.sendMessage(CC.translate("&aGiven " + receiver.getName() + " a random " + lootTableType.getFancy() + " item."));
        receiver.sendMessage(CC.translate("&aReceived a random " + lootTableType.getFancy() + " item."));
    }
}
