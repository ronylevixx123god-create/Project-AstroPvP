package me.kingsavaqe.astrotournaments.inventory;

import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import me.kingsavaqe.astrotournaments.inventory.adminSettingsGUI.AdminSettingsGUI;
import me.kingsavaqe.astrotournaments.inventory.settingsGUI.PlayerSettingsGUI;
import me.kingsavaqe.astrotournaments.objects.Arena;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.ItemBuilder;
import me.kingsavaqe.astrotournaments.utils.Utils;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;


public class TournamentGUI {
    private final Gui astroTournamentInventory;

    public Gui getUI() {
        return this.astroTournamentInventory;
    }


    public TournamentGUI(AstroTournamentsPlugin astroTournamentsPlugin, Player player) {
        Map<String, Arena> arenaMap = astroTournamentsPlugin.getArenaRegister().getArenas();
        this.astroTournamentInventory = new Gui(3, "Astro Tournaments Menu");
        this.astroTournamentInventory.setDefaultClickAction(event -> event.setCancelled(true));
        this.astroTournamentInventory.setItem(3, 9, new GuiItem(createExitItem(), event -> {
            player.closeInventory();
        }));

        this.astroTournamentInventory.setItem(3, 8, new GuiItem(createSettingsItem(), event -> {
            new PlayerSettingsGUI(astroTournamentsPlugin, astroTournamentsPlugin.getTournamentManager().getTournamentPlayer(player.getUniqueId())).getUI().open(player);
        }));

        if (player.isOp()) {
            this.astroTournamentInventory.setItem(3, 7, new GuiItem(createAdminMenu(), event -> {
                new AdminSettingsGUI(astroTournamentsPlugin).getUI().open(player);
            }));
        }



        arenaMap.values().forEach(arena -> {
            ItemBuilder itemBuilder = new ItemBuilder(Material.WOOL, "&fArena: " + arena.getId());
            itemBuilder.setColor(DyeColor.WHITE);
            if(arena.isInUse()) {
                TournamentPlayer player1 = arena.getCurrentlyFighting().getLeft();
                TournamentPlayer player2 = arena.getCurrentlyFighting().getRight();
                itemBuilder.setColor(DyeColor.RED);
                itemBuilder.setLore(Arrays.asList(
                        "&bPlayer One: &f" + player1.getPlayer().getName() + " &7&o(" + Utils.round(player1.getPlayer().getHealth(), 1) + ")",
                        "&bPlayer Two: &f" + player2.getPlayer().getName() + " &7&o(" + Utils.round(player2.getPlayer().getHealth(), 1) + ")"

                ));
            }
            this.astroTournamentInventory.addItem(new GuiItem(itemBuilder.colorize().build()));
        });
    }

    private ItemStack createExitItem() {
        return new ItemBuilder(Material.FENCE_GATE, "&cExit tournament menu", "&7You can execute /tournament leave", "&7to leave the tournament event").colorize().build();
    }

    private ItemStack createSettingsItem() {
        return new ItemBuilder(Material.ARMOR_STAND, "&aPlayer Settings", "&7Click here to change your tournament setup/settings").colorize().build();
    }

    private ItemStack createAdminMenu() {
        return new ItemBuilder(Material.SLIME_BALL, "&6Admin Settings &7(Admin Only)", "&7Click here to edit the tournament settings").colorize().build();
    }

}
