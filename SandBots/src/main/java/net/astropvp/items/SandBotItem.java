package net.astropvp.items;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SandBotItem {
    public static ItemStack get() {
        ItemStack item = new ItemStack(Material.MONSTER_EGG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.YellowB + "Sandbot");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "Place this bot down to spawn a sandbot.");
        lore.add("");
        lore.add(CC.YellowB + "Information");
        lore.add(CC.Gray + "After placing down this bot, you are able to");
        lore.add(CC.Gray + "place Lapis blocks on a radius of 10x5x10 and");
        lore.add(CC.Gray + "the bot will start placing sand under them");
        lore.add(CC.Gray + "You can also use " + CC.Yellow + "/f sandbots " + CC.Gray + "to check how");
        lore.add(CC.Gray + "many bots your faction has enabled");
        lore.add("");
        lore.add(CC.Gray + "The Sandbot limit is " + CC.Green + "2 per faction.");
        lore.add("");
        lore.add(CC.Gray + "Right-Click to place");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = new NBTTagCompoundBuilder();
        cosmicData.setString("itemSkinType", "BOT_STONE_OFF");
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        wrapper.setString("random", UUID.randomUUID().toString());
        wrapper.setString("cosmicType", "sandbot");
        return wrapper.build();
    }

    public static boolean isSandBot(ItemStack item) {
        if(item == null || item.getType() == Material.AIR)
            return false;
        if(item.getType() != Material.MONSTER_EGG)
            return false;
        NBTWrapper wrapper = new NBTWrapper(item);
        if(!wrapper.hasKey("cosmicType"))
            return false;
        return wrapper.getString("cosmicType").equals("sandbot");
    }
}
