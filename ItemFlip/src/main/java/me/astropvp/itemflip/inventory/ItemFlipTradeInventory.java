package me.astropvp.itemflip.inventory;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import me.astropvp.itemflip.data.ItemFlipData;
import me.astropvp.itemflip.data.ItemFlipPlayerData;
import me.astropvp.itemflip.enums.Color;
import me.astropvp.itemflip.utils.Glow;
import me.astropvp.itemflip.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFlipTradeInventory {
    public static Inventory getInventory(ItemFlipData flip) {
        ItemFlipPlayerData data = flip.getData().get(0);
        ItemFlipPlayerData other = flip.getData().get(1);
        Inventory inventory = Bukkit.createInventory(null, 54, CC.Gray + "Item Flip Inventory");
        inventory.setItem(0, getStakeAcceptPane((flip.getCountdown() > 0) ? flip.getCountdown() : 1, data.isAccepted()));
        inventory.setItem(8, getStakeAcceptPane((flip.getCountdown() > 0) ? flip.getCountdown() : 1, other.isAccepted()));
        Iterator<Color> colors = Arrays.<Color>asList(Color.values()).iterator();
        for (int i = inventory.getSize() - 9; i < inventory.getSize() &&
                colors.hasNext(); i++) {
            Color color = colors.next();
            inventory.setItem(i, getColorPane(color, (data.getColor() == color) ? data.getPlayer() : (
                    (other.getColor() == color) ? other.getPlayer() : null)));
        }
        ItemStack pane = (new ItemBuilder(Material.STAINED_GLASS_PANE)).damage((short)7).displayname(CC.GrayB).build();
        int j;
        for (j = 4; j < inventory.getSize() - 18; j += 9)
            inventory.setItem(j, pane);
        for (j = 36; j < 45; j++)
            inventory.setItem(j, pane);
        List<Integer> slots = Arrays.asList(new Integer[] {
                Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20),
                Integer.valueOf(21), Integer.valueOf(27), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30) });
        int k;
        for (k = 0; k < data.getItems().size(); k++)
            inventory.setItem(((Integer)slots.get(k)).intValue(), data.getItems().get(k));
        slots = Arrays.asList(new Integer[] {
                Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25),
                Integer.valueOf(26), Integer.valueOf(32), Integer.valueOf(33), Integer.valueOf(34), Integer.valueOf(35) });
        for (k = 0; k < other.getItems().size(); k++)
            inventory.setItem(((Integer)slots.get(k)).intValue(), other.getItems().get(k));
        return inventory;
    }

    private static ItemStack getStakeAcceptPane(int amount, boolean accepted) {
        ItemStack item = (new ItemBuilder(Material.STAINED_GLASS_PANE)).damage((short)(accepted ? 5 : 14)).displayname(accepted ? (CC.Green + "Stakes Accepted") : (CC.Red + "Stakes Declined")).lore(accepted ? (CC.Gray + "Modify the stakes to cancel.") : (CC.Gray + "Click to accept the stake.")).amount(amount).build();
        return item;
    }

    private static ItemStack getColorPane(Color color, Player taker) {
        ItemBuilder builder = (new ItemBuilder(Material.WOOL)).damage(color.getId()).displayname(color.getPrefix() + CC.Bold + color.getName());
        if (taker != null)
            builder.lore(color.getPrefix() + taker.getName());
        ItemStack item = builder.build();
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("item_flip_color", color.name());
        item = wrapper.build();
        if (taker != null)
            item = addGlow(item);
        return item;
    }

    private static ItemStack setAmount(ItemStack item, int amount) {
        item.setAmount(amount);
        return item;
    }

    public static ItemStack addGlow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant((Enchantment)new Glow(70), 1, true);
        item.setItemMeta(meta);
        return item;
    }
}
