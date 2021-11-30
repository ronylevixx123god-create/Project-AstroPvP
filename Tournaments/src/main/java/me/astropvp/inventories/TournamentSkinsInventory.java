package me.astropvp.inventories;

import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.factionupgrades.utils.SlotUtils;
import me.matthawkk.gui.BaseGUI;
import me.matthawkk.gui.GUIItem;
import me.matthawkk.gui.SubGUI;
import me.matthawkk.gui.items.GUIBackButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class TournamentSkinsInventory extends SubGUI {
    public TournamentSkinsInventory(Player player, ItemSkinType type, GUIItem previous) {
        super("Select Skin", SlotUtils.fitSlots(AstroItemSkinsPlugin.getInstance().getSkins(type).size() + 1), previous);
        setItem(0, new GUIBackButton(this));
        List<SkinType> skins = AstroItemSkinsPlugin.getInstance().getSkins(type);
        for(int i = 0; i < skins.size(); i++) {
            setItem(i + 1, new GUIItem(skins.get(i).getItem(), this) {
                public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                    CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player).setSkinOfType(type, AstroItemSkinsAPI.getItemSkin(this.getItem()).get(0));
                    getPrevious()[0].setItem(CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player).getSkinOfType(type).getItem());
                    getPrevious()[0].getGui().open(player);
                    getPrevious()[0].getGui().update();
                }
            });
        }
    }
}
