package net.astropvp.lootfilter.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.enums.CrateType;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import net.astropvp.core.AstroCore;
import net.astropvp.core.struct.AstroPlayer;
import net.astropvp.lootfilter.AstroLootFilterPlugin;
import net.astropvp.lootfilter.guis.LootFilterMainGUI;
import net.astropvp.lootfilter.struct.LootTable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandAlias("lootfilter|filterloot|ftloot|lootft")
public class LootFilterCommand extends BaseCommand {
    @Default
    public void onCommand(Player player) {
        LootFilterMainGUI lootFilterMainGUI = new LootFilterMainGUI(player.getUniqueId());
        lootFilterMainGUI.getLootFilterMainGUI().open(player.getPlayer());
    }
    @Subcommand("togglemessage|message|togglemessages|stfu")
    public void toggleMessage(Player player) {
        AstroPlayer astroPlayer = AstroCore.getInstance().getAstroPlayerManager().getAstroPlayer(player.getUniqueId());
        if (astroPlayer.isLootTableMessageEnabled()) {
            astroPlayer.setLootTableMessageEnabled(false);
            player.sendMessage(CC.translate("&e&l(!)&e LootFilter messages have been &c&lDISABLED&e."));
        } else {
            astroPlayer.setLootTableMessageEnabled(true);
            player.sendMessage(CC.translate("&e&l(!)&e LootFilter messages have been &a&lENABLED&e."));
        }

    }
    @CommandPermission("admin")
    @Subcommand("update|refresh")
    public void updateCommand(Player player) {
        LootTable.SPACE.setLoot(AstroSpaceGrindingAPI.getListItems(MonsterType.SPACE_ALIEN));
        LootTable.SLOTBOT.setLoot(CosmicSlotBotAPI.getSlotBotItems());
        LootTable.ASTRO.setLoot(AstroCrateKeysAPI.getListItems(CrateType.ASTRO));
        LootTable.LEGENDARY.setLoot(AstroCrateKeysAPI.getListItems(CrateType.LEGENDARY));
        LootTable.GODLY.setLoot(AstroCrateKeysAPI.getListItems(CrateType.GODLY));
        LootTable.SEASON.setLoot(AstroCrateKeysAPI.getListItems(CrateType.SEASON));
        LootTable.MUTATED.setLoot(AstroCrateKeysAPI.getListItems(CrateType.MUTATED));

        player.sendMessage(CC.translate("&a&l(!)&a Force updated all loottable filters!"));

    }
}
