package net.astropvp.core.utils;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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

    public static Double getIncreasedDamageOn(Player player) {
        Map<ArmorSet, Integer> crystals = CosmicArmorSetsAPI.getBonusCrystalsEquipped(player);
        double increase = 0;
        if(!crystals.containsKey(ArmorSet.APOLLO))
            increase = increase + 25;
        if(!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO))
            increase = increase + 25;
        if(!crystals.containsKey(ArmorSet.ANCIENT) && (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO)))
            increase = increase + 10;
        return increase;
    }

    public static Double getDecreasedDamageOn(Player player) {
        Map<ArmorSet, Integer> crystals = CosmicArmorSetsAPI.getBonusCrystalsEquipped(player);
        double decrease = 0;
        if(!crystals.containsKey(ArmorSet.APOLLO))
            decrease = decrease + 25;
        if(!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO))
            decrease = decrease + 25;
        if(!crystals.containsKey(ArmorSet.ANCIENT) && (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO)))
            decrease = decrease + 10;
        return decrease;
    }
}
