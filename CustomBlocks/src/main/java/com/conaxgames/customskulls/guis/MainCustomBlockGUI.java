package com.conaxgames.customskulls.guis;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.data.SaveCustomBlocks;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.inventoryui.TimeUtil;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class MainCustomBlockGUI {
    private final PaginatedGui customBlockGui;

    public MainCustomBlockGUI(AbilityData data, Block block) {
        this.customBlockGui = new PaginatedGui(3, 27, "Custom Block Menu");
        ItemStack activateButton = new ItemBuilder(Material.STONE_BUTTON, CC.translate("&a&lActivate Button"))
                .addLoreLine("")
                .addLoreLine(CC.translate("&f&l● &6Expires in: &f" + ((data.getLastActivated() + 3600000) - System.currentTimeMillis() <= 0 ? "✗" : TimeUtil.millisToRoundedTime( (data.getLastActivated() + 3600000) - System.currentTimeMillis()))))
                .addLoreLine(CC.translate("&f&l● &6Status: " + (data.isActive() ? "&a&lACTIVE" : "&c&lUNACTIVE")))
                .addLoreLine(CC.translate("&f&l● &6Price: &e$" + NumberUtils.formatMoney(data.getAbilityType().getPricePerAction())))
                .addLoreLine("")
                .addLoreLine(CC.translate("&7Click to activate this custom block"))
                .build();


        this.customBlockGui.setDefaultClickAction(event -> event.setCancelled(true));

        this.customBlockGui.setItem(12, new GuiItem(CoreSkulls.customBlocks.get(data.getAbilityType().getName()).getCustomBlockItem()));

        this.customBlockGui.setItem(14, new GuiItem(activateButton, event -> {
            if (EconomyUtils.hasBalance((OfflinePlayer) event.getWhoClicked(), data.getAbilityType().getPricePerAction())) {
                EconomyUtils.withdrawBalance((OfflinePlayer) event.getWhoClicked(), data.getAbilityType().getPricePerAction());
                data.setLastActivated(System.currentTimeMillis());
                SaveCustomBlocks.saveCustomBlock((Player) event.getWhoClicked(), data.getAbilityType(), block);
                event.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a Reactivated the CustomBlock for 1 hour!"));
                event.getWhoClicked().sendMessage(CC.translate("&c&l- $" + NumberUtils.formatMoney(data.getAbilityType().getPricePerAction())));
                ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.VILLAGER_YES, 20, 20);
                MainCustomBlockGUI mainCustomBlockGUI = new MainCustomBlockGUI(data, data.getLocation().getBlock());
                mainCustomBlockGUI.getCustomBlockGui().open(event.getWhoClicked());
                try {
                    ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(),data.getLocation(), 2,2,2,0.05f,40);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c You do not have enough money to reactivate this custom block."));
            }

        }));

        this.customBlockGui.getFiller().fillBorder(new GuiItem(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7)).name(CC.translate("&7")).build()));
    }



}
