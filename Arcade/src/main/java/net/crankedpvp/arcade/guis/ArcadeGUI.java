package net.crankedpvp.arcade.guis;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import lombok.Getter;
import net.crankedpvp.arcade.ArcadeAPI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class ArcadeGUI {
    private Gui arcadeGui;

    public ArcadeGUI() {
        this.arcadeGui = new Gui(1, "Astro Arcade");
        this.arcadeGui.setDefaultClickAction(event -> event.setCancelled(true));

        this.arcadeGui.setItem(4, new GuiItem(getArcadeItem(), event -> {
            ((Player) event.getWhoClicked()).chat("/warp arcade");
        }));
    }


    public ItemStack getArcadeItem() {
        ItemBuilder itemBuilder = new ItemBuilder(
                Material.STAINED_CLAY, 1, DyeColor.GREEN.getWoolData())
                .setName(CC.translate("&d&lArcade Warp"))
                .addLoreLine(CC.translate("&7This are is a warp meant"))
                .addLoreLine(CC.translate("&7only for the warriors who hold"))
                .addLoreLine(CC.translate("&7the greatest sets of armor on"))
                .addLoreLine(CC.translate("&7the realm. You must have an armor"))
                .addLoreLine(CC.translate("&7set equipped to join this warp."))
                .addLoreLine("")
                .addLoreLine(CC.translate("&dCurrent Info:"))
                .addLoreLine(CC.translate("&fPlayers: &7" + Bukkit.getWorld(ArcadeAPI.WORLD_NAME).getPlayers().size()))
                .addLoreLine("")
                .addLoreLine(CC.translate("&7Click to teleport to the arcade."));
        return itemBuilder.build();
    }
}
