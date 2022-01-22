package net.astropvp.armorsets.struct;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import lombok.Getter;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.sets.*;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ArmorSetType {
    PHANTOM(CC.translate("&c&lPhantom"), new PhantomArmorSet(), CC.Red, 14294340),
    ANCIENT(CC.translate("&3&lAncient"), new AncientArmorSet(), CC.DarkAqua, 3650995),
    ASTRO(CC.translate("&a&lAstro"), new AstroArmorSet(), CC.Green, 5765888),
    DRAGON(CC.translate("&e&lDragon"), new DragonArmorSet(), CC.Yellow, 0xFFEE00),
    ENGINEER(CC.translate("&6&lEngineer"), new EngineerArmorSet(), CC.Gold, 15176967),
    GRINDER(CC.translate("&8&lGrinder"), new GrinderArmorSet(), CC.DarkGray, 0x404040),
    LUNA(CC.translate("&7&lLuna"), new LunaArmorSet(), CC.Gray, 0xA0A0A0),
    SUPREME(CC.translate("&4&lSupreme"), new SupremeArmorSet(), CC.DarkRed, 8264475),
    TRAVELER(CC.translate("&5&lTraveler"), new TravelerArmorSet(), CC.DarkPurple,12001978 ),
    YETI(CC.translate("&b&lYeti"), new YetiArmorSet(), CC.Aqua, 518113),
    YIJKI(CC.translate("&f&lMother of Yijki"), new YijkiArmorSet(), CC.White,0xFFFFFF ),
    KOTH(CC.translate("&d&lKoth"), new PhantomArmorSet(), CC.LightPurple,31999 );

    //OMNI Rbg -> 0x0027FF


    private final String display;
    private final ArmorSet armorSet;
    private final String color;
    private final int heroicRGB;

    private ArmorSetType(String display, ArmorSet armorSet, String color, int heroicRGB) {
        this.display = display;
        this.armorSet = armorSet;
        this.color = color;
        this.heroicRGB = heroicRGB;
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
        List<ItemStack> newItems = new ArrayList<>();

        for (ItemStack item : items) {

            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            lore.add("");
            lore.addAll(set.getArmorLore());
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            NBTWrapper wrapper = new NBTWrapper(item);


            NBTTagCompoundBuilder builder = wrapper.getCompoundOrCreate("cosmicData");
            builder.setString("armorSetType", this.name());
            wrapper.setNBTBase("cosmicData", (NBTBase)builder.get());
            newItems.add(wrapper.build());
        }

        if (set instanceof ArmorSetWeapon) {
            newItems.addAll((((ArmorSetWeapon) set).getWeapons()));
        }

        return newItems;
    }


}
