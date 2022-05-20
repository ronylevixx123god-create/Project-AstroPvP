package me.astropvp.astropvpskins.objects.skins.belts;

import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CorruptPowerBeltSkin extends ItemSkin {

    public void onEquip(Player player) {

        if (player.getLocation().getWorld().getName().equals("world_the_end"))
            return;
        if (player.getLocation().getWorld().getName().equals("space"))
            return;
        if (player.getLocation().getWorld().getName().equals("world_outpost_pluto"))
            return;
        EnchantmentAPI.getEnchantment("Godly Overload").applyEquipEffect(player, 5);
    }

    public void onUnequip(Player player) {
        System.out.println("[PowrBelt] Attempting to remove godly overload 5]");
        EnchantmentAPI.getEnchantment("Godly Overload").applyUnequipEffect(player, 5);
        PlayerPotionEffects.untrackPotionEffect(player, new SpecificCustomEnchantment(EnchantmentAPI.getEnchantment("Godly Overload"), 5));

    }
}

