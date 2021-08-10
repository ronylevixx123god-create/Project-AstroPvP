package me.bryside.weaponrunes.utils;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WeaponBonusUtils {
    public static WeaponBonusType getWeaponBonusByItem(ItemStack i) {
        if (i == null || i.getType() == Material.AIR || !i.hasItemMeta())
            return null;
        NBTWrapper wrapper = new NBTWrapper(i);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("conaxData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData")) : new NBTTagCompoundBuilder();
        return WeaponBonusType.fromId(cosmicData.getString("bonusType"));
    }

    public static boolean hasWeaponBonus(ItemStack i) {
        NBTWrapper wrapper = new NBTWrapper(i);
        if (!wrapper.hasKey("conaxData"))
            return false;
        if (wrapper.hasKey("cosmicType") && wrapper.getString("cosmicType").equals("weaponBonus"))
            return false;
        return (new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData"))).hasKey("bonusType");
    }

    public static boolean isWeaponBonus(ItemStack i) {
        if (i == null || i.getType() == Material.AIR)
            return false;
        NBTWrapper wrapper = new NBTWrapper(i);
        if (!wrapper.hasKey("cosmicType"))
            return false;
        return wrapper.getString("cosmicType").equals("weaponBonus");
    }

    public static boolean isHeroicWeapon(ItemStack i) {
        if (i.getType().toString().endsWith("_SWORD") || i.getType().toString().endsWith("_AXE")) {
            NBTWrapper wrapper = new NBTWrapper(i);
            if (wrapper.getString("cosmicType").equals("HEROIC_WEAPON"))
                return true;
        }
        return false;
    }
}
