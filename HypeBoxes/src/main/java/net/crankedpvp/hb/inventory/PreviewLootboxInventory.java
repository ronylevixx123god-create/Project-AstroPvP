package net.crankedpvp.hb.inventory;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.crankedpvp.hb.HypeboxesPlugin;
import net.crankedpvp.hb.language.Language;
import net.crankedpvp.hb.struct.HypeBox;
import net.crankedpvp.hb.struct.HypeBoxReward;
import net.crankedpvp.hb.utils.CC;
import net.crankedpvp.hb.utils.GuiUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PreviewLootboxInventory {
    private final Gui previewInventory;

    public Gui getUI() {
        return this.previewInventory;
    }

    public PreviewLootboxInventory(Player player, String name, HypeboxesPlugin plugin) {
        HypeBox hypeBox = plugin.getHypeBoxRegister().getHypebox(name);
        this.previewInventory = new Gui(3, CC.translate(hypeBox.getDisplayName()));
        this.previewInventory.setDefaultClickAction(event -> event.setCancelled(true));
        for (HypeBoxReward reward : hypeBox.getRewards()) {
            ItemStack item = reward.getItemStack();
            this.previewInventory.addItem(new GuiItem(reward.buildWithInfo(), event -> {
                if (player.hasPermission("astro.admin")) {
                    if (event.isShiftClick() && event.isRightClick()) {
                        plugin.getHypeBoxManager().removeItemFromHypebox(player, item, name);
                        ItemStack itemStack = item.clone();
                        itemStack.setType(Material.BARRIER);
                        this.previewInventory.updateItem(event.getSlot(), new GuiItem(itemStack));
                        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 2.0F, 2.0F);
                    }
                    if (event.isLeftClick()) {
                        player.getInventory().addItem(item);
                        player.sendMessage(CC.translate(Language.PREFIX.getMessage() + "&a+ " + item.getItemMeta().getDisplayName()));
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2.0F, 2.0F);
                    }
                }
            }));
        }
    }

}
