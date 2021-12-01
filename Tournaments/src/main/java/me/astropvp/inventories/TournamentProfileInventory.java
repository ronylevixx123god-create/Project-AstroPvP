package me.astropvp.inventories;

import lombok.Getter;
import lombok.Setter;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.struct.PlayerData;
import me.matthawkk.gui.BaseGUI;
import me.matthawkk.gui.GUIItem;
import me.matthawkk.gui.SubGUI;
import me.matthawkk.gui.items.GUIBackButton;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TournamentProfileInventory extends SubGUI {
    private Player player;

    private Inventory loadout;

    @Getter private PlayerData data;

    @Setter @Getter private GUIItem clicked;

    public TournamentProfileInventory(Player player, GUIItem... previous) {
        super("Edit Profile", 54, previous);
        this.player = player;
        this.clicked = null;
        this.data = CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player);
        this.loadout = this.data.getInventory();
        setItem(45, new GUIBackButton(this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                for(int i = 0; i < getPrevious().length; i++) {
                    if(AstroItemSkinsAPI.hasItemSkin(getPrevious()[i].getItem())) {
                        SkinType type = AstroItemSkinsAPI.getItemSkinByItem(getPrevious()[i].getItem()).get(0);
                        AstroItemSkinsAPI.removeNoDisplay(getPrevious()[i].getItem());
                        getPrevious()[i].setItem(CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player).getSkinOfType(type.getItemSkinType()).apply(getPrevious()[i].getItem()));
                    }
                }
                getPrevious()[0].getGui().open(player);
            }
        });
        for(int i = 0; i < this.data.getInventory().getSize(); i++) {
            setItem(i, new GUIItem(this.data.getInventory().getItem(i), this) {
                public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                    if(getClicked() == null) {
                        setClicked(this);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 0.4f, 0.4f);
                    } else {
                        GUIItem item1 = getClicked();
                        GUIItem item2 = this;
                        switchItems(item1, item2);
                        setClicked(null);
                    }
                    update();
                }
            });
        }
        for(int i = 0; i < 9; i++) {
            setItem(i + 36, getFillerItem());
        }
        setItem(46, new GUIItem(this.data.getHelmetSkin().getItem(), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                new TournamentSkinsInventory(player, ItemSkinType.HELMET, this).open(player);
            }
        });
        setItem(47, new GUIItem(this.data.getChestplateSkin().getItem(), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                new TournamentSkinsInventory(player, ItemSkinType.AMULET, this).open(player);
            }
        });
        setItem(48, new GUIItem(this.data.getLeggingsSkin().getItem(), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                new TournamentSkinsInventory(player, ItemSkinType.BELT, this).open(player);
            }
        });
        setItem(49, new GUIItem(this.data.getBootsSkin().getItem(), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                new TournamentSkinsInventory(player, ItemSkinType.BOOTS, this).open(player);
            }
        });
        setItem(50, new GUIItem(this.data.getAxeSkin().getItem(), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                new TournamentSkinsInventory(player, ItemSkinType.AXE, this).open(player);
            }
        });
        setItem(51, new GUIItem(this.data.getSwordSkin().getItem(), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                new TournamentSkinsInventory(player, ItemSkinType.SWORD, this).open(player);
            }
        });
    }

    public void switchItems(GUIItem item1, GUIItem item2) {
        int slot1 = 0;
        int slot2 = 0;
        for(Map.Entry<Integer, GUIItem> entry : this.items.entrySet()) {
            if(entry.getValue().equals(item1)) {
                slot1 = entry.getKey();
            }
            if(entry.getValue().equals(item2))
                slot2 = entry.getKey();
        }
        setItem(slot1, item2);
        setItem(slot2, item1);
        this.loadout.setItem(slot2, item1.getItem());
        this.loadout.setItem(slot1, item2.getItem());
    }

    public void close(Player player) {
        CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player).setInventory(this.loadout);
    }

    private GUIItem getFillerItem() {
        return new GUIItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5), this) {
            public void onClick(Player player, ClickType clickType, InventoryClickEvent event) {
                event.setCancelled(true);
            }
        };
    }

}
