package net.astropvp.customblocks.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import net.astropvp.customblocks.struct.CustomBlockType;
import net.astropvp.customblocks.struct.abilities.LevelAble;
import net.astropvp.customblocks.struct.impl.AutoGrinder;
import net.astropvp.customblocks.utils.ItemBuilder;
import net.astropvp.customblocks.utils.TimeUtil;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class AutoGrinderGui {
    private PaginatedGui gui;
    private final ItemStack grinderKeyItem = CrateKeyItem.getItem(CrateType.GRINDER);
    public AutoGrinderGui(Player player, AutoGrinder autoGrinder) throws IllegalAccessException, InstantiationException {
        this.gui = new PaginatedGui(3, 27, "Auto Mob Grinder Menu");
        this.gui.setItem(2, 3, getKeyClaimItem(autoGrinder));
        this.gui.setItem(2, 7, getExpClaimItem(autoGrinder));
        this.gui.setItem(2, 5, getStatusItem(autoGrinder));

        this.gui.setDefaultClickAction(event -> event.setCancelled(true));

    }

    public GuiItem getKeyClaimItem(AutoGrinder autoGrinder) {
        ItemStack item = new ItemBuilder(Material.GOLD_NUGGET)
                .setName("&6&lGrinder Keys &7(x" + NumberUtils.formatMoney(autoGrinder.getGrinderKeys()) + "&7)")
                .addLoreLine("&7Click to claim the keys in this auto grinder!")
                .colorize()
                .build();
        return new GuiItem(item, event -> {
            if (autoGrinder.getGrinderKeys() == 0) return;
            while (event.getWhoClicked().getInventory().firstEmpty() != -1 && autoGrinder.getGrinderKeys() != 0) {
                autoGrinder.setGrinderKeys(autoGrinder.getGrinderKeys()-1);
                PlayerUtil.giveOrDropProtectedItem((Player) event.getWhoClicked(), grinderKeyItem, 60);
            }
            this.gui.updateItem(2, 3, getKeyClaimItem(autoGrinder));


        });
    }

    public GuiItem getExpClaimItem(AutoGrinder autoGrinder) {
        ItemStack item = new ItemBuilder(Material.EXP_BOTTLE)
                .setName("&a&lEXP &7(" + NumberUtils.formatMoney(autoGrinder.getExp()) + "&7)")
                .addLoreLine("&7Click to claim the exp in this auto grinder!")
                .colorize()
                .build();
        return new GuiItem(item, event -> {
            if (autoGrinder.getExp() == 0) return;
            ((Player)event.getWhoClicked()).giveExp(autoGrinder.getExp());
            event.getWhoClicked().sendMessage(CC.translate("&a&l+ " + NumberUtils.formatMoney(autoGrinder.getExp()) + " EXP"));
            autoGrinder.setExp(0);
            this.gui.updateItem(2, 7, getExpClaimItem(autoGrinder));

        });
    }


    public GuiItem getStatusItem(AutoGrinder autoGrinder) {
        ItemStack activateButton = new com.cosmicpvp.cosmicutils.utils.ItemBuilder(Material.STONE_BUTTON, CC.translate("&a&lActivate Button"))
                .addLoreLine("")
                .addLoreLine(CC.translate("&f&l● &6Expires in: &f" + ((autoGrinder.getLastActivated() + 3600000) - System.currentTimeMillis() <= 0 ? "✗" : TimeUtil.fancy((autoGrinder.getLastActivated() + 3600000) - System.currentTimeMillis()))))
                .addLoreLine(CC.translate("&f&l● &6Status: " + (autoGrinder.isActive() ? "&a&lACTIVE" : "&c&lINACTIVE")))
                .addLoreLine(CC.translate("&f&l● &6Price: &e$" + NumberUtils.formatMoney(autoGrinder.getActivationPrice())))
                .addLoreLine("")
                .addLoreLine(CC.translate("&7Click to activate this auto grinder"))
                .build();
        return new GuiItem(activateButton, event -> {
            if (EconomyUtils.hasBalance((OfflinePlayer) event.getWhoClicked(), autoGrinder.getActivationPrice())) {
                autoGrinder.activate((Player) event.getWhoClicked());
                EconomyUtils.withdrawBalance((OfflinePlayer) event.getWhoClicked(), autoGrinder.getActivationPrice());
                event.getWhoClicked().sendMessage(CC.translate("&c&l- " + NumberUtils.formatMoney(autoGrinder.getActivationPrice())));
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c You do not have enough money to activate this auto grinder!"));
            }
            this.gui.updateItem(2, 5, getStatusItem(autoGrinder));

        });
    }
}
