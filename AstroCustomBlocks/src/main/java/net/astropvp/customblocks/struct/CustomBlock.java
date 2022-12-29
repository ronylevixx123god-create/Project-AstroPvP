package net.astropvp.customblocks.struct;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astropvp.customblocks.data.CustomBlockData;
import net.astropvp.customblocks.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


@Getter
public abstract class CustomBlock {

    public ItemStack getCustomBlockItem(CustomBlockData customBlockData) {
        CustomBlockType customBlockType = CustomBlockType.valueOf(customBlockData.getType());
        int level = customBlockData.getLevel();
        return getItemStack(customBlockType, level);
    }

    public ItemStack getCustomBlockItem(CustomBlockType customBlockType, int level) {
        return getItemStack(customBlockType, level);
    }

    private ItemStack getItemStack(CustomBlockType customBlockType, int level) {
        ItemStack block = new ItemBuilder(Material.NOTE_BLOCK)
                .setName(customBlockType.getColor() + "&l" + customBlockType.getDisplayName() + " &7(&fLVL " + level + "&7)")
                .addLoreLine(customBlockType.getColor() + "Price to Place: &f" + customBlockType.getPlacePrice())
                .addLoreLine(customBlockType.getColor() + "Price to Mine: &f" + customBlockType.getMinePrice())
                .addLoreLine("")
                .addLoreLines(customBlockType.getLore())
                .addLoreLine("")
                .addLoreLine("&7Place custom block to use!")
                .colorize()
                .build();
        NBTWrapper nbtItem = new NBTWrapper(block);
        NBTTagCompoundBuilder cosmicData = nbtItem.hasKey("cosmicData") ? new NBTTagCompoundBuilder(nbtItem.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        NBTTagCompoundBuilder cosmicData2 = cosmicData.hasKey("customBlockData") ? new NBTTagCompoundBuilder(cosmicData.getNBTBase("customBlockData")) : new NBTTagCompoundBuilder();
        nbtItem.setString("cosmicType", "custom_block");
        cosmicData2.setInt("level", level);
        cosmicData2.setString("type", customBlockType.name());
        cosmicData.setNBTBase("customBlockData", cosmicData2.get());
        nbtItem.setNBTBase("cosmicData", cosmicData.get());
        return nbtItem.build();
    }

    public abstract void onClick(Player clicker);


}
