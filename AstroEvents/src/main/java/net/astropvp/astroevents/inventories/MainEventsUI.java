package net.astropvp.astroevents.inventories;

import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.astroevents.AstroEvents;
import net.astropvp.astroevents.game.MiniGameState;
import net.astropvp.astroevents.game.MiniGameType;
import net.astropvp.astroevents.utils.Utils;
import net.astropvp.astroevents.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainEventsUI {

    @Getter
    private final PaginatedGui eventsUI;

    public MainEventsUI(AstroEvents plugin, Player player) {
        this.eventsUI = new PaginatedGui(1, 9, "Events Menu");
        this.eventsUI.setDefaultClickAction(event -> event.setCancelled(true));

        plugin.getMiniGameRegister().getMiniGames().forEach(((miniGameType, miniGame) -> {

            ItemStack itemStack;
            if (miniGame.getMiniGameState() != MiniGameState.NOT_STARTED) {

                itemStack = new ItemBuilder(miniGame.getGuiMaterial())
                        .setName("&a&l" + Utils.toNiceString(miniGameType.name() + " Event"))
                        .addLore("&a* &7Event state: &a\"" + Utils.toNiceString(miniGame.getMiniGameState().name()),
                                "&a* &7Min/Max players: &a" + miniGame.getMinPlayers() + "&8/&a" + miniGame.getMaxPlayers(),
                                "&a* &7Amount of participants: &a" + miniGame.getParticipants().size(),
                                "",
                                "&7&nLeft-Click&7 to join the " + Utils.toNiceString(miniGameType.name() + " Minigame!"),
                                "&7&nRight-Click&7 to preview the loot.")
                        .colorize()
                        .build();


            } else {
                itemStack = new ItemBuilder(Material.BARRIER)
                        .setName("&a&l" + Utils.toNiceString(miniGameType.name() + " Event"))
                        .addLore("&cThis event is currently not running.",
                                "&cJoin the /discord and react to #events",
                                "&cto stay updated when we do event like these.",
                                "",
                                "&7&nRight-Click&7 to preview the loot.")
                        .colorize()
                        .build();
            }
            if (miniGameType == MiniGameType.PARKOUR) {
                this.eventsUI.setItem(3, new GuiItem(itemStack, event -> {
                    if (event.isLeftClick()) {
                        if (miniGame.getMiniGameState() == MiniGameState.STARTING)
                            miniGame.joinMiniGame(player);
                    } else {
                        LootPreviewUI lootPreviewUI = new LootPreviewUI(plugin, player, miniGame);
                        lootPreviewUI.getEventsUI().open(player);
                        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 2, 2);
                    }
                }));
            } else {
                this.eventsUI.setItem(5, new GuiItem(itemStack, event -> {
                    if (event.isLeftClick()) {
                        if (miniGame.getMiniGameState() == MiniGameState.STARTING)
                        miniGame.joinMiniGame(player);
                    } else {
                        LootPreviewUI lootPreviewUI = new LootPreviewUI(plugin, player, miniGame);
                        lootPreviewUI.getEventsUI().open(player);
                        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 2, 2);
                    }

                }));
            }
        }));
    }


}
