package me.astropvp.astropvpskins.objects.skins.belts;

import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CorruptPowerBeltSkin extends ItemSkin {

    public void onEquip(Player player) {

        if (player.getLocation().getWorld().getName().equals("world_the_end"))
            return;
        if (player.getLocation().getWorld().getName().equals("space"))
            return;
        EnchantmentAPI.getEnchantment("Godly Overload").applyEquipEffect(player, 5);
    }

    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Godly Overload").applyUnequipEffect(player, 5);
    }
}

