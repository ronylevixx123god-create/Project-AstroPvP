package net.astropvp.lootfilter.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.astropvp.lootfilter.AstroLootFilterPlugin;
import net.astropvp.lootfilter.guis.LootFilterMainGUI;
import org.bukkit.entity.Player;


public class LootFilterCommand extends BaseCommand {
    @CommandAlias("lootfilter|filterloot|ftloot|lootft")
    public void onCommand(Player player) {
        LootFilterMainGUI lootFilterMainGUI = new LootFilterMainGUI(player.getUniqueId());
        lootFilterMainGUI.getLootFilterMainGUI().open(player.getPlayer());
    }
}
