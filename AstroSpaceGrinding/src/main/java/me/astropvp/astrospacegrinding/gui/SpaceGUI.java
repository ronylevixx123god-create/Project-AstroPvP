package me.astropvp.astrospacegrinding.gui;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import lombok.Getter;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.data.SpacePlayerData;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpaceGUI {
        @Getter
        private final PaginatedGui spaceGUI;

        public SpaceGUI(SpacePlayerData spacePlayerData) {
            Player player = spacePlayerData.getPlayer();
            this.spaceGUI = new PaginatedGui(5, "Space Menu");

            this.spaceGUI.setDefaultClickAction(event -> event.setCancelled(true));
            player.sendMessage(CC.translate("&a&l(!)&a Opening the space menu..."));

            spaceGUI.getFiller().fillBorder(new GuiItem(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)).name(CC.translate("&7")).build()));
            ItemStack infoItem = new ItemBuilder(Material.BOOK, CC.translate("&b&lSpace Warp"))
                    .addLoreLine(CC.translate("&7The space lands have been invaded by numerous"))
                    .addLoreLine(CC.translate("&7weird creatures. &bAstronauts&7 team up to try and"))
                    .addLoreLine(CC.translate("&7fight against these monsters."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7The creatures have already invaded certain"))
                    .addLoreLine(CC.translate("&7spots in the space lands. Fight the monsters"))
                    .addLoreLine(CC.translate("&7to obtain some of the &bbest items&7 in the universe."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7Once you are in the space lands, you can"))
                    .addLoreLine(CC.translate("&7type &b/space stats&7 to view your space stats."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&dHeroic Enchantments&7 &7and &6Holy Whitescrolls&7."))
                    .addLoreLine(CC.translate("&7are disabled in the space lands."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7&nClick here&7 to teleport to the space lands."))
                    .build();

            this.spaceGUI.setItem(3, 4, new GuiItem(infoItem, event -> {
                player.chat("/warp space");
            }));


            ItemStack lootItem = new ItemBuilder(Material.CHEST, CC.translate("&6&lPossible Loot"))
                    .addLoreLine(CC.translate("&7Possible loot you can get from killing"))
                    .addLoreLine(CC.translate("&7space creatures in the space lands."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7&nClick here&7 to view the loot."))
                    .build();

            this.spaceGUI.setItem(3, 6, new GuiItem(lootItem, event -> {
                player.openInventory(AstroSpaceGrindingAPI.getLootInventory(MonsterType.SPACE_ALIEN));
            }));
        }



}
