package net.astropvp.core.utils;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class DamageUtils {
    public static void setDamageIncrease(double amountInPercentage, EntityDamageByEntityEvent event) {
        double increaseAmount = getCorrectIncreasePercentage(amountInPercentage);
        double oldDamage = event.getDamage();
        double newDamage = oldDamage * increaseAmount;
        if (!(event.getEntity() instanceof Player))
            return;
        if (!(event.getDamager() instanceof Player))
            return;
        if (event.getDamage() > 150.0D)
            newDamage = 150.0D;

        event.setDamage(newDamage);
    }

    public static void setDamageReduce(double amountInPercentage, EntityDamageByEntityEvent event) {
        double decreaseAmount = getCorrectDecreasePercentage(amountInPercentage);
        double newDamage = event.getDamage() * decreaseAmount;

        event.setDamage(newDamage);
    }

    public static void setDamageIncrease(double amountInPercentage, EntityDamageEvent event) {
        double increaseAmount = getCorrectIncreasePercentage(amountInPercentage);
        double oldDamage = event.getDamage();
        double newDamage = oldDamage * increaseAmount;

        event.setDamage(newDamage);
    }

    public static void setDamageReduce(double amountInPercentage, EntityDamageEvent event) {
        double decreaseAmount = getCorrectDecreasePercentage(amountInPercentage);
        double newDamage = event.getDamage() * decreaseAmount;
        event.setDamage(newDamage);
    }

    private static Double getCorrectIncreasePercentage(double value) {
        if (value < 100.0D)
            return 0.01D * value + 1.0D;
        return 0.01D * value;
    }

    private static Double getCorrectDecreasePercentage(double value) {
        value = 0.01D * value;
        value = 1.0D - value;
        return value;
    }

    public static boolean isPremadePiece(ItemStack itemStack) {
        List<ArmorSet> crystals = ArmorSet.getFromArmorBonusCrystal(itemStack);
        if (crystals.isEmpty()) return false;
        return !crystals.contains(ArmorSet.ASTRO) &&
                !crystals.contains(ArmorSet.ANCIENT) &&
                !crystals.contains(ArmorSet.CHAMPION) &&
                !crystals.contains(ArmorSet.ULTRA) &&
                !crystals.contains(ArmorSet.APOLLO) &&
                !crystals.contains(ArmorSet.SANTA);
    }


    public static boolean wearingOmniArmorSet(Player player) {
        int amount = 0;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                ItemMeta meta = item.getItemMeta();
                if (meta.getLore().contains(CC.translate("&3&lOMNI SET BONUS")))
                    amount++;
            }
        }
        return amount == 3;
    }

    public static boolean isHeroicArmor(ItemStack item) {
        if (item == null)
            return false;
        NBTWrapper wrapper = new NBTWrapper(item);
        return wrapper.getString("cosmicType").equals("HEROIC_ARMOR");
    }

    public static boolean wearingMetaArmorSet(Player player) {
        int metaArmorSetCount = 0;
        ArmorSet armorSet = CosmicArmorSetsAPI.getArmorSet(player.getInventory().getArmorContents());
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR &&
                    isHeroicArmor(item) && armorSet == null)
                metaArmorSetCount++;
        }
        return metaArmorSetCount == 4;
    }
}
