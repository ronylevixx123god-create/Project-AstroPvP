package net.astropvp.armorsets.struct;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.sets.PhantomArmorSet;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ArmorSetType {
    PHANTOM(CC.translate("&c&lPhantom"), new PhantomArmorSet());

    private final String display;
    private final ArmorSet armorSet;

    private ArmorSetType(String display, ArmorSet armorSet) {
        this.display = display;
        this.armorSet = armorSet;
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
            NBTItem wrapper = new NBTItem(item);
            NBTCompound cosmicData = wrapper.getOrCreateCompound("cosmicData");
            cosmicData.setString("armorSetType", this.name());
            item = wrapper.getItem();
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
