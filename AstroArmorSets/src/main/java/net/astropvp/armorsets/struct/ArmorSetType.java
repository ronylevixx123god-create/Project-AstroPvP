package net.astropvp.armorsets.struct;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import lombok.Getter;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.sets.PhantomArmorSet;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ArmorSetType {
    PHANTOM(CC.translate("&c&lPhantom"), new PhantomArmorSet(), CC.Red);

    private final String display;
    private final ArmorSet armorSet;
    private final String color;

    private ArmorSetType(String display, ArmorSet armorSet, String color) {
        this.display = display;
        this.armorSet = armorSet;
        this.color = color;
    }

    public String getFirstDisplayNameLetter() {
        return this.color + CC.Bold + name().charAt(0);
    }

    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        ArmorSet set = this.getArmorSet();
        items.add(set.getHelmet());
        items.add(set.getChestplate());
        items.add(set.getLeggings());
        items.add(set.getBoots());

        if (set instanceof ArmorSetWeapon) {
            items.addAll((((ArmorSetWeapon) set).getWeapons()));
        }

        for(int i = 0; i < items.size(); ++i) {
            ItemStack item = items.get(i).clone();
            NBTWrapper wrapper = new NBTWrapper(item);
            NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
            cosmicData.setString("armorSetType", this.name());
            item = wrapper.build();
            List<String> lore = item.getItemMeta().getLore();
            lore.add("");
            lore.addAll(set.getArmorLore());
            lore.add("");
            item = (new ItemBuilder(item)).build();
            item.getItemMeta().setLore(lore);
            items.set(i, item);
        }

        return items;
    }




}
