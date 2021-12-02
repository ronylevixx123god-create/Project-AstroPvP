package net.astropvp.astroevents.inventories;

import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.astroevents.AstroEvents;
import net.astropvp.astroevents.game.MiniGameState;
import net.astropvp.astroevents.utils.Utils;
import net.astropvp.astroevents.utils.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainEventsUI {

    @Getter
    private final PaginatedGui eventsUI;

    public MainEventsUI(AstroEvents plugin, Player player) {
        this.eventsUI = new PaginatedGui(1, 9, "Events Menu");

        plugin.getMiniGameRegister().getMiniGames().forEach(((miniGameType, miniGame) -> {

            if (miniGame.getMiniGameState() != MiniGameState.NOT_STARTED) {

                ItemStack itemStack = new ItemBuilder(miniGame.getGuiMaterial())
                        .setName("&a&l" + Utils.toNiceString(miniGameType.name() + " Event"))
                        .addLore("&a* &7Event state: &a\"" + Utils.toNiceString(miniGame.getMiniGameState().name()),
                                "&a* &7Min/Max players: &a" + miniGame.getMinPlayers() + "&8/&a" + miniGame.getMaxPlayers(),
                                "&a* &7Amount of participants: &a" + miniGame.getParticipants().size(),
                                "",
                                "&7&nLeft-Click&7 to join the " + Utils.toNiceString(miniGameType.name() + " Minigame!"),
                                "&7&nRight-Click&7 to preview the loot.")
                        .colorize()
                        .build();
                this.eventsUI.setItem(4, new GuiItem(itemStack, event -> miniGame.joinMiniGame(player)));


            }
        }));
    }


}
