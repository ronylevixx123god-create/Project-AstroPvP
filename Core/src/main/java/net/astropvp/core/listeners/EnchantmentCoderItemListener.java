package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import net.astropvp.core.guis.EnchantmentCoderInventory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantmentCoderItemListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        ItemStack item = event.getItem();
        if (!isItem(item))
            return;
        Player player = event.getPlayer();
        if (item.getAmount() > 1) {
            player.sendMessage(CC.translate("&c&l(!) &cYou cannot open stacked Heroic Enchantment Coders."));
            return;
        }
        player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 0.5F, 0.5F);
        player.openInventory(EnchantmentCoderInventory.getInstance().getInventory());
    }

    private boolean isItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return false;
        NBTWrapper wrapper = new NBTWrapper(item);
        if (!wrapper.hasKey("cosmicData"))
            return false;
        NBTTagCompoundBuilder tag = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
        return (tag.hasKey("enchantmentCoder") && tag.getBoolean("enchantmentCoder"));
    }
}
